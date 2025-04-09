/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.vulcan;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0018B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0017H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan2Flight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "doCancel", "", "isSuccess", "stage", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan2Flight$FlyStage;", "startX", "", "startY", "startZ", "timerValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "vticks", "", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "FlyStage", "CrossSine"})
public final class Vulcan2Flight
extends FlightMode {
    @NotNull
    private final FloatValue timerValue = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 1.0f, 0.1f, 6.0f);
    private boolean isSuccess;
    private int vticks;
    private boolean doCancel;
    @NotNull
    private FlyStage stage = FlyStage.FLYING;
    private double startX;
    private double startZ;
    private double startY;

    public Vulcan2Flight() {
        super("Vulcan2");
    }

    @Override
    public void onEnable() {
        this.vticks = 0;
        this.doCancel = false;
        if (!(MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0 == 0.0)) {
            this.getFlight().setState(false);
            ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lVulcan-Fly\u00a78] \u00a7cPlease stand on a solid block to fly!");
            this.isSuccess = true;
            return;
        }
        this.stage = FlyStage.FLYING;
        this.isSuccess = false;
        ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lVulcan-Fly\u00a78] \u00a7aPlease press Sneak before you land on ground!");
        ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lVulcan-Fly\u00a78] \u00a77Tips: DO NOT Use killaura when you're flying!");
        this.startX = MinecraftInstance.mc.field_71439_g.field_70165_t;
        this.startY = MinecraftInstance.mc.field_71439_g.field_70163_u;
        this.startZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        if (!this.isSuccess) {
            MinecraftInstance.mc.field_71439_g.func_70107_b(this.startX, this.startY, this.startZ);
            ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lVulcan-Fly\u00a78] \u00a7cFly attempt Failed...");
            ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lVulcan-Fly\u00a78] \u00a7cIf it keeps happen, DONT use it again in CURRENT gameplay");
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        switch (WhenMappings.$EnumSwitchMapping$0[this.stage.ordinal()]) {
            case 1: {
                this.isSuccess = false;
                MovementUtils.INSTANCE.resetMotion(true);
                MovementUtils.INSTANCE.strafe(((Number)this.timerValue.get()).floatValue());
                this.doCancel = true;
                if (MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e) {
                    MovementUtils.INSTANCE.strafe(0.45f);
                }
                if (!MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e || MinecraftInstance.mc.field_71439_g.field_70173_aa % 2 != 1) break;
                double fixedY = MinecraftInstance.mc.field_71439_g.field_70163_u - MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0;
                Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, fixedY - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v));
                if (block == null) {
                    return;
                }
                Block underBlock2 = block;
                if (underBlock2.func_149730_j()) {
                    this.stage = FlyStage.WAIT_APPLY;
                    MovementUtils.INSTANCE.resetMotion(true);
                    MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
                    this.doCancel = false;
                    MinecraftInstance.mc.field_71439_g.field_70122_E = false;
                    double fixedX = MinecraftInstance.mc.field_71439_g.field_70165_t - MinecraftInstance.mc.field_71439_g.field_70165_t % 1.0;
                    double fixedZ = MinecraftInstance.mc.field_71439_g.field_70161_v - MinecraftInstance.mc.field_71439_g.field_70161_v % 1.0;
                    fixedX = fixedX > 0.0 ? (fixedX += 0.5) : (fixedX -= 0.5);
                    fixedZ = fixedZ > 0.0 ? (fixedZ += 0.5) : (fixedZ -= 0.5);
                    MinecraftInstance.mc.field_71439_g.func_70107_b(fixedX, fixedY, fixedZ);
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, fixedY, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
                    this.doCancel = true;
                    ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lVulcan-Fly\u00a78] \u00a7aWaiting for landing...");
                    break;
                }
                ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lVulcan-Fly\u00a78] \u00a7cYou can only land on a solid block!");
                break;
            }
            case 2: {
                int fixedY = this.vticks;
                this.vticks = fixedY + 1;
                this.doCancel = false;
                if (this.vticks == 60) {
                    ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lVulcan-Fly\u00a78] \u00a7cSeems took a long time! Please turn off the Fly manually");
                }
                MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                MovementUtils.INSTANCE.resetMotion(true);
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
                double fixedY2 = MinecraftInstance.mc.field_71439_g.field_70163_u - MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0;
                if (MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -10.0, 0.0)).isEmpty() && MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0, -12.0, 0.0)).isEmpty()) {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, fixedY2 - (double)10, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
                } else {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, fixedY2 - (double)1024, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
                }
                this.doCancel = true;
            }
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            if (this.doCancel) {
                event.cancelEvent();
                this.doCancel = false;
            }
            ((C03PacketPlayer)packet).field_149474_g = true;
        } else if (packet instanceof S08PacketPlayerPosLook) {
            if (this.stage == FlyStage.WAIT_APPLY && Math.sqrt((((S08PacketPlayerPosLook)packet).field_148940_a - MinecraftInstance.mc.field_71439_g.field_70165_t) * (((S08PacketPlayerPosLook)packet).field_148940_a - MinecraftInstance.mc.field_71439_g.field_70165_t) + (((S08PacketPlayerPosLook)packet).field_148938_b - MinecraftInstance.mc.field_71439_g.field_70163_u) * (((S08PacketPlayerPosLook)packet).field_148938_b - MinecraftInstance.mc.field_71439_g.field_70163_u) + (((S08PacketPlayerPosLook)packet).field_148939_c - MinecraftInstance.mc.field_71439_g.field_70161_v) * (((S08PacketPlayerPosLook)packet).field_148939_c - MinecraftInstance.mc.field_71439_g.field_70161_v)) < 1.4) {
                this.isSuccess = true;
                this.getFlight().setState(false);
                return;
            }
            event.cancelEvent();
        } else if (packet instanceof C0BPacketEntityAction) {
            event.cancelEvent();
        }
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/vulcan/Vulcan2Flight$FlyStage;", "", "(Ljava/lang/String;I)V", "FLYING", "WAIT_APPLY", "CrossSine"})
    public static final class FlyStage
    extends Enum<FlyStage> {
        public static final /* enum */ FlyStage FLYING = new FlyStage();
        public static final /* enum */ FlyStage WAIT_APPLY = new FlyStage();
        private static final /* synthetic */ FlyStage[] $VALUES;

        public static FlyStage[] values() {
            return (FlyStage[])$VALUES.clone();
        }

        public static FlyStage valueOf(String value) {
            return Enum.valueOf(FlyStage.class, value);
        }

        static {
            $VALUES = flyStageArray = new FlyStage[]{FlyStage.FLYING, FlyStage.WAIT_APPLY};
        }
    }

    @Metadata(mv={1, 6, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[FlyStage.values().length];
            nArray[FlyStage.FLYING.ordinal()] = 1;
            nArray[FlyStage.WAIT_APPLY.ordinal()] = 2;
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

