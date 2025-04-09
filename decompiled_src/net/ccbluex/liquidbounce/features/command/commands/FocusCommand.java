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
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/FocusCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class FocusCommand
extends Command {
    public FocusCommand() {
        boolean $i$f$emptyArray = false;
        super("focus", new String[0]);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length == 3) {
            void $this$filterTo$iv$iv;
            String focused = args2[1];
            String target = args2[2];
            Object object = MinecraftInstance.mc.field_71441_e.field_73010_i;
            Intrinsics.checkNotNullExpressionValue(object, "mc.theWorld.playerEntities");
            Iterable $this$filter$iv = (Iterable)object;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                EntityPlayer it = (EntityPlayer)element$iv$iv;
                boolean bl = false;
                if (!(StringsKt.equals(it.func_70005_c_(), target, true) && !it.equals((Object)MinecraftInstance.mc.field_71439_g))) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            object = (List)destination$iv$iv;
            List it = object;
            boolean bl = false;
            if (it.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder().append("\u00a76Couldn't find anyone named \u00a7a");
                String string = target.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                this.alert(stringBuilder.append(string).append("\u00a76 in the world.").toString());
                return;
            }
            EntityPlayer entity = (EntityPlayer)object.get(0);
            String string = focused.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            object = string;
            if (Intrinsics.areEqual(object, "add")) {
                List<EntityPlayer> list = CrossSine.INSTANCE.getCombatManager().getFocusedPlayerList();
                Intrinsics.checkNotNullExpressionValue(entity, "entity");
                list.add(entity);
                StringBuilder stringBuilder = new StringBuilder().append("Successfully added \u00a7a");
                string = target.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                this.alert(stringBuilder.append(string).append("\u00a73 into the focus list.").toString());
                return;
            }
            if (Intrinsics.areEqual(object, "remove")) {
                if (CrossSine.INSTANCE.getCombatManager().getFocusedPlayerList().contains(entity)) {
                    CrossSine.INSTANCE.getCombatManager().getFocusedPlayerList().remove(entity);
                    StringBuilder stringBuilder = new StringBuilder().append("Successfully removed \u00a7a");
                    string = target.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    this.alert(stringBuilder.append(string).append("\u00a73 from the focus list.").toString());
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder().append("\u00a76Couldn't find anyone named \u00a7a");
                string = target.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                this.alert(stringBuilder.append(string).append("\u00a76 in the focus list.").toString());
                return;
            }
        } else if (args2.length == 2 && StringsKt.equals(args2[1], "clear", true)) {
            CrossSine.INSTANCE.getCombatManager().getFocusedPlayerList().clear();
            this.alert("Successfully cleared the focus list.");
            return;
        }
        this.chatSyntax("focus <clear/add/remove> <target name>");
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
        String pref = args2[0];
        switch (args2.length) {
            case 1: {
                String[] stringArray = new String[]{"clear", "add", "remove"};
                list = CollectionsKt.listOf(stringArray);
                break;
            }
            case 2: {
                if (StringsKt.equals(args2[0], "add", true) || StringsKt.equals(args2[0], "remove", true)) {
                    void $this$mapTo$iv$iv;
                    EntityPlayer it;
                    Iterable $this$filterTo$iv$iv;
                    List list2 = MinecraftInstance.mc.field_71441_e.field_73010_i;
                    Intrinsics.checkNotNullExpressionValue(list2, "mc.theWorld.playerEntities");
                    Iterable $this$filter$iv = list2;
                    boolean $i$f$filter = false;
                    Iterable iterable = $this$filter$iv;
                    Collection destination$iv$iv = new ArrayList();
                    boolean $i$f$filterTo = false;
                    for (Object element$iv$iv : $this$filterTo$iv$iv) {
                        it = (EntityPlayer)element$iv$iv;
                        boolean bl = false;
                        String string = it.func_70005_c_();
                        Intrinsics.checkNotNullExpressionValue(string, "it.name");
                        if (!StringsKt.startsWith(string, pref, true)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    Iterable $this$map$iv = (List)destination$iv$iv;
                    boolean $i$f$map = false;
                    $this$filterTo$iv$iv = $this$map$iv;
                    destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        it = (EntityPlayer)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        collection.add(it.func_70005_c_());
                    }
                    list = CollectionsKt.toList((List)destination$iv$iv);
                    break;
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

