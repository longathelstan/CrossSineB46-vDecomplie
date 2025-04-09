/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.listener;

import com.viaversion.viabackwards.BukkitPlugin;
import com.viaversion.viabackwards.protocol.v1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockBreakListener
extends ViaBukkitListener {
    public BlockBreakListener(BukkitPlugin plugin) {
        super((Plugin)plugin, Protocol1_11To1_10.class);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || !this.isOnPipe(player)) {
            return;
        }
        int slot = player.getInventory().getHeldItemSlot();
        ItemStack item = player.getInventory().getItem(slot);
        if (item != null && item.getType().getMaxDurability() > 0) {
            player.getInventory().setItem(slot, item);
        }
    }
}

