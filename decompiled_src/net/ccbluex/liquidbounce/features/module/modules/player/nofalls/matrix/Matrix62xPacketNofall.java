/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/matrix/Matrix62xPacketNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "onNoFall", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Matrix62xPacketNofall
extends NoFallMode {
    public Matrix62xPacketNofall() {
        super("Matrix6.2.X-Packet");
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!MinecraftInstance.mc.field_71439_g.field_70122_E && (double)MinecraftInstance.mc.field_71439_g.field_70143_R - MinecraftInstance.mc.field_71439_g.field_70181_x > 3.0) {
            this.getNofall().setWasTimer(true);
            MinecraftInstance.mc.field_71428_T.field_74278_d = RangesKt.coerceAtLeast(MinecraftInstance.mc.field_71428_T.field_74278_d * ((double)MinecraftInstance.mc.field_71428_T.field_74278_d < 0.6 ? 0.25f : 0.5f), 0.2f);
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(true));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(false));
            MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
        }
    }
}

