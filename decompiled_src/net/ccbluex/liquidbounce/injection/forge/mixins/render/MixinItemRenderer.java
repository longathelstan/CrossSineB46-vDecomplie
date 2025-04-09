/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.ccbluex.liquidbounce.features.module.modules.visual.NoRender;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemCarrotOnAStick;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemRenderer.class})
public abstract class MixinItemRenderer {
    @Shadow
    private float field_78451_d;
    @Shadow
    private float field_78454_c;
    @Shadow
    private int field_78450_g = -1;
    @Shadow
    @Final
    private Minecraft field_78455_a;
    @Shadow
    private ItemStack field_78453_b;
    @Shadow
    private RenderItem field_178112_h;
    private Animations animations;

    @Shadow
    protected abstract void func_178101_a(float var1, float var2);

    @Shadow
    protected abstract void func_178109_a(AbstractClientPlayer var1);

    @Shadow
    protected abstract void func_178110_a(EntityPlayerSP var1, float var2);

    @Shadow
    public abstract void func_178099_a(EntityLivingBase var1, ItemStack var2, ItemCameraTransforms.TransformType var3);

    @Shadow
    protected abstract void func_178097_a(AbstractClientPlayer var1, float var2, float var3, float var4);

    @Shadow
    protected abstract boolean func_178107_a(Block var1);

    @Shadow
    protected abstract void func_178104_a(AbstractClientPlayer var1, float var2);

    @Shadow
    protected abstract void func_178103_d();

    @Shadow
    protected abstract void func_178098_a(float var1, AbstractClientPlayer var2);

    @Shadow
    protected abstract void func_178105_d(float var1);

    @Shadow
    protected abstract void func_178095_a(AbstractClientPlayer var1, float var2, float var3);

    @Inject(method={"updateEquippedItem"}, at={@At(value="HEAD")}, cancellable=true)
    public void updateEquippedItemHead(CallbackInfo i) {
        this.field_78451_d = this.field_78454_c;
        ItemStack itemstack = SlotUtils.INSTANCE.getStack();
        boolean flag = false;
        if (this.field_78453_b != null && itemstack != null) {
            if (!this.field_78453_b.func_179549_c(itemstack)) {
                if (!this.field_78453_b.func_77973_b().shouldCauseReequipAnimation(this.field_78453_b, itemstack, this.field_78450_g != SlotUtils.INSTANCE.getSlot())) {
                    this.field_78453_b = itemstack;
                    this.field_78450_g = SlotUtils.INSTANCE.getSlot();
                    return;
                }
                flag = true;
            }
        } else {
            flag = this.field_78453_b != null || itemstack != null;
        }
        float f = 0.4f;
        float f1 = flag ? 0.0f : 1.0f;
        float f2 = MathHelper.func_76131_a((float)(f1 - this.field_78454_c), (float)(-f), (float)f);
        this.field_78454_c += f2;
        if (this.field_78454_c < 0.1f) {
            this.field_78453_b = itemstack;
            this.field_78450_g = SlotUtils.INSTANCE.getSlot();
        }
        i.cancel();
    }

