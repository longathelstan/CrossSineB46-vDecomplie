/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_10to1_9_3.rewriter;

import com.viaversion.viabackwards.api.entities.storage.EntityReplacement;
import com.viaversion.viabackwards.api.entities.storage.WrappedEntityData;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.v1_10to1_9_3.Protocol1_10To1_9_3;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import java.util.List;

public class EntityPacketRewriter1_10
extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_10To1_9_3> {
    public EntityPacketRewriter1_10(Protocol1_10To1_9_3 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_10To1_9_3)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_ENTITY, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_10.this.getObjectTrackerHandler());
                this.handler(EntityPacketRewriter1_10.this.getObjectRewriter(EntityTypes1_11.ObjectType::findById));
                this.handler(((Protocol1_10To1_9_3)EntityPacketRewriter1_10.this.protocol).getItemRewriter().getFallingBlockHandler());
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.ADD_EXPERIENCE_ORB, EntityTypes1_10.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.ADD_GLOBAL_ENTITY, EntityTypes1_10.EntityType.LIGHTNING_BOLT);
        ((Protocol1_10To1_9_3)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.UNSIGNED_BYTE);
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
                this.handler(EntityPacketRewriter1_10.this.getTrackerHandler(Types.UNSIGNED_BYTE, 0));
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    EntityType type = EntityPacketRewriter1_10.this.tracker(wrapper.user()).entityType(entityId);
                    List<EntityData> entityDataList = wrapper.get(Types1_9.ENTITY_DATA_LIST, 0);
                    EntityPacketRewriter1_10.this.handleEntityData(wrapper.get(Types.VAR_INT, 0), entityDataList, wrapper.user());
                    EntityReplacement entityReplacement = EntityPacketRewriter1_10.this.entityDataForType(type);
                    if (entityReplacement != null) {
                        WrappedEntityData storage = new WrappedEntityData(entityDataList);
                        wrapper.set(Types.UNSIGNED_BYTE, 0, (short)entityReplacement.replacementId());
                        if (entityReplacement.hasBaseData()) {
                            entityReplacement.defaultData().createData(storage);
                        }
                    }
                });
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.ADD_PAINTING, EntityTypes1_10.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.LOGIN, EntityTypes1_10.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_10To1_9_3)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_PLAYER, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_10.this.getTrackerAndDataHandler(Types1_9.ENTITY_DATA_LIST, EntityTypes1_11.EntityType.PLAYER));
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_9_3.SET_ENTITY_DATA, Types1_9.ENTITY_DATA_LIST);
    }

    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(EntityTypes1_10.EntityType.POLAR_BEAR, EntityTypes1_10.EntityType.SHEEP).plainName();
        this.filter().type(EntityTypes1_10.EntityType.POLAR_BEAR).index(13).handler((event, data) -> {
            boolean b = (Boolean)data.getValue();
            data.setTypeAndValue(EntityDataTypes1_9.BYTE, b ? (byte)14 : (byte)0);
        });
        this.filter().type(EntityTypes1_10.EntityType.ZOMBIE).index(13).handler((event, data) -> {
            if ((Integer)data.getValue() == 6) {
                data.setValue(0);
            }
        });
        this.filter().type(EntityTypes1_10.EntityType.SKELETON).index(12).handler((event, data) -> {
            if ((Integer)data.getValue() == 2) {
                data.setValue(0);
            }
        });
        this.filter().removeIndex(5);
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_10.getTypeFromId(typeId, false);
    }

    @Override
    public EntityType objectTypeFromId(int typeId) {
        return EntityTypes1_10.getTypeFromId(typeId, true);
    }
}

