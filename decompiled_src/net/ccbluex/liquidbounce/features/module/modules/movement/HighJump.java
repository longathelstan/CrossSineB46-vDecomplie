/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.Collection;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="HighJump", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0019H\u0016J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020!H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/HighJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "glassValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "heightValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "jumpY", "", "martrixStatus", "", "martrixWasTimer", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "stableMotionValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onDisable", "", "onEnable", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class HighJump
extends Module {
    @NotNull
    private final FloatValue heightValue = new FloatValue("Height", 2.0f, 1.1f, 7.0f);
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final BoolValue glassValue;
    @NotNull
    private final Value<Float> stableMotionValue;
    private double jumpY;
    private int martrixStatus;
    private boolean martrixWasTimer;
    @NotNull
    private final MSTimer timer;

    public HighJump() {
        String[] stringArray = new String[]{"Vanilla", "StableMotion", "Damage", "AACv3", "DAC", "Mineplex", "Matrix", "MatrixWater", "Pika"};
        this.modeValue = new ListValue("Mode", stringArray, "Vanilla");
        this.glassValue = new BoolValue("OnlyGlassPane", false);
        this.stableMotionValue = new FloatValue("StableMotion", 0.42f, 0.1f, 1.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ HighJump this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return HighJump.access$getModeValue$p(this.this$0).equals("StableMotion");
            }
        });
        this.jumpY = 114514.0;
        this.timer = new MSTimer();
    }

    @Override
    public void onEnable() {
        this.jumpY = 114514.0;
        this.martrixStatus = 0;
        this.martrixWasTimer = false;
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }

    /*
     * Unable to fully structure code
     */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)this.glassValue.get()).booleanValue() && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockPane)) {
            return;
        }
        var4_2 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(var4_2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        var2_4 = var4_2;
        tmp = -1;
        switch (var2_4.hashCode()) {
            case -1339126929: {
                if (var2_4.equals("damage")) {
                    tmp = 1;
                }
                break;
            }
            case -358093354: {
                if (var2_4.equals("matrixWater")) {
                    tmp = 2;
                }
                break;
            }
            case 92570112: {
                if (var2_4.equals("aacv3")) {
                    tmp = 3;
                }
                break;
            }
            case -1362669950: {
                if (var2_4.equals("mineplex")) {
                    tmp = 4;
                }
                break;
            }
            case 99206: {
                if (var2_4.equals("dac")) {
                    tmp = 5;
                }
                break;
            }
            case -1331973455: {
                if (var2_4.equals("stablemotion")) {
                    tmp = 6;
                }
                break;
            }
            case 3440911: {
                if (var2_4.equals("pika")) {
                    tmp = 7;
                }
                break;
            }
            case -1081239615: {
                if (var2_4.equals("matrix")) {
                    tmp = 8;
                }
                break;
            }
        }
        switch (tmp) {
            case 1: {
                if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 0 || !MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                var3_5 = MinecraftInstance.mc.field_71439_g;
                var3_5.field_70181_x += (double)(0.42f * ((Number)this.heightValue.get()).floatValue());
                break;
            }
            case 7: {
                if (MinecraftInstance.mc.field_71439_g.field_70737_aN <= 0 || !MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                var3_6 = MinecraftInstance.mc.field_71439_g;
                var3_6.field_70181_x += 6.299999713897705;
                break;
            }
            case 3: {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                var3_7 = MinecraftInstance.mc.field_71439_g;
                var3_7.field_70181_x += 0.059;
                break;
            }
            case 5: {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                var3_8 = MinecraftInstance.mc.field_71439_g;
                var3_8.field_70181_x += 0.049999;
                break;
            }
            case 4: {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MovementUtils.INSTANCE.strafe(0.35f);
                break;
            }
            case 6: {
                if (this.jumpY == 114514.0) break;
                if (this.jumpY + ((Number)this.heightValue.get()).doubleValue() - (double)true > MinecraftInstance.mc.field_71439_g.field_70163_u) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.stableMotionValue.get()).floatValue();
                    break;
                }
                this.jumpY = 114514.0;
                break;
            }
            case 2: {
                if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                if (Intrinsics.areEqual(MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)true, MinecraftInstance.mc.field_71439_g.field_70161_v)).func_177230_c(), Block.func_149729_e((int)9))) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.18;
                    break;
                }
                if (!Intrinsics.areEqual(MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)).func_177230_c(), Block.func_149729_e((int)9))) break;
                MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.heightValue.get()).floatValue();
                MinecraftInstance.mc.field_71439_g.field_70122_E = true;
                break;
            }
            case 8: {
                if (this.martrixWasTimer) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    this.martrixWasTimer = false;
                }
                var3_9 = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, MinecraftInstance.mc.field_71439_g.field_70181_x, 0.0).func_72314_b(0.0, 0.0, 0.0));
                Intrinsics.checkNotNullExpressionValue(var3_9, "mc.theWorld.getColliding\u20260).expand(0.0, 0.0, 0.0))");
                if (((Collection)var3_9).isEmpty() == false) ** GOTO lbl102
                var3_9 = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -4.0, 0.0).func_72314_b(0.0, 0.0, 0.0));
                Intrinsics.checkNotNullExpressionValue(var3_9, "mc.theWorld.getColliding\u20260).expand(0.0, 0.0, 0.0))");
                if (!(((Collection)var3_9).isEmpty() == false)) ** GOTO lbl105
