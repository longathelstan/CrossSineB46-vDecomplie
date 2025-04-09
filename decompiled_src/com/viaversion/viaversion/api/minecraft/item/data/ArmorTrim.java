/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrimMaterial;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrimPattern;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class ArmorTrim {
    final Holder<ArmorTrimMaterial> material;
    final Holder<ArmorTrimPattern> pattern;
    final boolean showInTooltip;
    public static final Type<ArmorTrim> TYPE = new Type<ArmorTrim>(ArmorTrim.class){

        @Override
        public ArmorTrim read(ByteBuf buffer) {
            Object material = ArmorTrimMaterial.TYPE.read(buffer);
            Object pattern = ArmorTrimPattern.TYPE.read(buffer);
            boolean showInTooltip = buffer.readBoolean();
            return new ArmorTrim((Holder<ArmorTrimMaterial>)material, (Holder<ArmorTrimPattern>)pattern, showInTooltip);
        }

        @Override
        public void write(ByteBuf buffer, ArmorTrim value) {
            ArmorTrimMaterial.TYPE.write(buffer, value.material);
            ArmorTrimPattern.TYPE.write(buffer, value.pattern);
            buffer.writeBoolean(value.showInTooltip);
        }
    };

    public ArmorTrim(Holder<ArmorTrimMaterial> material, Holder<ArmorTrimPattern> pattern, boolean showInTooltip) {
        this.material = material;
        this.pattern = pattern;
        this.showInTooltip = showInTooltip;
    }

    public ArmorTrim rewrite(Int2IntFunction idRewriteFunction) {
        Holder<ArmorTrimPattern> pattern;
        Holder<ArmorTrimMaterial> material = this.material;
        if (material.isDirect()) {
            material = Holder.of(material.value().rewrite(idRewriteFunction));
        }
        if ((pattern = this.pattern).isDirect()) {
            pattern = Holder.of(pattern.value().rewrite(idRewriteFunction));
        }
        return new ArmorTrim(material, pattern, this.showInTooltip);
    }

    public Holder<ArmorTrimMaterial> material() {
        return this.material;
    }

    public Holder<ArmorTrimPattern> pattern() {
        return this.pattern;
    }

    public boolean showInTooltip() {
        return this.showInTooltip;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ArmorTrim)) {
            return false;
        }
        ArmorTrim armorTrim = (ArmorTrim)object;
        return Objects.equals(this.material, armorTrim.material) && Objects.equals(this.pattern, armorTrim.pattern) && this.showInTooltip == armorTrim.showInTooltip;
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.material)) * 31 + Objects.hashCode(this.pattern)) * 31 + Boolean.hashCode(this.showInTooltip);
    }

    public String toString() {
        return String.format("%s[material=%s, pattern=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.material), Objects.toString(this.pattern), Boolean.toString(this.showInTooltip));
    }
}

