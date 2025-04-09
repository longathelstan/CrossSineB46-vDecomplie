/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.snbt.impl.v1_8;

import com.viaversion.nbt.tag.ByteArrayTag;
import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.DoubleTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.LongTag;
import com.viaversion.nbt.tag.ShortTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.snbt.ISNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import java.util.Map;

public class SNbtSerializer_v1_8
implements ISNbtSerializer {
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
            return "[" + byteArrayTag.getValue().length + " bytes]";
        }
        if (tag instanceof StringTag) {
            StringTag stringTag = (StringTag)tag;
            return "\"" + stringTag.getValue().replace("\"", "\\\"") + "\"";
        }
        if (tag instanceof ListTag) {
            ListTag listTag = (ListTag)tag;
            StringBuilder out = new StringBuilder("[");
            for (int i = 0; i < listTag.size(); ++i) {
                if (i != 0) {
                    out.append(",");
                }
                out.append(i).append(":").append(this.serialize((Tag)listTag.get(i)));
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
                out.append((String)entry.getKey()).append(":").append(this.serialize((Tag)entry.getValue()));
            }
            return out.append("}").toString();
        }
        if (tag instanceof IntArrayTag) {
            IntArrayTag intArrayTag = (IntArrayTag)tag;
            StringBuilder out = new StringBuilder("[");
            for (int i : intArrayTag.getValue()) {
                out.append(i).append(",");
            }
            return out.append("]").toString();
        }
        throw new SNbtSerializeException(tag);
    }
}

