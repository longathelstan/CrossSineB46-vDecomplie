/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.rewinside;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/rewinside/TeleportRewinsideFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class TeleportRewinsideFlight
extends FlightMode {
    public TeleportRewinsideFlight() {
        super("TeleportRewinside");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Vec3 vectorStart = new Vec3(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v);
        float yaw = -MinecraftInstance.mc.field_71439_g.field_70177_z;
        float pitch = -MinecraftInstance.mc.field_71439_g.field_70125_A;
        double length = 9.9;
        Vec3 vectorEnd = new Vec3(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * length + vectorStart.field_72450_a, Math.sin(Math.toRadians(pitch)) * length + vectorStart.field_72448_b, Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)) * length + vectorStart.field_72449_c);
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vectorEnd.field_72450_a, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2, vectorEnd.field_72449_c, true));
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vectorStart.field_72450_a, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2, vectorStart.field_72449_c, true));
        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
    }
}

