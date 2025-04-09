/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0016J\b\u0010\u0010\u001a\u00020\fH\u0016J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0016H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/matrix/Matrix117Flight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "airCount", "", "dontPlace", "", "hasEmptySlot", "resetFallDistValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "yChanged", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Matrix117Flight
extends FlightMode {
    @NotNull
    private final BoolValue resetFallDistValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "-SpoofGround"), true);
    private boolean dontPlace;
    private int airCount;
    private boolean yChanged;
    private boolean hasEmptySlot;

    public Matrix117Flight() {
        super("Matrix1.17");
    }

    @Override
    public void onEnable() {
        this.dontPlace = true;
        this.yChanged = false;
        this.hasEmptySlot = false;
        int n = 0;
        while (n < 9) {
            int i;
            if (MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a[i = n++] != null) continue;
            this.hasEmptySlot = true;
        }
        if (!this.hasEmptySlot) {
            this.getFlight().setState(false);
            ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lMatrix1.17-\u00a7a\u00a7lFly\u00a78] \u00a7aYou need to have an empty slot to fly.");
        }
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.func_152344_a(Matrix117Flight::onDisable$lambda-0);
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.PRE && ((Boolean)this.resetFallDistValue.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.field_70163_u < this.getFlight().getLaunchY() + 0.15 && MinecraftInstance.mc.field_71439_g.field_70163_u > this.getFlight().getLaunchY() + 0.05) {
            int n = this.airCount;
            this.airCount = n + 1;
            if (this.airCount >= 3) {
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, true)));
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, null, 0.0f, 0.0f, 0.0f));
            }
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.yChanged) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
        }
        int n = 0;
        while (n < 9) {
            int i;
            if (MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a[i = n++] != null) continue;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(i));
            break;
        }
        if (!this.dontPlace || MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0 > this.getFlight().getLaunchY()) {
            this.dontPlace = true;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, null, 0.0f, 0.0f, 0.0f));
        }
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            if (!this.yChanged) {
                if (MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e) {
                    this.yChanged = true;
                    Flight flight = this.getFlight();
                    flight.setLaunchY(flight.getLaunchY() + 1.0);
                } else if (MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e) {
                    this.yChanged = true;
                    Flight flight = this.getFlight();
                    flight.setLaunchY(flight.getLaunchY() - 1.0);
                }
            } else {
                this.yChanged = false;
            }
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
            if (this.yChanged) {
                MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
            }
            this.dontPlace = true;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, null, 0.0f, 0.0f, 0.0f));
        }
        MinecraftInstance.mc.field_71439_g.field_70122_E = false;
        if (MinecraftInstance.mc.field_71439_g.field_70181_x < 0.0) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70159_w *= 0.7;
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70179_y *= 0.7;
        }
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.7f;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer)packet).field_149474_g = false;
        }
    }

    @Override
    public void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getBlock() instanceof BlockAir && (double)event.getY() <= this.getFlight().getLaunchY()) {
            event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)((double)event.getX() + 1.0), (double)this.getFlight().getLaunchY(), (double)((double)event.getZ() + 1.0)));
        }
    }

    private static final void onDisable$lambda-0() {
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
    }
}

