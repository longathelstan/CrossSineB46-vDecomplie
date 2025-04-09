/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.Int2IntMapMappings;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.DimensionDataImpl;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataFilter;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEvent;
import com.viaversion.viaversion.rewriter.entitydata.EntityDataHandlerEventImpl;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.TagUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class EntityRewriter<C extends ClientboundPacketType, T extends Protocol<C, ?, ?, ?>>
extends RewriterBase<T>
implements com.viaversion.viaversion.api.rewriter.EntityRewriter<T> {
    static final EntityData[] EMPTY_ARRAY = new EntityData[0];
    protected final List<EntityDataFilter> entityDataFilters = new ArrayList<EntityDataFilter>();
    protected final boolean trackMappedType;
    protected Mappings typeMappings;

    protected EntityRewriter(T protocol) {
        this(protocol, true);
    }

    protected EntityRewriter(T protocol, boolean trackMappedType) {
        super(protocol);
        this.trackMappedType = trackMappedType;
        protocol.put(this);
    }

    public EntityDataFilter.Builder filter() {
        return new EntityDataFilter.Builder(this);
    }

    public void registerFilter(EntityDataFilter filter) {
        Preconditions.checkArgument((!this.entityDataFilters.contains(filter) ? 1 : 0) != 0);
        this.entityDataFilters.add(filter);
    }

    @Override
    public void handleEntityData(int entityId, List<EntityData> dataList, UserConnection connection) {
        TrackedEntity entity = this.tracker(connection).entity(entityId);
        EntityType type = entity != null ? entity.entityType() : null;
        int size = dataList.size();
        for (int i = 0; i < size; ++i) {
            EntityData entityData = dataList.get(i);
            EntityDataHandlerEvent event = null;
            for (EntityDataFilter filter : this.entityDataFilters) {
                if (!filter.isFiltered(type, entityData)) continue;
                if (event == null) {
                    event = new EntityDataHandlerEventImpl(connection, entity, entityId, entityData, dataList);
                }
                try {
                    filter.handler().handle(event, entityData);
                }
                catch (Exception e) {
                    this.logException(e, type, dataList, entityData);
                    dataList.remove(i--);
                    --size;
                    break;
                }
                if (!event.cancelled()) continue;
                dataList.remove(i--);
                --size;
                break;
            }
            if (event == null || !event.hasExtraData()) continue;
            dataList.addAll(event.extraData());
        }
        if (entity != null) {
            entity.sentEntityData(true);
        }
    }

    @Override
    public int newEntityId(int id) {
        return this.typeMappings != null ? this.typeMappings.getNewIdOrDefault(id, id) : id;
    }

    public void mapEntityType(EntityType type, EntityType mappedType) {
        Preconditions.checkArgument((type.getClass() != mappedType.getClass() ? 1 : 0) != 0, (Object)"EntityTypes should not be of the same class/enum");
        this.mapEntityType(type.getId(), mappedType.getId());
    }

    protected void mapEntityType(int id, int mappedId) {
        if (this.typeMappings == null) {
            this.typeMappings = Int2IntMapMappings.of();
        }
        this.typeMappings.setNewId(id, mappedId);
    }

    public void mapTypes() {
        Preconditions.checkArgument((this.typeMappings == null ? 1 : 0) != 0, (Object)"Type mappings have already been set - manual type mappings should be set *after* this");
        Preconditions.checkNotNull((Object)this.protocol.getMappingData().getEntityMappings(), (Object)"Protocol does not have entity mappings");
        this.typeMappings = this.protocol.getMappingData().getEntityMappings();
    }

    public void registerEntityDataTypeHandler(@Nullable EntityDataType itemType, @Nullable EntityDataType blockStateType, @Nullable EntityDataType particleType) {
        this.registerEntityDataTypeHandler(itemType, null, blockStateType, particleType, null);
    }

    public void registerEntityDataTypeHandler(@Nullable EntityDataType itemType, @Nullable EntityDataType blockStateType, @Nullable EntityDataType optionalBlockStateType, @Nullable EntityDataType particleType, @Nullable EntityDataType particlesType) {
        this.registerEntityDataTypeHandler(itemType, blockStateType, optionalBlockStateType, particleType, particlesType, null, null);
    }

    public void registerEntityDataTypeHandler(@Nullable EntityDataType itemType, @Nullable EntityDataType blockStateType, @Nullable EntityDataType optionalBlockStateType, @Nullable EntityDataType particleType, @Nullable EntityDataType particlesType, @Nullable EntityDataType componentType, @Nullable EntityDataType optionalComponentType) {
        this.filter().handler((event, data) -> {
            EntityDataType type = data.dataType();
            if (type == itemType) {
                data.setValue(this.protocol.getItemRewriter().handleItemToClient(event.user(), (Item)data.value()));
            } else if (type == blockStateType) {
                int value = (Integer)data.value();
                data.setValue(this.protocol.getMappingData().getNewBlockStateId(value));
            } else if (type == optionalBlockStateType) {
                int value = (Integer)data.value();
                if (value != 0) {
                    data.setValue(this.protocol.getMappingData().getNewBlockStateId(value));
                }
            } else if (type == particleType) {
                this.rewriteParticle(event.user(), (Particle)data.value());
            } else if (type == particlesType) {
                Particle[] particles;
                for (Particle particle : particles = (Particle[])data.value()) {
                    this.rewriteParticle(event.user(), particle);
                }
            } else if (type == componentType || type == optionalComponentType) {
                Tag component = (Tag)data.value();
                this.protocol.getComponentRewriter().processTag(event.user(), component);
            }
        });
    }

    public void registerBlockStateHandler(EntityType entityType, int index2) {
        this.filter().type(entityType).index(index2).handler((event, data) -> {
            int state = (Integer)data.getValue();
            data.setValue(this.protocol.getMappingData().getNewBlockStateId(state));
        });
    }

    public void registerTracker(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.map(Types.VAR_INT);
                this.handler(EntityRewriter.this.trackerHandler());
            }
        });
    }

    public void registerTrackerWithData(C packetType, final EntityType fallingBlockType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

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
                this.handler(EntityRewriter.this.trackerHandler());
                this.handler(wrapper -> {
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    EntityType entityType = EntityRewriter.this.tracker(wrapper.user()).entityType(entityId);
                    if (entityType == fallingBlockType) {
                        wrapper.set(Types.INT, 0, EntityRewriter.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Types.INT, 0)));
                    }
                });
            }
        });
    }

    public void registerTrackerWithData1_19(C packetType, final EntityType fallingBlockType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

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
                this.map(Types.VAR_INT);
                this.handler(EntityRewriter.this.trackerHandler());
                this.handler(wrapper -> {
                    if (EntityRewriter.this.protocol.getMappingData() == null) {
                        return;
                    }
                    int entityId = wrapper.get(Types.VAR_INT, 0);
                    EntityType entityType = EntityRewriter.this.tracker(wrapper.user()).entityType(entityId);
                    if (entityType == fallingBlockType) {
                        wrapper.set(Types.VAR_INT, 2, EntityRewriter.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Types.VAR_INT, 2)));
                    }
                });
            }
        });
    }

    public void registerTracker(C packetType, EntityType entityType, Type<Integer> intType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int entityId = (Integer)wrapper.passthrough(intType);
            this.tracker(wrapper.user()).addEntity(entityId, entityType);
        });
    }

    public void registerTracker(C packetType, EntityType entityType) {
        this.registerTracker(packetType, entityType, Types.VAR_INT);
    }

    public void registerRemoveEntities(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int[] entityIds = wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
            Object entityTracker = this.tracker(wrapper.user());
            for (int entity : entityIds) {
                entityTracker.removeEntity(entity);
            }
        });
    }

    public void registerSetEntityData(C packetType, @Nullable Type<List<EntityData>> dataType, Type<List<EntityData>> mappedDataType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            List entityData;
            int entityId = wrapper.passthrough(Types.VAR_INT);
            if (dataType != null) {
                entityData = (List)wrapper.read(dataType);
                wrapper.write(mappedDataType, entityData);
            } else {
                entityData = (List)wrapper.passthrough(mappedDataType);
            }
            this.handleEntityData(entityId, entityData, wrapper.user());
        });
    }

    public void registerSetEntityData(C packetType, Type<List<EntityData>> dataType) {
        this.registerSetEntityData(packetType, null, dataType);
    }

    public void clearEntities(UserConnection connection) {
        Object tracker = this.tracker(connection);
        tracker.clearEntities();
    }

    public PacketHandler trackerHandler() {
        return this.trackerAndRewriterHandler(null);
    }

    public PacketHandler playerTrackerHandler() {
        return wrapper -> {
            Object tracker = this.tracker(wrapper.user());
            int entityId = wrapper.get(Types.INT, 0);
            tracker.setClientEntityId(entityId);
            tracker.addEntity(entityId, tracker.playerType());
        };
    }

    public PacketHandler worldDataTrackerHandler(int nbtIndex) {
        return wrapper -> {
            Object tracker = this.tracker(wrapper.user());
            CompoundTag registryData = wrapper.get(Types.NAMED_COMPOUND_TAG, nbtIndex);
            NumberTag height = registryData.getNumberTag("height");
            if (height != null) {
                int blockHeight = height.asInt();
                tracker.setCurrentWorldSectionHeight(blockHeight >> 4);
            } else {
                CompoundTag compoundTag = registryData;
                this.protocol.getLogger().warning("Height missing in dimension data: " + compoundTag);
            }
            NumberTag minY = registryData.getNumberTag("min_y");
            if (minY != null) {
                tracker.setCurrentMinY(minY.asInt());
            } else {
                CompoundTag compoundTag = registryData;
                this.protocol.getLogger().warning("Min Y missing in dimension data: " + compoundTag);
            }
            String world = wrapper.get(Types.STRING, 0);
            if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
                tracker.clearEntities();
            }
            tracker.setCurrentWorld(world);
        };
    }

    public PacketHandler worldDataTrackerHandlerByKey() {
        return wrapper -> {
            String dimensionKey;
            Object tracker = this.tracker(wrapper.user());
            DimensionData dimensionData = tracker.dimensionData(dimensionKey = wrapper.get(Types.STRING, 0));
            if (dimensionData == null) {
                String string = dimensionKey;
                this.protocol.getLogger().severe("Dimension data missing for dimension: " + string + ", falling back to overworld");
                dimensionData = tracker.dimensionData("minecraft:overworld");
                Preconditions.checkNotNull((Object)dimensionData, (Object)"Overworld data missing");
            }
            tracker.setCurrentWorldSectionHeight(dimensionData.height() >> 4);
            tracker.setCurrentMinY(dimensionData.minY());
            String world = wrapper.get(Types.STRING, 1);
            if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
                tracker.clearEntities();
            }
            tracker.setCurrentWorld(world);
        };
    }

    public PacketHandler worldDataTrackerHandlerByKey1_20_5(int dimensionIdIndex) {
        return wrapper -> {
            int dimensionId = wrapper.get(Types.VAR_INT, dimensionIdIndex);
            String world = wrapper.get(Types.STRING, 0);
            this.trackWorldDataByKey1_20_5(wrapper.user(), dimensionId, world);
        };
    }

    public void trackWorldDataByKey1_20_5(UserConnection connection, int dimensionId, String world) {
        Object tracker = this.tracker(connection);
        DimensionData dimensionData = tracker.dimensionData(dimensionId);
        if (dimensionData == null) {
            int n = dimensionId;
            this.protocol.getLogger().severe("Dimension data missing for dimension: " + n + ", falling back to overworld");
            dimensionData = tracker.dimensionData("overworld");
            Preconditions.checkNotNull((Object)dimensionData, (Object)"Overworld data missing");
        }
        tracker.setCurrentWorldSectionHeight(dimensionData.height() >> 4);
        tracker.setCurrentMinY(dimensionData.minY());
        if (tracker.currentWorld() != null && !tracker.currentWorld().equals(world)) {
            tracker.clearEntities();
        }
        tracker.setCurrentWorld(world);
    }

    public PacketHandler biomeSizeTracker() {
        return wrapper -> this.trackBiomeSize(wrapper.user(), wrapper.get(Types.NAMED_COMPOUND_TAG, 0));
    }

    public PacketHandler configurationBiomeSizeTracker() {
        return wrapper -> this.trackBiomeSize(wrapper.user(), wrapper.get(Types.COMPOUND_TAG, 0));
    }

    public void trackBiomeSize(UserConnection connection, CompoundTag registry) {
        ListTag<CompoundTag> biomes = TagUtil.getRegistryEntries(registry, "worldgen/biome");
        this.tracker(connection).setBiomesSent(biomes.size());
    }

    public PacketHandler dimensionDataHandler() {
        return wrapper -> this.cacheDimensionData(wrapper.user(), wrapper.get(Types.NAMED_COMPOUND_TAG, 0));
    }

    public PacketHandler configurationDimensionDataHandler() {
        return wrapper -> this.cacheDimensionData(wrapper.user(), wrapper.get(Types.COMPOUND_TAG, 0));
    }

    public void cacheDimensionData(UserConnection connection, CompoundTag registry) {
        ListTag<CompoundTag> dimensions = TagUtil.getRegistryEntries(registry, "dimension_type");
        HashMap<String, DimensionData> dimensionDataMap = new HashMap<String, DimensionData>(dimensions.size());
        for (CompoundTag dimension : dimensions) {
            NumberTag idTag = dimension.getNumberTag("id");
            CompoundTag element = dimension.getCompoundTag("element");
            String name = dimension.getStringTag("name").getValue();
            dimensionDataMap.put(Key.stripMinecraftNamespace(name), new DimensionDataImpl(idTag.asInt(), element));
        }
        this.tracker(connection).setDimensions(dimensionDataMap);
    }

    public void handleRegistryData1_20_5(UserConnection connection, String registryKey, RegistryEntry[] entries) {
        if (registryKey.equals("worldgen/biome")) {
            this.tracker(connection).setBiomesSent(entries.length);
        } else if (registryKey.equals("dimension_type")) {
            HashMap<String, DimensionData> dimensionDataMap = new HashMap<String, DimensionData>(entries.length);
            for (int i = 0; i < entries.length; ++i) {
                RegistryEntry entry = entries[i];
                String key = Key.stripMinecraftNamespace(entry.key());
                DimensionData dimensionData = entry.tag() != null ? new DimensionDataImpl(i, (CompoundTag)entry.tag()) : DimensionDataImpl.withDefaultsFor(key, i);
                dimensionDataMap.put(key, dimensionData);
            }
            this.tracker(connection).setDimensions(dimensionDataMap);
        }
    }

    public PacketHandler trackerAndRewriterHandler(@Nullable Type<List<EntityData>> dataType) {
        return wrapper -> {
            int entityId = wrapper.get(Types.VAR_INT, 0);
            int type = wrapper.get(Types.VAR_INT, 1);
            int newType = this.newEntityId(type);
            if (newType != type) {
                wrapper.set(Types.VAR_INT, 1, newType);
            }
            EntityType entType = this.typeFromId(this.trackMappedType ? newType : type);
            this.tracker(wrapper.user()).addEntity(entityId, entType);
            if (dataType != null) {
                this.handleEntityData(entityId, (List)wrapper.get(dataType, 0), wrapper.user());
            }
        };
    }

    public PacketHandler trackerAndRewriterHandler(@Nullable Type<List<EntityData>> dataType, EntityType entityType) {
        return wrapper -> {
            int entityId = wrapper.get(Types.VAR_INT, 0);
            this.tracker(wrapper.user()).addEntity(entityId, entityType);
            if (dataType != null) {
                this.handleEntityData(entityId, (List)wrapper.get(dataType, 0), wrapper.user());
            }
        };
    }

    public PacketHandler objectTrackerHandler() {
        return wrapper -> {
            int entityId = wrapper.get(Types.VAR_INT, 0);
            byte type = wrapper.get(Types.BYTE, 0);
            EntityType entType = this.objectTypeFromId(type);
            this.tracker(wrapper.user()).addEntity(entityId, entType);
        };
    }

    public RegistryEntry[] addRegistryEntries(RegistryEntry[] entries, RegistryEntry ... toAdd) {
        int length = entries.length;
        RegistryEntry[] newEntries = Arrays.copyOf(entries, length + toAdd.length);
        System.arraycopy(toAdd, 0, newEntries, length, toAdd.length);
        return newEntries;
    }

    public void rewriteParticle(UserConnection connection, Particle particle) {
        int id;
        ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
        if (mappings.isBlockParticle(id = particle.id())) {
            Particle.ParticleData<Integer> data = particle.getArgument(0);
            data.setValue(this.protocol.getMappingData().getNewBlockStateId((Integer)data.getValue()));
        } else if (mappings.isItemParticle(id) && this.protocol.getItemRewriter() != null) {
            Particle.ParticleData<Item> data = particle.getArgument(0);
            ItemRewriter<?> itemRewriter = this.protocol.getItemRewriter();
            Item item = itemRewriter.handleItemToClient(connection, (Item)data.getValue());
            if (itemRewriter.mappedItemType() != null && itemRewriter.itemType() != itemRewriter.mappedItemType()) {
                particle.set(0, itemRewriter.mappedItemType(), item);
            } else {
                data.setValue(item);
            }
        }
        particle.setId(this.protocol.getMappingData().getNewParticleId(id));
    }

    void logException(Exception e, @Nullable EntityType type, List<EntityData> entityDataList, EntityData entityData) {
        if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
            EntityData entityData2 = entityData;
            String string = type != null ? type.name() : "untracked";
            String string2 = this.getClass().getSimpleName();
            this.protocol.getLogger().severe("An error occurred in entity data handler " + string2 + " for " + string + " entity type: " + entityData2);
            this.protocol.getLogger().severe(entityDataList.stream().sorted(Comparator.comparingInt(EntityData::id)).map(EntityData::toString).collect(Collectors.joining("\n", "Full entity data: ", "")));
            this.protocol.getLogger().log(Level.SEVERE, "Error: ", e);
        }
    }
}

