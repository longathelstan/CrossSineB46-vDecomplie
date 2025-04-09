/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.ncp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001cH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/ncp/NCPDamageLongjump;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "()V", "balance", "", "canBoost", "", "damageStat", "hasJumped", "jumpYPosArr", "", "", "[Ljava/lang/Double;", "ncpBoostValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "ncpdInstantValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "x", "y", "z", "onEnable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class NCPDamageLongjump
extends LongJumpMode {
    @NotNull
    private final FloatValue ncpBoostValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Boost"), 4.25f, 1.0f, 10.0f);
    @NotNull
    private final BoolValue ncpdInstantValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "DamageInstant"), false);
    @NotNull
    private final Double[] jumpYPosArr;
    private boolean canBoost;
    private double x;
    private double y;
    private double z;
    private int balance;
    private boolean damageStat;
    private boolean hasJumped;

    public NCPDamageLongjump() {
        super("NCPDamage");
        Double[] doubleArray = new Double[]{0.41999998688698, 0.7531999805212, 1.00133597911214, 1.16610926093821, 1.24918707874468, 1.24918707874468, 1.1707870772188, 1.0155550727022, 0.78502770378924, 0.4807108763317, 0.10408037809304, 0.0};
        this.jumpYPosArr = doubleArray;
    }

    @Override
    public void onEnable() {
        this.hasJumped = false;
        this.damageStat = false;
        this.balance = (Boolean)this.ncpdInstantValue.get() != false ? 114514 : 0;
        this.x = MinecraftInstance.mc.field_71439_g.field_70165_t;
        this.y = MinecraftInstance.mc.field_71439_g.field_70163_u;
        this.z = MinecraftInstance.mc.field_71439_g.field_70161_v;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.damageStat) {
            MinecraftInstance.mc.field_71439_g.func_70107_b(this.x, this.y, this.z);
            if (this.balance > this.jumpYPosArr.length * 4) {
                int n = 4;
                int n2 = 0;
                while (n2 < n) {
                    int n3;
                    int it = n3 = n2++;
                    boolean bl = false;
                    Double[] $this$forEach$iv = this.jumpYPosArr;
                    boolean $i$f$forEach = false;
                    for (Double element$iv : $this$forEach$iv) {
                        double it2 = ((Number)element$iv).doubleValue();
                        boolean bl2 = false;
                        PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.x, this.y + it2, this.z, false)));
                    }
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.x, this.y, this.z, false)));
                }
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer(true)));
                this.damageStat = true;
            }
        } else if (!this.hasJumped) {
            MovementUtils.INSTANCE.strafe(0.5f * ((Number)this.ncpBoostValue.get()).floatValue());
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
            this.hasJumped = true;
        }
        if (((Boolean)this.getLongjump().getAutoDisableValue().get()).booleanValue() && this.hasJumped) {
            this.getLongjump().setState(false);
        }
    }

    @Override
    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.canBoost = true;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer && !this.damageStat) {
            int n = this.balance;
            this.balance = n + 1;
            event.cancelEvent();
        }
    }
}

