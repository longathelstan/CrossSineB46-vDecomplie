/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.dump;

import com.viaversion.viaversion.dump.VersionInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Map;
import java.util.Objects;

public final class DumpTemplate {
    private final VersionInfo versionInfo;
    private final Map<String, Object> configuration;
    private final JsonObject platformDump;
    private final JsonObject injectionDump;
    private final JsonObject playerSample;

    public DumpTemplate(VersionInfo versionInfo, Map<String, Object> configuration, JsonObject platformDump, JsonObject injectionDump, JsonObject playerSample) {
        this.versionInfo = versionInfo;
        this.configuration = configuration;
        this.platformDump = platformDump;
        this.injectionDump = injectionDump;
        this.playerSample = playerSample;
    }

    public VersionInfo versionInfo() {
        return this.versionInfo;
    }

    public Map<String, Object> configuration() {
        return this.configuration;
    }

    public JsonObject platformDump() {
        return this.platformDump;
    }

    public JsonObject injectionDump() {
        return this.injectionDump;
    }

    public JsonObject playerSample() {
        return this.playerSample;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DumpTemplate)) {
            return false;
        }
        DumpTemplate dumpTemplate = (DumpTemplate)object;
        return Objects.equals(this.versionInfo, dumpTemplate.versionInfo) && Objects.equals(this.configuration, dumpTemplate.configuration) && Objects.equals(this.platformDump, dumpTemplate.platformDump) && Objects.equals(this.injectionDump, dumpTemplate.injectionDump) && Objects.equals(this.playerSample, dumpTemplate.playerSample);
    }

    public int hashCode() {
        return ((((0 * 31 + Objects.hashCode(this.versionInfo)) * 31 + Objects.hashCode(this.configuration)) * 31 + Objects.hashCode(this.platformDump)) * 31 + Objects.hashCode(this.injectionDump)) * 31 + Objects.hashCode(this.playerSample);
    }

    public String toString() {
        return String.format("%s[versionInfo=%s, configuration=%s, platformDump=%s, injectionDump=%s, playerSample=%s]", this.getClass().getSimpleName(), Objects.toString(this.versionInfo), Objects.toString(this.configuration), Objects.toString(this.platformDump), Objects.toString(this.injectionDump), Objects.toString(this.playerSample));
    }
}

