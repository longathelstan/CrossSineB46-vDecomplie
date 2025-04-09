/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_15_2to1_16.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;

public class PlayerAbilitiesProvider
implements Provider {
    public float getFlyingSpeed(UserConnection connection) {
        return 0.05f;
    }

    public float getWalkingSpeed(UserConnection connection) {
        return 0.1f;
    }
}

