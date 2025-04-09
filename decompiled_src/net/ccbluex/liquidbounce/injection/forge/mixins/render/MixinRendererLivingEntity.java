/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.Chams;
import net.ccbluex.liquidbounce.features.module.modules.visual.HitColor;
import net.ccbluex.liquidbounce.features.module.modules.visual.NameTags;
import net.ccbluex.liquidbounce.features.module.modules.visual.TrueSight;
import net.ccbluex.liquidbounce.injection.forge.mixins.render.MixinRender;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={RendererLivingEntity.class})
public abstract class MixinRendererLivingEntity
extends MixinRender {
    @Shadow
    protected ModelBase field_77045_g;
    @Shadow
    protected FloatBuffer field_177095_g = GLAllocation.func_74529_h((int)4);
    private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);

    @Shadow
    public abstract <T extends EntityLivingBase> int func_77030_a(T var1, float var2, float var3);

    @Shadow
    public ModelBase func_177087_b() {
        return this.field_77045_g;
    }

    @Shadow
    protected <T extends EntityLivingBase> float func_77037_a(T p_getDeathMaxRotation_1_) {
        return 90.0f;
    }

    @Overwrite
    protected <T extends EntityLivingBase> boolean func_177092_a(T entitylivingbaseIn, float partialTicks, boolean combineTextures) {
        boolean flag1;
        HitColor hitColor = CrossSine.moduleManager.getModule(HitColor.class);
        float f = entitylivingbaseIn.func_70013_c(partialTicks);
        int i = this.func_77030_a(entitylivingbaseIn, f, partialTicks);
        boolean flag = (i >> 24 & 0xFF) > 0;
        boolean bl = flag1 = entitylivingbaseIn.field_70737_aN > 0 || entitylivingbaseIn.field_70725_aQ > 0;
        if (!flag && !flag1) {
            return false;
        }
        if (!flag && !combineTextures) {
            return false;
        }
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77478_a);
        GlStateManager.func_179098_w();
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.field_176095_s);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176099_x, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176098_y, (int)OpenGlHelper.field_77478_a);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176097_z, (int)OpenGlHelper.field_176093_u);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176081_B, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176082_C, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176077_E, (int)7681);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176078_F, (int)OpenGlHelper.field_77478_a);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176085_I, (int)770);
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77476_b);
        GlStateManager.func_179098_w();
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.field_176095_s);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176099_x, (int)OpenGlHelper.field_176094_t);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176098_y, (int)OpenGlHelper.field_176092_v);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176097_z, (int)OpenGlHelper.field_176091_w);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176080_A, (int)OpenGlHelper.field_176092_v);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176081_B, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176082_C, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176076_D, (int)770);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176077_E, (int)7681);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176078_F, (int)OpenGlHelper.field_176091_w);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176085_I, (int)770);
        this.field_177095_g.position(0);
        if (flag1) {
            assert (hitColor != null);
            if (hitColor.getState()) {
                int color = (Boolean)hitColor.getHitColorTheme().get() != false ? ClientTheme.INSTANCE.getColorWithAlpha(1, (Integer)hitColor.getHitColorAlphaValue().get(), true).getRGB() : new Color(hitColor.getHitColorRValue().get(), hitColor.getHitColorGValue().get(), hitColor.getHitColorBValue().get(), (Integer)hitColor.getHitColorAlphaValue().get()).getRGB();
                float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
                float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
                float blue2 = (float)(color & 0xFF) / 255.0f;
                float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
                this.field_177095_g.put(red2);
                this.field_177095_g.put(green2);
                this.field_177095_g.put(blue2);
                this.field_177095_g.put(alpha);
            } else {
                this.field_177095_g.put(1.0f);
                this.field_177095_g.put(0.0f);
                this.field_177095_g.put(0.0f);
                this.field_177095_g.put(0.3f);
            }
        } else {
            float f1 = (float)(i >> 24 & 0xFF) / 255.0f;
            float f2 = (float)(i >> 16 & 0xFF) / 255.0f;
            float f3 = (float)(i >> 8 & 0xFF) / 255.0f;
            float f4 = (float)(i & 0xFF) / 255.0f;
            this.field_177095_g.put(f2);
            this.field_177095_g.put(f3);
            this.field_177095_g.put(f4);
            this.field_177095_g.put(1.0f - f1);
        }
        this.field_177095_g.flip();
        GL11.glTexEnv((int)8960, (int)8705, (FloatBuffer)this.field_177095_g);
        GlStateManager.func_179138_g((int)OpenGlHelper.field_176096_r);
        GlStateManager.func_179098_w();
        GlStateManager.func_179144_i((int)field_177096_e.func_110552_b());
        GL11.glTexEnvi((int)8960, (int)8704, (int)OpenGlHelper.field_176095_s);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176099_x, (int)8448);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176098_y, (int)OpenGlHelper.field_176091_w);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176097_z, (int)OpenGlHelper.field_77476_b);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176081_B, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176082_C, (int)768);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176077_E, (int)7681);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176078_F, (int)OpenGlHelper.field_176091_w);
        GL11.glTexEnvi((int)8960, (int)OpenGlHelper.field_176085_I, (int)770);
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77478_a);
        return true;
    }

    @Overwrite
    protected <T extends EntityLivingBase> void func_77043_a(T p_rotateCorpse_1_, float p_rotateCorpse_2_, float p_rotateCorpse_3_, float p_rotateCorpse_4_) {
        GlStateManager.func_179114_b((float)(180.0f - p_rotateCorpse_3_), (float)0.0f, (float)1.0f, (float)0.0f);
        if (p_rotateCorpse_1_.field_70725_aQ > 0) {
            float f = ((float)p_rotateCorpse_1_.field_70725_aQ + p_rotateCorpse_4_ - 1.0f) / 20.0f * 1.6f;
            if ((f = MathHelper.func_76129_c((float)f)) > 1.0f) {
                f = 1.0f;
            }
            GlStateManager.func_179114_b((float)(f * this.func_77037_a(p_rotateCorpse_1_)), (float)0.0f, (float)0.0f, (float)1.0f);
        } else {
            String string = EnumChatFormatting.func_110646_a((String)p_rotateCorpse_1_.func_70005_c_());
        }
    }

    @Inject(method={"doRender"}, at={@At(value="HEAD")})
    private <T extends EntityLivingBase> void injectChamsPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        Chams chams = CrossSine.moduleManager.getModule(Chams.class);
        if (chams.getState() && ((Boolean)chams.getTargetsValue().get()).booleanValue() && ((Boolean)chams.getLegacyMode().get()).booleanValue() && (((Boolean)chams.getLocalPlayerValue().get()).booleanValue() && entity == Minecraft.func_71410_x().field_71439_g || EntityUtils.INSTANCE.isSelected((Entity)entity, false))) {
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-1000000.0f);
        }
    }

    @Inject(method={"doRender"}, at={@At(value="RETURN")})
    private <T extends EntityLivingBase> void injectChamsPost(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
        Chams chams = CrossSine.moduleManager.getModule(Chams.class);
        if (chams.getState() && ((Boolean)chams.getTargetsValue().get()).booleanValue() && ((Boolean)chams.getLegacyMode().get()).booleanValue() && (((Boolean)chams.getLocalPlayerValue().get()).booleanValue() && entity == Minecraft.func_71410_x().field_71439_g || EntityUtils.INSTANCE.isSelected((Entity)entity, false))) {
            GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
            GL11.glDisable((int)32823);
        }
    }

    @Inject(method={"canRenderName"}, at={@At(value="HEAD")}, cancellable=true)
    private <T extends EntityLivingBase> void canRenderName(T entity, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (CrossSine.moduleManager.getModule(NameTags.class).getState() && EntityUtils.INSTANCE.isSelected((Entity)entity, false)) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method={"renderModel"}, at={@At(value="HEAD")}, cancellable=true)
    protected <T extends EntityLivingBase> void renderModel(T p_renderModel_1_, float p_renderModel_2_, float p_renderModel_3_, float p_renderModel_4_, float p_renderModel_5_, float p_renderModel_6_, float p_renderModel_7_, CallbackInfo ci) {
        boolean semiVisible;
        boolean visible = !p_renderModel_1_.func_82150_aj();
        Chams chams = CrossSine.moduleManager.getModule(Chams.class);
        TrueSight trueSight = CrossSine.moduleManager.getModule(TrueSight.class);
        boolean chamsFlag = chams.getState() && (Boolean)chams.getTargetsValue().get() != false && (Boolean)chams.getLegacyMode().get() == false && ((Boolean)chams.getLocalPlayerValue().get() != false && p_renderModel_1_ == Minecraft.func_71410_x().field_71439_g || EntityUtils.INSTANCE.isSelected((Entity)p_renderModel_1_, false));
        boolean bl = semiVisible = !visible && (!p_renderModel_1_.func_98034_c((EntityPlayer)Minecraft.func_71410_x().field_71439_g) || trueSight.getState() && (Boolean)trueSight.getEntitiesValue().get() != false);
        if (visible || semiVisible) {
            if (!this.func_180548_c(p_renderModel_1_)) {
                return;
            }
            if (semiVisible) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)0.15f);
                GlStateManager.func_179132_a((boolean)false);
                GlStateManager.func_179147_l();
                GlStateManager.func_179112_b((int)770, (int)771);
                GlStateManager.func_179092_a((int)516, (float)0.003921569f);
            }
            int blend = 3042;
            int depth = 2929;
            int srcAlpha = 770;
            int srcAlphaPlus1 = 771;
            int polygonOffsetLine = 10754;
            int texture2D = 3553;
            int lighting = 2896;
            boolean textured = chams.getTexturedValue().get();
            Color chamsColor = new Color(0);
            switch (chams.getColorModeValue().get()) {
                case "Custom": {
                    chamsColor = new Color(chams.getRedValue().get(), chams.getGreenValue().get(), chams.getBlueValue().get());
                    break;
                }
                case "Slowly": {
                    chamsColor = ColorUtils.slowlyRainbow(System.nanoTime(), 0, chams.getSaturationValue().get().floatValue(), chams.getBrightnessValue().get().floatValue());
                    break;
                }
                case "Fade": {
                    chamsColor = ColorUtils.fade(new Color(chams.getRedValue().get(), chams.getGreenValue().get(), chams.getBlueValue().get(), chams.getAlphaValue().get()), 0, 100);
                }
            }
            chamsColor = ColorUtils.reAlpha(chamsColor, chams.getAlphaValue().get());
            if (chamsFlag) {
                Color chamsColor2 = new Color(0);
                switch (chams.getBehindColorModeValue().get()) {
                    case "Same": {
                        chamsColor2 = chamsColor;
                        break;
                    }
                    case "Opposite": {
                        chamsColor2 = ColorUtils.getOppositeColor(chamsColor);
                        break;
                    }
                    case "Red": {
                        chamsColor2 = new Color(-1104346);
                    }
                }
                GL11.glPushMatrix();
                GL11.glEnable((int)10754);
                GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
                OpenGlHelper.func_77475_a((int)OpenGlHelper.field_77476_b, (float)240.0f, (float)240.0f);
                if (!textured) {
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)3553);
                    GL11.glDisable((int)2896);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glColor4f((float)((float)chamsColor2.getRed() / 255.0f), (float)((float)chamsColor2.getGreen() / 255.0f), (float)((float)chamsColor2.getBlue() / 255.0f), (float)((float)chamsColor2.getAlpha() / 255.0f));
                }
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
            }
            this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
            if (chamsFlag) {
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                if (!textured) {
                    GL11.glColor4f((float)((float)chamsColor.getRed() / 255.0f), (float)((float)chamsColor.getGreen() / 255.0f), (float)((float)chamsColor.getBlue() / 255.0f), (float)((float)chamsColor.getAlpha() / 255.0f));
                }
                this.field_77045_g.func_78088_a(p_renderModel_1_, p_renderModel_2_, p_renderModel_3_, p_renderModel_4_, p_renderModel_5_, p_renderModel_6_, p_renderModel_7_);
                if (!textured) {
                    GL11.glEnable((int)3553);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)2896);
                }
                GL11.glPolygonOffset((float)1.0f, (float)-1000000.0f);
                GL11.glDisable((int)10754);
                GL11.glPopMatrix();
            }
            if (semiVisible) {
                GlStateManager.func_179084_k();
                GlStateManager.func_179092_a((int)516, (float)0.1f);
                GlStateManager.func_179121_F();
                GlStateManager.func_179132_a((boolean)true);
            }
        }
        ci.cancel();
    }

    @Redirect(method={"renderName(Lnet/minecraft/entity/EntityLivingBase;DDD)V"}, at=@At(value="FIELD", target="Lnet/minecraft/client/renderer/entity/RenderManager;playerViewX:F"))
    private float renderName(RenderManager renderManager) {
        return Minecraft.func_71410_x().field_71474_y.field_74320_O == 2 ? -renderManager.field_78732_j : renderManager.field_78732_j;
    }
}

