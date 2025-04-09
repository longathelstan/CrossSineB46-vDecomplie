/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.MathUtil;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockRewriter<C extends ClientboundPacketType> {
    final Protocol<C, ?, ?, ?> protocol;
    final Type<BlockPosition> positionType;
    final Type<CompoundTag> compoundTagType;

    public BlockRewriter(Protocol<C, ?, ?, ?> protocol, Type<BlockPosition> positionType, Type<CompoundTag> compoundTagType) {
        this.protocol = protocol;
        this.positionType = positionType;
        this.compoundTagType = compoundTagType;
    }

    public static <C extends ClientboundPacketType> BlockRewriter<C> legacy(Protocol<C, ?, ?, ?> protocol) {
        return new BlockRewriter<C>(protocol, Types.BLOCK_POSITION1_8, Types.NAMED_COMPOUND_TAG);
    }

    public static <C extends ClientboundPacketType> BlockRewriter<C> for1_14(Protocol<C, ?, ?, ?> protocol) {
        return new BlockRewriter<C>(protocol, Types.BLOCK_POSITION1_14, Types.NAMED_COMPOUND_TAG);
    }

    public static <C extends ClientboundPacketType> BlockRewriter<C> for1_20_2(Protocol<C, ?, ?, ?> protocol) {
        return new BlockRewriter<C>(protocol, Types.BLOCK_POSITION1_14, Types.COMPOUND_TAG);
    }

    public void registerBlockEvent(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(BlockRewriter.this.positionType);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    if (BlockRewriter.this.protocol.getMappingData().getBlockMappings() == null) {
                        return;
                    }
                    int id = wrapper.get(Types.VAR_INT, 0);
                    int mappedId = BlockRewriter.this.protocol.getMappingData().getNewBlockId(id);
                    if (mappedId == -1) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.set(Types.VAR_INT, 0, mappedId);
                });
            }
        });
    }

    public void registerBlockUpdate(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(BlockRewriter.this.positionType);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> wrapper.set(Types.VAR_INT, 0, BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Types.VAR_INT, 0))));
            }
        });
    }

    public void registerChunkBlocksUpdate(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Types.BLOCK_CHANGE_ARRAY)) {
                        record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
    }

    public void registerSectionBlocksUpdate(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.LONG);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Types.VAR_LONG_BLOCK_CHANGE_ARRAY)) {
                        record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
    }

    public void registerSectionBlocksUpdate1_20(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.LONG);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Types.VAR_LONG_BLOCK_CHANGE_ARRAY)) {
                        record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
    }

    public void registerBlockBreakAck(C packetType) {
        this.registerBlockUpdate(packetType);
    }

    public void registerLevelEvent(C packetType, int playRecordId, int blockBreakId) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int id = wrapper.passthrough(Types.INT);
            wrapper.passthrough(this.positionType);
            int data = wrapper.read(Types.INT);
            MappingData mappingData = this.protocol.getMappingData();
            if (playRecordId != -1 && id == playRecordId && mappingData.getItemMappings() != null) {
                wrapper.write(Types.INT, mappingData.getNewItemId(data));
            } else if (id == blockBreakId && mappingData.getBlockStateMappings() != null) {
                wrapper.write(Types.INT, mappingData.getNewBlockStateId(data));
            } else {
                wrapper.write(Types.INT, data);
            }
        });
    }

    public void registerLevelEvent1_21(C packetType, int blockBreakId) {
        this.registerLevelEvent(packetType, -1, blockBreakId);
    }

    public void registerLevelChunk(C packetType, Type<Chunk> chunkType, Type<Chunk> newChunkType) {
        this.registerLevelChunk(packetType, chunkType, newChunkType, null);
    }

    public void registerLevelChunk(C packetType, Type<Chunk> chunkType, Type<Chunk> newChunkType, @Nullable BiConsumer<UserConnection, Chunk> chunkRewriter) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            Chunk chunk = (Chunk)wrapper.read(chunkType);
            wrapper.write(newChunkType, chunk);
            this.handleChunk(chunk);
            if (chunkRewriter != null) {
                chunkRewriter.accept(wrapper.user(), chunk);
            }
        });
    }

    public void handleChunk(Chunk chunk) {
        for (int s = 0; s < chunk.getSections().length; ++s) {
            ChunkSection section = chunk.getSections()[s];
            if (section == null) continue;
            DataPalette palette = section.palette(PaletteType.BLOCKS);
            for (int i = 0; i < palette.size(); ++i) {
                int mappedBlockStateId = this.protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
                palette.setIdByIndex(i, mappedBlockStateId);
            }
        }
    }

    public void registerLevelChunk1_19(C packetType, ChunkTypeSupplier chunkTypeSupplier) {
        this.registerLevelChunk1_19(packetType, chunkTypeSupplier, null);
    }

    public void registerLevelChunk1_19(C packetType, ChunkTypeSupplier chunkTypeSupplier, @Nullable BiConsumer<UserConnection, BlockEntity> blockEntityHandler) {
        this.protocol.registerClientbound(packetType, this.chunkHandler1_19(chunkTypeSupplier, blockEntityHandler));
    }

    public PacketHandler chunkHandler1_19(ChunkTypeSupplier chunkTypeSupplier, @Nullable BiConsumer<UserConnection, BlockEntity> blockEntityHandler) {
        return wrapper -> {
            Chunk chunk = this.handleChunk1_19(wrapper, chunkTypeSupplier);
            Mappings blockEntityMappings = this.protocol.getMappingData().getBlockEntityMappings();
            if (blockEntityMappings != null || blockEntityHandler != null) {
                List<BlockEntity> blockEntities = chunk.blockEntities();
                for (int i = 0; i < blockEntities.size(); ++i) {
                    int mappedId;
                    int id;
                    BlockEntity blockEntity = blockEntities.get(i);
                    if (blockEntityMappings != null && (id = blockEntity.typeId()) != (mappedId = blockEntityMappings.getNewIdOrDefault(id, id))) {
                        blockEntities.set(i, blockEntity.withTypeId(mappedId));
                    }
                    if (blockEntityHandler == null || blockEntity.tag() == null) continue;
                    blockEntityHandler.accept(wrapper.user(), blockEntity);
                }
            }
        };
    }

    public Chunk handleChunk1_19(PacketWrapper wrapper, ChunkTypeSupplier chunkTypeSupplier) {
        Object tracker = this.protocol.getEntityRewriter().tracker(wrapper.user());
        Preconditions.checkArgument((tracker.biomesSent() != -1 ? 1 : 0) != 0, (Object)"Biome count not set");
        Preconditions.checkArgument((tracker.currentWorldSectionHeight() != -1 ? 1 : 0) != 0, (Object)"Section height not set");
        Type<Chunk> chunkType = chunkTypeSupplier.supply(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(this.protocol.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
        Chunk chunk = wrapper.passthrough(chunkType);
        for (ChunkSection section : chunk.getSections()) {
            DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
            for (int i = 0; i < blockPalette.size(); ++i) {
                int id = blockPalette.idByIndex(i);
                blockPalette.setIdByIndex(i, this.protocol.getMappingData().getNewBlockStateId(id));
            }
        }
        return chunk;
    }

    public void registerBlockEntityData(C packetType) {
        this.registerBlockEntityData(packetType, null);
    }

    public void registerBlockEntityData(C packetType, @Nullable Consumer<BlockEntity> blockEntityHandler) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            CompoundTag tag;
            BlockPosition position = wrapper.passthrough(this.positionType);
            int blockEntityId = wrapper.read(Types.VAR_INT);
            Mappings mappings = this.protocol.getMappingData().getBlockEntityMappings();
            if (mappings != null) {
                wrapper.write(Types.VAR_INT, mappings.getNewIdOrDefault(blockEntityId, blockEntityId));
            } else {
                wrapper.write(Types.VAR_INT, blockEntityId);
            }
            if (blockEntityHandler != null && (tag = wrapper.passthrough(this.compoundTagType)) != null) {
                BlockEntityImpl blockEntity = new BlockEntityImpl(BlockEntity.pack(position.x(), position.z()), (short)position.y(), blockEntityId, tag);
                blockEntityHandler.accept(blockEntity);
            }
        });
    }

    @FunctionalInterface
    public static interface ChunkTypeSupplier {
        public Type<Chunk> supply(int var1, int var2, int var3);
    }
}

