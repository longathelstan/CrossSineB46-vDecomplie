/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19_3to1_19_4.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class PlayerVehicleTracker
implements StorableObject {
    private int vehicleId = -1;

    public int getVehicleId() {
        return this.vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}

