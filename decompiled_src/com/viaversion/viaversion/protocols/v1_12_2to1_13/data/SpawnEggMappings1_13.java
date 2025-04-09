/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.viaversion.viaversion.util.Key;
import java.util.Optional;

public class SpawnEggMappings1_13 {
    private static final BiMap<String, Integer> spawnEggs = HashBiMap.create();

    private static void registerSpawnEgg(String name) {
        spawnEggs.put((Object)Key.namespaced(name), (Object)spawnEggs.size());
    }

    public static int getSpawnEggId(String entityIdentifier) {
        if (!spawnEggs.containsKey((Object)entityIdentifier)) {
            return -1;
        }
        return 0x17F0000 | (Integer)spawnEggs.get((Object)entityIdentifier) & 0xFFFF;
    }

    public static Optional<String> getEntityId(int spawnEggId) {
        if (spawnEggId >> 16 != 383) {
            return Optional.empty();
        }
        return Optional.ofNullable((String)spawnEggs.inverse().get((Object)(spawnEggId & 0xFFFF)));
    }

    static {
        SpawnEggMappings1_13.registerSpawnEgg("bat");
        SpawnEggMappings1_13.registerSpawnEgg("blaze");
        SpawnEggMappings1_13.registerSpawnEgg("cave_spider");
        SpawnEggMappings1_13.registerSpawnEgg("chicken");
        SpawnEggMappings1_13.registerSpawnEgg("cow");
        SpawnEggMappings1_13.registerSpawnEgg("creeper");
        SpawnEggMappings1_13.registerSpawnEgg("donkey");
        SpawnEggMappings1_13.registerSpawnEgg("elder_guardian");
        SpawnEggMappings1_13.registerSpawnEgg("enderman");
        SpawnEggMappings1_13.registerSpawnEgg("endermite");
        SpawnEggMappings1_13.registerSpawnEgg("evocation_illager");
        SpawnEggMappings1_13.registerSpawnEgg("ghast");
        SpawnEggMappings1_13.registerSpawnEgg("guardian");
        SpawnEggMappings1_13.registerSpawnEgg("horse");
        SpawnEggMappings1_13.registerSpawnEgg("husk");
        SpawnEggMappings1_13.registerSpawnEgg("llama");
        SpawnEggMappings1_13.registerSpawnEgg("magma_cube");
        SpawnEggMappings1_13.registerSpawnEgg("mooshroom");
        SpawnEggMappings1_13.registerSpawnEgg("mule");
        SpawnEggMappings1_13.registerSpawnEgg("ocelot");
        SpawnEggMappings1_13.registerSpawnEgg("parrot");
        SpawnEggMappings1_13.registerSpawnEgg("pig");
        SpawnEggMappings1_13.registerSpawnEgg("polar_bear");
        SpawnEggMappings1_13.registerSpawnEgg("rabbit");
        SpawnEggMappings1_13.registerSpawnEgg("sheep");
        SpawnEggMappings1_13.registerSpawnEgg("shulker");
        SpawnEggMappings1_13.registerSpawnEgg("silverfish");
        SpawnEggMappings1_13.registerSpawnEgg("skeleton");
        SpawnEggMappings1_13.registerSpawnEgg("skeleton_horse");
        SpawnEggMappings1_13.registerSpawnEgg("slime");
        SpawnEggMappings1_13.registerSpawnEgg("spider");
        SpawnEggMappings1_13.registerSpawnEgg("squid");
        SpawnEggMappings1_13.registerSpawnEgg("stray");
        SpawnEggMappings1_13.registerSpawnEgg("vex");
        SpawnEggMappings1_13.registerSpawnEgg("villager");
        SpawnEggMappings1_13.registerSpawnEgg("vindication_illager");
        SpawnEggMappings1_13.registerSpawnEgg("witch");
        SpawnEggMappings1_13.registerSpawnEgg("wither_skeleton");
        SpawnEggMappings1_13.registerSpawnEgg("wolf");
        SpawnEggMappings1_13.registerSpawnEgg("zombie");
        SpawnEggMappings1_13.registerSpawnEgg("zombie_horse");
        SpawnEggMappings1_13.registerSpawnEgg("zombie_pigman");
        SpawnEggMappings1_13.registerSpawnEgg("zombie_villager");
    }
}

