/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionHandler;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DoorConnectionHandler
implements ConnectionHandler {
    static final Int2ObjectMap<DoorData> DOOR_DATA_MAP = new Int2ObjectOpenHashMap<DoorData>();
    static final Map<Short, Integer> CONNECTED_STATES = new HashMap<Short, Integer>();

    static ConnectionData.ConnectorInitAction init() {
        LinkedList<String> baseDoors = new LinkedList<String>();
        baseDoors.add("minecraft:oak_door");
        baseDoors.add("minecraft:birch_door");
        baseDoors.add("minecraft:jungle_door");
        baseDoors.add("minecraft:dark_oak_door");
        baseDoors.add("minecraft:acacia_door");
        baseDoors.add("minecraft:spruce_door");
        baseDoors.add("minecraft:iron_door");
        DoorConnectionHandler connectionHandler = new DoorConnectionHandler();
        return blockData -> {
            int type = baseDoors.indexOf(blockData.getMinecraftKey());
            if (type == -1) {
                return;
            }
            int id = blockData.getSavedBlockStateId();
            DoorData doorData = new DoorData(blockData.getValue("half").equals("lower"), blockData.getValue("hinge").equals("right"), blockData.getValue("powered").equals("true"), blockData.getValue("open").equals("true"), BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)), type);
            DOOR_DATA_MAP.put(id, doorData);
            CONNECTED_STATES.put(DoorConnectionHandler.getStates(doorData), id);
            ConnectionData.connectionHandlerMap.put(id, (ConnectionHandler)connectionHandler);
        };
    }

    static short getStates(DoorData doorData) {
        short s = 0;
        if (doorData.lower()) {
            s = (short)(s | 1);
        }
        if (doorData.open()) {
            s = (short)(s | 2);
        }
        if (doorData.powered()) {
            s = (short)(s | 4);
        }
        if (doorData.rightHinge()) {
            s = (short)(s | 8);
        }
        s = (short)(s | doorData.facing().ordinal() << 4);
        s = (short)(s | (doorData.type() & 7) << 6);
        return s;
    }

    @Override
    public int connect(UserConnection user, BlockPosition position, int blockState) {
        DoorData doorData = (DoorData)DOOR_DATA_MAP.get(blockState);
        if (doorData == null) {
            return blockState;
        }
        short s = 0;
        s = (short)(s | (doorData.type() & 7) << 6);
        if (doorData.lower()) {
            DoorData upperHalf = (DoorData)DOOR_DATA_MAP.get(this.getBlockData(user, position.getRelative(BlockFace.TOP)));
            if (upperHalf == null) {
                return blockState;
            }
            s = (short)(s | 1);
            if (doorData.open()) {
                s = (short)(s | 2);
            }
            if (upperHalf.powered()) {
                s = (short)(s | 4);
            }
            if (upperHalf.rightHinge()) {
                s = (short)(s | 8);
            }
            s = (short)(s | doorData.facing().ordinal() << 4);
        } else {
            DoorData lowerHalf = (DoorData)DOOR_DATA_MAP.get(this.getBlockData(user, position.getRelative(BlockFace.BOTTOM)));
            if (lowerHalf == null) {
                return blockState;
            }
            if (lowerHalf.open()) {
                s = (short)(s | 2);
            }
            if (doorData.powered()) {
                s = (short)(s | 4);
            }
            if (doorData.rightHinge()) {
                s = (short)(s | 8);
            }
            s = (short)(s | lowerHalf.facing().ordinal() << 4);
        }
        Integer newBlockState = CONNECTED_STATES.get(s);
        return newBlockState == null ? blockState : newBlockState;
    }

    private static final class DoorData {
        final boolean lower;
        final boolean rightHinge;
        final boolean powered;
        final boolean open;
        final BlockFace facing;
        final int type;

        DoorData(boolean lower, boolean rightHinge, boolean powered, boolean open, BlockFace facing, int type) {
            this.lower = lower;
            this.rightHinge = rightHinge;
            this.powered = powered;
            this.open = open;
            this.facing = facing;
            this.type = type;
        }

        public boolean lower() {
            return this.lower;
        }

        public boolean rightHinge() {
            return this.rightHinge;
        }

        public boolean powered() {
            return this.powered;
        }

        public boolean open() {
            return this.open;
        }

        public BlockFace facing() {
            return this.facing;
        }

        public int type() {
            return this.type;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof DoorData)) {
                return false;
            }
            DoorData doorData = (DoorData)object;
            return this.lower == doorData.lower && this.rightHinge == doorData.rightHinge && this.powered == doorData.powered && this.open == doorData.open && Objects.equals((Object)this.facing, (Object)doorData.facing) && this.type == doorData.type;
        }

        public int hashCode() {
            return (((((0 * 31 + Boolean.hashCode(this.lower)) * 31 + Boolean.hashCode(this.rightHinge)) * 31 + Boolean.hashCode(this.powered)) * 31 + Boolean.hashCode(this.open)) * 31 + Objects.hashCode((Object)this.facing)) * 31 + Integer.hashCode(this.type);
        }

        public String toString() {
            return String.format("%s[lower=%s, rightHinge=%s, powered=%s, open=%s, facing=%s, type=%s]", this.getClass().getSimpleName(), Boolean.toString(this.lower), Boolean.toString(this.rightHinge), Boolean.toString(this.powered), Boolean.toString(this.open), Objects.toString((Object)this.facing), Integer.toString(this.type));
        }
    }
}

