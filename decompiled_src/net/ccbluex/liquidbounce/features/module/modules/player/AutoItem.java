/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoItem;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="AutoItem", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoItem;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackEnemy", "", "autoTool", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "autoWeapon", "bestSlot", "", "mining", "onlySwordValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "onlyTools", "prevItem", "prevItemWeapon", "sneakValue", "spoof", "spoofTick", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AutoItem
extends Module {
    @NotNull
    public static final AutoItem INSTANCE = new AutoItem();
    @NotNull
    private static final BoolValue autoTool = new BoolValue("AutoTool", true);
    @NotNull
    private static final Value<Boolean> sneakValue = new BoolValue("Sneak", false).displayable(sneakValue.1.INSTANCE);
    @NotNull
    private static final Value<Boolean> onlyTools = new BoolValue("Only-Tool", true).displayable(onlyTools.1.INSTANCE);
    @NotNull
    private static final BoolValue autoWeapon = new BoolValue("AutoWeapon", false);
    @NotNull
    private static final Value<Boolean> onlySwordValue = new BoolValue("OnlySword", false).displayable(onlySwordValue.1.INSTANCE);
    @NotNull
    private static final BoolValue spoof = new BoolValue("Spoof-Item", true);
    private static int prevItem;
    private static boolean mining;
    private static int bestSlot;
    private static boolean attackEnemy;
    private static int prevItemWeapon;
    private static int spoofTick;

    private AutoItem() {
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)autoTool.get()).booleanValue()) {
            if (!MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d() && MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d() && MinecraftInstance.mc.field_71476_x != null && MinecraftInstance.mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && (!sneakValue.get().booleanValue() || MinecraftInstance.mc.field_71439_g.func_70093_af())) {
                int bestSpeed = 0;
                if (!mining) {
                    prevItem = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
                }
                Block block = MinecraftInstance.mc.field_71441_e.func_180495_p(MinecraftInstance.mc.field_71476_x.func_178782_a()).func_177230_c();
                int n = 0;
                while (n < 9) {
                    ItemStack item;
                    int i;
                    if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i = n++) == null) continue;
                    float speed = item.func_150997_a(block);
                    if (speed > (float)bestSpeed) {
                        bestSpeed = (int)speed;
                        bestSlot = i;
                    }
                    if (bestSlot == -1) continue;
                    Item item2 = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(bestSlot).func_77973_b();
                    if (onlyTools.get().booleanValue() && !(item2 instanceof ItemShears) && !(item2 instanceof ItemTool)) continue;
                    SlotUtils.INSTANCE.setSlot(bestSlot, (Boolean)spoof.get(), this.getName());
                }
                mining = true;
            } else if (mining) {
                SlotUtils.INSTANCE.stopSet();
                mining = false;
            } else {
                prevItem = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
            }
        }
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        attackEnemy = true;
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)autoWeapon.get()).booleanValue() && event.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)event.getPacket()).func_149565_c() == C02PacketUseEntity.Action.ATTACK && attackEnemy) {
            Object v0;
            void $this$filterTo$iv$iv;
            Iterable $this$mapTo$iv$iv;
            attackEnemy = false;
            Iterable $this$map$iv = new IntRange(0, 8);
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            Iterator iterator2 = $this$mapTo$iv$iv.iterator();
            while (iterator2.hasNext()) {
                void it;
                int item$iv$iv;
                int n = item$iv$iv = ((IntIterator)iterator2).nextInt();
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(new Pair<Integer, ItemStack>((int)it, MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a((int)it)));
            }
            Iterable $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$filter = false;
            $this$mapTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                Pair it2 = (Pair)element$iv$iv;
                boolean bl = false;
                if (!(it2.getSecond() != null && (((ItemStack)it2.getSecond()).func_77973_b() instanceof ItemSword || ((ItemStack)it2.getSecond()).func_77973_b() instanceof ItemTool && onlySwordValue.get() == false))) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$maxByOrNull$iv = (List)destination$iv$iv;
            boolean $i$f$maxByOrNull = false;
            Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
            if (!iterator$iv.hasNext()) {
                v0 = null;
            } else {
                Object maxElem$iv = iterator$iv.next();
                if (!iterator$iv.hasNext()) {
                    v0 = maxElem$iv;
                } else {
                    Pair it = (Pair)maxElem$iv;
                    boolean bl = false;
                    Collection element$iv$iv = ((ItemStack)it.getSecond()).func_111283_C().get((Object)"generic.attackDamage");
                    Intrinsics.checkNotNullExpressionValue(element$iv$iv, "it.second.attributeModif\u2026s[\"generic.attackDamage\"]");
                    Object it2 = (AttributeModifier)CollectionsKt.first(element$iv$iv);
                    double d = it2 == null ? 0.0 : (element$iv$iv = it2.func_111164_d());
                    it2 = it.getSecond();
                    Intrinsics.checkNotNullExpressionValue(it2, "it.second");
                    ItemStack itemStack = (ItemStack)it2;
                    it2 = Enchantment.field_180314_l;
                    Intrinsics.checkNotNullExpressionValue(it2, "sharpness");
                    double maxValue$iv = d + 1.25 * (double)ItemUtils.INSTANCE.getEnchantment(itemStack, (Enchantment)it2);
                    do {
                        double d2;
                        Object e$iv = iterator$iv.next();
                        Pair it3 = (Pair)e$iv;
                        $i$a$-maxByOrNull-AutoItem$onPacket$3 = false;
                        Collection collection = ((ItemStack)it3.getSecond()).func_111283_C().get((Object)"generic.attackDamage");
                        Intrinsics.checkNotNullExpressionValue(collection, "it.second.attributeModif\u2026s[\"generic.attackDamage\"]");
                        Object object = (AttributeModifier)CollectionsKt.first(collection);
                        double d3 = object == null ? 0.0 : (d2 = object.func_111164_d());
                        object = it3.getSecond();
                        Intrinsics.checkNotNullExpressionValue(object, "it.second");
                        ItemStack itemStack2 = (ItemStack)object;
                        object = Enchantment.field_180314_l;
                        Intrinsics.checkNotNullExpressionValue(object, "sharpness");
                        double v$iv = d3 + 1.25 * (double)ItemUtils.INSTANCE.getEnchantment(itemStack2, (Enchantment)object);
                        if (Double.compare(maxValue$iv, v$iv) >= 0) continue;
                        maxElem$iv = e$iv;
                        maxValue$iv = v$iv;
                    } while (iterator$iv.hasNext());
                    v0 = maxElem$iv;
                }
            }
            Pair pair = v0;
            if (pair == null) {
                return;
            }
            int slot = ((Number)pair.component1()).intValue();
            if (slot == MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c) {
                return;
            }
            if (!SlotUtils.INSTANCE.getChanged()) {
                prevItemWeapon = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
            }
            spoofTick = 15;
            SlotUtils.INSTANCE.setSlot(slot, (Boolean)spoof.get(), this.getName());
            MinecraftInstance.mc.field_71442_b.func_78765_e();
            MinecraftInstance.mc.func_147114_u().func_147297_a(event.getPacket());
            event.cancelEvent();
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)autoWeapon.get()).booleanValue() && spoofTick > 0) {
            if (spoofTick == 1) {
                SlotUtils.INSTANCE.stopSet();
            }
            int n = spoofTick;
            spoofTick = n + -1;
        }
    }

    public static final /* synthetic */ BoolValue access$getAutoTool$p() {
        return autoTool;
    }

    public static final /* synthetic */ BoolValue access$getAutoWeapon$p() {
        return autoWeapon;
    }
}

