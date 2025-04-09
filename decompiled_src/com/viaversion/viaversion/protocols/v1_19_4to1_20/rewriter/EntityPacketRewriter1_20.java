/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19_4to1_20.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.minecraft.Quaternion;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_4to1_20.Protocol1_19_4To1_20;
import com.viaversion.viaversion.rewriter.EntityRewriter;
import com.viaversion.viaversion.util.TagUtil;

public final class EntityPacketRewriter1_20
extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_19_4To1_20> {
    static final Quaternion Y_FLIPPED_ROTATION = new Quaternion(0.0f, 1.0f, 0.0f, 0.0f);

    public EntityPacketRewriter1_20(Protocol1_19_4To1_20 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_4.ADD_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
        this.registerSetEntityData(ClientboundPackets1_19_4.SET_ENTITY_DATA, Types1_19_4.ENTITY_DATA_LIST, Types1_20.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.STRING_ARRAY);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.OPTIONAL_GLOBAL_POSITION);
                this.create(Types.VAR_INT, 0);
                this.handler(EntityPacketRewriter1_20.this.dimensionDataHandler());
                this.handler(EntityPacketRewriter1_20.this.biomeSizeTracker());
                this.handler(EntityPacketRewriter1_20.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    CompoundTag registry = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    ListTag<CompoundTag> damageTypes = TagUtil.getRegistryEntries(registry, "damage_type");
                    int highestId = -1;
                    for (CompoundTag damageType : damageTypes) {
                        int id = damageType.getInt("id");
                        highestId = Math.max(highestId, id);
                    }
                    CompoundTag outsideBorderReason = new CompoundTag();
                    CompoundTag outsideBorderElement = new CompoundTag();
                    outsideBorderElement.put("scaling", new StringTag("always"));
                    outsideBorderElement.put("exhaustion", new FloatTag(0.0f));
                    outsideBorderElement.put("message_id", new StringTag("badRespawnPoint"));
                    outsideBorderReason.put("id", new IntTag(highestId + 1));
                    outsideBorderReason.put("name", new StringTag("minecraft:outside_border"));
                    outsideBorderReason.put("element", outsideBorderElement);
                    damageTypes.add(outsideBorderReason);
                    CompoundTag genericKillReason = new CompoundTag();
                    CompoundTag genericKillElement = new CompoundTag();
                    genericKillElement.put("scaling", new StringTag("always"));
                    genericKillElement.put("exhaustion", new FloatTag(0.0f));
                    genericKillElement.put("message_id", new StringTag("badRespawnPoint"));
                    genericKillReason.put("id", new IntTag(highestId + 2));
                    genericKillReason.put("name", new StringTag("minecraft:generic_kill"));
                    genericKillReason.put("element", genericKillElement);
                    damageTypes.add(genericKillReason);
                });
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.map(Types.LONG);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.map(Types.BOOLEAN);
                this.map(Types.BYTE);
                this.map(Types.OPTIONAL_GLOBAL_POSITION);
                this.create(Types.VAR_INT, 0);
                this.handler(EntityPacketRewriter1_20.this.worldDataTrackerHandlerByKey());
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().mapDataType(Types1_20.ENTITY_DATA_TYPES::byId);
        this.registerEntityDataTypeHandler(Types1_20.ENTITY_DATA_TYPES.itemType, Types1_20.ENTITY_DATA_TYPES.blockStateType, Types1_20.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_20.ENTITY_DATA_TYPES.particleType, null);
        this.registerBlockStateHandler(EntityTypes1_19_4.ABSTRACT_MINECART, 11);
        this.filter().type(EntityTypes1_19_4.ITEM_DISPLAY).handler((event, data) -> {
            if (event.trackedEntity().hasSentEntityData() || event.hasExtraData()) {
                return;
            }
            if (event.dataAtIndex(12) == null) {
                event.createExtraData(new EntityData(12, Types1_20.ENTITY_DATA_TYPES.quaternionType, Y_FLIPPED_ROTATION));
            }
        });
        this.filter().type(EntityTypes1_19_4.ITEM_DISPLAY).index(12).handler((event, data) -> {
            Quaternion quaternion = (Quaternion)data.value();
            data.setValue(this.rotateY180(quaternion));
        });
    }

    @Override
    public EntityType typeFromId(int type) {
        return EntityTypes1_19_4.getTypeFromId(type);
    }

    Quaternion rotateY180(Quaternion quaternion) {
        return new Quaternion(-quaternion.z(), quaternion.w(), quaternion.x(), -quaternion.y());
    }
}

