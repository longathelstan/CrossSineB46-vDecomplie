/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.misc;

import com.google.common.io.ByteStreams;
import java.awt.Desktop;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004J*\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u0014J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u0004J\u0016\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004J*\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u0004J\"\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2={"Lnet/ccbluex/liquidbounce/utils/misc/HttpUtils;", "", "()V", "DEFAULT_AGENT", "", "download", "", "url", "file", "Ljava/io/File;", "get", "make", "Ljava/net/HttpURLConnection;", "method", "data", "agent", "openWebpage", "", "uri", "Ljava/net/URI;", "Ljava/net/URL;", "post", "request", "requestStream", "Ljava/io/InputStream;", "CrossSine"})
public final class HttpUtils {
    @NotNull
    public static final HttpUtils INSTANCE = new HttpUtils();
    @NotNull
    public static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.7113.93 Safari/537.36 Java/1.8.0_191";

    private HttpUtils() {
    }

    @NotNull
    public final HttpURLConnection make(@NotNull String url, @NotNull String method, @NotNull String data, @NotNull String agent) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(agent, "agent");
        URLConnection uRLConnection = new URL(url).openConnection();
        if (uRLConnection == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
        }
        HttpURLConnection httpConnection = (HttpURLConnection)uRLConnection;
        httpConnection.setRequestMethod(method);
        httpConnection.setConnectTimeout(2000);
        httpConnection.setReadTimeout(10000);
        httpConnection.setRequestProperty("User-Agent", agent);
        httpConnection.setInstanceFollowRedirects(true);
        httpConnection.setDoOutput(true);
        if (((CharSequence)data).length() > 0) {
            DataOutputStream dataOutputStream = new DataOutputStream(httpConnection.getOutputStream());
            dataOutputStream.writeBytes(data);
            dataOutputStream.flush();
        }
        return httpConnection;
    }

    public static /* synthetic */ HttpURLConnection make$default(HttpUtils httpUtils, String string, String string2, String string3, String string4, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = "";
        }
        if ((n & 8) != 0) {
            string4 = DEFAULT_AGENT;
        }
        return httpUtils.make(string, string2, string3, string4);
    }

    @NotNull
    public final String request(@NotNull String url, @NotNull String method, @NotNull String data, @NotNull String agent) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(agent, "agent");
        HttpURLConnection connection = this.make(url, method, data, agent);
        InputStream inputStream = connection.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "connection.inputStream");
        Charset charset = Charsets.UTF_8;
        return TextStreamsKt.readText(new InputStreamReader(inputStream, charset));
    }

    public static /* synthetic */ String request$default(HttpUtils httpUtils, String string, String string2, String string3, String string4, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = "";
        }
        if ((n & 8) != 0) {
            string4 = DEFAULT_AGENT;
        }
        return httpUtils.request(string, string2, string3, string4);
    }

    @Nullable
    public final InputStream requestStream(@NotNull String url, @NotNull String method, @NotNull String agent) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(agent, "agent");
        HttpURLConnection connection = HttpUtils.make$default(this, url, method, agent, null, 8, null);
        return connection.getInputStream();
    }

    public static /* synthetic */ InputStream requestStream$default(HttpUtils httpUtils, String string, String string2, String string3, int n, Object object) {
        if ((n & 4) != 0) {
            string3 = DEFAULT_AGENT;
        }
        return httpUtils.requestStream(string, string2, string3);
    }

    public final boolean openWebpage(@NotNull String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        try {
            URL linkURL = null;
            linkURL = new URL(url);
            return this.openWebpage(linkURL.toURI());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public final boolean openWebpage(@NotNull URL url) {
        Intrinsics.checkNotNullParameter(url, "url");
        try {
            return this.openWebpage(url.toURI());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean openWebpage(@Nullable URI uri) {
        Desktop desktop;
        Desktop desktop2 = desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void download(@NotNull String url, @NotNull File file) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(file, "file");
        ClientUtils.INSTANCE.logWarn("Downloading " + url + " to " + file.getAbsolutePath());
        Closeable closeable = new FileOutputStream(file);
        Throwable throwable = null;
        try {
            FileOutputStream it = (FileOutputStream)closeable;
            boolean bl = false;
            long l = ByteStreams.copy((InputStream)HttpUtils.make$default(INSTANCE, url, "GET", null, null, 12, null).getInputStream(), (OutputStream)it);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }

    @NotNull
    public final String get(@NotNull String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        return HttpUtils.request$default(this, url, "GET", null, null, 12, null);
    }

    @NotNull
    public final String post(@NotNull String url, @NotNull String data) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(data, "data");
        return HttpUtils.request$default(this, url, "POST", data, null, 8, null);
    }

    static {
        HttpURLConnection.setFollowRedirects(true);
    }
}

