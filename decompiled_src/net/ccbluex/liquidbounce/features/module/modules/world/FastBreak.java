/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.BedAura;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="FastBreak", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/FastBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "breakDamageValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class FastBreak
extends Module {
    @NotNull
    private final FloatValue breakDamageValue = new FloatValue("BreakDamage", 0.8f, 0.1f, 1.0f);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.mc.field_71442_b.field_78781_i = 0;
        if (MinecraftInstance.mc.field_71442_b.field_78770_f > ((Number)this.breakDamageValue.get()).floatValue()) {
            MinecraftInstance.mc.field_71442_b.field_78770_f = 1.0f;
        }
        if (BedAura.INSTANCE.getCurrentDamage() > ((Number)this.breakDamageValue.get()).floatValue()) {
            BedAura.INSTANCE.setCurrentDamage(1.0f);
        }
    }
}

