/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19_4to1_20.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.RecipeRewriter1_19_4;
import com.viaversion.viaversion.protocols.v1_19_4to1_20.Protocol1_19_4To1_20;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;

public final class ItemPacketRewriter1_20
extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_4, Protocol1_19_4To1_20> {
    public ItemPacketRewriter1_20(Protocol1_19_4To1_20 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        final BlockRewriter<ClientboundPackets1_19_4> blockRewriter = BlockRewriter.for1_14(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_19_4.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_19_4.BLOCK_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_19_4.LEVEL_EVENT, 1010, 2001);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, this::handleBlockEntity);
        this.registerOpenScreen(ClientboundPackets1_19_4.OPEN_SCREEN);
        this.registerCooldown(ClientboundPackets1_19_4.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_SLOT);
        this.registerSetEquipment(ClientboundPackets1_19_4.SET_EQUIPMENT);
        this.registerContainerClick1_17_1(ServerboundPackets1_19_4.CONTAINER_CLICK);
        this.registerMerchantOffers1_19(ClientboundPackets1_19_4.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_19_4.SET_CREATIVE_MODE_SLOT);
        this.registerContainerSetData(ClientboundPackets1_19_4.CONTAINER_SET_DATA);
        this.registerLevelParticles1_19(ClientboundPackets1_19_4.LEVEL_PARTICLES);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Types.COMPONENT);
                    wrapper.passthrough(Types.COMPONENT);
                    this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                    wrapper.passthrough(Types.VAR_INT);
                    int flags = wrapper.passthrough(Types.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                wrapper.passthrough(Types.STRING_ARRAY);
                int requirements = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }
                wrapper.write(Types.BOOLEAN, false);
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            wrapper.write(Types.BOOLEAN, true);
        });
        ((Protocol1_19_4To1_20)this.protocol).registerServerbound(ServerboundPackets1_19_4.SIGN_UPDATE, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            boolean frontText = wrapper.read(Types.BOOLEAN);
            if (!frontText) {
                wrapper.cancel();
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.LEVEL_CHUNK_WITH_LIGHT, new PacketHandlers(){

            @Override
            protected void register() {
                this.handler(blockRewriter.chunkHandler1_19(ChunkType1_18::new, (user, blockEntity) -> ItemPacketRewriter1_20.this.handleBlockEntity((BlockEntity)blockEntity)));
                this.read(Types.BOOLEAN);
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.LIGHT_UPDATE, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.read(Types.BOOLEAN);
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.SECTION_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.LONG);
                this.read(Types.BOOLEAN);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Types.VAR_LONG_BLOCK_CHANGE_ARRAY)) {
                        record.setBlockId(((Protocol1_19_4To1_20)ItemPacketRewriter1_20.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
        RecipeRewriter1_19_4 recipeRewriter = new RecipeRewriter1_19_4(this.protocol);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_RECIPES, wrapper -> {
            int size;
            int newSize = size = wrapper.passthrough(Types.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                String type = wrapper.read(Types.STRING);
                String cutType = Key.stripMinecraftNamespace(type);
                if (cutType.equals("smithing")) {
                    --newSize;
                    wrapper.read(Types.STRING);
                    wrapper.read(Types.ITEM1_13_2_ARRAY);
                    wrapper.read(Types.ITEM1_13_2_ARRAY);
                    wrapper.read(Types.ITEM1_13_2);
                    continue;
                }
                wrapper.write(Types.STRING, type);
                wrapper.passthrough(Types.STRING);
                recipeRewriter.handleRecipeType(wrapper, cutType);
            }
            wrapper.set(Types.VAR_INT, 0, newSize);
        });
    }

    void handleBlockEntity(BlockEntity blockEntity) {
        Tag glowing;
        Tag color;
        if (blockEntity.typeId() != 7 && blockEntity.typeId() != 8) {
            return;
        }
        CompoundTag tag = blockEntity.tag();
        CompoundTag frontText = new CompoundTag();
        tag.put("front_text", frontText);
        ListTag<StringTag> messages = new ListTag<StringTag>(StringTag.class);
        for (int i = 1; i < 5; ++i) {
            int n = i;
            Tag text = tag.remove("Text" + n);
            messages.add(text instanceof StringTag ? (StringTag)text : new StringTag(ComponentUtil.emptyJsonComponentString()));
        }
        frontText.put("messages", messages);
        ListTag<StringTag> filteredMessages = new ListTag<StringTag>(StringTag.class);
        for (int i = 1; i < 5; ++i) {
            int n = i;
            Tag text = tag.remove("FilteredText" + n);
            filteredMessages.add(text instanceof StringTag ? (StringTag)text : messages.get(i - 1));
        }
        if (!filteredMessages.equals(messages)) {
            frontText.put("filtered_messages", filteredMessages);
        }
        if ((color = tag.remove("Color")) != null) {
            frontText.put("color", color);
        }
        if ((glowing = tag.remove("GlowingText")) != null) {
            frontText.put("has_glowing_text", glowing);
        }
    }
}

