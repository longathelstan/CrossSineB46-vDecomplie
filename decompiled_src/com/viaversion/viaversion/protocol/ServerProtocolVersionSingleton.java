/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.Objects;
import java.util.SortedSet;

public final class ServerProtocolVersionSingleton
implements ServerProtocolVersion {
    private final ProtocolVersion protocolVersion;

    public ServerProtocolVersionSingleton(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public ProtocolVersion lowestSupportedProtocolVersion() {
        return this.protocolVersion;
    }

    @Override
    public ProtocolVersion highestSupportedProtocolVersion() {
        return this.protocolVersion;
    }

    @Override
    public SortedSet<ProtocolVersion> supportedProtocolVersions() {
        ObjectLinkedOpenHashSet<ProtocolVersion> set = new ObjectLinkedOpenHashSet<ProtocolVersion>();
        set.add(this.protocolVersion);
        return set;
    }

    public ProtocolVersion protocolVersion() {
        return this.protocolVersion;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ServerProtocolVersionSingleton)) {
            return false;
        }
        ServerProtocolVersionSingleton serverProtocolVersionSingleton = (ServerProtocolVersionSingleton)object;
        return Objects.equals(this.protocolVersion, serverProtocolVersionSingleton.protocolVersion);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.protocolVersion);
    }

    public String toString() {
        return String.format("%s[protocolVersion=%s]", this.getClass().getSimpleName(), Objects.toString(this.protocolVersion));
    }
}

