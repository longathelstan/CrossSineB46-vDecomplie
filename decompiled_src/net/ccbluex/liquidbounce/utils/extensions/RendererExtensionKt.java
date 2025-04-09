/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.extensions;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=2, xi=48, d1={"\u0000*\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0001\u001a2\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n\u001a*\u0010\u000b\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\r\u00a8\u0006\u000e"}, d2={"drawCenteredString", "", "Lnet/minecraft/client/gui/FontRenderer;", "s", "", "x", "", "y", "color", "shadow", "", "drawCenteredStringFade", "", "Ljava/awt/Color;", "CrossSine"})
public final class RendererExtensionKt {
    public static final int drawCenteredString(@NotNull FontRenderer $this$drawCenteredString, @NotNull String s, float x, float y, int color, boolean shadow) {
        Intrinsics.checkNotNullParameter($this$drawCenteredString, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        return $this$drawCenteredString.func_175065_a(s, x - (float)$this$drawCenteredString.func_78256_a(s) / 2.0f, y, color, shadow);
    }

    public static final int drawCenteredString(@NotNull FontRenderer $this$drawCenteredString, @NotNull String s, float x, float y, int color) {
        Intrinsics.checkNotNullParameter($this$drawCenteredString, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        return $this$drawCenteredString.func_175065_a(s, x - (float)$this$drawCenteredString.func_78256_a(s) / 2.0f, y, color, false);
    }

    public static final void drawCenteredStringFade(@NotNull FontRenderer $this$drawCenteredStringFade, @NotNull String s, float x, float y, @NotNull Color color) {
        Intrinsics.checkNotNullParameter($this$drawCenteredStringFade, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(color, "color");
        $this$drawCenteredStringFade.func_175065_a(s, x - (float)$this$drawCenteredStringFade.func_78256_a(s) / 2.0f + 0.5f, y + 0.5f, new Color(0, 0, 0, color.getAlpha()).getRGB(), false);
        $this$drawCenteredStringFade.func_175065_a(s, x - (float)$this$drawCenteredStringFade.func_78256_a(s) / 2.0f, y, color.getRGB(), false);
    }
}

