/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Sprint", category=ModuleCategory.MOVEMENT, array=false, defaultOn=true)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020 H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u0011\u0010\u0015\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR\u000e\u0010\u0017\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Sprint;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "allDirectionsBypassValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "allDirectionsValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getAllDirectionsValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "collideValue", "getCollideValue", "downValue", "forceSprint", "", "getForceSprint", "()Z", "setForceSprint", "(Z)V", "hungryValue", "getHungryValue", "sneakValue", "getSneakValue", "switchStat", "textValue", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Sprint
extends Module {
    @NotNull
    public static final Sprint INSTANCE = new Sprint();
    @NotNull
    private static final BoolValue textValue = new BoolValue("ShowText", false);
    @NotNull
    private static final BoolValue downValue = new BoolValue("Down", false);
    @NotNull
    private static final BoolValue hungryValue = new BoolValue("Hungry", true);
    @NotNull
    private static final BoolValue sneakValue = new BoolValue("Sneak", false);
    @NotNull
    private static final BoolValue collideValue = new BoolValue("Collide", false);
    @NotNull
    private static final BoolValue allDirectionsValue = new BoolValue("AllDirections", false);
    @NotNull
    private static final Value<String> allDirectionsBypassValue;
    private static boolean switchStat;
    private static boolean forceSprint;

    private Sprint() {
    }

    @NotNull
    public final BoolValue getHungryValue() {
        return hungryValue;
    }

    @NotNull
    public final BoolValue getSneakValue() {
        return sneakValue;
    }

    @NotNull
    public final BoolValue getCollideValue() {
        return collideValue;
    }

    @NotNull
    public final BoolValue getAllDirectionsValue() {
        return allDirectionsValue;
    }

    public final boolean getForceSprint() {
        return forceSprint;
    }

    public final void setForceSprint(boolean bl) {
        forceSprint = bl;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)textValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71466_p.func_175063_a(MinecraftInstance.mc.field_71439_g.func_70093_af() ? "[Sneaking (vanilla)]" : "[Sprinting (toggled)]", 2.0f, (Boolean)downValue.get() != false ? (float)StaticStorage.scaledResolution.func_78328_b() + -9.0f : 2.0f, Color.WHITE.getRGB());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        block29: {
            Intrinsics.checkNotNullParameter(event, "event");
            if (!((Boolean)allDirectionsValue.get()).booleanValue()) break block29;
            switch (allDirectionsBypassValue.get()) {
                case "NoStopSprint": {
                    forceSprint = true;
                    break;
                }
                case "SpamSprint": {
                    forceSprint = true;
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
                    break;
                }
                case "Spoof": {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
                    switchStat = true;
                }
            }
            Rotation rotation = new Rotation(MovementUtils.INSTANCE.getMovingYaw(), MinecraftInstance.mc.field_71439_g.field_70125_A);
            Rotation rotation2 = new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
            if (!(RotationUtils.getRotationDifference(rotation, rotation2) > 30.0)) return;
            switch (allDirectionsBypassValue.get()) {
                case "Rotate": {
                    RotationUtils.setTargetRotation(new Rotation(MovementUtils.INSTANCE.getMovingYaw(), MinecraftInstance.mc.field_71439_g.field_70125_A), 2);
                    return;
                }
                case "RotateSpoof": {
                    boolean bl = switchStat = !switchStat;
                    if (!switchStat) return;
                    RotationUtils.setTargetRotation(new Rotation(MovementUtils.INSTANCE.getMovingYaw(), MinecraftInstance.mc.field_71439_g.field_70125_A), 0);
                    return;
                }
                case "Toggle": {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.START_SPRINTING));
                    return;
                }
                case "Minemora": {
                    if (!MinecraftInstance.mc.field_71439_g.field_70122_E) return;
                    Rotation rotation3 = new Rotation(MovementUtils.INSTANCE.getMovingYaw(), MinecraftInstance.mc.field_71439_g.field_70125_A);
                    if (!(RotationUtils.getRotationDifference(rotation3) > 60.0)) return;
                    MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.3E-6, MinecraftInstance.mc.field_71439_g.field_70161_v);
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                }
                default: {
                    return;
                }
            }
        }
        switchStat = false;
        forceSprint = false;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        block18: {
            Intrinsics.checkNotNullParameter(event, "event");
            Packet<?> packet = event.getPacket();
            if (!(packet instanceof C0BPacketEntityAction)) break block18;
            switch (allDirectionsBypassValue.get()) {
                case "NoStopSprint": 
                case "SpamSprint": {
                    if (((C0BPacketEntityAction)packet).func_180764_b() != C0BPacketEntityAction.Action.STOP_SPRINTING) break;
                    event.cancelEvent();
                    break;
                }
                case "Toggle": {
                    if (switchStat) {
                        if (((C0BPacketEntityAction)packet).func_180764_b() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                            event.cancelEvent();
                            break;
                        }
                        switchStat = !switchStat;
                        break;
                    }
                    if (((C0BPacketEntityAction)packet).func_180764_b() == C0BPacketEntityAction.Action.START_SPRINTING) {
                        event.cancelEvent();
                        break;
                    }
                    switchStat = !switchStat;
                    break;
                }
                case "Spoof": {
                    if (!switchStat || ((C0BPacketEntityAction)packet).func_180764_b() != C0BPacketEntityAction.Action.STOP_SPRINTING && ((C0BPacketEntityAction)packet).func_180764_b() != C0BPacketEntityAction.Action.START_SPRINTING) break;
                    event.cancelEvent();
                }
            }
        }
    }

    static {
        String[] stringArray = new String[]{"Rotate", "RotateSpoof", "Toggle", "Spoof", "SpamSprint", "NoStopSprint", "Minemora", "LimitSpeed", "None"};
        allDirectionsBypassValue = new ListValue("AllDirectionsBypass", stringArray, "None").displayable(allDirectionsBypassValue.1.INSTANCE);
    }
}

