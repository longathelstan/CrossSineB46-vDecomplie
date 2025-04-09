/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class ArmorTrimPattern {
    final String assetName;
    final int itemId;
    final Tag description;
    final boolean decal;
    public static final HolderType<ArmorTrimPattern> TYPE = new HolderType<ArmorTrimPattern>(){

        @Override
        public ArmorTrimPattern readDirect(ByteBuf buffer) {
            String assetName = (String)Types.STRING.read(buffer);
            int itemId = Types.VAR_INT.readPrimitive(buffer);
            Tag description = (Tag)Types.TAG.read(buffer);
            boolean decal = buffer.readBoolean();
            return new ArmorTrimPattern(assetName, itemId, description, decal);
        }

        @Override
        public void writeDirect(ByteBuf buffer, ArmorTrimPattern value) {
            Types.STRING.write(buffer, value.assetName());
            Types.VAR_INT.writePrimitive(buffer, value.itemId());
            Types.TAG.write(buffer, value.description());
            buffer.writeBoolean(value.decal());
        }
    };

    public ArmorTrimPattern(String assetName, int itemId, Tag description, boolean decal) {
        this.assetName = assetName;
        this.itemId = itemId;
        this.description = description;
        this.decal = decal;
    }

    public ArmorTrimPattern rewrite(Int2IntFunction idRewriteFunction) {
        return new ArmorTrimPattern(this.assetName, idRewriteFunction.applyAsInt(this.itemId), this.description, this.decal);
    }

    public String assetName() {
        return this.assetName;
    }

    public int itemId() {
        return this.itemId;
    }

    public Tag description() {
        return this.description;
    }

    public boolean decal() {
        return this.decal;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ArmorTrimPattern)) {
            return false;
        }
        ArmorTrimPattern armorTrimPattern = (ArmorTrimPattern)object;
        return Objects.equals(this.assetName, armorTrimPattern.assetName) && this.itemId == armorTrimPattern.itemId && Objects.equals(this.description, armorTrimPattern.description) && this.decal == armorTrimPattern.decal;
    }

    public int hashCode() {
        return (((0 * 31 + Objects.hashCode(this.assetName)) * 31 + Integer.hashCode(this.itemId)) * 31 + Objects.hashCode(this.description)) * 31 + Boolean.hashCode(this.decal);
    }

    public String toString() {
        return String.format("%s[assetName=%s, itemId=%s, description=%s, decal=%s]", this.getClass().getSimpleName(), Objects.toString(this.assetName), Integer.toString(this.itemId), Objects.toString(this.description), Boolean.toString(this.decal));
    }
}

