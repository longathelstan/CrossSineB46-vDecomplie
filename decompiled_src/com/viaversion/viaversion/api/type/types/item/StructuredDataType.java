/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.item;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StructuredDataType
extends Type<StructuredData<?>> {
    StructuredDataKey<?>[] types;

    public StructuredDataType() {
        super(StructuredData.class);
    }

    @Override
    public void write(ByteBuf buffer, StructuredData<?> object) {
        Types.VAR_INT.writePrimitive(buffer, object.id());
        object.write(buffer);
    }

    @Override
    public StructuredData<?> read(ByteBuf buffer) {
        Preconditions.checkNotNull(this.types, (Object)"StructuredDataType has not been initialized");
        int id = Types.VAR_INT.readPrimitive(buffer);
        StructuredDataKey<?> key = this.key(id);
        if (key == null) {
            int n = id;
            throw new IllegalArgumentException("No data component serializer found for id " + n);
        }
        return this.readData(buffer, key, id);
    }

    public @Nullable StructuredDataKey<?> key(int id) {
        return id >= 0 && id < this.types.length ? this.types[id] : null;
    }

    <T> StructuredData<T> readData(ByteBuf buffer, StructuredDataKey<T> key, int id) {
        return StructuredData.of(key, key.type().read(buffer), id);
    }

    public DataFiller filler(Protocol<?, ?, ?, ?> protocol) {
        return new DataFiller(protocol);
    }

    public final class DataFiller {
        final FullMappings mappings;

        DataFiller(Protocol<?, ?, ?, ?> protocol) {
            this.mappings = protocol.getMappingData().getDataComponentSerializerMappings();
            Preconditions.checkArgument((this.mappings != null ? 1 : 0) != 0, (String)"No mappings found for protocol %s", (Object[])new Object[]{protocol.getClass()});
            Preconditions.checkArgument((StructuredDataType.this.types == null ? 1 : 0) != 0, (Object)"StructuredDataType has already been initialized");
            StructuredDataType.this.types = new StructuredDataKey[this.mappings.mappedSize()];
        }

        public DataFiller add(StructuredDataKey<?> key) {
            int id = this.mappings.mappedId(key.identifier());
            Preconditions.checkArgument((id != -1 ? 1 : 0) != 0, (String)"No mapped id found for %s", (Object[])new Object[]{key.identifier()});
            Preconditions.checkArgument((StructuredDataType.this.types[id] == null ? 1 : 0) != 0, (String)"Data component serializer already exists for id %s", (Object[])new Object[]{id});
            StructuredDataType.this.types[id] = key;
            return this;
        }
    }
}

