/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.Mappings;

public final class IdentityMappings
implements Mappings {
    private final int size;
    private final int mappedSize;

    public IdentityMappings(int size, int mappedSize) {
        this.size = size;
        this.mappedSize = mappedSize;
    }

    @Override
    public int getNewId(int id) {
        return id >= 0 && id < this.size ? id : -1;
    }

    @Override
    public void setNewId(int id, int mappedId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mappings inverse() {
        return new IdentityMappings(this.mappedSize, this.size);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int mappedSize() {
        return this.mappedSize;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IdentityMappings)) {
            return false;
        }
        IdentityMappings identityMappings = (IdentityMappings)object;
        return this.size == identityMappings.size && this.mappedSize == identityMappings.mappedSize;
    }

    public int hashCode() {
        return (0 * 31 + Integer.hashCode(this.size)) * 31 + Integer.hashCode(this.mappedSize);
    }

    public String toString() {
        return String.format("%s[size=%s, mappedSize=%s]", this.getClass().getSimpleName(), Integer.toString(this.size), Integer.toString(this.mappedSize));
    }
}

