/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/aac/AAC305Flight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "delay", "", "fastValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AAC305Flight
extends FlightMode {
    @NotNull
    private final BoolValue fastValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Fast"), true);
    private int delay;

    public AAC305Flight() {
        super("AAC3.0.5");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.delay == 2) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
        } else if (this.delay > 2) {
            this.delay = 0;
        }
        if (((Boolean)this.fastValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_70747_aH = (double)MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a == 0.0 ? 0.08f : 0.0f;
        }
        int n = this.delay;
        this.delay = n + 1;
    }
}

