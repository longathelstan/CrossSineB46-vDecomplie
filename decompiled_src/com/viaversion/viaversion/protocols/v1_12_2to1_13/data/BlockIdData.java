/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.google.common.collect.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.MappingData1_13;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class BlockIdData {
    public static final String[] PREVIOUS = new String[0];
    public static final Map<String, String[]> blockIdMapping = new HashMap<String, String[]>();
    public static final Map<String, String[]> fallbackReverseMapping = new HashMap<String, String[]>();
    public static final Int2ObjectMap<String> numberIdToString = new Int2ObjectOpenHashMap<String>();

    public static void init() {
        InputStream stream = MappingData1_13.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockIds1.12to1.13.json");
        try (InputStreamReader reader = new InputStreamReader(stream);){
            Map map = (Map)GsonUtil.getGson().fromJson((Reader)reader, new TypeToken<Map<String, String[]>>(){}.getType());
            blockIdMapping.putAll(map);
            for (Map.Entry<String, String[]> entry : blockIdMapping.entrySet()) {
                for (String val : entry.getValue()) {
                    Object[] previous = fallbackReverseMapping.get(val);
                    if (previous == null) {
                        previous = PREVIOUS;
                    }
                    fallbackReverseMapping.put(val, (String[])ObjectArrays.concat((Object[])previous, (Object)entry.getKey()));
                }
            }
        }
        catch (IOException e) {
            Protocol1_12_2To1_13.LOGGER.log(Level.SEVERE, "Failed to load block id mappings", e);
        }
        InputStream blockS = MappingData1_13.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockNumberToString1.12.json");
        try (InputStreamReader blockR = new InputStreamReader(blockS);){
            Map map = (Map)GsonUtil.getGson().fromJson((Reader)blockR, new TypeToken<Map<Integer, String>>(){}.getType());
            numberIdToString.putAll(map);
        }
        catch (IOException e) {
            Protocol1_12_2To1_13.LOGGER.log(Level.SEVERE, "Failed to load block number to string mappings", e);
        }
    }
}

