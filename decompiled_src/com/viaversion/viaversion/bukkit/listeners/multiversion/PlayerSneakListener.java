/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.multiversion;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.logging.Level;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

public class PlayerSneakListener
extends ViaBukkitListener {
    private static final float STANDING_HEIGHT = 1.8f;
    private static final float HEIGHT_1_14 = 1.5f;
    private static final float HEIGHT_1_9 = 1.6f;
    private static final float DEFAULT_WIDTH = 0.6f;
    private final boolean is1_9Fix;
    private final boolean is1_14Fix;
    private Map<Player, Boolean> sneaking;
    private Set<UUID> sneakingUuids;
    private final Method getHandle;
    private Method setSize;
    private boolean useCache;

    public PlayerSneakListener(ViaVersionPlugin plugin, boolean is1_9Fix, boolean is1_14Fix) throws ReflectiveOperationException {
        super((Plugin)plugin, null);
        String packageName;
        this.is1_9Fix = is1_9Fix;
        this.is1_14Fix = is1_14Fix;
        String string = packageName = plugin.getServer().getClass().getPackage().getName();
        this.getHandle = Class.forName(string + ".entity.CraftPlayer").getMethod("getHandle", new Class[0]);
        String string2 = packageName.replace("org.bukkit.craftbukkit", "net.minecraft.server");
        Class<?> entityPlayerClass = Class.forName(string2 + ".EntityPlayer");
        try {
            this.setSize = entityPlayerClass.getMethod("setSize", Float.TYPE, Float.TYPE);
        }
        catch (NoSuchMethodException e) {
            this.setSize = entityPlayerClass.getMethod("a", Float.TYPE, Float.TYPE);
        }
        if (Via.getAPI().getServerVersion().lowestSupportedProtocolVersion().newerThan(ProtocolVersion.v1_8)) {
            this.sneaking = new WeakHashMap<Player, Boolean>();
            this.useCache = true;
            plugin.getServer().getScheduler().runTaskTimer((Plugin)plugin, () -> {
                for (Map.Entry<Player, Boolean> entry : this.sneaking.entrySet()) {
                    this.setHeight(entry.getKey(), entry.getValue() != false ? 1.5f : 1.6f);
                }
            }, 1L, 1L);
        }
        if (is1_14Fix) {
            this.sneakingUuids = new HashSet<UUID>();
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void playerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        UserConnection userConnection = this.getUserConnection(player);
        if (userConnection == null) {
            return;
        }
        ProtocolInfo info = userConnection.getProtocolInfo();
        if (info == null) {
            return;
        }
        ProtocolVersion protocolVersion = info.protocolVersion();
        if (this.is1_14Fix && protocolVersion.newerThanOrEqualTo(ProtocolVersion.v1_14)) {
            this.setHeight(player, event.isSneaking() ? 1.5f : 1.8f);
            if (event.isSneaking()) {
                this.sneakingUuids.add(player.getUniqueId());
            } else {
                this.sneakingUuids.remove(player.getUniqueId());
            }
            if (!this.useCache) {
                return;
            }
            if (event.isSneaking()) {
                this.sneaking.put(player, true);
            } else {
                this.sneaking.remove(player);
            }
        } else if (this.is1_9Fix && protocolVersion.newerThanOrEqualTo(ProtocolVersion.v1_9)) {
            this.setHeight(player, event.isSneaking() ? 1.6f : 1.8f);
            if (!this.useCache) {
                return;
            }
            if (event.isSneaking()) {
                this.sneaking.put(player, false);
            } else {
                this.sneaking.remove(player);
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void playerDamage(EntityDamageEvent event) {
        if (!this.is1_14Fix) {
            return;
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.SUFFOCATION) {
            return;
        }
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player)event.getEntity();
        if (!this.sneakingUuids.contains(player.getUniqueId())) {
            return;
        }
        double y = player.getEyeLocation().getY() + 0.045;
        if ((y -= (double)((int)y)) < 0.09) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        if (this.sneaking != null) {
            this.sneaking.remove(event.getPlayer());
        }
        if (this.sneakingUuids != null) {
            this.sneakingUuids.remove(event.getPlayer().getUniqueId());
        }
    }

    private void setHeight(Player player, float height) {
        try {
            this.setSize.invoke(this.getHandle.invoke((Object)player, new Object[0]), Float.valueOf(0.6f), Float.valueOf(height));
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Failed to set player height", e);
        }
    }
}

