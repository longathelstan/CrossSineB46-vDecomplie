/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.lang.reflect.InvocationTargetException;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleInfo(name="GuiClicker", category=ModuleCategory.PLAYER)
public class GuiClicker
extends Module {
    private final IntegerValue delayValue = new IntegerValue("Delay", 5, 0, 10);
    int mouseDown;

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        if (!Mouse.isButtonDown((int)0) || !Keyboard.isKeyDown((int)54) && !Keyboard.isKeyDown((int)42)) {
            this.mouseDown = 0;
            return;
        }
        if (AutoClicker.INSTANCE.getState() && ((Boolean)AutoClicker.INSTANCE.getInvClicker().get()).booleanValue()) {
            return;
        }
        ++this.mouseDown;
        this.inInvClick(GuiClicker.mc.field_71462_r);
    }

    private void inInvClick(GuiScreen guiScreen) {
        int mouseInGUIPosX = Mouse.getX() * guiScreen.field_146294_l / GuiClicker.mc.field_71443_c;
        int mouseInGUIPosY = guiScreen.field_146295_m - Mouse.getY() * guiScreen.field_146295_m / GuiClicker.mc.field_71440_d - 1;
        try {
            if (this.mouseDown >= (Integer)this.delayValue.get()) {
                ReflectionHelper.findMethod(GuiScreen.class, null, (String[])new String[]{"func_73864_a", "mouseClicked"}, (Class[])new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE}).invoke((Object)guiScreen, mouseInGUIPosX, mouseInGUIPosY, 0);
                this.mouseDown = 0;
            }
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            // empty catch block
        }
    }
}

