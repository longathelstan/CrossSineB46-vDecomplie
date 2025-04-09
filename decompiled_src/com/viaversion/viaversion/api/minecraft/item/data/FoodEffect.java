/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.item.data.PotionEffect;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class FoodEffect {
    final PotionEffect effect;
    final float probability;
    public static final Type<FoodEffect> TYPE = new Type<FoodEffect>(FoodEffect.class){

        @Override
        public FoodEffect read(ByteBuf buffer) {
            PotionEffect effect = (PotionEffect)PotionEffect.TYPE.read(buffer);
            float probability = buffer.readFloat();
            return new FoodEffect(effect, probability);
        }

        @Override
        public void write(ByteBuf buffer, FoodEffect value) {
            PotionEffect.TYPE.write(buffer, value.effect);
            buffer.writeFloat(value.probability);
        }
    };
    public static final Type<FoodEffect[]> ARRAY_TYPE = new ArrayType<FoodEffect>(TYPE);

    public FoodEffect(PotionEffect effect, float probability) {
        this.effect = effect;
        this.probability = probability;
    }

    public PotionEffect effect() {
        return this.effect;
    }

    public float probability() {
        return this.probability;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FoodEffect)) {
            return false;
        }
        FoodEffect foodEffect = (FoodEffect)object;
        return Objects.equals(this.effect, foodEffect.effect) && Float.compare(this.probability, foodEffect.probability) == 0;
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.effect)) * 31 + Float.hashCode(this.probability);
    }

    public String toString() {
        return String.format("%s[effect=%s, probability=%s]", this.getClass().getSimpleName(), Objects.toString(this.effect), Float.toString(this.probability));
    }
}

