/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/DamageCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "CrossSine"})
public final class DamageCommand
extends Command {
    public DamageCommand() {
        String[] stringArray = new String[]{"hurt"};
        super("damage", stringArray);
    }

    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        int damage = 1;
        if (args2.length > 1) {
            try {
                damage = Integer.parseInt(args2[1]);
            }
            catch (NumberFormatException ignored) {
                this.chatSyntaxError();
                return;
            }
        }
        double x = MinecraftInstance.mc.field_71439_g.field_70165_t;
        double y = MinecraftInstance.mc.field_71439_g.field_70163_u;
        double z = MinecraftInstance.mc.field_71439_g.field_70161_v;
        int n = 0;
        int n2 = 65 * damage;
        while (n < n2) {
            int i = n++;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.049, z, false));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
        }
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
        this.chat("You were damaged.");
    }
}

