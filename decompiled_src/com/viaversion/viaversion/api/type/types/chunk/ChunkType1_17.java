/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.chunk;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public final class ChunkType1_17
extends Type<Chunk> {
    private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];
    private final int ySectionCount;

    public ChunkType1_17(int ySectionCount) {
        super(Chunk.class);
        Preconditions.checkArgument((ySectionCount > 0 ? 1 : 0) != 0);
        this.ySectionCount = ySectionCount;
    }

    @Override
    public Chunk read(ByteBuf input) {
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        BitSet sectionsMask = BitSet.valueOf((long[])Types.LONG_ARRAY_PRIMITIVE.read(input));
        CompoundTag heightMap = (CompoundTag)Types.NAMED_COMPOUND_TAG.read(input);
        int[] biomeData = (int[])Types.VAR_INT_ARRAY_PRIMITIVE.read(input);
        ByteBuf data = input.readSlice(Types.VAR_INT.readPrimitive(input));
        ChunkSection[] sections = new ChunkSection[this.ySectionCount];
        for (int i = 0; i < this.ySectionCount; ++i) {
            if (!sectionsMask.get(i)) continue;
            short nonAirBlocksCount = data.readShort();
            ChunkSection section = (ChunkSection)Types1_16.CHUNK_SECTION.read(data);
            section.setNonAirBlocksCount(nonAirBlocksCount);
            sections[i] = section;
        }
        ArrayList<CompoundTag> nbtData = new ArrayList<CompoundTag>(Arrays.asList((CompoundTag[])Types.NAMED_COMPOUND_TAG_ARRAY.read(input)));
        return new BaseChunk(chunkX, chunkZ, true, false, sectionsMask, sections, biomeData, heightMap, nbtData);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf output, Chunk chunk) {
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        Types.LONG_ARRAY_PRIMITIVE.write(output, chunk.getChunkMask().toLongArray());
        Types.NAMED_COMPOUND_TAG.write(output, chunk.getHeightMap());
        Types.VAR_INT_ARRAY_PRIMITIVE.write(output, chunk.getBiomeData());
        ByteBuf buf = output.alloc().buffer();
        try {
            ChunkSection[] sections;
            for (ChunkSection section : sections = chunk.getSections()) {
                if (section == null) continue;
                buf.writeShort(section.getNonAirBlocksCount());
                Types1_16.CHUNK_SECTION.write(buf, section);
            }
            buf.readerIndex(0);
            Types.VAR_INT.writePrimitive(output, buf.readableBytes());
            output.writeBytes(buf);
        }
        finally {
            buf.release();
        }
        Types.NAMED_COMPOUND_TAG_ARRAY.write(output, chunk.getBlockEntities().toArray(EMPTY_COMPOUNDS));
    }
}

