/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SoundEvent {
    private final String identifier;
    private final @Nullable Float fixedRange;

    public SoundEvent(String identifier, @Nullable Float fixedRange) {
        this.identifier = identifier;
        this.fixedRange = fixedRange;
    }

    public SoundEvent withIdentifier(String identifier) {
        return new SoundEvent(identifier, this.fixedRange);
    }

    public String identifier() {
        return this.identifier;
    }

    public @Nullable Float fixedRange() {
        return this.fixedRange;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SoundEvent)) {
            return false;
        }
        SoundEvent soundEvent = (SoundEvent)object;
        return Objects.equals(this.identifier, soundEvent.identifier) && Objects.equals(this.fixedRange, soundEvent.fixedRange);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.identifier)) * 31 + Objects.hashCode(this.fixedRange);
    }

    public String toString() {
        return String.format("%s[identifier=%s, fixedRange=%s]", this.getClass().getSimpleName(), Objects.toString(this.identifier), Objects.toString(this.fixedRange));
    }
}

