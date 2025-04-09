/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.Objects;
import java.util.Set;

public final class BlockedProtocolVersionsImpl
implements BlockedProtocolVersions {
    private final Set<ProtocolVersion> singleBlockedVersions;
    private final ProtocolVersion blocksBelow;
    private final ProtocolVersion blocksAbove;

    public BlockedProtocolVersionsImpl(Set<ProtocolVersion> singleBlockedVersions, ProtocolVersion blocksBelow, ProtocolVersion blocksAbove) {
        this.singleBlockedVersions = singleBlockedVersions;
        this.blocksBelow = blocksBelow;
        this.blocksAbove = blocksAbove;
    }

    @Override
    public boolean contains(ProtocolVersion protocolVersion) {
        return this.blocksBelow.isKnown() && protocolVersion.olderThan(this.blocksBelow) || this.blocksAbove.isKnown() && protocolVersion.newerThan(this.blocksAbove) || this.singleBlockedVersions.contains(protocolVersion);
    }

    @Override
    public Set<ProtocolVersion> singleBlockedVersions() {
        return this.singleBlockedVersions;
    }

    @Override
    public ProtocolVersion blocksBelow() {
        return this.blocksBelow;
    }

    @Override
    public ProtocolVersion blocksAbove() {
        return this.blocksAbove;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BlockedProtocolVersionsImpl)) {
            return false;
        }
        BlockedProtocolVersionsImpl blockedProtocolVersionsImpl = (BlockedProtocolVersionsImpl)object;
        return Objects.equals(this.singleBlockedVersions, blockedProtocolVersionsImpl.singleBlockedVersions) && Objects.equals(this.blocksBelow, blockedProtocolVersionsImpl.blocksBelow) && Objects.equals(this.blocksAbove, blockedProtocolVersionsImpl.blocksAbove);
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.singleBlockedVersions)) * 31 + Objects.hashCode(this.blocksBelow)) * 31 + Objects.hashCode(this.blocksAbove);
    }

    public String toString() {
        return String.format("%s[singleBlockedVersions=%s, blocksBelow=%s, blocksAbove=%s]", this.getClass().getSimpleName(), Objects.toString(this.singleBlockedVersions), Objects.toString(this.blocksBelow), Objects.toString(this.blocksAbove));
    }
}

