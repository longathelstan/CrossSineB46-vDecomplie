/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.Protocol1_19_3To1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.storage.PlayerVehicleTracker;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.TagUtil;

public final class EntityPacketRewriter1_19_4
extends EntityRewriter<ClientboundPackets1_19_3, Protocol1_19_3To1_19_4> {
    public EntityPacketRewriter1_19_4(Protocol1_19_3To1_19_4 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.STRING_ARRAY);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.handler(EntityPacketRewriter1_19_4.this.dimensionDataHandler());
                this.handler(EntityPacketRewriter1_19_4.this.biomeSizeTracker());
                this.handler(EntityPacketRewriter1_19_4.this.worldDataTrackerHandlerByKey());
                this.handler(EntityPacketRewriter1_19_4.this.playerTrackerHandler());
                this.handler(wrapper -> {
                    CompoundTag registry = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    CompoundTag damageTypeRegistry = ((Protocol1_19_3To1_19_4)EntityPacketRewriter1_19_4.this.protocol).getMappingData().damageTypesRegistry();
                    registry.put("minecraft:damage_type", damageTypeRegistry);
                    ListTag<CompoundTag> biomes = TagUtil.getRegistryEntries(registry, "worldgen/biome");
                    for (CompoundTag biomeTag : biomes) {
                        CompoundTag biomeData = biomeTag.getCompoundTag("element");
                        StringTag precipitation = biomeData.getStringTag("precipitation");
                        byte precipitationByte = precipitation.getValue().equals("none") ? (byte)0 : (byte)1;
                        biomeData.put("has_precipitation", new ByteTag(precipitationByte));
                    }
                });
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.PLAYER_POSITION, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    PlayerVehicleTracker playerVehicleTracker;
                    if (wrapper.read(Types.BOOLEAN).booleanValue() && (playerVehicleTracker = wrapper.user().get(PlayerVehicleTracker.class)).getVehicleId() != -1) {
                        PacketWrapper bundleStart = wrapper.create(ClientboundPackets1_19_4.BUNDLE_DELIMITER);
                        bundleStart.send(Protocol1_19_3To1_19_4.class);
                        PacketWrapper setPassengers = wrapper.create(ClientboundPackets1_19_4.SET_PASSENGERS);
                        setPassengers.write(Types.VAR_INT, playerVehicleTracker.getVehicleId());
                        setPassengers.write(Types.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                        setPassengers.send(Protocol1_19_3To1_19_4.class);
                        wrapper.send(Protocol1_19_3To1_19_4.class);
                        wrapper.cancel();
                        PacketWrapper bundleEnd = wrapper.create(ClientboundPackets1_19_4.BUNDLE_DELIMITER);
                        bundleEnd.send(Protocol1_19_3To1_19_4.class);
                        playerVehicleTracker.setVehicleId(-1);
                    }
                });
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.SET_PASSENGERS, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    int[] passengerIds;
                    PlayerVehicleTracker playerVehicleTracker = wrapper.user().get(PlayerVehicleTracker.class);
                    int clientEntityId = wrapper.user().getEntityTracker(Protocol1_19_3To1_19_4.class).clientEntityId();
                    int vehicleId = wrapper.get(Types.VAR_INT, 0);
                    if (playerVehicleTracker.getVehicleId() == vehicleId) {
                        playerVehicleTracker.setVehicleId(-1);
                    }
                    for (int passengerId : passengerIds = wrapper.get(Types.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                        if (passengerId != clientEntityId) continue;
                        playerVehicleTracker.setVehicleId(vehicleId);
                        break;
                    }
                });
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.TELEPORT_ENTITY, new PacketHandlers(){

            @Override
            protected void register() {
                this.handler(wrapper -> {
                    int clientEntityId;
                    int entityId = wrapper.read(Types.VAR_INT);
                    if (entityId != (clientEntityId = wrapper.user().getEntityTracker(Protocol1_19_3To1_19_4.class).clientEntityId())) {
                        wrapper.write(Types.VAR_INT, entityId);
                        return;
                    }
                    wrapper.setPacketType(ClientboundPackets1_19_4.PLAYER_POSITION);
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.write(Types.FLOAT, Float.valueOf((float)wrapper.read(Types.BYTE).byteValue() * 360.0f / 256.0f));
                    wrapper.write(Types.FLOAT, Float.valueOf((float)wrapper.read(Types.BYTE).byteValue() * 360.0f / 256.0f));
                    wrapper.read(Types.BOOLEAN);
                    wrapper.write(Types.BYTE, (byte)0);
                    wrapper.write(Types.VAR_INT, -1);
                });
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.ANIMATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    short action = wrapper.read(Types.UNSIGNED_BYTE);
                    if (action != 1) {
                        wrapper.write(Types.UNSIGNED_BYTE, action);
                        return;
                    }
                    wrapper.setPacketType(ClientboundPackets1_19_4.HURT_ANIMATION);
                    wrapper.write(Types.FLOAT, Float.valueOf(0.0f));
                });
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.handler(EntityPacketRewriter1_19_4.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> wrapper.user().put(new PlayerVehicleTracker()));
            }
        });
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_3.ENTITY_EVENT, wrapper -> {
            int entityId = wrapper.read(Types.INT);
            byte event = wrapper.read(Types.BYTE);
            int damageType = this.damageTypeFromEntityEvent(event);
            if (damageType != -1) {
                wrapper.setPacketType(ClientboundPackets1_19_4.DAMAGE_EVENT);
                wrapper.write(Types.VAR_INT, entityId);
                wrapper.write(Types.VAR_INT, damageType);
                wrapper.write(Types.VAR_INT, 0);
                wrapper.write(Types.VAR_INT, 0);
                wrapper.write(Types.BOOLEAN, false);
                return;
            }
            wrapper.write(Types.INT, entityId);
            wrapper.write(Types.BYTE, event);
        });
        this.registerTrackerWithData1_19(ClientboundPackets1_19_3.ADD_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
        this.registerRemoveEntities(ClientboundPackets1_19_3.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_19_3.SET_ENTITY_DATA, Types1_19_3.ENTITY_DATA_LIST, Types1_19_4.ENTITY_DATA_LIST);
    }

    int damageTypeFromEntityEvent(byte entityEvent) {
        int n;
        switch (entityEvent) {
            case 33: {
                n = 36;
                break;
            }
            case 36: {
                n = 5;
                break;
            }
            case 37: {
                n = 27;
                break;
            }
            case 57: {
                n = 15;
                break;
            }
            case 2: 
            case 44: {
                n = 16;
                break;
            }
            default: {
                n = -1;
            }
        }
        return n;
    }

    @Override
    protected void registerRewrites() {
        this.filter().mapDataType(typeId -> Types1_19_4.ENTITY_DATA_TYPES.byId(typeId >= 14 ? typeId + 1 : typeId));
        this.registerEntityDataTypeHandler(Types1_19_4.ENTITY_DATA_TYPES.itemType, Types1_19_4.ENTITY_DATA_TYPES.blockStateType, Types1_19_4.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_19_4.ENTITY_DATA_TYPES.particleType, null);
        this.registerBlockStateHandler(EntityTypes1_19_4.ABSTRACT_MINECART, 11);
        this.filter().type(EntityTypes1_19_4.BOAT).index(11).handler((event, data) -> {
            int boatType = (Integer)data.value();
            if (boatType > 4) {
                data.setValue(boatType + 1);
            }
        });
        this.filter().type(EntityTypes1_19_4.ABSTRACT_HORSE).removeIndex(18);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_19_4.getTypeFromId(type);
    }
}

