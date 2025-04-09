/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.criticals.normal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/normal/PacketJumpCritical;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "()V", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "CrossSine"})
public final class PacketJumpCritical
extends CriticalMode {
    public PacketJumpCritical() {
        super("PacketJump");
    }

    @Override
    public void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.41999998688698, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.7531999805212, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 1.00133597911214, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 1.16610926093821, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 1.24918707874468, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 1.1707870772188, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 1.0155550727022, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.78502770378924, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.4807108763317, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.10408037809304, 0.0, false, 5, null);
        Criticals.sendCriticalPacket$default(this.getCritical(), 0.0, 0.0, 0.0, true, 5, null);
    }
}

