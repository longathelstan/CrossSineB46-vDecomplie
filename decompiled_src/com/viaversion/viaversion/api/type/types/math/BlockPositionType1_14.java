/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class BlockPositionType1_14
extends Type<BlockPosition> {
    public BlockPositionType1_14() {
        super(BlockPosition.class);
    }

    @Override
    public BlockPosition read(ByteBuf buffer) {
        long val = buffer.readLong();
        long x = val >> 38;
        long y = val << 52 >> 52;
        long z = val << 26 >> 38;
        return new BlockPosition((int)x, (int)y, (int)z);
    }

    @Override
    public void write(ByteBuf buffer, BlockPosition object) {
        buffer.writeLong(((long)object.x() & 0x3FFFFFFL) << 38 | (long)(object.y() & 0xFFF) | ((long)object.z() & 0x3FFFFFFL) << 12);
    }

    public static final class OptionalBlockPositionType
    extends OptionalType<BlockPosition> {
        public OptionalBlockPositionType() {
            super(Types.BLOCK_POSITION1_14);
        }
    }
}

