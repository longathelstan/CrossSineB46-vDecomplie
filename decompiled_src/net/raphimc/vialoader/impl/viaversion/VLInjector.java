/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.impl.viaversion;

import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLinkedOpenHashSet;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.SortedSet;

public class VLInjector
implements ViaInjector {
    @Override
    public void inject() {
    }

    @Override
    public void uninject() {
    }

    @Override
    public ProtocolVersion getServerProtocolVersion() {
        return this.getServerProtocolVersions().first();
    }

    @Override
    public SortedSet<ProtocolVersion> getServerProtocolVersions() {
        ObjectLinkedOpenHashSet<ProtocolVersion> versions = new ObjectLinkedOpenHashSet<ProtocolVersion>();
        versions.addAll(ProtocolVersion.getProtocols());
        return versions;
    }

    @Override
    public String getEncoderName() {
        return "via-codec";
    }

    @Override
    public String getDecoderName() {
        return "via-codec";
    }

    @Override
    public JsonObject getDump() {
        return new JsonObject();
    }
}

