/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Phase;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Step", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010#\u001a\u00020\u0004H\u0002J\b\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020%H\u0016J\u0010\u0010'\u001a\u00020%2\u0006\u0010(\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020%2\u0006\u0010(\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020%2\u0006\u0010(\u001a\u00020-H\u0007J\u0010\u0010.\u001a\u00020%2\u0006\u0010(\u001a\u00020/H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Step;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canStep", "", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "heightValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "isAACStep", "isStep", "jumpHeightValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "lastOnGround", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "ncpNextStep", "", "spartanSwitch", "stepX", "", "stepY", "stepZ", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "timerDynValue", "timerValue", "wasTimer", "couldStep", "fakeJump", "", "onDisable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Step
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final FloatValue heightValue;
    @NotNull
    private final Value<Float> jumpHeightValue;
    @NotNull
    private final IntegerValue delayValue;
    @NotNull
    private final Value<Float> timerValue;
    @NotNull
    private final Value<Boolean> timerDynValue;
    private boolean isStep;
    private double stepX;
    private double stepY;
    private double stepZ;
    private int ncpNextStep;
    private boolean spartanSwitch;
    private boolean isAACStep;
    private boolean wasTimer;
    private boolean lastOnGround;
    private boolean canStep;
    @NotNull
    private final MSTimer timer;

    public Step() {
        String[] stringArray = new String[]{"Vanilla", "Jump", "Matrix6.7.0", "NCP", "NCPNew", "MotionNCP", "MotionNCP2", "OldNCP", "OldAAC", "LAAC", "AAC3.3.4", "AAC3.6.4", "AAC4.4.0", "Spartan", "Rewinside", "Vulcan", "Verus", "BlocksMC"};
        this.modeValue = new ListValue("Mode", stringArray, "NCP");
        this.heightValue = new FloatValue("Height", 1.0f, 0.6f, 10.0f);
        this.jumpHeightValue = new FloatValue("JumpMotion", 0.42f, 0.37f, 0.42f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Step this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("Jump") || this.this$0.getModeValue().equals("TimerJump");
            }
        });
        this.delayValue = new IntegerValue("Delay", 0, 0, 500);
        this.timerValue = new FloatValue("Timer", 1.0f, 0.05f, 1.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Step this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return !this.this$0.getModeValue().equals("Matrix6.7.0") && !this.this$0.getModeValue().equals("Verus");
            }
        });
        this.timerDynValue = new BoolValue("UseDynamicTimer", false).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Step this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return !this.this$0.getModeValue().equals("Matrix6.7.0") && !this.this$0.getModeValue().equals("Verus");
            }
        });
        this.timer = new MSTimer();
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @Override
    public void onDisable() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        MinecraftInstance.mc.field_71439_g.field_70138_W = 0.6f;
        if (this.wasTimer) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        }
        this.wasTimer = false;
        this.lastOnGround = MinecraftInstance.mc.field_71439_g.field_70122_E;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70123_F && MinecraftInstance.mc.field_71439_g.field_70122_E && this.lastOnGround) {
            this.canStep = true;
            if (this.modeValue.equals("AAC4.4.0") || this.modeValue.equals("NCPNew") || this.modeValue.equals("Matrix6.7.0")) {
                MinecraftInstance.mc.field_71439_g.field_70138_W = ((Number)this.heightValue.get()).floatValue();
            }
        } else {
            this.canStep = false;
            MinecraftInstance.mc.field_71439_g.field_70138_W = 0.6f;
        }
        this.lastOnGround = MinecraftInstance.mc.field_71439_g.field_70122_E;
        if (this.wasTimer) {
            this.wasTimer = false;
            if (this.modeValue.equals("AAC4.4.0")) {
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 0.913;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 0.913;
            }
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        }
        String mode2 = (String)this.modeValue.get();
        if (StringsKt.equals(mode2, "jump", true) && MinecraftInstance.mc.field_71439_g.field_70123_F && MinecraftInstance.mc.field_71439_g.field_70122_E && !MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            this.fakeJump();
            MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.jumpHeightValue.get()).floatValue();
        } else if (StringsKt.equals(mode2, "timerjump", true)) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
            if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    this.fakeJump();
                    MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.jumpHeightValue.get()).floatValue();
                    this.isStep = true;
                } else if (this.isStep) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = MinecraftInstance.mc.field_71439_g.field_70181_x > 0.0 ? (float)(1.0 - MinecraftInstance.mc.field_71439_g.field_70181_x / 1.8) : 1.25f;
                }
            } else {
                this.isStep = false;
            }
        } else if (StringsKt.equals(mode2, "laac", true)) {
            if (!(!MinecraftInstance.mc.field_71439_g.field_70123_F || MinecraftInstance.mc.field_71439_g.func_70617_f_() || MinecraftInstance.mc.field_71439_g.func_70090_H() || MinecraftInstance.mc.field_71439_g.func_180799_ab() || MinecraftInstance.mc.field_71439_g.field_70134_J)) {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.timer.hasTimePassed(((Number)this.delayValue.get()).intValue())) {
                    this.isStep = true;
                    this.fakeJump();
                    EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70181_x += 0.620000001490116;
                    float f = MinecraftInstance.mc.field_71439_g.field_70177_z * ((float)Math.PI / 180);
                    EntityPlayerSP entityPlayerSP2 = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP2.field_70159_w -= (double)MathHelper.func_76126_a((float)f) * 0.2;
                    entityPlayerSP2 = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP2.field_70179_y += (double)MathHelper.func_76134_b((float)f) * 0.2;
                    this.timer.reset();
                }
                MinecraftInstance.mc.field_71439_g.field_70122_E = true;
            } else {
                this.isStep = false;
            }
        } else if (StringsKt.equals(mode2, "aac3.6.4", true)) {
            if (MinecraftInstance.mc.field_71439_g.field_70123_F && MovementUtils.INSTANCE.isMoving()) {
                EntityPlayerSP entityPlayerSP;
                if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.couldStep()) {
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70159_w *= 1.12;
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70179_y *= 1.12;
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                    this.isAACStep = true;
                }
                if (this.isAACStep) {
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70181_x -= 0.015;
                    if (!MinecraftInstance.mc.field_71439_g.func_71039_bw() && MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f) {
                        MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.3f;
                    }
                }
            } else {
                this.isAACStep = false;
            }
        } else if (StringsKt.equals(mode2, "aac3.3.4", true)) {
            if (MinecraftInstance.mc.field_71439_g.field_70123_F && MovementUtils.INSTANCE.isMoving()) {
                EntityPlayerSP entityPlayerSP;
                if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.couldStep()) {
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70159_w *= 1.26;
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70179_y *= 1.26;
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                    this.isAACStep = true;
                }
                if (this.isAACStep) {
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70181_x -= 0.015;
                    if (!MinecraftInstance.mc.field_71439_g.func_71039_bw() && MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f) {
                        MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.3f;
                    }
                }
            } else {
                this.isAACStep = false;
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        String mode2 = (String)this.modeValue.get();
        if (StringsKt.equals(mode2, "motionncp2", true) && MinecraftInstance.mc.field_71439_g.field_70123_F) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.404 + Math.random() / (double)500;
        } else if (StringsKt.equals(mode2, "motionncp", true) && MinecraftInstance.mc.field_71439_g.field_70123_F && !MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.couldStep()) {
                this.fakeJump();
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                event.setY(0.41999998688698);
                this.ncpNextStep = 1;
            } else if (this.ncpNextStep == 1) {
                event.setY(0.33319999363422);
                this.ncpNextStep = 2;
            } else if (this.ncpNextStep == 2) {
                double yaw = MovementUtils.INSTANCE.getDirection();
                event.setY(0.24813599859094704);
                event.setX(-Math.sin(yaw) * 0.7);
                event.setZ(Math.cos(yaw) * 0.7);
                this.ncpNextStep = 0;
            }
        }
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        String mode2 = (String)this.modeValue.get();
        if (event.getEventState() == EventState.PRE) {
            float height;
            Phase phase = CrossSine.INSTANCE.getModuleManager().get(Phase.class);
            Intrinsics.checkNotNull(phase);
            if (phase.getState()) {
                event.setStepHeight(0.0f);
                return;
            }
            if (StringsKt.equals(mode2, "AAC4.4.0", true) || StringsKt.equals(mode2, "NCPNew", true) || this.modeValue.equals("Matrix6.7.0")) {
                if (event.getStepHeight() > 0.6f && !this.canStep) {
                    return;
                }
                if (event.getStepHeight() <= 0.6f) {
                    return;
                }
            }
            if (!MinecraftInstance.mc.field_71439_g.field_70122_E || !this.timer.hasTimePassed(((Number)this.delayValue.get()).intValue()) || StringsKt.equals(mode2, "Jump", true) || StringsKt.equals(mode2, "MotionNCP", true) || StringsKt.equals(mode2, "LAAC", true) || StringsKt.equals(mode2, "AAC3.3.4", true) || StringsKt.equals(mode2, "TimerJump", true)) {
                MinecraftInstance.mc.field_71439_g.field_70138_W = 0.6f;
                event.setStepHeight(0.6f);
                return;
            }
            MinecraftInstance.mc.field_71439_g.field_70138_W = height = ((Number)this.heightValue.get()).floatValue();
            event.setStepHeight(height);
            if (event.getStepHeight() > 0.6f) {
                this.isStep = true;
                this.stepX = MinecraftInstance.mc.field_71439_g.field_70165_t;
                this.stepY = MinecraftInstance.mc.field_71439_g.field_70163_u;
                this.stepZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
            }
        } else {
            if (!this.isStep) {
                return;
            }
            if (MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY > 0.6) {
                if ((double)((Number)this.timerValue.get()).floatValue() < 1.0) {
                    this.wasTimer = true;
                    MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timerValue.get()).floatValue();
                    if (this.timerDynValue.get().booleanValue()) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = (float)((double)MinecraftInstance.mc.field_71428_T.field_74278_d / Math.sqrt(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY));
                    }
                }
                if (StringsKt.equals(mode2, "BlocksMC", true)) {
                    this.fakeJump();
                    if (PlayerUtils.getGroundTicks() == 0) {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                    }
                    if (PlayerUtils.getOffGroundTicks() == 2) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 2.0f;
                    }
                    if (PlayerUtils.getOffGroundTicks() == 3) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                        MinecraftInstance.mc.field_71439_g.field_70181_x = MovementUtils.INSTANCE.predictedMotion(MinecraftInstance.mc.field_71439_g.field_70181_x, 5);
                    }
                }
                if (StringsKt.equals(mode2, "NCP", true) || StringsKt.equals(mode2, "OldAAC", true)) {
                    this.fakeJump();
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698, this.stepZ, false));
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212, this.stepZ, false));
                    this.timer.reset();
                } else if (StringsKt.equals(mode2, "NCPNew", true)) {
                    double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                    this.fakeJump();
                    if (rstepHeight > 2.019) {
                        Double[] stpPacket;
                        Double[] doubleArray = new Double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.919};
                        Double[] $this$forEach$iv = stpPacket = doubleArray;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                    } else if (rstepHeight <= 2.019 && rstepHeight > 1.869) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                    } else if (rstepHeight <= 1.869 && rstepHeight > 1.5) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                    } else if (rstepHeight <= 1.5 && rstepHeight > 1.015) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.42, 0.7532, 1.01, 1.093, 1.015};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                    } else if (rstepHeight <= 1.015 && rstepHeight > 0.875) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698, 0.7531999805212};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                    } else if (rstepHeight <= 0.875 && rstepHeight > 0.6) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.39, 0.6938};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                    }
                    this.timer.reset();
                } else if (StringsKt.equals(mode2, "Verus", true)) {
                    double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f / (float)Math.ceil(rstepHeight * 2.0);
                    double stpHight = 0.0;
                    this.fakeJump();
                    int $this$forEach$iv = (int)(Math.ceil(rstepHeight * 2.0) - 1.0);
                    int $i$f$forEach = 0;
                    while ($i$f$forEach < $this$forEach$iv) {
                        int n;
                        int it = n = $i$f$forEach++;
                        boolean bl = false;
                        MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + (stpHight += 0.5), this.stepZ, true));
                    }
                    this.wasTimer = true;
                } else if (StringsKt.equals(mode2, "Vulcan", true)) {
                    double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                    this.fakeJump();
                    if (rstepHeight > 2.0) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.5, 1.0, 1.5, 2.0};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                        }
                    } else if (rstepHeight <= 2.0 && rstepHeight > 1.5) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.5, 1.0, 1.5};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                        }
                    } else if (rstepHeight <= 1.5 && rstepHeight > 1.0) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.5, 1.0};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                        }
                    } else if (rstepHeight <= 1.0 && rstepHeight > 0.6) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.5};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                        }
                    }
                    this.timer.reset();
                } else if (StringsKt.equals(mode2, "Matrix6.7.0", true)) {
                    double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                    this.fakeJump();
                    if (rstepHeight <= 3.0042 && rstepHeight > 2.95) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289, 2.04032, 2.23371, 2.35453, 2.40423};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        Double[] doubleArray = $this$forEach$iv;
                        int n = 0;
                        int n2 = doubleArray.length;
                        while (n < n2) {
                            Double element$iv = doubleArray[n];
                            ++n;
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            boolean bl2 = 0.9 <= it ? it <= 1.01 : false;
                            if (bl2) {
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                                continue;
                            }
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.11f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 2.95 && rstepHeight > 2.83) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289, 2.04032, 2.23371, 2.35453};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        Double[] doubleArray = $this$forEach$iv;
                        int n = 0;
                        int n3 = doubleArray.length;
                        while (n < n3) {
                            Double element$iv = doubleArray[n];
                            ++n;
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            boolean bl3 = 0.9 <= it ? it <= 1.01 : false;
                            if (bl3) {
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                                continue;
                            }
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.12f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 2.83 && rstepHeight > 2.64) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289, 2.04032, 2.23371};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        Double[] doubleArray = $this$forEach$iv;
                        int n = 0;
                        int n4 = doubleArray.length;
                        while (n < n4) {
                            Double element$iv = doubleArray[n];
                            ++n;
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            boolean bl4 = 0.9 <= it ? it <= 1.01 : false;
                            if (bl4) {
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                                continue;
                            }
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.13f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 2.64 && rstepHeight > 2.37) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289, 2.04032};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        Double[] doubleArray = $this$forEach$iv;
                        int n = 0;
                        int n5 = doubleArray.length;
                        while (n < n5) {
                            Double element$iv = doubleArray[n];
                            ++n;
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            boolean bl5 = 0.9 <= it ? it <= 1.01 : false;
                            if (bl5) {
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                                continue;
                            }
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.14f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 2.37 && rstepHeight > 2.02) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989, 1.77289};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        Double[] doubleArray = $this$forEach$iv;
                        int n = 0;
                        int n6 = doubleArray.length;
                        while (n < n6) {
                            Double element$iv = doubleArray[n];
                            ++n;
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            boolean bl6 = 0.9 <= it ? it <= 1.01 : false;
                            if (bl6) {
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                                continue;
                            }
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.16f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 2.02 && rstepHeight > 1.77) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41951, 0.75223, 0.9999, 1.42989};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        Double[] doubleArray = $this$forEach$iv;
                        int n = 0;
                        int n7 = doubleArray.length;
                        while (n < n7) {
                            Double element$iv = doubleArray[n];
                            ++n;
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            boolean bl7 = 0.9 <= it ? it <= 1.01 : false;
                            if (bl7) {
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                                continue;
                            }
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.21f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 1.77 && rstepHeight > 1.6) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698, 0.7531999805212, 1.17319996740818};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        Double[] doubleArray = $this$forEach$iv;
                        int n = 0;
                        int n8 = doubleArray.length;
                        while (n < n8) {
                            Double element$iv = doubleArray[n];
                            ++n;
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            boolean bl8 = 0.753 <= it ? it <= 0.754 : false;
                            if (bl8) {
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, true));
                                continue;
                            }
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.28f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 1.6 && rstepHeight > 1.3525) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698, 0.7531999805212, 1.001335979112147};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.28f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 1.3525 && rstepHeight > 1.02) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698, 0.7531999805212};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.34f;
                        this.wasTimer = true;
                    } else if (rstepHeight <= 1.02 && rstepHeight > 0.6) {
                        Double[] stpPacket;
                        Double[] $this$forEach$iv = new Double[]{0.41999998688698};
                        $this$forEach$iv = stpPacket = $this$forEach$iv;
                        boolean $i$f$forEach = false;
                        for (Double element$iv : $this$forEach$iv) {
                            double it = ((Number)element$iv).doubleValue();
                            boolean bl = false;
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + it, this.stepZ, false));
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.5f;
                        this.wasTimer = true;
                    }
                    this.timer.reset();
                } else if (StringsKt.equals(mode2, "Spartan", true)) {
                    this.fakeJump();
                    if (this.spartanSwitch) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147, this.stepZ, false));
                    } else {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.6, this.stepZ, false));
                    }
                    this.spartanSwitch = !this.spartanSwitch;
                    this.timer.reset();
                } else if (StringsKt.equals(mode2, "AAC4.4.0", true)) {
                    double rstepHeight = MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - this.stepY;
                    this.fakeJump();
                    this.timer.reset();
                    if (rstepHeight >= 0.984375 && rstepHeight < 1.484375) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.4, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.9, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.0, this.stepZ, true));
                    } else if (rstepHeight >= 1.484375 && rstepHeight < 1.984375) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.42, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7718, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.0556, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.2714, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.412, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.5, this.stepZ, true));
                    } else if (rstepHeight >= 1.984375) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.45, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.84375, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.18125, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.4625, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.6875, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.85625, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.96875, this.stepZ, false));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX + MinecraftInstance.mc.field_71439_g.field_70159_w * 0.5, this.stepY + 2.0, this.stepZ + MinecraftInstance.mc.field_71439_g.field_70179_y * 0.5, true));
                    }
                } else if (StringsKt.equals(mode2, "Rewinside", true)) {
                    this.fakeJump();
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698, this.stepZ, false));
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212, this.stepZ, false));
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147, this.stepZ, false));
                    this.timer.reset();
                }
            }
            this.isStep = false;
            this.stepX = 0.0;
            this.stepY = 0.0;
            this.stepZ = 0.0;
        }
    }

    @EventTarget(ignoreCondition=true)
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer && this.isStep && this.modeValue.equals("OldNCP")) {
            ((C03PacketPlayer)packet).field_149477_b += 0.07;
            this.isStep = false;
        }
    }

    private final void fakeJump() {
        MinecraftInstance.mc.field_71439_g.field_70160_al = true;
        MinecraftInstance.mc.field_71439_g.func_71029_a(StatList.field_75953_u);
    }

    private final boolean couldStep() {
        double yaw = MovementUtils.INSTANCE.getDirection();
        double x = -Math.sin(yaw) * 0.32;
        double z = Math.cos(yaw) * 0.32;
        return MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(x, 1.001335979112147, z)).isEmpty();
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }
}

