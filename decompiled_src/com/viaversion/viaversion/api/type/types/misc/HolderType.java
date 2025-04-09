/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.type.types.misc;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;

public abstract class HolderType<T>
extends Type<Holder<T>> {
    protected HolderType() {
        super(Holder.class);
    }

    @Override
    public Holder<T> read(ByteBuf buffer) {
        int id = Types.VAR_INT.readPrimitive(buffer) - 1;
        if (id == -1) {
            return Holder.of(this.readDirect(buffer));
        }
        return Holder.of(id);
    }

    @Override
    public void write(ByteBuf buffer, Holder<T> object) {
        if (object.hasId()) {
            Types.VAR_INT.writePrimitive(buffer, object.id() + 1);
        } else {
            Types.VAR_INT.writePrimitive(buffer, 0);
            this.writeDirect(buffer, object.value());
        }
    }

    public abstract T readDirect(ByteBuf var1);

    public abstract void writeDirect(ByteBuf var1, T var2);
}

