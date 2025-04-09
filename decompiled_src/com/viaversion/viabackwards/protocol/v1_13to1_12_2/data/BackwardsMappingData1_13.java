/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.StatisticMappings1_13;
import java.util.HashMap;
import java.util.Map;

public class BackwardsMappingData1_13
extends BackwardsMappingData {
    private final Int2ObjectMap<String> statisticMappings = new Int2ObjectOpenHashMap<String>();
    private final Map<String, String> translateMappings = new HashMap<String, String>();

    public BackwardsMappingData1_13() {
        super("1.13", "1.12", Protocol1_12_2To1_13.class);
    }

    @Override
    public void loadExtras(CompoundTag data) {
        super.loadExtras(data);
        for (Map.Entry<String, Integer> entry : StatisticMappings1_13.CUSTOM_STATS.entrySet()) {
            this.statisticMappings.put((int)entry.getValue(), entry.getKey());
        }
        for (Map.Entry<String, Object> entry : Protocol1_12_2To1_13.MAPPINGS.getTranslateMapping().entrySet()) {
            this.translateMappings.put((String)entry.getValue(), entry.getKey());
        }
    }

    @Override
    public int getNewBlockStateId(int id) {
        int n;
        if (id >= 5635 && id <= 5650) {
            id = id < 5639 ? (id += 4) : (id < 5643 ? (id -= 4) : (id < 5647 ? (id += 4) : (id -= 4)));
        }
        int mappedId = super.getNewBlockStateId(id);
        switch (mappedId) {
            case 1595: 
            case 1596: 
            case 1597: {
                n = 1584;
                break;
            }
            case 1611: 
            case 1612: 
            case 1613: {
                n = 1600;
                break;
            }
            default: {
                n = mappedId;
            }
        }
        return n;
    }

    @Override
    protected int checkValidity(int id, int mappedId, String type) {
        return mappedId;
    }

    public Int2ObjectMap<String> getStatisticMappings() {
        return this.statisticMappings;
    }

    public Map<String, String> getTranslateMappings() {
        return this.translateMappings;
    }
}

