/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public class HolderSetType
extends Type<HolderSet> {
    public HolderSetType() {
        super(HolderSet.class);
    }

    @Override
    public HolderSet read(ByteBuf buffer) {
        int size = Types.VAR_INT.readPrimitive(buffer) - 1;
        if (size == -1) {
            String tag = (String)Types.STRING.read(buffer);
            return HolderSet.of(tag);
        }
        int[] values2 = new int[size];
        for (int i = 0; i < size; ++i) {
            values2[i] = Types.VAR_INT.readPrimitive(buffer);
        }
        return HolderSet.of(values2);
    }

    @Override
    public void write(ByteBuf buffer, HolderSet object) {
        if (object.hasTagKey()) {
            Types.VAR_INT.writePrimitive(buffer, 0);
            Types.STRING.write(buffer, object.tagKey());
        } else {
            int[] values2 = object.ids();
            Types.VAR_INT.writePrimitive(buffer, values2.length + 1);
            for (int value : values2) {
                Types.VAR_INT.writePrimitive(buffer, value);
            }
        }
    }

    public static final class OptionalHolderSetType
    extends OptionalType<HolderSet> {
        public OptionalHolderSetType() {
            super(Types.HOLDER_SET);
        }
    }
}

