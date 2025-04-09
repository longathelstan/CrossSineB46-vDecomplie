/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.item.data.FireworkExplosion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class Fireworks {
    final int flightDuration;
    final FireworkExplosion[] explosions;
    public static final Type<Fireworks> TYPE = new Type<Fireworks>(Fireworks.class){

        @Override
        public Fireworks read(ByteBuf buffer) {
            int flightDuration = Types.VAR_INT.readPrimitive(buffer);
            FireworkExplosion[] explosions = (FireworkExplosion[])FireworkExplosion.ARRAY_TYPE.read(buffer);
            return new Fireworks(flightDuration, explosions);
        }

        @Override
        public void write(ByteBuf buffer, Fireworks value) {
            Types.VAR_INT.writePrimitive(buffer, value.flightDuration);
            FireworkExplosion.ARRAY_TYPE.write(buffer, value.explosions);
        }
    };

    public Fireworks(int flightDuration, FireworkExplosion[] explosions) {
        this.flightDuration = flightDuration;
        this.explosions = explosions;
    }

    public int flightDuration() {
        return this.flightDuration;
    }

    public FireworkExplosion[] explosions() {
        return this.explosions;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Fireworks)) {
            return false;
        }
        Fireworks fireworks = (Fireworks)object;
        return this.flightDuration == fireworks.flightDuration && Objects.equals(this.explosions, fireworks.explosions);
    }

    public int hashCode() {
        return (0 * 31 + Integer.hashCode(this.flightDuration)) * 31 + Objects.hashCode(this.explosions);
    }

    public String toString() {
        return String.format("%s[flightDuration=%s, explosions=%s]", this.getClass().getSimpleName(), Integer.toString(this.flightDuration), Objects.toString(this.explosions));
    }
}

