/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.flights;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020)H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068DX\u0084\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u0003X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u001e\u0010\r\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f0\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006*"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "modeName", "", "(Ljava/lang/String;)V", "flight", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Flight;", "getFlight", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/Flight;", "getModeName", "()Ljava/lang/String;", "valuePrefix", "getValuePrefix", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public abstract class FlightMode
extends MinecraftInstance {
    @NotNull
    private final String modeName;
    @NotNull
    private final String valuePrefix;

    public FlightMode(@NotNull String modeName) {
        Intrinsics.checkNotNullParameter(modeName, "modeName");
        this.modeName = modeName;
        this.valuePrefix = Intrinsics.stringPlus(this.modeName, "-");
    }

    @NotNull
    public final String getModeName() {
        return this.modeName;
    }

    @NotNull
    protected final String getValuePrefix() {
        return this.valuePrefix;
    }

    @NotNull
    protected final Flight getFlight() {
        Flight flight = CrossSine.INSTANCE.getModuleManager().get(Flight.class);
        Intrinsics.checkNotNull(flight);
        return flight;
    }

    @NotNull
    public List<Value<?>> getValues() {
        return ClassUtils.INSTANCE.getValues(this.getClass(), this);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onTick(@NotNull TickEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }
}

