/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.special;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.EnumTriggerType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u000bH\u0007\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/special/AutoDisable;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "handleEvents", "", "handleGameEnd", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class AutoDisable
implements Listenable {
    @NotNull
    public static final AutoDisable INSTANCE = new AutoDisable();

    private AutoDisable() {
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$filter$iv = CrossSine.INSTANCE.getModuleManager().getModules();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Module it = (Module)element$iv$iv;
            boolean bl = false;
            if (!(it.getState() && it.getAutoDisable() == EnumAutoDisableType.RESPAWN && it.getTriggerType() == EnumTriggerType.TOGGLE)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Module module = (Module)element$iv;
            boolean bl = false;
            module.setState(false);
        }
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = CrossSine.INSTANCE.getModuleManager().getModules();
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                Module it = (Module)element$iv$iv;
                boolean bl = false;
                if (!(it.getState() && it.getAutoDisable() == EnumAutoDisableType.FLAG && it.getTriggerType() == EnumTriggerType.TOGGLE)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Module module = (Module)element$iv;
                boolean bl = false;
                module.setState(false);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void handleGameEnd() {
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv = CrossSine.INSTANCE.getModuleManager().getModules();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Module it = (Module)element$iv$iv;
            boolean bl = false;
            if (!(it.getState() && it.getAutoDisable() == EnumAutoDisableType.GAME_END)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Module module = (Module)element$iv;
            boolean bl = false;
            module.setState(false);
        }
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

