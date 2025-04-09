/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="AutoBot", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0002J\u0010\u0010(\u001a\u00020\u00072\u0006\u0010)\u001a\u00020\tH\u0002J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoBot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoBowValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "autoBowWaitForBowAimValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "autoPotDelayValue", "", "autoPotGroundDistanceValue", "", "autoPotHealthValue", "autoPotNotCombatValue", "autoPotOnGround", "autoPotOpenInventoryValue", "autoPotPot", "autoPotRegenValue", "autoPotSelectValue", "autoPotSimulateInventoryValue", "autoPotThrowAngle", "autoPotThrowAngleOption", "autoPotThrowMode", "", "autoPotThrowTickValue", "autoPotThrowTime", "autoPotThrowing", "autoPotUtilityValue", "autoPotValue", "autoSoupBowlValue", "autoSoupDelayValue", "autoSoupHealthValue", "autoSoupOpenInventoryValue", "autoSoupSimulateInventoryValue", "autoSoupTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoSoupValue", "autoPotFindPotion", "startSlot", "endSlot", "autoPotFindSinglePotion", "slot", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AutoBot
extends Module {
    @NotNull
    private final BoolValue autoSoupValue = new BoolValue("AutoSoup", true);
    @NotNull
    private final BoolValue autoPotValue = new BoolValue("AutoPot", true);
    @NotNull
    private final BoolValue autoBowValue = new BoolValue("AutoBow", true);
    @NotNull
    private final Value<Float> autoSoupHealthValue = new FloatValue("Health", 15.0f, 0.0f, 20.0f).displayable(new Function0<Boolean>(this){
        final /* synthetic */ AutoBot this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AutoBot.access$getAutoSoupValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Integer> autoSoupDelayValue = new IntegerValue("Delay", 150, 0, 500).displayable(new Function0<Boolean>(this){
        final /* synthetic */ AutoBot this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AutoBot.access$getAutoSoupValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Boolean> autoSoupOpenInventoryValue = new BoolValue("OpenInv", false).displayable(new Function0<Boolean>(this){
        final /* synthetic */ AutoBot this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AutoBot.access$getAutoSoupValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Boolean> autoSoupSimulateInventoryValue = new BoolValue("SimulateInventory", true).displayable(new Function0<Boolean>(this){
        final /* synthetic */ AutoBot this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AutoBot.access$getAutoSoupValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<String> autoSoupBowlValue;
    @NotNull
    private final MSTimer autoSoupTimer;
    @NotNull
    private final Value<String> autoPotThrowMode;
    @NotNull
    private final Value<Float> autoPotHealthValue;
    @NotNull
    private final Value<Integer> autoPotDelayValue;
    @NotNull
    private final Value<Integer> autoPotThrowTickValue;
    @NotNull
    private final Value<Integer> autoPotSelectValue;
    @NotNull
    private final Value<Float> autoPotGroundDistanceValue;
    @NotNull
    private final Value<Integer> autoPotThrowAngleOption;
    @NotNull
    private final Value<Boolean> autoPotOpenInventoryValue;
    @NotNull
    private final Value<Boolean> autoPotSimulateInventoryValue;
    @NotNull
    private final Value<Boolean> autoPotRegenValue;
    @NotNull
    private final Value<Boolean> autoPotUtilityValue;
    @NotNull
    private final Value<Boolean> autoPotNotCombatValue;
    @NotNull
    private final Value<Boolean> autoPotOnGround;
    private boolean autoPotThrowing;
    private int autoPotThrowTime;
    private int autoPotPot;
    private float autoPotThrowAngle;
    @NotNull
    private final Value<Boolean> autoBowWaitForBowAimValue;

    public AutoBot() {
        String[] stringArray = new String[]{"Drop", "Move", "Stay"};
        this.autoSoupBowlValue = new ListValue("Bowl", stringArray, "Drop").displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoSoupValue$p(this.this$0).get();
            }
        });
        this.autoSoupTimer = new MSTimer();
        stringArray = new String[]{"Up", "Forward", "Down", "Custom"};
        this.autoPotThrowMode = new ListValue("AutoPot-ThrowMode", stringArray, "Up").displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotHealthValue = new FloatValue("AutoPot-Health", 15.0f, 1.0f, 20.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotDelayValue = new IntegerValue("AutoPot-Delay", 500, 500, 1000).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotThrowTickValue = new IntegerValue("AutoPot-ThrowTick", 3, 1, 10).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotSelectValue = new IntegerValue("AutoPot-SelectSlot", -1, -1, 9).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotGroundDistanceValue = new FloatValue("AutoPot-GroundDistance", 2.0f, 0.0f, 4.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get() != false && (Boolean)AutoBot.access$getAutoPotOnGround$p(this.this$0).get() == false;
            }
        });
        this.autoPotThrowAngleOption = new IntegerValue("AutoPot-ThrowAngle", -45, -90, 90).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AutoBot.access$getAutoPotThrowMode$p(this.this$0).equals("Custom") && (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get() != false;
            }
        });
        this.autoPotOpenInventoryValue = new BoolValue("AutoPot-OpenInv", false).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotSimulateInventoryValue = new BoolValue("AutoPot-SimulateInventory", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotRegenValue = new BoolValue("AutoPot-Regen", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotUtilityValue = new BoolValue("AutoPot-Utility", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotNotCombatValue = new BoolValue("AutoPot-NotCombat", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotOnGround = new BoolValue("AutoPot-OnGround", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoPotValue$p(this.this$0).get();
            }
        });
        this.autoPotPot = -1;
        this.autoBowWaitForBowAimValue = new BoolValue("AutoBow-WaitForBowAimBot", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoBot this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AutoBot.access$getAutoBowValue$p(this.this$0).get();
            }
        });
    }

    private final int autoPotFindPotion(int startSlot, int endSlot) {
        int n = startSlot;
        while (n < endSlot) {
            int i;
            if (!this.autoPotFindSinglePotion(i = n++)) continue;
            return i;
        }
        return -1;
    }

    private final boolean autoPotFindSinglePotion(int slot) {
        block7: {
            ItemPotion itemPotion;
            ItemStack stack;
            block6: {
                stack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(slot).func_75211_c();
                if (stack == null || !(stack.func_77973_b() instanceof ItemPotion) || !ItemPotion.func_77831_g((int)stack.func_77952_i())) {
                    return false;
                }
                Item item = stack.func_77973_b();
                if (item == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
                }
                itemPotion = (ItemPotion)item;
                if (!(MinecraftInstance.mc.field_71439_g.func_110143_aJ() < ((Number)this.autoPotHealthValue.get()).floatValue()) || !this.autoPotRegenValue.get().booleanValue()) break block6;
                for (PotionEffect potionEffect : itemPotion.func_77832_l(stack)) {
                    if (potionEffect.func_76456_a() != Potion.field_76432_h.field_76415_H) continue;
                    return true;
                }
                if (MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76428_l)) break block7;
                for (PotionEffect potionEffect : itemPotion.func_77832_l(stack)) {
                    if (potionEffect.func_76456_a() != Potion.field_76428_l.field_76415_H) continue;
                    return true;
                }
                break block7;
            }
            if (this.autoPotUtilityValue.get().booleanValue()) {
                for (PotionEffect potionEffect : itemPotion.func_77832_l(stack)) {
                    if (potionEffect.func_76456_a() == Potion.field_76432_h.field_76415_H || !InventoryUtils.INSTANCE.isPositivePotionEffect(potionEffect.func_76456_a()) || MinecraftInstance.mc.field_71439_g.func_82165_m(potionEffect.func_76456_a())) continue;
                    return true;
                }
            }
        }
        return false;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        block48: {
            int invPotion;
            int potion;
            boolean enableSelect;
            Intrinsics.checkNotNullParameter(event, "event");
            if (((Boolean)this.autoSoupValue.get()).booleanValue()) {
                if (!this.autoSoupTimer.hasTimePassed(((Number)this.autoSoupDelayValue.get()).intValue())) {
                    return;
                }
                Item item = Items.field_151009_A;
                Intrinsics.checkNotNullExpressionValue(item, "mushroom_stew");
                int soupInHotbar = InventoryUtils.INSTANCE.findItem(36, 45, item);
                if (MinecraftInstance.mc.field_71439_g.func_110143_aJ() <= ((Number)this.autoSoupHealthValue.get()).floatValue() && soupInHotbar != -1) {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(soupInHotbar - 36));
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(soupInHotbar).func_75211_c()));
                    if (this.autoSoupBowlValue.equals("Drop")) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                    }
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
                    this.autoSoupTimer.reset();
                    return;
                }
                Item item2 = Items.field_151054_z;
                Intrinsics.checkNotNullExpressionValue(item2, "bowl");
                int bowlInHotbar = InventoryUtils.INSTANCE.findItem(36, 45, item2);
                if (this.autoSoupBowlValue.equals("Move") && bowlInHotbar != -1) {
                    if (this.autoSoupOpenInventoryValue.get().booleanValue() && !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory)) {
                        return;
                    }
                    boolean bowlMovable = false;
                    int n = 9;
                    while (n < 37) {
                        int i;
                        ItemStack itemStack;
                        if ((itemStack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n++).func_75211_c()) == null) {
                            bowlMovable = true;
                            break;
                        }
                        if (!Intrinsics.areEqual(itemStack.func_77973_b(), Items.field_151054_z) || itemStack.field_77994_a >= 64) continue;
                        bowlMovable = true;
                        break;
                    }
                    if (bowlMovable) {
                        boolean openInventory;
                        boolean bl = openInventory = !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && this.autoSoupSimulateInventoryValue.get() != false;
                        if (openInventory) {
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                        }
                        MinecraftInstance.mc.field_71442_b.func_78753_a(0, bowlInHotbar, 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                    }
                }
                Item openInventory = Items.field_151009_A;
                Intrinsics.checkNotNullExpressionValue(openInventory, "mushroom_stew");
                int soupInInventory = InventoryUtils.INSTANCE.findItem(9, 36, openInventory);
                if (soupInInventory != -1 && InventoryUtils.INSTANCE.hasSpaceHotbar()) {
                    if (this.autoSoupOpenInventoryValue.get().booleanValue() && !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory)) {
                        return;
                    }
                    boolean bl = openInventory = !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && this.autoSoupSimulateInventoryValue.get() != false;
                    if (openInventory) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    }
                    MinecraftInstance.mc.field_71442_b.func_78753_a(0, soupInInventory, 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                    if (openInventory) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0DPacketCloseWindow());
                    }
                    this.autoSoupTimer.reset();
                }
            }
            if (!((Boolean)this.autoPotValue.get()).booleanValue()) break block48;
            String soupInInventory = this.autoPotThrowMode.get().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(soupInInventory, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (soupInInventory) {
                case "up": {
                    this.autoPotThrowAngle = -90.0f;
                    break;
                }
                case "forward": {
                    this.autoPotThrowAngle = 0.0f;
                    break;
                }
                case "down": {
                    this.autoPotThrowAngle = 90.0f;
                    break;
                }
                case "custom": {
                    this.autoPotThrowAngle = ((Number)this.autoPotThrowAngleOption.get()).intValue();
                }
            }
            if (this.autoPotNotCombatValue.get().booleanValue() && CrossSine.INSTANCE.getCombatManager().getInCombat()) {
                return;
            }
            if (this.autoPotOnGround.get().booleanValue() && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                return;
            }
            if (!this.autoPotOnGround.get().booleanValue()) {
                int openInventory;
                FallingPlayer fallingPlayer = new FallingPlayer(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, MinecraftInstance.mc.field_71439_g.field_70159_w, MinecraftInstance.mc.field_71439_g.field_70181_x, MinecraftInstance.mc.field_71439_g.field_70179_y, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70702_br, MinecraftInstance.mc.field_71439_g.field_70701_bs, MinecraftInstance.mc.field_71439_g.field_70747_aH);
                BlockPos collisionBlock = fallingPlayer.findCollision(20);
                double d = MinecraftInstance.mc.field_71439_g.field_70163_u;
                BlockPos blockPos = collisionBlock;
                int n = blockPos == null ? 0 : (openInventory = blockPos.func_177956_o());
                if (d - (double)n >= (double)(((Number)this.autoPotGroundDistanceValue.get()).floatValue() + 1.01f)) {
                    return;
                }
            }
            if (this.autoPotThrowing) {
                int fallingPlayer = this.autoPotThrowTime;
                this.autoPotThrowTime = fallingPlayer + 1;
                RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, this.autoPotThrowAngle), 0);
                if (this.autoPotThrowTime == ((Number)this.autoPotThrowTickValue.get()).intValue()) {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(this.autoPotPot - 36));
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm()));
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
                    this.autoPotPot = -1;
                }
                if (this.autoPotThrowTime >= ((Number)this.autoPotThrowTickValue.get()).intValue() * 2) {
                    this.autoPotThrowTime = 0;
                    this.autoPotThrowing = false;
                }
                return;
            }
            if (!InventoryUtils.INSTANCE.getINV_TIMER().hasTimePassed(((Number)this.autoPotDelayValue.get()).intValue())) {
                return;
            }
            boolean bl = enableSelect = ((Number)this.autoPotSelectValue.get()).intValue() != -1;
            int n = enableSelect ? (this.autoPotFindSinglePotion(36 + ((Number)this.autoPotSelectValue.get()).intValue()) ? 36 + ((Number)this.autoPotSelectValue.get()).intValue() : -1) : (potion = this.autoPotFindPotion(36, 45));
            if (potion != -1) {
                RotationUtils.setTargetRotation(new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, this.autoPotThrowAngle), 0);
                this.autoPotPot = potion;
                this.autoPotThrowing = true;
                InventoryUtils.INSTANCE.getINV_TIMER().reset();
                return;
            }
            if (this.autoPotOpenInventoryValue.get().booleanValue() && !enableSelect && (invPotion = this.autoPotFindPotion(9, 36)) != -1) {
                boolean openInventory;
                boolean bl2 = openInventory = !(MinecraftInstance.mc.field_71462_r instanceof GuiInventory) && this.autoPotSimulateInventoryValue.get() != false;
                if (InventoryUtils.INSTANCE.hasSpaceHotbar()) {
                    if (openInventory) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    }
                    MinecraftInstance.mc.field_71442_b.func_78753_a(0, invPotion, 0, 1, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                    if (openInventory) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0DPacketCloseWindow());
                    }
                    return;
                }
                int n2 = 36;
                while (n2 < 45) {
                    int i;
                    ItemStack stack;
                    if ((stack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n2++).func_75211_c()) == null || !(stack.func_77973_b() instanceof ItemPotion) || !ItemPotion.func_77831_g((int)stack.func_77952_i())) continue;
                    if (openInventory) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    }
                    MinecraftInstance.mc.field_71442_b.func_78753_a(0, invPotion, 0, 0, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                    MinecraftInstance.mc.field_71442_b.func_78753_a(0, i, 0, 0, (EntityPlayer)MinecraftInstance.mc.field_71439_g);
                    if (!openInventory) break;
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0DPacketCloseWindow());
                    break;
                }
            }
        }
        if (((Boolean)this.autoBowValue.get()).booleanValue()) {
            BowAimbot bowAimbot = CrossSine.INSTANCE.getModuleManager().get(BowAimbot.class);
            Intrinsics.checkNotNull(bowAimbot);
            BowAimbot bowAimbot2 = bowAimbot;
            if (MinecraftInstance.mc.field_71439_g.func_71039_bw()) {
                ItemStack itemStack = MinecraftInstance.mc.field_71439_g.func_70694_bm();
                if (Intrinsics.areEqual(itemStack == null ? null : itemStack.func_77973_b(), Items.field_151031_f) && MinecraftInstance.mc.field_71439_g.func_71057_bx() > 20 && (!this.autoBowWaitForBowAimValue.get().booleanValue() || !bowAimbot2.getState() || bowAimbot2.hasTarget())) {
                    MinecraftInstance.mc.field_71439_g.func_71034_by();
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
                }
            }
        }
    }

    public static final /* synthetic */ BoolValue access$getAutoSoupValue$p(AutoBot $this) {
        return $this.autoSoupValue;
    }

    public static final /* synthetic */ BoolValue access$getAutoPotValue$p(AutoBot $this) {
        return $this.autoPotValue;
    }

    public static final /* synthetic */ Value access$getAutoPotOnGround$p(AutoBot $this) {
        return $this.autoPotOnGround;
    }

    public static final /* synthetic */ Value access$getAutoPotThrowMode$p(AutoBot $this) {
        return $this.autoPotThrowMode;
    }

    public static final /* synthetic */ BoolValue access$getAutoBowValue$p(AutoBot $this) {
        return $this.autoBowValue;
    }
}

