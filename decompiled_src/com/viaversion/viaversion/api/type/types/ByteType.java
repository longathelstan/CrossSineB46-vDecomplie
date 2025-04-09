/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class ByteType
extends Type<Byte>
implements TypeConverter<Byte> {
    public ByteType() {
        super(Byte.class);
    }

    public byte readPrimitive(ByteBuf buffer) {
        return buffer.readByte();
    }

    public void writePrimitive(ByteBuf buffer, byte object) {
        buffer.writeByte((int)object);
    }

    @Override
    @Deprecated
    public Byte read(ByteBuf buffer) {
        return buffer.readByte();
    }

    @Override
    @Deprecated
    public void write(ByteBuf buffer, Byte object) {
        buffer.writeByte((int)object.byteValue());
    }

    @Override
    public Byte from(Object o) {
        if (o instanceof Number) {
            Number number = (Number)o;
            return number.byteValue();
        }
        if (o instanceof Boolean) {
            Boolean boo = (Boolean)o;
            return boo != false ? (byte)1 : 0;
        }
        throw new UnsupportedOperationException();
    }
}

