/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.rewriter;

import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.Protocolr1_5_2Tor1_6_1;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ClientboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.packet.ServerboundPackets1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPackets1_5_2, ServerboundPackets1_6_4, Protocolr1_5_2Tor1_6_1> {
    public ItemRewriter(Protocolr1_5_2Tor1_6_1 protocol) {
        super(protocol, "1.5.2", Types1_7_6.ITEM, Types1_7_6.ITEM_ARRAY);
        this.addNonExistentItems(159);
        this.addNonExistentItemRange(170, 173);
        this.addNonExistentItem(383, 100);
        this.addNonExistentItemRange(417, 421);
    }

    @Override
    protected void registerPackets() {
        this.registerCreativeInventoryAction(ServerboundPackets1_6_4.SET_CREATIVE_MODE_SLOT);
    }
}

