/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.data;

import java.util.HashMap;

public enum Particle1_7_6 {
    EXPLOSION_NORMAL("explode"),
    EXPLOSION_LARGE("largeexplode"),
    EXPLOSION_HUGE("hugeexplosion"),
    FIREWORKS_SPARK("fireworksSpark"),
    WATER_BUBBLE("bubble"),
    WATER_SPLASH("splash"),
    WATER_WAKE("wake"),
    SUSPENDED("suspended"),
    SUSPENDED_DEPTH("depthsuspend"),
    CRIT("crit"),
    CRIT_MAGIC("magicCrit"),
    SMOKE_NORMAL("smoke"),
    SMOKE_LARGE("largesmoke"),
    SPELL("spell"),
    SPELL_INSTANT("instantSpell"),
    SPELL_MOB("mobSpell"),
    SPELL_MOB_AMBIENT("mobSpellAmbient"),
    SPELL_WITCH("witchMagic"),
    DRIP_WATER("dripWater"),
    DRIP_LAVA("dripLava"),
    VILLAGER_ANGRY("angryVillager"),
    VILLAGER_HAPPY("happyVillager"),
    TOWN_AURA("townaura"),
    NOTE("note"),
    PORTAL("portal"),
    ENCHANTMENT_TABLE("enchantmenttable"),
    FLAME("flame"),
    LAVA("lava"),
    FOOTSTEP("footstep"),
    CLOUD("cloud"),
    REDSTONE("reddust"),
    SNOWBALL("snowballpoof"),
    SNOW_SHOVEL("snowshovel"),
    SLIME("slime"),
    HEART("heart"),
    BARRIER("barrier"),
    ICON_CRACK("iconcrack", 2),
    BLOCK_CRACK("blockcrack", 1),
    BLOCK_DUST("blockdust", 1),
    WATER_DROP("droplet"),
    ITEM_TAKE("take"),
    MOB_APPEARANCE("mobappearance");

    public final String name;
    public final int extra;
    private static final HashMap<String, Particle1_7_6> particleMap;

    private Particle1_7_6(String name) {
        this(name, 0);
    }

    private Particle1_7_6(String name, int extra) {
        this.name = name;
        this.extra = extra;
    }

    public static Particle1_7_6 find(String part) {
        return particleMap.get(part);
    }

    static {
        particleMap = new HashMap();
        for (Particle1_7_6 particle : Particle1_7_6.values()) {
            particleMap.put(particle.name, particle);
        }
    }
}

