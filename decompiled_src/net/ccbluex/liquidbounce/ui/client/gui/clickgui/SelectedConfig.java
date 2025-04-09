/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.gui.clickgui;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000b\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/gui/clickgui/SelectedConfig;", "", "name", "", "isOnline", "", "(Ljava/lang/String;Z)V", "()Z", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "CrossSine"})
public final class SelectedConfig {
    @NotNull
    private final String name;
    private final boolean isOnline;

    public SelectedConfig(@NotNull String name, boolean isOnline) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.name = name;
        this.isOnline = isOnline;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final boolean isOnline() {
        return this.isOnline;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    public final boolean component2() {
        return this.isOnline;
    }

    @NotNull
    public final SelectedConfig copy(@NotNull String name, boolean isOnline) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new SelectedConfig(name, isOnline);
    }

    public static /* synthetic */ SelectedConfig copy$default(SelectedConfig selectedConfig, String string, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            string = selectedConfig.name;
        }
        if ((n & 2) != 0) {
            bl = selectedConfig.isOnline;
        }
        return selectedConfig.copy(string, bl);
    }

    @NotNull
    public String toString() {
        return "SelectedConfig(name=" + this.name + ", isOnline=" + this.isOnline + ')';
    }

    public int hashCode() {
        int result = this.name.hashCode();
        int n = this.isOnline ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SelectedConfig)) {
            return false;
        }
        SelectedConfig selectedConfig = (SelectedConfig)other;
        if (!Intrinsics.areEqual(this.name, selectedConfig.name)) {
            return false;
        }
        return this.isOnline == selectedConfig.isOnline;
    }
}

