/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2.nbt;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.SelectorComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.subtypes.IStyleSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt.NbtTextSerializer_v1_20_3;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_21_2.TextComponentCodec_v1_21_2;
import java.util.function.Function;

public class NbtTextSerializer_v1_21_2
extends NbtTextSerializer_v1_20_3 {
    private final TextComponentCodec_v1_21_2 codec;

    public NbtTextSerializer_v1_21_2(TextComponentCodec_v1_21_2 codec, Function<NbtTextSerializer_v1_20_3, IStyleSerializer<Tag>> styleSerializer) {
        super(styleSerializer);
        this.codec = codec;
    }

    @Override
    public ATextComponent deserialize(Tag object) {
        ATextComponent textComponent = super.deserialize(object);
        if (textComponent instanceof SelectorComponent) {
            SelectorComponent selectorComponent = (SelectorComponent)textComponent;
            this.codec.verifyEntitySelector(selectorComponent.getSelector());
        }
        return textComponent;
    }
}

