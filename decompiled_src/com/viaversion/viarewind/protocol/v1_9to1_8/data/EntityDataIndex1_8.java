/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.data;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EntityDataIndex1_9;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;

public class EntityDataIndex1_8 {
    private static final HashMap<Pair<EntityTypes1_9.EntityType, Integer>, EntityDataIndex1_9> ENTITY_DATA_REWRITES = new HashMap();

    private static Optional<EntityDataIndex1_9> getIndex(EntityType type, int index2) {
        Pair<EntityType, Integer> pair = new Pair<EntityType, Integer>(type, index2);
        return Optional.ofNullable(ENTITY_DATA_REWRITES.get(pair));
    }

    public static EntityDataIndex1_9 searchIndex(EntityType type, int index2) {
        EntityType currentType = type;
        do {
            Optional<EntityDataIndex1_9> optMeta;
            if (!(optMeta = EntityDataIndex1_8.getIndex(currentType, index2)).isPresent()) continue;
            return optMeta.get();
        } while ((currentType = currentType.getParent()) != null);
        return null;
    }

    static {
        for (EntityDataIndex1_9 index2 : EntityDataIndex1_9.values()) {
            ENTITY_DATA_REWRITES.put(new Pair<EntityTypes1_9.EntityType, Integer>(index2.getClazz(), index2.getNewIndex()), index2);
        }
    }
}

