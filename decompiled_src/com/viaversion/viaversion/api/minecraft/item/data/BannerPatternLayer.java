/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.item.data.BannerPattern;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class BannerPatternLayer {
    final Holder<BannerPattern> pattern;
    final int dyeColor;
    public static final Type<BannerPatternLayer> TYPE = new Type<BannerPatternLayer>(BannerPatternLayer.class){

        @Override
        public BannerPatternLayer read(ByteBuf buffer) {
            Object pattern = BannerPattern.TYPE.read(buffer);
            int color = Types.VAR_INT.readPrimitive(buffer);
            return new BannerPatternLayer((Holder<BannerPattern>)pattern, color);
        }

        @Override
        public void write(ByteBuf buffer, BannerPatternLayer value) {
            BannerPattern.TYPE.write(buffer, value.pattern);
            Types.VAR_INT.writePrimitive(buffer, value.dyeColor);
        }
    };
    public static final Type<BannerPatternLayer[]> ARRAY_TYPE = new ArrayType<BannerPatternLayer>(TYPE);

    public BannerPatternLayer(Holder<BannerPattern> pattern, int dyeColor) {
        this.pattern = pattern;
        this.dyeColor = dyeColor;
    }

    public Holder<BannerPattern> pattern() {
        return this.pattern;
    }

    public int dyeColor() {
        return this.dyeColor;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BannerPatternLayer)) {
            return false;
        }
        BannerPatternLayer bannerPatternLayer = (BannerPatternLayer)object;
        return Objects.equals(this.pattern, bannerPatternLayer.pattern) && this.dyeColor == bannerPatternLayer.dyeColor;
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.pattern)) * 31 + Integer.hashCode(this.dyeColor);
    }

    public String toString() {
        return String.format("%s[pattern=%s, dyeColor=%s]", this.getClass().getSimpleName(), Objects.toString(this.pattern), Integer.toString(this.dyeColor));
    }
}

