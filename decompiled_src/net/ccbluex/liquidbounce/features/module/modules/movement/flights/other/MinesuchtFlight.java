/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/MinesuchtFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MinesuchtFlight
extends FlightMode {
    @NotNull
    private final MSTimer timer = new MSTimer();

    public MinesuchtFlight() {
        super("Minesucht");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        double posX = MinecraftInstance.mc.field_71439_g.field_70165_t;
        double posY = MinecraftInstance.mc.field_71439_g.field_70163_u;
        double posZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
        if (!MinecraftInstance.mc.field_71474_y.field_74351_w.func_151470_d()) {
            return;
        }
        if (this.timer.hasTimePassed(100L)) {
            Vec3 vec3 = MinecraftInstance.mc.field_71439_g.func_174824_e(0.0f);
            Vec3 vec31 = MinecraftInstance.mc.field_71439_g.func_70676_i(0.0f);
            Vec3 vec32 = vec3.func_72441_c(vec31.field_72450_a * (double)7, vec31.field_72448_b * (double)7, vec31.field_72449_c * (double)7);
            if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R > 0.8) {
                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + (double)50, posZ, false));
                MinecraftInstance.mc.field_71439_g.func_180430_e(100.0f, 100.0f);
                MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + (double)20, posZ, true));
            }
            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vec32.field_72450_a, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)50, vec32.field_72449_c, true));
            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, false));
            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vec32.field_72450_a, posY, vec32.field_72449_c, true));
            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, false));
            this.timer.reset();
        } else {
            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, true));
        }
    }
}

