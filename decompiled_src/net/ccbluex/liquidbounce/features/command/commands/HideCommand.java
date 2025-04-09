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
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/HideCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class HideCommand
extends Command {
    public HideCommand() {
        boolean $i$f$emptyArray = false;
        super("hide", new String[0]);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length > 1) {
            if (StringsKt.equals(args2[1], "list", true)) {
                void $this$filterTo$iv$iv;
                this.alert("\u00a7c\u00a7lHidden");
                Iterable $this$filter$iv = CrossSine.INSTANCE.getModuleManager().getModules();
                boolean $i$f$filter = false;
                Iterable iterable = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv) {
                    Module it = (Module)element$iv$iv;
                    boolean bl = false;
                    if (!(!it.getArray())) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                Iterable $this$forEach$iv = (List)destination$iv$iv;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    Module it = (Module)element$iv;
                    boolean bl = false;
                    ClientUtils.INSTANCE.displayChatMessage(Intrinsics.stringPlus("\u00a76> \u00a7c", it.getName()));
                }
                return;
            }
            if (StringsKt.equals(args2[1], "clear", true)) {
                for (Module module : CrossSine.INSTANCE.getModuleManager().getModules()) {
                    module.setArray(true);
                }
                this.alert("Cleared hidden modules.");
                return;
            }
            if (StringsKt.equals(args2[1], "reset", true)) {
                for (Module module : CrossSine.INSTANCE.getModuleManager().getModules()) {
                    module.setArray(module.getClass().getAnnotation(ModuleInfo.class).array());
                }
                this.alert("Reset hidden modules.");
                return;
            }
            Module module = CrossSine.INSTANCE.getModuleManager().getModule(args2[1]);
            if (module == null) {
                this.alert("Module \u00a7a\u00a7l" + args2[1] + "\u00a73 not found.");
                return;
            }
            module.setArray(!module.getArray());
            this.alert("Module \u00a7a\u00a7l" + module.getName() + "\u00a73 is now \u00a7a\u00a7l" + (module.getArray() ? "visible" : "invisible") + "\u00a73 on the array list.");
            this.playEdit();
            return;
        }
        this.chatSyntax("hide <module/list/clear/reset>");
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<String> tabComplete(@NotNull String[] args2) {
        List<String> list;
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length == 0) {
            return CollectionsKt.emptyList();
        }
        String moduleName = args2[0];
        if (args2.length == 1) {
            void $this$filterTo$iv$iv;
            String it;
            Iterable $this$mapTo$iv$iv;
            Iterable $this$map$iv = CrossSine.INSTANCE.getModuleManager().getModules();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                Module module = (Module)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(((Module)((Object)it)).getName());
            }
            Iterable $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$filter = false;
            $this$mapTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                it = (String)element$iv$iv;
                boolean bl = false;
                if (!StringsKt.startsWith(it, moduleName, true)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            list = CollectionsKt.toList((List)destination$iv$iv);
        } else {
            list = CollectionsKt.emptyList();
        }
        return list;
    }
}

