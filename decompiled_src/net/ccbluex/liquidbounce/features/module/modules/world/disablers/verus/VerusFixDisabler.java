/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.verus;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001a\u0010\u0018\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u001a\u0010\u001b\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\f\"\u0004\b\u001d\u0010\u000eR\u001a\u0010\u001e\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000e\u00a8\u0006%"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/verus/VerusFixDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "fly4IValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "jam", "", "omniSprint13EValue", "packetCount", "pitch", "", "getPitch", "()D", "setPitch", "(D)V", "prevSlot", "getPrevSlot", "()I", "setPrevSlot", "(I)V", "scaffold14EValue", "x", "getX", "setX", "y", "getY", "setY", "yaw", "getYaw", "setYaw", "z", "getZ", "setZ", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class VerusFixDisabler
extends DisablerMode {
    @NotNull
    private final BoolValue omniSprint13EValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "OmniSprint"), true);
    @NotNull
    private final BoolValue scaffold14EValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "BlockPlace"), true);
    @NotNull
    private final BoolValue fly4IValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "fly4I"), true);
    private double x;
    private double y;
    private double z;
    private double pitch;
    private double yaw;
    private int prevSlot;
    private int jam;
    private int packetCount;

    public VerusFixDisabler() {
        super("VerusFix");
    }

    public final double getX() {
        return this.x;
    }

    public final void setX(double d) {
        this.x = d;
    }

    public final double getY() {
        return this.y;
    }

    public final void setY(double d) {
        this.y = d;
    }

    public final double getZ() {
        return this.z;
    }

    public final void setZ(double d) {
        this.z = d;
    }

    public final double getPitch() {
        return this.pitch;
    }

    public final void setPitch(double d) {
        this.pitch = d;
    }

    public final double getYaw() {
        return this.yaw;
    }

    public final void setYaw(double d) {
        this.yaw = d;
    }

    public final int getPrevSlot() {
        return this.prevSlot;
    }

    public final void setPrevSlot(int n) {
        this.prevSlot = n;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        int n;
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71441_e == null || event.isCancelled()) {
            return;
        }
        if (packet instanceof C03PacketPlayer && !(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) && (MinecraftInstance.mc.field_71439_g.field_70181_x == 0.0 || MinecraftInstance.mc.field_71439_g.field_70122_E && MinecraftInstance.mc.field_71439_g.field_70124_G) && !((C03PacketPlayer)packet).field_149474_g) {
            ((C03PacketPlayer)packet).field_149474_g = true;
            this.getDisabler().debugMessage("Packet C03 OnGround Fix");
        }
        if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition) {
            this.x = ((C03PacketPlayer.C04PacketPlayerPosition)packet).field_149479_a;
            this.y = ((C03PacketPlayer.C04PacketPlayerPosition)packet).field_149477_b;
            this.z = ((C03PacketPlayer.C04PacketPlayerPosition)packet).field_149478_c;
            this.jam = 0;
        }
        if (packet instanceof C03PacketPlayer.C05PacketPlayerLook) {
            this.yaw = ((C03PacketPlayer.C05PacketPlayerLook)packet).field_149476_e;
            this.pitch = ((C03PacketPlayer.C05PacketPlayerLook)packet).field_149473_f;
        }
        if (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            this.x = ((C03PacketPlayer.C06PacketPlayerPosLook)packet).field_149479_a;
            this.y = ((C03PacketPlayer.C06PacketPlayerPosLook)packet).field_149477_b;
            this.z = ((C03PacketPlayer.C06PacketPlayerPosLook)packet).field_149478_c;
            this.jam = 0;
            this.yaw = ((C03PacketPlayer.C06PacketPlayerPosLook)packet).field_149476_e;
            this.pitch = ((C03PacketPlayer.C06PacketPlayerPosLook)packet).field_149473_f;
        }
        if (packet instanceof C03PacketPlayer && !(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
            n = this.jam;
            this.jam = n + 1;
            if (this.jam > 20) {
                this.jam = 0;
                event.cancelEvent();
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.x, this.y, this.z, (float)this.yaw, (float)this.pitch, ((C03PacketPlayer)packet).field_149474_g)));
                this.getDisabler().debugMessage("Packet C03 Spam Fix");
            }
        }
        if (!MinecraftInstance.mc.func_71356_B() && packet instanceof C09PacketHeldItemChange) {
            if (((C09PacketHeldItemChange)packet).func_149614_c() == this.prevSlot) {
                event.cancelEvent();
                this.getDisabler().debugMessage("Packet C09 Duplicate Cancel");
            } else {
                this.prevSlot = ((C09PacketHeldItemChange)packet).func_149614_c();
            }
        }
        if (((Boolean)this.omniSprint13EValue.get()).booleanValue() && packet instanceof C0BPacketEntityAction) {
            event.cancelEvent();
            this.getDisabler().debugMessage("Packet C0B Check Cancel");
        }
        if (((Boolean)this.scaffold14EValue.get()).booleanValue() && packet instanceof C08PacketPlayerBlockPlacement) {
            ((C08PacketPlayerBlockPlacement)packet).field_149577_f = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149577_f, -1.0f, 1.0f);
            ((C08PacketPlayerBlockPlacement)packet).field_149578_g = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149578_g, -1.0f, 1.0f);
            ((C08PacketPlayerBlockPlacement)packet).field_149584_h = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149584_h, -1.0f, 1.0f);
            this.getDisabler().debugMessage("Packet C08 Placement Fix");
        }
        if (((Boolean)this.fly4IValue.get()).booleanValue() && packet instanceof C03PacketPlayer && !((C03PacketPlayer)packet).field_149474_g) {
            if (!(packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C05PacketPlayerLook || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
                n = this.packetCount;
                this.packetCount = n + 1;
                if (this.packetCount >= 2) {
                    event.cancelEvent();
                    this.getDisabler().debugMessage("Packet C03 Flying Cancel");
                }
            } else {
                this.packetCount = 0;
            }
        }
    }
}

