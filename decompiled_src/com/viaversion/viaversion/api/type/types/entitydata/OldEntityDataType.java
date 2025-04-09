/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataTypeTemplate;
import io.netty.buffer.ByteBuf;

public abstract class OldEntityDataType
extends EntityDataTypeTemplate {
    private static final int END = 127;

    @Override
    public EntityData read(ByteBuf buffer) {
        byte index2 = buffer.readByte();
        if (index2 == 127) {
            return null;
        }
        EntityDataType type = this.getType((index2 & 0xE0) >> 5);
        return new EntityData(index2 & 0x1F, type, type.type().read(buffer));
    }

    protected abstract EntityDataType getType(int var1);

    @Override
    public void write(ByteBuf buffer, EntityData object) {
        if (object == null) {
            buffer.writeByte(127);
        } else {
            int index2 = (object.dataType().typeId() << 5 | object.id() & 0x1F) & 0xFF;
            buffer.writeByte(index2);
            object.dataType().type().write(buffer, object.getValue());
        }
    }
}

