/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.ncp;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class LatestNCP
extends FlightMode {
    final BoolValue teleportValue = new BoolValue("Teleport", false);
    final BoolValue timerValue = new BoolValue("Timer", true);
    final FloatValue addValue = new FloatValue("AddSpeed", 0.0f, 0.0f, 1.5f);
    private boolean started;
    private boolean notUnder;
    private boolean clipped;

    public LatestNCP() {
        super("LatestNCP");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    @EventTarget
    public void onUpdate(UpdateEvent event) {
        block11: {
            block10: {
                block9: {
                    AxisAlignedBB bb;
                    if (((Boolean)this.timerValue.get()).booleanValue()) {
                        LatestNCP.mc.field_71428_T.field_74278_d = !LatestNCP.mc.field_71439_g.field_70122_E ? 0.4f : 1.0f;
                    }
                    if (LatestNCP.mc.field_71441_e.func_72945_a((Entity)LatestNCP.mc.field_71439_g, bb = LatestNCP.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, 1.0, 0.0)).isEmpty()) break block9;
                    if (!this.started) break block10;
                }
                switch (PlayerUtils.INSTANCE.getOffGroundTicks()) {
                    case 0: {
                        if (this.notUnder && this.clipped) {
                            this.started = true;
                            MovementUtils.INSTANCE.strafe(9.5 + (double)((Float)this.addValue.get()).floatValue());
                            LatestNCP.mc.field_71439_g.field_70181_x = 0.42f;
                            this.notUnder = false;
                            break;
                        }
                        break block11;
                    }
                    case 1: {
                        if (this.started) {
                            MovementUtils.INSTANCE.strafe(8.0 + (double)((Float)this.addValue.get()).floatValue());
                            break;
                        }
                        break block11;
                    }
                }
                break block11;
            }
            this.notUnder = true;
            if (this.clipped) {
                return;
            }
            this.clipped = true;
            if (((Boolean)this.teleportValue.get()).booleanValue()) {
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)new C03PacketPlayer.C06PacketPlayerPosLook(LatestNCP.mc.field_71439_g.field_70165_t, LatestNCP.mc.field_71439_g.field_70163_u, LatestNCP.mc.field_71439_g.field_70161_v, LatestNCP.mc.field_71439_g.field_70177_z, LatestNCP.mc.field_71439_g.field_70125_A, false));
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)new C03PacketPlayer.C06PacketPlayerPosLook(LatestNCP.mc.field_71439_g.field_70165_t, LatestNCP.mc.field_71439_g.field_70163_u - 0.1, LatestNCP.mc.field_71439_g.field_70161_v, LatestNCP.mc.field_71439_g.field_70177_z, LatestNCP.mc.field_71439_g.field_70125_A, false));
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)new C03PacketPlayer.C06PacketPlayerPosLook(LatestNCP.mc.field_71439_g.field_70165_t, LatestNCP.mc.field_71439_g.field_70163_u, LatestNCP.mc.field_71439_g.field_70161_v, LatestNCP.mc.field_71439_g.field_70177_z, LatestNCP.mc.field_71439_g.field_70125_A, false));
            }
        }
        MovementUtils.INSTANCE.strafe();
    }

    @Override
    public void onEnable() {
        this.notUnder = false;
        this.started = false;
        this.clipped = false;
    }
}

