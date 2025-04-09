/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005J\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000fJ\u001e\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u0016\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u0006\u0010\u001b\u001a\u00020\u0014J\u0006\u0010\u001c\u001a\u00020\u0014J\u0010\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u000e\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020 J\b\u0010$\u001a\u00020\u000fH\u0016J\u0006\u0010%\u001a\u00020\u000fJ\u000e\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020(J\u0016\u0010)\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020*2\u0006\u0010#\u001a\u00020 J\u000e\u0010+\u001a\u00020\u000f2\u0006\u0010,\u001a\u00020\u0014J\u0010\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020/H\u0007J\u0006\u00100\u001a\u00020\u0012R\u001f\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u00061"}, d2={"Lnet/ccbluex/liquidbounce/utils/InventoryUtils;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "BLOCK_BLACKLIST", "", "Lnet/minecraft/block/Block;", "kotlin.jvm.PlatformType", "getBLOCK_BLACKLIST", "()Ljava/util/List;", "CLICK_TIMER", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "INV_TIMER", "getINV_TIMER", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "canPlaceBlock", "", "block", "closePacket", "", "findAutoBlockBlock", "", "biggest", "findItem", "startSlot", "endSlot", "item", "Lnet/minecraft/item/Item;", "findSword", "getBestSwapSlot", "getDamageLevel", "", "itemStack", "Lnet/minecraft/item/ItemStack;", "getItemDurability", "", "stack", "handleEvents", "hasSpaceHotbar", "isBlockListBlock", "itemBlock", "Lnet/minecraft/item/ItemBlock;", "isPositivePotion", "Lnet/minecraft/item/ItemPotion;", "isPositivePotionEffect", "id", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "openPacket", "CrossSine"})
public final class InventoryUtils
implements Listenable {
    @NotNull
    public static final InventoryUtils INSTANCE = new InventoryUtils();
    @JvmField
    @NotNull
    public static final MSTimer CLICK_TIMER = new MSTimer();
    @NotNull
    private static final MSTimer INV_TIMER = new MSTimer();
    @NotNull
    private static final List<Block> BLOCK_BLACKLIST;

    private InventoryUtils() {
    }

    @NotNull
    public final MSTimer getINV_TIMER() {
        return INV_TIMER;
    }

    @NotNull
    public final List<Block> getBLOCK_BLACKLIST() {
        return BLOCK_BLACKLIST;
    }

    public final int findItem(int startSlot, int endSlot, @NotNull Item item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int n = startSlot;
        while (n < endSlot) {
            int i;
            ItemStack stack;
            if ((stack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n++).func_75211_c()) == null || stack.func_77973_b() != item) continue;
            return i;
        }
        return -1;
    }

    public final int findItem(@Nullable Item item) {
        int n = 0;
        while (n < 9) {
            int i;
            ItemStack itemStack;
            if (!((itemStack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i = n++)) == null ? item == null : itemStack.func_77973_b() == item)) continue;
            return i;
        }
        return -1;
    }

    public final int findSword() {
        int bestDurability = -1;
        float bestDamage = -1.0f;
        int bestSlot = -1;
        int n = 0;
        while (n < 9) {
            ItemStack itemStack;
            int i;
            if (MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i = n++) == null || !(itemStack.func_77973_b() instanceof ItemSword)) continue;
            Item item = itemStack.func_77973_b();
            if (item == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemSword");
            }
            ItemSword sword = (ItemSword)item;
            int sharpnessLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_180314_l.field_77352_x, (ItemStack)itemStack);
            float damage = sword.func_150931_i() + (float)sharpnessLevel * 1.25f;
            int durability = sword.func_77612_l();
            if (bestDamage < damage) {
                bestDamage = damage;
                bestDurability = durability;
                bestSlot = i;
            }
            if (!(damage == bestDamage) || durability <= bestDurability) continue;
            bestDurability = durability;
            bestSlot = i;
        }
        return bestSlot;
    }

    public final boolean hasSpaceHotbar() {
        int n = 36;
        while (n < 45) {
            int i;
            if (MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n++).func_75211_c() != null) continue;
            return true;
        }
        return false;
    }

    public final int getBestSwapSlot() {
        ItemStack stack;
        int i;
        int currentSlot = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
        int bestSlot = -1;
        double bestDamage = -1.0;
        int n = 0;
        while (n < 9) {
            double damage;
            if ((i = n++) == currentSlot || (damage = this.getDamageLevel(stack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i))) == 0.0 || !(damage > bestDamage)) continue;
            bestDamage = damage;
            bestSlot = i;
        }
        if (bestSlot == -1) {
            n = 0;
            while (n < 9) {
                String[] stringArray;
                if ((i = n++) == currentSlot || (stack = MinecraftInstance.mc.field_71439_g.field_71071_by.func_70301_a(i)) != null && !Arrays.stream(stringArray = new String[]{"compass", "snowball", "spawn", "skull"}).noneMatch(arg_0 -> InventoryUtils.getBestSwapSlot$lambda-0(stack, arg_0))) continue;
                bestSlot = i;
                break;
            }
        }
        return bestSlot;
    }

    public final double getDamageLevel(@Nullable ItemStack itemStack) {
        double baseDamage = 0.0;
        if (itemStack != null) {
            for (Map.Entry entry : itemStack.func_111283_C().entries()) {
                Intrinsics.checkNotNullExpressionValue(entry, "itemStack.attributeModifiers.entries()");
                String key = (String)entry.getKey();
                AttributeModifier value = (AttributeModifier)entry.getValue();
                if (!Intrinsics.areEqual(key, "generic.attackDamage")) continue;
                baseDamage = value.func_111164_d();
                break;
            }
        }
        int sharp_level = EnchantmentHelper.func_77506_a((int)Enchantment.field_180314_l.field_77352_x, (ItemStack)itemStack);
        int fire_level = EnchantmentHelper.func_77506_a((int)Enchantment.field_77334_n.field_77352_x, (ItemStack)itemStack);
        return baseDamage + (double)sharp_level * 1.25 + (double)(fire_level * 4 - 1);
    }

    public final int findAutoBlockBlock(boolean biggest) {
        if (biggest) {
            int a = -1;
            int aa = 0;
            int n = 36;
            while (n < 45) {
                int i;
                if (!MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n++).func_75216_d()) continue;
                Item aaa = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c().func_77973_b();
                ItemStack aaaa = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
                if (!(aaa instanceof ItemBlock) || aaaa.field_77994_a <= aa) continue;
                aa = aaaa.field_77994_a;
                a = i;
            }
            return a;
        }
        int n = 36;
        while (n < 45) {
            int i;
            ItemStack itemStack;
            if ((itemStack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n++).func_75211_c()) == null || !(itemStack.func_77973_b() instanceof ItemBlock)) continue;
            Item item = itemStack.func_77973_b();
            if (item == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
            }
            ItemBlock itemBlock = (ItemBlock)item;
            Block block = itemBlock.func_179223_d();
            Intrinsics.checkNotNullExpressionValue(block, "block");
            if (!this.canPlaceBlock(block)) continue;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            if ((EntityExtensionKt.getPing((EntityLivingBase)entityPlayerSP) <= 100 || itemStack.field_77994_a <= 2) && itemStack.field_77994_a == 0) continue;
            return i;
        }
        return -1;
    }

    public final boolean canPlaceBlock(@NotNull Block block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return block.func_149686_d() && !BLOCK_BLACKLIST.contains(block);
    }

    public final boolean isBlockListBlock(@NotNull ItemBlock itemBlock) {
        Intrinsics.checkNotNullParameter(itemBlock, "itemBlock");
        Block block = itemBlock.func_179223_d();
        return BLOCK_BLACKLIST.contains(block) || !block.func_149686_d();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C0EPacketClickWindow || packet instanceof C08PacketPlayerBlockPlacement) {
            INV_TIMER.reset();
        }
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            CLICK_TIMER.reset();
        } else if (packet instanceof C0EPacketClickWindow) {
            CLICK_TIMER.reset();
        }
    }

    public final void openPacket() {
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
    }

    public final void closePacket() {
        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0DPacketCloseWindow());
    }

    public final boolean isPositivePotionEffect(int id) {
        return id == Potion.field_76428_l.field_76415_H || id == Potion.field_76424_c.field_76415_H || id == Potion.field_76432_h.field_76415_H || id == Potion.field_76439_r.field_76415_H || id == Potion.field_76430_j.field_76415_H || id == Potion.field_76441_p.field_76415_H || id == Potion.field_76429_m.field_76415_H || id == Potion.field_76427_o.field_76415_H || id == Potion.field_76444_x.field_76415_H || id == Potion.field_76422_e.field_76415_H || id == Potion.field_76420_g.field_76415_H || id == Potion.field_180152_w.field_76415_H || id == Potion.field_76426_n.field_76415_H;
    }

    public final boolean isPositivePotion(@NotNull ItemPotion item, @NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(stack, "stack");
        List list = item.func_77832_l(stack);
        Intrinsics.checkNotNullExpressionValue(list, "item.getEffects(stack)");
        Iterable $this$forEach$iv = list;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PotionEffect it = (PotionEffect)element$iv;
            boolean bl = false;
            if (!INSTANCE.isPositivePotionEffect(it.func_76456_a())) continue;
            return true;
        }
        return false;
    }

    public final float getItemDurability(@NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter(stack, "stack");
        if (stack.func_77984_f() && stack.func_77958_k() > 0) {
            return (float)(stack.func_77958_k() - stack.func_77952_i()) / (float)stack.func_77958_k();
        }
        return 1.0f;
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    private static final boolean getBestSwapSlot$lambda-0(ItemStack $stack, CharSequence s) {
        String string = $stack.func_77977_a();
        Intrinsics.checkNotNullExpressionValue(string, "stack.unlocalizedName");
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        String string2 = string.toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(locale)");
        CharSequence charSequence = string2;
        CharSequence charSequence2 = s;
        Intrinsics.checkNotNull(charSequence2);
        return StringsKt.contains$default(charSequence, charSequence2, false, 2, null);
    }

    static {
        Block[] blockArray = new Block[]{Blocks.field_150381_bn, (Block)Blocks.field_150486_ae, Blocks.field_150477_bB, Blocks.field_150447_bR, Blocks.field_150467_bQ, (Block)Blocks.field_150354_m, Blocks.field_150321_G, Blocks.field_150478_aa, Blocks.field_150462_ai, Blocks.field_150460_al, Blocks.field_150392_bi, Blocks.field_150367_z, Blocks.field_150456_au, Blocks.field_150452_aw, (Block)Blocks.field_150328_O, Blocks.field_150457_bL, (Block)Blocks.field_150327_N, Blocks.field_150323_B, Blocks.field_150409_cd, Blocks.field_180393_cK, Blocks.field_180394_cL, Blocks.field_150335_W};
        BLOCK_BLACKLIST = CollectionsKt.listOf(blockArray);
    }
}

