/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.RotationModule;
import net.ccbluex.liquidbounce.features.module.modules.movement.Jesus;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.ccbluex.liquidbounce.features.module.modules.visual.FreeLook;
import net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntity;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityLivingBase.class})
public abstract class MixinEntityLivingBase
extends MixinEntity {
    @Shadow
    protected boolean field_70703_bu;
    @Shadow
    private int field_70773_bE;
    @Shadow
    public float field_70759_as;
    @Shadow
    public float field_70758_at;
    @Shadow
    public float field_70702_br;
    @Shadow
    public float field_70701_bs;
    @Shadow
    private EntityLivingBase field_110150_bn;
    @Shadow
    private int field_142016_bo;
    @Shadow
    public float field_70733_aJ;
    @Shadow
    public float field_70761_aq;

    @Shadow
    protected abstract float func_175134_bD();

    @Shadow
    public void func_71038_i() {
    }

    @Shadow
    public abstract PotionEffect func_70660_b(Potion var1);

    @Shadow
    public abstract boolean func_70644_a(Potion var1);

    @Shadow
    public void func_70636_d() {
    }

    @Shadow
    protected abstract void func_180433_a(double var1, boolean var3, Block var4, BlockPos var5);

    @Shadow
    public abstract float func_110143_aJ();

    @Shadow
    public abstract ItemStack func_70694_bm();

    @Shadow
    protected abstract void func_70629_bd();

    @Overwrite
    protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
        boolean flag;
        float rotationYaw = this.field_70177_z;
        if ((EntityLivingBase)this instanceof EntityPlayerSP) {
            if (this.field_70733_aJ > 0.0f) {
                float f = CrossSine.INSTANCE.getDestruced() || !RotationModule.INSTANCE.getState() ? this.field_70177_z : (p_110146_1_ = RotationUtils.fakeRotation != null ? RotationUtils.fakeRotation.getYaw() : (float)RotationUtils.smoothYaw);
            }
            rotationYaw = CrossSine.INSTANCE.getDestruced() || !RotationModule.INSTANCE.getState() ? this.field_70177_z : (RotationUtils.fakeRotation != null ? RotationUtils.fakeRotation.getYaw() : (float)RotationUtils.smoothYaw);
        }
        float f = MathHelper.func_76142_g((float)(p_110146_1_ - this.field_70761_aq));
        this.field_70761_aq += f * 0.3f;
        float f1 = MathHelper.func_76142_g((float)(rotationYaw - this.field_70761_aq));
        boolean bl = flag = f1 < -90.0f || f1 >= 90.0f;
        if (f1 < -75.0f) {
            f1 = -75.0f;
        }
        if (f1 >= 75.0f) {
            f1 = 75.0f;
        }
        this.field_70761_aq = rotationYaw - f1;
        if (f1 * f1 > 2500.0f) {
            this.field_70761_aq += f1 * 0.2f;
        }
        if (flag) {
            p_110146_2_ *= -1.0f;
        }
        return p_110146_2_;
    }

    @Overwrite
    protected void func_70664_aZ() {
        if (((EntityLivingBase)this).equals((Object)Minecraft.func_71410_x().field_71439_g)) {
            JumpEvent event = new JumpEvent((float)this.field_70181_x, this.func_70051_ag(), this.field_70177_z);
            CrossSine.eventManager.callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            this.field_70181_x = this.func_175134_bD();
            if (this.func_70644_a(Potion.field_76430_j)) {
                this.field_70181_x += (double)((float)(this.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1f);
            }
            if (this.func_70051_ag() || event.getBoosting()) {
                float f = (!CrossSine.moduleManager.getModule(FreeLook.class).getState() && !FreeLook.moveCorrect && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw : (CrossSine.moduleManager.getModule(FreeLook.class).getState() && RotationUtils.freeLookRotation != null && !FreeLook.moveCorrect ? FreeLook.cameraYaw2 : event.getYaw())) * ((float)Math.PI / 180);
                this.field_70159_w -= (double)(MathHelper.func_76126_a((float)f) * 0.2f);
                this.field_70179_y += (double)(MathHelper.func_76134_b((float)f) * 0.2f);
            }
            this.field_70160_al = true;
            ForgeHooks.onLivingJump((EntityLivingBase)((EntityLivingBase)this));
        }
    }

    @ModifyConstant(method={"onLivingUpdate"}, constant={@Constant(doubleValue=0.005)})
    private double ViaVersion_MovementThreshold(double constant) {
        if (ProtocolFixer.newerThan1_8()) {
            return 0.003;
        }
        return 0.005;
    }

    @Overwrite
    public Vec3 func_70676_i(float p_getLook_1_) {
        if ((EntityLivingBase)this instanceof EntityPlayerSP) {
            return this.func_174806_f(this.field_70125_A, this.field_70759_as);
        }
        if (p_getLook_1_ == 1.0f) {
            return this.func_174806_f(this.field_70125_A, this.field_70759_as);
        }
        float f = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * p_getLook_1_;
        float f1 = this.field_70758_at + (this.field_70759_as - this.field_70758_at) * p_getLook_1_;
        return this.func_174806_f(f, f1);
    }

    @Inject(method={"onLivingUpdate"}, at={@At(value="FIELD", target="Lnet/minecraft/entity/EntityLivingBase;isJumping:Z", ordinal=1)})
    private void onJumpSection(CallbackInfo callbackInfo) {
        Jesus jesus = CrossSine.moduleManager.getModule(Jesus.class);
        if (jesus.getState() && !this.field_70703_bu && !this.func_70093_af() && this.func_70090_H() && jesus.getModeValue().equals("Legit")) {
            this.func_70629_bd();
        }
    }

    @Overwrite
    private int func_82166_i() {
        int speed;
        int n = this.func_70644_a(Potion.field_76422_e) ? 6 - (1 + this.func_70660_b(Potion.field_76422_e).func_76458_c()) : (speed = this.func_70644_a(Potion.field_76419_f) ? 6 + (1 + this.func_70660_b(Potion.field_76419_f).func_76458_c()) * 2 : 6);
        if (Animations.INSTANCE.getState() && this.equals(Minecraft.func_71410_x().field_71439_g)) {
            speed = (int)((float)speed * ((Float)Animations.INSTANCE.getSwingSpeedValue().get()).floatValue());
        }
        return speed;
    }

    public EntityLivingBase getLastAttacker() {
        return this.field_110150_bn;
    }

    public int getLastAttackerTime() {
        return this.field_142016_bo;
    }
}

