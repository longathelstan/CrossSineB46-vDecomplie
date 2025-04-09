/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="NoWeb", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoWeb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "horizonSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "usedTimer", "", "onDisable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class NoWeb
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final FloatValue horizonSpeed;
    private boolean usedTimer;

    public NoWeb() {
        String[] stringArray = new String[]{"None", "OldAAC", "LAAC", "Rewinside", "Horizon", "Spartan", "AAC4", "AAC5", "Matrix", "Test"};
        this.modeValue = new ListValue("Mode", stringArray, "None");
        this.horizonSpeed = new FloatValue("HorizonSpeed", 0.1f, 0.01f, 0.8f);
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.usedTimer) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
            this.usedTimer = false;
        }
        if (!MinecraftInstance.mc.field_71439_g.field_70134_J) {
            return;
        }
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "none": {
                MinecraftInstance.mc.field_71439_g.field_70134_J = false;
                break;
            }
            case "oldaac": {
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.59f;
                if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) break;
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                break;
            }
            case "laac": {
                float f = MinecraftInstance.mc.field_71439_g.field_70747_aH = !(MinecraftInstance.mc.field_71439_g.field_71158_b.field_78902_a == 0.0f) ? 1.0f : 1.21f;
                if (!MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                }
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                break;
            }
            case "aac4": {
                MinecraftInstance.mc.field_71428_T.field_74278_d = 0.99f;
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.02958f;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70181_x -= 0.00775;
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.405;
                MinecraftInstance.mc.field_71428_T.field_74278_d = 1.35f;
                break;
            }
            case "horizon": {
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MovementUtils.INSTANCE.strafe(((Number)this.horizonSpeed.get()).floatValue());
                break;
            }
            case "spartan": {
                MovementUtils.INSTANCE.strafe(0.27f);
                MinecraftInstance.mc.field_71428_T.field_74278_d = 3.7f;
                if (!MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                }
                if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 2 == 0) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.7f;
                }
                if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 40 == 0) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 3.0f;
                }
                this.usedTimer = true;
                break;
            }
            case "matrix": {
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.12425f;
                MinecraftInstance.mc.field_71439_g.field_70181_x = -0.0125;
                if (MinecraftInstance.mc.field_71474_y.field_74311_E.func_151470_d()) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = -0.1625;
                }
                if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 40 != 0) break;
                MinecraftInstance.mc.field_71428_T.field_74278_d = 3.0f;
                this.usedTimer = true;
                break;
            }
            case "aac5": {
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.42f;
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                break;
            }
            case "test": {
                if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 7 == 0) {
                    MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.42f;
                }
                if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 7 == 1) {
                    MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.33f;
                }
                if (MinecraftInstance.mc.field_71439_g.field_70173_aa % 7 != 2) break;
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.08f;
                break;
            }
            case "rewinside": {
                MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.42f;
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
            }
        }
    }

    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.modeValue.equals("AAC4")) {
            event.cancelEvent();
        }
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }

    @Override
    @Nullable
    public String getTag() {
        return (String)this.modeValue.get();
    }
}

