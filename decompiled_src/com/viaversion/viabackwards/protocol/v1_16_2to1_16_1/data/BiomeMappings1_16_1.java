/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.data;

import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.Key;
import java.util.Map;

public final class BiomeMappings1_16_1 {
    private static final Object2IntMap<String> MODERN_TO_LEGACY_ID = new Object2IntOpenHashMap<String>();
    private static final Object2IntMap<String> LEGACY_BIOMES = new Object2IntOpenHashMap<String>();

    private static void add(int id, String biome) {
        LEGACY_BIOMES.put(biome, id);
    }

    public static int toLegacyBiome(String biome) {
        int legacyBiome = MODERN_TO_LEGACY_ID.getInt(Key.stripMinecraftNamespace(biome));
        if (legacyBiome == -1) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                String string = biome;
                Protocol1_16_2To1_16_1.LOGGER.warning("Biome with id " + string + " has no legacy biome mapping (custom datapack?)");
            }
            return 1;
        }
        return legacyBiome;
    }

    static {
        LEGACY_BIOMES.defaultReturnValue(-1);
        MODERN_TO_LEGACY_ID.defaultReturnValue(-1);
        BiomeMappings1_16_1.add(0, "ocean");
        BiomeMappings1_16_1.add(1, "plains");
        BiomeMappings1_16_1.add(2, "desert");
        BiomeMappings1_16_1.add(3, "mountains");
        BiomeMappings1_16_1.add(4, "forest");
        BiomeMappings1_16_1.add(5, "taiga");
        BiomeMappings1_16_1.add(6, "swamp");
        BiomeMappings1_16_1.add(7, "river");
        BiomeMappings1_16_1.add(8, "nether");
        BiomeMappings1_16_1.add(9, "the_end");
        BiomeMappings1_16_1.add(10, "frozen_ocean");
        BiomeMappings1_16_1.add(11, "frozen_river");
        BiomeMappings1_16_1.add(12, "snowy_tundra");
        BiomeMappings1_16_1.add(13, "snowy_mountains");
        BiomeMappings1_16_1.add(14, "mushroom_fields");
        BiomeMappings1_16_1.add(15, "mushroom_field_shore");
        BiomeMappings1_16_1.add(16, "beach");
        BiomeMappings1_16_1.add(17, "desert_hills");
        BiomeMappings1_16_1.add(18, "wooded_hills");
        BiomeMappings1_16_1.add(19, "taiga_hills");
        BiomeMappings1_16_1.add(20, "mountain_edge");
        BiomeMappings1_16_1.add(21, "jungle");
        BiomeMappings1_16_1.add(22, "jungle_hills");
        BiomeMappings1_16_1.add(23, "jungle_edge");
        BiomeMappings1_16_1.add(24, "deep_ocean");
        BiomeMappings1_16_1.add(25, "stone_shore");
        BiomeMappings1_16_1.add(26, "snowy_beach");
        BiomeMappings1_16_1.add(27, "birch_forest");
        BiomeMappings1_16_1.add(28, "birch_forest_hills");
        BiomeMappings1_16_1.add(29, "dark_forest");
        BiomeMappings1_16_1.add(30, "snowy_taiga");
        BiomeMappings1_16_1.add(31, "snowy_taiga_hills");
        BiomeMappings1_16_1.add(32, "giant_tree_taiga");
        BiomeMappings1_16_1.add(33, "giant_tree_taiga_hills");
        BiomeMappings1_16_1.add(34, "wooded_mountains");
        BiomeMappings1_16_1.add(35, "savanna");
        BiomeMappings1_16_1.add(36, "savanna_plateau");
        BiomeMappings1_16_1.add(37, "badlands");
        BiomeMappings1_16_1.add(38, "wooded_badlands_plateau");
        BiomeMappings1_16_1.add(39, "badlands_plateau");
        BiomeMappings1_16_1.add(40, "small_end_islands");
        BiomeMappings1_16_1.add(41, "end_midlands");
        BiomeMappings1_16_1.add(42, "end_highlands");
        BiomeMappings1_16_1.add(43, "end_barrens");
        BiomeMappings1_16_1.add(44, "warm_ocean");
        BiomeMappings1_16_1.add(45, "lukewarm_ocean");
        BiomeMappings1_16_1.add(46, "cold_ocean");
        BiomeMappings1_16_1.add(47, "deep_warm_ocean");
        BiomeMappings1_16_1.add(48, "deep_lukewarm_ocean");
        BiomeMappings1_16_1.add(49, "deep_cold_ocean");
        BiomeMappings1_16_1.add(50, "deep_frozen_ocean");
        BiomeMappings1_16_1.add(127, "the_void");
        BiomeMappings1_16_1.add(129, "sunflower_plains");
        BiomeMappings1_16_1.add(130, "desert_lakes");
        BiomeMappings1_16_1.add(131, "gravelly_mountains");
        BiomeMappings1_16_1.add(132, "flower_forest");
        BiomeMappings1_16_1.add(133, "taiga_mountains");
        BiomeMappings1_16_1.add(134, "swamp_hills");
        BiomeMappings1_16_1.add(140, "ice_spikes");
        BiomeMappings1_16_1.add(149, "modified_jungle");
        BiomeMappings1_16_1.add(151, "modified_jungle_edge");
        BiomeMappings1_16_1.add(155, "tall_birch_forest");
        BiomeMappings1_16_1.add(156, "tall_birch_hills");
        BiomeMappings1_16_1.add(157, "dark_forest_hills");
        BiomeMappings1_16_1.add(158, "snowy_taiga_mountains");
        BiomeMappings1_16_1.add(160, "giant_spruce_taiga");
        BiomeMappings1_16_1.add(161, "giant_spruce_taiga_hills");
        BiomeMappings1_16_1.add(162, "modified_gravelly_mountains");
        BiomeMappings1_16_1.add(163, "shattered_savanna");
        BiomeMappings1_16_1.add(164, "shattered_savanna_plateau");
        BiomeMappings1_16_1.add(165, "eroded_badlands");
        BiomeMappings1_16_1.add(166, "modified_wooded_badlands_plateau");
        BiomeMappings1_16_1.add(167, "modified_badlands_plateau");
        BiomeMappings1_16_1.add(168, "bamboo_jungle");
        BiomeMappings1_16_1.add(169, "bamboo_jungle_hills");
        for (Object2IntMap.Entry entry : LEGACY_BIOMES.object2IntEntrySet()) {
            MODERN_TO_LEGACY_ID.put((String)entry.getKey(), entry.getIntValue());
        }
        JsonObject mappings = BackwardsMappingDataLoader.INSTANCE.loadFromDataDir("biome-mappings.json");
        for (Map.Entry<String, JsonElement> entry : mappings.entrySet()) {
            int legacyBiome = LEGACY_BIOMES.getInt(entry.getValue().getAsString());
            if (legacyBiome == -1) {
                String string = entry.getValue().getAsString();
                Protocol1_16_2To1_16_1.LOGGER.warning("Unknown legacy biome: " + string);
                continue;
            }
            MODERN_TO_LEGACY_ID.put(entry.getKey(), legacyBiome);
        }
    }
}

