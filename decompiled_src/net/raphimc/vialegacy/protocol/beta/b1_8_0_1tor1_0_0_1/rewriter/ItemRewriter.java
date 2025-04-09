/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.rewriter;

import net.raphimc.vialegacy.api.remapper.LegacyItemRewriter;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.Protocolb1_8_0_1tor1_0_0_1;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.packet.ClientboundPacketsb1_8;
import net.raphimc.vialegacy.protocol.beta.b1_8_0_1tor1_0_0_1.types.Typesb1_8_0_1;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.packet.ServerboundPackets1_0_0;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.Types1_2_4;

public class ItemRewriter
extends LegacyItemRewriter<ClientboundPacketsb1_8, ServerboundPackets1_0_0, Protocolb1_8_0_1tor1_0_0_1> {
    public ItemRewriter(Protocolb1_8_0_1tor1_0_0_1 protocol) {
        super(protocol, "b1.8.1", Typesb1_8_0_1.CREATIVE_ITEM, null, Types1_2_4.NBT_ITEM, Types1_2_4.NBT_ITEM_ARRAY);
        this.addNonExistentItemRange(110, 122);
        this.addNonExistentItemRange(369, 382);
        this.addNonExistentItems(438);
        this.addNonExistentItemRange(2256, 2266);
    }

    @Override
    protected void registerPackets() {
        this.registerCreativeInventoryAction(ServerboundPackets1_0_0.SET_CREATIVE_MODE_SLOT);
    }
}

