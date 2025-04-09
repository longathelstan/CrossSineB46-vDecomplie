/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.BlockIn;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="BlockIn", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%H\u0002J\b\u0010\u0006\u001a\u00020'H\u0002J\u000e\u0010(\u001a\b\u0012\u0004\u0012\u00020%0)H\u0002J(\u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020,2\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010-\u001a\u00020.H\u0002J\b\u0010/\u001a\u00020'H\u0016J\b\u00100\u001a\u00020'H\u0016J\u0010\u00101\u001a\u00020'2\u0006\u00102\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020'2\u0006\u00102\u001a\u000205H\u0007J\u0010\u00106\u001a\u00020'2\u0006\u00102\u001a\u000207H\u0007J\u0010\u00108\u001a\u00020'2\u0006\u00102\u001a\u000209H\u0007J\b\u0010:\u001a\u00020'H\u0002J\u0010\u0010;\u001a\u00020\r2\u0006\u0010<\u001a\u00020,H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\r0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006="}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/BlockIn;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoPosition", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "blockPlaceDelay", "findBlock", "getDelay", "", "getGetDelay", "()J", "hitable", "jump", "", "lastPlace", "", "maxPlaceDelay", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "maxRotationSpeedValue", "minPlaceDelay", "minRotationSpeedValue", "placeTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "rotationSpeedValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "rotationValue", "silentRotationValue", "sneakValue", "speedValue", "", "swingValue", "targetPlace", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "calculateRotation", "eyesPos", "Lnet/minecraft/util/Vec3;", "hitVec", "", "generateOffsets", "Lkotlin/sequences/Sequence;", "isValidBlockRotation", "neighbor", "Lnet/minecraft/util/BlockPos;", "facing", "Lnet/minecraft/util/EnumFacing;", "onDisable", "onEnable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "place", "search", "blockPosition", "CrossSine"})
public final class BlockIn
extends Module {
    @NotNull
    public static final BlockIn INSTANCE = new BlockIn();
    @NotNull
    private static final BoolValue hitable = new BoolValue("Hitable", false);
    @NotNull
    private static final BoolValue swingValue = new BoolValue("Swing", false);
    @NotNull
    private static final BoolValue rotationValue = new BoolValue("Rotation", false);
    @NotNull
    private static final Value<Boolean> silentRotationValue = new BoolValue("SilentRotation", true).displayable(silentRotationValue.1.INSTANCE);
    @NotNull
    private static final BoolValue sneakValue = new BoolValue("Sneak", false);
    @NotNull
    private static final BoolValue blockPlaceDelay = new BoolValue("PlaceDelay", false);
    @NotNull
    private static final BoolValue findBlock = new BoolValue("AutoBlock", false);
    @NotNull
    private static final BoolValue autoPosition = new BoolValue("AutoPosition", false);
    @NotNull
    private static final Value<Float> speedValue = new FloatValue("Speed", 1.0f, 0.1f, 1.0f).displayable(speedValue.1.INSTANCE);
    @NotNull
    private static final IntegerValue maxPlaceDelay = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int v = ((Number)BlockIn.access$getMinPlaceDelay$p().get()).intValue();
            if (v > newValue) {
                this.set(v);
            }
        }
    }.displayable(maxPlaceDelay.2.INSTANCE);
    @NotNull
    private static final IntegerValue minPlaceDelay = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int v = ((Number)BlockIn.access$getMaxPlaceDelay$p().get()).intValue();
            if (v < newValue) {
                this.set(v);
            }
        }
    }.displayable(minPlaceDelay.2.INSTANCE);
    @NotNull
    private static final Value<Boolean> rotationSpeedValue = new BoolValue("RotationSpeed", true).displayable(rotationSpeedValue.1.INSTANCE);
    @NotNull
    private static final IntegerValue maxRotationSpeedValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int v = ((Number)BlockIn.access$getMinRotationSpeedValue$p().get()).intValue();
            if (v > newValue) {
                this.set(v);
            }
        }
    }.displayable(maxRotationSpeedValue.2.INSTANCE);
    @NotNull
    private static final IntegerValue minRotationSpeedValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int v = ((Number)BlockIn.access$getMaxRotationSpeedValue$p().get()).intValue();
            if (v < newValue) {
                this.set(v);
            }
        }
    }.displayable(minRotationSpeedValue.2.INSTANCE);
    @Nullable
    private static Rotation rotation;
    @Nullable
    private static PlaceInfo targetPlace;
    @NotNull
    private static final TimerMS placeTimer;
    private static int lastPlace;
    private static boolean jump;

    private BlockIn() {
    }

    @Override
    public void onEnable() {
        if (InventoryUtils.INSTANCE.findAutoBlockBlock(true) == -1) {
            ClientUtils.INSTANCE.displayAlert("BlockIn : NO BLOCK FOUND");
            this.setState(false);
        }
        jump = false;
        if (((Boolean)sneakValue.get()).booleanValue() && !MinecraftInstance.mc.field_71439_g.func_70093_af()) {
            ClientUtils.INSTANCE.displayAlert("Sneak before enable module");
            this.setState(false);
        }
    }

    @Override
    public void onDisable() {
        SlotUtils.INSTANCE.stopSet();
        jump = false;
        targetPlace = null;
        rotation = null;
        this.setState(false);
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            ((C08PacketPlayerBlockPlacement)packet).field_149577_f = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149577_f, -1.0f, 1.0f);
            ((C08PacketPlayerBlockPlacement)packet).field_149578_g = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149578_g, -1.0f, 1.0f);
            ((C08PacketPlayerBlockPlacement)packet).field_149584_h = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149584_h, -1.0f, 1.0f);
        }
    }

    @EventTarget
    public final void onPreUpdate(@NotNull PreUpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.findBlock();
        if (((Boolean)findBlock.get()).booleanValue()) {
            if (InventoryUtils.INSTANCE.findAutoBlockBlock(true) == -1) {
                return;
            }
            SlotUtils.INSTANCE.setSlot(InventoryUtils.INSTANCE.findAutoBlockBlock(true) - 36, true, this.getName());
            MinecraftInstance.mc.field_71442_b.func_78765_e();
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)autoPosition.get()).booleanValue()) {
            PlayerUtils.INSTANCE.setCorrectBlockPos(((Number)speedValue.get()).floatValue());
        }
    }

    @EventTarget
    public final void onTick(@NotNull TickEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (lastPlace == 1) {
            MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
            lastPlace = 0;
        }
        if (((Boolean)sneakValue.get()).booleanValue() && !MinecraftInstance.mc.field_71439_g.func_70093_af()) {
            this.onDisable();
        }
        if (!(BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(-1, 0, 0)) instanceof BlockAir || BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(1, 0, 0)) instanceof BlockAir || BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(0, 0, -1)) instanceof BlockAir || BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(0, 0, 1)) instanceof BlockAir || BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(-1, 1, 0)) instanceof BlockAir || BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(1, 1, 0)) instanceof BlockAir || BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(0, 1, -1)) instanceof BlockAir || BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(0, 1, 1)) instanceof BlockAir)) {
            if (BlockUtils.getBlock(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(0, 2, 0)) instanceof BlockAir) {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E && !jump) {
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                    jump = true;
                }
            } else {
                this.onDisable();
            }
        }
        this.place();
    }

    private final void place() {
        block7: {
            block8: {
                block9: {
                    if (((Boolean)blockPlaceDelay.get()).booleanValue() && !placeTimer.hasTimePassed(this.getGetDelay())) break block7;
                    if (!((Boolean)hitable.get()).booleanValue()) break block8;
                    BlockPos blockPos = MinecraftInstance.mc.field_71476_x.func_178782_a();
                    PlaceInfo placeInfo = targetPlace;
                    Intrinsics.checkNotNull(placeInfo);
                    if (!Intrinsics.areEqual(blockPos, placeInfo.getBlockPos())) break block9;
                    EnumFacing enumFacing = MinecraftInstance.mc.field_71476_x.field_178784_b;
                    PlaceInfo placeInfo2 = targetPlace;
                    Intrinsics.checkNotNull(placeInfo2);
                    if (enumFacing == placeInfo2.getEnumFacing()) break block8;
                }
                return;
            }
            if (((Boolean)findBlock.get()).booleanValue() && InventoryUtils.INSTANCE.findAutoBlockBlock(true) != -1 || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
                PlayerControllerMP playerControllerMP = MinecraftInstance.mc.field_71442_b;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                ItemStack itemStack = MinecraftInstance.mc.field_71439_g.func_70694_bm();
                PlaceInfo placeInfo = targetPlace;
                Intrinsics.checkNotNull(placeInfo);
                BlockPos blockPos = placeInfo.getBlockPos();
                PlaceInfo placeInfo3 = targetPlace;
                Intrinsics.checkNotNull(placeInfo3);
                EnumFacing enumFacing = placeInfo3.getEnumFacing();
                PlaceInfo placeInfo4 = targetPlace;
                Intrinsics.checkNotNull(placeInfo4);
                if (playerControllerMP.func_178890_a(entityPlayerSP, worldClient, itemStack, blockPos, enumFacing, placeInfo4.getVec3())) {
                    if (((Boolean)swingValue.get()).booleanValue()) {
                        MinecraftInstance.mc.field_71439_g.func_71038_i();
                    } else {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
                    }
                    CPSCounter.registerClick(CPSCounter.MouseButton.RIGHT);
                    MouseUtils.INSTANCE.setRightClicked(true);
                    placeTimer.reset();
                    int n = lastPlace;
                    lastPlace = n + 1;
                }
            }
            targetPlace = null;
        }
    }

    private final long getGetDelay() {
        return TimeUtils.INSTANCE.randomDelay(((Number)minPlaceDelay.get()).intValue(), ((Number)maxPlaceDelay.get()).intValue());
    }

    /*
     * WARNING - void declaration
     */
    private final void findBlock() {
        Object v0;
        block6: {
            void $this$mapTo$iv$iv;
            BlockPos[] blockPosArray = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(-1, 1, 0), new BlockPos(1, 1, 0), new BlockPos(0, 1, -1), new BlockPos(0, 1, 1), new BlockPos(0, 2, 0)};
            List<BlockPos> offsets = CollectionsKt.listOf(blockPosArray);
            Iterable $this$map$iv = offsets;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void offset;
                BlockPos blockPos = (BlockPos)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(new BlockPos((Entity)MinecraftInstance.mc.field_71439_g).func_177982_a(offset.func_177958_n(), offset.func_177956_o(), offset.func_177952_p()));
            }
            Iterable $this$firstOrNull$iv = (List)destination$iv$iv;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                BlockPos pos = (BlockPos)element$iv;
                boolean bl = false;
                if (!(BlockUtils.getBlock(pos) instanceof BlockAir)) continue;
                v0 = element$iv;
                break block6;
            }
            v0 = null;
        }
        BlockPos blockPos = v0;
        if (blockPos != null) {
            if (!BlockUtils.isReplaceable(blockPos) || this.search(blockPos)) {
                return;
            }
            int n = -1;
            while (n < 2) {
                int x = n++;
                int n2 = -1;
                while (n2 < 2) {
                    int z = n2++;
                    BlockPos blockPos2 = blockPos.func_177982_a(x, 0, z);
                    Intrinsics.checkNotNullExpressionValue(blockPos2, "blockPos.add(x, 0, z)");
                    if (!this.search(blockPos2)) continue;
                    return;
                }
            }
        }
    }

    private final boolean search(BlockPos blockPosition) {
        BlockPos blockPos = blockPosition;
        if (!BlockUtils.isReplaceable(blockPos)) {
            return false;
        }
        Vec3 eyesPos = new Vec3(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)MinecraftInstance.mc.field_71439_g.func_70047_e(), MinecraftInstance.mc.field_71439_g.field_70161_v);
        PlaceRotation placeRotation = null;
        Set checkedPositions = new LinkedHashSet();
        EnumFacing[] enumFacingArray = StaticStorage.facings();
        Intrinsics.checkNotNullExpressionValue(enumFacingArray, "facings()");
        EnumFacing[] enumFacingArray2 = enumFacingArray;
        int n = 0;
        int n2 = enumFacingArray2.length;
        while (n < n2) {
            EnumFacing side = enumFacingArray2[n];
            ++n;
            BlockPos neighbor = blockPos.func_177972_a(side);
            if (!BlockUtils.canBeClicked(neighbor)) continue;
            Vec3 dirVec = new Vec3(side.func_176730_m());
            Iterator<Vec3> iterator2 = this.generateOffsets().iterator();
            while (iterator2.hasNext()) {
                Vec3 offset = iterator2.next();
                Vec3 posVec = new Vec3((Vec3i)blockPos).func_178787_e(offset);
                if (checkedPositions.contains(posVec)) continue;
                Intrinsics.checkNotNullExpressionValue(posVec, "posVec");
                checkedPositions.add(posVec);
                Vec3 hitVec = posVec.func_178787_e(new Vec3(dirVec.field_72450_a * 0.5, dirVec.field_72448_b * 0.5, dirVec.field_72449_c * 0.5));
                Intrinsics.checkNotNullExpressionValue(hitVec, "hitVec");
                Rotation rotation = this.calculateRotation(eyesPos, hitVec);
                Intrinsics.checkNotNullExpressionValue(neighbor, "neighbor");
                EnumFacing enumFacing = side.func_176734_d();
                Intrinsics.checkNotNullExpressionValue(enumFacing, "side.opposite");
                if (!this.isValidBlockRotation(neighbor, eyesPos, rotation, enumFacing) || placeRotation != null && !(RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation()))) continue;
                enumFacing = side.func_176734_d();
                Intrinsics.checkNotNullExpressionValue(enumFacing, "side.opposite");
                placeRotation = new PlaceRotation(new PlaceInfo(neighbor, enumFacing, hitVec), rotation);
            }
        }
        if (placeRotation == null) {
            return false;
        }
        rotation = placeRotation.getRotation();
        double diffAngle = RotationUtils.getRotationDifference(RotationUtils.serverRotation, rotation);
        if (diffAngle < 0.0) {
            diffAngle = -diffAngle;
        }
        if (diffAngle > 180.0) {
            diffAngle = 180.0;
        }
        Rotation rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, BlockIn.rotation, (float)(diffAngle / (double)360 * ((Number)maxRotationSpeedValue.get()).doubleValue() + (1.0 - diffAngle / (double)360) * ((Number)minRotationSpeedValue.get()).doubleValue()));
        Intrinsics.checkNotNullExpressionValue(rotation, "limitAngleChange(\n      \u2026).toFloat()\n            )");
        Rotation rotationSmooth = rotation;
        if (((Boolean)rotationValue.get()).booleanValue()) {
            if (silentRotationValue.get().booleanValue()) {
                RotationUtils.setTargetRotation(rotationSpeedValue.get() != false ? rotationSmooth : BlockIn.rotation, 1);
            } else {
                Rotation rotation2 = rotationSpeedValue.get() != false ? rotationSmooth : BlockIn.rotation;
                Intrinsics.checkNotNull(rotation2);
                rotation = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNullExpressionValue(rotation, "mc.thePlayer");
                rotation2.toPlayer((EntityPlayer)rotation);
            }
        }
        targetPlace = placeRotation.getPlaceInfo();
        return true;
    }

    private final Sequence<Vec3> generateOffsets() {
        return SequencesKt.sequence((Function2)new Function2<SequenceScope<? super Vec3>, Continuation<? super Unit>, Object>(null){
            double D$0;
            double D$1;
            double D$2;
            double D$3;
            int label;
            private /* synthetic */ Object L$0;

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Object invokeSuspend(@NotNull Object var1_1) {
                block9: {
                    block8: {
                        var11_2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        block0 : switch (this.label) {
                            case 0: {
                                ResultKt.throwOnFailure(var1_1);
                                $this$sequence = (SequenceScope)this.L$0;
                                x = step = 0.1;
lbl7:
                                // 2 sources

                                while (x < 0.9) {
                                    y = step;
lbl9:
                                    // 2 sources

                                    while (y < 0.9) {
                                        z = step;
lbl11:
                                        // 2 sources

                                        while (z < 0.9) {
                                            this.L$0 = $this$sequence;
                                            this.D$0 = step;
                                            this.D$1 = x;
                                            this.D$2 = y;
                                            this.D$3 = z;
                                            this.label = 1;
                                            v0 = $this$sequence.yield(new Vec3(x, y, z), this);
                                            if (v0 == var11_2) {
                                                return var11_2;
                                            }
                                            ** GOTO lbl33
                                        }
                                        break block0;
                                    }
                                    break block8;
                                }
                                break block9;
                            }
                            case 1: {
                                z = this.D$3;
                                y = this.D$2;
                                x = this.D$1;
                                step = this.D$0;
                                $this$sequence = (SequenceScope)this.L$0;
                                ResultKt.throwOnFailure($result);
                                v0 = $result;
lbl33:
                                // 2 sources

                                z += step;
                                ** GOTO lbl11
                            }
                        }
                        y += step;
                        ** GOTO lbl9
                    }
                    x += step;
                    ** GOTO lbl7
                }
                return Unit.INSTANCE;
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }

            @NotNull
            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> $completion) {
                Function2<SequenceScope<? super Vec3>, Continuation<? super Unit>, Object> function2 = new /* invalid duplicate definition of identical inner class */;
                function2.L$0 = value;
                return (Continuation)((Object)function2);
            }

            @Nullable
            public final Object invoke(@NotNull SequenceScope<? super Vec3> p1, @Nullable Continuation<? super Unit> p2) {
                return (this.create(p1, p2)).invokeSuspend(Unit.INSTANCE);
            }
        });
    }

    private final Rotation calculateRotation(Vec3 eyesPos, Vec3 hitVec) {
        double diffX = hitVec.field_72450_a - eyesPos.field_72450_a;
        double diffY = hitVec.field_72448_b - eyesPos.field_72448_b;
        double diffZ = hitVec.field_72449_c - eyesPos.field_72449_c;
        double diffXZ = MathHelper.func_76133_a((double)(diffX * diffX + diffZ * diffZ));
        return new Rotation(MathHelper.func_76142_g((float)((float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - (double)90))), MathHelper.func_76142_g((float)((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))))));
    }

    private final boolean isValidBlockRotation(BlockPos neighbor, Vec3 eyesPos, Rotation rotation, EnumFacing facing) {
        Vec3 rotationVector = RotationUtils.getVectorForRotation(rotation);
        Vec3 vector = eyesPos.func_72441_c(rotationVector.field_72450_a * (double)4, rotationVector.field_72448_b * (double)4, rotationVector.field_72449_c * (double)4);
        MovingObjectPosition obj = MinecraftInstance.mc.field_71441_e.func_147447_a(eyesPos, vector, false, false, true);
        return obj.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && Intrinsics.areEqual(obj.func_178782_a(), neighbor) && obj.field_178784_b == facing;
    }

    public static final /* synthetic */ BoolValue access$getRotationValue$p() {
        return rotationValue;
    }

    public static final /* synthetic */ BoolValue access$getAutoPosition$p() {
        return autoPosition;
    }

    public static final /* synthetic */ IntegerValue access$getMinPlaceDelay$p() {
        return minPlaceDelay;
    }

    public static final /* synthetic */ BoolValue access$getBlockPlaceDelay$p() {
        return blockPlaceDelay;
    }

    public static final /* synthetic */ IntegerValue access$getMaxPlaceDelay$p() {
        return maxPlaceDelay;
    }

    public static final /* synthetic */ IntegerValue access$getMinRotationSpeedValue$p() {
        return minRotationSpeedValue;
    }

    public static final /* synthetic */ Value access$getRotationSpeedValue$p() {
        return rotationSpeedValue;
    }

    public static final /* synthetic */ IntegerValue access$getMaxRotationSpeedValue$p() {
        return maxRotationSpeedValue;
    }

    static {
        placeTimer = new TimerMS();
    }
}

