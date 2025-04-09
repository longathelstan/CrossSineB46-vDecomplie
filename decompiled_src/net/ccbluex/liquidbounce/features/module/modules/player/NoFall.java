/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="NoFall", category=ModuleCategory.PLAYER, autoDisable=EnumAutoDisableType.FLAG)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u0017H\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\u0010\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020*H\u0007J\u0010\u0010+\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020,H\u0007R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00130\u0012X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b\u00a8\u0006-"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/NoFall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "noVoid", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "wasTimer", "", "getWasTimer", "()Z", "setWasTimer", "(Z)V", "checkVoid", "onDisable", "", "onEnable", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class NoFall
extends Module {
    @NotNull
    public static final NoFall INSTANCE;
    @NotNull
    private static final List<NoFallMode> modes;
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final BoolValue noVoid;
    private static boolean wasTimer;
    @NotNull
    private static final List<Value<?>> values;

    private NoFall() {
    }

    @NotNull
    public final NoFallMode getMode() {
        Object v0;
        block2: {
            for (Object t : (Iterable)modes) {
                NoFallMode it = (NoFallMode)t;
                boolean bl = false;
                if (!modeValue.equals(it.getModeName())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        NoFallMode noFallMode = v0;
        if (noFallMode == null) {
            throw new NullPointerException();
        }
        return noFallMode;
    }

    public final boolean getWasTimer() {
        return wasTimer;
    }

    public final void setWasTimer(boolean bl) {
        wasTimer = bl;
    }

    @Override
    public void onEnable() {
        wasTimer = false;
        this.getMode().onEnable();
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        MinecraftInstance.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05f);
        MinecraftInstance.mc.field_71439_g.field_70145_X = false;
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.02f;
        this.getMode().onDisable();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        block8: {
            block7: {
                Intrinsics.checkNotNullParameter(event, "event");
                if (wasTimer) {
                    MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    wasTimer = false;
                }
                this.getMode().onUpdate(event);
                if (!this.getState()) break block7;
                FreeCam freeCam = CrossSine.INSTANCE.getModuleManager().get(FreeCam.class);
                Intrinsics.checkNotNull(freeCam);
                if (!freeCam.getState()) break block8;
            }
            return;
        }
        if (MinecraftInstance.mc.field_71439_g.func_175149_v() || MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75101_c || MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75102_a) {
            return;
        }
        AxisAlignedBB axisAlignedBB = MinecraftInstance.mc.field_71439_g.func_174813_aQ();
        Intrinsics.checkNotNullExpressionValue(axisAlignedBB, "mc.thePlayer.entityBoundingBox");
        if (BlockUtils.collideBlock(axisAlignedBB, onUpdate.1.INSTANCE) || BlockUtils.collideBlock(new AxisAlignedBB(MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72336_d, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72337_e, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72334_f, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72340_a, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b - 0.01, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72339_c), onUpdate.2.INSTANCE)) {
            return;
        }
        if (this.checkVoid() && ((Boolean)noVoid.get()).booleanValue()) {
            return;
        }
        this.getMode().onNoFall(event);
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.checkVoid() && ((Boolean)noVoid.get()).booleanValue()) {
            return;
        }
        this.getMode().onMotion(event);
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.checkVoid() && ((Boolean)noVoid.get()).booleanValue()) {
            return;
        }
        this.getMode().onPacket(event);
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onRender2D(event);
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.checkVoid() && ((Boolean)noVoid.get()).booleanValue()) {
            return;
        }
        this.getMode().onMove(event);
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.checkVoid() && ((Boolean)noVoid.get()).booleanValue()) {
            return;
        }
        this.getMode().onJump(event);
    }

    private final boolean checkVoid() {
        int i = (int)(-(MinecraftInstance.mc.field_71439_g.field_70163_u - 1.4857625));
        boolean dangerous = true;
        while (i <= 0) {
            dangerous = MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(MinecraftInstance.mc.field_71439_g.field_70159_w * 0.5, (double)i, MinecraftInstance.mc.field_71439_g.field_70179_y * 0.5)).isEmpty();
            int n = i;
            i = n + 1;
            if (dangerous) continue;
            break;
        }
        return dangerous;
    }

    @Override
    @NotNull
    public List<Value<?>> getValues() {
        return values;
    }

    @Override
    @Nullable
    public String getTag() {
        return (String)modeValue.get();
    }

    public static final /* synthetic */ ListValue access$getModeValue$p() {
        return modeValue;
    }

    /*
     * WARNING - void declaration
     */
    static {
        void $this$mapTo$iv$iv;
        void $this$mapTo$iv$iv2;
        Collection collection;
        void $this$mapTo$iv$iv3;
        INSTANCE = new NoFall();
        Iterable $this$map$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".nofalls"), NoFallMode.class);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Iterable destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv3) {
            void it;
            Class clazz = (Class)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            Object t = it.newInstance();
            if (t == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode");
            }
            collection.add((NoFallMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                NoFallMode it = (NoFallMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (NoFallMode)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)it.getModeName()));
            }
        });
        Iterable $this$map$iv2 = modes;
        boolean $i$f$map2 = false;
        destination$iv$iv = $this$map$iv2;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        boolean $i$f$mapTo22 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            void it;
            NoFallMode bl = (NoFallMode)item$iv$iv;
            collection = destination$iv$iv2;
            boolean bl2 = false;
            collection.add(it.getModeName());
        }
        Collection $this$toTypedArray$iv = (List)destination$iv$iv2;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray = thisCollection$iv.toArray(new String[0]);
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        Object object = stringArray;
        modeValue = new ListValue((String[])object){

            protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                if (NoFall.INSTANCE.getState()) {
                    NoFall.INSTANCE.onDisable();
                }
            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                if (NoFall.INSTANCE.getState()) {
                    NoFall.INSTANCE.onEnable();
                }
            }
        };
        noVoid = new BoolValue("NoVoid", false);
        Object it = object = CollectionsKt.toMutableList((Collection)super.getValues());
        boolean bl = false;
        Iterable $this$map$iv3 = modes;
        boolean $i$f$map3 = false;
        Iterable $i$f$mapTo22 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo3 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void mode2;
            NoFallMode noFallMode = (NoFallMode)item$iv$iv;
            Collection collection2 = destination$iv$iv3;
            boolean bl3 = false;
            Iterable $this$forEach$iv = mode2.getValues();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Value value = (Value)element$iv;
                boolean bl4 = false;
                it.add(value.displayable(new Function0<Boolean>((NoFallMode)mode2){
                    final /* synthetic */ NoFallMode $mode;
                    {
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return NoFall.access$getModeValue$p().equals(this.$mode.getModeName());
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv3;
        values = object;
    }
}

