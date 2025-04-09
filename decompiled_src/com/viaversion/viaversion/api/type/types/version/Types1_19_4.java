/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_19_4;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import java.util.List;

public final class Types1_19_4 {
    public static final ParticleType PARTICLE = new ParticleType();
    public static final EntityDataTypes1_19_4 ENTITY_DATA_TYPES = new EntityDataTypes1_19_4(PARTICLE);
    public static final Type<EntityData> ENTITY_DATA = new EntityDataType(ENTITY_DATA_TYPES);
    public static final Type<List<EntityData>> ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
}

