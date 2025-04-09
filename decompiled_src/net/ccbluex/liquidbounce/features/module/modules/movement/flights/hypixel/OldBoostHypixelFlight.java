/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.hypixel;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u001aH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/hypixel/OldBoostHypixelFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "boostState", "", "failedStart", "", "lastDistance", "", "moveSpeed", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "CrossSine"})
public final class OldBoostHypixelFlight
extends FlightMode {
    private int boostState = 1;
    private double moveSpeed;
    private double lastDistance;
    private boolean failedStart;
    @NotNull
    private final tickTimer timer = new tickTimer();

    public OldBoostHypixelFlight() {
        super("OldBoostHypixel");
    }

    @Override
    public void onEnable() {
        if (!MinecraftInstance.mc.field_71439_g.field_70122_E) {
            return;
        }
        int n = 10;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int it = n3 = n2++;
            boolean bl = false;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
        }
        for (double fallDistance = 3.0125; fallDistance > 0.0; fallDistance -= 0.7531999805212) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.41999998688698, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.7531999805212, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.3579E-6, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
        }
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
        MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70163_u += 0.42;
        this.boostState = 1;
        this.moveSpeed = 0.1;
        this.lastDistance = 0.0;
        this.failedStart = false;
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getFlight().setAntiDesync(true);
        if (event.getEventState() == EventState.PRE) {
            this.timer.update();
            if (this.timer.hasTimePassed(2)) {
                MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0E-5, MinecraftInstance.mc.field_71439_g.field_70161_v);
                this.timer.reset();
            }
            if (!this.failedStart) {
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
            }
        } else {
            double xDist = MinecraftInstance.mc.field_71439_g.field_70165_t - MinecraftInstance.mc.field_71439_g.field_70169_q;
            double zDist = MinecraftInstance.mc.field_71439_g.field_70161_v - MinecraftInstance.mc.field_71439_g.field_70166_s;
            this.lastDistance = Math.sqrt(xDist * xDist + zDist * zDist);
        }
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!MovementUtils.INSTANCE.isMoving()) {
            event.zeroXZ();
            return;
        }
        if (this.failedStart) {
            return;
        }
        double amplifier = 1.0 + (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c) ? 0.2 * (double)(MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76424_c).func_76458_c() + 1) : 0.0);
        double baseSpeed = 0.29 * amplifier;
        switch (this.boostState) {
            case 1: {
                this.moveSpeed = (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c) ? 1.56 : 2.034) * baseSpeed;
                this.boostState = 2;
                break;
            }
            case 2: {
                this.moveSpeed *= 2.16;
                this.boostState = 3;
                break;
            }
            case 3: {
                this.moveSpeed = this.lastDistance - (MinecraftInstance.mc.field_71439_g.field_70173_aa % 2 == 0 ? 0.0103 : 0.0123) * (this.lastDistance - baseSpeed);
                this.boostState = 4;
                break;
            }
            default: {
                this.moveSpeed = this.lastDistance - this.lastDistance / 159.8;
            }
        }
        this.moveSpeed = RangesKt.coerceAtLeast(this.moveSpeed, 0.3);
        double yaw = MovementUtils.INSTANCE.getDirection();
        event.setX(-Math.sin(yaw) * this.moveSpeed);
        event.setZ(Math.cos(yaw) * this.moveSpeed);
        MinecraftInstance.mc.field_71439_g.field_70159_w = event.getX();
        MinecraftInstance.mc.field_71439_g.field_70179_y = event.getZ();
    }

    @Override
    public void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getBlock() instanceof BlockAir && (double)event.getY() <= MinecraftInstance.mc.field_71439_g.field_70163_u) {
            event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)((double)event.getX() + 1.0), (double)MinecraftInstance.mc.field_71439_g.field_70163_u, (double)((double)event.getZ() + 1.0)));
        }
    }

    @Override
    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.cancelEvent();
    }

    @Override
    public void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.setStepHeight(0.0f);
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer)packet).field_149474_g = false;
        } else if (packet instanceof S08PacketPlayerPosLook) {
            this.failedStart = true;
            ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lBoostHypixel-\u00a7a\u00a7lFly\u00a78] \u00a7cSetback detected.");
        }
    }
}

