/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.rewriter;

import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.Protocolr1_1Tor1_2_1_3;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.packet.ClientboundPackets1_1;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.packet.ServerboundPackets1_2_1;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.Types1_2_4;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPackets1_1, ServerboundPackets1_2_1, Protocolr1_1Tor1_2_1_3> {
    public ItemRewriter(Protocolr1_1Tor1_2_1_3 protocol) {
        super(protocol, "1.1", Types1_2_4.NBT_ITEM, Types1_2_4.NBT_ITEM_ARRAY);
        this.addNonExistentItem(6, 3);
        this.addNonExistentItem(17, 3);
        this.addNonExistentItem(18, 3);
        this.addNonExistentItem(98, 3);
        this.addNonExistentItemRange(123, 124);
        this.addNonExistentItemRange(384, 385);
        this.addNonExistentItem(383, 98);
    }

    @Override
    protected void registerPackets() {
        this.registerCreativeInventoryAction(ServerboundPackets1_2_1.SET_CREATIVE_MODE_SLOT);
    }
}

