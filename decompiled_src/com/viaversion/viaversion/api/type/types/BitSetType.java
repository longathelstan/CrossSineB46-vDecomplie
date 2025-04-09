/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.BitSet;

public class BitSetType
extends Type<BitSet> {
    private final int length;
    private final int bytesLength;

    public BitSetType(int length) {
        super(BitSet.class);
        this.length = length;
        this.bytesLength = -Math.floorDiv(-length, 8);
    }

    @Override
    public BitSet read(ByteBuf buffer) {
        byte[] bytes = new byte[this.bytesLength];
        buffer.readBytes(bytes);
        return BitSet.valueOf(bytes);
    }

    @Override
    public void write(ByteBuf buffer, BitSet object) {
        int n = this.length;
        int n2 = object.length();
        Preconditions.checkArgument((object.length() <= this.length ? 1 : 0) != 0, (Object)("BitSet of length " + n2 + " larger than max length " + n));
        byte[] bytes = object.toByteArray();
        buffer.writeBytes(Arrays.copyOf(bytes, this.bytesLength));
    }
}

