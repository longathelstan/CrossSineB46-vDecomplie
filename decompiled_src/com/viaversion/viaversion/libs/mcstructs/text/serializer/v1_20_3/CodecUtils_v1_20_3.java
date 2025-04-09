/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface CodecUtils_v1_20_3 {
    default public List<Tag> unwrapMarkers(ListTag<?> list) {
        ArrayList<Tag> tags = new ArrayList<Tag>((Collection<Tag>)list.getValue());
        if (CompoundTag.class == list.getElementType()) {
            for (int i = 0; i < tags.size(); ++i) {
                Tag wrapped;
                CompoundTag compound = (CompoundTag)tags.get(i);
                if (compound.size() != 1 || (wrapped = compound.get("")) == null) continue;
                tags.set(i, wrapped);
            }
        }
        return tags;
    }

    default public boolean isString(@Nullable JsonElement element) {
        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isString();
    }

    default public boolean isNumber(@Nullable JsonElement element) {
        return element != null && element.isJsonPrimitive() && (element.getAsJsonPrimitive().isNumber() || element.getAsJsonPrimitive().isBoolean());
    }

    default public boolean isObject(@Nullable JsonElement element) {
        return element != null && element.isJsonObject();
    }

    default public boolean containsString(JsonObject obj, String name) {
        return obj.has(name) && this.isString(obj.get(name));
    }

    default public boolean containsArray(JsonObject obj, String name) {
        return obj.has(name) && obj.get(name).isJsonArray();
    }

    default public boolean containsObject(JsonObject obj, String name) {
        return obj.has(name) && this.isObject(obj.get(name));
    }

    @Nullable
    default public Boolean optionalBoolean(CompoundTag tag, String name) {
        if (!tag.contains(name)) {
            return null;
        }
        return this.requiredBoolean(tag, name);
    }

    @Nullable
    default public Boolean optionalBoolean(JsonObject obj, String name) {
        if (!obj.has(name)) {
            return null;
        }
        return this.requiredBoolean(obj, name);
    }

    @Nullable
    default public Integer optionalInt(CompoundTag tag, String name) {
        if (!tag.contains(name)) {
            return null;
        }
        return this.requiredInt(tag, name);
    }

    @Nullable
    default public Integer optionalInt(JsonObject obj, String name) {
        if (!obj.has(name)) {
            return null;
        }
        return this.requiredInt(obj, name);
    }

    @Nullable
    default public String optionalString(CompoundTag tag, String name) {
        if (!tag.contains(name)) {
            return null;
        }
        return this.requiredString(tag, name);
    }

    @Nullable
    default public String optionalString(JsonObject obj, String name) {
        if (!obj.has(name)) {
            return null;
        }
        return this.requiredString(obj, name);
    }

    @Nullable
    default public CompoundTag optionalCompound(CompoundTag tag, String name) {
        if (!tag.contains(name)) {
            return null;
        }
        return this.requiredCompound(tag, name);
    }

    @Nullable
    default public JsonObject optionalObject(JsonObject obj, String name) {
        if (!obj.has(name)) {
            return null;
        }
        return this.requiredObject(obj, name);
    }

    default public boolean requiredBoolean(CompoundTag tag, String name) {
        if (!(tag.get(name) instanceof ByteTag)) {
            throw new IllegalArgumentException("Expected byte tag for '" + name + "' tag");
        }
        return tag.get(name) instanceof ByteTag ? ((ByteTag)tag.get(name)).asBoolean() : false;
    }

    default public boolean requiredBoolean(JsonObject obj, String name) {
        if (!obj.has(name)) {
            throw new IllegalArgumentException("Missing boolean for '" + name + "' tag");
        }
        JsonElement element = obj.get(name);
        if (!element.isJsonPrimitive()) {
            throw new IllegalArgumentException("Expected boolean for '" + name + "' tag");
        }
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isBoolean()) {
            return primitive.getAsBoolean();
        }
        if (primitive.isNumber()) {
            return primitive.getAsInt() != 0;
        }
        throw new IllegalArgumentException("Expected boolean for '" + name + "' tag");
    }

    default public int requiredInt(CompoundTag tag, String name) {
        if (!(tag.get(name) instanceof IntTag)) {
            throw new IllegalArgumentException("Expected int tag for '" + name + "' tag");
        }
        return tag.get(name) instanceof IntTag ? ((IntTag)tag.get(name)).asInt() : 0;
    }

    default public int requiredInt(JsonObject obj, String name) {
        if (!obj.has(name)) {
            throw new IllegalArgumentException("Missing int for '" + name + "' tag");
        }
        JsonElement element = obj.get(name);
        if (!element.isJsonPrimitive()) {
            throw new IllegalArgumentException("Expected int for '" + name + "' tag");
        }
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isNumber()) {
            return primitive.getAsInt();
        }
        if (primitive.isBoolean()) {
            return primitive.getAsBoolean() ? 1 : 0;
        }
        throw new IllegalArgumentException("Expected int for '" + name + "' tag");
    }

    default public String requiredString(CompoundTag tag, String name) {
        if (!(tag.get(name) instanceof StringTag)) {
            throw new IllegalArgumentException("Expected string tag for '" + name + "' tag");
        }
        return tag.get(name) instanceof StringTag ? ((StringTag)tag.get(name)).getValue() : "";
    }

    default public String requiredString(JsonObject obj, String name) {
        if (!obj.has(name)) {
            throw new IllegalArgumentException("Missing string for '" + name + "' tag");
        }
        JsonElement element = obj.get(name);
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
            throw new IllegalArgumentException("Expected string for '" + name + "' tag");
        }
        return element.getAsString();
    }

    default public CompoundTag requiredCompound(CompoundTag tag, String name) {
        if (!(tag.get(name) instanceof CompoundTag)) {
            throw new IllegalArgumentException("Expected compound tag for '" + name + "' tag");
        }
        return tag.get(name) instanceof CompoundTag ? (CompoundTag)tag.get(name) : new CompoundTag();
    }

    default public JsonObject requiredObject(JsonObject obj, String name) {
        if (!obj.has(name)) {
            throw new IllegalArgumentException("Missing object for '" + name + "' tag");
        }
        JsonElement element = obj.get(name);
        if (!element.isJsonObject()) {
            throw new IllegalArgumentException("Expected object for '" + name + "' tag");
        }
        return element.getAsJsonObject();
    }
}

