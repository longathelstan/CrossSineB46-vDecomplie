/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class WorldNameTracker
implements StorableObject {
    private String worldName;

    public String getWorldName() {
        return this.worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }
}

