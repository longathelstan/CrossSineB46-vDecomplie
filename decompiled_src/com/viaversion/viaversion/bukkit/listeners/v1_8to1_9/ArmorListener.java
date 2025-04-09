/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.v1_8to1_9;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.ArmorTypes1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ArmorListener
extends ViaBukkitListener {
    private static final UUID ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");

    public ArmorListener(Plugin plugin) {
        super(plugin, Protocol1_8To1_9.class);
    }

    public void sendArmorUpdate(Player player) {
        if (!this.isOnPipe(player)) {
            return;
        }
        int armor = 0;
        for (ItemStack stack : player.getInventory().getArmorContents()) {
            armor += ArmorTypes1_8.findById(stack.getTypeId()).getArmorPoints();
        }
        PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.UPDATE_ATTRIBUTES, null, this.getUserConnection(player));
        wrapper.write(Types.VAR_INT, player.getEntityId());
        wrapper.write(Types.INT, 1);
        wrapper.write(Types.STRING, "generic.armor");
        wrapper.write(Types.DOUBLE, 0.0);
        wrapper.write(Types.VAR_INT, 1);
        wrapper.write(Types.UUID, ARMOR_ATTRIBUTE);
        wrapper.write(Types.DOUBLE, Double.valueOf(armor));
        wrapper.write(Types.BYTE, (byte)0);
        wrapper.scheduleSend(Protocol1_8To1_9.class);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onInventoryClick(InventoryClickEvent e) {
        HumanEntity human = e.getWhoClicked();
        if (human instanceof Player) {
            Player player = (Player)human;
            if (e.getInventory() instanceof CraftingInventory && (e.getCurrentItem() != null && ArmorTypes1_8.isArmor(e.getCurrentItem().getTypeId()) || e.getRawSlot() >= 5 && e.getRawSlot() <= 8)) {
                this.sendDelayedArmorUpdate(player);
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) {
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.getPlugin(), () -> this.sendArmorUpdate(player), 3L);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onItemBreak(PlayerItemBreakEvent e) {
        this.sendDelayedArmorUpdate(e.getPlayer());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onJoin(PlayerJoinEvent e) {
        this.sendDelayedArmorUpdate(e.getPlayer());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onRespawn(PlayerRespawnEvent e) {
        this.sendDelayedArmorUpdate(e.getPlayer());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        this.sendArmorUpdate(e.getPlayer());
    }

    public void sendDelayedArmorUpdate(Player player) {
        if (!this.isOnPipe(player)) {
            return;
        }
        Via.getPlatform().runSync(() -> this.sendArmorUpdate(player));
    }
}

