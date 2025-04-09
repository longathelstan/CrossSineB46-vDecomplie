/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.rewriter;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.minecraft.entitydata.EntityDataTypes1_7_6_10;
import com.viaversion.viarewind.api.rewriter.VREntityRewriter;
import com.viaversion.viarewind.api.type.RewindTypes;
import com.viaversion.viarewind.api.type.version.Types1_7_6_10;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data.EntityDataIndex1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.data.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.EntityTracker1_8;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.ScoreboardTracker;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_8;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEvent;
import com.viaversion.viaversion.util.IdAndData;
import java.util.List;
import java.util.UUID;

public class EntityPacketRewriter1_8
extends VREntityRewriter<ClientboundPackets1_8, Protocol1_8To1_7_6_10> {
    public EntityPacketRewriter1_8(Protocol1_8To1_7_6_10 protocol) {
        super(protocol, EntityDataTypes1_7_6_10.STRING, EntityDataTypes1_7_6_10.BYTE);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.STRING);
                this.read(Types.BOOLEAN);
                this.handler(EntityPacketRewriter1_8.this.playerTrackerHandler());
                this.handler(EntityPacketRewriter1_8.this.getDimensionHandler());
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.INT, 0);
                    if (ViaRewind.getConfig().isReplaceAdventureMode() && wrapper.get(Types.UNSIGNED_BYTE, 0) == 2) {
                        wrapper.set(Types.UNSIGNED_BYTE, 0, (short)0);
                    }
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    tracker.addPlayer(entityId, wrapper.user().getProtocolInfo().getUuid());
                    tracker.setClientEntityGameMode(wrapper.get(Types.UNSIGNED_BYTE, 0).shortValue());
                    wrapper.user().put(new ScoreboardTracker(wrapper.user()));
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.REMOVE_ENTITIES, wrapper -> {
            int[] entities = wrapper.read(Types.VAR_INT_ARRAY_PRIMITIVE);
            this.removeEntities(wrapper.user(), entities);
            wrapper.cancel();
            List parts = Lists.partition((List)Ints.asList((int[])entities), (int)127);
            for (List part : parts) {
                PacketWrapper destroy = PacketWrapper.create(ClientboundPackets1_7_2_5.REMOVE_ENTITIES, wrapper.user());
                destroy.write(RewindTypes.INT_ARRAY, part.stream().mapToInt(Integer::intValue).toArray());
                destroy.send(Protocol1_8To1_7_6_10.class);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types1_8.ENTITY_DATA_LIST, Types1_7_6_10.ENTITY_DATA_LIST);
                this.handler(wrapper -> {
                    EntityTracker1_8 tracker = (EntityTracker1_8)EntityPacketRewriter1_8.this.tracker(wrapper.user());
                    int entityId = wrapper.get(Types.INT, 0);
                    List<EntityData> entityData = wrapper.get(Types1_7_6_10.ENTITY_DATA_LIST, 0);
                    if (tracker.getHolograms().containsKey(entityId)) {
                        wrapper.cancel();
                        ((VirtualHologramEntity)tracker.getHolograms().get(entityId)).syncState(EntityPacketRewriter1_8.this, entityData);
                    } else if (tracker.hasEntity(entityId)) {
                        EntityPacketRewriter1_8.this.handleEntityData(entityId, entityData, wrapper.user());
                    } else {
                        wrapper.cancel();
                    }
                    if (tracker.clientEntityId() == entityId) {
                        tracker.updateEntityData(entityData);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.handler(EntityPacketRewriter1_8.this.getObjectTrackerHandler());
                this.handler(EntityPacketRewriter1_8.this.getObjectRewriter(EntityTypes1_8.ObjectType::findById));
                this.handler(wrapper -> {
                    byte by;
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    EntityTypes1_8.EntityType type = EntityTypes1_8.getTypeFromId(wrapper.get(Types.BYTE, 0).byteValue(), true);
                    int x = wrapper.get(Types.INT, 0);
                    int y = wrapper.get(Types.INT, 1);
                    int z = wrapper.get(Types.INT, 2);
                    byte pitch = wrapper.get(Types.BYTE, 1);
                    byte by2 = wrapper.get(Types.BYTE, 2);
                    int data = wrapper.get(Types.INT, 3);
                    if (type == EntityTypes1_8.ObjectType.ITEM_FRAME.getType()) {
                        byte by3;
                        switch (by2) {
                            case -128: {
                                z += 32;
                                by3 = 0;
                                break;
                            }
                            case -64: {
                                x -= 32;
                                by3 = -64;
                                break;
                            }
                            case 0: {
                                z -= 32;
                                by3 = -128;
                                break;
                            }
                            case 64: {
                                x += 32;
                                by3 = 64;
                                break;
                            }
                            default: {
                                by3 = by2;
                            }
                        }
                        by = by3;
                    } else if (type == EntityTypes1_8.ObjectType.ARMOR_STAND.getType()) {
                        wrapper.cancel();
                        EntityTracker1_8 tracker = (EntityTracker1_8)EntityPacketRewriter1_8.this.tracker(wrapper.user());
                        VirtualHologramEntity hologram = (VirtualHologramEntity)tracker.getHolograms().get(entityId);
                        hologram.setPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        hologram.setRotation((float)by2 * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        hologram.setHeadYaw((float)by2 * 360.0f / 256.0f);
                    } else if (type != null && type.isOrHasParent(EntityTypes1_8.EntityType.FALLING_BLOCK)) {
                        int blockId = data & 0xFFF;
                        int blockData = data >> 12 & 0xF;
                        IdAndData replace = ((Protocol1_8To1_7_6_10)EntityPacketRewriter1_8.this.protocol).getItemRewriter().handleBlock(blockId, blockData);
                        if (replace != null) {
                            blockId = replace.getId();
                            blockData = replace.getData();
                        }
                        data = blockId | blockData << 16;
                        wrapper.set(Types.INT, 3, data);
                    }
                    wrapper.set(Types.INT, 0, x);
                    wrapper.set(Types.INT, 1, y);
                    wrapper.set(Types.INT, 2, z);
                    wrapper.set(Types.BYTE, 2, by);
                    if (data > 0) {
                        wrapper.passthrough(Types.SHORT);
                        wrapper.passthrough(Types.SHORT);
                        wrapper.passthrough(Types.SHORT);
                    }
                });
            }
        });
        this.registerTracker(ClientboundPackets1_8.ADD_EXPERIENCE_ORB, EntityTypes1_8.EntityType.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_8.ADD_GLOBAL_ENTITY, EntityTypes1_8.EntityType.LIGHTNING_BOLT);
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.ADD_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.STRING);
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.INT_POSITION);
                this.map(Types.UNSIGNED_BYTE, Types.INT);
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    BlockPosition position = wrapper.get(RewindTypes.INT_POSITION, 0);
                    int rotation = wrapper.get(Types.INT, 0);
                    int modX = 0;
                    int modZ = 0;
                    switch (rotation) {
                        case 0: {
                            modZ = -1;
                            break;
                        }
                        case 1: {
                            modX = 1;
                            break;
                        }
                        case 2: {
                            modZ = 1;
                            break;
                        }
                        case 3: {
                            modX = -1;
                        }
                    }
                    wrapper.set(RewindTypes.INT_POSITION, 0, new BlockPosition(position.x() + modX, position.y(), position.z() + modZ));
                    EntityPacketRewriter1_8.this.addTrackedEntity(wrapper, entityId, EntityTypes1_8.EntityType.PAINTING);
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types.SHORT);
                this.map(Types1_8.ENTITY_DATA_LIST, Types1_7_6_10.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_8.this.getTrackerHandler(Types.UNSIGNED_BYTE, 0));
                this.handler(wrapper -> {
                    short typeId = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    EntityTypes1_8.EntityType type = EntityTypes1_8.getTypeFromId(typeId, false);
                    if (type == EntityTypes1_8.EntityType.ARMOR_STAND) {
                        wrapper.cancel();
                        int entityId = wrapper.get(Types.VAR_INT, 0);
                        int x = wrapper.get(Types.INT, 0);
                        int y = wrapper.get(Types.INT, 1);
                        int z = wrapper.get(Types.INT, 2);
                        byte pitch = wrapper.get(Types.BYTE, 1);
                        byte yaw = wrapper.get(Types.BYTE, 0);
                        byte headYaw = wrapper.get(Types.BYTE, 2);
                        EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                        VirtualHologramEntity hologram = (VirtualHologramEntity)tracker.getHolograms().get(entityId);
                        hologram.setPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        hologram.setRotation((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                        hologram.setHeadYaw((float)headYaw * 360.0f / 256.0f);
                        hologram.syncState(((Protocol1_8To1_7_6_10)EntityPacketRewriter1_8.this.protocol()).getEntityRewriter(), wrapper.get(Types1_7_6_10.ENTITY_DATA_LIST, 0));
                    }
                });
                this.handler(EntityPacketRewriter1_8.this.getMobSpawnRewriter(Types1_7_6_10.ENTITY_DATA_LIST));
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    UUID uuid = wrapper.read(Types.UUID);
                    wrapper.write(Types.STRING, uuid.toString());
                    GameProfileStorage gameProfileStorage = wrapper.user().get(GameProfileStorage.class);
                    GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
                    if (gameProfile == null) {
                        wrapper.write(Types.STRING, "");
                        wrapper.write(Types.VAR_INT, 0);
                    } else {
                        wrapper.write(Types.STRING, gameProfile.name.length() > 16 ? gameProfile.name.substring(0, 16) : gameProfile.name);
                        wrapper.write(Types.VAR_INT, gameProfile.properties.size());
                        for (GameProfileStorage.Property property : gameProfile.properties) {
                            wrapper.write(Types.STRING, property.name);
                            wrapper.write(Types.STRING, property.value);
                            wrapper.write(Types.STRING, property.signature == null ? "" : property.signature);
                        }
                    }
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    if (gameProfile != null && gameProfile.gamemode == 3) {
                        for (short i = 0; i < 5; i = (short)(i + 1)) {
                            PacketWrapper entityEquipment = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_EQUIPPED_ITEM, wrapper.user());
                            entityEquipment.write(Types.INT, entityId);
                            entityEquipment.write(Types.SHORT, i);
                            entityEquipment.write(RewindTypes.COMPRESSED_NBT_ITEM, i == 4 ? gameProfile.getSkull() : null);
                            entityEquipment.scheduleSend(Protocol1_8To1_7_6_10.class);
                        }
                    }
                    tracker.addPlayer(entityId, uuid);
                });
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types1_8.ENTITY_DATA_LIST, Types1_7_6_10.ENTITY_DATA_LIST);
                this.handler(EntityPacketRewriter1_8.this.getTrackerAndDataHandler(Types1_7_6_10.ENTITY_DATA_LIST, EntityTypes1_8.EntityType.PLAYER));
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_EQUIPPED_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.SHORT);
                this.map(Types.ITEM1_8, RewindTypes.COMPRESSED_NBT_ITEM);
                this.handler(wrapper -> ((Protocol1_8To1_7_6_10)EntityPacketRewriter1_8.this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), wrapper.get(RewindTypes.COMPRESSED_NBT_ITEM, 0)));
                this.handler(wrapper -> {
                    short limit;
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    int id = wrapper.get(Types.INT, 0);
                    short s = limit = tracker.clientEntityId() == id ? (short)3 : (short)4;
                    if (wrapper.get(Types.SHORT, 0) > limit) {
                        wrapper.cancel();
                    }
                });
                this.handler(wrapper -> {
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    short slot = wrapper.get(Types.SHORT, 0);
                    UUID uuid = tracker.getPlayerUUID(wrapper.get(Types.INT, 0));
                    if (uuid == null) {
                        return;
                    }
                    Item item = wrapper.get(RewindTypes.COMPRESSED_NBT_ITEM, 0);
                    wrapper.user().get(PlayerSessionStorage.class).setPlayerEquipment(uuid, item, slot);
                    GameProfileStorage storage = wrapper.user().get(GameProfileStorage.class);
                    GameProfileStorage.GameProfile profile = storage.get(uuid);
                    if (profile != null && profile.gamemode == 3) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.PLAYER_SLEEP, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.BLOCK_POSITION1_8, RewindTypes.U_BYTE_POSITION);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.TAKE_ITEM_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.VAR_INT, Types.INT);
                this.handler(wrapper -> wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class).removeEntity(wrapper.get(Types.INT, 0)));
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_ENTITY_MOTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.MOVE_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.MOVE_ENTITY_POS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.read(Types.BOOLEAN);
                this.handler(wrapper -> {
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    VirtualHologramEntity hologram = tracker.getHolograms().get(wrapper.get(Types.INT, 0));
                    if (hologram != null) {
                        wrapper.cancel();
                        byte x = wrapper.get(Types.BYTE, 0);
                        byte y = wrapper.get(Types.BYTE, 1);
                        byte z = wrapper.get(Types.BYTE, 2);
                        hologram.setRelativePosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.MOVE_ENTITY_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.read(Types.BOOLEAN);
                this.handler(wrapper -> {
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    VirtualHologramEntity hologram = tracker.getHolograms().get(wrapper.get(Types.INT, 0));
                    if (hologram != null) {
                        wrapper.cancel();
                        byte yaw = wrapper.get(Types.BYTE, 0);
                        byte pitch = wrapper.get(Types.BYTE, 1);
                        hologram.setRotation((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.MOVE_ENTITY_POS_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.read(Types.BOOLEAN);
                this.handler(wrapper -> {
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    VirtualHologramEntity hologram = tracker.getHolograms().get(wrapper.get(Types.INT, 0));
                    if (hologram != null) {
                        wrapper.cancel();
                        byte x = wrapper.get(Types.BYTE, 0);
                        byte y = wrapper.get(Types.BYTE, 1);
                        byte z = wrapper.get(Types.BYTE, 2);
                        byte yaw = wrapper.get(Types.BYTE, 3);
                        byte pitch = wrapper.get(Types.BYTE, 4);
                        hologram.setRelativePosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        hologram.setRotation((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.TELEPORT_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.read(Types.BOOLEAN);
                this.handler(wrapper -> {
                    VirtualHologramEntity hologram;
                    int entityId = wrapper.get(Types.INT, 0);
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    if (tracker.entityType(entityId) == EntityTypes1_8.EntityType.ABSTRACT_MINECART) {
                        int y = wrapper.get(Types.INT, 2);
                        wrapper.set(Types.INT, 2, y += 12);
                    }
                    if ((hologram = (VirtualHologramEntity)tracker.getHolograms().get(entityId)) != null) {
                        wrapper.cancel();
                        int x = wrapper.get(Types.INT, 1);
                        int y = wrapper.get(Types.INT, 2);
                        int z = wrapper.get(Types.INT, 3);
                        byte yaw = wrapper.get(Types.BYTE, 0);
                        byte pitch = wrapper.get(Types.BYTE, 1);
                        hologram.setPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                        hologram.setRotation((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.ROTATE_HEAD, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    VirtualHologramEntity hologram = tracker.getHolograms().get(wrapper.get(Types.INT, 0));
                    if (hologram != null) {
                        wrapper.cancel();
                        byte yaw = wrapper.get(Types.BYTE, 0);
                        hologram.setHeadYaw((float)yaw * 360.0f / 256.0f);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.SET_ENTITY_LINK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    boolean leash = wrapper.get(Types.BOOLEAN, 0);
                    if (!leash) {
                        EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                        int passenger = wrapper.get(Types.INT, 0);
                        int vehicle = wrapper.get(Types.INT, 1);
                        tracker.setPassenger(vehicle, passenger);
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.UPDATE_MOB_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT, Types.SHORT);
                this.read(Types.BYTE);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.REMOVE_MOB_EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.map(Types.BYTE);
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).registerClientbound(ClientboundPackets1_8.UPDATE_ATTRIBUTES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT, Types.INT);
                this.handler(wrapper -> {
                    EntityTracker1_8 tracker = (EntityTracker1_8)wrapper.user().getEntityTracker(Protocol1_8To1_7_6_10.class);
                    if (tracker.getHolograms().containsKey(wrapper.get(Types.INT, 0))) {
                        wrapper.cancel();
                        return;
                    }
                    int amount = wrapper.passthrough(Types.INT);
                    for (int i = 0; i < amount; ++i) {
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.DOUBLE);
                        int modifierLength = wrapper.read(Types.VAR_INT);
                        wrapper.write(Types.SHORT, (short)modifierLength);
                        for (int j = 0; j < modifierLength; ++j) {
                            wrapper.passthrough(Types.UUID);
                            wrapper.passthrough(Types.DOUBLE);
                            wrapper.passthrough(Types.BYTE);
                        }
                    }
                });
            }
        });
        ((Protocol1_8To1_7_6_10)this.protocol).cancelClientbound(ClientboundPackets1_8.UPDATE_ENTITY_NBT);
    }

    @Override
    protected void registerRewrites() {
        this.mapEntityTypeWithData(EntityTypes1_8.EntityType.GUARDIAN, EntityTypes1_8.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(EntityTypes1_8.EntityType.ENDERMITE, EntityTypes1_8.EntityType.SQUID).plainName();
        this.mapEntityTypeWithData(EntityTypes1_8.EntityType.RABBIT, EntityTypes1_8.EntityType.CHICKEN).plainName();
        this.filter().handler(this::handleEntityData);
    }

    public void handleEntityData(EntityDataHandlerEvent event, EntityData entityData) {
        EntityDataIndex1_7_6_10 metaIndex = EntityDataIndex1_7_6_10.searchIndex(event.entityType(), entityData.id());
        if (metaIndex == null) {
            event.cancel();
            return;
        }
        if (metaIndex.getOldType() == null) {
            event.cancel();
            return;
        }
        Object value = entityData.getValue();
        entityData.setTypeAndValue(metaIndex.getNewType(), value);
        entityData.setDataTypeUnsafe(metaIndex.getOldType());
        entityData.setId(metaIndex.getIndex());
        switch (metaIndex.getOldType()) {
            case INT: {
                if (metaIndex.getNewType() == EntityDataTypes1_8.BYTE) {
                    entityData.setValue(((Byte)value).intValue());
                    if (metaIndex == EntityDataIndex1_7_6_10.ABSTRACT_AGEABLE_AGE && (Integer)entityData.getValue() < 0) {
                        entityData.setValue(-25000);
                    }
                }
                if (metaIndex.getNewType() == EntityDataTypes1_8.SHORT) {
                    entityData.setValue(((Short)value).intValue());
                }
                if (metaIndex.getNewType() != EntityDataTypes1_8.INT) break;
                entityData.setValue(value);
                break;
            }
            case BYTE: {
                if (metaIndex.getNewType() == EntityDataTypes1_8.INT) {
                    entityData.setValue(((Integer)value).byteValue());
                }
                if (metaIndex.getNewType() == EntityDataTypes1_8.BYTE) {
                    if (metaIndex == EntityDataIndex1_7_6_10.ITEM_FRAME_ROTATION) {
                        entityData.setValue(Integer.valueOf((Byte)value % 4).byteValue());
                    } else {
                        entityData.setValue(value);
                    }
                }
                if (metaIndex != EntityDataIndex1_7_6_10.PLAYER_SKIN_FLAGS) break;
                byte flags = (Byte)value;
                boolean cape = (flags & 1) != 0;
                flags = (byte)(cape ? 0 : 2);
                entityData.setValue(flags);
                break;
            }
            case ITEM: {
                entityData.setValue(((Protocol1_8To1_7_6_10)this.protocol).getItemRewriter().handleItemToClient(event.user(), (Item)value));
                break;
            }
            case FLOAT: 
            case STRING: 
            case SHORT: 
            case POSITION: {
                entityData.setValue(value);
                break;
            }
            default: {
                event.cancel();
            }
        }
    }

    @Override
    public EntityTypes1_8.EntityType typeFromId(int type) {
        return EntityTypes1_8.getTypeFromId(type, false);
    }

    @Override
    public EntityTypes1_8.EntityType objectTypeFromId(int type) {
        return EntityTypes1_8.getTypeFromId(type, true);
    }
}

