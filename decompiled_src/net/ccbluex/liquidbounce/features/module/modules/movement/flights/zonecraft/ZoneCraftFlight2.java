/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.zonecraft;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/zonecraft/ZoneCraftFlight2;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "onMove", "", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "CrossSine"})
public final class ZoneCraftFlight2
extends FlightMode {
    public ZoneCraftFlight2() {
        super("ZoneCraft2");
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
        } else {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
            MovementUtils.INSTANCE.strafe(0.259f);
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70163_u -= MinecraftInstance.mc.field_71439_g.field_70163_u % 0.1152;
        }
    }
}

