/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.entities.storage;

public abstract class EntityPositionStorage {
    private double x;
    private double y;
    private double z;

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public void setCoordinates(double x, double y, double z, boolean relative) {
        if (relative) {
            this.x += x;
            this.y += y;
            this.z += z;
        } else {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

