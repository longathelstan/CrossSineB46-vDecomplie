/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_15_2to1_16.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public final class AttributeMappings1_16 {
    private static final BiMap<String, String> ATTRIBUTE_MAPPINGS = HashBiMap.create();

    public static BiMap<String, String> attributeIdentifierMappings() {
        return ATTRIBUTE_MAPPINGS;
    }

    static {
        ATTRIBUTE_MAPPINGS.put((Object)"generic.maxHealth", (Object)"minecraft:generic.max_health");
        ATTRIBUTE_MAPPINGS.put((Object)"zombie.spawnReinforcements", (Object)"minecraft:zombie.spawn_reinforcements");
        ATTRIBUTE_MAPPINGS.put((Object)"horse.jumpStrength", (Object)"minecraft:horse.jump_strength");
        ATTRIBUTE_MAPPINGS.put((Object)"generic.followRange", (Object)"minecraft:generic.follow_range");
        ATTRIBUTE_MAPPINGS.put((Object)"generic.knockbackResistance", (Object)"minecraft:generic.knockback_resistance");
        ATTRIBUTE_MAPPINGS.put((Object)"generic.movementSpeed", (Object)"minecraft:generic.movement_speed");
        ATTRIBUTE_MAPPINGS.put((Object)"generic.flyingSpeed", (Object)"minecraft:generic.flying_speed");
        ATTRIBUTE_MAPPINGS.put((Object)"generic.attackDamage", (Object)"minecraft:generic.attack_damage");
        ATTRIBUTE_MAPPINGS.put((Object)"generic.attackKnockback", (Object)"minecraft:generic.attack_knockback");
        ATTRIBUTE_MAPPINGS.put((Object)"generic.attackSpeed", (Object)"minecraft:generic.attack_speed");
        ATTRIBUTE_MAPPINGS.put((Object)"generic.armorToughness", (Object)"minecraft:generic.armor_toughness");
    }
}

