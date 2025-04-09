/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.aac;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\r\u001a\u00020\u0004H\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/aac/AACv4Nofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "aac4Fakelag", "", "aac4Packets", "", "Lnet/minecraft/network/play/client/C03PacketPlayer;", "packetModify", "inAir", "height", "", "plus", "inVoid", "onEnable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class AACv4Nofall
extends NoFallMode {
    private boolean aac4Fakelag;
    private boolean packetModify;
    @NotNull
    private final List<C03PacketPlayer> aac4Packets = new ArrayList();

    public AACv4Nofall() {
        super("AACv4");
    }

    @Override
    public void onEnable() {
        this.aac4Packets.clear();
        this.packetModify = false;
        this.aac4Fakelag = false;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C03PacketPlayer && this.aac4Fakelag) {
            event.cancelEvent();
            if (this.packetModify) {
                ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
                this.packetModify = false;
            }
            this.aac4Packets.add((C03PacketPlayer)event.getPacket());
        }
    }

    @Override
    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getEventState() == EventState.PRE) {
            if (!this.inVoid()) {
                if (this.aac4Fakelag) {
                    this.aac4Fakelag = false;
                    if (this.aac4Packets.size() > 0) {
                        for (C03PacketPlayer packet : this.aac4Packets) {
                            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)packet);
                        }
                        this.aac4Packets.clear();
                    }
                }
                return;
            }
            if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.aac4Fakelag) {
                this.aac4Fakelag = false;
                if (this.aac4Packets.size() > 0) {
                    for (C03PacketPlayer packet : this.aac4Packets) {
                        MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)packet);
                    }
                    this.aac4Packets.clear();
                }
                return;
            }
            if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R > 2.5 && this.aac4Fakelag) {
                this.packetModify = true;
                MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
            }
            if (this.inAir(4.0, 1.0)) {
                return;
            }
            if (!this.aac4Fakelag) {
                this.aac4Fakelag = true;
            }
        }
    }

    private final boolean inVoid() {
        if (MinecraftInstance.mc.field_71439_g.field_70163_u < 0.0) {
            return false;
        }
        int off = 0;
        while ((double)off < MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2) {
            AxisAlignedBB bb = new AxisAlignedBB(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70165_t, (double)off, MinecraftInstance.mc.field_71439_g.field_70161_v);
            List list = MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, bb);
            Intrinsics.checkNotNullExpressionValue(list, "mc.theWorld.getColliding\u2026ngBoxes(mc.thePlayer, bb)");
            if (!((Collection)list).isEmpty()) {
                return true;
            }
            off += 2;
        }
        return false;
    }

    private final boolean inAir(double height, double plus2) {
        if (MinecraftInstance.mc.field_71439_g.field_70163_u < 0.0) {
            return false;
        }
        int off = 0;
        while ((double)off < height) {
            AxisAlignedBB bb = new AxisAlignedBB(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)off, MinecraftInstance.mc.field_71439_g.field_70161_v);
            if (!MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, bb).isEmpty()) {
                return true;
            }
            off += (int)plus2;
        }
        return false;
    }
}

