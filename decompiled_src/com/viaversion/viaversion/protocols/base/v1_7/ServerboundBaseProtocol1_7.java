/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.base.v1_7;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.base.packet.BaseClientboundPacket;
import com.viaversion.viaversion.protocols.base.packet.BasePacketTypesProvider;
import com.viaversion.viaversion.protocols.base.packet.BaseServerboundPacket;
import com.viaversion.viaversion.util.ChatColorUtil;
import com.viaversion.viaversion.util.ComponentUtil;
import io.netty.channel.ChannelFuture;

public class ServerboundBaseProtocol1_7
extends AbstractProtocol<BaseClientboundPacket, BaseClientboundPacket, BaseServerboundPacket, BaseServerboundPacket> {
    public ServerboundBaseProtocol1_7() {
        super(BaseClientboundPacket.class, BaseClientboundPacket.class, BaseServerboundPacket.class, BaseServerboundPacket.class);
    }

    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundLoginPackets.LOGIN_ACKNOWLEDGED, wrapper -> {
            ProtocolInfo info = wrapper.user().getProtocolInfo();
            info.setState(State.CONFIGURATION);
        });
        this.registerServerbound(ServerboundLoginPackets.HELLO, wrapper -> {
            UserConnection user = wrapper.user();
            ProtocolVersion protocol = user.getProtocolInfo().protocolVersion();
            if (Via.getConfig().blockedProtocolVersions().contains(protocol)) {
                if (!user.getChannel().isOpen() || !user.shouldApplyBlockProtocol()) {
                    return;
                }
                wrapper.cancel();
                String disconnectMessage = ChatColorUtil.translateAlternateColorCodes(Via.getConfig().getBlockedDisconnectMsg());
                PacketWrapper disconnectPacket = PacketWrapper.create(ClientboundLoginPackets.LOGIN_DISCONNECT, user);
                JsonObject object = ComponentUtil.plainToJson(disconnectMessage);
                if (protocol.olderThanOrEqualTo(ProtocolVersion.v1_8)) {
                    disconnectPacket.write(Types.STRING, object.toString());
                } else {
                    disconnectPacket.write(Types.COMPONENT, object);
                }
                ChannelFuture future = disconnectPacket.sendFutureRaw();
                future.addListener(f -> user.getChannel().close());
            }
        });
    }

    @Override
    public boolean isBaseProtocol() {
        return true;
    }

    @Override
    protected PacketTypesProvider<BaseClientboundPacket, BaseClientboundPacket, BaseServerboundPacket, BaseServerboundPacket> createPacketTypesProvider() {
        return BasePacketTypesProvider.INSTANCE;
    }
}

