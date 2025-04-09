/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.data;

import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import io.netty.buffer.ByteBuf;

final class EmptyStructuredData<T>
implements StructuredData<T> {
    private final StructuredDataKey<T> key;
    private int id;

    EmptyStructuredData(StructuredDataKey<T> key, int id) {
        this.key = key;
        this.id = id;
    }

    @Override
    public void setValue(T value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(ByteBuf buffer) {
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
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
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
        EmptyStructuredData that = (EmptyStructuredData)o;
        if (this.id != that.id) {
            return false;
        }
        return this.key.equals(that.key);
    }

    public int hashCode() {
        int result = this.key.hashCode();
        result = 31 * result + this.id;
        return result;
    }

    public String toString() {
        int n = this.id;
        StructuredDataKey<T> structuredDataKey = this.key;
        return "EmptyStructuredData{key=" + structuredDataKey + ", id=" + n + "}";
    }
}

