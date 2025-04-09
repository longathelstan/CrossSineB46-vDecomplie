/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.special.NotificationUtil;
import net.ccbluex.liquidbounce.features.special.TYPE;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ModuleInfo(name="BedWarsHelper", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\n2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010!\u001a\u00020 H\u0002J\u0010\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020\nH\u0002J\u0010\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\u0007H\u0002J\u0010\u0010)\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/BedWarsHelper;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoChest", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "diamondArmorList", "Ljava/util/ArrayList;", "Lnet/minecraft/entity/player/EntityPlayer;", "diamondSwordList", "enderpearlList", "fireBallList", "invisibilityPotionList", "ironSwordList", "items", "", "", "obsidianList", "openDelay", "stoneSwordList", "tntList", "alert", "", "string", "entity", "list", "depositItems", "container", "Lnet/minecraft/inventory/ContainerChest;", "hasTargetItemsInChest", "", "hasTargetItemsInInventory", "isWearingDiamondArmor", "player", "onPreUpdate", "event", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "transferItem", "slot", "withdrawItems", "CrossSine"})
public final class BedWarsHelper
extends Module {
    @NotNull
    private final BoolValue autoChest = new BoolValue("AutoChest", false);
    @NotNull
    private final Value<Integer> openDelay = new IntegerValue("Delay", 150, 0, 500).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BedWarsHelper this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BedWarsHelper.access$getAutoChest$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Integer> delayValue = new IntegerValue("Delay", 150, 0, 500).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BedWarsHelper this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BedWarsHelper.access$getAutoChest$p(this.this$0).get();
        }
    });
    @NotNull
    private final List<String> items;
    @NotNull
    private final ArrayList<EntityPlayer> stoneSwordList;
    @NotNull
    private final ArrayList<EntityPlayer> ironSwordList;
    @NotNull
    private final ArrayList<EntityPlayer> diamondSwordList;
    @NotNull
    private final ArrayList<EntityPlayer> fireBallList;
    @NotNull
    private final ArrayList<EntityPlayer> enderpearlList;
    @NotNull
    private final ArrayList<EntityPlayer> tntList;
    @NotNull
    private final ArrayList<EntityPlayer> obsidianList;
    @NotNull
    private final ArrayList<EntityPlayer> diamondArmorList;
    @NotNull
    private final ArrayList<EntityPlayer> invisibilityPotionList;

    public BedWarsHelper() {
        String[] stringArray = new String[]{"minecraft:iron_ingot", "minecraft:gold_ingot", "minecraft:diamond", "minecraft:emerald"};
        this.items = CollectionsKt.listOf(stringArray);
        this.stoneSwordList = new ArrayList();
        this.ironSwordList = new ArrayList();
        this.diamondSwordList = new ArrayList();
        this.fireBallList = new ArrayList();
        this.enderpearlList = new ArrayList();
        this.tntList = new ArrayList();
        this.obsidianList = new ArrayList();
        this.diamondArmorList = new ArrayList();
        this.invisibilityPotionList = new ArrayList();
    }

    @EventTarget
    public final void onPreUpdate(@NotNull PreUpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        for (EntityPlayer entity : MinecraftInstance.mc.field_71441_e.field_73010_i) {
            if (entity == null || Intrinsics.areEqual(entity, MinecraftInstance.mc.field_71439_g)) continue;
            if (Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), Items.field_151052_q) && !this.stoneSwordList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has Stone Sword", entity, this.stoneSwordList);
            }
            if (Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), Items.field_151040_l) && !this.ironSwordList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has Iron Sword", entity, this.ironSwordList);
            }
            if (Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), Items.field_151048_u) && !this.diamondSwordList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has Diamond Sword", entity, this.diamondSwordList);
            }
            if (Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), Items.field_151059_bz) && !this.fireBallList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has Fire Ball", entity, this.fireBallList);
            }
            if (Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), Items.field_151079_bi) && !this.enderpearlList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has Ender Pearl", entity, this.enderpearlList);
            }
            if (Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), ItemBlock.func_150899_d((int)46)) && !this.tntList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has TNT", entity, this.tntList);
            }
            if (Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), ItemBlock.func_150899_d((int)49)) && !this.obsidianList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has Obsidian", entity, this.obsidianList);
            }
            if (this.isWearingDiamondArmor(entity) && !this.diamondArmorList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has Diamond Armor", entity, this.diamondArmorList);
            }
            if (Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), Potion.field_76441_p) && !this.invisibilityPotionList.contains(entity)) {
                this.alert("\u00a7C" + entity.func_70005_c_() + "\u00a7F has Invisibility Potion", entity, this.invisibilityPotionList);
            }
            if (!Intrinsics.areEqual(entity.func_70694_bm().func_77973_b(), Items.field_151041_m) || !this.stoneSwordList.contains(entity) && !this.ironSwordList.contains(entity) && !this.diamondSwordList.contains(entity)) continue;
            this.stoneSwordList.remove(entity);
            this.ironSwordList.remove(entity);
            this.diamondSwordList.remove(entity);
        }
        if (((Boolean)this.autoChest.get()).booleanValue() && MinecraftInstance.mc.field_71462_r instanceof GuiChest) {
            GuiScreen guiScreen = MinecraftInstance.mc.field_71462_r;
            if (guiScreen == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.client.gui.inventory.GuiContainer");
            }
            GuiContainer chest = (GuiContainer)guiScreen;
            Container container = chest.field_147002_h;
            if (container == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.inventory.ContainerChest");
            }
            ContainerChest container2 = (ContainerChest)container;
            new Thread(() -> BedWarsHelper.onPreUpdate$lambda-0(this, container2)).start();
        }
    }

    private final boolean hasTargetItemsInInventory() {
        int n = 9;
        while (n < 36) {
            int i;
            Slot slot;
            if (!(slot = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n++)).func_75216_d()) continue;
            ItemStack stack = slot.func_75211_c();
            String string = ((ResourceLocation)Item.field_150901_e.func_177774_c((Object)stack.func_77973_b())).toString();
            Intrinsics.checkNotNullExpressionValue(string, "itemRegistry.getNameForO\u2026ct(stack.item).toString()");
            String itemName = string;
            if (!this.items.contains(itemName)) continue;
            return true;
        }
        return false;
    }

    private final boolean hasTargetItemsInChest(ContainerChest container) {
        int n = 0;
        while (n < 27) {
            int i;
            Slot slot;
            if (!(slot = container.func_75139_a(i = n++)).func_75216_d()) continue;
            ItemStack stack = slot.func_75211_c();
            String string = ((ResourceLocation)Item.field_150901_e.func_177774_c((Object)stack.func_77973_b())).toString();
            Intrinsics.checkNotNullExpressionValue(string, "itemRegistry.getNameForO\u2026ct(stack.item).toString()");
            String itemName = string;
            if (!this.items.contains(itemName)) continue;
            return true;
        }
        return false;
    }

    private final void depositItems(ContainerChest container) {
        int n = 9;
        while (n < 36) {
            int i;
            Slot slot;
            if (!(slot = container.func_75139_a(i = n++)).func_75216_d()) continue;
            ItemStack stack = slot.func_75211_c();
            String string = ((ResourceLocation)Item.field_150901_e.func_177774_c((Object)stack.func_77973_b())).toString();
            Intrinsics.checkNotNullExpressionValue(string, "itemRegistry.getNameForO\u2026ct(stack.item).toString()");
            String itemName = string;
            if (!this.items.contains(itemName)) continue;
            System.out.println((Object)("Depositing item: " + itemName + " at slot " + i));
            this.transferItem(i);
            Thread.sleep(200L);
        }
    }

    private final void withdrawItems(ContainerChest container) {
        int n = 0;
        while (n < 27) {
            int i;
            Slot slot;
            if (!(slot = container.func_75139_a(i = n++)).func_75216_d()) continue;
            ItemStack stack = slot.func_75211_c();
            String string = ((ResourceLocation)Item.field_150901_e.func_177774_c((Object)stack.func_77973_b())).toString();
            Intrinsics.checkNotNullExpressionValue(string, "itemRegistry.getNameForO\u2026ct(stack.item).toString()");
            String itemName = string;
            if (!this.items.contains(itemName)) continue;
            System.out.println((Object)("Withdrawing item: " + itemName + " from slot " + i));
            this.transferItem(i);
            Thread.sleep(200L);
        }
    }

    private final void transferItem(int slot) {
        System.out.println((Object)Intrinsics.stringPlus("Transferring item from slot: ", slot));
        MinecraftInstance.mc.field_71442_b.func_78753_a(MinecraftInstance.mc.field_71439_g.field_71070_bA.field_75152_c, slot, 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
    }

    private final boolean isWearingDiamondArmor(EntityPlayer player) {
        ItemStack[] armorInventory = player.field_71071_by.field_70460_b;
        Intrinsics.checkNotNullExpressionValue(armorInventory, "armorInventory");
        for (ItemStack itemStack : armorInventory) {
            if (!Intrinsics.areEqual(itemStack.func_77973_b(), Items.field_151173_ae) && !Intrinsics.areEqual(itemStack.func_77973_b(), Items.field_151163_ad)) continue;
            return true;
        }
        return false;
    }

    private final void alert(String string, EntityPlayer entity, ArrayList<EntityPlayer> list) {
        CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("BWH", string + ", Distance: " + new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH)).format(Float.valueOf(MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)entity))), TYPE.WARNING, System.currentTimeMillis(), 3000));
        ClientUtils.INSTANCE.displayChatMessage("\u00a7F[\u00a7CBWH\u00a7F] " + string + ", Distance: " + new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH)).format(Float.valueOf(MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)entity))));
        list.add(entity);
    }

    private static final void onPreUpdate$lambda-0(BedWarsHelper this$0, ContainerChest $container) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter($container, "$container");
        try {
            Thread.sleep(100L);
            if (this$0.hasTargetItemsInInventory()) {
                System.out.println((Object)"State: Depositing items into chest");
                this$0.depositItems($container);
            } else if (this$0.hasTargetItemsInChest($container)) {
                System.out.println((Object)"State: Withdrawing items from chest");
                this$0.withdrawItems($container);
            }
            Thread.sleep(200L);
            MinecraftInstance.mc.field_71439_g.func_71053_j();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static final /* synthetic */ BoolValue access$getAutoChest$p(BedWarsHelper $this) {
        return $this.autoChest;
    }
}

