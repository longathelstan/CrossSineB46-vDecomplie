/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.features.module.modules.client.RotationModule;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.ccbluex.liquidbounce.injection.forge.mixins.render.MixinModelBase;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={ModelBiped.class})
public class MixinModelBiped
extends MixinModelBase {
    @Shadow
    public ModelRenderer field_78116_c;
    @Shadow
    public ModelRenderer field_178720_f;
    @Shadow
    public ModelRenderer field_78115_e;
    @Shadow
    public ModelRenderer field_178723_h;
    @Shadow
    public ModelRenderer field_178724_i;
    @Shadow
    public ModelRenderer field_178721_j;
    @Shadow
    public ModelRenderer field_178722_k;
    @Shadow
    public int field_78119_l;
    @Shadow
    public int field_78120_m;
    @Shadow
    public boolean field_78117_n;
    @Shadow
    public boolean field_78118_o;

    @Overwrite
    public void func_78087_a(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_) {
        float lvt_9_2_;
        float lvt_8_2_;
        ModelRenderer var10000;
        this.field_78116_c.field_78796_g = p_setRotationAngles_4_ / 57.295776f;
        this.field_78116_c.field_78795_f = p_setRotationAngles_5_ / 57.295776f;
        if (RotationModule.INSTANCE.getState() && RotationUtils.serverRotation != null && p_setRotationAngles_7_ instanceof EntityPlayer && p_setRotationAngles_7_.equals((Object)Minecraft.func_71410_x().field_71439_g)) {
            this.field_78116_c.field_78795_f = (float)Math.toRadians(RotationUtils.fakeRotation != null ? (double)RotationUtils.fakeRotation.getPitch() : (RotationUtils.targetRotation != null ? RotationUtils.smoothPitch : RenderUtils.interpolate(RotationUtils.headPitch, RotationUtils.prevHeadPitch, Minecraft.func_71410_x().field_71428_T.field_74281_c)));
        }
        this.field_178723_h.field_78795_f = MathHelper.func_76134_b((float)(p_setRotationAngles_1_ * 0.6662f + (float)Math.PI)) * 2.0f * p_setRotationAngles_2_ * 0.5f;
        this.field_178724_i.field_78795_f = MathHelper.func_76134_b((float)(p_setRotationAngles_1_ * 0.6662f)) * 2.0f * p_setRotationAngles_2_ * 0.5f;
        this.field_178723_h.field_78808_h = 0.0f;
        this.field_178724_i.field_78808_h = 0.0f;
        this.field_178721_j.field_78795_f = MathHelper.func_76134_b((float)(p_setRotationAngles_1_ * 0.6662f)) * 1.4f * p_setRotationAngles_2_;
        this.field_178722_k.field_78795_f = MathHelper.func_76134_b((float)(p_setRotationAngles_1_ * 0.6662f + (float)Math.PI)) * 1.4f * p_setRotationAngles_2_;
        this.field_178721_j.field_78796_g = 0.0f;
        this.field_178722_k.field_78796_g = 0.0f;
        if (this.field_78093_q) {
            var10000 = this.field_178723_h;
            var10000.field_78795_f += -0.62831855f;
            var10000 = this.field_178724_i;
            var10000.field_78795_f += -0.62831855f;
            this.field_178721_j.field_78795_f = -1.2566371f;
            this.field_178722_k.field_78795_f = -1.2566371f;
            this.field_178721_j.field_78796_g = 0.31415927f;
            this.field_178722_k.field_78796_g = -0.31415927f;
        }
        if (this.field_78119_l != 0) {
            this.field_178724_i.field_78795_f = this.field_178724_i.field_78795_f * 0.5f - 0.31415927f * (float)this.field_78119_l;
        }
        this.field_178723_h.field_78796_g = 0.0f;
        this.field_178723_h.field_78808_h = 0.0f;
        switch (this.field_78120_m) {
            default: {
                break;
            }
            case 1: {
                this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5f - 0.31415927f * (float)this.field_78120_m;
                break;
            }
            case 3: {
                this.field_178723_h.field_78795_f = this.field_178723_h.field_78795_f * 0.5f - 0.31415927f * (float)this.field_78120_m;
                this.field_178723_h.field_78796_g = Animations.INSTANCE.getState() && (Boolean)Animations.INSTANCE.getBlockAnimation().get() != false ? 0.0f : -0.5235988f;
            }
        }
        this.field_178724_i.field_78796_g = 0.0f;
        if (this.field_78095_p > -9990.0f) {
            lvt_8_2_ = this.field_78095_p;
            this.field_78115_e.field_78796_g = MathHelper.func_76126_a((float)(MathHelper.func_76129_c((float)lvt_8_2_) * (float)Math.PI * 2.0f)) * 0.2f;
            this.field_178723_h.field_78798_e = MathHelper.func_76126_a((float)this.field_78115_e.field_78796_g) * 5.0f;
            this.field_178723_h.field_78800_c = -MathHelper.func_76134_b((float)this.field_78115_e.field_78796_g) * 5.0f;
            this.field_178724_i.field_78798_e = -MathHelper.func_76126_a((float)this.field_78115_e.field_78796_g) * 5.0f;
            this.field_178724_i.field_78800_c = MathHelper.func_76134_b((float)this.field_78115_e.field_78796_g) * 5.0f;
            var10000 = this.field_178723_h;
            var10000.field_78796_g += this.field_78115_e.field_78796_g;
            var10000 = this.field_178724_i;
            var10000.field_78796_g += this.field_78115_e.field_78796_g;
            var10000 = this.field_178724_i;
            var10000.field_78795_f += this.field_78115_e.field_78796_g;
            lvt_8_2_ = 1.0f - this.field_78095_p;
            lvt_8_2_ *= lvt_8_2_;
            lvt_8_2_ *= lvt_8_2_;
            lvt_8_2_ = 1.0f - lvt_8_2_;
            lvt_9_2_ = MathHelper.func_76126_a((float)(lvt_8_2_ * (float)Math.PI));
            float lvt_10_1_ = MathHelper.func_76126_a((float)(this.field_78095_p * (float)Math.PI)) * -(this.field_78116_c.field_78795_f - 0.7f) * 0.75f;
            var10000 = this.field_178723_h;
            var10000.field_78795_f = (float)((double)var10000.field_78795_f - ((double)lvt_9_2_ * 1.2 + (double)lvt_10_1_));
            var10000 = this.field_178723_h;
            var10000.field_78796_g += this.field_78115_e.field_78796_g * 2.0f;
            var10000 = this.field_178723_h;
            var10000.field_78808_h += MathHelper.func_76126_a((float)(this.field_78095_p * (float)Math.PI)) * -0.4f;
        }
        if (this.field_78117_n) {
            this.field_78115_e.field_78795_f = 0.5f;
            var10000 = this.field_178723_h;
            var10000.field_78795_f += 0.4f;
            var10000 = this.field_178724_i;
            var10000.field_78795_f += 0.4f;
            this.field_178721_j.field_78798_e = 4.0f;
            this.field_178722_k.field_78798_e = 4.0f;
            this.field_178721_j.field_78797_d = 9.0f;
            this.field_178722_k.field_78797_d = 9.0f;
            this.field_78116_c.field_78797_d = 1.0f;
        } else {
            this.field_78115_e.field_78795_f = 0.0f;
            this.field_178721_j.field_78798_e = 0.1f;
            this.field_178722_k.field_78798_e = 0.1f;
            this.field_178721_j.field_78797_d = 12.0f;
            this.field_178722_k.field_78797_d = 12.0f;
            this.field_78116_c.field_78797_d = 0.0f;
        }
        var10000 = this.field_178723_h;
        var10000.field_78808_h += MathHelper.func_76134_b((float)(p_setRotationAngles_3_ * 0.09f)) * 0.05f + 0.05f;
        var10000 = this.field_178724_i;
        var10000.field_78808_h -= MathHelper.func_76134_b((float)(p_setRotationAngles_3_ * 0.09f)) * 0.05f + 0.05f;
        var10000 = this.field_178723_h;
        var10000.field_78795_f += MathHelper.func_76126_a((float)(p_setRotationAngles_3_ * 0.067f)) * 0.05f;
        var10000 = this.field_178724_i;
        var10000.field_78795_f -= MathHelper.func_76126_a((float)(p_setRotationAngles_3_ * 0.067f)) * 0.05f;
        if (this.field_78118_o) {
            lvt_8_2_ = 0.0f;
            lvt_9_2_ = 0.0f;
            this.field_178723_h.field_78808_h = 0.0f;
            this.field_178724_i.field_78808_h = 0.0f;
            this.field_178723_h.field_78796_g = -(0.1f - lvt_8_2_ * 0.6f) + this.field_78116_c.field_78796_g;
            this.field_178724_i.field_78796_g = 0.1f - lvt_8_2_ * 0.6f + this.field_78116_c.field_78796_g + 0.4f;
            this.field_178723_h.field_78795_f = -1.5707964f + this.field_78116_c.field_78795_f;
            this.field_178724_i.field_78795_f = -1.5707964f + this.field_78116_c.field_78795_f;
            var10000 = this.field_178723_h;
            var10000.field_78795_f -= lvt_8_2_ * 1.2f - lvt_9_2_ * 0.4f;
            var10000 = this.field_178724_i;
            var10000.field_78795_f -= lvt_8_2_ * 1.2f - lvt_9_2_ * 0.4f;
            var10000 = this.field_178723_h;
            var10000.field_78808_h += MathHelper.func_76134_b((float)(p_setRotationAngles_3_ * 0.09f)) * 0.05f + 0.05f;
            var10000 = this.field_178724_i;
            var10000.field_78808_h -= MathHelper.func_76134_b((float)(p_setRotationAngles_3_ * 0.09f)) * 0.05f + 0.05f;
            var10000 = this.field_178723_h;
            var10000.field_78795_f += MathHelper.func_76126_a((float)(p_setRotationAngles_3_ * 0.067f)) * 0.05f;
            var10000 = this.field_178724_i;
            var10000.field_78795_f -= MathHelper.func_76126_a((float)(p_setRotationAngles_3_ * 0.067f)) * 0.05f;
        }
        MixinModelBiped.func_178685_a(this.field_78116_c, this.field_178720_f);
    }
}

