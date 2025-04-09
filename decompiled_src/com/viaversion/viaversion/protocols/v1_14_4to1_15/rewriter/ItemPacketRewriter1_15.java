/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_14_4to1_15.rewriter;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_3to1_14_4.packet.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class ItemPacketRewriter1_15
extends ItemRewriter<ClientboundPackets1_14_4, ServerboundPackets1_14, Protocol1_14_4To1_15> {
    public ItemPacketRewriter1_15(Protocol1_14_4To1_15 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    public void registerPackets() {
        this.registerCooldown(ClientboundPackets1_14_4.COOLDOWN);
        this.registerSetContent(ClientboundPackets1_14_4.CONTAINER_SET_CONTENT);
        this.registerMerchantOffers(ClientboundPackets1_14_4.MERCHANT_OFFERS);
        this.registerSetSlot(ClientboundPackets1_14_4.CONTAINER_SET_SLOT);
        this.registerSetEquippedItem(ClientboundPackets1_14_4.SET_EQUIPPED_ITEM);
        this.registerAdvancements(ClientboundPackets1_14_4.UPDATE_ADVANCEMENTS);
        new RecipeRewriter<ClientboundPackets1_14_4>(this.protocol).register(ClientboundPackets1_14_4.UPDATE_RECIPES);
        this.registerContainerClick(ServerboundPackets1_14.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_14.SET_CREATIVE_MODE_SLOT);
    }
}

