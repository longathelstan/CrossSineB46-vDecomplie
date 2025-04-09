/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.serializer;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.comments.CommentLine;
import com.viaversion.viaversion.libs.snakeyaml.emitter.Emitable;
import com.viaversion.viaversion.libs.snakeyaml.events.AliasEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.CommentEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.DocumentEndEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.DocumentStartEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.ImplicitTuple;
import com.viaversion.viaversion.libs.snakeyaml.events.MappingEndEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.MappingStartEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.ScalarEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.SequenceEndEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.SequenceStartEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.StreamEndEvent;
import com.viaversion.viaversion.libs.snakeyaml.events.StreamStartEvent;
import com.viaversion.viaversion.libs.snakeyaml.nodes.AnchorNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.MappingNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Node;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeId;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeTuple;
import com.viaversion.viaversion.libs.snakeyaml.nodes.ScalarNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.SequenceNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Tag;
import com.viaversion.viaversion.libs.snakeyaml.resolver.Resolver;
import com.viaversion.viaversion.libs.snakeyaml.serializer.AnchorGenerator;
import com.viaversion.viaversion.libs.snakeyaml.serializer.SerializerException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Serializer {
    private final Emitable emitter;
    private final Resolver resolver;
    private final boolean explicitStart;
    private final boolean explicitEnd;
    private DumperOptions.Version useVersion;
    private final Map<String, String> useTags;
    private final Set<Node> serializedNodes;
    private final Map<Node, String> anchors;
    private final AnchorGenerator anchorGenerator;
    private Boolean closed;
    private final Tag explicitRoot;

    public Serializer(Emitable emitter, Resolver resolver, DumperOptions opts, Tag rootTag) {
        if (emitter == null) {
            throw new NullPointerException("Emitter must  be provided");
        }
        if (resolver == null) {
            throw new NullPointerException("Resolver must  be provided");
        }
        if (opts == null) {
            throw new NullPointerException("DumperOptions must  be provided");
        }
        this.emitter = emitter;
        this.resolver = resolver;
        this.explicitStart = opts.isExplicitStart();
        this.explicitEnd = opts.isExplicitEnd();
        if (opts.getVersion() != null) {
            this.useVersion = opts.getVersion();
        }
        this.useTags = opts.getTags();
        this.serializedNodes = new HashSet<Node>();
        this.anchors = new HashMap<Node, String>();
        this.anchorGenerator = opts.getAnchorGenerator();
        this.closed = null;
        this.explicitRoot = rootTag;
    }

    public void open() throws IOException {
        if (this.closed != null) {
            if (Boolean.TRUE.equals(this.closed)) {
                throw new SerializerException("serializer is closed");
            }
            throw new SerializerException("serializer is already opened");
        }
        this.emitter.emit(new StreamStartEvent(null, null));
        this.closed = Boolean.FALSE;
    }

    public void close() throws IOException {
        if (this.closed == null) {
            throw new SerializerException("serializer is not opened");
        }
        if (!Boolean.TRUE.equals(this.closed)) {
            this.emitter.emit(new StreamEndEvent(null, null));
            this.closed = Boolean.TRUE;
            this.serializedNodes.clear();
            this.anchors.clear();
        }
    }

    public void serialize(Node node) throws IOException {
        if (this.closed == null) {
            throw new SerializerException("serializer is not opened");
        }
        if (this.closed.booleanValue()) {
            throw new SerializerException("serializer is closed");
        }
        this.emitter.emit(new DocumentStartEvent(null, null, this.explicitStart, this.useVersion, this.useTags));
        this.anchorNode(node);
        if (this.explicitRoot != null) {
            node.setTag(this.explicitRoot);
        }
        this.serializeNode(node, null);
        this.emitter.emit(new DocumentEndEvent(null, null, this.explicitEnd));
        this.serializedNodes.clear();
        this.anchors.clear();
    }

    private void anchorNode(Node node) {
        if (node.getNodeId() == NodeId.anchor) {
            node = ((AnchorNode)node).getRealNode();
        }
        if (this.anchors.containsKey(node)) {
            String anchor = this.anchors.get(node);
            if (null == anchor) {
                anchor = this.anchorGenerator.nextAnchor(node);
                this.anchors.put(node, anchor);
            }
        } else {
            this.anchors.put(node, node.getAnchor() != null ? this.anchorGenerator.nextAnchor(node) : null);
            switch (node.getNodeId()) {
                case sequence: {
                    SequenceNode seqNode = (SequenceNode)node;
                    List<Node> list = seqNode.getValue();
                    for (Node item : list) {
                        this.anchorNode(item);
                    }
                    break;
                }
                case mapping: {
                    MappingNode mnode = (MappingNode)node;
                    List<NodeTuple> map = mnode.getValue();
                    for (NodeTuple object : map) {
                        Node key = object.getKeyNode();
                        Node value = object.getValueNode();
                        this.anchorNode(key);
                        this.anchorNode(value);
                    }
                    break;
                }
            }
        }
    }

    private void serializeNode(Node node, Node parent) throws IOException {
        if (node.getNodeId() == NodeId.anchor) {
            node = ((AnchorNode)node).getRealNode();
        }
        String tAlias = this.anchors.get(node);
        if (this.serializedNodes.contains(node)) {
            this.emitter.emit(new AliasEvent(tAlias, null, null));
        } else {
            this.serializedNodes.add(node);
            switch (node.getNodeId()) {
                case scalar: {
                    ScalarNode scalarNode = (ScalarNode)node;
                    this.serializeComments(node.getBlockComments());
                    Tag detectedTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), true);
                    Tag defaultTag = this.resolver.resolve(NodeId.scalar, scalarNode.getValue(), false);
                    ImplicitTuple tuple = new ImplicitTuple(node.getTag().equals(detectedTag), node.getTag().equals(defaultTag));
                    ScalarEvent event = new ScalarEvent(tAlias, node.getTag().getValue(), tuple, scalarNode.getValue(), null, null, scalarNode.getScalarStyle());
                    this.emitter.emit(event);
                    this.serializeComments(node.getInLineComments());
                    this.serializeComments(node.getEndComments());
                    break;
                }
                case sequence: {
                    SequenceNode seqNode = (SequenceNode)node;
                    this.serializeComments(node.getBlockComments());
                    boolean implicitS = node.getTag().equals(this.resolver.resolve(NodeId.sequence, null, true));
                    this.emitter.emit(new SequenceStartEvent(tAlias, node.getTag().getValue(), implicitS, null, null, seqNode.getFlowStyle()));
                    List<Node> list = seqNode.getValue();
                    for (Node item : list) {
                        this.serializeNode(item, node);
                    }
                    this.emitter.emit(new SequenceEndEvent(null, null));
                    this.serializeComments(node.getInLineComments());
                    this.serializeComments(node.getEndComments());
                    break;
                }
                default: {
                    this.serializeComments(node.getBlockComments());
                    Tag implicitTag = this.resolver.resolve(NodeId.mapping, null, true);
                    boolean implicitM = node.getTag().equals(implicitTag);
                    MappingNode mnode = (MappingNode)node;
                    List<NodeTuple> map = mnode.getValue();
                    if (mnode.getTag() == Tag.COMMENT) break;
                    this.emitter.emit(new MappingStartEvent(tAlias, mnode.getTag().getValue(), implicitM, null, null, mnode.getFlowStyle()));
                    for (NodeTuple row : map) {
                        Node key = row.getKeyNode();
                        Node value = row.getValueNode();
                        this.serializeNode(key, mnode);
                        this.serializeNode(value, mnode);
                    }
                    this.emitter.emit(new MappingEndEvent(null, null));
                    this.serializeComments(node.getInLineComments());
                    this.serializeComments(node.getEndComments());
                }
            }
        }
    }

    private void serializeComments(List<CommentLine> comments) throws IOException {
        if (comments == null) {
            return;
        }
        for (CommentLine line : comments) {
            CommentEvent commentEvent = new CommentEvent(line.getCommentType(), line.getValue(), line.getStartMark(), line.getEndMark());
            this.emitter.emit(commentEvent);
        }
    }
}

