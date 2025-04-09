/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2.json;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.SelectorComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes.IStyleSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json.JsonTextSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.json.JsonTextSerializer_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2.TextComponentCodec_v1_21_2;
import java.util.function.Function;

public class JsonTextSerializer_v1_21_2
extends JsonTextSerializer_v1_20_5 {
    private final TextComponentCodec_v1_21_2 codec;

    public JsonTextSerializer_v1_21_2(TextComponentCodec_v1_21_2 codec, Function<JsonTextSerializer_v1_20_3, IStyleSerializer<JsonElement>> styleSerializer) {
        super(styleSerializer);
        this.codec = codec;
    }

    @Override
    public ATextComponent deserialize(JsonElement object) {
        ATextComponent textComponent = super.deserialize(object);
        if (textComponent instanceof SelectorComponent) {
            SelectorComponent selectorComponent = (SelectorComponent)textComponent;
            this.codec.verifyEntitySelector(selectorComponent.getSelector());
        }
        return textComponent;
    }
}

