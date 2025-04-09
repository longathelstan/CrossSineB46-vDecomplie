/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class UnsignedShortType
extends Type<Integer>
implements TypeConverter<Integer> {
    public UnsignedShortType() {
        super(Integer.class);
    }

    @Override
    public Integer read(ByteBuf buffer) {
        return buffer.readUnsignedShort();
    }

    @Override
    public void write(ByteBuf buffer, Integer object) {
        buffer.writeShort(object.intValue());
    }

    @Override
    public Integer from(Object o) {
        if (o instanceof Number) {
            Number number = (Number)o;
            return number.intValue();
        }
        if (o instanceof Boolean) {
            Boolean boo = (Boolean)o;
            return boo != false ? 1 : 0;
        }
        throw new UnsupportedOperationException();
    }
}

