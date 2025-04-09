/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16to1_15_2.rewriter;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.PlayerAttributesStorage;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.WolfDataMaskStorage;
import com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage.WorldNameTracker;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.util.Key;
import java.util.UUID;

public class EntityPacketRewriter1_16
extends EntityRewriter<ClientboundPackets1_16, Protocol1_16To1_15_2> {
    final ValueTransformer<String, Integer> dimensionTransformer = new ValueTransformer<String, Integer>(Types.STRING, (Type)Types.INT){

        @Override
        public Integer transform(PacketWrapper wrapper, String input) {
            Integer n;
            switch (input = Key.namespaced(input)) {
                case "minecraft:the_nether": {
                    n = -1;
                    break;
                }
                case "minecraft:the_end": {
                    n = 1;
                    break;
                }
                default: {
                    n = 0;
                }
            }
            return n;
        }
    };

    public EntityPacketRewriter1_16(Protocol1_16To1_15_2 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.ADD_ENTITY, new PacketHandlers(){

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
                this.map(Types.INT);
                this.handler(wrapper -> {
                    EntityType entityType = EntityPacketRewriter1_16.this.typeFromId(wrapper.get(Types.VAR_INT, 1));
                    if (entityType == EntityTypes1_16.LIGHTNING_BOLT) {
                        wrapper.cancel();
                        PacketWrapper spawnLightningPacket = wrapper.create(ClientboundPackets1_15.ADD_GLOBAL_ENTITY);
                        spawnLightningPacket.write(Types.VAR_INT, wrapper.get(Types.VAR_INT, 0));
                        spawnLightningPacket.write(Types.BYTE, (byte)1);
                        spawnLightningPacket.write(Types.DOUBLE, wrapper.get(Types.DOUBLE, 0));
                        spawnLightningPacket.write(Types.DOUBLE, wrapper.get(Types.DOUBLE, 1));
                        spawnLightningPacket.write(Types.DOUBLE, wrapper.get(Types.DOUBLE, 2));
                        spawnLightningPacket.send(Protocol1_16To1_15_2.class);
                    }
                });
                this.handler(EntityPacketRewriter1_16.this.getSpawnTrackerWithDataHandler(EntityTypes1_16.FALLING_BLOCK));
            }
        });
        this.registerSpawnTracker(ClientboundPackets1_16.ADD_MOB);
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(EntityPacketRewriter1_16.this.dimensionTransformer);
                this.handler(wrapper -> {
                    WorldNameTracker worldNameTracker = wrapper.user().get(WorldNameTracker.class);
                    String nextWorldName = wrapper.read(Types.STRING);
                    wrapper.passthrough(Types.LONG);
                    wrapper.passthrough(Types.UNSIGNED_BYTE);
                    wrapper.read(Types.BYTE);
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_16To1_15_2.class);
                    int dimension = wrapper.get(Types.INT, 0);
                    if (((ClientWorld)clientWorld).getEnvironment() != null && dimension == ((ClientWorld)clientWorld).getEnvironment().id() && (wrapper.user().isClientSide() || Via.getPlatform().isProxy() || wrapper.user().getProtocolInfo().protocolVersion().olderThanOrEqualTo(ProtocolVersion.v1_12_2) || !nextWorldName.equals(worldNameTracker.getWorldName()))) {
                        PacketWrapper packet = wrapper.create(ClientboundPackets1_15.RESPAWN);
                        packet.write(Types.INT, dimension == 0 ? -1 : 0);
                        packet.write(Types.LONG, 0L);
                        packet.write(Types.UNSIGNED_BYTE, (short)0);
                        packet.write(Types.STRING, "default");
                        packet.send(Protocol1_16To1_15_2.class);
                    }
                    if (((ClientWorld)clientWorld).setEnvironment(dimension)) {
                        EntityPacketRewriter1_16.this.tracker(wrapper.user()).clearEntities();
                    }
                    wrapper.write(Types.STRING, "default");
                    wrapper.read(Types.BOOLEAN);
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        wrapper.set(Types.STRING, 0, "flat");
                    }
                    PlayerAttributesStorage attributes = wrapper.user().get(PlayerAttributesStorage.class);
                    boolean keepPlayerAttributes = wrapper.read(Types.BOOLEAN);
                    if (keepPlayerAttributes) {
                        wrapper.send(Protocol1_16To1_15_2.class);
                        wrapper.cancel();
                        attributes.sendAttributes(wrapper.user(), EntityPacketRewriter1_16.this.tracker(wrapper.user()).clientEntityId());
                    } else {
                        attributes.clearAttributes();
                    }
                    worldNameTracker.setWorldName(nextWorldName);
                });
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.read(Types.BYTE);
                this.read(Types.STRING_ARRAY);
                this.read(Types.NAMED_COMPOUND_TAG);
                this.map(EntityPacketRewriter1_16.this.dimensionTransformer);
                this.handler(wrapper -> {
                    WorldNameTracker worldNameTracker = wrapper.user().get(WorldNameTracker.class);
                    worldNameTracker.setWorldName(wrapper.read(Types.STRING));
                });
                this.map(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_16To1_15_2.class);
                    ((ClientWorld)clientWorld).setEnvironment(wrapper.get(Types.INT, 1));
                    wrapper.write(Types.STRING, "default");
                    wrapper.passthrough(Types.VAR_INT);
                    wrapper.passthrough(Types.BOOLEAN);
                    wrapper.passthrough(Types.BOOLEAN);
                    wrapper.read(Types.BOOLEAN);
                    if (wrapper.read(Types.BOOLEAN).booleanValue()) {
                        wrapper.set(Types.STRING, 0, "flat");
                    }
                });
                this.handler(EntityPacketRewriter1_16.this.playerTrackerHandler());
            }
        });
        this.registerTracker(ClientboundPackets1_16.ADD_EXPERIENCE_ORB, EntityTypes1_16.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_16.ADD_PAINTING, EntityTypes1_16.PAINTING);
        this.registerTracker(ClientboundPackets1_16.ADD_PLAYER, EntityTypes1_16.PLAYER);
        this.registerRemoveEntities(ClientboundPackets1_16.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_16.SET_ENTITY_DATA, Types1_16.ENTITY_DATA_LIST, Types1_14.ENTITY_DATA_LIST);
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.UPDATE_ATTRIBUTES, wrapper -> {
            PlayerAttributesStorage attributes = wrapper.user().get(PlayerAttributesStorage.class);
            int entityId = wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.INT);
            for (int i = 0; i < size; ++i) {
                String identifier = Key.stripMinecraftNamespace(wrapper.read(Types.STRING));
                String mappedIdentifier = ((Protocol1_16To1_15_2)this.protocol).getMappingData().mappedAttributeIdentifier(identifier);
                wrapper.write(Types.STRING, mappedIdentifier);
                double value = wrapper.passthrough(Types.DOUBLE);
                int count = wrapper.passthrough(Types.VAR_INT);
                PlayerAttributesStorage.AttributeModifier[] modifiers = new PlayerAttributesStorage.AttributeModifier[count];
                for (int j = 0; j < count; ++j) {
                    UUID uuid = wrapper.passthrough(Types.UUID);
                    double amount = wrapper.passthrough(Types.DOUBLE);
                    byte operation = wrapper.passthrough(Types.BYTE);
                    modifiers[j] = new PlayerAttributesStorage.AttributeModifier(uuid, amount, operation);
                }
                if (entityId != this.tracker(wrapper.user()).clientEntityId()) continue;
                attributes.addAttribute(mappedIdentifier, new PlayerAttributesStorage.Attribute(value, modifiers));
            }
        });
        ((Protocol1_16To1_15_2)this.protocol).registerClientbound(ClientboundPackets1_16.PLAYER_INFO, wrapper -> {
            int action = wrapper.passthrough(Types.VAR_INT);
            int playerCount = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < playerCount; ++i) {
                wrapper.passthrough(Types.UUID);
                if (action == 0) {
                    wrapper.passthrough(Types.STRING);
                    int properties = wrapper.passthrough(Types.VAR_INT);
                    for (int j = 0; j < properties; ++j) {
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.OPTIONAL_STRING);
                    }
                    wrapper.passthrough(Types.VAR_INT);
                    wrapper.passthrough(Types.VAR_INT);
                    ((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(wrapper.user(), wrapper.passthrough(Types.OPTIONAL_COMPONENT));
                    continue;
                }
                if (action == 1) {
                    wrapper.passthrough(Types.VAR_INT);
                    continue;
                }
                if (action == 2) {
                    wrapper.passthrough(Types.VAR_INT);
                    continue;
                }
                if (action != 3) continue;
                ((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(wrapper.user(), wrapper.passthrough(Types.OPTIONAL_COMPONENT));
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, data) -> {
            JsonElement text;
            data.setDataType(Types1_14.ENTITY_DATA_TYPES.byId(data.dataType().typeId()));
            EntityDataType type = data.dataType();
            if (type == Types1_14.ENTITY_DATA_TYPES.itemType) {
                data.setValue(((Protocol1_16To1_15_2)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)data.getValue()));
            } else if (type == Types1_14.ENTITY_DATA_TYPES.optionalBlockStateType) {
                data.setValue(((Protocol1_16To1_15_2)this.protocol).getMappingData().getNewBlockStateId((Integer)data.getValue()));
            } else if (type == Types1_14.ENTITY_DATA_TYPES.particleType) {
                this.rewriteParticle(event.user(), (Particle)data.getValue());
            } else if (type == Types1_14.ENTITY_DATA_TYPES.optionalComponentType && (text = (JsonElement)data.value()) != null) {
                ((Protocol1_16To1_15_2)this.protocol).getComponentRewriter().processText(event.user(), text);
            }
        });
        this.filter().type(EntityTypes1_16.ZOGLIN).cancel(16);
        this.filter().type(EntityTypes1_16.HOGLIN).cancel(15);
        this.filter().type(EntityTypes1_16.PIGLIN).cancel(16);
        this.filter().type(EntityTypes1_16.PIGLIN).cancel(17);
        this.filter().type(EntityTypes1_16.PIGLIN).cancel(18);
        this.filter().type(EntityTypes1_16.STRIDER).index(15).handler((event, data) -> {
            boolean baby = (Boolean)data.value();
            data.setTypeAndValue(Types1_14.ENTITY_DATA_TYPES.varIntType, baby ? 1 : 3);
        });
        this.filter().type(EntityTypes1_16.STRIDER).cancel(16);
        this.filter().type(EntityTypes1_16.STRIDER).cancel(17);
        this.filter().type(EntityTypes1_16.STRIDER).cancel(18);
        this.filter().type(EntityTypes1_16.FISHING_BOBBER).cancel(8);
        this.filter().type(EntityTypes1_16.ABSTRACT_ARROW).cancel(8);
        this.filter().type(EntityTypes1_16.ABSTRACT_ARROW).handler((event, data) -> {
            if (event.index() >= 8) {
                event.setIndex(event.index() + 1);
            }
        });
        this.filter().type(EntityTypes1_16.WOLF).index(16).handler((event, data) -> {
            byte mask = (Byte)data.value();
            StoredEntityData entityData = this.tracker(event.user()).entityData(event.entityId());
            entityData.put(new WolfDataMaskStorage(mask));
        });
        this.filter().type(EntityTypes1_16.WOLF).index(20).handler((event, data) -> {
            int angerTime;
            WolfDataMaskStorage wolfData;
            StoredEntityData entityData = this.tracker(event.user()).entityDataIfPresent(event.entityId());
            byte previousMask = 0;
            if (entityData != null && (wolfData = entityData.get(WolfDataMaskStorage.class)) != null) {
                previousMask = wolfData.tameableMask();
            }
            byte tameableMask = (byte)((angerTime = ((Integer)data.value()).intValue()) > 0 ? previousMask | 2 : previousMask & 0xFFFFFFFD);
            event.createExtraData(new EntityData(16, Types1_14.ENTITY_DATA_TYPES.byteType, tameableMask));
            event.cancel();
        });
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(EntityTypes1_16.HOGLIN, EntityTypes1_16.COW).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_16.ZOGLIN, EntityTypes1_16.COW).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_16.PIGLIN, EntityTypes1_16.ZOMBIFIED_PIGLIN).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_16.STRIDER, EntityTypes1_16.MAGMA_CUBE).jsonName();
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_16.getTypeFromId(typeId);
    }
}

