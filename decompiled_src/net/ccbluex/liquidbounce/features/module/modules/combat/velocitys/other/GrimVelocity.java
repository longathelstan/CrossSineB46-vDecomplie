/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0012H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/other/GrimVelocity;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "grimTCancel", "", "getGrimTCancel", "()I", "setGrimTCancel", "(I)V", "updates", "getUpdates", "setUpdates", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class GrimVelocity
extends VelocityMode {
    private int grimTCancel;
    private int updates;

    public GrimVelocity() {
        super("Grim");
    }

    public final int getGrimTCancel() {
        return this.grimTCancel;
    }

    public final void setGrimTCancel(int n) {
        this.grimTCancel = n;
    }

    public final int getUpdates() {
        return this.updates;
    }

    public final void setUpdates(int n) {
        this.updates = n;
    }

    @Override
    public void onEnable() {
        this.grimTCancel = 0;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)packet).func_149412_c() == MinecraftInstance.mc.field_71439_g.func_145782_y()) {
            event.cancelEvent();
            this.grimTCancel = 6;
        }
        if (packet instanceof S32PacketConfirmTransaction && this.grimTCancel > 0) {
            event.cancelEvent();
            int n = this.grimTCancel;
            this.grimTCancel = n + -1;
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int n = this.updates;
        this.updates = n + 1;
        if (this.updates >= 10) {
            this.updates = 0;
            if (this.grimTCancel > 0) {
                n = this.grimTCancel;
                this.grimTCancel = n + -1;
            }
        }
    }
}

