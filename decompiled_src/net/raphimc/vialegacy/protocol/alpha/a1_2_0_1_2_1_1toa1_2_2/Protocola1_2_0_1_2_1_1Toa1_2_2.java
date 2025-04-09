/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_0_1_2_1_1toa1_2_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_2_0_1_2_1_1toa1_2_2.packet.ClientboundPacketsa1_2_0;
import net.raphimc.vialegacy.protocol.alpha.a1_2_0_1_2_1_1toa1_2_2.packet.ServerboundPacketsa1_2_0;
import net.raphimc.vialegacy.protocol.alpha.a1_2_2toa1_2_3_1_2_3_4.packet.ClientboundPacketsa1_2_2;
import net.raphimc.vialegacy.protocol.alpha.a1_2_2toa1_2_3_1_2_3_4.packet.ServerboundPacketsa1_2_2;

public class Protocola1_2_0_1_2_1_1Toa1_2_2
extends StatelessProtocol<ClientboundPacketsa1_2_0, ClientboundPacketsa1_2_2, ServerboundPacketsa1_2_0, ServerboundPacketsa1_2_2> {
    public Protocola1_2_0_1_2_1_1Toa1_2_2() {
        super(ClientboundPacketsa1_2_0.class, ClientboundPacketsa1_2_2.class, ServerboundPacketsa1_2_0.class, ServerboundPacketsa1_2_2.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPacketsa1_2_0.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.UNSIGNED_BYTE, 0) == 91) {
                        wrapper.set(Types.UNSIGNED_BYTE, 0, (short)93);
                    }
                });
            }
        });
        this.cancelServerbound(ServerboundPacketsa1_2_2.INTERACT);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocola1_2_0_1_2_1_1Toa1_2_2.class, ClientboundPacketsa1_2_0::getPacket));
    }
}

