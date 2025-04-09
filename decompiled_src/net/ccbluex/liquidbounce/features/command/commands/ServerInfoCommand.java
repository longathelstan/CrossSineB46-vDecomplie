/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/ServerInfoCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "CrossSine"})
public final class ServerInfoCommand
extends Command {
    public ServerInfoCommand() {
        String[] stringArray = new String[]{"si"};
        super("serverinfo", stringArray);
    }

    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (MinecraftInstance.mc.func_147104_D() == null) {
            this.alert("This command does not work in single player.");
            return;
        }
        ServerData serverData = MinecraftInstance.mc.func_147104_D();
        if (serverData == null) {
            return;
        }
        ServerData data = serverData;
        this.alert("Server infos:");
        this.alert(Intrinsics.stringPlus("\u00a77Name: \u00a78", data.field_78847_a));
        this.alert(Intrinsics.stringPlus("\u00a77IP: \u00a78", data.field_78845_b));
        this.alert(Intrinsics.stringPlus("\u00a77Players: \u00a78", data.field_78846_c));
        this.alert(Intrinsics.stringPlus("\u00a77MOTD: \u00a78", data.field_78843_d));
        this.alert(Intrinsics.stringPlus("\u00a77ServerVersion: \u00a78", data.field_82822_g));
        this.alert(Intrinsics.stringPlus("\u00a77ProtocolVersion: \u00a78", data.field_82821_f));
        this.alert(Intrinsics.stringPlus("\u00a77Ping: \u00a78", data.field_78844_e));
    }
}

