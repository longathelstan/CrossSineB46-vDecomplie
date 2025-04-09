/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.phases.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/other/MineplexPhase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "mineplexClip", "", "ticktimer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "onEnable", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "CrossSine"})
public final class MineplexPhase
extends PhaseMode {
    private boolean mineplexClip;
    @NotNull
    private final tickTimer ticktimer = new tickTimer();

    public MineplexPhase() {
        super("Mineplex");
    }

    @Override
    public void onEnable() {
        this.mineplexClip = false;
        this.ticktimer.reset();
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
            this.mineplexClip = true;
        }
        if (!this.mineplexClip) {
            return;
        }
        this.ticktimer.update();
        event.setX(0.0);
        event.setZ(0.0);
        if (this.ticktimer.hasTimePassed(3)) {
            this.ticktimer.reset();
            this.mineplexClip = false;
        } else if (this.ticktimer.hasTimePassed(1)) {
            double offset = this.ticktimer.hasTimePassed(2) ? 1.6 : 0.06;
            double direction = MovementUtils.INSTANCE.getDirection();
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(direction) * offset, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(direction) * offset);
        }
    }
}

