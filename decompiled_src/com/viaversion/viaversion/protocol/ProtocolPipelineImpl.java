/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.protocol.AbstractSimpleProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.ProtocolUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ProtocolPipelineImpl
extends AbstractSimpleProtocol
implements ProtocolPipeline {
    private final List<Protocol> protocolList = new ArrayList<Protocol>();
    private final Set<Class<? extends Protocol>> protocolSet = new HashSet<Class<? extends Protocol>>();
    private final UserConnection userConnection;
    private List<Protocol> reversedProtocolList = new ArrayList<Protocol>();
    private int baseProtocols;

    public ProtocolPipelineImpl(UserConnection userConnection) {
        this.userConnection = userConnection;
        userConnection.getProtocolInfo().setPipeline(this);
        this.registerPackets();
    }

    @Override
    protected void registerPackets() {
        this.add(Via.getManager().getProtocolManager().getBaseProtocol());
    }

    @Override
    public void init(UserConnection userConnection) {
        throw new UnsupportedOperationException("ProtocolPipeline can only be initialized once");
    }

    @Override
    public void add(Protocol protocol) {
        this.reversedProtocolList.add(this.baseProtocols, protocol);
        if (protocol.isBaseProtocol()) {
            this.protocolList.add(this.baseProtocols, protocol);
            ++this.baseProtocols;
        } else {
            this.protocolList.add(protocol);
        }
        this.protocolSet.add(protocol.getClass());
        protocol.init(this.userConnection);
    }

    @Override
    public void add(Collection<Protocol> protocols) {
        for (Protocol protocol : protocols) {
            if (protocol.isBaseProtocol()) {
                throw new UnsupportedOperationException("Base protocols cannot be added in bulk");
            }
            protocol.init(this.userConnection);
            this.protocolSet.add(protocol.getClass());
        }
        this.protocolList.addAll(protocols);
        this.refreshReversedList();
    }

    private void refreshReversedList() {
        int i;
        ArrayList<Protocol> reversedProtocols = new ArrayList<Protocol>(this.protocolList.size());
        for (i = 0; i < this.baseProtocols; ++i) {
            reversedProtocols.add(this.protocolList.get(i));
        }
        for (i = this.protocolList.size() - 1; i >= this.baseProtocols; --i) {
            reversedProtocols.add(this.protocolList.get(i));
        }
        this.reversedProtocolList = reversedProtocols;
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws InformativeException, CancelException {
        int originalID = packetWrapper.getId();
        DebugHandler debugHandler = Via.getManager().debugHandler();
        boolean debug = debugHandler.enabled();
        if (debug && !debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper, direction)) {
            this.logPacket(direction, state, packetWrapper, originalID);
        }
        packetWrapper.apply(direction, state, this.protocolListFor(direction));
        super.transform(direction, state, packetWrapper);
        if (debug && debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper, direction)) {
            this.logPacket(direction, state, packetWrapper, originalID);
        }
    }

    private List<Protocol> protocolListFor(Direction direction) {
        return direction == Direction.SERVERBOUND ? this.protocolList : this.reversedProtocolList;
    }

    private void logPacket(Direction direction, State state, PacketWrapper packetWrapper, int originalID) {
        String string;
        ProtocolInfo protocolInfo = this.userConnection.getProtocolInfo();
        String actualUsername = protocolInfo.getUsername();
        if (actualUsername != null) {
            String string2 = actualUsername;
            string = string2 + " ";
        } else {
            string = "";
        }
        String username = string;
        Via.getPlatform().getLogger().log(Level.INFO, "{0}{1} {2}: {3} ({4}) -> {5} ({6}) [{7}] {8}", new Object[]{username, direction, state, originalID, ProtocolUtil.toNiceHex(originalID), packetWrapper.getId(), ProtocolUtil.toNiceHex(packetWrapper.getId()), protocolInfo.protocolVersion().getName(), packetWrapper});
    }

    @Override
    public boolean contains(Class<? extends Protocol> protocolClass) {
        return this.protocolSet.contains(protocolClass);
    }

    @Override
    public <P extends Protocol> @Nullable P getProtocol(Class<P> pipeClass) {
        for (Protocol protocol : this.protocolList) {
            if (protocol.getClass() != pipeClass) continue;
            return (P)protocol;
        }
        return null;
    }

    @Override
    public List<Protocol> pipes(@Nullable Class<? extends Protocol> protocolClass, boolean skipCurrentPipeline, Direction direction) {
        int i;
        List<Protocol> protocolList = this.protocolListFor(direction);
        int index2 = this.indexOf(protocolClass, skipCurrentPipeline, protocolList);
        ArrayList<Protocol> pipes = new ArrayList<Protocol>(this.baseProtocols + protocolList.size() - index2);
        int size = Math.min(index2, this.baseProtocols);
        for (i = 0; i < size; ++i) {
            pipes.add(protocolList.get(i));
        }
        size = protocolList.size();
        for (i = index2; i < size; ++i) {
            pipes.add(protocolList.get(i));
        }
        return pipes;
    }

    private int indexOf(@Nullable Class<? extends Protocol> protocolClass, boolean skipCurrentPipeline, List<Protocol> protocolList) {
        if (protocolClass == null) {
            return 0;
        }
        int index2 = -1;
        for (int i = 0; i < protocolList.size(); ++i) {
            if (protocolList.get(i).getClass() != protocolClass) continue;
            index2 = i;
            break;
        }
        if (index2 == -1) {
            throw new NoSuchElementException(protocolClass.getCanonicalName());
        }
        if (skipCurrentPipeline) {
            index2 = Math.min(index2 + 1, protocolList.size());
        }
        return index2;
    }

    @Override
    public List<Protocol> pipes() {
        return Collections.unmodifiableList(this.protocolList);
    }

    @Override
    public List<Protocol> reversedPipes() {
        return Collections.unmodifiableList(this.reversedProtocolList);
    }

    @Override
    public int baseProtocolCount() {
        return this.baseProtocols;
    }

    @Override
    public boolean hasNonBaseProtocols() {
        return this.protocolList.size() > this.baseProtocols;
    }

    @Override
    public void cleanPipes() {
        this.protocolList.clear();
        this.reversedProtocolList.clear();
        this.protocolSet.clear();
        this.baseProtocols = 0;
        this.registerPackets();
    }

    @Override
    public String toString() {
        List<Protocol> list = this.protocolList;
        return "ProtocolPipelineImpl{protocolList=" + list + "}";
    }
}

