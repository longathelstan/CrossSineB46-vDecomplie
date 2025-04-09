/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5;

import com.viaversion.viaversion.api.connection.UserConnection;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.packet.ClientboundPackets1_2_1;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.packet.ServerboundPackets1_2_1;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ClientboundPackets1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ServerboundPackets1_2_4;

public class Protocolr1_2_1_3Tor1_2_4_5
extends StatelessProtocol<ClientboundPackets1_2_1, ClientboundPackets1_2_4, ServerboundPackets1_2_1, ServerboundPackets1_2_4> {
    private final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_2_1_3Tor1_2_4_5() {
        super(ClientboundPackets1_2_1.class, ClientboundPackets1_2_4.class, ServerboundPackets1_2_1.class, ServerboundPackets1_2_4.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.cancelServerbound(ServerboundPackets1_2_4.PLAYER_ABILITIES);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_2_1_3Tor1_2_4_5.class, ClientboundPackets1_2_1::getPacket));
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

