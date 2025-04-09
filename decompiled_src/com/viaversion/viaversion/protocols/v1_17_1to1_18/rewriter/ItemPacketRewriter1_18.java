/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_17_1to1_18.rewriter;

import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viaversion.protocols.v1_17to1_17_1.packet.ClientboundPackets1_17_1;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public final class ItemPacketRewriter1_18
extends ItemRewriter<ClientboundPackets1_17_1, ServerboundPackets1_17, Protocol1_17_1To1_18> {
    public ItemPacketRewriter1_18(Protocol1_17_1To1_18 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        this.registerCooldown(ClientboundPackets1_17_1.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_17_1.CONTAINER_SET_CONTENT);
        this.registerMerchantOffers(ClientboundPackets1_17_1.MERCHANT_OFFERS);
        this.registerSetSlot1_17_1(ClientboundPackets1_17_1.CONTAINER_SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_17_1.UPDATE_ADVANCEMENTS);
        this.registerSetEquipment(ClientboundPackets1_17_1.SET_EQUIPMENT);
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_17_1.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_14);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    int data = wrapper.get(Types.INT, 1);
                    if (id == 1010) {
                        wrapper.set(Types.INT, 1, ((Protocol1_17_1To1_18)ItemPacketRewriter1_18.this.protocol).getMappingData().getNewItemId(data));
                    }
                });
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_17_1.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    if (id == 2) {
                        wrapper.set(Types.INT, 0, 3);
                        wrapper.write(Types.VAR_INT, 7754);
                        return;
                    }
                    if (id == 3) {
                        wrapper.write(Types.VAR_INT, 7786);
                        return;
                    }
                    ParticleMappings mappings = ((Protocol1_17_1To1_18)ItemPacketRewriter1_18.this.protocol).getMappingData().getParticleMappings();
                    if (mappings.isBlockParticle(id)) {
                        int data = wrapper.passthrough(Types.VAR_INT);
                        wrapper.set(Types.VAR_INT, 0, ((Protocol1_17_1To1_18)ItemPacketRewriter1_18.this.protocol).getMappingData().getNewBlockStateId(data));
                    } else if (mappings.isItemParticle(id)) {
                        ItemPacketRewriter1_18.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                    }
                    int newId = ((Protocol1_17_1To1_18)ItemPacketRewriter1_18.this.protocol).getMappingData().getNewParticleId(id);
                    if (newId != id) {
                        wrapper.set(Types.INT, 0, newId);
                    }
                });
            }
        });
        new RecipeRewriter<ClientboundPackets1_17_1>(this.protocol).register(ClientboundPackets1_17_1.UPDATE_RECIPES);
        this.registerContainerClick1_17_1(ServerboundPackets1_17.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_17.SET_CREATIVE_MODE_SLOT);
    }
}

