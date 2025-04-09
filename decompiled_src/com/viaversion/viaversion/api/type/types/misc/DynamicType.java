/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.util.IdHolder;
import io.netty.buffer.ByteBuf;

public abstract class DynamicType<T extends IdHolder>
extends Type<T> {
    protected final Int2ObjectMap<DataReader<T>> readers;

    protected DynamicType(Int2ObjectMap<DataReader<T>> readers, Class<T> outputClass) {
        super(outputClass.getSimpleName(), outputClass);
        this.readers = readers;
    }

    protected DynamicType(Class<T> outputClass) {
        this(new Int2ObjectOpenHashMap<DataReader<T>>(), outputClass);
    }

    public DataFiller filler(Protocol<?, ?, ?, ?> protocol) {
        return this.filler(protocol, true);
    }

    public DataFiller filler(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
        return new DataFiller(protocol, useMappedNames);
    }

    protected void readData(ByteBuf buffer, T value) {
        DataReader reader = (DataReader)this.readers.get(value.id());
        if (reader != null) {
            reader.read(buffer, value);
        }
    }

    public RawDataFiller rawFiller() {
        return new RawDataFiller();
    }

    protected abstract FullMappings mappings(Protocol<?, ?, ?, ?> var1);

    public final class DataFiller {
        final FullMappings mappings;
        final boolean useMappedNames;

        DataFiller(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
            this.mappings = DynamicType.this.mappings(protocol);
            Preconditions.checkNotNull((Object)this.mappings, (String)"Mappings for %s are null", (Object[])new Object[]{protocol.getClass()});
            this.useMappedNames = useMappedNames;
        }

        public DataFiller reader(String identifier, DataReader<T> reader) {
            DynamicType.this.readers.put(this.useMappedNames ? this.mappings.mappedId(identifier) : this.mappings.id(identifier), reader);
            return this;
        }
    }

    @FunctionalInterface
    public static interface DataReader<T> {
        public void read(ByteBuf var1, T var2);
    }

    public final class RawDataFiller {
        public RawDataFiller reader(int id, DataReader<T> reader) {
            DynamicType.this.readers.put(id, reader);
            return this;
        }
    }
}

