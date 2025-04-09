/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.ncp;

import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.TitleValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\b\u0010\u001a\u001a\u00020\u0018H\u0016J\b\u0010\u001b\u001a\u00020\u0018H\u0016J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\"H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/ncp/VelocityLongjump;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "()V", "blink", "", "damage", "delay", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "delayMS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "hold", "holdMS", "motionY", "", "note", "Lnet/ccbluex/liquidbounce/features/value/TitleValue;", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayClient;", "prevState", "state", "", "clearPackets", "", "getBowSlot", "onDisable", "onEnable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class VelocityLongjump
extends LongJumpMode {
    @NotNull
    private final TitleValue note = new TitleValue("Bow LongJump");
    @NotNull
    private final IntegerValue delay = new IntegerValue("Delay", 800, 500, 700);
    @NotNull
    private final IntegerValue hold = new IntegerValue("hold", 300, 300, 500);
    @NotNull
    private final TimerMS delayMS = new TimerMS();
    @NotNull
    private final TimerMS holdMS = new TimerMS();
    private boolean blink;
    private boolean damage;
    private int state;
    private boolean prevState;
    private float motionY;
    @NotNull
    private final LinkedBlockingQueue<Packet<INetHandlerPlayClient>> packets = new LinkedBlockingQueue();

    public VelocityLongjump() {
        super("Velocity");
    }

    @Override
    public void onEnable() {
        this.prevState = Velocity.INSTANCE.getState();
        this.motionY = 0.0f;
    }

    @Override
    public void onDisable() {
        this.state = 0;
        this.blink = false;
        this.damage = false;
        SlotUtils.INSTANCE.stopSet();
        if (this.prevState) {
            Velocity.INSTANCE.setState(this.prevState);
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.getBowSlot() == -1) {
            ClientUtils.INSTANCE.displayAlert("Bow not found");
            this.getLongjump().setState(false);
            return;
        }
        ClientUtils.INSTANCE.displayAlert(String.valueOf(this.state));
        MovementUtils.INSTANCE.strafe(0.375);
        switch (this.state) {
            case 0: {
                SlotUtils.INSTANCE.setSlot(this.getBowSlot(), true, this.getLongjump().getName());
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = true;
                this.state = 1;
                this.holdMS.reset();
                if (!this.prevState) break;
                Velocity.INSTANCE.setState(false);
                break;
            }
            case 1: {
                if (!this.holdMS.hasTimePassed(((Number)this.hold.get()).intValue())) break;
                RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, -90.0f), 20);
                MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = false;
                this.state = 2;
                break;
            }
            case 3: {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                }
                if (!this.delayMS.hasTimePassed(((Number)this.delay.get()).intValue())) break;
                this.clearPackets();
                SlotUtils.INSTANCE.stopSet();
                this.blink = false;
                this.state = 4;
                break;
            }
            case 4: {
                this.state = 0;
                this.blink = false;
                this.damage = false;
                SlotUtils.INSTANCE.stopSet();
                if (this.prevState) {
                    Velocity.INSTANCE.setState(this.prevState);
                }
                this.getLongjump().setState(false);
            }
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity && this.state == 2 && ((S12PacketEntityVelocity)packet).func_149412_c() == MinecraftInstance.mc.field_71439_g.func_145782_y()) {
            this.blink = true;
            this.delayMS.reset();
            this.damage = true;
            this.state = 3;
            this.motionY = (float)((S12PacketEntityVelocity)packet).func_149410_e() / 8000.0f;
        }
        if (this.blink) {
            String string = packet.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(string, "packet.javaClass.simpleName");
            if (StringsKt.startsWith(string, "S", true)) {
                event.cancelEvent();
                this.packets.add(packet);
            }
        }
    }

    @Override
    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.damage) {
            event.zeroXZ();
        }
    }

    private final void clearPackets() {
        while (!this.packets.isEmpty()) {
            Packet<INetHandlerPlayClient> packet = this.packets.take();
            if (packet == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.network.Packet<net.minecraft.network.play.INetHandlerPlayClient?>");
            }
            PacketUtils.INSTANCE.handlePacket(packet);
        }
    }

    private final int getBowSlot() {
        int n = 36;
        while (n < 45) {
            int i;
            ItemStack stack;
            if ((stack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n++).func_75211_c()) == null || !(stack.func_77973_b() instanceof ItemBow)) continue;
            return i - 36;
        }
        return -1;
    }
}

