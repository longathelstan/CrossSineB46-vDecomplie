/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/NormalStrafe;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "SpeedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "TimerValue", "Ymotion", "onUpdate", "", "CrossSine"})
public final class NormalStrafe
extends SpeedMode {
    @NotNull
    private final FloatValue Ymotion = new FloatValue("Normal-YMotion", 0.0f, 0.0f, 0.8f);
    @NotNull
    private final FloatValue SpeedValue = new FloatValue("Normal-Speed", 0.0f, 0.0f, 5.0f);
    @NotNull
    private final FloatValue TimerValue = new FloatValue("Normal-Timer", 1.0f, 1.0f, 5.0f);

    public NormalStrafe() {
        super("Normal");
    }

    @Override
    public void onUpdate() {
        if (MovementUtils.INSTANCE.isMoving()) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, 0.0, 6, null);
                MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.Ymotion.get()).floatValue();
            }
            MovementUtils.INSTANCE.strafe(((Number)this.SpeedValue.get()).floatValue());
            MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.TimerValue.get()).floatValue();
        }
    }

    private static final void onUpdate$onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }
}

