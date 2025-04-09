/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.player.InvManager;
import net.ccbluex.liquidbounce.features.module.modules.world.Stealer;
import net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.extensions.RendererExtensionKt;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiContainer.class})
public abstract class MixinGuiContainer
extends MixinGuiScreen {
    @Shadow
    protected int field_146999_f;
    @Shadow
    protected int field_147000_g;
    @Shadow
    protected int field_147003_i;
    @Shadow
    public Container field_147002_h;
    @Shadow
    protected int field_147009_r;
    private long guiOpenTime = -1L;
    private boolean translated = false;
    @Shadow
    private int field_146988_G;
    @Shadow
    private int field_146996_I;

    @Shadow
    protected abstract boolean func_146983_a(int var1);

    @Inject(method={"initGui"}, at={@At(value="RETURN")})
    private void initGuiReturn(CallbackInfo callbackInfo) {
        this.guiOpenTime = System.currentTimeMillis();
        this.field_147003_i = (this.field_146294_l - this.field_146999_f) / 2;
        this.field_147009_r = (this.field_146295_m - this.field_147000_g) / 2;
    }

    @Overwrite
    public void func_146281_b() {
        if (this.field_146297_k.field_71439_g != null) {
            this.field_147002_h.func_75134_a((EntityPlayer)this.field_146297_k.field_71439_g);
            InvManager.INSTANCE.setCurrentSlot(-1);
            Stealer.INSTANCE.setCurrentSlot(-1);
        }
    }

    @Inject(method={"drawScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void drawScreenHead(CallbackInfo callbackInfo) {
        if (!CrossSine.INSTANCE.getDestruced()) {
            Stealer stealer = CrossSine.moduleManager.getModule(Stealer.class);
            Minecraft mc = Minecraft.func_71410_x();
            GuiScreen guiScreen = mc.field_71462_r;
            if (stealer.getState() && stealer.getFreelookValue().get().booleanValue() && guiScreen instanceof GuiChest && !((Boolean)stealer.getSilentValue().get()).booleanValue()) {
                mc.field_71415_G = true;
                mc.field_71417_B.func_74372_a();
            }
            if (stealer.getState() && ((Boolean)stealer.getSilentValue().get()).booleanValue() && guiScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest)guiScreen;
                if (!((Boolean)stealer.getChestTitleValue().get()).booleanValue() || chest.field_147015_w != null && chest.field_147015_w.func_70005_c_().contains(new ItemStack((Item)Item.field_150901_e.func_82594_a((Object)new ResourceLocation("minecraft:chest"))).func_82833_r())) {
                    mc.func_71381_h();
                    mc.field_71462_r = guiScreen;
                    if (stealer.getSilentTitleValue().get().booleanValue() && ((Boolean)stealer.getSilentValue().get()).booleanValue()) {
                        RendererExtensionKt.drawCenteredString(Fonts.fontSFUI35, "ChestStealer Silent", this.field_146294_l / 2, this.field_146295_m / 2 + 30, -1, true);
                    }
                    callbackInfo.cancel();
                }
            } else {
                double pct;
                mc.field_71462_r.func_146270_b(0);
                if (((Boolean)Interface.INSTANCE.getInventoryAnimation().get()).booleanValue() && Interface.INSTANCE.getState() && guiScreen instanceof GuiInventory && (pct = (double)Math.max(300L - (System.currentTimeMillis() - this.guiOpenTime), 0L) / 300.0) != 0.0) {
                    GL11.glPushMatrix();
                    pct = EaseUtils.INSTANCE.easeInCirc(pct);
                    double scale = 1.0 - pct;
                    GL11.glScaled((double)scale, (double)scale, (double)scale);
                    GL11.glTranslated((double)(((double)this.field_147003_i + (double)this.field_146999_f * 0.5 * pct) / scale - (double)this.field_147003_i), (double)(((double)this.field_147009_r + (double)this.field_147000_g * 0.5 * pct) / scale - (double)this.field_147009_r), (double)0.0);
                    this.translated = true;
                }
            }
        }
    }

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void checkCloseClick(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        if (!CrossSine.INSTANCE.getDestruced() && mouseButton - 100 == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
            this.field_146297_k.field_71439_g.func_71053_j();
            ci.cancel();
        }
    }

    @Inject(method={"mouseClicked"}, at={@At(value="TAIL")})
    private void checkHotbarClicks(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        this.func_146983_a(mouseButton - 100);
    }

    @Inject(method={"updateDragSplitting"}, at={@At(value="INVOKE", target="Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;")}, cancellable=true)
    private void fixRemnants(CallbackInfo ci) {
        if (this.field_146988_G == 2) {
            this.field_146996_I = this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_77976_d();
            ci.cancel();
        }
    }

    @Inject(method={"drawScreen"}, at={@At(value="RETURN")})
    private void drawScreenReturn(CallbackInfo callbackInfo) {
        if (this.translated) {
            GL11.glPopMatrix();
            this.translated = false;
        }
    }

    @Inject(method={"keyTyped"}, at={@At(value="HEAD")})
    private void keyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        Stealer stealer = CrossSine.moduleManager.getModule(Stealer.class);
        try {
            if (stealer.getState() && this.field_146297_k.field_71462_r instanceof GuiChest) {
                CrossSine.eventManager.callEvent(new KeyEvent(keyCode == 0 ? typedChar + 256 : keyCode));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Inject(method={"drawSlot"}, at={@At(value="HEAD")})
    private void drawSlot(Slot slot, CallbackInfo ci) {
        InvManager invManager = InvManager.INSTANCE;
        Stealer stealer = Stealer.INSTANCE;
        int x = slot.field_75223_e;
        int y = slot.field_75221_f;
        Color color0 = new Color(80, 80, 80, 255);
        int currentSlotChestStealer = stealer.getCurrentSlot();
        int currentSlotInvCleaner = invManager.getCurrentSlot();
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)8192);
        GL11.glDisable((int)2896);
        if (this.field_146297_k.field_71462_r instanceof GuiChest && stealer.getState() && !((Boolean)stealer.getSilentValue().get()).booleanValue() && slot.field_75222_d == currentSlotChestStealer && currentSlotChestStealer != -1) {
            RenderUtils.drawBloomRoundedRect(x, y, x + 16, y + 16, 3.0f, 2.5f, color0, RenderUtils.ShaderBloom.BOTH);
        }
        if (this.field_146297_k.field_71462_r instanceof GuiInventory && invManager.getState() && slot.field_75222_d == currentSlotInvCleaner && currentSlotInvCleaner != -1) {
            RenderUtils.drawBloomRoundedRect(x, y, x + 16, y + 16, 3.0f, 2.5f, color0, RenderUtils.ShaderBloom.BOTH);
        }
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
}

