/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionHandler;
import java.util.HashSet;

public class FlowerConnectionHandler
implements ConnectionHandler {
    private static final Int2IntMap FLOWERS = new Int2IntOpenHashMap();

    static ConnectionData.ConnectorInitAction init() {
        HashSet<String> baseFlower = new HashSet<String>();
        baseFlower.add("minecraft:rose_bush");
        baseFlower.add("minecraft:sunflower");
        baseFlower.add("minecraft:peony");
        baseFlower.add("minecraft:tall_grass");
        baseFlower.add("minecraft:large_fern");
        baseFlower.add("minecraft:lilac");
        FlowerConnectionHandler handler = new FlowerConnectionHandler();
        return blockData -> {
            if (baseFlower.contains(blockData.getMinecraftKey())) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
                if (blockData.getValue("half").equals("lower")) {
                    blockData.set("half", "upper");
                    FLOWERS.put(blockData.getSavedBlockStateId(), blockData.getBlockStateId());
                }
            }
        };
    }

    @Override
    public int connect(UserConnection user, BlockPosition position, int blockState) {
        int blockBelowId = this.getBlockData(user, position.getRelative(BlockFace.BOTTOM));
        int connectBelow = FLOWERS.get(blockBelowId);
        if (connectBelow != 0) {
            int blockAboveId = this.getBlockData(user, position.getRelative(BlockFace.TOP));
            if (Via.getConfig().isStemWhenBlockAbove() ? blockAboveId == 0 : !FLOWERS.containsKey(blockAboveId)) {
                return connectBelow;
            }
        }
        return blockState;
    }
}

