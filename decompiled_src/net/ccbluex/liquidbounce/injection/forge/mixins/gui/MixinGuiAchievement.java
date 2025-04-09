/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.minecraft.client.gui.achievement.GuiAchievement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiAchievement.class})
public class MixinGuiAchievement {
    @Inject(method={"updateAchievementWindow"}, at={@At(value="HEAD")}, cancellable=true)
    private void injectAchievements(CallbackInfo ci) {
        if (CrossSine.moduleManager != null && CrossSine.moduleManager.getModule(Interface.class) != null && CrossSine.moduleManager.getModule(Interface.class).getState()) {
            ci.cancel();
        }
    }
}

