/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.providers.Provider;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.InventoryStorage;

public abstract class AlphaInventoryProvider
implements Provider {
    public abstract boolean usesInventoryTracker();

    public Item getHandItem(UserConnection user) {
        InventoryStorage inventoryStorage = user.get(InventoryStorage.class);
        Item[] inventory = this.getMainInventoryItems(user);
        return inventory[inventoryStorage.selectedHotbarSlot];
    }

    public abstract Item[] getMainInventoryItems(UserConnection var1);

    public abstract Item[] getCraftingInventoryItems(UserConnection var1);

    public abstract Item[] getArmorInventoryItems(UserConnection var1);

    public abstract Item[] getContainerItems(UserConnection var1);

    public abstract void addToInventory(UserConnection var1, Item var2);
}

