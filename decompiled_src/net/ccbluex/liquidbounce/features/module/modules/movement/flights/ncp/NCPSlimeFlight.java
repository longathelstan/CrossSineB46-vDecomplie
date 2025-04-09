/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.ncp;

import java.util.LinkedList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001(B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0019H\u0016J\b\u0010\u001d\u001a\u00020\u0019H\u0016J\u0010\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020'H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/NCPSlimeFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "firstlaunch", "", "needreset", "packetBuffer", "Ljava/util/LinkedList;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "packets", "", "stage", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/NCPSlimeFlight$Stage;", "swingModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "test", "", "ticks", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "timerBoostValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "vanillabypass", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "Stage", "CrossSine"})
public final class NCPSlimeFlight
extends FlightMode {
    @NotNull
    private final BoolValue timerBoostValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "DoTimer"), true);
    @NotNull
    private final ListValue swingModeValue;
    @NotNull
    private Stage stage;
    private int ticks;
    private int packets;
    @NotNull
    private final MSTimer timer;
    private boolean firstlaunch;
    private boolean needreset;
    private int vanillabypass;
    private double test;
    @NotNull
    private final LinkedList<Packet<INetHandlerPlayServer>> packetBuffer;

    public NCPSlimeFlight() {
        super("NCPSlime");
        String[] stringArray = new String[]{"Normal", "Packet"};
        this.swingModeValue = new ListValue(Intrinsics.stringPlus(this.getValuePrefix(), "SwingMode"), stringArray, "Normal");
        this.stage = Stage.WAITING;
        this.timer = new MSTimer();
        this.firstlaunch = true;
        this.test = 1.0;
        this.packetBuffer = new LinkedList();
    }

    @Override
    public void onEnable() {
        this.test = 1.0;
        this.needreset = false;
        this.firstlaunch = true;
        this.vanillabypass = 0;
        this.packets = 0;
        this.ticks = 0;
        this.packetBuffer.clear();
        this.timer.reset();
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            this.stage = Stage.WAITING;
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
        } else {
            this.stage = Stage.INFFLYING;
        }
    }

    @Override
    public void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.packetBuffer.clear();
        this.timer.reset();
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        block6: {
            block8: {
                block7: {
                    Intrinsics.checkNotNullParameter(event, "event");
                    if (this.stage == Stage.WAITING) {
                        return;
                    }
                    Packet<?> packet = event.getPacket();
                    if (packet instanceof C03PacketPlayer) {
                        ((C03PacketPlayer)packet).field_149474_g = true;
                        this.packetBuffer.add(packet);
                        event.cancelEvent();
                    }
                    if (!(packet instanceof S12PacketEntityVelocity)) break block6;
                    if (MinecraftInstance.mc.field_71439_g == null) break block7;
                    WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                    Entity entity = worldClient == null ? null : worldClient.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
                    if (entity == null) {
                        return;
                    }
                    if (Intrinsics.areEqual(entity, MinecraftInstance.mc.field_71439_g)) break block8;
                }
                return;
            }
            event.cancelEvent();
        }
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        for (Packet packet : this.packetBuffer) {
            Intrinsics.checkNotNullExpressionValue(packet, "packet");
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)packet);
        }
        this.packetBuffer.clear();
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.timer.hasTimePassed((long)(Math.random() * (double)1000))) {
            this.timer.reset();
            for (Packet packet : this.packetBuffer) {
                Intrinsics.checkNotNullExpressionValue(packet, "packet");
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)packet);
            }
            this.packetBuffer.clear();
        }
        switch (WhenMappings.$EnumSwitchMapping$0[this.stage.ordinal()]) {
            case 1: {
                MovingObjectPosition movingObjectPosition;
                if (!(MinecraftInstance.mc.field_71439_g.field_70163_u >= this.getFlight().getLaunchY() + 0.8)) break;
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    EnumFacing enumFacing;
                    MovingObjectPosition movingObjectPosition2;
                    RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0f), 0);
                    EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                    MovingObjectPosition movingObjectPosition3 = movingObjectPosition2 = EntityExtensionKt.rayTraceWithCustomRotation((Entity)entityPlayerSP, 4.5, MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0f);
                    if ((movingObjectPosition3 == null ? null : movingObjectPosition3.field_72313_a) != MovingObjectPosition.MovingObjectType.BLOCK) {
                        return;
                    }
                    BlockPos blockPos = movingObjectPosition2.func_178782_a();
                    if (MinecraftInstance.mc.field_71442_b.func_180512_c(blockPos, enumFacing = movingObjectPosition2.field_178784_b)) {
                        this.stage = Stage.FLYING;
                    }
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                    break;
                }
                RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0f), 0);
                int slot = -1;
                int blockPos = 0;
                while (blockPos < 9) {
                    int j;
                    if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(j = blockPos++) == null || !(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(j).func_77973_b() instanceof ItemBlock)) continue;
                    Integer n = PlayerUtils.INSTANCE.findSlimeBlock();
                    Intrinsics.checkNotNull(n);
                    slot = n;
                    break;
                }
                if (slot == -1) {
                    this.getFlight().setState(false);
                    return;
                }
                int oldSlot = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
                MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c = slot;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                MovingObjectPosition movingObjectPosition4 = movingObjectPosition = EntityExtensionKt.rayTraceWithCustomRotation((Entity)entityPlayerSP, 4.5, MinecraftInstance.mc.field_71439_g.field_70177_z, 90.0f);
                if ((movingObjectPosition4 == null ? null : movingObjectPosition4.field_72313_a) != MovingObjectPosition.MovingObjectType.BLOCK) {
                    return;
                }
                BlockPos blockPos2 = movingObjectPosition.func_178782_a();
                EnumFacing enumFacing = movingObjectPosition.field_178784_b;
                Object object = movingObjectPosition.field_72307_f;
                Intrinsics.checkNotNullExpressionValue(object, "movingObjectPosition.hitVec");
                Vec3 hitVec = object;
                if (MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, MinecraftInstance.mc.field_71439_g.func_70694_bm(), blockPos2, enumFacing, hitVec)) {
                    String string = ((String)this.swingModeValue.get()).toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    object = string;
                    if (Intrinsics.areEqual(object, "normal")) {
                        MinecraftInstance.mc.field_71439_g.func_71038_i();
                    } else if (Intrinsics.areEqual(object, "packet")) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
                    }
                }
                MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
                break;
            }
            case 2: 
            case 3: {
                if (((Boolean)this.timerBoostValue.get()).booleanValue()) {
                    int n = this.ticks;
                    this.ticks = n + 1;
                    boolean bl = 1 <= (n = this.ticks) ? n < 11 : false;
                    if (bl) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 2.0f;
                    } else {
                        boolean bl2 = 10 <= n ? n < 16 : false;
                        if (bl2) {
                            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.4f;
                        }
                    }
                    if (this.ticks < 15) break;
                    this.ticks = 0;
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 0.6f;
                    break;
                }
                MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
            }
        }
    }

    @Override
    public void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        switch (WhenMappings.$EnumSwitchMapping$0[this.stage.ordinal()]) {
            case 1: {
                if (!(event.getBlock() instanceof BlockAir) || !((double)event.getY() <= this.getFlight().getLaunchY() + (double)100)) break;
                event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)((double)event.getX() + 1.0), (double)this.getFlight().getLaunchY(), (double)((double)event.getZ() + 1.0)));
                break;
            }
            case 2: {
                if (!(event.getBlock() instanceof BlockAir) || !((double)event.getY() <= this.getFlight().getLaunchY() + 1.0)) break;
                event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)((double)event.getX() + 1.0), (double)this.getFlight().getLaunchY(), (double)((double)event.getZ() + 1.0)));
                break;
            }
            case 3: {
                if (!(event.getBlock() instanceof BlockAir) || !((double)event.getY() <= MinecraftInstance.mc.field_71439_g.field_70163_u)) break;
                event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)((double)event.getX() + 1.0), (double)this.getFlight().getLaunchY(), (double)((double)event.getZ() + 1.0)));
            }
        }
    }

    @Override
    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.stage == Stage.WAITING) {
            return;
        }
        event.cancelEvent();
    }

    @Override
    public void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.stage == Stage.WAITING) {
            return;
        }
        event.setStepHeight(0.0f);
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/ncp/NCPSlimeFlight$Stage;", "", "(Ljava/lang/String;I)V", "WAITING", "FLYING", "INFFLYING", "CrossSine"})
    public static final class Stage
    extends Enum<Stage> {
        public static final /* enum */ Stage WAITING = new Stage();
        public static final /* enum */ Stage FLYING = new Stage();
        public static final /* enum */ Stage INFFLYING = new Stage();
        private static final /* synthetic */ Stage[] $VALUES;

        public static Stage[] values() {
            return (Stage[])$VALUES.clone();
        }

        public static Stage valueOf(String value) {
            return Enum.valueOf(Stage.class, value);
        }

        static {
            $VALUES = stageArray = new Stage[]{Stage.WAITING, Stage.FLYING, Stage.INFFLYING};
        }
    }

    @Metadata(mv={1, 6, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Stage.values().length];
            nArray[Stage.WAITING.ordinal()] = 1;
            nArray[Stage.FLYING.ordinal()] = 2;
            nArray[Stage.INFFLYING.ordinal()] = 3;
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

