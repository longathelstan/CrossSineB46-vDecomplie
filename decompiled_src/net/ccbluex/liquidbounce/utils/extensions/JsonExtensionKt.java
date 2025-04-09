/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.extensions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=2, xi=48, d1={"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\u0010\u0004\n\u0000\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\u0002\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0007H\u0086\u0002\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0086\u0002\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\tH\u0086\u0002\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0086\u0002\u00a8\u0006\n"}, d2={"set", "", "Lcom/google/gson/JsonObject;", "s", "", "value", "Lcom/google/gson/JsonElement;", "", "", "", "CrossSine"})
public final class JsonExtensionKt {
    public static final void set(@NotNull JsonObject $this$set, @NotNull String s, @NotNull String value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(value, "value");
        $this$set.addProperty(s, value);
    }

    public static final void set(@NotNull JsonObject $this$set, @NotNull String s, @NotNull Number value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(value, "value");
        $this$set.addProperty(s, value);
    }

    public static final void set(@NotNull JsonObject $this$set, @NotNull String s, char value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        $this$set.addProperty(s, Character.valueOf(value));
    }

    public static final void set(@NotNull JsonObject $this$set, @NotNull String s, boolean value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        $this$set.addProperty(s, Boolean.valueOf(value));
    }

    public static final void set(@NotNull JsonObject $this$set, @NotNull String s, @NotNull JsonElement value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(value, "value");
        $this$set.add(s, value);
    }
}

