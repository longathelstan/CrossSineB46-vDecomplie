/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.verus;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/verus/DamageLongJump;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "()V", "damaged", "", "verjump", "", "verusBoostSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "verusSpeed", "verusY", "onDisable", "", "onEnable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class DamageLongJump
extends LongJumpMode {
    @NotNull
    private final FloatValue verusSpeed;
    @NotNull
    private final FloatValue verusBoostSpeed;
    @NotNull
    private final FloatValue verusY;
    private int verjump;
    private boolean damaged;

    public DamageLongJump() {
        super("DamageVerus");
        String string = Intrinsics.stringPlus(this.getValuePrefix(), "Speed");
        this.verusSpeed = new FloatValue(this, string){
            final /* synthetic */ DamageLongJump this$0;
            {
                this.this$0 = $receiver;
                super($super_call_param$1, 6.0f, 1.0f, 10.0f);
            }

            protected void onChange(float oldValue, float newValue) {
                if (((Number)DamageLongJump.access$getVerusBoostSpeed$p(this.this$0).get()).floatValue() > newValue) {
                    this.set(DamageLongJump.access$getVerusBoostSpeed$p(this.this$0).get());
                }
            }
        };
        string = Intrinsics.stringPlus(this.getValuePrefix(), "Boost");
        this.verusBoostSpeed = new FloatValue(this, string){
            final /* synthetic */ DamageLongJump this$0;
            {
                this.this$0 = $receiver;
                super($super_call_param$1, 4.25f, 1.0f, 10.0f);
            }

            protected void onChange(float oldValue, float newValue) {
                if (((Number)DamageLongJump.access$getVerusSpeed$p(this.this$0).get()).floatValue() < newValue) {
                    this.set(DamageLongJump.access$getVerusSpeed$p(this.this$0).get());
                }
            }
        };
        this.verusY = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "MotionY"), 0.42f, 0.3f, 2.0f);
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer && this.verjump < 3) {
            ((C03PacketPlayer)packet).field_149474_g = false;
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.verjump < 4) {
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
            ++this.verjump;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70737_aN == 9) {
            this.damaged = true;
            MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.verusY.get()).floatValue();
        }
        MovementUtils.INSTANCE.strafe(MinecraftInstance.mc.field_71439_g.field_70737_aN > 7 ? ((Number)this.verusBoostSpeed.get()).floatValue() : ((Number)this.verusSpeed.get()).floatValue());
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.damaged) {
            event.zeroXZ();
        }
    }

    @Override
    public void onDisable() {
        this.verjump = 0;
        this.damaged = false;
    }

    @Override
    public void onEnable() {
        this.verjump = 0;
        this.damaged = false;
    }

    public static final /* synthetic */ FloatValue access$getVerusBoostSpeed$p(DamageLongJump $this) {
        return $this.verusBoostSpeed;
    }

    public static final /* synthetic */ FloatValue access$getVerusSpeed$p(DamageLongJump $this) {
        return $this.verusSpeed;
    }
}

