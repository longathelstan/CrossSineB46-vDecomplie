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

public class StairConnectionHandler
implements ConnectionHandler {
    static final Int2ObjectMap<StairData> STAIR_DATA_MAP = new Int2ObjectOpenHashMap<StairData>();
    static final Map<Short, Integer> CONNECTED_BLOCKS = new HashMap<Short, Integer>();

    static ConnectionData.ConnectorInitAction init() {
        LinkedList<String> baseStairs = new LinkedList<String>();
        baseStairs.add("minecraft:oak_stairs");
        baseStairs.add("minecraft:cobblestone_stairs");
        baseStairs.add("minecraft:brick_stairs");
        baseStairs.add("minecraft:stone_brick_stairs");
        baseStairs.add("minecraft:nether_brick_stairs");
        baseStairs.add("minecraft:sandstone_stairs");
        baseStairs.add("minecraft:spruce_stairs");
        baseStairs.add("minecraft:birch_stairs");
        baseStairs.add("minecraft:jungle_stairs");
        baseStairs.add("minecraft:quartz_stairs");
        baseStairs.add("minecraft:acacia_stairs");
        baseStairs.add("minecraft:dark_oak_stairs");
        baseStairs.add("minecraft:red_sandstone_stairs");
        baseStairs.add("minecraft:purpur_stairs");
        baseStairs.add("minecraft:prismarine_stairs");
        baseStairs.add("minecraft:prismarine_brick_stairs");
        baseStairs.add("minecraft:dark_prismarine_stairs");
        StairConnectionHandler connectionHandler = new StairConnectionHandler();
        return blockData -> {
            byte shape;
            int type = baseStairs.indexOf(blockData.getMinecraftKey());
            if (type == -1) {
                return;
            }
            if (blockData.getValue("waterlogged").equals("true")) {
                return;
            }
            switch (blockData.getValue("shape")) {
                case "straight": {
                    shape = 0;
                    break;
                }
                case "inner_left": {
                    shape = 1;
                    break;
                }
                case "inner_right": {
                    shape = 2;
                    break;
                }
                case "outer_left": {
                    shape = 3;
                    break;
                }
                case "outer_right": {
                    shape = 4;
                    break;
                }
                default: {
                    return;
                }
            }
            StairData stairData = new StairData(blockData.getValue("half").equals("bottom"), shape, (byte)type, BlockFace.valueOf(blockData.getValue("facing").toUpperCase(Locale.ROOT)));
            STAIR_DATA_MAP.put(blockData.getSavedBlockStateId(), stairData);
            CONNECTED_BLOCKS.put(StairConnectionHandler.getStates(stairData), blockData.getSavedBlockStateId());
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)connectionHandler);
        };
    }

    static short getStates(StairData stairData) {
        short s = 0;
        if (stairData.bottom()) {
            s = (short)(s | 1);
        }
        s = (short)(s | stairData.shape() << 1);
        s = (short)(s | stairData.type() << 4);
        s = (short)(s | stairData.facing().ordinal() << 9);
        return s;
    }

    @Override
    public int connect(UserConnection user, BlockPosition position, int blockState) {
        StairData stairData = (StairData)STAIR_DATA_MAP.get(blockState);
        if (stairData == null) {
            return blockState;
        }
        short s = 0;
        if (stairData.bottom()) {
            s = (short)(s | 1);
        }
        s = (short)(s | this.getShape(user, position, stairData) << 1);
        s = (short)(s | stairData.type() << 4);
        Integer newBlockState = CONNECTED_BLOCKS.get(s = (short)(s | stairData.facing().ordinal() << 9));
        return newBlockState == null ? blockState : newBlockState;
    }

    int getShape(UserConnection user, BlockPosition position, StairData stair) {
        BlockFace facing2;
        BlockFace facing = stair.facing();
        StairData relativeStair = (StairData)STAIR_DATA_MAP.get(this.getBlockData(user, position.getRelative(facing)));
        if (relativeStair != null && relativeStair.bottom() == stair.bottom()) {
            facing2 = relativeStair.facing();
            if (facing.axis() != facing2.axis() && this.checkOpposite(user, stair, position, facing2.opposite())) {
                return facing2 == this.rotateAntiClockwise(facing) ? 3 : 4;
            }
        }
        if ((relativeStair = (StairData)STAIR_DATA_MAP.get(this.getBlockData(user, position.getRelative(facing.opposite())))) != null && relativeStair.bottom() == stair.bottom()) {
            facing2 = relativeStair.facing();
            if (facing.axis() != facing2.axis() && this.checkOpposite(user, stair, position, facing2)) {
                return facing2 == this.rotateAntiClockwise(facing) ? 1 : 2;
            }
        }
        return 0;
    }

    boolean checkOpposite(UserConnection user, StairData stair, BlockPosition position, BlockFace face) {
        StairData relativeStair = (StairData)STAIR_DATA_MAP.get(this.getBlockData(user, position.getRelative(face)));
        return relativeStair == null || relativeStair.facing() != stair.facing() || relativeStair.bottom() != stair.bottom();
    }

    BlockFace rotateAntiClockwise(BlockFace face) {
        BlockFace blockFace;
        switch (face) {
            case NORTH: {
                blockFace = BlockFace.WEST;
                break;
            }
            case SOUTH: {
                blockFace = BlockFace.EAST;
                break;
            }
            case EAST: {
                blockFace = BlockFace.NORTH;
                break;
            }
            case WEST: {
                blockFace = BlockFace.SOUTH;
                break;
            }
            default: {
                blockFace = face;
            }
        }
        return blockFace;
    }

    private static final class StairData {
        final boolean bottom;
        final byte shape;
        final byte type;
        final BlockFace facing;

        StairData(boolean bottom, byte shape, byte type, BlockFace facing) {
            this.bottom = bottom;
            this.shape = shape;
            this.type = type;
            this.facing = facing;
        }

        public boolean bottom() {
            return this.bottom;
        }

        public byte shape() {
            return this.shape;
        }

        public byte type() {
            return this.type;
        }

        public BlockFace facing() {
            return this.facing;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof StairData)) {
                return false;
            }
            StairData stairData = (StairData)object;
            return this.bottom == stairData.bottom && this.shape == stairData.shape && this.type == stairData.type && Objects.equals((Object)this.facing, (Object)stairData.facing);
        }

        public int hashCode() {
            return (((0 * 31 + Boolean.hashCode(this.bottom)) * 31 + Byte.hashCode(this.shape)) * 31 + Byte.hashCode(this.type)) * 31 + Objects.hashCode((Object)this.facing);
        }

        public String toString() {
            return String.format("%s[bottom=%s, shape=%s, type=%s, facing=%s]", this.getClass().getSimpleName(), Boolean.toString(this.bottom), Byte.toString(this.shape), Byte.toString(this.type), Objects.toString((Object)this.facing));
        }
    }
}

