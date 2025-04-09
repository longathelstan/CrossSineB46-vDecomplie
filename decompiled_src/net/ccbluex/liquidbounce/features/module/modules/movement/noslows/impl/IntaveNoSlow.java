/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.noslows.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.noslows.NoSlowMode;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/impl/IntaveNoSlow;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/NoSlowMode;", "()V", "onPreMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "slow", "", "CrossSine"})
public final class IntaveNoSlow
extends NoSlowMode {
    public IntaveNoSlow() {
        super("Intave");
    }

    @Override
    public void onPreMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.getHoldConsume()) {
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.UP)));
        }
    }

    @Override
    public float slow() {
        if (this.getHoldConsume()) {
            return 1.0f;
        }
        return 0.2f;
    }
}

