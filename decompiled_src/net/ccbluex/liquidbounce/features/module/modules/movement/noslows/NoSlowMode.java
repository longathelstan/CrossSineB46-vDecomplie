/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.noslows;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020!H\u0016J\u0010\u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020!2\u0006\u0010$\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020!2\u0006\u0010$\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020!2\u0006\u0010$\u001a\u00020)H\u0016J\u0010\u0010+\u001a\u00020!2\u0006\u0010$\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020.H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068DX\u0084\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068DX\u0084\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068DX\u0084\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108DX\u0084\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148DX\u0084\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\bR\u0014\u0010\u0019\u001a\u00020\u0003X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000eR\u001e\u0010\u001b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001d0\u001c8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006/"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/NoSlowMode;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "modeName", "", "(Ljava/lang/String;)V", "holdBow", "", "getHoldBow", "()Z", "holdConsume", "getHoldConsume", "holdSword", "getHoldSword", "getModeName", "()Ljava/lang/String;", "noslow", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoSlow;", "getNoslow", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoSlow;", "speed", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Speed;", "getSpeed", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/Speed;", "sprint", "getSprint", "valuePrefix", "getValuePrefix", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "onDisable", "", "onEnable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPostMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPreMotion", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "slow", "", "CrossSine"})
public abstract class NoSlowMode
extends MinecraftInstance {
    @NotNull
    private final String modeName;
    @NotNull
    private final String valuePrefix;

    public NoSlowMode(@NotNull String modeName) {
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
    protected final NoSlow getNoslow() {
        NoSlow noSlow = CrossSine.INSTANCE.getModuleManager().get(NoSlow.class);
        Intrinsics.checkNotNull(noSlow);
        return noSlow;
    }

    protected final boolean getHoldConsume() {
        return MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemFood || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemPotion && !ItemPotion.func_77831_g((int)MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77960_j()) || MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBucketMilk);
    }

    protected final boolean getHoldBow() {
        return MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBow;
    }

    protected final boolean getHoldSword() {
        return MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword;
    }

    @NotNull
    protected final Speed getSpeed() {
        Speed speed = CrossSine.INSTANCE.getModuleManager().get(Speed.class);
        Intrinsics.checkNotNull(speed);
        return speed;
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

    public void onPreMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onPostMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
    }

    public boolean getSprint() {
        return true;
    }

    public float slow() {
        return 0.2f;
    }
}

