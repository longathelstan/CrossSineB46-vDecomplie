/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.type.types.entitydata.OldEntityDataType;

public class EntityDataType1_8
extends OldEntityDataType {
    @Override
    protected EntityDataType getType(int index2) {
        return EntityDataTypes1_8.byId(index2);
    }
}

