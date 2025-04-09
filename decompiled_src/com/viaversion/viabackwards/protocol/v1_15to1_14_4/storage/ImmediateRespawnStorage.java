/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_15to1_14_4.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class ImmediateRespawnStorage
implements StorableObject {
    private boolean immediateRespawn;

    public boolean isImmediateRespawn() {
        return this.immediateRespawn;
    }

    public void setImmediateRespawn(boolean immediateRespawn) {
        this.immediateRespawn = immediateRespawn;
    }
}

