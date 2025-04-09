/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_12to1_11_1.data;

public class BlockColors1_11_1 {
    private static final String[] COLORS = new String[16];

    public static String get(int key) {
        return key >= 0 && key < COLORS.length ? COLORS[key] : "Unknown color";
    }

    static {
        BlockColors1_11_1.COLORS[0] = "White";
        BlockColors1_11_1.COLORS[1] = "Orange";
        BlockColors1_11_1.COLORS[2] = "Magenta";
        BlockColors1_11_1.COLORS[3] = "Light Blue";
        BlockColors1_11_1.COLORS[4] = "Yellow";
        BlockColors1_11_1.COLORS[5] = "Lime";
        BlockColors1_11_1.COLORS[6] = "Pink";
        BlockColors1_11_1.COLORS[7] = "Gray";
        BlockColors1_11_1.COLORS[8] = "Light Gray";
        BlockColors1_11_1.COLORS[9] = "Cyan";
        BlockColors1_11_1.COLORS[10] = "Purple";
        BlockColors1_11_1.COLORS[11] = "Blue";
        BlockColors1_11_1.COLORS[12] = "Brown";
        BlockColors1_11_1.COLORS[13] = "Green";
        BlockColors1_11_1.COLORS[14] = "Red";
        BlockColors1_11_1.COLORS[15] = "Black";
    }
}

