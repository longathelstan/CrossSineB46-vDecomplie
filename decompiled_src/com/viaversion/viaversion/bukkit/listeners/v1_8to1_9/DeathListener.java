/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.v1_8to1_9;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.logging.Level;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class DeathListener
extends ViaBukkitListener {
    public DeathListener(Plugin plugin) {
        super(plugin, Protocol1_8To1_9.class);
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (this.isOnPipe(p) && Via.getConfig().isShowNewDeathMessages() && this.checkGamerule(p.getWorld()) && e.getDeathMessage() != null) {
            this.sendPacket(p, e.getDeathMessage());
        }
    }

    public boolean checkGamerule(World w) {
        try {
            return Boolean.parseBoolean(w.getGameRuleValue("showDeathMessages"));
        }
        catch (Exception e) {
            return false;
        }
    }

    private void sendPacket(Player p, String msg) {
        Via.getPlatform().runSync(() -> {
            UserConnection userConnection = this.getUserConnection(p);
            if (userConnection != null) {
                PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.PLAYER_COMBAT, userConnection);
                try {
                    wrapper.write(Types.VAR_INT, 2);
                    wrapper.write(Types.VAR_INT, p.getEntityId());
                    wrapper.write(Types.INT, p.getEntityId());
                    wrapper.write(Types.COMPONENT, ComponentUtil.plainToJson(msg));
                    wrapper.scheduleSend(Protocol1_8To1_9.class);
                }
                catch (Exception e) {
                    Via.getPlatform().getLogger().log(Level.WARNING, "Failed to send death message", e);
                }
            }
        });
    }
}

