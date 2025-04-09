/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19_3to1_19_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.UUID;

public final class ChatSessionStorage
implements StorableObject {
    private final UUID uuid = UUID.randomUUID();

    public UUID uuid() {
        return this.uuid;
    }
}

