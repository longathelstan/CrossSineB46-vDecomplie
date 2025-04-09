/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui;

import java.awt.Color;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/ColorManager;", "", "()V", "button", "Ljava/awt/Color;", "getButton", "()Ljava/awt/Color;", "moduleBackground", "getModuleBackground", "textBox", "getTextBox", "unusedSlider", "getUnusedSlider", "CrossSine"})
public final class ColorManager {
    @NotNull
    public static final ColorManager INSTANCE = new ColorManager();
    @NotNull
    private static final Color textBox = new Color(31, 31, 31);
    @NotNull
    private static final Color button = new Color(52, 52, 52);
    @NotNull
    private static final Color moduleBackground = new Color(35, 35, 35);
    @NotNull
    private static final Color unusedSlider = new Color(154, 154, 154);

    private ColorManager() {
    }

    @NotNull
    public final Color getTextBox() {
        return textBox;
    }

    @NotNull
    public final Color getButton() {
        return button;
    }

    @NotNull
    public final Color getModuleBackground() {
        return moduleBackground;
    }

    @NotNull
    public final Color getUnusedSlider() {
        return unusedSlider;
    }
}

