/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_18_2to1_19.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ServerboundPackets1_19;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.provider.AckSequenceProvider;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class ItemPacketRewriter1_19
extends ItemRewriter<ClientboundPackets1_18, ServerboundPackets1_19, Protocol1_18_2To1_19> {
    public ItemPacketRewriter1_19(Protocol1_18_2To1_19 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        this.registerCooldown(ClientboundPackets1_18.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_18.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_18.CONTAINER_SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_18.UPDATE_ADVANCEMENTS);
        this.registerSetEquipment(ClientboundPackets1_18.SET_EQUIPMENT);
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_18.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.VAR_INT);
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
                    ParticleMappings particleMappings;
                    int id = wrapper.get(Types.VAR_INT, 0);
                    if (id == (particleMappings = ((Protocol1_18_2To1_19)ItemPacketRewriter1_19.this.protocol).getMappingData().getParticleMappings()).id("vibration")) {
                        wrapper.read(Types.BLOCK_POSITION1_14);
                        String resourceLocation = Key.stripMinecraftNamespace(wrapper.passthrough(Types.STRING));
                        if (resourceLocation.equals("entity")) {
                            wrapper.passthrough(Types.VAR_INT);
                            wrapper.write(Types.FLOAT, Float.valueOf(0.0f));
                        }
                    }
                });
                this.handler(ItemPacketRewriter1_19.this.levelParticlesHandler(Types.VAR_INT));
            }
        });
        this.registerContainerClick1_17_1(ServerboundPackets1_19.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_19.SET_CREATIVE_MODE_SLOT);
        this.registerContainerSetData(ClientboundPackets1_18.CONTAINER_SET_DATA);
        ((Protocol1_18_2To1_19)this.protocol).registerClientbound(ClientboundPackets1_18.MERCHANT_OFFERS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int size = wrapper.read(Types.UNSIGNED_BYTE).shortValue();
                    wrapper.write(Types.VAR_INT, size);
                    for (int i = 0; i < size; ++i) {
                        ItemPacketRewriter1_19.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        ItemPacketRewriter1_19.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                            ItemPacketRewriter1_19.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                        } else {
                            wrapper.write(Types.ITEM1_13_2, null);
                        }
                        wrapper.passthrough(Types.BOOLEAN);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.INT);
                        wrapper.passthrough(Types.FLOAT);
                        wrapper.passthrough(Types.INT);
                    }
                });
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerServerbound(ServerboundPackets1_19.PLAYER_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_14);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(ItemPacketRewriter1_19.this.sequenceHandler());
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerServerbound(ServerboundPackets1_19.USE_ITEM_ON, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_14);
                this.map(Types.VAR_INT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BOOLEAN);
                this.handler(ItemPacketRewriter1_19.this.sequenceHandler());
            }
        });
        ((Protocol1_18_2To1_19)this.protocol).registerServerbound(ServerboundPackets1_19.USE_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(ItemPacketRewriter1_19.this.sequenceHandler());
            }
        });
        new RecipeRewriter<ClientboundPackets1_18>(this.protocol).register(ClientboundPackets1_18.UPDATE_RECIPES);
    }

    PacketHandler sequenceHandler() {
        return wrapper -> {
            int sequence = wrapper.read(Types.VAR_INT);
            AckSequenceProvider provider = Via.getManager().getProviders().get(AckSequenceProvider.class);
            provider.handleSequence(wrapper.user(), sequence);
        };
    }
}

