/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.data;

import java.util.HashMap;
import java.util.Map;

public class EntityIds1_8 {
    public static final Map<String, Integer> ENTITY_NAME_TO_ID = new HashMap<String, Integer>();
    public static final Map<Integer, String> ENTITY_ID_TO_NAME = new HashMap<Integer, String>();

    private static void register(int id, String name) {
        ENTITY_ID_TO_NAME.put(id, name);
        ENTITY_NAME_TO_ID.put(name, id);
    }

    static {
        EntityIds1_8.register(1, "Item");
        EntityIds1_8.register(2, "XPOrb");
        EntityIds1_8.register(7, "ThrownEgg");
        EntityIds1_8.register(8, "LeashKnot");
        EntityIds1_8.register(9, "Painting");
        EntityIds1_8.register(10, "Arrow");
        EntityIds1_8.register(11, "Snowball");
        EntityIds1_8.register(12, "Fireball");
        EntityIds1_8.register(13, "SmallFireball");
        EntityIds1_8.register(14, "ThrownEnderpearl");
        EntityIds1_8.register(15, "EyeOfEnderSignal");
        EntityIds1_8.register(16, "ThrownPotion");
        EntityIds1_8.register(17, "ThrownExpBottle");
        EntityIds1_8.register(18, "ItemFrame");
        EntityIds1_8.register(19, "WitherSkull");
        EntityIds1_8.register(20, "PrimedTnt");
        EntityIds1_8.register(21, "FallingSand");
        EntityIds1_8.register(22, "FireworksRocketEntity");
        EntityIds1_8.register(30, "ArmorStand");
        EntityIds1_8.register(40, "MinecartCommandBlock");
        EntityIds1_8.register(41, "Boat");
        EntityIds1_8.register(42, "MinecartRideable");
        EntityIds1_8.register(43, "MinecartChest");
        EntityIds1_8.register(44, "MinecartFurnace");
        EntityIds1_8.register(45, "MinecartTNT");
        EntityIds1_8.register(46, "MinecartHopper");
        EntityIds1_8.register(47, "MinecartSpawner");
        EntityIds1_8.register(48, "Mob");
        EntityIds1_8.register(49, "Monster");
        EntityIds1_8.register(50, "Creeper");
        EntityIds1_8.register(51, "Skeleton");
        EntityIds1_8.register(52, "Spider");
        EntityIds1_8.register(53, "Giant");
        EntityIds1_8.register(54, "Zombie");
        EntityIds1_8.register(55, "Slime");
        EntityIds1_8.register(56, "Ghast");
        EntityIds1_8.register(57, "PigZombie");
        EntityIds1_8.register(58, "Enderman");
        EntityIds1_8.register(59, "CaveSpider");
        EntityIds1_8.register(60, "Silverfish");
        EntityIds1_8.register(61, "Blaze");
        EntityIds1_8.register(62, "LavaSlime");
        EntityIds1_8.register(63, "EnderDragon");
        EntityIds1_8.register(64, "WitherBoss");
        EntityIds1_8.register(65, "Bat");
        EntityIds1_8.register(66, "Witch");
        EntityIds1_8.register(67, "Endermite");
        EntityIds1_8.register(68, "Guardian");
        EntityIds1_8.register(90, "Pig");
        EntityIds1_8.register(91, "Sheep");
        EntityIds1_8.register(92, "Cow");
        EntityIds1_8.register(93, "Chicken");
        EntityIds1_8.register(94, "Squid");
        EntityIds1_8.register(95, "Wolf");
        EntityIds1_8.register(96, "MushroomCow");
        EntityIds1_8.register(97, "SnowMan");
        EntityIds1_8.register(98, "Ozelot");
        EntityIds1_8.register(99, "VillagerGolem");
        EntityIds1_8.register(100, "EntityHorse");
        EntityIds1_8.register(101, "Rabbit");
        EntityIds1_8.register(120, "Villager");
        EntityIds1_8.register(200, "EnderCrystal");
    }
}

