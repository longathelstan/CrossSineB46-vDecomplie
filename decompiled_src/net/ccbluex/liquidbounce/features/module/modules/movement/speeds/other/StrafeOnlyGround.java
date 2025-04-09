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

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/StrafeOnlyGround;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "BoostValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onUpdate", "", "CrossSine"})
public final class StrafeOnlyGround
extends SpeedMode {
    @NotNull
    private final FloatValue BoostValue = new FloatValue("Boost", 0.0f, 0.0f, 1.0f);

    public StrafeOnlyGround() {
        super("Ground");
    }

    @Override
    public void onUpdate() {
        if (MinecraftInstance.mc.field_71439_g.field_70122_E && MovementUtils.INSTANCE.isMoving()) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, 0.0, 6, null);
            MovementUtils.INSTANCE.strafe(((Number)this.BoostValue.get()).floatValue());
        }
    }
}

