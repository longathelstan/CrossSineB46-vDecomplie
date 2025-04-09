/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.phases.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/other/MatrixBlinkPhase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "clipDistValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "flagCount", "", "matrixClip", "", "showFlagsValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "tickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "timerValue", "onEnable", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class MatrixBlinkPhase
extends PhaseMode {
    private boolean matrixClip;
    private int flagCount;
    @NotNull
    private final tickTimer tickTimer = new tickTimer();
    @NotNull
    private final FloatValue timerValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "timer"), 0.3f, 0.1f, 1.0f);
    @NotNull
    private final FloatValue clipDistValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "initialClipDistance"), 0.1f, 0.03f, 0.3f);
    @NotNull
    private final BoolValue showFlagsValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "showFlag"), true);

    public MatrixBlinkPhase() {
        super("MatrixBlink");
    }

    @Override
    public void onEnable() {
        this.matrixClip = false;
        this.tickTimer.reset();
        this.flagCount = 0;
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.flagCount > 5) {
            return;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
            this.matrixClip = true;
        }
        if (this.matrixClip) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timerValue.get()).floatValue();
            this.tickTimer.update();
            event.setX(0.0);
            event.setZ(0.0);
            if (this.tickTimer.hasTimePassed(3)) {
                this.tickTimer.reset();
                this.matrixClip = false;
            } else if (this.tickTimer.hasTimePassed(1)) {
                double offset = this.tickTimer.hasTimePassed(2) ? 1.6 : (double)((Number)this.clipDistValue.get()).floatValue();
                double direction = MovementUtils.INSTANCE.getDirection();
                MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(direction) * offset, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(direction) * offset);
            }
        } else {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
            return;
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook) {
            if (((Boolean)this.showFlagsValue.get()).booleanValue()) {
                int n = this.flagCount;
                this.flagCount = n + 1;
                ClientUtils.INSTANCE.displayChatMessage(Intrinsics.stringPlus("\u00a77[\u00a7c\u00a7lPhase\u00a77] \u00a7bFlag: \u00a7e\u00a7l", n));
            }
            if (this.flagCount < 4) {
                event.cancelEvent();
            }
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c, ((S08PacketPlayerPosLook)packet).func_148931_f(), ((S08PacketPlayerPosLook)packet).func_148930_g(), true)));
        }
    }
}

