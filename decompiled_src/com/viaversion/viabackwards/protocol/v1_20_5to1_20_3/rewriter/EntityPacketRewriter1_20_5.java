/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.Protocol1_20_5To1_20_3;
import com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.storage.RegistryDataStorage;
import com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.storage.SecureChatStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.data.entity.DimensionDataImpl;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.Protocol1_20_3To1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Attributes1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.ArmorTrimStorage;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.BannerPatternStorage;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.KeyMappings;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.HashMap;

public final class EntityPacketRewriter1_20_5
extends EntityRewriter<ClientboundPacket1_20_5, Protocol1_20_5To1_20_3> {
    public EntityPacketRewriter1_20_5(Protocol1_20_5To1_20_3 protocol) {
        super(protocol, Types1_20_3.ENTITY_DATA_TYPES.optionalComponentType, Types1_20_3.ENTITY_DATA_TYPES.booleanType);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_20_5.ADD_ENTITY, EntityTypes1_20_5.FALLING_BLOCK);
        this.registerSetEntityData(ClientboundPackets1_20_5.SET_ENTITY_DATA, Types1_20_5.ENTITY_DATA_LIST, Types1_20_3.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_20_5.REMOVE_ENTITIES);
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.SET_EQUIPMENT, wrapper -> {
            byte slot;
            int entityId = wrapper.passthrough(Types.VAR_INT);
            EntityType type = this.tracker(wrapper.user()).entityType(entityId);
            do {
                slot = wrapper.read(Types.BYTE);
                Item item = ((Protocol1_20_5To1_20_3)this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), wrapper.read(Types1_20_5.ITEM));
                int rawSlot = slot & 0x7F;
                if (rawSlot == 6) {
                    boolean lastSlot = (slot & 0xFFFFFF80) == 0;
                    slot = (byte)(lastSlot ? 4 : -124);
                    if (type != null && type.isOrHasParent(EntityTypes1_20_5.LLAMA)) {
                        wrapper.cancel();
                        this.sendCarpetColorUpdate(wrapper.user(), entityId, item);
                    }
                }
                wrapper.write(Types.BYTE, slot);
                wrapper.write(Types.ITEM1_20_2, item);
            } while ((slot & 0xFFFFFF80) != 0);
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.HORSE_SCREEN_OPEN, wrapper -> {
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            int size = wrapper.read(Types.VAR_INT);
            wrapper.write(Types.VAR_INT, size + 1);
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundConfigurationPackets1_20_5.REGISTRY_DATA, wrapper -> {
            int i;
            RegistryEntry entry2;
            wrapper.cancel();
            String registryKey = Key.stripMinecraftNamespace(wrapper.read(Types.STRING));
            if (registryKey.equals("wolf_variant")) {
                return;
            }
            RegistryDataStorage registryDataStorage = wrapper.user().get(RegistryDataStorage.class);
            RegistryEntry[] entries = wrapper.read(Types.REGISTRY_ENTRY_ARRAY);
            if (registryKey.equals("banner_pattern")) {
                wrapper.user().get(BannerPatternStorage.class).setBannerPatterns(this.toMappings(entries));
                return;
            }
            boolean isTrimPattern = registryKey.equals("trim_pattern");
            if (isTrimPattern) {
                wrapper.user().get(ArmorTrimStorage.class).setTrimPatterns(this.toMappings(entries));
            } else if (registryKey.equals("trim_material")) {
                wrapper.user().get(ArmorTrimStorage.class).setTrimMaterials(this.toMappings(entries));
            }
            if (registryKey.equals("worldgen/biome")) {
                this.tracker(wrapper.user()).setBiomesSent(entries.length);
                for (RegistryEntry entry2 : entries) {
                    CompoundTag effects;
                    CompoundTag particle;
                    if (entry2.tag() == null || (particle = (effects = ((CompoundTag)entry2.tag()).getCompoundTag("effects")).getCompoundTag("particle")) == null) continue;
                    CompoundTag particleOptions = particle.getCompoundTag("options");
                    String particleType = particleOptions.getString("type");
                    this.updateParticleFormat(particleOptions, Key.stripMinecraftNamespace(particleType));
                }
            } else if (registryKey.equals("dimension_type")) {
                HashMap<String, DimensionData> dimensionDataMap = new HashMap<String, DimensionData>(entries.length);
                String[] keys2 = new String[entries.length];
                for (i = 0; i < entries.length; ++i) {
                    entry2 = entries[i];
                    String string = entry2.key();
                    Preconditions.checkNotNull((Object)entry2.tag(), (Object)("Server unexpectedly sent null dimension data for " + string));
                    String dimensionKey = Key.stripMinecraftNamespace(entry2.key());
                    CompoundTag tag = (CompoundTag)entry2.tag();
                    this.updateDimensionTypeData(tag);
                    dimensionDataMap.put(dimensionKey, new DimensionDataImpl(i, tag));
                    keys2[i] = dimensionKey;
                }
                registryDataStorage.setDimensionKeys(keys2);
                this.tracker(wrapper.user()).setDimensions(dimensionDataMap);
            }
            CompoundTag registryTag = new CompoundTag();
            ListTag<CompoundTag> entriesTag = new ListTag<CompoundTag>(CompoundTag.class);
            registryTag.putString("type", registryKey);
            registryTag.put("value", entriesTag);
            for (i = 0; i < entries.length; ++i) {
                entry2 = entries[i];
                String string = entry2.key();
                Preconditions.checkNotNull((Object)entry2.tag(), (Object)("Server unexpectedly sent null registry data entry for " + string));
                if (isTrimPattern) {
                    CompoundTag patternTag = (CompoundTag)entry2.tag();
                    StringTag templateItem = patternTag.getStringTag("template_item");
                    if (Protocol1_20_3To1_20_5.MAPPINGS.getFullItemMappings().id(templateItem.getValue()) == -1) continue;
                }
                CompoundTag entryCompoundTag = new CompoundTag();
                entryCompoundTag.putString("name", entry2.key());
                entryCompoundTag.putInt("id", i);
                entryCompoundTag.put("element", entry2.tag());
                entriesTag.add(entryCompoundTag);
            }
            registryDataStorage.registryData().put(registryKey, registryTag);
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.STRING_ARRAY);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    int dimensionId = wrapper.read(Types.VAR_INT);
                    RegistryDataStorage storage = wrapper.user().get(RegistryDataStorage.class);
                    wrapper.write(Types.STRING, storage.dimensionKeys()[dimensionId]);
                });
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.OPTIONAL_GLOBAL_POSITION);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    boolean enforcesSecureChat = wrapper.read(Types.BOOLEAN);
                    wrapper.user().get(SecureChatStorage.class).setEnforcesSecureChat(enforcesSecureChat);
                });
                this.handler(EntityPacketRewriter1_20_5.this.worldDataTrackerHandlerByKey());
                this.handler(EntityPacketRewriter1_20_5.this.playerTrackerHandler());
            }
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    int dimensionId = wrapper.read(Types.VAR_INT);
                    RegistryDataStorage storage = wrapper.user().get(RegistryDataStorage.class);
                    wrapper.write(Types.STRING, storage.dimensionKeys()[dimensionId]);
                });
                this.map(Types.STRING);
                this.handler(EntityPacketRewriter1_20_5.this.worldDataTrackerHandlerByKey());
            }
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.UPDATE_MOB_EFFECT, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            int amplifier = wrapper.read(Types.VAR_INT);
            wrapper.write(Types.BYTE, (byte)MathUtil.clamp(amplifier, -128, 127));
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BYTE);
            wrapper.write(Types.OPTIONAL_COMPOUND_TAG, null);
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.UPDATE_ATTRIBUTES, wrapper -> {
            int size;
            int entityId = wrapper.passthrough(Types.VAR_INT);
            int newSize = size = wrapper.passthrough(Types.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                int j;
                int modifierSize;
                EntityType type;
                int attributeId = wrapper.read(Types.VAR_INT);
                String attribute = Attributes1_20_5.idToKey(attributeId);
                int mappedId = ((Protocol1_20_5To1_20_3)this.protocol).getMappingData().getAttributeMappings().getNewId(attributeId);
                if ("generic.jump_strength".equals(attribute) && ((type = this.tracker(wrapper.user()).entityType(entityId)) == null || !type.isOrHasParent(EntityTypes1_20_5.HORSE))) {
                    mappedId = -1;
                }
                if (mappedId == -1) {
                    --newSize;
                    wrapper.read(Types.DOUBLE);
                    modifierSize = wrapper.read(Types.VAR_INT);
                    for (j = 0; j < modifierSize; ++j) {
                        wrapper.read(Types.UUID);
                        wrapper.read(Types.DOUBLE);
                        wrapper.read(Types.BYTE);
                    }
                    continue;
                }
                wrapper.write(Types.STRING, attribute);
                wrapper.passthrough(Types.DOUBLE);
                modifierSize = wrapper.passthrough(Types.VAR_INT);
                for (j = 0; j < modifierSize; ++j) {
                    wrapper.passthrough(Types.UUID);
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.passthrough(Types.BYTE);
                }
            }
            wrapper.set(Types.VAR_INT, 1, newSize);
        });
    }

    KeyMappings toMappings(RegistryEntry[] entries) {
        String[] keys2 = new String[entries.length];
        for (int i = 0; i < entries.length; ++i) {
            keys2[i] = Key.stripMinecraftNamespace(entries[i].key());
        }
        return new KeyMappings(keys2);
    }

    void updateParticleFormat(CompoundTag options, String particleType) {
        if ("block".equals(particleType) || "block_marker".equals(particleType) || "falling_dust".equals(particleType) || "dust_pillar".equals(particleType)) {
            Tag blockState = options.remove("block_state");
            if (blockState instanceof StringTag) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("Name", blockState);
                blockState = compoundTag;
            }
            options.put("value", blockState);
        } else if ("item".equals(particleType)) {
            Tag item = options.remove("item");
            if (item instanceof StringTag) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("id", item);
                item = compoundTag;
            }
            options.put("value", item);
        } else if ("dust_color_transition".equals(particleType)) {
            this.moveTag(options, "from_color", "fromColor");
            this.moveTag(options, "to_color", "toColor");
        } else if ("entity_effect".equals(particleType)) {
            Tag color = options.remove("color");
            if (color instanceof ListTag) {
                ListTag colorParts = (ListTag)color;
                color = new FloatTag(this.encodeARGB(((NumberTag)colorParts.get(0)).getValue().floatValue(), ((NumberTag)colorParts.get(1)).getValue().floatValue(), ((NumberTag)colorParts.get(2)).getValue().floatValue(), ((NumberTag)colorParts.get(3)).getValue().floatValue()));
            }
            options.put("value", color);
        }
    }

    int encodeARGB(float a, float r, float g, float b) {
        int encodedAlpha = this.encodeColorPart(a);
        int encodedRed = this.encodeColorPart(r);
        int encodedGreen = this.encodeColorPart(g);
        int encodedBlue = this.encodeColorPart(b);
        return encodedAlpha << 24 | encodedRed << 16 | encodedGreen << 8 | encodedBlue;
    }

    int encodeColorPart(float part) {
        return (int)Math.floor(part * 255.0f);
    }

    int removeAlpha(int argb) {
        return argb & 0xFFFFFF;
    }

    void moveTag(CompoundTag compoundTag, String from, String to) {
        Tag tag = compoundTag.remove(from);
        if (tag != null) {
            compoundTag.put(to, tag);
        }
    }

    void updateDimensionTypeData(CompoundTag elementTag) {
        CompoundTag monsterSpawnLightLevel = elementTag.getCompoundTag("monster_spawn_light_level");
        if (monsterSpawnLightLevel != null) {
            CompoundTag value = new CompoundTag();
            monsterSpawnLightLevel.put("value", value);
            value.putInt("min_inclusive", monsterSpawnLightLevel.getInt("min_inclusive"));
            value.putInt("max_inclusive", monsterSpawnLightLevel.getInt("max_inclusive"));
        }
    }

    void sendCarpetColorUpdate(UserConnection connection, int entityId, Item item) {
        PacketWrapper setEntityData = PacketWrapper.create(ClientboundPackets1_20_3.SET_ENTITY_DATA, connection);
        setEntityData.write(Types.VAR_INT, entityId);
        int color = -1;
        if (item != null && item.identifier() >= 445 && item.identifier() <= 460) {
            color = item.identifier() - 445;
        }
        ArrayList<EntityData> entityDataList = new ArrayList<EntityData>();
        entityDataList.add(new EntityData(20, Types1_20_3.ENTITY_DATA_TYPES.varIntType, color));
        setEntityData.write(Types1_20_3.ENTITY_DATA_LIST, entityDataList);
        setEntityData.send(Protocol1_20_5To1_20_3.class);
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, data) -> {
            int typeId = data.dataType().typeId();
            if (typeId == Types1_20_5.ENTITY_DATA_TYPES.particlesType.typeId()) {
                Particle[] particles = (Particle[])data.value();
                int color = 0;
                for (Particle particle : particles) {
                    if (particle.id() != ((Protocol1_20_5To1_20_3)this.protocol).getMappingData().getParticleMappings().id("entity_effect")) continue;
                    color = (Integer)particle.removeArgument(0).getValue();
                }
                data.setTypeAndValue(Types1_20_3.ENTITY_DATA_TYPES.varIntType, this.removeAlpha(color));
                return;
            }
            int id = typeId;
            if (typeId >= Types1_20_5.ENTITY_DATA_TYPES.armadilloState.typeId()) {
                --id;
            }
            if (typeId >= Types1_20_5.ENTITY_DATA_TYPES.wolfVariantType.typeId()) {
                --id;
            }
            if (typeId >= Types1_20_5.ENTITY_DATA_TYPES.particlesType.typeId()) {
                --id;
            }
            data.setDataType(Types1_20_3.ENTITY_DATA_TYPES.byId(id));
        });
        this.registerEntityDataTypeHandler1_20_3(Types1_20_3.ENTITY_DATA_TYPES.itemType, Types1_20_3.ENTITY_DATA_TYPES.blockStateType, Types1_20_3.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_20_3.ENTITY_DATA_TYPES.particleType, null, Types1_20_3.ENTITY_DATA_TYPES.componentType, Types1_20_3.ENTITY_DATA_TYPES.optionalComponentType);
        this.registerBlockStateHandler(EntityTypes1_20_5.ABSTRACT_MINECART, 11);
        this.filter().type(EntityTypes1_20_5.AREA_EFFECT_CLOUD).addIndex(9);
        this.filter().type(EntityTypes1_20_5.AREA_EFFECT_CLOUD).index(11).handler((event, data) -> {
            Particle particle = (Particle)data.value();
            if (particle.id() == ((Protocol1_20_5To1_20_3)this.protocol).getMappingData().getParticleMappings().mappedId("entity_effect")) {
                int color = (Integer)particle.removeArgument(0).getValue();
                event.createExtraData(new EntityData(9, Types1_20_3.ENTITY_DATA_TYPES.varIntType, this.removeAlpha(color)));
            }
        });
        this.filter().type(EntityTypes1_20_5.LLAMA).addIndex(20);
        this.filter().type(EntityTypes1_20_5.ARMADILLO).removeIndex(17);
        this.filter().type(EntityTypes1_20_5.WOLF).removeIndex(22);
        this.filter().type(EntityTypes1_20_5.OMINOUS_ITEM_SPAWNER).removeIndex(8);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(EntityTypes1_20_5.ARMADILLO, EntityTypes1_20_5.COW).tagName();
        this.mapEntityTypeWithData(EntityTypes1_20_5.BOGGED, EntityTypes1_20_5.STRAY).tagName();
        this.mapEntityTypeWithData(EntityTypes1_20_5.BREEZE_WIND_CHARGE, EntityTypes1_20_5.WIND_CHARGE);
        this.mapEntityTypeWithData(EntityTypes1_20_5.OMINOUS_ITEM_SPAWNER, EntityTypes1_20_5.TEXT_DISPLAY);
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_20_5.getTypeFromId(type);
    }
}

