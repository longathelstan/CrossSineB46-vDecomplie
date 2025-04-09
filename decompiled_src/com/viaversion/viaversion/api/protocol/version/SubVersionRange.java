/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.version;

import com.google.common.base.Preconditions;
import java.util.Objects;

public final class SubVersionRange {
    private final String baseVersion;
    private final int rangeFrom;
    private final int rangeTo;

    public SubVersionRange(String baseVersion, int rangeFrom, int rangeTo) {
        Preconditions.checkNotNull((Object)baseVersion);
        Preconditions.checkArgument((rangeFrom >= 0 ? 1 : 0) != 0);
        Preconditions.checkArgument((rangeTo > rangeFrom ? 1 : 0) != 0);
        this.baseVersion = baseVersion;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public String baseVersion() {
        return this.baseVersion;
    }

    public int rangeFrom() {
        return this.rangeFrom;
    }

    public int rangeTo() {
        return this.rangeTo;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SubVersionRange)) {
            return false;
        }
        SubVersionRange subVersionRange = (SubVersionRange)object;
        return Objects.equals(this.baseVersion, subVersionRange.baseVersion) && this.rangeFrom == subVersionRange.rangeFrom && this.rangeTo == subVersionRange.rangeTo;
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.baseVersion)) * 31 + Integer.hashCode(this.rangeFrom)) * 31 + Integer.hashCode(this.rangeTo);
    }

    public String toString() {
        return String.format("%s[baseVersion=%s, rangeFrom=%s, rangeTo=%s]", this.getClass().getSimpleName(), Objects.toString(this.baseVersion), Integer.toString(this.rangeFrom), Integer.toString(this.rangeTo));
    }
}

