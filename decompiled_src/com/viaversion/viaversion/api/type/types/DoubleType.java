/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class DoubleType
extends Type<Double>
implements TypeConverter<Double> {
    public DoubleType() {
        super(Double.class);
    }

    @Override
    @Deprecated
    public Double read(ByteBuf buffer) {
        return buffer.readDouble();
    }

    public double readPrimitive(ByteBuf buffer) {
        return buffer.readDouble();
    }

    @Override
    @Deprecated
    public void write(ByteBuf buffer, Double object) {
        buffer.writeDouble(object.doubleValue());
    }

    public void writePrimitive(ByteBuf buffer, double object) {
        buffer.writeDouble(object);
    }

    @Override
    public Double from(Object o) {
        if (o instanceof Number) {
            Number number = (Number)o;
            return number.doubleValue();
        }
        if (o instanceof Boolean) {
            Boolean boo = (Boolean)o;
            return boo != false ? 1.0 : 0.0;
        }
        throw new UnsupportedOperationException();
    }
}

