/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="ViewBobing", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/ViewBobing;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "BobChangerValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "CustomYaw", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "miniViewBobing", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getMiniViewBobing", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "CrossSine"})
public final class ViewBobing
extends Module {
    @NotNull
    private final BoolValue miniViewBobing = new BoolValue("MiniViewBobing", false);
    @NotNull
    private final ListValue BobChangerValue;
    @NotNull
    private final Value<Float> CustomYaw;

    public ViewBobing() {
        String[] stringArray = new String[]{"Low", "VeryLow", "Meme", "Custom", "Off"};
        this.BobChangerValue = new ListValue("BobChanger", stringArray, "Low");
        this.CustomYaw = new FloatValue("BobCustom", 0.0f, 0.0f, 10.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ViewBobing this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return ViewBobing.access$getBobChangerValue$p(this.this$0).equals("Custom");
            }
        });
    }

    @NotNull
    public final BoolValue getMiniViewBobing() {
        return this.miniViewBobing;
    }

    @EventTarget
    public final void onMotion(@Nullable MotionEvent event) {
        block16: {
            if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break block16;
            String string = ((String)this.BobChangerValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "low": {
                    MinecraftInstance.mc.field_71439_g.field_71109_bG = 0.03f;
                    break;
                }
                case "verylow": {
                    MinecraftInstance.mc.field_71439_g.field_71109_bG = 0.01f;
                    break;
                }
                case "meme": {
                    MinecraftInstance.mc.field_71439_g.field_71109_bG = 10.0f;
                    break;
                }
                case "custom": {
                    MinecraftInstance.mc.field_71439_g.field_71109_bG = ((Number)this.CustomYaw.get()).floatValue();
                }
            }
        }
    }

    @Override
    @Nullable
    public String getTag() {
        return (String)this.BobChangerValue.get();
    }

    public static final /* synthetic */ ListValue access$getBobChangerValue$p(ViewBobing $this) {
        return $this.BobChangerValue;
    }
}

