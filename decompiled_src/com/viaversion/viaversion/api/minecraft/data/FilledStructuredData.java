/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.data;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

final class FilledStructuredData<T>
implements StructuredData<T> {
    private final StructuredDataKey<T> key;
    private T value;
    private int id;

    FilledStructuredData(StructuredDataKey<T> key, T value, int id) {
        Preconditions.checkNotNull(key);
        this.key = key;
        this.value = value;
        this.id = id;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public void write(ByteBuf buffer) {
        this.key.type().write(buffer, this.value);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public StructuredDataKey<T> key() {
        return this.key;
    }

    @Override
    public T value() {
        return this.value;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int id() {
        return this.id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FilledStructuredData that = (FilledStructuredData)o;
        if (this.id != that.id) {
            return false;
        }
        if (!this.key.equals(that.key)) {
            return false;
        }
        return Objects.equals(this.value, that.value);
    }

    public int hashCode() {
        int result = this.key.hashCode();
        result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
        result = 31 * result + this.id;
        return result;
    }

    public String toString() {
        int n = this.id;
        T t = this.value;
        StructuredDataKey<T> structuredDataKey = this.key;
        return "FilledStructuredData{key=" + structuredDataKey + ", value=" + t + ", id=" + n + "}";
    }
}

