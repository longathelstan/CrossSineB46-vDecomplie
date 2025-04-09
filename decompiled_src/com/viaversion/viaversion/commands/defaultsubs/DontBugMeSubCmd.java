/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;

public class DontBugMeSubCmd
implements ViaSubCommand {
    @Override
    public String name() {
        return "dontbugme";
    }

    @Override
    public String description() {
        return "Toggle checking for updates.";
    }

    @Override
    public boolean execute(ViaCommandSender sender, String[] args2) {
        ViaVersionConfig config = Via.getConfig();
        boolean newValue = !config.isCheckForUpdates();
        config.setCheckForUpdates(newValue);
        config.save();
        this.sendMessage(sender, "&6We will %snotify you about updates.", newValue ? "&a" : "&cnot ");
        return true;
    }
}

