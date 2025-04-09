/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.LegacyStringDeserializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.utils.TextUtils;
import com.viaversion.viaversion.util.SerializerVersion;
import com.viaversion.viaversion.util.TagUtil;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ComponentUtil {
    private static final int MAX_UNSIGNED_SHORT = 65535;

    public static JsonObject emptyJsonComponent() {
        return ComponentUtil.plainToJson("");
    }

    public static String emptyJsonComponentString() {
        return "{\"text\":\"\"}";
    }

    public static JsonObject plainToJson(String message) {
        JsonObject object = new JsonObject();
        object.addProperty("text", message);
        return object;
    }

    public static @Nullable JsonElement tagToJson(@Nullable Tag tag) {
        try {
            ATextComponent component = SerializerVersion.V1_20_3.toComponent(tag);
            return component != null ? SerializerVersion.V1_19_4.toJson(component) : null;
        }
        catch (Exception e) {
            Tag tag2 = tag;
            Via.getPlatform().getLogger().log(Level.SEVERE, "Error converting tag: " + tag2, e);
            return ComponentUtil.plainToJson("<error>");
        }
    }

    public static @Nullable Tag jsonToTag(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        try {
            ATextComponent component = SerializerVersion.V1_19_4.toComponent(element);
            return ComponentUtil.trimStrings(SerializerVersion.V1_20_3.toTag(component));
        }
        catch (Exception e) {
            JsonElement jsonElement = element;
            Via.getPlatform().getLogger().log(Level.SEVERE, "Error converting component: " + jsonElement, e);
            return new StringTag("<error>");
        }
    }

    private static Tag trimStrings(Tag input) {
        if (input == null) {
            return null;
        }
        return TagUtil.handleDeep(input, (key, tag) -> {
            StringTag stringTag;
            byte[] value;
            if (tag instanceof StringTag && (value = (stringTag = (StringTag)tag).getValue().getBytes(StandardCharsets.UTF_8)).length > 65535) {
                stringTag.setValue("{}");
            }
            return tag;
        });
    }

    public static @Nullable String tagToJsonString(@Nullable Tag tag) {
        try {
            ATextComponent component = SerializerVersion.V1_20_5.toComponent(tag);
            return component != null ? SerializerVersion.V1_20_3.toString(component) : null;
        }
        catch (Exception e) {
            Tag tag2 = tag;
            Via.getPlatform().getLogger().log(Level.SEVERE, "Error converting tag: " + tag2, e);
            return ComponentUtil.plainToJson("<error>").toString();
        }
    }

    public static @Nullable Tag jsonStringToTag(@Nullable String json) {
        return ComponentUtil.jsonStringToTag(json, SerializerVersion.V1_20_3, SerializerVersion.V1_20_5);
    }

    public static @Nullable Tag jsonStringToTag(@Nullable String json, SerializerVersion from, SerializerVersion to) {
        if (json == null) {
            return null;
        }
        return to.toTag(from.jsonSerializer.deserialize(json));
    }

    public static @Nullable JsonElement convertJson(@Nullable JsonElement element, SerializerVersion from, SerializerVersion to) {
        return element != null ? ComponentUtil.convert(from, to, from.toComponent(element)) : null;
    }

    public static @Nullable JsonElement convertJson(@Nullable String json, SerializerVersion from, SerializerVersion to) {
        return json != null ? ComponentUtil.convert(from, to, from.toComponent(json)) : null;
    }

    public static @Nullable JsonElement convertJsonOrEmpty(@Nullable String json, SerializerVersion from, SerializerVersion to) {
        ATextComponent component = from.toComponent(json);
        if (component == null) {
            return ComponentUtil.emptyJsonComponent();
        }
        return to.toJson(component);
    }

    private static JsonElement convert(SerializerVersion from, SerializerVersion to, ATextComponent component) {
        Style style;
        AHoverEvent hoverEvent;
        if (from.ordinal() >= SerializerVersion.V1_16.ordinal() && to.ordinal() < SerializerVersion.V1_16.ordinal() && (hoverEvent = (style = component.getStyle()).getHoverEvent()) != null && !(hoverEvent instanceof TextHoverEvent)) {
            style.setHoverEvent(hoverEvent.toLegacy(to.jsonSerializer, to.snbtSerializer));
        }
        return to.toJson(component);
    }

    public static JsonElement legacyToJson(String message) {
        return SerializerVersion.V1_12.toJson(LegacyStringDeserializer.parse(message, true));
    }

    public static String legacyToJsonString(String message) {
        return ComponentUtil.legacyToJsonString(message, false);
    }

    public static String legacyToJsonString(String message, boolean itemData) {
        ATextComponent component = LegacyStringDeserializer.parse(message, true);
        if (itemData) {
            TextUtils.iterateAll(component, c -> {
                if (!c.getStyle().isEmpty()) {
                    c.setParentStyle(new Style().setItalic(false));
                }
            });
        }
        return SerializerVersion.V1_12.toString(component);
    }

    public static String jsonToLegacy(String value) {
        return TextComponentSerializer.V1_12.deserializeReader(value).asLegacyFormatString();
    }

    public static String jsonToLegacy(JsonElement value) {
        return SerializerVersion.V1_12.toComponent(value).asLegacyFormatString();
    }

    public static CompoundTag deserializeLegacyShowItem(JsonElement element, SerializerVersion version) {
        return (CompoundTag)version.toTag(version.toComponent(element).asUnformattedString());
    }

    public static CompoundTag deserializeShowItem(Tag value, SerializerVersion version) {
        return (CompoundTag)version.toTag(version.toComponent(value).asUnformattedString());
    }
}

