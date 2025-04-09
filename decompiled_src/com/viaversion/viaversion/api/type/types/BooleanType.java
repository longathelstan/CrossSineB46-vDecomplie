/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class BooleanType
extends Type<Boolean>
implements TypeConverter<Boolean> {
    public BooleanType() {
        super(Boolean.class);
    }

    @Override
    public Boolean read(ByteBuf buffer) {
        return buffer.readBoolean();
    }

    @Override
    public void write(ByteBuf buffer, Boolean object) {
        buffer.writeBoolean(object.booleanValue());
    }

    @Override
    public Boolean from(Object o) {
        if (o instanceof Number) {
            Number number = (Number)o;
            return number.intValue() == 1;
        }
        return (Boolean)o;
    }

    public static final class OptionalBooleanType
    extends OptionalType<Boolean> {
        public OptionalBooleanType() {
            super(Types.BOOLEAN);
        }
    }
}

