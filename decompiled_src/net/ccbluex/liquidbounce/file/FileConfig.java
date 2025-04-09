/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file;

import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH&J\u0006\u0010\u000e\u001a\u00020\rJ\b\u0010\u000f\u001a\u00020\rH&J\u000e\u0010\u0010\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/file/FileConfig;", "", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "getFile", "()Ljava/io/File;", "createConfig", "", "hasConfig", "", "loadConfig", "config", "", "loadConfigFile", "saveConfig", "saveConfigFile", "CrossSine"})
public abstract class FileConfig {
    @NotNull
    private final File file;

    public FileConfig(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
    }

    @NotNull
    public final File getFile() {
        return this.file;
    }

    public abstract void loadConfig(@NotNull String var1);

    @NotNull
    public abstract String saveConfig();

    public final void createConfig() {
        this.file.createNewFile();
    }

    @NotNull
    public final String loadConfigFile() {
        return FilesKt.readText(this.file, Charsets.UTF_8);
    }

    public final void saveConfigFile(@NotNull String config) {
        Intrinsics.checkNotNullParameter(config, "config");
        FilesKt.writeText(this.file, config, Charsets.UTF_8);
    }

    public final boolean hasConfig() {
        return this.file.exists();
    }
}

