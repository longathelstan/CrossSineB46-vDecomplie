/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen;
import net.ccbluex.liquidbounce.protocol.api.ProtocolSelector;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiMultiplayer.class})
public abstract class MixinGuiMultiplayer
extends MixinGuiScreen {
    @Inject(method={"initGui"}, at={@At(value="RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        if (!CrossSine.INSTANCE.getDestruced()) {
            this.field_146292_n.add(new GuiButton(123, this.field_146294_l - 104, 8, 98, 20, "Alt Manager"));
            this.field_146292_n.add(new GuiButton(1151, 4, this.field_146295_m - 24, 68, 20, "Protocol"));
        }
    }

    @Inject(method={"actionPerformed"}, at={@At(value="HEAD")})
    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        if (!CrossSine.INSTANCE.getDestruced() && button.field_146127_k == 123) {
            this.field_146297_k.func_147108_a((GuiScreen)new GuiAltManager((GuiScreen)this));
        }
        if (button.field_146127_k == 1151) {
            this.field_146297_k.func_147108_a((GuiScreen)new ProtocolSelector((GuiScreen)this));
        }
    }
}

