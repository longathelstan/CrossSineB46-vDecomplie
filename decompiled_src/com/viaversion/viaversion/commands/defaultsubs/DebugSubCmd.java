/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.debug.DebugHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DebugSubCmd
implements ViaSubCommand {
    @Override
    public String name() {
        return "debug";
    }

    @Override
    public String description() {
        return "Toggle various debug modes.";
    }

    @Override
    public boolean execute(ViaCommandSender sender, String[] args2) {
        DebugHandler debug = Via.getManager().debugHandler();
        if (args2.length == 0) {
            Via.getManager().debugHandler().setEnabled(!Via.getManager().debugHandler().enabled());
            this.sendMessage(sender, "&6Debug mode is now %s", Via.getManager().debugHandler().enabled() ? "&aenabled" : "&cdisabled");
            return true;
        }
        if (args2.length == 1) {
            if (args2[0].equalsIgnoreCase("clear")) {
                debug.clearPacketTypesToLog();
                this.sendMessage(sender, "&6Cleared packet types to log", new Object[0]);
                return true;
            }
            if (args2[0].equalsIgnoreCase("logposttransform")) {
                debug.setLogPostPacketTransform(!debug.logPostPacketTransform());
                this.sendMessage(sender, "&6Post transform packet logging is now %s", debug.logPostPacketTransform() ? "&aenabled" : "&cdisabled");
                return true;
            }
        } else if (args2.length == 2) {
            if (args2[0].equalsIgnoreCase("add")) {
                debug.addPacketTypeNameToLog(args2[1].toUpperCase(Locale.ROOT));
                this.sendMessage(sender, "&6Added packet type %s to debug logging", args2[1]);
                return true;
            }
            if (args2[0].equalsIgnoreCase("remove")) {
                debug.removePacketTypeNameToLog(args2[1].toUpperCase(Locale.ROOT));
                this.sendMessage(sender, "&6Removed packet type %s from debug logging", args2[1]);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(ViaCommandSender sender, String[] args2) {
        if (args2.length == 1) {
            return Arrays.asList("clear", "logposttransform", "add", "remove");
        }
        return Collections.emptyList();
    }
}

