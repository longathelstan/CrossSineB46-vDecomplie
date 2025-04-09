/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.phases.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.aac.AAC350Phase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/aac/AAC350Phase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "tickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AAC350Phase
extends PhaseMode {
    @NotNull
    private final tickTimer tickTimer = new tickTimer();

    public AAC350Phase() {
        super("AAC3.5.0");
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
        double x = -Math.sin(yaw);
        double z = Math.cos(yaw);
        MinecraftInstance.mc.field_71439_g.func_70107_b(oldX + x, MinecraftInstance.mc.field_71439_g.field_70163_u, oldZ + z);
        this.tickTimer.reset();
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            float yaw = (float)MovementUtils.INSTANCE.getDirection();
            ((C03PacketPlayer)packet).field_149479_a -= (double)MathHelper.func_76126_a((float)yaw) * 1.0E-8;
            ((C03PacketPlayer)packet).field_149478_c += (double)MathHelper.func_76134_b((float)yaw) * 1.0E-8;
        }
    }
}

