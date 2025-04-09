/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.nbt.io.TagRegistry;
import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.NamedCompoundTagType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import java.io.DataInput;
import java.io.IOException;

public class TagType
extends Type<Tag> {
    public TagType() {
        super(Tag.class);
    }

    @Override
    public Tag read(ByteBuf buffer) {
        byte id = buffer.readByte();
        if (id == 0) {
            return null;
        }
        TagLimiter tagLimiter = TagLimiter.create(0x200000, 512);
        try {
            return TagRegistry.read(id, (DataInput)new ByteBufInputStream(buffer), tagLimiter, 0);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(ByteBuf buffer, Tag tag) {
        try {
            NamedCompoundTagType.write(buffer, tag, null);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final class OptionalTagType
    extends OptionalType<Tag> {
        public OptionalTagType() {
            super(Types.TAG);
        }
    }
}

