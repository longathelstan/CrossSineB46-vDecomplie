/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.EntityIdMappings1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.PaintingNames1_13;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.ParticleIdMappings1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.BackwardsBlockStorage;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.NoteBlockStorage;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.PlayerPositionStorage1_13;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_12;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;

public class EntityPacketRewriter1_13
extends LegacyEntityRewriter<ClientboundPackets1_13, Protocol1_13To1_12_2> {
    public EntityPacketRewriter1_13(Protocol1_13To1_12_2 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.PLAYER_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                        return;
                    }
                    PlayerPositionStorage1_13 playerStorage = wrapper.user().get(PlayerPositionStorage1_13.class);
                    byte bitField = wrapper.get(Types.BYTE, 0);
                    playerStorage.setX(EntityPacketRewriter1_13.toSet(bitField, 0, playerStorage.x(), wrapper.get(Types.DOUBLE, 0)));
                    playerStorage.setY(EntityPacketRewriter1_13.toSet(bitField, 1, playerStorage.y(), wrapper.get(Types.DOUBLE, 1)));
                    playerStorage.setZ(EntityPacketRewriter1_13.toSet(bitField, 2, playerStorage.z(), wrapper.get(Types.DOUBLE, 2)));
                });
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.ADD_ENTITY, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_13.this.getObjectTrackerHandler());
                this.handler(wrapper -> {
                    EntityTypes1_13.ObjectType type = EntityTypes1_13.ObjectType.findById(wrapper.get(Types.BYTE, 0).byteValue());
                    if (type == EntityTypes1_13.ObjectType.FALLING_BLOCK) {
                        int blockState = wrapper.get(Types.INT, 0);
                        int combined = Protocol1_13To1_12_2.MAPPINGS.getNewBlockStateId(blockState);
                        combined = combined >> 4 & 0xFFF | (combined & 0xF) << 12;
                        wrapper.set(Types.INT, 0, combined);
                    } else if (type == EntityTypes1_13.ObjectType.ITEM_FRAME) {
                        int n;
                        int data = wrapper.get(Types.INT, 0);
                        switch (data) {
                            case 3: {
                                n = 0;
                                break;
                            }
                            case 4: {
                                n = 1;
                                break;
                            }
                            case 5: {
                                n = 3;
                                break;
                            }
                            default: {
                                n = data;
                            }
                        }
                        data = n;
                        wrapper.set(Types.INT, 0, data);
                    } else if (type == EntityTypes1_13.ObjectType.TRIDENT) {
                        wrapper.set(Types.BYTE, 0, (byte)EntityTypes1_13.ObjectType.TIPPED_ARROW.getId());
                    }
                });
            }
        });
        this.registerTracker(ClientboundPackets1_13.ADD_EXPERIENCE_ORB, EntityTypes1_13.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_13.ADD_GLOBAL_ENTITY, EntityTypes1_13.EntityType.LIGHTNING_BOLT);
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.ADD_MOB, new PacketHandlers(){

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
                this.map(Types1_13.ENTITY_DATA_LIST, Types1_12.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    int type = wrapper.get(Types.VAR_INT, 1);
                    EntityTypes1_13.EntityType entityType = EntityTypes1_13.getTypeFromId(type, false);
                    EntityPacketRewriter1_13.this.tracker(wrapper.user()).addEntity(wrapper.get(Types.VAR_INT, 0), entityType);
                    int oldId = EntityIdMappings1_12_2.getOldId(type);
                    if (oldId == -1) {
                        if (!EntityPacketRewriter1_13.this.hasData(entityType)) {
                            EntityTypes1_13.EntityType entityType2 = entityType;
                            int n = type;
                            ((Protocol1_13To1_12_2)EntityPacketRewriter1_13.this.protocol).getLogger().warning("Could not find entity type mapping " + n + "/" + entityType2);
                        }
                    } else {
                        wrapper.set(Types.VAR_INT, 1, oldId);
                    }
                });
                this.handler(EntityPacketRewriter1_13.this.getMobSpawnRewriter1_11(Types1_12.ENTITY_DATA_LIST));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types1_13.ENTITY_DATA_LIST, Types1_12.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_13.this.getTrackerAndDataHandler(Types1_12.ENTITY_DATA_LIST, EntityTypes1_13.EntityType.PLAYER));
            }
        });
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.ADD_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.handler(EntityPacketRewriter1_13.this.getTrackerHandler(EntityTypes1_13.EntityType.PAINTING));
                this.handler(wrapper -> {
                    int motive = wrapper.read(Types.VAR_INT);
                    String title2 = PaintingNames1_13.getStringId(motive);
                    wrapper.write(Types.STRING, title2);
                });
            }
        });
        this.registerJoinGame(ClientboundPackets1_13.LOGIN, EntityTypes1_13.EntityType.PLAYER);
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int dimensionId;
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_13To1_12_2.class);
                    if (((ClientWorld)clientWorld).setEnvironment(dimensionId = wrapper.get(Types.INT, 0).intValue())) {
                        EntityPacketRewriter1_13.this.tracker(wrapper.user()).clearEntities();
                        wrapper.user().get(BackwardsBlockStorage.class).clear();
                        wrapper.user().get(NoteBlockStorage.class).clear();
                    }
                });
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_13.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_13.SET_ENTITY_DATA, Types1_13.ENTITY_DATA_LIST, Types1_12.ENTITY_DATA_LIST);
        ((Protocol1_13To1_12_2)this.protocol).registerClientbound(ClientboundPackets1_13.PLAYER_LOOK_AT, null, wrapper -> {
            wrapper.cancel();
            if (!ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                return;
            }
            int anchor = wrapper.read(Types.VAR_INT);
            double x = wrapper.read(Types.DOUBLE);
            double y = wrapper.read(Types.DOUBLE);
            double z = wrapper.read(Types.DOUBLE);
            PlayerPositionStorage1_13 positionStorage = wrapper.user().get(PlayerPositionStorage1_13.class);
            PacketWrapper positionAndLook = wrapper.create(ClientboundPackets1_12_1.PLAYER_POSITION);
            positionAndLook.write(Types.DOUBLE, 0.0);
            positionAndLook.write(Types.DOUBLE, 0.0);
            positionAndLook.write(Types.DOUBLE, 0.0);
            EntityPositionHandler.writeFacingDegrees(positionAndLook, positionStorage.x(), anchor == 1 ? positionStorage.y() + 1.62 : positionStorage.y(), positionStorage.z(), x, y, z);
            positionAndLook.write(Types.BYTE, (byte)7);
            positionAndLook.write(Types.VAR_INT, -1);
            positionAndLook.send(Protocol1_13To1_12_2.class);
        });
        if (ViaBackwards.getConfig().isFix1_13FacePlayer()) {
            PacketHandlers movementRemapper = new PacketHandlers(){

                @Override
                public void register() {
                    this.map(Types.DOUBLE);
                    this.map(Types.DOUBLE);
                    this.map(Types.DOUBLE);
                    this.handler(wrapper -> wrapper.user().get(PlayerPositionStorage1_13.class).setCoordinates(wrapper, false));
                }
            };
            ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.MOVE_PLAYER_POS, movementRemapper);
            ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.MOVE_PLAYER_POS_ROT, movementRemapper);
            ((Protocol1_13To1_12_2)this.protocol).registerServerbound(ServerboundPackets1_12_1.MOVE_VEHICLE, movementRemapper);
        }
    }

    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(EntityTypes1_13.EntityType.DROWNED, EntityTypes1_13.EntityType.ZOMBIE_VILLAGER).plainName();
        this.mapEntityTypeWithData(EntityTypes1_13.EntityType.COD, EntityTypes1_13.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(EntityTypes1_13.EntityType.SALMON, EntityTypes1_13.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(EntityTypes1_13.EntityType.PUFFERFISH, EntityTypes1_13.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(EntityTypes1_13.EntityType.TROPICAL_FISH, EntityTypes1_13.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(EntityTypes1_13.EntityType.PHANTOM, EntityTypes1_13.EntityType.PARROT).plainName().spawnEntityData(storage -> storage.add(new EntityData(15, EntityDataTypes1_12.VAR_INT, 3)));
        this.mapEntityTypeWithData(EntityTypes1_13.EntityType.DOLPHIN, EntityTypes1_13.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(EntityTypes1_13.EntityType.TURTLE, EntityTypes1_13.EntityType.OCELOT).plainName();
        this.filter().handler((event, data) -> {
            int typeId = data.dataType().typeId();
            if (typeId == 4) {
                JsonElement element = (JsonElement)data.value();
                ((Protocol1_13To1_12_2)this.protocol).translatableRewriter().processText(event.user(), element);
                data.setDataType(EntityDataTypes1_12.COMPONENT);
            } else if (typeId == 5) {
                JsonElement element = (JsonElement)data.value();
                data.setTypeAndValue(EntityDataTypes1_12.STRING, ((Protocol1_13To1_12_2)this.protocol).jsonToLegacy(event.user(), element));
            } else if (typeId == 6) {
                Item item = (Item)data.getValue();
                data.setTypeAndValue(EntityDataTypes1_12.ITEM, ((Protocol1_13To1_12_2)this.protocol).getItemRewriter().handleItemToClient(event.user(), item));
            } else if (typeId == 15) {
                event.cancel();
            } else {
                data.setDataType(EntityDataTypes1_12.byId(typeId > 5 ? typeId - 1 : typeId));
            }
        });
        this.filter().type(EntityTypes1_13.EntityType.ZOMBIE).removeIndex(15);
        this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(13);
        this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(14);
        this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(15);
        this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(16);
        this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(17);
        this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(18);
        this.filter().type(EntityTypes1_13.EntityType.ABSTRACT_FISH).cancel(12);
        this.filter().type(EntityTypes1_13.EntityType.ABSTRACT_FISH).cancel(13);
        this.filter().type(EntityTypes1_13.EntityType.PHANTOM).cancel(12);
        this.filter().type(EntityTypes1_13.EntityType.BOAT).cancel(12);
        this.filter().type(EntityTypes1_13.EntityType.TRIDENT).cancel(7);
        this.filter().type(EntityTypes1_13.EntityType.WOLF).index(17).handler((event, data) -> data.setValue(15 - (Integer)data.getValue()));
        this.filter().type(EntityTypes1_13.EntityType.AREA_EFFECT_CLOUD).index(9).handler((event, data) -> {
            Particle particle = (Particle)data.getValue();
            ParticleIdMappings1_12_2.ParticleData particleData = ParticleIdMappings1_12_2.getMapping(particle.id());
            int firstArg = 0;
            int secondArg = 0;
            int[] particleArgs = particleData.rewriteMeta((Protocol1_13To1_12_2)this.protocol, particle.getArguments());
            if (particleArgs != null && particleArgs.length != 0) {
                if (particleData.getHandler().isBlockHandler() && particleArgs[0] == 0) {
                    particleArgs[0] = 102;
                }
                firstArg = particleArgs[0];
                secondArg = particleArgs.length == 2 ? particleArgs[1] : 0;
            }
            event.createExtraData(new EntityData(9, EntityDataTypes1_12.VAR_INT, particleData.getHistoryId()));
            event.createExtraData(new EntityData(10, EntityDataTypes1_12.VAR_INT, firstArg));
            event.createExtraData(new EntityData(11, EntityDataTypes1_12.VAR_INT, secondArg));
            event.cancel();
        });
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_13.getTypeFromId(typeId, false);
    }

    @Override
    public EntityType objectTypeFromId(int typeId) {
        return EntityTypes1_13.getTypeFromId(typeId, true);
    }

    @Override
    public int newEntityId(int newId) {
        return EntityIdMappings1_12_2.getOldId(newId);
    }

    static double toSet(int field, int bitIndex, double origin, double packetValue) {
        return (field & 1 << bitIndex) != 0 ? origin + packetValue : packetValue;
    }
}

