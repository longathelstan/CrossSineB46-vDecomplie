/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_11_1to1_11.rewriter;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.v1_11_1to1_11.Protocol1_11_1To1_11;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;

public class EntityPacketRewriter1_11_1
extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_11_1To1_11> {
    public EntityPacketRewriter1_11_1(Protocol1_11_1To1_11 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11_1To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_ENTITY, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_11_1.this.getObjectTrackerHandler());
                this.handler(EntityPacketRewriter1_11_1.this.getObjectRewriter(EntityTypes1_11.ObjectType::findById));
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.ADD_EXPERIENCE_ORB, EntityTypes1_11.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.ADD_GLOBAL_ENTITY, EntityTypes1_11.EntityType.LIGHTNING_BOLT);
        ((Protocol1_11_1To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_MOB, new PacketHandlers(){

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
                this.map(Types1_9.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_11_1.this.getTrackerHandler());
                this.handler(EntityPacketRewriter1_11_1.this.getMobSpawnRewriter1_11(Types1_9.ENTITY_DATA_LIST));
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.ADD_PAINTING, EntityTypes1_11.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.LOGIN, EntityTypes1_11.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_11_1To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types1_9.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_11_1.this.getTrackerAndDataHandler(Types1_9.ENTITY_DATA_LIST, EntityTypes1_11.EntityType.PLAYER));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_9_3.SET_ENTITY_DATA, Types1_9.ENTITY_DATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.filter().type(EntityTypes1_11.EntityType.FIREWORK_ROCKET).cancel(7);
        this.filter().type(EntityTypes1_11.EntityType.PIG).cancel(14);
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_11.getTypeFromId(typeId, false);
    }

    @Override
    public EntityType objectTypeFromId(int typeId) {
        return EntityTypes1_11.getTypeFromId(typeId, true);
    }
}

