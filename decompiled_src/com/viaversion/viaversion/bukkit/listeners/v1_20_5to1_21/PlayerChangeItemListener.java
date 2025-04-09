/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.v1_20_5to1_21;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.storage.EfficiencyAttributeStorage;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlayerChangeItemListener
extends ViaBukkitListener {
    final Enchantment efficiency = this.getByName("efficiency", "DIG_SPEED");
    final Enchantment aquaAffinity = this.getByName("aqua_affinity", "WATER_WORKER");
    final Enchantment depthStrider = this.getByName("depth_strider", "DEPTH_STRIDER");
    final Enchantment soulSpeed = this.getByName("soul_speed", "SOUL_SPEED");
    final Enchantment swiftSneak = this.getByName("swift_sneak", "SWIFT_SNEAK");

    public PlayerChangeItemListener(ViaVersionPlugin plugin) {
        super((Plugin)plugin, Protocol1_20_5To1_21.class);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        this.sendAttributeUpdate(player, item, Slot.HAND);
    }

    void sendAttributeUpdate(Player player, @Nullable ItemStack item, Slot slot) {
        UserConnection connection = Via.getAPI().getConnection(player.getUniqueId());
        if (connection == null || !this.isOnPipe(player)) {
            return;
        }
        EfficiencyAttributeStorage storage = connection.get(EfficiencyAttributeStorage.class);
        if (storage == null) {
            return;
        }
        EfficiencyAttributeStorage.ActiveEnchants activeEnchants = storage.activeEnchants();
        int efficiencyLevel = activeEnchants.efficiency().level();
        int aquaAffinityLevel = activeEnchants.aquaAffinity().level();
        int soulSpeedLevel = activeEnchants.soulSpeed().level();
        int swiftSneakLevel = activeEnchants.swiftSneak().level();
        int depthStriderLevel = activeEnchants.depthStrider().level();
        switch (slot) {
            case HAND: {
                efficiencyLevel = item != null ? item.getEnchantmentLevel(this.efficiency) : 0;
                break;
            }
            case HELMET: {
                aquaAffinityLevel = item != null ? item.getEnchantmentLevel(this.aquaAffinity) : 0;
                break;
            }
            case LEGGINGS: {
                swiftSneakLevel = item != null && this.swiftSneak != null ? item.getEnchantmentLevel(this.swiftSneak) : 0;
                break;
            }
            case BOOTS: {
                depthStriderLevel = item != null && this.depthStrider != null ? item.getEnchantmentLevel(this.depthStrider) : 0;
            }
        }
        storage.setEnchants(player.getEntityId(), connection, efficiencyLevel, soulSpeedLevel, swiftSneakLevel, aquaAffinityLevel, depthStriderLevel);
    }

    Enchantment getByName(String newName, String oldName) {
        Enchantment enchantment = Enchantment.getByName((String)newName);
        if (enchantment == null) {
            return Enchantment.getByName((String)oldName);
        }
        return enchantment;
    }

    static enum Slot {
        HAND,
        BOOTS,
        LEGGINGS,
        HELMET;

    }
}

