/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_15_2to1_16.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.WorldIdentifiers;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.data.AttributeMappings1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.data.DimensionRegistries1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.storage.InventoryTracker1_16;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.UUID;

public class EntityPacketRewriter1_16
extends EntityRewriter<ClientboundPackets1_15, Protocol1_15_2To1_16> {
    final PacketHandler DIMENSION_HANDLER = wrapper -> {
        String outputName;
        String dimensionName;
        WorldIdentifiers map = Via.getConfig().get1_16WorldNamesMap();
        WorldIdentifiers userMap = wrapper.user().get(WorldIdentifiers.class);
        if (userMap != null) {
            map = userMap;
        }
        int dimension = wrapper.read(Types.INT);
        switch (dimension) {
            case -1: {
                dimensionName = "minecraft:the_nether";
                outputName = map.nether();
                break;
            }
            case 0: {
                dimensionName = "minecraft:overworld";
                outputName = map.overworld();
                break;
            }
            case 1: {
                dimensionName = "minecraft:the_end";
                outputName = map.end();
                break;
            }
            default: {
                int n = dimension;
                ((Protocol1_15_2To1_16)this.protocol).getLogger().warning("Invalid dimension id: " + n);
                dimensionName = "minecraft:overworld";
                outputName = map.overworld();
            }
        }
        wrapper.write(Types.STRING, dimensionName);
        wrapper.write(Types.STRING, outputName);
    };

    public EntityPacketRewriter1_16(Protocol1_15_2To1_16 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_15.ADD_GLOBAL_ENTITY, ClientboundPackets1_16.ADD_ENTITY, wrapper -> {
            int entityId = wrapper.passthrough(Types.VAR_INT);
            byte type = wrapper.read(Types.BYTE);
            if (type != 1) {
                wrapper.cancel();
                return;
            }
            wrapper.user().getEntityTracker(Protocol1_15_2To1_16.class).addEntity(entityId, EntityTypes1_16.LIGHTNING_BOLT);
            wrapper.write(Types.UUID, UUID.randomUUID());
            wrapper.write(Types.VAR_INT, EntityTypes1_16.LIGHTNING_BOLT.getId());
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types.INT, 0);
            wrapper.write(Types.SHORT, (short)0);
            wrapper.write(Types.SHORT, (short)0);
            wrapper.write(Types.SHORT, (short)0);
        });
        this.registerTrackerWithData(ClientboundPackets1_15.ADD_ENTITY, EntityTypes1_16.FALLING_BLOCK);
        this.registerTracker(ClientboundPackets1_15.ADD_MOB);
        this.registerTracker(ClientboundPackets1_15.ADD_PLAYER, EntityTypes1_16.PLAYER);
        this.registerSetEntityData(ClientboundPackets1_15.SET_ENTITY_DATA, Types1_14.ENTITY_DATA_LIST, Types1_16.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_15.REMOVE_ENTITIES);
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(EntityPacketRewriter1_16.this.DIMENSION_HANDLER);
                this.map(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    EntityPacketRewriter1_16.this.tracker(wrapper.user()).clearEntities();
                    wrapper.write(Types.BYTE, (byte)-1);
                    boolean keepAttributes = wrapper.user().getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(ProtocolVersion.v1_15);
                    String levelType = wrapper.read(Types.STRING);
                    wrapper.write(Types.BOOLEAN, false);
                    wrapper.write(Types.BOOLEAN, levelType.equals("flat"));
                    wrapper.write(Types.BOOLEAN, keepAttributes);
                });
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_15.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    wrapper.write(Types.BYTE, (byte)-1);
                    wrapper.write(Types.STRING_ARRAY, DimensionRegistries1_16.getWorldNames());
                    wrapper.write(Types.NAMED_COMPOUND_TAG, DimensionRegistries1_16.getDimensionsTag());
                });
                this.handler(EntityPacketRewriter1_16.this.DIMENSION_HANDLER);
                this.map(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(EntityPacketRewriter1_16.this.playerTrackerHandler());
                this.handler(wrapper -> {
                    String type = wrapper.read(Types.STRING);
                    wrapper.passthrough(Types.VAR_INT);
                    wrapper.passthrough(Types.BOOLEAN);
                    wrapper.passthrough(Types.BOOLEAN);
                    wrapper.write(Types.BOOLEAN, false);
                    wrapper.write(Types.BOOLEAN, type.equals("flat"));
                });
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerClientbound(ClientboundPackets1_15.UPDATE_ATTRIBUTES, wrapper -> {
            int size;
            wrapper.passthrough(Types.VAR_INT);
            int actualSize = size = wrapper.passthrough(Types.INT).intValue();
            for (int i = 0; i < size; ++i) {
                int j;
                int modifierSize;
                String key = wrapper.read(Types.STRING);
                String attributeIdentifier = (String)AttributeMappings1_16.attributeIdentifierMappings().get((Object)key);
                if (attributeIdentifier == null && !Key.isValid(attributeIdentifier = Key.namespaced(key))) {
                    if (!Via.getConfig().isSuppressConversionWarnings()) {
                        String string = key;
                        ((Protocol1_15_2To1_16)this.protocol).getLogger().warning("Invalid attribute: " + string);
                    }
                    --actualSize;
                    wrapper.read(Types.DOUBLE);
                    modifierSize = wrapper.read(Types.VAR_INT);
                    for (j = 0; j < modifierSize; ++j) {
                        wrapper.read(Types.UUID);
                        wrapper.read(Types.DOUBLE);
                        wrapper.read(Types.BYTE);
                    }
                    continue;
                }
                wrapper.write(Types.STRING, attributeIdentifier);
                wrapper.passthrough(Types.DOUBLE);
                modifierSize = wrapper.passthrough(Types.VAR_INT);
                for (j = 0; j < modifierSize; ++j) {
                    wrapper.passthrough(Types.UUID);
                    wrapper.passthrough(Types.DOUBLE);
                    wrapper.passthrough(Types.BYTE);
                }
            }
            if (size != actualSize) {
                wrapper.set(Types.INT, 0, actualSize);
            }
        });
        ((Protocol1_15_2To1_16)this.protocol).registerServerbound(ServerboundPackets1_16.SWING, wrapper -> {
            InventoryTracker1_16 inventoryTracker = wrapper.user().get(InventoryTracker1_16.class);
            if (inventoryTracker.isInventoryOpen()) {
                wrapper.cancel();
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().mapDataType(Types1_16.ENTITY_DATA_TYPES::byId);
        this.registerEntityDataTypeHandler(Types1_16.ENTITY_DATA_TYPES.itemType, Types1_16.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_16.ENTITY_DATA_TYPES.particleType);
        this.registerBlockStateHandler(EntityTypes1_16.ABSTRACT_MINECART, 10);
        this.filter().type(EntityTypes1_16.ABSTRACT_ARROW).removeIndex(8);
        this.filter().type(EntityTypes1_16.WOLF).index(16).handler((event, data) -> {
            byte mask = (Byte)data.value();
            int angerTime = (mask & 2) != 0 ? Integer.MAX_VALUE : 0;
            event.createExtraData(new EntityData(20, Types1_16.ENTITY_DATA_TYPES.varIntType, angerTime));
        });
    }

    @Override
    public void onMappingDataLoaded() {
        this.mapTypes();
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_16.getTypeFromId(type);
    }
}

