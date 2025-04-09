/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_6_7tor1_5_0_1.rewriter;

import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_4_6_7tor1_5_0_1.Protocolr1_4_6_7Tor1_5_0_1;
import net.raphimc.vialegacy.protocol.release.r1_4_6_7tor1_5_0_1.packet.ClientboundPackets1_4_6;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPackets1_4_6, ServerboundPackets1_5_2, Protocolr1_4_6_7Tor1_5_0_1> {
    public ItemRewriter(Protocolr1_4_6_7Tor1_5_0_1 protocol) {
        super(protocol, "1.4.7", Types1_7_6.ITEM, Types1_7_6.ITEM_ARRAY);
        this.addNonExistentItem(43, 7);
        this.addNonExistentItem(44, 7);
        this.addNonExistentItemRange(146, 158);
        this.addNonExistentItems(178);
        this.addNonExistentItemRange(404, 408);
    }

    @Override
    protected void registerPackets() {
        this.registerCreativeInventoryAction(ServerboundPackets1_5_2.SET_CREATIVE_MODE_SLOT);
    }
}

