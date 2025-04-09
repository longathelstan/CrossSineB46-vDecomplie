/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui;

import kotlin.Metadata;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/IconManager;", "", "()V", "back", "Lnet/minecraft/util/ResourceLocation;", "getBack", "()Lnet/minecraft/util/ResourceLocation;", "removeIcon", "getRemoveIcon", "search", "getSearch", "settings", "getSettings", "CrossSine"})
public final class IconManager {
    @NotNull
    public static final IconManager INSTANCE = new IconManager();
    @NotNull
    private static final ResourceLocation removeIcon = new ResourceLocation("crosssine/ui/clickgui/close.png");
    @NotNull
    private static final ResourceLocation back = new ResourceLocation("crosssine/ui/clickgui/back.png");
    @NotNull
    private static final ResourceLocation search = new ResourceLocation("crosssine/ui/clickgui/search.png");
    @NotNull
    private static final ResourceLocation settings = new ResourceLocation("crosssine/ui/misc/settings.png");

    private IconManager() {
    }

    @NotNull
    public final ResourceLocation getRemoveIcon() {
        return removeIcon;
    }

    @NotNull
    public final ResourceLocation getBack() {
        return back;
    }

    @NotNull
    public final ResourceLocation getSearch() {
        return search;
    }

    @NotNull
    public final ResourceLocation getSettings() {
        return settings;
    }
}

