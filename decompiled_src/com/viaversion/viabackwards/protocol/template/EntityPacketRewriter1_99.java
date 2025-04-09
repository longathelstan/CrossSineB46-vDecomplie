/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.template;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.template.Protocol1_98To1_99;
import com.viaversion.viaversion.api.minecraft.RegistryEntry;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundConfigurationPackets1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import com.viaversion.viaversion.util.Key;

final class EntityPacketRewriter1_99
extends EntityRewriter<ClientboundPacket1_21, Protocol1_98To1_99> {
    public EntityPacketRewriter1_99(Protocol1_98To1_99 protocol) {
        super(protocol, Types1_20_5.ENTITY_DATA_TYPES.optionalComponentType, Types1_20_5.ENTITY_DATA_TYPES.booleanType);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_21.ADD_ENTITY, EntityTypes1_20_5.FALLING_BLOCK);
        this.registerSetEntityData(ClientboundPackets1_21.SET_ENTITY_DATA, Types1_20_5.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_21.REMOVE_ENTITIES);
        ((Protocol1_98To1_99)this.protocol).registerClientbound(ClientboundConfigurationPackets1_21.REGISTRY_DATA, wrapper -> {
            String registryKey = Key.stripMinecraftNamespace(wrapper.passthrough(Types.STRING));
            RegistryEntry[] entries = wrapper.passthrough(Types.REGISTRY_ENTRY_ARRAY);
            this.handleRegistryData1_20_5(wrapper.user(), registryKey, entries);
        });
        ((Protocol1_98To1_99)this.protocol).registerClientbound(ClientboundPackets1_21.LOGIN, new PacketHandlers(){

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
                this.handler(EntityPacketRewriter1_99.this.worldDataTrackerHandlerByKey1_20_5(3));
            }
        });
        ((Protocol1_98To1_99)this.protocol).registerClientbound(ClientboundPackets1_21.RESPAWN, wrapper -> {
            int dimensionId = wrapper.passthrough(Types.VAR_INT);
            String world = wrapper.passthrough(Types.STRING);
            this.trackWorldDataByKey1_20_5(wrapper.user(), dimensionId, world);
        });
    }

    @Override
    protected void registerRewrites() {
        this.registerEntityDataTypeHandler1_20_3(Types1_20_5.ENTITY_DATA_TYPES.itemType, Types1_20_5.ENTITY_DATA_TYPES.blockStateType, Types1_20_5.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_20_5.ENTITY_DATA_TYPES.particleType, Types1_20_5.ENTITY_DATA_TYPES.particlesType, Types1_20_5.ENTITY_DATA_TYPES.componentType, Types1_20_5.ENTITY_DATA_TYPES.optionalComponentType);
        this.registerBlockStateHandler(EntityTypes1_20_5.ABSTRACT_MINECART, 11);
    }

    @Override
    public void onMappingDataLoaded() {
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_20_5.getTypeFromId(type);
    }
}