lbl102:
                // 2 sources

                if (MinecraftInstance.mc.field_71439_g.field_70143_R > 10.0f && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 0.1f;
                    this.martrixWasTimer = true;
                }
lbl105:
                // 4 sources

                if (this.timer.hasTimePassed(1000L) && this.martrixStatus == 1) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                    MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                    this.martrixStatus = 0;
                    return;
                }
                if (this.martrixStatus == 1 && MinecraftInstance.mc.field_71439_g.field_70737_aN > 0) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 3.0;
                    MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                    MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                    MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
                    this.martrixStatus = 0;
                    return;
                }
                if (this.martrixStatus == 2) {
                    MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C0APacketAnimation());
                    MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
                    var3_10 = 8;
                    var4_3 = 0;
                    while (var4_3 < var3_10) {
                        it = var5_11 = var4_3++;
                        $i$a$-repeat-HighJump$onUpdate$1 = false;
                        MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.399, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
                        MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
                    }
                    MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
                    MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 0.6f;
                    this.martrixStatus = 1;
                    this.timer.reset();
                    MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)true, MinecraftInstance.mc.field_71439_g.field_70161_v), EnumFacing.UP));
                    MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C0APacketAnimation());
                    return;
                }
                if (MinecraftInstance.mc.field_71439_g.field_70123_F && this.martrixStatus == 0 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)true, MinecraftInstance.mc.field_71439_g.field_70161_v), EnumFacing.UP));
                    MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C0APacketAnimation());
                    this.martrixStatus = 2;
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 0.05f;
                }
                if (!MinecraftInstance.mc.field_71439_g.field_70123_F || !MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70122_E = false;
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)this.glassValue.get()).booleanValue() && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockPane)) {
            return;
        }
        if (!MinecraftInstance.mc.field_71439_g.field_70122_E) {
            String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (Intrinsics.areEqual("mineplex", string)) {
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70181_x = entityPlayerSP.field_70181_x + (MinecraftInstance.mc.field_71439_g.field_70143_R == 0.0f ? 0.0499 : 0.05);
            }
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)this.glassValue.get()).booleanValue() && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockPane)) {
            return;
        }
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "vanilla": {
                event.setMotion(event.getMotion() * ((Number)this.heightValue.get()).floatValue());
                break;
            }
            case "mineplex": {
                event.setMotion(0.47f);
                break;
            }
            case "stablemotion": {
                this.jumpY = MinecraftInstance.mc.field_71439_g.field_70163_u;
            }
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(HighJump $this) {
        return $this.modeValue;
    }
}

