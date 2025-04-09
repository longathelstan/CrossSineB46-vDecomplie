/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

public class WorldBorderEmulator
extends StoredObject {
    double x;
    double z;
    double oldDiameter;
    double newDiameter;
    long lerpTime;
    long lerpStartTime;
    boolean init = false;

    public WorldBorderEmulator(UserConnection user) {
        super(user);
    }

    public void init(double x, double z, double oldDiameter, double newDiameter, long lerpTime) {
        this.x = x;
        this.z = z;
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.lerpTime = lerpTime;
        this.init = true;
    }

    public void setCenter(double x, double z) {
        this.x = x;
        this.z = z;
    }

    public void lerpSize(double oldDiameter, double newDiameter, long lerpTime) {
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.lerpTime = lerpTime;
        this.lerpStartTime = System.currentTimeMillis();
    }

    public void setSize(double size) {
        this.oldDiameter = size;
        this.newDiameter = size;
        this.lerpTime = 0L;
    }

    public double getSize() {
        if (this.lerpTime == 0L) {
            return this.newDiameter;
        }
        double percent = (double)(System.currentTimeMillis() - this.lerpStartTime) / (double)this.lerpTime;
        if (percent > 1.0) {
            percent = 1.0;
        } else if (percent < 0.0) {
            percent = 0.0;
        }
        return this.oldDiameter + (this.newDiameter - this.oldDiameter) * percent;
    }

    public double getX() {
        return this.x;
    }

    public double getZ() {
        return this.z;
    }

    public boolean isInit() {
        return this.init;
    }

    public static enum Side {
        NORTH(0, -1),
        EAST(1, 0),
        SOUTH(0, 1),
        WEST(-1, 0);

        public final int modX;
        public final int modZ;

        Side(int modX, int modZ) {
            this.modX = modX;
            this.modZ = modZ;
        }
    }
}

