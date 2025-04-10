/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_17to1_16_4.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16_2;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.TagUtil;

public final class EntityPacketRewriter1_17
extends EntityRewriter<ClientboundPackets1_17, Protocol1_17To1_16_4> {
    boolean warned = ViaBackwards.getConfig().bedrockAtY0() || ViaBackwards.getConfig().suppressEmulationWarnings();

    public EntityPacketRewriter1_17(Protocol1_17To1_16_4 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_17.ADD_ENTITY, EntityTypes1_17.FALLING_BLOCK);
        this.registerSpawnTracker(ClientboundPackets1_17.ADD_MOB);
        this.registerTracker(ClientboundPackets1_17.ADD_EXPERIENCE_ORB, EntityTypes1_17.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_17.ADD_PAINTING, EntityTypes1_17.PAINTING);
        this.registerTracker(ClientboundPackets1_17.ADD_PLAYER, EntityTypes1_17.PLAYER);
        this.registerSetEntityData(ClientboundPackets1_17.SET_ENTITY_DATA, Types1_17.ENTITY_DATA_LIST, Types1_16.ENTITY_DATA_LIST);
        ((Protocol1_17To1_16_4)this.protocol).appendClientbound(ClientboundPackets1_17.ADD_ENTITY, wrapper -> {
            int entityType = wrapper.get(Types.VAR_INT, 1);
            if (entityType != EntityTypes1_16_2.ITEM_FRAME.getId()) {
                return;
            }
            int data = wrapper.get(Types.INT, 0);
            float pitch = 0.0f;
            float yaw = 0.0f;
            switch (Math.abs(data % 6)) {
                case 0: {
                    pitch = 90.0f;
                    break;
                }
                case 1: {
                    pitch = -90.0f;
                    break;
                }
                case 2: {
                    yaw = 180.0f;
                    break;
                }
                case 4: {
                    yaw = 90.0f;
                    break;
                }
                case 5: {
                    yaw = 270.0f;
                }
            }
            wrapper.set(Types.BYTE, 0, (byte)(pitch * 256.0f / 360.0f));
            wrapper.set(Types.BYTE, 1, (byte)(yaw * 256.0f / 360.0f));
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.REMOVE_ENTITY, ClientboundPackets1_16_2.REMOVE_ENTITIES, wrapper -> {
            int entityId = wrapper.read(Types.VAR_INT);
            this.tracker(wrapper.user()).removeEntity(entityId);
            int[] array = new int[]{entityId};
            wrapper.write(Types.VAR_INT_ARRAY_PRIMITIVE, array);
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.STRING_ARRAY);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    byte previousGamemode = wrapper.get(Types.BYTE, 1);
                    if (previousGamemode == -1) {
                        wrapper.set(Types.BYTE, 1, (byte)0);
                    }
                });
                this.handler(EntityPacketRewriter1_17.this.getPlayerTrackerHandler());
                this.handler(EntityPacketRewriter1_17.this.worldDataTrackerHandler(1));
                this.handler(wrapper -> {
                    CompoundTag registry = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    ListTag<CompoundTag> biomes = TagUtil.getRegistryEntries(registry, "worldgen/biome");
                    for (CompoundTag biome : biomes) {
                        CompoundTag biomeCompound = biome.getCompoundTag("element");
                        StringTag category = biomeCompound.getStringTag("category");
                        if (!category.getValue().equalsIgnoreCase("underground")) continue;
                        category.setValue("none");
                    }
                    ListTag<CompoundTag> dimensions = TagUtil.getRegistryEntries(registry, "dimension_type");
                    for (CompoundTag dimension : dimensions) {
                        CompoundTag dimensionCompound = dimension.getCompoundTag("element");
                        EntityPacketRewriter1_17.this.reduceExtendedHeight(dimensionCompound, false);
                    }
                    EntityPacketRewriter1_17.this.reduceExtendedHeight(wrapper.get(Types.NAMED_COMPOUND_TAG, 1), true);
                });
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.STRING);
                this.handler(EntityPacketRewriter1_17.this.worldDataTrackerHandler(0));
                this.handler(wrapper -> EntityPacketRewriter1_17.this.reduceExtendedHeight(wrapper.get(Types.NAMED_COMPOUND_TAG, 0), true));
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.PLAYER_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT);
                this.read(Types.BOOLEAN);
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.UPDATE_ATTRIBUTES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> wrapper.write(Types.INT, wrapper.read(Types.VAR_INT)));
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).mergePacket(ClientboundPackets1_17.PLAYER_COMBAT_ENTER, ClientboundPackets1_16_2.PLAYER_COMBAT, 0);
        ((Protocol1_17To1_16_4)this.protocol).mergePacket(ClientboundPackets1_17.PLAYER_COMBAT_END, ClientboundPackets1_16_2.PLAYER_COMBAT, 1);
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.PLAYER_COMBAT_KILL, ClientboundPackets1_16_2.PLAYER_COMBAT, wrapper -> {
            wrapper.write(Types.VAR_INT, 2);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.INT);
            ((ComponentRewriter)((Protocol1_17To1_16_4)this.protocol).getComponentRewriter()).processText(wrapper.user(), wrapper.passthrough(Types.COMPONENT));
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, data) -> {
            data.setDataType(Types1_16.ENTITY_DATA_TYPES.byId(data.dataType().typeId()));
            EntityDataType type = data.dataType();
            if (type == Types1_16.ENTITY_DATA_TYPES.particleType) {
                Particle particle = (Particle)data.getValue();
                if (particle.id() == 16) {
                    particle.getArguments().subList(4, 7).clear();
                } else if (particle.id() == 37) {
                    particle.setId(0);
                    particle.getArguments().clear();
                    return;
                }
                this.rewriteParticle(event.user(), particle);
            } else if (type == Types1_16.ENTITY_DATA_TYPES.poseType) {
                int pose = (Integer)data.value();
                if (pose == 6) {
                    data.setValue(1);
                } else if (pose > 6) {
                    data.setValue(pose - 1);
                }
            }
        });
        this.registerEntityDataTypeHandler(Types1_16.ENTITY_DATA_TYPES.itemType, null, Types1_16.ENTITY_DATA_TYPES.optionalBlockStateType, null, Types1_16.ENTITY_DATA_TYPES.componentType, Types1_16.ENTITY_DATA_TYPES.optionalComponentType);
        this.filter().type(EntityTypes1_17.AXOLOTL).cancel(17);
        this.filter().type(EntityTypes1_17.AXOLOTL).cancel(18);
        this.filter().type(EntityTypes1_17.AXOLOTL).cancel(19);
        this.filter().type(EntityTypes1_17.GLOW_SQUID).cancel(16);
        this.filter().type(EntityTypes1_17.GOAT).cancel(17);
        this.filter().type(EntityTypes1_17.SHULKER).addIndex(17);
        this.filter().removeIndex(7);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(EntityTypes1_17.AXOLOTL, EntityTypes1_17.TROPICAL_FISH).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_17.GOAT, EntityTypes1_17.SHEEP).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_17.GLOW_SQUID, EntityTypes1_17.SQUID).jsonName();
        this.mapEntityTypeWithData(EntityTypes1_17.GLOW_ITEM_FRAME, EntityTypes1_17.ITEM_FRAME);
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_17.getTypeFromId(typeId);
    }

    void reduceExtendedHeight(CompoundTag tag, boolean warn) {
        NumberTag minY = tag.getNumberTag("min_y");
        NumberTag height = tag.getNumberTag("height");
        NumberTag logicalHeight = tag.getNumberTag("logical_height");
        if (minY.asInt() != 0 || height.asInt() > 256 || logicalHeight.asInt() > 256) {
            if (warn && !this.warned) {
                ((Protocol1_17To1_16_4)this.protocol).getLogger().warning("Increased world height is NOT SUPPORTED for 1.16 players and below. They will see a void below y 0 and above 256. You can enable the `bedrock-at-y-0` config option to replace the air with a bedrock layer.");
                this.warned = true;
            }
            tag.putInt("height", Math.min(256, height.asInt()));
            tag.putInt("logical_height", Math.min(256, logicalHeight.asInt()));
        }
    }
}

