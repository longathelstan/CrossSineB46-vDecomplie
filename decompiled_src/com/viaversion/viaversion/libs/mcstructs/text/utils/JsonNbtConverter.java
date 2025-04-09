/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.utils;

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
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonNull;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

public class JsonNbtConverter {
    @Nullable
    public static JsonElement toJson(@Nullable Tag tag) {
        if (tag == null) {
            return null;
        }
        if (tag instanceof NumberTag) {
            return new JsonPrimitive(((NumberTag)tag).getValue());
        }
        if (tag instanceof ByteArrayTag) {
            JsonArray byteArray = new JsonArray();
            for (byte b : ((ByteArrayTag)tag).getValue()) {
                byteArray.add(b);
            }
            return byteArray;
        }
        if (tag instanceof StringTag) {
            return new JsonPrimitive(((StringTag)tag).getValue());
        }
        if (tag instanceof ListTag) {
            JsonArray list = new JsonArray();
            ListTag listTag = (ListTag)tag;
            Iterator iterator2 = listTag.getValue().iterator();
            while (iterator2.hasNext()) {
                Tag wrappedTag;
                CompoundTag compound;
                Tag tagInList = (Tag)iterator2.next();
                if (CompoundTag.class == listTag.getElementType() && (compound = (CompoundTag)tagInList).size() == 1 && (wrappedTag = compound.get("")) != null) {
                    tagInList = wrappedTag;
                }
                list.add(JsonNbtConverter.toJson(tagInList));
            }
            return list;
        }
        if (tag instanceof CompoundTag) {
            JsonObject compound = new JsonObject();
            for (Map.Entry entry : ((CompoundTag)tag).getValue().entrySet()) {
                compound.add((String)entry.getKey(), JsonNbtConverter.toJson((Tag)entry.getValue()));
            }
            return compound;
        }
        if (tag instanceof IntArrayTag) {
            JsonArray intArray = new JsonArray();
            for (int i : ((IntArrayTag)tag).getValue()) {
                intArray.add(i);
            }
            return intArray;
        }
        if (tag instanceof LongArrayTag) {
            JsonArray longArray = new JsonArray();
            for (long l : ((LongArrayTag)tag).getValue()) {
                longArray.add(l);
            }
            return longArray;
        }
        throw new IllegalArgumentException("Unknown Nbt type: " + tag);
    }

    @Nullable
    public static Tag toNbt(@Nullable JsonElement element) {
        if (element == null) {
            return null;
        }
        if (element instanceof JsonObject) {
            JsonObject object = element.getAsJsonObject();
            CompoundTag compound = new CompoundTag();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                compound.put(entry.getKey(), JsonNbtConverter.toNbt(entry.getValue()));
            }
            return compound;
        }
        if (element instanceof JsonArray) {
            JsonArray array = element.getAsJsonArray();
            ArrayList<Tag> nbtTags = new ArrayList<Tag>();
            Tag listType = null;
            boolean mixedList = false;
            for (Object arrayElement : array) {
                Tag tag = JsonNbtConverter.toNbt((JsonElement)arrayElement);
                nbtTags.add(tag);
                if ((listType = JsonNbtConverter.getListType(listType, tag)) != null) continue;
                mixedList = true;
            }
            if (listType == null) {
                return new ListTag();
            }
            if (mixedList) {
                ListTag<CompoundTag> list = new ListTag<CompoundTag>();
                for (Tag tag : nbtTags) {
                    if (tag instanceof CompoundTag) {
                        list.add((CompoundTag)tag);
                        continue;
                    }
                    CompoundTag entries = new CompoundTag();
                    entries.put("", tag);
                    list.add(entries);
                }
                return list;
            }
            if (listType instanceof ByteTag) {
                byte[] bytes = new byte[nbtTags.size()];
                for (int i = 0; i < nbtTags.size(); ++i) {
                    bytes[i] = ((NumberTag)nbtTags.get(i)).asByte();
                }
                return new ByteArrayTag(bytes);
            }
            if (listType instanceof IntTag) {
                int[] ints = new int[nbtTags.size()];
                for (int i = 0; i < nbtTags.size(); ++i) {
                    ints[i] = ((NumberTag)nbtTags.get(i)).asInt();
                }
                return new IntArrayTag(ints);
            }
            if (listType instanceof LongTag) {
                long[] longs = new long[nbtTags.size()];
                for (int i = 0; i < nbtTags.size(); ++i) {
                    longs[i] = ((NumberTag)nbtTags.get(i)).asLong();
                }
                return new LongArrayTag(longs);
            }
            return new ListTag(nbtTags);
        }
        if (element instanceof JsonNull) {
            return null;
        }
        if (element instanceof JsonPrimitive) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                return new StringTag(primitive.getAsString());
            }
            if (primitive.isBoolean()) {
                return new ByteTag(primitive.getAsBoolean());
            }
            BigDecimal number = primitive.getAsBigDecimal();
            try {
                long l = number.longValueExact();
                if ((long)((byte)l) == l) {
                    return new ByteTag((byte)l);
                }
                if ((long)((short)l) == l) {
                    return new ShortTag((short)l);
                }
                if ((long)((int)l) == l) {
                    return new IntTag((int)l);
                }
                return new LongTag(l);
            }
            catch (ArithmeticException e) {
                double d = number.doubleValue();
                if ((double)((float)d) == d) {
                    return new FloatTag((float)d);
                }
                return new DoubleTag(d);
            }
        }
        throw new IllegalArgumentException("Unknown JsonElement type: " + element.getClass().getName());
    }

    private static Tag getListType(Tag current, Tag tag) {
        if (current == null) {
            return tag;
        }
        if (tag == null || current.getClass() != tag.getClass()) {
            return null;
        }
        return current;
    }
}

