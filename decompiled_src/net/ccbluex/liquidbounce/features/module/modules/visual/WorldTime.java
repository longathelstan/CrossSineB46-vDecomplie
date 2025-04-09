/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name="WorldTime", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/WorldTime;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "a", "", "arrowValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "c", "customWorldTimeValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class WorldTime
extends Module {
    @NotNull
    private final IntegerValue customWorldTimeValue = new IntegerValue("CustomTime", 1, 0, 20000);
    @NotNull
    private final BoolValue arrowValue = new BoolValue("ArrowButton", false);
    private boolean c;
    private boolean a;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        MinecraftInstance.mc.field_71441_e.func_72877_b((long)((Number)this.customWorldTimeValue.get()).intValue());
        if (((Boolean)this.arrowValue.get()).booleanValue()) {
            if (!this.c && Keyboard.isKeyDown((int)203) && ((Number)this.customWorldTimeValue.getValue()).intValue() != 0) {
                this.customWorldTimeValue.set(((Number)this.customWorldTimeValue.getValue()).intValue() - 1000);
            }
            if (!this.a && Keyboard.isKeyDown((int)205) && ((Number)this.customWorldTimeValue.getValue()).intValue() != 20) {
                this.customWorldTimeValue.set(((Number)this.customWorldTimeValue.getValue()).intValue() + 1000);
            }
            this.c = Keyboard.isKeyDown((int)203);
            this.a = Keyboard.isKeyDown((int)205);
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S03PacketTimeUpdate) {
            event.cancelEvent();
        }
    }
}

