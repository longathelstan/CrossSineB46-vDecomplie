/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class EntityList1_2_4 {
    private static final BiMap<String, Integer> ENTITY_IDS = HashBiMap.create();

    public static int getEntityId(String entityName) {
        return (Integer)ENTITY_IDS.get((Object)entityName);
    }

    public static String getEntityName(int entityId) {
        return (String)ENTITY_IDS.inverse().get((Object)entityId);
    }

    static {
        ENTITY_IDS.put((Object)"Item", (Object)1);
        ENTITY_IDS.put((Object)"XPOrb", (Object)2);
        ENTITY_IDS.put((Object)"Painting", (Object)9);
        ENTITY_IDS.put((Object)"Arrow", (Object)10);
        ENTITY_IDS.put((Object)"Snowball", (Object)11);
        ENTITY_IDS.put((Object)"Fireball", (Object)12);
        ENTITY_IDS.put((Object)"SmallFireball", (Object)13);
        ENTITY_IDS.put((Object)"ThrownEnderpearl", (Object)14);
        ENTITY_IDS.put((Object)"EyeOfEnderSignal", (Object)15);
        ENTITY_IDS.put((Object)"ThrownPotion", (Object)16);
        ENTITY_IDS.put((Object)"ThrownExpBottle", (Object)17);
        ENTITY_IDS.put((Object)"PrimedTnt", (Object)20);
        ENTITY_IDS.put((Object)"FallingSand", (Object)21);
        ENTITY_IDS.put((Object)"Minecart", (Object)40);
        ENTITY_IDS.put((Object)"Boat", (Object)41);
        ENTITY_IDS.put((Object)"Mob", (Object)48);
        ENTITY_IDS.put((Object)"Monster", (Object)49);
        ENTITY_IDS.put((Object)"Creeper", (Object)50);
        ENTITY_IDS.put((Object)"Skeleton", (Object)51);
        ENTITY_IDS.put((Object)"Spider", (Object)52);
        ENTITY_IDS.put((Object)"Giant", (Object)53);
        ENTITY_IDS.put((Object)"Zombie", (Object)54);
        ENTITY_IDS.put((Object)"Slime", (Object)55);
        ENTITY_IDS.put((Object)"Ghast", (Object)56);
        ENTITY_IDS.put((Object)"PigZombie", (Object)57);
        ENTITY_IDS.put((Object)"Enderman", (Object)58);
        ENTITY_IDS.put((Object)"CaveSpider", (Object)59);
        ENTITY_IDS.put((Object)"Silverfish", (Object)60);
        ENTITY_IDS.put((Object)"Blaze", (Object)61);
        ENTITY_IDS.put((Object)"LavaSlime", (Object)62);
        ENTITY_IDS.put((Object)"EnderDragon", (Object)63);
        ENTITY_IDS.put((Object)"Pig", (Object)90);
        ENTITY_IDS.put((Object)"Sheep", (Object)91);
        ENTITY_IDS.put((Object)"Cow", (Object)92);
        ENTITY_IDS.put((Object)"Chicken", (Object)93);
        ENTITY_IDS.put((Object)"Squid", (Object)94);
        ENTITY_IDS.put((Object)"Wolf", (Object)95);
        ENTITY_IDS.put((Object)"MushroomCow", (Object)96);
        ENTITY_IDS.put((Object)"SnowMan", (Object)97);
        ENTITY_IDS.put((Object)"Ozelot", (Object)98);
        ENTITY_IDS.put((Object)"VillagerGolem", (Object)99);
        ENTITY_IDS.put((Object)"Villager", (Object)120);
        ENTITY_IDS.put((Object)"EnderCrystal", (Object)200);
    }
}

