/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class PaintingVariant {
    final int width;
    final int height;
    final String assetId;
    public static HolderType<PaintingVariant> TYPE = new HolderType<PaintingVariant>(){

        @Override
        public PaintingVariant readDirect(ByteBuf buffer) {
            int width = Types.VAR_INT.readPrimitive(buffer);
            int height = Types.VAR_INT.readPrimitive(buffer);
            String assetId = (String)Types.STRING.read(buffer);
            return new PaintingVariant(width, height, assetId);
        }

        @Override
        public void writeDirect(ByteBuf buffer, PaintingVariant variant) {
            Types.VAR_INT.writePrimitive(buffer, variant.width());
            Types.VAR_INT.writePrimitive(buffer, variant.height());
            Types.STRING.write(buffer, variant.assetId());
        }
    };

    public PaintingVariant(int width, int height, String assetId) {
        this.width = width;
        this.height = height;
        this.assetId = assetId;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public String assetId() {
        return this.assetId;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PaintingVariant)) {
            return false;
        }
        PaintingVariant paintingVariant = (PaintingVariant)object;
        return this.width == paintingVariant.width && this.height == paintingVariant.height && Objects.equals(this.assetId, paintingVariant.assetId);
    }

    public int hashCode() {
        return ((0 * 31 + Integer.hashCode(this.width)) * 31 + Integer.hashCode(this.height)) * 31 + Objects.hashCode(this.assetId);
    }

    public String toString() {
        return String.format("%s[width=%s, height=%s, assetId=%s]", this.getClass().getSimpleName(), Integer.toString(this.width), Integer.toString(this.height), Objects.toString(this.assetId));
    }
}

