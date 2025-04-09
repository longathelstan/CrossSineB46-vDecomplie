/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/PanicCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class PanicCommand
extends Command {
    public PanicCommand() {
        boolean $i$f$emptyArray = false;
        super("panic", new String[0]);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void execute(@NotNull String[] args2) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(args2, "args");
        Iterable $this$filter$iv = CrossSine.INSTANCE.getModuleManager().getModules();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Iterator destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo2 = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Module it = (Module)element$iv$iv;
            boolean bl = false;
            if (!it.getState()) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List modules = (List)((Object)destination$iv$iv);
        String msg = null;
        if (args2.length > 1 && ((CharSequence)args2[1]).length() > 0) {
            Object destination$iv$iv2;
            destination$iv$iv = args2[1].toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(destination$iv$iv, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            Iterator iterator2 = destination$iv$iv;
            if (Intrinsics.areEqual(iterator2, "all")) {
                msg = "all";
            } else if (Intrinsics.areEqual(iterator2, "nonrender")) {
                void $this$filterTo$iv$iv2;
                Iterable $this$filter$iv2 = modules;
                boolean $i$f$filter2 = false;
                Iterable $i$f$filterTo2 = $this$filter$iv2;
                destination$iv$iv2 = new ArrayList();
                boolean $i$f$filterTo3 = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                    Module it = (Module)element$iv$iv;
                    boolean bl = false;
                    if (!(it.getCategory() != ModuleCategory.VISUAL)) continue;
                    destination$iv$iv2.add(element$iv$iv);
                }
                modules = (List)destination$iv$iv2;
                msg = "all non-render";
            } else {
                void $this$filterTo$iv$iv3;
                void $this$filterTo$iv$iv4;
                ModuleCategory[] $this$filter$iv3 = ModuleCategory.values();
                boolean $i$f$filter3 = false;
                destination$iv$iv2 = $this$filter$iv3;
                Iterable destination$iv$iv3 = new ArrayList();
                boolean $i$f$filterTo4 = false;
                for (void element$iv$iv : $this$filterTo$iv$iv4) {
                    void it = element$iv$iv;
                    boolean bl = false;
                    if (!StringsKt.equals(it.name(), args2[1], true)) continue;
                    destination$iv$iv3.add(element$iv$iv);
                }
                List categories = (List)destination$iv$iv3;
                if (categories.isEmpty()) {
                    this.chat("Category " + args2[1] + " not found");
                    return;
                }
                ModuleCategory category = (ModuleCategory)((Object)categories.get(0));
                Iterable $this$filter$iv4 = modules;
                boolean $i$f$filter4 = false;
                destination$iv$iv3 = $this$filter$iv4;
                Collection destination$iv$iv4 = new ArrayList();
                boolean $i$f$filterTo5 = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv3) {
                    Module it = (Module)element$iv$iv;
                    boolean bl = false;
                    if (!(it.getCategory() == category)) continue;
                    destination$iv$iv4.add(element$iv$iv);
                }
                modules = (List)destination$iv$iv4;
                msg = Intrinsics.stringPlus("all ", category.name());
            }
        } else {
            this.chatSyntax("panic <all/client/combat/movement/player/visual/world>");
            return;
        }
        for (Module module : modules) {
            module.setState(false);
        }
        this.chat("Disabled " + msg + " modules.");
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
            String[] stringArray = new String[]{"all", "client", "combat", "player", "movement", "visual", "world"};
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

