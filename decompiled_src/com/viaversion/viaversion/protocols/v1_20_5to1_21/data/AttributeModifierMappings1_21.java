/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_5to1_21.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.viaversion.viaversion.util.Key;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class AttributeModifierMappings1_21 {
    private static final Map<UUID, String> ATTRIBUTE_MODIFIER_IDS = new HashMap<UUID, String>();
    private static final Map<String, UUID> ATTRIBUTE_MODIFIER_INVERSE_IDS = new HashMap<String, UUID>();
    private static final BiMap<String, String> ATTRIBUTE_MODIFIER_NAMES;

    public static @Nullable String uuidToId(UUID uuid) {
        return ATTRIBUTE_MODIFIER_IDS.get(uuid);
    }

    public static @Nullable UUID idToUuid(String id) {
        return ATTRIBUTE_MODIFIER_INVERSE_IDS.get(Key.stripMinecraftNamespace(id));
    }

    public static @Nullable String nameToId(String name) {
        return (String)ATTRIBUTE_MODIFIER_NAMES.get((Object)name);
    }

    public static @Nullable String idToName(String id) {
        return (String)ATTRIBUTE_MODIFIER_NAMES.inverse().get((Object)id);
    }

    private static void add(long msb, long lsb, String id) {
        UUID uuid = new UUID(msb, lsb);
        ATTRIBUTE_MODIFIER_IDS.put(uuid, id);
        ATTRIBUTE_MODIFIER_INVERSE_IDS.put(id, uuid);
    }

    static {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Zombie reinforcement caller charge", "reinforcement_caller_charge");
        hashMap.put("Zombie reinforcement callee charge", "reinforcement_callee_charge");
        hashMap.put("Leader zombie bonus", "leader_zombie_bonus");
        hashMap.put("Random zombie-spawn bonus", "zombie_random_spawn_bonus");
        hashMap.put("Random spawn bonus", "random_spawn_bonus");
        ATTRIBUTE_MODIFIER_NAMES = HashBiMap.create(Collections.unmodifiableMap(hashMap));
        AttributeModifierMappings1_21.add(-4483571535397864886L, -5989644940537681742L, "armor.body");
        AttributeModifierMappings1_21.add(8144722948526719024L, -7778190119041365872L, "effect.slowness");
        AttributeModifierMappings1_21.add(6967552254039378640L, -9116175396973475259L, "enchantment.aqua_affinity");
        AttributeModifierMappings1_21.add(5279725409867744698L, -5150363631200102632L, "attacking");
        AttributeModifierMappings1_21.add(148071211714102867L, -7685811609035173472L, "attacking");
        AttributeModifierMappings1_21.add(6196088217904236654L, -7493791321850887290L, "effect.minining_fatigue");
        AttributeModifierMappings1_21.add(-5084161844451524480L, -8859020046251006329L, "enchantment.soul_speed");
        AttributeModifierMappings1_21.add(-7907339078496465106L, -8112074600724210224L, "enchantment.swift_sneak");
        AttributeModifierMappings1_21.add(6688265815086220243L, -6545541163342161890L, "drinking");
        AttributeModifierMappings1_21.add(8315164243412860989L, -6631520853640075966L, "creative_mode_block_range");
        AttributeModifierMappings1_21.add(4389663563256579765L, -4827163546944004714L, "enchantment.efficiency");
        AttributeModifierMappings1_21.add(6732612758648800940L, -5145707699103688244L, "effect.health_boost");
        AttributeModifierMappings1_21.add(9079981369298536661L, -6728494925450710401L, "covered");
        AttributeModifierMappings1_21.add(-1521481167610687786L, -8630419745734927691L, "effect.absorption");
        AttributeModifierMappings1_21.add(-7473408062188862076L, -5872005994337939597L, "creative_mode_entity_range");
        AttributeModifierMappings1_21.add(-3721396875562958315L, -5317020504214661337L, "effect.unluck");
        AttributeModifierMappings1_21.add(-2861585646493481178L, -6113244764726669811L, "armor.leggings");
        AttributeModifierMappings1_21.add(6718535547217657911L, -5386630269401489641L, "enchantment.sweeping_edge");
        AttributeModifierMappings1_21.add(-7949229004988660584L, -7828611303000832459L, "effect.speed");
        AttributeModifierMappings1_21.add(-8650171790042118250L, -5749650997644763080L, "enchantment.soul_speed");
        AttributeModifierMappings1_21.add(551224288813600377L, -8734740027710371860L, "enchantment.respiration");
        AttributeModifierMappings1_21.add(-7046399332347654691L, -6723081531683397260L, "suffocating");
        AttributeModifierMappings1_21.add(7361814797886573596L, -8641397326606817395L, "sprinting");
        AttributeModifierMappings1_21.add(-6972338111383059132L, -8978659762232839026L, "armor.chestplate");
        AttributeModifierMappings1_21.add(-5371971015925809039L, -6062243582569928137L, "enchantment.fire_protection");
        AttributeModifierMappings1_21.add(7245570952092733273L, -8449101711440750679L, "effect.strength");
        AttributeModifierMappings1_21.add(-422425648963762075L, -5756800103171642205L, "base_attack_speed");
        AttributeModifierMappings1_21.add(-4607081316629330256L, -7008565754814018370L, "effect.jump_boost");
        AttributeModifierMappings1_21.add(271280981090454338L, -8746077033958322898L, "effect.luck");
        AttributeModifierMappings1_21.add(2211131075215181206L, -5513857709499300658L, "powder_snow");
        AttributeModifierMappings1_21.add(-8908768238899017377L, -8313820693701227669L, "armor.boots");
        AttributeModifierMappings1_21.add(-5797418877589107702L, -6181652684028920077L, "effect.haste");
        AttributeModifierMappings1_21.add(3086076556416732775L, -5150312587563650736L, "armor.helmet");
        AttributeModifierMappings1_21.add(-5082757096938257406L, -4891139119377885130L, "baby");
        AttributeModifierMappings1_21.add(2478452629826324956L, -7247530463494186011L, "effect.weakness");
        AttributeModifierMappings1_21.add(4659420831966187055L, -5191473055587376048L, "enchantment.blast_protection");
        AttributeModifierMappings1_21.add(7301951777949303281L, -6753860660653972126L, "evil");
        AttributeModifierMappings1_21.add(8533189226688352746L, -8254757081029716377L, "baby");
        AttributeModifierMappings1_21.add(1286946037536540352L, -5768092872487507967L, "enchantment.depth_strider");
        AttributeModifierMappings1_21.add(-3801225194067177672L, -6586624321849018929L, "base_attack_damage");
    }
}

