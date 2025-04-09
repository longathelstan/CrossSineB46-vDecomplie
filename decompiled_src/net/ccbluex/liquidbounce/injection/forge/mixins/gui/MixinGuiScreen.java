/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.Collections;
import java.util.List;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.client.impl.Notification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiScreen.class})
public abstract class MixinGuiScreen {
    @Shadow
    public Minecraft field_146297_k;
    @Shadow
    protected List<GuiButton> field_146292_n;
    @Shadow
    public int field_146294_l;
    @Shadow
    public int field_146295_m;
    @Shadow
    protected FontRenderer field_146289_q;

    @Shadow
    public abstract void func_73876_c();

    @Shadow
    public abstract void func_175272_a(IChatComponent var1, int var2, int var3);

    @Shadow
    protected abstract void func_146283_a(List<String> var1, int var2, int var3);

    @Shadow
    protected abstract void func_175275_f(String var1);

    @Shadow
    protected abstract void func_146284_a(GuiButton var1);

    @Shadow
    public abstract void func_73863_a(int var1, int var2, float var3);

    @Inject(method={"drawScreen"}, at={@At(value="RETURN")})
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (((Boolean)Interface.INSTANCE.getNotification().get()).booleanValue()) {
            Notification.INSTANCE.draw();
        }
    }

    @ModifyVariable(method={"sendChatMessage(Ljava/lang/String;)V"}, at=@At(value="HEAD"))
    private String mosendChatMessage(String p_sendChatMessage_1_) {
        if (p_sendChatMessage_1_.length() > 100) {
            return p_sendChatMessage_1_.substring(0, 100);
        }
        return p_sendChatMessage_1_;
    }

    @Inject(method={"drawDefaultBackground"}, at={@At(value="HEAD")}, cancellable=true)
    private void drawDefaultBackground(CallbackInfo callbackInfo) {
        if (this.field_146297_k.field_71462_r instanceof GuiContainer) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"handleComponentHover"}, at={@At(value="HEAD")})
    private void handleHoverOverComponent(IChatComponent component, int x, int y, CallbackInfo callbackInfo) {
        if (component == null || component.func_150256_b().func_150235_h() == null) {
            return;
        }
        ChatStyle chatStyle = component.func_150256_b();
        ClickEvent clickEvent = chatStyle.func_150235_h();
        HoverEvent hoverEvent = chatStyle.func_150210_i();
        this.func_146283_a(Collections.singletonList("\u00a7c\u00a7l" + clickEvent.func_150669_a().func_150673_b().toUpperCase() + ": \u00a7a" + clickEvent.func_150668_b()), x, y - (hoverEvent != null ? 17 : 0));
    }

    @Inject(method={"drawHoveringText*"}, at={@At(value="HEAD")})
    private void drawHoveringText(CallbackInfo ci) {
        GlStateManager.func_179145_e();
        GlStateManager.func_179126_j();
        RenderHelper.func_74519_b();
        GlStateManager.func_179091_B();
    }

    protected abstract void injectedActionPerformed(GuiButton var1);
}

