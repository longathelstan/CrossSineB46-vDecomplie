/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

public class PaintingNames1_13 {
    private static final Int2ObjectMap<String> PAINTINGS = new Int2ObjectOpenHashMap<String>(26, 0.99f);

    public static void init() {
        PaintingNames1_13.add("Kebab");
        PaintingNames1_13.add("Aztec");
        PaintingNames1_13.add("Alban");
        PaintingNames1_13.add("Aztec2");
        PaintingNames1_13.add("Bomb");
        PaintingNames1_13.add("Plant");
        PaintingNames1_13.add("Wasteland");
        PaintingNames1_13.add("Pool");
        PaintingNames1_13.add("Courbet");
        PaintingNames1_13.add("Sea");
        PaintingNames1_13.add("Sunset");
        PaintingNames1_13.add("Creebet");
        PaintingNames1_13.add("Wanderer");
        PaintingNames1_13.add("Graham");
        PaintingNames1_13.add("Match");
        PaintingNames1_13.add("Bust");
        PaintingNames1_13.add("Stage");
        PaintingNames1_13.add("Void");
        PaintingNames1_13.add("SkullAndRoses");
        PaintingNames1_13.add("Wither");
        PaintingNames1_13.add("Fighters");
        PaintingNames1_13.add("Pointer");
        PaintingNames1_13.add("Pigscene");
        PaintingNames1_13.add("BurningSkull");
        PaintingNames1_13.add("Skeleton");
        PaintingNames1_13.add("DonkeyKong");
    }

    private static void add(String motive) {
        PAINTINGS.put(PAINTINGS.size(), motive);
    }

    public static String getStringId(int id) {
        return PAINTINGS.getOrDefault(id, "kebab");
    }
}

