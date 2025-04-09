/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entitydata;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class EntityData {
    private int id;
    private EntityDataType dataType;
    private Object value;

    public EntityData(int id, EntityDataType dataType, @Nullable Object value) {
        this.id = id;
        this.dataType = dataType;
        this.value = this.checkValue(dataType, value);
    }

    public int id() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntityDataType dataType() {
        return this.dataType;
    }

    public void setDataType(EntityDataType dataType) {
        this.checkValue(dataType, this.value);
        this.dataType = dataType;
    }

    public <T> @Nullable T value() {
        return (T)this.value;
    }

    public @Nullable Object getValue() {
        return this.value;
    }

    public void setValue(@Nullable Object value) {
        this.value = this.checkValue(this.dataType, value);
    }

    public void setTypeAndValue(EntityDataType dataType, @Nullable Object value) {
        this.value = this.checkValue(dataType, value);
        this.dataType = dataType;
    }

    private Object checkValue(EntityDataType dataType, @Nullable Object value) {
        Preconditions.checkNotNull((Object)dataType);
        if (value != null && !dataType.type().getOutputClass().isAssignableFrom(value.getClass())) {
            String string = value.getClass().getSimpleName();
            Object object = value;
            EntityDataType entityDataType = dataType;
            throw new IllegalArgumentException("Entity data value and dataType are incompatible. Type=" + entityDataType + ", value=" + object + " (" + string + ")");
        }
        return value;
    }

    @Deprecated
    public void setDataTypeUnsafe(EntityDataType type) {
        this.dataType = type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        EntityData entityData = (EntityData)o;
        if (this.id != entityData.id) {
            return false;
        }
        if (this.dataType != entityData.dataType) {
            return false;
        }
        return Objects.equals(this.value, entityData.value);
    }

    public int hashCode() {
        int result = this.id;
        result = 31 * result + this.dataType.hashCode();
        result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
        return result;
    }

    public String toString() {
        Object object = this.value;
        EntityDataType entityDataType = this.dataType;
        int n = this.id;
        return "EntityData{id=" + n + ", dataType=" + entityDataType + ", value=" + object + "}";
    }
}

