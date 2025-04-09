/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import java.util.Objects;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class GameProfile {
    final @Nullable String name;
    final @Nullable UUID id;
    final Property[] properties;

    public GameProfile(@Nullable String name, @Nullable UUID id, Property[] properties) {
        this.name = name;
        this.id = id;
        this.properties = properties;
    }

    public @Nullable String name() {
        return this.name;
    }

    public @Nullable UUID id() {
        return this.id;
    }

    public Property[] properties() {
        return this.properties;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof GameProfile)) {
            return false;
        }
        GameProfile gameProfile = (GameProfile)object;
        return Objects.equals(this.name, gameProfile.name) && Objects.equals(this.id, gameProfile.id) && Objects.equals(this.properties, gameProfile.properties);
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.name)) * 31 + Objects.hashCode(this.id)) * 31 + Objects.hashCode(this.properties);
    }

    public String toString() {
        return String.format("%s[name=%s, id=%s, properties=%s]", this.getClass().getSimpleName(), Objects.toString(this.name), Objects.toString(this.id), Objects.toString(this.properties));
    }

    public static final class Property {
        final String name;
        final String value;
        final @Nullable String signature;

        public Property(String name, String value, @Nullable String signature) {
            this.name = name;
            this.value = value;
            this.signature = signature;
        }

        public String name() {
            return this.name;
        }

        public String value() {
            return this.value;
        }

        public @Nullable String signature() {
            return this.signature;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Property)) {
                return false;
            }
            Property property = (Property)object;
            return Objects.equals(this.name, property.name) && Objects.equals(this.value, property.value) && Objects.equals(this.signature, property.signature);
        }

        public int hashCode() {
            return ((0 * 31 + Objects.hashCode(this.name)) * 31 + Objects.hashCode(this.value)) * 31 + Objects.hashCode(this.signature);
        }

        public String toString() {
            return String.format("%s[name=%s, value=%s, signature=%s]", this.getClass().getSimpleName(), Objects.toString(this.name), Objects.toString(this.value), Objects.toString(this.signature));
        }
    }
}

