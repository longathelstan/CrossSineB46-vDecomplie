/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.minecraft.network.play.server.S45PacketTitle;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="NoRender", category=ModuleCategory.VISUAL, array=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/NoRender;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "bossHealth", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getBossHealth", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "fireEffect", "getFireEffect", "titleValue", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class NoRender
extends Module {
    @NotNull
    public static final NoRender INSTANCE = new NoRender();
    @NotNull
    private static final BoolValue fireEffect = new BoolValue("Fire", true);
    @NotNull
    private static final BoolValue bossHealth = new BoolValue("Boss-Health", false);
    @NotNull
    private static final BoolValue titleValue = new BoolValue("Title", false);

    private NoRender() {
    }

    @NotNull
    public final BoolValue getFireEffect() {
        return fireEffect;
    }

    @NotNull
    public final BoolValue getBossHealth() {
        return bossHealth;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof S45PacketTitle && ((Boolean)titleValue.get()).booleanValue()) {
            event.cancelEvent();
        }
    }
}

