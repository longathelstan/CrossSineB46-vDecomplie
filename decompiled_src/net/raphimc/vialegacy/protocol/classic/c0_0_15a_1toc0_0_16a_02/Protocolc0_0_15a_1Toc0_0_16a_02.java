/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_0_15a_1toc0_0_16a_02;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.classic.c0_0_15a_1toc0_0_16a_02.packet.ClientboundPacketsc0_15a;
import net.raphimc.vialegacy.protocol.classic.c0_0_15a_1toc0_0_16a_02.packet.ServerboundPacketsc0_15a;
import net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet.ClientboundPacketsc0_19a;
import net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet.ServerboundPacketsc0_19a;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types.Typesc0_30;

public class Protocolc0_0_15a_1Toc0_0_16a_02
extends StatelessProtocol<ClientboundPacketsc0_15a, ClientboundPacketsc0_19a, ServerboundPacketsc0_15a, ServerboundPacketsc0_19a> {
    public Protocolc0_0_15a_1Toc0_0_16a_02() {
        super(ClientboundPacketsc0_15a.class, ClientboundPacketsc0_19a.class, ServerboundPacketsc0_15a.class, ServerboundPacketsc0_19a.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPacketsc0_15a.LOGIN, wrapper -> {
            String username = wrapper.read(Typesc0_30.STRING);
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Typesc0_30.STRING, "c0.0.15a Server");
            String string = username;
            wrapper.write(Typesc0_30.STRING, "Logged in as: " + string);
        });
        this.registerClientbound(ClientboundPacketsc0_15a.TELEPORT_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    byte entityId = wrapper.get(Types.BYTE, 0);
                    byte yaw = wrapper.get(Types.BYTE, 1);
                    byte pitch = wrapper.get(Types.BYTE, 2);
                    PacketWrapper entityRotation = PacketWrapper.create(ClientboundPacketsc0_19a.MOVE_ENTITY_ROT, wrapper.user());
                    entityRotation.write(Types.BYTE, entityId);
                    entityRotation.write(Types.BYTE, yaw);
                    entityRotation.write(Types.BYTE, pitch);
                    wrapper.send(Protocolc0_0_15a_1Toc0_0_16a_02.class);
                    entityRotation.send(Protocolc0_0_15a_1Toc0_0_16a_02.class);
                    wrapper.cancel();
                });
            }
        });
        this.registerServerbound(ServerboundPacketsc0_19a.LOGIN, wrapper -> {
            wrapper.clearPacket();
            wrapper.write(Typesc0_30.STRING, wrapper.user().getProtocolInfo().getUsername());
        });
        this.cancelServerbound(ServerboundPacketsc0_19a.CHAT);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolc0_0_15a_1Toc0_0_16a_02.class, ClientboundPacketsc0_15a::getPacket));
    }
}

