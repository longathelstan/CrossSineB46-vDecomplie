/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

public final class ServerUtils
extends MinecraftInstance {
    public static ServerData serverData;

    public static void connectToLastServer() {
        if (serverData == null) {
            return;
        }
        mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMultiplayer(CrossSine.mainMenu), mc, serverData));
    }

    public static String getRemoteIp() {
        ServerData serverData;
        String serverIp = "MainMenu";
        if (mc.func_71387_A()) {
            serverIp = "SinglePlayer";
        } else if (ServerUtils.mc.field_71441_e != null && ServerUtils.mc.field_71441_e.field_72995_K && (serverData = mc.func_147104_D()) != null) {
            serverIp = serverData.field_78845_b;
        }
        return serverIp;
    }
}

