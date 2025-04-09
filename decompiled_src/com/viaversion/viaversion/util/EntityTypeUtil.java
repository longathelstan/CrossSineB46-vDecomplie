/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.ArrayList;
import java.util.Comparator;

public final class EntityTypeUtil {
    static final EntityType[] EMPTY_ARRAY = new EntityType[0];

    public static EntityType[] toOrderedArray(EntityType[] values2) {
        ArrayList<EntityType> types = new ArrayList<EntityType>();
        for (EntityType type : values2) {
            if (type.getId() == -1) continue;
            types.add(type);
        }
        types.sort(Comparator.comparingInt(EntityType::getId));
        return types.toArray(EMPTY_ARRAY);
    }

    public static <T extends EntityType> void initialize(T[] values2, EntityType[] typesToFill, Protocol<?, ?, ?, ?> protocol, EntityIdSetter<T> idSetter) {
        FullMappings mappings = protocol.getMappingData().getEntityMappings();
        for (T type : values2) {
            if (type.isAbstractType()) continue;
            int id = mappings.mappedId(type.identifier());
            Preconditions.checkArgument((id != -1 ? 1 : 0) != 0, (String)"Entity type %s has no id", (Object[])new Object[]{type.identifier()});
            idSetter.setId(type, id);
            typesToFill[id] = type;
        }
        if (typesToFill.length != mappings.mappedSize()) {
            int n = mappings.size();
            int n2 = typesToFill.length;
            throw new IllegalArgumentException("typesToFill length doesn't match the amount of entity types: " + n2 + " != " + n);
        }
    }

    public static EntityType[] createSizedArray(EntityType[] values2) {
        int count = 0;
        for (EntityType type : values2) {
            if (type.isAbstractType()) continue;
            ++count;
        }
        return new EntityType[count];
    }

    public static EntityType getTypeFromId(EntityType[] values2, int typeId, EntityType fallback) {
        EntityType type;
        if (typeId < 0 || typeId >= values2.length || (type = values2[typeId]) == null) {
            int n = typeId;
            String string = fallback.getClass().getSimpleName();
            Via.getPlatform().getLogger().severe("Could not find " + string + " type id " + n);
            return fallback;
        }
        return type;
    }

    @FunctionalInterface
    public static interface EntityIdSetter<T extends EntityType> {
        public void setId(T var1, int var2);
    }
}

