/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.entitydata;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;
import com.viaversion.viaversion.api.type.types.entitydata.ModernEntityDataType;

public class EntityDataType1_9
extends ModernEntityDataType {
    @Override
    protected EntityDataType getType(int index2) {
        return EntityDataTypes1_9.byId(index2);
    }
}

