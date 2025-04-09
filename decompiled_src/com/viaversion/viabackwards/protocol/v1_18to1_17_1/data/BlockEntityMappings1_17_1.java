/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_18to1_17_1.data;

import com.viaversion.viaversion.protocols.v1_17_1to1_18.data.BlockEntityMappings1_18;
import java.util.Arrays;

public final class BlockEntityMappings1_17_1 {
    private static final int[] IDS;

    public static int mappedId(int id) {
        if (id < 0 || id > IDS.length) {
            return -1;
        }
        return IDS[id];
    }

    static {
        int[] ids = BlockEntityMappings1_18.getIds();
        IDS = new int[Arrays.stream(ids).max().getAsInt() + 1];
        Arrays.fill(IDS, -1);
        for (int i = 0; i < ids.length; ++i) {
            int id = ids[i];
            if (id == -1) continue;
            BlockEntityMappings1_17_1.IDS[id] = i;
        }
    }
}

