/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.data;

import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityNameMappings1_12_2 {
    private static final Map<String, String> ENTITY_NAMES = new HashMap<String, String>();

    private static void reg(String past, String future) {
        ENTITY_NAMES.put(Key.namespaced(future), Key.namespaced(past));
    }

    public static String rewrite(String entName) {
        String entityName;
        String string = entityName = ENTITY_NAMES.get(Key.namespaced(entName));
        return string != null ? string : Objects.requireNonNull(entName, "defaultObj");
    }

    static {
        EntityNameMappings1_12_2.reg("commandblock_minecart", "command_block_minecart");
        EntityNameMappings1_12_2.reg("ender_crystal", "end_crystal");
        EntityNameMappings1_12_2.reg("evocation_fangs", "evoker_fangs");
        EntityNameMappings1_12_2.reg("evocation_illager", "evoker");
        EntityNameMappings1_12_2.reg("eye_of_ender_signal", "eye_of_ender");
        EntityNameMappings1_12_2.reg("fireworks_rocket", "firework_rocket");
        EntityNameMappings1_12_2.reg("illusion_illager", "illusioner");
        EntityNameMappings1_12_2.reg("snowman", "snow_golem");
        EntityNameMappings1_12_2.reg("villager_golem", "iron_golem");
        EntityNameMappings1_12_2.reg("vindication_illager", "vindicator");
        EntityNameMappings1_12_2.reg("xp_bottle", "experience_bottle");
        EntityNameMappings1_12_2.reg("xp_orb", "experience_orb");
    }
}

