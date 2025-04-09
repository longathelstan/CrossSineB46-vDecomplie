/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui.element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.IconManager;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.CategoryElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.SearchBox;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.element.module.ModuleElement;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 JT\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u001d2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+2\u0006\u0010\u001f\u001a\u00020 J(\u0010-\u001a\u00020\"2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003H\u0002J\u001c\u0010.\u001a\b\u0012\u0004\u0012\u00020/0+2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+H\u0002JL\u00100\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+JL\u00102\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+J\u0018\u00103\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u0003H\u0002JD\u00104\u001a\u00020\u001b2\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00032\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+J\u0006\u00108\u001a\u00020\u001bJ\u0010\u00109\u001a\u00020\u001b2\u0006\u0010:\u001a\u00020/H\u0002R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0003X\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000b\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000b\"\u0004\b\u0019\u0010\u0015\u00a8\u0006;"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/SearchElement;", "", "xPos", "", "yPos", "width", "height", "(FFFF)V", "animBox", "animScrollHeight", "getHeight", "()F", "lastHeight", "scrollHeight", "searchBox", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/SearchBox;", "getSearchBox", "()Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/SearchBox;", "startYY", "getWidth", "setWidth", "(F)V", "getXPos", "setXPos", "getYPos", "setYPos", "drawBox", "", "mouseX", "", "mouseY", "accentColor", "Ljava/awt/Color;", "drawPanel", "", "mX", "mY", "x", "y", "w", "h", "wheel", "ces", "", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/CategoryElement;", "drawScroll", "getSearchModules", "Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/element/module/ModuleElement;", "handleMouseClick", "mouseButton", "handleMouseRelease", "handleScrolling", "handleTyping", "typedChar", "", "keyCode", "isTyping", "searchMatch", "module", "CrossSine"})
public final class SearchElement {
    private float xPos;
    private float yPos;
    private float width;
    private final float height;
    private float scrollHeight;
    private float animScrollHeight;
    private float lastHeight;
    private final float startYY;
    private float animBox;
    @NotNull
    private final SearchBox searchBox;

    public SearchElement(float xPos, float yPos, float width, float height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.startYY = 5.0f;
        this.searchBox = new SearchBox(0, (int)this.xPos + 2, (int)this.yPos + 2, (int)this.width - 4, (int)this.height - 2);
    }

    public final float getXPos() {
        return this.xPos;
    }

    public final void setXPos(float f) {
        this.xPos = f;
    }

    public final float getYPos() {
        return this.yPos;
    }

    public final void setYPos(float f) {
        this.yPos = f;
    }

    public final float getWidth() {
        return this.width;
    }

    public final void setWidth(float f) {
        this.width = f;
    }

    public final float getHeight() {
        return this.height;
    }

    @NotNull
    public final SearchBox getSearchBox() {
        return this.searchBox;
    }

    /*
     * Unable to fully structure code
     */
    public final boolean drawBox(int mouseX, int mouseY, @NotNull Color accentColor) {
        Intrinsics.checkNotNullParameter(accentColor, "accentColor");
        v0 = 0.0045000003f * (float)RenderUtils.deltaTime;
        if (this.searchBox.func_146206_l()) ** GOTO lbl-1000
        var4_4 = this.searchBox.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(var4_4, "searchBox.text");
        if (((CharSequence)var4_4).length() > 0) lbl-1000:
        // 2 sources

        {
            v1 = 1.0f;
        } else {
            v1 = -1.0f;
        }
        this.animBox += v0 * v1;
        this.animBox = RangesKt.coerceIn(this.animBox, 0.0f, 1.0f);
        percent = (float)EaseUtils.INSTANCE.easeInOutCirc(this.animBox);
        Stencil.write(true);
        RenderUtils.drawBloomGradientRoundedRect(this.xPos + (this.width - 20.0f - (this.width - 20.0f) * percent) / (float)2, this.yPos, this.xPos + (this.width - 20.0f - (this.width - 20.0f) * percent) / (float)2 + this.width * percent + (20.0f - 20.0f * percent), this.yPos + this.height, 6.0f, 5.0f, new Color(25, 25, 25), new Color(35, 35, 35), RenderUtils.ShaderBloom.BOTH);
        Stencil.erase(true);
        if (this.searchBox.func_146206_l()) {
            this.searchBox.func_146194_f();
        } else {
            var5_6 = this.searchBox.func_146179_b();
            Intrinsics.checkNotNullExpressionValue(var5_6, "searchBox.text");
            if (((CharSequence)var5_6).length() == 0) {
                this.searchBox.func_146194_f();
                this.searchBox.func_146180_a("");
            } else {
                this.searchBox.func_146194_f();
            }
        }
        Stencil.dispose();
        GlStateManager.func_179118_c();
        RenderUtils.drawImage2(IconManager.INSTANCE.getSearch(), this.xPos + (this.width - 15.0f) * percent + (5.0f - 5.0f * percent) + (this.width - 20.0f - (this.width - 20.0f) * percent) / (float)2, this.yPos + 5.0f, 10, 10);
        GlStateManager.func_179141_d();
        var5_6 = this.searchBox.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(var5_6, "searchBox.text");
        return ((CharSequence)var5_6).length() > 0;
    }

    private final boolean searchMatch(ModuleElement module) {
        CharSequence charSequence = module.getModule().getName();
        String string = this.searchBox.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "searchBox.text");
        return StringsKt.contains(charSequence, string, true);
    }

    /*
     * WARNING - void declaration
     */
    private final List<ModuleElement> getSearchModules(List<CategoryElement> ces) {
        List modules = new ArrayList();
        Iterable $this$forEach$iv = ces;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            CategoryElement cat = (CategoryElement)element$iv;
            boolean bl = false;
            Iterable iterable = cat.getModuleElements();
            List list = modules;
            boolean $i$f$filter = false;
            void var12_12 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                ModuleElement it = (ModuleElement)element$iv$iv;
                boolean bl2 = false;
                if (!this.searchMatch(it)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            list.addAll((List)destination$iv$iv);
        }
        return modules;
    }

    public final void drawPanel(int mX, int mY, float x, float y, float w, float h, int wheel, @NotNull List<CategoryElement> ces, @NotNull Color accentColor) {
        Intrinsics.checkNotNullParameter(ces, "ces");
        Intrinsics.checkNotNullParameter(accentColor, "accentColor");
        int mouseX = 0;
        mouseX = mX;
        int mouseY = 0;
        mouseY = mY;
        this.lastHeight = 0.0f;
        Iterable $this$forEach$iv = this.getSearchModules(ces);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ModuleElement mod = (ModuleElement)element$iv;
            boolean bl = false;
            if (!this.searchMatch(mod)) continue;
            this.lastHeight += mod.getAnimHeight() + 40.0f;
        }
        if (this.lastHeight >= 10.0f) {
            this.lastHeight -= 10.0f;
        }
        this.handleScrolling(wheel, h);
        this.drawScroll(x, y + this.startYY, w, h);
        Fonts.font30SemiBold.func_175063_a("Search", ClickGui.Companion.getInstance().getWindowXStart() + 20.0f, y - 12.0f, -1);
        RenderUtils.drawImage2(IconManager.INSTANCE.getBack(), ClickGui.Companion.getInstance().getWindowXStart() + 4.0f, y - 15.0f, 10, 10);
        float startY = 0.0f;
        startY = y + this.startYY;
        if ((float)mouseY < y + this.startYY || (float)mouseY >= y + h) {
            mouseY = -1;
        }
        RenderUtils.makeScissorBox(x, y + this.startYY, x + w, y + h);
        GL11.glEnable((int)3089);
        Iterable $this$forEach$iv2 = ces;
        boolean $i$f$forEach2 = false;
        for (Object element$iv : $this$forEach$iv2) {
            CategoryElement cat = (CategoryElement)element$iv;
            boolean bl = false;
            Iterable $this$forEach$iv3 = cat.getModuleElements();
            boolean $i$f$forEach3 = false;
            for (Object element$iv2 : $this$forEach$iv3) {
                ModuleElement mod = (ModuleElement)element$iv2;
                boolean bl2 = false;
                if (!this.searchMatch(mod)) continue;
                startY += startY + this.animScrollHeight > y + h || startY + this.animScrollHeight + 40.0f + mod.getAnimHeight() < y + this.startYY ? 40.0f + mod.getAnimHeight() : mod.drawElement(mouseX, mouseY, x, startY + this.animScrollHeight, w, 40.0f, accentColor);
            }
        }
        GL11.glDisable((int)3089);
    }

    private final void handleScrolling(int wheel, float height) {
        if (wheel != 0) {
            this.scrollHeight = wheel > 0 ? (this.scrollHeight += 50.0f) : (this.scrollHeight -= 50.0f);
        }
        this.scrollHeight = this.lastHeight > height - (this.startYY + 10.0f) ? RangesKt.coerceIn(this.scrollHeight, -this.lastHeight + height - (this.startYY + 10.0f), 0.0f) : 0.0f;
        this.animScrollHeight = AnimHelperKt.animSmooth(this.animScrollHeight, this.scrollHeight, 0.5f);
    }

    private final void drawScroll(float x, float y, float width, float height) {
        if (this.lastHeight > height - (this.startYY + 10.0f)) {
            float last = height - (this.startYY + 10.0f) - (height - (this.startYY + 10.0f)) * ((height - (this.startYY + 10.0f)) / this.lastHeight);
            float multiply = last * RangesKt.coerceIn(Math.abs(this.animScrollHeight / (-this.lastHeight + height - (this.startYY + 10.0f))), 0.0f, 1.0f);
            RenderUtils.originalRoundedRect(x + width - 6.0f, y + 5.0f + multiply, x + width - 4.0f, y + 5.0f + (height - (this.startYY + 10.0f)) * ((height - (this.startYY + 10.0f)) / this.lastHeight) + multiply, 1.0f, 0x50FFFFFF);
        }
    }

    public final void handleMouseClick(int mX, int mY, int mouseButton, float x, float y, float w, float h, @NotNull List<CategoryElement> ces) {
        Intrinsics.checkNotNullParameter(ces, "ces");
        if (MouseUtils.mouseWithinBounds(mX, mY, x - 200.0f, y - 20.0f, x - 170.0f, y)) {
            return;
        }
        int mouseY = 0;
        mouseY = mY;
        this.searchBox.func_146192_a(mX, mouseY, mouseButton);
        String string = this.searchBox.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "searchBox.text");
        if (((CharSequence)string).length() == 0) {
            return;
        }
        if ((float)mouseY < y + this.startYY || (float)mouseY >= y + h) {
            mouseY = -1;
        }
        float startY = 0.0f;
        startY = y + this.startYY;
        Iterable $this$forEach$iv = this.getSearchModules(ces);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ModuleElement mod = (ModuleElement)element$iv;
            boolean bl = false;
            mod.handleClick(mX, mouseY, mouseButton, x, startY + this.animScrollHeight, w, 40.0f);
            startY += 40.0f + mod.getAnimHeight();
        }
    }

    public final void handleMouseRelease(int mX, int mY, int mouseButton, float x, float y, float w, float h, @NotNull List<CategoryElement> ces) {
        Intrinsics.checkNotNullParameter(ces, "ces");
        int mouseX = 0;
        mouseX = mX;
        int mouseY = 0;
        mouseY = mY;
        String string = this.searchBox.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "searchBox.text");
        if (((CharSequence)string).length() == 0) {
            return;
        }
        if ((float)mouseY < y + this.startYY || (float)mouseY >= y + h) {
            mouseY = -1;
        }
        float startY = 0.0f;
        startY = y + this.startYY;
        Iterable $this$forEach$iv = this.getSearchModules(ces);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ModuleElement mod = (ModuleElement)element$iv;
            boolean bl = false;
            mod.handleRelease(mouseX, mouseY, x, startY + this.animScrollHeight, w, 40.0f);
            startY += 40.0f + mod.getAnimHeight();
        }
    }

    public final boolean handleTyping(char typedChar, int keyCode, float x, float y, float w, float h, @NotNull List<CategoryElement> ces) {
        Intrinsics.checkNotNullParameter(ces, "ces");
        this.searchBox.func_146201_a(typedChar, keyCode);
        String string = this.searchBox.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "searchBox.text");
        if (((CharSequence)string).length() == 0) {
            return false;
        }
        Iterable $this$forEach$iv = this.getSearchModules(ces);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ModuleElement mod = (ModuleElement)element$iv;
            boolean bl = false;
            if (!mod.handleKeyTyped(typedChar, keyCode)) continue;
            return true;
        }
        return false;
    }

    public final boolean isTyping() {
        String string = this.searchBox.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "searchBox.text");
        return ((CharSequence)string).length() > 0;
    }
}

