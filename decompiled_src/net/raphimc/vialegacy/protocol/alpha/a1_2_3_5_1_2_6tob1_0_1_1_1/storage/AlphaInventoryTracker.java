/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.IdAndData;
import java.util.Arrays;
import net.raphimc.vialegacy.api.LegacyProtocolVersion;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.api.util.BlockFaceUtil;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.Protocola1_2_3_5_1_2_6Tob1_0_1_1_1;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.data.AlphaItems;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.data.CraftingManager;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.InventoryStorage;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.packet.ClientboundPacketsb1_1;
import net.raphimc.vialegacy.protocol.beta.b1_1_2tob1_2_0_2.types.Typesb1_1;
import net.raphimc.vialegacy.protocol.release.r1_4_2tor1_4_4_5.types.Types1_4_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;

public class AlphaInventoryTracker
extends StoredObject {
    private boolean creativeMode;
    private Item[] mainInventory = null;
    private Item[] craftingInventory = null;
    private Item[] armorInventory = null;
    private Item[] openContainerItems = null;
    private Item cursorItem = null;
    private int openWindowType = -1;
    final InventoryStorage inventoryStorage;

    public AlphaInventoryTracker(UserConnection user) {
        super(user);
        this.inventoryStorage = user.get(InventoryStorage.class);
        this.onRespawn();
    }

    public void onWindowOpen(int windowType, int containerSlots) {
        this.openWindowType = windowType;
        this.openContainerItems = new Item[containerSlots];
    }

    public void onWindowClose() {
        Item item;
        int i;
        if (this.openWindowType == 1) {
            for (i = 1; i <= 9; ++i) {
                item = this.openContainerItems[i];
                if (item == null) continue;
                Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.dropItem(this.user(), item, false);
                this.openContainerItems[i] = null;
            }
        }
        for (i = 0; i < 4; ++i) {
            item = this.craftingInventory[i];
            if (item == null) continue;
            Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.dropItem(this.user(), item, false);
            this.craftingInventory[i] = null;
        }
        if (this.cursorItem != null) {
            Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.dropItem(this.user(), this.cursorItem, false);
            this.cursorItem = null;
        }
        this.openWindowType = -1;
        this.updatePlayerInventory();
        this.updateCursorItem();
    }

    public void onWindowClick(byte windowId, short slot, byte button, short action, Item item) {
        boolean leftClick;
        boolean bl = leftClick = button != 1;
        if (slot == -999) {
            if (this.cursorItem != null) {
                if (leftClick) {
                    Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.dropItem(this.user(), this.cursorItem, false);
                    this.cursorItem = null;
                } else {
                    Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.dropItem(this.user(), this.splitStack(this.cursorItem, 1), false);
                }
            }
        } else {
            Item[] slots = null;
            boolean slotTakesItems = true;
            int slotStackLimit = 64;
            boolean isCraftingResultSlot = false;
            switch (windowId) {
                case 0: {
                    slots = new Item[45];
                    System.arraycopy(this.mainInventory, 0, slots, 36, 9);
                    System.arraycopy(this.mainInventory, 9, slots, 9, 27);
                    System.arraycopy(this.craftingInventory, 0, slots, 1, 4);
                    System.arraycopy(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.reverseArray(this.armorInventory), 0, slots, 5, 4);
                    isCraftingResultSlot = slot == 0;
                    slotTakesItems = !isCraftingResultSlot;
                    int n = slotStackLimit = slot >= 5 && slot <= 8 ? 1 : 64;
                    if (!isCraftingResultSlot) break;
                    slots[0] = CraftingManager.getResult(this.craftingInventory);
                    break;
                }
                case 33: {
                    slots = new Item[46];
                    System.arraycopy(this.openContainerItems, 0, slots, 0, 10);
                    System.arraycopy(this.mainInventory, 0, slots, 37, 9);
                    System.arraycopy(this.mainInventory, 9, slots, 10, 27);
                    isCraftingResultSlot = slot == 0;
                    boolean bl2 = slotTakesItems = !isCraftingResultSlot;
                    if (!isCraftingResultSlot) break;
                    Item[] craftingGrid = Arrays.copyOfRange(this.openContainerItems, 1, 10);
                    slots[0] = CraftingManager.getResult(craftingGrid);
                    break;
                }
                case 44: {
                    slots = new Item[39];
                    System.arraycopy(this.openContainerItems, 0, slots, 0, 3);
                    System.arraycopy(this.mainInventory, 0, slots, 30, 9);
                    System.arraycopy(this.mainInventory, 9, slots, 3, 27);
                    break;
                }
                case 55: {
                    slots = new Item[63];
                    System.arraycopy(this.openContainerItems, 0, slots, 0, 27);
                    System.arraycopy(this.mainInventory, 0, slots, 54, 9);
                    System.arraycopy(this.mainInventory, 9, slots, 27, 27);
                }
            }
            if (slots != null) {
                int amount;
                Item itm = slots[slot];
                if (itm == null) {
                    if (this.cursorItem != null && slotTakesItems) {
                        int n = amount = leftClick ? this.cursorItem.amount() : 1;
                        if (amount > slotStackLimit) {
                            amount = slotStackLimit;
                        }
                        slots[slot] = this.splitStack(this.cursorItem, amount);
                    }
                } else if (this.cursorItem == null) {
                    int n = amount = leftClick ? itm.amount() : (itm.amount() + 1) / 2;
                    if (isCraftingResultSlot) {
                        amount = itm.amount();
                    }
                    this.cursorItem = this.splitStack(itm, amount);
                    if (isCraftingResultSlot) {
                        this.onCraftingResultPickup(windowId, slots);
                    }
                } else if (slotTakesItems) {
                    if (itm.identifier() != this.cursorItem.identifier()) {
                        if (this.cursorItem.amount() <= slotStackLimit) {
                            slots[slot] = this.cursorItem;
                            this.cursorItem = itm;
                        }
                    } else {
                        int n = amount = leftClick ? this.cursorItem.amount() : 1;
                        if (amount > slotStackLimit - itm.amount()) {
                            amount = slotStackLimit - itm.amount();
                        }
                        if (amount > AlphaItems.getMaxStackSize(this.cursorItem.identifier()) - itm.amount()) {
                            amount = AlphaItems.getMaxStackSize(this.cursorItem.identifier()) - itm.amount();
                        }
                        this.cursorItem.setAmount(this.cursorItem.amount() - amount);
                        itm.setAmount(itm.amount() + amount);
                    }
                } else if (itm.identifier() == this.cursorItem.identifier() && AlphaItems.getMaxStackSize(this.cursorItem.identifier()) > 1 && (amount = itm.amount()) > 0 && amount + this.cursorItem.amount() <= AlphaItems.getMaxStackSize(this.cursorItem.identifier())) {
                    itm.setAmount(itm.amount() - amount);
                    this.cursorItem.setAmount(this.cursorItem.amount() + amount);
                    if (isCraftingResultSlot) {
                        this.onCraftingResultPickup(windowId, slots);
                    }
                }
                for (int i = 0; i < slots.length; ++i) {
                    Item slotItem = slots[i];
                    if (slotItem == null || slotItem.amount() != 0) continue;
                    slots[i] = null;
                }
                switch (windowId) {
                    case 0: {
                        System.arraycopy(slots, 36, this.mainInventory, 0, 9);
                        System.arraycopy(slots, 9, this.mainInventory, 9, 27);
                        System.arraycopy(slots, 1, this.craftingInventory, 0, 4);
                        System.arraycopy(slots, 5, this.armorInventory, 0, 4);
                        this.armorInventory = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.reverseArray(this.armorInventory);
                        break;
                    }
                    case 33: {
                        System.arraycopy(slots, 0, this.openContainerItems, 0, 10);
                        System.arraycopy(slots, 37, this.mainInventory, 0, 9);
                        System.arraycopy(slots, 10, this.mainInventory, 9, 27);
                        break;
                    }
                    case 44: {
                        System.arraycopy(slots, 0, this.openContainerItems, 0, 3);
                        System.arraycopy(slots, 30, this.mainInventory, 0, 9);
                        System.arraycopy(slots, 3, this.mainInventory, 9, 27);
                        break;
                    }
                    case 55: {
                        System.arraycopy(slots, 0, this.openContainerItems, 0, 27);
                        System.arraycopy(slots, 54, this.mainInventory, 0, 9);
                        System.arraycopy(slots, 27, this.mainInventory, 9, 27);
                    }
                }
                this.updateInventory(windowId, slots);
                boolean updateCraftResultSlot = false;
                switch (windowId) {
                    case 0: {
                        boolean bl3 = updateCraftResultSlot = !this.isEmpty(this.craftingInventory);
                        if (!updateCraftResultSlot) break;
                        slots[0] = CraftingManager.getResult(this.craftingInventory);
                        break;
                    }
                    case 33: {
                        Item[] craftingGrid = Arrays.copyOfRange(this.openContainerItems, 1, 10);
                        boolean bl4 = updateCraftResultSlot = !this.isEmpty(craftingGrid);
                        if (!updateCraftResultSlot) break;
                        slots[0] = CraftingManager.getResult(craftingGrid);
                    }
                }
                if (updateCraftResultSlot) {
                    this.updateInventorySlot(windowId, (short)0, slots[0]);
                }
            } else {
                this.updatePlayerInventory();
            }
        }
        if (this.cursorItem != null && this.cursorItem.amount() == 0) {
            this.cursorItem = null;
        }
        this.updateCursorItem();
    }

    public void onBlockPlace(BlockPosition position, short direction) {
        if (this.creativeMode) {
            return;
        }
        Item handItem = this.mainInventory[this.inventoryStorage.selectedHotbarSlot];
        if (handItem == null) {
            return;
        }
        if (direction == 255) {
            AlphaItems.doInteract(handItem);
        } else {
            IdAndData placedAgainst = this.user().get(ChunkTracker.class).getBlockNotNull(position);
            IdAndData targetBlock = this.user().get(ChunkTracker.class).getBlockNotNull(position.getRelative(BlockFaceUtil.getFace(direction)));
            AlphaItems.doPlace(handItem, direction, placedAgainst);
            if (handItem.identifier() < 256 || handItem.identifier() == ItemList1_6.reed.itemId()) {
                if (targetBlock.getId() == 0 || targetBlock.getId() == BlockList1_6.waterStill.blockId() || targetBlock.getId() == BlockList1_6.waterMoving.blockId() || targetBlock.getId() == BlockList1_6.lavaStill.blockId() || targetBlock.getId() == BlockList1_6.lavaMoving.blockId() || targetBlock.getId() == BlockList1_6.fire.blockId() || targetBlock.getId() == BlockList1_6.snow.blockId()) {
                    handItem.setAmount(handItem.amount() - 1);
                }
            } else if (handItem.identifier() == ItemList1_6.sign.itemId()) {
                if (direction != 0 && targetBlock.getId() == 0) {
                    handItem.setAmount(handItem.amount() - 1);
                }
            } else if (handItem.identifier() == ItemList1_6.redstone.itemId() && targetBlock.getId() == 0) {
                handItem.setAmount(handItem.amount() - 1);
            }
        }
        if (handItem.amount() == 0) {
            this.mainInventory[this.inventoryStorage.selectedHotbarSlot] = null;
        }
        this.updatePlayerInventorySlot(this.inventoryStorage.selectedHotbarSlot);
    }

    public void onHandItemDrop() {
        Item handItem = this.mainInventory[this.inventoryStorage.selectedHotbarSlot];
        if (handItem == null) {
            return;
        }
        handItem.setAmount(handItem.amount() - 1);
        if (handItem.amount() == 0) {
            this.mainInventory[this.inventoryStorage.selectedHotbarSlot] = null;
        }
        this.updatePlayerInventorySlot(this.inventoryStorage.selectedHotbarSlot);
    }

    public void onRespawn() {
        this.mainInventory = new Item[37];
        this.craftingInventory = new Item[4];
        this.armorInventory = new Item[4];
        this.openContainerItems = new Item[0];
        this.cursorItem = null;
        this.openWindowType = -1;
    }

    public void addItem(Item item) {
        int i;
        int slot;
        if (item == null) {
            return;
        }
        if (item.amount() == 0) {
            return;
        }
        if (item.data() == 0) {
            slot = -1;
            for (i = 0; i < this.mainInventory.length; ++i) {
                if (this.mainInventory[i] == null || this.mainInventory[i].identifier() != item.identifier() || this.mainInventory[i].amount() >= AlphaItems.getMaxStackSize(this.mainInventory[i].identifier())) continue;
                slot = i;
                break;
            }
            if (slot != -1) {
                int amount = item.amount();
                if (amount > AlphaItems.getMaxStackSize(this.mainInventory[slot].identifier()) - this.mainInventory[slot].amount()) {
                    amount = AlphaItems.getMaxStackSize(this.mainInventory[slot].identifier()) - this.mainInventory[slot].amount();
                }
                item.setAmount(item.amount() - amount);
                this.mainInventory[slot].setAmount(this.mainInventory[slot].amount() + amount);
                this.updatePlayerInventorySlot((short)slot);
            }
        }
        if (item.amount() != 0) {
            slot = -1;
            for (i = 0; i < this.mainInventory.length; ++i) {
                if (this.mainInventory[i] != null) continue;
                slot = i;
                break;
            }
            if (slot != -1) {
                this.mainInventory[slot] = item;
                this.updatePlayerInventorySlot((short)slot);
            }
        }
    }

    public void handleCreativeSetSlot(short slot, Item item) {
        if (!this.user().getProtocolInfo().serverProtocolVersion().equals(LegacyProtocolVersion.c0_30cpe)) {
            item = Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.fixItem(item);
        }
        if (slot <= 0) {
            return;
        }
        if (slot <= 4) {
            slot = (short)(slot - 1);
            this.craftingInventory[slot] = item;
        } else if (slot <= 8) {
            slot = (short)(slot - 5);
            slot = (short)(3 - slot);
            this.armorInventory[slot] = item;
        } else if (slot <= 44) {
            if (slot >= 36) {
                slot = (short)(slot - 36);
            }
            this.mainInventory[slot] = item;
        }
    }

    public void setCreativeMode(boolean creativeMode) {
        this.creativeMode = creativeMode;
    }

    public void setMainInventory(Item[] items) {
        this.mainInventory = items;
    }

    public void setCraftingInventory(Item[] items) {
        this.craftingInventory = items;
    }

    public void setArmorInventory(Item[] items) {
        this.armorInventory = items;
    }

    public void setOpenContainerItems(Item[] items) {
        this.openContainerItems = items;
    }

    public Item[] getMainInventory() {
        return this.mainInventory;
    }

    public Item[] getCraftingInventory() {
        return this.craftingInventory;
    }

    public Item[] getArmorInventory() {
        return this.armorInventory;
    }

    public Item[] getOpenContainerItems() {
        return this.openContainerItems;
    }

    public Item getCursorItem() {
        return this.cursorItem;
    }

    private void updatePlayerInventorySlot(short slot) {
        this.updateInventorySlot((byte)0, slot >= 0 && slot < 9 ? (short)(slot + 36) : slot, this.mainInventory[slot]);
    }

    private void updateCursorItem() {
        this.updateInventorySlot((byte)-1, (short)0, this.cursorItem);
    }

    private void updateInventorySlot(byte windowId, short slot, Item item) {
        PacketWrapper setSlot = PacketWrapper.create(ClientboundPacketsb1_1.CONTAINER_SET_SLOT, this.user());
        setSlot.write(Types.BYTE, windowId);
        setSlot.write(Types.SHORT, slot);
        setSlot.write(Typesb1_1.NBTLESS_ITEM, Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItem(item));
        setSlot.send(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
    }

    private void updatePlayerInventory() {
        Item[] items = new Item[45];
        System.arraycopy(this.mainInventory, 0, items, 36, 9);
        System.arraycopy(this.mainInventory, 9, items, 9, 27);
        System.arraycopy(this.craftingInventory, 0, items, 1, 4);
        System.arraycopy(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.reverseArray(this.armorInventory), 0, items, 5, 4);
        this.updateInventory((byte)0, items);
    }

    private void updateInventory(byte windowId, Item[] items) {
        PacketWrapper windowItems = PacketWrapper.create(ClientboundPacketsb1_1.CONTAINER_SET_CONTENT, this.user());
        windowItems.write(Types.BYTE, windowId);
        windowItems.write(Types1_4_2.NBTLESS_ITEM_ARRAY, Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.copyItems(items));
        windowItems.send(Protocola1_2_3_5_1_2_6Tob1_0_1_1_1.class);
    }

    private Item splitStack(Item item, int size) {
        item.setAmount(item.amount() - size);
        Item newItem = item.copy();
        newItem.setAmount(size);
        return newItem;
    }

    private void onCraftingResultPickup(byte windowId, Item[] slots) {
        for (int i = 1; i < 1 + (windowId == 0 ? 4 : 9); ++i) {
            Item item = slots[i];
            if (item == null) continue;
            item.setAmount(item.amount() - 1);
        }
    }

    private boolean isEmpty(Item[] items) {
        for (Item item : items) {
            if (item == null || item.amount() == 0) continue;
            return false;
        }
        return true;
    }
}

