/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0005J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0019\u001a\u00020\u0005J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001d\u001a\u00020\u0005J\u001d\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u000b2\u0006\u0010\u0019\u001a\u00020\u0005H\u0002\u00a2\u0006\u0002\u0010\u001fJ\u0018\u0010 \u001a\u00020\u001b2\u000e\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\"H\u0002J\u000e\u0010 \u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u0006J\u0006\u0010$\u001a\u00020\u001bJ\u000e\u0010%\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u0006R-\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\"\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006&"}, d2={"Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "", "()V", "commands", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lkotlin/collections/HashMap;", "getCommands", "()Ljava/util/HashMap;", "latestAutoComplete", "", "getLatestAutoComplete", "()[Ljava/lang/String;", "setLatestAutoComplete", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "prefix", "", "getPrefix", "()C", "setPrefix", "(C)V", "autoComplete", "", "input", "executeCommands", "", "getCommand", "name", "getCompletions", "(Ljava/lang/String;)[Ljava/lang/String;", "registerCommand", "commandClass", "Ljava/lang/Class;", "command", "registerCommands", "unregisterCommand", "CrossSine"})
public final class CommandManager {
    @NotNull
    private final HashMap<String, Command> commands = new HashMap();
    @NotNull
    private String[] latestAutoComplete;
    private char prefix;

    public CommandManager() {
        boolean $i$f$emptyArray = false;
        this.latestAutoComplete = new String[0];
        this.prefix = (char)46;
    }

    @NotNull
    public final HashMap<String, Command> getCommands() {
        return this.commands;
    }

    @NotNull
    public final String[] getLatestAutoComplete() {
        return this.latestAutoComplete;
    }

    public final void setLatestAutoComplete(@NotNull String[] stringArray) {
        Intrinsics.checkNotNullParameter(stringArray, "<set-?>");
        this.latestAutoComplete = stringArray;
    }

    public final char getPrefix() {
        return this.prefix;
    }

    public final void setPrefix(char c) {
        this.prefix = c;
    }

