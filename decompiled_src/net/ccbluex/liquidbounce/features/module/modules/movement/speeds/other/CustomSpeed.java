/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020$H\u0016J\b\u0010&\u001a\u00020$H\u0016J\b\u0010'\u001a\u00020$H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/CustomSpeed;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "AirSpaceKepPressed", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "GroundSpaceKeyPressed", "addYMotionValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "doCustomYValue", "doJump", "doLaunchSpeedValue", "doMinimumSpeedValue", "downAirSpeedValue", "downTimerValue", "groundResetXZValue", "groundStay", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "jumpTimerValue", "launchSpeedValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "minimumSpeedValue", "plusMode", "", "plusMultiply", "resetXZValue", "resetYValue", "speedValue", "strafeBeforeJump", "strafeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "upAirSpeedValue", "upTimerValue", "usePreMotion", "yValue", "onDisable", "", "onEnable", "onPreMotion", "onUpdate", "CrossSine"})
public final class CustomSpeed
extends SpeedMode {
    @NotNull
    private final FloatValue speedValue = new FloatValue("CustomSpeed", 1.6f, 0.0f, 2.0f);
    @NotNull
    private final BoolValue doLaunchSpeedValue = new BoolValue("CustomDoLaunchSpeed", true);
    @NotNull
    private final Value<Float> launchSpeedValue = new FloatValue("CustomLaunchSpeed", 1.6f, 0.2f, 2.0f).displayable(new Function0<Boolean>(this){
        final /* synthetic */ CustomSpeed this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)CustomSpeed.access$getDoLaunchSpeedValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final BoolValue strafeBeforeJump = new BoolValue("CustomLaunchMoveBeforeJump", false);
    @NotNull
    private final BoolValue doMinimumSpeedValue = new BoolValue("CustomDoMinimumSpeed", true);
    @NotNull
    private final Value<Float> minimumSpeedValue = new FloatValue("CustomMinimumSpeed", 0.25f, 0.1f, 2.0f).displayable(new Function0<Boolean>(this){
        final /* synthetic */ CustomSpeed this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)CustomSpeed.access$getDoMinimumSpeedValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final FloatValue addYMotionValue = new FloatValue("CustomAddYMotion", 0.0f, 0.0f, 2.0f);
    @NotNull
    private final BoolValue doCustomYValue = new BoolValue("CustomDoModifyJumpY", true);
    @NotNull
    private final Value<Float> yValue = new FloatValue("CustomY", 0.42f, 0.0f, 4.0f).displayable(new Function0<Boolean>(this){
        final /* synthetic */ CustomSpeed this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)CustomSpeed.access$getDoCustomYValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final FloatValue upTimerValue = new FloatValue("CustomUpTimer", 1.0f, 0.1f, 2.0f);
    @NotNull
    private final FloatValue jumpTimerValue = new FloatValue("CustomJumpTimer", 1.25f, 0.1f, 2.0f);
    @NotNull
    private final FloatValue downTimerValue = new FloatValue("CustomDownTimer", 1.0f, 0.1f, 2.0f);
    @NotNull
    private final FloatValue upAirSpeedValue = new FloatValue("CustomUpAirSpeed", 2.03f, 0.5f, 3.5f);
    @NotNull
    private final FloatValue downAirSpeedValue = new FloatValue("CustomDownAirSpeed", 2.01f, 0.5f, 3.5f);
    @NotNull
    private final ListValue strafeValue;
    @NotNull
    private final Value<String> plusMode;
    @NotNull
    private final Value<Float> plusMultiply;
    @NotNull
    private final IntegerValue groundStay;
    @NotNull
    private final BoolValue groundResetXZValue;
    @NotNull
    private final BoolValue resetXZValue;
    @NotNull
    private final BoolValue resetYValue;
    @NotNull
    private final BoolValue doJump;
    @NotNull
    private final BoolValue GroundSpaceKeyPressed;
    @NotNull
    private final BoolValue AirSpaceKepPressed;
    @NotNull
    private final BoolValue usePreMotion;

    public CustomSpeed() {
        super("Custom");
        String[] stringArray = new String[]{"Strafe", "Boost", "AirSpeed", "Plus", "PlusOnlyUp", "PlusOnlyDown", "Non-Strafe"};
        this.strafeValue = new ListValue("CustomStrafe", stringArray, "Boost");
        stringArray = new String[]{"Add", "Multiply"};
        this.plusMode = new ListValue("PlusBoostMode", stringArray, "Add").displayable(new Function0<Boolean>(this){
            final /* synthetic */ CustomSpeed this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return CustomSpeed.access$getStrafeValue$p(this.this$0).equals("Plus") || CustomSpeed.access$getStrafeValue$p(this.this$0).equals("PlusOnlyUp") || CustomSpeed.access$getStrafeValue$p(this.this$0).equals("PlusOnlyDown");
            }
        });
        this.plusMultiply = new FloatValue("PlusMultiplyAmount", 1.1f, 1.0f, 2.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ CustomSpeed this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return CustomSpeed.access$getPlusMode$p(this.this$0).equals("Multiply") && (CustomSpeed.access$getStrafeValue$p(this.this$0).equals("Plus") || CustomSpeed.access$getStrafeValue$p(this.this$0).equals("PlusOnlyUp") || CustomSpeed.access$getStrafeValue$p(this.this$0).equals("PlusOnlyDown"));
            }
        });
        this.groundStay = new IntegerValue("CustomGroundStay", 0, 0, 10);
        this.groundResetXZValue = new BoolValue("CustomGroundResetXZ", false);
        this.resetXZValue = new BoolValue("CustomResetXZ", false);
        this.resetYValue = new BoolValue("CustomResetY", false);
        this.doJump = new BoolValue("CustomDoJump", true);
        this.GroundSpaceKeyPressed = new BoolValue("CustomPressSpaceKeyOnGround", true);
        this.AirSpaceKepPressed = new BoolValue("CustomPressSpaceKeyInAir", false);
        this.usePreMotion = new BoolValue("CustomUsePreMotion", true);
    }

    @Override
    public void onPreMotion() {
        block47: {
            block45: {
                block46: {
                    if (!((Boolean)this.usePreMotion.get()).booleanValue()) {
                        return;
                    }
                    if (!MovementUtils.INSTANCE.isMoving()) break block45;
                    MinecraftInstance.mc.field_71428_T.field_74278_d = MinecraftInstance.mc.field_71439_g.field_70181_x > 0.0 ? ((Number)this.upTimerValue.get()).floatValue() : ((Number)this.downTimerValue.get()).floatValue();
                    if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break block46;
                    if (PlayerUtils.getGroundTicks() >= ((Number)this.groundStay.get()).intValue()) {
                        if (((Boolean)this.GroundSpaceKeyPressed.get()).booleanValue()) {
                            MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74314_A);
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.jumpTimerValue.get()).floatValue();
                        if (((Boolean)this.doLaunchSpeedValue.get()).booleanValue() && ((Boolean)this.strafeBeforeJump.get()).booleanValue()) {
                            MovementUtils.INSTANCE.strafe(((Number)this.launchSpeedValue.get()).floatValue());
                        }
                        if (((Boolean)this.doJump.get()).booleanValue()) {
                            MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, 0.0, 6, null);
                        } else if (!((Boolean)this.doCustomYValue.get()).booleanValue()) {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                        }
                        if (((Boolean)this.doLaunchSpeedValue.get()).booleanValue() && !((Boolean)this.strafeBeforeJump.get()).booleanValue()) {
                            MovementUtils.INSTANCE.strafe(((Number)this.launchSpeedValue.get()).floatValue());
                        }
                        if (((Boolean)this.doCustomYValue.get()).booleanValue() && !(((Number)this.yValue.get()).floatValue() == 0.0f)) {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.yValue.get()).floatValue();
                        }
                    } else if (((Boolean)this.groundResetXZValue.get()).booleanValue()) {
                        MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                    }
                    break block47;
                }
                if (((Boolean)this.AirSpaceKepPressed.get()).booleanValue()) {
                    MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74314_A);
                }
                if (((Boolean)this.doMinimumSpeedValue.get()).booleanValue() && MovementUtils.INSTANCE.getSpeed() < ((Number)this.minimumSpeedValue.get()).floatValue()) {
                    MovementUtils.INSTANCE.strafe(((Number)this.minimumSpeedValue.get()).floatValue());
                }
                String string = ((String)this.strafeValue.get()).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                switch (string) {
                    case "strafe": {
                        MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
                        break;
                    }
                    case "non-strafe": {
                        MovementUtils.INSTANCE.strafe();
                        break;
                    }
                    case "boost": {
                        MovementUtils.INSTANCE.strafe();
                        break;
                    }
                    case "airspeed": {
                        if (MinecraftInstance.mc.field_71439_g.field_70181_x > 0.0) {
                            MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.01f * ((Number)this.upAirSpeedValue.get()).floatValue();
                            MovementUtils.INSTANCE.strafe();
                            break;
                        }
                        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.01f * ((Number)this.downAirSpeedValue.get()).floatValue();
                        MovementUtils.INSTANCE.strafe();
                        break;
                    }
                    case "plus": {
                        String string2 = this.plusMode.get().toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        String string3 = string2;
                        if (Intrinsics.areEqual(string3, "plus")) {
                            MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1f);
                            break;
                        }
                        if (!Intrinsics.areEqual(string3, "multiply")) break;
                        string = MinecraftInstance.mc.field_71439_g;
                        ((EntityPlayerSP)string).field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                        string = MinecraftInstance.mc.field_71439_g;
                        ((EntityPlayerSP)string).field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                        break;
                    }
                    case "plusonlyup": {
                        if (MinecraftInstance.mc.field_71439_g.field_70181_x > 0.0) {
                            String string4 = this.plusMode.get().toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            String string5 = string4;
                            if (Intrinsics.areEqual(string5, "plus")) {
                                MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1f);
                                break;
                            }
                            if (!Intrinsics.areEqual(string5, "multiply")) break;
                            string = MinecraftInstance.mc.field_71439_g;
                            ((EntityPlayerSP)string).field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                            string = MinecraftInstance.mc.field_71439_g;
                            ((EntityPlayerSP)string).field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                            break;
                        }
                        MovementUtils.INSTANCE.strafe();
                        break;
                    }
                    case "plusonlydown": {
                        if (MinecraftInstance.mc.field_71439_g.field_70181_x < 0.0) {
                            String string6 = this.plusMode.get().toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            String string7 = string6;
                            if (Intrinsics.areEqual(string7, "plus")) {
                                MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1f);
                                break;
                            }
                            if (!Intrinsics.areEqual(string7, "multiply")) break;
                            string = MinecraftInstance.mc.field_71439_g;
                            ((EntityPlayerSP)string).field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                            string = MinecraftInstance.mc.field_71439_g;
                            ((EntityPlayerSP)string).field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                            break;
                        }
                        MovementUtils.INSTANCE.strafe();
                    }
                }
                String string8 = MinecraftInstance.mc.field_71439_g;
                ((EntityPlayerSP)string8).field_70181_x += ((Number)this.addYMotionValue.get()).doubleValue() * 0.03;
                break block47;
            }
            if (((Boolean)this.resetXZValue.get()).booleanValue()) {
                MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
    }

    @Override
    public void onUpdate() {
        block47: {
            block45: {
                block46: {
                    if (((Boolean)this.usePreMotion.get()).booleanValue()) {
                        return;
                    }
                    if (!MovementUtils.INSTANCE.isMoving()) break block45;
                    MinecraftInstance.mc.field_71428_T.field_74278_d = MinecraftInstance.mc.field_71439_g.field_70181_x > 0.0 ? ((Number)this.upTimerValue.get()).floatValue() : ((Number)this.downTimerValue.get()).floatValue();
                    if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break block46;
                    if (PlayerUtils.getGroundTicks() >= ((Number)this.groundStay.get()).intValue()) {
                        if (((Boolean)this.GroundSpaceKeyPressed.get()).booleanValue()) {
                            MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74314_A);
                        }
                        MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.jumpTimerValue.get()).floatValue();
                        if (((Boolean)this.doLaunchSpeedValue.get()).booleanValue() && ((Boolean)this.strafeBeforeJump.get()).booleanValue()) {
                            MovementUtils.INSTANCE.strafe(((Number)this.launchSpeedValue.get()).floatValue());
                        }
                        if (((Boolean)this.doJump.get()).booleanValue()) {
                            MovementUtils.jump$default(MovementUtils.INSTANCE, false, false, 0.0, 6, null);
                        } else if (!((Boolean)this.doCustomYValue.get()).booleanValue()) {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                        }
                        if (((Boolean)this.doLaunchSpeedValue.get()).booleanValue() && !((Boolean)this.strafeBeforeJump.get()).booleanValue()) {
                            MovementUtils.INSTANCE.strafe(((Number)this.launchSpeedValue.get()).floatValue());
                        }
                        if (((Boolean)this.doCustomYValue.get()).booleanValue() && !(((Number)this.yValue.get()).floatValue() == 0.0f)) {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.yValue.get()).floatValue();
                        }
                    } else if (((Boolean)this.groundResetXZValue.get()).booleanValue()) {
                        MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                    }
                    int n = PlayerUtils.INSTANCE.getGroundTicks();
                    PlayerUtils.setGroundTicks(n + 1);
                    break block47;
                }
                PlayerUtils.setGroundTicks(0);
                if (((Boolean)this.AirSpaceKepPressed.get()).booleanValue()) {
                    MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74314_A);
                }
                if (((Boolean)this.doMinimumSpeedValue.get()).booleanValue() && MovementUtils.INSTANCE.getSpeed() < ((Number)this.minimumSpeedValue.get()).floatValue()) {
                    MovementUtils.INSTANCE.strafe(((Number)this.minimumSpeedValue.get()).floatValue());
                }
                String string = ((String)this.strafeValue.get()).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                switch (string) {
                    case "strafe": {
                        MovementUtils.INSTANCE.strafe(((Number)this.speedValue.get()).floatValue());
                        break;
                    }
                    case "non-strafe": {
                        MovementUtils.INSTANCE.strafe();
                        break;
                    }
                    case "boost": {
                        MovementUtils.INSTANCE.strafe();
                        break;
                    }
                    case "airspeed": {
                        if (MinecraftInstance.mc.field_71439_g.field_70181_x > 0.0) {
                            MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.01f * ((Number)this.upAirSpeedValue.get()).floatValue();
                            MovementUtils.INSTANCE.strafe();
                            break;
                        }
                        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.01f * ((Number)this.downAirSpeedValue.get()).floatValue();
                        MovementUtils.INSTANCE.strafe();
                        break;
                    }
                    case "plus": {
                        String string2 = this.plusMode.get().toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        String string3 = string2;
                        if (Intrinsics.areEqual(string3, "plus")) {
                            MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1f);
                            break;
                        }
                        if (!Intrinsics.areEqual(string3, "multiply")) break;
                        string = MinecraftInstance.mc.field_71439_g;
                        ((EntityPlayerSP)string).field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                        string = MinecraftInstance.mc.field_71439_g;
                        ((EntityPlayerSP)string).field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                        break;
                    }
                    case "plusonlyup": {
                        if (MinecraftInstance.mc.field_71439_g.field_70181_x > 0.0) {
                            String string4 = this.plusMode.get().toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            String string5 = string4;
                            if (Intrinsics.areEqual(string5, "plus")) {
                                MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1f);
                                break;
                            }
                            if (!Intrinsics.areEqual(string5, "multiply")) break;
                            string = MinecraftInstance.mc.field_71439_g;
                            ((EntityPlayerSP)string).field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                            string = MinecraftInstance.mc.field_71439_g;
                            ((EntityPlayerSP)string).field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                            break;
                        }
                        MovementUtils.INSTANCE.strafe();
                        break;
                    }
                    case "plusonlydown": {
                        if (MinecraftInstance.mc.field_71439_g.field_70181_x < 0.0) {
                            String string6 = this.plusMode.get().toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            String string7 = string6;
                            if (Intrinsics.areEqual(string7, "plus")) {
                                MovementUtils.INSTANCE.move(((Number)this.speedValue.get()).floatValue() * 0.1f);
                                break;
                            }
                            if (!Intrinsics.areEqual(string7, "multiply")) break;
                            string = MinecraftInstance.mc.field_71439_g;
                            ((EntityPlayerSP)string).field_70159_w *= ((Number)this.plusMultiply.get()).doubleValue();
                            string = MinecraftInstance.mc.field_71439_g;
                            ((EntityPlayerSP)string).field_70179_y *= ((Number)this.plusMultiply.get()).doubleValue();
                            break;
                        }
                        MovementUtils.INSTANCE.strafe();
                    }
                }
                String string8 = MinecraftInstance.mc.field_71439_g;
                ((EntityPlayerSP)string8).field_70181_x += ((Number)this.addYMotionValue.get()).doubleValue() * 0.03;
                break block47;
            }
            if (((Boolean)this.resetXZValue.get()).booleanValue()) {
                MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
    }

    @Override
    public void onEnable() {
        if (((Boolean)this.resetXZValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
        }
        if (((Boolean)this.resetYValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
        }
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        Intrinsics.checkNotNull(MinecraftInstance.mc.field_71439_g);
        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.02f;
    }

    public static final /* synthetic */ BoolValue access$getDoLaunchSpeedValue$p(CustomSpeed $this) {
        return $this.doLaunchSpeedValue;
    }

    public static final /* synthetic */ BoolValue access$getDoMinimumSpeedValue$p(CustomSpeed $this) {
        return $this.doMinimumSpeedValue;
    }

    public static final /* synthetic */ BoolValue access$getDoCustomYValue$p(CustomSpeed $this) {
        return $this.doCustomYValue;
    }

    public static final /* synthetic */ ListValue access$getStrafeValue$p(CustomSpeed $this) {
        return $this.strafeValue;
    }

    public static final /* synthetic */ Value access$getPlusMode$p(CustomSpeed $this) {
        return $this.plusMode;
    }
}

