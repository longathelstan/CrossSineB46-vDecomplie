/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.command;

import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003H\u0004J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003H\u0004J\u001b\u0010\u0010\u001a\u00020\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0004\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0003H\u0004J\b\u0010\u0014\u001a\u00020\rH\u0004J\u001b\u0010\u0015\u001a\u00020\r2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H&\u00a2\u0006\u0002\u0010\u0012J\b\u0010\u0017\u001a\u00020\rH\u0004J!\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u00192\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0016\u00a2\u0006\u0002\u0010\u001aR\u001b\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0005\u00a2\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001b"}, d2={"Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "command", "", "alias", "", "(Ljava/lang/String;[Ljava/lang/String;)V", "getAlias", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getCommand", "()Ljava/lang/String;", "alert", "", "msg", "chat", "chatSyntax", "syntaxes", "([Ljava/lang/String;)V", "syntax", "chatSyntaxError", "execute", "args", "playEdit", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "CrossSine"})
public abstract class Command
extends MinecraftInstance {
    @NotNull
    private final String command;
    @NotNull
    private final String[] alias;

    public Command(@NotNull String command, @NotNull String[] alias) {
        Intrinsics.checkNotNullParameter(command, "command");
        Intrinsics.checkNotNullParameter(alias, "alias");
        this.command = command;
        this.alias = alias;
    }

    @NotNull
    public final String getCommand() {
        return this.command;
    }

    @NotNull
    public final String[] getAlias() {
        return this.alias;
    }

    public abstract void execute(@NotNull String[] var1);

    @NotNull
    public List<String> tabComplete(@NotNull String[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        return CollectionsKt.emptyList();
    }

    protected final void alert(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        ClientUtils.INSTANCE.displayAlert(msg);
    }

    protected final void chat(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        ClientUtils.INSTANCE.displayChatMessage(msg);
    }

    protected final void chatSyntax(@NotNull String syntax) {
        Intrinsics.checkNotNullParameter(syntax, "syntax");
        ClientUtils.INSTANCE.displayAlert("Syntax: \u00a77" + CrossSine.INSTANCE.getCommandManager().getPrefix() + syntax);
    }

    protected final void chatSyntax(@NotNull String[] syntaxes) {
        Intrinsics.checkNotNullParameter(syntaxes, "syntaxes");
        ClientUtils.INSTANCE.displayAlert("Syntax:");
        for (String syntax : syntaxes) {
            StringBuilder stringBuilder = new StringBuilder().append("\u00a78> \u00a77").append(CrossSine.INSTANCE.getCommandManager().getPrefix()).append(this.command).append(' ');
            String string = syntax.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            ClientUtils.INSTANCE.displayChatMessage(stringBuilder.append(string).toString());
        }
    }

    protected final void chatSyntaxError() {
        ClientUtils.INSTANCE.displayAlert("Syntax error");
    }

    protected final void playEdit() {
        MinecraftInstance.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("random.anvil_use"), (float)1.0f));
    }
}

