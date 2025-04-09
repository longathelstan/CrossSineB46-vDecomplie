/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/aac/AAC1910Flight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "aacJump", "", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onEnable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AAC1910Flight
extends FlightMode {
    @NotNull
    private final FloatValue speedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 0.3f, 0.2f, 1.7f);
    private double aacJump;

    public AAC1910Flight() {
        super("AAC1.9.10");
    }

    @Override
    public void onEnable() {
        this.aacJump = -3.8;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            this.aacJump += 0.2;
        }
        if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
            this.aacJump -= 0.2;
        }
        if (this.getFlight().getLaunchY() + this.aacJump > MinecraftInstance.mc.field_71439_g.field_70163_u) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(true));
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.8;
            MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
        }
        MovementUtils.INSTANCE.strafe();
    }
}

