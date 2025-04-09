/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Random;
import java.util.UUID;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import net.ccbluex.liquidbounce.features.module.modules.movement.MovementFix;
import net.ccbluex.liquidbounce.features.module.modules.visual.FreeLook;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Entity.class})
public abstract class MixinEntity {
    @Shadow
    public double field_70165_t;
    @Shadow
    public double field_70163_u;
    @Shadow
    public double field_70161_v;
    @Shadow
    public float field_70125_A;
    @Shadow
    public float field_70177_z;
    @Shadow
    public Entity field_70154_o;
    @Shadow
    public double field_70159_w;
    @Shadow
    public double field_70181_x;
    @Shadow
    public double field_70179_y;
    @Shadow
    public boolean field_70122_E;
    @Shadow
    public float field_70127_C;
    @Shadow
    public float field_70126_B;
    @Shadow
    public boolean field_70160_al;
    @Shadow
    public boolean field_70145_X;
    @Shadow
    public World field_70170_p;
    @Shadow
    public boolean field_70134_J;
    @Shadow
    public float field_70138_W;
    @Shadow
    public boolean field_70123_F;
    @Shadow
    public boolean field_70124_G;
    @Shadow
    public boolean field_70132_H;
    @Shadow
    public float field_70140_Q;
    @Shadow
    public float field_82151_R;
    @Shadow
    protected Random field_70146_Z;
    @Shadow
    public int field_70174_ab;
    @Shadow
    protected boolean field_71087_bX;
    @Shadow
    public int field_71088_bW;
    @Shadow
    public float field_70130_N;
    @Shadow
    private int field_70150_b;
    @Shadow
    private int field_70151_c;

    @Shadow
    public abstract boolean func_70051_ag();

    @Shadow
    public abstract void func_70031_b(boolean var1);

    @Shadow
    public abstract AxisAlignedBB func_174813_aQ();

    @Shadow
    public void func_70091_d(double x, double y, double z) {
    }

    @Shadow
    public abstract boolean func_70090_H();

    @Shadow
    public abstract boolean func_70115_ae();

    @Shadow
    public abstract void func_70015_d(int var1);

    @Shadow
    protected abstract void func_70081_e(int var1);

    @Shadow
    public abstract boolean func_70026_G();

    @Shadow
    public abstract void func_85029_a(CrashReportCategory var1);

    @Shadow
    protected abstract void func_145775_I();

    @Shadow
    protected abstract void func_180429_a(BlockPos var1, Block var2);

    @Shadow
    public abstract void func_174826_a(AxisAlignedBB var1);

    @Shadow
    public abstract Vec3 func_174806_f(float var1, float var2);

    @Shadow
    public abstract UUID func_110124_au();

    @Shadow
    public abstract boolean func_70093_af();

    @Shadow
    public abstract boolean equals(Object var1);

    @Shadow
    public abstract float func_70047_e();

    public int getNextStepDistance() {
        return this.field_70150_b;
    }

    public void setNextStepDistance(int nextStepDistance) {
        this.field_70150_b = nextStepDistance;
    }

    public int getFire() {
        return this.field_70151_c;
    }

    @Overwrite
    public void func_70060_a(float p_moveFlying_1_, float p_moveFlying_2_, float p_moveFlying_3_) {
        if (this != Minecraft.func_71410_x().field_71439_g) {
            return;
        }
        StrafeEvent strafeEvent = new StrafeEvent(p_moveFlying_1_, p_moveFlying_2_, p_moveFlying_3_, this.field_70177_z);
        MovementFix movementFix = CrossSine.moduleManager.getModule(MovementFix.class);
        CrossSine.eventManager.callEvent(strafeEvent);
        if (movementFix.getDoFix()) {
            movementFix.runStrafeFixLoop(movementFix.getSilentFix(), strafeEvent);
        }
        if (strafeEvent.isCancelled()) {
            return;
        }
        float f = p_moveFlying_1_ * p_moveFlying_1_ + p_moveFlying_2_ * p_moveFlying_2_;
        if (f >= 1.0E-4f) {
            if ((f = MathHelper.func_76129_c((float)f)) < 1.0f) {
                f = 1.0f;
            }
            f = p_moveFlying_3_ / f;
            float f1 = MathHelper.func_76126_a((float)((!CrossSine.moduleManager.getModule(FreeLook.class).getState() && !FreeLook.moveCorrect && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw : (CrossSine.moduleManager.getModule(FreeLook.class).getState() && RotationUtils.freeLookRotation != null && !FreeLook.moveCorrect ? FreeLook.cameraYaw2 : strafeEvent.getYaw())) * (float)Math.PI / 180.0f));
            float f2 = MathHelper.func_76134_b((float)((!CrossSine.moduleManager.getModule(FreeLook.class).getState() && !FreeLook.moveCorrect && RotationUtils.freeLookRotation != null ? FreeLook.cameraYaw : (CrossSine.moduleManager.getModule(FreeLook.class).getState() && RotationUtils.freeLookRotation != null && !FreeLook.moveCorrect ? FreeLook.cameraYaw2 : strafeEvent.getYaw())) * (float)Math.PI / 180.0f));
            this.field_70159_w += (double)((p_moveFlying_1_ *= f) * f2 - (p_moveFlying_2_ *= f) * f1);
            this.field_70179_y += (double)(p_moveFlying_2_ * f2 + p_moveFlying_1_ * f1);
        }
    }

    @Inject(method={"getCollisionBorderSize"}, at={@At(value="HEAD")}, cancellable=true)
    public void getCollisionBorderSize(CallbackInfoReturnable callbackInfoReturnable) {
        if (CrossSine.moduleManager.getModule(HitBox.class).getState()) {
            double hitBox = HitBox.getSize();
            if (ProtocolFixer.newerThan1_8()) {
                callbackInfoReturnable.setReturnValue(hitBox);
            } else {
                callbackInfoReturnable.setReturnValue((double)0.1f + hitBox);
            }
            callbackInfoReturnable.cancel();
        } else if (ProtocolFixer.newerThan1_8()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(0.0f));
        }
    }
}