    public final void registerCommands() {
        Iterable $this$forEach$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".commands"), Command.class);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Class p0 = (Class)element$iv;
            boolean bl = false;
            this.registerCommand(p0);
        }
    }

    public final void executeCommands(@NotNull String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        String[] stringArray = new String[]{" "};
        Collection $this$toTypedArray$iv = StringsKt.split$default((CharSequence)input, stringArray, false, 0, 6, null);
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray2 = thisCollection$iv.toArray(new String[0]);
        if (stringArray2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        String[] args2 = stringArray2;
        String string = args2[0].substring(1);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).substring(startIndex)");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        Command command = this.commands.get(string2);
        if (command != null) {
            command.execute(args2);
        } else {
            ClientUtils.INSTANCE.displayChatMessage("\u00a7cCommand not found. Type " + this.prefix + "help to view all commands.");
        }
    }

    public final boolean autoComplete(@NotNull String input) {
        String[] stringArray;
        Intrinsics.checkNotNullParameter(input, "input");
        String[] stringArray2 = this.getCompletions(input);
        if (stringArray2 == null) {
            boolean $i$f$emptyArray = false;
            stringArray = new String[]{};
        } else {
            stringArray = this.latestAutoComplete = stringArray2;
        }
        return StringsKt.startsWith$default((CharSequence)input, this.prefix, false, 2, null) && !(this.latestAutoComplete.length == 0);
    }

    /*
     * WARNING - void declaration
     */
    private final String[] getCompletions(String input) {
        if (((CharSequence)input).length() > 0) {
            Object[] objectArray = input.toCharArray();
            Intrinsics.checkNotNullExpressionValue(objectArray, "this as java.lang.String).toCharArray()");
            if (objectArray[0] == this.prefix) {
                String[] stringArray;
                objectArray = new String[1];
                objectArray[0] = (char)" ";
                List args2 = StringsKt.split$default((CharSequence)input, (String[])objectArray, false, 0, 6, null);
                if (args2.size() > 1) {
                    List<String> tabCompletions;
                    Collection thisCollection$iv;
                    boolean $i$f$toTypedArray;
                    Collection $this$toTypedArray$iv;
                    List<String> list;
                    Command command;
                    String string = ((String)args2.get(0)).substring(1);
                    Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).substring(startIndex)");
                    Command command2 = command = this.getCommand(string);
                    if (command2 == null) {
                        list = null;
                    } else {
                        $this$toTypedArray$iv = CollectionsKt.drop(args2, 1);
                        $i$f$toTypedArray = false;
                        thisCollection$iv = $this$toTypedArray$iv;
                        String[] stringArray2 = thisCollection$iv.toArray(new String[0]);
                        if (stringArray2 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                        }
                        list = command2.tabComplete(stringArray2);
                    }
                    List<String> list2 = tabCompletions = list;
                    if (list2 == null) {
                        stringArray = null;
                    } else {
                        $this$toTypedArray$iv = list2;
                        $i$f$toTypedArray = false;
                        thisCollection$iv = $this$toTypedArray$iv;
                        String[] stringArray3 = thisCollection$iv.toArray(new String[0]);
                        if (stringArray3 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                        }
                        stringArray = stringArray3;
                    }
                } else {
                    void $this$filterTo$iv$iv;
                    String it;
                    Iterable $this$mapTo$iv$iv;
                    Map $this$map$iv = this.commands;
                    boolean $i$f$map = false;
                    Map map = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList($this$map$iv.size());
                    boolean $i$f$mapTo = false;
                    Iterator iterator2 = $this$mapTo$iv$iv.entrySet().iterator();
                    while (iterator2.hasNext()) {
                        Map.Entry item$iv$iv;
                        Map.Entry entry = item$iv$iv = iterator2.next();
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        collection.add(Intrinsics.stringPlus(".", it.getKey()));
                    }
                    Iterable $this$filter$iv = (List)destination$iv$iv;
                    boolean $i$f$filter = false;
                    $this$mapTo$iv$iv = $this$filter$iv;
                    destination$iv$iv = new ArrayList();
                    boolean $i$f$filterTo = false;
                    for (Map.Entry element$iv$iv : $this$filterTo$iv$iv) {
                        it = (String)((Object)element$iv$iv);
                        boolean bl = false;
                        String string = it.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        String string2 = string;
                        string = ((String)args2.get(0)).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (!StringsKt.startsWith$default(string2, string, false, 2, null)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    Collection $this$toTypedArray$iv = (List)destination$iv$iv;
                    boolean $i$f$toTypedArray = false;
                    Collection thisCollection$iv = $this$toTypedArray$iv;
                    String[] stringArray4 = thisCollection$iv.toArray(new String[0]);
                    if (stringArray4 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                    }
                    stringArray = stringArray4;
                }
                return stringArray;
            }
        }
        return null;
    }

    @Nullable
    public final Command getCommand(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        String string = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return this.commands.get(string);
    }

    public final void registerCommand(@NotNull Command command) {
        Intrinsics.checkNotNullParameter(command, "command");
        Map map = this.commands;
        String[] stringArray = command.getCommand().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(stringArray, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String[] stringArray2 = stringArray;
        map.put(stringArray2, command);
        String[] $this$forEach$iv = command.getAlias();
        boolean $i$f$forEach = false;
        for (String element$iv : $this$forEach$iv) {
            String it = element$iv;
            boolean bl = false;
            Map map2 = this.getCommands();
            String string = it.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String string2 = string;
            map2.put(string2, command);
        }
    }

    private final void registerCommand(Class<? extends Command> commandClass) {
        try {
            Command command = commandClass.newInstance();
            Intrinsics.checkNotNullExpressionValue(command, "commandClass.newInstance()");
            this.registerCommand(command);
        }
        catch (Throwable e) {
            ClientUtils.INSTANCE.logError("Failed to load command: " + commandClass.getName() + " (" + e.getClass().getName() + ": " + e.getMessage() + ')');
        }
    }

    public final void unregisterCommand(@NotNull Command command) {
        Intrinsics.checkNotNullParameter(command, "command");
        Iterable $this$forEach$iv = MapsKt.toList((Map)this.commands);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Pair it = (Pair)element$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual(it.getSecond(), command)) continue;
            this.getCommands().remove(it.getFirst());
        }
    }
}

