/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.type;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class IntArrayType
extends Type<int[]> {
    public IntArrayType() {
        super(int[].class);
    }

    @Override
    public int[] read(ByteBuf byteBuf) {
        byte size = byteBuf.readByte();
        int[] array = new int[size];
        for (byte i = 0; i < size; i = (byte)(i + 1)) {
            array[i] = byteBuf.readInt();
        }
        return array;
    }

    @Override
    public void write(ByteBuf byteBuf, int[] array) {
        byteBuf.writeByte(array.length);
        for (int i : array) {
            byteBuf.writeInt(i);
        }
    }
}

