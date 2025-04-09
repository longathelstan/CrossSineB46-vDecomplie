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
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/matrix/MatrixBoostFlight;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "()V", "boostMotion", "", "boostTimer", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "bypassMode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "customYMotion", "jumpTimer", "speed", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MatrixBoostFlight
extends FlightMode {
    @NotNull
    private final ListValue bypassMode;
    @NotNull
    private final FloatValue speed;
    @NotNull
    private final FloatValue customYMotion;
    @NotNull
    private final FloatValue jumpTimer;
    @NotNull
    private final FloatValue boostTimer;
    private int boostMotion;

    public MatrixBoostFlight() {
        super("MatrixBoost");
        String[] stringArray = new String[]{"New", "Stable", "Test", "Custom"};
        this.bypassMode = new ListValue(Intrinsics.stringPlus(this.getValuePrefix(), "BypassMode"), stringArray, "New");
        this.speed = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "Speed"), 2.0f, 1.0f, 3.0f);
        this.customYMotion = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "CustomJumpMotion"), 0.6f, 0.2f, 5.0f);
        this.jumpTimer = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "JumpTimer"), 0.1f, 0.1f, 2.0f);
        this.boostTimer = new FloatValue(Intrinsics.stringPlus(this.getValuePrefix(), "BoostTimer"), 1.0f, 0.5f, 3.0f);
    }

    @Override
    public void onEnable() {
        this.boostMotion = 0;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        block23: {
            block25: {
                block24: {
                    block22: {
                        Intrinsics.checkNotNullParameter(event, "event");
                        if (this.boostMotion != 0) break block22;
                        double yaw = Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z);
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
                        if (this.bypassMode.equals("Test")) {
                            MovementUtils.INSTANCE.strafe(5.0f);
                            MinecraftInstance.mc.field_71439_g.field_70181_x = 2.0;
                        } else {
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(yaw) * 1.5, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(yaw) * 1.5, false));
                        }
                        this.boostMotion = 1;
                        MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.jumpTimer.get()).floatValue();
                        break block23;
                    }
                    if (this.boostMotion != 1 || !this.bypassMode.equals("Test")) break block24;
                    MovementUtils.INSTANCE.strafe(1.89f);
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 2.0;
                    break block23;
                }
                if (this.boostMotion != 2) break block25;
                MovementUtils.INSTANCE.strafe(((Number)this.speed.get()).floatValue());
                String string = ((String)this.bypassMode.get()).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                switch (string) {
                    case "stable": {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.8;
                        break;
                    }
                    case "new": {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.48;
                        break;
                    }
                    case "test": {
                        double yaw = Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z);
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + -Math.sin(yaw) * (double)2, MinecraftInstance.mc.field_71439_g.field_70163_u + 2.0, MinecraftInstance.mc.field_71439_g.field_70161_v + Math.cos(yaw) * (double)2, true));
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 2.0;
                        MovementUtils.INSTANCE.strafe(1.89f);
                        break;
                    }
                    case "custom": {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = ((Number)this.customYMotion.get()).floatValue();
                    }
                }
                this.boostMotion = 3;
                break block23;
            }
            if (this.boostMotion < 5) {
                int n = this.boostMotion;
                this.boostMotion = n + 1;
            } else if (this.boostMotion >= 5) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)this.boostTimer.get()).floatValue();
                if (MinecraftInstance.mc.field_71439_g.field_70163_u < this.getFlight().getLaunchY() - 1.0) {
                    this.boostMotion = 0;
                }
            }
        }
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (MinecraftInstance.mc.field_71462_r == null && packet instanceof S08PacketPlayerPosLook) {
            MinecraftInstance.mc.field_71439_g.func_70107_b(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c);
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)packet).field_148940_a, ((S08PacketPlayerPosLook)packet).field_148938_b, ((S08PacketPlayerPosLook)packet).field_148939_c, ((S08PacketPlayerPosLook)packet).field_148936_d, ((S08PacketPlayerPosLook)packet).field_148937_e, false));
            if (this.boostMotion == 1) {
                this.boostMotion = 2;
            }
            event.cancelEvent();
        }
    }
}

