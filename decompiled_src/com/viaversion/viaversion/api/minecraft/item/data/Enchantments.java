/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class Enchantments {
    final Int2IntMap enchantments;
    final boolean showInTooltip;
    public static final Type<Enchantments> TYPE = new Type<Enchantments>(Enchantments.class){

        @Override
        public Enchantments read(ByteBuf buffer) {
            Int2IntOpenHashMap enchantments = new Int2IntOpenHashMap();
            int size = Types.VAR_INT.readPrimitive(buffer);
            for (int i = 0; i < size; ++i) {
                int id = Types.VAR_INT.readPrimitive(buffer);
                int level = Types.VAR_INT.readPrimitive(buffer);
                enchantments.put(id, level);
            }
            return new Enchantments(enchantments, buffer.readBoolean());
        }

        @Override
        public void write(ByteBuf buffer, Enchantments value) {
            Types.VAR_INT.writePrimitive(buffer, value.enchantments.size());
            for (Int2IntMap.Entry entry : value.enchantments.int2IntEntrySet()) {
                Types.VAR_INT.writePrimitive(buffer, entry.getIntKey());
                Types.VAR_INT.writePrimitive(buffer, entry.getIntValue());
            }
            buffer.writeBoolean(value.showInTooltip());
        }
    };

    public Enchantments(boolean showInTooltip) {
        this(new Int2IntOpenHashMap(), showInTooltip);
    }

    public Enchantments(Int2IntMap enchantments, boolean showInTooltip) {
        this.enchantments = enchantments;
        this.showInTooltip = showInTooltip;
    }

    public int size() {
        return this.enchantments.size();
    }

    public void add(int id, int level) {
        this.enchantments.put(id, level);
    }

    public void remove(int id) {
        this.enchantments.remove(id);
    }

    public void clear() {
        this.enchantments.clear();
    }

    public int getLevel(int id) {
        return this.enchantments.getOrDefault(id, -1);
    }

    public Int2IntMap enchantments() {
        return this.enchantments;
    }

    public boolean showInTooltip() {
        return this.showInTooltip;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Enchantments)) {
            return false;
        }
        Enchantments enchantments = (Enchantments)object;
        return Objects.equals(this.enchantments, enchantments.enchantments) && this.showInTooltip == enchantments.showInTooltip;
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.enchantments)) * 31 + Boolean.hashCode(this.showInTooltip);
    }

    public String toString() {
        return String.format("%s[enchantments=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.enchantments), Boolean.toString(this.showInTooltip));
    }
}

