/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.listeners.UpdateListener;
import com.viaversion.viaversion.bukkit.listeners.multiversion.PlayerSneakListener;
import com.viaversion.viaversion.bukkit.listeners.v1_14_4to1_15.EntityToggleGlideListener;
import com.viaversion.viaversion.bukkit.listeners.v1_18_2to1_19.BlockBreakListener;
import com.viaversion.viaversion.bukkit.listeners.v1_19_3to1_19_4.ArmorToggleListener;
import com.viaversion.viaversion.bukkit.listeners.v1_20_5to1_21.PaperPlayerChangeItemListener;
import com.viaversion.viaversion.bukkit.listeners.v1_20_5to1_21.PlayerChangeItemListener;
import com.viaversion.viaversion.bukkit.listeners.v1_8to1_9.ArmorListener;
import com.viaversion.viaversion.bukkit.listeners.v1_8to1_9.BlockListener;
import com.viaversion.viaversion.bukkit.listeners.v1_8to1_9.DeathListener;
import com.viaversion.viaversion.bukkit.listeners.v1_8to1_9.HandItemCache;
import com.viaversion.viaversion.bukkit.listeners.v1_8to1_9.PaperPatch;
import com.viaversion.viaversion.bukkit.platform.PaperViaInjector;
import com.viaversion.viaversion.bukkit.providers.BukkitAckSequenceProvider;
import com.viaversion.viaversion.bukkit.providers.BukkitBlockConnectionProvider;
import com.viaversion.viaversion.bukkit.providers.BukkitInventoryQuickMoveProvider;
import com.viaversion.viaversion.bukkit.providers.BukkitViaMovementTransmitter;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.provider.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.providers.BlockConnectionProvider;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.provider.AckSequenceProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.HandItemProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.MovementTransmitterProvider;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class BukkitViaLoader
implements ViaPlatformLoader {
    final Set<BukkitTask> tasks = new HashSet<BukkitTask>();
    final ViaVersionPlugin plugin;
    HandItemCache handItemCache;

    public BukkitViaLoader(ViaVersionPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerListener(Listener listener) {
        this.plugin.getServer().getPluginManager().registerEvents(listener, (Plugin)this.plugin);
    }

    @Override
    public void load() {
        this.registerListener(new UpdateListener());
        ViaVersionPlugin plugin = (ViaVersionPlugin)Bukkit.getPluginManager().getPlugin("ViaVersion");
        if (!Via.getAPI().getServerVersion().isKnown()) {
            Via.getPlatform().getLogger().severe("Server version has not been loaded yet, cannot register additional listeners");
            return;
        }
        ProtocolVersion serverProtocolVersion = Via.getAPI().getServerVersion().lowestSupportedProtocolVersion();
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_9)) {
            new ArmorListener((Plugin)plugin).register();
            new DeathListener((Plugin)plugin).register();
            if (plugin.getConf().cancelBlockSounds()) {
                new BlockListener((Plugin)plugin).register();
            }
            if (plugin.getConf().isItemCache()) {
                this.handItemCache = new HandItemCache();
                this.tasks.add(this.handItemCache.runTaskTimerAsynchronously((Plugin)plugin, 1L, 1L));
            }
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_14)) {
            boolean use1_9Fix;
            boolean bl = use1_9Fix = plugin.getConf().is1_9HitboxFix() && serverProtocolVersion.olderThan(ProtocolVersion.v1_9);
            if (use1_9Fix || plugin.getConf().is1_14HitboxFix()) {
                try {
                    new PlayerSneakListener(plugin, use1_9Fix, plugin.getConf().is1_14HitboxFix()).register();
                }
                catch (ReflectiveOperationException e) {
                    Via.getPlatform().getLogger().log(Level.WARNING, "Could not load hitbox fix - please report this on our GitHub", e);
                }
            }
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_15)) {
            try {
                Class.forName("org.bukkit.event.entity.EntityToggleGlideEvent");
                new EntityToggleGlideListener(plugin).register();
            }
            catch (ClassNotFoundException use1_9Fix) {
                // empty catch block
            }
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_12) && !Boolean.getBoolean("com.viaversion.ignorePaperBlockPlacePatch")) {
            boolean paper = true;
            try {
                Class.forName("org.github.paperspigot.PaperSpigotConfig");
            }
            catch (ClassNotFoundException ignored) {
                try {
                    Class.forName("com.destroystokyo.paper.PaperConfig");
                }
                catch (ClassNotFoundException alsoIgnored) {
                    paper = false;
                }
            }
            if (paper) {
                new PaperPatch((Plugin)plugin).register();
            }
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_19_4) && plugin.getConf().isArmorToggleFix() && this.hasGetHandMethod()) {
            new ArmorToggleListener(plugin).register();
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_9)) {
            Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BukkitViaMovementTransmitter());
            Via.getManager().getProviders().use(HandItemProvider.class, new HandItemProvider(){

                @Override
                public Item getHandItem(UserConnection info) {
                    if (BukkitViaLoader.this.handItemCache != null) {
                        return BukkitViaLoader.this.handItemCache.getHandItem(info.getProtocolInfo().getUuid());
                    }
                    try {
                        return (Item)Bukkit.getScheduler().callSyncMethod(Bukkit.getPluginManager().getPlugin("ViaVersion"), () -> {
                            UUID playerUUID = info.getProtocolInfo().getUuid();
                            Player player = Bukkit.getPlayer((UUID)playerUUID);
                            if (player != null) {
                                return HandItemCache.convert(player.getItemInHand());
                            }
                            return null;
                        }).get(10L, TimeUnit.SECONDS);
                    }
                    catch (Exception e) {
                        Via.getPlatform().getLogger().log(Level.SEVERE, "Error fetching hand item", e);
                        return null;
                    }
                }
            });
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_12) && plugin.getConf().is1_12QuickMoveActionFix()) {
            Via.getManager().getProviders().use(InventoryQuickMoveProvider.class, new BukkitInventoryQuickMoveProvider());
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_13) && Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("world")) {
            BukkitBlockConnectionProvider blockConnectionProvider = new BukkitBlockConnectionProvider();
            Via.getManager().getProviders().use(BlockConnectionProvider.class, blockConnectionProvider);
            ConnectionData.blockConnectionProvider = blockConnectionProvider;
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_19)) {
            Via.getManager().getProviders().use(AckSequenceProvider.class, new BukkitAckSequenceProvider(plugin));
            new BlockBreakListener(plugin).register();
        }
        if (serverProtocolVersion.olderThan(ProtocolVersion.v1_21)) {
            if (PaperViaInjector.hasClass("io.papermc.paper.event.player.PlayerInventorySlotChangeEvent")) {
                new PaperPlayerChangeItemListener(plugin).register();
            } else {
                new PlayerChangeItemListener(plugin).register();
            }
        }
    }

    boolean hasGetHandMethod() {
        try {
            PlayerInteractEvent.class.getDeclaredMethod("getHand", new Class[0]);
            Material.class.getMethod("getEquipmentSlot", new Class[0]);
            return true;
        }
        catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public void unload() {
        for (BukkitTask task : this.tasks) {
            task.cancel();
        }
        this.tasks.clear();
    }
}

