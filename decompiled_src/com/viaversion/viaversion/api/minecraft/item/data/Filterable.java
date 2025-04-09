/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class Filterable<T> {
    final T raw;
    final T filtered;

    protected Filterable(T raw, @Nullable T filtered) {
        this.raw = raw;
        this.filtered = filtered;
    }

    public T raw() {
        return this.raw;
    }

    public boolean isFiltered() {
        return this.filtered != null;
    }

    public @Nullable T filtered() {
        return this.filtered;
    }

    public T get() {
        return this.filtered != null ? this.filtered : this.raw;
    }

    public static abstract class FilterableType<T, F extends Filterable<T>>
    extends Type<F> {
        final Type<T> elementType;
        final Type<T> optionalElementType;

        protected FilterableType(Type<T> elementType, Type<T> optionalElementType, Class<F> outputClass) {
            super(outputClass);
            this.elementType = elementType;
            this.optionalElementType = optionalElementType;
        }

        @Override
        public F read(ByteBuf buffer) {
            Object raw = this.elementType.read(buffer);
            Object filtered = this.optionalElementType.read(buffer);
            return this.create(raw, filtered);
        }

        @Override
        public void write(ByteBuf buffer, F value) {
            this.elementType.write(buffer, ((Filterable)value).raw());
            this.optionalElementType.write(buffer, ((Filterable)value).filtered());
        }

        protected abstract F create(T var1, T var2);
    }
}

