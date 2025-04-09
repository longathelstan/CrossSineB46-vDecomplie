/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

public final class Vector {
    private final int blockX;
    private final int blockY;
    private final int blockZ;

    public Vector(int blockX, int blockY, int blockZ) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }

    public int blockX() {
        return this.blockX;
    }

    public int blockY() {
        return this.blockY;
    }

    public int blockZ() {
        return this.blockZ;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Vector)) {
            return false;
        }
        Vector vector = (Vector)object;
        return this.blockX == vector.blockX && this.blockY == vector.blockY && this.blockZ == vector.blockZ;
    }

    public int hashCode() {
        return ((0 * 31 + Integer.hashCode(this.blockX)) * 31 + Integer.hashCode(this.blockY)) * 31 + Integer.hashCode(this.blockZ);
    }

    public String toString() {
        return String.format("%s[blockX=%s, blockY=%s, blockZ=%s]", this.getClass().getSimpleName(), Integer.toString(this.blockX), Integer.toString(this.blockY), Integer.toString(this.blockZ));
    }
}

