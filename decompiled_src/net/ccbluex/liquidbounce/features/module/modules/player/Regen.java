/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Regen", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/Regen;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "foodValue", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "noAirValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "potionEffectValue", "resetTimer", "", "speedValue", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onEnable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Regen
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final IntegerValue healthValue;
    @NotNull
    private final IntegerValue delayValue;
    @NotNull
    private final IntegerValue foodValue;
    @NotNull
    private final IntegerValue speedValue;
    @NotNull
    private final BoolValue noAirValue;
    @NotNull
    private final BoolValue potionEffectValue;
    @NotNull
    private final MSTimer timer;
    private boolean resetTimer;

    public Regen() {
        String[] stringArray = new String[]{"Vanilla", "OldSpartan", "NewSpartan", "AAC4NoFire"};
        this.modeValue = new ListValue("Mode", stringArray, "Vanilla");
        this.healthValue = new IntegerValue("Health", 18, 0, 20);
        this.delayValue = new IntegerValue("Delay", 0, 0, 1000);
        this.foodValue = new IntegerValue("Food", 18, 0, 20);
        this.speedValue = new IntegerValue("Speed", 100, 1, 100);
        this.noAirValue = new BoolValue("NoAir", false);
        this.potionEffectValue = new BoolValue("PotionEffect", false);
        this.timer = new MSTimer();
    }

    @Override
    public void onEnable() {
        this.timer.reset();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        block25: {
            Intrinsics.checkNotNullParameter(event, "event");
            if (this.resetTimer) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                this.resetTimer = false;
            }
            if (((Boolean)this.noAirValue.get()).booleanValue() && !MinecraftInstance.mc.field_71439_g.field_70122_E || MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75098_d || MinecraftInstance.mc.field_71439_g.func_71024_bL().func_75116_a() <= ((Number)this.foodValue.get()).intValue() || !MinecraftInstance.mc.field_71439_g.func_70089_S() || !(MinecraftInstance.mc.field_71439_g.func_110143_aJ() < (float)((Number)this.healthValue.get()).intValue())) break block25;
            if (((Boolean)this.potionEffectValue.get()).booleanValue() && !MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76428_l)) {
                return;
            }
            if (!this.timer.hasTimePassed(((Number)this.delayValue.get()).intValue())) {
                return;
            }
            String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "vanilla": {
                    int n = ((Number)this.speedValue.get()).intValue();
                    int n2 = 0;
                    while (n2 < n) {
                        int n3;
                        int it = n3 = n2++;
                        boolean bl = false;
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(MinecraftInstance.mc.field_71439_g.field_70122_E));
                    }
                    break;
                }
                case "aac4nofire": {
                    if (!MinecraftInstance.mc.field_71439_g.func_70027_ad() || MinecraftInstance.mc.field_71439_g.field_70173_aa % 10 != 0) break;
                    int n = 35;
                    int n4 = 0;
                    while (n4 < n) {
                        int n5;
                        int it = n5 = n4++;
                        boolean bl = false;
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(true));
                    }
                    break;
                }
                case "newspartan": {
                    if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 5 == 0) {
                        this.resetTimer = true;
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.98f;
                        int n = 10;
                        int n6 = 0;
                        while (n6 < n) {
                            int n7;
                            int it = n7 = n6++;
                            boolean bl = false;
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(true));
                        }
                        break;
                    }
                    if (!MovementUtils.INSTANCE.isMoving()) break;
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(MinecraftInstance.mc.field_71439_g.field_70122_E));
                    break;
                }
                case "oldspartan": {
                    if (MovementUtils.INSTANCE.isMoving() || !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        return;
                    }
                    int n = 9;
                    int n8 = 0;
                    while (n8 < n) {
                        int n9;
                        int it = n9 = n8++;
                        boolean bl = false;
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(MinecraftInstance.mc.field_71439_g.field_70122_E));
                    }
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 0.45f;
                    this.resetTimer = true;
                }
            }
            this.timer.reset();
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }
}

