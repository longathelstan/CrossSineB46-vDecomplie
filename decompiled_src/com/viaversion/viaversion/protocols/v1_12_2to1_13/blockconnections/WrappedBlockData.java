/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.util.Key;
import java.util.LinkedHashMap;
import java.util.Map;

public final class WrappedBlockData {
    private final LinkedHashMap<String, String> blockData = new LinkedHashMap();
    private final String minecraftKey;
    private final int savedBlockStateId;

    public static WrappedBlockData fromString(String s) {
        String[] array = s.split("\\[");
        String key = array[0];
        WrappedBlockData wrappedBlockdata = new WrappedBlockData(key, ConnectionData.getId(s));
        if (array.length > 1) {
            String[] data;
            String blockData = array[1];
            blockData = blockData.replace("]", "");
            for (String d : data = blockData.split(",")) {
                String[] a = d.split("=");
                wrappedBlockdata.blockData.put(a[0], a[1]);
            }
        }
        return wrappedBlockdata;
    }

    private WrappedBlockData(String minecraftKey, int savedBlockStateId) {
        this.minecraftKey = Key.namespaced(minecraftKey);
        this.savedBlockStateId = savedBlockStateId;
    }

    public String toString() {
        String string = this.minecraftKey;
        StringBuilder sb = new StringBuilder(string + "[");
        for (Map.Entry<String, String> entry : this.blockData.entrySet()) {
            sb.append(entry.getKey()).append('=').append(entry.getValue()).append(',');
        }
        String string2 = sb.substring(0, sb.length() - 1);
        return string2 + "]";
    }

    public String getMinecraftKey() {
        return this.minecraftKey;
    }

    public int getSavedBlockStateId() {
        return this.savedBlockStateId;
    }

    public int getBlockStateId() {
        return ConnectionData.getId(this.toString());
    }

    public WrappedBlockData set(String data, Object value) {
        if (!this.hasData(data)) {
            String string = this.minecraftKey;
            String string2 = data;
            throw new UnsupportedOperationException("No blockdata found for " + string2 + " at " + string);
        }
        this.blockData.put(data, value.toString());
        return this;
    }

    public String getValue(String data) {
        return this.blockData.get(data);
    }

    public boolean hasData(String key) {
        return this.blockData.containsKey(key);
    }
}

