/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="HitColor", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/HitColor;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "hitColorAlphaValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "getHitColorAlphaValue", "()Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "hitColorBValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getHitColorBValue", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "hitColorGValue", "getHitColorGValue", "hitColorRValue", "getHitColorRValue", "hitColorTheme", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getHitColorTheme", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "CrossSine"})
public final class HitColor
extends Module {
    @NotNull
    private final BoolValue hitColorTheme = new BoolValue("ColorTheme", false);
    @NotNull
    private final Value<Integer> hitColorRValue = new IntegerValue("HitRed", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ HitColor this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)this.this$0.getHitColorTheme().get() == false;
        }
    });
    @NotNull
    private final Value<Integer> hitColorGValue = new IntegerValue("HitGreen", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ HitColor this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)this.this$0.getHitColorTheme().get() == false;
        }
    });
    @NotNull
    private final Value<Integer> hitColorBValue = new IntegerValue("HitBlue", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ HitColor this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)this.this$0.getHitColorTheme().get() == false;
        }
    });
    @NotNull
    private final IntegerValue hitColorAlphaValue = new IntegerValue("HitAlpha", 255, 0, 255);

    @NotNull
    public final BoolValue getHitColorTheme() {
        return this.hitColorTheme;
    }

    @NotNull
    public final Value<Integer> getHitColorRValue() {
        return this.hitColorRValue;
    }

    @NotNull
    public final Value<Integer> getHitColorGValue() {
        return this.hitColorGValue;
    }

    @NotNull
    public final Value<Integer> getHitColorBValue() {
        return this.hitColorBValue;
    }

    @NotNull
    public final IntegerValue getHitColorAlphaValue() {
        return this.hitColorAlphaValue;
    }
}

