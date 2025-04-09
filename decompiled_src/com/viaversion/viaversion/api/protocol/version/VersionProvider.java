/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

@FunctionalInterface
public interface VersionProvider
extends Provider {
    default public ProtocolVersion getClientProtocol(UserConnection connection) {
        return null;
    }

    default public ProtocolVersion getServerProtocol(UserConnection connection) {
        try {
            return this.getClosestServerProtocol(connection);
        }
        catch (Exception e) {
            return null;
        }
    }

    public ProtocolVersion getClosestServerProtocol(UserConnection var1) throws Exception;
}

