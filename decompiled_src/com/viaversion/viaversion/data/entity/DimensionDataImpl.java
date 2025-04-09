/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.data.entity;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viaversion.api.data.entity.DimensionData;

public final class DimensionDataImpl
implements DimensionData {
    private final int id;
    private final int minY;
    private final int height;

    public DimensionDataImpl(int id, int minY, int height) {
        this.id = id;
        this.minY = minY;
        this.height = height;
    }

    public DimensionDataImpl(int id, CompoundTag dimensionData) {
        this.id = id;
        NumberTag height = dimensionData.getNumberTag("height");
        if (height == null) {
            CompoundTag compoundTag = dimensionData;
            throw new IllegalArgumentException("height missing in dimension data: " + compoundTag);
        }
        this.height = height.asInt();
        NumberTag minY = dimensionData.getNumberTag("min_y");
        if (minY == null) {
            CompoundTag compoundTag = dimensionData;
            throw new IllegalArgumentException("min_y missing in dimension data: " + compoundTag);
        }
        this.minY = minY.asInt();
    }

    public static DimensionData withDefaultsFor(String key, int id) {
        DimensionDataImpl dimensionDataImpl;
        switch (key) {
            case "overworld": 
            case "overworld_caves": {
                dimensionDataImpl = new DimensionDataImpl(id, -64, 384);
                break;
            }
            case "the_nether": 
            case "the_end": {
                dimensionDataImpl = new DimensionDataImpl(id, 0, 256);
                break;
            }
            default: {
                String string = key;
                throw new IllegalArgumentException("Missing registry data for unknown dimension: " + string);
            }
        }
        return dimensionDataImpl;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public int minY() {
        return this.minY;
    }

    @Override
    public int height() {
        return this.height;
    }

    public String toString() {
        int n = this.height;
        int n2 = this.minY;
        int n3 = this.id;
        return "DimensionDataImpl{id=" + n3 + ", minY=" + n2 + ", height=" + n + "}";
    }
}

