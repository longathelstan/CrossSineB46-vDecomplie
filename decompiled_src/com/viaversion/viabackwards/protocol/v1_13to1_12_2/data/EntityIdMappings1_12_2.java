/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.EntityIdMappings1_13;

public class EntityIdMappings1_12_2 {
    private static final Int2IntMap TYPES = new Int2IntOpenHashMap();

    public static int getOldId(int type1_13) {
        return TYPES.get(type1_13);
    }

    static {
        TYPES.defaultReturnValue(-1);
        for (Int2IntMap.Entry entry : EntityIdMappings1_13.getEntityTypes().int2IntEntrySet()) {
            TYPES.put(entry.getIntValue(), entry.getIntKey());
        }
    }
}

