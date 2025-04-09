/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.script.Script;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\tJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0005J\u0006\u0010\u0013\u001a\u00020\rJ\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0005J\u0006\u0010\u0015\u001a\u00020\rR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2={"Lnet/ccbluex/liquidbounce/script/ScriptManager;", "", "()V", "scripts", "", "Lnet/ccbluex/liquidbounce/script/Script;", "getScripts", "()Ljava/util/List;", "scriptsFolder", "Ljava/io/File;", "getScriptsFolder", "()Ljava/io/File;", "disableScripts", "", "enableScripts", "loadJsScript", "scriptFile", "loadScript", "script", "loadScripts", "unloadScript", "unloadScripts", "CrossSine"})
public final class ScriptManager {
    @NotNull
    private final List<Script> scripts = new ArrayList();
    @NotNull
    private final File scriptsFolder = new File(CrossSine.INSTANCE.getFileManager().getDir(), "scripts");

    @NotNull
    public final List<Script> getScripts() {
        return this.scripts;
    }

    @NotNull
    public final File getScriptsFolder() {
        return this.scriptsFolder;
    }

    public final void loadScripts() {
        if (!this.scriptsFolder.exists()) {
            this.scriptsFolder.mkdir();
        }
        File[] fileArray = this.scriptsFolder.listFiles();
        if (fileArray != null) {
            File[] $this$forEach$iv = fileArray;
            boolean $i$f$forEach = false;
            for (File element$iv : $this$forEach$iv) {
                File it = element$iv;
                boolean bl = false;
                String string = it.getName();
                Intrinsics.checkNotNullExpressionValue(string, "it.name");
                if (!StringsKt.endsWith(string, ".js", true)) continue;
                Remapper.INSTANCE.loadSrg();
                Intrinsics.checkNotNullExpressionValue(it, "it");
                this.loadJsScript(it);
            }
        }
    }

    public final void unloadScripts() {
        this.scripts.clear();
    }

    public final void unloadScript(@NotNull Script script) {
        Intrinsics.checkNotNullParameter(script, "script");
        this.scripts.remove(script);
    }

    public final void loadScript(@NotNull Script script) {
        Intrinsics.checkNotNullParameter(script, "script");
        this.scripts.add(script);
    }

    public final void loadJsScript(@NotNull File scriptFile) {
        Intrinsics.checkNotNullParameter(scriptFile, "scriptFile");
        try {
            this.scripts.add(new Script(scriptFile));
            ClientUtils.INSTANCE.logInfo("[ScriptAPI] Successfully loaded script '" + scriptFile.getName() + "'.");
        }
        catch (Throwable t) {
            ClientUtils.INSTANCE.logError("[ScriptAPI] Failed to load script '" + scriptFile.getName() + "'.", t);
        }
    }

    public final void enableScripts() {
        Iterable $this$forEach$iv = this.scripts;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Script it = (Script)element$iv;
            boolean bl = false;
            it.onEnable();
        }
    }

    public final void disableScripts() {
        Iterable $this$forEach$iv = this.scripts;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Script it = (Script)element$iv;
            boolean bl = false;
            it.onDisable();
        }
    }
}

