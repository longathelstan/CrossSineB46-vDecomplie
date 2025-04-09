/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class UnsignedByteType
extends Type<Short>
implements TypeConverter<Short> {
    public static final int MAX_VALUE = 255;

    public UnsignedByteType() {
        super("Unsigned Byte", Short.class);
    }

    @Override
    public Short read(ByteBuf buffer) {
        return buffer.readUnsignedByte();
    }

    @Override
    public void write(ByteBuf buffer, Short object) {
        buffer.writeByte((int)object.shortValue());
    }

    @Override
    public Short from(Object o) {
        if (o instanceof Number) {
            Number number = (Number)o;
            return number.shortValue();
        }
        if (o instanceof Boolean) {
            Boolean boo = (Boolean)o;
            return boo != false ? (short)1 : 0;
        }
        throw new UnsupportedOperationException();
    }
}

