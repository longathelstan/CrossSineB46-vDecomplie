/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.type;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;

public class PositionVarYType<T extends Number>
extends Type<BlockPosition> {
    private final Type<T> yType;
    private final IntFunction<T> toY;

    public PositionVarYType(Type<T> yType, IntFunction<T> toY) {
        super(BlockPosition.class);
        this.yType = yType;
        this.toY = toY;
    }

    @Override
    public BlockPosition read(ByteBuf buffer) {
        int x = buffer.readInt();
        int y = ((Number)this.yType.read(buffer)).intValue();
        int z = buffer.readInt();
        return new BlockPosition(x, y, z);
    }

    @Override
    public void write(ByteBuf buffer, BlockPosition value) {
        buffer.writeInt(value.x());
        this.yType.write(buffer, (Number)this.toY.apply(value.y()));
        buffer.writeInt(value.z());
    }
}

