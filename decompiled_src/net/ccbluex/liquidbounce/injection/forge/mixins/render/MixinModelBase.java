/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={ModelBase.class})
public class MixinModelBase {
    @Shadow
    public float field_78095_p;
    @Shadow
    public boolean field_78093_q;

    @Overwrite
    public static void func_178685_a(ModelRenderer p_copyModelAngles_0_, ModelRenderer p_copyModelAngles_1_) {
        p_copyModelAngles_1_.field_78795_f = p_copyModelAngles_0_.field_78795_f;
        p_copyModelAngles_1_.field_78796_g = p_copyModelAngles_0_.field_78796_g;
        p_copyModelAngles_1_.field_78808_h = p_copyModelAngles_0_.field_78808_h;
        p_copyModelAngles_1_.field_78800_c = p_copyModelAngles_0_.field_78800_c;
        p_copyModelAngles_1_.field_78797_d = p_copyModelAngles_0_.field_78797_d;
        p_copyModelAngles_1_.field_78798_e = p_copyModelAngles_0_.field_78798_e;
    }
}

