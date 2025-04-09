/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.json;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.HoverEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.EntityHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.ItemHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json.JsonHoverEventSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import java.util.UUID;

public class JsonHoverEventSerializer_v1_20_5
extends JsonHoverEventSerializer_v1_20_3 {
    private static final String ACTION = "action";
    private static final String CONTENTS = "contents";
    private static final String VALUE = "value";
    private final TextComponentCodec_v1_20_5 codec;
    private final ITypedSerializer<JsonElement, ATextComponent> textSerializer;
    private final SNbtSerializer<CompoundTag> sNbtSerializer;

    public JsonHoverEventSerializer_v1_20_5(TextComponentCodec_v1_20_5 codec, ITypedSerializer<JsonElement, ATextComponent> textSerializer, SNbtSerializer<CompoundTag> sNbtSerializer) {
        super(codec, textSerializer, sNbtSerializer);
        this.codec = codec;
        this.textSerializer = textSerializer;
        this.sNbtSerializer = sNbtSerializer;
    }

    @Override
    public JsonElement serialize(AHoverEvent object) {
        JsonObject out = new JsonObject();
        out.addProperty(ACTION, object.getAction().getName());
        if (object instanceof TextHoverEvent) {
            TextHoverEvent textHoverEvent = (TextHoverEvent)object;
            out.add(CONTENTS, this.textSerializer.serialize(textHoverEvent.getText()));
        } else if (object instanceof ItemHoverEvent) {
            ItemHoverEvent itemHoverEvent = (ItemHoverEvent)object;
            JsonObject contents = new JsonObject();
            contents.addProperty("id", itemHoverEvent.getItem().get());
            if (itemHoverEvent.getCount() != 1) {
                contents.addProperty("count", itemHoverEvent.getCount());
            }
            if (itemHoverEvent.getNbt() != null) {
                contents.add("components", this.codec.convertItemComponents(itemHoverEvent.getNbt()));
            }
            out.add(CONTENTS, contents);
        } else if (object instanceof EntityHoverEvent) {
            EntityHoverEvent entityHoverEvent = (EntityHoverEvent)object;
            JsonObject contents = new JsonObject();
            contents.addProperty("type", entityHoverEvent.getEntityType().get());
            JsonArray id = new JsonArray();
            id.add((int)(entityHoverEvent.getUuid().getMostSignificantBits() >> 32));
            id.add((int)(entityHoverEvent.getUuid().getMostSignificantBits() & 0xFFFFFFFFL));
            id.add((int)(entityHoverEvent.getUuid().getLeastSignificantBits() >> 32));
            id.add((int)(entityHoverEvent.getUuid().getLeastSignificantBits() & 0xFFFFFFFFL));
            contents.add("id", id);
            if (entityHoverEvent.getName() != null) {
                contents.add("name", this.textSerializer.serialize(entityHoverEvent.getName()));
            }
            out.add(CONTENTS, contents);
        } else {
            throw new IllegalArgumentException("Unknown hover event type: " + object.getClass().getName());
        }
        return out;
    }

    @Override
    public AHoverEvent deserialize(JsonElement object) {
        if (!object.isJsonObject()) {
            throw new IllegalArgumentException("Element must be a json object");
        }
        JsonObject obj = object.getAsJsonObject();
        HoverEventAction action = HoverEventAction.getByName(this.requiredString(obj, ACTION), false);
        if (action == null) {
            throw new IllegalArgumentException("Unknown hover event action: " + obj.get(ACTION).getAsString());
        }
        if (!action.isUserDefinable()) {
            throw new IllegalArgumentException("Hover event action is not user definable: " + (Object)((Object)action));
        }
        if (obj.has(CONTENTS)) {
            switch (action) {
                case SHOW_TEXT: {
                    return new TextHoverEvent(action, this.textSerializer.deserialize(obj.get(CONTENTS)));
                }
                case SHOW_ITEM: {
                    if (obj.has(CONTENTS) && this.isString(obj.get(CONTENTS))) {
                        Identifier id = Identifier.of(obj.get(CONTENTS).getAsString());
                        this.verifyItem(id);
                        return new ItemHoverEvent(action, id, 1, null);
                    }
                    if (obj.has(CONTENTS) && this.isObject(obj.get(CONTENTS))) {
                        JsonObject contents = obj.getAsJsonObject(CONTENTS);
                        Identifier id = Identifier.of(this.requiredString(contents, "id"));
                        this.verifyItem(id);
                        Integer count = this.optionalInt(contents, "count");
                        JsonObject components = this.optionalObject(contents, "components");
                        return new ItemHoverEvent(action, id, count == null ? 1 : count, components == null ? null : this.codec.convertItemComponents(components));
                    }
                    throw new IllegalArgumentException("Expected string or json array for 'contents' tag");
                }
                case SHOW_ENTITY: {
                    ATextComponent name;
                    JsonObject contents = this.requiredObject(obj, CONTENTS);
                    Identifier type = Identifier.of(this.requiredString(contents, "type"));
                    this.codec.verifyEntity(type);
                    UUID id = this.getUUID(contents.get("id"));
                    if (contents.has("name")) {
                        try {
                            name = this.textSerializer.deserialize(contents.get("name"));
                        }
                        catch (Throwable t) {
                            name = null;
                        }
                    } else {
                        name = null;
                    }
                    return new EntityHoverEvent(action, type, id, name);
                }
            }
            throw new IllegalArgumentException("Unknown hover event action: " + (Object)((Object)action));
        }
        if (obj.has(VALUE)) {
            ATextComponent value = this.textSerializer.deserialize(obj.get(VALUE));
            try {
                switch (action) {
                    case SHOW_TEXT: {
                        return new TextHoverEvent(action, value);
                    }
                    case SHOW_ITEM: {
                        CompoundTag parsed = this.sNbtSerializer.deserialize(value.asUnformattedString());
                        Identifier id = Identifier.of(this.requiredString(parsed, "id"));
                        this.verifyItem(id);
                        Integer count = this.optionalInt(parsed, "count");
                        CompoundTag components = this.optionalCompound(parsed, "components");
                        if (components != null) {
                            this.codec.verifyItemComponents(components);
                        }
                        return new ItemHoverEvent(action, id, count == null ? 1 : count, components);
                    }
                    case SHOW_ENTITY: {
                        CompoundTag parsed = this.sNbtSerializer.deserialize(value.asUnformattedString());
                        ATextComponent name = this.codec.deserializeJson(parsed.get("name") instanceof StringTag ? ((StringTag)parsed.get("name")).getValue() : "");
                        Identifier type = Identifier.of(parsed.get("type") instanceof StringTag ? ((StringTag)parsed.get("type")).getValue() : "");
                        UUID uuid = UUID.fromString(parsed.get("id") instanceof StringTag ? ((StringTag)parsed.get("id")).getValue() : "");
                        return new EntityHoverEvent(action, type, uuid, name);
                    }
                }
                throw new IllegalArgumentException("Unknown hover event action: " + (Object)((Object)action));
            }
            catch (Throwable t) {
                this.sneak(t);
            }
        }
        throw new IllegalArgumentException("Missing 'contents' or 'value' tag");
    }

    protected void verifyItem(Identifier id) {
        this.codec.verifyItem(id);
        if (id.equals(Identifier.of("minecraft:air"))) {
            throw new IllegalArgumentException("Item hover component id is 'minecraft:air'");
        }
    }
}

