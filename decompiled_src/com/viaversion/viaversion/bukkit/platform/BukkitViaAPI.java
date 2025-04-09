/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.ViaAPIBase;
import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import org.bukkit.entity.Player;

public class BukkitViaAPI
extends ViaAPIBase<Player> {
    private final ViaVersionPlugin plugin;

    public BukkitViaAPI(ViaVersionPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public ProtocolVersion getPlayerProtocolVersion(Player player) {
        return this.getPlayerProtocolVersion(player.getUniqueId());
    }

    @Override
    public ProtocolVersion getPlayerProtocolVersion(UUID uuid) {
        UserConnection connection = Via.getManager().getConnectionManager().getConnectedClient(uuid);
        if (connection != null) {
            return connection.getProtocolInfo().protocolVersion();
        }
        return ProtocolVersion.unknown;
    }

    @Override
    public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
        this.sendRawPacket(player.getUniqueId(), packet);
    }
}

