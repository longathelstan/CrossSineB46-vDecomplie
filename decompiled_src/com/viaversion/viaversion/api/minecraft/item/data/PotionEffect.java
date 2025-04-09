/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.item.data.PotionEffectData;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class PotionEffect {
    final int effect;
    final PotionEffectData effectData;
    public static final Type<PotionEffect> TYPE = new Type<PotionEffect>(PotionEffect.class){

        @Override
        public PotionEffect read(ByteBuf buffer) {
            int effect = Types.VAR_INT.readPrimitive(buffer);
            PotionEffectData effectData = (PotionEffectData)PotionEffectData.TYPE.read(buffer);
            return new PotionEffect(effect, effectData);
        }

        @Override
        public void write(ByteBuf buffer, PotionEffect value) {
            Types.VAR_INT.writePrimitive(buffer, value.effect);
            PotionEffectData.TYPE.write(buffer, value.effectData);
        }
    };
    public static final Type<PotionEffect[]> ARRAY_TYPE = new ArrayType<PotionEffect>(TYPE);

    public PotionEffect(int effect, PotionEffectData effectData) {
        this.effect = effect;
        this.effectData = effectData;
    }

    public int effect() {
        return this.effect;
    }

    public PotionEffectData effectData() {
        return this.effectData;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PotionEffect)) {
            return false;
        }
        PotionEffect potionEffect = (PotionEffect)object;
        return this.effect == potionEffect.effect && Objects.equals(this.effectData, potionEffect.effectData);
    }

    public int hashCode() {
        return (0 * 31 + Integer.hashCode(this.effect)) * 31 + Objects.hashCode(this.effectData);
    }

    public String toString() {
        return String.format("%s[effect=%s, effectData=%s]", this.getClass().getSimpleName(), Integer.toString(this.effect), Objects.toString(this.effectData));
    }
}

