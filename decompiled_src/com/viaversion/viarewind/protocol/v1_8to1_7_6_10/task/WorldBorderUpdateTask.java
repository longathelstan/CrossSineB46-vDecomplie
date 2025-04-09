/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_8to1_7_6_10.task;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.Protocol1_8To1_7_6_10;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.v1_8to1_7_6_10.storage.WorldBorderEmulator;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.logging.Level;

public class WorldBorderUpdateTask
implements Runnable {
    public static final int VIEW_DISTANCE = 16;

    @Override
    public void run() {
        for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
            WorldBorderEmulator worldBorderEmulatorTracker = connection.get(WorldBorderEmulator.class);
            if (!worldBorderEmulatorTracker.isInit()) continue;
            PlayerSessionStorage playerSession = connection.get(PlayerSessionStorage.class);
            double radius = worldBorderEmulatorTracker.getSize() / 2.0;
            for (WorldBorderEmulator.Side side : WorldBorderEmulator.Side.values()) {
                double d;
                double center;
                double pos;
                if (side.modX != 0) {
                    pos = playerSession.getPosZ();
                    center = worldBorderEmulatorTracker.getZ();
                    d = Math.abs(worldBorderEmulatorTracker.getX() + radius * (double)side.modX - playerSession.getPosX());
                } else {
                    center = worldBorderEmulatorTracker.getX();
                    pos = playerSession.getPosX();
                    d = Math.abs(worldBorderEmulatorTracker.getZ() + radius * (double)side.modZ - playerSession.getPosZ());
                }
                if (d >= 16.0) continue;
                double r = Math.sqrt(256.0 - d * d);
                double minH = Math.ceil(pos - r);
                double maxH = Math.floor(pos + r);
                double minV = Math.ceil(playerSession.getPosY() - r);
                double maxV = Math.floor(playerSession.getPosY() + r);
                if (minH < center - radius) {
                    minH = Math.ceil(center - radius);
                }
                if (maxH > center + radius) {
                    maxH = Math.floor(center + radius);
                }
                if (minV < 0.0) {
                    minV = 0.0;
                }
                double centerH = (minH + maxH) / 2.0;
                double centerV = (minV + maxV) / 2.0;
                double particleOffset = 2.5;
                PacketWrapper spawnParticle = PacketWrapper.create(ClientboundPackets1_8.LEVEL_PARTICLES, connection);
                spawnParticle.write(Types.STRING, ViaRewind.getConfig().getWorldBorderParticle());
                spawnParticle.write(Types.FLOAT, Float.valueOf((float)(side.modX != 0 ? worldBorderEmulatorTracker.getX() + radius * (double)side.modX : centerH)));
                spawnParticle.write(Types.FLOAT, Float.valueOf((float)centerV));
                spawnParticle.write(Types.FLOAT, Float.valueOf((float)(side.modX == 0 ? worldBorderEmulatorTracker.getZ() + radius * (double)side.modZ : centerH)));
                spawnParticle.write(Types.FLOAT, Float.valueOf((float)(side.modX != 0 ? 0.0 : (maxH - minH) / particleOffset)));
                spawnParticle.write(Types.FLOAT, Float.valueOf((float)((maxV - minV) / particleOffset)));
                spawnParticle.write(Types.FLOAT, Float.valueOf((float)(side.modX == 0 ? 0.0 : (maxH - minH) / particleOffset)));
                spawnParticle.write(Types.FLOAT, Float.valueOf(0.0f));
                spawnParticle.write(Types.INT, (int)Math.floor((maxH - minH) * (maxV - minV) * 0.5));
                try {
                    spawnParticle.send(Protocol1_8To1_7_6_10.class);
                }
                catch (Exception e) {
                    ViaRewind.getPlatform().getLogger().log(Level.SEVERE, "Failed to send world border particle", e);
                }
            }
        }
    }
}

