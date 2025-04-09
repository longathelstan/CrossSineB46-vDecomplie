/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;

public class ArrayType<T>
extends Type<T[]> {
    private final Type<T> elementType;

    public ArrayType(Type<T> type) {
        String string = type.getTypeName();
        super(string + " Array", ArrayType.getArrayClass(type.getOutputClass()));
        this.elementType = type;
    }

    public static Class<?> getArrayClass(Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    @Override
    public T[] read(ByteBuf buffer) {
        int amount = Types.VAR_INT.readPrimitive(buffer);
        Object[] array = (Object[])Array.newInstance(this.elementType.getOutputClass(), amount);
        for (int i = 0; i < amount; ++i) {
            array[i] = this.elementType.read(buffer);
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, T[] object) {
        Types.VAR_INT.writePrimitive(buffer, object.length);
        for (T o : object) {
            this.elementType.write(buffer, o);
        }
    }
}

