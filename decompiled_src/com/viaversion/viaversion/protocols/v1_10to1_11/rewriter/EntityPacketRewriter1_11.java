/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_10to1_11.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.types.EntityDataTypes1_9;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.v1_10to1_11.Protocol1_10To1_11;
import com.viaversion.viaversion.protocols.v1_10to1_11.data.BlockEntityMappings1_11;
import com.viaversion.viaversion.protocols.v1_10to1_11.data.EntityMappings1_11;
import com.viaversion.viaversion.protocols.v1_10to1_11.storage.EntityTracker1_11;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class EntityPacketRewriter1_11
extends EntityRewriter<ClientboundPackets1_9_3, Protocol1_10To1_11> {
    public EntityPacketRewriter1_11(Protocol1_10To1_11 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_10To1_11.class);
                    int dimensionId = wrapper.get(Types.INT, 1);
                    ((ClientWorld)clientWorld).setEnvironment(dimensionId);
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int dimensionId;
                    Object clientWorld = wrapper.user().getClientWorld(Protocol1_10To1_11.class);
                    if (((ClientWorld)clientWorld).setEnvironment(dimensionId = wrapper.get(Types.INT, 0).intValue())) {
                        EntityPacketRewriter1_11.this.tracker(wrapper.user()).clearEntities();
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_ENTITY, new PacketHandlers(){

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
                this.handler(wrapper -> {
                    byte type = wrapper.get(Types.BYTE, 0);
                    if (type == EntityTypes1_10.ObjectType.FISHIHNG_HOOK.getId()) {
                        EntityPacketRewriter1_11.this.tryFixFishingHookVelocity(wrapper);
                    }
                });
                this.handler(EntityPacketRewriter1_11.this.objectTrackerHandler());
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.UNSIGNED_BYTE, Types.VAR_INT);
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
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    int type = wrapper.get(Types.VAR_INT, 1);
                    EntityTypes1_11.EntityType entType = EntityPacketRewriter1_11.this.rewriteEntityType(type, wrapper.get(Types1_9.ENTITY_DATA_LIST, 0));
                    if (entType != null) {
                        wrapper.set(Types.VAR_INT, 1, entType.getId());
                        wrapper.user().getEntityTracker(Protocol1_10To1_11.class).addEntity(entityId, entType);
                        EntityPacketRewriter1_11.this.handleEntityData(entityId, wrapper.get(Types1_9.ENTITY_DATA_LIST, 0), wrapper.user());
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.TAKE_ITEM_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> wrapper.write(Types.VAR_INT, 1));
            }
        });
        this.registerSetEntityData(ClientboundPackets1_9_3.SET_ENTITY_DATA, Types1_9.ENTITY_DATA_LIST);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.TELEPORT_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    EntityTracker1_11 tracker;
                    int entityID = wrapper.get(Types.VAR_INT, 0);
                    if (Via.getConfig().isHologramPatch() && (tracker = (EntityTracker1_11)wrapper.user().getEntityTracker(Protocol1_10To1_11.class)).isHologram(entityID)) {
                        Double newValue = wrapper.get(Types.DOUBLE, 1);
                        newValue = newValue - Via.getConfig().getHologramYOffset();
                        wrapper.set(Types.DOUBLE, 1, newValue);
                    }
                });
            }
        });
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.SET_ENTITY_MOTION, wrapper -> {
            int entityId = wrapper.passthrough(Types.VAR_INT);
            if (this.tracker(wrapper.user()).entityType(entityId) == EntityTypes1_10.EntityType.FISHING_HOOK) {
                this.tryFixFishingHookVelocity(wrapper);
            }
        });
        this.registerRemoveEntities(ClientboundPackets1_9_3.REMOVE_ENTITIES);
        ((Protocol1_10To1_11)this.protocol).registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    StringTag idTag;
                    CompoundTag tag = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    if (wrapper.get(Types.UNSIGNED_BYTE, 0) == 1) {
                        EntityMappings1_11.toClientSpawner(tag);
                    }
                    if ((idTag = tag.getStringTag("id")) != null) {
                        idTag.setValue(BlockEntityMappings1_11.toNewIdentifier(idTag.getValue()));
                    }
                });
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, data) -> {
            if (data.getValue() instanceof DataItem) {
                EntityMappings1_11.toClientItem((Item)data.value());
            }
        });
        this.filter().type(EntityTypes1_11.EntityType.GUARDIAN).index(12).handler((event, data) -> {
            boolean value = ((Byte)data.getValue() & 2) == 2;
            data.setTypeAndValue(EntityDataTypes1_9.BOOLEAN, value);
        });
        this.filter().type(EntityTypes1_11.EntityType.ABSTRACT_SKELETON).removeIndex(12);
        this.filter().type(EntityTypes1_11.EntityType.ZOMBIE).handler((event, data) -> {
            if ((event.entityType() == EntityTypes1_11.EntityType.ZOMBIE || event.entityType() == EntityTypes1_11.EntityType.HUSK) && data.id() == 14) {
                event.cancel();
            } else if (data.id() == 15) {
                data.setId(14);
            }
        });
        this.filter().type(EntityTypes1_11.EntityType.ABSTRACT_HORSE).handler((event, data) -> {
            EntityType type = event.entityType();
            int id = data.id();
            if (id == 14) {
                event.cancel();
                return;
            }
            if (id == 16) {
                data.setId(14);
            } else if (id == 17) {
                data.setId(16);
            }
            if (!type.is(EntityTypes1_11.EntityType.HORSE) && data.id() == 15 || data.id() == 16) {
                event.cancel();
                return;
            }
            if ((type == EntityTypes1_11.EntityType.DONKEY || type == EntityTypes1_11.EntityType.MULE) && data.id() == 13) {
                if (((Byte)data.getValue() & 8) == 8) {
                    event.createExtraData(new EntityData(15, EntityDataTypes1_9.BOOLEAN, true));
                } else {
                    event.createExtraData(new EntityData(15, EntityDataTypes1_9.BOOLEAN, false));
                }
            }
        });
        this.filter().type(EntityTypes1_11.EntityType.ARMOR_STAND).index(0).handler((event, data) -> {
            int entityId;
            EntityTracker1_11 tracker;
            if (!Via.getConfig().isHologramPatch()) {
                return;
            }
            EntityData flags = event.dataAtIndex(11);
            EntityData customName2 = event.dataAtIndex(2);
            EntityData customNameVisible = event.dataAtIndex(3);
            if (flags == null || customName2 == null || customNameVisible == null) {
                return;
            }
            byte value = (Byte)data.value();
            if ((value & 0x20) == 32 && ((Byte)flags.getValue() & 1) == 1 && !((String)customName2.getValue()).isEmpty() && ((Boolean)customNameVisible.getValue()).booleanValue() && (tracker = (EntityTracker1_11)this.tracker(event.user())).addHologram(entityId = event.entityId())) {
                PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9_3.MOVE_ENTITY_POS, null, event.user());
                wrapper.write(Types.VAR_INT, entityId);
                wrapper.write(Types.SHORT, (short)0);
                wrapper.write(Types.SHORT, (short)(128.0 * (-Via.getConfig().getHologramYOffset() * 32.0)));
                wrapper.write(Types.SHORT, (short)0);
                wrapper.write(Types.BOOLEAN, true);
                wrapper.send(Protocol1_10To1_11.class);
            }
        });
    }

    void tryFixFishingHookVelocity(PacketWrapper wrapper) {
        short x = wrapper.read(Types.SHORT);
        short y = wrapper.read(Types.SHORT);
        short z = wrapper.read(Types.SHORT);
        wrapper.write(Types.SHORT, (short)((double)x * 1.33));
        wrapper.write(Types.SHORT, (short)((double)y * 1.2));
        wrapper.write(Types.SHORT, (short)((double)z * 1.33));
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_11.getTypeFromId(type, false);
    }

    @Override
    public EntityType objectTypeFromId(int type) {
        return EntityTypes1_11.getTypeFromId(type, true);
    }

    public EntityTypes1_11.EntityType rewriteEntityType(int numType, List<EntityData> entityData) {
        EntityTypes1_11.EntityType type;
        block16: {
            type = EntityTypes1_11.EntityType.findById(numType);
            if (type == null) {
                List<EntityData> list = entityData;
                int n = numType;
                Via.getManager().getPlatform().getLogger().severe("Error: could not find Entity type " + n + " with entity data: " + list);
                return null;
            }
            try {
                Optional<EntityData> options;
                if (type.is(EntityTypes1_11.EntityType.GUARDIAN) && (options = this.getById(entityData, 12)).isPresent() && ((Byte)options.get().getValue() & 4) == 4) {
                    return EntityTypes1_11.EntityType.ELDER_GUARDIAN;
                }
                if (type.is(EntityTypes1_11.EntityType.SKELETON) && (options = this.getById(entityData, 12)).isPresent()) {
                    if ((Integer)options.get().getValue() == 1) {
                        return EntityTypes1_11.EntityType.WITHER_SKELETON;
                    }
                    if ((Integer)options.get().getValue() == 2) {
                        return EntityTypes1_11.EntityType.STRAY;
                    }
                }
                if (type.is(EntityTypes1_11.EntityType.ZOMBIE) && (options = this.getById(entityData, 13)).isPresent()) {
                    int value = (Integer)options.get().getValue();
                    if (value > 0 && value < 6) {
                        entityData.add(new EntityData(16, EntityDataTypes1_9.VAR_INT, value - 1));
                        return EntityTypes1_11.EntityType.ZOMBIE_VILLAGER;
                    }
                    if (value == 6) {
                        return EntityTypes1_11.EntityType.HUSK;
                    }
                }
                if (type.is(EntityTypes1_11.EntityType.HORSE) && (options = this.getById(entityData, 14)).isPresent()) {
                    if ((Integer)options.get().getValue() == 0) {
                        return EntityTypes1_11.EntityType.HORSE;
                    }
                    if ((Integer)options.get().getValue() == 1) {
                        return EntityTypes1_11.EntityType.DONKEY;
                    }
                    if ((Integer)options.get().getValue() == 2) {
                        return EntityTypes1_11.EntityType.MULE;
                    }
                    if ((Integer)options.get().getValue() == 3) {
                        return EntityTypes1_11.EntityType.ZOMBIE_HORSE;
                    }
                    if ((Integer)options.get().getValue() == 4) {
                        return EntityTypes1_11.EntityType.SKELETON_HORSE;
                    }
                }
            }
            catch (Exception e) {
                if (Via.getConfig().isSuppressMetadataErrors() && !Via.getManager().isDebug()) break block16;
                ((Protocol1_10To1_11)this.protocol).getLogger().warning("An error occurred with entity type rewriter");
                List<EntityData> list = entityData;
                ((Protocol1_10To1_11)this.protocol).getLogger().warning("Entity data: " + list);
                ((Protocol1_10To1_11)this.protocol).getLogger().log(Level.WARNING, "Error: ", e);
            }
        }
        return type;
    }

    public Optional<EntityData> getById(List<EntityData> entityData, int id) {
        for (EntityData data : entityData) {
            if (data.id() != id) continue;
            return Optional.of(data);
        }
        return Optional.empty();
    }
}

