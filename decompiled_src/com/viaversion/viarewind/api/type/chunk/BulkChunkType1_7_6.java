/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.type.chunk;

import com.viaversion.viarewind.api.type.chunk.ChunkType1_7_6;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.Pair;
import io.netty.buffer.ByteBuf;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

public class BulkChunkType1_7_6
extends Type<Chunk[]> {
    public static final BulkChunkType1_7_6 TYPE = new BulkChunkType1_7_6();

    public BulkChunkType1_7_6() {
        super(Chunk[].class);
    }

    @Override
    public Chunk[] read(ByteBuf byteBuf) {
        throw new UnsupportedOperationException();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf byteBuf, Chunk[] chunks) {
        int compressedSize;
        byte[] compressedData;
        int chunkCount = chunks.length;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int[] chunkX = new int[chunkCount];
        int[] chunkZ = new int[chunkCount];
        short[] primaryBitMask = new short[chunkCount];
        short[] additionalBitMask = new short[chunkCount];
        for (int i = 0; i < chunkCount; ++i) {
            Pair<byte[], Short> chunkData;
            Chunk chunk = chunks[i];
            try {
                chunkData = ChunkType1_7_6.serialize(chunk);
                output.write(chunkData.key());
            }
            catch (Exception e) {
                throw new RuntimeException("Unable to serialize chunk", e);
            }
            chunkX[i] = chunk.getX();
            chunkZ[i] = chunk.getZ();
            primaryBitMask[i] = (short)chunk.getBitmask();
            additionalBitMask[i] = chunkData.value();
        }
        byte[] data = output.toByteArray();
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(data, 0, data.length);
            deflater.finish();
            compressedData = new byte[data.length];
            compressedSize = deflater.deflate(compressedData);
        }
        finally {
            deflater.end();
        }
        byteBuf.writeShort(chunkCount);
        byteBuf.writeInt(compressedSize);
        boolean skyLight = false;
        block6: for (Chunk chunk : chunks) {
            for (ChunkSection section : chunk.getSections()) {
                if (section == null || !section.getLight().hasSkyLight()) continue;
                skyLight = true;
                continue block6;
            }
        }
        byteBuf.writeBoolean(skyLight);
        byteBuf.writeBytes(compressedData, 0, compressedSize);
        for (int i = 0; i < chunkCount; ++i) {
            byteBuf.writeInt(chunkX[i]);
            byteBuf.writeInt(chunkZ[i]);
            byteBuf.writeShort((int)primaryBitMask[i]);
            byteBuf.writeShort((int)additionalBitMask[i]);
        }
    }
}

