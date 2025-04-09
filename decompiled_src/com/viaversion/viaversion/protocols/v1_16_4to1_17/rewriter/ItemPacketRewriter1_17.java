/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_16_4to1_17.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public final class ItemPacketRewriter1_17
extends ItemRewriter<ClientboundPackets1_16_2, ServerboundPackets1_17, Protocol1_16_4To1_17> {
    public ItemPacketRewriter1_17(Protocol1_16_4To1_17 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    public void registerPackets() {
        this.registerCooldown(ClientboundPackets1_16_2.COOLDOWN);
        this.registerSetContent(ClientboundPackets1_16_2.CONTAINER_SET_CONTENT);
        this.registerMerchantOffers(ClientboundPackets1_16_2.MERCHANT_OFFERS);
        this.registerSetSlot(ClientboundPackets1_16_2.CONTAINER_SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_16_2.UPDATE_ADVANCEMENTS);
        this.registerSetEquipment(ClientboundPackets1_16_2.SET_EQUIPMENT);
        this.registerLevelParticles(ClientboundPackets1_16_2.LEVEL_PARTICLES, Types.DOUBLE);
        new RecipeRewriter<ClientboundPackets1_16_2>(this.protocol).register(ClientboundPackets1_16_2.UPDATE_RECIPES);
        this.registerSetCreativeModeSlot(ServerboundPackets1_17.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_17.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_17.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.handler(wrapper -> wrapper.write(Types.SHORT, (short)0));
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int length = wrapper.read(Types.VAR_INT);
                    for (int i = 0; i < length; ++i) {
                        wrapper.read(Types.SHORT);
                        wrapper.read(Types.ITEM1_13_2);
                    }
                    Item item = wrapper.read(Types.ITEM1_13_2);
                    int action = wrapper.get(Types.VAR_INT, 0);
                    if (action == 5 || action == 1) {
                        item = null;
                    } else {
                        ItemPacketRewriter1_17.this.handleItemToServer(wrapper.user(), item);
                    }
                    wrapper.write(Types.ITEM1_13_2, item);
                });
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_16_2.CONTAINER_ACK, null, wrapper -> {
            short inventoryId = wrapper.read(Types.UNSIGNED_BYTE);
            short confirmationId = wrapper.read(Types.SHORT);
            boolean accepted = wrapper.read(Types.BOOLEAN);
            if (!accepted) {
                int id = 0x40000000 | inventoryId << 16 | confirmationId & 0xFFFF;
                PacketWrapper pingPacket = wrapper.create(ClientboundPackets1_17.PING);
                pingPacket.write(Types.INT, id);
                pingPacket.send(Protocol1_16_4To1_17.class);
            }
            wrapper.cancel();
        });
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_17.PONG, null, wrapper -> {
            int id = wrapper.read(Types.INT);
            if ((id & 0x40000000) != 0) {
                short inventoryId = (short)(id >> 16 & 0xFF);
                short confirmationId = (short)(id & 0xFFFF);
                PacketWrapper packet = wrapper.create(ServerboundPackets1_16_2.CONTAINER_ACK);
                packet.write(Types.UNSIGNED_BYTE, inventoryId);
                packet.write(Types.SHORT, confirmationId);
                packet.write(Types.BOOLEAN, true);
                packet.sendToServer(Protocol1_16_4To1_17.class);
            }
            wrapper.cancel();
        });
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (item.identifier() == 733) {
            if (tag == null) {
                tag = new CompoundTag();
                item.setTag(tag);
            }
            if (tag.getNumberTag("map") == null) {
                tag.put("map", new IntTag(0));
            }
        }
        item.setIdentifier(((Protocol1_16_4To1_17)this.protocol).getMappingData().getNewItemId(item.identifier()));
        return item;
    }
}

