/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitSelect;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleInfo(name="AutoClicker", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u0013H\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020 H\u0002J\u0010\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020&H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006'"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoClicker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canLeftClick", "", "getCanLeftClick", "()Z", "setCanLeftClick", "(Z)V", "invClicker", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getInvClicker", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "jitterAmount", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "leftCPS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "leftDelay", "", "leftMaxCPSValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "leftMinCPSValue", "leftSwordOnlyValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "getDelay", "inInvClick", "", "guiScreen", "Lnet/minecraft/client/gui/GuiScreen;", "leftClicker", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class AutoClicker
extends Module {
    @NotNull
    public static final AutoClicker INSTANCE = new AutoClicker();
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final BoolValue invClicker;
    @NotNull
    private static final IntegerValue leftMaxCPSValue;
    @NotNull
    private static final IntegerValue leftMinCPSValue;
    @NotNull
    private static final Value<Float> jitterAmount;
    @NotNull
    private static final BoolValue leftSwordOnlyValue;
    @NotNull
    private static final TimerMS leftCPS;
    private static boolean canLeftClick;
    private static int leftDelay;

    private AutoClicker() {
    }

    @NotNull
    public final BoolValue getInvClicker() {
        return invClicker;
    }

    public final boolean getCanLeftClick() {
        return canLeftClick;
    }

    public final void setCanLeftClick(boolean bl) {
        canLeftClick = bl;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)invClicker.get()).booleanValue() && MinecraftInstance.mc.field_71462_r != null) {
            if (!Mouse.isButtonDown((int)0) || !Keyboard.isKeyDown((int)54) && !Keyboard.isKeyDown((int)42)) {
                return;
            }
            GuiScreen guiScreen = MinecraftInstance.mc.field_71462_r;
            Intrinsics.checkNotNullExpressionValue(guiScreen, "mc.currentScreen");
            this.inInvClick(guiScreen);
        }
        this.leftClicker();
    }

    private final int getDelay() {
        String string = ((String)modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "normal": {
                leftDelay = (int)TimeUtils.INSTANCE.randomClickDelay(((Number)leftMinCPSValue.get()).intValue(), ((Number)leftMaxCPSValue.get()).intValue());
                break;
            }
            case "jitter": {
                leftDelay = Random.Default.nextInt(1, 14) <= 3 ? (Random.Default.nextInt(1, 3) == 1 ? Random.Default.nextInt(98, 102) : Random.Default.nextInt(114, 117)) : (Random.Default.nextInt(1, 4) == 1 ? Random.Default.nextInt(64, 69) : Random.Default.nextInt(83, 85));
                break;
            }
            case "butterfly": {
                leftDelay = Random.Default.nextInt(1, 10) == 1 ? Random.Default.nextInt(225, 250) : (Random.Default.nextInt(1, 6) == 1 ? Random.Default.nextInt(89, 94) : (Random.Default.nextInt(1, 3) == 1 ? Random.Default.nextInt(95, 103) : (Random.Default.nextInt(1, 3) == 1 ? Random.Default.nextInt(115, 123) : (Random.Default.nextBoolean() ? Random.Default.nextInt(131, 136) : Random.Default.nextInt(165, 174)))));
            }
        }
        return leftDelay;
    }

    private final void leftClicker() {
        block12: {
            block10: {
                block11: {
                    if (MinecraftInstance.mc.field_71439_g.func_70632_aY() || MinecraftInstance.mc.field_71439_g.func_70113_ah()) break block10;
                    if (!((Boolean)leftSwordOnlyValue.get()).booleanValue()) break block11;
                    ItemStack itemStack = MinecraftInstance.mc.field_71439_g.func_70694_bm();
                    if (!((itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemSword)) break block10;
                }
                if (MinecraftInstance.mc.field_71476_x == null || MinecraftInstance.mc.field_71476_x.field_72313_a != MovingObjectPosition.MovingObjectType.BLOCK) break block12;
            }
            MouseUtils.INSTANCE.setLeftClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74312_F));
            canLeftClick = false;
            return;
        }
        if (HitSelect.INSTANCE.getState() && HitSelect.INSTANCE.getCancelClick() && HitSelect.INSTANCE.getMode().equals("Pause")) {
            if (leftCPS.hasTimePassed(30L)) {
                MouseUtils.INSTANCE.setLeftClicked(false);
            }
            return;
        }
        if (SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() != null) {
            canLeftClick = false;
            return;
        }
        if (MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d() && leftCPS.hasTimePassed(this.getDelay())) {
            KeyBinding.func_74507_a((int)MinecraftInstance.mc.field_71474_y.field_74312_F.func_151463_i());
            if (modeValue.equals("Jitter")) {
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70177_z += RandomUtils.INSTANCE.nextFloat(-((Number)jitterAmount.get()).floatValue(), ((Number)jitterAmount.get()).floatValue());
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70125_A += RandomUtils.INSTANCE.nextFloat(-((Number)jitterAmount.get()).floatValue(), ((Number)jitterAmount.get()).floatValue());
            }
            MouseUtils.INSTANCE.setLeftClicked(true);
            canLeftClick = true;
            leftCPS.reset();
        } else if (leftCPS.hasTimePassed(30L)) {
            MouseUtils.INSTANCE.setLeftClicked(false);
        }
    }

    private final void inInvClick(GuiScreen guiScreen) {
        int mouseInGUIPosX = Mouse.getX() * guiScreen.field_146294_l / MinecraftInstance.mc.field_71443_c;
        int mouseInGUIPosY = guiScreen.field_146295_m - Mouse.getY() * guiScreen.field_146295_m / MinecraftInstance.mc.field_71440_d - 1;
        try {
            if (leftCPS.hasTimePassed(this.getDelay())) {
                Object[] objectArray = new String[]{"func_73864_a", "mouseClicked"};
                String[] stringArray = objectArray;
                objectArray = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE};
                Method method = ReflectionHelper.findMethod(GuiScreen.class, null, (String[])stringArray, (Class[])objectArray);
                objectArray = new Object[]{mouseInGUIPosX, mouseInGUIPosY, 0};
                method.invoke((Object)guiScreen, objectArray);
                leftCPS.reset();
            }
        }
        catch (IllegalAccessException illegalAccessException) {
        }
        catch (InvocationTargetException invocationTargetException) {
            // empty catch block
        }
    }

    @Override
    @Nullable
    public String getTag() {
        return ((Number)leftMaxCPSValue.get()).intValue() + " - " + ((Number)leftMinCPSValue.get()).intValue() + " CPS";
    }

    public static final /* synthetic */ IntegerValue access$getLeftMinCPSValue$p() {
        return leftMinCPSValue;
    }

    public static final /* synthetic */ ListValue access$getModeValue$p() {
        return modeValue;
    }

    public static final /* synthetic */ IntegerValue access$getLeftMaxCPSValue$p() {
        return leftMaxCPSValue;
    }

    static {
        String[] stringArray = new String[]{"Normal", "Jitter", "Butterfly"};
        modeValue = new ListValue("Left-Click-Mode", stringArray, "Normal");
        invClicker = new BoolValue("InvClicker", false);
        leftMaxCPSValue = (IntegerValue)new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int minCPS = ((Number)AutoClicker.access$getLeftMinCPSValue$p().get()).intValue();
                if (minCPS > newValue) {
                    this.set(minCPS);
                }
            }
        }.displayable(leftMaxCPSValue.2.INSTANCE);
        leftMinCPSValue = (IntegerValue)new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int maxCPS = ((Number)AutoClicker.access$getLeftMaxCPSValue$p().get()).intValue();
                if (maxCPS < newValue) {
                    this.set(maxCPS);
                }
            }
        }.displayable(leftMinCPSValue.2.INSTANCE);
        jitterAmount = new FloatValue("Jitter-Rotate-Amount", 1.0f, 0.0f, 10.0f).displayable(jitterAmount.1.INSTANCE);
        leftSwordOnlyValue = new BoolValue("Left-SwordOnly", false);
        leftCPS = new TimerMS();
    }
}

