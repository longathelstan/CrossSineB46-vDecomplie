/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.manage.AccountSerializer;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014J \u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\fH\u0016J\u0018\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0011H\u0014J \u0010\u001a\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0011H\u0014J\b\u0010\u001c\u001a\u00020\fH\u0016J\b\u0010\u001d\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiAdd;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "password", "Lnet/ccbluex/liquidbounce/ui/elements/GuiPasswordField;", "status", "", "username", "Lnet/minecraft/client/gui/GuiTextField;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "CrossSine"})
public final class GuiAdd
extends GuiScreen {
    @NotNull
    private final GuiAltManager prevGui;
    private GuiTextField username;
    private GuiPasswordField password;
    @Nullable
    private String status;

    public GuiAdd(@NotNull GuiAltManager prevGui) {
        Intrinsics.checkNotNullParameter((Object)prevGui, "prevGui");
        this.prevGui = prevGui;
        this.status = "\u00a77Idle...";
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 72, "Add"));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96, "ClipBoard"));
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120, "Back"));
        this.username = new GuiTextField(2, (FontRenderer)Fonts.fontTenacity35, this.field_146294_l / 2 - 100, 60, 200, 20);
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
        GameFontRenderer gameFontRenderer = Fonts.fontTenacity35;
        Intrinsics.checkNotNullExpressionValue((Object)gameFontRenderer, "fontTenacity35");
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
        this.func_73732_a(Fonts.fontTenacity35, "Add", this.field_146294_l / 2, 34, 0xFFFFFF);
        this.func_73732_a(Fonts.fontTenacity35, this.status == null ? "" : this.status, this.field_146294_l / 2, this.field_146295_m / 4 + 60, 0xFFFFFF);
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
                this.func_73732_a(Fonts.fontTenacity35, "\u00a77UserName", this.field_146294_l / 2 - 55, 66, 0xFFFFFF);
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
                this.func_73732_a(Fonts.fontTenacity35, "\u00a77PassWord", this.field_146294_l / 2 - 74, 91, 0xFFFFFF);
            }
        }
        String it = string = "Add ms@ before your real username can login microsoft account without browser!";
        boolean bl = false;
        Fonts.fontTenacity35.func_78276_b(it, this.field_146294_l - Fonts.fontTenacity35.func_78256_a(it), this.field_146295_m - Fonts.fontTenacity35.field_78288_b, 0xFFFFFF);
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
                $this$any$iv = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
                $i$f$any = false;
                if (!($this$any$iv instanceof Collection) || !((Collection)$this$any$iv).isEmpty()) ** GOTO lbl14
                v0 = false;
                ** GOTO lbl28
lbl14:
                // 1 sources

                var4_6 = $this$any$iv.iterator();
                while (var4_6.hasNext()) {
                    element$iv = var4_6.next();
                    it = (MinecraftAccount)element$iv;
                    $i$a$-any-GuiAdd$actionPerformed$1 = false;
                    v1 = it.getName();
                    v2 = this.username;
                    if (v2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("username");
                        v2 = null;
                    }
                    if (!Intrinsics.areEqual(v1, v2.func_146179_b())) continue;
                    v0 = true;
                    ** GOTO lbl28
                }
                v0 = false;
lbl28:
                // 3 sources

                if (v0) {
                    this.status = "\u00a7cAlready Add";
                    return;
                }
                v3 = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
                v4 = this.username;
                if (v4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("username");
                    v4 = null;
                }
                $this$any$iv = v4.func_146179_b();
                Intrinsics.checkNotNullExpressionValue($this$any$iv, "username.text");
                v5 = $this$any$iv;
                v6 = this.password;
                if (v6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("password");
                    v6 = null;
                }
                $this$any$iv = v6.func_146179_b();
                Intrinsics.checkNotNullExpressionValue($this$any$iv, "password.text");
                v3.add(AccountSerializer.INSTANCE.accountInstance((String)v5, (String)$this$any$iv));
                CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getAccountsConfig());
                $this$any$iv = this.field_146292_n;
                Intrinsics.checkNotNullExpressionValue($this$any$iv, "buttonList");
                $this$any$iv = (Iterable)$this$any$iv;
                var9_13 = this;
                var4_6 = $this$any$iv.iterator();
                while (var4_6.hasNext()) {
                    var5_7 = var4_6.next();
                    it = (GuiButton)var5_7;
                    $i$a$-find-GuiAdd$actionPerformed$2 = false;
                    if (!(it.field_146127_k == 0)) continue;
                    v7 = var5_7;
                    ** GOTO lbl61
                }
                v7 = null;
lbl61:
                // 2 sources

                Intrinsics.checkNotNull(v7);
                var9_13.func_146284_a(v7);
                break;
            }
            case 2: {
                var3_5 = GuiScreen.func_146277_j();
                Intrinsics.checkNotNullExpressionValue(var3_5, "getClipboardString()");
                v8 = (CharSequence)var3_5;
                var3_5 = new String[]{":"};
                args = StringsKt.split$default(v8, (String[])var3_5, false, 0, 6, null);
                v9 = this.username;
                if (v9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("username");
                    v9 = null;
                }
                v9.func_146180_a((String)args.get(0));
                v10 = this.password;
                if (v10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("password");
                    v10 = null;
                }
                if ((v11 = (String)CollectionsKt.getOrNull(args, 1)) == null) {
                    v11 = "";
                }
                v10.func_146180_a(v11);
                var3_5 = this.field_146292_n;
                Intrinsics.checkNotNullExpressionValue(var3_5, "buttonList");
                var3_5 = (Iterable)var3_5;
                var9_14 = this;
                var5_8 = var3_5.iterator();
                while (var5_8.hasNext()) {
                    var6_10 = var5_8.next();
                    it = (GuiButton)var6_10;
                    $i$a$-find-GuiAdd$actionPerformed$3 = false;
                    if (!(it.field_146127_k == 1)) continue;
                    v12 = var6_10;
                    ** GOTO lbl95
                }
                v12 = null;
lbl95:
                // 2 sources

                Intrinsics.checkNotNull(v12);
                var9_14.func_146284_a(v12);
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
                    $i$a$-find-GuiAdd$keyTyped$1 = false;
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
}

