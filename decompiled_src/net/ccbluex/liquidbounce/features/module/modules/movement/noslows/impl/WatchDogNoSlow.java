/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.noslows.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.noslows.NoSlowMode;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/impl/WatchDogNoSlow;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/NoSlowMode;", "()V", "blink", "", "send", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "slow", "", "CrossSine"})
public final class WatchDogNoSlow
extends NoSlowMode {
    private boolean send;
    private boolean blink;

    public WatchDogNoSlow() {
        super("WatchDog");
    }

    @Override
    public void onPreMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (PlayerUtils.getOffGroundTicks() == 4 && this.send) {
            this.send = false;
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, MinecraftInstance.mc.field_71439_g.func_70694_bm(), 0.0f, 0.0f, 0.0f)));
        } else if (MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && MinecraftInstance.mc.field_71439_g.func_71039_bw() && !this.getHoldSword()) {
            event.setY(event.getY() + 1.0E-14);
        }
        if (!KillAura.INSTANCE.getState() || !KillAura.INSTANCE.getAutoBlockValue().equals("WatchDogB")) {
            if (this.getHoldSword() && MinecraftInstance.mc.field_71439_g.func_71039_bw()) {
                if (this.blink) {
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                    this.blink = false;
                } else {
                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g()));
                    this.blink = true;
                }
            } else if (!this.blink) {
                this.blink = true;
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
            }
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement && !MinecraftInstance.mc.field_71439_g.func_71039_bw()) {
            if (((C08PacketPlayerBlockPlacement)packet).func_149568_f() == 255 && (this.getHoldConsume() || this.getHoldBow()) && PlayerUtils.getOffGroundTicks() < 2) {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                }
                this.send = true;
                event.cancelEvent();
            }
        } else if (packet instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)packet).func_180762_c() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) {
            if (this.send) {
                event.cancelEvent();
            }
            this.send = false;
        }
    }

    @Override
    public float slow() {
        return 1.0f;
    }
}

