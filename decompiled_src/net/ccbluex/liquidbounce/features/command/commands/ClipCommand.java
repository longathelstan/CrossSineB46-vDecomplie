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
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/ClipCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class ClipCommand
extends Command {
    public ClipCommand() {
        boolean $i$f$emptyArray = false;
        super("clip", new String[0]);
    }

    @Override
    public void execute(@NotNull String[] args2) {
        block15: {
            block17: {
                int n;
                double dist;
                block18: {
                    String string;
                    block16: {
                        Intrinsics.checkNotNullParameter(args2, "args");
                        if (args2.length <= 2) break block15;
                        dist = 0.0;
                        try {
                            dist = Double.parseDouble(args2[2]);
                        }
                        catch (NumberFormatException e) {
                            this.chatSyntaxError();
                            return;
                        }
                        String string2 = args2[1].toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        string = string2;
                        if (!Intrinsics.areEqual(string, "up")) break block16;
                        MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + dist, MinecraftInstance.mc.field_71439_g.field_70161_v);
                        break block17;
                    }
                    if (!Intrinsics.areEqual(string, "down")) break block18;
                    MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - dist, MinecraftInstance.mc.field_71439_g.field_70161_v);
                    break block17;
                }
                double d = MinecraftInstance.mc.field_71439_g.field_70177_z;
                String string = args2[1].toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                switch (string) {
                    case "right": {
                        n = 90;
                        break;
                    }
                    case "back": {
                        n = 180;
                        break;
                    }
                    case "left": {
                        n = 270;
                        break;
                    }
                    default: {
                        n = 0;
                    }
                }
                double yaw = Math.toRadians(d + (double)n);
                double x = -Math.sin(yaw) * dist;
                double z = Math.cos(yaw) * dist;
                MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t + x, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v + z);
            }
            return;
        }
        this.chatSyntax("clip <up/down/forward/back/left/right> <dist>");
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
            String[] stringArray = new String[]{"up", "down", "forward", "back", "left", "right"};
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

