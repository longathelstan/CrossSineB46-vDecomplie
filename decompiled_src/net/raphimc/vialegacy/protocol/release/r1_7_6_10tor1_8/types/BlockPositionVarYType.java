/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;

public class BlockPositionVarYType<T extends Number>
extends Type<BlockPosition> {
    private final Type<T> yType;
    private final IntFunction<T> yConverter;

    public BlockPositionVarYType(Type<T> yType, IntFunction<T> yConverter) {
        super(BlockPosition.class);
        this.yType = yType;
        this.yConverter = yConverter;
    }

    @Override
    public BlockPosition read(ByteBuf buffer) {
        return new BlockPosition(buffer.readInt(), ((Number)this.yType.read(buffer)).intValue(), buffer.readInt());
    }

    @Override
    public void write(ByteBuf buffer, BlockPosition position) {
        buffer.writeInt(position.x());
        this.yType.write(buffer, (Number)this.yConverter.apply(position.y()));
        buffer.writeInt(position.z());
    }
}

