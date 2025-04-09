/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class SelfDestructCommand
extends CommandBase {
    public String func_71517_b() {
        return "stopdestruct";
    }

    public String func_71518_a(ICommandSender sender) {
        return "/stopdestruct";
    }

    public void func_71515_b(ICommandSender sender, String[] args2) {
        CrossSine.INSTANCE.setDestruced(false);
        ClientUtils.INSTANCE.reloadClient();
        sender.func_145747_a((IChatComponent)new ChatComponentText("SelfDestruct Destruct his self"));
    }

    public int func_82362_a() {
        return 0;
    }
}

