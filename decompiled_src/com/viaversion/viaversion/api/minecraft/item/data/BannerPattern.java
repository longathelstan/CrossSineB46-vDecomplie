/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class BannerPattern {
    final String assetId;
    final String translationKey;
    public static final HolderType<BannerPattern> TYPE = new HolderType<BannerPattern>(){

        @Override
        public BannerPattern readDirect(ByteBuf buffer) {
            String assetId = (String)Types.STRING.read(buffer);
            String translationKey = (String)Types.STRING.read(buffer);
            return new BannerPattern(assetId, translationKey);
        }

        @Override
        public void writeDirect(ByteBuf buffer, BannerPattern value) {
            Types.STRING.write(buffer, value.assetId);
            Types.STRING.write(buffer, value.translationKey);
        }
    };

    public BannerPattern(String assetId, String translationKey) {
        this.assetId = assetId;
        this.translationKey = translationKey;
    }

    public String assetId() {
        return this.assetId;
    }

    public String translationKey() {
        return this.translationKey;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BannerPattern)) {
            return false;
        }
        BannerPattern bannerPattern = (BannerPattern)object;
        return Objects.equals(this.assetId, bannerPattern.assetId) && Objects.equals(this.translationKey, bannerPattern.translationKey);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.assetId)) * 31 + Objects.hashCode(this.translationKey);
    }

    public String toString() {
        return String.format("%s[assetId=%s, translationKey=%s]", this.getClass().getSimpleName(), Objects.toString(this.assetId), Objects.toString(this.translationKey));
    }
}

