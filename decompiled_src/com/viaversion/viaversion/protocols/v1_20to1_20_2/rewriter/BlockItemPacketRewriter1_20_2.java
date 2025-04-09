/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ChunkPosition;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.RecipeRewriter1_19_4;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.data.PotionEffects1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.MathUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPacketRewriter1_20_2
extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_20_2, Protocol1_20To1_20_2> {
    public BlockItemPacketRewriter1_20_2(Protocol1_20To1_20_2 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY, Types.ITEM1_20_2, Types.ITEM1_20_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_4> blockRewriter = BlockRewriter.for1_14(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_19_4.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_19_4.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_19_4.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_19_4.LEVEL_EVENT, 1010, 2001);
        this.registerSetContent1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_SLOT);
        this.registerContainerClick1_17_1(ServerboundPackets1_20_2.CONTAINER_CLICK);
        this.registerMerchantOffers1_19(ClientboundPackets1_19_4.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_20_2.SET_CREATIVE_MODE_SLOT);
        this.registerLevelParticles1_19(ClientboundPackets1_19_4.LEVEL_PARTICLES);
        ((Protocol1_20To1_20_2)this.protocol).registerServerbound(ServerboundPackets1_20_2.SET_BEACON, wrapper -> {
            if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                wrapper.write(Types.VAR_INT, wrapper.read(Types.VAR_INT) + 1);
            }
            if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                wrapper.write(Types.VAR_INT, wrapper.read(Types.VAR_INT) + 1);
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.FORGET_LEVEL_CHUNK, wrapper -> {
            int x = wrapper.read(Types.INT);
            int z = wrapper.read(Types.INT);
            wrapper.write(Types.CHUNK_POSITION, new ChunkPosition(x, z));
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.TAG_QUERY, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.write(Types.COMPOUND_TAG, wrapper.read(Types.NAMED_COMPOUND_TAG));
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.write(Types.COMPOUND_TAG, this.handleBlockEntity(wrapper.read(Types.NAMED_COMPOUND_TAG)));
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.LEVEL_CHUNK_WITH_LIGHT, wrapper -> {
            Object tracker = ((Protocol1_20To1_20_2)this.protocol).getEntityRewriter().tracker(wrapper.user());
            ChunkType1_18 chunkType = new ChunkType1_18(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_20To1_20_2)this.protocol).getMappingData().getBlockStateMappings().size()), MathUtil.ceilLog2(tracker.biomesSent()));
            Chunk chunk = wrapper.read(chunkType);
            ChunkType1_20_2 newChunkType = new ChunkType1_20_2(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_20To1_20_2)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
            wrapper.write(newChunkType, chunk);
            for (ChunkSection section : chunk.getSections()) {
                DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < blockPalette.size(); ++i) {
                    int id = blockPalette.idByIndex(i);
                    blockPalette.setIdByIndex(i, ((Protocol1_20To1_20_2)this.protocol).getMappingData().getNewBlockStateId(id));
                }
            }
            for (BlockEntity blockEntity : chunk.blockEntities()) {
                this.handleBlockEntity(blockEntity.tag());
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Types.COMPONENT);
                    wrapper.passthrough(Types.COMPONENT);
                    wrapper.write(Types.ITEM1_20_2, this.handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_13_2)));
                    wrapper.passthrough(Types.VAR_INT);
                    int flags = wrapper.passthrough(Types.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                wrapper.read(Types.STRING_ARRAY);
                int requirements = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }
                wrapper.passthrough(Types.BOOLEAN);
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_19_4.SET_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    byte slot;
                    do {
                        slot = wrapper.passthrough(Types.BYTE);
                        wrapper.write(Types.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_13_2)));
                    } while ((slot & 0xFFFFFF80) != 0);
                });
            }
        });
        new RecipeRewriter1_19_4<ClientboundPackets1_19_4>(this.protocol){

            @Override
            protected Type<Item> mappedItemType() {
                return BlockItemPacketRewriter1_20_2.this.mappedItemType();
            }

            @Override
            protected Type<Item[]> mappedItemArrayType() {
                return BlockItemPacketRewriter1_20_2.this.mappedItemArrayType();
            }
        }.register(ClientboundPackets1_19_4.UPDATE_RECIPES);
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.tag() != null) {
            BlockItemPacketRewriter1_20_2.to1_20_2Effects(item);
        }
        return super.handleItemToClient(connection, item);
    }

    @Override
    public @Nullable Item handleItemToServer(UserConnection connection, @Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.tag() != null) {
            BlockItemPacketRewriter1_20_2.to1_20_1Effects(item);
        }
        return super.handleItemToServer(connection, item);
    }

    public static void to1_20_2Effects(Item item) {
        Tag customPotionEffectsTag = item.tag().remove("CustomPotionEffects");
        if (customPotionEffectsTag instanceof ListTag) {
            ListTag effectsTag = (ListTag)customPotionEffectsTag;
            item.tag().put("custom_potion_effects", customPotionEffectsTag);
            for (Tag tag : effectsTag) {
                String key;
                if (!(tag instanceof CompoundTag)) continue;
                CompoundTag effectTag = (CompoundTag)tag;
                Tag idTag = effectTag.remove("Id");
                if (idTag instanceof NumberTag && (key = PotionEffects1_20_2.idToKey(((NumberTag)idTag).asInt() - 1)) != null) {
                    effectTag.put("id", new StringTag(key));
                }
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "Amplifier", "amplifier");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "Duration", "duration");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "Ambient", "ambient");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "ShowParticles", "show_particles");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "ShowIcon", "show_icon");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "HiddenEffect", "hidden_effect");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "FactorCalculationData", "factor_calculation_data");
            }
        }
    }

    public static void to1_20_1Effects(Item item) {
        Tag customPotionEffectsTag = item.tag().remove("custom_potion_effects");
        if (customPotionEffectsTag instanceof ListTag) {
            ListTag effectsTag = (ListTag)customPotionEffectsTag;
            item.tag().put("CustomPotionEffects", effectsTag);
            for (Tag tag : effectsTag) {
                if (!(tag instanceof CompoundTag)) continue;
                CompoundTag effectTag = (CompoundTag)tag;
                Tag tag2 = effectTag.remove("id");
                if (tag2 instanceof StringTag) {
                    StringTag idTag = (StringTag)tag2;
                    int id = PotionEffects1_20_2.keyToId(idTag.getValue());
                    effectTag.putInt("Id", id + 1);
                }
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "amplifier", "Amplifier");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "duration", "Duration");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "ambient", "Ambient");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "show_particles", "ShowParticles");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "show_icon", "ShowIcon");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "hidden_effect", "HiddenEffect");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "factor_calculation_data", "FactorCalculationData");
            }
        }
    }

    static void renameTag(CompoundTag tag, String entryName, String toEntryName) {
        Tag entry = tag.remove(entryName);
        if (entry != null) {
            tag.put(toEntryName, entry);
        }
    }

    @Nullable CompoundTag handleBlockEntity(@Nullable CompoundTag tag) {
        Tag secondaryEffect;
        if (tag == null) {
            return null;
        }
        Tag primaryEffect = tag.remove("Primary");
        if (primaryEffect instanceof NumberTag && ((NumberTag)primaryEffect).asInt() != 0) {
            tag.put("primary_effect", new StringTag(PotionEffects1_20_2.idToKeyOrLuck(((NumberTag)primaryEffect).asInt() - 1)));
        }
        if ((secondaryEffect = tag.remove("Secondary")) instanceof NumberTag && ((NumberTag)secondaryEffect).asInt() != 0) {
            tag.put("secondary_effect", new StringTag(PotionEffects1_20_2.idToKeyOrLuck(((NumberTag)secondaryEffect).asInt() - 1)));
        }
        return tag;
    }
}

