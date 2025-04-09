/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14to1_13_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class DifficultyStorage
implements StorableObject {
    private byte difficulty;

    public byte getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(byte difficulty) {
        this.difficulty = difficulty;
    }
}

