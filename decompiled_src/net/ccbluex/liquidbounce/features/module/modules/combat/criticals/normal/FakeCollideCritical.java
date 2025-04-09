/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.criticals.normal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.stats.StatList;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/normal/FakeCollideCritical;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "()V", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "CrossSine"})
public final class FakeCollideCritical
extends CriticalMode {
    public FakeCollideCritical() {
        super("FakeCollide");
    }

    @Override
    public void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        double motionX = 0.0;
        double motionZ = 0.0;
        if (MovementUtils.INSTANCE.isMoving()) {
            motionX = MinecraftInstance.mc.field_71439_g.field_70159_w;
            motionZ = MinecraftInstance.mc.field_71439_g.field_70179_y;
        } else {
            motionX = 0.0;
            motionZ = 0.0;
        }
        MinecraftInstance.mc.field_71439_g.func_71029_a(StatList.field_75953_u);
        this.getCritical().sendCriticalPacket(motionX / (double)3, 0.20000004768372, motionZ / (double)3, false);
        this.getCritical().sendCriticalPacket(motionX / 1.5, 0.12160004615784, motionZ / 1.5, false);
    }
}

