/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.Objects;

public final class ProtocolPathEntryImpl
implements ProtocolPathEntry {
    private final ProtocolVersion outputProtocolVersion;
    private final Protocol<?, ?, ?, ?> protocol;

    public ProtocolPathEntryImpl(ProtocolVersion outputProtocolVersion, Protocol<?, ?, ?, ?> protocol) {
        this.outputProtocolVersion = outputProtocolVersion;
        this.protocol = protocol;
    }

    @Override
    public ProtocolVersion outputProtocolVersion() {
        return this.outputProtocolVersion;
    }

    @Override
    public Protocol<?, ?, ?, ?> protocol() {
        return this.protocol;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProtocolPathEntryImpl)) {
            return false;
        }
        ProtocolPathEntryImpl protocolPathEntryImpl = (ProtocolPathEntryImpl)object;
        return Objects.equals(this.outputProtocolVersion, protocolPathEntryImpl.outputProtocolVersion) && Objects.equals(this.protocol, protocolPathEntryImpl.protocol);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.outputProtocolVersion)) * 31 + Objects.hashCode(this.protocol);
    }

    public String toString() {
        return String.format("%s[outputProtocolVersion=%s, protocol=%s]", this.getClass().getSimpleName(), Objects.toString(this.outputProtocolVersion), Objects.toString(this.protocol));
    }
}

