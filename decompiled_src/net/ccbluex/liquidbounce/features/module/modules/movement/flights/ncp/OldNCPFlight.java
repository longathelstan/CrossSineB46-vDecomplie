/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/OldNCPFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "onEnable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class OldNCPFlight
extends FlightMode {
    public OldNCPFlight() {
        super("OldNCP");
    }

    @Override
    public void onEnable() {
        if (!MinecraftInstance.mc.field_71439_g.field_70122_E) {
            return;
        }
        int n = 3;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int it = n3 = n2++;
            boolean bl = false;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.01, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
        }
        MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
        MinecraftInstance.mc.field_71439_g.func_71038_i();
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getFlight().setAntiDesync(true);
        if (this.getFlight().getLaunchY() > MinecraftInstance.mc.field_71439_g.field_70163_u) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -1.0E-33;
        }
        if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -0.2;
        }
        if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d() && MinecraftInstance.mc.field_71439_g.field_70163_u < this.getFlight().getLaunchY() - 0.1) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.2;
        }
        MovementUtils.INSTANCE.strafe();
    }
}

