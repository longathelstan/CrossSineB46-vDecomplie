/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.config;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\nH&R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/file/config/ConfigSection;", "", "sectionName", "", "(Ljava/lang/String;)V", "getSectionName", "()Ljava/lang/String;", "load", "", "json", "Lcom/google/gson/JsonObject;", "save", "CrossSine"})
public abstract class ConfigSection {
    @NotNull
    private final String sectionName;

    public ConfigSection(@NotNull String sectionName) {
        Intrinsics.checkNotNullParameter(sectionName, "sectionName");
        this.sectionName = sectionName;
    }

    @NotNull
    public final String getSectionName() {
        return this.sectionName;
    }

    public abstract void load(@NotNull JsonObject var1);

    @NotNull
    public abstract JsonObject save();
}

