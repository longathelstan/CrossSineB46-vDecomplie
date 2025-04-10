/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_12;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonDeserializationContext;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.KeybindComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.ScoreComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.SelectorComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.utils.JsonUtils;
import java.lang.reflect.Type;

public class TextDeserializer_v1_12
implements JsonDeserializer<ATextComponent> {
    @Override
    public ATextComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return new StringComponent(json.getAsString());
        }
        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            ATextComponent component = null;
            for (JsonElement element : array) {
                ATextComponent serializedElement = this.deserialize(element, element.getClass(), context);
                if (component == null) {
                    component = serializedElement;
                    continue;
                }
                component.append(serializedElement);
            }
            return component;
        }
        if (json.isJsonObject()) {
            Style newStyle;
            ATextComponent component;
            JsonObject rawComponent = json.getAsJsonObject();
            if (rawComponent.has("text")) {
                component = new StringComponent(rawComponent.get("text").getAsString());
            } else if (rawComponent.has("translate")) {
                String translate = rawComponent.get("translate").getAsString();
                if (rawComponent.has("with")) {
                    JsonArray with = rawComponent.getAsJsonArray("with");
                    Object[] args2 = new Object[with.size()];
                    for (int i = 0; i < with.size(); ++i) {
                        StringComponent stringComponent;
                        ATextComponent element = this.deserialize(with.get(i), typeOfT, context);
                        args2[i] = element;
                        if (!(element instanceof StringComponent) || !(stringComponent = (StringComponent)element).getStyle().isEmpty() || !stringComponent.getSiblings().isEmpty()) continue;
                        args2[i] = stringComponent.getText();
                    }
                    component = new TranslationComponent(translate, args2);
                } else {
                    component = new TranslationComponent(translate, new Object[0]);
                }
            } else if (rawComponent.has("score")) {
                JsonObject score = rawComponent.getAsJsonObject("score");
                if (!score.has("name") || !score.has("objective")) {
                    throw new JsonParseException("A score component needs at least a name and an objective");
                }
                component = new ScoreComponent(JsonUtils.getString(score, "name"), JsonUtils.getString(score, "objective"));
                if (score.has("value")) {
                    ((ScoreComponent)component).setValue(JsonUtils.getString(score, "value"));
                }
            } else if (rawComponent.has("selector")) {
                component = new SelectorComponent(JsonUtils.getString(rawComponent, "selector"), null);
            } else if (rawComponent.has("keybind")) {
                component = new KeybindComponent(JsonUtils.getString(rawComponent, "keybind"));
            } else {
                throw new JsonParseException("Don't know how to turn " + json + " into a Component");
            }
            if (rawComponent.has("extra")) {
                JsonArray extra = rawComponent.getAsJsonArray("extra");
                if (extra.isEmpty()) {
                    throw new JsonParseException("Unexpected empty array of components");
                }
                for (JsonElement element : extra) {
                    component.append(this.deserialize(element, typeOfT, context));
                }
            }
            if ((newStyle = (Style)context.deserialize(rawComponent, (Type)((Object)Style.class))) != null) {
                component.setStyle(newStyle);
            }
            return component;
        }
        throw new JsonParseException("Don't know how to turn " + json + " into a Component");
    }
}

