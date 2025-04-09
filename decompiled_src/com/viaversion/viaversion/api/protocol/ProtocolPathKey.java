/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

public interface ProtocolPathKey {
    public ProtocolVersion clientProtocolVersion();

    public ProtocolVersion serverProtocolVersion();
}

