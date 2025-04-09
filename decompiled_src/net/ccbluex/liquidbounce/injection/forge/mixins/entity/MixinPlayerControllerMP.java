/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.utils.CooldownHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerControllerMP.class})
public abstract class MixinPlayerControllerMP {
    @Shadow
    @Final
    private Minecraft field_78776_a;
    @Shadow
    private int field_78777_l;
    @Shadow
    @Final
    private NetHandlerPlayClient field_78774_b;

    @Inject(method={"attackEntity"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/PlayerControllerMP;syncCurrentPlayItem()V")}, cancellable=true)
    private void attackEntity(EntityPlayer entityPlayer, Entity targetEntity, CallbackInfo callbackInfo) {
        if (targetEntity == null) {
            return;
        }
        AttackEvent event = new AttackEvent(targetEntity);
        CrossSine.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
        CooldownHelper.INSTANCE.resetLastAttackedTicks();
    }

    @Inject(method={"windowClick"}, at={@At(value="HEAD")}, cancellable=true)
    private void windowClick(int windowId, int slotId, int mouseButtonClicked, int mode2, EntityPlayer playerIn, CallbackInfoReturnable<ItemStack> callbackInfo) {
        ClickWindowEvent event = new ClickWindowEvent(windowId, slotId, mouseButtonClicked, mode2);
        CrossSine.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

