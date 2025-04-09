/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import io.netty.buffer.ByteBuf;

public final class PotDecorations {
    public static final Type<PotDecorations> TYPE = new Type<PotDecorations>(PotDecorations.class){

        @Override
        public PotDecorations read(ByteBuf buffer) {
            return new PotDecorations((int[])Types.VAR_INT_ARRAY_PRIMITIVE.read(buffer));
        }

        @Override
        public void write(ByteBuf buffer, PotDecorations value) {
            Types.VAR_INT_ARRAY_PRIMITIVE.write(buffer, value.itemIds());
        }
    };
    final int[] itemIds;

    public PotDecorations(int[] itemIds) {
        this.itemIds = itemIds;
    }

    public PotDecorations(int backItem, int leftItem, int rightItem, int frontItem) {
        this.itemIds = new int[]{backItem, leftItem, rightItem, frontItem};
    }

    public int[] itemIds() {
        return this.itemIds;
    }

    public int backItem() {
        return this.item(0);
    }

    public int leftItem() {
        return this.item(1);
    }

    public int rightItem() {
        return this.item(2);
    }

    public int frontItem() {
        return this.item(3);
    }

    int item(int index2) {
        return index2 < 0 || index2 >= this.itemIds.length ? -1 : this.itemIds[index2];
    }

    public PotDecorations rewrite(Int2IntFunction idRewriteFunction) {
        int[] newItems = new int[this.itemIds.length];
        for (int i = 0; i < this.itemIds.length; ++i) {
            newItems[i] = idRewriteFunction.applyAsInt(this.itemIds[i]);
        }
        return new PotDecorations(newItems);
    }
}

