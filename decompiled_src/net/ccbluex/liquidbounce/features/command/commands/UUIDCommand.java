/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.features.special.UUIDSpoofer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/UUIDCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "CrossSine"})
public final class UUIDCommand
extends Command {
    public UUIDCommand() {
        boolean $i$f$emptyArray = false;
        super("uuid", new String[0]);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length == 2) {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            String theName = args2[1];
            if (StringsKt.equals(theName, "reset", true)) {
                UUIDSpoofer.INSTANCE.setSpoofId(null);
                this.chat("\u00a7aSuccessfully resetted your UUID.");
                return;
            }
            Iterable iterable = MinecraftInstance.mc.field_71441_e.field_73010_i;
            Intrinsics.checkNotNullExpressionValue(iterable, "mc.theWorld.playerEntities");
            iterable = iterable;
            boolean $i$f$filter = false;
            void var6_5 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                EntityPlayer it = (EntityPlayer)element$iv$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                if (!(!AntiBot.isBot((EntityLivingBase)it) && StringsKt.equals(it.func_70005_c_(), theName, true))) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            EntityPlayer targetPlayer = (EntityPlayer)CollectionsKt.firstOrNull((List)destination$iv$iv);
            if (targetPlayer == null) {
                UUIDSpoofer.INSTANCE.setSpoofId(theName);
            } else {
                UUIDSpoofer.INSTANCE.setSpoofId(targetPlayer.func_146103_bH().getId().toString());
            }
            StringBuilder stringBuilder = new StringBuilder().append("\u00a7aSuccessfully changed your UUID to \u00a76");
            String string = UUIDSpoofer.INSTANCE.getSpoofId();
            Intrinsics.checkNotNull(string);
            this.chat(stringBuilder.append(string).append("\u00a7a. Make sure to turn on BungeeCordSpoof in server selection.").toString());
            return;
        }
        if (args2.length == 1) {
            this.chat("\u00a76Session's UUID is \u00a77" + MinecraftInstance.mc.field_71449_j.func_148255_b() + "\u00a76.");
            this.chat("\u00a76Player's UUID is \u00a77" + MinecraftInstance.mc.field_71439_g.func_110124_au() + "\u00a76.");
        }
        this.chatSyntax("uuid <player's name in current world/uuid/reset>");
    }
}

