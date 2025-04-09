/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.EntityIdMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.ParticleIdMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter.WorldPacketRewriter1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.ComponentUtil;

public class EntityPacketRewriter1_13
extends EntityRewriter<ClientboundPackets1_12_1, Protocol1_12_2To1_13> {
    public EntityPacketRewriter1_13(Protocol1_12_2To1_13 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.BYTE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    byte type = wrapper.get(Types.BYTE, 0);
                    EntityTypes1_13.EntityType entType = EntityTypes1_13.getTypeFromId(type, true);
                    if (entType == null) {
                        return;
                    }
                    wrapper.user().getEntityTracker(Protocol1_12_2To1_13.class).addEntity(entityId, entType);
                    if (entType.is(EntityTypes1_13.EntityType.FALLING_BLOCK)) {
                        int oldId = wrapper.get(Types.INT, 0);
                        int combined = (oldId & 0xFFF) << 4 | oldId >> 12 & 0xF;
                        wrapper.set(Types.INT, 0, WorldPacketRewriter1_13.toNewId(combined));
                    }
                    if (entType.is(EntityTypes1_13.EntityType.ITEM_FRAME)) {
                        int data = wrapper.get(Types.INT, 0);
                        switch (data) {
                            case 0: {
                                data = 3;
                                break;
                            }
                            case 1: {
                                data = 4;
                                break;
                            }
                            case 3: {
                                data = 5;
                            }
                        }
                        wrapper.set(Types.INT, 0, data);
                    }
                });
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.ADD_MOB, new PacketHandlers(){

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
                this.map(Types1_12.ENTITY_DATA_LIST, Types1_13.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_13.this.trackerAndRewriterHandler(Types1_13.ENTITY_DATA_LIST));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types1_12.ENTITY_DATA_LIST, Types1_13.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_13.this.trackerAndRewriterHandler(Types1_13.ENTITY_DATA_LIST, EntityTypes1_13.EntityType.PLAYER));
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    Object clientChunks = wrapper.user().getClientWorld(Protocol1_12_2To1_13.class);
                    int dimensionId = wrapper.get(Types.INT, 1);
                    ((ClientWorld)clientChunks).setEnvironment(dimensionId);
                });
                this.handler(EntityPacketRewriter1_13.this.playerTrackerHandler());
                this.handler(Protocol1_12_2To1_13.SEND_DECLARE_COMMANDS_AND_TAGS);
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int dimensionId;
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_12_2To1_13.class);
                    if (((ClientWorld)clientWorld).setEnvironment(dimensionId = wrapper.get(Types.INT, 0).intValue())) {
                        if (Via.getConfig().isServersideBlockConnections()) {
                            ConnectionData.clearBlockStorage(wrapper.user());
                        }
                        EntityPacketRewriter1_13.this.tracker(wrapper.user()).clearEntities();
                    }
                });
                this.handler(Protocol1_12_2To1_13.SEND_DECLARE_COMMANDS_AND_TAGS);
            }
        });
        ((Protocol1_12_2To1_13)this.protocol).registerClientbound(ClientboundPackets1_12_1.UPDATE_MOB_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT);
                this.handler(packetWrapper -> {
                    byte flags = packetWrapper.read(Types.BYTE);
                    if (Via.getConfig().isNewEffectIndicator()) {
                        flags = (byte)(flags | 4);
                    }
                    packetWrapper.write(Types.BYTE, flags);
                });
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_12_1.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_12_1.SET_ENTITY_DATA, Types1_12.ENTITY_DATA_LIST, Types1_13.ENTITY_DATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.filter().mapDataType(typeId -> Types1_13.ENTITY_DATA_TYPES.byId(typeId > 4 ? typeId + 1 : typeId));
        this.filter().dataType(Types1_13.ENTITY_DATA_TYPES.itemType).handler((event, data) -> ((Protocol1_12_2To1_13)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)data.value()));
        this.filter().dataType(Types1_13.ENTITY_DATA_TYPES.optionalBlockStateType).handler((event, data) -> {
            int oldId = (Integer)data.value();
            if (oldId != 0) {
                int combined = (oldId & 0xFFF) << 4 | oldId >> 12 & 0xF;
                int newId = WorldPacketRewriter1_13.toNewId(combined);
                data.setValue(newId);
            }
        });
        this.filter().index(0).handler((event, data) -> data.setValue((byte)((Byte)data.getValue() & 0xFFFFFFEF)));
        this.filter().index(2).handler((event, data) -> {
            if (data.getValue() != null && !((String)data.getValue()).isEmpty()) {
                data.setTypeAndValue(Types1_13.ENTITY_DATA_TYPES.optionalComponentType, ComponentUtil.legacyToJson((String)data.getValue()));
            } else {
                data.setTypeAndValue(Types1_13.ENTITY_DATA_TYPES.optionalComponentType, null);
            }
        });
        this.filter().type(EntityTypes1_13.EntityType.WOLF).index(17).handler((event, data) -> data.setValue(15 - (Integer)data.getValue()));
        this.filter().type(EntityTypes1_13.EntityType.ZOMBIE).addIndex(15);
        this.filter().type(EntityTypes1_13.EntityType.ABSTRACT_MINECART).index(9).handler((event, data) -> {
            int oldId = (Integer)data.value();
            int combined = (oldId & 0xFFF) << 4 | oldId >> 12 & 0xF;
            int newId = WorldPacketRewriter1_13.toNewId(combined);
            data.setValue(newId);
        });
        this.filter().type(EntityTypes1_13.EntityType.AREA_EFFECT_CLOUD).handler((event, data) -> {
            if (data.id() == 9) {
                int particleId = (Integer)data.value();
                EntityData parameter1Data = event.dataAtIndex(10);
                EntityData parameter2Data = event.dataAtIndex(11);
                int parameter1 = parameter1Data != null ? (Integer)parameter1Data.value() : 0;
                int parameter2 = parameter2Data != null ? (Integer)parameter2Data.value() : 0;
                Particle particle = ParticleIdMappings1_13.rewriteParticle(particleId, new Integer[]{parameter1, parameter2});
                if (particle != null && particle.id() != -1) {
                    event.createExtraData(new EntityData(9, Types1_13.ENTITY_DATA_TYPES.particleType, particle));
                }
            }
            if (data.id() >= 9) {
                event.cancel();
            }
        });
    }

    @Override
    public int newEntityId(int id) {
        return EntityIdMappings1_13.getNewId(id);
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_13.getTypeFromId(type, false);
    }

    @Override
    public EntityType objectTypeFromId(int type) {
        return EntityTypes1_13.getTypeFromId(type, true);
    }
}

