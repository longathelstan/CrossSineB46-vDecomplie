/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.util.DumpUtil;

public class DumpSubCmd
implements ViaSubCommand {
    @Override
    public String name() {
        return "dump";
    }

    @Override
    public String description() {
        return "Dump information about your server, this is helpful if you report bugs.";
    }

    @Override
    public boolean execute(ViaCommandSender sender, String[] args2) {
        DumpUtil.postDump(sender.getUUID()).whenComplete((url, e) -> {
            if (e != null) {
                String string = e.getMessage();
                sender.sendMessage("\u00a74" + string);
                return;
            }
            String string = url;
            sender.sendMessage("\u00a72We've made a dump with useful information, report your issue and provide this url: " + string);
        });
        return true;
    }
}

