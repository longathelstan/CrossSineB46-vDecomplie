/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import io.netty.buffer.ByteBuf;

public class ChunkType
extends net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.ChunkType {
    public ChunkType() {
        super(true);
    }

    @Override
    protected void readUnusedInt(ByteBuf byteBuf) {
        byteBuf.readInt();
    }

    @Override
    protected void writeUnusedInt(ByteBuf byteBuf, Chunk chunk) {
        byteBuf.writeInt(0);
    }

    @Override
    public void write(ByteBuf byteBuf, Chunk chunk) {
        for (ChunkSection section : chunk.getSections()) {
            if (section == null || section.getLight().hasSkyLight()) continue;
            throw new IllegalStateException("Chunk section does not have skylight");
        }
        super.write(byteBuf, chunk);
    }
}

