/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16to1_15_2.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.data.AttributeMappings1_16;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;

public class BackwardsMappingData1_16
extends BackwardsMappingData {
    private final Map<String, String> attributeMappings = new HashMap<String, String>();

    public BackwardsMappingData1_16() {
        super("1.16", "1.15", Protocol1_15_2To1_16.class);
    }

    @Override
    protected void loadExtras(CompoundTag data) {
        super.loadExtras(data);
        for (Map.Entry entry : AttributeMappings1_16.attributeIdentifierMappings().entrySet()) {
            this.attributeMappings.put(Key.stripMinecraftNamespace((String)entry.getValue()), (String)entry.getKey());
        }
    }

    public String mappedAttributeIdentifier(String identifier) {
        return this.attributeMappings.getOrDefault(identifier, identifier);
    }
}

