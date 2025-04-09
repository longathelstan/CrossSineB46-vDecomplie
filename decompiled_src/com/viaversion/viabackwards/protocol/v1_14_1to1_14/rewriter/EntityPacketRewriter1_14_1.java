/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14_1to1_14.rewriter;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.v1_14_1to1_14.Protocol1_14_1To1_14;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import java.util.List;

public class EntityPacketRewriter1_14_1
extends LegacyEntityRewriter<ClientboundPackets1_14, Protocol1_14_1To1_14> {
    public EntityPacketRewriter1_14_1(Protocol1_14_1To1_14 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerTracker(ClientboundPackets1_14.ADD_EXPERIENCE_ORB, EntityTypes1_14.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_14.ADD_GLOBAL_ENTITY, EntityTypes1_14.LIGHTNING_BOLT);
        this.registerTracker(ClientboundPackets1_14.ADD_PAINTING, EntityTypes1_14.PAINTING);
        this.registerTracker(ClientboundPackets1_14.ADD_PLAYER, EntityTypes1_14.PLAYER);
        this.registerTracker(ClientboundPackets1_14.LOGIN, EntityTypes1_14.PLAYER, Types.INT);
        this.registerRemoveEntities(ClientboundPackets1_14.REMOVE_ENTITIES);
        ((Protocol1_14_1To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.VAR_INT);
                this.handler(EntityPacketRewriter1_14_1.this.getTrackerHandler());
            }
        });
        ((Protocol1_14_1To1_14)this.protocol).registerClientbound(ClientboundPackets1_14.ADD_MOB, new PacketHandlers(){

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
                this.map(Types1_14.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    int type = wrapper.get(Types.VAR_INT, 1);
                    EntityPacketRewriter1_14_1.this.tracker(wrapper.user()).addEntity(entityId, EntityTypes1_14.getTypeFromId(type));
                    List<EntityData> entityDataList = wrapper.get(Types1_14.ENTITY_DATA_LIST, 0);
                    EntityPacketRewriter1_14_1.this.handleEntityData(entityId, entityDataList, wrapper.user());
                });
            }
        });
        this.registerSetEntityData(ClientboundPackets1_14.SET_ENTITY_DATA, Types1_14.ENTITY_DATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.filter().type(EntityTypes1_14.VILLAGER).cancel(15);
        this.filter().type(EntityTypes1_14.VILLAGER).index(16).toIndex(15);
        this.filter().type(EntityTypes1_14.WANDERING_TRADER).cancel(15);
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_14.getTypeFromId(typeId);
    }
}

