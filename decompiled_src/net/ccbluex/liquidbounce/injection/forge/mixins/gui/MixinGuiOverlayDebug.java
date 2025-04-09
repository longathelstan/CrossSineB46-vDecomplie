/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.List;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.GuiOverlayDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GuiOverlayDebug.class})
public class MixinGuiOverlayDebug {
    @Inject(method={"getDebugInfoRight"}, at={@At(value="TAIL")})
    public void drawVersion(CallbackInfoReturnable<List<String>> cir) {
        ProtocolVersion version = ProtocolBase.getManager().getTargetVersion();
        cir.getReturnValue().add("");
        if (!MinecraftInstance.mc.func_71387_A()) {
            cir.getReturnValue().add("Protocol: " + version.getName());
        } else {
            cir.getReturnValue().add("Protocol: 1.8.x");
        }
    }
}

