/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.font.FontLoaders;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u001e\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/utils/GameButtonUtils;", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "(Lnet/minecraft/client/gui/GuiButton;)V", "animProgress", "", "getButton", "()Lnet/minecraft/client/gui/GuiButton;", "drawButtonText", "", "mc", "Lnet/minecraft/client/Minecraft;", "render", "mouseX", "", "mouseY", "CrossSine"})
public final class GameButtonUtils {
    @NotNull
    private final GuiButton button;
    private float animProgress;

    public GameButtonUtils(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        this.button = button;
    }

    @NotNull
    public final GuiButton getButton() {
        return this.button;
    }

    public final void render(int mouseX, int mouseY, @NotNull Minecraft mc) {
        Intrinsics.checkNotNullParameter(mc, "mc");
        this.animProgress += 0.00375f * (float)RenderUtils.deltaTime * (this.button.field_146123_n && this.button.field_146124_l ? 1.0f : -1.0f);
        this.animProgress = RangesKt.coerceIn(this.animProgress, 0.0f, 1.0f);
        double percent = EaseUtils.INSTANCE.easeInOutCirc(this.animProgress);
        RenderUtils.drawRoundedCornerRect(this.button.field_146128_h, this.button.field_146129_i, (float)this.button.field_146128_h + (float)this.button.field_146120_f, (float)this.button.field_146129_i + (float)this.button.field_146121_g, 8.0f, new Color(0, 0, 0, 110 + (int)((double)80 * percent)).getRGB());
        if (this.button.field_146124_l) {
            RenderUtils.drawRoundedGradientOutlineCorner(this.button.field_146128_h, this.button.field_146129_i, (float)this.button.field_146128_h + (float)this.button.field_146120_f, (float)this.button.field_146129_i + (float)this.button.field_146121_g, 2.0f, 13.0f, ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 2, null).getRGB());
        }
    }

    public final void drawButtonText(@NotNull Minecraft mc) {
        Intrinsics.checkNotNullParameter(mc, "mc");
        FontLoaders.F16.DisplayFonts(this.button.field_146126_j, (float)this.button.field_146128_h + (float)this.button.field_146120_f / 2.0f - (float)FontLoaders.F16.DisplayFontWidths(FontLoaders.F16, this.button.field_146126_j) / 2.0f, (float)this.button.field_146129_i + (float)this.button.field_146121_g / 2.0f - (float)FontLoaders.F16.getHeight() / 2.0f, this.button.field_146124_l ? (this.button.field_146123_n ? ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, null).getRGB() : Color.WHITE.getRGB()) : Color.GRAY.getRGB(), FontLoaders.F16);
    }
}

