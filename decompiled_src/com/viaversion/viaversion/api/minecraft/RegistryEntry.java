/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.nbt.tag.Tag;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class RegistryEntry {
    private final String key;
    private final @Nullable Tag tag;

    public RegistryEntry(String key, @Nullable Tag tag) {
        this.key = key;
        this.tag = tag;
    }

    public RegistryEntry withKey(String key) {
        return new RegistryEntry(key, this.tag != null ? this.tag.copy() : null);
    }

    public String key() {
        return this.key;
    }

    public @Nullable Tag tag() {
        return this.tag;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RegistryEntry)) {
            return false;
        }
        RegistryEntry registryEntry = (RegistryEntry)object;
        return Objects.equals(this.key, registryEntry.key) && Objects.equals(this.tag, registryEntry.tag);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.key)) * 31 + Objects.hashCode(this.tag);
    }

    public String toString() {
        return String.format("%s[key=%s, tag=%s]", this.getClass().getSimpleName(), Objects.toString(this.key), Objects.toString(this.tag));
    }
}

