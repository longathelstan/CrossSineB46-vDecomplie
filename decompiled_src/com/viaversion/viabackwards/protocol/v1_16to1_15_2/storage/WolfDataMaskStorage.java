/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage;

public final class WolfDataMaskStorage {
    private byte tameableMask;

    public WolfDataMaskStorage(byte tameableMask) {
        this.tameableMask = tameableMask;
    }

    public void setTameableMask(byte tameableMask) {
        this.tameableMask = tameableMask;
    }

    public byte tameableMask() {
        return this.tameableMask;
    }
}

