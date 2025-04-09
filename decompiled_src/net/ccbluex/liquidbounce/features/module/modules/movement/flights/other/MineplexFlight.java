/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0012H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/other/MineplexFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MineplexFlight
extends FlightMode {
    @NotNull
    private final FloatValue speedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 1.0f, 0.5f, 10.0f);
    @NotNull
    private final MSTimer timer = new MSTimer();

    public MineplexFlight() {
        super("Mineplex");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g() == null) {
            if (MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d() && this.timer.hasTimePassed(100L)) {
                MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.6, MinecraftInstance.mc.field_71439_g.field_70161_v);
                this.timer.reset();
            }
            if (MinecraftInstance.mc.field_71439_g.func_70093_af() && this.timer.hasTimePassed(100L)) {
                MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.6, MinecraftInstance.mc.field_71439_g.field_70161_v);
                this.timer.reset();
            }
            BlockPos blockPos = new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v);
            Vec3 vec = new Vec3((Vec3i)blockPos).func_72441_c(0.4, 0.4, 0.4).func_178787_e(new Vec3(EnumFacing.UP.func_176730_m()));
            MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g(), blockPos, EnumFacing.UP, new Vec3(vec.field_72450_a * (double)0.4f, vec.field_72448_b * (double)0.4f, vec.field_72449_c * (double)0.4f));
            MovementUtils.INSTANCE.strafe(0.27f);
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f + ((Number)this.speedValue.get()).floatValue();
        } else {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
            this.getFlight().setState(false);
            ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lMineplex-\u00a7a\u00a7lFly\u00a78] \u00a7aSelect an empty slot to fly.");
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer)packet).field_149474_g = true;
        }
    }

    @Override
    public void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getBlock() instanceof BlockAir && (double)event.getY() <= MinecraftInstance.mc.field_71439_g.field_70163_u) {
            event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)((double)event.getX() + 1.0), (double)MinecraftInstance.mc.field_71439_g.field_70163_u, (double)((double)event.getZ() + 1.0)));
        }
    }

    @Override
    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.cancelEvent();
    }

    @Override
    public void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.setStepHeight(0.0f);
    }
}

