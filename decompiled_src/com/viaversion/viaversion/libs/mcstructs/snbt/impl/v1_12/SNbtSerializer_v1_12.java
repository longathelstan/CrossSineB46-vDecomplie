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
import com.viaversion.nbt.tag.ShortTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.snbt.ISNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import java.util.Map;
import java.util.regex.Pattern;

public class SNbtSerializer_v1_12
implements ISNbtSerializer {
    private static final Pattern ESCAPE_PATTERN = Pattern.compile("[A-Za-z0-9._+-]+");

    @Override
    public String serialize(Tag tag) throws SNbtSerializeException {
        if (tag instanceof ByteTag) {
            ByteTag byteTag = (ByteTag)tag;
            return byteTag.getValue() + "b";
        }
        if (tag instanceof ShortTag) {
            ShortTag shortTag = (ShortTag)tag;
            return shortTag.getValue() + "s";
        }
        if (tag instanceof IntTag) {
            IntTag intTag = (IntTag)tag;
            return String.valueOf(intTag.getValue());
        }
        if (tag instanceof LongTag) {
            LongTag longTag = (LongTag)tag;
            return longTag.getValue() + "L";
        }
        if (tag instanceof FloatTag) {
            FloatTag floatTag = (FloatTag)tag;
            return floatTag.getValue() + "f";
        }
        if (tag instanceof DoubleTag) {
            DoubleTag doubleTag = (DoubleTag)tag;
            return doubleTag.getValue() + "d";
        }
        if (tag instanceof ByteArrayTag) {
            ByteArrayTag byteArrayTag = (ByteArrayTag)tag;
            StringBuilder out = new StringBuilder("[B;");
            for (int i = 0; i < byteArrayTag.length(); ++i) {
                if (i != 0) {
                    out.append(",");
                }
                out.append(byteArrayTag.get(i)).append("B");
            }
            return out.append("]").toString();
        }
        if (tag instanceof StringTag) {
            StringTag stringTag = (StringTag)tag;
            return this.escape(stringTag.getValue());
        }
        if (tag instanceof ListTag) {
            ListTag listTag = (ListTag)tag;
            StringBuilder out = new StringBuilder("[");
            for (int i = 0; i < listTag.size(); ++i) {
                if (i != 0) {
                    out.append(",");
                }
                out.append(this.serialize((Tag)listTag.get(i)));
            }
            return out.append("]").toString();
        }
        if (tag instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag)tag;
            StringBuilder out = new StringBuilder("{");
            for (Map.Entry entry : compoundTag.getValue().entrySet()) {
                if (out.length() != 1) {
                    out.append(",");
                }
                out.append(this.checkEscape((String)entry.getKey())).append(":").append(this.serialize((Tag)entry.getValue()));
            }
            return out.append("}").toString();
        }
        if (tag instanceof IntArrayTag) {
            IntArrayTag intArrayTag = (IntArrayTag)tag;
            StringBuilder out = new StringBuilder("[I;");
            for (int i = 0; i < intArrayTag.length(); ++i) {
                if (i != 0) {
                    out.append(",");
                }
                out.append(intArrayTag.get(i));
            }
            return out.append("]").toString();
        }
        if (tag instanceof LongArrayTag) {
            LongArrayTag longArrayTag = (LongArrayTag)tag;
            StringBuilder out = new StringBuilder("[L;");
            for (int i = 0; i < longArrayTag.length(); ++i) {
                if (i != 0) {
                    out.append(",");
                }
                out.append(longArrayTag.get(i)).append("L");
            }
            return out.append("]").toString();
        }
        throw new SNbtSerializeException(tag);
    }

    protected String checkEscape(String s) {
        if (ESCAPE_PATTERN.matcher(s).matches()) {
            return s;
        }
        return this.escape(s);
    }

    protected String escape(String s) {
        char[] chars;
        StringBuilder out = new StringBuilder("\"");
        for (char c : chars = s.toCharArray()) {
            if (c == '\\' || c == '\"') {
                out.append("\\");
            }
            out.append(c);
        }
        return out.append("\"").toString();
    }
}

