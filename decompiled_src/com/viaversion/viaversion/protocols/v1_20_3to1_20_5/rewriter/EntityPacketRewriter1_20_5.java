/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.libs.fastutil.ints.IntArraySet;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundConfigurationPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.Protocol1_20_3To1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Attributes1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.BannerPatterns1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.AcknowledgedMessagesStorage;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.ArmorTrimStorage;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.KeyMappings;
import com.viaversion.viaversion.util.TagUtil;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class EntityPacketRewriter1_20_5
extends EntityRewriter<ClientboundPacket1_20_3, Protocol1_20_3To1_20_5> {
    static final UUID CREATIVE_BLOCK_INTERACTION_RANGE = UUID.fromString("736565d2-e1a7-403d-a3f8-1aeb3e302542");
    static final UUID CREATIVE_ENTITY_INTERACTION_RANGE = UUID.fromString("98491ef6-97b1-4584-ae82-71a8cc85cf73");
    static final int CREATIVE_MODE_ID = 1;

    public EntityPacketRewriter1_20_5(Protocol1_20_3To1_20_5 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_20_3.ADD_ENTITY, EntityTypes1_20_5.FALLING_BLOCK);
        this.registerSetEntityData(ClientboundPackets1_20_3.SET_ENTITY_DATA, Types1_20_3.ENTITY_DATA_LIST, Types1_20_5.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_20_3.REMOVE_ENTITIES);
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.SET_EQUIPMENT, wrapper -> {
            byte slot;
            int entityId = wrapper.passthrough(Types.VAR_INT);
            EntityType type = this.tracker(wrapper.user()).entityType(entityId);
            do {
                slot = wrapper.read(Types.BYTE);
                int rawSlot = slot & 0x7F;
                if (type != null && type.isOrHasParent(EntityTypes1_20_5.ABSTRACT_HORSE) && rawSlot == 4) {
                    boolean lastSlot = (slot & 0xFFFFFF80) == 0;
                    slot = (byte)(lastSlot ? 6 : -122);
                }
                wrapper.write(Types.BYTE, slot);
                Item item = ((Protocol1_20_3To1_20_5)this.protocol).getItemRewriter().handleItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_20_2));
                wrapper.write(Types1_20_5.ITEM, item);
            } while ((slot & 0xFFFFFF80) != 0);
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundConfigurationPackets1_20_3.REGISTRY_DATA, wrapper -> {
            PacketWrapper knownPacksPacket = wrapper.create(ClientboundConfigurationPackets1_20_5.SELECT_KNOWN_PACKS);
            knownPacksPacket.write(Types.VAR_INT, 0);
            knownPacksPacket.send(Protocol1_20_3To1_20_5.class);
            CompoundTag registryData = wrapper.read(Types.COMPOUND_TAG);
            this.cacheDimensionData(wrapper.user(), registryData);
            this.trackBiomeSize(wrapper.user(), registryData);
            ListTag<CompoundTag> dimensionTypes = TagUtil.getRegistryEntries(registryData, "dimension_type");
            for (CompoundTag compoundTag : dimensionTypes) {
                CompoundTag elementTag = compoundTag.getCompoundTag("element");
                CompoundTag monsterSpawnLightLevel = elementTag.getCompoundTag("monster_spawn_light_level");
                if (monsterSpawnLightLevel == null) continue;
                CompoundTag value = (CompoundTag)monsterSpawnLightLevel.removeUnchecked("value");
                monsterSpawnLightLevel.putInt("min_inclusive", value.getInt("min_inclusive"));
                monsterSpawnLightLevel.putInt("max_inclusive", value.getInt("max_inclusive"));
            }
            ListTag<CompoundTag> biomes = TagUtil.getRegistryEntries(registryData, "worldgen/biome");
            for (CompoundTag biome : biomes) {
                CompoundTag effects = biome.getCompoundTag("element").getCompoundTag("effects");
                this.checkSoundTag(effects.getCompoundTag("mood_sound"), "sound");
                this.checkSoundTag(effects.getCompoundTag("additions_sound"), "sound");
                this.checkSoundTag(effects.getCompoundTag("music"), "sound");
                this.checkSoundTag(effects, "ambient_sound");
                CompoundTag particle = effects.getCompoundTag("particle");
                if (particle == null) continue;
                CompoundTag particleOptions = particle.getCompoundTag("options");
                String particleType = particleOptions.getString("type");
                this.updateParticleFormat(particleOptions, Key.stripMinecraftNamespace(particleType));
            }
            for (Map.Entry<String, Tag> entry : registryData.entrySet()) {
                CompoundTag entryTag = (CompoundTag)entry.getValue();
                String type = entryTag.getString("type");
                ListTag<CompoundTag> valueTag = entryTag.getListTag("value", CompoundTag.class);
                RegistryEntry[] registryEntries = new RegistryEntry[valueTag.stream().map(e -> e.getInt("id")).distinct().toArray().length];
                boolean requiresDummyValues = false;
                int highestId = -1;
                IntArraySet ids = new IntArraySet();
                for (CompoundTag tag : valueTag) {
                    String name = tag.getString("name");
                    int id = tag.getInt("id");
                    if (ids.add(id)) {
                        highestId = Math.max(highestId, id);
                        if (id >= registryEntries.length) {
                            registryEntries = Arrays.copyOf(registryEntries, Math.max(registryEntries.length * 2, id + 1));
                            requiresDummyValues = true;
                        }
                    }
                    registryEntries[id] = new RegistryEntry(name, tag.get("element"));
                }
                String strippedKey = Key.stripMinecraftNamespace(type);
                if (strippedKey.equals("damage_type")) {
                    registryEntries = Arrays.copyOf(registryEntries, ++highestId + 1);
                    CompoundTag spitData = new CompoundTag();
                    spitData.putString("scaling", "when_caused_by_living_non_player");
                    spitData.putString("message_id", "mob");
                    spitData.putFloat("exhaustion", 0.1f);
                    registryEntries[highestId] = new RegistryEntry("minecraft:spit", spitData);
                    Set registryEntryKeys = Arrays.stream(registryEntries).map(e -> Key.stripMinecraftNamespace(e.key())).collect(Collectors.toSet());
                    for (String key : ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().damageKeys()) {
                        if (registryEntryKeys.contains(key)) continue;
                        registryEntries = Arrays.copyOf(registryEntries, ++highestId + 1);
                        registryEntries[highestId] = new RegistryEntry(Key.namespaced(key), ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().damageType(key));
                    }
                }
                if (requiresDummyValues) {
                    int finalLength = highestId + 1;
                    if (registryEntries.length != finalLength) {
                        registryEntries = Arrays.copyOf(registryEntries, finalLength);
                    }
                    this.replaceNullValues(registryEntries);
                }
                if (strippedKey.equals("trim_pattern")) {
                    wrapper.user().get(ArmorTrimStorage.class).setTrimPatterns(this.toMappings(registryEntries));
                } else if (strippedKey.equals("trim_material")) {
                    wrapper.user().get(ArmorTrimStorage.class).setTrimMaterials(this.toMappings(registryEntries));
                }
                PacketWrapper registryPacket = wrapper.create(ClientboundConfigurationPackets1_20_5.REGISTRY_DATA);
                registryPacket.write(Types.STRING, type);
                registryPacket.write(Types.REGISTRY_ENTRY_ARRAY, registryEntries);
                registryPacket.send(Protocol1_20_3To1_20_5.class);
            }
            wrapper.cancel();
            PacketWrapper packetWrapper = wrapper.create(ClientboundConfigurationPackets1_20_5.REGISTRY_DATA);
            packetWrapper.write(Types.STRING, "minecraft:wolf_variant");
            CompoundTag paleWolf = new CompoundTag();
            paleWolf.putString("wild_texture", "entity/wolf/wolf");
            paleWolf.putString("tame_texture", "entity/wolf/wolf_tame");
            paleWolf.putString("angry_texture", "entity/wolf/wolf_angry");
            paleWolf.put("biomes", new ListTag<StringTag>(StringTag.class));
            packetWrapper.write(Types.REGISTRY_ENTRY_ARRAY, new RegistryEntry[]{new RegistryEntry("minecraft:pale", paleWolf)});
            packetWrapper.send(Protocol1_20_3To1_20_5.class);
            PacketWrapper bannerPatternsPacket = wrapper.create(ClientboundConfigurationPackets1_20_5.REGISTRY_DATA);
            bannerPatternsPacket.write(Types.STRING, "minecraft:banner_pattern");
            RegistryEntry[] patternEntries = new RegistryEntry[BannerPatterns1_20_5.keys().length];
            String[] keys2 = BannerPatterns1_20_5.keys();
            for (int i = 0; i < keys2.length; ++i) {
                String key;
                CompoundTag pattern = new CompoundTag();
                String string = key = keys2[i];
                String resourceLocation = "minecraft:" + string;
                pattern.putString("asset_id", key);
                String string2 = key;
                pattern.putString("translation_key", "block.minecraft.banner." + string2);
                patternEntries[i] = new RegistryEntry(resourceLocation, pattern);
            }
            bannerPatternsPacket.write(Types.REGISTRY_ENTRY_ARRAY, patternEntries);
            bannerPatternsPacket.send(Protocol1_20_3To1_20_5.class);
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.LOGIN, new PacketHandlers(){

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
                    String dimensionKey = wrapper.read(Types.STRING);
                    DimensionData data = EntityPacketRewriter1_20_5.this.tracker(wrapper.user()).dimensionData(dimensionKey);
                    wrapper.write(Types.VAR_INT, data.id());
                });
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.OPTIONAL_GLOBAL_POSITION);
                this.map(Types.VAR_INT);
                this.handler(EntityPacketRewriter1_20_5.this.worldDataTrackerHandlerByKey1_20_5(3));
                this.handler(EntityPacketRewriter1_20_5.this.playerTrackerHandler());
                this.handler(wrapper -> {
                    AcknowledgedMessagesStorage storage = wrapper.user().get(AcknowledgedMessagesStorage.class);
                    if (storage.secureChatEnforced() != null) {
                        wrapper.write(Types.BOOLEAN, storage.isSecureChatEnforced());
                    } else {
                        wrapper.write(Types.BOOLEAN, Via.getConfig().enforceSecureChat());
                    }
                    storage.clear();
                    byte gamemode = wrapper.get(Types.BYTE, 0);
                    if (gamemode == 1) {
                        EntityPacketRewriter1_20_5.this.sendRangeAttributes(wrapper.user(), true);
                    }
                });
            }
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.RESPAWN, wrapper -> {
            String dimensionKey = wrapper.read(Types.STRING);
            DimensionData data = this.tracker(wrapper.user()).dimensionData(dimensionKey);
            wrapper.write(Types.VAR_INT, data.id());
            wrapper.passthrough(Types.STRING);
            this.worldDataTrackerHandlerByKey1_20_5(0).handle(wrapper);
            wrapper.passthrough(Types.LONG);
            byte gamemode = wrapper.passthrough(Types.BYTE);
            this.sendRangeAttributes(wrapper.user(), gamemode == 1);
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.UPDATE_MOB_EFFECT, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            byte amplifier = wrapper.read(Types.BYTE);
            wrapper.write(Types.VAR_INT, Integer.valueOf(amplifier));
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BYTE);
            wrapper.read(Types.OPTIONAL_COMPOUND_TAG);
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.UPDATE_ATTRIBUTES, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                String attributeIdentifier = wrapper.read(Types.STRING);
                int mappedId = Attributes1_20_5.keyToId(attributeIdentifier);
                wrapper.write(Types.VAR_INT, mappedId != -1 ? mappedId : 0);
                wrapper.passthrough(Types.DOUBLE);
                int modifierSize = wrapper.passthrough(Types.VAR_INT);
                for (int j = 0; j < modifierSize; ++j) {
                    wrapper.passthrough(Types.UUID);
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.passthrough(Types.BYTE);
                }
            }
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.GAME_EVENT, wrapper -> {
            short event = wrapper.passthrough(Types.UNSIGNED_BYTE);
            if (event != 3) {
                return;
            }
            float value = wrapper.passthrough(Types.FLOAT).floatValue();
            this.sendRangeAttributes(wrapper.user(), value == 1.0f);
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
            this.moveTag(options, "value", "block_state");
        } else if ("item".equals(particleType)) {
            this.moveTag(options, "value", "item");
        } else if ("dust_color_transition".equals(particleType)) {
            this.moveTag(options, "fromColor", "from_color");
            this.moveTag(options, "toColor", "to_color");
        } else if ("entity_effect".equals(particleType)) {
            this.moveTag(options, "value", "color");
        }
    }

    void moveTag(CompoundTag compoundTag, String from, String to) {
        Tag tag = compoundTag.remove(from);
        if (tag != null) {
            compoundTag.put(to, tag);
        }
    }

    void checkSoundTag(@Nullable CompoundTag tag, String key) {
        if (tag == null) {
            return;
        }
        String sound = tag.getString(key);
        if (sound != null && ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().soundId(sound) == -1) {
            CompoundTag directSoundValue = new CompoundTag();
            directSoundValue.putString("sound_id", sound);
            tag.put(key, directSoundValue);
        }
    }

    void replaceNullValues(RegistryEntry[] entries) {
        RegistryEntry first = null;
        for (RegistryEntry registryEntry : entries) {
            if (registryEntry == null) continue;
            first = registryEntry;
            break;
        }
        for (int i = 0; i < entries.length; ++i) {
            if (entries[i] != null) continue;
            entries[i] = first.withKey(UUID.randomUUID().toString());
        }
    }

    void sendRangeAttributes(UserConnection connection, boolean creativeMode) {
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_20_5.UPDATE_ATTRIBUTES, connection);
        wrapper.write(Types.VAR_INT, this.tracker(connection).clientEntityId());
        wrapper.write(Types.VAR_INT, 2);
        this.writeAttribute(wrapper, "player.block_interaction_range", 4.5, creativeMode ? CREATIVE_BLOCK_INTERACTION_RANGE : null, 0.5);
        this.writeAttribute(wrapper, "player.entity_interaction_range", 3.0, creativeMode ? CREATIVE_ENTITY_INTERACTION_RANGE : null, 2.0);
        wrapper.scheduleSend(Protocol1_20_3To1_20_5.class);
    }

    void writeAttribute(PacketWrapper wrapper, String attributeId, double base, @Nullable UUID modifierId, double amount) {
        wrapper.write(Types.VAR_INT, Attributes1_20_5.keyToId(attributeId));
        wrapper.write(Types.DOUBLE, base);
        if (modifierId != null) {
            wrapper.write(Types.VAR_INT, 1);
            wrapper.write(Types.UUID, modifierId);
            wrapper.write(Types.DOUBLE, amount);
            wrapper.write(Types.BYTE, (byte)0);
        } else {
            wrapper.write(Types.VAR_INT, 0);
        }
    }

    static int withAlpha(int rgb) {
        return 0xFF000000 | rgb & 0xFFFFFF;
    }

    @Override
    protected void registerRewrites() {
        this.filter().mapDataType(typeId -> {
            int id = typeId;
            if (id >= Types1_20_5.ENTITY_DATA_TYPES.particlesType.typeId()) {
                ++id;
            }
            if (id >= Types1_20_5.ENTITY_DATA_TYPES.wolfVariantType.typeId()) {
                ++id;
            }
            if (id >= Types1_20_5.ENTITY_DATA_TYPES.armadilloState.typeId()) {
                ++id;
            }
            return Types1_20_5.ENTITY_DATA_TYPES.byId(id);
        });
        this.registerEntityDataTypeHandler(Types1_20_5.ENTITY_DATA_TYPES.itemType, Types1_20_5.ENTITY_DATA_TYPES.blockStateType, Types1_20_5.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_20_5.ENTITY_DATA_TYPES.particleType, null, Types1_20_5.ENTITY_DATA_TYPES.componentType, Types1_20_5.ENTITY_DATA_TYPES.optionalComponentType);
        this.registerBlockStateHandler(EntityTypes1_20_5.ABSTRACT_MINECART, 11);
        this.filter().type(EntityTypes1_20_5.LIVING_ENTITY).index(10).handler((event, data) -> {
            int effectColor = (Integer)data.value();
            if (effectColor == 0) {
                data.setTypeAndValue(Types1_20_5.ENTITY_DATA_TYPES.particlesType, new Particle[0]);
                return;
            }
            Particle particle = new Particle(((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getParticleMappings().mappedId("entity_effect"));
            particle.add(Types.INT, EntityPacketRewriter1_20_5.withAlpha(effectColor));
            data.setTypeAndValue(Types1_20_5.ENTITY_DATA_TYPES.particlesType, new Particle[]{particle});
        });
        this.filter().type(EntityTypes1_20_5.LLAMA).handler((event, data) -> {
            int dataIndex = event.index();
            if (dataIndex == 20) {
                event.cancel();
                int color = (Integer)data.value();
                PacketWrapper setEquipment = PacketWrapper.create(ClientboundPackets1_20_5.SET_EQUIPMENT, event.user());
                setEquipment.write(Types.VAR_INT, event.entityId());
                setEquipment.write(Types.BYTE, (byte)6);
                setEquipment.write(Types1_20_5.ITEM, new StructuredItem(color + 446, 1, new StructuredDataContainer()));
                setEquipment.scheduleSend(Protocol1_20_3To1_20_5.class);
            } else if (dataIndex > 20) {
                event.setIndex(dataIndex - 1);
            }
        });
        this.filter().type(EntityTypes1_20_5.AREA_EFFECT_CLOUD).handler((event, data) -> {
            EntityData colorData;
            int dataIndex = event.index();
            if (dataIndex == 9) {
                EntityData particleData = event.dataAtIndex(11);
                int color = (Integer)data.value();
                if (particleData == null) {
                    if (color != 0) {
                        Particle particle = new Particle(((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getParticleMappings().mappedId("entity_effect"));
                        particle.add(Types.INT, EntityPacketRewriter1_20_5.withAlpha(color));
                        event.createExtraData(new EntityData(10, Types1_20_5.ENTITY_DATA_TYPES.particleType, particle));
                    }
                } else {
                    this.addColor(particleData, color);
                }
                event.cancel();
                return;
            }
            if (dataIndex == 11 && (colorData = event.dataAtIndex(9)) != null && colorData.dataType() == Types1_20_5.ENTITY_DATA_TYPES.varIntType) {
                this.addColor(data, (Integer)colorData.value());
            }
            if (dataIndex > 9) {
                event.setIndex(dataIndex - 1);
            }
        });
        this.filter().type(EntityTypes1_20_5.ARROW).index(10).handler((event, data) -> {
            int color = (Integer)data.value();
            if (color != -1) {
                data.setValue(EntityPacketRewriter1_20_5.withAlpha(color));
            }
        });
        this.filter().type(EntityTypes1_20_5.ITEM_PROJECTILE).index(8).handler((event, data) -> {
            Item item = (Item)data.value();
            if (item == null || item.isEmpty()) {
                event.cancel();
            }
        });
    }

    void addColor(@Nullable EntityData particleMeta, int color) {
        if (particleMeta == null) {
            return;
        }
        Particle particle = (Particle)particleMeta.value();
        if (particle.id() == ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getParticleMappings().mappedId("entity_effect")) {
            particle.getArgument(0).setValue(EntityPacketRewriter1_20_5.withAlpha(color));
        }
    }

    @Override
    public void rewriteParticle(UserConnection connection, Particle particle) {
        super.rewriteParticle(connection, particle);
        if (particle.id() == ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getParticleMappings().mappedId("entity_effect")) {
            particle.add(Types.INT, 0);
        }
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_20_5.getTypeFromId(type);
    }
}

