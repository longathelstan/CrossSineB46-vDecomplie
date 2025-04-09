/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.RecipeRewriter1_19_4;

public class RecipeRewriter1_20_3<C extends ClientboundPacketType>
extends RecipeRewriter1_19_4<C> {
    public RecipeRewriter1_20_3(Protocol<C, ?, ?, ?> protocol) {
        super(protocol);
    }

    @Override
    public void handleCraftingShaped(PacketWrapper wrapper) {
        wrapper.passthrough(Types.STRING);
        wrapper.passthrough(Types.VAR_INT);
        int ingredients = wrapper.passthrough(Types.VAR_INT) * wrapper.passthrough(Types.VAR_INT);
        for (int i = 0; i < ingredients; ++i) {
            this.handleIngredient(wrapper);
        }
        Item item = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), item);
        wrapper.passthrough(Types.BOOLEAN);
    }

    @Override
    protected Type<Item> itemType() {
        return this.protocol.getItemRewriter().itemType();
    }

    @Override
    protected Type<Item[]> itemArrayType() {
        return this.protocol.getItemRewriter().itemArrayType();
    }

    @Override
    protected Type<Item> mappedItemType() {
        return this.protocol.getItemRewriter().mappedItemType();
    }

    @Override
    protected Type<Item[]> mappedItemArrayType() {
        return this.protocol.getItemRewriter().mappedItemArrayType();
    }
}

