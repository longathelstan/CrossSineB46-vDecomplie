/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="Timer", category=ModuleCategory.PLAYER, autoDisable=EnumAutoDisableType.RESPAWN)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/Timer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onMoveValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onStrafeValue", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Timer
extends Module {
    @NotNull
    private final FloatValue speedValue = new FloatValue("Speed", 2.0f, 0.1f, 10.0f);
    @NotNull
    private final BoolValue onMoveValue = new BoolValue("OnlyMove", true);
    @NotNull
    private final BoolValue onStrafeValue = new BoolValue("OnlyStrafe", false);

    @Override
    public void onDisable() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.mc.field_71428_T.field_74278_d = (Boolean)this.onMoveValue.get() != false && MovementUtils.INSTANCE.isMoving() || (Boolean)this.onStrafeValue.get() != false && MovementUtils.INSTANCE.isStrafing() ? ((Number)this.speedValue.get()).floatValue() : ((Number)this.speedValue.get()).floatValue();
    }

    @Override
    @Nullable
    public String getTag() {
        return String.valueOf(((Number)this.speedValue.get()).floatValue());
    }
}

