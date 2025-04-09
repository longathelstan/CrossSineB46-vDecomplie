/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.rewriter;

import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.Protocolr1_0_0_1Tor1_1;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.packet.ClientboundPackets1_0_0;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.packet.ServerboundPackets1_1;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.Types1_2_4;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPackets1_0_0, ServerboundPackets1_1, Protocolr1_0_0_1Tor1_1> {
    public ItemRewriter(Protocolr1_0_0_1Tor1_1 protocol) {
        super(protocol, "1.0", Types1_2_4.NBT_ITEM, Types1_2_4.NBT_ITEM_ARRAY);
        this.addNonExistentItems(383);
    }

    @Override
    protected void registerPackets() {
        this.registerCreativeInventoryAction(ServerboundPackets1_1.SET_CREATIVE_MODE_SLOT);
    }
}

