/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_11_1to1_12.rewriter;

import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public class EntityPacketRewriter1_12
extends EntityRewriter<ClientboundPackets1_9_3, Protocol1_11_1To1_12> {
    public EntityPacketRewriter1_12(Protocol1_11_1To1_12 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_9_3.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_11_1To1_12.class);
                    int dimensionId = wrapper.get(Types.INT, 1);
                    ((ClientWorld)clientWorld).setEnvironment(dimensionId);
                    if (wrapper.user().getProtocolInfo().protocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_13)) {
                        wrapper.create(ClientboundPackets1_13.UPDATE_RECIPES, packetWrapper -> packetWrapper.write(Types.VAR_INT, 0)).scheduleSend(Protocol1_12_2To1_13.class);
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int dimensionId;
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_11_1To1_12.class);
                    if (((ClientWorld)clientWorld).setEnvironment(dimensionId = wrapper.get(Types.INT, 0).intValue())) {
                        EntityPacketRewriter1_12.this.tracker(wrapper.user()).clearEntities();
                    }
                });
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.BYTE);
                this.handler(EntityPacketRewriter1_12.this.objectTrackerHandler());
            }
        });
        ((Protocol1_11_1To1_12)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.VAR_INT);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types1_9.ENTITY_DATA_LIST, Types1_12.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_12.this.trackerAndRewriterHandler(Types1_12.ENTITY_DATA_LIST));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_9_3.SET_ENTITY_DATA, Types1_9.ENTITY_DATA_LIST, Types1_12.ENTITY_DATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, data) -> {
            if (data.getValue() instanceof Item) {
                data.setValue(((Protocol1_11_1To1_12)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)data.value()));
            }
        });
        this.filter().type(EntityTypes1_12.EntityType.ABSTRACT_ILLAGER).addIndex(12);
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_12.getTypeFromId(type, false);
    }

    @Override
    public EntityType objectTypeFromId(int type) {
        return EntityTypes1_12.getTypeFromId(type, true);
    }
}

