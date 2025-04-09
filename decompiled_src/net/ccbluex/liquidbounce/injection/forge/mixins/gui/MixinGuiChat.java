/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.client.GuiChatModule;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.client.KeyStrokes;
import net.ccbluex.liquidbounce.features.module.modules.client.ScoreboardModule;
import net.ccbluex.liquidbounce.features.module.modules.client.SessionInfo;
import net.ccbluex.liquidbounce.features.module.modules.client.TargetHUD;
import net.ccbluex.liquidbounce.features.module.modules.client.impl.Notification;
import net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiChat.class})
public abstract class MixinGuiChat
extends MixinGuiScreen {
    @Shadow
    protected GuiTextField field_146415_a;
    @Shadow
    private List<String> field_146412_t;
    @Shadow
    private boolean field_146414_r;
    @Shadow
    private boolean field_146417_i;
    @Shadow
    private int field_146416_h;
    @Shadow
    private String field_146410_g;
    private float yPosOfInputField;
    private float fade = 0.0f;
    final GuiChatModule guiChatModule = CrossSine.moduleManager.getModule(GuiChatModule.class);

    @Shadow
    public abstract void func_146406_a(String[] var1);

    @Shadow
    public abstract void func_146404_p_();

    @Overwrite
    protected void func_73869_a(char p_keyTyped_1_, int p_keyTyped_2_) throws IOException {
        this.field_146414_r = false;
        if (p_keyTyped_2_ == 15) {
            this.func_146404_p_();
        } else {
            this.field_146417_i = false;
        }
        if (p_keyTyped_2_ == 1) {
            this.field_146297_k.func_147108_a(null);
        } else if (p_keyTyped_2_ != 28 && p_keyTyped_2_ != 156) {
            if (p_keyTyped_2_ == 200) {
                this.func_146402_a(-1);
            } else if (p_keyTyped_2_ == 208) {
                this.func_146402_a(1);
            } else if (p_keyTyped_2_ == 201) {
                this.field_146297_k.field_71456_v.func_146158_b().func_146229_b(this.field_146297_k.field_71456_v.func_146158_b().func_146232_i() - 1);
            } else if (p_keyTyped_2_ == 209) {
                this.field_146297_k.field_71456_v.func_146158_b().func_146229_b(-this.field_146297_k.field_71456_v.func_146158_b().func_146232_i() + 1);
            } else {
                this.field_146415_a.func_146201_a(p_keyTyped_1_, p_keyTyped_2_);
            }
        } else {
            String s = this.field_146415_a.func_146179_b().trim();
            if (s.length() > 0) {
                this.func_175275_f(s);
            }
            this.field_146297_k.func_147108_a(null);
        }
    }

    @Overwrite
    public void func_146402_a(int p_getSentHistory_1_) {
        int i = this.field_146416_h + p_getSentHistory_1_;
        int j = this.field_146297_k.field_71456_v.func_146158_b().func_146238_c().size();
        if ((i = MathHelper.func_76125_a((int)i, (int)0, (int)j)) != this.field_146416_h) {
            if (i == j) {
                this.field_146416_h = j;
                this.setText(this.field_146410_g);
            } else {
                if (this.field_146416_h == j) {
                    this.field_146410_g = this.field_146415_a.func_146179_b();
                }
                this.setText((String)this.field_146297_k.field_71456_v.func_146158_b().func_146238_c().get(i));
                this.field_146416_h = i;
            }
        }
    }

    private void setText(String text) {
        if (text.startsWith(String.valueOf(CrossSine.commandManager.getPrefix()))) {
            this.field_146415_a.func_146203_f(114514);
        } else if (this.guiChatModule.getState() && ((Boolean)this.guiChatModule.getChatLimitValue().get()).booleanValue()) {
            this.field_146415_a.func_146203_f(114514);
        } else {
            this.field_146415_a.func_146203_f(100);
        }
        this.field_146415_a.func_146180_a(text);
    }

    @Inject(method={"initGui"}, at={@At(value="RETURN")})
    private void init(CallbackInfo callbackInfo) {
        this.field_146415_a.field_146210_g = this.field_146295_m - 5;
        this.yPosOfInputField = this.field_146415_a.field_146210_g;
    }

    @Inject(method={"keyTyped"}, at={@At(value="HEAD")}, cancellable=true)
    private void keyTyped(char typedChar, int keyCode, CallbackInfo callbackInfo) {
        String text = this.field_146415_a.func_146179_b();
        if (text.startsWith(String.valueOf(CrossSine.commandManager.getPrefix()))) {
            this.field_146415_a.func_146203_f(114514);
            if (keyCode == 28 || keyCode == 156) {
                CrossSine.commandManager.executeCommands(text);
                callbackInfo.cancel();
                this.field_146297_k.field_71456_v.func_146158_b().func_146239_a(text);
                if (this.field_146297_k.field_71462_r instanceof GuiChat) {
                    Minecraft.func_71410_x().func_147108_a(null);
                }
            } else {
                CrossSine.commandManager.autoComplete(text);
            }
        } else {
            this.field_146415_a.func_146203_f(100);
        }
    }

    @Inject(method={"setText"}, at={@At(value="HEAD")}, cancellable=true)
    private void setText(String newChatText, boolean shouldOverwrite, CallbackInfo callbackInfo) {
        if (shouldOverwrite && newChatText.startsWith(String.valueOf(CrossSine.commandManager.getPrefix()))) {
            this.setText(CrossSine.commandManager.getPrefix() + "say " + newChatText);
            callbackInfo.cancel();
        }
    }

    @Inject(method={"updateScreen"}, at={@At(value="HEAD")})
    private void updateScreen(CallbackInfo callbackInfo) {
        int delta = RenderUtils.deltaTime;
        if (this.fade < 14.0f) {
            this.fade += 0.4f * (float)delta;
        }
        if (this.fade > 14.0f) {
            this.fade = 14.0f;
        }
        if (this.yPosOfInputField > (float)(this.field_146295_m - 12)) {
            this.yPosOfInputField -= 0.4f * (float)delta;
        }
        if (this.yPosOfInputField < (float)(this.field_146295_m - 12)) {
            this.yPosOfInputField = this.field_146295_m - 12;
        }
        this.field_146415_a.field_146210_g = (int)this.yPosOfInputField - 1;
    }

    @Inject(method={"autocompletePlayerNames"}, at={@At(value="HEAD")})
    private void prioritizeClientFriends(CallbackInfo callbackInfo) {
        this.field_146412_t.sort(Comparator.comparing(s -> !CrossSine.fileManager.getFriendsConfig().isFriend((String)s)));
    }

    @Inject(method={"sendAutocompleteRequest"}, at={@At(value="HEAD")}, cancellable=true)
    private void handleClientCommandCompletion(String full, String ignored, CallbackInfo callbackInfo) {
        if (CrossSine.commandManager.autoComplete(full)) {
            this.field_146414_r = true;
            String[] latestAutoComplete = CrossSine.commandManager.getLatestAutoComplete();
            if (full.toLowerCase().endsWith(latestAutoComplete[latestAutoComplete.length - 1].toLowerCase())) {
                return;
            }
            this.func_146406_a(latestAutoComplete);
            callbackInfo.cancel();
        }
    }

    private void onAutocompleteResponse(String[] autoCompleteResponse, CallbackInfo callbackInfo) {
        if (CrossSine.commandManager.getLatestAutoComplete().length != 0) {
            callbackInfo.cancel();
        }
    }

    public void draw() {
    }

    @Inject(method={"drawScreen"}, at={@At(value="HEAD")}, cancellable=true)
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        IChatComponent ichatcomponent;
        long time = System.currentTimeMillis();
        RenderUtils.drawBloomRoundedRect(2.0f, (float)this.field_146295_m - 14.0f, (float)this.field_146294_l - 2.0f, (float)this.field_146295_m - 2.0f, 3.0f, 3.0f, new Color(0, 0, 0, 80), RenderUtils.ShaderBloom.BOTH);
        this.field_146415_a.func_146194_f();
        if (CrossSine.commandManager.getLatestAutoComplete().length > 0 && !this.field_146415_a.func_146179_b().isEmpty() && this.field_146415_a.func_146179_b().startsWith(String.valueOf(CrossSine.commandManager.getPrefix()))) {
            String[] latestAutoComplete = CrossSine.commandManager.getLatestAutoComplete();
            String[] textArray = this.field_146415_a.func_146179_b().split(" ");
            String text = textArray[textArray.length - 1];
            Object[] result = Arrays.stream(latestAutoComplete).filter(str -> str.toLowerCase().startsWith(text.toLowerCase())).toArray();
            String resultText = "";
            if (result.length > 0) {
                resultText = ((String)result[0]).substring(Math.min(((String)result[0]).length(), text.length()));
            }
            this.field_146297_k.field_71466_p.func_175063_a(resultText, 5.5f + (float)this.field_146415_a.field_146209_f + (float)this.field_146297_k.field_71466_p.func_78256_a(this.field_146415_a.func_146179_b()), (float)this.field_146415_a.field_146210_g + 2.0f, new Color(165, 165, 165).getRGB());
        }
        if ((ichatcomponent = this.field_146297_k.field_71456_v.func_146158_b().func_146236_a(Mouse.getX(), Mouse.getY())) != null) {
            this.func_175272_a(ichatcomponent, mouseX, mouseY);
        }
        if (((Boolean)Interface.INSTANCE.getNotification().get()).booleanValue()) {
            Notification.INSTANCE.draw();
        }
        ci.cancel();
    }

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")})
    protected void mouseClicked(int p_mouseClicked_1_, int p_mouseClicked_2_, int p_mouseClicked_3_, CallbackInfo callbackInfo) throws IOException {
        ScoreboardModule sb = ScoreboardModule.INSTANCE;
        TargetHUD th = TargetHUD.INSTANCE;
        KeyStrokes ks = KeyStrokes.INSTANCE;
        SessionInfo si = SessionInfo.INSTANCE;
        if (p_mouseClicked_3_ == 0) {
            if (MouseUtils.mouseWithinBounds(p_mouseClicked_1_, p_mouseClicked_2_, sb.getUx_size(), sb.getUy_size(), sb.getUx2_size(), sb.getUy2_size())) {
                if (!sb.getDraging()) {
                    sb.setDraging(true);
                }
                sb.setDragOffsetX((float)p_mouseClicked_1_ - sb.getPosX());
                sb.setDragOffsetY((float)p_mouseClicked_2_ - sb.getPosY());
            }
            if (MouseUtils.mouseWithinBounds(p_mouseClicked_1_, p_mouseClicked_2_, th.getUx_size(), th.getUy_size(), th.getUx2_size(), th.getUy2_size())) {
                if (!th.getDraging()) {
                    th.setDraging(true);
                }
                th.setDragOffsetX((float)p_mouseClicked_1_ - th.getPosX());
                th.setDragOffsetY((float)p_mouseClicked_2_ - th.getPosY());
            }
            if (MouseUtils.mouseWithinBounds(p_mouseClicked_1_, p_mouseClicked_2_, ks.getUx_size(), ks.getUy_size(), ks.getUx2_size(), ks.getUy2_size())) {
                if (!ks.getDraging()) {
                    ks.setDraging(true);
                }
                ks.setDragOffsetX((float)p_mouseClicked_1_ - ks.getPosX());
                ks.setDragOffsetY((float)p_mouseClicked_2_ - ks.getPosY());
            }
            if (MouseUtils.mouseWithinBounds(p_mouseClicked_1_, p_mouseClicked_2_, si.getUx_size(), si.getUy_size(), si.getUx2_size(), si.getUy2_size())) {
                if (!si.getDraging()) {
                    si.setDraging(true);
                }
                si.setDragOffsetX((float)p_mouseClicked_1_ - si.getPosX());
                si.setDragOffsetY((float)p_mouseClicked_2_ - si.getPosY());
            }
        }
    }

    @Inject(method={"onGuiClosed"}, at={@At(value="HEAD")}, cancellable=true)
    public void onGuiClosed(CallbackInfo ci) {
        ScoreboardModule sb = ScoreboardModule.INSTANCE;
        TargetHUD th = TargetHUD.INSTANCE;
        KeyStrokes ks = KeyStrokes.INSTANCE;
        SessionInfo si = SessionInfo.INSTANCE;
        sb.setDraging(false);
        th.setDraging(false);
        ks.setDraging(false);
        si.setDraging(false);
    }
}

