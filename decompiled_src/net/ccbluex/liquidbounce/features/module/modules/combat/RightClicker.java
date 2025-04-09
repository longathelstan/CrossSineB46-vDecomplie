/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.RightClicker;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="RightClicker", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\b\u0010\u0016\u001a\u00020\u0013H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/RightClicker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canRightClick", "", "getCanRightClick", "()Z", "setCanRightClick", "(Z)V", "rightBlockOnly", "Lnet/ccbluex/liquidbounce/features/value/Value;", "rightCPS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "rightMaxCPSValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "rightMinCPSValue", "rightOption", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "rightClicker", "CrossSine"})
public final class RightClicker
extends Module {
    @NotNull
    public static final RightClicker INSTANCE = new RightClicker();
    @NotNull
    private static final BoolValue rightOption = new BoolValue("Right-Option", true);
    @NotNull
    private static final IntegerValue rightMaxCPSValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int minCPS = ((Number)RightClicker.access$getRightMinCPSValue$p().get()).intValue();
            if (minCPS > newValue) {
                this.set(minCPS);
            }
        }
    }.displayable(rightMaxCPSValue.2.INSTANCE);
    @NotNull
    private static final IntegerValue rightMinCPSValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int maxCPS = ((Number)RightClicker.access$getRightMaxCPSValue$p().get()).intValue();
            if (maxCPS < newValue) {
                this.set(maxCPS);
            }
        }
    }.displayable(rightMinCPSValue.2.INSTANCE);
    @NotNull
    private static final Value<Boolean> rightBlockOnly = new BoolValue("Right-BlockOnly", false).displayable(rightBlockOnly.1.INSTANCE);
    private static boolean canRightClick;
    @NotNull
    private static TimerMS rightCPS;

    private RightClicker() {
    }

    public final boolean getCanRightClick() {
        return canRightClick;
    }

    public final void setCanRightClick(boolean bl) {
        canRightClick = bl;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.rightClicker();
    }

    private final void rightClicker() {
        block8: {
            block7: {
                block6: {
                    if (!rightBlockOnly.get().booleanValue()) break block6;
                    ItemStack itemStack = MinecraftInstance.mc.field_71439_g.func_70694_bm();
                    if (!((itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemBlock)) break block7;
                }
                ItemStack itemStack = MinecraftInstance.mc.field_71439_g.func_70694_bm();
                if (!((itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemSword)) break block8;
            }
            MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
            canRightClick = false;
            return;
        }
        if (MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d() && rightCPS.hasTimePassed(TimeUtils.INSTANCE.randomClickDelay(((Number)rightMinCPSValue.get()).intValue(), ((Number)rightMaxCPSValue.get()).intValue()))) {
            MouseUtils.INSTANCE.setRightClicked(true);
            KeyBinding.func_74507_a((int)MinecraftInstance.mc.field_71474_y.field_74313_G.func_151463_i());
            canRightClick = true;
            rightCPS.reset();
        } else if (rightCPS.hasTimePassed(10L)) {
            MouseUtils.INSTANCE.setRightClicked(false);
        }
    }

    public static final /* synthetic */ IntegerValue access$getRightMinCPSValue$p() {
        return rightMinCPSValue;
    }

    public static final /* synthetic */ BoolValue access$getRightOption$p() {
        return rightOption;
    }

    public static final /* synthetic */ IntegerValue access$getRightMaxCPSValue$p() {
        return rightMaxCPSValue;
    }

    static {
        rightCPS = new TimerMS();
    }
}

