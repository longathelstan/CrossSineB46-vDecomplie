/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Glint", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Glint;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blueValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "greenValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "redValue", "getColor", "Ljava/awt/Color;", "CrossSine"})
public final class Glint
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final Value<Integer> redValue;
    @NotNull
    private final Value<Integer> greenValue;
    @NotNull
    private final Value<Integer> blueValue;

    public Glint() {
        String[] stringArray = new String[]{"Theme", "AnotherRainbow", "Custom"};
        this.modeValue = new ListValue("Mode", stringArray, "Custom");
        this.redValue = new IntegerValue("Red", 255, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Glint this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Glint.access$getModeValue$p(this.this$0).equals("Custom");
            }
        });
        this.greenValue = new IntegerValue("Green", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Glint this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Glint.access$getModeValue$p(this.this$0).equals("Custom");
            }
        });
        this.blueValue = new IntegerValue("Blue", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Glint this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Glint.access$getModeValue$p(this.this$0).equals("Custom");
            }
        });
    }

    @NotNull
    public final Color getColor() {
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String string2 = string;
        return Intrinsics.areEqual(string2, "theme") ? ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, null) : (Intrinsics.areEqual(string2, "anotherrainbow") ? ColorUtils.skyRainbow(10, 0.9f, 1.0f, 1.0) : new Color(((Number)this.redValue.get()).intValue(), ((Number)this.greenValue.get()).intValue(), ((Number)this.blueValue.get()).intValue()));
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(Glint $this) {
        return $this.modeValue;
    }
}

