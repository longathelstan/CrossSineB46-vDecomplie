/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import com.mojang.authlib.GameProfile;
import net.ccbluex.liquidbounce.features.module.modules.combat.KeepSprint;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.visual.Animations;
import net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntityLivingBase;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.CooldownHelper;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityPlayer.class})
public abstract class MixinEntityPlayer
extends MixinEntityLivingBase {
    @Unique
    private float currentHeight = 1.62f;
    @Unique
    private long lastMillis = System.currentTimeMillis();
    @Shadow
    protected int field_71101_bC;
    @Shadow
    public PlayerCapabilities field_71075_bZ;
    @Shadow
    public boolean field_71083_bS;
    @Shadow
    public InventoryPlayer field_71071_by;
    private ItemStack cooldownStack;
    private int cooldownStackSlot;
    private final ItemStack[] mainInventory = new ItemStack[36];
    private final ItemStack[] armorInventory = new ItemStack[4];

    @Shadow
    public abstract String func_70005_c_();

    @Override
    @Shadow
    public abstract ItemStack func_70694_bm();

    @Shadow
    public abstract GameProfile func_146103_bH();

    @Shadow
    protected abstract boolean func_70041_e_();

    @Shadow
    protected abstract String func_145776_H();

    @Shadow
    public abstract FoodStats func_71024_bL();

    @Shadow
    public abstract int func_71057_bx();

    @Shadow
    public abstract ItemStack func_71011_bu();

    @Shadow
    public abstract boolean func_71039_bw();

    @Inject(method={"onUpdate"}, at={@At(value="RETURN")})
    private void injectCooldown(CallbackInfo callbackInfo) {
        if (this.func_146103_bH() == Minecraft.func_71410_x().field_71439_g.func_146103_bH()) {
            CooldownHelper.INSTANCE.incrementLastAttackedTicks();
            CooldownHelper.INSTANCE.updateGenericAttackSpeed(this.func_70694_bm());
            if (this.cooldownStackSlot != this.field_71071_by.field_70461_c || !ItemStack.func_77989_b((ItemStack)this.cooldownStack, (ItemStack)this.func_70694_bm())) {
                CooldownHelper.INSTANCE.resetLastAttackedTicks();
            }
            this.cooldownStack = this.func_70694_bm();
            this.cooldownStackSlot = this.field_71071_by.field_70461_c;
        }
    }

    @ModifyConstant(method={"attackTargetEntityWithCurrentItem"}, constant={@Constant(doubleValue=0.6)})
    private double injectKeepSprintA(double constant) {
        return KeepSprint.INSTANCE.getState() && KeepSprint.INSTANCE.getMotion() != null ? (double)KeepSprint.INSTANCE.getMotion().floatValue() : constant;
    }

    @Redirect(method={"attackTargetEntityWithCurrentItem"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/EntityPlayer;setSprinting(Z)V"))
    private void injectKeepSprintB(EntityPlayer instance, boolean sprint) {
        if (!KeepSprint.INSTANCE.getState()) {
            instance.func_70031_b(sprint);
        }
    }

    @Inject(method={"dropItem"}, at={@At(value="HEAD")})
    private void dropItem(ItemStack p_dropItem_1_, boolean p_dropItem_2_, boolean p_dropItem_3_, CallbackInfoReturnable<EntityItem> cir) {
        for (int i = 0; i < this.mainInventory.length; ++i) {
            if (ProtocolFixer.newerThanOrEqualsTo1_16()) {
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)new C0APacketAnimation());
            }
            if (this.mainInventory[i] == null) continue;
            this.mainInventory[i] = null;
        }
        for (int j = 0; j < this.armorInventory.length; ++j) {
            if (ProtocolFixer.newerThanOrEqualsTo1_16()) {
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)new C0APacketAnimation());
            }
            if (this.armorInventory[j] == null) continue;
            this.armorInventory[j] = null;
        }
    }

    @Inject(method={"getEyeHeight"}, at={@At(value="HEAD")}, cancellable=true)
    private void modifyEyeHeight(CallbackInfoReturnable<Float> cir) {
        float f2;
        double y;
        Scaffold scaffold = Scaffold.INSTANCE;
        if (scaffold.getState() && scaffold.getEagleValue().equals("Silent") && scaffold.getY() != null) {
            y = scaffold.getY().intValue();
            f2 = (float)((double)1.62f - (MinecraftInstance.mc.field_71439_g.field_70137_T + (MinecraftInstance.mc.field_71439_g.field_70163_u - MinecraftInstance.mc.field_71439_g.field_70137_T) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - y));
            cir.setReturnValue(Float.valueOf(f2));
        }
        if (scaffold.getState() && scaffold.getSprintModeValue().equals("WatchDog") && !scaffold.getTowerStatus() && scaffold.getLastGroundY() != null) {
            y = scaffold.getLastGroundY().intValue();
            f2 = (float)((double)1.62f - (MinecraftInstance.mc.field_71439_g.field_70137_T + (MinecraftInstance.mc.field_71439_g.field_70163_u - MinecraftInstance.mc.field_71439_g.field_70137_T) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - y));
            cir.setReturnValue(Float.valueOf(f2));
        }
        if (((Boolean)Animations.INSTANCE.getOldSneak().get()).booleanValue() && Animations.INSTANCE.getState()) {
            int delay2 = 10;
            if (this.func_70093_af()) {
                long time;
                long timeSinceLastChange;
                float sneakingHeight = 1.54f;
                if (this.currentHeight > 1.54f && (timeSinceLastChange = (time = System.currentTimeMillis()) - this.lastMillis) > 10L) {
                    this.currentHeight -= 0.012f;
                    this.lastMillis = time;
                }
            } else {
                float standingHeight = 1.62f;
                if (this.currentHeight < 1.62f && this.currentHeight > 0.2f) {
                    long time = System.currentTimeMillis();
                    long timeSinceLastChange = time - this.lastMillis;
                    if (timeSinceLastChange > 10L) {
                        this.currentHeight += 0.012f;
                        this.lastMillis = time;
                    }
                } else {
                    this.currentHeight = 1.62f;
                }
            }
            cir.setReturnValue(Float.valueOf(this.currentHeight));
        }
    }
}

