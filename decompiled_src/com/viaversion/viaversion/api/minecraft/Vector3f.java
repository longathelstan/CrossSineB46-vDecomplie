/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

public final class Vector3f {
    private final float x;
    private final float y;
    private final float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Vector3f)) {
            return false;
        }
        Vector3f vector3f = (Vector3f)object;
        return Float.compare(this.x, vector3f.x) == 0 && Float.compare(this.y, vector3f.y) == 0 && Float.compare(this.z, vector3f.z) == 0;
    }

    public int hashCode() {
        return ((0 * 31 + Float.hashCode(this.x)) * 31 + Float.hashCode(this.y)) * 31 + Float.hashCode(this.z);
    }

    public String toString() {
        return String.format("%s[x=%s, y=%s, z=%s]", this.getClass().getSimpleName(), Float.toString(this.x), Float.toString(this.y), Float.toString(this.z));
    }
}

