/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class LongArrayType
extends Type<long[]> {
    public LongArrayType() {
        super(long[].class);
    }

    @Override
    public long[] read(ByteBuf buffer) {
        int length = Types.VAR_INT.readPrimitive(buffer);
        long[] array = new long[length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = buffer.readLong();
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, long[] object) {
        Types.VAR_INT.writePrimitive(buffer, object.length);
        for (long l : object) {
            buffer.writeLong(l);
        }
    }
}

