/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="TickBase", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0010\u001a\u00020\u0004J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/TickBase;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "counter", "", "delayTimer", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "freezing", "", "getFreezing", "()Z", "setFreezing", "(Z)V", "ticks", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "getExtraTicks", "onEnable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class TickBase
extends Module {
    private int counter = -1;
    private boolean freezing;
    @NotNull
    private final TimerMS timer = new TimerMS();
    @NotNull
    private final IntegerValue ticks = new IntegerValue("Ticks", 3, 1, 10);
    @NotNull
    private final IntegerValue delayTimer = new IntegerValue("CoolDown", 0, 0, 5000);

    public final boolean getFreezing() {
        return this.freezing;
    }

    public final void setFreezing(boolean bl) {
        this.freezing = bl;
    }

    @Override
    public void onEnable() {
        this.counter = -1;
        this.freezing = false;
    }

    public final int getExtraTicks() {
        block5: {
            block6: {
                double d;
                int n = this.counter;
                this.counter = n + -1;
                if (n > 0) {
                    return -1;
                }
                this.freezing = false;
                if (Scaffold.INSTANCE.getState() || !this.timer.hasTimePassed(((Number)this.delayTimer.get()).intValue())) break block5;
                if (KillAura.INSTANCE.getState() && KillAura.INSTANCE.getCurrentTarget() != null && MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)KillAura.INSTANCE.getCurrentTarget()) > ((Number)KillAura.INSTANCE.getRangeValue().get()).floatValue() || SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() != null && MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)SilentAura.INSTANCE.getTarget()) > ((Number)SilentAura.INSTANCE.getReachValue().get()).floatValue()) break block6;
                boolean bl = CrossSine.INSTANCE.getCombatManager().getTarget() != null ? (3.0 <= (d = (double)MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)CrossSine.INSTANCE.getCombatManager().getTarget())) ? d <= 3.2 : false) : false;
                if (!bl) break block5;
            }
            if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 2) {
                this.counter = ((Number)this.ticks.get()).intValue();
                this.timer.reset();
                return this.counter;
            }
        }
        return 0;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.POST && this.freezing) {
            MinecraftInstance.mc.field_71439_g.field_70165_t = MinecraftInstance.mc.field_71439_g.field_70142_S;
            MinecraftInstance.mc.field_71439_g.field_70163_u = MinecraftInstance.mc.field_71439_g.field_70137_T;
            MinecraftInstance.mc.field_71439_g.field_70161_v = MinecraftInstance.mc.field_71439_g.field_70136_U;
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.freezing) {
            MinecraftInstance.mc.field_71428_T.field_74281_c = 0.0f;
        }
    }
}

