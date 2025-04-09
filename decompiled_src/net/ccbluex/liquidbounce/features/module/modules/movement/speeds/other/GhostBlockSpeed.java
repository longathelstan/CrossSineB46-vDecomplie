/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016\u00a8\u0006\u0006"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/GhostBlockSpeed;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onUpdate", "CrossSine"})
public final class GhostBlockSpeed
extends SpeedMode {
    public GhostBlockSpeed() {
        super("GhostBlock");
    }

    @Override
    public void onUpdate() {
        if (InventoryUtils.INSTANCE.findAutoBlockBlock(true) == -1) {
            return;
        }
        RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, -90.0f), 1);
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, 0.0, 6, null);
        }
        BlockPos blockPos = new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2, MinecraftInstance.mc.field_71439_g.field_70161_v);
        if (MinecraftInstance.mc.field_71439_g.field_70122_E && BlockUtils.isReplaceable(blockPos)) {
            SlotUtils.INSTANCE.setSlot(InventoryUtils.INSTANCE.findAutoBlockBlock(true) - 36, true, this.getSpeed().getName());
            MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.func_70694_bm(), blockPos, EnumFacing.UP, new Vec3(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2, MinecraftInstance.mc.field_71439_g.field_70161_v));
            MinecraftInstance.mc.field_71439_g.func_71038_i();
        }
    }

    @Override
    public void onDisable() {
        SlotUtils.INSTANCE.stopSet();
    }
}

