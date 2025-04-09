/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.tasks.v1_11_1to1_12;

import com.viaversion.viaversion.bukkit.providers.BukkitInventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.storage.ItemTransactionStorage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitInventoryUpdateTask
implements Runnable {
    private final BukkitInventoryQuickMoveProvider provider;
    private final UUID uuid;
    private final List<ItemTransactionStorage> items;

    public BukkitInventoryUpdateTask(BukkitInventoryQuickMoveProvider provider, UUID uuid) {
        this.provider = provider;
        this.uuid = uuid;
        this.items = Collections.synchronizedList(new ArrayList());
    }

    public void addItem(short windowId, short slotId, short actionId) {
        ItemTransactionStorage storage = new ItemTransactionStorage(windowId, slotId, actionId);
        this.items.add(storage);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Player p = Bukkit.getServer().getPlayer(this.uuid);
        if (p == null) {
            this.provider.onTaskExecuted(this.uuid);
            return;
        }
        try {
            List<ItemTransactionStorage> list = this.items;
            synchronized (list) {
                ItemTransactionStorage storage;
                Object packet;
                boolean result;
                Iterator<ItemTransactionStorage> iterator2 = this.items.iterator();
                while (iterator2.hasNext() && (result = this.provider.sendPacketToServer(p, packet = this.provider.buildWindowClickPacket(p, storage = iterator2.next())))) {
                }
                this.items.clear();
            }
        }
        finally {
            this.provider.onTaskExecuted(this.uuid);
        }
    }
}

