/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import java.awt.Color;
import java.io.IOException;
import java.util.UUID;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

public class SessionGui
extends GuiScreen {
    private GuiScreen previousScreen;
    private String status = "Session:";
    private GuiTextField sessionField;
    private ScaledResolution sr;

    public SessionGui(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    public void func_73866_w_() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.sr = new ScaledResolution(this.field_146297_k);
        this.sessionField = new GuiTextField(1, this.field_146297_k.field_71466_p, this.sr.func_78326_a() / 2 - 100, this.sr.func_78328_b() / 2, 200, 20);
        this.sessionField.func_146203_f(Short.MAX_VALUE);
        this.sessionField.func_146195_b(true);
        this.field_146292_n.add(new GuiButton(998, this.sr.func_78326_a() / 2 - 100, this.sr.func_78328_b() / 2 + 30, 200, 20, "Login"));
        this.field_146292_n.add(new GuiButton(999, this.sr.func_78326_a() / 2 - 100, this.sr.func_78328_b() / 2 + 60, 200, 20, "Restore"));
        super.func_73866_w_();
    }

    public void func_146281_b() {
        Keyboard.enableRepeatEvents((boolean)false);
        super.func_146281_b();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.func_146276_q_();
        this.field_146297_k.field_71466_p.func_78276_b(this.status, this.sr.func_78326_a() / 2 - this.field_146297_k.field_71466_p.func_78256_a(this.status) / 2, this.sr.func_78328_b() / 2 - 30, Color.WHITE.getRGB());
        this.sessionField.func_146194_f();
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    protected void func_146284_a(GuiButton button) throws IOException {
        if (button.field_146127_k == 998) {
            try {
                String[] args2 = this.sessionField.func_146179_b().split(":");
                String name = args2[0];
                UUID uuid = UUID.fromString(args2[1].replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
                String token = args2[2];
                try {
                    this.field_146297_k.func_152347_ac().joinServer(new GameProfile(uuid, name), token, uuid.toString());
                }
                catch (AuthenticationUnavailableException var7) {
                    this.status = "\u00a7cError: Authentication unavailable";
                    return;
                }
                catch (InvalidCredentialsException var8) {
                    this.status = "\u00a7cError: Invalid credentials";
                    return;
                }
                catch (AuthenticationException authenticationexception) {
                    this.status = "\u00a7cError: Authentication failed";
                    return;
                }
                this.field_146297_k.field_71449_j = new Session(args2[0], args2[1], args2[2], "mojang");
                this.field_146297_k.func_147108_a(this.previousScreen);
                return;
            }
            catch (Exception e) {
                this.status = "\u00a7cError: Couldn't set session     " + e.getMessage();
                e.printStackTrace();
            }
        }
        if (button.field_146127_k == 999) {
            try {
                this.field_146297_k.field_71449_j = GuiAltManager.Companion.getOriginalSession();
                this.field_146297_k.func_147108_a(this.previousScreen);
                return;
            }
            catch (Exception e) {
                this.status = "\u00a7cError: Couldn't restore session";
            }
        }
        super.func_146284_a(button);
    }

    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        this.sessionField.func_146201_a(typedChar, keyCode);
        if (1 == keyCode) {
            this.field_146297_k.func_147108_a(this.previousScreen);
        } else {
            super.func_73869_a(typedChar, keyCode);
        }
    }
}

