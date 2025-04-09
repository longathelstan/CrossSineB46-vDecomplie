/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.rewriter;

import com.google.common.collect.Sets;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viabackwards.protocol.v1_16_2to1_16_1.storage.BiomeStorage;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16_2;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.data.DimensionRegistries1_16;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.TagUtil;
import java.util.Set;

public class EntityPacketRewriter1_16_2
extends EntityRewriter<ClientboundPackets1_16_2, Protocol1_16_2To1_16_1> {
    final Set<String> oldDimensions = Sets.newHashSet((Object[])new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"});
    boolean warned;

    public EntityPacketRewriter1_16_2(Protocol1_16_2To1_16_1 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets1_16_2.ADD_ENTITY, EntityTypes1_16_2.FALLING_BLOCK);
        this.registerSpawnTracker(ClientboundPackets1_16_2.ADD_MOB);
        this.registerTracker(ClientboundPackets1_16_2.ADD_EXPERIENCE_ORB, EntityTypes1_16_2.EXPERIENCE_ORB);
        this.registerTracker(ClientboundPackets1_16_2.ADD_PAINTING, EntityTypes1_16_2.PAINTING);
        this.registerTracker(ClientboundPackets1_16_2.ADD_PLAYER, EntityTypes1_16_2.PLAYER);
        this.registerRemoveEntities(ClientboundPackets1_16_2.REMOVE_ENTITIES);
        this.registerSetEntityData(ClientboundPackets1_16_2.SET_ENTITY_DATA, Types1_16.ENTITY_DATA_LIST);
        ((Protocol1_16_2To1_16_1)this.protocol).registerClientbound(ClientboundPackets1_16_2.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.handler(wrapper -> {
                    boolean hardcore = wrapper.read(Types.BOOLEAN);
                    short gamemode = wrapper.read(Types.BYTE).byteValue();
                    if (hardcore) {
                        gamemode = (short)(gamemode | 8);
                    }
                    wrapper.write(Types.UNSIGNED_BYTE, gamemode);
                });
                this.map(Types.BYTE);
                this.map(Types.STRING_ARRAY);
                this.handler(wrapper -> {
                    CompoundTag registry = wrapper.read(Types.NAMED_COMPOUND_TAG);
                    if (wrapper.user().getProtocolInfo().protocolVersion().olderThanOrEqualTo(ProtocolVersion.v1_15_2)) {
                        ListTag<CompoundTag> biomes = TagUtil.getRegistryEntries(registry, "worldgen/biome");
                        BiomeStorage biomeStorage = wrapper.user().get(BiomeStorage.class);
                        biomeStorage.clear();
                        for (CompoundTag biome : biomes) {
                            StringTag name = biome.getStringTag("name");
                            NumberTag id = biome.getNumberTag("id");
                            biomeStorage.addBiome(name.getValue(), id.asInt());
                        }
                    } else if (!EntityPacketRewriter1_16_2.this.warned && !ViaBackwards.getConfig().suppressEmulationWarnings()) {
                        EntityPacketRewriter1_16_2.this.warned = true;
                        ((Protocol1_16_2To1_16_1)EntityPacketRewriter1_16_2.this.protocol).getLogger().warning("1.16 and 1.16.1 clients are only partially supported and may have wrong biomes displayed.");
                    }
                    wrapper.write(Types.NAMED_COMPOUND_TAG, DimensionRegistries1_16.getDimensionsTag());
                    CompoundTag dimensionData = wrapper.read(Types.NAMED_COMPOUND_TAG);
                    wrapper.write(Types.STRING, EntityPacketRewriter1_16_2.this.getDimensionFromData(dimensionData));
                });
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.handler(wrapper -> {
                    int maxPlayers = wrapper.read(Types.VAR_INT);
                    wrapper.write(Types.UNSIGNED_BYTE, (short)Math.min(maxPlayers, 255));
                });
                this.handler(EntityPacketRewriter1_16_2.this.getPlayerTrackerHandler());
            }
        });
        ((Protocol1_16_2To1_16_1)this.protocol).registerClientbound(ClientboundPackets1_16_2.RESPAWN, wrapper -> {
            CompoundTag dimensionData = wrapper.read(Types.NAMED_COMPOUND_TAG);
            wrapper.write(Types.STRING, this.getDimensionFromData(dimensionData));
            this.tracker(wrapper.user()).clearEntities();
        });
    }

    String getDimensionFromData(CompoundTag dimensionData) {
        StringTag effectsLocation = dimensionData.getStringTag("effects");
        return effectsLocation != null && this.oldDimensions.contains(Key.namespaced(effectsLocation.getValue())) ? effectsLocation.getValue() : "minecraft:overworld";
    }

    @Override
    protected void registerRewrites() {
        this.registerEntityDataTypeHandler(Types1_16.ENTITY_DATA_TYPES.itemType, null, Types1_16.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_16.ENTITY_DATA_TYPES.particleType, Types1_16.ENTITY_DATA_TYPES.componentType, Types1_16.ENTITY_DATA_TYPES.optionalComponentType);
        this.filter().type(EntityTypes1_16_2.ABSTRACT_PIGLIN).index(15).toIndex(16);
        this.filter().type(EntityTypes1_16_2.ABSTRACT_PIGLIN).index(16).toIndex(15);
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
        this.mapEntityTypeWithData(EntityTypes1_16_2.PIGLIN_BRUTE, EntityTypes1_16_2.PIGLIN).jsonName();
    }

    @Override
    public EntityType typeFromId(int typeId) {
        return EntityTypes1_16_2.getTypeFromId(typeId);
    }
}

