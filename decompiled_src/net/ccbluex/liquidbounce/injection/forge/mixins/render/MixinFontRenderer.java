/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={FontRenderer.class})
public abstract class MixinFontRenderer {
    @ModifyVariable(method={"renderString"}, at=@At(value="HEAD"), ordinal=0)
    private String renderString(String string) {
        if (string == null || CrossSine.eventManager == null) {
            return string;
        }
        TextEvent textEvent = new TextEvent(string);
        CrossSine.eventManager.callEvent(textEvent);
        return textEvent.getText();
    }

    @ModifyVariable(method={"getStringWidth"}, at=@At(value="HEAD"), ordinal=0)
    private String getStringWidth(String string) {
        if (string == null || CrossSine.eventManager == null) {
            return string;
        }
        TextEvent textEvent = new TextEvent(string);
        CrossSine.eventManager.callEvent(textEvent);
        return textEvent.getText();
    }

    @Inject(method={"drawString(Ljava/lang/String;FFIZ)I"}, at={@At(value="HEAD")}, cancellable=true)
    public void drawString(String p_drawString_1_, float p_drawString_2_, float p_drawString_3_, int p_drawString_4_, boolean p_drawString_5_, CallbackInfoReturnable<Integer> cir) {
    }

    @Inject(method={"getStringWidth"}, at={@At(value="HEAD")}, cancellable=true)
    public void getStringWidth(String p_getStringWidth_1_, CallbackInfoReturnable<Integer> cir) {
    }
}

