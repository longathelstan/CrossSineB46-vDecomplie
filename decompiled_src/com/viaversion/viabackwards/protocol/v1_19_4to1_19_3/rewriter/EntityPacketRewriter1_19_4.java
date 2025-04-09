/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19_4to1_19_3.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viabackwards.api.entities.storage.EntityReplacement;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.protocols.v1_19_1to1_19_3.packet.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.util.TagUtil;

public final class EntityPacketRewriter1_19_4
extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_19_4To1_19_3> {
    public EntityPacketRewriter1_19_4(Protocol1_19_4To1_19_3 protocol) {
        super(protocol, Types1_19_3.ENTITY_DATA_TYPES.optionalComponentType, Types1_19_3.ENTITY_DATA_TYPES.booleanType);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_4.ADD_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
        this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_19_4.SET_ENTITY_DATA, Types1_19_4.ENTITY_DATA_LIST, Types1_19_3.ENTITY_DATA_LIST);
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_4.LOGIN, new PacketHandlers(){

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
                this.handler(wrapper -> {
                    CompoundTag registry = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    TagUtil.removeNamespaced(registry, "trim_pattern");
                    TagUtil.removeNamespaced(registry, "trim_material");
                    TagUtil.removeNamespaced(registry, "damage_type");
                    ListTag<CompoundTag> biomes = TagUtil.getRegistryEntries(registry, "worldgen/biome");
                    for (CompoundTag biomeTag : biomes) {
                        CompoundTag biomeData;
                        NumberTag hasPrecipitation = (biomeData = biomeTag.getCompoundTag("element")).getNumberTag("has_precipitation");
                        biomeData.putString("precipitation", hasPrecipitation.asByte() == 1 ? "rain" : "none");
                    }
                });
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_4.PLAYER_POSITION, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT);
                this.create(Types.BOOLEAN, false);
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_4.DAMAGE_EVENT, ClientboundPackets1_19_3.ENTITY_EVENT, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.read(Types.VAR_INT);
                this.read(Types.VAR_INT);
                this.read(Types.VAR_INT);
                this.handler(wrapper -> {
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        wrapper.read(Types.DOUBLE);
                        wrapper.read(Types.DOUBLE);
                        wrapper.read(Types.DOUBLE);
                    }
                });
                this.create(Types.BYTE, (byte)2);
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_4.HURT_ANIMATION, ClientboundPackets1_19_3.ANIMATE, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.read(Types.FLOAT);
                this.create(Types.UNSIGNED_BYTE, (short)1);
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.handler(EntityPacketRewriter1_19_4.this.worldDataTrackerHandlerByKey());
            }
        });
        ((Protocol1_19_4To1_19_3)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_MOB_EFFECT, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BYTE);
            int duration = wrapper.read(Types.VAR_INT);
            wrapper.write(Types.VAR_INT, duration == -1 ? 999999 : duration);
        });
    }

    @Override
    public void registerRewrites() {
        this.filter().handler((event, data) -> {
            int id = data.dataType().typeId();
            if (id >= 25) {
                return;
            }
            if (id >= 15) {
                --id;
            }
            data.setDataType(Types1_19_3.ENTITY_DATA_TYPES.byId(id));
        });
        this.registerEntityDataTypeHandler(Types1_19_3.ENTITY_DATA_TYPES.itemType, null, Types1_19_3.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_19_3.ENTITY_DATA_TYPES.particleType, Types1_19_3.ENTITY_DATA_TYPES.componentType, Types1_19_3.ENTITY_DATA_TYPES.optionalComponentType);
        this.registerBlockStateHandler(EntityTypes1_19_4.ABSTRACT_MINECART, 11);
        this.filter().type(EntityTypes1_19_4.BOAT).index(11).handler((event, data) -> {
            int boatType = (Integer)data.value();
            if (boatType > 4) {
                data.setValue(boatType - 1);
            }
        });
        this.filter().type(EntityTypes1_19_4.TEXT_DISPLAY).index(22).handler((event, data) -> {
            event.setIndex(2);
            data.setDataType(Types1_19_3.ENTITY_DATA_TYPES.optionalComponentType);
            event.createExtraData(new EntityData(3, Types1_19_3.ENTITY_DATA_TYPES.booleanType, true));
        });
        this.filter().type(EntityTypes1_19_4.DISPLAY).handler((event, data) -> {
            if (event.index() > 7) {
                event.cancel();
            }
        });
        this.filter().type(EntityTypes1_19_4.INTERACTION).cancel(8);
        this.filter().type(EntityTypes1_19_4.INTERACTION).cancel(9);
        this.filter().type(EntityTypes1_19_4.INTERACTION).cancel(10);
        this.filter().type(EntityTypes1_19_4.SNIFFER).cancel(17);
        this.filter().type(EntityTypes1_19_4.SNIFFER).cancel(18);
        this.filter().type(EntityTypes1_19_4.ABSTRACT_HORSE).addIndex(18);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        EntityReplacement.EntityDataCreator displayDataCreator = storage -> {
            storage.add(new EntityData(0, Types1_19_3.ENTITY_DATA_TYPES.byteType, (byte)32));
            storage.add(new EntityData(5, Types1_19_3.ENTITY_DATA_TYPES.booleanType, true));
            storage.add(new EntityData(15, Types1_19_3.ENTITY_DATA_TYPES.byteType, (byte)17));
        };
        this.mapEntityTypeWithData(EntityTypes1_19_4.TEXT_DISPLAY, EntityTypes1_19_4.ARMOR_STAND).spawnEntityData(displayDataCreator);
        this.mapEntityTypeWithData(EntityTypes1_19_4.ITEM_DISPLAY, EntityTypes1_19_4.ARMOR_STAND).spawnEntityData(displayDataCreator);
        this.mapEntityTypeWithData(EntityTypes1_19_4.BLOCK_DISPLAY, EntityTypes1_19_4.ARMOR_STAND).spawnEntityData(displayDataCreator);
        this.mapEntityTypeWithData(EntityTypes1_19_4.INTERACTION, EntityTypes1_19_4.ARMOR_STAND).spawnEntityData(displayDataCreator);
        this.mapEntityTypeWithData(EntityTypes1_19_4.SNIFFER, EntityTypes1_19_4.RAVAGER).jsonName();
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_19_4.getTypeFromId(type);
    }
}

