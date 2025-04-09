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
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/FriendCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class FriendCommand
extends Command {
    public FriendCommand() {
        String[] stringArray = new String[]{"friends"};
        super("friend", stringArray);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public void execute(@NotNull String[] args) {
        block23: {
            block24: {
                block25: {
                    Intrinsics.checkNotNullParameter(args, "args");
                    if (args.length <= 1) break block23;
                    friendsConfig = CrossSine.INSTANCE.getFileManager().getFriendsConfig();
                    if (StringsKt.equals(args[1], "add", true)) {
                        if (args.length > 2) {
                            name = args[2];
                            if (((CharSequence)name).length() == 0) {
                                this.alert("The name is empty.");
                                return;
                            }
                            if (args.length > 3) {
                                var4_9 = StringUtils.toCompleteString(args, 3);
                                Intrinsics.checkNotNullExpressionValue(var4_9, "toCompleteString(args, 3)");
                                v0 = friendsConfig.addFriend(name, var4_9);
                            } else {
                                v0 = FriendsConfig.addFriend$default(friendsConfig, name, null, 2, null);
                            }
                            if (v0) {
                                CrossSine.INSTANCE.getFileManager().saveConfig(friendsConfig);
                                this.alert("\u00a7a\u00a7l" + name + "\u00a73 was added to your friend list.");
                                this.playEdit();
                            } else {
                                this.alert("The name is already in the list.");
                            }
                            return;
                        }
                        this.chatSyntax("friend add <name> [alias]");
                        return;
                    }
                    if (!StringsKt.equals(args[1], "addall", true)) break block24;
                    if (args.length != 3) break block25;
                    regex = args[2];
                    coloredRegex = ColorUtils.translateAlternateColorCodes(regex);
                    added = 0;
                    var6_15 /* !! */  = MinecraftInstance.mc.field_71441_e.field_73010_i;
                    Intrinsics.checkNotNullExpressionValue(var6_15 /* !! */ , "mc.theWorld.playerEntities");
                    var6_15 /* !! */  = var6_15 /* !! */ ;
                    $i$f$filter = false;
                    var8_19 = $this$filter$iv;
                    destination$iv$iv = new ArrayList<E>();
                    $i$f$filterTo = false;
                    var11_28 = $this$filterTo$iv$iv.iterator();
                    while (var11_28.hasNext()) {
                        element$iv$iv = var11_28.next();
                        it = (EntityPlayer)element$iv$iv;
                        $i$a$-filter-FriendCommand$execute$1 = false;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        if (AntiBot.isBot((EntityLivingBase)it)) ** GOTO lbl-1000
                        var15_39 = it.func_145748_c_().func_150254_d();
                        Intrinsics.checkNotNullExpressionValue(var15_39, "it.displayName.formattedText");
                        if (StringsKt.contains((CharSequence)var15_39, coloredRegex, false)) {
                            v1 = true;
                        } else lbl-1000:
                        // 2 sources

                        {
                            v1 = false;
                        }
                        if (!v1) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    $this$filter$iv = (List)destination$iv$iv;
                    $i$f$forEach = false;
                    for (T element$iv : $this$forEach$iv) {
                        it = (EntityPlayer)element$iv;
                        $i$a$-forEach-FriendCommand$execute$2 = false;
                        element$iv$iv = it.func_70005_c_();
                        Intrinsics.checkNotNullExpressionValue(element$iv$iv, "it.name");
                        if (!FriendsConfig.addFriend$default(friendsConfig, element$iv$iv, null, 2, null)) continue;
                        element$iv$iv = added;
                        added = element$iv$iv + 1;
                    }
                    this.alert("Added \u00a7a\u00a7l" + added + " \u00a73players matching the same regex to your friend list.");
                    this.playEdit();
                    return;
                }
                this.chatSyntax("friend addall <colored regex>");
                return;
            }
            if (StringsKt.equals(args[1], "removeall", true)) {
                if (args.length == 3) {
                    regex = args[2];
                    remove = 0;
                    added = friendsConfig.getFriends();
                    $i$f$map = false;
                    $i$f$forEach = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        element$iv$iv = (FriendsConfig.Friend)item$iv$iv;
                        var16_40 = destination$iv$iv;
                        $i$a$-map-FriendCommand$execute$3 = false;
                        var16_40.add(it.getPlayerName());
                    }
                    $this$map$iv = (List)destination$iv$iv;
                    $i$f$filter = false;
                    $this$mapTo$iv$iv = $this$filter$iv;
                    destination$iv$iv = new ArrayList<E>();
                    $i$f$filterTo = false;
                    for (T element$iv$iv : $this$filterTo$iv$iv) {
                        it = (String)element$iv$iv;
                        $i$a$-filter-FriendCommand$execute$4 = false;
                        if (!StringsKt.contains((CharSequence)it, regex, false)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    $this$filter$iv = (List)destination$iv$iv;
                    $i$f$forEach = false;
                    for (T element$iv : $this$forEach$iv) {
                        it = (String)element$iv;
                        $i$a$-forEach-FriendCommand$execute$5 = false;
                        if (!friendsConfig.removeFriend(it)) continue;
                        var11_31 = remove;
                        remove = var11_31 + 1;
                    }
                    this.alert("Removed \u00a7a\u00a7l" + remove + " \u00a73players matching the same regex from your friend list.");
                    this.playEdit();
                    return;
                }
                this.chatSyntax("friend removeall <regex>");
                return;
            }
            if (StringsKt.equals(args[1], "remove", true)) {
                if (args.length > 2) {
                    name = args[2];
                    if (friendsConfig.removeFriend(name)) {
                        CrossSine.INSTANCE.getFileManager().saveConfig(friendsConfig);
                        this.alert("\u00a7a\u00a7l" + name + "\u00a73 was removed from your friend list.");
                        this.playEdit();
                    } else {
                        this.alert("This name is not in the list.");
                    }
                    return;
                }
                this.chatSyntax("friend remove <name>");
                return;
            }
            if (StringsKt.equals(args[1], "clear", true)) {
                friends = friendsConfig.getFriends().size();
                friendsConfig.clearFriends();
                CrossSine.INSTANCE.getFileManager().saveConfig(friendsConfig);
                this.alert("Removed " + friends + " friend(s).");
                return;
            }
            if (StringsKt.equals(args[1], "list", true)) {
                this.alert("Your Friends:");
                for (FriendsConfig.Friend friend : friendsConfig.getFriends()) {
                    this.alert("\u00a77> \u00a7a\u00a7l" + friend.getPlayerName() + " \u00a7c(\u00a77\u00a7l" + friend.getAlias() + "\u00a7c)");
                }
                this.alert("You have \u00a7c" + friendsConfig.getFriends().size() + "\u00a73 friends.");
                return;
            }
        }
        this.chatSyntax("friend <add/addall/removeall/list/clear>");
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
                String[] stringArray = new String[]{"add", "addall", "remove", "removeall", "list", "clear"};
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
                break;
            }
            case 2: {
                String $this$filterTo$iv$iv = args2[0].toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue($this$filterTo$iv$iv, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                String string = $this$filterTo$iv$iv;
                if (Intrinsics.areEqual(string, "add")) {
                    void $this$filterTo$iv$iv2;
                    String it;
                    Iterable $this$mapTo$iv$iv;
                    List $i$f$filter = MinecraftInstance.mc.field_71441_e.field_73010_i;
                    Intrinsics.checkNotNullExpressionValue($i$f$filter, "mc.theWorld.playerEntities");
                    Iterable $this$map$iv = $i$f$filter;
                    boolean $i$f$map = false;
                    Iterable destination$iv$iv = $this$map$iv;
                    Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        EntityPlayer bl = (EntityPlayer)item$iv$iv;
                        Collection collection = destination$iv$iv2;
                        boolean bl2 = false;
                        collection.add(it.func_70005_c_());
                    }
                    Iterable $this$filter$iv = (List)destination$iv$iv2;
                    boolean $i$f$filter2 = false;
                    $this$mapTo$iv$iv = $this$filter$iv;
                    destination$iv$iv2 = new ArrayList();
                    boolean $i$f$filterTo = false;
                    for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                        it = (String)element$iv$iv;
                        boolean bl = false;
                        Intrinsics.checkNotNullExpressionValue(it, "it");
                        if (!StringsKt.startsWith(it, args2[1], true)) continue;
                        destination$iv$iv2.add(element$iv$iv);
                    }
                    return (List)destination$iv$iv2;
                }
                if (Intrinsics.areEqual(string, "remove")) {
                    Object it;
                    Iterable $this$mapTo$iv$iv;
                    Iterable $this$map$iv = CrossSine.INSTANCE.getFileManager().getFriendsConfig().getFriends();
                    boolean $i$f$map = false;
                    Iterable $this$filterTo$iv$iv2 = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        it = (FriendsConfig.Friend)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        collection.add(((FriendsConfig.Friend)it).getPlayerName());
                    }
                    Iterable $this$filter$iv = (List)destination$iv$iv;
                    boolean $i$f$filter = false;
                    $this$mapTo$iv$iv = $this$filter$iv;
                    destination$iv$iv = new ArrayList();
                    boolean $i$f$filterTo = false;
                    for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                        it = (String)element$iv$iv;
                        boolean bl = false;
                        if (!StringsKt.startsWith((String)it, args2[1], true)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    return (List)destination$iv$iv;
                }
                return CollectionsKt.emptyList();
            }
            default: {
                list = CollectionsKt.emptyList();
            }
        }
        return list;
    }
}

