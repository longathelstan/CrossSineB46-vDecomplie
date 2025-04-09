/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ByteArrayType
extends Type<byte[]> {
    public ByteArrayType() {
        super(byte[].class);
    }

    @Override
    public void write(ByteBuf buffer, byte[] array) {
        if (array.length != 1024) {
            throw new IllegalStateException("Byte array needs to be exactly 1024 bytes long");
        }
        buffer.writeBytes(array);
    }

    @Override
    public byte[] read(ByteBuf buffer) {
        byte[] array = new byte[1024];
        buffer.readBytes(array);
        return array;
    }
}

