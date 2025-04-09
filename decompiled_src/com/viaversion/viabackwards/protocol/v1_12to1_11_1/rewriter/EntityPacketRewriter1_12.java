/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_12to1_11_1.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.storage.ParrotStorage;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.storage.ShoulderTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;

public class EntityPacketRewriter1_12
extends LegacyEntityRewriter<ClientboundPackets1_12, Protocol1_12To1_11_1> {
    public EntityPacketRewriter1_12(Protocol1_12To1_11_1 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.ADD_ENTITY, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_12.this.getObjectTrackerHandler());
                this.handler(EntityPacketRewriter1_12.this.getObjectRewriter(EntityTypes1_12.ObjectType::findById));
                this.handler(((Protocol1_12To1_11_1)EntityPacketRewriter1_12.this.protocol).getItemRewriter().getFallingBlockHandler());
            }
        });
        this.registerTracker(ClientboundPackets1_12.ADD_EXPERIENCE_ORB, EntityTypes1_12.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_12.ADD_GLOBAL_ENTITY, EntityTypes1_12.EntityType.LIGHTNING_BOLT);
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.ADD_MOB, new PacketHandlers(){

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
                this.map(Types1_12.ENTITY_DATA_LIST, Types1_9.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_12.this.getTrackerHandler());
                this.handler(EntityPacketRewriter1_12.this.getMobSpawnRewriter1_11(Types1_9.ENTITY_DATA_LIST));
            }
        });
        this.registerTracker(ClientboundPackets1_12.ADD_PAINTING, EntityTypes1_12.EntityType.PAINTING);
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types1_12.ENTITY_DATA_LIST, Types1_9.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_12.this.getTrackerAndDataHandler(Types1_9.ENTITY_DATA_LIST, EntityTypes1_12.EntityType.PLAYER));
            }
        });
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.handler(EntityPacketRewriter1_12.this.getDimensionHandler(1));
                this.handler(EntityPacketRewriter1_12.this.getPlayerTrackerHandler());
                this.handler(wrapper -> {
                    ShoulderTracker tracker = wrapper.user().get(ShoulderTracker.class);
                    tracker.setEntityId(wrapper.get(Types.INT, 0));
                });
                this.handler(packetWrapper -> {
                    PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9_3.AWARD_STATS, packetWrapper.user());
                    wrapper.write(Types.VAR_INT, 1);
                    wrapper.write(Types.STRING, "achievement.openInventory");
                    wrapper.write(Types.VAR_INT, 1);
                    wrapper.scheduleSend(Protocol1_12To1_11_1.class);
                });
            }
        });
        this.registerRespawn(ClientboundPackets1_12.RESPAWN);
        this.registerRemoveEntities(ClientboundPackets1_12.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_12.SET_ENTITY_DATA, Types1_12.ENTITY_DATA_LIST, Types1_9.ENTITY_DATA_LIST);
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.UPDATE_ATTRIBUTES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int size;
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
        this.mapEntityTypeWithData(EntityTypes1_12.EntityType.PARROT, EntityTypes1_12.EntityType.BAT).plainName().spawnEntityData(storage -> storage.add(new EntityData(12, EntityDataTypes1_12.BYTE, (byte)0)));
        this.mapEntityTypeWithData(EntityTypes1_12.EntityType.ILLUSIONER, EntityTypes1_12.EntityType.EVOKER).plainName();
        this.filter().handler((event, data) -> {
            if (data.dataType() == EntityDataTypes1_12.COMPONENT) {
                ((Protocol1_12To1_11_1)this.protocol).getComponentRewriter().processText(event.user(), (JsonElement)data.getValue());
            }
        });
        this.filter().type(EntityTypes1_12.EntityType.EVOKER).removeIndex(12);
        this.filter().type(EntityTypes1_12.EntityType.ILLUSIONER).index(0).handler((event, data) -> {
            byte mask = (Byte)data.getValue();
            if ((mask & 0x20) == 32) {
                mask = (byte)(mask & 0xFFFFFFDF);
            }
            data.setValue(mask);
        });
        this.filter().type(EntityTypes1_12.EntityType.PARROT).handler((event, data) -> {
            StoredEntityData entityData = this.storedEntityData(event);
            if (!entityData.has(ParrotStorage.class)) {
                entityData.put(new ParrotStorage());
            }
        });
        this.filter().type(EntityTypes1_12.EntityType.PARROT).cancel(12);
        this.filter().type(EntityTypes1_12.EntityType.PARROT).index(13).handler((event, data) -> {
            boolean isTamed;
            StoredEntityData entityData = this.storedEntityData(event);
            ParrotStorage storage = entityData.get(ParrotStorage.class);
            boolean isSitting = ((Byte)data.getValue() & 1) == 1;
            boolean bl = isTamed = ((Byte)data.getValue() & 4) == 4;
            if (storage.isTamed() || isTamed) {
                // empty if block
            }
            storage.setTamed(isTamed);
            if (isSitting) {
                event.setIndex(12);
                data.setValue((byte)1);
                storage.setSitting(true);
            } else if (storage.isSitting()) {
                event.setIndex(12);
                data.setValue((byte)0);
                storage.setSitting(false);
            } else {
                event.cancel();
            }
        });
        this.filter().type(EntityTypes1_12.EntityType.PARROT).cancel(14);
        this.filter().type(EntityTypes1_12.EntityType.PARROT).cancel(15);
        this.filter().type(EntityTypes1_12.EntityType.PLAYER).index(15).handler((event, data) -> {
            CompoundTag tag = (CompoundTag)data.getValue();
            ShoulderTracker tracker = event.user().get(ShoulderTracker.class);
            if (tag.isEmpty() && tracker.getLeftShoulder() != null) {
                tracker.setLeftShoulder(null);
                tracker.update();
            } else if (tag.getStringTag("id") != null && event.entityId() == tracker.getEntityId()) {
                String id = tag.getStringTag("id").getValue();
                if (tracker.getLeftShoulder() == null || !tracker.getLeftShoulder().equals(id)) {
                    tracker.setLeftShoulder(id);
                    tracker.update();
                }
            }
            event.cancel();
        });
        this.filter().type(EntityTypes1_12.EntityType.PLAYER).index(16).handler((event, data) -> {
            CompoundTag tag = (CompoundTag)event.data().getValue();
            ShoulderTracker tracker = event.user().get(ShoulderTracker.class);
            if (tag.isEmpty() && tracker.getRightShoulder() != null) {
                tracker.setRightShoulder(null);
                tracker.update();
            } else if (tag.getStringTag("id") != null && event.entityId() == tracker.getEntityId()) {
                String id = tag.getStringTag("id").getValue();
                if (tracker.getRightShoulder() == null || !tracker.getRightShoulder().equals(id)) {
                    tracker.setRightShoulder(id);
                    tracker.update();
                }
            }
            event.cancel();
        });
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_12.getTypeFromId(typeId, false);
    }

    @Override
    public EntityType objectTypeFromId(int typeId) {
        return EntityTypes1_12.getTypeFromId(typeId, true);
    }
}

