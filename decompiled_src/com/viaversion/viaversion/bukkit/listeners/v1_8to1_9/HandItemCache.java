/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.v1_8to1_9;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HandItemCache
extends BukkitRunnable {
    private final Map<UUID, Item> handCache = new ConcurrentHashMap<UUID, Item>();

    public void run() {
        ArrayList<UUID> players = new ArrayList<UUID>(this.handCache.keySet());
        for (Player p : Bukkit.getOnlinePlayers()) {
            this.handCache.put(p.getUniqueId(), HandItemCache.convert(p.getItemInHand()));
            players.remove(p.getUniqueId());
        }
        for (UUID uuid : players) {
            this.handCache.remove(uuid);
        }
    }

    public Item getHandItem(UUID player) {
        return this.handCache.get(player);
    }

    public static Item convert(ItemStack itemInHand) {
        if (itemInHand == null) {
            return new DataItem(0, 0, 0, null);
        }
        return new DataItem(itemInHand.getTypeId(), (byte)itemInHand.getAmount(), itemInHand.getDurability(), null);
    }
}

