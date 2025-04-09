/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20to1_19_4.rewriter;

import com.google.common.collect.Sets;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.v1_20to1_19_4.Protocol1_20To1_19_4;
import com.viaversion.viaversion.api.minecraft.Quaternion;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.util.Key;
import java.util.Set;

public final class EntityPacketRewriter1_20
extends EntityRewriter<ClientboundPackets1_19_4, Protocol1_20To1_19_4> {
    final Set<String> newTrimPatterns = Sets.newHashSet((Object[])new String[]{"host_armor_trim_smithing_template", "raiser_armor_trim_smithing_template", "silence_armor_trim_smithing_template", "shaper_armor_trim_smithing_template", "wayfinder_armor_trim_smithing_template"});
    static final Quaternion Y_FLIPPED_ROTATION = new Quaternion(0.0f, 1.0f, 0.0f, 0.0f);

    public EntityPacketRewriter1_20(Protocol1_20To1_19_4 protocol) {
        super(protocol, Types1_19_4.ENTITY_DATA_TYPES.optionalComponentType, Types1_19_4.ENTITY_DATA_TYPES.booleanType);
    }

    @Override
    public void registerPackets() {
        this.registerTrackerWithData1_19(ClientboundPackets1_19_4.ADD_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
        this.registerSetEntityData(ClientboundPackets1_19_4.SET_ENTITY_DATA, Types1_20.ENTITY_DATA_LIST, Types1_19_4.ENTITY_DATA_LIST);
        this.registerRemoveEntities(ClientboundPackets1_19_4.REMOVE_ENTITIES);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.LOGIN, new PacketHandlers(){

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
                this.read(Types.VAR_INT);
                this.handler(EntityPacketRewriter1_20.this.dimensionDataHandler());
                this.handler(EntityPacketRewriter1_20.this.biomeSizeTracker());
                this.handler(EntityPacketRewriter1_20.this.worldDataTrackerHandlerByKey());
                this.handler(wrapper -> {
                    ListTag<CompoundTag> values2;
                    CompoundTag registry = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    CompoundTag trimPatternTag = registry.getCompoundTag("minecraft:trim_pattern");
                    if (trimPatternTag != null || (trimPatternTag = registry.getCompoundTag("trim_pattern")) != null) {
                        values2 = trimPatternTag.getListTag("value", CompoundTag.class);
                    } else {
                        CompoundTag trimPatternRegistry = Protocol1_20To1_19_4.MAPPINGS.getTrimPatternRegistry().copy();
                        registry.put("minecraft:trim_pattern", trimPatternRegistry);
                        values2 = trimPatternRegistry.getListTag("value", CompoundTag.class);
                    }
                    for (CompoundTag entry : values2) {
                        CompoundTag element = entry.getCompoundTag("element");
                        StringTag templateItem = element.getStringTag("template_item");
                        if (!EntityPacketRewriter1_20.this.newTrimPatterns.contains(Key.stripMinecraftNamespace(templateItem.getValue()))) continue;
                        templateItem.setValue("minecraft:spire_armor_trim_smithing_template");
                    }
                });
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.RESPAWN, new PacketHandlers(){

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
                this.read(Types.VAR_INT);
                this.handler(EntityPacketRewriter1_20.this.worldDataTrackerHandlerByKey());
            }
        });
    }

    @Override
    protected void registerRewrites() {
        this.filter().handler((event, data) -> data.setDataType(Types1_19_4.ENTITY_DATA_TYPES.byId(data.dataType().typeId())));
        this.registerEntityDataTypeHandler(Types1_19_4.ENTITY_DATA_TYPES.itemType, Types1_19_4.ENTITY_DATA_TYPES.blockStateType, Types1_19_4.ENTITY_DATA_TYPES.optionalBlockStateType, Types1_19_4.ENTITY_DATA_TYPES.particleType, Types1_19_4.ENTITY_DATA_TYPES.componentType, Types1_19_4.ENTITY_DATA_TYPES.optionalComponentType);
        this.registerBlockStateHandler(EntityTypes1_19_4.ABSTRACT_MINECART, 11);
        this.filter().type(EntityTypes1_19_4.ITEM_DISPLAY).handler((event, data) -> {
            if (event.trackedEntity().hasSentEntityData() || event.hasExtraData()) {
                return;
            }
            if (event.dataAtIndex(12) == null) {
                event.createExtraData(new EntityData(12, Types1_19_4.ENTITY_DATA_TYPES.quaternionType, Y_FLIPPED_ROTATION));
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

