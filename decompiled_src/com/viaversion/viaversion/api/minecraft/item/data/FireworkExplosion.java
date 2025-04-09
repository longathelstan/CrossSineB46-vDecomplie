/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class FireworkExplosion {
    final int shape;
    final int[] colors;
    final int[] fadeColors;
    final boolean hasTrail;
    final boolean hasTwinkle;
    public static final Type<FireworkExplosion> TYPE = new Type<FireworkExplosion>(FireworkExplosion.class){

        @Override
        public FireworkExplosion read(ByteBuf buffer) {
            int shape = Types.VAR_INT.readPrimitive(buffer);
            int[] colors = (int[])Types.INT_ARRAY_PRIMITIVE.read(buffer);
            int[] fadeColors = (int[])Types.INT_ARRAY_PRIMITIVE.read(buffer);
            boolean hasTrail = buffer.readBoolean();
            boolean hasTwinkle = buffer.readBoolean();
            return new FireworkExplosion(shape, colors, fadeColors, hasTrail, hasTwinkle);
        }

        @Override
        public void write(ByteBuf buffer, FireworkExplosion value) {
            Types.VAR_INT.writePrimitive(buffer, value.shape);
            Types.INT_ARRAY_PRIMITIVE.write(buffer, value.colors);
            Types.INT_ARRAY_PRIMITIVE.write(buffer, value.fadeColors);
            buffer.writeBoolean(value.hasTrail);
            buffer.writeBoolean(value.hasTwinkle);
        }
    };
    public static final Type<FireworkExplosion[]> ARRAY_TYPE = new ArrayType<FireworkExplosion>(TYPE);

    public FireworkExplosion(int shape, int[] colors, int[] fadeColors, boolean hasTrail, boolean hasTwinkle) {
        this.shape = shape;
        this.colors = colors;
        this.fadeColors = fadeColors;
        this.hasTrail = hasTrail;
        this.hasTwinkle = hasTwinkle;
    }

    public int shape() {
        return this.shape;
    }

    public int[] colors() {
        return this.colors;
    }

    public int[] fadeColors() {
        return this.fadeColors;
    }

    public boolean hasTrail() {
        return this.hasTrail;
    }

    public boolean hasTwinkle() {
        return this.hasTwinkle;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof FireworkExplosion)) {
            return false;
        }
        FireworkExplosion fireworkExplosion = (FireworkExplosion)object;
        return this.shape == fireworkExplosion.shape && Objects.equals(this.colors, fireworkExplosion.colors) && Objects.equals(this.fadeColors, fireworkExplosion.fadeColors) && this.hasTrail == fireworkExplosion.hasTrail && this.hasTwinkle == fireworkExplosion.hasTwinkle;
    }

    public int hashCode() {
        return ((((0 * 31 + Integer.hashCode(this.shape)) * 31 + Objects.hashCode(this.colors)) * 31 + Objects.hashCode(this.fadeColors)) * 31 + Boolean.hashCode(this.hasTrail)) * 31 + Boolean.hashCode(this.hasTwinkle);
    }

    public String toString() {
        return String.format("%s[shape=%s, colors=%s, fadeColors=%s, hasTrail=%s, hasTwinkle=%s]", this.getClass().getSimpleName(), Integer.toString(this.shape), Objects.toString(this.colors), Objects.toString(this.fadeColors), Boolean.toString(this.hasTrail), Boolean.toString(this.hasTwinkle));
    }
}