    @Overwrite
    private void func_178096_b(float equipProgress, float swingProgress) {
        this.doItemRenderGLTranslate();
        GlStateManager.func_179109_b((float)0.0f, (float)(equipProgress * -0.6f), (float)0.0f);
        GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float f = MathHelper.func_76126_a((float)(swingProgress * swingProgress * (float)Math.PI));
        float f1 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)swingProgress) * (float)Math.PI));
        GlStateManager.func_179114_b((float)(f * -20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(f1 * -20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)(f1 * -80.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.doItemRenderGLScale();
    }

    private void oldBlockAnimation() {
        GlStateManager.func_179109_b((float)-0.5f, (float)0.4f, (float)-0.1f);
        GlStateManager.func_179114_b((float)30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)-80.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)60.0f, (float)0.0f, (float)1.0f, (float)0.0f);
    }

    @Overwrite
    public void func_78440_a(float partialTicks) {
        if (this.animations == null) {
            this.animations = CrossSine.moduleManager.getModule(Animations.class);
        }
        float f = 1.0f - (this.field_78451_d + (this.field_78454_c - this.field_78451_d) * partialTicks);
        EntityPlayerSP abstractclientplayer = this.field_78455_a.field_71439_g;
        float f1 = abstractclientplayer.func_70678_g(partialTicks);
        float f2 = abstractclientplayer.field_70127_C + (abstractclientplayer.field_70125_A - abstractclientplayer.field_70127_C) * partialTicks;
        float f3 = abstractclientplayer.field_70126_B + (abstractclientplayer.field_70177_z - abstractclientplayer.field_70126_B) * partialTicks;
        this.func_178101_a(f2, f3);
        this.func_178109_a((AbstractClientPlayer)abstractclientplayer);
        this.func_178110_a(abstractclientplayer, partialTicks);
        GlStateManager.func_179091_B();
        GlStateManager.func_179094_E();
        if (this.field_78453_b != null) {
            if (((Boolean)Animations.INSTANCE.getBlockAnimation().get()).booleanValue() && (this.field_78453_b.func_77973_b() instanceof ItemCarrotOnAStick || this.field_78453_b.func_77973_b() instanceof ItemFishingRod)) {
                GlStateManager.func_179109_b((float)0.08f, (float)-0.027f, (float)-0.33f);
                GlStateManager.func_179152_a((float)0.93f, (float)1.0f, (float)1.0f);
            }
            if (this.field_78453_b.func_77973_b() instanceof ItemMap) {
                this.func_178097_a((AbstractClientPlayer)abstractclientplayer, f2, f, f1);
            } else if (abstractclientplayer.func_71039_bw() || this.field_78453_b.func_77973_b() instanceof ItemSword && (KillAura.INSTANCE.getState() && KillAura.INSTANCE.getDisplayBlocking() && KillAura.INSTANCE.getCurrentTarget() != null || SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getCanBlock() && SilentAura.INSTANCE.getTarget() != null)) {
                block0 : switch (this.field_78453_b.func_77975_n()) {
                    case NONE: {
                        this.func_178096_b(f, 0.0f);
                        break;
                    }
                    case EAT: 
                    case DRINK: {
                        this.func_178104_a((AbstractClientPlayer)abstractclientplayer, partialTicks);
                        if (((Boolean)Animations.INSTANCE.getBlockAnimation().get()).booleanValue()) {
                            this.func_178096_b(f, f1);
                            break;
                        }
                        this.func_178096_b(f, 0.0f);
                        break;
                    }
                    case BLOCK: {
                        if (this.animations.getState()) {
                            switch ((String)this.animations.getBlockingModeValue().get()) {
                                case "1.7": {
                                    this.func_178096_b(f, f1);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Akrien": {
                                    this.func_178096_b(f1, 0.0f);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Avatar": {
                                    this.avatar(f1);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "ETB": {
                                    this.etb(f, f1);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Exhibition": {
                                    this.func_178096_b(f, 0.83f);
                                    float f4 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)f1) * 3.83f));
                                    GlStateManager.func_179109_b((float)-0.5f, (float)0.2f, (float)0.2f);
                                    GlStateManager.func_179114_b((float)(-f4 * 0.0f), (float)0.0f, (float)0.0f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)(-f4 * 43.0f), (float)58.0f, (float)23.0f, (float)45.0f);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Push": {
                                    this.push(f1);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Reverse": {
                                    this.func_178096_b(f1, f1);
                                    this.func_178103_d();
                                    GlStateManager.func_179114_b((float)0.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                                    break block0;
                                }
                                case "Shield": {
                                    this.jello(f1);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "SigmaNew": {
                                    this.doItemRenderGLTranslate();
                                    GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                                    float var11 = MathHelper.func_76126_a((float)(f1 * f1 * (float)Math.PI));
                                    float var12 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)f1) * (float)Math.PI));
                                    GlStateManager.func_179114_b((float)(var12 * -5.0f), (float)1.0f, (float)0.0f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)(var12 * 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                                    GlStateManager.func_179114_b((float)(var12 * 25.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                                    this.doItemRenderGLScale();
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "SigmaOld": {
                                    this.sigmaOld(f);
                                    float var15 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)f1) * (float)Math.PI));
                                    GlStateManager.func_179114_b((float)(-var15 * 55.0f / 2.0f), (float)-8.0f, (float)-0.0f, (float)9.0f);
                                    GlStateManager.func_179114_b((float)(-var15 * 45.0f), (float)1.0f, (float)(var15 / 2.0f), (float)-0.0f);
                                    this.func_178103_d();
                                    GL11.glTranslated((double)1.2, (double)0.3, (double)0.5);
                                    GL11.glTranslatef((float)-1.0f, (float)(this.field_78455_a.field_71439_g.func_70093_af() ? -0.1f : -0.2f), (float)0.2f);
                                    GlStateManager.func_179152_a((float)1.2f, (float)1.2f, (float)1.2f);
                                    break block0;
                                }
                                case "Slide": {
                                    this.slide(f1);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "SlideDown": {
                                    this.func_178096_b(0.2f, f1);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Swong": {
                                    this.func_178096_b(f / 2.0f, 0.0f);
                                    GlStateManager.func_179114_b((float)(-MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)f1) * (float)Math.PI)) * 40.0f / 2.0f), (float)(MathHelper.func_76129_c((float)f1) / 2.0f), (float)-0.0f, (float)9.0f);
                                    GlStateManager.func_179114_b((float)(-MathHelper.func_76129_c((float)f1) * 30.0f), (float)1.0f, (float)(MathHelper.func_76129_c((float)f1) / 2.0f), (float)-0.0f);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "VisionFX": {
                                    this.continuity(f1);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Swank": {
                                    GL11.glTranslated((double)-0.1, (double)0.15, (double)0.0);
                                    this.func_178096_b(f / 0.15f, f1);
                                    float rot = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)f1) * (float)Math.PI));
                                    GlStateManager.func_179114_b((float)(rot * 30.0f), (float)2.0f, (float)(-rot), (float)9.0f);
                                    GlStateManager.func_179114_b((float)(rot * 35.0f), (float)1.0f, (float)(-rot), (float)-0.0f);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Jello": {
                                    this.func_178096_b(0.0f, 0.0f);
                                    this.func_178103_d();
                                    int alpha = (int)Math.min(255L, (System.currentTimeMillis() % 255L > 127L ? Math.abs(Math.abs(System.currentTimeMillis()) % 255L - 255L) : System.currentTimeMillis() % 255L) * 2L);
                                    GlStateManager.func_179109_b((float)0.3f, (float)-0.0f, (float)0.4f);
                                    GlStateManager.func_179114_b((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                                    GlStateManager.func_179109_b((float)0.0f, (float)0.5f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)90.0f, (float)1.0f, (float)0.0f, (float)-1.0f);
                                    GlStateManager.func_179109_b((float)0.6f, (float)0.5f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)-90.0f, (float)1.0f, (float)0.0f, (float)-1.0f);
                                    GlStateManager.func_179114_b((float)-10.0f, (float)1.0f, (float)0.0f, (float)-1.0f);
                                    GlStateManager.func_179114_b((float)(abstractclientplayer.field_82175_bq ? (float)(-alpha) / 5.0f : 1.0f), (float)1.0f, (float)-0.0f, (float)1.0f);
                                    break block0;
                                }
                                case "HSlide": {
                                    this.func_178096_b(f1 != 0.0f ? Math.max(1.0f - f1 * 2.0f, 0.0f) * 0.7f : 0.0f, 1.0f);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "None": {
                                    this.func_178096_b(0.0f, 0.0f);
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Rotate": {
                                    this.rotateSword(f1);
                                    break block0;
                                }
                                case "Liquid": {
                                    this.func_178096_b(f + 0.1f, f1);
                                    this.func_178103_d();
                                    GlStateManager.func_179109_b((float)-0.5f, (float)0.2f, (float)0.0f);
                                    break block0;
                                }
                                case "Fall": {
                                    this.doItemRenderGLTranslate();
                                    GlStateManager.func_179109_b((float)0.0f, (float)(f * -0.6f), (float)0.0f);
                                    GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                                    this.doItemRenderGLScale();
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Yeet": {
                                    this.doItemRenderGLTranslate();
                                    GlStateManager.func_179109_b((float)0.0f, (float)(f * -0.6f), (float)0.0f);
                                    GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                                    float var11 = MathHelper.func_76126_a((float)(f1 * f1 * (float)Math.PI));
                                    float var12 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)f1) * (float)Math.PI));
                                    GlStateManager.func_179114_b((float)(var11 * 0.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)(var12 * 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                                    GlStateManager.func_179114_b((float)(var12 * -40.0f + 10.0f), (float)1.0f, (float)0.0f, (float)0.0f);
                                    this.doItemRenderGLScale();
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Yeet2": {
                                    this.doItemRenderGLTranslate();
                                    GlStateManager.func_179109_b((float)0.0f, (float)(f * -0.8f), (float)0.0f);
                                    GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                                    float var11 = MathHelper.func_76126_a((float)(f1 * f1 * (float)Math.PI));
                                    float var12 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)f1) * (float)Math.PI));
                                    GlStateManager.func_179114_b((float)(var11 * 0.0f), (float)0.0f, (float)1.0f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)(var12 * 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                                    GlStateManager.func_179114_b((float)(var12 * -20.0f - 9.5f), (float)1.0f, (float)0.0f, (float)0.0f);
                                    this.doItemRenderGLScale();
                                    this.func_178103_d();
                                    break block0;
                                }
                                case "Dortware": {
                                    float var9 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)f1) * (float)Math.PI));
                                    GL11.glTranslated((double)-0.04, (double)0.0, (double)0.0);
                                    this.func_178096_b(f / 2.5f, 0.0f);
                                    GlStateManager.func_179114_b((float)(-var9 * 0.0f / 2.0f), (float)(var9 / 2.0f), (float)1.0f, (float)4.0f);
                                    GlStateManager.func_179114_b((float)(-var9 * 120.0f), (float)1.0f, (float)(var9 / 3.0f), (float)-0.0f);
                                    GlStateManager.func_179109_b((float)-0.5f, (float)0.2f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)-80.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                                    GlStateManager.func_179114_b((float)60.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                                    break block0;
                                }
                            }
                            break;
                        }
                        this.func_178096_b(f + 0.1f, f1);
                        this.func_178103_d();
                        GlStateManager.func_179109_b((float)-0.5f, (float)0.2f, (float)0.0f);
                        break;
                    }
                    case BOW: {
                        if (((Boolean)Animations.INSTANCE.getBlockAnimation().get()).booleanValue()) {
                            this.func_178096_b(f, f1);
                        } else {
                            this.func_178096_b(f, 0.0f);
                        }
                        this.func_178098_a(partialTicks, (AbstractClientPlayer)abstractclientplayer);
                    }
                }
            } else {
                if (!this.animations.getState() || !((Boolean)this.animations.getFluxAnimation().get()).booleanValue()) {
                    this.func_178105_d(f1);
                }
                this.func_178096_b(f, f1);
            }
            this.func_178099_a((EntityLivingBase)abstractclientplayer, this.field_78453_b, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else if (!abstractclientplayer.func_82150_aj()) {
            this.func_178095_a((AbstractClientPlayer)abstractclientplayer, f, f1);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179101_C();
        RenderHelper.func_74518_a();
    }

    private void doItemRenderGLTranslate() {
        if (this.animations.getState()) {
            GlStateManager.func_179109_b((float)(0.56f + ((Float)this.animations.getItemPosXValue().get()).floatValue()), (float)(-0.52f + ((Float)this.animations.getItemPosYValue().get()).floatValue()), (float)(-0.71999997f + ((Float)this.animations.getItemPosZValue().get()).floatValue()));
        } else {
            GlStateManager.func_179109_b((float)0.56f, (float)-0.52f, (float)-0.71999997f);
        }
    }

    private void doItemRenderGLScale() {
        if (this.animations.getState()) {
            GlStateManager.func_179139_a((double)((double)((float)((Integer)Animations.INSTANCE.getItemScaleValue().get()).intValue() / 100.0f) * 0.4), (double)((double)((float)((Integer)Animations.INSTANCE.getItemScaleValue().get()).intValue() / 100.0f) * 0.4), (double)((double)((float)((Integer)Animations.INSTANCE.getItemScaleValue().get()).intValue() / 100.0f) * 0.4));
        } else {
            GlStateManager.func_179139_a((double)0.4, (double)0.4, (double)0.4);
        }
    }

    private void sigmaOld(float f) {
        this.doItemRenderGLTranslate();
        GlStateManager.func_179109_b((float)0.0f, (float)(f * -0.6f), (float)0.0f);
        GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)0.0f, (float)0.0f, (float)1.0f, (float)0.2f);
        GlStateManager.func_179114_b((float)0.0f, (float)0.2f, (float)0.1f, (float)1.0f);
        GlStateManager.func_179114_b((float)0.0f, (float)1.3f, (float)0.1f, (float)0.2f);
        this.doItemRenderGLScale();
    }

    private void avatar(float swingProgress) {
        this.doItemRenderGLTranslate();
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float f = MathHelper.func_76126_a((float)(swingProgress * swingProgress * (float)Math.PI));
        float f2 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)swingProgress) * (float)Math.PI));
        GlStateManager.func_179114_b((float)(f * -20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(f2 * -20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)(f2 * -40.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.doItemRenderGLScale();
    }

    private void slide(float var9) {
        this.doItemRenderGLTranslate();
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float var11 = MathHelper.func_76126_a((float)(var9 * var9 * (float)Math.PI));
        float var12 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)var9) * (float)Math.PI));
        GlStateManager.func_179114_b((float)(var11 * 0.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(var12 * 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)(var12 * -40.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.doItemRenderGLScale();
    }

    private void rotateSword(float f1) {
        this.genCustom();
        this.func_178103_d();
        GlStateManager.func_179109_b((float)-0.5f, (float)0.2f, (float)0.0f);
        GlStateManager.func_179114_b((float)(MathHelper.func_76129_c((float)f1) * 10.0f * 40.0f), (float)1.0f, (float)-0.0f, (float)2.0f);
    }

    private void genCustom() {
        GlStateManager.func_179109_b((float)0.56f, (float)-0.52f, (float)-0.71999997f);
        GlStateManager.func_179109_b((float)0.0f, (float)-0.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float var3 = MathHelper.func_76126_a((float)0.0f);
        float var4 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)0.0f) * (float)Math.PI));
        GlStateManager.func_179114_b((float)(var3 * -34.0f), (float)0.0f, (float)1.0f, (float)0.2f);
        GlStateManager.func_179114_b((float)(var4 * -20.7f), (float)0.2f, (float)0.1f, (float)1.0f);
        GlStateManager.func_179114_b((float)(var4 * -68.6f), (float)1.3f, (float)0.1f, (float)0.2f);
        GlStateManager.func_179152_a((float)0.4f, (float)0.4f, (float)0.4f);
    }

    private void jello(float var12) {
        this.doItemRenderGLTranslate();
        GlStateManager.func_179114_b((float)48.57f, (float)0.0f, (float)0.24f, (float)0.14f);
        float var13 = MathHelper.func_76126_a((float)(var12 * var12 * (float)Math.PI));
        float var14 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)var12) * (float)Math.PI));
        GlStateManager.func_179114_b((float)(var13 * -35.0f), (float)0.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(var14 * 0.0f), (float)0.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(var14 * 20.0f), (float)1.0f, (float)1.0f, (float)1.0f);
        this.doItemRenderGLScale();
    }

    private void continuity(float var10) {
        this.doItemRenderGLTranslate();
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float var12 = -MathHelper.func_76126_a((float)(var10 * var10 * (float)Math.PI));
        float var13 = MathHelper.func_76134_b((float)(MathHelper.func_76129_c((float)var10) * (float)Math.PI));
        float var14 = MathHelper.func_76135_e((float)(MathHelper.func_76129_c((float)0.1f) * (float)Math.PI));
        GlStateManager.func_179114_b((float)(var12 * var14 * 30.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(var13 * 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)(var13 * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        this.doItemRenderGLScale();
    }

    private void etb(float equipProgress, float swingProgress) {
        this.doItemRenderGLTranslate();
        GlStateManager.func_179109_b((float)0.0f, (float)(equipProgress * -0.6f), (float)0.0f);
        GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float var3 = MathHelper.func_76126_a((float)(swingProgress * swingProgress * (float)Math.PI));
        float var4 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)swingProgress) * (float)Math.PI));
        GlStateManager.func_179114_b((float)(var3 * -34.0f), (float)0.0f, (float)1.0f, (float)0.2f);
        GlStateManager.func_179114_b((float)(var4 * -20.7f), (float)0.2f, (float)0.1f, (float)1.0f);
        GlStateManager.func_179114_b((float)(var4 * -68.6f), (float)1.3f, (float)0.1f, (float)0.2f);
        this.doItemRenderGLScale();
    }

    private void push(float idc) {
        this.doItemRenderGLTranslate();
        GlStateManager.func_179109_b((float)0.0f, (float)-0.060000002f, (float)0.0f);
        GlStateManager.func_179114_b((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float var3 = MathHelper.func_76126_a((float)(idc * idc * (float)Math.PI));
        float var4 = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)idc) * (float)Math.PI));
        GlStateManager.func_179114_b((float)(var3 * -10.0f), (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)(var4 * -10.0f), (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)(var4 * -10.0f), (float)1.0f, (float)1.0f, (float)1.0f);
        this.doItemRenderGLScale();
    }

    @Inject(method={"renderFireInFirstPerson"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderFireInFirstPerson(CallbackInfo callbackInfo) {
        NoRender NoRender2 = CrossSine.moduleManager.getModule(NoRender.class);
        if (NoRender2.getState() && ((Boolean)NoRender2.getFireEffect().get()).booleanValue()) {
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)0.9f);
            GlStateManager.func_179143_c((int)519);
            GlStateManager.func_179132_a((boolean)false);
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179084_k();
            GlStateManager.func_179132_a((boolean)true);
            GlStateManager.func_179143_c((int)515);
            callbackInfo.cancel();
        }
    }
}

