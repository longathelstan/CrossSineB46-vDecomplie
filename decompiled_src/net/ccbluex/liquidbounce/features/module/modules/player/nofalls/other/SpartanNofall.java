/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/other/SpartanNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "spartanTimer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "onEnable", "", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class SpartanNofall
extends NoFallMode {
    @NotNull
    private final tickTimer spartanTimer = new tickTimer();

    public SpartanNofall() {
        super("Spartan");
    }

    @Override
    public void onEnable() {
        this.spartanTimer.reset();
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.spartanTimer.update();
        if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R > 1.5 && this.spartanTimer.hasTimePassed(10)) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)10, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)10, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            this.spartanTimer.reset();
        }
    }
}

