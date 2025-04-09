/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="MoreParticles", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/MoreParticles;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "sharpness", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "timesValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "displayEffectFor", "", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "onAttack", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "CrossSine"})
public final class MoreParticles
extends Module {
    @NotNull
    private final IntegerValue timesValue = new IntegerValue("Times", 1, 1, 10);
    @NotNull
    private final BoolValue sharpness = new BoolValue("FakeSharp", false);

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.displayEffectFor((EntityLivingBase)event.getTargetEntity());
    }

    private final void displayEffectFor(EntityLivingBase entity) {
        int n = ((Number)this.timesValue.get()).intValue();
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int it = n3 = n2++;
            boolean bl = false;
            if (((Boolean)this.sharpness.get()).booleanValue()) {
                MinecraftInstance.mc.field_71452_i.func_178926_a((Entity)entity, EnumParticleTypes.CRIT_MAGIC);
            }
            MinecraftInstance.mc.field_71452_i.func_178926_a((Entity)entity, EnumParticleTypes.CRIT);
        }
    }
}

