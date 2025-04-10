/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;

public class ReloadSubCmd
implements ViaSubCommand {
    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String description() {
        return "Reload the config from the disk.";
    }

    @Override
    public boolean execute(ViaCommandSender sender, String[] args2) {
        Via.getManager().getConfigurationProvider().reloadConfigs();
        this.sendMessage(sender, "&6Configuration successfully reloaded! Some features may need a restart.", new Object[0]);
        return true;
    }
}

