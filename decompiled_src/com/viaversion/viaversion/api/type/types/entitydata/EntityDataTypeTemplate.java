/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.Type;

public abstract class EntityDataTypeTemplate
extends Type<EntityData> {
    protected EntityDataTypeTemplate() {
        super("Entity data type", EntityData.class);
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return EntityDataTypeTemplate.class;
    }
}

