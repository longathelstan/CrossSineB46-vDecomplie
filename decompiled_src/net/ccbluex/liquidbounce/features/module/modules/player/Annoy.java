/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Annoy", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/Annoy;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "pitch", "", "pitchModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "rotateValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "spinSpeedValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "yaw", "yawModeValue", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Annoy
extends Module {
    @NotNull
    private final ListValue yawModeValue;
    @NotNull
    private final ListValue pitchModeValue;
    @NotNull
    private final Value<Integer> spinSpeedValue;
    @NotNull
    private final BoolValue rotateValue;
    private float yaw;
    private float pitch;

    public Annoy() {
        String[] stringArray = new String[]{"Jitter", "Spin", "Back", "BackJitter"};
        this.yawModeValue = new ListValue("YawMode", stringArray, "Spin");
        stringArray = new String[]{"Down", "Up", "Jitter", "AnotherJitter"};
        this.pitchModeValue = new ListValue("PitchMode", stringArray, "Down");
        this.spinSpeedValue = new IntegerValue("spinSpeed", 20, 1, 90).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Annoy this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Annoy.access$getYawModeValue$p(this.this$0).equals("Spin");
            }
        });
        this.rotateValue = new BoolValue("SilentRotate", true);
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        String string = ((String)this.yawModeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "spin": {
                this.yaw += (float)((Number)this.spinSpeedValue.get()).intValue();
                if (this.yaw > 180.0f) {
                    this.yaw = -180.0f;
                    break;
                }
                if (!(this.yaw < -180.0f)) break;
                this.yaw = 180.0f;
                break;
            }
            case "jitter": {
                this.yaw = MinecraftInstance.mc.field_71439_g.field_70177_z + (MinecraftInstance.mc.field_71439_g.field_70173_aa % 2 == 0 ? 90.0f : -90.0f);
                break;
            }
            case "back": {
                this.yaw = MinecraftInstance.mc.field_71439_g.field_70177_z + 180.0f;
                break;
            }
            case "backjitter": {
                this.yaw = MinecraftInstance.mc.field_71439_g.field_70177_z + 180.0f + (float)RandomUtils.INSTANCE.nextDouble(-3.0, 3.0);
            }
        }
        string = ((String)this.pitchModeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "up": {
                this.pitch = -90.0f;
                break;
            }
            case "down": {
                this.pitch = 90.0f;
                break;
            }
            case "anotherjitter": {
                this.pitch = 60.0f + (float)RandomUtils.INSTANCE.nextDouble(-3.0, 3.0);
                break;
            }
            case "jitter": {
                this.pitch += 30.0f;
                if (this.pitch > 90.0f) {
                    this.pitch = -90.0f;
                    break;
                }
                if (!(this.pitch < -90.0f)) break;
                this.pitch = 90.0f;
            }
        }
        if (((Boolean)this.rotateValue.get()).booleanValue()) {
            RotationUtils.setTargetRotation(new Rotation(this.yaw, this.pitch), 0);
        } else {
            MinecraftInstance.mc.field_71439_g.field_70177_z = this.yaw;
            MinecraftInstance.mc.field_71439_g.field_70125_A = this.pitch;
        }
    }

    public static final /* synthetic */ ListValue access$getYawModeValue$p(Annoy $this) {
        return $this.yawModeValue;
    }
}

