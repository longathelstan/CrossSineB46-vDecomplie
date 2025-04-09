/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.command;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.util.ChatColorUtil;
import java.util.Collections;
import java.util.List;

public interface ViaSubCommand {
    public String name();

    public String description();

    default public String usage() {
        return this.name();
    }

    default public String permission() {
        String string = this.name();
        return "viaversion.admin." + string;
    }

    public boolean execute(ViaCommandSender var1, String[] var2);

    default public List<String> onTabComplete(ViaCommandSender sender, String[] args2) {
        return Collections.emptyList();
    }

    public static String color(String s) {
        return ChatColorUtil.translateAlternateColorCodes(s);
    }

    default public void sendMessage(ViaCommandSender sender, String message, Object ... args2) {
        sender.sendMessage(ViaSubCommand.color(args2 == null ? message : String.format(message, args2)));
    }
}

