/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.protocol.version.VersionType;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.protocol.RedirectProtocolVersion;
import com.viaversion.viaversion.protocol.version.BaseVersionProvider;
import com.viaversion.viaversion.protocols.base.ServerboundHandshakePackets;
import com.viaversion.viaversion.protocols.base.packet.BaseClientboundPacket;
import com.viaversion.viaversion.protocols.base.packet.BasePacketTypesProvider;
import com.viaversion.viaversion.protocols.base.packet.BaseServerboundPacket;
import java.util.ArrayList;
import java.util.List;

public class InitialBaseProtocol
extends AbstractProtocol<BaseClientboundPacket, BaseClientboundPacket, BaseServerboundPacket, BaseServerboundPacket> {
    private static final int STATUS_INTENT = 1;
    private static final int LOGIN_INTENT = 2;
    private static final int TRANSFER_INTENT = 3;

    public InitialBaseProtocol() {
        super(BaseClientboundPacket.class, BaseClientboundPacket.class, BaseServerboundPacket.class, BaseServerboundPacket.class);
    }

    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundHandshakePackets.CLIENT_INTENTION, wrapper -> {
            ArrayList<Object> protocols;
            ProtocolVersion serverProtocol;
            int protocolVersion = wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.STRING);
            wrapper.passthrough(Types.UNSIGNED_SHORT);
            int state = wrapper.passthrough(Types.VAR_INT);
            VersionProvider versionProvider = Via.getManager().getProviders().get(VersionProvider.class);
            if (versionProvider == null) {
                wrapper.user().setActive(false);
                return;
            }
            ProtocolInfo info = wrapper.user().getProtocolInfo();
            info.setProtocolVersion(ProtocolVersion.getProtocol(protocolVersion));
            ProtocolVersion clientVersion = versionProvider.getClientProtocol(wrapper.user());
            if (clientVersion != null) {
                info.setProtocolVersion(clientVersion);
            }
            try {
                serverProtocol = versionProvider.getClosestServerProtocol(wrapper.user());
            }
            catch (Exception e) {
                throw new RuntimeException("Error getting server protocol", e);
            }
            info.setServerProtocolVersion(serverProtocol);
            ProtocolManager protocolManager = Via.getManager().getProtocolManager();
            List<ProtocolPathEntry> protocolPath = protocolManager.getProtocolPath(info.protocolVersion(), serverProtocol);
            ProtocolPipeline pipeline = info.getPipeline();
            ArrayList<Protocol> alreadyAdded = new ArrayList<Protocol>(pipeline.pipes());
            ProtocolVersion clientboundBaseProtocolVersion = null;
            if (serverProtocol.getVersionType() != VersionType.SPECIAL) {
                clientboundBaseProtocolVersion = serverProtocol;
            } else if (serverProtocol instanceof RedirectProtocolVersion) {
                RedirectProtocolVersion version = (RedirectProtocolVersion)serverProtocol;
                clientboundBaseProtocolVersion = version.getBaseProtocolVersion();
            }
            for (Protocol protocol : protocolManager.getBaseProtocols(info.protocolVersion(), clientboundBaseProtocolVersion)) {
                pipeline.add(protocol);
            }
            if (protocolPath != null) {
                protocols = new ArrayList(protocolPath.size());
                for (ProtocolPathEntry entry : protocolPath) {
                    protocols.add(entry.protocol());
                    protocolManager.completeMappingDataLoading(entry.protocol().getClass());
                }
                pipeline.add(protocols);
                wrapper.set(Types.VAR_INT, 0, serverProtocol.getOriginalVersion());
            }
            try {
                protocols = new ArrayList<Protocol>(pipeline.pipes());
                protocols.removeAll(alreadyAdded);
                wrapper.resetReader();
                wrapper.apply(Direction.SERVERBOUND, State.HANDSHAKE, protocols);
            }
            catch (CancelException e) {
                wrapper.cancel();
            }
            if (Via.getManager().isDebug()) {
                ProtocolVersion protocolVersion2 = info.serverProtocolVersion();
                ProtocolVersion protocolVersion3 = info.protocolVersion();
                Via.getPlatform().getLogger().info("User connected with protocol: " + protocolVersion3 + " and serverProtocol: " + protocolVersion2);
                List<Protocol> list = pipeline.pipes();
                Via.getPlatform().getLogger().info("Protocol pipeline: " + list);
            }
            if (state == 1) {
                info.setState(State.STATUS);
            } else if (state == 2) {
                info.setState(State.LOGIN);
            } else if (state == 3) {
                info.setState(State.LOGIN);
                if (serverProtocol.olderThan(ProtocolVersion.v1_20_5)) {
                    wrapper.set(Types.VAR_INT, 1, 2);
                }
            }
        });
    }

    @Override
    public boolean isBaseProtocol() {
        return true;
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(VersionProvider.class, new BaseVersionProvider());
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws InformativeException, CancelException {
        super.transform(direction, state, packetWrapper);
        if (direction == Direction.SERVERBOUND && state == State.HANDSHAKE && packetWrapper.getId() != 0) {
            packetWrapper.user().setActive(false);
        }
    }

    @Override
    protected PacketTypesProvider<BaseClientboundPacket, BaseClientboundPacket, BaseServerboundPacket, BaseServerboundPacket> createPacketTypesProvider() {
        return BasePacketTypesProvider.INSTANCE;
    }
}

