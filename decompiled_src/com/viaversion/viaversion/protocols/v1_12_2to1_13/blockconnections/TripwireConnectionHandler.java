/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.WrappedBlockData;
import java.util.Arrays;
import java.util.Locale;

public class TripwireConnectionHandler
implements ConnectionHandler {
    static final Int2ObjectMap<TripwireData> TRIPWIRE_DATA_MAP = new Int2ObjectOpenHashMap<TripwireData>();
    static final Int2ObjectMap<BlockFace> TRIPWIRE_HOOKS = new Int2ObjectArrayMap<BlockFace>();
    static final int[] CONNECTED_BLOCKS = new int[128];

    static ConnectionData.ConnectorInitAction init() {
        Arrays.fill(CONNECTED_BLOCKS, -1);
        TripwireConnectionHandler connectionHandler = new TripwireConnectionHandler();
        return blockData -> {
            if (blockData.getMinecraftKey().equals("minecraft:tripwire_hook")) {
                TRIPWIRE_HOOKS.put(blockData.getSavedBlockStateId(), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
            } else if (blockData.getMinecraftKey().equals("minecraft:tripwire")) {
                TripwireData tripwireData = new TripwireData(blockData.getValue("attached").equals("true"), blockData.getValue("disarmed").equals("true"), blockData.getValue("powered").equals("true"));
                TRIPWIRE_DATA_MAP.put(blockData.getSavedBlockStateId(), tripwireData);
                TripwireConnectionHandler.CONNECTED_BLOCKS[TripwireConnectionHandler.getStates((WrappedBlockData)blockData)] = blockData.getSavedBlockStateId();
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)connectionHandler);
            }
        };
    }

    static byte getStates(WrappedBlockData blockData) {
        byte b = 0;
        if (blockData.getValue("attached").equals("true")) {
            b = (byte)(b | 1);
        }
        if (blockData.getValue("disarmed").equals("true")) {
            b = (byte)(b | 2);
        }
        if (blockData.getValue("powered").equals("true")) {
            b = (byte)(b | 4);
        }
        if (blockData.getValue("east").equals("true")) {
            b = (byte)(b | 8);
        }
        if (blockData.getValue("north").equals("true")) {
            b = (byte)(b | 0x10);
        }
        if (blockData.getValue("south").equals("true")) {
            b = (byte)(b | 0x20);
        }
        if (blockData.getValue("west").equals("true")) {
            b = (byte)(b | 0x40);
        }
        return b;
    }

    @Override
    public int connect(UserConnection user, BlockPosition position, int blockState) {
        int newBlockState;
        TripwireData tripwireData = (TripwireData)TRIPWIRE_DATA_MAP.get(blockState);
        if (tripwireData == null) {
            return blockState;
        }
        byte b = 0;
        if (tripwireData.attached()) {
            b = (byte)(b | 1);
        }
        if (tripwireData.disarmed()) {
            b = (byte)(b | 2);
        }
        if (tripwireData.powered()) {
            b = (byte)(b | 4);
        }
        int east = this.getBlockData(user, position.getRelative(BlockFace.EAST));
        int north = this.getBlockData(user, position.getRelative(BlockFace.NORTH));
        int south = this.getBlockData(user, position.getRelative(BlockFace.SOUTH));
        int west = this.getBlockData(user, position.getRelative(BlockFace.WEST));
        if (TRIPWIRE_DATA_MAP.containsKey(east) || TRIPWIRE_HOOKS.get(east) == BlockFace.WEST) {
            b = (byte)(b | 8);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(north) || TRIPWIRE_HOOKS.get(north) == BlockFace.SOUTH) {
            b = (byte)(b | 0x10);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(south) || TRIPWIRE_HOOKS.get(south) == BlockFace.NORTH) {
            b = (byte)(b | 0x20);
        }
        if (TRIPWIRE_DATA_MAP.containsKey(west) || TRIPWIRE_HOOKS.get(west) == BlockFace.EAST) {
            b = (byte)(b | 0x40);
        }
        return (newBlockState = CONNECTED_BLOCKS[b]) == -1 ? blockState : newBlockState;
    }

    private static final class TripwireData {
        final boolean attached;
        final boolean disarmed;
        final boolean powered;

        TripwireData(boolean attached, boolean disarmed, boolean powered) {
            this.attached = attached;
            this.disarmed = disarmed;
            this.powered = powered;
        }

        public boolean attached() {
            return this.attached;
        }

        public boolean disarmed() {
            return this.disarmed;
        }

        public boolean powered() {
            return this.powered;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof TripwireData)) {
                return false;
            }
            TripwireData tripwireData = (TripwireData)object;
            return this.attached == tripwireData.attached && this.disarmed == tripwireData.disarmed && this.powered == tripwireData.powered;
        }

        public int hashCode() {
            return ((0 * 31 + Boolean.hashCode(this.attached)) * 31 + Boolean.hashCode(this.disarmed)) * 31 + Boolean.hashCode(this.powered);
        }

        public String toString() {
            return String.format("%s[attached=%s, disarmed=%s, powered=%s]", this.getClass().getSimpleName(), Boolean.toString(this.attached), Boolean.toString(this.disarmed), Boolean.toString(this.powered));
        }
    }
}

