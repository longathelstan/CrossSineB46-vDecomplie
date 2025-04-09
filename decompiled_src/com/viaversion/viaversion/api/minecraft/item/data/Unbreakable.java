/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public final class Unbreakable {
    final boolean showInTooltip;
    public static final Type<Unbreakable> TYPE = new Type<Unbreakable>(Unbreakable.class){

        @Override
        public Unbreakable read(ByteBuf buffer) {
            return new Unbreakable(buffer.readBoolean());
        }

        @Override
        public void write(ByteBuf buffer, Unbreakable value) {
            buffer.writeBoolean(value.showInTooltip());
        }
    };

    public Unbreakable(boolean showInTooltip) {
        this.showInTooltip = showInTooltip;
    }

    public boolean showInTooltip() {
        return this.showInTooltip;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Unbreakable)) {
            return false;
        }
        Unbreakable unbreakable = (Unbreakable)object;
        return this.showInTooltip == unbreakable.showInTooltip;
    }

    public int hashCode() {
        return 0 * 31 + Boolean.hashCode(this.showInTooltip);
    }

    public String toString() {
        return String.format("%s[showInTooltip=%s]", this.getClass().getSimpleName(), Boolean.toString(this.showInTooltip));
    }
}

