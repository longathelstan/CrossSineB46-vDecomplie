/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class OptionalVarIntType
extends Type<Integer> {
    public OptionalVarIntType() {
        super(Integer.class);
    }

    @Override
    public Integer read(ByteBuf buffer) {
        int value = Types.VAR_INT.readPrimitive(buffer);
        return value == 0 ? null : Integer.valueOf(value - 1);
    }

    @Override
    public void write(ByteBuf buffer, Integer object) {
        if (object == null) {
            Types.VAR_INT.writePrimitive(buffer, 0);
        } else {
            Types.VAR_INT.writePrimitive(buffer, object + 1);
        }
    }
}

