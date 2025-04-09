/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.phases.vanilla;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.vanilla.FastFallPhase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/vanilla/FastFallPhase;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "()V", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class FastFallPhase
extends PhaseMode {
    public FastFallPhase() {
        super("FastFall");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.mc.field_71439_g.field_70145_X = true;
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP.field_70181_x -= 10.0;
        MinecraftInstance.mc.field_71439_g.func_70634_a(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.5, MinecraftInstance.mc.field_71439_g.field_70161_v);
        EntityPlayerSP entityPlayerSP2 = MinecraftInstance.mc.field_71439_g;
        entityPlayerSP = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer.entityBoundingBox");
        entityPlayerSP2.field_70122_E = BlockUtils.collideBlockIntersects((AxisAlignedBB)entityPlayerSP, onUpdate.1.INSTANCE);
    }
}

