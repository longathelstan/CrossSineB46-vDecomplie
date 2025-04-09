/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="HitSelect", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\b\u0010\u0019\u001a\u00020\u0016H\u0016J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001dH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/HitSelect;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "cancelClick", "", "chance", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "hitCount", "", "hurtTime", "mode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getMode", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timerMS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "getCancelClick", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class HitSelect
extends Module {
    @NotNull
    public static final HitSelect INSTANCE = new HitSelect();
    @NotNull
    private static final ListValue mode;
    @NotNull
    private static final IntegerValue chance;
    private static int hurtTime;
    private static boolean cancelClick;
    private static int hitCount;
    @NotNull
    private static final TimerMS timerMS;

    private HitSelect() {
    }

    @NotNull
    public final ListValue getMode() {
        return mode;
    }

    @Override
    public void onDisable() {
        hurtTime = 0;
        hitCount = 0;
        cancelClick = false;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        timerMS.reset();
        if (hurtTime == 0) {
            hurtTime = 10;
            int n = hitCount;
            hitCount = n + 1;
        }
        if (this.getCancelClick() && mode.equals("Active")) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public final void onPreUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (hurtTime > 0) {
            hurtTime += -1;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70737_aN >= 9) {
            hitCount = 0;
        }
        if (timerMS.hasTimePassed(5000L)) {
            hurtTime = 0;
            hitCount = 0;
        }
        cancelClick = mode.equals("Active") ? hurtTime > 1 : hurtTime > 0;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C0APacketAnimation && this.getCancelClick() && mode.equals("Active")) {
            event.cancelEvent();
        }
        if (event.getPacket() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)event.getPacket()).func_149412_c() == MinecraftInstance.mc.field_71439_g.func_145782_y()) {
            hitCount = 0;
        }
    }

    public final boolean getCancelClick() {
        return cancelClick && ((Number)chance.get()).intValue() >= RandomUtils.nextInt(1, 100) && hitCount >= 2;
    }

    @Override
    @Nullable
    public String getTag() {
        return (String)mode.get() + ' ' + ((Number)chance.get()).intValue() + '%';
    }

    static {
        String[] stringArray = new String[]{"Pause", "Active"};
        mode = new ListValue("Mode", stringArray, "Pause");
        chance = new IntegerValue("Chance", 100, 1, 100);
        timerMS = new TimerMS();
    }
}

