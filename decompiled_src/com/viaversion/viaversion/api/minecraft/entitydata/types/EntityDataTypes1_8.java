/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;

public enum EntityDataTypes1_8 implements EntityDataType
{
    BYTE(Types.BYTE),
    SHORT(Types.SHORT),
    INT(Types.INT),
    FLOAT(Types.FLOAT),
    STRING(Types.STRING),
    ITEM(Types.ITEM1_8),
    BLOCK_POSITION(Types.VECTOR),
    ROTATIONS(Types.ROTATIONS);

    private final Type<?> type;

    private EntityDataTypes1_8(Type<?> type) {
        this.type = type;
    }

    public static EntityDataTypes1_8 byId(int id) {
        return EntityDataTypes1_8.values()[id];
    }

    @Override
    public int typeId() {
        return this.ordinal();
    }

    @Override
    public Type type() {
        return this.type;
    }
}

