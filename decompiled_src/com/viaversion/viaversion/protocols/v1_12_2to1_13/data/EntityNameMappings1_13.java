/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityNameMappings1_13 {
    private static final Map<String, String> entityNames = new HashMap<String, String>();

    private static void reg(String past, String future) {
        entityNames.put(Key.namespaced(past), Key.namespaced(future));
    }

    public static String rewrite(String entName) {
        String entityName = entityNames.get(entName);
        if (entityName != null) {
            return entityName;
        }
        String string = entityName = entityNames.get(Key.namespaced(entName));
        return string != null ? string : Objects.requireNonNull(entName, "defaultObj");
    }

    static {
        EntityNameMappings1_13.reg("commandblock_minecart", "command_block_minecart");
        EntityNameMappings1_13.reg("ender_crystal", "end_crystal");
        EntityNameMappings1_13.reg("evocation_fangs", "evoker_fangs");
        EntityNameMappings1_13.reg("evocation_illager", "evoker");
        EntityNameMappings1_13.reg("eye_of_ender_signal", "eye_of_ender");
        EntityNameMappings1_13.reg("fireworks_rocket", "firework_rocket");
        EntityNameMappings1_13.reg("illusion_illager", "illusioner");
        EntityNameMappings1_13.reg("snowman", "snow_golem");
        EntityNameMappings1_13.reg("villager_golem", "iron_golem");
        EntityNameMappings1_13.reg("vindication_illager", "vindicator");
        EntityNameMappings1_13.reg("xp_bottle", "experience_bottle");
        EntityNameMappings1_13.reg("xp_orb", "experience_orb");
    }
}

