/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import java.util.Arrays;
import java.util.List;

public final class BlockData {
    private static final List<String> CONNECTION_TYPES = Arrays.asList("fence", "netherFence", "pane", "cobbleWall", "redstone", "allFalseIfStairPre1_12");
    private static final int MAGIC_STAIRS_ID = BlockData.connectionTypeId("allFalseIfStairPre1_12");
    private final Int2ObjectMap<boolean[]> connectData = new Int2ObjectArrayMap<boolean[]>();

    public void put(int blockConnectionTypeId, boolean[] booleans) {
        this.connectData.put(blockConnectionTypeId, booleans);
    }

    public boolean connectsTo(int blockConnectionTypeId, BlockFace face, boolean pre1_12AbstractFence) {
        if (pre1_12AbstractFence && this.connectData.containsKey(MAGIC_STAIRS_ID)) {
            return false;
        }
        boolean[] booleans = (boolean[])this.connectData.get(blockConnectionTypeId);
        return booleans != null && booleans[face.ordinal()];
    }

    public static int connectionTypeId(String blockConnection) {
        int connectionTypeId = CONNECTION_TYPES.indexOf(blockConnection);
        boolean bl = connectionTypeId != -1;
        String string = blockConnection;
        Preconditions.checkArgument((boolean)bl, (Object)("Unknown connection type: " + string));
        return connectionTypeId;
    }
}

