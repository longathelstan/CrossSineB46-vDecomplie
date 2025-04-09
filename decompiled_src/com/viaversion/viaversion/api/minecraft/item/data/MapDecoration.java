/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class MapDecoration {
    final String type;
    final double x;
    final double z;
    final float rotation;
    public static final Type<MapDecoration> TYPE = new Type<MapDecoration>(MapDecoration.class){

        @Override
        public MapDecoration read(ByteBuf buffer) {
            String type = (String)Types.STRING.read(buffer);
            double x = Types.DOUBLE.readPrimitive(buffer);
            double z = Types.DOUBLE.readPrimitive(buffer);
            float rotation = Types.FLOAT.readPrimitive(buffer);
            return new MapDecoration(type, x, z, rotation);
        }

        @Override
        public void write(ByteBuf buffer, MapDecoration value) {
            Types.STRING.write(buffer, value.type);
            buffer.writeDouble(value.x);
            buffer.writeDouble(value.z);
            buffer.writeFloat(value.rotation);
        }
    };

    public MapDecoration(String type, double x, double z, float rotation) {
        this.type = type;
        this.x = x;
        this.z = z;
        this.rotation = rotation;
    }

    public String type() {
        return this.type;
    }

    public double x() {
        return this.x;
    }

    public double z() {
        return this.z;
    }

    public float rotation() {
        return this.rotation;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MapDecoration)) {
            return false;
        }
        MapDecoration mapDecoration = (MapDecoration)object;
        return Objects.equals(this.type, mapDecoration.type) && Double.compare(this.x, mapDecoration.x) == 0 && Double.compare(this.z, mapDecoration.z) == 0 && Float.compare(this.rotation, mapDecoration.rotation) == 0;
    }

    public int hashCode() {
        return (((0 * 31 + Objects.hashCode(this.type)) * 31 + Double.hashCode(this.x)) * 31 + Double.hashCode(this.z)) * 31 + Float.hashCode(this.rotation);
    }

    public String toString() {
        return String.format("%s[type=%s, x=%s, z=%s, rotation=%s]", this.getClass().getSimpleName(), Objects.toString(this.type), Double.toString(this.x), Double.toString(this.z), Float.toString(this.rotation));
    }
}

