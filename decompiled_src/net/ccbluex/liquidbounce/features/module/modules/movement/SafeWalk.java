/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.SafeWalk;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name="SafeWalk", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u001bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/SafeWalk;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "PitchLitmit", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "PitchMax", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "PitchMin", "ShiftMax", "getShiftMax", "()Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "ShiftMin", "noSpeedPotion", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "og", "onBlock", "onHoldShift", "shiftValue", "getShift", "", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "CrossSine"})
public final class SafeWalk
extends Module {
    @NotNull
    public static final SafeWalk INSTANCE = new SafeWalk();
    @NotNull
    private static final BoolValue shiftValue = new BoolValue("Shift", false);
    @NotNull
    private static final BoolValue og = new BoolValue("OnlyGround", false);
    @NotNull
    private static final Value<Boolean> onBlock = new BoolValue("Block only", false).displayable(onBlock.1.INSTANCE);
    @NotNull
    private static final Value<Boolean> noSpeedPotion = new BoolValue("NoPotionSpeed", false).displayable(noSpeedPotion.1.INSTANCE);
    @NotNull
    private static final Value<Boolean> onHoldShift = new BoolValue("OnHoldShift", false).displayable(onHoldShift.1.INSTANCE);
    @NotNull
    private static final IntegerValue ShiftMax = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int ShiftMin2 = ((Number)SafeWalk.access$getShiftMin$p().get()).intValue();
            if (ShiftMin2 > newValue) {
                this.set(ShiftMin2);
            }
        }
    }.displayable(ShiftMax.2.INSTANCE);
    @NotNull
    private static final IntegerValue ShiftMin = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int ShiftMax2 = ((Number)SafeWalk.INSTANCE.getShiftMax().get()).intValue();
            if (ShiftMax2 < newValue) {
                this.set(ShiftMax2);
            }
        }
    }.displayable(ShiftMin.2.INSTANCE);
    @NotNull
    private static final BoolValue PitchLitmit = new BoolValue("Pitch", false);
    @NotNull
    private static final IntegerValue PitchMax = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int PitchMin2 = ((Number)SafeWalk.access$getPitchMin$p().get()).intValue();
            if (PitchMin2 > newValue) {
                this.set(PitchMin2);
            }
        }
    }.displayable(PitchMax.2.INSTANCE);
    @NotNull
    private static final IntegerValue PitchMin = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int PitchMax2 = ((Number)SafeWalk.access$getPitchMax$p().get()).intValue();
            if (PitchMax2 < newValue) {
                this.set(PitchMax2);
            }
        }
    }.displayable(PitchMin.2.INSTANCE);

    private SafeWalk() {
    }

    @NotNull
    public final IntegerValue getShiftMax() {
        return ShiftMax;
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)shiftValue.get()).booleanValue()) {
            return;
        }
        if (!((Boolean)og.get()).booleanValue() || MinecraftInstance.mc.field_71439_g.field_70122_E) {
            event.setSafeWalk((Boolean)PitchLitmit.get() == false || MinecraftInstance.mc.field_71439_g.field_70125_A < (float)((Number)PitchMax.get()).intValue() && MinecraftInstance.mc.field_71439_g.field_70125_A > (float)((Number)PitchMin.get()).intValue());
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!((Boolean)shiftValue.get()).booleanValue()) {
            return;
        }
        if (MinecraftInstance.mc.field_71462_r == null) {
            if (MinecraftInstance.mc.field_71474_y.field_74368_y.func_151470_d()) {
                if (!onBlock.get().booleanValue() || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
                    if (!(onHoldShift.get().booleanValue() && !Keyboard.isKeyDown((int)MinecraftInstance.mc.field_71474_y.field_74311_E.func_151463_i()) || ((Boolean)og.get()).booleanValue() && !MinecraftInstance.mc.field_71439_g.field_70122_E || noSpeedPotion.get().booleanValue() && MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c) || ((Boolean)PitchLitmit.get()).booleanValue() && (!(MinecraftInstance.mc.field_71439_g.field_70125_A < (float)((Number)PitchMax.get()).intValue()) || !(MinecraftInstance.mc.field_71439_g.field_70125_A > (float)((Number)PitchMin.get()).intValue())))) {
                        MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = Intrinsics.areEqual(MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t + MinecraftInstance.mc.field_71439_g.field_70159_w * this.getShift(), MinecraftInstance.mc.field_71439_g.field_70163_u - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v + MinecraftInstance.mc.field_71439_g.field_70179_y * this.getShift())).func_177230_c(), Blocks.field_150350_a);
                        return;
                    }
                } else {
                    MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
                }
            }
            if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0f && MinecraftInstance.mc.field_71439_g.func_70093_af() && !Keyboard.isKeyDown((int)MinecraftInstance.mc.field_71474_y.field_74311_E.func_151463_i())) {
                MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = Keyboard.isKeyDown((int)MinecraftInstance.mc.field_71474_y.field_74311_E.func_151463_i());
            }
            if (onBlock.get().booleanValue() && !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock)) {
                MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = Keyboard.isKeyDown((int)MinecraftInstance.mc.field_71474_y.field_74311_E.func_151463_i());
            }
        }
    }

    private final double getShift() {
        int fuckmin = ((Number)ShiftMin.get()).intValue() / 10;
        int fuckmax = ((Number)ShiftMax.get()).intValue() / 10;
        double min = Math.min(fuckmin, fuckmax);
        double max = Math.max(fuckmin, fuckmax);
        return Math.random() * (max - min) + min;
    }

    @Override
    public void onDisable() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
    }

    public static final /* synthetic */ BoolValue access$getShiftValue$p() {
        return shiftValue;
    }

    public static final /* synthetic */ IntegerValue access$getShiftMin$p() {
        return ShiftMin;
    }

    public static final /* synthetic */ IntegerValue access$getPitchMin$p() {
        return PitchMin;
    }

    public static final /* synthetic */ BoolValue access$getPitchLitmit$p() {
        return PitchLitmit;
    }

    public static final /* synthetic */ IntegerValue access$getPitchMax$p() {
        return PitchMax;
    }
}

