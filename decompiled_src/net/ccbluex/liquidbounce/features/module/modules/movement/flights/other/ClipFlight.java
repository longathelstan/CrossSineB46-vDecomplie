/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/ClipFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "groundValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "lastJump", "", "motionXValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "motionYValue", "motionZValue", "spoofValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "timerValue", "xValue", "yValue", "zValue", "onEnable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class ClipFlight
extends FlightMode {
    @NotNull
    private final FloatValue xValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "X"), 2.0f, -5.0f, 5.0f);
    @NotNull
    private final FloatValue yValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Y"), 2.0f, -5.0f, 5.0f);
    @NotNull
    private final FloatValue zValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Z"), 2.0f, -5.0f, 5.0f);
    @NotNull
    private final IntegerValue delayValue = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "Delay"), 500, 0, 3000);
    @NotNull
    private final FloatValue motionXValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "MotionX"), 0.0f, -1.0f, 1.0f);
    @NotNull
    private final FloatValue motionYValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "MotionY"), 0.0f, -1.0f, 1.0f);
    @NotNull
    private final FloatValue motionZValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "MotionZ"), 0.0f, -1.0f, 1.0f);
    @NotNull
    private final BoolValue spoofValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "SpoofGround"), false);
    @NotNull
    private final BoolValue groundValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "GroundWhenClip"), true);
    @NotNull
    private final FloatValue timerValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Timer"), 0.7f, 0.02f, 2.5f);
    @NotNull
    private final MSTimer timer = new MSTimer();
    private boolean lastJump;

    public ClipFlight() {
        super("Clip");
    }

    @Override
    public void onEnable() {
        this.timer.reset();
        this.lastJump = false;
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() != EventState.POST) {
            return;
        }
        MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timerValue.get()).floatValue();
        MinecraftInstance.mc.field_71439_g.field_70159_w = ((Number)this.motionXValue.get()).floatValue();
        MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.motionYValue.get()).floatValue();
        MinecraftInstance.mc.field_71439_g.field_70179_y = ((Number)this.motionZValue.get()).floatValue();
        if (this.timer.hasTimePassed(((Number)this.delayValue.get()).intValue())) {
            double yaw = Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z);
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(yaw) * ((Number)this.xValue.get()).doubleValue(), MinecraftInstance.mc.field_71439_g.field_70163_u + ((Number)this.yValue.get()).doubleValue(), MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(yaw) * ((Number)this.zValue.get()).doubleValue());
            this.timer.reset();
            this.lastJump = true;
        }
        MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            if (((Boolean)this.spoofValue.get()).booleanValue()) {
                ((C03PacketPlayer)packet).field_149474_g = true;
            }
            if (((Boolean)this.groundValue.get()).booleanValue() && (this.timer.hasTimePassed(((Number)this.delayValue.get()).intValue()) || this.lastJump)) {
                ((C03PacketPlayer)packet).field_149474_g = true;
                this.lastJump = false;
            }
        }
    }
}

