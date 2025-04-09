/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;

public class EntityIdMappings1_13 {
    private static final Int2IntMap ENTITY_TYPES = new Int2IntOpenHashMap(83);

    private static void registerEntity(int type1_12, int type1_13) {
        ENTITY_TYPES.put(type1_12, type1_13);
    }

    public static int getNewId(int type1_12) {
        return ENTITY_TYPES.getOrDefault(type1_12, type1_12);
    }

    public static Int2IntMap getEntityTypes() {
        return ENTITY_TYPES;
    }

    static {
        ENTITY_TYPES.defaultReturnValue(-1);
        EntityIdMappings1_13.registerEntity(1, 32);
        EntityIdMappings1_13.registerEntity(2, 22);
        EntityIdMappings1_13.registerEntity(3, 0);
        EntityIdMappings1_13.registerEntity(4, 15);
        EntityIdMappings1_13.registerEntity(5, 84);
        EntityIdMappings1_13.registerEntity(6, 71);
        EntityIdMappings1_13.registerEntity(7, 74);
        EntityIdMappings1_13.registerEntity(8, 35);
        EntityIdMappings1_13.registerEntity(9, 49);
        EntityIdMappings1_13.registerEntity(10, 2);
        EntityIdMappings1_13.registerEntity(11, 67);
        EntityIdMappings1_13.registerEntity(12, 34);
        EntityIdMappings1_13.registerEntity(13, 65);
        EntityIdMappings1_13.registerEntity(14, 75);
        EntityIdMappings1_13.registerEntity(15, 23);
        EntityIdMappings1_13.registerEntity(16, 77);
        EntityIdMappings1_13.registerEntity(17, 76);
        EntityIdMappings1_13.registerEntity(18, 33);
        EntityIdMappings1_13.registerEntity(19, 85);
        EntityIdMappings1_13.registerEntity(20, 55);
        EntityIdMappings1_13.registerEntity(21, 24);
        EntityIdMappings1_13.registerEntity(22, 25);
        EntityIdMappings1_13.registerEntity(23, 30);
        EntityIdMappings1_13.registerEntity(24, 68);
        EntityIdMappings1_13.registerEntity(25, 60);
        EntityIdMappings1_13.registerEntity(26, 13);
        EntityIdMappings1_13.registerEntity(27, 89);
        EntityIdMappings1_13.registerEntity(28, 63);
        EntityIdMappings1_13.registerEntity(29, 88);
        EntityIdMappings1_13.registerEntity(30, 1);
        EntityIdMappings1_13.registerEntity(31, 11);
        EntityIdMappings1_13.registerEntity(32, 46);
        EntityIdMappings1_13.registerEntity(33, 20);
        EntityIdMappings1_13.registerEntity(34, 21);
        EntityIdMappings1_13.registerEntity(35, 78);
        EntityIdMappings1_13.registerEntity(36, 81);
        EntityIdMappings1_13.registerEntity(37, 31);
        EntityIdMappings1_13.registerEntity(40, 41);
        EntityIdMappings1_13.registerEntity(41, 5);
        EntityIdMappings1_13.registerEntity(42, 39);
        EntityIdMappings1_13.registerEntity(43, 40);
        EntityIdMappings1_13.registerEntity(44, 42);
        EntityIdMappings1_13.registerEntity(45, 45);
        EntityIdMappings1_13.registerEntity(46, 43);
        EntityIdMappings1_13.registerEntity(47, 44);
        EntityIdMappings1_13.registerEntity(50, 10);
        EntityIdMappings1_13.registerEntity(51, 62);
        EntityIdMappings1_13.registerEntity(52, 69);
        EntityIdMappings1_13.registerEntity(53, 27);
        EntityIdMappings1_13.registerEntity(54, 87);
        EntityIdMappings1_13.registerEntity(55, 64);
        EntityIdMappings1_13.registerEntity(56, 26);
        EntityIdMappings1_13.registerEntity(57, 53);
        EntityIdMappings1_13.registerEntity(58, 18);
        EntityIdMappings1_13.registerEntity(59, 6);
        EntityIdMappings1_13.registerEntity(60, 61);
        EntityIdMappings1_13.registerEntity(61, 4);
        EntityIdMappings1_13.registerEntity(62, 38);
        EntityIdMappings1_13.registerEntity(63, 17);
        EntityIdMappings1_13.registerEntity(64, 83);
        EntityIdMappings1_13.registerEntity(65, 3);
        EntityIdMappings1_13.registerEntity(66, 82);
        EntityIdMappings1_13.registerEntity(67, 19);
        EntityIdMappings1_13.registerEntity(68, 28);
        EntityIdMappings1_13.registerEntity(69, 59);
        EntityIdMappings1_13.registerEntity(200, 16);
        EntityIdMappings1_13.registerEntity(90, 51);
        EntityIdMappings1_13.registerEntity(91, 58);
        EntityIdMappings1_13.registerEntity(92, 9);
        EntityIdMappings1_13.registerEntity(93, 7);
        EntityIdMappings1_13.registerEntity(94, 70);
        EntityIdMappings1_13.registerEntity(95, 86);
        EntityIdMappings1_13.registerEntity(96, 47);
        EntityIdMappings1_13.registerEntity(97, 66);
        EntityIdMappings1_13.registerEntity(98, 48);
        EntityIdMappings1_13.registerEntity(99, 80);
        EntityIdMappings1_13.registerEntity(100, 29);
        EntityIdMappings1_13.registerEntity(101, 56);
        EntityIdMappings1_13.registerEntity(102, 54);
        EntityIdMappings1_13.registerEntity(103, 36);
        EntityIdMappings1_13.registerEntity(104, 37);
        EntityIdMappings1_13.registerEntity(105, 50);
        EntityIdMappings1_13.registerEntity(120, 79);
    }
}

