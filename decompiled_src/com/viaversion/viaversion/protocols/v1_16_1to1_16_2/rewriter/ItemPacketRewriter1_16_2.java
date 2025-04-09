/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_16_1to1_16_2.rewriter;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class ItemPacketRewriter1_16_2
extends ItemRewriter<ClientboundPackets1_16, ServerboundPackets1_16_2, Protocol1_16_1To1_16_2> {
    public ItemPacketRewriter1_16_2(Protocol1_16_1To1_16_2 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    public void registerPackets() {
        this.registerCooldown(ClientboundPackets1_16.COOLDOWN);
        this.registerSetContent(ClientboundPackets1_16.CONTAINER_SET_CONTENT);
        this.registerMerchantOffers(ClientboundPackets1_16.MERCHANT_OFFERS);
        this.registerSetSlot(ClientboundPackets1_16.CONTAINER_SET_SLOT);
        this.registerSetEquipment(ClientboundPackets1_16.SET_EQUIPMENT);
        this.registerAdvancements(ClientboundPackets1_16.UPDATE_ADVANCEMENTS);
        ((Protocol1_16_1To1_16_2)this.protocol).registerClientbound(ClientboundPackets1_16.RECIPE, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.write(Types.BOOLEAN, false);
            wrapper.write(Types.BOOLEAN, false);
            wrapper.write(Types.BOOLEAN, false);
            wrapper.write(Types.BOOLEAN, false);
        });
        new RecipeRewriter<ClientboundPackets1_16>(this.protocol).register(ClientboundPackets1_16.UPDATE_RECIPES);
        this.registerContainerClick(ServerboundPackets1_16_2.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_16_2.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_16_1To1_16_2)this.protocol).registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
        this.registerLevelParticles(ClientboundPackets1_16.LEVEL_PARTICLES, Types.DOUBLE);
    }
}

