/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiTextField.class})
public abstract class MixinGuiTextField {
    @Shadow
    public int field_175208_g;
    @Shadow
    public FontRenderer field_146211_a;
    @Shadow
    public int field_146209_f;
    @Shadow
    public int field_146210_g;
    @Shadow
    public int field_146218_h;
    @Shadow
    public int field_146219_i;
    @Shadow
    private String field_146216_j = "";
    @Shadow
    private int field_146217_k = 32;
    @Shadow
    private int field_146214_l;
    @Shadow
    private boolean field_146215_m = true;
    @Shadow
    private boolean field_146212_n = true;
    @Shadow
    private boolean field_146213_o;
    @Shadow
    private boolean field_146226_p = true;
    @Shadow
    private int field_146225_q;
    @Shadow
    private int field_146224_r;
    @Shadow
    private int field_146223_s;
    private int enabledColor = 0xE0E0E0;
    @Shadow
    private int field_146221_u = 0x707070;

    @Shadow
    public abstract boolean func_146176_q();

    @Shadow
    public abstract boolean func_146181_i();

    @Shadow
    public abstract int func_146200_o();

    @Shadow
    public abstract int func_146208_g();

    @Shadow
    public abstract void func_146188_c(int var1, int var2, int var3, int var4);

    @Inject(method={"drawTextBox"}, at={@At(value="HEAD")}, cancellable=true)
    public void drawTextBox(CallbackInfo ci) {
        if (this.func_146176_q()) {
            if (this.func_146181_i()) {
                RenderUtils.drawRoundedCornerRect(this.field_146209_f - 1, this.field_146210_g - 1, this.field_146209_f + this.field_146218_h + 1, this.field_146210_g + this.field_146219_i + 1, 2.0f, new Color(255, 255, 255, 50).getRGB());
                RenderUtils.drawRoundedCornerRect(this.field_146209_f, this.field_146210_g, this.field_146209_f + this.field_146218_h, this.field_146210_g + this.field_146219_i, 2.0f, new Color(0, 0, 0, 200).getRGB());
            }
            int i = this.field_146226_p ? this.enabledColor : this.field_146221_u;
            int j = this.field_146224_r - this.field_146225_q;
            int k = this.field_146223_s - this.field_146225_q;
            String s = this.field_146211_a.func_78269_a(this.field_146216_j.substring(this.field_146225_q), this.func_146200_o());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.field_146213_o && this.field_146214_l / 6 % 2 == 0 && flag;
            int l = this.field_146215_m ? this.field_146209_f + 4 : this.field_146209_f;
            int i1 = this.field_146215_m ? this.field_146210_g + (this.field_146219_i - 8) / 2 : this.field_146210_g;
            int j1 = l;
            if (k > s.length()) {
                k = s.length();
            }
            if (s.length() > 0) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = this.field_146211_a.func_175063_a(s1, (float)l + 5.0f, (float)i1 + 2.0f, i);
            }
            boolean flag2 = this.field_146224_r < this.field_146216_j.length() || this.field_146216_j.length() >= this.func_146208_g();
            int k1 = j1;
            if (!flag) {
                k1 = j > 0 ? l + this.field_146218_h : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }
            if (s.length() > 0 && flag && j < s.length()) {
                j1 = this.field_146211_a.func_175063_a(s.substring(j), (float)j1 + 5.0f, (float)i1 + 2.0f, i);
            }
            if (flag1) {
                if (flag2) {
                    RenderUtils.drawRoundedCornerRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.field_146211_a.field_78288_b, 3.0f, -3092272);
                } else {
                    this.field_146211_a.func_175063_a("_", (float)k1 + 2.0f, (float)i1 + 2.0f, i);
                }
            }
            if (k != j) {
                int l1 = l + this.field_146211_a.func_78256_a(s.substring(0, k));
                this.func_146188_c(k1, i1 - 1, l1 - 1, i1 + 1 + this.field_146211_a.field_78288_b);
            }
        }
        ci.cancel();
    }
}

