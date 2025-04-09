/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class ArmorTrimMaterial {
    final String assetName;
    final int itemId;
    final float itemModelIndex;
    final Int2ObjectMap<String> overrideArmorMaterials;
    final Tag description;
    public static final HolderType<ArmorTrimMaterial> TYPE = new HolderType<ArmorTrimMaterial>(){

        @Override
        public ArmorTrimMaterial readDirect(ByteBuf buffer) {
            String assetName = (String)Types.STRING.read(buffer);
            int item = Types.VAR_INT.readPrimitive(buffer);
            float itemModelIndex = buffer.readFloat();
            int overrideArmorMaterialsSize = Types.VAR_INT.readPrimitive(buffer);
            Int2ObjectOpenHashMap<String> overrideArmorMaterials = new Int2ObjectOpenHashMap<String>(overrideArmorMaterialsSize);
            for (int i = 0; i < overrideArmorMaterialsSize; ++i) {
                int key = Types.VAR_INT.readPrimitive(buffer);
                String value = (String)Types.STRING.read(buffer);
                overrideArmorMaterials.put(key, value);
            }
            Tag description = (Tag)Types.TAG.read(buffer);
            return new ArmorTrimMaterial(assetName, item, itemModelIndex, overrideArmorMaterials, description);
        }

        @Override
        public void writeDirect(ByteBuf buffer, ArmorTrimMaterial value) {
            Types.STRING.write(buffer, value.assetName());
            Types.VAR_INT.writePrimitive(buffer, value.itemId());
            buffer.writeFloat(value.itemModelIndex());
            Types.VAR_INT.writePrimitive(buffer, value.overrideArmorMaterials().size());
            for (Int2ObjectMap.Entry entry : value.overrideArmorMaterials().int2ObjectEntrySet()) {
                Types.VAR_INT.writePrimitive(buffer, entry.getIntKey());
                Types.STRING.write(buffer, (String)entry.getValue());
            }
            Types.TAG.write(buffer, value.description());
        }
    };

    public ArmorTrimMaterial(String assetName, int itemId, float itemModelIndex, Int2ObjectMap<String> overrideArmorMaterials, Tag description) {
        this.assetName = assetName;
        this.itemId = itemId;
        this.itemModelIndex = itemModelIndex;
        this.overrideArmorMaterials = overrideArmorMaterials;
        this.description = description;
    }

    public ArmorTrimMaterial rewrite(Int2IntFunction idRewriteFunction) {
        return new ArmorTrimMaterial(this.assetName, idRewriteFunction.applyAsInt(this.itemId), this.itemModelIndex, this.overrideArmorMaterials, this.description);
    }

    public String assetName() {
        return this.assetName;
    }

    public int itemId() {
        return this.itemId;
    }

    public float itemModelIndex() {
        return this.itemModelIndex;
    }

    public Int2ObjectMap<String> overrideArmorMaterials() {
        return this.overrideArmorMaterials;
    }

    public Tag description() {
        return this.description;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ArmorTrimMaterial)) {
            return false;
        }
        ArmorTrimMaterial armorTrimMaterial = (ArmorTrimMaterial)object;
        return Objects.equals(this.assetName, armorTrimMaterial.assetName) && this.itemId == armorTrimMaterial.itemId && Float.compare(this.itemModelIndex, armorTrimMaterial.itemModelIndex) == 0 && Objects.equals(this.overrideArmorMaterials, armorTrimMaterial.overrideArmorMaterials) && Objects.equals(this.description, armorTrimMaterial.description);
    }

    public int hashCode() {
        return ((((0 * 31 + Objects.hashCode(this.assetName)) * 31 + Integer.hashCode(this.itemId)) * 31 + Float.hashCode(this.itemModelIndex)) * 31 + Objects.hashCode(this.overrideArmorMaterials)) * 31 + Objects.hashCode(this.description);
    }

    public String toString() {
        return String.format("%s[assetName=%s, itemId=%s, itemModelIndex=%s, overrideArmorMaterials=%s, description=%s]", this.getClass().getSimpleName(), Objects.toString(this.assetName), Integer.toString(this.itemId), Float.toString(this.itemModelIndex), Objects.toString(this.overrideArmorMaterials), Objects.toString(this.description));
    }
}

