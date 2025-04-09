/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MinecraftInstanceKt;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Animations", category=ModuleCategory.VISUAL, canEnable=true, array=false, defaultOn=true)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0013\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001c\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0010R\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0006\u00a8\u0006("}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Animations;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "BlockAnimation", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getBlockAnimation", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "blockingModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getBlockingModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "fluxAnimation", "getFluxAnimation", "itemPosXValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "getItemPosXValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "itemPosYValue", "getItemPosYValue", "itemPosZValue", "getItemPosZValue", "itemScaleValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "getItemScaleValue", "()Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "oldSneak", "getOldSneak", "resetValue", "swingSpeedValue", "getSwingSpeedValue", "tag", "", "getTag", "()Ljava/lang/String;", "useItem", "getUseItem", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class Animations
extends Module {
    @NotNull
    public static final Animations INSTANCE = new Animations();
    @NotNull
    private static final ListValue blockingModeValue;
    @NotNull
    private static final BoolValue resetValue;
    @NotNull
    private static final FloatValue itemPosXValue;
    @NotNull
    private static final FloatValue itemPosYValue;
    @NotNull
    private static final FloatValue itemPosZValue;
    @NotNull
    private static final IntegerValue itemScaleValue;
    @NotNull
    private static final FloatValue swingSpeedValue;
    @NotNull
    private static final BoolValue fluxAnimation;
    @NotNull
    private static final BoolValue BlockAnimation;
    @NotNull
    private static final BoolValue useItem;
    @NotNull
    private static final BoolValue oldSneak;

    private Animations() {
    }

    @NotNull
    public final ListValue getBlockingModeValue() {
        return blockingModeValue;
    }

    @NotNull
    public final FloatValue getItemPosXValue() {
        return itemPosXValue;
    }

    @NotNull
    public final FloatValue getItemPosYValue() {
        return itemPosYValue;
    }

    @NotNull
    public final FloatValue getItemPosZValue() {
        return itemPosZValue;
    }

    @NotNull
    public final IntegerValue getItemScaleValue() {
        return itemScaleValue;
    }

    @NotNull
    public final FloatValue getSwingSpeedValue() {
        return swingSpeedValue;
    }

    @NotNull
    public final BoolValue getFluxAnimation() {
        return fluxAnimation;
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)blockingModeValue.get();
    }

    @NotNull
    public final BoolValue getBlockAnimation() {
        return BlockAnimation;
    }

    @NotNull
    public final BoolValue getUseItem() {
        return useItem;
    }

    @NotNull
    public final BoolValue getOldSneak() {
        return oldSneak;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)resetValue.get()).booleanValue()) {
            itemPosXValue.set(Float.valueOf(0.0f));
            itemPosZValue.set(Float.valueOf(0.0f));
            itemPosYValue.set(Float.valueOf(0.0f));
            itemScaleValue.set(100);
            swingSpeedValue.set(Float.valueOf(1.0f));
            resetValue.set(false);
        }
        if (((Boolean)useItem.get()).booleanValue() && MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d() && MinecraftInstance.mc.field_71476_x.func_178782_a() != null) {
            MinecraftInstance.mc.field_71442_b.func_78767_c();
        }
        if (((Boolean)BlockAnimation.get()).booleanValue() && MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d() && MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d() && MinecraftInstanceKt.getMc().field_71476_x != null && MinecraftInstanceKt.getMc().field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
            PlayerUtils.INSTANCE.swing();
        }
    }

    static {
        String[] stringArray = new String[]{"1.7", "Akrien", "Avatar", "ETB", "Exhibition", "Dortware", "Push", "Reverse", "Shield", "SigmaNew", "SigmaOld", "Slide", "SlideDown", "HSlide", "Swong", "VisionFX", "Swank", "Jello", "Rotate", "Liquid", "Fall", "Yeet", "Yeet2", "None"};
        blockingModeValue = new ListValue("BlockingMode", stringArray, "1.7");
        resetValue = new BoolValue("Reset", false);
        itemPosXValue = new FloatValue("ItemPosX", 0.0f, -1.0f, 1.0f);
        itemPosYValue = new FloatValue("ItemPosY", 0.0f, -1.0f, 1.0f);
        itemPosZValue = new FloatValue("ItemPosZ", 0.0f, -1.0f, 1.0f);
        itemScaleValue = new IntegerValue("ItemScale", 100, 0, 100);
        swingSpeedValue = new FloatValue("SwingSpeed", 1.0f, 0.5f, 5.0f);
        fluxAnimation = new BoolValue("Flux Swing", false);
        BlockAnimation = new BoolValue("BlockAnimation", true);
        useItem = new BoolValue("UseItemWhileDigging", true);
        oldSneak = new BoolValue("OldSneak", true);
    }
}

