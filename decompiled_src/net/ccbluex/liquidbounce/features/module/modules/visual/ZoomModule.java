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
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.KeyBindValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Zoom", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/ZoomModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "currentFov", "", "prevFov", "zoomKey", "Lnet/ccbluex/liquidbounce/features/value/KeyBindValue;", "getZoomKey", "()Lnet/ccbluex/liquidbounce/features/value/KeyBindValue;", "zoomLevel", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "zoomSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "zooming", "", "onDisable", "", "onEnable", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class ZoomModule
extends Module {
    @NotNull
    public static final ZoomModule INSTANCE = new ZoomModule();
    @NotNull
    private static final FloatValue zoomSpeed = new FloatValue("Speed", 0.25f, 0.01f, 1.0f);
    @NotNull
    private static final IntegerValue zoomLevel = new IntegerValue("Level", 30, 30, 70);
    @NotNull
    private static final KeyBindValue zoomKey = new KeyBindValue("ZoomKey", 46);
    private static boolean zooming;
    private static float prevFov;
    private static float currentFov;

    private ZoomModule() {
    }

    @NotNull
    public final KeyBindValue getZoomKey() {
        return zoomKey;
    }

    @Override
    public void onEnable() {
        prevFov = MinecraftInstance.mc.field_71474_y.field_74334_X;
        currentFov = MinecraftInstance.mc.field_71474_y.field_74334_X;
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71474_y.field_74334_X = prevFov;
        prevFov = 0.0f;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!zooming && zoomKey.isPressed() && MinecraftInstance.mc.field_71462_r == null) {
            prevFov = MinecraftInstance.mc.field_71474_y.field_74334_X;
            currentFov = MinecraftInstance.mc.field_71474_y.field_74334_X;
            zooming = true;
        } else if (!zoomKey.isPressed() || MinecraftInstance.mc.field_71462_r != null) {
            zooming = false;
        }
        currentFov = zooming && MinecraftInstance.mc.field_71462_r == null ? (currentFov += ((float)((Number)zoomLevel.get()).intValue() - currentFov) * ((Number)zoomSpeed.get()).floatValue()) : (currentFov += (prevFov - currentFov) * ((Number)zoomSpeed.get()).floatValue());
        MinecraftInstance.mc.field_71474_y.field_74334_X = currentFov;
        MinecraftInstance.mc.field_71474_y.field_74334_X = RangesKt.coerceIn(MinecraftInstance.mc.field_71474_y.field_74334_X, 0.0f, prevFov);
    }
}

