/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.event.EntityKilledEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S45PacketTitle;

public class StatisticsUtils
implements Listenable {
    private static int kills;
    private static int deaths;
    private static int win;
    private static int totalPlayed;

    @EventTarget
    public void onTargetKilled(EntityKilledEvent e) {
        if (!(e.getTargetEntity() instanceof EntityPlayer)) {
            return;
        }
        ++kills;
    }

    @EventTarget(ignoreCondition=true)
    private void onPacket(PacketEvent e) {
        Packet<?> packet = e.getPacket();
        if (packet instanceof S45PacketTitle) {
            String title2 = ((S45PacketTitle)packet).func_179805_b().func_150254_d();
            if (title2.contains("Winner")) {
                ++win;
            }
            if (title2.contains("BedWar")) {
                ++totalPlayed;
            }
            if (title2.contains("SkyWar")) {
                ++totalPlayed;
            }
        }
    }

    public static void addDeaths() {
        ++deaths;
    }

    public static int getDeaths() {
        return deaths;
    }

    public static int getKills() {
        return kills;
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

