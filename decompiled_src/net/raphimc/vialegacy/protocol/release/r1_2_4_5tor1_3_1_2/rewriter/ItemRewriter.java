/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.rewriter;

import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.Protocolr1_2_4_5Tor1_3_1_2;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.packet.ClientboundPackets1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.Types1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.packet.ServerboundPackets1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPackets1_2_4, ServerboundPackets1_3_1, Protocolr1_2_4_5Tor1_3_1_2> {
    public ItemRewriter(Protocolr1_2_4_5Tor1_3_1_2 protocol) {
        super(protocol, "1.2.5", Types1_2_4.NBT_ITEM, Types1_2_4.NBT_ITEM_ARRAY, Types1_7_6.ITEM, Types1_7_6.ITEM_ARRAY);
        this.addNonExistentItemRange(126, 136);
        this.addNonExistentItem(322, 1);
        this.addNonExistentItemRange(386, 388);
    }

    @Override
    protected void registerPackets() {
        this.registerCreativeInventoryAction(ServerboundPackets1_3_1.SET_CREATIVE_MODE_SLOT);
    }
}

