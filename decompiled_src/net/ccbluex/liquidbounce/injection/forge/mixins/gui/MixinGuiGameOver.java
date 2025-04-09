/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGuiScreen;
import net.ccbluex.liquidbounce.ui.client.gui.GuiMainMenu;
import net.ccbluex.liquidbounce.utils.StatisticsUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiGameOver.class})
public abstract class MixinGuiGameOver
extends MixinGuiScreen
implements GuiYesNoCallback {
    @Inject(method={"actionPerformed"}, at={@At(value="HEAD")})
    public void actionPerformed(GuiButton button, CallbackInfo callbackInfo) {
        switch (button.field_146127_k) {
            case 0: {
                StatisticsUtils.addDeaths();
                this.field_146297_k.field_71439_g.func_71004_bE();
                this.field_146297_k.func_147108_a(null);
                break;
            }
            case 1: {
                if (this.field_146297_k.field_71441_e.func_72912_H().func_76093_s()) {
                    this.field_146297_k.func_147108_a((GuiScreen)new GuiMainMenu());
                    break;
                }
                GuiYesNo lvt_2_1_ = new GuiYesNo((GuiYesNoCallback)this, I18n.func_135052_a((String)"deathScreen.quit.confirm", (Object[])new Object[0]), "", I18n.func_135052_a((String)"deathScreen.titleScreen", (Object[])new Object[0]), I18n.func_135052_a((String)"deathScreen.respawn", (Object[])new Object[0]), 0);
                this.field_146297_k.func_147108_a((GuiScreen)lvt_2_1_);
                lvt_2_1_.func_146350_a(20);
            }
        }
    }

    public void confirmClicked(boolean p_confirmClicked_1_, int p_confirmClicked_2_, CallbackInfo ci) {
        if (p_confirmClicked_1_) {
            StatisticsUtils.addDeaths();
            this.field_146297_k.field_71441_e.func_72882_A();
            this.field_146297_k.func_71403_a(null);
            this.field_146297_k.func_147108_a((GuiScreen)new GuiMainMenu());
        } else {
            StatisticsUtils.addDeaths();
            this.field_146297_k.field_71439_g.func_71004_bE();
            this.field_146297_k.func_147108_a(null);
        }
    }
}

