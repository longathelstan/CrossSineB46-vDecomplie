/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/other/VulcanStrafeDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "avoidPhaseBpValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "c03Counter", "", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class VulcanStrafeDisabler
extends DisablerMode {
    @NotNull
    private final BoolValue avoidPhaseBpValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "NoBadPacket"), true);
    @NotNull
    private final IntegerValue delayValue = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "PacketDelay"), 6, 3, 10);
    private int c03Counter;

    public VulcanStrafeDisabler() {
        super("VulcanStrafe");
    }

    @Override
    public void onEnable() {
        this.c03Counter = -15;
    }

    @Override
    public void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.c03Counter = -15;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            int n = this.c03Counter;
            this.c03Counter = n + 1;
            if (((C03PacketPlayer)packet).func_149466_j()) {
                if (this.c03Counter >= ((Number)this.delayValue.get()).intValue()) {
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, (Boolean)this.avoidPhaseBpValue.get() != false ? BlockPos.field_177992_a : new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v), EnumFacing.DOWN)));
                    this.c03Counter = 0;
                } else if (this.c03Counter == ((Number)this.delayValue.get()).intValue() - 2) {
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, BlockPos.field_177992_a, EnumFacing.DOWN)));
                }
            }
        }
    }
}

