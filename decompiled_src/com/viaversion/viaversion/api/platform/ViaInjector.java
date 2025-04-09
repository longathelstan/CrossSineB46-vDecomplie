/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLinkedOpenHashSet;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.SortedSet;

public interface ViaInjector {
    public void inject() throws Exception;

    public void uninject() throws Exception;

    default public boolean lateProtocolVersionSetting() {
        return false;
    }

    public ProtocolVersion getServerProtocolVersion() throws Exception;

    default public SortedSet<ProtocolVersion> getServerProtocolVersions() throws Exception {
        ObjectLinkedOpenHashSet<ProtocolVersion> versions = new ObjectLinkedOpenHashSet<ProtocolVersion>();
        versions.add(this.getServerProtocolVersion());
        return versions;
    }

    default public String getEncoderName() {
        return "via-encoder";
    }

    default public String getDecoderName() {
        return "via-decoder";
    }

    public JsonObject getDump();
}

