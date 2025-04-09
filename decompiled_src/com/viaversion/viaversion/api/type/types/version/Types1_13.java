/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_13;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkSectionType1_13;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import java.util.List;

public final class Types1_13 {
    public static final Type<ChunkSection> CHUNK_SECTION = new ChunkSectionType1_13();
    public static final ParticleType PARTICLE = new ParticleType();
    public static final EntityDataTypes1_13 ENTITY_DATA_TYPES = new EntityDataTypes1_13(PARTICLE);
    public static final Type<EntityData> ENTITY_DATA = new EntityDataType(ENTITY_DATA_TYPES);
    public static final Type<List<EntityData>> ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
}

