/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7.rewriter;

import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7.Protocolr1_4_4_5Tor1_4_6_7;
import net.raphimc.vialegacy.protocol.release.r1_4_4_5tor1_4_6_7.packet.ClientboundPackets1_4_4;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPackets1_4_4, ServerboundPackets1_5_2, Protocolr1_4_4_5Tor1_4_6_7> {
    public ItemRewriter(Protocolr1_4_4_5Tor1_4_6_7 protocol) {
        super(protocol, "1.4.5", Types1_7_6.ITEM, Types1_7_6.ITEM_ARRAY);
        this.addNonExistentItem(43, 6);
        this.addNonExistentItem(44, 6);
        this.addNonExistentItemRange(401, 403);
    }

    @Override
    protected void registerPackets() {
        this.registerCreativeInventoryAction(ServerboundPackets1_5_2.SET_CREATIVE_MODE_SLOT);
    }
}

