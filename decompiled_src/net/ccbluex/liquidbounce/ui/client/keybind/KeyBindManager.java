/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.keybind;

import java.awt.Color;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.ui.client.keybind.KeyInfo;
import net.ccbluex.liquidbounce.ui.client.other.PopUI;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u0007H\u0016J \u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u001dH\u0016J\u0018\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0004H\u0014J \u0010'\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004H\u0014J\b\u0010)\u001a\u00020\u001dH\u0016J\u0006\u0010*\u001a\u00020\u001dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006+"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "baseHeight", "", "baseWidth", "clicked", "", "getClicked", "()Z", "setClicked", "(Z)V", "keys", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "Lkotlin/collections/ArrayList;", "nowDisplayKey", "getNowDisplayKey", "()Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;", "setNowDisplayKey", "(Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyInfo;)V", "popUI", "Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "getPopUI", "()Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;", "setPopUI", "(Lnet/ccbluex/liquidbounce/ui/client/other/PopUI;)V", "doesGuiPauseGame", "drawScreen", "", "mouseX", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateAllKeys", "CrossSine"})
public final class KeyBindManager
extends GuiScreen {
    private final int baseHeight;
    private final int baseWidth;
    @NotNull
    private final ArrayList<KeyInfo> keys = new ArrayList();
    @Nullable
    private KeyInfo nowDisplayKey;
    private boolean clicked;
    @Nullable
    private PopUI popUI;

    public KeyBindManager() {
        this.baseHeight = 205;
        this.baseWidth = 500;
        this.keys.add(new KeyInfo(12.0f, 12.0f, 27.0f, 32.0f, 41, "`"));
        this.keys.add(new KeyInfo(44.0f, 12.0f, 27.0f, 32.0f, 2, "1"));
        this.keys.add(new KeyInfo(76.0f, 12.0f, 27.0f, 32.0f, 3, "2"));
        this.keys.add(new KeyInfo(108.0f, 12.0f, 27.0f, 32.0f, 4, "3"));
        this.keys.add(new KeyInfo(140.0f, 12.0f, 27.0f, 32.0f, 5, "4"));
        this.keys.add(new KeyInfo(172.0f, 12.0f, 27.0f, 32.0f, 6, "5"));
        this.keys.add(new KeyInfo(204.0f, 12.0f, 27.0f, 32.0f, 7, "6"));
        this.keys.add(new KeyInfo(236.0f, 12.0f, 27.0f, 32.0f, 8, "7"));
        this.keys.add(new KeyInfo(268.0f, 12.0f, 27.0f, 32.0f, 9, "8"));
        this.keys.add(new KeyInfo(300.0f, 12.0f, 27.0f, 32.0f, 10, "9"));
        this.keys.add(new KeyInfo(332.0f, 12.0f, 27.0f, 32.0f, 11, "0"));
        this.keys.add(new KeyInfo(364.0f, 12.0f, 27.0f, 32.0f, 12, "-"));
        this.keys.add(new KeyInfo(396.0f, 12.0f, 27.0f, 32.0f, 13, "="));
        this.keys.add(new KeyInfo(428.0f, 12.0f, 59.0f, 32.0f, 14, "Backspace"));
        this.keys.add(new KeyInfo(12.0f, 49.0f, 43.0f, 32.0f, 15, "Tab"));
        this.keys.add(new KeyInfo(60.0f, 49.0f, 27.0f, 32.0f, 16, "Q"));
        this.keys.add(new KeyInfo(92.0f, 49.0f, 27.0f, 32.0f, 17, "W"));
        this.keys.add(new KeyInfo(124.0f, 49.0f, 27.0f, 32.0f, 18, "E"));
        this.keys.add(new KeyInfo(156.0f, 49.0f, 27.0f, 32.0f, 19, "R"));
        this.keys.add(new KeyInfo(188.0f, 49.0f, 27.0f, 32.0f, 20, "T"));
        this.keys.add(new KeyInfo(220.0f, 49.0f, 27.0f, 32.0f, 21, "Y"));
        this.keys.add(new KeyInfo(252.0f, 49.0f, 27.0f, 32.0f, 22, "U"));
        this.keys.add(new KeyInfo(284.0f, 49.0f, 27.0f, 32.0f, 23, "I"));
        this.keys.add(new KeyInfo(316.0f, 49.0f, 27.0f, 32.0f, 24, "O"));
        this.keys.add(new KeyInfo(348.0f, 49.0f, 27.0f, 32.0f, 25, "P"));
        this.keys.add(new KeyInfo(380.0f, 49.0f, 27.0f, 32.0f, 26, "["));
        this.keys.add(new KeyInfo(412.0f, 49.0f, 27.0f, 32.0f, 27, "]"));
        this.keys.add(new KeyInfo(444.0f, 49.0f, 43.0f, 32.0f, 43, "\\"));
        this.keys.add(new KeyInfo(12.0f, 86.0f, 59.0f, 32.0f, 15, "Caps Lock"));
        this.keys.add(new KeyInfo(76.0f, 86.0f, 27.0f, 32.0f, 30, "A"));
        this.keys.add(new KeyInfo(108.0f, 86.0f, 27.0f, 32.0f, 31, "S"));
        this.keys.add(new KeyInfo(140.0f, 86.0f, 27.0f, 32.0f, 32, "D"));
        this.keys.add(new KeyInfo(172.0f, 86.0f, 27.0f, 32.0f, 33, "F"));
        this.keys.add(new KeyInfo(204.0f, 86.0f, 27.0f, 32.0f, 34, "G"));
        this.keys.add(new KeyInfo(236.0f, 86.0f, 27.0f, 32.0f, 35, "H"));
        this.keys.add(new KeyInfo(268.0f, 86.0f, 27.0f, 32.0f, 36, "J"));
        this.keys.add(new KeyInfo(300.0f, 86.0f, 27.0f, 32.0f, 37, "K"));
        this.keys.add(new KeyInfo(332.0f, 86.0f, 27.0f, 32.0f, 38, "L"));
        this.keys.add(new KeyInfo(364.0f, 86.0f, 27.0f, 32.0f, 39, ""));
        this.keys.add(new KeyInfo(396.0f, 86.0f, 27.0f, 32.0f, 40, "'"));
        this.keys.add(new KeyInfo(428.0f, 86.0f, 59.0f, 32.0f, 28, "Enter"));
        this.keys.add(new KeyInfo(12.0f, 123.0f, 75.0f, 32.0f, 42, "Shift", "LShift"));
        this.keys.add(new KeyInfo(92.0f, 123.0f, 27.0f, 32.0f, 44, "Z"));
        this.keys.add(new KeyInfo(124.0f, 123.0f, 27.0f, 32.0f, 45, "X"));
        this.keys.add(new KeyInfo(156.0f, 123.0f, 27.0f, 32.0f, 46, "C"));
        this.keys.add(new KeyInfo(188.0f, 123.0f, 27.0f, 32.0f, 47, "V"));
        this.keys.add(new KeyInfo(220.0f, 123.0f, 27.0f, 32.0f, 48, "B"));
        this.keys.add(new KeyInfo(252.0f, 123.0f, 27.0f, 32.0f, 49, "N"));
        this.keys.add(new KeyInfo(284.0f, 123.0f, 27.0f, 32.0f, 50, "M"));
        this.keys.add(new KeyInfo(316.0f, 123.0f, 27.0f, 32.0f, 51, ","));
        this.keys.add(new KeyInfo(348.0f, 123.0f, 27.0f, 32.0f, 52, "."));
        this.keys.add(new KeyInfo(380.0f, 123.0f, 27.0f, 32.0f, 53, "/"));
        this.keys.add(new KeyInfo(412.0f, 123.0f, 75.0f, 32.0f, 54, "Shift", "RShift"));
        this.keys.add(new KeyInfo(12.0f, 160.0f, 43.0f, 32.0f, 29, "Ctrl", "LCtrl"));
        this.keys.add(new KeyInfo(60.0f, 160.0f, 43.0f, 32.0f, 56, "Alt", "LAlt"));
        this.keys.add(new KeyInfo(108.0f, 160.0f, 251.0f, 32.0f, 57, " ", "Space"));
        this.keys.add(new KeyInfo(364.0f, 160.0f, 43.0f, 32.0f, 184, "Alt", "RAlt"));
        this.keys.add(new KeyInfo(412.0f, 160.0f, 27.0f, 32.0f, 199, "\u00d8", "Home"));
        this.keys.add(new KeyInfo(444.0f, 160.0f, 43.0f, 32.0f, 157, "Ctrl", "RCtrl"));
    }

    @Nullable
    public final KeyInfo getNowDisplayKey() {
        return this.nowDisplayKey;
    }

    public final void setNowDisplayKey(@Nullable KeyInfo keyInfo) {
        this.nowDisplayKey = keyInfo;
    }

    public final boolean getClicked() {
        return this.clicked;
    }

    public final void setClicked(boolean bl) {
        this.clicked = bl;
    }

    @Nullable
    public final PopUI getPopUI() {
        return this.popUI;
    }

    public final void setPopUI(@Nullable PopUI popUI) {
        this.popUI = popUI;
    }

    public void func_73866_w_() {
        this.nowDisplayKey = null;
        this.updateAllKeys();
    }

    public final void updateAllKeys() {
        new Thread(() -> KeyBindManager.updateAllKeys$lambda-0(this)).start();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int wheel;
        this.func_146276_q_();
        int mcWidth = (int)((float)this.field_146294_l * 0.8f - (float)this.field_146294_l * 0.2f);
        GL11.glPushMatrix();
        GL11.glPushMatrix();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        Fonts.font50SemiBold.func_175065_a("KeyBind Manager", (float)this.field_146294_l * 0.21f * 0.5f, (float)this.field_146295_m * 0.2f * 0.5f - 0.5f, Color.WHITE.getRGB(), false);
        GL11.glPopMatrix();
        GL11.glTranslatef((float)((float)this.field_146294_l * 0.2f), (float)((float)this.field_146295_m * 0.2f + (float)Fonts.SFApple40.getHeight() * 2.3f), (float)0.0f);
        float scale = (float)mcWidth / (float)this.baseWidth;
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        RenderUtils.drawRoundedRect(0.0f, 0.0f, this.baseWidth, this.baseHeight, 7.0f, Color.WHITE.getRGB());
        for (KeyInfo key : this.keys) {
            key.render();
        }
        if (this.nowDisplayKey != null) {
            KeyInfo keyInfo = this.nowDisplayKey;
            Intrinsics.checkNotNull(keyInfo);
            keyInfo.renderTab();
        }
        GL11.glPopMatrix();
        if (Mouse.hasWheel() && (wheel = Mouse.getDWheel()) != 0) {
            if (this.popUI != null) {
                PopUI popUI = this.popUI;
                Intrinsics.checkNotNull(popUI);
                popUI.onStroll(this.field_146294_l, this.field_146295_m, mouseX, mouseY, wheel);
            } else if (this.nowDisplayKey != null) {
                float scaledMouseX = ((float)mouseX - (float)this.field_146294_l * 0.2f) / scale;
                float scaledMouseY = ((float)mouseY - ((float)this.field_146295_m * 0.2f + (float)Fonts.SFApple40.getHeight() * 2.3f)) / scale;
                KeyInfo keyInfo = this.nowDisplayKey;
                Intrinsics.checkNotNull(keyInfo);
                keyInfo.stroll(scaledMouseX, scaledMouseY, wheel);
            }
        }
        PopUI popUI = this.popUI;
        if (popUI != null) {
            popUI.onRender(this.field_146294_l, this.field_146295_m);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (this.popUI == null) {
            float scale = ((float)this.field_146294_l * 0.8f - (float)this.field_146294_l * 0.2f) / (float)this.baseWidth;
            float scaledMouseX = ((float)mouseX - (float)this.field_146294_l * 0.2f) / scale;
            float scaledMouseY = ((float)mouseY - ((float)this.field_146295_m * 0.2f + (float)Fonts.SFApple40.getHeight() * 2.3f)) / scale;
            if (this.nowDisplayKey == null) {
                if (scaledMouseX < 0.0f || scaledMouseY < 0.0f || scaledMouseX > (float)this.baseWidth || scaledMouseY > (float)this.baseHeight) {
                    this.field_146297_k.func_147108_a(null);
                    return;
                }
                for (KeyInfo key : this.keys) {
                    if (!(scaledMouseX > key.getPosX()) || !(scaledMouseY > key.getPosY()) || !(scaledMouseX < key.getPosX() + key.getWidth()) || !(scaledMouseY < key.getPosY() + key.getHeight())) continue;
                    key.click(scaledMouseX, scaledMouseY);
                    break;
                }
            } else {
                KeyInfo keyInfo = this.nowDisplayKey;
                Intrinsics.checkNotNull(keyInfo);
                keyInfo.click(scaledMouseX, scaledMouseY);
            }
        } else {
            PopUI popUI = this.popUI;
            Intrinsics.checkNotNull(popUI);
            popUI.onClick(this.field_146294_l, this.field_146295_m, mouseX, mouseY);
        }
    }

    public void func_146281_b() {
        CrossSine.INSTANCE.getConfigManager().smartSave();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (1 == keyCode) {
            if (this.popUI != null) {
                PopUI popUI = this.popUI;
                Intrinsics.checkNotNull(popUI);
                popUI.setAnimatingOut(false);
                PopUI popUI2 = this.popUI;
                Intrinsics.checkNotNull(popUI2);
                if (popUI2.getAnimationProgress() >= 1.0f) {
                    this.popUI = null;
                }
            } else if (this.nowDisplayKey != null) {
                this.nowDisplayKey = null;
            } else {
                this.field_146297_k.func_147108_a(null);
            }
            return;
        }
        PopUI popUI = this.popUI;
        if (popUI != null) {
            popUI.onKey(typedChar, keyCode);
        }
    }

    public boolean func_73868_f() {
        return false;
    }

    private static final void updateAllKeys$lambda-0(KeyBindManager this$0) {
        Intrinsics.checkNotNullParameter((Object)this$0, "this$0");
        for (KeyInfo key : this$0.keys) {
            key.update();
        }
    }
}

