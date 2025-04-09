/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_5to1_21.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public final class OnGroundTracker
implements StorableObject {
    private boolean onGround;

    public boolean onGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}

