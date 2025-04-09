/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.phases.vanilla;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.vanilla.ClipPhase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/vanilla/ClipPhase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "tickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "onEnable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class ClipPhase
extends PhaseMode {
    @NotNull
    private final tickTimer tickTimer = new tickTimer();

    public ClipPhase() {
        super("Clip");
    }

    @Override
    public void onEnable() {
        this.tickTimer.reset();
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        AxisAlignedBB axisAlignedBB = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
        Intrinsics.checkNotNullExpressionValue(axisAlignedBB, "mc.thePlayer.entityBoundingBox");
        boolean isInsideBlock2 = BlockUtils.collideBlockIntersects(axisAlignedBB, onUpdate.isInsideBlock.1.INSTANCE);
        if (isInsideBlock2) {
            MinecraftInstance.mc.field_71439_g.field_70145_X = true;
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70122_E = true;
        }
        this.tickTimer.update();
        if (!this.tickTimer.hasTimePassed(2) || !MinecraftInstance.mc.field_71439_g.field_70123_F || isInsideBlock2 && !MinecraftInstance.mc.field_71439_g.func_70093_af()) {
            return;
        }
        double yaw = Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z);
        double oldX = MinecraftInstance.mc.field_71439_g.field_70165_t;
        double oldZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
        int i = 1;
        while (i <= 10) {
            double z;
            double x = -Math.sin(yaw) * (double)i;
            if (BlockUtils.getBlock(new BlockPos(oldX + x, MinecraftInstance.mc.field_71439_g.field_70163_u, oldZ + (z = Math.cos(yaw) * (double)i))) instanceof BlockAir && BlockUtils.getBlock(new BlockPos(oldX + x, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0, oldZ + z)) instanceof BlockAir) {
                MinecraftInstance.mc.field_71439_g.func_70107_b(oldX + x, MinecraftInstance.mc.field_71439_g.field_70163_u, oldZ + z);
                break;
            }
            int n = i;
            i = n + 1;
        }
        this.tickTimer.reset();
    }
}

