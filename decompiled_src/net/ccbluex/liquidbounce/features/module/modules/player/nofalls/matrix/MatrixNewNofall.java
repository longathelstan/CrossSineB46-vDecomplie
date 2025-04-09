/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.matrix;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/matrix/MatrixNewNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class MatrixNewNofall
extends NoFallMode {
    public MatrixNewNofall() {
        super("MatrixNew");
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C03PacketPlayer) {
            if (!MinecraftInstance.mc.field_71439_g.field_70122_E) {
                if (MinecraftInstance.mc.field_71439_g.field_70143_R > 2.69f) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 0.3f;
                    ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
                    MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
                }
                MinecraftInstance.mc.field_71428_T.field_74278_d = (double)MinecraftInstance.mc.field_71439_g.field_70143_R > 3.5 ? 0.3f : 1.0f;
            }
            List list = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, MinecraftInstance.mc.field_71439_g.field_70181_x, 0.0));
            Intrinsics.checkNotNullExpressionValue(list, "mc.theWorld.getColliding\u2026.thePlayer.motionY, 0.0))");
            if (!((Collection)list).isEmpty() && !((C03PacketPlayer)event.getPacket()).func_149465_i() && MinecraftInstance.mc.field_71439_g.field_70181_x < -0.6) {
                ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
            }
        }
    }
}

