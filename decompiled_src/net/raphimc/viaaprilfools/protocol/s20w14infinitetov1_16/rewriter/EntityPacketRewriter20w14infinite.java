/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.data.AttributeMappings1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.data.DimensionRegistries1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.UUID;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.Protocol20w14infiniteTo1_16;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.packet.ClientboundPackets20w14infinite;

public class EntityPacketRewriter20w14infinite
extends EntityRewriter<ClientboundPackets20w14infinite, Protocol20w14infiniteTo1_16> {
    final PacketHandler DIMENSION_HANDLER = wrapper -> {
        String string;
        String dimensionType;
        int dimension = wrapper.read(Types.INT);
        switch (dimension) {
            case -1: {
                string = dimensionType = "minecraft:the_nether";
                break;
            }
            case 0: {
                string = dimensionType = "minecraft:overworld";
                break;
            }
            case 1: {
                string = dimensionType = "minecraft:the_end";
                break;
            }
            default: {
                dimensionType = "minecraft:overworld";
                int n = dimension;
                String string2 = dimensionType;
                string = string2 + n;
            }
        }
        String dimensionName = string;
        wrapper.write(Types.STRING, dimensionType);
        wrapper.write(Types.STRING, dimensionName);
    };

    public EntityPacketRewriter20w14infinite(Protocol20w14infiniteTo1_16 protocol) {
        super(protocol);
    }

    @Override
    protected void registerPackets() {
        this.registerTrackerWithData(ClientboundPackets20w14infinite.ADD_ENTITY, EntityTypes1_16.FALLING_BLOCK);
        this.registerTracker(ClientboundPackets20w14infinite.ADD_MOB);
        this.registerTracker(ClientboundPackets20w14infinite.ADD_PLAYER, EntityTypes1_16.PLAYER);
        this.registerSetEntityData(ClientboundPackets20w14infinite.SET_ENTITY_DATA, Types1_14.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets20w14infinite.REMOVE_ENTITIES);
        ((Protocol20w14infiniteTo1_16)this.protocol).registerClientbound(ClientboundPackets20w14infinite.ADD_GLOBAL_ENTITY, ClientboundPackets1_16.ADD_ENTITY, wrapper -> {
            int entityId = wrapper.passthrough(Types.VAR_INT);
            wrapper.user().getEntityTracker(Protocol20w14infiniteTo1_16.class).addEntity(entityId, EntityTypes1_16.LIGHTNING_BOLT);
            wrapper.write(Types.UUID, UUID.randomUUID());
            wrapper.write(Types.VAR_INT, EntityTypes1_16.LIGHTNING_BOLT.getId());
            wrapper.read(Types.BYTE);
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
        ((Protocol20w14infiniteTo1_16)this.protocol).registerClientbound(ClientboundPackets20w14infinite.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(EntityPacketRewriter20w14infinite.this.DIMENSION_HANDLER);
                this.map(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    EntityPacketRewriter20w14infinite.this.tracker(wrapper.user()).clearEntities();
                    wrapper.write(Types.BYTE, (byte)-1);
                    String levelType = wrapper.read(Types.STRING);
                    wrapper.write(Types.BOOLEAN, false);
                    wrapper.write(Types.BOOLEAN, levelType.equals("flat"));
                    wrapper.write(Types.BOOLEAN, true);
                });
            }
        });
        ((Protocol20w14infiniteTo1_16)this.protocol).registerClientbound(ClientboundPackets20w14infinite.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    wrapper.write(Types.BYTE, (byte)-1);
                    wrapper.write(Types.STRING_ARRAY, DimensionRegistries1_16.getWorldNames());
                    wrapper.write(Types.NAMED_COMPOUND_TAG, DimensionRegistries1_16.getDimensionsTag());
                });
                this.handler(EntityPacketRewriter20w14infinite.this.DIMENSION_HANDLER);
                this.map(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(EntityPacketRewriter20w14infinite.this.playerTrackerHandler());
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
        ((Protocol20w14infiniteTo1_16)this.protocol).registerClientbound(ClientboundPackets20w14infinite.UPDATE_ATTRIBUTES, wrapper -> {
            int size;
            wrapper.passthrough(Types.VAR_INT);
            int actualSize = size = wrapper.passthrough(Types.INT).intValue();
            for (int i = 0; i < size; ++i) {
                int j;
                int modifierSize;
                String key = wrapper.read(Types.STRING);
                String attributeIdentifier = (String)AttributeMappings1_16.attributeIdentifierMappings().get((Object)key);
                if (attributeIdentifier == null) {
                    String string = key;
                    attributeIdentifier = "minecraft:" + string;
                    if (!Key.isValid(attributeIdentifier)) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            String string2 = key;
                            ((Protocol20w14infiniteTo1_16)this.protocol).getLogger().warning("Invalid attribute: " + string2);
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
    }

    @Override
    protected void registerRewrites() {
        this.registerEntityDataTypeHandler(Types1_14.ENTITY_DATA_TYPES.itemType, Types1_14.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_14.ENTITY_DATA_TYPES.particleType);
        this.registerBlockStateHandler(EntityTypes1_16.ABSTRACT_MINECART, 10);
        this.filter().type(EntityTypes1_16.ABSTRACT_ARROW).removeIndex(8);
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

