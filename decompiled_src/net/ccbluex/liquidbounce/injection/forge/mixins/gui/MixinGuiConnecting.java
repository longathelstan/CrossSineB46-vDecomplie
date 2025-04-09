/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.extensions.RendererExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiConnecting.class})
public abstract class MixinGuiConnecting
extends GuiScreen {
    @Inject(method={"connect"}, at={@At(value="HEAD")})
    private void headConnect(String ip, int port, CallbackInfo callbackInfo) {
        ServerUtils.serverData = new ServerData("", ip + ":" + port, false);
    }

    @Overwrite
    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.func_146276_q_();
        RenderUtils.drawLoadingCircle(this.field_146294_l / 2, this.field_146295_m / 4 + 70);
        String ip = "Unknown";
        ServerData serverData = this.field_146297_k.func_147104_D();
        if (serverData != null) {
            ip = serverData.field_78845_b;
        }
        RendererExtensionKt.drawCenteredString(this.field_146297_k.field_71466_p, "Connecting to", this.field_146294_l / 2, this.field_146295_m / 4 + 110, 0xFFFFFF, true);
        RendererExtensionKt.drawCenteredString(this.field_146297_k.field_71466_p, ip, this.field_146294_l / 2, this.field_146295_m / 4 + 120, 5407227, true);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
}

