/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.math;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class BlockPositionType1_8
extends Type<BlockPosition> {
    public BlockPositionType1_8() {
        super(BlockPosition.class);
    }

    @Override
    public BlockPosition read(ByteBuf buffer) {
        long val = buffer.readLong();
        long x = val >> 38;
        long y = val << 26 >> 52;
        long z = val << 38 >> 38;
        return new BlockPosition((int)x, (short)y, (int)z);
    }

    @Override
    public void write(ByteBuf buffer, BlockPosition object) {
        buffer.writeLong(((long)object.x() & 0x3FFFFFFL) << 38 | ((long)object.y() & 0xFFFL) << 26 | (long)object.z() & 0x3FFFFFFL);
    }

    public static final class OptionalBlockPositionType
    extends OptionalType<BlockPosition> {
        public OptionalBlockPositionType() {
            super(Types.BLOCK_POSITION1_8);
        }
    }
}

