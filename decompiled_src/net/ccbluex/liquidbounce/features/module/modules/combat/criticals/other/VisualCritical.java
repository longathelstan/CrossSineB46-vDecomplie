/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.criticals.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0017\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/other/VisualCritical;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "()V", "displayEffectFor", "", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "onAttack", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "CrossSine"})
public final class VisualCritical
extends CriticalMode {
    public VisualCritical() {
        super("Visual");
    }

    @Override
    @EventTarget
    public void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.displayEffectFor((EntityLivingBase)event.getTargetEntity());
    }

    private final void displayEffectFor(EntityLivingBase entity) {
        MinecraftInstance.mc.field_71452_i.func_178926_a((Entity)entity, EnumParticleTypes.CRIT);
    }
}

