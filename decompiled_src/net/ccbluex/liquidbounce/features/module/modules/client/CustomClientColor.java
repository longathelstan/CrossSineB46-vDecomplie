/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="CustomClientColor", category=ModuleCategory.CLIENT, loadConfig=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/CustomClientColor;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blueValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "greenValue", "redValue", "getColor", "Ljava/awt/Color;", "alpha", "", "CrossSine"})
public final class CustomClientColor
extends Module {
    @NotNull
    public static final CustomClientColor INSTANCE = new CustomClientColor();
    @NotNull
    private static final IntegerValue redValue = new IntegerValue("Red", 255, 0, 255);
    @NotNull
    private static final IntegerValue greenValue = new IntegerValue("Green", 255, 0, 255);
    @NotNull
    private static final IntegerValue blueValue = new IntegerValue("Blue", 255, 0, 255);

    private CustomClientColor() {
    }

    @NotNull
    public final Color getColor() {
        return new Color(((Number)redValue.get()).intValue(), ((Number)greenValue.get()).intValue(), ((Number)blueValue.get()).intValue());
    }

    @NotNull
    public final Color getColor(int alpha) {
        return new Color(((Number)redValue.get()).intValue(), ((Number)greenValue.get()).intValue(), ((Number)blueValue.get()).intValue(), alpha);
    }
}

