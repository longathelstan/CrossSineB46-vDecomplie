/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.rewriter;

import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.Protocolr1_4_2Tor1_4_4_5;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.packet.ClientboundPackets1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPackets1_4_2, ServerboundPackets1_5_2, Protocolr1_4_2Tor1_4_4_5> {
    public ItemRewriter(Protocolr1_4_2Tor1_4_4_5 protocol) {
        super(protocol, "1.4.2", Types1_7_6.ITEM, Types1_7_6.ITEM_ARRAY);
        this.addNonExistentItems(2267);
    }

    @Override
    protected void registerPackets() {
        this.registerCreativeInventoryAction(ServerboundPackets1_5_2.SET_CREATIVE_MODE_SLOT);
    }
}

