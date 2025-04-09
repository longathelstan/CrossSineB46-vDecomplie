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
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/command/commands/TpCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public final class TpCommand
extends Command {
    public TpCommand() {
        boolean $i$f$emptyArray = false;
        super("tp", new String[0]);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void execute(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        if (args2.length == 2) {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            String theName = args2[1];
            Iterable iterable = MinecraftInstance.mc.field_71441_e.field_73010_i;
            Intrinsics.checkNotNullExpressionValue(iterable, "mc.theWorld.playerEntities");
            iterable = iterable;
            boolean $i$f$filter = false;
            void var6_8 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                EntityPlayer it = (EntityPlayer)element$iv$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                if (!(!AntiBot.isBot((EntityLivingBase)it) && StringsKt.equals(it.func_70005_c_(), theName, true))) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            EntityPlayer targetPlayer = (EntityPlayer)CollectionsKt.firstOrNull((List)destination$iv$iv);
            if (targetPlayer != null) {
                MinecraftInstance.mc.field_71439_g.func_70634_a(targetPlayer.field_70165_t, targetPlayer.field_70163_u, targetPlayer.field_70161_v);
                this.chat("Attempted to teleport you to \u00a7f" + targetPlayer.func_70005_c_() + "\u00a77.");
                return;
            }
            this.chat("\u00a77We couldn't find any player in the current world with that name.");
            return;
        }
        if (args2.length == 4) {
            try {
                double posX = StringsKt.equals(args2[1], "~", true) ? MinecraftInstance.mc.field_71439_g.field_70165_t : Double.parseDouble(args2[1]);
                double posY = StringsKt.equals(args2[2], "~", true) ? MinecraftInstance.mc.field_71439_g.field_70163_u : Double.parseDouble(args2[2]);
                double posZ = StringsKt.equals(args2[3], "~", true) ? MinecraftInstance.mc.field_71439_g.field_70161_v : Double.parseDouble(args2[3]);
                MinecraftInstance.mc.field_71439_g.func_70634_a(posX, posY, posZ);
                this.chat("Attempted to teleport you to \u00a7f" + posX + "\u00a77, \u00a7f" + posY + "\u00a77, \u00a7f" + posZ + "\u00a77.");
                return;
            }
            catch (NumberFormatException e) {
                this.chat("\u00a77Please check if you have typed the numbers correctly, and try again.");
                return;
            }
        }
        this.chatSyntax("teleport/tp <player name/x y z>");
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    @NotNull
    public List<String> tabComplete(@NotNull String[] args) {
        block6: {
            block5: {
                Intrinsics.checkNotNullParameter(args, "args");
                if (args.length == 0) {
                    return CollectionsKt.emptyList();
                }
                pref = args[0];
                if (args.length != 1) break block5;
                var3_3 = MinecraftInstance.mc.field_71441_e.field_73010_i;
                Intrinsics.checkNotNullExpressionValue(var3_3, "mc.theWorld.playerEntities");
                $this$filter$iv = var3_3;
                $i$f$filter = false;
                var5_5 = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    it = (EntityPlayer)element$iv$iv;
                    $i$a$-filter-TpCommand$tabComplete$1 = false;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    if (AntiBot.isBot((EntityLivingBase)it)) ** GOTO lbl-1000
                    var12_12 = it.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(var12_12, "it.name");
                    if (StringsKt.startsWith(var12_12, pref, true)) {
                        v0 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v0 = false;
                    }
                    if (!v0) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$map$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (EntityPlayer)item$iv$iv;
                    var13_13 = destination$iv$iv;
                    $i$a$-map-TpCommand$tabComplete$2 = false;
                    var13_13.add(it.func_70005_c_());
                }
                v1 /* !! */  = CollectionsKt.toList((List)destination$iv$iv);
                break block6;
            }
            v1 /* !! */  = CollectionsKt.emptyList();
        }
        return v1 /* !! */ ;
    }
}

