/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20to1_20_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.gson.JsonElement;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class LastResourcePack
implements StorableObject {
    private final String url;
    private final String hash;
    private final boolean required;
    private final @Nullable JsonElement prompt;

    public LastResourcePack(String url, String hash, boolean required, @Nullable JsonElement prompt) {
        this.url = url;
        this.hash = hash;
        this.required = required;
        this.prompt = prompt;
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }

    public String url() {
        return this.url;
    }

    public String hash() {
        return this.hash;
    }

    public boolean required() {
        return this.required;
    }

    public @Nullable JsonElement prompt() {
        return this.prompt;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LastResourcePack)) {
            return false;
        }
        LastResourcePack lastResourcePack = (LastResourcePack)object;
        return Objects.equals(this.url, lastResourcePack.url) && Objects.equals(this.hash, lastResourcePack.hash) && this.required == lastResourcePack.required && Objects.equals(this.prompt, lastResourcePack.prompt);
    }

    public int hashCode() {
        return (((0 * 31 + Objects.hashCode(this.url)) * 31 + Objects.hashCode(this.hash)) * 31 + Boolean.hashCode(this.required)) * 31 + Objects.hashCode(this.prompt);
    }

    public String toString() {
        return String.format("%s[url=%s, hash=%s, required=%s, prompt=%s]", this.getClass().getSimpleName(), Objects.toString(this.url), Objects.toString(this.hash), Boolean.toString(this.required), Objects.toString(this.prompt));
    }
}

