/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.visual.ZoomModule;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Camera", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0006R\u0011\u0010\u000e\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0006\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/CameraModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "clipValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getClipValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "curFov", "", "prevFov", "shouldSmooth", "", "smoothF5", "getSmoothF5", "smoothValue", "getSmoothValue", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class CameraModule
extends Module {
    @NotNull
    public static final CameraModule INSTANCE = new CameraModule();
    @NotNull
    private static final BoolValue clipValue = new BoolValue("Clip", false);
    @NotNull
    private static final BoolValue smoothValue = new BoolValue("Motion", false);
    @NotNull
    private static final BoolValue smoothF5 = new BoolValue("SmoothF5", false);
    private static boolean shouldSmooth;
    private static float prevFov;
    private static float curFov;

    private CameraModule() {
    }

    @NotNull
    public final BoolValue getClipValue() {
        return clipValue;
    }

    @NotNull
    public final BoolValue getSmoothValue() {
        return smoothValue;
    }

    @NotNull
    public final BoolValue getSmoothF5() {
        return smoothF5;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (ZoomModule.INSTANCE.getState() && ZoomModule.INSTANCE.getZoomKey().isPressed()) {
            return;
        }
        if (((Boolean)smoothF5.get()).booleanValue()) {
            if (MinecraftInstance.mc.field_71474_y.field_74320_O == 0 && shouldSmooth) {
                if (!(MinecraftInstance.mc.field_71474_y.field_74334_X == prevFov)) {
                    MinecraftInstance.mc.field_71474_y.field_74334_X = prevFov;
                }
                shouldSmooth = false;
            } else if (!shouldSmooth) {
                prevFov = MinecraftInstance.mc.field_71474_y.field_74334_X;
                curFov = -10.0f;
                shouldSmooth = true;
            }
            if (shouldSmooth && MinecraftInstance.mc.field_71462_r == null) {
                curFov += (prevFov - curFov) * 0.1f;
            } else if (!(curFov == prevFov)) {
                curFov = prevFov;
            }
            if (shouldSmooth) {
                MinecraftInstance.mc.field_71474_y.field_74334_X = curFov;
                MinecraftInstance.mc.field_71474_y.field_74334_X = RangesKt.coerceIn(MinecraftInstance.mc.field_71474_y.field_74334_X, 0.0f, prevFov);
            }
        }
    }
}

