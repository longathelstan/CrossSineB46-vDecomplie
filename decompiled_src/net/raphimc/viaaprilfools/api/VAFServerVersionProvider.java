/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.api;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.protocol.version.VersionType;

public class VAFServerVersionProvider
implements VersionProvider {
    private final VersionProvider delegate;

    public VAFServerVersionProvider(VersionProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    public ProtocolVersion getClientProtocol(UserConnection connection) {
        ProtocolVersion version = connection.getProtocolInfo().protocolVersion();
        if (version.getVersionType() == VersionType.SPECIAL) {
            return ProtocolVersion.getProtocol(VersionType.SPECIAL, version.getOriginalVersion());
        }
        return this.delegate.getClientProtocol(connection);
    }

    @Override
    public ProtocolVersion getClosestServerProtocol(UserConnection connection) throws Exception {
        return this.delegate.getClosestServerProtocol(connection);
    }
}

