/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.FastBreak;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.tickTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="Nuker", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackedBlocks", "Ljava/util/ArrayList;", "Lnet/minecraft/util/BlockPos;", "Lkotlin/collections/ArrayList;", "blockHitDelay", "", "currentBlock", "hitDelayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "layerValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "nuke", "nukeDelayValue", "nukeTimer", "Lnet/ccbluex/liquidbounce/utils/timer/tickTimer;", "nukeValue", "priorityValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "radiusValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "rotationsValue", "throughWallsValue", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "validBlock", "", "block", "Lnet/minecraft/block/Block;", "Companion", "CrossSine"})
public final class Nuker
extends Module {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final FloatValue radiusValue = new FloatValue("Radius", 5.2f, 1.0f, 6.0f);
    @NotNull
    private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
    @NotNull
    private final ListValue priorityValue;
    @NotNull
    private final BoolValue rotationsValue;
    @NotNull
    private final BoolValue layerValue;
    @NotNull
    private final IntegerValue hitDelayValue;
    @NotNull
    private final IntegerValue nukeValue;
    @NotNull
    private final IntegerValue nukeDelayValue;
    @NotNull
    private final ArrayList<BlockPos> attackedBlocks;
    @Nullable
    private BlockPos currentBlock;
    private int blockHitDelay;
    @NotNull
    private tickTimer nukeTimer;
    private int nuke;
    private static float currentDamage;

    public Nuker() {
        String[] stringArray = new String[]{"Distance", "Hardness"};
        this.priorityValue = new ListValue("Priority", stringArray, "Distance");
        this.rotationsValue = new BoolValue("Rotations", true);
        this.layerValue = new BoolValue("Layer", false);
        this.hitDelayValue = new IntegerValue("HitDelay", 4, 0, 20);
        this.nukeValue = new IntegerValue("Nuke", 1, 1, 20);
        this.nukeDelayValue = new IntegerValue("NukeDelay", 1, 1, 20);
        this.attackedBlocks = new ArrayList();
        this.nukeTimer = new tickTimer();
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        block45: {
            void $this$filterTo$iv$iv;
            EntityPlayerSP thePlayer;
            block42: {
                Map.Entry element$iv$iv;
                String $this$filterTo$iv$iv2;
                Map.Entry $this$filter$iv;
                Intrinsics.checkNotNullParameter(event, "event");
                if (this.blockHitDelay > 0) {
                    FastBreak fastBreak = CrossSine.INSTANCE.getModuleManager().get(FastBreak.class);
                    Intrinsics.checkNotNull(fastBreak);
                    if (!fastBreak.getState()) {
                        int n = this.blockHitDelay;
                        this.blockHitDelay = n + -1;
                        return;
                    }
                }
                this.nukeTimer.update();
                if (this.nukeTimer.hasTimePassed(((Number)this.nukeDelayValue.get()).intValue())) {
                    this.nuke = 0;
                    this.nukeTimer.reset();
                }
                this.attackedBlocks.clear();
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP);
                thePlayer = entityPlayerSP;
                if (MinecraftInstance.mc.field_71442_b.func_78758_h()) break block42;
                Map<BlockPos, Block> map = BlockUtils.searchBlocks(MathKt.roundToInt(((Number)this.radiusValue.get()).floatValue()) + 1);
                boolean $i$f$filter2 = false;
                void var6_9 = $this$filter$iv;
                Map destination$iv$iv = new LinkedHashMap();
                boolean $i$f$filterTo2 = false;
                Iterator iterator2 = $this$filterTo$iv$iv2.entrySet().iterator();
                while (iterator2.hasNext()) {
                    boolean bl;
                    Map.Entry $dstr$pos$block = element$iv$iv = iterator2.next();
                    boolean bl2 = false;
                    BlockPos pos = (BlockPos)$dstr$pos$block.getKey();
                    Block block = (Block)$dstr$pos$block.getValue();
                    if (BlockUtils.getCenterDistance(pos) <= (double)((Number)this.radiusValue.get()).floatValue() && this.validBlock(block)) {
                        if (((Boolean)this.layerValue.get()).booleanValue() && (double)pos.func_177956_o() < thePlayer.field_70163_u) {
                            bl = false;
                        } else if (!((Boolean)this.throughWallsValue.get()).booleanValue()) {
                            Vec3 eyesPos = new Vec3(thePlayer.field_70165_t, thePlayer.func_174813_aQ().field_72338_b + (double)thePlayer.eyeHeight, thePlayer.field_70161_v);
                            Vec3 blockVec = new Vec3((double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 0.5, (double)pos.func_177952_p() + 0.5);
                            WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                            Intrinsics.checkNotNull(worldClient);
                            MovingObjectPosition rayTrace = worldClient.func_147447_a(eyesPos, blockVec, false, true, false);
                            bl = rayTrace != null && Intrinsics.areEqual(rayTrace.func_178782_a(), pos);
                        } else {
                            bl = true;
                        }
                    } else {
                        bl = false;
                    }
                    if (!bl) continue;
                    destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
                }
                Map validBlocks = MapsKt.toMutableMap(destination$iv$iv);
                do {
                    BlockPos blockPos;
                    block44: {
                        Block block;
                        block43: {
                            Map.Entry entry;
                            BlockPos safePos;
                            Block block2;
                            BlockPos pos;
                            BlockPos safePos2;
                            if (Intrinsics.areEqual($this$filterTo$iv$iv2 = (String)this.priorityValue.get(), "Distance")) {
                                Map.Entry entry2;
                                iterator2 = ((Iterable)validBlocks.entrySet()).iterator();
                                if (!iterator2.hasNext()) {
                                    entry2 = null;
                                } else {
                                    element$iv$iv = iterator2.next();
                                    if (!iterator2.hasNext()) {
                                        entry2 = element$iv$iv;
                                    } else {
                                        Map.Entry $dstr$pos$block22 = element$iv$iv;
                                        boolean bl = false;
                                        BlockPos pos2 = (BlockPos)$dstr$pos$block22.getKey();
                                        Block block3 = (Block)$dstr$pos$block22.getValue();
                                        double distance = BlockUtils.getCenterDistance(pos2);
                                        safePos2 = new BlockPos(thePlayer.field_70165_t, thePlayer.field_70163_u - 1.0, thePlayer.field_70161_v);
                                        double $dstr$pos$block22 = pos2.func_177958_n() == safePos2.func_177958_n() && safePos2.func_177956_o() <= pos2.func_177956_o() && pos2.func_177952_p() == safePos2.func_177952_p() ? Double.MAX_VALUE - distance : distance;
                                        do {
                                            Map.Entry $dstr$pos$block32 = bl = iterator2.next();
                                            $i$a$-minByOrNull-Nuker$onUpdate$1 = false;
                                            pos = (BlockPos)$dstr$pos$block32.getKey();
                                            block2 = (Block)$dstr$pos$block32.getValue();
                                            double distance2 = BlockUtils.getCenterDistance(pos);
                                            safePos = new BlockPos(thePlayer.field_70165_t, thePlayer.field_70163_u - 1.0, thePlayer.field_70161_v);
                                            double $dstr$pos$block32 = pos.func_177958_n() == safePos.func_177958_n() && safePos.func_177956_o() <= pos.func_177956_o() && pos.func_177952_p() == safePos.func_177952_p() ? Double.MAX_VALUE - distance2 : distance2;
                                            if (Double.compare($dstr$pos$block22, $dstr$pos$block32) <= 0) continue;
                                            element$iv$iv = bl;
                                            $dstr$pos$block22 = $dstr$pos$block32;
                                        } while (iterator2.hasNext());
                                        entry2 = element$iv$iv;
                                    }
                                }
                                entry = entry2;
                            } else if (Intrinsics.areEqual($this$filterTo$iv$iv2, "Hardness")) {
                                Map.Entry entry3;
                                iterator2 = ((Iterable)validBlocks.entrySet()).iterator();
                                if (!iterator2.hasNext()) {
                                    entry3 = null;
                                } else {
                                    element$iv$iv = iterator2.next();
                                    if (!iterator2.hasNext()) {
                                        entry3 = element$iv$iv;
                                    } else {
                                        Map.Entry $dstr$pos$block42 = element$iv$iv;
                                        boolean bl = false;
                                        BlockPos pos3 = (BlockPos)$dstr$pos$block42.getKey();
                                        Block block4 = (Block)$dstr$pos$block42.getValue();
                                        EntityPlayer entityPlayer = (EntityPlayer)thePlayer;
                                        WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                                        Intrinsics.checkNotNull(worldClient);
                                        double hardness = block4.func_180647_a(entityPlayer, (World)worldClient, pos3);
                                        safePos2 = new BlockPos(thePlayer.field_70165_t, thePlayer.field_70163_u - 1.0, thePlayer.field_70161_v);
                                        double $dstr$pos$block42 = pos3.func_177958_n() == safePos2.func_177958_n() && safePos2.func_177956_o() <= pos3.func_177956_o() && pos3.func_177952_p() == safePos2.func_177952_p() ? Double.MIN_VALUE + hardness : hardness;
                                        do {
                                            Map.Entry $dstr$pos$block52 = bl = iterator2.next();
                                            $i$a$-maxByOrNull-Nuker$onUpdate$2 = false;
                                            pos = (BlockPos)$dstr$pos$block52.getKey();
                                            block2 = (Block)$dstr$pos$block52.getValue();
                                            EntityPlayer entityPlayer2 = (EntityPlayer)thePlayer;
                                            WorldClient worldClient2 = MinecraftInstance.mc.field_71441_e;
                                            Intrinsics.checkNotNull(worldClient2);
                                            double hardness2 = block2.func_180647_a(entityPlayer2, (World)worldClient2, pos);
                                            safePos = new BlockPos(thePlayer.field_70165_t, thePlayer.field_70163_u - 1.0, thePlayer.field_70161_v);
                                            double $dstr$pos$block52 = pos.func_177958_n() == safePos.func_177958_n() && safePos.func_177956_o() <= pos.func_177956_o() && pos.func_177952_p() == safePos.func_177952_p() ? Double.MIN_VALUE + hardness2 : hardness2;
                                            if (Double.compare($dstr$pos$block42, $dstr$pos$block52) >= 0) continue;
                                            element$iv$iv = bl;
                                            $dstr$pos$block42 = $dstr$pos$block52;
                                        } while (iterator2.hasNext());
                                        entry3 = element$iv$iv;
                                    }
                                }
                                entry = entry3;
                            } else {
                                return;
                            }
                            Map.Entry $i$f$filter2 = entry;
                            if ($i$f$filter2 == null) {
                                return;
                            }
                            $this$filter$iv = $i$f$filter2;
                            blockPos = (BlockPos)$this$filter$iv.getKey();
                            block = (Block)$this$filter$iv.getValue();
                            if (!Intrinsics.areEqual(blockPos, this.currentBlock)) {
                                currentDamage = 0.0f;
                            }
                            if (((Boolean)this.rotationsValue.get()).booleanValue()) {
                                VecRotation rotation;
                                if (RotationUtils.faceBlock(blockPos) == null) {
                                    return;
                                }
                                RotationUtils.setTargetRotation(rotation.getRotation(), 0);
                            }
                            this.currentBlock = blockPos;
                            this.attackedBlocks.add(blockPos);
                            if (!(currentDamage == 0.0f)) break block43;
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                            EntityPlayer entityPlayer = (EntityPlayer)thePlayer;
                            WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                            Intrinsics.checkNotNull(worldClient);
                            if (block.func_180647_a(entityPlayer, (World)worldClient, blockPos) >= 1.0f) break block44;
                        }
                        thePlayer.func_71038_i();
                        EntityPlayer entityPlayer = (EntityPlayer)thePlayer;
                        WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                        Intrinsics.checkNotNull(worldClient);
                        WorldClient worldClient3 = MinecraftInstance.mc.field_71441_e;
                        Intrinsics.checkNotNull(worldClient3);
                        worldClient3.func_175715_c(thePlayer.func_145782_y(), blockPos, (int)((currentDamage += block.func_180647_a(entityPlayer, (World)worldClient, blockPos)) * 10.0f) - 1);
                        if (currentDamage >= 1.0f) {
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                            MinecraftInstance.mc.field_71442_b.func_178888_a(blockPos, EnumFacing.DOWN);
                            this.blockHitDelay = ((Number)this.hitDelayValue.get()).intValue();
                            currentDamage = 0.0f;
                        }
                        return;
                    }
                    currentDamage = 0.0f;
                    thePlayer.func_71038_i();
                    MinecraftInstance.mc.field_71442_b.func_178888_a(blockPos, EnumFacing.DOWN);
                    this.blockHitDelay = ((Number)this.hitDelayValue.get()).intValue();
                    validBlocks.remove(blockPos);
                    int rotation = this.nuke;
                    this.nuke = rotation + 1;
                } while (this.nuke < ((Number)this.nukeValue.get()).intValue());
                break block45;
            }
            ItemStack itemStack = thePlayer.func_70694_bm();
            if ((itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemSword) {
                return;
            }
            Map<BlockPos, Block> $this$filter$iv = BlockUtils.searchBlocks(MathKt.roundToInt(((Number)this.radiusValue.get()).floatValue()) + 1);
            boolean $i$f$filter = false;
            Map<BlockPos, Block> blockPos = $this$filter$iv;
            Map destination$iv$iv = new LinkedHashMap();
            boolean $i$f$filterTo = false;
            Iterator $i$f$filterTo2 = $this$filterTo$iv$iv.entrySet().iterator();
            while ($i$f$filterTo2.hasNext()) {
                boolean bl;
                Map.Entry element$iv$iv;
                Map.Entry $dstr$pos$block = element$iv$iv = $i$f$filterTo2.next();
                boolean bl3 = false;
                BlockPos pos = (BlockPos)$dstr$pos$block.getKey();
                Block block = (Block)$dstr$pos$block.getValue();
                if (BlockUtils.getCenterDistance(pos) <= (double)((Number)this.radiusValue.get()).floatValue() && this.validBlock(block)) {
                    if (((Boolean)this.layerValue.get()).booleanValue() && (double)pos.func_177956_o() < thePlayer.field_70163_u) {
                        bl = false;
                    } else if (!((Boolean)this.throughWallsValue.get()).booleanValue()) {
                        Vec3 eyesPos = new Vec3(thePlayer.field_70165_t, thePlayer.func_174813_aQ().field_72338_b + (double)thePlayer.eyeHeight, thePlayer.field_70161_v);
                        Vec3 blockVec = new Vec3((double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 0.5, (double)pos.func_177952_p() + 0.5);
                        WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                        Intrinsics.checkNotNull(worldClient);
                        MovingObjectPosition rayTrace = worldClient.func_147447_a(eyesPos, blockVec, false, true, false);
                        bl = rayTrace != null && Intrinsics.areEqual(rayTrace.func_178782_a(), pos);
                    } else {
                        bl = true;
                    }
                } else {
                    bl = false;
                }
                if (!bl) continue;
                destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
            }
            Map $this$forEach$iv = destination$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator3 = $this$forEach$iv.entrySet().iterator();
            while (iterator3.hasNext()) {
                Map.Entry element$iv;
                Map.Entry $dstr$pos$_u24__u24 = element$iv = iterator3.next();
                boolean bl = false;
                BlockPos pos = (BlockPos)$dstr$pos$_u24__u24.getKey();
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.DOWN));
                thePlayer.func_71038_i();
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.DOWN));
                this.attackedBlocks.add(pos);
            }
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!((Boolean)this.layerValue.get()).booleanValue()) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP);
            double d = entityPlayerSP.field_70165_t;
            EntityPlayerSP entityPlayerSP2 = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP2);
            double d2 = entityPlayerSP2.field_70163_u - 1.0;
            EntityPlayerSP entityPlayerSP3 = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNull(entityPlayerSP3);
            BlockPos safePos = new BlockPos(d, d2, entityPlayerSP3.field_70161_v);
            Block safeBlock = BlockUtils.getBlock(safePos);
            if (safeBlock != null && this.validBlock(safeBlock)) {
                RenderUtils.drawBlockBox(safePos, Color.GREEN, true);
            }
        }
        for (BlockPos blockPos : this.attackedBlocks) {
            RenderUtils.drawBlockBox(blockPos, Color.RED, true);
        }
    }

    private final boolean validBlock(Block block) {
        return !Intrinsics.areEqual(block, Blocks.field_150350_a) && !(block instanceof BlockLiquid) && !Intrinsics.areEqual(block, Blocks.field_150357_h);
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker$Companion;", "", "()V", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "CrossSine"})
    public static final class Companion {
        private Companion() {
        }

        public final float getCurrentDamage() {
            return currentDamage;
        }

        public final void setCurrentDamage(float f) {
            currentDamage = f;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

