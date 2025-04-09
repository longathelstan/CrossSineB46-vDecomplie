/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockPosition;

public final class GlobalBlockPosition
extends BlockPosition {
    private final String dimension;

    public GlobalBlockPosition(String dimension, int x, int y, int z) {
        super(x, y, z);
        this.dimension = dimension;
    }

    public String dimension() {
        return this.dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GlobalBlockPosition position = (GlobalBlockPosition)o;
        if (this.x != position.x) {
            return false;
        }
        if (this.y != position.y) {
            return false;
        }
        if (this.z != position.z) {
            return false;
        }
        return this.dimension.equals(position.dimension);
    }

    @Override
    public int hashCode() {
        int result = this.dimension.hashCode();
        result = 31 * result + this.x;
        result = 31 * result + this.y;
        result = 31 * result + this.z;
        return result;
    }

    @Override
    public String toString() {
        int n = this.z;
        int n2 = this.y;
        int n3 = this.x;
        String string = this.dimension;
        return "GlobalBlockPosition{dimension='" + string + "', x=" + n3 + ", y=" + n2 + ", z=" + n + "}";
    }
}

