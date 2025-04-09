/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import java.util.HashMap;
import java.util.Map;

public class PotionIdMappings1_9 {
    public static final Map<String, Integer> POTION_NAME_TO_ID = new HashMap<String, Integer>();
    public static final Map<Integer, String> POTION_ID_TO_NAME = new HashMap<Integer, String>();
    public static final Int2IntMap POTION_INDEX = new Int2IntOpenHashMap(36);

    public static String potionNameFromDamage(short damage) {
        String id;
        String cached = POTION_ID_TO_NAME.get(damage);
        if (cached != null) {
            return cached;
        }
        if (damage == 0) {
            return "water";
        }
        int effect = damage & 0xF;
        int name = damage & 0x3F;
        boolean enhanced = (damage & 0x20) > 0;
        boolean extended = (damage & 0x40) > 0;
        boolean canEnhance = true;
        boolean canExtend = true;
        block0 : switch (effect) {
            case 1: {
                id = "regeneration";
                break;
            }
            case 2: {
                id = "swiftness";
                break;
            }
            case 3: {
                id = "fire_resistance";
                canEnhance = false;
                break;
            }
            case 4: {
                id = "poison";
                break;
            }
            case 5: {
                id = "healing";
                canExtend = false;
                break;
            }
            case 6: {
                id = "night_vision";
                canEnhance = false;
                break;
            }
            case 8: {
                id = "weakness";
                canEnhance = false;
                break;
            }
            case 9: {
                id = "strength";
                break;
            }
            case 10: {
                id = "slowness";
                canEnhance = false;
                break;
            }
            case 11: {
                id = "leaping";
                break;
            }
            case 12: {
                id = "harming";
                canExtend = false;
                break;
            }
            case 13: {
                id = "water_breathing";
                canEnhance = false;
                break;
            }
            case 14: {
                id = "invisibility";
                canEnhance = false;
                break;
            }
            default: {
                canEnhance = false;
                canExtend = false;
                switch (name) {
                    case 0: {
                        String string = "mundane";
                        break block0;
                    }
                    case 16: {
                        String string = "awkward";
                        break block0;
                    }
                    case 32: {
                        String string = "thick";
                        break block0;
                    }
                    default: {
                        String string = id = "empty";
                    }
                }
            }
        }
        if (effect > 0) {
            if (canEnhance && enhanced) {
                String string = id;
                id = "strong_" + string;
            } else if (canExtend && extended) {
                String string = id;
                id = "long_" + string;
            }
        }
        return id;
    }

    public static int getNewPotionID(int oldID) {
        int index2;
        if (oldID >= 16384) {
            oldID -= 8192;
        }
        if ((index2 = POTION_INDEX.get(oldID)) != -1) {
            return index2;
        }
        index2 = POTION_INDEX.get(oldID = POTION_NAME_TO_ID.get(PotionIdMappings1_9.potionNameFromDamage((short)oldID)).intValue());
        return index2 != -1 ? index2 : 0;
    }

    private static void register(int id, String name) {
        POTION_INDEX.put(id, POTION_ID_TO_NAME.size());
        POTION_ID_TO_NAME.put(id, name);
        POTION_NAME_TO_ID.put(name, id);
    }

    static {
        PotionIdMappings1_9.register(-1, "empty");
        PotionIdMappings1_9.register(0, "water");
        PotionIdMappings1_9.register(64, "mundane");
        PotionIdMappings1_9.register(32, "thick");
        PotionIdMappings1_9.register(16, "awkward");
        PotionIdMappings1_9.register(8198, "night_vision");
        PotionIdMappings1_9.register(8262, "long_night_vision");
        PotionIdMappings1_9.register(8206, "invisibility");
        PotionIdMappings1_9.register(8270, "long_invisibility");
        PotionIdMappings1_9.register(8203, "leaping");
        PotionIdMappings1_9.register(8267, "long_leaping");
        PotionIdMappings1_9.register(8235, "strong_leaping");
        PotionIdMappings1_9.register(8195, "fire_resistance");
        PotionIdMappings1_9.register(8259, "long_fire_resistance");
        PotionIdMappings1_9.register(8194, "swiftness");
        PotionIdMappings1_9.register(8258, "long_swiftness");
        PotionIdMappings1_9.register(8226, "strong_swiftness");
        PotionIdMappings1_9.register(8202, "slowness");
        PotionIdMappings1_9.register(8266, "long_slowness");
        PotionIdMappings1_9.register(8205, "water_breathing");
        PotionIdMappings1_9.register(8269, "long_water_breathing");
        PotionIdMappings1_9.register(8261, "healing");
        PotionIdMappings1_9.register(8229, "strong_healing");
        PotionIdMappings1_9.register(8204, "harming");
        PotionIdMappings1_9.register(8236, "strong_harming");
        PotionIdMappings1_9.register(8196, "poison");
        PotionIdMappings1_9.register(8260, "long_poison");
        PotionIdMappings1_9.register(8228, "strong_poison");
        PotionIdMappings1_9.register(8193, "regeneration");
        PotionIdMappings1_9.register(8257, "long_regeneration");
        PotionIdMappings1_9.register(8225, "strong_regeneration");
        PotionIdMappings1_9.register(8201, "strength");
        PotionIdMappings1_9.register(8265, "long_strength");
        PotionIdMappings1_9.register(8233, "strong_strength");
        PotionIdMappings1_9.register(8200, "weakness");
        PotionIdMappings1_9.register(8264, "long_weakness");
    }
}

