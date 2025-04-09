/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.lang.invoke.LambdaMetafactory;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.liuli.elixir.manage.AccountSerializer;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014J \u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\fH\u0016J\u0018\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0011H\u0014J \u0010\u001a\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0011H\u0014J\b\u0010\u001c\u001a\u00020\fH\u0016J\b\u0010\u001d\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDirectLogin;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "password", "Lnet/ccbluex/liquidbounce/ui/elements/GuiPasswordField;", "status", "", "username", "Lnet/minecraft/client/gui/GuiTextField;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "CrossSine"})
public final class GuiDirectLogin
extends GuiScreen {
    @NotNull
    private final GuiAltManager prevGui;
    private GuiTextField username;
    private GuiPasswordField password;
    @NotNull
    private String status;

    public GuiDirectLogin(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkNotNullParameter((Object)prevGui, "prevGui");
        this.prevGui = prevGui;
        this.status = "\u00a77Idle";
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72, "Login"));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, "ClipBoard"));
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120, "back"));
        this.username = new GuiTextField(2, (FontRenderer)Fonts.fontTenacityBold35, this.field_146294_l / 2 - 100, 60, 200, 20);
        GuiTextField guiTextField = this.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        guiTextField.func_146195_b(true);
        GuiTextField guiTextField2 = this.username;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField2 = null;
        }
        guiTextField2.func_146203_f(Integer.MAX_VALUE);
        GameFontRenderer gameFontRenderer = Fonts.fontTenacityBold35;
        Intrinsics.checkNotNullExpressionValue((Object)gameFontRenderer, "fontTenacityBold35");
        this.password = new GuiPasswordField(3, gameFontRenderer, this.field_146294_l / 2 - 100, 85, 200, 20);
        GuiPasswordField guiPasswordField = this.password;
        if (guiPasswordField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiPasswordField = null;
        }
        guiPasswordField.func_146203_f(Integer.MAX_VALUE);
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        GuiPasswordField guiPasswordField;
        this.func_146278_c(0);
        this.func_73732_a(Fonts.fontTenacityBold35, "Direct Login", this.field_146294_l / 2, 34, 0xFFFFFF);
        this.func_73732_a(Fonts.fontTenacityBold35, this.status, this.field_146294_l / 2, this.field_146295_m / 4 + 60, 0xFFFFFF);
        GuiTextField guiTextField = this.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        guiTextField.func_146194_f();
        GuiPasswordField guiPasswordField2 = this.password;
        if (guiPasswordField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiPasswordField2 = null;
        }
        guiPasswordField2.func_146194_f();
        GuiTextField guiTextField2 = this.username;
        if (guiTextField2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField2 = null;
        }
        String string = guiTextField2.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "username.text");
        if (((CharSequence)string).length() == 0) {
            GuiTextField guiTextField3 = this.username;
            if (guiTextField3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("username");
                guiTextField3 = null;
            }
            if (!guiTextField3.func_146206_l()) {
                this.func_73732_a(Fonts.fontTenacityBold35, "\u00a77Username", this.field_146294_l / 2 - 55, 66, 0xFFFFFF);
            }
        }
        if ((guiPasswordField = this.password) == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiPasswordField = null;
        }
        string = guiPasswordField.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "password.text");
        if (((CharSequence)string).length() == 0) {
            GuiPasswordField guiPasswordField3 = this.password;
            if (guiPasswordField3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("password");
                guiPasswordField3 = null;
            }
            if (!guiPasswordField3.func_146206_l()) {
                this.func_73732_a(Fonts.fontTenacityBold35, "\u00a77PassWord", this.field_146294_l / 2 - 74, 91, 0xFFFFFF);
            }
        }
        String it = string = "Add ms@ before your real username can login microsoft account without browser!";
        boolean bl = false;
        Fonts.fontTenacityBold35.func_78276_b(it, this.field_146294_l - Fonts.fontTenacityBold35.func_78256_a(it), this.field_146295_m - Fonts.fontTenacityBold35.field_78288_b, 0xFFFFFF);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    /*
     * Unable to fully structure code
     */
    protected void func_146284_a(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 0: {
                this.field_146297_k.func_147108_a((GuiScreen)this.prevGui);
                break;
            }
            case 1: {
                v0 = this.username;
                if (v0 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("username");
                    v0 = null;
                }
                var2_2 = v0.func_146179_b();
                Intrinsics.checkNotNullExpressionValue(var2_2, "username.text");
                if (((CharSequence)var2_2).length() == 0) {
                    this.status = "\u00a7cFillBoat";
                    return;
                }
                new Thread((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, actionPerformed$lambda-1(net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDirectLogin ), ()V)((GuiDirectLogin)this)).start();
                break;
            }
            case 2: {
                var3_4 = GuiScreen.func_146277_j();
                Intrinsics.checkNotNullExpressionValue(var3_4, "getClipboardString()");
                v1 = (CharSequence)var3_4;
                var3_4 = new String[]{":"};
                args = StringsKt.split$default(v1, (String[])var3_4, false, 0, 6, null);
                v2 = this.username;
                if (v2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("username");
                    v2 = null;
                }
                v2.func_146180_a((String)args.get(0));
                v3 = this.password;
                if (v3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("password");
                    v3 = null;
                }
                if ((v4 = (String)CollectionsKt.getOrNull(args, 1)) == null) {
                    v4 = "";
                }
                v3.func_146180_a(v4);
                var3_4 = this.field_146292_n;
                Intrinsics.checkNotNullExpressionValue(var3_4, "buttonList");
                var3_4 = (Iterable)var3_4;
                var8_5 = this;
                var4_6 = var3_4.iterator();
                while (var4_6.hasNext()) {
                    var5_7 = var4_6.next();
                    it = (GuiButton)var5_7;
                    $i$a$-find-GuiDirectLogin$actionPerformed$2 = false;
                    if (!(it.field_146127_k == 1)) continue;
                    v5 = var5_7;
                    ** GOTO lbl51
                }
                v5 = null;
lbl51:
                // 2 sources

                Intrinsics.checkNotNull(v5);
                var8_5.func_146284_a(v5);
            }
        }
        super.func_146284_a(button);
    }

    /*
     * Unable to fully structure code
     */
    protected void func_73869_a(char typedChar, int keyCode) {
        switch (keyCode) {
            case 1: {
                this.field_146297_k.func_147108_a((GuiScreen)this.prevGui);
                return;
            }
            case 28: {
                var3_3 = this.field_146292_n;
                Intrinsics.checkNotNullExpressionValue(var3_3, "buttonList");
                var3_3 = var3_3;
                var8_4 = this;
                for (E var5_6 : var3_3) {
                    it = (GuiButton)var5_6;
                    $i$a$-find-GuiDirectLogin$keyTyped$1 = false;
                    if (!(it.field_146127_k == 1)) continue;
                    v0 = var5_6;
                    ** GOTO lbl17
                }
                v0 = null;
lbl17:
                // 2 sources

                Intrinsics.checkNotNull(v0);
                var8_4.func_146284_a(v0);
                return;
            }
        }
        v1 = this.username;
        if (v1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            v1 = null;
        }
        if (v1.func_146206_l()) {
            v2 = this.username;
            if (v2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("username");
                v2 = null;
            }
            v2.func_146201_a(typedChar, keyCode);
        }
        if ((v3 = this.password) == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            v3 = null;
        }
        if (v3.func_146206_l()) {
            v4 = this.password;
            if (v4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("password");
                v4 = null;
            }
            v4.func_146201_a(typedChar, keyCode);
        }
        super.func_73869_a(typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        GuiTextField guiTextField = this.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        guiTextField.func_146192_a(mouseX, mouseY, mouseButton);
        GuiPasswordField guiPasswordField = this.password;
        if (guiPasswordField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiPasswordField = null;
        }
        guiPasswordField.func_146192_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73876_c() {
        GuiTextField guiTextField = this.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        guiTextField.func_146178_a();
        GuiPasswordField guiPasswordField = this.password;
        if (guiPasswordField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiPasswordField = null;
        }
        guiPasswordField.func_146178_a();
        super.func_73876_c();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        super.func_146281_b();
    }

    private static final void actionPerformed$lambda-1(GuiDirectLogin this$0) {
        Intrinsics.checkNotNullParameter((Object)this$0, "this$0");
        GuiTextField guiTextField = this$0.username;
        if (guiTextField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("username");
            guiTextField = null;
        }
        String string = guiTextField.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "username.text");
        String string2 = string;
        GuiPasswordField guiPasswordField = this$0.password;
        if (guiPasswordField == null) {
            Intrinsics.throwUninitializedPropertyAccessException("password");
            guiPasswordField = null;
        }
        string = guiPasswordField.func_146179_b();
        Intrinsics.checkNotNullExpressionValue(string, "password.text");
        String res = GuiAltManager.Companion.login(AccountSerializer.INSTANCE.accountInstance(string2, string));
        this$0.status = "\u00a7aLogging in";
        this$0.status = res;
    }
}

