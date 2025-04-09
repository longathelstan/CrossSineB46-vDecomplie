/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.IdAndData;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.data.AlphaItems;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.packet.ClientboundPacketsa1_2_6;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.packet.ServerboundPacketsa1_2_6;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider.AlphaInventoryProvider;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider.TrackingAlphaInventoryProvider;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.AlphaInventoryTracker;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.InventoryStorage;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.task.AlphaInventoryUpdateTask;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.packet.ClientboundPacketsb1_1;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.packet.ServerboundPacketsb1_1;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.types.Typesb1_1;
import net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.types.Typesb1_7_0_3;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.Protocolr1_1Tor1_2_1_3;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.packet.ClientboundPackets1_2_1;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.EntityList1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.PlayerInfoStorage;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocola1_2_3_5_1_2_6Tob1_0_1_1_1
extends StatelessProtocol<ClientboundPacketsa1_2_6, ClientboundPacketsb1_1, ServerboundPacketsa1_2_6, ServerboundPacketsb1_1> {
    public Protocola1_2_3_5_1_2_6Tob1_0_1_1_1() {
        super(ClientboundPacketsa1_2_6.class, ClientboundPacketsb1_1.class, ServerboundPacketsa1_2_6.class, ServerboundPacketsb1_1.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPacketsa1_2_6.PLAYER_INVENTORY, ClientboundPacketsb1_1.CONTAINER_SET_CONTENT, (PacketWrapper wrapper) -> {
            InventoryStorage inventoryStorage = wrapper.user().get(InventoryStorage.class);
            AlphaInventoryTracker inventoryTracker = wrapper.user().get(AlphaInventoryTracker.class);
            int type = wrapper.read(Types.INT);
            Item[] items = wrapper.read(Types1_4_2.NBTLESS_ITEM_ARRAY);
            Item[] windowItems = new Item[45];
            System.arraycopy(inventoryStorage.mainInventory, 0, windowItems, 36, 9);
            System.arraycopy(inventoryStorage.mainInventory, 9, windowItems, 9, 27);
            System.arraycopy(inventoryStorage.craftingInventory, 0, windowItems, 1, 4);
            System.arraycopy(inventoryStorage.armorInventory, 0, windowItems, 5, 4);
            switch (type) {
                case -1: {
                    inventoryStorage.mainInventory = items;
                    if (inventoryTracker != null) {
                        inventoryTracker.setMainInventory(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(items));
                    }
                    System.arraycopy(items, 0, windowItems, 36, 9);
                    System.arraycopy(items, 9, windowItems, 9, 27);
                    break;
                }
                case -2: {
                    inventoryStorage.craftingInventory = items;
                    if (inventoryTracker != null) {
                        inventoryTracker.setCraftingInventory(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(items));
                    }
                    System.arraycopy(items, 0, windowItems, 1, 4);
                    break;
                }
                case -3: {
                    inventoryStorage.armorInventory = items;
                    if (inventoryTracker != null) {
                        inventoryTracker.setArmorInventory(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(items));
                    }
                    System.arraycopy(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.reverseArray(items), 0, windowItems, 5, 4);
                }
            }
            wrapper.write(Types.BYTE, (byte)0);
            wrapper.write(Types1_4_2.NBTLESS_ITEM_ARRAY, Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(windowItems));
        });
        this.registerClientbound(ClientboundPacketsa1_2_6.SET_HEALTH, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.SHORT);
            }
        });
        this.registerClientbound(ClientboundPacketsa1_2_6.RESPAWN, wrapper -> {
            wrapper.user().get(InventoryStorage.class).resetPlayerInventory();
            AlphaInventoryTracker inventoryTracker = wrapper.user().get(AlphaInventoryTracker.class);
            if (inventoryTracker != null) {
                inventoryTracker.onRespawn();
            }
        });
        this.registerClientbound(ClientboundPacketsa1_2_6.SET_CARRIED_ITEM, ClientboundPacketsb1_1.SET_EQUIPPED_ITEM, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.create(Types.SHORT, (short)0);
                this.map(Types.SHORT);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.SHORT, 1) == 0) {
                        wrapper.set(Types.SHORT, 1, (short)-1);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPacketsa1_2_6.ADD_TO_INVENTORY, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            Item item = wrapper.read(Types1_3_1.NBTLESS_ITEM);
            Via.getManager().getProviders().get(AlphaInventoryProvider.class).addToInventory(wrapper.user(), item);
        });
        this.registerClientbound(ClientboundPacketsa1_2_6.PRE_CHUNK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> wrapper.user().get(InventoryStorage.class).unload(wrapper.get(Types.INT, 0), wrapper.get(Types.INT, 1)));
            }
        });
        this.registerClientbound(ClientboundPacketsa1_2_6.BLOCK_ENTITY_DATA, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            InventoryStorage tracker = wrapper.user().get(InventoryStorage.class);
            BlockPosition pos = wrapper.read(Types1_7_6.BLOCK_POSITION_SHORT);
            CompoundTag tag = wrapper.read(Types1_7_6.NBT);
            if (tag.getInt("x") != pos.x() || tag.getInt("y") != pos.y() || tag.getInt("z") != pos.z()) {
                return;
            }
            IdAndData block = wrapper.user().get(ChunkTracker.class).getBlockNotNull(pos);
            String blockName = tag.getString("id", "");
            if (block.getId() == BlockList1_6.signPost.blockId() || block.getId() == BlockList1_6.signWall.blockId() || blockName.equals("Sign")) {
                PacketWrapper updateSign = PacketWrapper.create(ClientboundPacketsb1_1.UPDATE_SIGN, wrapper.user());
                updateSign.write(Types1_7_6.BLOCK_POSITION_SHORT, pos);
                updateSign.write(Typesb1_7_0_3.STRING, tag.getString("Text1", ""));
                updateSign.write(Typesb1_7_0_3.STRING, tag.getString("Text2", ""));
                updateSign.write(Typesb1_7_0_3.STRING, tag.getString("Text3", ""));
                updateSign.write(Typesb1_7_0_3.STRING, tag.getString("Text4", ""));
                updateSign.send(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
            } else if (block.getId() == BlockList1_6.mobSpawner.blockId() || blockName.equals("MobSpawner")) {
                if (wrapper.user().getProtocolInfo().getPipeline().contains(Protocolr1_1Tor1_2_1_3.class)) {
                    PacketWrapper spawnerData = PacketWrapper.create(ClientboundPackets1_2_1.BLOCK_ENTITY_DATA, wrapper.user());
                    spawnerData.write(Types1_7_6.BLOCK_POSITION_SHORT, pos);
                    spawnerData.write(Types.BYTE, (byte)1);
                    spawnerData.write(Types.INT, EntityList1_2_4.getEntityId(tag.getString("EntityId")));
                    spawnerData.write(Types.INT, 0);
                    spawnerData.write(Types.INT, 0);
                    spawnerData.send(Protocolr1_1Tor1_2_1_3.class);
                }
            } else if (block.getId() == BlockList1_6.chest.blockId() || blockName.equals("Chest")) {
                Item[] chestItems = new Item[27];
                this.readItemsFromTag(tag, chestItems);
                tracker.containers.put(pos, chestItems);
                if (pos.equals(tracker.openContainerPos)) {
                    this.sendWindowItems(wrapper.user(), (byte)55, chestItems);
                }
            } else if (block.getId() == BlockList1_6.furnaceIdle.blockId() || block.getId() == BlockList1_6.furnaceBurning.blockId() || blockName.equals("Furnace")) {
                Item[] furnaceItems = new Item[3];
                this.readItemsFromTag(tag, furnaceItems);
                tracker.containers.put(pos, furnaceItems);
                if (pos.equals(tracker.openContainerPos)) {
                    this.sendWindowItems(wrapper.user(), (byte)44, furnaceItems);
                    this.sendProgressUpdate(wrapper.user(), (short)44, (short)0, tag.getShort("CookTime"));
                    this.sendProgressUpdate(wrapper.user(), (short)44, (short)1, tag.getShort("BurnTime"));
                    this.sendProgressUpdate(wrapper.user(), (short)44, (short)2, this.getBurningTime(furnaceItems[1]));
                }
            } else {
                CompoundTag compoundTag = tag;
                BlockPosition blockPosition = pos;
                IdAndData idAndData = block;
                ViaLegacy.getPlatform().getLogger().warning("Unhandled Complex Entity data: " + idAndData + "@" + blockPosition + ": '" + compoundTag + "'");
            }
        });
        this.registerServerbound(ServerboundPacketsb1_1.PLAYER_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    short status = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    if (status == 4) {
                        wrapper.cancel();
                        Item selectedItem = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItem(Via.getManager().getProviders().get(AlphaInventoryProvider.class).getHandItem(wrapper.user()));
                        if (selectedItem == null) {
                            return;
                        }
                        AlphaInventoryTracker inventoryTracker = wrapper.user().get(AlphaInventoryTracker.class);
                        if (inventoryTracker != null) {
                            inventoryTracker.onHandItemDrop();
                        }
                        selectedItem.setAmount(1);
                        Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.dropItem(wrapper.user(), selectedItem, false);
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPacketsb1_1.USE_ITEM_ON, wrapper -> {
            InventoryStorage tracker = wrapper.user().get(InventoryStorage.class);
            AlphaInventoryTracker inventoryTracker = wrapper.user().get(AlphaInventoryTracker.class);
            BlockPosition pos = wrapper.read(Types1_7_6.BLOCK_POSITION_UBYTE);
            short direction = wrapper.read(Types.UNSIGNED_BYTE);
            Item item = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItem(wrapper.read(Typesb1_1.NBTLESS_ITEM));
            if (item == null && inventoryTracker != null) {
                item = Via.getManager().getProviders().get(AlphaInventoryProvider.class).getHandItem(wrapper.user());
            }
            wrapper.write(Types.SHORT, item == null ? (short)-1 : (short)item.identifier());
            wrapper.write(Types1_7_6.BLOCK_POSITION_UBYTE, pos);
            wrapper.write(Types.UNSIGNED_BYTE, direction);
            if (inventoryTracker != null) {
                inventoryTracker.onBlockPlace(pos, direction);
            }
            if (direction == 255) {
                return;
            }
            IdAndData block = wrapper.user().get(ChunkTracker.class).getBlockNotNull(pos);
            if (block.getId() != BlockList1_6.furnaceIdle.blockId() && block.getId() != BlockList1_6.furnaceBurning.blockId() && block.getId() != BlockList1_6.chest.blockId() && block.getId() != BlockList1_6.workbench.blockId()) {
                return;
            }
            tracker.openContainerPos = pos;
            Item[] containerItems = tracker.containers.get(tracker.openContainerPos);
            if (containerItems == null && block.getId() != BlockList1_6.workbench.blockId()) {
                tracker.openContainerPos = null;
                PacketWrapper chatMessage = PacketWrapper.create(ClientboundPacketsb1_1.CHAT, wrapper.user());
                chatMessage.write(Typesb1_7_0_3.STRING, "\u00a7cMissing Container");
                chatMessage.send(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
                return;
            }
            PacketWrapper openWindow = PacketWrapper.create(ClientboundPacketsb1_1.OPEN_SCREEN, wrapper.user());
            if (block.getId() == BlockList1_6.chest.blockId()) {
                openWindow.write(Types.UNSIGNED_BYTE, (short)55);
                openWindow.write(Types.UNSIGNED_BYTE, (short)0);
                openWindow.write(Typesb1_7_0_3.STRING, "Chest");
                openWindow.write(Types.UNSIGNED_BYTE, (short)27);
                if (inventoryTracker != null) {
                    inventoryTracker.onWindowOpen(0, 27);
                }
            } else if (block.getId() == BlockList1_6.workbench.blockId()) {
                openWindow.write(Types.UNSIGNED_BYTE, (short)33);
                openWindow.write(Types.UNSIGNED_BYTE, (short)1);
                openWindow.write(Typesb1_7_0_3.STRING, "Crafting Table");
                openWindow.write(Types.UNSIGNED_BYTE, (short)9);
                if (inventoryTracker != null) {
                    inventoryTracker.onWindowOpen(1, 10);
                }
            } else {
                openWindow.write(Types.UNSIGNED_BYTE, (short)44);
                openWindow.write(Types.UNSIGNED_BYTE, (short)2);
                openWindow.write(Typesb1_7_0_3.STRING, "Furnace");
                openWindow.write(Types.UNSIGNED_BYTE, (short)3);
                if (inventoryTracker != null) {
                    inventoryTracker.onWindowOpen(2, 3);
                }
            }
            openWindow.send(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
            if (block.getId() != BlockList1_6.workbench.blockId()) {
                this.sendWindowItems(wrapper.user(), block.getId() == BlockList1_6.chest.blockId() ? (byte)55 : (byte)44, containerItems);
            }
        });
        this.registerServerbound(ServerboundPacketsb1_1.SET_CARRIED_ITEM, wrapper -> {
            InventoryStorage inventoryStorage = wrapper.user().get(InventoryStorage.class);
            short slot = wrapper.read(Types.SHORT);
            if (slot < 0 || slot > 8) {
                slot = 0;
            }
            inventoryStorage.selectedHotbarSlot = slot;
            Item selectedItem = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItem(Via.getManager().getProviders().get(AlphaInventoryProvider.class).getHandItem(wrapper.user()));
            if (Objects.equals(selectedItem, inventoryStorage.handItem)) {
                wrapper.cancel();
                return;
            }
            inventoryStorage.handItem = selectedItem;
            wrapper.write(Types.INT, 0);
            wrapper.write(Types.SHORT, (short)(selectedItem == null ? 0 : selectedItem.identifier()));
        });
        this.registerServerbound(ServerboundPacketsb1_1.CONTAINER_CLOSE, null, (PacketWrapper wrapper) -> {
            wrapper.cancel();
            wrapper.user().get(InventoryStorage.class).openContainerPos = null;
            AlphaInventoryTracker inventoryTracker = wrapper.user().get(AlphaInventoryTracker.class);
            if (inventoryTracker != null) {
                inventoryTracker.onWindowClose();
            }
        });
        this.registerServerbound(ServerboundPacketsb1_1.CONTAINER_CLICK, ServerboundPacketsa1_2_6.BLOCK_ENTITY_DATA, (PacketWrapper wrapper) -> {
            InventoryStorage tracker = wrapper.user().get(InventoryStorage.class);
            AlphaInventoryTracker inventoryTracker = wrapper.user().get(AlphaInventoryTracker.class);
            byte windowId = wrapper.read(Types.BYTE);
            short slot = wrapper.read(Types.SHORT);
            byte button = wrapper.read(Types.BYTE);
            short action = wrapper.read(Types.SHORT);
            Item item = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItem(wrapper.read(Typesb1_1.NBTLESS_ITEM));
            if (inventoryTracker != null) {
                inventoryTracker.onWindowClick(windowId, slot, button, action, item);
            }
            if (windowId != 55 && windowId != 44 || tracker.openContainerPos == null) {
                wrapper.cancel();
                return;
            }
            Object[] containerItems = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItems(Via.getManager().getProviders().get(AlphaInventoryProvider.class).getContainerItems(wrapper.user()));
            if (Arrays.equals(tracker.containers.get(tracker.openContainerPos), containerItems)) {
                wrapper.cancel();
                return;
            }
            tracker.containers.put(tracker.openContainerPos, (Item[])containerItems);
            CompoundTag tag = new CompoundTag();
            tag.putString("id", windowId == 55 ? "Chest" : "Furnace");
            tag.putInt("x", tracker.openContainerPos.x());
            tag.putInt("y", tracker.openContainerPos.y());
            tag.putInt("z", tracker.openContainerPos.z());
            this.writeItemsToTag(tag, (Item[])containerItems);
            wrapper.write(Types.INT, tracker.openContainerPos.x());
            wrapper.write(Types.SHORT, (short)tracker.openContainerPos.y());
            wrapper.write(Types.INT, tracker.openContainerPos.z());
            wrapper.write(Types1_7_6.NBT, tag);
        });
        this.registerServerbound(ServerboundPacketsb1_1.SIGN_UPDATE, ServerboundPacketsa1_2_6.BLOCK_ENTITY_DATA, (PacketWrapper wrapper) -> {
            BlockPosition pos = wrapper.passthrough(Types1_7_6.BLOCK_POSITION_SHORT);
            CompoundTag tag = new CompoundTag();
            tag.putString("id", "Sign");
            tag.putInt("x", pos.x());
            tag.putInt("y", pos.y());
            tag.putInt("z", pos.z());
            tag.putString("Text1", wrapper.read(Typesb1_7_0_3.STRING));
            tag.putString("Text2", wrapper.read(Typesb1_7_0_3.STRING));
            tag.putString("Text3", wrapper.read(Typesb1_7_0_3.STRING));
            tag.putString("Text4", wrapper.read(Typesb1_7_0_3.STRING));
            wrapper.write(Types1_7_6.NBT, tag);
        });
        this.cancelServerbound(ServerboundPacketsb1_1.CONTAINER_ACK);
    }

    void writeItemsToTag(CompoundTag tag, Item[] items) {
        ListTag<CompoundTag> itemList = new ListTag<CompoundTag>(CompoundTag.class);
        for (int i = 0; i < items.length; ++i) {
            Item item = items[i];
            if (item == null) continue;
            CompoundTag itemTag = new CompoundTag();
            itemTag.putByte("Slot", (byte)i);
            itemTag.putShort("id", (short)item.identifier());
            itemTag.putByte("Count", (byte)item.amount());
            itemTag.putShort("Damage", item.data());
            itemList.add(itemTag);
        }
        tag.put("Items", itemList);
    }

    void readItemsFromTag(CompoundTag tag, Item[] items) {
        ListTag<CompoundTag> itemList = tag.getListTag("Items", CompoundTag.class);
        if (itemList != null) {
            for (CompoundTag itemTag : itemList) {
                items[itemTag.getByte((String)"Slot") & 0xFF] = new DataItem(itemTag.getShort("id"), itemTag.getByte("Count"), itemTag.getShort("Damage"), null);
            }
        }
    }

    void sendWindowItems(UserConnection user, byte windowId, Item[] items) {
        PacketWrapper windowItems = PacketWrapper.create(ClientboundPacketsb1_1.CONTAINER_SET_CONTENT, user);
        windowItems.write(Types.BYTE, windowId);
        windowItems.write(Types1_4_2.NBTLESS_ITEM_ARRAY, Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(items));
        windowItems.send(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
        AlphaInventoryTracker inventoryTracker = user.get(AlphaInventoryTracker.class);
        if (inventoryTracker != null) {
            inventoryTracker.setOpenContainerItems(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(items));
        }
    }

    void sendProgressUpdate(UserConnection user, short windowId, short id, short value) {
        PacketWrapper windowProperty = PacketWrapper.create(ClientboundPacketsb1_1.CONTAINER_SET_DATA, user);
        windowProperty.write(Types.UNSIGNED_BYTE, windowId);
        windowProperty.write(Types.SHORT, id);
        windowProperty.write(Types.SHORT, value);
        windowProperty.send(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
    }

    short getBurningTime(Item item) {
        if (item == null) {
            return 0;
        }
        int id = item.identifier();
        if (id == BlockList1_6.bookShelf.blockId() || id == BlockList1_6.chest.blockId() || id == BlockList1_6.fence.blockId() || id == BlockList1_6.jukebox.blockId() || id == BlockList1_6.wood.blockId() || id == BlockList1_6.planks.blockId() || id == BlockList1_6.doorWood.blockId() || id == BlockList1_6.signWall.blockId() || id == BlockList1_6.signPost.blockId() || id == BlockList1_6.workbench.blockId()) {
            return 300;
        }
        if (id == ItemList1_6.stick.itemId()) {
            return 100;
        }
        if (id == ItemList1_6.coal.itemId()) {
            return 1600;
        }
        if (id == ItemList1_6.bucketLava.itemId()) {
            return 20000;
        }
        return 0;
    }

    public static void dropItem(UserConnection user, Item item, boolean flag) {
        double motionY;
        double motionZ;
        double motionX;
        PlayerInfoStorage playerInfoStorage = user.get(PlayerInfoStorage.class);
        double itemX = playerInfoStorage.posX;
        double itemY = playerInfoStorage.posY + (double)1.62f - (double)0.3f + 0.12;
        double itemZ = playerInfoStorage.posZ;
        if (flag) {
            float f2 = ThreadLocalRandom.current().nextFloat() * 0.5f;
            float f1 = (float)((double)ThreadLocalRandom.current().nextFloat() * Math.PI * 2.0);
            motionX = -Math.sin(f1) * (double)f2;
            motionZ = Math.cos(f1) * (double)f2;
            motionY = 0.2f;
        } else {
            motionX = -Math.sin((double)(playerInfoStorage.yaw / 180.0f) * Math.PI) * Math.cos((double)(playerInfoStorage.pitch / 180.0f) * Math.PI) * (double)0.3f;
            motionZ = Math.cos((double)(playerInfoStorage.yaw / 180.0f) * Math.PI) * Math.cos((double)(playerInfoStorage.pitch / 180.0f) * Math.PI) * (double)0.3f;
            motionY = -Math.sin((double)(playerInfoStorage.pitch / 180.0f) * Math.PI) * (double)0.3f + (double)0.1f;
            float f1 = (float)((double)ThreadLocalRandom.current().nextFloat() * Math.PI * 2.0);
            float f2 = 0.02f * ThreadLocalRandom.current().nextFloat();
            motionX += Math.cos(f1) * (double)f2;
            motionY += (double)((ThreadLocalRandom.current().nextFloat() - ThreadLocalRandom.current().nextFloat()) * 0.1f);
            motionZ += Math.sin(f1) * (double)f2;
        }
        PacketWrapper spawnItem = PacketWrapper.create(ServerboundPacketsa1_2_6.SPAWN_ITEM, user);
        spawnItem.write(Types.INT, 0);
        spawnItem.write(Types.SHORT, (short)item.identifier());
        spawnItem.write(Types.BYTE, (byte)item.amount());
        spawnItem.write(Types.INT, (int)(itemX * 32.0));
        spawnItem.write(Types.INT, (int)(itemY * 32.0));
        spawnItem.write(Types.INT, (int)(itemZ * 32.0));
        spawnItem.write(Types.BYTE, (byte)(motionX * 128.0));
        spawnItem.write(Types.BYTE, (byte)(motionY * 128.0));
        spawnItem.write(Types.BYTE, (byte)(motionZ * 128.0));
        spawnItem.sendToServer(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
    }

    public static Item[] reverseArray(Item[] array) {
        if (array == null) {
            return null;
        }
        Item[] reversed = new Item[array.length];
        for (int i = 0; i < array.length / 2; ++i) {
            reversed[i] = array[array.length - i - 1];
            reversed[array.length - i - 1] = array[i];
        }
        return reversed;
    }

    public static Item copyItem(Item item) {
        return item == null ? null : item.copy();
    }

    public static Item[] copyItems(Item[] items) {
        return (Item[])Arrays.stream(items).map(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1::copyItem).toArray(Item[]::new);
    }

    public static Item fixItem(Item item) {
        if (item == null || !AlphaItems.isValid(item.identifier())) {
            return null;
        }
        item.setTag(null);
        return item;
    }

    public static Item[] fixItems(Item[] items) {
        for (int i = 0; i < items.length; ++i) {
            items[i] = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItem(items[i]);
        }
        return items;
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(AlphaInventoryProvider.class, new TrackingAlphaInventoryProvider());
        Via.getPlatform().runRepeatingSync(new AlphaInventoryUpdateTask(), 20L);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class, ClientboundPacketsa1_2_6::getPacket));
        userConnection.put(new InventoryStorage());
        if (Via.getManager().getProviders().get(AlphaInventoryProvider.class).usesInventoryTracker()) {
            userConnection.put(new AlphaInventoryTracker(userConnection));
        }
    }
}

