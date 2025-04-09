/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.criticals.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/other/VerusSmartCritical;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "()V", "attacks", "", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onEnable", "CrossSine"})
public final class VerusSmartCritical
extends CriticalMode {
    private int attacks;

    public VerusSmartCritical() {
        super("VerusSmart");
    }

    @Override
    public void onEnable() {
        this.attacks = 0;
    }

    @Override
    public void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int n = this.attacks;
        this.attacks = n + 1;
        if (this.attacks > 4) {
            this.attacks = 0;
            Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.001, 0.0, true, 5, null);
            Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.0, 0.0, false, 7, null);
        } else {
            this.getCritical().setAntiDesync(false);
        }
    }
}

