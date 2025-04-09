/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.v1_8to1_9;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class BlockListener
extends ViaBukkitListener {
    public BlockListener(Plugin plugin) {
        super(plugin, Protocol1_8To1_9.class);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void placeBlock(BlockPlaceEvent e) {
        if (this.isOnPipe(e.getPlayer())) {
            Block b = e.getBlockPlaced();
            EntityTracker1_9 tracker = (EntityTracker1_9)this.getUserConnection(e.getPlayer()).getEntityTracker(Protocol1_8To1_9.class);
            tracker.addBlockInteraction(new BlockPosition(b.getX(), b.getY(), b.getZ()));
        }
    }
}

