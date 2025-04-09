/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class BlockPlaceDestroyTracker
implements StorableObject {
    private long lastMining;

    public boolean isMining() {
        long time = System.currentTimeMillis() - this.lastMining;
        return time < 75L;
    }

    public void setMining() {
        this.lastMining = System.currentTimeMillis();
    }

    public void updateMining() {
        if (this.isMining()) {
            this.lastMining = System.currentTimeMillis();
        }
    }

    public void setLastMining(long lastMining) {
        this.lastMining = lastMining;
    }
}

