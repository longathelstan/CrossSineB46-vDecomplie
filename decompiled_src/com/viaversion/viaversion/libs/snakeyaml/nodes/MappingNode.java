/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.nodes;

import com.viaversion.viaversion.libs.snakeyaml.DumperOptions;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.nodes.CollectionNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeId;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeTuple;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Tag;
import java.util.List;

public class MappingNode
extends CollectionNode<NodeTuple> {
    private List<NodeTuple> value;
    private boolean merged = false;

    public MappingNode(Tag tag, boolean resolved, List<NodeTuple> value, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
        super(tag, startMark, endMark, flowStyle);
        if (value == null) {
            throw new NullPointerException("value in a Node is required.");
        }
        this.value = value;
        this.resolved = resolved;
    }

    public MappingNode(Tag tag, List<NodeTuple> value, DumperOptions.FlowStyle flowStyle) {
        this(tag, true, value, null, null, flowStyle);
    }

    @Override
    public NodeId getNodeId() {
        return NodeId.mapping;
    }

    @Override
    public List<NodeTuple> getValue() {
        return this.value;
    }

    public void setValue(List<NodeTuple> mergedValue) {
        this.value = mergedValue;
    }

    public void setOnlyKeyType(Class<? extends Object> keyType) {
        for (NodeTuple nodes : this.value) {
            nodes.getKeyNode().setType(keyType);
        }
    }

    public void setTypes(Class<? extends Object> keyType, Class<? extends Object> valueType) {
        for (NodeTuple nodes : this.value) {
            nodes.getValueNode().setType(valueType);
            nodes.getKeyNode().setType(keyType);
        }
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (NodeTuple node : this.getValue()) {
            buf.append("{ key=");
            buf.append(node.getKeyNode());
            buf.append("; value=");
            if (node.getValueNode() instanceof CollectionNode) {
                buf.append(System.identityHashCode(node.getValueNode()));
            } else {
                buf.append(node);
            }
            buf.append(" }");
        }
        String values2 = buf.toString();
        return "<" + this.getClass().getName() + " (tag=" + this.getTag() + ", values=" + values2 + ")>";
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public boolean isMerged() {
        return this.merged;
    }
}

