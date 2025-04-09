/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.nbt.conversion;

import com.viaversion.nbt.conversion.ConversionException;
import com.viaversion.nbt.conversion.TagConverter;
import com.viaversion.nbt.conversion.converter.ByteArrayTagConverter;
import com.viaversion.nbt.conversion.converter.ByteTagConverter;
import com.viaversion.nbt.conversion.converter.CompoundTagConverter;
import com.viaversion.nbt.conversion.converter.DoubleTagConverter;
import com.viaversion.nbt.conversion.converter.FloatTagConverter;
import com.viaversion.nbt.conversion.converter.IntArrayTagConverter;
import com.viaversion.nbt.conversion.converter.IntTagConverter;
import com.viaversion.nbt.conversion.converter.ListTagConverter;
import com.viaversion.nbt.conversion.converter.LongArrayTagConverter;
import com.viaversion.nbt.conversion.converter.LongTagConverter;
import com.viaversion.nbt.conversion.converter.ShortTagConverter;
import com.viaversion.nbt.conversion.converter.StringTagConverter;
import com.viaversion.nbt.io.TagRegistry;
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
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public final class ConverterRegistry {
    private static final Int2ObjectMap<TagConverter<? extends Tag, ?>> TAG_TO_CONVERTER = new Int2ObjectOpenHashMap();
    private static final Map<Class<?>, TagConverter<? extends Tag, ?>> TYPE_TO_CONVERTER = new HashMap();

    public static <T extends Tag, V> void register(Class<T> tag, Class<? extends V> type, TagConverter<T, V> converter) {
        int tagId = TagRegistry.getIdFor(tag);
        if (tagId == -1) {
            throw new IllegalArgumentException("Tag " + tag.getName() + " is not a registered tag.");
        }
        if (TAG_TO_CONVERTER.containsKey(tagId)) {
            throw new IllegalArgumentException("Type conversion to tag " + tag.getName() + " is already registered.");
        }
        if (TYPE_TO_CONVERTER.containsKey(type)) {
            throw new IllegalArgumentException("Tag conversion to type " + type.getName() + " is already registered.");
        }
        TAG_TO_CONVERTER.put(tagId, (TagConverter<Tag, ?>)converter);
        TYPE_TO_CONVERTER.put(type, converter);
    }

    public static <T extends Tag, V> void unregister(Class<T> tag, Class<V> type) {
        TAG_TO_CONVERTER.remove(TagRegistry.getIdFor(tag));
        TYPE_TO_CONVERTER.remove(type);
    }

    @Nullable
    public static <T extends Tag, V> V convertToValue(@Nullable T tag) throws ConversionException {
        if (tag == null || tag.getValue() == null) {
            return null;
        }
        TagConverter converter = (TagConverter)TAG_TO_CONVERTER.get(tag.getTagId());
        if (converter == null) {
            throw new ConversionException("Tag type " + tag.getClass().getName() + " has no converter.");
        }
        return (V)converter.convert(tag);
    }

    @Nullable
    public static <V, T extends Tag> T convertToTag(@Nullable V value) throws ConversionException {
        if (value == null) {
            return null;
        }
        Class<?> valueClass = value.getClass();
        TagConverter<Tag, ?> converter = TYPE_TO_CONVERTER.get(valueClass);
        if (converter == null) {
            Class<?> interfaceClass;
            Class<?>[] classArray = valueClass.getInterfaces();
            int n = classArray.length;
            for (int i = 0; i < n && (converter = TYPE_TO_CONVERTER.get(interfaceClass = classArray[i])) == null; ++i) {
            }
            if (converter == null) {
                throw new ConversionException("Value type " + valueClass.getName() + " has no converter.");
            }
        }
        return (T)converter.convert(value);
    }

    static {
        ConverterRegistry.register(ByteTag.class, Byte.class, new ByteTagConverter());
        ConverterRegistry.register(ShortTag.class, Short.class, new ShortTagConverter());
        ConverterRegistry.register(IntTag.class, Integer.class, new IntTagConverter());
        ConverterRegistry.register(LongTag.class, Long.class, new LongTagConverter());
        ConverterRegistry.register(FloatTag.class, Float.class, new FloatTagConverter());
        ConverterRegistry.register(DoubleTag.class, Double.class, new DoubleTagConverter());
        ConverterRegistry.register(ByteArrayTag.class, byte[].class, new ByteArrayTagConverter());
        ConverterRegistry.register(StringTag.class, String.class, new StringTagConverter());
        ConverterRegistry.register(ListTag.class, List.class, new ListTagConverter());
        ConverterRegistry.register(CompoundTag.class, Map.class, new CompoundTagConverter());
        ConverterRegistry.register(IntArrayTag.class, int[].class, new IntArrayTagConverter());
        ConverterRegistry.register(LongArrayTag.class, long[].class, new LongArrayTagConverter());
    }
}

