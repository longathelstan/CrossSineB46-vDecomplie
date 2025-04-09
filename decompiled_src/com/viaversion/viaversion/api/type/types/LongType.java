/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class LongType
extends Type<Long>
implements TypeConverter<Long> {
    public LongType() {
        super(Long.class);
    }

    @Override
    @Deprecated
    public Long read(ByteBuf buffer) {
        return buffer.readLong();
    }

    @Override
    @Deprecated
    public void write(ByteBuf buffer, Long object) {
        buffer.writeLong(object.longValue());
    }

    @Override
    public Long from(Object o) {
        if (o instanceof Number) {
            Number number = (Number)o;
            return number.longValue();
        }
        if (o instanceof Boolean) {
            Boolean boo = (Boolean)o;
            return boo != false ? 1L : 0L;
        }
        throw new UnsupportedOperationException();
    }

    public long readPrimitive(ByteBuf buffer) {
        return buffer.readLong();
    }

    public void writePrimitive(ByteBuf buffer, long object) {
        buffer.writeLong(object);
    }
}

