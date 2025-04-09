/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumParticleTypes;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/JetpackFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "effectValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class JetpackFlight
extends FlightMode {
    @NotNull
    private final BoolValue effectValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Effect"), true);

    public JetpackFlight() {
        super("Jetpack");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            if (((Boolean)this.effectValue.get()).booleanValue()) {
                MinecraftInstance.mc.field_71452_i.func_178927_a(EnumParticleTypes.FLAME.func_179348_c(), MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.2, MinecraftInstance.mc.field_71439_g.field_70161_v, -MinecraftInstance.mc.field_71439_g.field_70159_w, -0.5, -MinecraftInstance.mc.field_71439_g.field_70179_y, new int[0]);
            }
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70181_x += 0.15;
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70159_w *= 1.1;
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70179_y *= 1.1;
        }
    }
}

