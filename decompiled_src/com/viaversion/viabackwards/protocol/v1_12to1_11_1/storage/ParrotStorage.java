/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_12to1_11_1.storage;

public class ParrotStorage {
    private boolean tamed = true;
    private boolean sitting = true;

    public boolean isTamed() {
        return this.tamed;
    }

    public void setTamed(boolean tamed) {
        this.tamed = tamed;
    }

    public boolean isSitting() {
        return this.sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }
}

