/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_2to1_20.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.protocol.v1_20_2to1_20.Protocol1_20_2To1_20;
import com.viaversion.viabackwards.protocol.v1_20_2to1_20.provider.AdvancementCriteriaProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ChunkPosition;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.data.PotionEffects1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter.RecipeRewriter1_20_2;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.MathUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPacketRewriter1_20_2
extends BackwardsItemRewriter<ClientboundPackets1_20_2, ServerboundPackets1_19_4, Protocol1_20_2To1_20> {
    public BlockItemPacketRewriter1_20_2(Protocol1_20_2To1_20 protocol) {
        super(protocol, Types.ITEM1_20_2, Types.ITEM1_20_2_ARRAY, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_20_2> blockRewriter = BlockRewriter.for1_14(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_20_2.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_20_2.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_20_2.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_20_2.LEVEL_EVENT, 1010, 2001);
        this.registerSetContent1_17_1(ClientboundPackets1_20_2.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_20_2.CONTAINER_SET_SLOT);
        this.registerContainerClick1_17_1(ServerboundPackets1_19_4.CONTAINER_CLICK);
        this.registerMerchantOffers1_19(ClientboundPackets1_20_2.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_19_4.SET_CREATIVE_MODE_SLOT);
        this.registerLevelParticles1_19(ClientboundPackets1_20_2.LEVEL_PARTICLES);
        ((Protocol1_20_2To1_20)this.protocol).cancelClientbound(ClientboundPackets1_20_2.CHUNK_BATCH_START);
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.CHUNK_BATCH_FINISHED, null, wrapper -> {
            wrapper.cancel();
            PacketWrapper receivedPacket = wrapper.create(ServerboundPackets1_20_2.CHUNK_BATCH_RECEIVED);
            receivedPacket.write(Types.FLOAT, Float.valueOf(500.0f));
            receivedPacket.sendToServer(Protocol1_20_2To1_20.class);
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.FORGET_LEVEL_CHUNK, wrapper -> {
            ChunkPosition chunkPosition = wrapper.read(Types.CHUNK_POSITION);
            wrapper.write(Types.INT, chunkPosition.chunkX());
            wrapper.write(Types.INT, chunkPosition.chunkZ());
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.MAP_ITEM_DATA, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.BOOLEAN);
            if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                int icons = wrapper.passthrough(Types.VAR_INT);
                for (int i = 0; i < icons; ++i) {
                    int markerType = wrapper.read(Types.VAR_INT);
                    wrapper.write(Types.VAR_INT, markerType < 27 ? markerType : 2);
                    wrapper.passthrough(Types.BYTE);
                    wrapper.passthrough(Types.BYTE);
                    wrapper.passthrough(Types.BYTE);
                    wrapper.passthrough(Types.OPTIONAL_COMPONENT);
                }
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.TAG_QUERY, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.write(Types.NAMED_COMPOUND_TAG, wrapper.read(Types.COMPOUND_TAG));
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.write(Types.NAMED_COMPOUND_TAG, this.handleBlockEntity(wrapper.read(Types.COMPOUND_TAG)));
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.LEVEL_CHUNK_WITH_LIGHT, wrapper -> {
            Object tracker = ((Protocol1_20_2To1_20)this.protocol).getEntityRewriter().tracker(wrapper.user());
            ChunkType1_20_2 chunkType = new ChunkType1_20_2(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_20_2To1_20)this.protocol).getMappingData().getBlockStateMappings().size()), MathUtil.ceilLog2(tracker.biomesSent()));
            Chunk chunk = wrapper.read(chunkType);
            ChunkType1_18 newChunkType = new ChunkType1_18(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_20_2To1_20)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
            wrapper.write(newChunkType, chunk);
            for (ChunkSection section : chunk.getSections()) {
                DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < blockPalette.size(); ++i) {
                    int id = blockPalette.idByIndex(i);
                    blockPalette.setIdByIndex(i, ((Protocol1_20_2To1_20)this.protocol).getMappingData().getNewBlockStateId(id));
                }
            }
            for (BlockEntity blockEntity : chunk.blockEntities()) {
                this.handleBlockEntity(blockEntity.tag());
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerServerbound(ServerboundPackets1_19_4.SET_BEACON, wrapper -> {
            if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                wrapper.write(Types.VAR_INT, wrapper.read(Types.VAR_INT) - 1);
            }
            if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                wrapper.write(Types.VAR_INT, wrapper.read(Types.VAR_INT) - 1);
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.UPDATE_ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                String advancement = wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Types.COMPONENT);
                    wrapper.passthrough(Types.COMPONENT);
                    wrapper.write(Types.ITEM1_13_2, this.handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_20_2)));
                    wrapper.passthrough(Types.VAR_INT);
                    int flags = wrapper.passthrough(Types.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                AdvancementCriteriaProvider criteriaProvider = Via.getManager().getProviders().get(AdvancementCriteriaProvider.class);
                wrapper.write(Types.STRING_ARRAY, criteriaProvider.getCriteria(advancement));
                int requirements = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }
                wrapper.passthrough(Types.BOOLEAN);
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_20_2.SET_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    byte slot;
                    do {
                        slot = wrapper.passthrough(Types.BYTE);
                        wrapper.write(Types.ITEM1_13_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_20_2)));
                    } while ((slot & 0xFFFFFF80) != 0);
                });
            }
        });
        new RecipeRewriter1_20_2<ClientboundPackets1_20_2>(this.protocol){

            @Override
            protected Type<Item> mappedItemType() {
                return BlockItemPacketRewriter1_20_2.this.mappedItemType();
            }

            @Override
            protected Type<Item[]> mappedItemArrayType() {
                return BlockItemPacketRewriter1_20_2.this.mappedItemArrayType();
            }
        }.register(ClientboundPackets1_20_2.UPDATE_RECIPES);
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.tag() != null) {
            com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter.BlockItemPacketRewriter1_20_2.to1_20_1Effects(item);
            CompoundTag skullOwnerTag = item.tag().getCompoundTag("SkullOwner");
            if (skullOwnerTag != null && !skullOwnerTag.contains("Id") && skullOwnerTag.contains("Properties")) {
                skullOwnerTag.put("Id", new IntArrayTag(new int[]{0, 0, 0, 0}));
            }
        }
        return super.handleItemToClient(connection, item);
    }

    @Override
    public @Nullable Item handleItemToServer(UserConnection connection, @Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.tag() != null) {
            com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter.BlockItemPacketRewriter1_20_2.to1_20_2Effects(item);
        }
        return super.handleItemToServer(connection, item);
    }

    @Nullable CompoundTag handleBlockEntity(@Nullable CompoundTag tag) {
        CompoundTag skullOwnerTag;
        Tag secondaryEffect;
        if (tag == null) {
            return null;
        }
        Tag primaryEffect = tag.remove("primary_effect");
        if (primaryEffect instanceof StringTag) {
            String effectKey = Key.stripMinecraftNamespace(((StringTag)primaryEffect).getValue());
            tag.putInt("Primary", PotionEffects1_20_2.keyToId(effectKey) + 1);
        }
        if ((secondaryEffect = tag.remove("secondary_effect")) instanceof StringTag) {
            String effectKey = Key.stripMinecraftNamespace(((StringTag)secondaryEffect).getValue());
            tag.putInt("Secondary", PotionEffects1_20_2.keyToId(effectKey) + 1);
        }
        if ((skullOwnerTag = tag.getCompoundTag("SkullOwner")) != null && !skullOwnerTag.contains("Id") && skullOwnerTag.contains("Properties")) {
            skullOwnerTag.put("Id", new IntArrayTag(new int[]{0, 0, 0, 0}));
        }
        return tag;
    }
}

