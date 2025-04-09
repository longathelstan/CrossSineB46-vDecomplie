/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.InvManager;
import net.ccbluex.liquidbounce.features.module.modules.world.Stealer;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="Stealer", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010B\u001a\u00020CH\u0002J\u0010\u0010D\u001a\u00020\u000f2\u0006\u0010E\u001a\u00020FH\u0002J\u0018\u0010G\u001a\u00020C2\u0006\u0010H\u001a\u00020F2\u0006\u0010I\u001a\u00020JH\u0002J\u0010\u0010K\u001a\u00020C2\u0006\u0010L\u001a\u00020MH\u0007J\u0010\u0010N\u001a\u00020C2\u0006\u0010L\u001a\u00020OH\u0003J\u0010\u0010P\u001a\u00020C2\u0006\u0010L\u001a\u00020QH\u0007J\u0010\u0010R\u001a\u00020C2\u0006\u0010L\u001a\u00020SH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010#\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u000e\u0010.\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u000f0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0014\u00102\u001a\u00020\u000f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b3\u00104R\u000e\u00105\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000209X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u000209X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010=\u001a\b\u0012\u0004\u0012\u00020\u000f0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u00101R\u0011\u0010?\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010%R\u000e\u0010A\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006T"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/Stealer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Aura", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "AuraclickedBlocks", "", "Lnet/minecraft/util/BlockPos;", "getAuraclickedBlocks", "()Ljava/util/List;", "AuracurrentBlock", "AuradelayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "AuradiscoverDelayEnabledValue", "", "AuradiscoverDelayValue", "AuranoCombatingValue", "AuranotOpenedValue", "AuraonlyOnGroundValue", "AurarangeValue", "", "AurarotationsValue", "AuraswingValue", "", "AurathroughWallsValue", "AuraunderClick", "alwayTake", "autoCloseMaxDelayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "autoCloseMinDelayValue", "autoCloseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoCloseValue", "chestTimer", "chestTitleValue", "getChestTitleValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "chestValue", "closeOnFullValue", "contentReceived", "currentSlot", "getCurrentSlot", "()I", "setCurrentSlot", "(I)V", "delayTimer", "freelookValue", "getFreelookValue", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "fullInventory", "getFullInventory", "()Z", "instantValue", "maxDelayValue", "minDelayValue", "nextCloseDelay", "", "nextDelay", "noCompassValue", "onlyItemsValue", "silentTitleValue", "getSilentTitleValue", "silentValue", "getSilentValue", "takeRandomizedValue", "click", "", "isEmpty", "chest", "Lnet/minecraft/client/gui/inventory/GuiChest;", "move", "screen", "slot", "Lnet/minecraft/inventory/Slot;", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class Stealer
extends Module {
    @NotNull
    public static final Stealer INSTANCE = new Stealer();
    @NotNull
    private static final BoolValue instantValue = new BoolValue("Instant", false);
    @NotNull
    private static final IntegerValue maxDelayValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number)Stealer.access$getMinDelayValue$p().get()).intValue();
            if (i > newValue) {
                this.set(i);
            }
            Stealer.access$setNextDelay$p(TimeUtils.INSTANCE.randomDelay(((Number)Stealer.access$getMinDelayValue$p().get()).intValue(), ((Number)this.get()).intValue()));
        }
    }.displayable(maxDelayValue.2.INSTANCE);
    @NotNull
    private static final IntegerValue minDelayValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number)Stealer.access$getMaxDelayValue$p().get()).intValue();
            if (i < newValue) {
                this.set(i);
            }
            Stealer.access$setNextDelay$p(TimeUtils.INSTANCE.randomDelay(((Number)this.get()).intValue(), ((Number)Stealer.access$getMaxDelayValue$p().get()).intValue()));
        }
    }.displayable(minDelayValue.2.INSTANCE);
    @NotNull
    private static final IntegerValue chestValue = new IntegerValue("ChestOpenDelay", 300, 0, 1000);
    @NotNull
    private static final BoolValue takeRandomizedValue = new BoolValue("TakeRandomized", false);
    @NotNull
    private static final Value<Boolean> alwayTake = new BoolValue("AlwayTakeItem", false).displayable(alwayTake.1.INSTANCE);
    @NotNull
    private static final BoolValue onlyItemsValue = new BoolValue("OnlyItems", false);
    @NotNull
    private static final BoolValue noCompassValue = new BoolValue("NoCompass", false);
    @NotNull
    private static final BoolValue autoCloseValue = new BoolValue("AutoClose", true);
    @NotNull
    private static final Value<Boolean> freelookValue = new BoolValue("FreeLook", false).displayable(freelookValue.1.INSTANCE);
    @NotNull
    private static final BoolValue silentValue = new BoolValue("Silent", true);
    @NotNull
    private static final Value<Boolean> silentTitleValue = new BoolValue("Title", true).displayable(silentTitleValue.1.INSTANCE);
    @NotNull
    private static final IntegerValue autoCloseMaxDelayValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number)Stealer.access$getAutoCloseMinDelayValue$p().get()).intValue();
            if (i > newValue) {
                this.set(i);
            }
            Stealer.access$setNextCloseDelay$p(TimeUtils.INSTANCE.randomDelay(((Number)Stealer.access$getAutoCloseMinDelayValue$p().get()).intValue(), ((Number)this.get()).intValue()));
        }
    }.displayable(autoCloseMaxDelayValue.2.INSTANCE);
    @NotNull
    private static final IntegerValue autoCloseMinDelayValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number)Stealer.access$getAutoCloseMaxDelayValue$p().get()).intValue();
            if (i < newValue) {
                this.set(i);
            }
            Stealer.access$setNextCloseDelay$p(TimeUtils.INSTANCE.randomDelay(((Number)this.get()).intValue(), ((Number)Stealer.access$getAutoCloseMaxDelayValue$p().get()).intValue()));
        }
    }.displayable(autoCloseMinDelayValue.2.INSTANCE);
    @NotNull
    private static final Value<Boolean> closeOnFullValue = new BoolValue("CloseOnFull", true).displayable(closeOnFullValue.1.INSTANCE);
    @NotNull
    private static final BoolValue chestTitleValue = new BoolValue("ChestTitle", false);
    @NotNull
    private static final BoolValue Aura = new BoolValue("Aura", false);
    @NotNull
    private static final Value<Float> AurarangeValue = new FloatValue("Range", 5.0f, 1.0f, 6.0f).displayable(AurarangeValue.1.INSTANCE);
    @NotNull
    private static final Value<Integer> AuradelayValue = new IntegerValue("Delay", 100, 50, 500).displayable(AuradelayValue.1.INSTANCE);
    @NotNull
    private static final Value<Boolean> AurathroughWallsValue = new BoolValue("ThroughWalls", true).displayable(AurathroughWallsValue.1.INSTANCE);
    @NotNull
    private static final Value<String> AuraswingValue;
    @NotNull
    private static final Value<Boolean> AurarotationsValue;
    @NotNull
    private static final Value<Boolean> AuradiscoverDelayEnabledValue;
    @NotNull
    private static final Value<Integer> AuradiscoverDelayValue;
    @NotNull
    private static final Value<Boolean> AuraonlyOnGroundValue;
    @NotNull
    private static final Value<Boolean> AuranotOpenedValue;
    @NotNull
    private static final Value<Boolean> AuranoCombatingValue;
    @Nullable
    private static BlockPos AuracurrentBlock;
    private static boolean AuraunderClick;
    @NotNull
    private static final List<BlockPos> AuraclickedBlocks;
    @NotNull
    private static final MSTimer delayTimer;
    @NotNull
    private static final MSTimer chestTimer;
    private static long nextDelay;
    @NotNull
    private static final MSTimer autoCloseTimer;
    private static long nextCloseDelay;
    private static int currentSlot;
    private static int contentReceived;

    private Stealer() {
    }

    @NotNull
    public final Value<Boolean> getFreelookValue() {
        return freelookValue;
    }

    @NotNull
    public final BoolValue getSilentValue() {
        return silentValue;
    }

    @NotNull
    public final Value<Boolean> getSilentTitleValue() {
        return silentTitleValue;
    }

    @NotNull
    public final BoolValue getChestTitleValue() {
        return chestTitleValue;
    }

    @NotNull
    public final List<BlockPos> getAuraclickedBlocks() {
        return AuraclickedBlocks;
    }

    public final int getCurrentSlot() {
        return currentSlot;
    }

    public final void setCurrentSlot(int n) {
        currentSlot = n;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        GuiScreen screen;
        block21: {
            block22: {
                Intrinsics.checkNotNullParameter(event, "event");
                if (!chestTimer.hasTimePassed(((Number)chestValue.get()).intValue())) {
                    return;
                }
                screen = MinecraftInstance.mc.field_71462_r;
                if (!(screen instanceof GuiChest) || !delayTimer.hasTimePassed(nextDelay)) {
                    autoCloseTimer.reset();
                    return;
                }
                if (((Boolean)noCompassValue.get()).booleanValue()) {
                    String string;
                    ItemStack itemStack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g();
                    if (itemStack == null) {
                        string = null;
                    } else {
                        Item item = itemStack.func_77973_b();
                        string = item == null ? null : item.func_77658_a();
                    }
                    if (Intrinsics.areEqual(string, "item.compass")) {
                        return;
                    }
                }
                if (!((Boolean)chestTitleValue.get()).booleanValue()) break block21;
                if (((GuiChest)screen).field_147015_w == null) break block22;
                String string = ((GuiChest)screen).field_147015_w.func_70005_c_();
                Intrinsics.checkNotNullExpressionValue(string, "screen.lowerChestInventory.name");
                CharSequence charSequence = string;
                string = new ItemStack((Item)Item.field_150901_e.func_82594_a((Object)new ResourceLocation("minecraft:chest"))).func_82833_r();
                Intrinsics.checkNotNullExpressionValue(string, "ItemStack(Item.itemRegis\u2026aft:chest\"))).displayName");
                if (StringsKt.contains$default(charSequence, string, false, 2, null)) break block21;
            }
            return;
        }
        InvManager invManager = CrossSine.INSTANCE.getModuleManager().get(InvManager.class);
        Intrinsics.checkNotNull(invManager);
        InvManager invManager2 = invManager;
        if (!(this.isEmpty((GuiChest)screen) || closeOnFullValue.get().booleanValue() && this.getFullInventory())) {
            autoCloseTimer.reset();
            if (((Boolean)takeRandomizedValue.get()).booleanValue()) {
                List items;
                do {
                    items = new ArrayList();
                    int n = 0;
                    int n2 = ((GuiChest)screen).field_147018_x * 9;
                    while (n < n2) {
                        int slotIndex;
                        Slot slot;
                        if ((slot = (Slot)((GuiChest)screen).field_147002_h.field_75151_b.get(slotIndex = n++)).func_75211_c() == null || ((Boolean)onlyItemsValue.get()).booleanValue() && slot.func_75211_c().func_77973_b() instanceof ItemBlock) continue;
                        if (invManager2.getState() && !alwayTake.get().booleanValue()) {
                            ItemStack itemStack = slot.func_75211_c();
                            Intrinsics.checkNotNullExpressionValue(itemStack, "slot.stack");
                            if (!invManager2.isUseful(itemStack, -1)) continue;
                        }
                        Intrinsics.checkNotNullExpressionValue(slot, "slot");
                        items.add(slot);
                    }
                    int randomSlot = Random.Default.nextInt(items.size());
                    Slot slot = (Slot)items.get(randomSlot);
                    this.move((GuiChest)screen, slot);
                    currentSlot = slot.getSlotIndex();
                } while (((Boolean)instantValue.get()).booleanValue() || delayTimer.hasTimePassed(nextDelay) && !((Collection)items).isEmpty());
                return;
            }
            int n = 0;
            int n3 = ((GuiChest)screen).field_147018_x * 9;
            while (n < n3) {
                int slotIndex = n++;
                Slot slot = (Slot)((GuiChest)screen).field_147002_h.field_75151_b.get(slotIndex);
                if (!((Boolean)instantValue.get()).booleanValue()) {
                    if (!delayTimer.hasTimePassed(nextDelay) || slot.func_75211_c() == null || ((Boolean)onlyItemsValue.get()).booleanValue() && slot.func_75211_c().func_77973_b() instanceof ItemBlock) continue;
                    if (invManager2.getState()) {
                        ItemStack itemStack = slot.func_75211_c();
                        Intrinsics.checkNotNullExpressionValue(itemStack, "slot.stack");
                        if (!invManager2.isUseful(itemStack, -1)) continue;
                    }
                }
                GuiChest guiChest = (GuiChest)screen;
                Intrinsics.checkNotNullExpressionValue(slot, "slot");
                this.move(guiChest, slot);
                currentSlot = slotIndex;
            }
        } else if (((Boolean)instantValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.func_71053_j();
        } else if (((Boolean)autoCloseValue.get()).booleanValue() && ((GuiChest)screen).field_147002_h.field_75152_c == contentReceived && autoCloseTimer.hasTimePassed(nextCloseDelay)) {
            MinecraftInstance.mc.field_71439_g.func_71053_j();
            nextCloseDelay = TimeUtils.INSTANCE.randomDelay(((Number)autoCloseMinDelayValue.get()).intValue(), ((Number)autoCloseMaxDelayValue.get()).intValue());
        }
    }

    @EventTarget
    private final void onPacket(PacketEvent event) {
        Packet<?> packet;
        Packet<?> packet2 = event.getPacket();
        if (packet2 instanceof S30PacketWindowItems) {
            contentReceived = ((S30PacketWindowItems)packet2).func_148911_c();
        }
        if (packet2 instanceof S2DPacketOpenWindow) {
            chestTimer.reset();
        }
        if (((Boolean)Aura.get()).booleanValue() && AuranotOpenedValue.get().booleanValue() && event.getPacket() instanceof S24PacketBlockAction && ((S24PacketBlockAction)(packet = event.getPacket())).func_148868_c() instanceof BlockChest && ((S24PacketBlockAction)packet).func_148864_h() == 1 && !AuraclickedBlocks.contains(((S24PacketBlockAction)packet).func_179825_a())) {
            BlockPos blockPos = ((S24PacketBlockAction)packet).func_179825_a();
            Intrinsics.checkNotNullExpressionValue(blockPos, "packet.blockPosition");
            AuraclickedBlocks.add(blockPos);
        }
    }

    private final void move(GuiChest screen, Slot slot) {
        screen.func_146984_a(slot, slot.field_75222_d, 0, 1);
        delayTimer.reset();
        nextDelay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
    }

    private final boolean isEmpty(GuiChest chest) {
        InvManager invManager = CrossSine.INSTANCE.getModuleManager().get(InvManager.class);
        Intrinsics.checkNotNull(invManager);
        InvManager invManager2 = invManager;
        int n = 0;
        int n2 = chest.field_147018_x * 9;
        while (n < n2) {
            int i;
            Slot slot;
            if ((slot = (Slot)chest.field_147002_h.field_75151_b.get(i = n++)).func_75211_c() == null || ((Boolean)onlyItemsValue.get()).booleanValue() && slot.func_75211_c().func_77973_b() instanceof ItemBlock) continue;
            if (invManager2.getState()) {
                ItemStack itemStack = slot.func_75211_c();
                Intrinsics.checkNotNullExpressionValue(itemStack, "slot.stack");
                if (!invManager2.isUseful(itemStack, -1)) continue;
            }
            return false;
        }
        return true;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)Aura.get()).booleanValue()) {
            if (AuraonlyOnGroundValue.get().booleanValue() && !MinecraftInstance.mc.field_71439_g.field_70122_E || AuranoCombatingValue.get().booleanValue() && CrossSine.INSTANCE.getCombatManager().getInCombat()) {
                return;
            }
            if (event.getEventState() == EventState.PRE) {
                Object v1;
                Map.Entry it;
                Map.Entry element$iv$iv;
                Map $this$filterTo$iv$iv;
                Map $this$filter$iv;
                if (MinecraftInstance.mc.field_71462_r instanceof GuiContainer) {
                    return;
                }
                float radius = ((Number)AurarangeValue.get()).floatValue() + 1.0f;
                Vec3 eyesPos = new Vec3(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)MinecraftInstance.mc.field_71439_g.func_70047_e(), MinecraftInstance.mc.field_71439_g.field_70161_v);
                Map<BlockPos, Block> map = BlockUtils.searchBlocks((int)radius);
                boolean $i$f$filter = false;
                Iterator iterator2 = $this$filter$iv;
                Map destination$iv$iv = new LinkedHashMap();
                boolean $i$f$filterTo = false;
                Iterator iterator3 = $this$filterTo$iv$iv.entrySet().iterator();
                while (iterator3.hasNext()) {
                    it = element$iv$iv = iterator3.next();
                    boolean bl = false;
                    boolean bl2 = it.getValue() instanceof BlockChest && !INSTANCE.getAuraclickedBlocks().contains(it.getKey()) && BlockUtils.getCenterDistance((BlockPos)it.getKey()) < (double)((Number)AurarangeValue.get()).floatValue();
                    if (!bl2) continue;
                    destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
                }
                $this$filter$iv = destination$iv$iv;
                $i$f$filter = false;
                $this$filterTo$iv$iv = $this$filter$iv;
                destination$iv$iv = new LinkedHashMap();
                $i$f$filterTo = false;
                iterator3 = $this$filterTo$iv$iv.entrySet().iterator();
                while (iterator3.hasNext()) {
                    BlockPos blockPos;
                    MovingObjectPosition movingObjectPosition;
                    it = element$iv$iv = iterator3.next();
                    boolean bl = false;
                    if (!(AurathroughWallsValue.get() != false ? true : (movingObjectPosition = MinecraftInstance.mc.field_71441_e.func_147447_a(eyesPos, BlockExtensionKt.getVec(blockPos = (BlockPos)it.getKey()), false, true, false)) != null && Intrinsics.areEqual(movingObjectPosition.func_178782_a(), blockPos))) continue;
                    destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
                }
                iterator2 = ((Iterable)destination$iv$iv.entrySet()).iterator();
                if (!iterator2.hasNext()) {
                    v1 = null;
                } else {
                    Object t = iterator2.next();
                    if (!iterator2.hasNext()) {
                        v1 = t;
                    } else {
                        Map.Entry it2 = (Map.Entry)t;
                        boolean bl = false;
                        double d = BlockUtils.getCenterDistance((BlockPos)it2.getKey());
                        do {
                            Object t2 = iterator2.next();
                            Map.Entry it3 = (Map.Entry)t2;
                            $i$a$-minByOrNull-Stealer$onMotion$3 = false;
                            double d2 = BlockUtils.getCenterDistance((BlockPos)it3.getKey());
                            if (Double.compare(d, d2) <= 0) continue;
                            t = t2;
                            d = d2;
                        } while (iterator2.hasNext());
                        v1 = t;
                    }
                }
                Map.Entry entry = v1;
                BlockPos blockPos = AuracurrentBlock = entry == null ? null : (BlockPos)entry.getKey();
                if (AurarotationsValue.get().booleanValue()) {
                    BlockPos blockPos2 = AuracurrentBlock;
                    if (blockPos2 == null) {
                        return;
                    }
                    VecRotation vecRotation = RotationUtils.faceBlock(blockPos2);
                    if (vecRotation == null) {
                        return;
                    }
                    RotationUtils.setTargetRotation(vecRotation.getRotation(), 0);
                }
            } else if (AuracurrentBlock != null && InventoryUtils.INSTANCE.getINV_TIMER().hasTimePassed(((Number)AuradelayValue.get()).intValue()) && !AuraunderClick) {
                AuraunderClick = true;
                if (AuradiscoverDelayEnabledValue.get().booleanValue()) {
                    Timer timer = new Timer();
                    long l = ((Number)AuradiscoverDelayValue.get()).intValue();
                    TimerTask timerTask2 = new TimerTask(){

                        public void run() {
                            TimerTask $this$onMotion_u24lambda_u2d3 = this;
                            boolean bl = false;
                            Stealer.access$click(Stealer.INSTANCE);
                        }
                    };
                    timer.schedule(timerTask2, l);
                } else {
                    this.click();
                }
            }
        }
    }

    private final void click() {
        try {
            PlayerControllerMP playerControllerMP = MinecraftInstance.mc.field_71442_b;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
            ItemStack itemStack = MinecraftInstance.mc.field_71439_g.func_70694_bm();
            BlockPos blockPos = AuracurrentBlock;
            Intrinsics.checkNotNull(blockPos);
            if (playerControllerMP.func_178890_a(entityPlayerSP, worldClient, itemStack, AuracurrentBlock, EnumFacing.DOWN, BlockExtensionKt.getVec(blockPos))) {
                if (AuraswingValue.equals("packet")) {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
                } else if (AuraswingValue.equals("normal")) {
                    MinecraftInstance.mc.field_71439_g.func_71038_i();
                }
                BlockPos blockPos2 = AuracurrentBlock;
                Intrinsics.checkNotNull(blockPos2);
                AuraclickedBlocks.add(blockPos2);
                AuracurrentBlock = null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        AuraunderClick = false;
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)Aura.get()).booleanValue()) {
            AuraclickedBlocks.clear();
        }
    }

    private final boolean getFullInventory() {
        boolean bl;
        block1: {
            ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
            Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
            Object[] $this$none$iv = itemStackArray;
            boolean $i$f$none = false;
            for (Object element$iv : $this$none$iv) {
                ItemStack it = (ItemStack)element$iv;
                boolean bl2 = false;
                if (!(it == null)) continue;
                bl = false;
                break block1;
            }
            bl = true;
        }
        return bl;
    }

    public static final /* synthetic */ void access$click(Stealer $this) {
        $this.click();
    }

    public static final /* synthetic */ IntegerValue access$getMinDelayValue$p() {
        return minDelayValue;
    }

    public static final /* synthetic */ void access$setNextDelay$p(long l) {
        nextDelay = l;
    }

    public static final /* synthetic */ BoolValue access$getInstantValue$p() {
        return instantValue;
    }

    public static final /* synthetic */ IntegerValue access$getMaxDelayValue$p() {
        return maxDelayValue;
    }

    public static final /* synthetic */ IntegerValue access$getAutoCloseMinDelayValue$p() {
        return autoCloseMinDelayValue;
    }

    public static final /* synthetic */ void access$setNextCloseDelay$p(long l) {
        nextCloseDelay = l;
    }

    public static final /* synthetic */ BoolValue access$getAutoCloseValue$p() {
        return autoCloseValue;
    }

    public static final /* synthetic */ IntegerValue access$getAutoCloseMaxDelayValue$p() {
        return autoCloseMaxDelayValue;
    }

    public static final /* synthetic */ BoolValue access$getAura$p() {
        return Aura;
    }

    public static final /* synthetic */ Value access$getAuradiscoverDelayEnabledValue$p() {
        return AuradiscoverDelayEnabledValue;
    }

    static {
        String[] stringArray = new String[]{"Normal", "Packet", "None"};
        AuraswingValue = new ListValue("Swing", stringArray, "Normal").displayable(AuraswingValue.1.INSTANCE);
        AurarotationsValue = new BoolValue("Rotations", true).displayable(AurarotationsValue.1.INSTANCE);
        AuradiscoverDelayEnabledValue = new BoolValue("DiscoverDelay", false).displayable(AuradiscoverDelayEnabledValue.1.INSTANCE);
        AuradiscoverDelayValue = new IntegerValue("DiscoverDelayValue", 200, 50, 300).displayable(AuradiscoverDelayValue.1.INSTANCE);
        AuraonlyOnGroundValue = new BoolValue("OnlyOnGround", true).displayable(AuraonlyOnGroundValue.1.INSTANCE);
        AuranotOpenedValue = new BoolValue("NotOpened", false).displayable(AuranotOpenedValue.1.INSTANCE);
        AuranoCombatingValue = new BoolValue("NoCombating", true).displayable(AuranoCombatingValue.1.INSTANCE);
        AuraclickedBlocks = new ArrayList();
        delayTimer = new MSTimer();
        chestTimer = new MSTimer();
        nextDelay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
        autoCloseTimer = new MSTimer();
        nextCloseDelay = TimeUtils.INSTANCE.randomDelay(((Number)autoCloseMinDelayValue.get()).intValue(), ((Number)autoCloseMaxDelayValue.get()).intValue());
        currentSlot = -1;
    }
}

