/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.v1_20_5to1_21;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.bukkit.listeners.v1_20_5to1_21.PlayerChangeItemListener;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class PaperPlayerChangeItemListener
extends PlayerChangeItemListener {
    public PaperPlayerChangeItemListener(ViaVersionPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerInventorySlotChangedEvent(PlayerInventorySlotChangeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getNewItemStack();
        PlayerInventory inventory = player.getInventory();
        int slot = event.getSlot();
        if (slot == inventory.getHeldItemSlot()) {
            this.sendAttributeUpdate(player, item, PlayerChangeItemListener.Slot.HAND);
        } else if (slot == 36) {
            this.sendAttributeUpdate(player, item, PlayerChangeItemListener.Slot.BOOTS);
        } else if (slot == 37) {
            this.sendAttributeUpdate(player, item, PlayerChangeItemListener.Slot.LEGGINGS);
        } else if (slot == 39) {
            this.sendAttributeUpdate(player, item, PlayerChangeItemListener.Slot.HELMET);
        }
    }
}

