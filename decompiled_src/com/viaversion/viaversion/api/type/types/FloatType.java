/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class FloatType
extends Type<Float>
implements TypeConverter<Float> {
    public FloatType() {
        super(Float.class);
    }

    public float readPrimitive(ByteBuf buffer) {
        return buffer.readFloat();
    }

    public void writePrimitive(ByteBuf buffer, float object) {
        buffer.writeFloat(object);
    }

    @Override
    @Deprecated
    public Float read(ByteBuf buffer) {
        return Float.valueOf(buffer.readFloat());
    }

    @Override
    @Deprecated
    public void write(ByteBuf buffer, Float object) {
        buffer.writeFloat(object.floatValue());
    }

    @Override
    public Float from(Object o) {
        if (o instanceof Number) {
            Number number = (Number)o;
            return Float.valueOf(number.floatValue());
        }
        if (o instanceof Boolean) {
            Boolean boo = (Boolean)o;
            return Float.valueOf(boo != false ? 1.0f : 0.0f);
        }
        throw new UnsupportedOperationException();
    }

    public static final class OptionalFloatType
    extends OptionalType<Float> {
        public OptionalFloatType() {
            super(Types.FLOAT);
        }
    }
}

