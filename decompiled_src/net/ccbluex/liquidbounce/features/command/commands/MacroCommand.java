/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0004H\u0002\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/MacroCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "save", "CrossSine"})
public final class MacroCommand
extends Command {
    public MacroCommand() {
        String[] stringArray = new String[]{"m"};
        super("macro", stringArray);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void execute(@NotNull String[] args2) {
        block24: {
            Intrinsics.checkNotNullParameter(args2, "args");
            if (args2.length <= 1) break block24;
            String arg1 = args2[1];
            String string = arg1.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "add": {
                    if (args2.length > 3) {
                        String string2 = args2[2].toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                        int key = Keyboard.getKeyIndex((String)string2);
                        if (key != 0) {
                            String comm;
                            string2 = comm = StringUtils.toCompleteString(args2, 3);
                            Intrinsics.checkNotNullExpressionValue(string2, "comm");
                            if (!StringsKt.startsWith$default(string2, ".", false, 2, null)) {
                                comm = Intrinsics.stringPlus(".", comm);
                            }
                            ArrayList<Macro> arrayList = CrossSine.INSTANCE.getMacroManager().getMacros();
                            string2 = comm;
                            Intrinsics.checkNotNullExpressionValue(string2, "comm");
                            arrayList.add(new Macro(key, string2));
                            this.alert("Bound macro " + comm + " to key " + Keyboard.getKeyName((int)key) + '.');
                        } else {
                            this.alert("Unknown key to bind macro.");
                        }
                        this.save();
                        break;
                    }
                    this.chatSyntax("macro add <key> <macro>");
                    break;
                }
                case "remove": {
                    if (args2.length > 2) {
                        void $this$forEach$iv;
                        List list;
                        Iterable destination$iv$iv;
                        if (StringsKt.startsWith$default(args2[2], ".", false, 2, null)) {
                            void $this$filterTo$iv$iv2;
                            Iterable $this$filter$iv = CrossSine.INSTANCE.getMacroManager().getMacros();
                            boolean $i$f$filter = false;
                            Iterable iterable = $this$filter$iv;
                            destination$iv$iv = new ArrayList();
                            boolean $i$f$filterTo = false;
                            for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                                Macro it = (Macro)element$iv$iv;
                                boolean bl = false;
                                if (!Intrinsics.areEqual(it.getCommand(), StringUtils.toCompleteString(args2, 2))) continue;
                                destination$iv$iv.add(element$iv$iv);
                            }
                            list = (List)destination$iv$iv;
                        } else {
                            void $this$filterTo$iv$iv;
                            String $this$filterTo$iv$iv2 = args2[2].toUpperCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue($this$filterTo$iv$iv2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                            int key = Keyboard.getKeyIndex((String)$this$filterTo$iv$iv2);
                            Iterable $this$filter$iv = CrossSine.INSTANCE.getMacroManager().getMacros();
                            boolean $i$f$filter = false;
                            destination$iv$iv = $this$filter$iv;
                            Collection destination$iv$iv2 = new ArrayList();
                            boolean $i$f$filterTo = false;
                            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                                Macro it = (Macro)element$iv$iv;
                                boolean bl = false;
                                if (!(it.getKey() == key)) continue;
                                destination$iv$iv2.add(element$iv$iv);
                            }
                            list = (List)destination$iv$iv2;
                        }
                        Iterable key = list;
                        boolean $i$f$forEach = false;
                        for (Object element$iv : $this$forEach$iv) {
                            Macro it = (Macro)element$iv;
                            boolean bl = false;
                            CrossSine.INSTANCE.getMacroManager().getMacros().remove(it);
                            this.alert("Remove macro " + it.getCommand() + '.');
                        }
                        this.save();
                        break;
                    }
                    this.chatSyntax("macro remove <macro/key>");
                    break;
                }
                case "list": {
                    this.alert("Macros:");
                    Iterable $this$forEach$iv = CrossSine.INSTANCE.getMacroManager().getMacros();
                    boolean $i$f$forEach = false;
                    for (Object element$iv : $this$forEach$iv) {
                        Macro it = (Macro)element$iv;
                        boolean bl = false;
                        this.alert("key=" + Keyboard.getKeyName((int)it.getKey()) + ", command=" + it.getCommand());
                    }
                    break;
                }
                default: {
                    this.chatSyntax("macro <add/remove/list>");
                }
            }
            return;
        }
        this.chatSyntax("macro <add/remove/list>");
    }

    private final void save() {
        CrossSine.INSTANCE.getConfigManager().smartSave();
        this.playEdit();
    }
}

