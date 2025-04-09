/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.InvManager;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.injection.access.IItemStack;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorPiece;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ModuleInfo(name="InvManager", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010@\u001a\u00020\u0016H\u0002J\u0015\u0010A\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010B0\u001dH\u0002\u00a2\u0006\u0002\u0010CJ!\u0010D\u001a\u0004\u0018\u00010\u00062\u0006\u0010E\u001a\u00020\u00062\b\u0010F\u001a\u0004\u0018\u00010GH\u0002\u00a2\u0006\u0002\u0010HJ\u0016\u0010I\u001a\u00020\u00162\u0006\u0010J\u001a\u00020G2\u0006\u0010K\u001a\u00020\u0006J(\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020G0L2\b\b\u0002\u0010M\u001a\u00020\u00062\b\b\u0002\u0010N\u001a\u00020\u0006H\u0002J\u0018\u0010O\u001a\u00020\u00162\u0006\u0010P\u001a\u00020\u00062\u0006\u0010Q\u001a\u00020\u0016H\u0002J\b\u0010R\u001a\u00020SH\u0016J\u0010\u0010T\u001a\u00020S2\u0006\u0010U\u001a\u00020VH\u0007J\u0010\u0010W\u001a\u00020\u001e2\u0006\u0010E\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016@BX\u0082\u000e\u00a2\u0006\b\n\u0000\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u001fR\u000e\u0010 \u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00160#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020$0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010,\u001a\u00020\u00168BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010.R\u000e\u0010/\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020\u00060#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00104\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00105\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00108\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00109\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001e0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006X"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/InvManager;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "currentSlot", "", "getCurrentSlot", "()I", "setCurrentSlot", "(I)V", "delay", "", "goal", "Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$EnumNBTPriorityType;", "getGoal", "()Lnet/ccbluex/liquidbounce/utils/item/ItemUtils$EnumNBTPriorityType;", "hotbarValue", "ignoreVehiclesValue", "instantValue", "invOpenValue", "value", "", "invOpened", "setInvOpened", "(Z)V", "itemDelayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "items", "", "", "[Ljava/lang/String;", "maxDelayValue", "minDelayValue", "nbtArmorPriority", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "nbtGoalValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "nbtItemNotGarbage", "nbtWeaponPriority", "noCombatValue", "noMoveValue", "onlyPositivePotionValue", "openInventory", "getOpenInventory", "()Z", "randomSlotValue", "simDelayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "simulateDelayValue", "simulateInventory", "sortSlot1Value", "sortSlot2Value", "sortSlot3Value", "sortSlot4Value", "sortSlot5Value", "sortSlot6Value", "sortSlot7Value", "sortSlot8Value", "sortSlot9Value", "sortValue", "swingValue", "throwValue", "checkOpen", "findBestArmor", "Lnet/ccbluex/liquidbounce/utils/item/ArmorPiece;", "()[Lnet/ccbluex/liquidbounce/utils/item/ArmorPiece;", "findBetterItem", "targetSlot", "slotStack", "Lnet/minecraft/item/ItemStack;", "(ILnet/minecraft/item/ItemStack;)Ljava/lang/Integer;", "isUseful", "itemStack", "slot", "", "start", "end", "move", "item", "isArmorSlot", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "type", "CrossSine"})
public final class InvManager
extends Module {
    @NotNull
    public static final InvManager INSTANCE;
    @NotNull
    private static final BoolValue instantValue;
    @NotNull
    private static final IntegerValue maxDelayValue;
    @NotNull
    private static final IntegerValue minDelayValue;
    @NotNull
    private static final BoolValue invOpenValue;
    @NotNull
    private static final BoolValue simulateInventory;
    @NotNull
    private static final Value<Integer> simulateDelayValue;
    @NotNull
    private static final BoolValue noMoveValue;
    @NotNull
    private static final BoolValue hotbarValue;
    @NotNull
    private static final BoolValue randomSlotValue;
    @NotNull
    private static final BoolValue sortValue;
    @NotNull
    private static final BoolValue throwValue;
    @NotNull
    private static final BoolValue armorValue;
    @NotNull
    private static final BoolValue noCombatValue;
    @NotNull
    private static final IntegerValue itemDelayValue;
    @NotNull
    private static final BoolValue swingValue;
    @NotNull
    private static final ListValue nbtGoalValue;
    @NotNull
    private static final Value<Boolean> nbtItemNotGarbage;
    @NotNull
    private static final Value<Float> nbtArmorPriority;
    @NotNull
    private static final Value<Float> nbtWeaponPriority;
    @NotNull
    private static final BoolValue ignoreVehiclesValue;
    @NotNull
    private static final BoolValue onlyPositivePotionValue;
    @NotNull
    private static final String[] items;
    @NotNull
    private static final Value<String> sortSlot1Value;
    @NotNull
    private static final Value<String> sortSlot2Value;
    @NotNull
    private static final Value<String> sortSlot3Value;
    @NotNull
    private static final Value<String> sortSlot4Value;
    @NotNull
    private static final Value<String> sortSlot5Value;
    @NotNull
    private static final Value<String> sortSlot6Value;
    @NotNull
    private static final Value<String> sortSlot7Value;
    @NotNull
    private static final Value<String> sortSlot8Value;
    @NotNull
    private static final Value<String> sortSlot9Value;
    private static boolean invOpened;
    private static long delay;
    @NotNull
    private static final MSTimer simDelayTimer;
    private static int currentSlot;

    private InvManager() {
    }

    private final boolean getOpenInventory() {
        return !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && (Boolean)simulateInventory.get() != false;
    }

    private final void setInvOpened(boolean value) {
        if (value != invOpened) {
            if (value) {
                InventoryUtils.INSTANCE.openPacket();
            } else {
                InventoryUtils.INSTANCE.closePacket();
            }
        }
        invOpened = value;
    }

    private final ItemUtils.EnumNBTPriorityType getGoal() {
        return ItemUtils.EnumNBTPriorityType.valueOf((String)nbtGoalValue.get());
    }

    public final int getCurrentSlot() {
        return currentSlot;
    }

    public final void setCurrentSlot(int n) {
        currentSlot = n;
    }

    @Override
    public void onDisable() {
        this.setInvOpened(false);
    }

    private final boolean checkOpen() {
        if (!invOpened && this.getOpenInventory()) {
            this.setInvOpened(true);
            simDelayTimer.reset();
            return true;
        }
        return !simDelayTimer.hasTimePassed(((Number)simulateDelayValue.get()).intValue());
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Map<Integer, ItemStack> armorPiece;
        Intrinsics.checkNotNullParameter(event, "event");
        if ((Boolean)noMoveValue.get() != false && MovementUtils.INSTANCE.isMoving() || MinecraftInstance.mc.field_71439_g.field_71070_bA != null && MinecraftInstance.mc.field_71439_g.field_71070_bA.field_75152_c != 0 || CrossSine.INSTANCE.getCombatManager().getInCombat() && ((Boolean)noCombatValue.get()).booleanValue()) {
            if (InventoryUtils.CLICK_TIMER.hasTimePassed(((Number)simulateDelayValue.get()).intValue())) {
                this.setInvOpened(false);
            }
            return;
        }
        if (!InventoryUtils.CLICK_TIMER.hasTimePassed(delay) && !((Boolean)instantValue.get()).booleanValue() || !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && ((Boolean)invOpenValue.get()).booleanValue()) {
            return;
        }
        if (((Boolean)armorValue.get()).booleanValue()) {
            ArmorPiece[] bestArmor = this.findBestArmor();
            int n = 0;
            while (n < 4) {
                int armorSlot;
                ItemStack oldArmor;
                int i;
                if (bestArmor[i = n++] == null || (oldArmor = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70440_f(armorSlot = 3 - i)) != null && oldArmor.func_77973_b() instanceof ItemArmor && ItemUtils.INSTANCE.compareArmor(new ArmorPiece(oldArmor, -1), (ArmorPiece)((Object)armorPiece), ((Number)nbtArmorPriority.get()).floatValue(), this.getGoal()) >= 0) continue;
                if (oldArmor != null && this.move(8 - armorSlot, true)) {
                    return;
                }
                if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70440_f(armorSlot) != null || !this.move(((ArmorPiece)((Object)armorPiece)).getSlot(), false)) continue;
                currentSlot = ((ArmorPiece)((Object)armorPiece)).getSlot();
                return;
            }
        }
        if (((Boolean)sortValue.get()).booleanValue()) {
            int bestArmor = 0;
            while (bestArmor < 9) {
                int bestItem;
                int index2 = bestArmor++;
                Integer n = this.findBetterItem(index2, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(index2));
                if (n == null || (bestItem = n.intValue()) == index2) continue;
                if (this.checkOpen()) {
                    return;
                }
                MinecraftInstance.mc.field_71442_b.func_78753_a(0, bestItem < 9 ? bestItem + 36 : bestItem, index2, 2, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                currentSlot = bestItem < 9 ? bestItem + 36 : bestItem;
                delay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
                return;
            }
        }
        if (((Boolean)throwValue.get()).booleanValue()) {
            Integer garbageItem;
            void $this$filterTo$iv$iv;
            Map<Integer, ItemStack> $this$filter$iv = this.items(5, (Boolean)hotbarValue.get() != false ? 45 : 36);
            boolean $i$f$filter = false;
            armorPiece = $this$filter$iv;
            Map destination$iv$iv = new LinkedHashMap();
            boolean $i$f$filterTo = false;
            Iterator iterator2 = $this$filterTo$iv$iv.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry element$iv$iv;
                Map.Entry it = element$iv$iv = iterator2.next();
                boolean bl = false;
                if (!(!INSTANCE.isUseful((ItemStack)it.getValue(), ((Number)it.getKey()).intValue()))) continue;
                destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
            }
            Set garbageItems = destination$iv$iv.keySet();
            Integer n = !((Collection)garbageItems).isEmpty() ? Integer.valueOf((Boolean)randomSlotValue.get() != false ? ((Number)CollectionsKt.toList(garbageItems).get(RandomUtils.nextInt(0, garbageItems.size()))).intValue() : ((Number)CollectionsKt.first(garbageItems)).intValue()) : (garbageItem = (Integer)null);
            if (garbageItem != null) {
                if (this.checkOpen()) {
                    return;
                }
                if (((Boolean)swingValue.get()).booleanValue()) {
                    MinecraftInstance.mc.field_71439_g.func_71038_i();
                }
                MinecraftInstance.mc.field_71442_b.func_78753_a(MinecraftInstance.mc.field_71439_g.field_71070_bA.field_75152_c, garbageItem.intValue(), 1, 4, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                currentSlot = garbageItem;
                delay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
                return;
            }
        }
        if (InventoryUtils.CLICK_TIMER.hasTimePassed(((Number)simulateDelayValue.get()).intValue())) {
            this.setInvOpened(false);
        }
    }

    public final boolean isUseful(@NotNull ItemStack itemStack, int slot) {
        boolean bl;
        Intrinsics.checkNotNullParameter(itemStack, "itemStack");
        try {
            boolean bl2;
            block38: {
                Map.Entry element$iv;
                int currDamage;
                Item item = itemStack.func_77973_b();
                if (item instanceof ItemSword || item instanceof ItemTool) {
                    double d;
                    int n;
                    if (slot >= 36) {
                        Integer n2 = this.findBetterItem(slot - 36, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(slot - 36));
                        n = slot - 36;
                        if (n2 != null && n2 == n) {
                            return true;
                        }
                    }
                    n = 0;
                    while (n < 9) {
                        int i;
                        if (!(StringsKt.equals(this.type(i = n++), "sword", true) && item instanceof ItemSword || StringsKt.equals(this.type(i), "pickaxe", true) && item instanceof ItemPickaxe) && (!StringsKt.equals(this.type(i), "axe", true) || !(item instanceof ItemAxe)) || this.findBetterItem(i, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i)) != null) continue;
                        return true;
                    }
                    Collection collection = itemStack.func_111283_C().get((Object)"generic.attackDamage");
                    Intrinsics.checkNotNullExpressionValue(collection, "itemStack.attributeModif\u2026s[\"generic.attackDamage\"]");
                    AttributeModifier attributeModifier = (AttributeModifier)CollectionsKt.firstOrNull(collection);
                    double damage = (attributeModifier == null ? 0.0 : (d = attributeModifier.func_111164_d())) + ItemUtils.INSTANCE.getWeaponEnchantFactor(itemStack, ((Number)nbtWeaponPriority.get()).floatValue(), this.getGoal());
                    Map<Integer, ItemStack> $this$none$iv = this.items(0, 45);
                    boolean $i$f$none2 = false;
                    if ($this$none$iv.isEmpty()) {
                        bl2 = true;
                    } else {
                        Iterator<Map.Entry<Integer, ItemStack>> iterator2 = $this$none$iv.entrySet().iterator();
                        while (iterator2.hasNext()) {
                            boolean bl3;
                            Map.Entry<Integer, ItemStack> element$iv2;
                            Map.Entry<Integer, ItemStack> $dstr$_u24__u24$stack = element$iv2 = iterator2.next();
                            boolean bl4 = false;
                            ItemStack stack2 = $dstr$_u24__u24$stack.getValue();
                            if (!Intrinsics.areEqual(stack2, itemStack) && Intrinsics.areEqual(stack2.getClass(), itemStack.getClass())) {
                                double d2;
                                Collection collection2 = stack2.func_111283_C().get((Object)"generic.attackDamage");
                                Intrinsics.checkNotNullExpressionValue(collection2, "stack.attributeModifiers[\"generic.attackDamage\"]");
                                AttributeModifier attributeModifier2 = (AttributeModifier)CollectionsKt.firstOrNull(collection2);
                                double dmg = (attributeModifier2 == null ? 0.0 : (d2 = attributeModifier2.func_111164_d())) + ItemUtils.INSTANCE.getWeaponEnchantFactor(stack2, ((Number)nbtWeaponPriority.get()).floatValue(), INSTANCE.getGoal());
                                bl3 = damage == dmg ? (currDamage = item.getDamage(itemStack)) >= stack2.func_77973_b().getDamage(stack2) : damage < dmg;
                            } else {
                                bl3 = false;
                            }
                            if (!bl3) continue;
                            bl2 = false;
                            break block38;
                        }
                        bl2 = true;
                    }
                } else if (item instanceof ItemBow) {
                    Enchantment i = Enchantment.field_77345_t;
                    Intrinsics.checkNotNullExpressionValue(i, "power");
                    int currPower = ItemUtils.INSTANCE.getEnchantment(itemStack, i);
                    Map $this$none$iv = InvManager.items$default(this, 0, 0, 3, null);
                    boolean $i$f$none = false;
                    if ($this$none$iv.isEmpty()) {
                        bl2 = true;
                    } else {
                        Iterator $i$f$none2 = $this$none$iv.entrySet().iterator();
                        while ($i$f$none2.hasNext()) {
                            boolean bl5;
                            Map.Entry $dstr$_u24__u24$stack = element$iv = $i$f$none2.next();
                            boolean bl6 = false;
                            ItemStack stack = (ItemStack)$dstr$_u24__u24$stack.getValue();
                            if (!Intrinsics.areEqual(itemStack, stack) && stack.func_77973_b() instanceof ItemBow) {
                                int currDamage2;
                                Enchantment stack2 = Enchantment.field_77345_t;
                                Intrinsics.checkNotNullExpressionValue(stack2, "power");
                                int power = ItemUtils.INSTANCE.getEnchantment(stack, stack2);
                                bl5 = currPower == power ? (currDamage2 = item.getDamage(itemStack)) >= stack.func_77973_b().getDamage(stack) : currPower < power;
                            } else {
                                bl5 = false;
                            }
                            if (!bl5) continue;
                            bl2 = false;
                            break block38;
                        }
                        bl2 = true;
                    }
                } else if (item instanceof ItemArmor) {
                    ArmorPiece currArmor = new ArmorPiece(itemStack, slot);
                    Map $this$none$iv = InvManager.items$default(this, 0, 0, 3, null);
                    boolean $i$f$none = false;
                    if ($this$none$iv.isEmpty()) {
                        bl2 = true;
                    } else {
                        Iterator $i$f$none2 = $this$none$iv.entrySet().iterator();
                        while ($i$f$none2.hasNext()) {
                            boolean bl7;
                            Map.Entry $dstr$slot$stack = element$iv = $i$f$none2.next();
                            boolean bl8 = false;
                            int slot2 = ((Number)$dstr$slot$stack.getKey()).intValue();
                            ItemStack stack = (ItemStack)$dstr$slot$stack.getValue();
                            if (!Intrinsics.areEqual(stack, itemStack) && stack.func_77973_b() instanceof ItemArmor) {
                                ArmorPiece armor = new ArmorPiece(stack, slot2);
                                if (armor.getArmorType() != currArmor.getArmorType()) {
                                    bl7 = false;
                                } else {
                                    currDamage = item.getDamage(itemStack);
                                    int result = ItemUtils.INSTANCE.compareArmor(currArmor, armor, ((Number)nbtArmorPriority.get()).floatValue(), INSTANCE.getGoal());
                                    bl7 = result == 0 ? currDamage >= stack.func_77973_b().getDamage(stack) : result < 0;
                                }
                            } else {
                                bl7 = false;
                            }
                            if (!bl7) continue;
                            bl2 = false;
                            break block38;
                        }
                        bl2 = true;
                    }
                } else if (item instanceof ItemFlintAndSteel) {
                    int currDamage3 = item.getDamage(itemStack);
                    Map $this$none$iv = InvManager.items$default(this, 0, 0, 3, null);
                    boolean $i$f$none = false;
                    if ($this$none$iv.isEmpty()) {
                        bl2 = true;
                    } else {
                        Iterator $i$f$none2 = $this$none$iv.entrySet().iterator();
                        while ($i$f$none2.hasNext()) {
                            Map.Entry $dstr$_u24__u24$stack = element$iv = $i$f$none2.next();
                            boolean bl9 = false;
                            ItemStack stack = (ItemStack)$dstr$_u24__u24$stack.getValue();
                            if (!(!Intrinsics.areEqual(itemStack, stack) && stack.func_77973_b() instanceof ItemFlintAndSteel && currDamage3 >= stack.func_77973_b().getDamage(stack))) continue;
                            bl2 = false;
                            break block38;
                        }
                        bl2 = true;
                    }
                } else if (Intrinsics.areEqual(itemStack.func_77977_a(), "item.compass")) {
                    Map<Integer, ItemStack> $this$none$iv = this.items(0, 45);
                    boolean $i$f$none = false;
                    if ($this$none$iv.isEmpty()) {
                        bl2 = true;
                    } else {
                        Iterator<Map.Entry<Integer, ItemStack>> iterator3 = $this$none$iv.entrySet().iterator();
                        while (iterator3.hasNext()) {
                            Map.Entry<Integer, ItemStack> element$iv3;
                            Map.Entry<Integer, ItemStack> $dstr$_u24__u24$stack = element$iv3 = iterator3.next();
                            boolean bl10 = false;
                            ItemStack stack = $dstr$_u24__u24$stack.getValue();
                            if (!(!Intrinsics.areEqual(itemStack, stack) && Intrinsics.areEqual(stack.func_77977_a(), "item.compass"))) continue;
                            bl2 = false;
                            break block38;
                        }
                        bl2 = true;
                    }
                } else {
                    bl2 = nbtItemNotGarbage.get() != false && ItemUtils.INSTANCE.hasNBTGoal(itemStack, this.getGoal()) || item instanceof ItemFood || Intrinsics.areEqual(itemStack.func_77977_a(), "item.arrow") || item instanceof ItemBlock && !InventoryUtils.INSTANCE.isBlockListBlock((ItemBlock)item) || item instanceof ItemBed || item instanceof ItemPotion && ((Boolean)onlyPositivePotionValue.get() == false || InventoryUtils.INSTANCE.isPositivePotion((ItemPotion)item, itemStack)) || item instanceof ItemEnderPearl || item instanceof ItemBucket || (Boolean)ignoreVehiclesValue.get() != false && (item instanceof ItemBoat || item instanceof ItemMinecart);
                }
            }
            bl = bl2;
        }
        catch (Exception ex) {
            ClientUtils.INSTANCE.logError("(InvManager) Failed to check item: " + itemStack.func_77977_a() + '.', ex);
            bl = true;
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    private final ArmorPiece[] findBestArmor() {
        Map<Integer, List<ArmorPiece>> armorPieces = IntStream.range(0, 36).filter(InvManager::findBestArmor$lambda-7).mapToObj(InvManager::findBestArmor$lambda-8).collect(Collectors.groupingBy(InvManager::findBestArmor$lambda-9));
        ArmorPiece[] bestArmor = new ArmorPiece[4];
        Intrinsics.checkNotNullExpressionValue(armorPieces, "armorPieces");
        for (Map.Entry<Integer, List<ArmorPiece>> entry : armorPieces.entrySet()) {
            void it;
            List<ArmorPiece> list;
            Integer key = entry.getKey();
            List<ArmorPiece> value = entry.getValue();
            Integer n = key;
            Intrinsics.checkNotNull(n);
            List<ArmorPiece> list2 = list = value;
            int n2 = n;
            ArmorPiece[] armorPieceArray = bestArmor;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            CollectionsKt.sortWith(it, InvManager::findBestArmor$lambda-11$lambda-10);
            Unit unit = Unit.INSTANCE;
            List<ArmorPiece> list3 = list;
            Intrinsics.checkNotNullExpressionValue(list3, "value.also { it.sortWith\u2026Priority.get(), goal) } }");
            armorPieceArray[n2] = CollectionsKt.lastOrNull(list3);
        }
        return bestArmor;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final Integer findBetterItem(int targetSlot, ItemStack slotStack) {
        String type = this.type(targetSlot);
        String string = type.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "sword": 
            case "pickaxe": 
            case "axe": {
                Class<Object> clazz;
                Class<ItemSword> clazz2;
                if (StringsKt.equals(type, "Sword", true)) {
                    clazz2 = ItemSword.class;
                } else if (StringsKt.equals(type, "Pickaxe", true)) {
                    clazz2 = ItemPickaxe.class;
                } else {
                    if (!StringsKt.equals(type, "Axe", true)) return null;
                    clazz2 = ItemAxe.class;
                }
                Class<ItemSword> clazz3 = clazz2;
                int bestWeapon = 0;
                ItemStack itemStack = slotStack;
                if (itemStack == null) {
                    clazz = null;
                } else {
                    Item item = itemStack.func_77973_b();
                    clazz = item == null ? null : item.getClass();
                }
                bestWeapon = Intrinsics.areEqual(clazz, clazz3) ? targetSlot : -1;
                ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
                Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
                Object[] objectArray = itemStackArray;
                boolean bl = false;
                int index$iv = 0;
                for (Object item$iv : objectArray) {
                    double d;
                    ItemStack bestStack;
                    double d2;
                    void itemStack2;
                    int n = index$iv;
                    index$iv = n + 1;
                    ItemStack itemStack3 = (ItemStack)item$iv;
                    int index2 = n;
                    boolean bl2 = false;
                    if (itemStack2 == null || !Intrinsics.areEqual(itemStack2.func_77973_b().getClass(), clazz3) || StringsKt.equals(INSTANCE.type(index2), type, true)) continue;
                    if (bestWeapon == -1) {
                        bestWeapon = index2;
                        continue;
                    }
                    Collection collection = itemStack2.func_111283_C().get((Object)"generic.attackDamage");
                    Intrinsics.checkNotNullExpressionValue(collection, "itemStack.attributeModif\u2026s[\"generic.attackDamage\"]");
                    AttributeModifier attributeModifier = (AttributeModifier)CollectionsKt.firstOrNull(collection);
                    double currDamage = (attributeModifier == null ? 0.0 : (d2 = attributeModifier.func_111164_d())) + ItemUtils.INSTANCE.getWeaponEnchantFactor((ItemStack)itemStack2, ((Number)nbtWeaponPriority.get()).floatValue(), INSTANCE.getGoal());
                    if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(bestWeapon) == null) continue;
                    Collection collection2 = bestStack.func_111283_C().get((Object)"generic.attackDamage");
                    Intrinsics.checkNotNullExpressionValue(collection2, "bestStack.attributeModif\u2026s[\"generic.attackDamage\"]");
                    AttributeModifier attributeModifier2 = (AttributeModifier)CollectionsKt.firstOrNull(collection2);
                    double d3 = attributeModifier2 == null ? 0.0 : (d = attributeModifier2.func_111164_d());
                    double bestDamage = d3 + ItemUtils.INSTANCE.getWeaponEnchantFactor(bestStack, ((Number)nbtWeaponPriority.get()).floatValue(), INSTANCE.getGoal());
                    if (!(bestDamage < currDamage)) continue;
                    bestWeapon = index2;
                }
                if (bestWeapon == -1) {
                    if (bestWeapon != targetSlot) return null;
                }
                Integer n = bestWeapon;
                return n;
            }
            case "bow": {
                int n;
                int n2;
                boolean bl = false;
                ItemStack itemStack = slotStack;
                int n3 = (itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemBow ? targetSlot : -1;
                int bestPower = 0;
                if (n3 != -1) {
                    ItemStack itemStack4 = slotStack;
                    Intrinsics.checkNotNull(itemStack4);
                    Enchantment enchantment = Enchantment.field_77345_t;
                    Intrinsics.checkNotNullExpressionValue(enchantment, "power");
                    n2 = ItemUtils.INSTANCE.getEnchantment(itemStack4, enchantment);
                } else {
                    n2 = 0;
                }
                bestPower = n2;
                ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
                Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
                Object[] objectArray = itemStackArray;
                boolean bl3 = false;
                int index$iv = 0;
                for (Object item$iv2 : objectArray) {
                    int n4 = index$iv;
                    index$iv = n4 + 1;
                    ItemStack itemStack2 = (ItemStack)item$iv2;
                    int index3 = n4;
                    boolean bl4 = false;
                    ItemStack itemStack5 = itemStack2;
                    if (!((itemStack5 == null ? null : itemStack5.func_77973_b()) instanceof ItemBow) || StringsKt.equals(INSTANCE.type(index3), type, true)) continue;
                    if (n == -1) {
                        n = index3;
                        continue;
                    }
                    Intrinsics.checkNotNullExpressionValue(itemStack2, "itemStack");
                    Enchantment enchantment = Enchantment.field_77345_t;
                    Intrinsics.checkNotNullExpressionValue(enchantment, "power");
                    int power = ItemUtils.INSTANCE.getEnchantment(itemStack2, enchantment);
                    enchantment = Enchantment.field_77345_t;
                    Intrinsics.checkNotNullExpressionValue(enchantment, "power");
                    if (ItemUtils.INSTANCE.getEnchantment(itemStack2, enchantment) <= bestPower) continue;
                    n = index3;
                    bestPower = power;
                }
                if (n == -1) return null;
                Integer n5 = n;
                return n5;
            }
            case "food": {
                void index4;
                void v13;
                Item item;
                ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
                Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
                Object[] objectArray = itemStackArray;
                boolean $i$f$forEachIndexed = false;
                boolean bl = false;
                Object[] objectArray2 = objectArray;
                int n = 0;
                int n6 = objectArray2.length;
                do {
                    void stack;
                    void var7_36;
                    if (n >= n6) return null;
                    Object item$iv = objectArray2[n];
                    ++n;
                    void var12_81 = var7_36;
                    var7_36 = var12_81 + true;
                    ItemStack item$iv2 = (ItemStack)item$iv;
                    index4 = var12_81;
                    boolean bl5 = false;
                    v13 = stack;
                } while (!((item = v13 == null ? null : v13.func_77973_b()) instanceof ItemFood) || item instanceof ItemAppleGold || StringsKt.equals(INSTANCE.type((int)index4), "Food", true));
                if (slotStack != null) {
                    if (slotStack.func_77973_b() instanceof ItemFood) return null;
                }
                boolean bl6 = true;
                boolean replaceCurr = bl6;
                if (!replaceCurr) return null;
                Integer n7 = (int)index4;
                return n7;
            }
            case "block": {
                void index5;
                ItemStack itemStack;
                Item item;
                ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
                Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
                Object[] objectArray = itemStackArray;
                boolean $i$f$forEachIndexed = false;
                boolean bl = false;
                Object[] objectArray3 = objectArray;
                int n = 0;
                int n8 = objectArray3.length;
                do {
                    void var7_38;
                    if (n >= n8) return null;
                    Object item$iv = objectArray3[n];
                    ++n;
                    void var12_82 = var7_38;
                    var7_38 = var12_82 + true;
                    ItemStack stack = (ItemStack)item$iv;
                    index5 = var12_82;
                    boolean bl7 = false;
                    itemStack = stack;
                } while (!((item = itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemBlock) || InventoryUtils.INSTANCE.getBLOCK_BLACKLIST().contains(((ItemBlock)item).field_150939_a) || StringsKt.equals(INSTANCE.type((int)index5), "Block", true));
                if (slotStack != null) {
                    if (slotStack.func_77973_b() instanceof ItemBlock) return null;
                }
                boolean bl8 = true;
                boolean replaceCurr = bl8;
                if (!replaceCurr) return null;
                Integer n9 = (int)index5;
                return n9;
            }
            case "water": {
                void index6;
                ItemStack itemStack;
                Item item;
                ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
                Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
                Object[] objectArray = itemStackArray;
                boolean $i$f$forEachIndexed = false;
                boolean bl = false;
                Object[] objectArray4 = objectArray;
                int n = 0;
                int n10 = objectArray4.length;
                do {
                    void var7_40;
                    if (n >= n10) return null;
                    Object item$iv = objectArray4[n];
                    ++n;
                    void var12_83 = var7_40;
                    var7_40 = var12_83 + true;
                    ItemStack stack = (ItemStack)item$iv;
                    index6 = var12_83;
                    boolean bl9 = false;
                    itemStack = stack;
                } while (!((item = itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemBucket) || !Intrinsics.areEqual(((ItemBucket)item).field_77876_a, Blocks.field_150358_i) || StringsKt.equals(INSTANCE.type((int)index6), "Water", true));
                if (slotStack != null && slotStack.func_77973_b() instanceof ItemBucket) {
                    Item item2 = slotStack.func_77973_b();
                    if (item2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBucket");
                    }
                    if (Intrinsics.areEqual(((ItemBucket)item2).field_77876_a, Blocks.field_150358_i)) return null;
                }
                boolean bl10 = true;
                boolean replaceCurr = bl10;
                if (!replaceCurr) return null;
                Integer n11 = (int)index6;
                return n11;
            }
            case "gapple": {
                void index7;
                ItemStack itemStack;
                Item item;
                ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
                Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
                Object[] objectArray = itemStackArray;
                boolean $i$f$forEachIndexed = false;
                boolean bl = false;
                Object[] objectArray5 = objectArray;
                int n = 0;
                int n12 = objectArray5.length;
                do {
                    void var7_42;
                    if (n >= n12) return null;
                    Object item$iv = objectArray5[n];
                    ++n;
                    void var12_84 = var7_42;
                    var7_42 = var12_84 + true;
                    ItemStack stack = (ItemStack)item$iv;
                    index7 = var12_84;
                    boolean bl11 = false;
                    itemStack = stack;
                } while (!((item = itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemAppleGold) || StringsKt.equals(INSTANCE.type((int)index7), "Gapple", true));
                if (slotStack != null) {
                    if (slotStack.func_77973_b() instanceof ItemAppleGold) return null;
                }
                boolean bl12 = true;
                boolean replaceCurr = bl12;
                if (!replaceCurr) return null;
                Integer n13 = (int)index7;
                return n13;
            }
            case "pearl": {
                void index8;
                ItemStack itemStack;
                Item item;
                ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
                Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
                Object[] objectArray = itemStackArray;
                boolean $i$f$forEachIndexed = false;
                boolean bl = false;
                Object[] objectArray6 = objectArray;
                int n = 0;
                int n14 = objectArray6.length;
                do {
                    void var7_44;
                    if (n >= n14) return null;
                    Object item$iv = objectArray6[n];
                    ++n;
                    void var12_85 = var7_44;
                    var7_44 = var12_85 + true;
                    ItemStack stack = (ItemStack)item$iv;
                    index8 = var12_85;
                    boolean bl13 = false;
                    itemStack = stack;
                } while (!((item = itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemEnderPearl) || StringsKt.equals(INSTANCE.type((int)index8), "Pearl", true));
                if (slotStack != null) {
                    if (slotStack.func_77973_b() instanceof ItemEnderPearl) return null;
                }
                boolean bl14 = true;
                boolean replaceCurr = bl14;
                if (!replaceCurr) return null;
                Integer n15 = (int)index8;
                return n15;
            }
            case "potion": {
                void index9;
                ItemStack stack;
                ItemStack itemStack;
                Item item;
                ItemStack[] itemStackArray = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70462_a;
                Intrinsics.checkNotNullExpressionValue(itemStackArray, "mc.thePlayer.inventory.mainInventory");
                Object[] objectArray = itemStackArray;
                boolean $i$f$forEachIndexed = false;
                boolean bl = false;
                Object[] objectArray7 = objectArray;
                int n = 0;
                int n16 = objectArray7.length;
                do {
                    void var7_46;
                    if (n >= n16) return null;
                    Object item$iv = objectArray7[n];
                    ++n;
                    void var12_86 = var7_46;
                    var7_46 = var12_86 + true;
                    stack = (ItemStack)item$iv;
                    index9 = var12_86;
                    boolean bl15 = false;
                    itemStack = stack;
                } while (!((item = itemStack == null ? null : itemStack.func_77973_b()) instanceof ItemPotion) || !ItemPotion.func_77831_g((int)stack.func_77952_i()) || StringsKt.equals(INSTANCE.type((int)index9), "Potion", true));
                if (slotStack != null && slotStack.func_77973_b() instanceof ItemPotion) {
                    if (ItemPotion.func_77831_g((int)slotStack.func_77952_i())) return null;
                }
                boolean bl16 = true;
                boolean replaceCurr = bl16;
                if (!replaceCurr) return null;
                Integer n17 = (int)index9;
                return n17;
            }
        }
        return null;
    }

    private final Map<Integer, ItemStack> items(int start, int end) {
        Map items = new LinkedHashMap();
        int n = end - 1;
        if (start <= n) {
            int i;
            do {
                ItemStack itemStack;
                if (MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n--).func_75211_c() == null || itemStack.func_77973_b() == null) continue;
                if ((36 <= i ? i < 45 : false) && StringsKt.equals(this.type(i), "Ignore", true) || System.currentTimeMillis() - ((IItemStack)itemStack).getItemDelay() < (long)((Number)itemDelayValue.get()).intValue()) continue;
                Map map = items;
                Integer n2 = i;
                map.put(n2, itemStack);
            } while (i != start);
        }
        return items;
    }

    static /* synthetic */ Map items$default(InvManager invManager, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) != 0) {
            n2 = 45;
        }
        return invManager.items(n, n2);
    }

    private final boolean move(int item, boolean isArmorSlot) {
        if (item == -1) {
            return false;
        }
        if (!isArmorSlot && item < 9 && ((Boolean)hotbarValue.get()).booleanValue() && !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory)) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(item));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(item).func_75211_c()));
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
            delay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
            return true;
        }
        if (this.checkOpen()) {
            return true;
        }
        if (((Boolean)throwValue.get()).booleanValue() && isArmorSlot) {
            MinecraftInstance.mc.field_71442_b.func_78753_a(MinecraftInstance.mc.field_71439_g.field_71069_bz.field_75152_c, item, 0, 4, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
            currentSlot = item;
        } else {
            MinecraftInstance.mc.field_71442_b.func_78753_a(MinecraftInstance.mc.field_71439_g.field_71069_bz.field_75152_c, isArmorSlot ? item : (item < 9 ? item + 36 : item), 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
            currentSlot = isArmorSlot ? item : (item < 9 ? item + 36 : item);
        }
        delay = TimeUtils.INSTANCE.randomDelay(((Number)minDelayValue.get()).intValue(), ((Number)maxDelayValue.get()).intValue());
        return true;
    }

    private final String type(int targetSlot) {
        String string;
        switch (targetSlot) {
            case 0: {
                string = sortSlot1Value.get();
                break;
            }
            case 1: {
                string = sortSlot2Value.get();
                break;
            }
            case 2: {
                string = sortSlot3Value.get();
                break;
            }
            case 3: {
                string = sortSlot4Value.get();
                break;
            }
            case 4: {
                string = sortSlot5Value.get();
                break;
            }
            case 5: {
                string = sortSlot6Value.get();
                break;
            }
            case 6: {
                string = sortSlot7Value.get();
                break;
            }
            case 7: {
                string = sortSlot8Value.get();
                break;
            }
            case 8: {
                string = sortSlot9Value.get();
                break;
            }
            default: {
                string = "";
            }
        }
        return string;
    }

    private static final boolean findBestArmor$lambda-7(int i) {
        ItemStack itemStack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
        return itemStack != null && itemStack.func_77973_b() instanceof ItemArmor && (i < 9 || System.currentTimeMillis() - ((IItemStack)itemStack).getItemDelay() >= (long)((Number)itemDelayValue.get()).intValue());
    }

    private static final ArmorPiece findBestArmor$lambda-8(int i) {
        ItemStack itemStack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i);
        Intrinsics.checkNotNullExpressionValue(itemStack, "mc.thePlayer.inventory.getStackInSlot(i)");
        return new ArmorPiece(itemStack, i);
    }

    private static final Integer findBestArmor$lambda-9(ArmorPiece obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        return obj.getArmorType();
    }

    private static final int findBestArmor$lambda-11$lambda-10(ArmorPiece armorPiece, ArmorPiece armorPiece2) {
        Intrinsics.checkNotNullExpressionValue(armorPiece, "armorPiece");
        Intrinsics.checkNotNullExpressionValue(armorPiece2, "armorPiece2");
        return ItemUtils.INSTANCE.compareArmor(armorPiece, armorPiece2, ((Number)nbtArmorPriority.get()).floatValue(), INSTANCE.getGoal());
    }

    public static final /* synthetic */ IntegerValue access$getMinDelayValue$p() {
        return minDelayValue;
    }

    public static final /* synthetic */ BoolValue access$getInstantValue$p() {
        return instantValue;
    }

    public static final /* synthetic */ IntegerValue access$getMaxDelayValue$p() {
        return maxDelayValue;
    }

    public static final /* synthetic */ BoolValue access$getSimulateInventory$p() {
        return simulateInventory;
    }

    public static final /* synthetic */ ListValue access$getNbtGoalValue$p() {
        return nbtGoalValue;
    }

    public static final /* synthetic */ BoolValue access$getSortValue$p() {
        return sortValue;
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_4;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        INSTANCE = new InvManager();
        instantValue = new BoolValue("Instant", false);
        maxDelayValue = (IntegerValue)new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int minCPS = ((Number)InvManager.access$getMinDelayValue$p().get()).intValue();
                if (minCPS > newValue) {
                    this.set(minCPS);
                }
            }
        }.displayable(maxDelayValue.2.INSTANCE);
        minDelayValue = (IntegerValue)new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int maxDelay = ((Number)InvManager.access$getMaxDelayValue$p().get()).intValue();
                if (maxDelay < newValue) {
                    this.set(maxDelay);
                }
            }
        }.displayable(minDelayValue.2.INSTANCE);
        invOpenValue = new BoolValue("InvOpen", false);
        simulateInventory = new BoolValue("InvSpoof", true);
        simulateDelayValue = new IntegerValue("InvSpoof", 0, 0, 1000).displayable(simulateDelayValue.1.INSTANCE);
        noMoveValue = new BoolValue("NoMove", false);
        hotbarValue = new BoolValue("Hotbar", true);
        randomSlotValue = new BoolValue("RandomSlot", false);
        sortValue = new BoolValue("Sort", true);
        throwValue = new BoolValue("Drop", true);
        armorValue = new BoolValue("Armor", true);
        noCombatValue = new BoolValue("NoCombat", false);
        itemDelayValue = new IntegerValue("ItemDelay", 0, 0, 5000);
        swingValue = new BoolValue("Swing", true);
        Object[] objectArray = ItemUtils.EnumNBTPriorityType.values();
        String string = "NBTGoal";
        boolean $i$f$map = false;
        void var2_3 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        for (void item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            void var9_10 = item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.toString());
        }
        Collection $this$toTypedArray$iv = (List)var3_4;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray = thisCollection$iv.toArray(new String[0]);
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        String string2 = "NONE";
        String[] stringArray2 = stringArray;
        String string3 = string;
        nbtGoalValue = new ListValue(string3, stringArray2, string2);
        nbtItemNotGarbage = new BoolValue("NBTItemNotGarbage", true).displayable(nbtItemNotGarbage.1.INSTANCE);
        nbtArmorPriority = new FloatValue("NBTArmorPriority", 0.0f, 0.0f, 5.0f).displayable(nbtArmorPriority.1.INSTANCE);
        nbtWeaponPriority = new FloatValue("NBTWeaponPriority", 0.0f, 0.0f, 5.0f).displayable(nbtWeaponPriority.1.INSTANCE);
        ignoreVehiclesValue = new BoolValue("IgnoreVehicles", false);
        onlyPositivePotionValue = new BoolValue("OnlyPositivePotion", false);
        objectArray = new String[]{"None", "Ignore", "Sword", "Bow", "Pickaxe", "Axe", "Food", "Block", "Water", "Gapple", "Pearl", "Potion"};
        items = objectArray;
        sortSlot1Value = new ListValue("SortSlot-1", items, "Sword").displayable(sortSlot1Value.1.INSTANCE);
        sortSlot2Value = new ListValue("SortSlot-2", items, "Gapple").displayable(sortSlot2Value.1.INSTANCE);
        sortSlot3Value = new ListValue("SortSlot-3", items, "Potion").displayable(sortSlot3Value.1.INSTANCE);
        sortSlot4Value = new ListValue("SortSlot-4", items, "Pickaxe").displayable(sortSlot4Value.1.INSTANCE);
        sortSlot5Value = new ListValue("SortSlot-5", items, "Axe").displayable(sortSlot5Value.1.INSTANCE);
        sortSlot6Value = new ListValue("SortSlot-6", items, "None").displayable(sortSlot6Value.1.INSTANCE);
        sortSlot7Value = new ListValue("SortSlot-7", items, "Block").displayable(sortSlot7Value.1.INSTANCE);
        sortSlot8Value = new ListValue("SortSlot-8", items, "Pearl").displayable(sortSlot8Value.1.INSTANCE);
        sortSlot9Value = new ListValue("SortSlot-9", items, "Food").displayable(sortSlot9Value.1.INSTANCE);
        simDelayTimer = new MSTimer();
        currentSlot = -1;
    }
}

