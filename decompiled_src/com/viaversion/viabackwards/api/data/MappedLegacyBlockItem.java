/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.util.IdAndData;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappedLegacyBlockItem {
    final int id;
    final short data;
    final String name;
    final IdAndData block;
    BlockEntityHandler blockEntityHandler;

    public MappedLegacyBlockItem(int id) {
        this(id, -1, null, Type.ITEM);
    }

    public MappedLegacyBlockItem(int id, short data, @Nullable String name, Type type) {
        String string;
        this.id = id;
        this.data = data;
        if (name != null) {
            String string2 = name;
            string = "\u00a7f" + string2;
        } else {
            string = this.name = null;
        }
        this.block = type != Type.ITEM ? (data != -1 ? new IdAndData(id, data) : new IdAndData(id)) : null;
    }

    public int getId() {
        return this.id;
    }

    public short getData() {
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public IdAndData getBlock() {
        return this.block;
    }

    public boolean hasBlockEntityHandler() {
        return this.blockEntityHandler != null;
    }

    public @Nullable BlockEntityHandler getBlockEntityHandler() {
        return this.blockEntityHandler;
    }

    public void setBlockEntityHandler(@Nullable BlockEntityHandler blockEntityHandler) {
        this.blockEntityHandler = blockEntityHandler;
    }

    public static enum Type {
        ITEM("items"),
        BLOCK_ITEM("block-items"),
        BLOCK("blocks");

        final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    @FunctionalInterface
    public static interface BlockEntityHandler {
        public void handleCompoundTag(int var1, CompoundTag var2);
    }
}

