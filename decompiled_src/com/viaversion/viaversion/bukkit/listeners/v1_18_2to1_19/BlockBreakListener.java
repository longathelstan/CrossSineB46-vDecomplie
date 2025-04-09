/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.v1_18_2to1_19;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.protocols.v1_18_2to1_19.Protocol1_18_2To1_19;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public final class BlockBreakListener
extends ViaBukkitListener {
    private static final Class<?> CRAFT_BLOCK_STATE_CLASS;

    public BlockBreakListener(ViaVersionPlugin plugin) {
        super((Plugin)plugin, Protocol1_18_2To1_19.class);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!event.isCancelled() || !this.isBlockEntity(block.getState())) {
            return;
        }
        ProtocolVersion serverProtocolVersion = Via.getAPI().getServerVersion().highestSupportedProtocolVersion();
        long delay2 = serverProtocolVersion.newerThan(ProtocolVersion.v1_8) && serverProtocolVersion.olderThan(ProtocolVersion.v1_14) ? 2L : 1L;
        this.getPlugin().getServer().getScheduler().runTaskLater(this.getPlugin(), () -> {
            BlockState state = block.getState();
            if (this.isBlockEntity(state)) {
                state.update(true, false);
            }
        }, delay2);
    }

    private boolean isBlockEntity(BlockState state) {
        return state.getClass() != CRAFT_BLOCK_STATE_CLASS;
    }

    static {
        try {
            CRAFT_BLOCK_STATE_CLASS = NMSUtil.obc("block.CraftBlockState");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

