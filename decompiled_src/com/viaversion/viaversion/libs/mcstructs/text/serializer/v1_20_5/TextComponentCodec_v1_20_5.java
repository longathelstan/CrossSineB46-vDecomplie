/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentCodec;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes.ITextComponentSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtStyleSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtTextSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.json.JsonHoverEventSerializer_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.json.JsonStyleSerializer_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.json.JsonTextSerializer_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.nbt.NbtHoverEventSerializer_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.utils.JsonNbtConverter;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class TextComponentCodec_v1_20_5
extends TextComponentCodec {
    public TextComponentCodec_v1_20_5() {
        super(() -> SNbtSerializer.V1_14, (codec, sNbtSerializer) -> new JsonTextSerializer_v1_20_5(textSerializer -> new JsonStyleSerializer_v1_20_5(styleSerializer -> new JsonHoverEventSerializer_v1_20_5((TextComponentCodec_v1_20_5)codec, (ITypedSerializer<JsonElement, ATextComponent>)textSerializer, (SNbtSerializer<CompoundTag>)sNbtSerializer))), (codec, sNbtSerializer) -> new NbtTextSerializer_v1_20_3(textSerializer -> new NbtStyleSerializer_v1_20_3(styleSerializer -> new NbtHoverEventSerializer_v1_20_5((TextComponentCodec_v1_20_5)codec, (ITypedSerializer<Tag, ATextComponent>)textSerializer, (SNbtSerializer<CompoundTag>)sNbtSerializer))));
    }

    protected TextComponentCodec_v1_20_5(Supplier<SNbtSerializer<CompoundTag>> sNbtSerializerSupplier, BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITextComponentSerializer<JsonElement>> jsonSerializerSupplier, BiFunction<TextComponentCodec, SNbtSerializer<CompoundTag>, ITextComponentSerializer<Tag>> nbtSerializerSupplier) {
        super(sNbtSerializerSupplier, jsonSerializerSupplier, nbtSerializerSupplier);
    }

    public void verifyItem(Identifier identifier) {
    }

    public void verifyEntity(Identifier identifier) {
    }

    public void verifyItemComponents(Tag tag) {
    }

    public JsonObject convertItemComponents(CompoundTag tag) {
        return JsonNbtConverter.toJson(tag).getAsJsonObject();
    }

    public CompoundTag convertItemComponents(JsonObject json) {
        return (CompoundTag)JsonNbtConverter.toNbt(json);
    }
}

