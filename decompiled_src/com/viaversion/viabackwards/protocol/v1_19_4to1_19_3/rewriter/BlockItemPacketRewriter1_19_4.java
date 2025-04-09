/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19_4to1_19_3.rewriter;

import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.protocol.v1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.rewriter.RecipeRewriter1_19_3;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.Key;

public final class BlockItemPacketRewriter1_19_4
extends BackwardsItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_3, Protocol1_19_4To1_19_3> {
    public BlockItemPacketRewriter1_19_4(Protocol1_19_4To1_19_3 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_4> blockRewriter = BlockRewriter.for1_14(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_19_4.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_19_4.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate(ClientboundPackets1_19_4.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_19_4.LEVEL_EVENT, 1010, 2001);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_19_4.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_18::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA);
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SCREEN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.COMPONENT);
                this.handler(wrapper -> {
                    int windowType = wrapper.get(Types.VAR_INT, 1);
                    if (windowType == 21) {
                        wrapper.cancel();
                    } else if (windowType > 21) {
                        wrapper.set(Types.VAR_INT, 1, windowType - 1);
                    }
                    ((ComponentRewriter)((Protocol1_19_4To1_19_3)BlockItemPacketRewriter1_19_4.this.protocol).getComponentRewriter()).processText(wrapper.user(), wrapper.get(Types.COMPONENT, 0));
                });
            }
        });
        this.registerCooldown(ClientboundPackets1_19_4.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_19_4.UPDATE_ADVANCEMENTS);
        this.registerSetEquipment(ClientboundPackets1_19_4.SET_EQUIPMENT);
        this.registerContainerClick1_17_1(ServerboundPackets1_19_3.CONTAINER_CLICK);
        this.registerMerchantOffers1_19(ClientboundPackets1_19_4.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_19_3.SET_CREATIVE_MODE_SLOT);
        this.registerContainerSetData(ClientboundPackets1_19_4.CONTAINER_SET_DATA);
        this.registerLevelParticles1_19(ClientboundPackets1_19_4.LEVEL_PARTICLES);
        RecipeRewriter1_19_3<ClientboundPackets1_19_4> recipeRewriter = new RecipeRewriter1_19_3<ClientboundPackets1_19_4>(this.protocol){

            @Override
            public void handleCraftingShaped(PacketWrapper wrapper) {
                int ingredients = wrapper.passthrough(Types.VAR_INT) * wrapper.passthrough(Types.VAR_INT);
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.VAR_INT);
                for (int i = 0; i < ingredients; ++i) {
                    this.handleIngredient(wrapper);
                }
                this.rewrite(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                wrapper.read(Types.BOOLEAN);
            }
        };
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_RECIPES, wrapper -> {
            int size;
            int newSize = size = wrapper.passthrough(Types.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                String type = wrapper.read(Types.STRING);
                String cutType = Key.stripMinecraftNamespace(type);
                if (cutType.equals("smithing_transform") || cutType.equals("smithing_trim")) {
                    --newSize;
                    wrapper.read(Types.STRING);
                    wrapper.read(Types.ITEM1_13_2_ARRAY);
                    wrapper.read(Types.ITEM1_13_2_ARRAY);
                    wrapper.read(Types.ITEM1_13_2_ARRAY);
                    if (!cutType.equals("smithing_transform")) continue;
                    wrapper.read(Types.ITEM1_13_2);
                    continue;
                }
                if (cutType.equals("crafting_decorated_pot")) {
                    --newSize;
                    wrapper.read(Types.STRING);
                    wrapper.read(Types.VAR_INT);
                    continue;
                }
                wrapper.write(Types.STRING, type);
                wrapper.passthrough(Types.STRING);
                recipeRewriter.handleRecipeType(wrapper, cutType);
            }
            wrapper.set(Types.VAR_INT, 0, newSize);
        });
    }
}

