/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.provider.AlphaInventoryProvider;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.storage.InventoryStorage;

public class NoOpAlphaInventoryProvider
extends AlphaInventoryProvider {
    @Override
    public boolean usesInventoryTracker() {
        return false;
    }

    @Override
    public Item[] getMainInventoryItems(UserConnection user) {
        return new Item[36];
    }

    @Override
    public Item[] getCraftingInventoryItems(UserConnection user) {
        return new Item[4];
    }

    @Override
    public Item[] getArmorInventoryItems(UserConnection user) {
        return new Item[4];
    }

    @Override
    public Item[] getContainerItems(UserConnection user) {
        InventoryStorage inventoryStorage = user.get(InventoryStorage.class);
        return inventoryStorage.containers.get(inventoryStorage.openContainerPos);
    }

    @Override
    public void addToInventory(UserConnection user, Item item) {
    }
}

