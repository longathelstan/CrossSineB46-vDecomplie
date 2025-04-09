/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.blockentity;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import java.util.Objects;

public final class BlockEntityImpl
implements BlockEntity {
    private final byte packedXZ;
    private final short y;
    private final int typeId;
    private final CompoundTag tag;

    public BlockEntityImpl(byte packedXZ, short y, int typeId, CompoundTag tag) {
        this.packedXZ = packedXZ;
        this.y = y;
        this.typeId = typeId;
        this.tag = tag;
    }

    @Override
    public BlockEntity withTypeId(int typeId) {
        return new BlockEntityImpl(this.packedXZ, this.y, typeId, this.tag);
    }

    @Override
    public byte packedXZ() {
        return this.packedXZ;
    }

    @Override
    public short y() {
        return this.y;
    }

    @Override
    public int typeId() {
        return this.typeId;
    }

    @Override
    public CompoundTag tag() {
        return this.tag;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BlockEntityImpl)) {
            return false;
        }
        BlockEntityImpl blockEntityImpl = (BlockEntityImpl)object;
        return this.packedXZ == blockEntityImpl.packedXZ && this.y == blockEntityImpl.y && this.typeId == blockEntityImpl.typeId && Objects.equals(this.tag, blockEntityImpl.tag);
    }

    public int hashCode() {
        return (((0 * 31 + Byte.hashCode(this.packedXZ)) * 31 + Short.hashCode(this.y)) * 31 + Integer.hashCode(this.typeId)) * 31 + Objects.hashCode(this.tag);
    }

    public String toString() {
        return String.format("%s[packedXZ=%s, y=%s, typeId=%s, tag=%s]", this.getClass().getSimpleName(), Byte.toString(this.packedXZ), Short.toString(this.y), Integer.toString(this.typeId), Objects.toString(this.tag));
    }
}

