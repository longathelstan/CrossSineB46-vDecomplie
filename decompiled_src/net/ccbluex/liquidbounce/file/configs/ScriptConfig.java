/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.configs;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.file.FileConfig;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\rH\u0007J\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\rH\u0016J\u000e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\b\u0010\u0015\u001a\u00020\rH\u0016R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0017"}, d2={"Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "subscripts", "", "Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig$Subscript;", "getSubscripts", "()Ljava/util/List;", "addSubscripts", "", "url", "", "name", "clearSubscripts", "", "isSubscript", "loadConfig", "config", "removeSubscript", "saveConfig", "Subscript", "CrossSine"})
public final class ScriptConfig
extends FileConfig {
    @NotNull
    private final List<Subscript> subscripts;

    public ScriptConfig(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        super(file);
        this.subscripts = new ArrayList();
    }

    @NotNull
    public final List<Subscript> getSubscripts() {
        return this.subscripts;
    }

    @Override
    public void loadConfig(@NotNull String config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.clearSubscripts();
        String[] stringArray = new String[]{"\n"};
        Iterable $this$forEach$iv = StringsKt.split$default((CharSequence)config, stringArray, false, 0, 6, null);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Object object;
            String line = (String)element$iv;
            boolean bl = false;
            if (StringsKt.contains$default((CharSequence)line, ":", false, 2, null)) {
                String[] data;
                object = new String[]{":"};
                Collection $this$toTypedArray$iv = StringsKt.split$default((CharSequence)line, object, false, 0, 6, null);
                boolean $i$f$toTypedArray = false;
                Collection thisCollection$iv = $this$toTypedArray$iv;
                if (thisCollection$iv.toArray(new String[0]) == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                }
                object = Base64.getDecoder().decode(data[0]);
                Intrinsics.checkNotNullExpressionValue(object, "getDecoder().decode(data[0])");
                Charset charset = Charset.defaultCharset();
                Intrinsics.checkNotNullExpressionValue(charset, "defaultCharset()");
                String string = new String((byte[])object, charset);
                object = Base64.getDecoder().decode(data[1]);
                Intrinsics.checkNotNullExpressionValue(object, "getDecoder().decode(data[1])");
                charset = Charset.defaultCharset();
                Intrinsics.checkNotNullExpressionValue(charset, "defaultCharset()");
                this.addSubscripts(string, new String((byte[])object, charset));
                continue;
            }
            byte[] byArray = Base64.getDecoder().decode(line);
            Intrinsics.checkNotNullExpressionValue(byArray, "getDecoder().decode(line)");
            object = Charset.defaultCharset();
            Intrinsics.checkNotNullExpressionValue(object, "defaultCharset()");
            new String(byArray, (Charset)object);
        }
    }

    @Override
    @NotNull
    public String saveConfig() {
        StringBuilder builder = new StringBuilder();
        for (Subscript subscript : this.subscripts) {
            Base64.Encoder encoder = Base64.getEncoder();
            String string = subscript.getUrl();
            byte[] byArray = string.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
            StringBuilder stringBuilder = builder.append(encoder.encode(byArray)).append(":");
            Base64.Encoder encoder2 = Base64.getEncoder();
            string = subscript.getName();
            byArray = string.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(byArray, "this as java.lang.String).getBytes(charset)");
            stringBuilder.append(encoder2.encode(byArray)).append("\n");
        }
        String string = builder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "builder.toString()");
        return string;
    }

    @JvmOverloads
    public final boolean addSubscripts(@NotNull String url, @NotNull String name) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(name, "name");
        if (this.isSubscript(url)) {
            return false;
        }
        this.subscripts.add(new Subscript(url, name));
        return true;
    }

    public static /* synthetic */ boolean addSubscripts$default(ScriptConfig scriptConfig, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = string;
        }
        return scriptConfig.addSubscripts(string, string2);
    }

    public final boolean removeSubscript(@NotNull String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        if (!this.isSubscript(url)) {
            return false;
        }
        this.subscripts.removeIf(arg_0 -> ScriptConfig.removeSubscript$lambda-1(url, arg_0));
        return true;
    }

    public final boolean isSubscript(@NotNull String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        for (Subscript subscript : this.subscripts) {
            if (!Intrinsics.areEqual(subscript.getUrl(), url)) continue;
            return true;
        }
        return false;
    }

    public final void clearSubscripts() {
        this.subscripts.clear();
    }

    @JvmOverloads
    public final boolean addSubscripts(@NotNull String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        return ScriptConfig.addSubscripts$default(this, url, null, 2, null);
    }

    private static final boolean removeSubscript$lambda-1(String $url, Subscript friend) {
        Intrinsics.checkNotNullParameter($url, "$url");
        Intrinsics.checkNotNullParameter(friend, "friend");
        return Intrinsics.areEqual(friend.getUrl(), $url);
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig$Subscript;", "", "url", "", "name", "(Ljava/lang/String;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "getUrl", "CrossSine"})
    public static final class Subscript {
        @NotNull
        private final String url;
        @NotNull
        private final String name;

        public Subscript(@NotNull String url, @NotNull String name) {
            Intrinsics.checkNotNullParameter(url, "url");
            Intrinsics.checkNotNullParameter(name, "name");
            this.url = url;
            this.name = name;
        }

        @NotNull
        public final String getUrl() {
            return this.url;
        }

        @NotNull
        public final String getName() {
            return this.name;
        }
    }
}

