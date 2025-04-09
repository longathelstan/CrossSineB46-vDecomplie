/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

public final class VillagerData {
    private final int type;
    private final int profession;
    private final int level;

    public VillagerData(int type, int profession, int level) {
        this.type = type;
        this.profession = profession;
        this.level = level;
    }

    public int type() {
        return this.type;
    }

    public int profession() {
        return this.profession;
    }

    public int level() {
        return this.level;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VillagerData)) {
            return false;
        }
        VillagerData villagerData = (VillagerData)object;
        return this.type == villagerData.type && this.profession == villagerData.profession && this.level == villagerData.level;
    }

    public int hashCode() {
        return ((0 * 31 + Integer.hashCode(this.type)) * 31 + Integer.hashCode(this.profession)) * 31 + Integer.hashCode(this.level);
    }

    public String toString() {
        return String.format("%s[type=%s, profession=%s, level=%s]", this.getClass().getSimpleName(), Integer.toString(this.type), Integer.toString(this.profession), Integer.toString(this.level));
    }
}

