/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_10to1_11.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.viaversion.viaversion.util.Key;

public class BlockEntityMappings1_11 {
    private static final BiMap<String, String> OLD_TO_NEW_NAMES = HashBiMap.create();

    private static void rewrite(String oldName, String newName) {
        OLD_TO_NEW_NAMES.put((Object)oldName, (Object)Key.namespaced(newName));
    }

    public static BiMap<String, String> inverse() {
        return OLD_TO_NEW_NAMES.inverse();
    }

    public static String toNewIdentifier(String oldId) {
        String newName = (String)OLD_TO_NEW_NAMES.get((Object)oldId);
        if (newName != null) {
            return newName;
        }
        return oldId;
    }

    static {
        BlockEntityMappings1_11.rewrite("Furnace", "furnace");
        BlockEntityMappings1_11.rewrite("Chest", "chest");
        BlockEntityMappings1_11.rewrite("EnderChest", "ender_chest");
        BlockEntityMappings1_11.rewrite("RecordPlayer", "jukebox");
        BlockEntityMappings1_11.rewrite("Trap", "dispenser");
        BlockEntityMappings1_11.rewrite("Dropper", "dropper");
        BlockEntityMappings1_11.rewrite("Sign", "sign");
        BlockEntityMappings1_11.rewrite("MobSpawner", "mob_spawner");
        BlockEntityMappings1_11.rewrite("Music", "noteblock");
        BlockEntityMappings1_11.rewrite("Piston", "piston");
        BlockEntityMappings1_11.rewrite("Cauldron", "brewing_stand");
        BlockEntityMappings1_11.rewrite("EnchantTable", "enchanting_table");
        BlockEntityMappings1_11.rewrite("Airportal", "end_portal");
        BlockEntityMappings1_11.rewrite("Beacon", "beacon");
        BlockEntityMappings1_11.rewrite("Skull", "skull");
        BlockEntityMappings1_11.rewrite("DLDetector", "daylight_detector");
        BlockEntityMappings1_11.rewrite("Hopper", "hopper");
        BlockEntityMappings1_11.rewrite("Comparator", "comparator");
        BlockEntityMappings1_11.rewrite("FlowerPot", "flower_pot");
        BlockEntityMappings1_11.rewrite("Banner", "banner");
        BlockEntityMappings1_11.rewrite("Structure", "structure_block");
        BlockEntityMappings1_11.rewrite("EndGateway", "end_gateway");
        BlockEntityMappings1_11.rewrite("Control", "command_block");
    }
}

