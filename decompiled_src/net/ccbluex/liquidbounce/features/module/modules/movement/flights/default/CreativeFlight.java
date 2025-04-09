/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.default;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016\u00a8\u0006\u0006"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/default/CreativeFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "onDisable", "", "onEnable", "CrossSine"})
public final class CreativeFlight
extends FlightMode {
    public CreativeFlight() {
        super("Creative");
    }

    @Override
    public void onEnable() {
        MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = true;
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
    }
}

