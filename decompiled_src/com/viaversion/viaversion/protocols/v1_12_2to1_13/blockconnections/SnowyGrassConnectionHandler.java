/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionHandler;
import java.util.HashSet;

public class SnowyGrassConnectionHandler
implements ConnectionHandler {
    static final Object2IntMap<GrassBlock> GRASS_BLOCKS = new Object2IntOpenHashMap<GrassBlock>();
    static final IntSet SNOWY_GRASS_BLOCKS = new IntOpenHashSet();

    static ConnectionData.ConnectorInitAction init() {
        HashSet<String> snowyGrassBlocks = new HashSet<String>();
        snowyGrassBlocks.add("minecraft:grass_block");
        snowyGrassBlocks.add("minecraft:podzol");
        snowyGrassBlocks.add("minecraft:mycelium");
        GRASS_BLOCKS.defaultReturnValue(-1);
        SnowyGrassConnectionHandler handler = new SnowyGrassConnectionHandler();
        return blockData -> {
            if (snowyGrassBlocks.contains(blockData.getMinecraftKey())) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
                blockData.set("snowy", "true");
                GRASS_BLOCKS.put(new GrassBlock(blockData.getSavedBlockStateId(), true), blockData.getBlockStateId());
                blockData.set("snowy", "false");
                GRASS_BLOCKS.put(new GrassBlock(blockData.getSavedBlockStateId(), false), blockData.getBlockStateId());
            }
            if (blockData.getMinecraftKey().equals("minecraft:snow") || blockData.getMinecraftKey().equals("minecraft:snow_block")) {
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
                SNOWY_GRASS_BLOCKS.add(blockData.getSavedBlockStateId());
            }
        };
    }

    @Override
    public int connect(UserConnection user, BlockPosition position, int blockState) {
        int blockUpId = this.getBlockData(user, position.getRelative(BlockFace.TOP));
        int newId = GRASS_BLOCKS.getInt(new GrassBlock(blockState, SNOWY_GRASS_BLOCKS.contains(blockUpId)));
        return newId != -1 ? newId : blockState;
    }

    private static final class GrassBlock {
        final int blockStateId;
        final boolean snowy;

        GrassBlock(int blockStateId, boolean snowy) {
            this.blockStateId = blockStateId;
            this.snowy = snowy;
        }

        public int blockStateId() {
            return this.blockStateId;
        }

        public boolean snowy() {
            return this.snowy;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof GrassBlock)) {
                return false;
            }
            GrassBlock grassBlock = (GrassBlock)object;
            return this.blockStateId == grassBlock.blockStateId && this.snowy == grassBlock.snowy;
        }

        public int hashCode() {
            return (0 * 31 + Integer.hashCode(this.blockStateId)) * 31 + Boolean.hashCode(this.snowy);
        }

        public String toString() {
            return String.format("%s[blockStateId=%s, snowy=%s]", this.getClass().getSimpleName(), Integer.toString(this.blockStateId), Boolean.toString(this.snowy));
        }
    }
}

