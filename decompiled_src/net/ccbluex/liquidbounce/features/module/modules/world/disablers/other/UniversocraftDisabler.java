/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.other;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S07PacketRespawn;

public class UniversocraftDisabler
extends DisablerMode {
    private boolean disabling;
    private int ticks;

    public UniversocraftDisabler() {
        super("Universocraft");
    }

    @Override
    @EventTarget
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        ++this.ticks;
        if (packet instanceof S07PacketRespawn) {
            this.disabling = true;
        } else if (packet instanceof C02PacketUseEntity) {
            this.disabling = false;
        } else if (packet instanceof C03PacketPlayer && UniversocraftDisabler.mc.field_71439_g.field_70173_aa <= 10) {
            this.disabling = true;
        } else if (packet instanceof C0FPacketConfirmTransaction && this.disabling && UniversocraftDisabler.mc.field_71439_g.field_70173_aa < 350) {
            if (this.ticks >= 1) {
                this.ticks = 0;
                ((C0FPacketConfirmTransaction)packet).field_149534_b = Short.MIN_VALUE;
            } else {
                ((C0FPacketConfirmTransaction)packet).field_149534_b = Short.MAX_VALUE;
            }
        }
    }
}

