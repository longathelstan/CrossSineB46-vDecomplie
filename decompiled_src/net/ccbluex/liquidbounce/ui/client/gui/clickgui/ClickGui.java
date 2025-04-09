/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.config.ConfigManager;
import net.ccbluex.liquidbounce.font.FontLoaders;
import net.ccbluex.liquidbounce.ui.client.gui.ClickGUIModule;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.IconManager;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.SelectedConfig;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.CategoryElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.SearchElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.VideoElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.ModuleElement;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.geom.Rectangle;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.MutablePair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\f\n\u0002\b\n\u0018\u0000 u2\u00020\u0001:\u0001uB\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010Z\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b0'2\u0006\u0010[\u001a\u00020\u001b2\u0006\u0010\\\u001a\u00020\u001bH\u0002J\b\u0010]\u001a\u00020\u0006H\u0016J<\u0010^\u001a\u00020_2\u0006\u0010[\u001a\u00020\u001b2\u0006\u0010\\\u001a\u00020\u001b2\u0006\u0010`\u001a\u00020\u00042\u0006\u0010a\u001a\u00020V2\b\b\u0002\u0010b\u001a\u00020\u00042\b\b\u0002\u0010c\u001a\u00020\u0004H\u0002J \u0010d\u001a\u00020_2\u0006\u0010[\u001a\u00020\u001b2\u0006\u0010\\\u001a\u00020\u001b2\u0006\u0010`\u001a\u00020\u0004H\u0016J\b\u0010e\u001a\u00020_H\u0002J\u0018\u0010f\u001a\u00020_2\u0006\u0010[\u001a\u00020\u001b2\u0006\u0010\\\u001a\u00020\u001bH\u0002J\u0018\u0010g\u001a\u00020_2\u0006\u0010[\u001a\u00020\u001b2\u0006\u0010\\\u001a\u00020\u001bH\u0002J\u0010\u0010h\u001a\u00020_2\u0006\u0010[\u001a\u00020\u001bH\u0002J\b\u0010i\u001a\u00020_H\u0016J\u0018\u0010j\u001a\u00020_2\u0006\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020\u001bH\u0014J \u0010n\u001a\u00020_2\u0006\u0010[\u001a\u00020\u001b2\u0006\u0010\\\u001a\u00020\u001b2\u0006\u0010o\u001a\u00020\u001bH\u0014J \u0010p\u001a\u00020_2\u0006\u0010[\u001a\u00020\u001b2\u0006\u0010\\\u001a\u00020\u001b2\u0006\u0010q\u001a\u00020\u001bH\u0014J\b\u0010r\u001a\u00020_H\u0016J\b\u0010s\u001a\u00020_H\u0002J\b\u0010t\u001a\u00020_H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\b\"\u0004\b\u0019\u0010\nR\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u001f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010#\u001a\n\u0012\u0004\u0012\u00020%\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b0'X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u00100\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b1\u0010\u0012R\u000e\u00102\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00107\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u0012\"\u0004\b9\u0010:R\u0014\u0010;\u001a\u00020\u001f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b<\u0010!R\u000e\u0010=\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010@\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010\b\"\u0004\bB\u0010\nR\u000e\u0010C\u001a\u00020DX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020DX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010H\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\bI\u0010\u0012R\u0011\u0010J\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\bK\u0010\u0012R\u000e\u0010L\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010M\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u0012\"\u0004\bO\u0010:R\u000e\u0010P\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010Q\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u0010\u0012\"\u0004\bS\u0010:R\u000e\u0010T\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020VX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010Y\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006v"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/ClickGui;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "animProgress", "", "cant", "", "getCant", "()Z", "setCant", "(Z)V", "categoriesBottommargin", "categoriesTopMargin", "categoryElements", "", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/CategoryElement;", "categoryXOffset", "getCategoryXOffset", "()F", "closed", "elementHeight", "elementsStartY", "endYAnim", "keyBinding", "getKeyBinding", "setKeyBinding", "lastScrollOffset", "", "minWindowHeight", "minWindowWidth", "moveAera", "Lnet/ccbluex/liquidbounce/utils/geom/Rectangle;", "getMoveAera", "()Lnet/ccbluex/liquidbounce/utils/geom/Rectangle;", "moveDragging", "onlineConfigList", "", "Lnet/ccbluex/liquidbounce/file/config/ConfigManager$OnlineConfig;", "quad", "Lkotlin/Pair;", "resizeArea", "resizeDragging", "rotationClickAnim", "scrollOffsetDisplay", "scrollOffsetRaw", "searchElement", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/SearchElement;", "searchHeight", "searchWidth", "getSearchWidth", "searchXOffset", "searchYOffset", "selectedConfig", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/SelectedConfig;", "showOnlineConfigs", "sideWidth", "getSideWidth", "setSideWidth", "(F)V", "splitArea", "getSplitArea", "splitDragging", "startYAnim", "stringWidth", "textEditing", "getTextEditing", "setTextEditing", "video1", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/VideoElement;", "video2", "warning", "warningAnim", "windowHeight", "getWindowHeight", "windowWidth", "getWindowWidth", "windowXEnd", "windowXStart", "getWindowXStart", "setWindowXStart", "windowYEnd", "windowYStart", "getWindowYStart", "setWindowYStart", "x2", "xButtonColor", "Ljava/awt/Color;", "xHoldOffset", "y2", "yHoldOffset", "determineQuadrant", "mouseX", "mouseY", "doesGuiPauseGame", "drawFullSized", "", "partialTicks", "accentColor", "xOffset", "yOffset", "drawScreen", "handleMisc", "handleMove", "handleResize", "handleSplit", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "mouseReleased", "state", "onGuiClosed", "reload", "resetPositions", "Companion", "CrossSine"})
public final class ClickGui
extends GuiScreen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<CategoryElement> categoryElements = new ArrayList();
    private float startYAnim = (float)this.field_146295_m / 2.0f;
    private float endYAnim = (float)this.field_146295_m / 2.0f;
    private float rotationClickAnim;
    @Nullable
    private SearchElement searchElement;
    private float stringWidth;
    private float windowXStart = 30.0f;
    private float windowYStart = 30.0f;
    private float windowXEnd = 500.0f;
    private float windowYEnd = 400.0f;
    private final float minWindowWidth;
    private final float minWindowHeight;
    private boolean warning;
    private float warningAnim;
    private final float searchXOffset;
    private final float searchYOffset;
    @Nullable
    private List<ConfigManager.OnlineConfig> onlineConfigList;
    private int scrollOffsetRaw;
    private int lastScrollOffset;
    private float scrollOffsetDisplay;
    @NotNull
    private SelectedConfig selectedConfig = new SelectedConfig(CrossSine.INSTANCE.getConfigManager().getNowConfig(), false);
    private boolean showOnlineConfigs;
    private float sideWidth = 150.0f;
    private final float searchHeight;
    private final float elementHeight;
    private final float elementsStartY;
    private final float categoriesTopMargin;
    private final float categoriesBottommargin;
    @NotNull
    private final Color xButtonColor = new Color(0.2f, 0.0f, 0.0f, 1.0f);
    private boolean moveDragging;
    private boolean resizeDragging;
    private boolean splitDragging;
    @NotNull
    private Pair<Integer, Integer> quad = new Pair<Integer, Integer>(0, 0);
    private final float resizeArea;
    private float x2;
    private float y2;
    private float xHoldOffset;
    private float yHoldOffset;
    private boolean closed;
    private boolean cant;
    private boolean textEditing;
    private boolean keyBinding;
    private float animProgress;
    @NotNull
    private final VideoElement video1 = new VideoElement();
    @NotNull
    private final VideoElement video2 = new VideoElement();
    @Nullable
    private static ClickGui instance;

    public ClickGui() {
        this.minWindowWidth = 475.0f;
        this.minWindowHeight = 350.0f;
        this.searchXOffset = 10.0f;
        this.searchYOffset = 30.0f;
        this.searchHeight = 20.0f;
        this.elementHeight = 24.0f;
        this.elementsStartY = 55.0f;
        this.categoriesTopMargin = 20.0f;
        this.categoriesBottommargin = 20.0f;
        this.resizeArea = 12.0f;
        ModuleCategory[] $this$forEach$iv = ModuleCategory.values();
        boolean $i$f$forEach = false;
        for (ModuleCategory element$iv : $this$forEach$iv) {
            ModuleCategory it = element$iv;
            boolean bl = false;
            this.categoryElements.add(new CategoryElement(it));
        }
        this.searchElement = new SearchElement(this.windowXStart + this.searchXOffset, this.windowYStart + this.searchYOffset, this.getSearchWidth(), this.searchHeight);
        this.categoryElements.get(0).setFocused(true);
    }

    public final float getWindowXStart() {
        return this.windowXStart;
    }

    public final void setWindowXStart(float f) {
        this.windowXStart = f;
    }

    public final float getWindowYStart() {
        return this.windowYStart;
    }

    public final void setWindowYStart(float f) {
        this.windowYStart = f;
    }

    public final float getWindowWidth() {
        return Math.abs(this.windowXEnd - this.windowXStart);
    }

    public final float getWindowHeight() {
        return Math.abs(this.windowYEnd - this.windowYStart);
    }

    public final float getSideWidth() {
        return this.sideWidth;
    }

    public final void setSideWidth(float f) {
        this.sideWidth = f;
    }

    private final float getCategoryXOffset() {
        return this.sideWidth;
    }

    private final float getSearchWidth() {
        return this.sideWidth - 10.0f;
    }

    private final Rectangle getMoveAera() {
        return new Rectangle(this.windowXStart, this.windowYStart, this.getWindowWidth() - 20.0f, 20.0f);
    }

    private final Rectangle getSplitArea() {
        return new Rectangle(this.windowXStart + this.sideWidth - (float)5, this.windowYStart, 15.0f, this.getWindowHeight());
    }

    public final boolean getCant() {
        return this.cant;
    }

    public final void setCant(boolean bl) {
        this.cant = bl;
    }

    public final boolean getTextEditing() {
        return this.textEditing;
    }

    public final void setTextEditing(boolean bl) {
        this.textEditing = bl;
    }

    public final boolean getKeyBinding() {
        return this.keyBinding;
    }

    public final void setKeyBinding(boolean bl) {
        this.keyBinding = bl;
    }

    private final void reload() {
        this.categoryElements.clear();
        ModuleCategory[] $this$forEach$iv = ModuleCategory.values();
        boolean $i$f$forEach = false;
        for (ModuleCategory element$iv : $this$forEach$iv) {
            ModuleCategory it = element$iv;
            boolean bl = false;
            this.categoryElements.add(new CategoryElement(it));
        }
        this.categoryElements.get(0).setFocused(true);
    }

    private final Pair<Integer, Integer> determineQuadrant(int mouseX, int mouseY) {
        MutablePair result = new MutablePair((Object)0, (Object)0);
        float offset2 = 0.0f;
        float f = this.windowXStart - this.resizeArea;
        float f2 = this.windowXStart - offset2;
        float f3 = mouseX;
        boolean bl = f <= f3 ? f3 <= f2 : false;
        if (bl) {
            result.left = -1;
            this.xHoldOffset = (float)mouseX - this.windowXStart;
        }
        f = this.windowXEnd + offset2;
        f2 = this.windowXEnd + this.resizeArea;
        f3 = mouseX;
        boolean bl2 = f <= f3 ? f3 <= f2 : false;
        if (bl2) {
            result.left = 1;
            this.xHoldOffset = (float)mouseX - this.windowXEnd;
        }
        f = this.windowYStart - this.resizeArea;
        f2 = this.windowYStart - offset2;
        f3 = mouseY;
        boolean bl3 = f <= f3 ? f3 <= f2 : false;
        if (bl3) {
            result.right = 1;
            this.yHoldOffset = (float)mouseY - this.windowYStart;
        }
        f = this.windowYEnd + offset2;
        f2 = this.windowYEnd + this.resizeArea;
        f3 = mouseY;
        boolean bl4 = f <= f3 ? f3 <= f2 : false;
        if (bl4) {
            result.right = -1;
            this.yHoldOffset = (float)mouseY - this.windowYEnd;
        }
        Map.Entry entry = (Map.Entry)result;
        return new Pair<Integer, Integer>((Integer)entry.getKey(), (Integer)entry.getValue());
    }

    private final void handleMove(int mouseX, int mouseY) {
        if (this.moveDragging) {
            float w = this.getWindowWidth();
            float h = this.getWindowHeight();
            this.windowXStart = (float)mouseX + this.x2;
            this.windowYStart = (float)mouseY + this.y2;
            this.windowXEnd = this.windowXStart + w;
            this.windowYEnd = this.windowYStart + h;
        }
    }

    private final void handleResize(int mouseX, int mouseY) {
        float mouseX2 = (float)mouseX - this.xHoldOffset;
        float mouseY2 = (float)mouseY - this.yHoldOffset;
        if (this.resizeDragging) {
            Color triangleColor = new Color(255, 255, 255);
            Pair<Integer, Integer> pair = TuplesKt.to(this.quad.getFirst(), this.quad.getSecond());
            if (Intrinsics.areEqual(pair, TuplesKt.to(1, 1))) {
                this.windowXEnd = RangesKt.coerceAtLeast(mouseX2, this.windowXStart + this.minWindowWidth);
                this.windowYStart = RangesKt.coerceAtMost(mouseY2, this.windowYEnd - this.minWindowHeight);
                RenderUtils.drawSquareTriangle(this.windowXEnd + this.resizeArea, this.windowYStart - this.resizeArea, -this.resizeArea, this.resizeArea, triangleColor, true);
            } else if (Intrinsics.areEqual(pair, TuplesKt.to(-1, -1))) {
                this.windowXStart = RangesKt.coerceAtMost(mouseX2, this.windowXEnd - this.minWindowWidth);
                this.windowYEnd = RangesKt.coerceAtLeast(mouseY2, this.windowYStart + this.minWindowHeight);
                RenderUtils.drawSquareTriangle(this.windowXStart - this.resizeArea, this.windowYEnd + this.resizeArea, this.resizeArea, -this.resizeArea, triangleColor, true);
            } else if (Intrinsics.areEqual(pair, TuplesKt.to(-1, 1))) {
                this.windowXStart = RangesKt.coerceAtMost(mouseX2, this.windowXEnd - this.minWindowWidth);
                this.windowYStart = RangesKt.coerceAtMost(mouseY2, this.windowYEnd - this.minWindowHeight);
                RenderUtils.drawSquareTriangle(this.windowXStart - this.resizeArea, this.windowYStart - this.resizeArea, this.resizeArea, this.resizeArea, triangleColor, true);
            } else if (Intrinsics.areEqual(pair, TuplesKt.to(1, -1))) {
                this.windowXEnd = RangesKt.coerceAtLeast(mouseX2, this.windowXStart + this.minWindowWidth);
                this.windowYEnd = RangesKt.coerceAtLeast(mouseY2, this.windowYStart + this.minWindowHeight);
                RenderUtils.drawSquareTriangle(this.windowXEnd + this.resizeArea, this.windowYEnd + this.resizeArea, -this.resizeArea, -this.resizeArea, triangleColor, true);
            }
        }
    }

    private final void resetPositions() {
        this.windowXStart = 30.0f;
        this.windowYStart = 20.0f;
        this.windowXEnd = (float)this.field_146294_l - 30.0f;
        this.windowYEnd = (float)this.field_146295_m - 20.0f;
        this.resizeDragging = false;
        this.moveDragging = false;
    }

    private final void handleSplit(int mouseX) {
        if (this.splitDragging) {
            this.sideWidth = RangesKt.coerceIn((float)mouseX - this.windowXStart, (Fonts.font32.func_78256_a(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_())) > Fonts.font40.func_78256_a("CrossSine B46") ? (float)Fonts.font32.func_78256_a(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_())) + 45.0f : (float)Fonts.font40.func_78256_a("CrossSine B46") + 50.0f) + 15.0f, this.getWindowWidth() / (float)2);
        }
    }

    private final void handleMisc() {
        if (Keyboard.isKeyDown((int)88)) {
            this.resetPositions();
        }
        if (Keyboard.isKeyDown((int)63)) {
            this.reload();
        }
    }

    /*
     * WARNING - void declaration
     */
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        Iterable $this$forEach$iv = this.categoryElements;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            void $this$forEach$iv2;
            void $this$filterTo$iv$iv;
            CategoryElement cat = (CategoryElement)element$iv;
            boolean bl = false;
            Iterable $this$filter$iv = cat.getModuleElements();
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                ModuleElement it = (ModuleElement)element$iv$iv;
                boolean bl2 = false;
                if (!it.listeningKeybind()) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                ModuleElement mod = (ModuleElement)element$iv2;
                boolean bl3 = false;
                mod.resetState();
            }
        }
        super.func_73866_w_();
    }

    /*
     * WARNING - void declaration
     */
    public void func_146281_b() {
        void $this$mapTo$iv$iv;
        CategoryElement it;
        Iterable $this$filterTo$iv$iv;
        Iterable $this$filter$iv = this.categoryElements;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (CategoryElement)element$iv$iv;
            boolean bl = false;
            if (!it.getFocused()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (CategoryElement)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            it.handleMouseRelease(-1, -1, 0, 0.0f, 0.0f, 0.0f, 0.0f);
            collection.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv;
        this.moveDragging = false;
        this.resizeDragging = false;
        this.splitDragging = false;
        this.closed = false;
        this.animProgress = 0.0f;
        Keyboard.enableRepeatEvents((boolean)false);
        CrossSine.INSTANCE.getFileManager().saveConfigs(new FileConfig[0]);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.animProgress += 0.001875f * (float)RenderUtils.deltaTime * (this.closed ? -1.0f : 1.0f);
        this.animProgress = RangesKt.coerceIn(this.animProgress, 0.0f, 1.0f);
        this.warningAnim += 0.001875f * (float)RenderUtils.deltaTime * (this.warning ? 1.0f : -1.0f);
        this.warningAnim = RangesKt.coerceIn(this.warningAnim, 0.0f, 1.0f);
        if (this.closed && this.animProgress == 0.0f) {
            this.field_146297_k.func_147108_a(null);
        }
        if (this.warningAnim == 0.0f) {
            this.warning = true;
        }
        if (this.warningAnim == 1.0f) {
            this.warning = false;
        }
        float percent = (float)EaseUtils.easeOutBack(this.animProgress);
        this.field_146297_k.field_71417_B.func_74374_c();
        if (this.moveDragging) {
            this.field_146297_k.field_71417_B.field_74377_a = RangesKt.coerceIn(this.field_146297_k.field_71417_B.field_74377_a, -30, 30);
            this.rotationClickAnim = AnimationUtils.animate(60.0f * ((float)this.field_146297_k.field_71417_B.field_74377_a / 30.0f), this.rotationClickAnim, 0.005f * (float)RenderUtils.deltaTime);
        } else {
            this.rotationClickAnim = AnimationUtils.animate(0.0f, this.rotationClickAnim, 0.005f * (float)RenderUtils.deltaTime);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.windowXStart + this.getWindowWidth() / (float)2), (float)(this.windowYStart + this.getWindowHeight() / (float)2), (float)0.0f);
        GlStateManager.func_179114_b((float)this.rotationClickAnim, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.windowXStart + this.getWindowWidth() / (float)2)), (float)(-(this.windowYStart + this.getWindowHeight() / (float)2)), (float)0.0f);
        if (!((Boolean)ClickGUIModule.fastRenderValue.get()).booleanValue()) {
            GL11.glScalef((float)percent, (float)percent, (float)percent);
            GL11.glTranslatef((float)(this.windowXEnd * 0.5f * (1.0f - percent) / percent), (float)(this.windowYEnd * 0.5f * (1.0f - percent) / percent), (float)0.0f);
        }
        for (CategoryElement ce : this.categoryElements) {
            SearchElement searchElement = this.searchElement;
            Intrinsics.checkNotNull(searchElement);
            String string = searchElement.getSearchBox().func_146179_b();
            Intrinsics.checkNotNullExpressionValue(string, "searchElement!!.searchBox.text");
            if (!(((CharSequence)string).length() == 0) || !Intrinsics.areEqual(ce.getName(), "CONFIG") || !ce.getFocused()) continue;
            this.lastScrollOffset = Mouse.getDWheel();
        }
        this.handleMisc();
        this.handleMove(mouseX, mouseY);
        this.handleResize(mouseX, mouseY);
        this.handleSplit(mouseX);
        ClickGui.drawFullSized$default(this, mouseX, mouseY, partialTicks, ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, null), 0.0f, 0.0f, 48, null);
        if (this.warningAnim > 0.0f && (this.windowXEnd > (float)this.field_146294_l || this.windowYEnd > (float)this.field_146295_m)) {
            FontLoaders.F24.drawCenteredString("Press F12 to reset scale", (double)this.field_146294_l / 2.0, 10.0, new Color(255, 0, 0, (int)((float)255 * this.warningAnim)).getRGB());
        }
        GL11.glPopMatrix();
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final void drawFullSized(int mouseX, int mouseY, float partialTicks, Color accentColor, float xOffset, float yOffset) {
        windowRadius = 9.0f;
        RenderUtils.drawBloomGradientRoundedRect(this.windowXStart + xOffset, this.windowYStart + yOffset, this.windowXEnd + xOffset, this.windowYEnd + yOffset, windowRadius, 7.0f, new Color(10, 10, 10), new Color(30, 30, 30), RenderUtils.ShaderBloom.BOTH);
        var8_8 = this.windowXEnd - 20.0f;
        var9_9 = this.windowXEnd;
        var10_11 = mouseX;
        v0 = var8_8 <= var10_11 ? var10_11 <= var9_9 : false;
        if (v0) {
            var8_8 = this.windowYStart;
            var9_9 = this.windowYStart + 20.0f;
            var10_11 = mouseY;
            v1 = var8_8 <= var10_11 ? var10_11 <= var9_9 : false;
            if (v1) {
                RenderUtils.drawBloomRoundedRect(this.windowXEnd + xOffset - 20.0f, this.windowYStart + yOffset, this.windowXEnd + xOffset, this.windowYStart + yOffset + 20.0f, windowRadius, 3.0f, this.xButtonColor, RenderUtils.ShaderBloom.BLOOMONLY);
            }
        }
        GlStateManager.func_179118_c();
        RenderUtils.drawImage(IconManager.INSTANCE.getRemoveIcon(), (int)(this.windowXEnd + xOffset) - 15, (int)(this.windowYStart + yOffset) + 5, 10, 10);
        GlStateManager.func_179141_d();
        v2 = this.searchElement;
        Intrinsics.checkNotNull(v2);
        v2.setXPos(this.windowXStart + xOffset + this.searchXOffset);
        v3 = this.searchElement;
        Intrinsics.checkNotNull(v3);
        v3.setYPos(this.windowYStart + yOffset + this.searchYOffset);
        v4 = this.searchElement;
        Intrinsics.checkNotNull(v4);
        v4.setWidth(this.getSearchWidth());
        Intrinsics.checkNotNull(this.searchElement);
        this.searchElement.getSearchBox().field_146218_h = (int)this.getSearchWidth() - 4;
        Intrinsics.checkNotNull(this.searchElement);
        this.searchElement.getSearchBox().field_146209_f = (int)(this.windowXStart + xOffset + this.searchXOffset + (float)2);
        Intrinsics.checkNotNull(this.searchElement);
        this.searchElement.getSearchBox().field_146210_g = (int)(this.windowYStart + yOffset + this.searchYOffset + (float)2);
        v5 = this.searchElement;
        Intrinsics.checkNotNull(v5);
        if (v5.drawBox(mouseX, mouseY, accentColor)) {
            v6 = this.searchElement;
            Intrinsics.checkNotNull(v6);
            v6.drawPanel(mouseX, mouseY, this.windowXStart + xOffset + this.getCategoryXOffset(), this.windowYStart + yOffset + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, Mouse.getDWheel(), this.categoryElements, accentColor);
            return;
        }
        RenderUtils.drawBloomRoundedRect(this.windowXStart + xOffset + 4.0f + 8.0f, this.startYAnim, this.windowXStart + xOffset + 12.0f + 8.0f + this.stringWidth, this.endYAnim, 3.0f, 2.5f, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 190, false, 4, null), RenderUtils.ShaderBloom.BOTH);
        startY = this.windowYStart + yOffset + this.elementsStartY;
        for (CategoryElement ce : this.categoryElements) {
            block22: {
                block21: {
                    ce.drawLabel(mouseX, mouseY, this.windowXStart + xOffset, startY, this.getCategoryXOffset(), this.elementHeight);
                    if (ce.getFocused()) {
                        v7 = (Boolean)ClickGUIModule.fastRenderValue.get() != false ? startY + 6.0f : (this.startYAnim = AnimationUtils.animate(startY + 6.0f, this.startYAnim, (this.startYAnim - (startY + 5.0f) > 0.0f ? 0.65f : 0.55f) * (float)RenderUtils.deltaTime * 0.025f));
                        this.endYAnim = (Boolean)ClickGUIModule.fastRenderValue.get() != false ? startY + this.elementHeight - 6.0f : AnimationUtils.animate(startY + this.elementHeight - 6.0f, this.endYAnim, (this.endYAnim - (startY + this.elementHeight - 5.0f) < 0.0f ? 0.65f : 0.55f) * (float)RenderUtils.deltaTime * 0.025f);
                        ce.drawPanel(mouseX, mouseY, this.windowXStart + xOffset + this.getCategoryXOffset(), this.windowYStart + yOffset + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, Mouse.getDWheel(), accentColor);
                    }
                    startY += this.elementHeight;
                    if (Intrinsics.areEqual(ce.getName(), "INFO") && ce.getFocused()) {
                        RenderUtils.drawRect(this.windowXStart + this.sideWidth + 5.0f, this.windowYStart + 100.0f, this.windowXEnd, this.windowYStart + 102.0f, new Color(50, 50, 50).getRGB());
                        GlStateManager.func_179094_E();
                        Fonts.font50SemiBold.drawString("CrossSine B46", this.windowXStart + this.sideWidth + 15.0f, this.windowYStart + 20.0f, new Color(100, 100, 100).getRGB());
                        Fonts.font30SemiBold.drawString("Made with <3 by shxp3", this.windowXStart + this.sideWidth + 15.0f, this.windowYStart + 35.0f, new Color(100, 100, 100).getRGB());
                        RenderUtils.drawImage(new ResourceLocation("crosssine/ui/icons/discord.png"), (int)(this.windowXStart + this.sideWidth + 15.0f), (int)(this.windowYStart + 45.0f), 28, 28);
                        Fonts.font35SemiBold.drawString("Discord", this.windowXStart + this.sideWidth + 45.0f, this.windowYStart + 55.0f, new Color(50, 50, 50).getRGB());
                        RenderUtils.drawImage(new ResourceLocation("crosssine/ui/icons/github.png"), (int)(this.windowXStart + this.sideWidth + 15.0f), (int)(this.windowYStart + 70.0f), 28, 28);
                        Fonts.font35SemiBold.drawString("Github", this.windowXStart + this.sideWidth + 45.0f, this.windowYStart + 82.0f, new Color(50, 50, 50).getRGB());
                        this.video1.draw("BmCqBr_-qtI", mouseX, mouseY, this.windowXStart + this.sideWidth + 28.0f, this.windowYStart + 123.0f, 150, 84);
                        this.video2.draw("QKXBks1Tgh4", mouseX, mouseY, this.windowXStart + this.sideWidth + 28.0f, this.windowYStart + 123.0f + 98.0f, 150, 84);
                        GlStateManager.func_179121_F();
                    }
                    if (!Intrinsics.areEqual(ce.getName(), "CONFIG") || !ce.getFocused()) continue;
                    baseX = this.windowXStart + this.sideWidth + 15.0f;
                    baseY = this.windowYStart + 50.0f;
                    configDir = CrossSine.INSTANCE.getFileManager().getConfigsDir();
                    if (configDir.listFiles() != null) break block21;
                    v8 = null;
                    break block22;
                }
                $i$f$mapNotNull = false;
                var19_25 = $this$mapNotNull$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$mapNotNullTo = false;
                $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                $i$f$forEach = false;
                var24_44 = $this$forEach$iv$iv$iv;
                var25_46 = 0;
                var26_49 = ((void)var24_44).length;
                while (var25_46 < var26_49) {
                    element$iv$iv$iv = var24_44[var25_46];
                    ++var25_46;
                    element$iv$iv = element$iv$iv$iv;
                    $i$a$-forEach-ArraysKt___ArraysKt$mapNotNullTo$1$iv$iv = false;
                    it = element$iv$iv;
                    $i$a$-mapNotNull-ClickGui$drawFullSized$configList$1 = false;
                    if (!it.isFile()) ** GOTO lbl-1000
                    var32_64 = it.getName();
                    Intrinsics.checkNotNullExpressionValue(var32_64, "it.name");
                    if (StringsKt.endsWith$default(var32_64, ".json", false, 2, null)) {
                        var32_64 = it.getName();
                        Intrinsics.checkNotNullExpressionValue(var32_64, "it.name");
                        v9 = StringsKt.removeSuffix(var32_64, (CharSequence)".json");
                    } else lbl-1000:
                    // 2 sources

                    {
                        v9 = null;
                    }
                    if (v9 == null) continue;
                    it$iv$iv = v9;
                    $i$a$-let-ArraysKt___ArraysKt$mapNotNullTo$1$1$iv$iv = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                v8 = (List)destination$iv$iv;
            }
            var15_18 = v8;
            v10 /* !! */  = configList = var15_18 == null ? CollectionsKt.emptyList() : var15_18;
            if (this.showOnlineConfigs) {
                v11 = this.onlineConfigList;
                if (v11 == null) {
                    v12 = null;
                } else {
                    $this$map$iv = v11;
                    $i$f$map = false;
                    destination$iv$iv = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        var25_47 = (ConfigManager.OnlineConfig)item$iv$iv;
                        var37_71 = destination$iv$iv;
                        $i$a$-map-ClickGui$drawFullSized$listToDisplay$1 = false;
                        var37_71.add(it.getName());
                    }
                    v12 = (List)destination$iv$iv;
                }
                v13 = v12;
                if (v12 == null) {
                    v13 = CollectionsKt.emptyList();
                }
            } else {
                v13 = configList;
            }
            listToDisplay = v13;
            visibleCount = 5;
            entryHeight = 20.0f;
            maxScroll = Math.max(0, listToDisplay.size() - visibleCount);
            if (this.lastScrollOffset != 0) {
                this.scrollOffsetRaw -= MathKt.getSign(this.lastScrollOffset);
                this.scrollOffsetRaw = RangesKt.coerceIn(this.scrollOffsetRaw, 0, maxScroll);
            }
            this.scrollOffsetDisplay += ((float)this.scrollOffsetRaw - this.scrollOffsetDisplay) * 0.2f;
            scrollOffset = MathKt.roundToInt(this.scrollOffsetDisplay);
            RenderUtils.drawBloomRoundedRect(baseX, baseY - 23.0f, baseX + 200.0f, baseY + 20.0f, 5.0f, 1.5f, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
            GlStateManager.func_179117_G();
            Fonts.font40SemiBold.drawString(Intrinsics.stringPlus("Now Config: ", CrossSine.INSTANCE.getConfigManager().getNowConfig()), baseX + 5.0f, baseY - 14.0f, Color.WHITE.getRGB());
            Fonts.font40SemiBold.drawString("Config: " + this.selectedConfig.getName() + ' ' + (this.selectedConfig.isOnline() != false ? "(Online)" : ""), baseX + 5.0f, baseY + 6.0f, Color.WHITE.getRGB());
            $this$forEachIndexed$iv = CollectionsKt.take((Iterable)CollectionsKt.drop((Iterable)listToDisplay, scrollOffset), visibleCount);
            $i$f$forEachIndexed = false;
            index$iv = 0;
            for (T item$iv : $this$forEachIndexed$iv) {
                it = index$iv;
                index$iv = it + 1;
                if (it < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                $i$a$-map-ClickGui$drawFullSized$listToDisplay$1 = (String)item$iv;
                i = it;
                $i$a$-forEachIndexed-ClickGui$drawFullSized$1 = false;
                entryY = baseY + 25.0f + (float)i * entryHeight;
                RenderUtils.drawRect(baseX, entryY, baseX + 200.0f, entryY + entryHeight, new Color(30, 30, 30, 180).getRGB());
                if (Intrinsics.areEqual(this.selectedConfig, new SelectedConfig((String)name, this.showOnlineConfigs))) {
                    RenderUtils.drawRoundedOutline(baseX, entryY, baseX + 200.0f, entryY + entryHeight, 3.0f, 3.0f, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 3, null).getRGB());
                }
                Fonts.font40SemiBold.drawString((String)name, baseX + 5.0f, entryY + 6.0f, Color.LIGHT_GRAY.getRGB());
                if (!MouseUtils.mouseWithinBounds(mouseX, mouseY, baseX, entryY, baseX + 200.0f, entryY + entryHeight) || !Mouse.isButtonDown((int)0)) continue;
                this.selectedConfig = new SelectedConfig((String)name, this.showOnlineConfigs);
            }
            if (configList.size() > visibleCount) {
                barHeight = RangesKt.coerceAtLeast(entryHeight * (float)visibleCount * (float)visibleCount / (float)configList.size(), 10.0f);
                barY = baseY + 25.0f + entryHeight * (float)scrollOffset * (float)visibleCount / (float)configList.size();
                barX = baseX + 200.0f - 4.0f;
                RenderUtils.drawRoundedRect(barX + 5.0f, barY, barX + 7.0f, barY + barHeight, 1.0f, new Color(150, 150, 150, 180).getRGB());
            }
            saveX1 = baseX;
            saveX2 = baseX + 95.0f;
            saveY1 = baseY + 140.0f;
            saveY2 = saveY1 + 20.0f;
            RenderUtils.drawBloomRoundedRect(saveX1, saveY1, saveX2, saveY2, 6.0f, 1.5f, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
            Fonts.font40SemiBold.drawCenteredString("Folder", (saveX1 + saveX2) / (float)2, saveY1 + 6.0f, Color.WHITE.getRGB());
            loadX1 = baseX + 105.0f;
            loadX2 = loadX1 + 95.0f;
            loadY1 = baseY + 140.0f;
            loadY2 = loadY1 + 20.0f;
            RenderUtils.drawBloomRoundedRect(loadX1, loadY1, loadX2, loadY2, 6.0f, 1.5f, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
            Fonts.font40SemiBold.drawCenteredString("Load", (loadX1 + loadX2) / (float)2, loadY1 + 6.0f, Color.WHITE.getRGB());
            toggleX1 = baseX + 105.0f;
            toggleX2 = toggleX1 + 95.0f;
            toggleY1 = loadY2 + 5.0f;
            toggleY2 = toggleY1 + 20.0f;
            RenderUtils.drawBloomRoundedRect(toggleX1, toggleY1, toggleX2, toggleY2, 6.0f, 1.5f, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
            toggleLabel = this.showOnlineConfigs != false ? "Show Local" : "Show Online";
            Fonts.font40SemiBold.drawCenteredString(toggleLabel, (toggleX1 + toggleX2) / (float)2, toggleY1 + 6.0f, Color.WHITE.getRGB());
            deleteX1 = saveX1;
            deleteX2 = saveX2;
            deleteY1 = saveY2 + 5.0f;
            deleteY2 = deleteY1 + 20.0f;
            RenderUtils.drawBloomRoundedRect(deleteX1, deleteY1, deleteX2, deleteY2, 6.0f, 1.5f, new Color(50, 50, 50, 180), RenderUtils.ShaderBloom.BOTH);
            Fonts.font40SemiBold.drawCenteredString("Delete", (deleteX1 + deleteX2) / (float)2, deleteY1 + 6.0f, Color.WHITE.getRGB());
            if (this.onlineConfigList != null) continue;
            new Thread((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, drawFullSized$lambda-10(net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui ), ()V)((ClickGui)this)).start();
        }
        for (CategoryElement ce : this.categoryElements) {
            if (!ce.getFocused()) continue;
            this.stringWidth = (Boolean)ClickGUIModule.fastRenderValue.get() != false ? (float)Fonts.font40SemiBold.func_78256_a(ce.getName()) : AnimationUtils.animate(Fonts.font40SemiBold.func_78256_a(ce.getName()), this.stringWidth, 0.55f * (float)RenderUtils.deltaTime * 0.025f);
        }
        RenderUtils.drawRect(this.windowXStart + this.sideWidth + 5.0f, this.windowYStart, this.windowXStart + this.sideWidth + 7.0f, this.windowYEnd, new Color(50, 50, 50).getRGB());
        RenderUtils.drawRoundedOutline(this.windowXStart, this.windowYStart, this.windowXEnd, this.windowYEnd, 13.0f, 4.0f, new Color(50, 50, 50).getRGB());
        GlStateManager.func_179117_G();
        GL11.glPushMatrix();
        Stencil.write(false);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.fastRoundedRect(this.windowXStart + 10.0f, this.windowYEnd - 10.0f, this.windowXStart + 5.0f + 45.0f, this.windowYEnd - 5.0f - 45.0f, 10.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        Stencil.erase(true);
        var9_10 = this.field_146297_k.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(var9_10, "mc.thePlayer");
        RenderUtils.drawHead(EntityExtensionKt.getSkin((EntityLivingBase)var9_10), (int)(this.windowXStart + (float)10), (int)(this.windowYEnd - (float)50), 40, 40, Color.WHITE.getRGB());
        GlStateManager.func_179117_G();
        Stencil.dispose();
        GL11.glPopMatrix();
        decimalFormat3 = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
        Fonts.font32.drawString(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_()), this.windowXStart + 55.0f, this.windowYEnd - 20.0f, Color.white.getRGB());
        Fonts.font32.drawString(Intrinsics.stringPlus("Health : ", decimalFormat3.format(Float.valueOf(this.field_146297_k.field_71439_g.func_110143_aJ()))), this.windowXStart + 55.0f, this.windowYEnd - 20.0f - (float)Fonts.font32.getHeight(), Color.white.getRGB());
        Fonts.font40.drawString("CrossSine B46", this.windowXStart + 55.0f, this.windowYEnd - 20.0f - (float)(Fonts.font40.getHeight() * 2), Color.white.getRGB());
        RenderUtils.drawRoundedOutline(this.windowXStart + 5.0f, this.windowYEnd - 55.0f, this.windowXStart + 15.0f + (Fonts.font32.func_78256_a(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_())) > Fonts.font40.func_78256_a("CrossSine B46") ? (float)Fonts.font32.func_78256_a(Intrinsics.stringPlus("Name : ", this.field_146297_k.field_71439_g.func_70005_c_())) + 45.0f : (float)Fonts.font40.func_78256_a("CrossSine B46") + 50.0f), this.windowYEnd - 5.0f, 15.0f, 4.0f, new Color(50, 50, 50).getRGB());
    }

    static /* synthetic */ void drawFullSized$default(ClickGui clickGui, int n, int n2, float f, Color color, float f2, float f3, int n3, Object object) {
        if ((n3 & 0x10) != 0) {
            f2 = 0.0f;
        }
        if ((n3 & 0x20) != 0) {
            f3 = 0.0f;
        }
        clickGui.drawFullSized(n, n2, f, color, f2, f3);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        SearchElement searchElement = this.searchElement;
        Intrinsics.checkNotNull(searchElement);
        if (searchElement.isTyping() && new Rectangle(this.windowXStart, this.windowYStart, 60.0f, 24.0f).contains(mouseX, mouseY)) {
            SearchElement searchElement2 = this.searchElement;
            Intrinsics.checkNotNull(searchElement2);
            searchElement2.getSearchBox().func_146180_a("");
            return;
        }
        if (this.getMoveAera().contains(mouseX, mouseY) && !this.moveDragging) {
            this.moveDragging = true;
            this.x2 = this.windowXStart - (float)mouseX;
            this.y2 = this.windowYStart - (float)mouseY;
            return;
        }
        if (new Rectangle(this.windowXEnd - (float)20, this.windowYStart, 20.0f, 20.0f).contains(mouseX, mouseY)) {
            this.field_146297_k.func_147108_a(null);
            return;
        }
        if (this.getSplitArea().contains(mouseX, mouseY)) {
            this.splitDragging = true;
            return;
        }
        Pair<Integer, Integer> quad2 = this.determineQuadrant(mouseX, mouseY);
        if (((Number)quad2.getFirst()).intValue() != 0 && ((Number)quad2.getSecond()).intValue() != 0) {
            this.quad = quad2;
            this.resizeDragging = true;
            return;
        }
        for (CategoryElement ce : this.categoryElements) {
            float toggleY2;
            float toggleX2;
            float toggleY1;
            float toggleX1;
            File file;
            float deleteY2;
            float deleteX2;
            float deleteY1;
            float deleteX1;
            float loadY2;
            float loadX2;
            float loadY1;
            float loadX1;
            SearchElement searchElement3 = this.searchElement;
            Intrinsics.checkNotNull(searchElement3);
            String string = searchElement3.getSearchBox().func_146179_b();
            Intrinsics.checkNotNullExpressionValue(string, "searchElement!!.searchBox.text");
            if (!(((CharSequence)string).length() == 0)) continue;
            if (Intrinsics.areEqual(ce.getName(), "INFO") && ce.getFocused()) {
                if (MouseUtils.mouseWithinBounds(mouseX, mouseY, this.windowXStart + this.sideWidth + 15.0f, this.windowYStart + 45.0f, this.windowXStart + this.sideWidth + 45.0f, this.windowYStart + 75.0f)) {
                    HttpUtils.INSTANCE.openWebpage("https://www.discord.gg/E4AbJZsaXq");
                }
                if (MouseUtils.mouseWithinBounds(mouseX, mouseY, this.windowXStart + this.sideWidth + 15.0f, this.windowYStart + 77.0f, this.windowXStart + this.sideWidth + 45.0f, this.windowYStart + 107.0f)) {
                    HttpUtils.INSTANCE.openWebpage("https://www.github.com/shxp3");
                }
            }
            if (!Intrinsics.areEqual(ce.getName(), "CONFIG") || !ce.getFocused() || mouseButton != 0) continue;
            float saveX1 = this.windowXStart + this.sideWidth + 15.0f;
            float saveY1 = this.windowYStart + 50.0f + 140.0f;
            float saveX2 = saveX1 + 95.0f;
            float saveY2 = saveY1 + 20.0f;
            if (MouseUtils.mouseWithinBounds(mouseX, mouseY, saveX1, saveY1, saveX2, saveY2)) {
                Desktop.getDesktop().open(CrossSine.INSTANCE.getFileManager().getConfigsDir());
            }
            if (MouseUtils.mouseWithinBounds(mouseX, mouseY, loadX1 = saveX1 + 105.0f, loadY1 = saveY1, loadX2 = loadX1 + 95.0f, loadY2 = loadY1 + 20.0f)) {
                SelectedConfig config = this.selectedConfig;
                boolean bl = false;
                if (config.isOnline()) {
                    Object v4;
                    block22: {
                        List<ConfigManager.OnlineConfig> list = this.onlineConfigList;
                        Intrinsics.checkNotNull(list);
                        for (Object t : (Iterable)list) {
                            ConfigManager.OnlineConfig it = (ConfigManager.OnlineConfig)t;
                            boolean bl2 = false;
                            if (!Intrinsics.areEqual(it.getName(), config.getName())) continue;
                            v4 = t;
                            break block22;
                        }
                        v4 = null;
                    }
                    ConfigManager.OnlineConfig onlineConfig = v4;
                    if (onlineConfig != null) {
                        ConfigManager.OnlineConfig it;
                        boolean bl3 = false;
                        CrossSine.INSTANCE.getConfigManager().loadOnlineConfig(it);
                    }
                } else {
                    ConfigManager.load$default(CrossSine.INSTANCE.getConfigManager(), config.getName(), false, 2, null);
                }
                CrossSine.INSTANCE.getConfigManager().setNowConfig(this.selectedConfig.getName());
            }
            if (MouseUtils.mouseWithinBounds(mouseX, mouseY, deleteX1 = saveX1, deleteY1 = saveY2 + 5.0f, deleteX2 = saveX2, deleteY2 = deleteY1 + 20.0f) && (file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.selectedConfig.getName(), ".json"))).exists()) {
                file.delete();
                if (Intrinsics.areEqual(CrossSine.INSTANCE.getConfigManager().getNowConfig(), this.selectedConfig.getName())) {
                    CrossSine.INSTANCE.getConfigManager().setNowConfig("default");
                }
            }
            if (!MouseUtils.mouseWithinBounds(mouseX, mouseY, toggleX1 = this.windowXStart + this.sideWidth + 15.0f + 105.0f, toggleY1 = loadY2 + 5.0f, toggleX2 = toggleX1 + 95.0f, toggleY2 = toggleY1 + 20.0f) || !Mouse.isButtonDown((int)0)) continue;
            this.showOnlineConfigs = !this.showOnlineConfigs;
        }
        float startY = 0.0f;
        startY = this.windowYStart + this.elementsStartY;
        SearchElement searchElement4 = this.searchElement;
        Intrinsics.checkNotNull(searchElement4);
        searchElement4.handleMouseClick(mouseX, mouseY, mouseButton, this.windowXStart + this.getCategoryXOffset(), this.windowYStart + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, this.categoryElements);
        SearchElement searchElement5 = this.searchElement;
        Intrinsics.checkNotNull(searchElement5);
        if (!searchElement5.isTyping()) {
            Iterable $this$forEach$iv = this.categoryElements;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                CategoryElement cat = (CategoryElement)element$iv;
                boolean bl = false;
                if (cat.getFocused()) {
                    cat.handleMouseClick(mouseX, mouseY, mouseButton, this.getWindowXStart() + this.getCategoryXOffset(), this.getWindowYStart() + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin);
                }
                if (MouseUtils.mouseWithinBounds(mouseX, mouseY, this.getWindowXStart(), startY, this.getWindowXStart() + this.getCategoryXOffset(), startY + this.elementHeight)) {
                    SearchElement searchElement6 = this.searchElement;
                    Intrinsics.checkNotNull(searchElement6);
                    if (!searchElement6.isTyping()) {
                        this.categoryElements.forEach(ClickGui::mouseClicked$lambda-15$lambda-14);
                        cat.setFocused(true);
                        return;
                    }
                }
                startY += this.elementHeight;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    protected void func_146286_b(int mouseX, int mouseY, int state) {
        if (this.moveDragging && this.getMoveAera().contains(mouseX, mouseY)) {
            this.moveDragging = false;
            return;
        }
        if (this.resizeDragging) {
            this.resizeDragging = false;
        }
        if (this.splitDragging) {
            this.splitDragging = false;
        }
        SearchElement searchElement = this.searchElement;
        Intrinsics.checkNotNull(searchElement);
        searchElement.handleMouseRelease(mouseX, mouseY, state, this.windowXStart + this.getCategoryXOffset(), this.windowYStart + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, this.categoryElements);
        SearchElement searchElement2 = this.searchElement;
        Intrinsics.checkNotNull(searchElement2);
        if (!searchElement2.isTyping()) {
            void $this$forEach$iv;
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = this.categoryElements;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                CategoryElement it = (CategoryElement)element$iv$iv;
                boolean bl = false;
                if (!it.getFocused()) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                CategoryElement cat = (CategoryElement)element$iv;
                boolean bl = false;
                cat.handleMouseRelease(mouseX, mouseY, state, this.getWindowXStart() + this.getCategoryXOffset(), this.getWindowYStart() + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin);
            }
        }
        super.func_146286_b(mouseX, mouseY, state);
    }

    /*
     * WARNING - void declaration
     */
    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        void $this$filterTo$iv$iv;
        if (!(keyCode != 1 || this.cant || this.textEditing || this.keyBinding)) {
            this.closed = true;
            if (((Boolean)ClickGUIModule.fastRenderValue.get()).booleanValue()) {
                this.field_146297_k.func_147108_a(null);
            }
            return;
        }
        Iterable $this$filter$iv = this.categoryElements;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            CategoryElement it = (CategoryElement)element$iv$iv;
            boolean bl = false;
            if (!it.getFocused()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            CategoryElement cat = (CategoryElement)element$iv;
            boolean bl = false;
            if (!cat.handleKeyTyped(typedChar, keyCode)) continue;
            return;
        }
        SearchElement searchElement = this.searchElement;
        Intrinsics.checkNotNull(searchElement);
        if (searchElement.handleTyping(typedChar, keyCode, this.windowXStart + this.getCategoryXOffset(), this.windowYStart + this.categoriesTopMargin, this.getWindowWidth() - this.getCategoryXOffset(), this.getWindowHeight() - this.categoriesBottommargin, this.categoryElements)) {
            return;
        }
        super.func_73869_a(typedChar, keyCode);
    }

    public boolean func_73868_f() {
        return false;
    }

    private static final void drawFullSized$lambda-10(ClickGui this$0) {
        Intrinsics.checkNotNullParameter((Object)this$0, "this$0");
        this$0.onlineConfigList = CrossSine.INSTANCE.getConfigManager().fetchOnlineConfigs();
    }

    private static final void mouseClicked$lambda-15$lambda-14(CategoryElement e) {
        Intrinsics.checkNotNullParameter(e, "e");
        e.setFocused(false);
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/ClickGui$Companion;", "", "()V", "instance", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/ClickGui;", "getInstance", "CrossSine"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ClickGui getInstance() {
            ClickGui clickGui;
            if (instance == null) {
                ClickGui clickGui2;
                ClickGui it = clickGui2 = new ClickGui();
                boolean bl = false;
                instance = it;
                clickGui = clickGui2;
            } else {
                ClickGui clickGui3 = instance;
                clickGui = clickGui3;
                Intrinsics.checkNotNull((Object)clickGui3);
            }
            return clickGui;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

