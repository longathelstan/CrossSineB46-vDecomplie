/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtStyleSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.json.JsonHoverEventSerializer_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.json.JsonStyleSerializer_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_5.nbt.NbtHoverEventSerializer_v1_20_5;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2.json.JsonTextSerializer_v1_21_2;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2.nbt.NbtTextSerializer_v1_21_2;

public class TextComponentCodec_v1_21_2
extends TextComponentCodec_v1_20_5 {
    public TextComponentCodec_v1_21_2() {
        super(() -> SNbtSerializer.V1_14, (codec, sNbtSerializer) -> new JsonTextSerializer_v1_21_2((TextComponentCodec_v1_21_2)codec, textSerializer -> new JsonStyleSerializer_v1_20_5(styleSerializer -> new JsonHoverEventSerializer_v1_20_5((TextComponentCodec_v1_20_5)codec, (ITypedSerializer<JsonElement, ATextComponent>)textSerializer, (SNbtSerializer<CompoundTag>)sNbtSerializer))), (codec, sNbtSerializer) -> new NbtTextSerializer_v1_21_2((TextComponentCodec_v1_21_2)codec, textSerializer -> new NbtStyleSerializer_v1_20_3(styleSerializer -> new NbtHoverEventSerializer_v1_20_5((TextComponentCodec_v1_20_5)codec, (ITypedSerializer<Tag, ATextComponent>)textSerializer, (SNbtSerializer<CompoundTag>)sNbtSerializer))));
    }

    public void verifyEntitySelector(String selector) {
    }
}

