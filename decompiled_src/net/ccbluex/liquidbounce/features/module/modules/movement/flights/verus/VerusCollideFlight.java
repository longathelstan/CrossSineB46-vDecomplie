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
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/verus/VerusCollideFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "justEnabled", "", "ticks", "", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "CrossSine"})
public final class VerusCollideFlight
extends FlightMode {
    private int ticks;
    private boolean justEnabled = true;

    public VerusCollideFlight() {
        super("VerusCollide");
    }

    @Override
    public void onEnable() {
        this.ticks = 0;
        this.justEnabled = true;
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
        MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
        if (this.ticks % 14 == 0 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
            this.justEnabled = false;
            MovementUtils.INSTANCE.strafe(0.69f);
            event.setY(0.42);
            this.ticks = 0;
            MinecraftInstance.mc.field_71439_g.field_70181_x = -(MinecraftInstance.mc.field_71439_g.field_70163_u - Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u));
        } else {
            if (GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74314_A) && this.ticks % 2 == 1 && MinecraftInstance.mc.field_71439_g.field_70173_aa % 2 == 0) {
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                MovementUtils.INSTANCE.strafe(0.3f);
            }
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                if (!this.justEnabled) {
                    MovementUtils.INSTANCE.strafe(1.01f);
                }
            } else {
                MovementUtils.INSTANCE.strafe(0.41f);
            }
        }
        int n = this.ticks;
        this.ticks = n + 1;
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

