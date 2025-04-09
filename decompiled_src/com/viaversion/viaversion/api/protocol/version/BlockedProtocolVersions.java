/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.Set;

public interface BlockedProtocolVersions {
    public boolean contains(ProtocolVersion var1);

    public ProtocolVersion blocksBelow();

    public ProtocolVersion blocksAbove();

    public Set<ProtocolVersion> singleBlockedVersions();
}

