/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.ChunkPosition;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class ChunkPositionType
extends Type<ChunkPosition> {
    public ChunkPositionType() {
        super(ChunkPosition.class);
    }

    @Override
    public ChunkPosition read(ByteBuf buffer) {
        long chunkKey = Types.LONG.readPrimitive(buffer);
        return new ChunkPosition(chunkKey);
    }

    @Override
    public void write(ByteBuf buffer, ChunkPosition chunkPosition) {
        Types.LONG.writePrimitive(buffer, chunkPosition.chunkKey());
    }
}

