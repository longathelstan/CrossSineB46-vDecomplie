/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_10to1_11.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.util.Pair;

public class PotionColorMappings1_11 {
    private static final Int2ObjectMap<Pair<Integer, Boolean>> POTIONS = new Int2ObjectOpenHashMap<Pair<Integer, Boolean>>(37);

    public static Pair<Integer, Boolean> getNewData(int oldData) {
        return (Pair)POTIONS.get(oldData);
    }

    private static void addRewrite(int oldData, int newData, boolean isInstant) {
        POTIONS.put(oldData, new Pair<Integer, Boolean>(newData, isInstant));
    }

    static {
        PotionColorMappings1_11.addRewrite(0, 3694022, false);
        PotionColorMappings1_11.addRewrite(1, 3694022, false);
        PotionColorMappings1_11.addRewrite(2, 3694022, false);
        PotionColorMappings1_11.addRewrite(3, 3694022, false);
        PotionColorMappings1_11.addRewrite(4, 3694022, false);
        PotionColorMappings1_11.addRewrite(5, 0x1F1FA1, false);
        PotionColorMappings1_11.addRewrite(6, 0x1F1FA1, false);
        PotionColorMappings1_11.addRewrite(7, 8356754, false);
        PotionColorMappings1_11.addRewrite(8, 8356754, false);
        PotionColorMappings1_11.addRewrite(9, 2293580, false);
        PotionColorMappings1_11.addRewrite(10, 2293580, false);
        PotionColorMappings1_11.addRewrite(11, 2293580, false);
        PotionColorMappings1_11.addRewrite(12, 14981690, false);
        PotionColorMappings1_11.addRewrite(13, 14981690, false);
        PotionColorMappings1_11.addRewrite(14, 8171462, false);
        PotionColorMappings1_11.addRewrite(15, 8171462, false);
        PotionColorMappings1_11.addRewrite(16, 8171462, false);
        PotionColorMappings1_11.addRewrite(17, 5926017, false);
        PotionColorMappings1_11.addRewrite(18, 5926017, false);
        PotionColorMappings1_11.addRewrite(19, 3035801, false);
        PotionColorMappings1_11.addRewrite(20, 3035801, false);
        PotionColorMappings1_11.addRewrite(21, 16262179, true);
        PotionColorMappings1_11.addRewrite(22, 16262179, true);
        PotionColorMappings1_11.addRewrite(23, 4393481, true);
        PotionColorMappings1_11.addRewrite(24, 4393481, true);
        PotionColorMappings1_11.addRewrite(25, 5149489, false);
        PotionColorMappings1_11.addRewrite(26, 5149489, false);
        PotionColorMappings1_11.addRewrite(27, 5149489, false);
        PotionColorMappings1_11.addRewrite(28, 13458603, false);
        PotionColorMappings1_11.addRewrite(29, 13458603, false);
        PotionColorMappings1_11.addRewrite(30, 13458603, false);
        PotionColorMappings1_11.addRewrite(31, 9643043, false);
        PotionColorMappings1_11.addRewrite(32, 9643043, false);
        PotionColorMappings1_11.addRewrite(33, 9643043, false);
        PotionColorMappings1_11.addRewrite(34, 0x484D48, false);
        PotionColorMappings1_11.addRewrite(35, 0x484D48, false);
        PotionColorMappings1_11.addRewrite(36, 0x339900, false);
    }
}

