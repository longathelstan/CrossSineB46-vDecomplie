/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;

public final class SuspiciousStewEffect {
    final int mobEffect;
    final int duration;
    public static final Type<SuspiciousStewEffect> TYPE = new Type<SuspiciousStewEffect>(SuspiciousStewEffect.class){

        @Override
        public SuspiciousStewEffect read(ByteBuf buffer) {
            int effect = Types.VAR_INT.readPrimitive(buffer);
            int duration = Types.VAR_INT.readPrimitive(buffer);
            return new SuspiciousStewEffect(effect, duration);
        }

        @Override
        public void write(ByteBuf buffer, SuspiciousStewEffect value) {
            Types.VAR_INT.writePrimitive(buffer, value.mobEffect);
            Types.VAR_INT.writePrimitive(buffer, value.duration);
        }
    };
    public static final Type<SuspiciousStewEffect[]> ARRAY_TYPE = new ArrayType<SuspiciousStewEffect>(TYPE);

    public SuspiciousStewEffect(int mobEffect, int duration) {
        this.mobEffect = mobEffect;
        this.duration = duration;
    }

    public int mobEffect() {
        return this.mobEffect;
    }

    public int duration() {
        return this.duration;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SuspiciousStewEffect)) {
            return false;
        }
        SuspiciousStewEffect suspiciousStewEffect = (SuspiciousStewEffect)object;
        return this.mobEffect == suspiciousStewEffect.mobEffect && this.duration == suspiciousStewEffect.duration;
    }

    public int hashCode() {
        return (0 * 31 + Integer.hashCode(this.mobEffect)) * 31 + Integer.hashCode(this.duration);
    }

    public String toString() {
        return String.format("%s[mobEffect=%s, duration=%s]", this.getClass().getSimpleName(), Integer.toString(this.mobEffect), Integer.toString(this.duration));
    }
}

