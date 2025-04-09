/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16_4to1_16_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class PlayerHandStorage
implements StorableObject {
    private int currentHand;

    public int getCurrentHand() {
        return this.currentHand;
    }

    public void setCurrentHand(int currentHand) {
        this.currentHand = currentHand;
    }
}

