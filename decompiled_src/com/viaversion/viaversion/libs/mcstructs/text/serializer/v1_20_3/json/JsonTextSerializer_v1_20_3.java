/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.KeybindComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.NbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.ScoreComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.SelectorComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.BlockNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.EntityNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.StorageNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes.IStyleSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes.ITextComponentSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.CodecUtils_v1_20_3;
import java.util.Map;
import java.util.function.Function;

public class JsonTextSerializer_v1_20_3
implements ITextComponentSerializer<JsonElement>,
CodecUtils_v1_20_3 {
    private final IStyleSerializer<JsonElement> styleSerializer;

    public JsonTextSerializer_v1_20_3(Function<JsonTextSerializer_v1_20_3, IStyleSerializer<JsonElement>> styleSerializer) {
        this.styleSerializer = styleSerializer.apply(this);
    }

    @Override
    public IStyleSerializer<JsonElement> getStyleSerializer() {
        return this.styleSerializer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonElement serialize(ATextComponent object) {
        ATextComponent component;
        JsonObject out = new JsonObject();
        if (object instanceof StringComponent) {
            component = (StringComponent)object;
            if (component.getSiblings().isEmpty() && component.getStyle().isEmpty()) {
                return new JsonPrimitive(((StringComponent)component).getText());
            }
            out.addProperty("text", ((StringComponent)component).getText());
        } else if (object instanceof TranslationComponent) {
            component = (TranslationComponent)object;
            out.addProperty("translate", ((TranslationComponent)component).getKey());
            if (((TranslationComponent)component).getFallback() != null) {
                out.addProperty("fallback", ((TranslationComponent)component).getFallback());
            }
            if (((TranslationComponent)component).getArgs().length > 0) {
                JsonArray args2 = new JsonArray();
                for (Object arg : ((TranslationComponent)component).getArgs()) {
                    args2.add(this.convert(arg));
                }
                out.add("with", args2);
            }
        } else if (object instanceof KeybindComponent) {
            component = (KeybindComponent)object;
            out.addProperty("keybind", ((KeybindComponent)component).getKeybind());
        } else if (object instanceof ScoreComponent) {
            component = (ScoreComponent)object;
            JsonObject score = new JsonObject();
            score.addProperty("name", ((ScoreComponent)component).getName());
            score.addProperty("objective", ((ScoreComponent)component).getObjective());
            out.add("score", score);
        } else if (object instanceof SelectorComponent) {
            component = (SelectorComponent)object;
            out.addProperty("selector", ((SelectorComponent)component).getSelector());
            if (((SelectorComponent)component).getSeparator() != null) {
                out.add("separator", this.serialize(((SelectorComponent)component).getSeparator()));
            }
        } else {
            if (!(object instanceof NbtComponent)) throw new IllegalArgumentException("Unknown component type: " + object.getClass().getName());
            component = (NbtComponent)object;
            out.addProperty("nbt", ((NbtComponent)component).getComponent());
            if (((NbtComponent)component).isResolve()) {
                out.addProperty("interpret", true);
            }
            if (component instanceof EntityNbtComponent) {
                EntityNbtComponent entityComponent = (EntityNbtComponent)component;
                out.addProperty("entity", entityComponent.getSelector());
            } else if (component instanceof BlockNbtComponent) {
                BlockNbtComponent blockNbtComponent = (BlockNbtComponent)component;
                out.addProperty("block", blockNbtComponent.getPos());
            } else {
                if (!(component instanceof StorageNbtComponent)) throw new IllegalArgumentException("Unknown Nbt component type: " + component.getClass().getName());
                StorageNbtComponent storageNbtComponent = (StorageNbtComponent)component;
                out.addProperty("storage", storageNbtComponent.getId().get());
            }
        }
        JsonObject style = ((JsonElement)this.styleSerializer.serialize(object.getStyle())).getAsJsonObject();
        if (!style.isEmpty()) {
            for (Map.Entry<String, JsonElement> entry : style.entrySet()) {
                out.add(entry.getKey(), entry.getValue());
            }
        }
        if (object.getSiblings().isEmpty()) return out;
        JsonArray siblings = new JsonArray();
        for (ATextComponent sibling : object.getSiblings()) {
            siblings.add(this.serialize(sibling));
        }
        out.add("extra", siblings);
        return out;
    }

    protected JsonElement convert(Object object) {
        if (object instanceof Boolean) {
            return new JsonPrimitive((Boolean)object);
        }
        if (object instanceof Number) {
            return new JsonPrimitive((Number)object);
        }
        if (object instanceof String) {
            return new JsonPrimitive((String)object);
        }
        if (object instanceof ATextComponent) {
            return this.serialize((ATextComponent)object);
        }
        throw new IllegalArgumentException("Unknown object type: " + object.getClass().getName());
    }

    @Override
    public ATextComponent deserialize(JsonElement object) {
        if (this.isString(object)) {
            return new StringComponent(object.getAsString());
        }
        if (object.isJsonArray()) {
            if (object.getAsJsonArray().isEmpty()) {
                throw new IllegalArgumentException("Empty json array");
            }
            JsonArray array = object.getAsJsonArray();
            ATextComponent[] components = new ATextComponent[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                components[i] = this.deserialize(array.get(i));
            }
            if (components.length == 1) {
                return components[0];
            }
            ATextComponent parent = components[0];
            for (int i = 1; i < components.length; ++i) {
                parent.append(components[i]);
            }
            return parent;
        }
        if (!object.isJsonObject()) {
            throw new IllegalArgumentException("Unknown component type: " + object.getClass().getSimpleName());
        }
        ATextComponent component = null;
        JsonObject obj = object.getAsJsonObject();
        String type = this.optionalString(obj, "type");
        if (this.containsString(obj, "text") && (type == null || type.equals("text"))) {
            component = new StringComponent(obj.get("text").getAsString());
        } else if (this.containsString(obj, "translate") && (type == null || type.equals("translatable"))) {
            String key = obj.get("translate").getAsString();
            String fallback = this.optionalString(obj, "fallback");
            if (obj.has("with")) {
                if (!this.containsArray(obj, "with")) {
                    throw new IllegalArgumentException("Expected json array for 'with' tag");
                }
                JsonArray with = obj.getAsJsonArray("with");
                Object[] args2 = new Object[with.size()];
                for (int i = 0; i < with.size(); ++i) {
                    JsonElement arg = with.get(i);
                    if (this.isNumber(arg)) {
                        if (arg.getAsJsonPrimitive().isNumber()) {
                            args2[i] = arg.getAsInt();
                            continue;
                        }
                        args2[i] = arg.getAsBoolean() ? 1 : 0;
                        continue;
                    }
                    args2[i] = this.isString(arg) ? arg.getAsString() : this.deserialize(arg);
                }
                component = new TranslationComponent(key, args2).setFallback(fallback);
            } else {
                component = new TranslationComponent(key, new Object[0]).setFallback(fallback);
            }
        } else if (this.containsString(obj, "keybind") && (type == null || type.equals("keybind"))) {
            component = new KeybindComponent(obj.get("keybind").getAsString());
        } else if (this.containsObject(obj, "score") && this.containsString(obj.getAsJsonObject("score"), "name") && this.containsString(obj.getAsJsonObject("score"), "objective") && (type == null || type.equals("score"))) {
            JsonObject score = obj.getAsJsonObject("score");
            String name = score.get("name").getAsString();
            String objective = score.get("objective").getAsString();
            component = new ScoreComponent(name, objective);
        } else if (this.containsString(obj, "selector") && (type == null || type.equals("selector"))) {
            String selector = obj.get("selector").getAsString();
            ATextComponent separator = null;
            if (obj.has("separator")) {
                separator = this.deserialize(obj.get("separator"));
            }
            component = new SelectorComponent(selector, separator);
        } else if (this.containsString(obj, "nbt") && (type == null || type.equals("nbt"))) {
            String nbt = obj.get("nbt").getAsString();
            boolean interpret = Boolean.TRUE.equals(this.optionalBoolean(obj, "interpret"));
            ATextComponent separator = null;
            if (obj.has("separator")) {
                try {
                    separator = this.deserialize(obj.get("separator"));
                }
                catch (Throwable args2) {
                    // empty catch block
                }
            }
            String source = this.optionalString(obj, "source");
            boolean typeFound = false;
            if (this.containsString(obj, "entity") && (source == null || source.equals("entity"))) {
                component = new EntityNbtComponent(nbt, interpret, separator, obj.get("entity").getAsString());
                typeFound = true;
            } else if (this.containsString(obj, "block") && (source == null || source.equals("block"))) {
                component = new BlockNbtComponent(nbt, interpret, separator, obj.get("block").getAsString());
                typeFound = true;
            } else if (this.containsString(obj, "storage") && (source == null || source.equals("storage"))) {
                try {
                    component = new StorageNbtComponent(nbt, interpret, separator, Identifier.of(obj.get("storage").getAsString()));
                    typeFound = true;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (!typeFound) {
                throw new IllegalArgumentException("Unknown Nbt component type: " + obj.getClass().getSimpleName());
            }
        } else {
            throw new IllegalArgumentException("Unknown component type: " + obj.getClass().getSimpleName());
        }
        Style style = (Style)this.styleSerializer.deserialize(obj);
        if (!style.isEmpty()) {
            component.setStyle(style);
        }
        if (obj.has("extra")) {
            if (!obj.has("extra") || !obj.get("extra").isJsonArray()) {
                throw new IllegalArgumentException("Expected json array for 'extra' tag");
            }
            JsonArray extraList = obj.getAsJsonArray("extra");
            if (extraList.isEmpty()) {
                throw new IllegalArgumentException("Empty extra json array");
            }
            ATextComponent[] extra = new ATextComponent[extraList.size()];
            for (int i = 0; i < extraList.size(); ++i) {
                extra[i] = this.deserialize(extraList.get(i));
            }
            component.append(extra);
        }
        return component;
    }
}

