/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0016H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/MinemoraFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "boost", "", "disableLogger", "noGround", "packetBuffer", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "tick", "", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MinemoraFlight
extends FlightMode {
    private int tick;
    private boolean boost;
    private boolean noGround;
    private boolean disableLogger;
    @NotNull
    private final LinkedBlockingQueue<Packet<INetHandlerPlayServer>> packetBuffer = new LinkedBlockingQueue();

    public MinemoraFlight() {
        super("Minemora");
    }

    @Override
    public void onEnable() {
        this.noGround = !MinecraftInstance.mc.field_71439_g.field_70122_E;
        this.boost = false;
        this.tick = 0;
        MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
        MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onDisable() {
        this.tick = 0;
        try {
            this.disableLogger = true;
            while (!this.packetBuffer.isEmpty()) {
                MinecraftInstance.mc.func_147114_u().func_147297_a(this.packetBuffer.take());
            }
            this.disableLogger = false;
        }
        finally {
            this.disableLogger = false;
        }
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (MinecraftInstance.mc.field_71439_g == null || this.disableLogger) {
            return;
        }
        if (packet instanceof C03PacketPlayer) {
            event.cancelEvent();
        }
        if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook || packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C0APacketAnimation || packet instanceof C0BPacketEntityAction || packet instanceof C02PacketUseEntity) {
            event.cancelEvent();
            this.packetBuffer.add(packet);
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getFlight().setAntiDesync(false);
        if (this.boost) {
            MovementUtils.INSTANCE.resetMotion(false);
            int n = 10;
            int n2 = 0;
            while (n2 < n) {
                int n3;
                int it = n3 = n2++;
                boolean bl = false;
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            }
            this.getFlight().setState(false);
        }
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() != EventState.PRE) {
            return;
        }
        int n = this.tick;
        this.tick = n + 1;
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        if (this.tick == 1) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.25f;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)3.42f, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
        } else {
            if (MovementUtils.INSTANCE.isMoving()) {
                MovementUtils.INSTANCE.strafe(1.7f);
            } else {
                MovementUtils.INSTANCE.resetMotion(false);
            }
            if (MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e) {
                MinecraftInstance.mc.field_71439_g.field_70181_x = 1.7;
            } else if (MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e) {
                MinecraftInstance.mc.field_71439_g.field_70181_x = -1.7;
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    if (!this.noGround) {
                        this.getFlight().setState(false);
                    } else {
                        this.boost = true;
                    }
                }
            } else {
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
            }
        }
    }
}

