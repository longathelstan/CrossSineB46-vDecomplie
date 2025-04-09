/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataType1_12;
import java.util.List;

public final class Types1_12 {
    public static final Type<EntityData> ENTITY_DATA = new EntityDataType1_12();
    public static final Type<List<EntityData>> ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
}

