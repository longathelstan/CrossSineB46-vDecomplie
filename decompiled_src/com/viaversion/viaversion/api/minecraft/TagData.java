/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import java.util.Arrays;
import java.util.Objects;

public final class TagData {
    private final String identifier;
    private final int[] entries;

    public TagData(String identifier, int[] entries) {
        this.identifier = identifier;
        this.entries = entries;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        TagData tagData = (TagData)o;
        if (!this.identifier.equals(tagData.identifier)) {
            return false;
        }
        return Arrays.equals(this.entries, tagData.entries);
    }

    public int hashCode() {
        int result = this.identifier.hashCode();
        result = 31 * result + Arrays.hashCode(this.entries);
        return result;
    }

    public String identifier() {
        return this.identifier;
    }

    public int[] entries() {
        return this.entries;
    }

    public String toString() {
        return String.format("%s[identifier=%s, entries=%s]", this.getClass().getSimpleName(), Objects.toString(this.identifier), Objects.toString(this.entries));
    }
}

