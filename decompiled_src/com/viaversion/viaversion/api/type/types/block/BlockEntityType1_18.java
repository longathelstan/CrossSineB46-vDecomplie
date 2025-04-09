/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.block;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class BlockEntityType1_18
extends Type<BlockEntity> {
    public BlockEntityType1_18() {
        super(BlockEntity.class);
    }

    @Override
    public BlockEntity read(ByteBuf buffer) {
        byte xz = buffer.readByte();
        short y = buffer.readShort();
        int typeId = Types.VAR_INT.readPrimitive(buffer);
        CompoundTag tag = (CompoundTag)Types.NAMED_COMPOUND_TAG.read(buffer);
        return new BlockEntityImpl(xz, y, typeId, tag);
    }

    @Override
    public void write(ByteBuf buffer, BlockEntity entity) {
        buffer.writeByte((int)entity.packedXZ());
        buffer.writeShort((int)entity.y());
        Types.VAR_INT.writePrimitive(buffer, entity.typeId());
        Types.NAMED_COMPOUND_TAG.write(buffer, entity.tag());
    }
}

