/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.normal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/normal/MotionFlagNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "flySpeedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onNoFall", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MotionFlagNofall
extends NoFallMode {
    @NotNull
    private final FloatValue flySpeedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "MotionSpeed"), -0.01f, -5.0f, 5.0f);

    public MotionFlagNofall() {
        super("MotionFlag");
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70143_R > 3.0f) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.flySpeedValue.get()).floatValue();
        }
    }
}

