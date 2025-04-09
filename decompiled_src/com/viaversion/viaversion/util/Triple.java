/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Triple<A, B, C> {
    private final @Nullable A first;
    private final @Nullable B second;
    private final @Nullable C third;

    public Triple(@Nullable A first, @Nullable B second, @Nullable C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public @Nullable A first() {
        return this.first;
    }

    public @Nullable B second() {
        return this.second;
    }

    public @Nullable C third() {
        return this.third;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Triple)) {
            return false;
        }
        Triple triple = (Triple)object;
        return Objects.equals(this.first, triple.first) && Objects.equals(this.second, triple.second) && Objects.equals(this.third, triple.third);
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.first)) * 31 + Objects.hashCode(this.second)) * 31 + Objects.hashCode(this.third);
    }

    public String toString() {
        return String.format("%s[first=%s, second=%s, third=%s]", this.getClass().getSimpleName(), Objects.toString(this.first), Objects.toString(this.second), Objects.toString(this.third));
    }
}

