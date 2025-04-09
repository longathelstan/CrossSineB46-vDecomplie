/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_5to1_21.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.PaintingVariant;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityDataType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Enchantments1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.data.Paintings1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.storage.EfficiencyAttributeStorage;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.storage.OnGroundTracker;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.ArrayUtil;
import com.viaversion.viaversion.util.Key;

public final class EntityPacketRewriter1_21
extends EntityRewriter<ClientboundPacket1_20_5, Protocol1_20_5To1_21> {
    public EntityPacketRewriter1_21(Protocol1_20_5To1_21 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_20_5.ADD_ENTITY, EntityTypes1_20_5.FALLING_BLOCK);
        this.registerSetEntityData(ClientboundPackets1_20_5.SET_ENTITY_DATA, Types1_20_5.ENTITY_DATA_LIST, Types1_21.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_20_5.REMOVE_ENTITIES);
        ((Protocol1_20_5To1_21)this.protocol).registerClientbound(ClientboundConfigurationPackets1_20_5.REGISTRY_DATA, wrapper -> {
            String type = Key.stripMinecraftNamespace(wrapper.passthrough(Types.STRING));
            RegistryEntry[] entries = wrapper.passthrough(Types.REGISTRY_ENTRY_ARRAY);
            if (type.equals("damage_type")) {
                CompoundTag campfireDamageType = new CompoundTag();
                campfireDamageType.putString("scaling", "when_caused_by_living_non_player");
                campfireDamageType.putString("message_id", "inFire");
                campfireDamageType.putFloat("exhaustion", 0.1f);
                wrapper.set(Types.REGISTRY_ENTRY_ARRAY, 0, ArrayUtil.add(entries, new RegistryEntry("minecraft:campfire", campfireDamageType)));
            } else {
                this.handleRegistryData1_20_5(wrapper.user(), type, entries);
            }
        });
        ((Protocol1_20_5To1_21)this.protocol).registerClientbound(ClientboundConfigurationPackets1_20_5.FINISH_CONFIGURATION, wrapper -> {
            PacketWrapper paintingRegistryPacket = wrapper.create(ClientboundConfigurationPackets1_20_5.REGISTRY_DATA);
            paintingRegistryPacket.write(Types.STRING, "minecraft:painting_variant");
            RegistryEntry[] paintingsRegistry = new RegistryEntry[Paintings1_20_5.PAINTINGS.length];
            for (int i = 0; i < Paintings1_20_5.PAINTINGS.length; ++i) {
                PaintingVariant painting = Paintings1_20_5.PAINTINGS[i];
                CompoundTag tag = new CompoundTag();
                tag.putInt("width", painting.width());
                tag.putInt("height", painting.height());
                tag.putString("asset_id", painting.assetId());
                paintingsRegistry[i] = new RegistryEntry(painting.assetId(), tag);
            }
            paintingRegistryPacket.write(Types.REGISTRY_ENTRY_ARRAY, paintingsRegistry);
            paintingRegistryPacket.send(Protocol1_20_5To1_21.class);
            PacketWrapper enchantmentRegistryPacket = wrapper.create(ClientboundConfigurationPackets1_20_5.REGISTRY_DATA);
            enchantmentRegistryPacket.write(Types.STRING, "minecraft:enchantment");
            RegistryEntry[] enchantmentRegistry = new RegistryEntry[Enchantments1_20_5.ENCHANTMENTS.size()];
            for (int i = 0; i < Enchantments1_20_5.ENCHANTMENTS.size(); ++i) {
                String key = Enchantments1_20_5.idToKey(i);
                CompoundTag tag = ((Protocol1_20_5To1_21)this.protocol).getMappingData().enchantment(i);
                enchantmentRegistry[i] = new RegistryEntry(key, tag);
            }
            enchantmentRegistryPacket.write(Types.REGISTRY_ENTRY_ARRAY, enchantmentRegistry);
            enchantmentRegistryPacket.send(Protocol1_20_5To1_21.class);
            PacketWrapper jukeboxSongsPacket = wrapper.create(ClientboundConfigurationPackets1_20_5.REGISTRY_DATA);
            jukeboxSongsPacket.write(Types.STRING, "minecraft:jukebox_song");
            jukeboxSongsPacket.write(Types.REGISTRY_ENTRY_ARRAY, ((Protocol1_20_5To1_21)this.protocol).getMappingData().jukeboxSongs());
            jukeboxSongsPacket.send(Protocol1_20_5To1_21.class);
        });
        ((Protocol1_20_5To1_21)this.protocol).registerClientbound(ClientboundPackets1_20_5.LOGIN, new PacketHandlers(){

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
                this.map(Types.VAR_INT);
                this.map(Types.STRING);
                this.handler(EntityPacketRewriter1_21.this.worldDataTrackerHandlerByKey1_20_5(3));
                this.handler(EntityPacketRewriter1_21.this.playerTrackerHandler());
                this.handler(wrapper -> wrapper.user().get(EfficiencyAttributeStorage.class).onLoginSent(wrapper.user()));
            }
        });
        ((Protocol1_20_5To1_21)this.protocol).registerClientbound(ClientboundPackets1_20_5.RESPAWN, wrapper -> {
            int dimensionId = wrapper.passthrough(Types.VAR_INT);
            String world = wrapper.passthrough(Types.STRING);
            this.trackWorldDataByKey1_20_5(wrapper.user(), dimensionId, world);
            wrapper.user().get(EfficiencyAttributeStorage.class).onRespawn(wrapper.user());
        });
        if (!Via.getConfig().fix1_21PlacementRotation()) {
            return;
        }
        ((Protocol1_20_5To1_21)this.protocol).registerServerbound(ServerboundPackets1_20_5.MOVE_PLAYER_POS, wrapper -> {
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.user().get(OnGroundTracker.class).setOnGround(wrapper.passthrough(Types.BOOLEAN));
        });
        ((Protocol1_20_5To1_21)this.protocol).registerServerbound(ServerboundPackets1_20_5.MOVE_PLAYER_ROT, wrapper -> {
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.user().get(OnGroundTracker.class).setOnGround(wrapper.passthrough(Types.BOOLEAN));
        });
        ((Protocol1_20_5To1_21)this.protocol).registerServerbound(ServerboundPackets1_20_5.MOVE_PLAYER_POS_ROT, wrapper -> {
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.user().get(OnGroundTracker.class).setOnGround(wrapper.passthrough(Types.BOOLEAN));
        });
        ((Protocol1_20_5To1_21)this.protocol).registerServerbound(ServerboundPackets1_20_5.MOVE_PLAYER_STATUS_ONLY, wrapper -> wrapper.user().get(OnGroundTracker.class).setOnGround(wrapper.passthrough(Types.BOOLEAN)));
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, data) -> {
            EntityDataType type = data.dataType();
            if (type == Types1_20_5.ENTITY_DATA_TYPES.wolfVariantType) {
                int variant = (Integer)data.value();
                data.setTypeAndValue(Types1_21.ENTITY_DATA_TYPES.wolfVariantType, Holder.of(variant));
            } else if (type == Types1_20_5.ENTITY_DATA_TYPES.paintingVariantType) {
                int variant = (Integer)data.value();
                data.setTypeAndValue(Types1_21.ENTITY_DATA_TYPES.paintingVariantType, Holder.of(variant));
            } else {
                data.setDataType(Types1_21.ENTITY_DATA_TYPES.byId(type.typeId()));
            }
        });
        this.registerEntityDataTypeHandler(Types1_21.ENTITY_DATA_TYPES.itemType, Types1_21.ENTITY_DATA_TYPES.blockStateType, Types1_21.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_21.ENTITY_DATA_TYPES.particleType, Types1_21.ENTITY_DATA_TYPES.particlesType, Types1_21.ENTITY_DATA_TYPES.componentType, Types1_21.ENTITY_DATA_TYPES.optionalComponentType);
        this.registerBlockStateHandler(EntityTypes1_20_5.ABSTRACT_MINECART, 11);
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_20_5.getTypeFromId(type);
    }
}

