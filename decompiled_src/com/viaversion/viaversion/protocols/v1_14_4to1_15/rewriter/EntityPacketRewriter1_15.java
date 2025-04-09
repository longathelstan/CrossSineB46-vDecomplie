/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_14_4to1_15.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.v1_14_3to1_14_4.packet.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;

public class EntityPacketRewriter1_15
extends EntityRewriter<ClientboundPackets1_14_4, Protocol1_14_4To1_15> {
    public EntityPacketRewriter1_15(Protocol1_14_4To1_15 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_14_4.ADD_ENTITY, EntityTypes1_15.FALLING_BLOCK);
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_14_4.ADD_MOB, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_15.this.trackerHandler());
                this.handler(wrapper -> EntityPacketRewriter1_15.this.sendEntityDataPacket(wrapper, wrapper.get(Types.VAR_INT, 0)));
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_14_4.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    wrapper.user().getEntityTracker(Protocol1_14_4To1_15.class).addEntity(entityId, EntityTypes1_15.PLAYER);
                    EntityPacketRewriter1_15.this.sendEntityDataPacket(wrapper, entityId);
                });
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_14_4.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    EntityPacketRewriter1_15.this.tracker(wrapper.user()).clearEntities();
                    wrapper.write(Types.LONG, 0L);
                });
            }
        });
        ((Protocol1_14_4To1_15)this.protocol).registerClientbound(ClientboundPackets1_14_4.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.handler(EntityPacketRewriter1_15.this.playerTrackerHandler());
                this.handler(wrapper -> wrapper.write(Types.LONG, 0L));
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.STRING);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> wrapper.write(Types.BOOLEAN, !Via.getConfig().is1_15InstantRespawn()));
            }
        });
        this.registerSetEntityData(ClientboundPackets1_14_4.SET_ENTITY_DATA, Types1_14.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_14_4.REMOVE_ENTITIES);
    }

    @Override
    protected void registerRewrites() {
        this.registerEntityDataTypeHandler(Types1_14.ENTITY_DATA_TYPES.itemType, Types1_14.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_14.ENTITY_DATA_TYPES.particleType);
        this.registerBlockStateHandler(EntityTypes1_15.ABSTRACT_MINECART, 10);
        this.filter().type(EntityTypes1_15.LIVING_ENTITY).addIndex(12);
        this.filter().type(EntityTypes1_15.WOLF).removeIndex(18);
    }

    void sendEntityDataPacket(PacketWrapper wrapper, int entityId) {
        List<EntityData> entityData = wrapper.read(Types1_14.ENTITY_DATA_LIST);
        if (entityData.isEmpty()) {
            return;
        }
        wrapper.send(Protocol1_14_4To1_15.class);
        wrapper.cancel();
        this.handleEntityData(entityId, entityData, wrapper.user());
        PacketWrapper entityDataPacket = PacketWrapper.create(ClientboundPackets1_15.SET_ENTITY_DATA, wrapper.user());
        entityDataPacket.write(Types.VAR_INT, entityId);
        entityDataPacket.write(Types1_14.ENTITY_DATA_LIST, entityData);
        entityDataPacket.send(Protocol1_14_4To1_15.class);
    }

    @Override
    public int newEntityId(int id) {
        return id >= 4 ? id + 1 : id;
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_15.getTypeFromId(type);
    }
}

