/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.vulcan;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002J\b\u0010\u0011\u001a\u00020\rH\u0016J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0016H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/VulcanFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "canClipValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getCanClipValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "canGlide", "", "ticks", "", "waitFlag", "clip", "", "dist", "", "y", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class VulcanFlight
extends FlightMode {
    @NotNull
    private final BoolValue canClipValue = new BoolValue("CanClip", true);
    private boolean waitFlag;
    private boolean canGlide;
    private int ticks;

    public VulcanFlight() {
        super("Vulcan");
    }

    @NotNull
    public final BoolValue getCanClipValue() {
        return this.canClipValue;
    }

    @Override
    public void onEnable() {
        if (MinecraftInstance.mc.field_71439_g.field_70122_E && ((Boolean)this.canClipValue.get()).booleanValue()) {
            this.clip(0.0f, -0.1f);
            this.waitFlag = true;
            this.canGlide = false;
            this.ticks = 0;
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.1f;
        } else {
            this.waitFlag = false;
            this.canGlide = true;
        }
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.PRE && this.canGlide) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
            MinecraftInstance.mc.field_71439_g.field_70181_x = -(this.ticks % 2 == 0 ? 0.17 : 0.1);
            if (this.ticks == 0) {
                MinecraftInstance.mc.field_71439_g.field_70181_x = -0.07;
            }
            int n = this.ticks;
            this.ticks = n + 1;
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook && this.waitFlag) {
            this.waitFlag = false;
            MinecraftInstance.mc.field_71439_g.func_70107_b(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c);
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, false));
            event.cancelEvent();
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
            this.clip(0.127318f, 0.0f);
            this.clip(3.425559f, 3.7f);
            this.clip(3.14285f, 3.54f);
            this.clip(2.88522f, 3.4f);
            this.canGlide = true;
        }
    }

    private final void clip(float dist, float y) {
        double yaw = Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z);
        double x = -Math.sin(yaw) * (double)dist;
        double z = Math.cos(yaw) * (double)dist;
        MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + x, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)y, MinecraftInstance.mc.field_71439_g.field_70161_v + z);
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
    }
}

