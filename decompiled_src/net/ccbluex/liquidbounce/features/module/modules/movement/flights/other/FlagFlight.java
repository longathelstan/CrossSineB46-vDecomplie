/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/FlagFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "onEnable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class FlagFlight
extends FlightMode {
    public FlagFlight() {
        super("Flag");
    }

    @Override
    public void onEnable() {
        if (MinecraftInstance.mc.func_71356_B()) {
            this.getFlight().setState(false);
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.func_71356_B()) {
            return;
        }
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(MinecraftInstance.mc.field_71439_g.field_70165_t + MinecraftInstance.mc.field_71439_g.field_70159_w * (double)999, MinecraftInstance.mc.field_71439_g.field_70163_u + (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d() ? 1.5624 : 1.0E-8) - (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d() ? 0.0624 : 2.0E-8), MinecraftInstance.mc.field_71439_g.field_70161_v + MinecraftInstance.mc.field_71439_g.field_70179_y * (double)999, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, true));
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(MinecraftInstance.mc.field_71439_g.field_70165_t + MinecraftInstance.mc.field_71439_g.field_70159_w * (double)999, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)6969, MinecraftInstance.mc.field_71439_g.field_70161_v + MinecraftInstance.mc.field_71439_g.field_70179_y * (double)999, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, true));
        MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + MinecraftInstance.mc.field_71439_g.field_70159_w * (double)11, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + MinecraftInstance.mc.field_71439_g.field_70179_y * (double)11);
        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
    }
}

