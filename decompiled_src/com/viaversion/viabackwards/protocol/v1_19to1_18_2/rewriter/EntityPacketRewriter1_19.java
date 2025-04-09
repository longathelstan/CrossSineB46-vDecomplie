/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_19to1_18_2.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage.DimensionRegistryStorage;
import com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage.LastDeathPosition;
import com.viaversion.viabackwards.protocol.v1_19to1_18_2.storage.StoredPainting;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_18;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.protocols.v1_17_1to1_18.packet.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.packet.ClientboundPackets1_19;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.TagUtil;

public final class EntityPacketRewriter1_19
extends EntityRewriter<ClientboundPackets1_19, Protocol1_19To1_18_2> {
    public EntityPacketRewriter1_19(Protocol1_19To1_18_2 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerTracker(ClientboundPackets1_19.ADD_EXPERIENCE_ORB, EntityTypes1_19.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_19.ADD_PLAYER, EntityTypes1_19.PLAYER);
        this.registerSetEntityData(ClientboundPackets1_19.SET_ENTITY_DATA, Types1_19.ENTITY_DATA_LIST, Types1_18.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19.REMOVE_ENTITIES);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.ADD_ENTITY, new PacketHandlers(){

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
                this.handler(wrapper -> {
                    byte headYaw = wrapper.read(Types.BYTE);
                    int data = wrapper.read(Types.VAR_INT);
                    EntityType entityType = EntityPacketRewriter1_19.this.trackAndMapEntity(wrapper);
                    if (entityType.isOrHasParent(EntityTypes1_19.LIVING_ENTITY)) {
                        wrapper.write(Types.BYTE, headYaw);
                        byte pitch = wrapper.get(Types.BYTE, 0);
                        byte yaw = wrapper.get(Types.BYTE, 1);
                        wrapper.set(Types.BYTE, 0, yaw);
                        wrapper.set(Types.BYTE, 1, pitch);
                        wrapper.setPacketType(ClientboundPackets1_18.ADD_MOB);
                        return;
                    }
                    if (entityType == EntityTypes1_19.PAINTING) {
                        wrapper.cancel();
                        int entityId = wrapper.get(Types.VAR_INT, 0);
                        StoredEntityData entityData = EntityPacketRewriter1_19.this.tracker(wrapper.user()).entityData(entityId);
                        BlockPosition position = new BlockPosition(wrapper.get(Types.DOUBLE, 0).intValue(), wrapper.get(Types.DOUBLE, 1).intValue(), wrapper.get(Types.DOUBLE, 2).intValue());
                        entityData.put(new StoredPainting(entityId, wrapper.get(Types.UUID, 0), position, data));
                        return;
                    }
                    if (entityType == EntityTypes1_19.FALLING_BLOCK) {
                        data = ((Protocol1_19To1_18_2)EntityPacketRewriter1_19.this.protocol).getMappingData().getNewBlockStateId(data);
                    }
                    wrapper.write(Types.INT, data);
                });
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.UPDATE_MOB_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.handler(wrapper -> wrapper.read(Types.OPTIONAL_NAMED_COMPOUND_TAG));
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.STRING_ARRAY);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    DimensionRegistryStorage dimensionRegistryStorage = wrapper.user().get(DimensionRegistryStorage.class);
                    dimensionRegistryStorage.clear();
                    String dimensionKey = Key.stripMinecraftNamespace(wrapper.read(Types.STRING));
                    CompoundTag registry = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    ListTag<CompoundTag> dimensions = TagUtil.getRegistryEntries(registry, "dimension_type");
                    boolean found = false;
                    for (CompoundTag compoundTag : dimensions) {
                        String name = Key.stripMinecraftNamespace(compoundTag.getString("name"));
                        CompoundTag dimensionData = compoundTag.getCompoundTag("element");
                        dimensionRegistryStorage.addDimension(name, dimensionData.copy());
                        if (found || !name.equals(dimensionKey)) continue;
                        wrapper.write(Types.NAMED_COMPOUND_TAG, dimensionData);
                        found = true;
                    }
                    if (!found) {
                        String string = dimensionKey;
                        throw new IllegalStateException("Could not find dimension " + string + " in dimension registry");
                    }
                    ListTag<CompoundTag> biomes = TagUtil.getRegistryEntries(registry, "worldgen/biome");
                    for (CompoundTag biome : biomes) {
                        CompoundTag biomeCompound = biome.getCompoundTag("element");
                        biomeCompound.putString("category", "none");
                    }
                    EntityPacketRewriter1_19.this.tracker(wrapper.user()).setBiomesSent(biomes.size());
                    ListTag<CompoundTag> listTag = TagUtil.removeRegistryEntries(registry, "chat_type");
                    for (CompoundTag chatType : listTag) {
                        NumberTag idTag = chatType.getNumberTag("id");
                        dimensionRegistryStorage.addChatType(idTag.asInt(), chatType);
                    }
                });
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    GlobalBlockPosition lastDeathPosition = wrapper.read(Types.OPTIONAL_GLOBAL_POSITION);
                    if (lastDeathPosition != null) {
                        wrapper.user().put(new LastDeathPosition(lastDeathPosition));
                    } else {
                        wrapper.user().remove(LastDeathPosition.class);
                    }
                });
                this.handler(EntityPacketRewriter1_19.this.worldDataTrackerHandler(1));
                this.handler(EntityPacketRewriter1_19.this.playerTrackerHandler());
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    String dimensionKey = wrapper.read(Types.STRING);
                    CompoundTag dimension = wrapper.user().get(DimensionRegistryStorage.class).dimension(dimensionKey);
                    if (dimension == null) {
                        String string = dimensionKey;
                        throw new IllegalArgumentException("Could not find dimension " + string + " in dimension registry");
                    }
                    wrapper.write(Types.NAMED_COMPOUND_TAG, dimension);
                });
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    GlobalBlockPosition lastDeathPosition = wrapper.read(Types.OPTIONAL_GLOBAL_POSITION);
                    if (lastDeathPosition != null) {
                        wrapper.user().put(new LastDeathPosition(lastDeathPosition));
                    } else {
                        wrapper.user().remove(LastDeathPosition.class);
                    }
                });
                this.handler(EntityPacketRewriter1_19.this.worldDataTrackerHandler(0));
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_19.PLAYER_INFO, wrapper -> {
            int action = wrapper.passthrough(Types.VAR_INT);
            int entries = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < entries; ++i) {
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
                    wrapper.passthrough(Types.OPTIONAL_COMPONENT);
                    wrapper.read(Types.OPTIONAL_PROFILE_KEY);
                    continue;
                }
                if (action == 1 || action == 2) {
                    wrapper.passthrough(Types.VAR_INT);
                    continue;
                }
                if (action != 3) continue;
                wrapper.passthrough(Types.OPTIONAL_COMPONENT);
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, data) -> {
            int pose;
            EntityDataType type;
            if (data.dataType().typeId() <= Types1_18.ENTITY_DATA_TYPES.poseType.typeId()) {
                data.setDataType(Types1_18.ENTITY_DATA_TYPES.byId(data.dataType().typeId()));
            }
            if ((type = data.dataType()) == Types1_18.ENTITY_DATA_TYPES.particleType) {
                Particle particle = (Particle)data.getValue();
                ParticleMappings particleMappings = ((Protocol1_19To1_18_2)this.protocol).getMappingData().getParticleMappings();
                if (particle.id() == particleMappings.id("sculk_charge")) {
                    event.cancel();
                    return;
                }
                if (particle.id() == particleMappings.id("shriek")) {
                    event.cancel();
                    return;
                }
                if (particle.id() == particleMappings.id("vibration")) {
                    event.cancel();
                    return;
                }
                this.rewriteParticle(event.user(), particle);
            } else if (type == Types1_18.ENTITY_DATA_TYPES.poseType && (pose = ((Integer)data.value()).intValue()) >= 8) {
                data.setValue(0);
            }
        });
        this.registerEntityDataTypeHandler(Types1_18.ENTITY_DATA_TYPES.itemType, null, Types1_18.ENTITY_DATA_TYPES.optionalBlockStateType, null, Types1_18.ENTITY_DATA_TYPES.componentType, Types1_18.ENTITY_DATA_TYPES.optionalComponentType);
        this.registerBlockStateHandler(EntityTypes1_19.ABSTRACT_MINECART, 11);
        this.filter().type(EntityTypes1_19.PAINTING).index(8).handler((event, data) -> {
            event.cancel();
            StoredEntityData entityData = this.tracker(event.user()).entityDataIfPresent(event.entityId());
            StoredPainting storedPainting = entityData.remove(StoredPainting.class);
            if (storedPainting != null) {
                PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_18.ADD_PAINTING, event.user());
                packet.write(Types.VAR_INT, storedPainting.entityId());
                packet.write(Types.UUID, storedPainting.uuid());
                packet.write(Types.VAR_INT, (Integer)data.value());
                packet.write(Types.BLOCK_POSITION1_14, storedPainting.position());
                packet.write(Types.BYTE, storedPainting.direction());
                try {
                    packet.send(Protocol1_19To1_18_2.class);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        this.filter().type(EntityTypes1_19.CAT).index(19).handler((event, data) -> data.setDataType(Types1_18.ENTITY_DATA_TYPES.varIntType));
        this.filter().type(EntityTypes1_19.FROG).cancel(16);
        this.filter().type(EntityTypes1_19.FROG).cancel(17);
        this.filter().type(EntityTypes1_19.FROG).cancel(18);
        this.filter().type(EntityTypes1_19.WARDEN).cancel(16);
        this.filter().type(EntityTypes1_19.GOAT).cancel(18);
        this.filter().type(EntityTypes1_19.GOAT).cancel(19);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(EntityTypes1_19.FROG, EntityTypes1_19.RABBIT).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_19.TADPOLE, EntityTypes1_19.PUFFERFISH).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_19.CHEST_BOAT, EntityTypes1_19.BOAT);
        this.mapEntityTypeWithData(EntityTypes1_19.WARDEN, EntityTypes1_19.IRON_GOLEM).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_19.ALLAY, EntityTypes1_19.VEX).jsonName();
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_19.getTypeFromId(typeId);
    }
}

