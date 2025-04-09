/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13_1to1_13.rewriter;

import com.viaversion.viabackwards.protocol.v1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public class ItemPacketRewriter1_13_1
extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_13, Protocol1_13_1To1_13> {
    public ItemPacketRewriter1_13_1(Protocol1_13_1To1_13 protocol) {
        super(protocol, Types.ITEM1_13, Types.ITEM1_13_SHORT_ARRAY);
    }

    @Override
    public void registerPackets() {
        this.registerCooldown(ClientboundPackets1_13.COOLDOWN);
        this.registerSetContent(ClientboundPackets1_13.CONTAINER_SET_CONTENT);
        this.registerSetSlot(ClientboundPackets1_13.CONTAINER_SET_SLOT);
        ((Protocol1_13_1To1_13)this.protocol).registerClientbound(ClientboundPackets1_13.CUSTOM_PAYLOAD, wrapper -> {
            String channel = wrapper.passthrough(Types.STRING);
            if (channel.equals("minecraft:trader_list")) {
                wrapper.passthrough(Types.INT);
                int size = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
                for (int i = 0; i < size; ++i) {
                    Item input = wrapper.passthrough(Types.ITEM1_13);
                    this.handleItemToClient(wrapper.user(), input);
                    Item output = wrapper.passthrough(Types.ITEM1_13);
                    this.handleItemToClient(wrapper.user(), output);
                    boolean secondItem = wrapper.passthrough(Types.BOOLEAN);
                    if (secondItem) {
                        Item second = wrapper.passthrough(Types.ITEM1_13);
                        this.handleItemToClient(wrapper.user(), second);
                    }
                    wrapper.passthrough(Types.BOOLEAN);
                    wrapper.passthrough(Types.INT);
                    wrapper.passthrough(Types.INT);
                }
            }
        });
        this.registerSetEquippedItem(ClientboundPackets1_13.SET_EQUIPPED_ITEM);
        this.registerContainerClick(ServerboundPackets1_13.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_13.SET_CREATIVE_MODE_SLOT);
        this.registerLevelParticles(ClientboundPackets1_13.LEVEL_PARTICLES, Types.FLOAT);
    }
}

