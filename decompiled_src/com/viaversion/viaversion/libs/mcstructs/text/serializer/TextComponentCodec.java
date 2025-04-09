/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtDeserializeException;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes.ITextComponentSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json.JsonHoverEventSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json.JsonStyleSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json.JsonTextSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtHoverEventSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtStyleSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtTextSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2.TextComponentCodec_v1_21_2;
import java.io.StringReader;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TextComponentCodec {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    public static final TextComponentCodec V1_20_3 = new TextComponentCodec(() -> SNbtSerializer.V1_14, (codec, sNbtSerializer) -> new JsonTextSerializer_v1_20_3(textSerializer -> new JsonStyleSerializer_v1_20_3(styleSerializer -> new JsonHoverEventSerializer_v1_20_3((TextComponentCodec)codec, (ITypedSerializer<JsonElement, ATextComponent>)textSerializer, (SNbtSerializer<CompoundTag>)sNbtSerializer))), (codec, sNbtSerializer) -> new NbtTextSerializer_v1_20_3(textSerializer -> new NbtStyleSerializer_v1_20_3(styleSerializer -> new NbtHoverEventSerializer_v1_20_3((TextComponentCodec)codec, (ITypedSerializer<Tag, ATextComponent>)textSerializer, (SNbtSerializer<CompoundTag>)sNbtSerializer))));
    public static final TextComponentCodec_v1_20_5 V1_20_5 = new TextComponentCodec_v1_20_5();
    public static final TextComponentCodec_v1_21_2 V1_21_2 = new TextComponentCodec_v1_21_2();
    public static final TextComponentCodec LATEST = V1_21_2;
    private final Supplier<SNbtSerializer<CompoundTag>> sNbtSerializerSupplier;
    private final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITextComponentSerializer<JsonElement>> jsonSerializerSupplier;
    private final BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITextComponentSerializer<Tag>> nbtSerializerSupplier;
    private SNbtSerializer<CompoundTag> sNbtSerializer;
    private ITextComponentSerializer<JsonElement> jsonSerializer;
    private ITextComponentSerializer<Tag> nbtSerializer;

    public TextComponentCodec(Supplier<SNbtSerializer<CompoundTag>> sNbtSerializerSupplier, BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITextComponentSerializer<JsonElement>> jsonSerializerSupplier, BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITextComponentSerializer<Tag>> nbtSerializerSupplier) {
        this.sNbtSerializerSupplier = sNbtSerializerSupplier;
        this.jsonSerializerSupplier = jsonSerializerSupplier;
        this.nbtSerializerSupplier = nbtSerializerSupplier;
    }

    private SNbtSerializer<CompoundTag> getSNbtSerializer() {
        if (this.sNbtSerializer == null) {
            this.sNbtSerializer = this.sNbtSerializerSupplier.get();
        }
        return this.sNbtSerializerSupplier.get();
    }

    public ITextComponentSerializer<JsonElement> getJsonSerializer() {
        if (this.jsonSerializer == null) {
            this.jsonSerializer = this.jsonSerializerSupplier.apply(this, this.getSNbtSerializer());
        }
        return this.jsonSerializer;
    }

    public ITextComponentSerializer<Tag> getNbtSerializer() {
        if (this.nbtSerializer == null) {
            this.nbtSerializer = this.nbtSerializerSupplier.apply(this, this.getSNbtSerializer());
        }
        return this.nbtSerializer;
    }

    public ATextComponent deserializeJson(String json) {
        return this.deserializeJsonTree(JsonParser.parseString(json));
    }

    public ATextComponent deserializeJsonReader(String json) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(false);
        try {
            return this.deserialize(Streams.parse(reader));
        }
        catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
        }
    }

    public ATextComponent deserializeLenientJson(String json) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return this.deserializeJsonTree(JsonParser.parseReader(reader));
    }

    public ATextComponent deserializeNbt(String nbt) {
        try {
            return this.deserialize(this.getSNbtSerializer().getDeserializer().deserializeValue(nbt));
        }
        catch (SNbtDeserializeException e) {
            throw new RuntimeException("Failed to deserialize SNbt", e);
        }
    }

    public ATextComponent deserializeJsonTree(@Nullable JsonElement element) {
        if (element == null) {
            return null;
        }
        return this.deserialize(element);
    }

    public ATextComponent deserializeNbtTree(@Nullable Tag nbt) {
        if (nbt == null) {
            return null;
        }
        return this.deserialize(nbt);
    }

    public ATextComponent deserialize(JsonElement json) {
        return (ATextComponent)this.getJsonSerializer().deserialize(json);
    }

    public ATextComponent deserialize(Tag nbt) {
        return (ATextComponent)this.getNbtSerializer().deserialize(nbt);
    }

    public JsonElement serializeJsonTree(ATextComponent component) {
        return (JsonElement)this.getJsonSerializer().serialize(component);
    }

    public Tag serializeNbt(ATextComponent component) {
        return (Tag)this.getNbtSerializer().serialize(component);
    }

    public String serializeJsonString(ATextComponent component) {
        return GSON.toJson(this.serializeJsonTree(component));
    }

    public String serializeNbtString(ATextComponent component) {
        try {
            return this.getSNbtSerializer().serialize(this.serializeNbt(component));
        }
        catch (SNbtSerializeException e) {
            throw new RuntimeException("Failed to serialize SNbt", e);
        }
    }

    public TextComponentSerializer asSerializer() {
        return new TextComponentSerializer(this, () -> new GsonBuilder().registerTypeHierarchyAdapter(ATextComponent.class, (src, typeOfSrc, context) -> this.serializeJsonTree((ATextComponent)src)).registerTypeHierarchyAdapter(ATextComponent.class, (src, typeOfSrc, context) -> this.deserializeJsonTree(src)).disableHtmlEscaping().create());
    }
}

