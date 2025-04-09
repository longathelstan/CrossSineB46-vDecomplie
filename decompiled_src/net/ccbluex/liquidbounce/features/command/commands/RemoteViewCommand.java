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
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/RemoteViewCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class RemoteViewCommand
extends Command {
    public RemoteViewCommand() {
        String[] stringArray = new String[]{"rv"};
        super("remoteview", stringArray);
    }

    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length < 2) {
            if (!Intrinsics.areEqual(MinecraftInstance.mc.func_175606_aa(), MinecraftInstance.mc.field_71439_g)) {
                MinecraftInstance.mc.func_175607_a((Entity)MinecraftInstance.mc.field_71439_g);
                return;
            }
            this.chatSyntax("remoteview <username>");
            return;
        }
        String targetName = args2[1];
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            if (!Intrinsics.areEqual(targetName, entity.func_70005_c_())) continue;
            MinecraftInstance.mc.func_175607_a(entity);
            this.chat("Now viewing perspective of \u00a78" + entity.func_70005_c_() + "\u00a73.");
            this.chat("Execute \u00a78" + CrossSine.INSTANCE.getCommandManager().getPrefix() + "remoteview \u00a73again to go back to yours.");
            break;
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<String> tabComplete(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length == 0) {
            return CollectionsKt.emptyList();
        }
        if (args2.length == 1) {
            void $this$filterTo$iv$iv;
            String it;
            Iterable $this$mapTo$iv$iv;
            List list = MinecraftInstance.mc.field_71441_e.field_73010_i;
            Intrinsics.checkNotNullExpressionValue(list, "mc.theWorld.playerEntities");
            Iterable $this$map$iv = list;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                EntityPlayer entityPlayer = (EntityPlayer)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(it.func_70005_c_());
            }
            Iterable $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$filter = false;
            $this$mapTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                it = (String)element$iv$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                if (!StringsKt.startsWith(it, args2[0], true)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return (List)destination$iv$iv;
        }
        return CollectionsKt.emptyList();
    }
}

