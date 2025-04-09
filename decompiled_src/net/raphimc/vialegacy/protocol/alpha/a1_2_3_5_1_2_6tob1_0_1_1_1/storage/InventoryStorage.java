/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.item.Item;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InventoryStorage
implements StorableObject {
    public static final byte WORKBENCH_WID = 33;
    public static final byte FURNACE_WID = 44;
    public static final byte CHEST_WID = 55;
    public Item handItem = null;
    public Item[] mainInventory = null;
    public Item[] craftingInventory = null;
    public Item[] armorInventory = null;
    public final Map<BlockPosition, Item[]> containers = new HashMap<BlockPosition, Item[]>();
    public BlockPosition openContainerPos = null;
    public short selectedHotbarSlot = 0;

    public InventoryStorage() {
        this.resetPlayerInventory();
    }

    public void unload(int chunkX, int chunkZ) {
        Iterator<BlockPosition> it = this.containers.keySet().iterator();
        while (it.hasNext()) {
            BlockPosition entry = it.next();
            int x = entry.x() >> 4;
            int z = entry.z() >> 4;
            if (chunkX != x || chunkZ != z) continue;
            it.remove();
        }
    }

    public void resetPlayerInventory() {
        this.mainInventory = new Item[37];
        this.craftingInventory = new Item[4];
        this.armorInventory = new Item[4];
        this.openContainerPos = null;
    }
}

