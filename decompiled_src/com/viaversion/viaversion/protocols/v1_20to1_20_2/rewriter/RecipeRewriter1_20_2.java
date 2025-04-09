/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20to1_20_2.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.RecipeRewriter1_19_4;

public class RecipeRewriter1_20_2<C extends ClientboundPacketType>
extends RecipeRewriter1_19_4<C> {
    public RecipeRewriter1_20_2(Protocol<C, ?, ?, ?> protocol) {
        super(protocol);
    }

    @Override
    protected Type<Item> itemType() {
        return Types.ITEM1_20_2;
    }

    @Override
    protected Type<Item[]> itemArrayType() {
        return Types.ITEM1_20_2_ARRAY;
    }
}

