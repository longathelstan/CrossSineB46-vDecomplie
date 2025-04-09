/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.verus;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\u0010\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0018H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/verus/VerusJumpFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "airStrafeValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "boostLength", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "boostValue", "moveBeforeDamage", "", "speedValue", "", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "times", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onEnable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class VerusJumpFlight
extends FlightMode {
    @NotNull
    private final BoolValue boostValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Boost"), false);
    @NotNull
    private final Value<Float> speedValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 2.0f, 0.0f, 3.0f).displayable(new Function0<Boolean>(this){
        final /* synthetic */ VerusJumpFlight this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)VerusJumpFlight.access$getBoostValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Integer> boostLength = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "BoostTime"), 500, 300, 1000).displayable(new Function0<Boolean>(this){
        final /* synthetic */ VerusJumpFlight this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)VerusJumpFlight.access$getBoostValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Boolean> moveBeforeDamage = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "MoveBeforeDamage"), true).displayable(new Function0<Boolean>(this){
        final /* synthetic */ VerusJumpFlight this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)VerusJumpFlight.access$getBoostValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final BoolValue airStrafeValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "AirStrafe"), true);
    private int times;
    @NotNull
    private MSTimer timer = new MSTimer();

    public VerusJumpFlight() {
        super("VerusJump");
    }

    @Override
    public void onEnable() {
        this.times = 0;
        this.timer.reset();
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)this.boostValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
            if (this.times < 5 && !this.moveBeforeDamage.get().booleanValue()) {
                MovementUtils.INSTANCE.strafe(0.0f);
            }
            if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.times < 5) {
                int n = this.times;
                this.times = n + 1;
                this.timer.reset();
                if (this.times < 5) {
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                    MovementUtils.INSTANCE.strafe(0.48f);
                }
            }
            if (this.times >= 5) {
                if (!this.timer.hasTimePassed(((Number)this.boostLength.get()).intValue())) {
                    MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
                } else {
                    this.times = 0;
                }
            }
        } else {
            MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74314_A);
            if (MinecraftInstance.mc.field_71439_g.field_70122_E && MovementUtils.INSTANCE.isMoving()) {
                MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
                MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                MovementUtils.INSTANCE.strafe(0.48f);
            } else if (((Boolean)this.airStrafeValue.get()).booleanValue()) {
                MovementUtils.INSTANCE.strafe();
            }
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (((Boolean)this.boostValue.get()).booleanValue() && packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer)packet).field_149474_g = this.times >= 5 && !this.timer.hasTimePassed(((Number)this.boostLength.get()).intValue());
        }
    }

    @Override
    public void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getBlock() instanceof BlockAir && (double)event.getY() <= this.getFlight().getLaunchY()) {
            event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)((double)event.getX() + 1.0), (double)this.getFlight().getLaunchY(), (double)((double)event.getZ() + 1.0)));
        }
    }

    public static final /* synthetic */ BoolValue access$getBoostValue$p(VerusJumpFlight $this) {
        return $this.boostValue;
    }
}

