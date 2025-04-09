/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12;

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
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.ShortTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.snbt.ISNbtDeserializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;
import com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_12.StringReader_v1_12;
import java.util.regex.Pattern;

public class SNbtDeserializer_v1_12
implements ISNbtDeserializer<CompoundTag> {
    private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
    private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
    private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
    private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
    private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
    private static final Pattern SHORT_DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);

    @Override
    public CompoundTag deserialize(String s) throws SNbtDeserializeException {
        StringReader_v1_12 reader = this.makeReader(s);
        CompoundTag compoundTag = this.readCompound(reader);
        reader.skipWhitespaces();
        if (reader.canRead()) {
            throw this.makeException(reader, "Trailing data found");
        }
        return compoundTag;
    }

    @Override
    public Tag deserializeValue(String s) throws SNbtDeserializeException {
        return this.readValue(this.makeReader(s));
    }

    protected CompoundTag readCompound(StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.jumpTo('{');
        CompoundTag compound = new CompoundTag();
        reader.skipWhitespaces();
        while (reader.canRead() && reader.peek() != '}') {
            String key = reader.readString();
            if (key == null) {
                throw this.makeException(reader, "Expected key");
            }
            if (key.isEmpty()) {
                throw this.makeException(reader, "Expected non-empty key");
            }
            reader.jumpTo(':');
            compound.put(key, this.readValue(reader));
            if (!this.hasNextValue(reader)) break;
            if (reader.canRead()) continue;
            throw this.makeException(reader, "Expected key");
        }
        reader.jumpTo('}');
        return compound;
    }

    protected Tag readListOrArray(StringReader_v1_12 reader) throws SNbtDeserializeException {
        if (reader.canRead(2) && !this.isQuote(reader.charAt(1)) && reader.charAt(2) == ';') {
            return this.readArray(reader);
        }
        return this.readList(reader);
    }

    protected ListTag<Tag> readList(StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.jumpTo('[');
        reader.skipWhitespaces();
        if (!reader.canRead()) {
            throw this.makeException(reader, "Expected value");
        }
        ListTag<Tag> list = new ListTag<Tag>();
        while (reader.peek() != ']') {
            Tag tag = this.readValue(reader);
            list.add(tag);
            if (!this.hasNextValue(reader)) break;
            if (reader.canRead()) continue;
            throw this.makeException(reader, "Expected value");
        }
        reader.jumpTo(']');
        return list;
    }

    protected <T extends NumberTag> ListTag<T> readPrimitiveList(StringReader_v1_12 reader, Class<T> primitiveType, Class<? extends Tag> arrayType) throws SNbtDeserializeException {
        ListTag<NumberTag> list = new ListTag<NumberTag>();
        while (reader.peek() != ']') {
            Tag tag = this.readValue(reader);
            if (!primitiveType.isAssignableFrom(tag.getClass())) {
                throw new SNbtDeserializeException("Unable to insert " + tag.getClass().getSimpleName() + " into " + arrayType.getSimpleName());
            }
            list.add((NumberTag)tag);
            if (!this.hasNextValue(reader)) break;
            if (reader.canRead()) continue;
            throw this.makeException(reader, "Expected value");
        }
        reader.jumpTo(']');
        return list;
    }

    protected Tag readArray(StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.jumpTo('[');
        char c = reader.read();
        reader.read();
        reader.skipWhitespaces();
        if (!reader.canRead()) {
            throw this.makeException(reader, "Expected value");
        }
        if (c == 'B') {
            ListTag<ByteTag> tags = this.readPrimitiveList(reader, ByteTag.class, ByteArrayTag.class);
            byte[] array = new byte[tags.size()];
            for (int i = 0; i < tags.size(); ++i) {
                array[i] = tags.get(i).asByte();
            }
            return new ByteArrayTag(array);
        }
        if (c == 'L') {
            ListTag<LongTag> tags = this.readPrimitiveList(reader, LongTag.class, LongArrayTag.class);
            long[] array = new long[tags.size()];
            for (int i = 0; i < tags.size(); ++i) {
                array[i] = tags.get(i).asLong();
            }
            return new LongArrayTag(array);
        }
        if (c == 'I') {
            ListTag<IntTag> tags = this.readPrimitiveList(reader, IntTag.class, IntArrayTag.class);
            int[] array = new int[tags.size()];
            for (int i = 0; i < tags.size(); ++i) {
                array[i] = tags.get(i).asInt();
            }
            return new IntArrayTag(array);
        }
        throw new SNbtDeserializeException("Invalid array type '" + c + "' found");
    }

    protected Tag readValue(StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.skipWhitespaces();
        if (!reader.canRead()) {
            throw this.makeException(reader, "Expected value");
        }
        char c = reader.peek();
        if (c == '{') {
            return this.readCompound(reader);
        }
        if (c == '[') {
            return this.readListOrArray(reader);
        }
        return this.readPrimitive(reader);
    }

    protected Tag readPrimitive(StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.skipWhitespaces();
        if (this.isQuote(reader.peek())) {
            return new StringTag(reader.readQuotedString());
        }
        String value = reader.readUnquotedString();
        if (value.isEmpty()) {
            throw this.makeException(reader, "Expected value");
        }
        return this.readPrimitive(value);
    }

    protected Tag readPrimitive(String value) {
        try {
            if (FLOAT_PATTERN.matcher(value).matches()) {
                return new FloatTag(Float.parseFloat(value.substring(0, value.length() - 1)));
            }
            if (BYTE_PATTERN.matcher(value).matches()) {
                return new ByteTag(Byte.parseByte(value.substring(0, value.length() - 1)));
            }
            if (LONG_PATTERN.matcher(value).matches()) {
                return new LongTag(Long.parseLong(value.substring(0, value.length() - 1)));
            }
            if (SHORT_PATTERN.matcher(value).matches()) {
                return new ShortTag(Short.parseShort(value.substring(0, value.length() - 1)));
            }
            if (INT_PATTERN.matcher(value).matches()) {
                return new IntTag(Integer.parseInt(value));
            }
            if (DOUBLE_PATTERN.matcher(value).matches()) {
                return new DoubleTag(Double.parseDouble(value.substring(0, value.length() - 1)));
            }
            if (SHORT_DOUBLE_PATTERN.matcher(value).matches()) {
                return new DoubleTag(Double.parseDouble(value));
            }
            if (value.equalsIgnoreCase("false")) {
                return new ByteTag(0);
            }
            if (value.equalsIgnoreCase("true")) {
                return new ByteTag(1);
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return new StringTag(value);
    }

    protected boolean hasNextValue(StringReader_v1_12 reader) {
        reader.skipWhitespaces();
        if (reader.canRead() && reader.peek() == ',') {
            reader.skip();
            reader.skipWhitespaces();
            return true;
        }
        return false;
    }

    protected SNbtDeserializeException makeException(StringReader_v1_12 reader, String message) {
        return new SNbtDeserializeException(message, reader.getString(), reader.getIndex());
    }

    protected StringReader_v1_12 makeReader(String string) {
        return new StringReader_v1_12(string);
    }

    protected boolean isQuote(char c) {
        return c == '\"';
    }
}

