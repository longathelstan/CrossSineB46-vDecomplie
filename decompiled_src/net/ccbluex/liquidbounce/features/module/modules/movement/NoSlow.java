/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

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
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.noslows.NoSlowMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="NoSlow", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020(H\u0007R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0006R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001b0\u001aX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0010\u00a8\u0006)"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoSlow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canNoslow", "", "getCanNoslow", "()Z", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/NoSlowMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/noslows/NoSlowMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "getModes", "()Ljava/util/List;", "onlyKillAura", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "shouldSprint", "getShouldSprint", "tag", "", "getTag", "()Ljava/lang/String;", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onSlowDown", "Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class NoSlow
extends Module {
    @NotNull
    private final List<NoSlowMode> modes;
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final BoolValue onlyKillAura;
    @NotNull
    private final List<Value<?>> values;

    /*
     * WARNING - void declaration
     */
    public NoSlow() {
        void $this$mapTo$iv$iv;
        void $this$mapTo$iv$iv2;
        void $this$map$iv;
        Collection collection;
        void $this$mapTo$iv$iv3;
        void $this$map$iv2;
        Object object = (String[])ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".noslows"), NoSlowMode.class);
        NoSlow noSlow = this;
        boolean $i$f$map = false;
        void var3_5 = $this$map$iv2;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv3) {
            void it;
            Class clazz = (Class)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            Object t = it.newInstance();
            if (t == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.noslows.NoSlowMode");
            }
            collection.add((NoSlowMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy22 = false;
        noSlow.modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                NoSlowMode it = (NoSlowMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (NoSlowMode)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)it.getModeName()));
            }
        });
        Iterable $i$f$sortedBy22 = this.modes;
        noSlow = this;
        boolean $i$f$map2 = false;
        destination$iv$iv = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo22 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            void it;
            NoSlowMode bl = (NoSlowMode)item$iv$iv;
            collection = destination$iv$iv2;
            boolean bl2 = false;
            collection.add(it.getModeName());
        }
        Object $this$toTypedArray$iv = (List)destination$iv$iv2;
        boolean $i$f$toTypedArray = false;
        Object thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray = thisCollection$iv.toArray(new String[0]);
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        object = stringArray;
        noSlow.modeValue = new ListValue(this, (String[])object){
            final /* synthetic */ NoSlow this$0;
            {
                this.this$0 = $receiver;
                super("Mode", $super_call_param$1, "Vanilla");
            }

            protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                if (this.this$0.getState()) {
                    this.this$0.onDisable();
                }
            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                if (this.this$0.getState()) {
                    this.this$0.onEnable();
                }
            }
        };
        this.onlyKillAura = new BoolValue("OnlyKillAura", false);
        $this$toTypedArray$iv = object = CollectionsKt.toMutableList((Collection)super.getValues());
        noSlow = this;
        boolean bl = false;
        Iterable $this$map$iv3 = this.getModes();
        boolean $i$f$map3 = false;
        Iterable $i$f$mapTo22 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo3 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void mode2;
            NoSlowMode noSlowMode = (NoSlowMode)item$iv$iv;
            Collection collection2 = destination$iv$iv3;
            boolean bl3 = false;
            Iterable $this$forEach$iv = mode2.getValues();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                void it;
                Value value = (Value)element$iv;
                boolean bl4 = false;
                it.add(value.displayable(new Function0<Boolean>(this, (NoSlowMode)mode2){
                    final /* synthetic */ NoSlow this$0;
                    final /* synthetic */ NoSlowMode $mode;
                    {
                        this.this$0 = $receiver;
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return NoSlow.access$getModeValue$p(this.this$0).equals(this.$mode.getModeName());
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv3;
        noSlow.values = object;
    }

    @NotNull
    public final List<NoSlowMode> getModes() {
        return this.modes;
    }

    private final NoSlowMode getMode() {
        Object v0;
        block2: {
            for (Object t : (Iterable)this.modes) {
                NoSlowMode it = (NoSlowMode)t;
                boolean bl = false;
                if (!this.modeValue.equals(it.getModeName())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        NoSlowMode noSlowMode = v0;
        if (noSlowMode == null) {
            throw new NullPointerException();
        }
        return noSlowMode;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getCanNoslow()) {
            return;
        }
        this.getMode().onUpdate(event);
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getCanNoslow()) {
            return;
        }
        if (event.isPre()) {
            this.getMode().onPreMotion(event);
        }
        if (event.isPost()) {
            this.getMode().onPostMotion(event);
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getCanNoslow()) {
            return;
        }
        this.getMode().onPacket(event);
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getCanNoslow()) {
            return;
        }
        this.getMode().onMove(event);
    }

    @EventTarget
    public final void onSlowDown(@NotNull SlowDownEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getCanNoslow()) {
            return;
        }
        float speed = this.getMode().slow();
        event.setForward(speed);
        event.setStrafe(speed);
    }

    private final boolean getCanNoslow() {
        return (Boolean)this.onlyKillAura.get() == false || KillAura.INSTANCE.getState() && KillAura.INSTANCE.getCurrentTarget() != null || SilentAura.INSTANCE.getState() && SilentAura.INSTANCE.getTarget() != null;
    }

    public final boolean getShouldSprint() {
        return this.getMode().getSprint();
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }

    @Override
    @NotNull
    public List<Value<?>> getValues() {
        return this.values;
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(NoSlow $this) {
        return $this.modeValue;
    }
}

