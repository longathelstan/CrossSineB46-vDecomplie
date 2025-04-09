/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0010\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/utils/FileUtils;", "", "()V", "copyDir", "", "fromDir", "Ljava/io/File;", "toDir", "extractZip", "zipFile", "folder", "zipStream", "Ljava/io/InputStream;", "unpackFile", "file", "name", "", "writeFile", "str", "path", "CrossSine"})
public final class FileUtils {
    @NotNull
    public static final FileUtils INSTANCE = new FileUtils();

    private FileUtils() {
    }

    public final void unpackFile(@NotNull File file, @NotNull String name) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(name, "name");
        FileOutputStream fos = new FileOutputStream(file);
        IOUtils.copy((InputStream)FileUtils.class.getClassLoader().getResourceAsStream(name), (OutputStream)fos);
        fos.close();
    }

    public final void writeFile(@NotNull String str, @NotNull String path) {
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(path, "path");
        File file = new File(path);
        FilesKt.writeText(file, str, Charsets.UTF_8);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void extractZip(@NotNull InputStream zipStream, @NotNull File folder) {
        Intrinsics.checkNotNullParameter(zipStream, "zipStream");
        Intrinsics.checkNotNullParameter(folder, "folder");
        if (!folder.exists()) {
            folder.mkdir();
        }
        Closeable closeable = new ZipInputStream(zipStream);
        Throwable throwable = null;
        try {
            ZipInputStream zipInputStream = (ZipInputStream)closeable;
            boolean bl = false;
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if (zipEntry.isDirectory()) {
                    zipEntry = zipInputStream.getNextEntry();
                    continue;
                }
                File newFile = new File(folder, zipEntry.getName());
                new File(newFile.getParent()).mkdirs();
                Closeable closeable2 = new FileOutputStream(newFile);
                Throwable throwable2 = null;
                try {
                    FileOutputStream it = (FileOutputStream)closeable2;
                    boolean bl2 = false;
                    long l = ByteStreamsKt.copyTo$default(zipInputStream, it, 0, 2, null);
                }
                catch (Throwable throwable3) {
                    throwable2 = throwable3;
                    throw throwable3;
                }
                finally {
                    CloseableKt.closeFinally(closeable2, throwable2);
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.closeEntry();
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable4) {
            throwable = throwable4;
            throw throwable4;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    public final void extractZip(@NotNull File zipFile, @NotNull File folder) {
        Intrinsics.checkNotNullParameter(zipFile, "zipFile");
        Intrinsics.checkNotNullParameter(folder, "folder");
        this.extractZip(new FileInputStream(zipFile), folder);
    }

    public final void copyDir(@NotNull File fromDir, @NotNull File toDir) {
        Intrinsics.checkNotNullParameter(fromDir, "fromDir");
        Intrinsics.checkNotNullParameter(toDir, "toDir");
        if (!fromDir.exists()) {
            throw new IllegalArgumentException("From dir not exists");
        }
        if (!fromDir.isDirectory() || toDir.exists() && !toDir.isDirectory()) {
            throw new IllegalArgumentException("Arguments MUST be a directory");
        }
        if (!toDir.exists()) {
            toDir.mkdirs();
        }
        File[] fileArray = fromDir.listFiles();
        Intrinsics.checkNotNullExpressionValue(fileArray, "fromDir.listFiles()");
        Object[] $this$forEach$iv = fileArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            File it = (File)element$iv;
            boolean bl = false;
            File toFile = new File(toDir, it.getName());
            if (it.isDirectory()) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                INSTANCE.copyDir(it, toFile);
                continue;
            }
            CopyOption[] copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            Files.copy(it.toPath(), toFile.toPath(), copyOptionArray);
        }
    }
}

