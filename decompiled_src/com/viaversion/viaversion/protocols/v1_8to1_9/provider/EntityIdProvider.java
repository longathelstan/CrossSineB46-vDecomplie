/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;

public class EntityIdProvider
implements Provider {
    public int getEntityId(UserConnection user) throws Exception {
        return user.getEntityTracker(Protocol1_8To1_9.class).clientEntityId();
    }
}

