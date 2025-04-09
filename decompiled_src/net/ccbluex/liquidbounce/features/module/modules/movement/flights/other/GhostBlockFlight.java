/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/GhostBlockFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "lastGroundY", "", "onDisable", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class GhostBlockFlight
extends FlightMode {
    private double lastGroundY;

    public GhostBlockFlight() {
        super("GhostBlock");
    }

    @Override
    public void onEnable() {
        this.lastGroundY = MinecraftInstance.mc.field_71439_g.field_70163_u;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        BlockPos blockPos;
        Intrinsics.checkNotNullParameter(event, "event");
        if (InventoryUtils.INSTANCE.findAutoBlockBlock(true) == -1) {
            return;
        }
        RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0f), 1);
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
        }
        if (BlockUtils.isReplaceable(blockPos = new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, this.lastGroundY - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v))) {
            if (PlayerUtils.getOffGroundTicks() == 9) {
                SlotUtils.INSTANCE.setSlot(InventoryUtils.INSTANCE.findAutoBlockBlock(true) - 36, true, this.getFlight().getName());
            }
            if (PlayerUtils.getOffGroundTicks() >= 10) {
                MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.func_70694_bm(), blockPos, EnumFacing.UP, new Vec3(MinecraftInstance.mc.field_71439_g.field_70165_t, this.lastGroundY - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v));
                MinecraftInstance.mc.field_71439_g.func_71038_i();
            }
            if (PlayerUtils.getOffGroundTicks() <= 1) {
                SlotUtils.INSTANCE.stopSet();
            }
        }
    }

    @Override
    public void onDisable() {
        SlotUtils.INSTANCE.stopSet();
    }
}

