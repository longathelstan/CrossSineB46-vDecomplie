/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

public final class StatisticData {
    private final int categoryId;
    private final int newId;
    private final int value;

    public StatisticData(int categoryId, int newId, int value) {
        this.categoryId = categoryId;
        this.newId = newId;
        this.value = value;
    }

    public int categoryId() {
        return this.categoryId;
    }

    public int newId() {
        return this.newId;
    }

    public int value() {
        return this.value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StatisticData)) {
            return false;
        }
        StatisticData statisticData = (StatisticData)object;
        return this.categoryId == statisticData.categoryId && this.newId == statisticData.newId && this.value == statisticData.value;
    }

    public int hashCode() {
        return ((0 * 31 + Integer.hashCode(this.categoryId)) * 31 + Integer.hashCode(this.newId)) * 31 + Integer.hashCode(this.value);
    }

    public String toString() {
        return String.format("%s[categoryId=%s, newId=%s, value=%s]", this.getClass().getSimpleName(), Integer.toString(this.categoryId), Integer.toString(this.newId), Integer.toString(this.value));
    }
}

