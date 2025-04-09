/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.FoodEffect;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class FoodProperties {
    final int nutrition;
    final float saturationModifier;
    final boolean canAlwaysEat;
    final float eatSeconds;
    final Item usingConvertsTo;
    final FoodEffect[] possibleEffects;
    public static final Type<FoodProperties> TYPE1_20_5 = new Type<FoodProperties>(FoodProperties.class){

        @Override
        public FoodProperties read(ByteBuf buffer) {
            int nutrition = Types.VAR_INT.readPrimitive(buffer);
            float saturationModifier = buffer.readFloat();
            boolean canAlwaysEat = buffer.readBoolean();
            float eatSeconds = buffer.readFloat();
            FoodEffect[] possibleEffects = (FoodEffect[])FoodEffect.ARRAY_TYPE.read(buffer);
            return new FoodProperties(nutrition, saturationModifier, canAlwaysEat, eatSeconds, null, possibleEffects);
        }

        @Override
        public void write(ByteBuf buffer, FoodProperties value) {
            Types.VAR_INT.writePrimitive(buffer, value.nutrition);
            buffer.writeFloat(value.saturationModifier);
            buffer.writeBoolean(value.canAlwaysEat);
            buffer.writeFloat(value.eatSeconds);
            FoodEffect.ARRAY_TYPE.write(buffer, value.possibleEffects);
        }
    };
    public static final Type<FoodProperties> TYPE1_21 = new Type<FoodProperties>(FoodProperties.class){

        @Override
        public FoodProperties read(ByteBuf buffer) {
            int nutrition = Types.VAR_INT.readPrimitive(buffer);
            float saturationModifier = buffer.readFloat();
            boolean canAlwaysEat = buffer.readBoolean();
            float eatSeconds = buffer.readFloat();
            Item usingConvertsTo = (Item)Types1_21.OPTIONAL_ITEM.read(buffer);
            FoodEffect[] possibleEffects = (FoodEffect[])FoodEffect.ARRAY_TYPE.read(buffer);
            return new FoodProperties(nutrition, saturationModifier, canAlwaysEat, eatSeconds, usingConvertsTo, possibleEffects);
        }

        @Override
        public void write(ByteBuf buffer, FoodProperties value) {
            Types.VAR_INT.writePrimitive(buffer, value.nutrition);
            buffer.writeFloat(value.saturationModifier);
            buffer.writeBoolean(value.canAlwaysEat);
            buffer.writeFloat(value.eatSeconds);
            Types1_21.OPTIONAL_ITEM.write(buffer, value.usingConvertsTo);
            FoodEffect.ARRAY_TYPE.write(buffer, value.possibleEffects);
        }
    };

    public FoodProperties(int nutrition, float saturationModifier, boolean canAlwaysEat, float eatSeconds, Item usingConvertsTo, FoodEffect[] possibleEffects) {
        this.nutrition = nutrition;
        this.saturationModifier = saturationModifier;
        this.canAlwaysEat = canAlwaysEat;
        this.eatSeconds = eatSeconds;
        this.usingConvertsTo = usingConvertsTo;
        this.possibleEffects = possibleEffects;
    }

    public int nutrition() {
        return this.nutrition;
    }

    public float saturationModifier() {
        return this.saturationModifier;
    }

    public boolean canAlwaysEat() {
        return this.canAlwaysEat;
    }

    public float eatSeconds() {
        return this.eatSeconds;
    }

    public Item usingConvertsTo() {
        return this.usingConvertsTo;
    }

    public FoodEffect[] possibleEffects() {
        return this.possibleEffects;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FoodProperties)) {
            return false;
        }
        FoodProperties foodProperties = (FoodProperties)object;
        return this.nutrition == foodProperties.nutrition && Float.compare(this.saturationModifier, foodProperties.saturationModifier) == 0 && this.canAlwaysEat == foodProperties.canAlwaysEat && Float.compare(this.eatSeconds, foodProperties.eatSeconds) == 0 && Objects.equals(this.usingConvertsTo, foodProperties.usingConvertsTo) && Objects.equals(this.possibleEffects, foodProperties.possibleEffects);
    }

    public int hashCode() {
        return (((((0 * 31 + Integer.hashCode(this.nutrition)) * 31 + Float.hashCode(this.saturationModifier)) * 31 + Boolean.hashCode(this.canAlwaysEat)) * 31 + Float.hashCode(this.eatSeconds)) * 31 + Objects.hashCode(this.usingConvertsTo)) * 31 + Objects.hashCode(this.possibleEffects);
    }

    public String toString() {
        return String.format("%s[nutrition=%s, saturationModifier=%s, canAlwaysEat=%s, eatSeconds=%s, usingConvertsTo=%s, possibleEffects=%s]", this.getClass().getSimpleName(), Integer.toString(this.nutrition), Float.toString(this.saturationModifier), Boolean.toString(this.canAlwaysEat), Float.toString(this.eatSeconds), Objects.toString(this.usingConvertsTo), Objects.toString(this.possibleEffects));
    }
}

