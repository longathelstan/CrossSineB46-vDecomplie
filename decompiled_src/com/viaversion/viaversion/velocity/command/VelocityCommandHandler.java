/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.velocity.command.VelocityCommandSender;
import com.viaversion.viaversion.velocity.command.subs.ProbeSubCmd;
import java.util.List;

public class VelocityCommandHandler
extends ViaCommandHandler
implements SimpleCommand {
    public VelocityCommandHandler() {
        this.registerSubCommand(new ProbeSubCmd());
    }

    public void execute(SimpleCommand.Invocation invocation) {
        this.onCommand(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
    }

    public List<String> suggest(SimpleCommand.Invocation invocation) {
        return this.onTabComplete(new VelocityCommandSender(invocation.source()), (String[])invocation.arguments());
    }

    public boolean hasPermission(SimpleCommand.Invocation invocation) {
        return invocation.source().hasPermission("viaversion.command");
    }
}

