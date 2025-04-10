/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class MovementTracker
implements StorableObject {
    private static final long IDLE_PACKET_DELAY = 50L;
    private static final long IDLE_PACKET_LIMIT = 20L;
    private long nextIdlePacket;
    private boolean ground;

    public void incrementIdlePacket() {
        this.nextIdlePacket = Math.max(this.nextIdlePacket + 50L, System.currentTimeMillis() - 1000L);
    }

    public long getNextIdlePacket() {
        return this.nextIdlePacket;
    }

    public boolean isGround() {
        return this.ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }
}

