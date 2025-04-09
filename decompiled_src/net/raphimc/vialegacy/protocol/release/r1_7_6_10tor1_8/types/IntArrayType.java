/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class IntArrayType
extends Type<int[]> {
    public IntArrayType() {
        super(int[].class);
    }

    @Override
    public int[] read(ByteBuf buffer) {
        byte length = buffer.readByte();
        int[] array = new int[length];
        for (byte i = 0; i < length; i = (byte)(i + 1)) {
            array[i] = buffer.readInt();
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, int[] array) {
        buffer.writeByte(array.length);
        for (int i : array) {
            buffer.writeInt(i);
        }
    }
}

