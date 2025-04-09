/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Locale;
import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Gapple", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020/2\u0006\u00101\u001a\u000202H\u0007J\u0010\u00103\u001a\u00020/2\u0006\u00101\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020/2\u0006\u00101\u001a\u000206H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\t\"\u0004\b\u001d\u0010\u000bR\u000e\u0010\u001e\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0011\"\u0004\b!\u0010\u0013R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020'\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0011\"\u0004\b,\u0010\u0013R\u000e\u0010-\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/Gapple;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "AlertValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "absorpCheck", "delay", "", "getDelay", "()I", "setDelay", "(I)V", "eating", "groundCheck", "invCheck", "isDisable", "", "()Z", "setDisable", "(Z)V", "max", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "min", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "percent", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "prevSlot", "getPrevSlot", "setPrevSlot", "regenSec", "switchBack", "getSwitchBack", "setSwitchBack", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "tryHeal", "getTryHeal", "setTryHeal", "waitRegen", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class Gapple
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final FloatValue percent;
    @NotNull
    private final IntegerValue min;
    @NotNull
    private final IntegerValue max;
    @NotNull
    private final FloatValue regenSec;
    @NotNull
    private final BoolValue groundCheck;
    @NotNull
    private final BoolValue waitRegen;
    @NotNull
    private final BoolValue invCheck;
    @NotNull
    private final BoolValue absorpCheck;
    @NotNull
    private final BoolValue AlertValue;
    @NotNull
    private final MSTimer timer;
    private int eating;
    private int delay;
    private boolean isDisable;
    private boolean tryHeal;
    private int prevSlot;
    private boolean switchBack;

    public Gapple() {
        String[] stringArray = new String[]{"Auto", "LegitAuto", "Legit", "Head"};
        this.modeValue = new ListValue("Mode", stringArray, "Auto");
        this.percent = new FloatValue("HealthPercent", 75.0f, 1.0f, 100.0f);
        this.min = new IntegerValue("MinDelay", 75, 1, 5000);
        this.max = new IntegerValue("MaxDelay", 125, 1, 5000);
        this.regenSec = new FloatValue("MinRegenSec", 4.6f, 0.0f, 10.0f);
        this.groundCheck = new BoolValue("OnlyOnGround", false);
        this.waitRegen = new BoolValue("WaitRegen", true);
        this.invCheck = new BoolValue("InvCheck", false);
        this.absorpCheck = new BoolValue("NoAbsorption", true);
        this.AlertValue = new BoolValue("Alert", false);
        this.timer = new MSTimer();
        this.eating = -1;
        this.prevSlot = -1;
    }

    @NotNull
    public final MSTimer getTimer() {
        return this.timer;
    }

    public final int getDelay() {
        return this.delay;
    }

    public final void setDelay(int n) {
        this.delay = n;
    }

    public final boolean isDisable() {
        return this.isDisable;
    }

    public final void setDisable(boolean bl) {
        this.isDisable = bl;
    }

    public final boolean getTryHeal() {
        return this.tryHeal;
    }

    public final void setTryHeal(boolean bl) {
        this.tryHeal = bl;
    }

    public final int getPrevSlot() {
        return this.prevSlot;
    }

    public final void setPrevSlot(int n) {
        this.prevSlot = n;
    }

    public final boolean getSwitchBack() {
        return this.switchBack;
    }

    public final void setSwitchBack(boolean bl) {
        this.switchBack = bl;
    }

    @Override
    public void onEnable() {
        this.eating = -1;
        this.prevSlot = -1;
        this.switchBack = false;
        this.timer.reset();
        this.isDisable = false;
        this.tryHeal = false;
        this.delay = MathHelper.func_76136_a((Random)new Random(), (int)((Number)this.min.get()).intValue(), (int)((Number)this.max.get()).intValue());
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.isDisable = true;
        this.tryHeal = false;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (this.eating != -1 && packet instanceof C03PacketPlayer) {
            int n = this.eating;
            this.eating = n + 1;
        } else if (packet instanceof S09PacketHeldItemChange || packet instanceof C09PacketHeldItemChange) {
            this.eating = -1;
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        block32: {
            Intrinsics.checkNotNullParameter(event, "event");
            if (!this.tryHeal) break block32;
            String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "auto": {
                    string = Items.field_151153_ao;
                    Intrinsics.checkNotNullExpressionValue(string, "golden_apple");
                    int gappleInHotbar = InventoryUtils.INSTANCE.findItem(36, 45, (Item)string);
                    if (gappleInHotbar != -1) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(gappleInHotbar - 36));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm()));
                        int n = 35;
                        int n2 = 0;
                        while (n2 < n) {
                            int n3;
                            int it = n3 = n2++;
                            boolean bl = false;
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(MinecraftInstance.mc.field_71439_g.field_70122_E));
                        }
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
                        if (((Boolean)this.AlertValue.get()).booleanValue()) {
                            this.alert("Gapple eaten");
                        }
                        this.tryHeal = false;
                        this.timer.reset();
                        this.delay = MathHelper.func_76136_a((Random)new Random(), (int)((Number)this.min.get()).intValue(), (int)((Number)this.max.get()).intValue());
                        break;
                    }
                    this.tryHeal = false;
                    break;
                }
                case "legitauto": {
                    if (this.eating == -1) {
                        string = Items.field_151153_ao;
                        Intrinsics.checkNotNullExpressionValue(string, "golden_apple");
                        int gappleInHotbar = InventoryUtils.INSTANCE.findItem(36, 45, (Item)string);
                        if (gappleInHotbar == -1) {
                            this.tryHeal = false;
                            return;
                        }
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(gappleInHotbar - 36));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm()));
                        this.eating = 0;
                        break;
                    }
                    if (this.eating <= 35) break;
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
                    this.timer.reset();
                    this.tryHeal = false;
                    this.delay = MathHelper.func_76136_a((Random)new Random(), (int)((Number)this.min.get()).intValue(), (int)((Number)this.max.get()).intValue());
                    break;
                }
                case "legit": {
                    if (this.eating == -1) {
                        string = Items.field_151153_ao;
                        Intrinsics.checkNotNullExpressionValue(string, "golden_apple");
                        int gappleInHotbar = InventoryUtils.INSTANCE.findItem(36, 45, (Item)string);
                        if (gappleInHotbar == -1) {
                            this.tryHeal = false;
                            return;
                        }
                        if (this.prevSlot == -1) {
                            this.prevSlot = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
                        }
                        MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c = gappleInHotbar - 36;
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm()));
                        this.eating = 0;
                        break;
                    }
                    if (this.eating <= 35) break;
                    this.timer.reset();
                    this.tryHeal = false;
                    this.delay = MathHelper.func_76136_a((Random)new Random(), (int)((Number)this.min.get()).intValue(), (int)((Number)this.max.get()).intValue());
                    break;
                }
                case "head": {
                    string = Items.field_151144_bL;
                    Intrinsics.checkNotNullExpressionValue(string, "skull");
                    int headInHotbar = InventoryUtils.INSTANCE.findItem(36, 45, (Item)string);
                    if (headInHotbar != -1) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(headInHotbar - 36));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.func_70694_bm()));
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
                        this.timer.reset();
                        this.tryHeal = false;
                        this.delay = MathHelper.func_76136_a((Random)new Random(), (int)((Number)this.min.get()).intValue(), (int)((Number)this.max.get()).intValue());
                        break;
                    }
                    this.tryHeal = false;
                }
            }
        }
        if (MinecraftInstance.mc.field_71439_g.field_70173_aa <= 10 && this.isDisable) {
            this.isDisable = false;
        }
        int absorp = MathHelper.func_76143_f((double)MinecraftInstance.mc.field_71439_g.func_110139_bj());
        if (!this.tryHeal && this.prevSlot != -1) {
            if (!this.switchBack) {
                this.switchBack = true;
                return;
            }
            MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c = this.prevSlot;
            this.prevSlot = -1;
            this.switchBack = false;
        }
        if ((Boolean)this.groundCheck.get() != false && !MinecraftInstance.mc.field_71439_g.field_70122_E || (Boolean)this.invCheck.get() != false && MinecraftInstance.mc.field_71462_r instanceof GuiContainer || absorp > 0 && ((Boolean)this.absorpCheck.get()).booleanValue()) {
            return;
        }
        if (((Boolean)this.waitRegen.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76428_l) && (float)MinecraftInstance.mc.field_71439_g.func_70660_b(Potion.field_76428_l).func_76459_b() > ((Number)this.regenSec.get()).floatValue() * 20.0f) {
            return;
        }
        if (!this.isDisable && MinecraftInstance.mc.field_71439_g.func_110143_aJ() <= ((Number)this.percent.get()).floatValue() / 100.0f * MinecraftInstance.mc.field_71439_g.func_110138_aP() && this.timer.hasTimePassed(this.delay)) {
            if (this.tryHeal) {
                return;
            }
            this.tryHeal = true;
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }
}

