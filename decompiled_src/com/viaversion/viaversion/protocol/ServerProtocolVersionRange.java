/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import java.util.Objects;
import java.util.SortedSet;

public final class ServerProtocolVersionRange
implements ServerProtocolVersion {
    private final ProtocolVersion lowestSupportedProtocolVersion;
    private final ProtocolVersion highestSupportedProtocolVersion;
    private final SortedSet<ProtocolVersion> supportedProtocolVersions;

    public ServerProtocolVersionRange(ProtocolVersion lowestSupportedProtocolVersion, ProtocolVersion highestSupportedProtocolVersion, SortedSet<ProtocolVersion> supportedProtocolVersions) {
        this.lowestSupportedProtocolVersion = lowestSupportedProtocolVersion;
        this.highestSupportedProtocolVersion = highestSupportedProtocolVersion;
        this.supportedProtocolVersions = supportedProtocolVersions;
    }

    @Override
    public ProtocolVersion lowestSupportedProtocolVersion() {
        return this.lowestSupportedProtocolVersion;
    }

    @Override
    public ProtocolVersion highestSupportedProtocolVersion() {
        return this.highestSupportedProtocolVersion;
    }

    @Override
    public SortedSet<ProtocolVersion> supportedProtocolVersions() {
        return this.supportedProtocolVersions;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ServerProtocolVersionRange)) {
            return false;
        }
        ServerProtocolVersionRange serverProtocolVersionRange = (ServerProtocolVersionRange)object;
        return Objects.equals(this.lowestSupportedProtocolVersion, serverProtocolVersionRange.lowestSupportedProtocolVersion) && Objects.equals(this.highestSupportedProtocolVersion, serverProtocolVersionRange.highestSupportedProtocolVersion) && Objects.equals(this.supportedProtocolVersions, serverProtocolVersionRange.supportedProtocolVersions);
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.lowestSupportedProtocolVersion)) * 31 + Objects.hashCode(this.highestSupportedProtocolVersion)) * 31 + Objects.hashCode(this.supportedProtocolVersions);
    }

    public String toString() {
        return String.format("%s[lowestSupportedProtocolVersion=%s, highestSupportedProtocolVersion=%s, supportedProtocolVersions=%s]", this.getClass().getSimpleName(), Objects.toString(this.lowestSupportedProtocolVersion), Objects.toString(this.highestSupportedProtocolVersion), Objects.toString(this.supportedProtocolVersions));
    }
}

