/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class Bee {
    final CompoundTag entityData;
    final int ticksInHive;
    final int minTicksInHive;
    public static final Type<Bee> TYPE = new Type<Bee>(Bee.class){

        @Override
        public Bee read(ByteBuf buffer) {
            CompoundTag entityData = (CompoundTag)Types.COMPOUND_TAG.read(buffer);
            int ticksInHive = Types.VAR_INT.readPrimitive(buffer);
            int minTicksInHive = Types.VAR_INT.readPrimitive(buffer);
            return new Bee(entityData, ticksInHive, minTicksInHive);
        }

        @Override
        public void write(ByteBuf buffer, Bee value) {
            Types.COMPOUND_TAG.write(buffer, value.entityData);
            Types.VAR_INT.writePrimitive(buffer, value.ticksInHive);
            Types.VAR_INT.writePrimitive(buffer, value.minTicksInHive);
        }
    };
    public static final Type<Bee[]> ARRAY_TYPE = new ArrayType<Bee>(TYPE);

    public Bee(CompoundTag entityData, int ticksInHive, int minTicksInHive) {
        this.entityData = entityData;
        this.ticksInHive = ticksInHive;
        this.minTicksInHive = minTicksInHive;
    }

    public CompoundTag entityData() {
        return this.entityData;
    }

    public int ticksInHive() {
        return this.ticksInHive;
    }

    public int minTicksInHive() {
        return this.minTicksInHive;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Bee)) {
            return false;
        }
        Bee bee = (Bee)object;
        return Objects.equals(this.entityData, bee.entityData) && this.ticksInHive == bee.ticksInHive && this.minTicksInHive == bee.minTicksInHive;
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.entityData)) * 31 + Integer.hashCode(this.ticksInHive)) * 31 + Integer.hashCode(this.minTicksInHive);
    }

    public String toString() {
        return String.format("%s[entityData=%s, ticksInHive=%s, minTicksInHive=%s]", this.getClass().getSimpleName(), Objects.toString(this.entityData), Integer.toString(this.ticksInHive), Integer.toString(this.minTicksInHive));
    }
}

