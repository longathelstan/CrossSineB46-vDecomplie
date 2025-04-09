/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.shader.Shader;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="MotionBlur", category=ModuleCategory.VISUAL, array=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/MotionBlur;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blurAmount", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "onDisable", "", "onTick", "event", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "CrossSine"})
public final class MotionBlur
extends Module {
    @NotNull
    private final IntegerValue blurAmount = new IntegerValue("Amount", 7, 1, 10);

    @Override
    public void onDisable() {
        if (MinecraftInstance.mc.field_71460_t.func_147702_a()) {
            MinecraftInstance.mc.field_71460_t.func_181022_b();
        }
    }

    @EventTarget
    public final void onTick(@NotNull TickEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        try {
            if (MinecraftInstance.mc.field_71439_g != null) {
                if (MinecraftInstance.mc.field_71460_t.func_147706_e() == null) {
                    MinecraftInstance.mc.field_71460_t.func_175069_a(new ResourceLocation("minecraft", "shaders/post/motion_blur.json"));
                }
                float uniform = 1.0f - RangesKt.coerceAtMost(((Number)this.blurAmount.get()).floatValue() / 10.0f, 0.9f);
                if (MinecraftInstance.mc.field_71460_t.func_147706_e() != null) {
                    ((Shader)MinecraftInstance.mc.field_71460_t.func_147706_e().field_148031_d.get(0)).func_148043_c().func_147991_a("Phosphor").func_148095_a(uniform, 0.0f, 0.0f);
                }
            }
        }
        catch (Exception a) {
            a.printStackTrace();
        }
    }
}

