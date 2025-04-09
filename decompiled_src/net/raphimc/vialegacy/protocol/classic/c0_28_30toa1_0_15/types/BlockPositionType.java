/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class BlockPositionType
extends Type<BlockPosition> {
    public BlockPositionType() {
        super(BlockPosition.class);
    }

    @Override
    public BlockPosition read(ByteBuf buffer) {
        return new BlockPosition(buffer.readShort(), buffer.readShort(), buffer.readShort());
    }

    @Override
    public void write(ByteBuf buffer, BlockPosition position) {
        buffer.writeShort(position.x());
        buffer.writeShort(position.y());
        buffer.writeShort(position.z());
    }
}

