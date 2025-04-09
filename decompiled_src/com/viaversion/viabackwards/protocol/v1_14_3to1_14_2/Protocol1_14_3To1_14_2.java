/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14_3to1_14_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public class Protocol1_14_3To1_14_2
extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
    public Protocol1_14_3To1_14_2() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPackets1_14.MERCHANT_OFFERS, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.ITEM1_13_2);
                wrapper.passthrough(Types.ITEM1_13_2);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Types.ITEM1_13_2);
                }
                wrapper.passthrough(Types.BOOLEAN);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.FLOAT);
            }
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.read(Types.BOOLEAN);
        });
        RecipeRewriter<ClientboundPackets1_14> recipeHandler = new RecipeRewriter<ClientboundPackets1_14>(this);
        this.registerClientbound(ClientboundPackets1_14.UPDATE_RECIPES, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            int deleted = 0;
            for (int i = 0; i < size; ++i) {
                String fullType = wrapper.read(Types.STRING);
                String type = Key.stripMinecraftNamespace(fullType);
                String id = wrapper.read(Types.STRING);
                if (type.equals("crafting_special_repairitem")) {
                    ++deleted;
                    continue;
                }
                wrapper.write(Types.STRING, fullType);
                wrapper.write(Types.STRING, id);
                recipeHandler.handleRecipeType(wrapper, type);
            }
            wrapper.set(Types.VAR_INT, 0, size - deleted);
        });
    }
}

