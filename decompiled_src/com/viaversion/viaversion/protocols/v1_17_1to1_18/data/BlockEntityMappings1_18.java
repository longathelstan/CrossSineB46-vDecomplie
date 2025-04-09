/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_17_1to1_18.data;

import com.viaversion.viaversion.protocols.v1_17_1to1_18.Protocol1_17_1To1_18;
import java.util.Arrays;

public final class BlockEntityMappings1_18 {
    private static final int[] IDS = new int[14];

    public static int newId(int id) {
        int newId;
        if (id < 0 || id > IDS.length || (newId = IDS[id]) == -1) {
            int n = id;
            Protocol1_17_1To1_18.LOGGER.warning("Received out of bounds block entity id: " + n);
            return -1;
        }
        return newId;
    }

    public static int[] getIds() {
        return IDS;
    }

    static {
        Arrays.fill(IDS, -1);
        BlockEntityMappings1_18.IDS[1] = 8;
        BlockEntityMappings1_18.IDS[2] = 21;
        BlockEntityMappings1_18.IDS[3] = 13;
        BlockEntityMappings1_18.IDS[4] = 14;
        BlockEntityMappings1_18.IDS[5] = 24;
        BlockEntityMappings1_18.IDS[6] = 18;
        BlockEntityMappings1_18.IDS[7] = 19;
        BlockEntityMappings1_18.IDS[8] = 20;
        BlockEntityMappings1_18.IDS[9] = 7;
        BlockEntityMappings1_18.IDS[10] = 22;
        BlockEntityMappings1_18.IDS[11] = 23;
        BlockEntityMappings1_18.IDS[12] = 30;
        BlockEntityMappings1_18.IDS[13] = 31;
    }
}

