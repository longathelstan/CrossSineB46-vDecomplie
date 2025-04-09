/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import java.util.SortedSet;

public interface ServerProtocolVersion {
    public ProtocolVersion lowestSupportedProtocolVersion();

    public ProtocolVersion highestSupportedProtocolVersion();

    public SortedSet<ProtocolVersion> supportedProtocolVersions();

    default public boolean isKnown() {
        return this.lowestSupportedProtocolVersion().isKnown() && this.highestSupportedProtocolVersion().isKnown();
    }

    @Deprecated
    default public int lowestSupportedVersion() {
        return this.lowestSupportedProtocolVersion().getVersion();
    }

    @Deprecated
    default public int highestSupportedVersion() {
        return this.highestSupportedProtocolVersion().getVersion();
    }

    @Deprecated
    default public IntSortedSet supportedVersions() {
        return this.supportedProtocolVersions().stream().mapToInt(ProtocolVersion::getVersion).collect(IntLinkedOpenHashSet::new, IntCollection::add, IntCollection::addAll);
    }
}

