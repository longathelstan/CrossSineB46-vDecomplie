/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;

public interface ProtocolDetectorService {
    public ProtocolVersion serverProtocolVersion(String var1);

    public void probeAllServers();

    public void setProtocolVersion(String var1, int var2);

    public int uncacheProtocolVersion(String var1);

    public Object2IntMap<String> detectedProtocolVersions();
}

