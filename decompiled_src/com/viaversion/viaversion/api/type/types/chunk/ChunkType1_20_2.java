/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.chunk;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkSectionType1_18;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;

public final class ChunkType1_20_2
extends Type<Chunk> {
    private final ChunkSectionType1_18 sectionType;
    private final int ySectionCount;

    public ChunkType1_20_2(int ySectionCount, int globalPaletteBlockBits, int globalPaletteBiomeBits) {
        super(Chunk.class);
        Preconditions.checkArgument((ySectionCount > 0 ? 1 : 0) != 0);
        this.sectionType = new ChunkSectionType1_18(globalPaletteBlockBits, globalPaletteBiomeBits);
        this.ySectionCount = ySectionCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Chunk read(ByteBuf buffer) {
        int chunkX = buffer.readInt();
        int chunkZ = buffer.readInt();
        CompoundTag heightMap = (CompoundTag)Types.COMPOUND_TAG.read(buffer);
        ByteBuf sectionsBuf = buffer.readBytes(Types.VAR_INT.readPrimitive(buffer));
        ChunkSection[] sections = new ChunkSection[this.ySectionCount];
        try {
            for (int i = 0; i < this.ySectionCount; ++i) {
                sections[i] = this.sectionType.read(sectionsBuf);
            }
        }
        finally {
            sectionsBuf.release();
        }
        int blockEntitiesLength = Types.VAR_INT.readPrimitive(buffer);
        ArrayList<BlockEntity> blockEntities = new ArrayList<BlockEntity>(blockEntitiesLength);
        for (int i = 0; i < blockEntitiesLength; ++i) {
            blockEntities.add((BlockEntity)Types.BLOCK_ENTITY1_20_2.read(buffer));
        }
        return new Chunk1_18(chunkX, chunkZ, sections, heightMap, blockEntities);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf buffer, Chunk chunk) {
        buffer.writeInt(chunk.getX());
        buffer.writeInt(chunk.getZ());
        Types.COMPOUND_TAG.write(buffer, chunk.getHeightMap());
        ByteBuf sectionBuffer = buffer.alloc().buffer();
        try {
            for (ChunkSection section : chunk.getSections()) {
                this.sectionType.write(sectionBuffer, section);
            }
            sectionBuffer.readerIndex(0);
            Types.VAR_INT.writePrimitive(buffer, sectionBuffer.readableBytes());
            buffer.writeBytes(sectionBuffer);
        }
        finally {
            sectionBuffer.release();
        }
        Types.VAR_INT.writePrimitive(buffer, chunk.blockEntities().size());
        for (BlockEntity blockEntity : chunk.blockEntities()) {
            Types.BLOCK_ENTITY1_20_2.write(buffer, blockEntity);
        }
    }
}

