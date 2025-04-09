/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.ui.client.gui.GuiMainMenu;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/ConnectCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "CrossSine"})
public final class ConnectCommand
extends Command {
    public ConnectCommand() {
        boolean $i$f$emptyArray = false;
        super("connect", new String[0]);
    }

    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length == 3) {
            if (Intrinsics.areEqual(args2[2], "silent")) {
                this.chat("Connecting to \u00a7a\u00a7l" + args2[1] + " \u00a77(Silent mode)");
                MinecraftInstance.mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), MinecraftInstance.mc, new ServerData("", args2[1], false)));
            }
            return;
        }
        if (args2.length == 3 && Intrinsics.areEqual(args2[2], "silent")) {
            return;
        }
        if (args2.length == 2) {
            this.chat(Intrinsics.stringPlus("Connecting to \u00a7a\u00a7l", args2[1]));
            MinecraftInstance.mc.field_71441_e.func_72882_A();
            MinecraftInstance.mc.func_147108_a((GuiScreen)new GuiConnecting((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), MinecraftInstance.mc, new ServerData("", args2[1], false)));
        } else {
            this.chatSyntax("connect <ip:port> (silent)");
        }
    }
}

