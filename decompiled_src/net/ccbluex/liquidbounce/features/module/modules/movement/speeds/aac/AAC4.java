/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016\u00a8\u0006\u0006"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/aac/AAC4;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onUpdate", "CrossSine"})
public final class AAC4
extends SpeedMode {
    public AAC4() {
        super("AAC4");
    }

    @Override
    public void onUpdate() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        if (!MovementUtils.INSTANCE.isMoving()) {
            return;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, 0.0, 6, null);
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        }
        if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R > 0.7 && (double)MinecraftInstance.mc.field_71439_g.field_70143_R < 1.3) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.08f;
        }
    }

    @Override
    public void onDisable() {
        Intrinsics.checkNotNull(MinecraftInstance.mc.field_71439_g);
        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.02f;
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }
}

