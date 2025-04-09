/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_15to1_14_4.rewriter;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viabackwards.protocol.v1_15to1_14_4.storage.ImmediateRespawnStorage;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import java.util.ArrayList;

public class EntityPacketRewriter1_15
extends EntityRewriter<ClientboundPackets1_15, Protocol1_15To1_14_4> {
    public EntityPacketRewriter1_15(Protocol1_15To1_14_4 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_15To1_14_4)this.protocol).registerClientbound(ClientboundPackets1_15.SET_HEALTH, wrapper -> {
            float health = wrapper.passthrough(Types.FLOAT).floatValue();
            if (health > 0.0f) {
                return;
            }
            if (!wrapper.user().get(ImmediateRespawnStorage.class).isImmediateRespawn()) {
                return;
            }
            PacketWrapper statusPacket = wrapper.create(ServerboundPackets1_14.CLIENT_COMMAND);
            statusPacket.write(Types.VAR_INT, 0);
            statusPacket.sendToServer(Protocol1_15To1_14_4.class);
        });
        ((Protocol1_15To1_14_4)this.protocol).registerClientbound(ClientboundPackets1_15.GAME_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.FLOAT);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.UNSIGNED_BYTE, 0) == 11) {
                        wrapper.user().get(ImmediateRespawnStorage.class).setImmediateRespawn(wrapper.get(Types.FLOAT, 0).floatValue() == 1.0f);
                    }
                });
            }
        });
        this.registerTrackerWithData(ClientboundPackets1_15.ADD_ENTITY, EntityTypes1_15.FALLING_BLOCK);
        ((Protocol1_15To1_14_4)this.protocol).registerClientbound(ClientboundPackets1_15.ADD_MOB, new PacketHandlers(){

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
                this.handler(wrapper -> wrapper.write(Types1_14.ENTITY_DATA_LIST, new ArrayList()));
                this.handler(wrapper -> {
                    int type = wrapper.get(Types.VAR_INT, 1);
                    EntityType entityType = EntityTypes1_15.getTypeFromId(type);
                    EntityPacketRewriter1_15.this.tracker(wrapper.user()).addEntity(wrapper.get(Types.VAR_INT, 0), entityType);
                    wrapper.set(Types.VAR_INT, 1, EntityPacketRewriter1_15.this.newEntityId(type));
                });
            }
        });
        ((Protocol1_15To1_14_4)this.protocol).registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.read(Types.LONG);
                this.handler(EntityPacketRewriter1_15.this.getDimensionHandler(0));
            }
        });
        ((Protocol1_15To1_14_4)this.protocol).registerClientbound(ClientboundPackets1_15.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.read(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.STRING);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.handler(EntityPacketRewriter1_15.this.getPlayerTrackerHandler());
                this.handler(wrapper -> {
                    boolean immediateRespawn = wrapper.read(Types.BOOLEAN) == false;
                    wrapper.user().get(ImmediateRespawnStorage.class).setImmediateRespawn(immediateRespawn);
                });
            }
        });
        this.registerTracker(ClientboundPackets1_15.ADD_EXPERIENCE_ORB, EntityTypes1_15.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_15.ADD_GLOBAL_ENTITY, EntityTypes1_15.LIGHTNING_BOLT);
        this.registerTracker(ClientboundPackets1_15.ADD_PAINTING, EntityTypes1_15.PAINTING);
        ((Protocol1_15To1_14_4)this.protocol).registerClientbound(ClientboundPackets1_15.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> wrapper.write(Types1_14.ENTITY_DATA_LIST, new ArrayList()));
                this.handler(EntityPacketRewriter1_15.this.getTrackerHandler(EntityTypes1_15.PLAYER));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_15.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_15.SET_ENTITY_DATA, Types1_14.ENTITY_DATA_LIST);
        ((Protocol1_15To1_14_4)this.protocol).registerClientbound(ClientboundPackets1_15.UPDATE_ATTRIBUTES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int size;
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    EntityType entityType = EntityPacketRewriter1_15.this.tracker(wrapper.user()).entityType(entityId);
                    if (entityType != EntityTypes1_15.BEE) {
                        return;
                    }
                    int newSize = size = wrapper.get(Types.INT, 0).intValue();
                    for (int i = 0; i < size; ++i) {
                        int j;
                        int modSize;
                        String key = wrapper.read(Types.STRING);
                        if (key.equals("generic.flyingSpeed")) {
                            --newSize;
                            wrapper.read(Types.DOUBLE);
                            modSize = wrapper.read(Types.VAR_INT);
                            for (j = 0; j < modSize; ++j) {
                                wrapper.read(Types.UUID);
                                wrapper.read(Types.DOUBLE);
                                wrapper.read(Types.BYTE);
                            }
                            continue;
                        }
                        wrapper.write(Types.STRING, key);
                        wrapper.passthrough(Types.DOUBLE);
                        modSize = wrapper.passthrough(Types.VAR_INT);
                        for (j = 0; j < modSize; ++j) {
                            wrapper.passthrough(Types.UUID);
                            wrapper.passthrough(Types.DOUBLE);
                            wrapper.passthrough(Types.BYTE);
                        }
                    }
                    if (newSize != size) {
                        wrapper.set(Types.INT, 0, newSize);
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.registerEntityDataTypeHandler(Types1_14.ENTITY_DATA_TYPES.itemType, null, Types1_14.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_14.ENTITY_DATA_TYPES.particleType, Types1_14.ENTITY_DATA_TYPES.componentType, Types1_14.ENTITY_DATA_TYPES.optionalComponentType);
        this.filter().type(EntityTypes1_15.LIVING_ENTITY).removeIndex(12);
        this.filter().type(EntityTypes1_15.BEE).cancel(15);
        this.filter().type(EntityTypes1_15.BEE).cancel(16);
        this.filter().type(EntityTypes1_15.ENDERMAN).cancel(16);
        this.filter().type(EntityTypes1_15.TRIDENT).cancel(10);
        this.filter().type(EntityTypes1_15.WOLF).addIndex(17);
        this.filter().type(EntityTypes1_15.WOLF).index(8).handler((event, data) -> event.createExtraData(new EntityData(17, Types1_14.ENTITY_DATA_TYPES.floatType, event.data().value())));
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(EntityTypes1_15.BEE, EntityTypes1_15.PUFFERFISH).jsonName().spawnEntityData(storage -> {
            storage.add(new EntityData(14, Types1_14.ENTITY_DATA_TYPES.booleanType, false));
            storage.add(new EntityData(15, Types1_14.ENTITY_DATA_TYPES.varIntType, 2));
        });
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_15.getTypeFromId(typeId);
    }
}

