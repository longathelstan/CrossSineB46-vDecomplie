/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.verus;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/verus/VerusCustomFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "airSpeedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "groundSpeedValue", "hopDelayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "onlyOnGround", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "waitTicks", "", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "CrossSine"})
public final class VerusCustomFlight
extends FlightMode {
    @NotNull
    private final FloatValue airSpeedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "AirSpeed"), 0.5f, 0.0f, 1.0f);
    @NotNull
    private final FloatValue groundSpeedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "GroundSpeed"), 0.42f, 0.0f, 1.0f);
    @NotNull
    private final IntegerValue hopDelayValue = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "HopDelay"), 3, 0, 10);
    @NotNull
    private final BoolValue onlyOnGround = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "OnlyEnableOnGround"), true);
    private int waitTicks;

    public VerusCustomFlight() {
        super("VerusCustom");
    }

    @Override
    public void onEnable() {
        if (!MinecraftInstance.mc.field_71439_g.field_70122_E && ((Boolean)this.onlyOnGround.get()).booleanValue()) {
            return;
        }
        this.waitTicks = 0;
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MovementUtils.INSTANCE.isMoving()) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                MovementUtils.INSTANCE.strafe(((Number)this.groundSpeedValue.get()).floatValue());
                int n = this.waitTicks;
                this.waitTicks = n + 1;
                if (this.waitTicks >= ((Number)this.hopDelayValue.get()).intValue()) {
                    this.waitTicks = 0;
                    MinecraftInstance.mc.field_71439_g.func_71029_a(StatList.field_75953_u);
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                    event.setY(0.41999998688698);
                }
            } else {
                MovementUtils.INSTANCE.strafe(((Number)this.airSpeedValue.get()).floatValue());
            }
        }
    }

    @Override
    public void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getBlock() instanceof BlockAir && (double)event.getY() <= this.getFlight().getLaunchY()) {
            event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)((double)event.getX() + 1.0), (double)this.getFlight().getLaunchY(), (double)((double)event.getZ() + 1.0)));
        }
    }

    @Override
    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.cancelEvent();
    }
}

