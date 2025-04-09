/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.storage;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.connection.StorableObject;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class RegistryDataStorage
implements StorableObject {
    private final CompoundTag registryData = new CompoundTag();
    private String[] dimensionKeys;
    private boolean sentRegistryData;

    public CompoundTag registryData() {
        return this.registryData;
    }

    public boolean sentRegistryData() {
        return this.sentRegistryData;
    }

    public void setSentRegistryData() {
        this.sentRegistryData = true;
    }

    public String @Nullable [] dimensionKeys() {
        return this.dimensionKeys;
    }

    public void setDimensionKeys(String[] dimensionKeys) {
        this.dimensionKeys = dimensionKeys;
    }

    public void clear() {
        this.registryData.clear();
        this.dimensionKeys = null;
        this.sentRegistryData = false;
    }
}

