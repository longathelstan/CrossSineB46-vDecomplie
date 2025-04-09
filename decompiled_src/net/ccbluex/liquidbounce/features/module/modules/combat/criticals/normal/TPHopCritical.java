/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.criticals.normal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/normal/TPHopCritical;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "()V", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "CrossSine"})
public final class TPHopCritical
extends CriticalMode {
    public TPHopCritical() {
        super("TPHop");
    }

    @Override
    public void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.02, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.01, 0.0, false, 5, null);
        MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.01, MinecraftInstance.mc.field_71439_g.field_70161_v);
    }
}

