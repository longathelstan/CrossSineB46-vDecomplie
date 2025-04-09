/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0002\u0010\nJ!\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0002\u0010\rR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/AutoDisableCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "modes", "", "", "[Ljava/lang/String;", "execute", "", "args", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class AutoDisableCommand
extends Command {
    @NotNull
    private final String[] modes;

    /*
     * WARNING - void declaration
     */
    public AutoDisableCommand() {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Object[] objectArray = new String[]{"ad"};
        super("autodisable", (String[])objectArray);
        objectArray = EnumAutoDisableType.values();
        AutoDisableCommand autoDisableCommand = this;
        boolean $i$f$map = false;
        void var3_4 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        for (void item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            void var10_11 = item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            String string = it.name().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            collection.add(string);
        }
        Collection $this$toTypedArray$iv = (List)destination$iv$iv;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray = thisCollection$iv.toArray(new String[0]);
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        autoDisableCommand.modes = stringArray;
    }

    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length > 2) {
            EnumAutoDisableType enumAutoDisableType;
            Module module;
            Module module2 = CrossSine.INSTANCE.getModuleManager().getModule(args2[1]);
            if (module2 == null) {
                this.alert("Module '" + args2[1] + "' not found.");
                return;
            }
            Module module3 = module2;
            try {
                module = module3;
                String string = args2[2].toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                enumAutoDisableType = EnumAutoDisableType.valueOf(string);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                module = module3;
                enumAutoDisableType = EnumAutoDisableType.NONE;
            }
            module.setAutoDisable(enumAutoDisableType);
            this.playEdit();
            this.alert("Set module \u00a7l" + module2.getName() + "\u00a7r AutoDisable state to \u00a7l" + (Object)((Object)module2.getAutoDisable()) + "\u00a7r.");
            return;
        }
        this.chatSyntax("autodisable <module> [" + StringUtils.toCompleteString(this.modes, 0, ",") + ']');
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
        switch (args2.length) {
            case 1: {
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
                    if (!StringsKt.startsWith(it, args2[0], true)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                list = CollectionsKt.toList((List)destination$iv$iv);
                break;
            }
            case 2: {
                String[] $this$filter$iv = this.modes;
                boolean $i$f$filter = false;
                String[] $this$filterTo$iv$iv = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo = false;
                for (String element$iv$iv : $this$filterTo$iv$iv) {
                    String it = element$iv$iv;
                    boolean bl = false;
                    if (!StringsKt.startsWith(it, args2[1], true)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                list = (List)destination$iv$iv;
                break;
            }
            default: {
                list = CollectionsKt.emptyList();
            }
        }
        return list;
    }
}

