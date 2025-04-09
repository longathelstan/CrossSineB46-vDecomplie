/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Jesus;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="Jesus", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0014\u001a\u00020\r2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020 H\u0007J\u0012\u0010!\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\"H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006#"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Jesus;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "jumpMotionValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "nextTick", "", "noJumpValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "isLiquidBlock", "bb", "Lnet/minecraft/util/AxisAlignedBB;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Jesus
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final BoolValue noJumpValue;
    @NotNull
    private final Value<Float> jumpMotionValue;
    private boolean nextTick;
    @NotNull
    private final MSTimer msTimer;

    public Jesus() {
        String[] stringArray = new String[]{"Vanilla", "NCP", "Jump", "AAC", "AACFly", "AAC3.3.11", "AAC4.2.1", "Horizon1.4.6", "Spartan", "Twilight", "Matrix", "Medusa", "Vulcan", "Dolphin", "Legit"};
        this.modeValue = new ListValue("Mode", stringArray, "Vanilla");
        this.noJumpValue = new BoolValue("NoJump", false);
        this.jumpMotionValue = new FloatValue("JumpMotion", 0.5f, 0.1f, 1.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Jesus this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("Jump") || this.this$0.getModeValue().equals("AACFly");
            }
        });
        this.msTimer = new MSTimer();
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    private final boolean isLiquidBlock(AxisAlignedBB bb) {
        return BlockUtils.collideBlock(bb, isLiquidBlock.1.INSTANCE);
    }

    static /* synthetic */ boolean isLiquidBlock$default(Jesus jesus, AxisAlignedBB axisAlignedBB, int n, Object object) {
        if ((n & 1) != 0) {
            AxisAlignedBB axisAlignedBB2 = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
            Intrinsics.checkNotNullExpressionValue(axisAlignedBB2, "mc.thePlayer.entityBoundingBox");
            axisAlignedBB = axisAlignedBB2;
        }
        return jesus.isLiquidBlock(axisAlignedBB);
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71439_g.func_70093_af()) {
            return;
        }
        BlockPos blockPos = MinecraftInstance.mc.field_71439_g.func_180425_c().func_177977_b();
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "medusa": 
            case "ncp": 
            case "vulcan": {
                if (!Jesus.isLiquidBlock$default(this, null, 1, null) || !MinecraftInstance.mc.field_71439_g.func_70055_a(Material.field_151579_a)) break;
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.08;
                break;
            }
            case "jump": {
                if (BlockUtils.getBlock(blockPos) != Blocks.field_150355_j || !MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.jumpMotionValue.get()).floatValue();
                break;
            }
            case "aac": {
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E && BlockUtils.getBlock(blockPos) == Blocks.field_150355_j || MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                    if (!MinecraftInstance.mc.field_71439_g.func_70051_ag()) {
                        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70159_w *= 0.99999;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70181_x *= 0.0;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70179_y *= 0.99999;
                        if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = (float)((int)(MinecraftInstance.mc.field_71439_g.field_70163_u - (double)((int)(MinecraftInstance.mc.field_71439_g.field_70163_u - 1.0)))) / 8.0f;
                        }
                    } else {
                        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70159_w *= 0.99999;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70181_x *= 0.0;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70179_y *= 0.99999;
                        if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = (float)((int)(MinecraftInstance.mc.field_71439_g.field_70163_u - (double)((int)(MinecraftInstance.mc.field_71439_g.field_70163_u - 1.0)))) / 8.0f;
                        }
                    }
                    if (MinecraftInstance.mc.field_71439_g.field_70143_R >= 4.0f) {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = -0.004;
                    } else if (MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.09;
                    }
                }
                if (MinecraftInstance.mc.field_71439_g.field_70737_aN == 0) break;
                MinecraftInstance.mc.field_71439_g.field_70122_E = false;
                break;
            }
            case "matrix": {
                if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = false;
                if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.09;
                    return;
                }
                Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v));
                Block blockUp = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.1, MinecraftInstance.mc.field_71439_g.field_70161_v));
                if (blockUp instanceof BlockLiquid) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
                } else if (block instanceof BlockLiquid) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                }
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 1.15;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 1.15;
                break;
            }
            case "spartan": {
                if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                    EntityPlayerSP block = MinecraftInstance.mc.field_71439_g;
                    block.field_70181_x += 0.15;
                    return;
                }
                Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v));
                Block blockUp = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.1, MinecraftInstance.mc.field_71439_g.field_70161_v));
                if (blockUp instanceof BlockLiquid) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
                } else if (block instanceof BlockLiquid) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                }
                MinecraftInstance.mc.field_71439_g.field_70122_E = true;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 1.085;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 1.085;
                break;
            }
            case "aac3.3.11": {
                if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 1.17;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 1.17;
                if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.24;
                    break;
                }
                if (MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150350_a) break;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70181_x += 0.04;
                break;
            }
            case "dolphin": {
                if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70181_x += (double)0.04f;
                break;
            }
            case "aac4.2.1": {
                if ((MinecraftInstance.mc.field_71439_g.field_70122_E || BlockUtils.getBlock(blockPos) != Blocks.field_150355_j) && !MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70181_x *= 0.0;
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.08f;
                if (MinecraftInstance.mc.field_71439_g.field_70143_R > 0.0f) {
                    return;
                }
                if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = true;
                break;
            }
            case "horizon1.4.6": {
                MinecraftInstance.mc.field_71474_y.field_74314_A.field_74513_e = MinecraftInstance.mc.field_71439_g.func_70090_H();
                if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                MovementUtils.INSTANCE.strafe();
                if (!MovementUtils.INSTANCE.isMoving() || MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70181_x += 0.13;
                break;
            }
            case "twilight": {
                if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) break;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 1.04;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 1.04;
                MovementUtils.INSTANCE.strafe();
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!MinecraftInstance.mc.field_71439_g.func_70090_H()) {
            return;
        }
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String string2 = string;
        if (Intrinsics.areEqual(string2, "aacfly")) {
            event.setY(((Number)this.jumpMotionValue.get()).floatValue());
            MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.jumpMotionValue.get()).floatValue();
        } else if (Intrinsics.areEqual(string2, "twilight")) {
            event.setY(0.01);
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.01;
        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        block16: {
            Intrinsics.checkNotNullParameter(event, "event");
            if (MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71439_g.func_174813_aQ() == null) {
                return;
            }
            if (!(event.getBlock() instanceof BlockLiquid) || Jesus.isLiquidBlock$default(this, null, 1, null) || MinecraftInstance.mc.field_71439_g.func_70093_af()) break block16;
            String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "medusa": 
                case "ncp": 
                case "vulcan": 
                case "vanilla": 
                case "jump": {
                    event.setBoundingBox(AxisAlignedBB.func_178781_a((double)event.getX(), (double)event.getY(), (double)event.getZ(), (double)(event.getX() + 1), (double)(event.getY() + 1), (double)(event.getZ() + 1)));
                    if (!Intrinsics.areEqual(this.modeValue.get(), "Vulcan")) break;
                    MovementUtils.INSTANCE.strafe(MovementUtils.INSTANCE.getSpeed() * 0.39f);
                }
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        block16: {
            Intrinsics.checkNotNullParameter(event, "event");
            if (MinecraftInstance.mc.field_71439_g == null) {
                return;
            }
            if (!(event.getPacket() instanceof C03PacketPlayer)) break block16;
            switch ((String)this.modeValue.get()) {
                case "NCP": {
                    if (!this.isLiquidBlock(new AxisAlignedBB(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72336_d, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72337_e, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72334_f, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72340_a, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72339_c))) break;
                    boolean bl = this.nextTick = !this.nextTick;
                    if (!this.nextTick) break;
                    Packet<?> packet = event.getPacket();
                    ((C03PacketPlayer)packet).field_149477_b -= 0.001;
                    break;
                }
                case "Medusa": {
                    this.nextTick = !this.nextTick;
                    ((C03PacketPlayer)event.getPacket()).field_149477_b = MinecraftInstance.mc.field_71439_g.field_70163_u + (this.nextTick ? 0.1 : -0.1);
                    if (this.msTimer.hasTimePassed(1000L)) {
                        ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
                        this.msTimer.reset();
                        break;
                    }
                    ((C03PacketPlayer)event.getPacket()).field_149474_g = false;
                    break;
                }
                case "Vulcan": {
                    this.nextTick = !this.nextTick;
                    ((C03PacketPlayer)event.getPacket()).field_149477_b = MinecraftInstance.mc.field_71439_g.field_70163_u + (this.nextTick ? 0.1 : -0.1);
                    if (this.msTimer.hasTimePassed(1500L)) {
                        event.cancelEvent();
                        PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer(true)));
                        this.msTimer.reset();
                        break;
                    }
                    ((C03PacketPlayer)event.getPacket()).field_149474_g = false;
                }
            }
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        Block block = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.01, MinecraftInstance.mc.field_71439_g.field_70161_v));
        if ((((Boolean)this.noJumpValue.get()).booleanValue() || Intrinsics.areEqual(this.modeValue.get(), "Vulcan")) && block instanceof BlockLiquid) {
            event.cancelEvent();
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }
}

