/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.listeners.v1_14_4to1_15;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import java.util.Arrays;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class EntityToggleGlideListener
extends ViaBukkitListener {
    private boolean swimmingMethodExists;

    public EntityToggleGlideListener(ViaVersionPlugin plugin) {
        super((Plugin)plugin, Protocol1_14_4To1_15.class);
        try {
            Player.class.getMethod("isSwimming", new Class[0]);
            this.swimmingMethodExists = true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void entityToggleGlide(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player)entity;
        if (!this.isOnPipe(player)) {
            return;
        }
        if (event.isGliding() && event.isCancelled()) {
            PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_15.SET_ENTITY_DATA, null, this.getUserConnection(player));
            packet.write(Types.VAR_INT, player.getEntityId());
            byte bitmask = 0;
            if (player.getFireTicks() > 0) {
                bitmask = (byte)(bitmask | 1);
            }
            if (player.isSneaking()) {
                bitmask = (byte)(bitmask | 2);
            }
            if (player.isSprinting()) {
                bitmask = (byte)(bitmask | 8);
            }
            if (this.swimmingMethodExists && player.isSwimming()) {
                bitmask = (byte)(bitmask | 0x10);
            }
            if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                bitmask = (byte)(bitmask | 0x20);
            }
            if (player.isGlowing()) {
                bitmask = (byte)(bitmask | 0x40);
            }
            packet.write(Types1_14.ENTITY_DATA_LIST, Arrays.asList(new EntityData(0, Types1_14.ENTITY_DATA_TYPES.byteType, bitmask)));
            packet.scheduleSend(Protocol1_14_4To1_15.class);
        }
    }
}

