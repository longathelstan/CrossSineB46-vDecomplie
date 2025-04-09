/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import net.ccbluex.liquidbounce.injection.access.IItemStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemStack.class})
public class MixinItemStack
implements IItemStack {
    private long itemDelay;

    @Inject(method={"<init>(Lnet/minecraft/item/Item;IILnet/minecraft/nbt/NBTTagCompound;)V"}, at={@At(value="RETURN")})
    private void init(CallbackInfo callbackInfo) {
        this.itemDelay = System.currentTimeMillis();
    }

    @Override
    public long getItemDelay() {
        return this.itemDelay;
    }
}

