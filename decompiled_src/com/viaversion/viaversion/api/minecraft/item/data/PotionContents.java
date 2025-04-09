/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.item.data.PotionEffect;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class PotionContents {
    final @Nullable Integer potion;
    final @Nullable Integer customColor;
    final PotionEffect[] customEffects;
    public static final Type<PotionContents> TYPE = new Type<PotionContents>(PotionContents.class){

        @Override
        public PotionContents read(ByteBuf buffer) {
            Integer potion = buffer.readBoolean() ? Integer.valueOf(Types.VAR_INT.readPrimitive(buffer)) : null;
            Integer customColor = buffer.readBoolean() ? Integer.valueOf(buffer.readInt()) : null;
            PotionEffect[] customEffects = (PotionEffect[])PotionEffect.ARRAY_TYPE.read(buffer);
            return new PotionContents(potion, customColor, customEffects);
        }

        @Override
        public void write(ByteBuf buffer, PotionContents value) {
            buffer.writeBoolean(value.potion != null);
            if (value.potion != null) {
                Types.VAR_INT.writePrimitive(buffer, value.potion);
            }
            buffer.writeBoolean(value.customColor != null);
            if (value.customColor != null) {
                buffer.writeInt(value.customColor.intValue());
            }
            PotionEffect.ARRAY_TYPE.write(buffer, value.customEffects);
        }
    };

    public PotionContents(@Nullable Integer potion, @Nullable Integer customColor, PotionEffect[] customEffects) {
        this.potion = potion;
        this.customColor = customColor;
        this.customEffects = customEffects;
    }

    public @Nullable Integer potion() {
        return this.potion;
    }

    public @Nullable Integer customColor() {
        return this.customColor;
    }

    public PotionEffect[] customEffects() {
        return this.customEffects;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PotionContents)) {
            return false;
        }
        PotionContents potionContents = (PotionContents)object;
        return Objects.equals(this.potion, potionContents.potion) && Objects.equals(this.customColor, potionContents.customColor) && Objects.equals(this.customEffects, potionContents.customEffects);
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.potion)) * 31 + Objects.hashCode(this.customColor)) * 31 + Objects.hashCode(this.customEffects);
    }

    public String toString() {
        return String.format("%s[potion=%s, customColor=%s, customEffects=%s]", this.getClass().getSimpleName(), Objects.toString(this.potion), Objects.toString(this.customColor), Objects.toString(this.customEffects));
    }
}

