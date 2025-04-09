/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerSubCmd
implements ViaSubCommand {
    @Override
    public String name() {
        return "player";
    }

    @Override
    public String description() {
        return "Shows connection information about one or all players.";
    }

    @Override
    public String usage() {
        return "player <name|*>";
    }

    @Override
    public boolean execute(ViaCommandSender sender, String[] args2) {
        if (args2.length == 0) {
            return false;
        }
        for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
            ProtocolInfo info = connection.getProtocolInfo();
            if (!args2[0].equalsIgnoreCase(info.getUsername()) && !args2[0].equals("*")) continue;
            String string = info.serverProtocolVersion().getName();
            String string2 = info.protocolVersion().getName();
            UUID uUID = info.getUuid();
            String string3 = info.getUsername();
            this.sendMessage(sender, "&7[&6" + string3 + "&7] UUID: &2" + uUID + " &7Client-Protocol: &2" + string2 + " &7Server-Protocol: &2" + string, new Object[0]);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(ViaCommandSender sender, String[] args2) {
        if (args2.length == 1) {
            String input = args2[0].toLowerCase();
            ArrayList<String> matches = new ArrayList<String>();
            for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
                String name = connection.getProtocolInfo().getUsername();
                if (!input.isEmpty() && !name.toLowerCase().startsWith(input)) continue;
                matches.add(name);
            }
            matches.add("*");
            return matches;
        }
        return ViaSubCommand.super.onTabComplete(sender, args2);
    }
}

