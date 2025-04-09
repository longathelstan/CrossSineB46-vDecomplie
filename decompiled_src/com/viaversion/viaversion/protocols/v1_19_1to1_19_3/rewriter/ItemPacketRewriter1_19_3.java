/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19_1to1_19_3.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.Protocol1_19_1To1_19_3;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19to1_19_1.packet.ClientboundPackets1_19_1;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class ItemPacketRewriter1_19_3
extends ItemRewriter<ClientboundPackets1_19_1, ServerboundPackets1_19_3, Protocol1_19_1To1_19_3> {
    static final int MISC_CRAFTING_BOOK_CATEGORY = 0;

    public ItemPacketRewriter1_19_3(Protocol1_19_1To1_19_3 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_1> blockRewriter = BlockRewriter.for1_14(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_19_1.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_19_1.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate(ClientboundPackets1_19_1.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_19_1.LEVEL_EVENT, 1010, 2001);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_19_1.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_18::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_1.BLOCK_ENTITY_DATA);
        this.registerCooldown(ClientboundPackets1_19_1.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_19_1.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_1.CONTAINER_SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_19_1.UPDATE_ADVANCEMENTS);
        this.registerSetEquipment(ClientboundPackets1_19_1.SET_EQUIPMENT);
        this.registerContainerClick1_17_1(ServerboundPackets1_19_3.CONTAINER_CLICK);
        this.registerMerchantOffers1_19(ClientboundPackets1_19_1.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_19_3.SET_CREATIVE_MODE_SLOT);
        this.registerContainerSetData(ClientboundPackets1_19_1.CONTAINER_SET_DATA);
        this.registerLevelParticles1_19(ClientboundPackets1_19_1.LEVEL_PARTICLES);
        RecipeRewriter recipeRewriter = new RecipeRewriter(this.protocol);
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_1.UPDATE_RECIPES, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            block27: for (int i = 0; i < size; ++i) {
                String type = Key.stripMinecraftNamespace(wrapper.passthrough(Types.STRING));
                wrapper.passthrough(Types.STRING);
                switch (type) {
                    case "crafting_shapeless": {
                        wrapper.passthrough(Types.STRING);
                        wrapper.write(Types.VAR_INT, 0);
                        int ingredients = wrapper.passthrough(Types.VAR_INT);
                        for (int j = 0; j < ingredients; ++j) {
                            Item[] items;
                            for (Item item : items = wrapper.passthrough(Types.ITEM1_13_2_ARRAY)) {
                                this.handleItemToClient(wrapper.user(), item);
                            }
                        }
                        this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        continue block27;
                    }
                    case "crafting_shaped": {
                        int ingredients = wrapper.passthrough(Types.VAR_INT) * wrapper.passthrough(Types.VAR_INT);
                        wrapper.passthrough(Types.STRING);
                        wrapper.write(Types.VAR_INT, 0);
                        for (int j = 0; j < ingredients; ++j) {
                            Item[] items;
                            for (Item item : items = wrapper.passthrough(Types.ITEM1_13_2_ARRAY)) {
                                this.handleItemToClient(wrapper.user(), item);
                            }
                        }
                        this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        continue block27;
                    }
                    case "smelting": 
                    case "campfire_cooking": 
                    case "blasting": 
                    case "smoking": {
                        Item[] items;
                        wrapper.passthrough(Types.STRING);
                        wrapper.write(Types.VAR_INT, 0);
                        for (Item item : items = wrapper.passthrough(Types.ITEM1_13_2_ARRAY)) {
                            this.handleItemToClient(wrapper.user(), item);
                        }
                        this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        wrapper.passthrough(Types.FLOAT);
                        wrapper.passthrough(Types.VAR_INT);
                        continue block27;
                    }
                    case "crafting_special_armordye": 
                    case "crafting_special_bookcloning": 
                    case "crafting_special_mapcloning": 
                    case "crafting_special_mapextending": 
                    case "crafting_special_firework_rocket": 
                    case "crafting_special_firework_star": 
                    case "crafting_special_firework_star_fade": 
                    case "crafting_special_tippedarrow": 
                    case "crafting_special_bannerduplicate": 
                    case "crafting_special_shielddecoration": 
                    case "crafting_special_shulkerboxcoloring": 
                    case "crafting_special_suspiciousstew": 
                    case "crafting_special_repairitem": {
                        wrapper.write(Types.VAR_INT, 0);
                        continue block27;
                    }
                    default: {
                        recipeRewriter.handleRecipeType(wrapper, type);
                    }
                }
            }
        });
        ((Protocol1_19_1To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_1.EXPLODE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.FLOAT, Types.DOUBLE);
                this.map(Types.FLOAT, Types.DOUBLE);
                this.map(Types.FLOAT, Types.DOUBLE);
            }
        });
    }
}

