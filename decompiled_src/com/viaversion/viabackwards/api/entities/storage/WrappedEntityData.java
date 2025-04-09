/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class WrappedEntityData {
    private final List<EntityData> entityDataList;

    public WrappedEntityData(List<EntityData> entityDataList) {
        this.entityDataList = entityDataList;
    }

    public boolean has(EntityData data) {
        return this.entityDataList.contains(data);
    }

    public void remove(EntityData data) {
        this.entityDataList.remove(data);
    }

    public void remove(int index2) {
        this.entityDataList.removeIf(data -> data.id() == index2);
    }

    public void add(EntityData data) {
        this.entityDataList.add(data);
    }

    public @Nullable EntityData get(int index2) {
        for (EntityData data : this.entityDataList) {
            if (index2 != data.id()) continue;
            return data;
        }
        return null;
    }

    public List<EntityData> entityDataList() {
        return this.entityDataList;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WrappedEntityData)) {
            return false;
        }
        WrappedEntityData wrappedEntityData = (WrappedEntityData)object;
        return Objects.equals(this.entityDataList, wrappedEntityData.entityDataList);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.entityDataList);
    }

    public String toString() {
        return String.format("%s[entityDataList=%s]", this.getClass().getSimpleName(), Objects.toString(this.entityDataList));
    }
}

