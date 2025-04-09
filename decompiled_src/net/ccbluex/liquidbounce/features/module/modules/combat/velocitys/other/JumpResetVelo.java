/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.BackTrack;
import net.ccbluex.liquidbounce.features.module.modules.combat.FakeLag;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/other/JumpResetVelo;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onVelocityPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class JumpResetVelo
extends VelocityMode {
    public JumpResetVelo() {
        super("JumpReset");
    }

    @Override
    public void onVelocityPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!BackTrack.INSTANCE.getState() && !FakeLag.INSTANCE.getState() && MinecraftInstance.mc.field_71439_g.field_70122_E && RandomUtils.nextInt(1, 100) <= ((Number)this.getVelocity().getChance().get()).intValue()) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if ((BackTrack.INSTANCE.getState() || !FakeLag.INSTANCE.getState()) && MinecraftInstance.mc.field_71439_g.field_70737_aN >= 9 && MinecraftInstance.mc.field_71439_g.field_70122_E && RandomUtils.nextInt(1, 100) <= ((Number)this.getVelocity().getChance().get()).intValue()) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
        }
    }
}

