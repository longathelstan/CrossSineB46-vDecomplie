/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command.commands;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
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
import net.ccbluex.liquidbounce.file.config.ConfigManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/ConfigCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class ConfigCommand
extends Command {
    public ConfigCommand() {
        String[] stringArray = new String[]{"cfg"};
        super("config", stringArray);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void execute(@NotNull String[] args2) {
        block58: {
            Intrinsics.checkNotNullParameter(args2, "args");
            if (args2.length <= 1) break block58;
            File[] fileArray = args2[1].toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(fileArray, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (fileArray) {
                case "create": {
                    if (args2.length > 2) {
                        File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args2[2], ".json"));
                        if (!file.exists()) {
                            CrossSine.INSTANCE.getConfigManager().load(args2[2], true);
                            this.alert(Intrinsics.stringPlus("Created config ", args2[2]));
                            return;
                        }
                        this.alert("Config " + args2[2] + " already exists");
                        return;
                    }
                    this.chatSyntax(Intrinsics.stringPlus(args2[1], " <configName>"));
                    return;
                }
                case "load": 
                case "forceload": {
                    if (args2.length > 2) {
                        File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args2[2], ".json"));
                        if (file.exists()) {
                            CrossSine.INSTANCE.getConfigManager().load(args2[2], StringsKt.equals(args2[1], "load", true));
                            this.alert(Intrinsics.stringPlus("Loaded config ", args2[2]));
                            return;
                        }
                        this.alert("Config " + args2[2] + " does not exist");
                        return;
                    }
                    this.chatSyntax(Intrinsics.stringPlus(args2[1], " <configName>"));
                    return;
                }
                case "delete": {
                    if (args2.length > 2) {
                        File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args2[2], ".json"));
                        if (file.exists()) {
                            file.delete();
                            this.alert(Intrinsics.stringPlus("Successfully deleted config ", args2[2]));
                            return;
                        }
                        this.alert("Config " + args2[2] + " does not exist");
                        return;
                    }
                    this.chatSyntax(Intrinsics.stringPlus(args2[1], " <configName>"));
                    return;
                }
                case "openfolder": {
                    Desktop.getDesktop().open(CrossSine.INSTANCE.getFileManager().getConfigsDir());
                    return;
                }
                case "list": {
                    void $this$mapTo$iv$iv;
                    Iterator $this$map$iv;
                    void $this$filterTo$iv$iv;
                    Iterable $this$filter$iv;
                    File[] fileArray2 = CrossSine.INSTANCE.getFileManager().getConfigsDir().listFiles();
                    if (fileArray2 == null) {
                        return;
                    }
                    fileArray = fileArray2;
                    boolean $i$f$filter = false;
                    void var6_13 = $this$filter$iv;
                    Collection destination$iv$iv = new ArrayList();
                    boolean $i$f$filterTo = false;
                    for (void element$iv$iv : $this$filterTo$iv$iv) {
                        void it = element$iv$iv;
                        boolean bl = false;
                        if (!it.isFile()) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    $this$filter$iv = (List)destination$iv$iv;
                    boolean $i$f$map = false;
                    $this$filterTo$iv$iv = $this$map$iv;
                    destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        String string;
                        void it;
                        File file = (File)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        String name = it.getName();
                        Intrinsics.checkNotNullExpressionValue(name, "name");
                        if (StringsKt.endsWith$default(name, ".json", false, 2, null)) {
                            String string2 = name.substring(0, name.length() - 5);
                            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                            string = string2;
                        } else {
                            string = name;
                        }
                        collection.add(string);
                    }
                    List list = (List)destination$iv$iv;
                    this.alert("Configs(" + list.size() + "):");
                    for (String file : list) {
                        if (file.equals(CrossSine.INSTANCE.getConfigManager().getNowConfig())) {
                            this.alert(Intrinsics.stringPlus("-> \u00a7a\u00a7l", file));
                            continue;
                        }
                        this.alert(Intrinsics.stringPlus("> ", file));
                    }
                    return;
                }
                case "save": {
                    ConfigManager.save$default(CrossSine.INSTANCE.getConfigManager(), true, true, false, 4, null);
                    this.alert(Intrinsics.stringPlus("Saved config ", CrossSine.INSTANCE.getConfigManager().getNowConfig()));
                    return;
                }
                case "reload": {
                    CrossSine.INSTANCE.getConfigManager().load(CrossSine.INSTANCE.getConfigManager().getNowConfig(), false);
                    this.alert(Intrinsics.stringPlus("Reloaded config ", CrossSine.INSTANCE.getConfigManager().getNowConfig()));
                    return;
                }
                case "rename": {
                    File newFile;
                    if (args2.length > 3) {
                        File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args2[2], ".json"));
                        newFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args2[3], ".json"));
                        if (file.exists() && !newFile.exists()) {
                            file.renameTo(newFile);
                            this.alert("Renamed config " + args2[2] + " to " + args2[3]);
                        } else if (!file.exists()) {
                            this.alert("Config " + args2[2] + " does not exist");
                        } else if (newFile.exists()) {
                            this.alert("Config " + args2[3] + " already exists");
                        }
                        if (!StringsKt.equals(CrossSine.INSTANCE.getConfigManager().getNowConfig(), args2[2], true)) return;
                        CrossSine.INSTANCE.getConfigManager().load(args2[3], false);
                        CrossSine.INSTANCE.getConfigManager().saveConfigSet();
                        return;
                    }
                    this.chatSyntax(Intrinsics.stringPlus(args2[1], " <configName> <newName>"));
                    return;
                }
                case "current": {
                    this.alert(Intrinsics.stringPlus("Current config is ", CrossSine.INSTANCE.getConfigManager().getNowConfig()));
                    return;
                }
                case "copy": {
                    File newFile;
                    if (args2.length > 3) {
                        File file = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args2[2], ".json"));
                        newFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(args2[3], ".json"));
                        if (file.exists() && !newFile.exists()) {
                            Files.copy(file.toPath(), newFile.toPath(), new CopyOption[0]);
                            this.alert("Copied config " + args2[2] + " to " + args2[3]);
                            return;
                        }
                        if (!file.exists()) {
                            this.alert("Config " + args2[2] + " does not exist");
                            return;
                        }
                        if (!newFile.exists()) return;
                        this.alert("Config " + args2[3] + " already exists");
                        return;
                    }
                    this.chatSyntax(Intrinsics.stringPlus(args2[1], " <configName> <newName>"));
                }
                default: {
                    return;
                }
            }
        }
        String[] stringArray = new String[]{"current", "copy <configName> <newName>", "create <configName>", "load <configName>", "forceload <configName>", "delete <configName>", "rename <configName> <newName>", "reload", "list", "openfolder", "save"};
        this.chatSyntax(stringArray);
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
        block0 : switch (args2.length) {
            case 1: {
                void $this$filterTo$iv$iv;
                String[] stringArray = new String[]{"current", "copy", "create", "load", "forceload", "delete", "rename", "reload", "list", "openfolder", "save"};
                Iterable $this$filter$iv = CollectionsKt.listOf(stringArray);
                boolean $i$f$filter = false;
                Iterable iterable = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv) {
                    String string = (String)element$iv$iv;
                    boolean bl22 = false;
                    if (!StringsKt.startsWith(string, args2[0], true)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                list = (List)destination$iv$iv;
                break;
            }
            case 2: {
                String $this$filterTo$iv$iv = args2[0].toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue($this$filterTo$iv$iv, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                switch ($this$filterTo$iv$iv) {
                    case "load": 
                    case "rename": 
                    case "copy": 
                    case "delete": 
                    case "forceload": {
                        void $this$filterTo$iv$iv2;
                        Object $this$mapTo$iv$iv;
                        void var9_19;
                        Iterable $this$filterTo$iv$iv22;
                        File[] fileArray = CrossSine.INSTANCE.getFileManager().getConfigsDir().listFiles();
                        if (fileArray == null) {
                            return CollectionsKt.emptyList();
                        }
                        Object $this$filter$iv = fileArray;
                        boolean $i$f$filter = false;
                        File[] destination$iv$iv = $this$filter$iv;
                        Collection destination$iv$iv2 = new ArrayList();
                        boolean $i$f$filterTo = false;
                        Iterator iterator2 = $this$filterTo$iv$iv22;
                        boolean bl = false;
                        int bl22 = ((void)iterator2).length;
                        while (var9_19 < bl22) {
                            void element$iv$iv = iterator2[var9_19];
                            ++var9_19;
                            void it = element$iv$iv;
                            boolean bl3 = false;
                            if (!it.isFile()) continue;
                            destination$iv$iv2.add(element$iv$iv);
                        }
                        Iterable $this$map$iv = (List)destination$iv$iv2;
                        boolean $i$f$map = false;
                        $this$filterTo$iv$iv22 = $this$map$iv;
                        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        boolean $i$f$mapTo = false;
                        for (Object t : $this$mapTo$iv$iv) {
                            String string;
                            void it;
                            File bl22 = (File)t;
                            Collection collection = destination$iv$iv3;
                            boolean bl23 = false;
                            String name = it.getName();
                            Intrinsics.checkNotNullExpressionValue(name, "name");
                            if (StringsKt.endsWith$default(name, ".json", false, 2, null)) {
                                String string2 = name.substring(0, name.length() - 5);
                                Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                                string = string2;
                            } else {
                                string = name;
                            }
                            collection.add(string);
                        }
                        $this$filter$iv = (List)destination$iv$iv3;
                        $i$f$filter = false;
                        $this$mapTo$iv$iv = $this$filter$iv;
                        destination$iv$iv3 = new ArrayList();
                        $i$f$filterTo = false;
                        for (Object t : $this$filterTo$iv$iv2) {
                            String it = (String)t;
                            boolean bl4 = false;
                            Intrinsics.checkNotNullExpressionValue(it, "it");
                            if (!StringsKt.startsWith(it, args2[1], true)) continue;
                            destination$iv$iv3.add(t);
                        }
                        list = (List)destination$iv$iv3;
                        break block0;
                    }
                }
                list = CollectionsKt.emptyList();
                break;
            }
            default: {
                list = CollectionsKt.emptyList();
            }
        }
        return list;
    }
}

