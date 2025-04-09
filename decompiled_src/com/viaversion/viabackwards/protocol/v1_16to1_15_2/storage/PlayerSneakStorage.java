/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class PlayerSneakStorage
implements StorableObject {
    private boolean sneaking;

    public boolean isSneaking() {
        return this.sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }
}

