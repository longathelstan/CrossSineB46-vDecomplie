/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

public class CompressionStatusTracker
extends StoredObject {
    public boolean removeCompression = false;

    public CompressionStatusTracker(UserConnection user) {
        super(user);
    }
}

