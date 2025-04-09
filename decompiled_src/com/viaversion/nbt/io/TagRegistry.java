/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.io;

import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.ByteArrayTag;
import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.DoubleTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.LongArrayTag;
import com.viaversion.nbt.tag.LongTag;
import com.viaversion.nbt.tag.ShortTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import java.io.DataInput;
import java.io.IOException;
import org.jetbrains.annotations.Nullable;

public final class TagRegistry {
    public static final int END = 0;
    private static final int HIGHEST_ID = 12;
    private static final RegisteredTagType[] TAGS = new RegisteredTagType[13];
    private static final Object2IntMap<Class<? extends Tag>> TAG_TO_ID = new Object2IntOpenHashMap<Class<? extends Tag>>();

    public static <T extends Tag> void register(int id, Class<T> tag, TagSupplier<T> supplier) {
        if (id < 0 || id > 12) {
            throw new IllegalArgumentException("Tag ID must be between 0 and 12");
        }
        if (TAGS[id] != null) {
            throw new IllegalArgumentException("Tag ID \"" + id + "\" is already in use.");
        }
        if (TAG_TO_ID.containsKey(tag)) {
            throw new IllegalArgumentException("Tag \"" + tag.getSimpleName() + "\" is already registered.");
        }
        TagRegistry.TAGS[id] = new RegisteredTagType(tag, supplier);
        TAG_TO_ID.put((Class<? extends Tag>)tag, id);
    }

    @Nullable
    public static Class<? extends Tag> getClassFor(int id) {
        return id >= 0 && id < TAGS.length ? TAGS[id].type : null;
    }

    public static int getIdFor(Class<? extends Tag> clazz) {
        return TAG_TO_ID.getInt(clazz);
    }

    public static Tag read(int id, DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
        TagSupplier supplier;
        TagSupplier tagSupplier = supplier = id > 0 && id < TAGS.length ? TAGS[id].supplier : null;
        if (supplier == null) {
            throw new IllegalArgumentException("Could not find tag with ID \"" + id + "\".");
        }
        return supplier.create(in, tagLimiter, nestingLevel);
    }

    static {
        TAG_TO_ID.defaultReturnValue(-1);
        TagRegistry.register(1, ByteTag.class, (in, tagLimiter, nestingLevel) -> ByteTag.read(in, tagLimiter));
        TagRegistry.register(2, ShortTag.class, (in, tagLimiter, nestingLevel) -> ShortTag.read(in, tagLimiter));
        TagRegistry.register(3, IntTag.class, (in, tagLimiter, nestingLevel) -> IntTag.read(in, tagLimiter));
        TagRegistry.register(4, LongTag.class, (in, tagLimiter, nestingLevel) -> LongTag.read(in, tagLimiter));
        TagRegistry.register(5, FloatTag.class, (in, tagLimiter, nestingLevel) -> FloatTag.read(in, tagLimiter));
        TagRegistry.register(6, DoubleTag.class, (in, tagLimiter, nestingLevel) -> DoubleTag.read(in, tagLimiter));
        TagRegistry.register(7, ByteArrayTag.class, (in, tagLimiter, nestingLevel) -> ByteArrayTag.read(in, tagLimiter));
        TagRegistry.register(8, StringTag.class, (in, tagLimiter, nestingLevel) -> StringTag.read(in, tagLimiter));
        TagRegistry.register(9, ListTag.class, ListTag::read);
        TagRegistry.register(10, CompoundTag.class, CompoundTag::read);
        TagRegistry.register(11, IntArrayTag.class, (in, tagLimiter, nestingLevel) -> IntArrayTag.read(in, tagLimiter));
        TagRegistry.register(12, LongArrayTag.class, (in, tagLimiter, nestingLevel) -> LongArrayTag.read(in, tagLimiter));
    }

    private static final class RegisteredTagType {
        private final Class<? extends Tag> type;
        private final TagSupplier<? extends Tag> supplier;

        private <T extends Tag> RegisteredTagType(Class<T> type, TagSupplier<T> supplier) {
            this.type = type;
            this.supplier = supplier;
        }
    }

    @FunctionalInterface
    public static interface TagSupplier<T extends Tag> {
        public T create(DataInput var1, TagLimiter var2, int var3) throws IOException;
    }
}

