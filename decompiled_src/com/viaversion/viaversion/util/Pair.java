/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Pair<X, Y> {
    private final @Nullable X key;
    private final @Nullable Y value;

    public Pair(@Nullable X key, @Nullable Y value) {
        this.key = key;
        this.value = value;
    }

    public @Nullable X key() {
        return this.key;
    }

    public @Nullable Y value() {
        return this.value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair)object;
        return Objects.equals(this.key, pair.key) && Objects.equals(this.value, pair.value);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.key)) * 31 + Objects.hashCode(this.value);
    }

    public String toString() {
        return String.format("%s[key=%s, value=%s]", this.getClass().getSimpleName(), Objects.toString(this.key), Objects.toString(this.value));
    }
}

