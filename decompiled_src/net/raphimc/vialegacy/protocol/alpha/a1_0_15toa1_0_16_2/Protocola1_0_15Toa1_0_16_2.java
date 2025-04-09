/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ClientboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ServerboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.alpha.a1_0_16_2toa1_0_17_1_0_17_4.packet.ClientboundPacketsa1_0_16;
import net.raphimc.vialegacy.protocol.alpha.a1_0_17_1_0_17_4toa1_1_0_1_1_2_1.packet.ServerboundPacketsa1_0_17;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;

public class Protocola1_0_15Toa1_0_16_2
extends StatelessProtocol<ClientboundPacketsa1_0_15, ClientboundPacketsa1_0_16, ServerboundPacketsa1_0_15, ServerboundPacketsa1_0_17> {
    public Protocola1_0_15Toa1_0_16_2() {
        super(ClientboundPacketsa1_0_15.class, ClientboundPacketsa1_0_16.class, ServerboundPacketsa1_0_15.class, ServerboundPacketsa1_0_17.class);
    }

    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundPacketsa1_0_17.HANDSHAKE, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            PacketWrapper handshake = PacketWrapper.create(ClientboundPacketsa1_0_16.HANDSHAKE, wrapper.user());
            handshake.write(Typesb1_7_0_3.STRING, "-");
            handshake.send(Protocola1_0_15Toa1_0_16_2.class);
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocola1_0_15Toa1_0_16_2.class, ClientboundPacketsa1_0_15::getPacket));
    }
}

