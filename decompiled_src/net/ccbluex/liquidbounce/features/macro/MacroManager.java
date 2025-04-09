/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.macro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0010"}, d2={"Lnet/ccbluex/liquidbounce/features/macro/MacroManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "macros", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/features/macro/Macro;", "Lkotlin/collections/ArrayList;", "getMacros", "()Ljava/util/ArrayList;", "handleEvents", "", "onKey", "", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "CrossSine"})
public final class MacroManager
extends MinecraftInstance
implements Listenable {
    @NotNull
    private final ArrayList<Macro> macros = new ArrayList();

    @NotNull
    public final ArrayList<Macro> getMacros() {
        return this.macros;
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onKey(@NotNull KeyEvent event) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$filter$iv = this.macros;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Macro it = (Macro)element$iv$iv;
            boolean bl = false;
            if (!(it.getKey() == event.getKey())) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Macro it = (Macro)element$iv;
            boolean bl = false;
            it.exec();
        }
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

