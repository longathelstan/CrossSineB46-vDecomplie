/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.keybind;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.ui.client.keybind.KeyInfo;
import net.ccbluex.liquidbounce.ui.client.other.PopUI;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0002J\u0018\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0016J\b\u0010\u001a\u001a\u00020\u0015H\u0016J\u0018\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0010H\u0016J\b\u0010\u001f\u001a\u00020\u0015H\u0016J \u0010\u000f\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u0010H\u0016J\b\u0010!\u001a\u00020\u0015H\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2={"Lnet/ccbluex/liquidbounce/ui/client/keybind/KeySelectUI;", "Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "info", "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "(Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;)V", "animationScroll", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "height", "", "getInfo", "()Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "maxStroll", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "scroll", "", "singleHeight", "str", "", "apply", "", "module", "click", "mouseX", "mouseY", "close", "key", "typedChar", "", "keyCode", "render", "wheel", "update", "CrossSine"})
public final class KeySelectUI
extends PopUI {
    @NotNull
    private final KeyInfo info;
    @NotNull
    private String str;
    @NotNull
    private List<? extends Module> modules;
    private final float singleHeight;
    private int scroll;
    @Nullable
    private Animation animationScroll;
    private float maxStroll;
    private final float height;

    public KeySelectUI(@NotNull KeyInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        super("Select a module to bind");
        this.info = info;
        this.str = "";
        this.modules = CollectionsKt.toList((Iterable)CrossSine.INSTANCE.getModuleManager().getModules());
        this.singleHeight = Fonts.SFApple35.getHeight();
        this.maxStroll = (float)this.modules.size() * this.singleHeight;
        this.height = 8.0f + (float)Fonts.SFApple40.getHeight() + (float)Fonts.SFApple35.getHeight() + 0.5f;
    }

    @NotNull
    public final KeyInfo getInfo() {
        return this.info;
    }

    @Override
    public void render() {
        CharSequence charSequence;
        if (this.animationScroll == null) {
            this.animationScroll = new Animation(Easing.EASE_OUT_CIRC, 20L);
            Intrinsics.checkNotNull(this.animationScroll);
            this.animationScroll.value = this.scroll;
        }
        Animation animation = this.animationScroll;
        Intrinsics.checkNotNull(animation);
        animation.run(this.scroll);
        Animation animation2 = this.animationScroll;
        Intrinsics.checkNotNull(animation2);
        float yOffset = this.height - (float)animation2.value + 5.0f;
        if (StringsKt.startsWith$default(this.str, ".", false, 2, null)) {
            Fonts.SFApple35.func_175065_a("Press ENTER to add macro.", 8.0f, this.singleHeight + yOffset, Color.BLACK.getRGB(), false);
        } else {
            for (Module module : this.modules) {
                if (yOffset > this.height - this.singleHeight && yOffset - this.singleHeight < 190.0f) {
                    String string;
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)0.0f, (float)yOffset, (float)0.0f);
                    String name = module.getName();
                    if (((CharSequence)this.str).length() > 0) {
                        StringBuilder stringBuilder = new StringBuilder().append("\u00a70");
                        String string2 = name.substring(0, this.str.length());
                        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                        StringBuilder stringBuilder2 = stringBuilder.append(string2).append("\u00a77");
                        string2 = name.substring(this.str.length(), name.length());
                        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                        string = stringBuilder2.append(string2).toString();
                    } else {
                        string = Intrinsics.stringPlus("\u00a70", name);
                    }
                    Fonts.SFApple35.func_175065_a(string, 8.0f, this.singleHeight * 0.5f, Color.BLACK.getRGB(), false);
                    GL11.glPopMatrix();
                }
                yOffset += this.singleHeight;
            }
        }
        RenderUtils.drawRect(0.0f, 8.0f + (float)Fonts.SFApple40.getHeight(), (float)this.getBaseWidth(), this.height + 5.0f, Color.WHITE.getRGB());
        RenderUtils.drawRect(0.0f, (float)this.getBaseHeight() - this.singleHeight, (float)this.getBaseWidth(), (float)this.getBaseHeight(), Color.WHITE.getRGB());
        GameFontRenderer gameFontRenderer = Fonts.SFApple35;
        CharSequence charSequence2 = this.str;
        if (charSequence2.length() == 0) {
            GameFontRenderer gameFontRenderer2 = gameFontRenderer;
            boolean bl = false;
            charSequence = "Search...";
            gameFontRenderer = gameFontRenderer2;
        } else {
            charSequence = charSequence2;
        }
        gameFontRenderer.func_175065_a((String)charSequence, 8.0f, 8.0f + (float)Fonts.SFApple40.getHeight() + 4.0f, Color.LIGHT_GRAY.getRGB(), false);
        RenderUtils.drawRect(8.0f, this.height + 2.0f, (float)this.getBaseWidth() - 8.0f, this.height + 3.0f, Color.LIGHT_GRAY.getRGB());
    }

    @Override
    public void key(char typedChar, int keyCode) {
        switch (keyCode) {
            case 14: {
                if (((CharSequence)this.str).length() > 0) {
                    String string = this.str.substring(0, this.str.length() - 1);
                    Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                    this.str = string;
                    this.update();
                }
                return;
            }
            case 28: {
                if (StringsKt.startsWith$default(this.str, ".", false, 2, null)) {
                    CrossSine.INSTANCE.getMacroManager().getMacros().add(new Macro(this.info.getKey(), this.str));
                    CrossSine.INSTANCE.getKeyBindManager().updateAllKeys();
                    this.close();
                } else if (!((Collection)this.modules).isEmpty()) {
                    this.apply(this.modules.get(0));
                }
                return;
            }
        }
        if (ChatAllowedCharacters.func_71566_a((char)typedChar)) {
            this.str = Intrinsics.stringPlus(this.str, Character.valueOf(typedChar));
            this.update();
        }
    }

    @Override
    public void scroll(float mouseX, float mouseY, int wheel) {
        int afterStroll = this.scroll - wheel / 10;
        if (afterStroll > 0 && (float)afterStroll < this.maxStroll - (float)100) {
            this.scroll = afterStroll;
        }
    }

    @Override
    public void click(float mouseX, float mouseY) {
        if (mouseX < 8.0f || mouseX > (float)(this.getBaseWidth() - 8) || mouseY < this.height || mouseY > (float)this.getBaseHeight() - this.singleHeight) {
            return;
        }
        double d = this.height;
        Animation animation = this.animationScroll;
        Intrinsics.checkNotNull(animation);
        double yOffset = d - animation.value + (double)2.0f;
        for (Module module : this.modules) {
            if ((double)mouseY > yOffset && (double)mouseY < yOffset + (double)this.singleHeight) {
                this.apply(module);
                break;
            }
            yOffset += (double)this.singleHeight;
        }
    }

    private final void apply(Module module) {
        module.setKeyBind(this.info.getKey());
        CrossSine.INSTANCE.getKeyBindManager().updateAllKeys();
        this.close();
    }

    @Override
    public void close() {
        this.setAnimatingOut(false);
        if (this.getAnimationProgress() >= 1.0f) {
            CrossSine.INSTANCE.getKeyBindManager().setPopUI(null);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void update() {
        List list;
        KeySelectUI keySelectUI = this;
        if (((CharSequence)this.str).length() > 0) {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            Iterable iterable = CrossSine.INSTANCE.getModuleManager().getModules();
            KeySelectUI keySelectUI2 = keySelectUI;
            boolean $i$f$filter = false;
            void var3_4 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                Module it = (Module)element$iv$iv;
                boolean bl = false;
                if (!StringsKt.startsWith(it.getName(), this.str, true)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            list = (List)destination$iv$iv;
            keySelectUI = keySelectUI2;
        } else {
            list = CollectionsKt.toList((Iterable)CrossSine.INSTANCE.getModuleManager().getModules());
        }
        keySelectUI.modules = list;
        this.maxStroll = (float)this.modules.size() * this.singleHeight;
        this.scroll = 0;
    }
}

