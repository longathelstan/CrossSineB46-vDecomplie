/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes;
import com.viaversion.viaversion.api.type.types.entitydata.ModernEntityDataType;

public final class EntityDataType
extends ModernEntityDataType {
    private final EntityDataTypes dataTypes;

    public EntityDataType(EntityDataTypes dataTypes) {
        this.dataTypes = dataTypes;
    }

    @Override
    protected com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType getType(int index2) {
        return this.dataTypes.byId(index2);
    }
}

