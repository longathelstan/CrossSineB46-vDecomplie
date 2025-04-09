/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.phases.vanilla;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/vanilla/AutomaticPhase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "aClip", "", "freezeMotionValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "offSetValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "timerValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AutomaticPhase
extends PhaseMode {
    @NotNull
    private final MSTimer timer = new MSTimer();
    @NotNull
    private final FloatValue offSetValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Offset"), 4.0f, -8.0f, 8.0f);
    @NotNull
    private final IntegerValue timerValue = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "PhaseDelay"), 1000, 500, 5000);
    @NotNull
    private final BoolValue freezeMotionValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "FreezeMotion"), true);
    private boolean aClip = true;

    public AutomaticPhase() {
        super("Automatic");
    }

    @Override
    public void onEnable() {
        this.timer.reset();
        this.aClip = true;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.timer.hasTimePassed(((Number)this.timerValue.get()).intValue())) {
            if (this.aClip) {
                this.aClip = false;
                MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - ((Number)this.offSetValue.get()).doubleValue(), MinecraftInstance.mc.field_71439_g.field_70161_v);
            }
        } else if (((Boolean)this.freezeMotionValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer && !MinecraftInstance.mc.func_147114_u().field_147309_h) {
            this.timer.reset();
            this.aClip = true;
        }
    }
}

