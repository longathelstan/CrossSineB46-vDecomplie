/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights.matrix;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/matrix/MatrixDamageFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "boostTicks", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "customstrafe", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "mode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "motionreduceonend", "packetymotion", "", "randomAmount", "randomNum", "randomize", "speedBoost", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "tick", "", "timer", "velocitypacket", "", "warn", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "resetmotion", "CrossSine"})
public final class MatrixDamageFlight
extends FlightMode {
    @NotNull
    private final ListValue mode;
    @NotNull
    private final BoolValue warn;
    @NotNull
    private final FloatValue timer;
    @NotNull
    private final FloatValue speedBoost;
    @NotNull
    private final IntegerValue boostTicks;
    @NotNull
    private final BoolValue randomize;
    @NotNull
    private final IntegerValue randomAmount;
    @NotNull
    private final BoolValue customstrafe;
    @NotNull
    private final BoolValue motionreduceonend;
    private boolean velocitypacket;
    private double packetymotion;
    private int tick;
    private double randomNum;

    public MatrixDamageFlight() {
        super("MatrixDamage");
        String[] stringArray = new String[]{"Stable", "Test", "Custom"};
        this.mode = new ListValue(Intrinsics.stringPlus(this.getValuePrefix(), "Mode"), stringArray, "Stable");
        this.warn = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "DamageWarn"), true);
        this.timer = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Timer"), 1.0f, 0.0f, 2.0f);
        this.speedBoost = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-BoostSpeed"), 0.5f, 0.0f, 3.0f);
        this.boostTicks = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-BoostTicks"), 27, 10, 40);
        this.randomize = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-Randomize"), true);
        this.randomAmount = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-RandomAmount"), 1, 0, 30);
        this.customstrafe = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "Custom-Strafe"), true);
        this.motionreduceonend = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "MotionReduceOnEnd"), true);
        this.randomNum = 0.2;
    }

    @Override
    public void onEnable() {
        if (((Boolean)this.warn.get()).booleanValue()) {
            ClientUtils.INSTANCE.displayChatMessage("\u00a78[\u00a7c\u00a7lMatrix-Dmg-Fly\u00a78] \u00a7aGetting damage from other entities (players, arrows, snowballs, eggs...) is required to bypass.");
        }
        this.velocitypacket = false;
        this.packetymotion = 0.0;
        this.tick = 0;
    }

    private final void resetmotion() {
        if (((Boolean)this.motionreduceonend.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_70159_w /= (double)10;
            MinecraftInstance.mc.field_71439_g.field_70181_x /= (double)10;
            MinecraftInstance.mc.field_71439_g.field_70179_y /= (double)10;
        }
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        block17: {
            Intrinsics.checkNotNullParameter(event, "event");
            if (((Boolean)this.motionreduceonend.get()).booleanValue()) {
                this.getFlight().setNeedReset(false);
            }
            if (!this.velocitypacket) break block17;
            double yaw = Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z);
            String string = ((String)this.mode.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "stable": {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70159_w += -Math.sin(yaw) * 0.416;
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70179_y += Math.cos(yaw) * 0.416;
                    MinecraftInstance.mc.field_71439_g.field_70181_x = this.packetymotion;
                    int n = this.tick;
                    this.tick = n + 1;
                    if (n < 27) break;
                    this.resetmotion();
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    this.velocitypacket = false;
                    this.packetymotion = 0.0;
                    this.tick = 0;
                    break;
                }
                case "test": {
                    EntityPlayerSP entityPlayerSP;
                    int n = this.tick;
                    this.tick = n + 1;
                    if (n >= 4) {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.1f;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70159_w += -Math.sin(yaw) * 0.42;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70179_y += Math.cos(yaw) * 0.42;
                    } else {
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 0.9f;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70159_w += -Math.sin(yaw) * 0.33;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        entityPlayerSP.field_70179_y += Math.cos(yaw) * 0.33;
                    }
                    MinecraftInstance.mc.field_71439_g.field_70181_x = this.packetymotion;
                    int n2 = this.tick;
                    this.tick = n2 + 1;
                    if (n2 < 27) break;
                    this.resetmotion();
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    this.velocitypacket = false;
                    this.packetymotion = 0.0;
                    this.tick = 0;
                    break;
                }
                case "custom": {
                    if (((Boolean)this.customstrafe.get()).booleanValue()) {
                        MovementUtils.INSTANCE.strafe();
                    }
                    this.randomNum = (Boolean)this.randomize.get() != false ? Math.random() * ((Number)this.randomAmount.get()).doubleValue() * 0.01 : 0.0;
                    MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.timer.get()).floatValue();
                    EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70159_w += -Math.sin(yaw) * (0.3 + (double)((Number)this.speedBoost.get()).floatValue() / (double)10 + this.randomNum);
                    entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                    entityPlayerSP.field_70179_y += Math.cos(yaw) * (0.3 + (double)((Number)this.speedBoost.get()).floatValue() / (double)10 + this.randomNum);
                    MinecraftInstance.mc.field_71439_g.field_70181_x = this.packetymotion;
                    int n = this.tick;
                    this.tick = n + 1;
                    if (n < ((Number)this.boostTicks.get()).intValue()) break;
                    this.resetmotion();
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    this.velocitypacket = false;
                    this.packetymotion = 0.0;
                    this.tick = 0;
                }
            }
        }
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        this.resetmotion();
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        block5: {
            Packet<?> packet;
            block7: {
                block6: {
                    Intrinsics.checkNotNullParameter(event, "event");
                    packet = event.getPacket();
                    if (!(packet instanceof S12PacketEntityVelocity)) break block5;
                    if (MinecraftInstance.mc.field_71439_g == null) break block6;
                    WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                    Entity entity = worldClient == null ? null : worldClient.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
                    if (entity == null) {
                        return;
                    }
                    if (Intrinsics.areEqual(entity, MinecraftInstance.mc.field_71439_g)) break block7;
                }
                return;
            }
            if ((double)((S12PacketEntityVelocity)packet).field_149416_c / 8000.0 > 0.2) {
                this.velocitypacket = true;
                this.packetymotion = (double)((S12PacketEntityVelocity)packet).field_149416_c / 8000.0;
            }
        }
    }
}

