/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.bukkit.commands.BukkitCommandSender;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class BukkitCommandHandler
extends ViaCommandHandler
implements CommandExecutor,
TabExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args2) {
        return this.onCommand(new BukkitCommandSender(sender), args2);
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args2) {
        return this.onTabComplete(new BukkitCommandSender(sender), args2);
    }
}

