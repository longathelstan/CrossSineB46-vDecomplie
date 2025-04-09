/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_12_2to1_12_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class KeepAliveTracker
implements StorableObject {
    private long keepAlive = Integer.MAX_VALUE;

    public long getKeepAlive() {
        return this.keepAlive;
    }

    public void setKeepAlive(long keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String toString() {
        long l = this.keepAlive;
        return "KeepAliveTracker{keepAlive=" + l + "}";
    }
}

