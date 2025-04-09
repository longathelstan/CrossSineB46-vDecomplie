/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_9_3to1_10.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class ResourcePackTracker
implements StorableObject {
    private String lastHash = "";

    public String getLastHash() {
        return this.lastHash;
    }

    public void setLastHash(String lastHash) {
        this.lastHash = lastHash;
    }
}

