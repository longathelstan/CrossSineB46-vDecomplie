/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiIngameMenu.class})
public abstract class MixinGuiIngameMenu
extends MixinGuiScreen {
    @Inject(method={"initGui"}, at={@At(value="RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        if (!CrossSine.INSTANCE.getDestruced()) {
            if (!this.field_146297_k.func_71387_A()) {
                this.field_146292_n.add(new GuiButton(1337, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 128, "Reconnect"));
                this.field_146292_n.add(new GuiButton(1068, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 128 + 24, "Switcher"));
                this.field_146292_n.add(new GuiButton(1078, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 128 + 140, "Key Bind Manager"));
                this.field_146292_n.add(new GuiButton(16578, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 128 + 115, "RandomOffline and Reconnect"));
            } else {
                this.field_146292_n.add(new GuiButton(1068, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 128, "Switcher"));
                this.field_146292_n.add(new GuiButton(1078, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 128 + 105, "Key Bind Manager"));
                this.field_146292_n.add(new GuiButton(16578, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 128 + 80, "RandomOffline and Reconnect"));
            }
        }
    }

    @Inject(method={"actionPerformed"}, at={@At(value="HEAD")})
    private void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        if (button.field_146127_k == 1337) {
            this.field_146297_k.field_71441_e.func_72882_A();
            ServerUtils.connectToLastServer();
        }
        if (button.field_146127_k == 16578) {
            this.field_146297_k.field_71441_e.func_72882_A();
            ServerUtils.connectToLastServer();
            LoginUtils.INSTANCE.randomCracked();
        }
        if (button.field_146127_k == 1068) {
            this.field_146297_k.func_147108_a((GuiScreen)new GuiMultiplayer((GuiScreen)this));
        }
        if (button.field_146127_k == 1078) {
            this.field_146297_k.func_147108_a((GuiScreen)CrossSine.keyBindManager);
        }
    }
}

