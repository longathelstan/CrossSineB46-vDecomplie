/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;

public class BlockPosition {
    protected final int x;
    protected final int y;
    protected final int z;

    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPosition getRelative(BlockFace face) {
        return new BlockPosition(this.x + face.modX(), this.y + face.modY(), this.z + face.modZ());
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int z() {
        return this.z;
    }

    public GlobalBlockPosition withDimension(String dimension) {
        return new GlobalBlockPosition(dimension, this.x, this.y, this.z);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        BlockPosition position = (BlockPosition)o;
        if (this.x != position.x) {
            return false;
        }
        if (this.y != position.y) {
            return false;
        }
        return this.z == position.z;
    }

    public int hashCode() {
        int result = this.x;
        result = 31 * result + this.y;
        result = 31 * result + this.z;
        return result;
    }

    public String toString() {
        int n = this.z;
        int n2 = this.y;
        int n3 = this.x;
        return "BlockPosition{x=" + n3 + ", y=" + n2 + ", z=" + n + "}";
    }
}

