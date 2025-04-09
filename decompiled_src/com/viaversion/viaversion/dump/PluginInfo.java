/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.dump;

import java.util.List;
import java.util.Objects;

public final class PluginInfo {
    private final boolean enabled;
    private final String name;
    private final String version;
    private final String main;
    private final List<String> authors;

    public PluginInfo(boolean enabled, String name, String version, String main, List<String> authors) {
        this.enabled = enabled;
        this.name = name;
        this.version = version;
        this.main = main;
        this.authors = authors;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public String name() {
        return this.name;
    }

    public String version() {
        return this.version;
    }

    public String main() {
        return this.main;
    }

    public List<String> authors() {
        return this.authors;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PluginInfo)) {
            return false;
        }
        PluginInfo pluginInfo = (PluginInfo)object;
        return this.enabled == pluginInfo.enabled && Objects.equals(this.name, pluginInfo.name) && Objects.equals(this.version, pluginInfo.version) && Objects.equals(this.main, pluginInfo.main) && Objects.equals(this.authors, pluginInfo.authors);
    }

    public int hashCode() {
        return ((((0 * 31 + Boolean.hashCode(this.enabled)) * 31 + Objects.hashCode(this.name)) * 31 + Objects.hashCode(this.version)) * 31 + Objects.hashCode(this.main)) * 31 + Objects.hashCode(this.authors);
    }

    public String toString() {
        return String.format("%s[enabled=%s, name=%s, version=%s, main=%s, authors=%s]", this.getClass().getSimpleName(), Boolean.toString(this.enabled), Objects.toString(this.name), Objects.toString(this.version), Objects.toString(this.main), Objects.toString(this.authors));
    }
}

