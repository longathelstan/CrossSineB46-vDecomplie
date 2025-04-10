/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_5to1_21.rewriter;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.AttributeModifiers1_20_5;
import com.viaversion.viaversion.api.minecraft.item.data.AttributeModifiers1_21;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter.RecipeRewriter1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.data.AttributeModifierMappings1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.storage.OnGroundTracker;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.StructuredItemRewriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class BlockItemPacketRewriter1_21
extends StructuredItemRewriter<ClientboundPacket1_20_5, ServerboundPacket1_20_5, Protocol1_20_5To1_21> {
    private static final List<String> DISCS;

    public BlockItemPacketRewriter1_21(Protocol1_20_5To1_21 protocol) {
        super(protocol, Types1_20_5.ITEM, Types1_20_5.ITEM_ARRAY, Types1_21.ITEM, Types1_21.ITEM_ARRAY, Types1_20_5.ITEM_COST, Types1_20_5.OPTIONAL_ITEM_COST, Types1_21.ITEM_COST, Types1_21.OPTIONAL_ITEM_COST, Types1_20_5.PARTICLE, Types1_21.PARTICLE);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_20_5> blockRewriter = BlockRewriter.for1_20_2(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_20_5.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_20_5.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_20_5.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_20_5.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_20_2::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_20_5.BLOCK_ENTITY_DATA);
        this.registerCooldown(ClientboundPackets1_20_5.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_20_5.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_20_5.CONTAINER_SET_SLOT);
        this.registerAdvancements1_20_3(ClientboundPackets1_20_5.UPDATE_ADVANCEMENTS);
        this.registerSetEquipment(ClientboundPackets1_20_5.SET_EQUIPMENT);
        this.registerContainerClick1_17_1(ServerboundPackets1_20_5.CONTAINER_CLICK);
        this.registerMerchantOffers1_20_5(ClientboundPackets1_20_5.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_20_5.SET_CREATIVE_MODE_SLOT);
        this.registerLevelParticles1_20_5(ClientboundPackets1_20_5.LEVEL_PARTICLES);
        this.registerExplosion(ClientboundPackets1_20_5.EXPLODE);
        ((Protocol1_20_5To1_21)this.protocol).registerClientbound(ClientboundPackets1_20_5.HORSE_SCREEN_OPEN, wrapper -> {
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            int size = wrapper.read(Types.VAR_INT);
            wrapper.write(Types.VAR_INT, Math.max(0, (size - 1) / 3));
        });
        ((Protocol1_20_5To1_21)this.protocol).registerClientbound(ClientboundPackets1_20_5.LEVEL_EVENT, wrapper -> {
            int id = wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            int data = wrapper.read(Types.INT);
            if (id == 1010) {
                int jukeboxSong = this.itemToJubeboxSong(data);
                if (jukeboxSong == -1) {
                    wrapper.cancel();
                    return;
                }
                wrapper.write(Types.INT, jukeboxSong);
            } else if (id == 2001) {
                wrapper.write(Types.INT, ((Protocol1_20_5To1_21)this.protocol).getMappingData().getNewBlockStateId(data));
            } else {
                wrapper.write(Types.INT, data);
            }
        });
        ((Protocol1_20_5To1_21)this.protocol).registerServerbound(ServerboundPackets1_20_5.USE_ITEM, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            float yaw = wrapper.read(Types.FLOAT).floatValue();
            float pitch = wrapper.read(Types.FLOAT).floatValue();
            if (!Via.getConfig().fix1_21PlacementRotation()) {
                return;
            }
            OnGroundTracker tracker = wrapper.user().get(OnGroundTracker.class);
            PacketWrapper playerRotation = wrapper.create(ServerboundPackets1_20_5.MOVE_PLAYER_ROT);
            playerRotation.write(Types.FLOAT, Float.valueOf(yaw));
            playerRotation.write(Types.FLOAT, Float.valueOf(pitch));
            playerRotation.write(Types.BOOLEAN, tracker.onGround());
            playerRotation.sendToServer(Protocol1_20_5To1_21.class);
            wrapper.sendToServer(Protocol1_20_5To1_21.class);
            wrapper.cancel();
        });
        new RecipeRewriter1_20_3<ClientboundPackets1_20_5>(this.protocol).register1_20_5(ClientboundPackets1_20_5.UPDATE_RECIPES);
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        if (item.isEmpty()) {
            return item;
        }
        super.handleItemToClient(connection, item);
        BlockItemPacketRewriter1_21.updateItemData(item);
        StructuredDataContainer dataContainer = item.dataContainer();
        if (dataContainer.has(StructuredDataKey.RARITY)) {
            return item;
        }
        if (item.identifier() == 1188 || item.identifier() == 1200) {
            dataContainer.set(StructuredDataKey.RARITY, 0);
            this.saveTag(this.createCustomTag(item), new ByteTag(true), "rarity");
        }
        return item;
    }

    public static void updateItemData(Item item) {
        StructuredDataContainer dataContainer = item.dataContainer();
        dataContainer.replaceKey(StructuredDataKey.FOOD1_20_5, StructuredDataKey.FOOD1_21);
        dataContainer.replaceKey(StructuredDataKey.CONTAINER1_20_5, StructuredDataKey.CONTAINER1_21);
        dataContainer.replaceKey(StructuredDataKey.CHARGED_PROJECTILES1_20_5, StructuredDataKey.CHARGED_PROJECTILES1_21);
        dataContainer.replaceKey(StructuredDataKey.BUNDLE_CONTENTS1_20_5, StructuredDataKey.BUNDLE_CONTENTS1_21);
        dataContainer.replace(StructuredDataKey.ATTRIBUTE_MODIFIERS1_20_5, StructuredDataKey.ATTRIBUTE_MODIFIERS1_21, attributeModifiers -> {
            AttributeModifiers1_21.AttributeModifier[] modifiers = (AttributeModifiers1_21.AttributeModifier[])Arrays.stream(attributeModifiers.modifiers()).map(modifier -> {
                int mappedAttributeId = Protocol1_20_5To1_21.MAPPINGS.getNewAttributeId(modifier.attribute());
                AttributeModifiers1_20_5.ModifierData modData = modifier.modifier();
                AttributeModifiers1_21.ModifierData updatedModData = new AttributeModifiers1_21.ModifierData(Protocol1_20_5To1_21.mapAttributeUUID(modData.uuid(), modData.name()), modData.amount(), modData.operation());
                return new AttributeModifiers1_21.AttributeModifier(mappedAttributeId, updatedModData, modifier.slotType());
            }).toArray(AttributeModifiers1_21.AttributeModifier[]::new);
            return new AttributeModifiers1_21(modifiers, attributeModifiers.showInTooltip());
        });
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        if (item.isEmpty()) {
            return item;
        }
        super.handleItemToServer(connection, item);
        BlockItemPacketRewriter1_21.downgradeItemData(item);
        BlockItemPacketRewriter1_21.resetRarityValues(item, this.nbtTagName("rarity"));
        return item;
    }

    public static void downgradeItemData(Item item) {
        StructuredDataContainer dataContainer = item.dataContainer();
        dataContainer.replaceKey(StructuredDataKey.FOOD1_21, StructuredDataKey.FOOD1_20_5);
        dataContainer.replaceKey(StructuredDataKey.CONTAINER1_21, StructuredDataKey.CONTAINER1_20_5);
        dataContainer.replaceKey(StructuredDataKey.CHARGED_PROJECTILES1_21, StructuredDataKey.CHARGED_PROJECTILES1_20_5);
        dataContainer.replaceKey(StructuredDataKey.BUNDLE_CONTENTS1_21, StructuredDataKey.BUNDLE_CONTENTS1_20_5);
        dataContainer.remove(StructuredDataKey.JUKEBOX_PLAYABLE);
        dataContainer.replace(StructuredDataKey.ATTRIBUTE_MODIFIERS1_21, StructuredDataKey.ATTRIBUTE_MODIFIERS1_20_5, attributeModifiers -> {
            AttributeModifiers1_20_5.AttributeModifier[] modifiers = (AttributeModifiers1_20_5.AttributeModifier[])Arrays.stream(attributeModifiers.modifiers()).map(modifier -> {
                int mappedAttributeId = Protocol1_20_5To1_21.MAPPINGS.getAttributeMappings().inverse().getNewId(modifier.attribute());
                if (mappedAttributeId == -1) {
                    return null;
                }
                AttributeModifiers1_21.ModifierData modData = modifier.modifier();
                String name = AttributeModifierMappings1_21.idToName(modData.id());
                AttributeModifiers1_20_5.ModifierData updatedModData = new AttributeModifiers1_20_5.ModifierData(Protocol1_20_5To1_21.mapAttributeId(modData.id()), name != null ? name : modData.id(), modData.amount(), modData.operation());
                return new AttributeModifiers1_20_5.AttributeModifier(mappedAttributeId, updatedModData, modifier.slotType());
            }).filter(Objects::nonNull).toArray(AttributeModifiers1_20_5.AttributeModifier[]::new);
            return new AttributeModifiers1_20_5(modifiers, attributeModifiers.showInTooltip());
        });
    }

    public static void resetRarityValues(Item item, String tagName) {
        StructuredDataContainer dataContainer = item.dataContainer();
        CompoundTag customData = dataContainer.get(StructuredDataKey.CUSTOM_DATA);
        if (customData == null) {
            return;
        }
        if (customData.remove(tagName) != null) {
            dataContainer.remove(StructuredDataKey.RARITY);
            if (customData.isEmpty()) {
                dataContainer.remove(StructuredDataKey.CUSTOM_DATA);
            }
        }
    }

    private int itemToJubeboxSong(int id) {
        String identifier = Protocol1_20_5To1_21.MAPPINGS.getFullItemMappings().identifier(id);
        if (!identifier.contains("music_disc_")) {
            return -1;
        }
        identifier = identifier.substring("minecraft:music_disc_".length());
        return DISCS.indexOf(identifier);
    }

    static {
        String[] stringArray = new String[]{"11", "13", "5", "blocks", "cat", "chirp", "far", "mall", "mellohi", "otherside", "pigstep", "relic", "stal", "strad", "wait", "ward"};
        String[] stringArray2 = stringArray;
        Object[] objectArray = new Object[stringArray.length];
        int n = 0;
        while (n < stringArray2.length) {
            stringArray2 = stringArray2;
            objectArray[n] = Objects.requireNonNull(stringArray2[n]);
            n = n + 1;
        }
        DISCS = Collections.unmodifiableList(Arrays.asList(objectArray));
    }
}

