/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_20_5;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.ArrayType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataListType;
import com.viaversion.viaversion.api.type.types.entitydata.EntityDataType;
import com.viaversion.viaversion.api.type.types.item.ItemCostType1_20_5;
import com.viaversion.viaversion.api.type.types.item.ItemType1_20_5;
import com.viaversion.viaversion.api.type.types.item.StructuredDataType;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import java.util.List;

public final class Types1_20_5 {
    public static final StructuredDataType STRUCTURED_DATA = new StructuredDataType();
    public static final Type<StructuredData<?>[]> STRUCTURED_DATA_ARRAY = new ArrayType(STRUCTURED_DATA);
    public static final Type<Item> ITEM = new ItemType1_20_5(STRUCTURED_DATA);
    public static final Type<Item[]> ITEM_ARRAY = new ArrayType<Item>(ITEM);
    public static final Type<Item> ITEM_COST = new ItemCostType1_20_5(STRUCTURED_DATA_ARRAY);
    public static final Type<Item> OPTIONAL_ITEM_COST = new ItemCostType1_20_5.OptionalItemCostType(ITEM_COST);
    public static final ParticleType PARTICLE = new ParticleType();
    public static final ArrayType<Particle> PARTICLES = new ArrayType<Particle>(PARTICLE);
    public static final EntityDataTypes1_20_5 ENTITY_DATA_TYPES = new EntityDataTypes1_20_5(PARTICLE, PARTICLES);
    public static final Type<EntityData> ENTITY_DATA = new EntityDataType(ENTITY_DATA_TYPES);
    public static final Type<List<EntityData>> ENTITY_DATA_LIST = new EntityDataListType(ENTITY_DATA);
}

