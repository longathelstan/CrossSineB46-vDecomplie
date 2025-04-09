/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/JumpFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "startY", "", "onBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "CrossSine"})
public final class JumpFlight
extends FlightMode {
    private double startY;

    public JumpFlight() {
        super("Jump");
    }

    @EventTarget
    public final void onBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)(event.getX() + 1), (double)this.startY, (double)(event.getZ() + 1)));
    }
}

