/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

public final class Quaternion {
    private final float x;
    private final float y;
    private final float z;
    private final float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    public float w() {
        return this.w;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Quaternion)) {
            return false;
        }
        Quaternion quaternion = (Quaternion)object;
        return Float.compare(this.x, quaternion.x) == 0 && Float.compare(this.y, quaternion.y) == 0 && Float.compare(this.z, quaternion.z) == 0 && Float.compare(this.w, quaternion.w) == 0;
    }

    public int hashCode() {
        return (((0 * 31 + Float.hashCode(this.x)) * 31 + Float.hashCode(this.y)) * 31 + Float.hashCode(this.z)) * 31 + Float.hashCode(this.w);
    }

    public String toString() {
        return String.format("%s[x=%s, y=%s, z=%s, w=%s]", this.getClass().getSimpleName(), Float.toString(this.x), Float.toString(this.y), Float.toString(this.z), Float.toString(this.w));
    }
}

