/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data;

import java.util.HashMap;

public final class Particles1_8
extends Enum<Particles1_8> {
    public static final /* enum */ Particles1_8 EXPLOSION_NORMAL;
    public static final /* enum */ Particles1_8 EXPLOSION_LARGE;
    public static final /* enum */ Particles1_8 EXPLOSION_HUGE;
    public static final /* enum */ Particles1_8 FIREWORKS_SPARK;
    public static final /* enum */ Particles1_8 WATER_BUBBLE;
    public static final /* enum */ Particles1_8 WATER_SPLASH;
    public static final /* enum */ Particles1_8 WATER_WAKE;
    public static final /* enum */ Particles1_8 SUSPENDED;
    public static final /* enum */ Particles1_8 SUSPENDED_DEPTH;
    public static final /* enum */ Particles1_8 CRIT;
    public static final /* enum */ Particles1_8 CRIT_MAGIC;
    public static final /* enum */ Particles1_8 SMOKE_NORMAL;
    public static final /* enum */ Particles1_8 SMOKE_LARGE;
    public static final /* enum */ Particles1_8 SPELL;
    public static final /* enum */ Particles1_8 SPELL_INSTANT;
    public static final /* enum */ Particles1_8 SPELL_MOB;
    public static final /* enum */ Particles1_8 SPELL_MOB_AMBIENT;
    public static final /* enum */ Particles1_8 SPELL_WITCH;
    public static final /* enum */ Particles1_8 DRIP_WATER;
    public static final /* enum */ Particles1_8 DRIP_LAVA;
    public static final /* enum */ Particles1_8 VILLAGER_ANGRY;
    public static final /* enum */ Particles1_8 VILLAGER_HAPPY;
    public static final /* enum */ Particles1_8 TOWN_AURA;
    public static final /* enum */ Particles1_8 NOTE;
    public static final /* enum */ Particles1_8 PORTAL;
    public static final /* enum */ Particles1_8 ENCHANTMENT_TABLE;
    public static final /* enum */ Particles1_8 FLAME;
    public static final /* enum */ Particles1_8 LAVA;
    public static final /* enum */ Particles1_8 FOOTSTEP;
    public static final /* enum */ Particles1_8 CLOUD;
    public static final /* enum */ Particles1_8 REDSTONE;
    public static final /* enum */ Particles1_8 SNOWBALL;
    public static final /* enum */ Particles1_8 SNOW_SHOVEL;
    public static final /* enum */ Particles1_8 SLIME;
    public static final /* enum */ Particles1_8 HEART;
    public static final /* enum */ Particles1_8 BARRIER;
    public static final /* enum */ Particles1_8 ICON_CRACK;
    public static final /* enum */ Particles1_8 BLOCK_CRACK;
    public static final /* enum */ Particles1_8 BLOCK_DUST;
    public static final /* enum */ Particles1_8 WATER_DROP;
    public static final /* enum */ Particles1_8 ITEM_TAKE;
    public static final /* enum */ Particles1_8 MOB_APPEARANCE;
    public final String name;
    public final int extra;
    private static final HashMap<String, Particles1_8> particleMap;
    private static final /* synthetic */ Particles1_8[] $VALUES;

    public static Particles1_8[] values() {
        return (Particles1_8[])$VALUES.clone();
    }

    public static Particles1_8 valueOf(String name) {
        return Enum.valueOf(Particles1_8.class, name);
    }

    private Particles1_8(String name) {
        this(name, 0);
    }

    private Particles1_8(String name, int extra) {
        this.name = name;
        this.extra = extra;
    }

    public static Particles1_8 find(String part) {
        return particleMap.get(part);
    }

    public static Particles1_8 find(int id) {
        if (id < 0) {
            return null;
        }
        Particles1_8[] values2 = Particles1_8.values();
        return id >= values2.length ? null : values2[id];
    }

    private static /* synthetic */ Particles1_8[] $values() {
        return new Particles1_8[]{EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORKS_SPARK, WATER_BUBBLE, WATER_SPLASH, WATER_WAKE, SUSPENDED, SUSPENDED_DEPTH, CRIT, CRIT_MAGIC, SMOKE_NORMAL, SMOKE_LARGE, SPELL, SPELL_INSTANT, SPELL_MOB, SPELL_MOB_AMBIENT, SPELL_WITCH, DRIP_WATER, DRIP_LAVA, VILLAGER_ANGRY, VILLAGER_HAPPY, TOWN_AURA, NOTE, PORTAL, ENCHANTMENT_TABLE, FLAME, LAVA, FOOTSTEP, CLOUD, REDSTONE, SNOWBALL, SNOW_SHOVEL, SLIME, HEART, BARRIER, ICON_CRACK, BLOCK_CRACK, BLOCK_DUST, WATER_DROP, ITEM_TAKE, MOB_APPEARANCE};
    }

    static {
        Particles1_8[] particles;
        EXPLOSION_NORMAL = new Particles1_8("explode");
        EXPLOSION_LARGE = new Particles1_8("largeexplode");
        EXPLOSION_HUGE = new Particles1_8("hugeexplosion");
        FIREWORKS_SPARK = new Particles1_8("fireworksSpark");
        WATER_BUBBLE = new Particles1_8("bubble");
        WATER_SPLASH = new Particles1_8("splash");
        WATER_WAKE = new Particles1_8("wake");
        SUSPENDED = new Particles1_8("suspended");
        SUSPENDED_DEPTH = new Particles1_8("depthsuspend");
        CRIT = new Particles1_8("crit");
        CRIT_MAGIC = new Particles1_8("magicCrit");
        SMOKE_NORMAL = new Particles1_8("smoke");
        SMOKE_LARGE = new Particles1_8("largesmoke");
        SPELL = new Particles1_8("spell");
        SPELL_INSTANT = new Particles1_8("instantSpell");
        SPELL_MOB = new Particles1_8("mobSpell");
        SPELL_MOB_AMBIENT = new Particles1_8("mobSpellAmbient");
        SPELL_WITCH = new Particles1_8("witchMagic");
        DRIP_WATER = new Particles1_8("dripWater");
        DRIP_LAVA = new Particles1_8("dripLava");
        VILLAGER_ANGRY = new Particles1_8("angryVillager");
        VILLAGER_HAPPY = new Particles1_8("happyVillager");
        TOWN_AURA = new Particles1_8("townaura");
        NOTE = new Particles1_8("note");
        PORTAL = new Particles1_8("portal");
        ENCHANTMENT_TABLE = new Particles1_8("enchantmenttable");
        FLAME = new Particles1_8("flame");
        LAVA = new Particles1_8("lava");
        FOOTSTEP = new Particles1_8("footstep");
        CLOUD = new Particles1_8("cloud");
        REDSTONE = new Particles1_8("reddust");
        SNOWBALL = new Particles1_8("snowballpoof");
        SNOW_SHOVEL = new Particles1_8("snowshovel");
        SLIME = new Particles1_8("slime");
        HEART = new Particles1_8("heart");
        BARRIER = new Particles1_8("barrier");
        ICON_CRACK = new Particles1_8("iconcrack", 2);
        BLOCK_CRACK = new Particles1_8("blockcrack", 1);
        BLOCK_DUST = new Particles1_8("blockdust", 1);
        WATER_DROP = new Particles1_8("droplet");
        ITEM_TAKE = new Particles1_8("take");
        MOB_APPEARANCE = new Particles1_8("mobappearance");
        $VALUES = Particles1_8.$values();
        particleMap = new HashMap();
        for (Particles1_8 particle : particles = Particles1_8.values()) {
            particleMap.put(particle.name, particle);
        }
    }
}

