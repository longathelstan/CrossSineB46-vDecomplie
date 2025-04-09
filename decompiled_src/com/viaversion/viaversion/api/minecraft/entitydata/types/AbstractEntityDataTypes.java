/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entitydata.types;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes;
import com.viaversion.viaversion.api.type.Type;

public abstract class AbstractEntityDataTypes
implements EntityDataTypes {
    private final EntityDataType[] values;

    protected AbstractEntityDataTypes(int values2) {
        this.values = new EntityDataType[values2];
    }

    @Override
    public EntityDataType byId(int id) {
        return this.values[id];
    }

    @Override
    public EntityDataType[] values() {
        return this.values;
    }

    protected EntityDataType add(int typeId, Type<?> type) {
        EntityDataType dataType;
        this.values[typeId] = dataType = EntityDataType.create(typeId, type);
        return dataType;
    }
}

