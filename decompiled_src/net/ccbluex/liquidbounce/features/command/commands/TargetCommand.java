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
import net.ccbluex.liquidbounce.features.module.modules.world.Target;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/TargetCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class TargetCommand
extends Command {
    public TargetCommand() {
        boolean $i$f$emptyArray = false;
        super("target", new String[0]);
    }

    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length > 1) {
            if (StringsKt.equals(args2[1], "players", true)) {
                Target.INSTANCE.getPlayerValue().set((Boolean)Target.INSTANCE.getPlayerValue().get() == false);
                this.alert("\u00a77Target player toggled " + ((Boolean)Target.INSTANCE.getPlayerValue().get() != false ? "on" : "off") + '.');
                this.playEdit();
                return;
            }
            if (StringsKt.equals(args2[1], "mobs", true)) {
                Target.INSTANCE.getMobValue().set((Boolean)Target.INSTANCE.getMobValue().get() == false);
                this.alert("\u00a77Target mobs toggled " + ((Boolean)Target.INSTANCE.getMobValue().get() != false ? "on" : "off") + '.');
                this.playEdit();
                return;
            }
            if (StringsKt.equals(args2[1], "animals", true)) {
                Target.INSTANCE.getAnimalValue().set((Boolean)Target.INSTANCE.getAnimalValue().get() == false);
                this.alert("\u00a77Target animals toggled " + ((Boolean)Target.INSTANCE.getAnimalValue().get() != false ? "on" : "off") + '.');
                this.playEdit();
                return;
            }
            if (StringsKt.equals(args2[1], "invisible", true)) {
                Target.INSTANCE.getInvisibleValue().set((Boolean)Target.INSTANCE.getInvisibleValue().get() == false);
                this.alert("\u00a77Target Invisible toggled " + ((Boolean)Target.INSTANCE.getInvisibleValue().get() != false ? "on" : "off") + '.');
                this.playEdit();
                return;
            }
        }
        this.chatSyntax("target <players/mobs/animals/invisible>");
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<String> tabComplete(@NotNull String[] args2) {
        List list;
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length == 0) {
            return CollectionsKt.emptyList();
        }
        if (args2.length == 1) {
            void $this$filterTo$iv$iv;
            String[] stringArray = new String[]{"players", "mobs", "animals", "invisible"};
            Iterable $this$filter$iv = CollectionsKt.listOf(stringArray);
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                String it = (String)element$iv$iv;
                boolean bl = false;
                if (!StringsKt.startsWith(it, args2[0], true)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            list = (List)destination$iv$iv;
        } else {
            list = CollectionsKt.emptyList();
        }
        return list;
    }
}

