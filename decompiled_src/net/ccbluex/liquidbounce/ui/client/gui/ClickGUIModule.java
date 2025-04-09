/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.ClickGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

@ModuleInfo(name="ClickGUI", category=ModuleCategory.CLIENT, keyBind=54, canEnable=false, array=false)
public class ClickGUIModule
extends Module {
    public static final BoolValue fastRenderValue = new BoolValue("FastRender", false);

    @Override
    public void onEnable() {
        mc.func_147108_a((GuiScreen)ClickGui.Companion.getInstance());
    }

    @EventTarget(ignoreCondition=true)
    public void onPacket(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof S2EPacketCloseWindow && ClickGUIModule.mc.field_71462_r instanceof ClickGui) {
            event.cancelEvent();
        }
    }
}

