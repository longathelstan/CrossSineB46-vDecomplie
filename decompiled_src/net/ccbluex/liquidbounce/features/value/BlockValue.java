/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.value;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/value/BlockValue;", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "name", "", "value", "", "(Ljava/lang/String;I)V", "openList", "", "getOpenList", "()Z", "setOpenList", "(Z)V", "CrossSine"})
public final class BlockValue
extends IntegerValue {
    private boolean openList;

    public BlockValue(@NotNull String name, int value) {
        Intrinsics.checkNotNullParameter(name, "name");
        super(name, value, 1, 197);
    }

    public final boolean getOpenList() {
        return this.openList;
    }

    public final void setOpenList(boolean bl) {
        this.openList = bl;
    }
}

