/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_9_3to1_10.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_3to1_10.Protocol1_9_3To1_10;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public class ItemPacketRewriter1_10
extends ItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_9_3To1_10> {
    public ItemPacketRewriter1_10(Protocol1_9_3To1_10 protocol) {
        super(protocol, Types.ITEM1_8, Types.ITEM1_8_SHORT_ARRAY);
    }

    @Override
    public void registerPackets() {
        this.registerSetCreativeModeSlot(ServerboundPackets1_9_3.SET_CREATIVE_MODE_SLOT);
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        boolean newItem;
        if (item == null) {
            return null;
        }
        boolean bl = newItem = item.identifier() >= 213 && item.identifier() <= 217;
        if (newItem) {
            item.setIdentifier(1);
            item.setData((short)0);
        }
        return item;
    }
}

