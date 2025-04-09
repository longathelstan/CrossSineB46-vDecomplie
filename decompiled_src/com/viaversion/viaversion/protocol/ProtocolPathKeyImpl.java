/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.ProtocolPathKey;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.Objects;

public final class ProtocolPathKeyImpl
implements ProtocolPathKey {
    private final ProtocolVersion clientProtocolVersion;
    private final ProtocolVersion serverProtocolVersion;

    public ProtocolPathKeyImpl(ProtocolVersion clientProtocolVersion, ProtocolVersion serverProtocolVersion) {
        this.clientProtocolVersion = clientProtocolVersion;
        this.serverProtocolVersion = serverProtocolVersion;
    }

    @Override
    public ProtocolVersion clientProtocolVersion() {
        return this.clientProtocolVersion;
    }

    @Override
    public ProtocolVersion serverProtocolVersion() {
        return this.serverProtocolVersion;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProtocolPathKeyImpl)) {
            return false;
        }
        ProtocolPathKeyImpl protocolPathKeyImpl = (ProtocolPathKeyImpl)object;
        return Objects.equals(this.clientProtocolVersion, protocolPathKeyImpl.clientProtocolVersion) && Objects.equals(this.serverProtocolVersion, protocolPathKeyImpl.serverProtocolVersion);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.clientProtocolVersion)) * 31 + Objects.hashCode(this.serverProtocolVersion);
    }

    public String toString() {
        return String.format("%s[clientProtocolVersion=%s, serverProtocolVersion=%s]", this.getClass().getSimpleName(), Objects.toString(this.clientProtocolVersion), Objects.toString(this.serverProtocolVersion));
    }
}

