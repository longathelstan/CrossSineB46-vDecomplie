/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_11to1_10.rewriter;

import com.viaversion.viabackwards.api.entities.storage.WrappedEntityData;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.v1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viabackwards.protocol.v1_11to1_10.data.SplashPotionMappings1_10;
import com.viaversion.viabackwards.protocol.v1_11to1_10.storage.ChestedHorseStorage;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import java.util.List;

public class EntityPacketRewriter1_11
extends LegacyEntityRewriter<ClientboundPackets1_9_3, Protocol1_11To1_10> {
    public EntityPacketRewriter1_11(Protocol1_11To1_10 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int type = wrapper.get(Types.INT, 0);
                    if (type == 2002 || type == 2007) {
                        int mappedData;
                        if (type == 2007) {
                            wrapper.set(Types.INT, 0, 2002);
                        }
                        if ((mappedData = SplashPotionMappings1_10.getOldData(wrapper.get(Types.INT, 1))) != -1) {
                            wrapper.set(Types.INT, 1, mappedData);
                        }
                    }
                });
            }
        });
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_ENTITY, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_11.this.getObjectTrackerHandler());
                this.handler(EntityPacketRewriter1_11.this.getObjectRewriter(EntityTypes1_11.ObjectType::findById));
                this.handler(((Protocol1_11To1_10)EntityPacketRewriter1_11.this.protocol).getItemRewriter().getFallingBlockHandler());
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.ADD_EXPERIENCE_ORB, EntityTypes1_11.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_9_3.ADD_GLOBAL_ENTITY, EntityTypes1_11.EntityType.LIGHTNING_BOLT);
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.VAR_INT, Types.UNSIGNED_BYTE);
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
                this.handler(EntityPacketRewriter1_11.this.getTrackerHandler(Types.UNSIGNED_BYTE, 0));
                this.handler(EntityPacketRewriter1_11.this.getMobSpawnRewriter(Types1_9.ENTITY_DATA_LIST));
                this.handler(wrapper -> {
                    List<EntityData> entityDataList = wrapper.get(Types1_9.ENTITY_DATA_LIST, 0);
                    if (entityDataList.isEmpty()) {
                        entityDataList.add(new EntityData(0, EntityDataTypes1_9.BYTE, (byte)0));
                    }
                });
            }
        });
        this.registerTracker(ClientboundPackets1_9_3.ADD_PAINTING, EntityTypes1_11.EntityType.PAINTING);
        this.registerJoinGame(ClientboundPackets1_9_3.LOGIN, EntityTypes1_11.EntityType.PLAYER);
        this.registerRespawn(ClientboundPackets1_9_3.RESPAWN);
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_PLAYER, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_11.this.getTrackerAndDataHandler(Types1_9.ENTITY_DATA_LIST, EntityTypes1_11.EntityType.PLAYER));
                this.handler(wrapper -> {
                    List<EntityData> entityDataList = wrapper.get(Types1_9.ENTITY_DATA_LIST, 0);
                    if (entityDataList.isEmpty()) {
                        entityDataList.add(new EntityData(0, EntityDataTypes1_9.BYTE, (byte)0));
                    }
                });
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_9_3.SET_ENTITY_DATA, Types1_9.ENTITY_DATA_LIST);
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.ENTITY_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    if (entityId != EntityPacketRewriter1_11.this.tracker(wrapper.user()).clientEntityId()) {
                        return;
                    }
                    byte entityStatus = wrapper.get(Types.BYTE, 0);
                    if (entityStatus == 35) {
                        wrapper.clearPacket();
                        wrapper.setPacketType(ClientboundPackets1_9_3.GAME_EVENT);
                        wrapper.write(Types.UNSIGNED_BYTE, (short)10);
                        wrapper.write(Types.FLOAT, Float.valueOf(0.0f));
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.ELDER_GUARDIAN, EntityTypes1_11.EntityType.GUARDIAN);
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.WITHER_SKELETON, EntityTypes1_11.EntityType.SKELETON).spawnEntityData(storage -> storage.add(this.getSkeletonTypeData(1)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.STRAY, EntityTypes1_11.EntityType.SKELETON).plainName().spawnEntityData(storage -> storage.add(this.getSkeletonTypeData(2)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.HUSK, EntityTypes1_11.EntityType.ZOMBIE).plainName().spawnEntityData(storage -> this.handleZombieType(storage, 6));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.ZOMBIE_VILLAGER, EntityTypes1_11.EntityType.ZOMBIE).spawnEntityData(storage -> this.handleZombieType(storage, 1));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.HORSE, EntityTypes1_11.EntityType.HORSE).spawnEntityData(storage -> storage.add(this.getHorseDataType(0)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.DONKEY, EntityTypes1_11.EntityType.HORSE).spawnEntityData(storage -> storage.add(this.getHorseDataType(1)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.MULE, EntityTypes1_11.EntityType.HORSE).spawnEntityData(storage -> storage.add(this.getHorseDataType(2)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.SKELETON_HORSE, EntityTypes1_11.EntityType.HORSE).spawnEntityData(storage -> storage.add(this.getHorseDataType(4)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.ZOMBIE_HORSE, EntityTypes1_11.EntityType.HORSE).spawnEntityData(storage -> storage.add(this.getHorseDataType(3)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.EVOKER_FANGS, EntityTypes1_11.EntityType.SHULKER);
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.EVOKER, EntityTypes1_11.EntityType.VILLAGER).plainName();
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.VEX, EntityTypes1_11.EntityType.BAT).plainName();
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.VINDICATOR, EntityTypes1_11.EntityType.VILLAGER).plainName().spawnEntityData(storage -> storage.add(new EntityData(13, EntityDataTypes1_9.VAR_INT, 4)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.LLAMA, EntityTypes1_11.EntityType.HORSE).plainName().spawnEntityData(storage -> storage.add(this.getHorseDataType(1)));
        this.mapEntityTypeWithData(EntityTypes1_11.EntityType.LLAMA_SPIT, EntityTypes1_11.EntityType.SNOWBALL);
        this.mapObjectType(EntityTypes1_11.ObjectType.LLAMA_SPIT, EntityTypes1_11.ObjectType.SNOWBALL, -1);
        this.mapObjectType(EntityTypes1_11.ObjectType.EVOKER_FANGS, EntityTypes1_11.ObjectType.FALLING_BLOCK, 4294);
        this.filter().type(EntityTypes1_11.EntityType.GUARDIAN).index(12).handler((event, data) -> {
            int bitmask;
            boolean b = (Boolean)data.getValue();
            int n = bitmask = b ? 2 : 0;
            if (event.entityType() == EntityTypes1_11.EntityType.ELDER_GUARDIAN) {
                bitmask |= 4;
            }
            data.setTypeAndValue(EntityDataTypes1_9.BYTE, (byte)bitmask);
        });
        this.filter().type(EntityTypes1_11.EntityType.ABSTRACT_SKELETON).index(12).toIndex(13);
        this.filter().type(EntityTypes1_11.EntityType.ZOMBIE).handler((event, data) -> {
            switch (data.id()) {
                case 13: {
                    event.cancel();
                    break;
                }
                case 14: {
                    event.setIndex(15);
                    break;
                }
                case 15: {
                    event.setIndex(14);
                    break;
                }
                case 16: {
                    event.setIndex(13);
                    data.setValue(1 + (Integer)data.getValue());
                }
            }
        });
        this.filter().type(EntityTypes1_11.EntityType.EVOKER).index(12).handler((event, data) -> {
            event.setIndex(13);
            data.setTypeAndValue(EntityDataTypes1_9.VAR_INT, ((Byte)data.getValue()).intValue());
        });
        this.filter().type(EntityTypes1_11.EntityType.VEX).index(12).handler((event, data) -> data.setValue((byte)0));
        this.filter().type(EntityTypes1_11.EntityType.VINDICATOR).index(12).handler((event, data) -> {
            event.setIndex(13);
            data.setTypeAndValue(EntityDataTypes1_9.VAR_INT, ((Number)data.getValue()).intValue() == 1 ? 2 : 4);
        });
        this.filter().type(EntityTypes1_11.EntityType.ABSTRACT_HORSE).index(13).handler((event, data) -> {
            StoredEntityData entityData = this.storedEntityData(event);
            byte b = (Byte)data.getValue();
            if (entityData.has(ChestedHorseStorage.class) && entityData.get(ChestedHorseStorage.class).isChested()) {
                b = (byte)(b | 8);
                data.setValue(b);
            }
        });
        this.filter().type(EntityTypes1_11.EntityType.CHESTED_HORSE).handler((event, data) -> {
            StoredEntityData entityData = this.storedEntityData(event);
            if (!entityData.has(ChestedHorseStorage.class)) {
                entityData.put(new ChestedHorseStorage());
            }
        });
        this.filter().type(EntityTypes1_11.EntityType.HORSE).index(16).toIndex(17);
        this.filter().type(EntityTypes1_11.EntityType.CHESTED_HORSE).index(15).handler((event, data) -> {
            StoredEntityData entityData = this.storedEntityData(event);
            ChestedHorseStorage storage = entityData.get(ChestedHorseStorage.class);
            boolean b = (Boolean)data.getValue();
            storage.setChested(b);
            event.cancel();
        });
        this.filter().type(EntityTypes1_11.EntityType.LLAMA).handler((event, data) -> {
            StoredEntityData entityData = this.storedEntityData(event);
            ChestedHorseStorage storage = entityData.get(ChestedHorseStorage.class);
            int index2 = event.index();
            switch (index2) {
                case 16: {
                    storage.setLiamaStrength((Integer)data.getValue());
                    event.cancel();
                    break;
                }
                case 17: {
                    storage.setLiamaCarpetColor((Integer)data.getValue());
                    event.cancel();
                    break;
                }
                case 18: {
                    storage.setLiamaVariant((Integer)data.getValue());
                    event.cancel();
                }
            }
        });
        this.filter().type(EntityTypes1_11.EntityType.ABSTRACT_HORSE).index(14).toIndex(16);
        this.filter().type(EntityTypes1_11.EntityType.VILLAGER).index(13).handler((event, data) -> {
            if ((Integer)data.getValue() == 5) {
                data.setValue(0);
            }
        });
        this.filter().type(EntityTypes1_11.EntityType.SHULKER).cancel(15);
    }

    EntityData getSkeletonTypeData(int type) {
        return new EntityData(12, EntityDataTypes1_9.VAR_INT, type);
    }

    EntityData getZombieTypeData(int type) {
        return new EntityData(13, EntityDataTypes1_9.VAR_INT, type);
    }

    void handleZombieType(WrappedEntityData storage, int type) {
        EntityData meta = storage.get(13);
        if (meta == null) {
            storage.add(this.getZombieTypeData(type));
        }
    }

    EntityData getHorseDataType(int type) {
        return new EntityData(14, EntityDataTypes1_9.VAR_INT, type);
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

