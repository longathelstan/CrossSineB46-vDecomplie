/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import java.util.EnumMap;
import java.util.Map;

public enum BlockFace {
    NORTH(0, 0, -1, EnumAxis.Z),
    SOUTH(0, 0, 1, EnumAxis.Z),
    EAST(1, 0, 0, EnumAxis.X),
    WEST(-1, 0, 0, EnumAxis.X),
    TOP(0, 1, 0, EnumAxis.Y),
    BOTTOM(0, -1, 0, EnumAxis.Y);

    public static final BlockFace[] HORIZONTAL;
    static final Map<BlockFace, BlockFace> opposites;
    final byte modX;
    final byte modY;
    final byte modZ;
    final EnumAxis axis;

    BlockFace(byte modX, byte modY, byte modZ, EnumAxis axis) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
        this.axis = axis;
    }

    public BlockFace opposite() {
        return opposites.get((Object)this);
    }

    public byte modX() {
        return this.modX;
    }

    public byte modY() {
        return this.modY;
    }

    public byte modZ() {
        return this.modZ;
    }

    public EnumAxis axis() {
        return this.axis;
    }

    static {
        HORIZONTAL = new BlockFace[]{NORTH, SOUTH, EAST, WEST};
        opposites = new EnumMap<BlockFace, BlockFace>(BlockFace.class);
        opposites.put(NORTH, SOUTH);
        opposites.put(SOUTH, NORTH);
        opposites.put(EAST, WEST);
        opposites.put(WEST, EAST);
        opposites.put(TOP, BOTTOM);
        opposites.put(BOTTOM, TOP);
    }

    public static enum EnumAxis {
        X,
        Y,
        Z;

    }
}

