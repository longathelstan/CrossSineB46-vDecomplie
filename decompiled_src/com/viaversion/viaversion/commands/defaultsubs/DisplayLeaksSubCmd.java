/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import io.netty.util.ResourceLeakDetector;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayLeaksSubCmd
implements ViaSubCommand {
    @Override
    public String name() {
        return "displayleaks";
    }

    @Override
    public String description() {
        return "Try to hunt memory leaks!";
    }

    @Override
    public String usage() {
        return "displayleaks <level>";
    }

    @Override
    public boolean execute(ViaCommandSender sender, String[] args2) {
        if (args2.length == 1) {
            try {
                ResourceLeakDetector.Level level = ResourceLeakDetector.Level.valueOf((String)args2[0]);
                ResourceLeakDetector.setLevel((ResourceLeakDetector.Level)level);
                ResourceLeakDetector.Level level2 = level;
                this.sendMessage(sender, "&6Set leak detector level to &2" + level2, new Object[0]);
            }
            catch (IllegalArgumentException e) {
                String string = Arrays.toString(ResourceLeakDetector.Level.values());
                this.sendMessage(sender, "&cInvalid level (" + string + ")", new Object[0]);
            }
        } else {
            ResourceLeakDetector.Level level = ResourceLeakDetector.getLevel();
            this.sendMessage(sender, "&6Current leak detection level is &2" + level, new Object[0]);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(ViaCommandSender sender, String[] args2) {
        if (args2.length == 1) {
            return Arrays.stream(ResourceLeakDetector.Level.values()).map(Enum::name).filter(it -> it.startsWith(args2[0])).collect(Collectors.toList());
        }
        return ViaSubCommand.super.onTabComplete(sender, args2);
    }
}

