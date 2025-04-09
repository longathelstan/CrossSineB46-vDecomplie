/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

public interface ProtocolPathEntry {
    public ProtocolVersion outputProtocolVersion();

    public Protocol<?, ?, ?, ?> protocol();
}

