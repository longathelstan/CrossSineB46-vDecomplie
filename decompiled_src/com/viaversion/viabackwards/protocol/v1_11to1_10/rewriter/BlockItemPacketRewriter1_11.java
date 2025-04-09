/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_11to1_10.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.data.MappedLegacyBlockItem;
import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import com.viaversion.viabackwards.protocol.v1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viabackwards.protocol.v1_11to1_10.storage.ChestedHorseStorage;
import com.viaversion.viabackwards.protocol.v1_11to1_10.storage.WindowTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_11;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.protocols.v1_10to1_11.data.EntityMappings1_11;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.IdAndData;
import java.util.Arrays;
import java.util.Optional;

public class BlockItemPacketRewriter1_11
extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_11To1_10> {
    LegacyEnchantmentRewriter enchantmentRewriter;

    public BlockItemPacketRewriter1_11(Protocol1_11To1_10 protocol) {
        super(protocol, "1.11");
    }

    @Override
    protected void registerPackets() {
        this.registerBlockChange(ClientboundPackets1_9_3.BLOCK_UPDATE);
        this.registerMultiBlockChange(ClientboundPackets1_9_3.CHUNK_BLOCKS_UPDATE);
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.CONTAINER_SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.ITEM1_8);
                this.handler(wrapper -> BlockItemPacketRewriter1_11.this.handleItemToClient(wrapper.user(), wrapper.get(Types.ITEM1_8, 0)));
                this.handler(wrapper -> {
                    if (BlockItemPacketRewriter1_11.this.isLlama(wrapper.user())) {
                        Optional<ChestedHorseStorage> horse = BlockItemPacketRewriter1_11.this.getChestedHorse(wrapper.user());
                        if (horse.isPresent() ^ true) {
                            return;
                        }
                        ChestedHorseStorage storage = horse.get();
                        int currentSlot = wrapper.get(Types.SHORT, 0).shortValue();
                        currentSlot = BlockItemPacketRewriter1_11.this.getNewSlotId(storage, currentSlot);
                        wrapper.set(Types.SHORT, 0, Integer.valueOf(currentSlot).shortValue());
                        wrapper.set(Types.ITEM1_8, 0, BlockItemPacketRewriter1_11.this.getNewItem(storage, currentSlot, wrapper.get(Types.ITEM1_8, 0)));
                    }
                });
            }
        });
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.CONTAINER_SET_CONTENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.ITEM1_8_SHORT_ARRAY);
                this.handler(wrapper -> {
                    Item[] stacks = wrapper.get(Types.ITEM1_8_SHORT_ARRAY, 0);
                    for (int i = 0; i < stacks.length; ++i) {
                        stacks[i] = BlockItemPacketRewriter1_11.this.handleItemToClient(wrapper.user(), stacks[i]);
                    }
                    if (BlockItemPacketRewriter1_11.this.isLlama(wrapper.user())) {
                        Optional<ChestedHorseStorage> horse = BlockItemPacketRewriter1_11.this.getChestedHorse(wrapper.user());
                        if (horse.isPresent() ^ true) {
                            return;
                        }
                        ChestedHorseStorage storage = horse.get();
                        stacks = Arrays.copyOf(stacks, !storage.isChested() ? 38 : 53);
                        for (int i = stacks.length - 1; i >= 0; --i) {
                            stacks[BlockItemPacketRewriter1_11.this.getNewSlotId((ChestedHorseStorage)storage, (int)i)] = stacks[i];
                            stacks[i] = BlockItemPacketRewriter1_11.this.getNewItem(storage, i, stacks[i]);
                        }
                        wrapper.set(Types.ITEM1_8_SHORT_ARRAY, 0, stacks);
                    }
                });
            }
        });
        this.registerSetEquippedItem(ClientboundPackets1_9_3.SET_EQUIPPED_ITEM);
        this.registerCustomPayloadTradeList(ClientboundPackets1_9_3.CUSTOM_PAYLOAD);
        ((Protocol1_11To1_10)this.protocol).registerServerbound(ServerboundPackets1_9_3.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.VAR_INT);
                this.map(Types.ITEM1_8);
                this.handler(wrapper -> BlockItemPacketRewriter1_11.this.handleItemToServer(wrapper.user(), wrapper.get(Types.ITEM1_8, 0)));
                this.handler(wrapper -> {
                    if (BlockItemPacketRewriter1_11.this.isLlama(wrapper.user())) {
                        Optional<ChestedHorseStorage> horse = BlockItemPacketRewriter1_11.this.getChestedHorse(wrapper.user());
                        if (horse.isPresent() ^ true) {
                            return;
                        }
                        ChestedHorseStorage storage = horse.get();
                        short clickSlot = wrapper.get(Types.SHORT, 0);
                        int correctSlot = BlockItemPacketRewriter1_11.this.getOldSlotId(storage, clickSlot);
                        wrapper.set(Types.SHORT, 0, Integer.valueOf(correctSlot).shortValue());
                    }
                });
            }
        });
        this.registerSetCreativeModeSlot(ServerboundPackets1_9_3.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_11To1_10.class);
            ChunkType1_9_3 type = ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            Chunk chunk = wrapper.passthrough(type);
            this.handleChunk(chunk);
            for (CompoundTag tag : chunk.getBlockEntities()) {
                String id;
                StringTag idTag = tag.getStringTag("id");
                if (idTag == null || !(id = idTag.getValue()).equals("minecraft:sign")) continue;
                idTag.setValue("Sign");
            }
        });
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.UNSIGNED_BYTE, 0) == 10) {
                        wrapper.cancel();
                    }
                    if (wrapper.get(Types.UNSIGNED_BYTE, 0) == 1) {
                        CompoundTag tag = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                        EntityMappings1_11.toClientSpawner(tag, true);
                    }
                });
            }
        });
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.OPEN_SCREEN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.STRING);
                this.map(Types.COMPONENT);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    int entityId = -1;
                    if (wrapper.get(Types.STRING, 0).equals("EntityHorse")) {
                        entityId = wrapper.passthrough(Types.INT);
                    }
                    ((ComponentRewriter)((Protocol1_11To1_10)BlockItemPacketRewriter1_11.this.protocol).getComponentRewriter()).processText(wrapper.user(), wrapper.get(Types.COMPONENT, 0));
                    String inventory = wrapper.get(Types.STRING, 0);
                    WindowTracker windowTracker = wrapper.user().get(WindowTracker.class);
                    windowTracker.setInventory(inventory);
                    windowTracker.setEntityId(entityId);
                    if (BlockItemPacketRewriter1_11.this.isLlama(wrapper.user())) {
                        wrapper.set(Types.UNSIGNED_BYTE, 1, (short)17);
                    }
                });
            }
        });
        ((Protocol1_11To1_10)this.protocol).registerClientbound(ClientboundPackets1_9_3.CONTAINER_CLOSE, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    WindowTracker windowTracker = wrapper.user().get(WindowTracker.class);
                    windowTracker.setInventory(null);
                    windowTracker.setEntityId(-1);
                });
            }
        });
        ((Protocol1_11To1_10)this.protocol).registerServerbound(ServerboundPackets1_9_3.CONTAINER_CLOSE, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    WindowTracker windowTracker = wrapper.user().get(WindowTracker.class);
                    windowTracker.setInventory(null);
                    windowTracker.setEntityId(-1);
                });
            }
        });
        ((Protocol1_11To1_10)this.protocol).getEntityRewriter().filter().handler((event, data) -> {
            if (data.dataType().type().equals(Types.ITEM1_8)) {
                data.setValue(this.handleItemToClient(event.user(), (Item)data.getValue()));
            }
        });
    }

    @Override
    protected void registerRewrites() {
        MappedLegacyBlockItem data = this.itemReplacements.computeIfAbsent(IdAndData.toRawData(52), s -> new MappedLegacyBlockItem(52));
        data.setBlockEntityHandler((b, tag) -> EntityMappings1_11.toClientSpawner(tag, true));
        this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName());
        this.enchantmentRewriter.registerEnchantment(71, "\u00a7cCurse of Vanishing");
        this.enchantmentRewriter.registerEnchantment(10, "\u00a7cCurse of Binding");
        this.enchantmentRewriter.setHideLevelForEnchants(71, 10);
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(connection, item);
        CompoundTag tag = item.tag();
        if (tag == null) {
            return item;
        }
        EntityMappings1_11.toClientItem(item, true);
        this.enchantmentRewriter.handleToClient(item);
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(connection, item);
        CompoundTag tag = item.tag();
        if (tag == null) {
            return item;
        }
        EntityMappings1_11.toServerItem(item, true);
        this.enchantmentRewriter.handleToServer(item);
        return item;
    }

    boolean isLlama(UserConnection user) {
        WindowTracker tracker = user.get(WindowTracker.class);
        if (tracker.getInventory() != null && tracker.getInventory().equals("EntityHorse")) {
            Object entTracker = user.getEntityTracker(Protocol1_11To1_10.class);
            StoredEntityData entityData = entTracker.entityData(tracker.getEntityId());
            return entityData != null && entityData.type().is(EntityTypes1_11.EntityType.LLAMA);
        }
        return false;
    }

    Optional<ChestedHorseStorage> getChestedHorse(UserConnection user) {
        Object entTracker;
        StoredEntityData entityData;
        WindowTracker tracker = user.get(WindowTracker.class);
        if (tracker.getInventory() != null && tracker.getInventory().equals("EntityHorse") && (entityData = (entTracker = user.getEntityTracker(Protocol1_11To1_10.class)).entityData(tracker.getEntityId())) != null) {
            return Optional.of(entityData.get(ChestedHorseStorage.class));
        }
        return Optional.empty();
    }

    int getNewSlotId(ChestedHorseStorage storage, int slotId) {
        int totalSlots = !storage.isChested() ? 38 : 53;
        int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
        int startNonExistingFormula = 2 + 3 * strength;
        int offsetForm = 15 - 3 * strength;
        if (slotId >= startNonExistingFormula && totalSlots > slotId + offsetForm) {
            return offsetForm + slotId;
        }
        if (slotId == 1) {
            return 0;
        }
        return slotId;
    }

    int getOldSlotId(ChestedHorseStorage storage, int slotId) {
        int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
        int startNonExistingFormula = 2 + 3 * strength;
        int endNonExistingFormula = 2 + 3 * (storage.isChested() ? 5 : 0);
        int offsetForm = endNonExistingFormula - startNonExistingFormula;
        if (slotId == 1 || slotId >= startNonExistingFormula && slotId < endNonExistingFormula) {
            return 0;
        }
        if (slotId >= endNonExistingFormula) {
            return slotId - offsetForm;
        }
        if (slotId == 0) {
            return 1;
        }
        return slotId;
    }

    Item getNewItem(ChestedHorseStorage storage, int slotId, Item current) {
        int strength = storage.isChested() ? storage.getLiamaStrength() : 0;
        int startNonExistingFormula = 2 + 3 * strength;
        int endNonExistingFormula = 2 + 3 * (storage.isChested() ? 5 : 0);
        if (slotId >= startNonExistingFormula && slotId < endNonExistingFormula) {
            return new DataItem(166, 1, 0, this.getNamedTag("\u00a74SLOT DISABLED"));
        }
        if (slotId == 1) {
            return null;
        }
        return current;
    }
}

