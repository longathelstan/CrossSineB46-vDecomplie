/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.NamedCompoundTagType;
import io.netty.buffer.ByteBuf;
import java.io.IOException;

public class CompoundTagType
extends Type<CompoundTag> {
    public CompoundTagType() {
        super(CompoundTag.class);
    }

    @Override
    public CompoundTag read(ByteBuf buffer) {
        try {
            return NamedCompoundTagType.read(buffer, false);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(ByteBuf buffer, CompoundTag object) {
        try {
            NamedCompoundTagType.write(buffer, object, null);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final class OptionalCompoundTagType
    extends OptionalType<CompoundTag> {
        public OptionalCompoundTagType() {
            super(Types.COMPOUND_TAG);
        }
    }
}

