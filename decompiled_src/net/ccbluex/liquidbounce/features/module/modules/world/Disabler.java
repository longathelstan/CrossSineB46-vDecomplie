/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Disabler", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\b\u0010\u001f\u001a\u00020\nH\u0016J\b\u0010 \u001a\u00020\nH\u0016J\u0010\u0010!\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020&H\u0007J\u0010\u0010'\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020*H\u0007J\u0010\u0010+\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020,H\u0007J\u0010\u0010-\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020\n2\u0006\u0010\u001d\u001a\u000200H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u001e\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R&\u0010\u0013\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u0014j\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e`\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u0017X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010\u00a8\u00061"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/Disabler;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "debugValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "mode", "Ljava/util/LinkedList;", "mode2", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "modeList", "", "Lkotlin/Unit;", "modeValue", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getModeValue", "()Ljava/util/List;", "modes", "getModes", "settings", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "values", "", "getValues", "debugMessage", "str", "", "onBlockBB", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class Disabler
extends Module {
    @NotNull
    public static final Disabler INSTANCE;
    @NotNull
    private static final BoolValue debugValue;
    @NotNull
    private static final LinkedList<BoolValue> mode;
    @NotNull
    private static final LinkedList<DisablerMode> mode2;
    @NotNull
    private static final ArrayList<Value<?>> settings;
    @NotNull
    private static final Unit modeList;
    @NotNull
    private static final List<DisablerMode> modes;
    @NotNull
    private static final List<Value<?>> values;

    private Disabler() {
    }

    @NotNull
    public final List<DisablerMode> getModes() {
        return modes;
    }

    @Override
    public void onEnable() {
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onEnable();
        }
    }

    @Override
    public void onDisable() {
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onDisable();
        }
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onUpdate(event);
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onRender2D(event);
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onMotion(event);
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onPacket(event);
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onMove(event);
        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onBlockBB(event);
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onJump(event);
        }
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onStep(event);
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Iterable $this$forEach$iv = modes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it = (DisablerMode)element$iv;
            boolean bl = false;
            Value<?> value = INSTANCE.getValue(it.getModeName());
            if (!(value == null ? false : Intrinsics.areEqual(value.getValue(), true))) continue;
            it.onWorld(event);
        }
    }

    public final void debugMessage(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        if (((Boolean)debugValue.get()).booleanValue()) {
            this.alert(Intrinsics.stringPlus("\u00a77[\u00a7c\u00a7lDisabler\u00a77] \u00a7b", str));
        }
    }

    private final List<Value<?>> getModeValue() {
        return settings;
    }

    @Override
    @NotNull
    public List<Value<?>> getValues() {
        return values;
    }

    /*
     * WARNING - void declaration
     */
    static {
        void $this$mapTo$iv$iv;
        Object modulesMode2;
        void it;
        Collection collection;
        Object item$iv$iv;
        Iterable $this$mapTo$iv$iv2;
        INSTANCE = new Disabler();
        debugValue = new BoolValue("Debug", false);
        mode = new LinkedList();
        mode2 = new LinkedList();
        Object object = new Value[]{debugValue};
        settings = CollectionsKt.arrayListOf(object);
        Iterable $this$map$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".disablers"), DisablerMode.class);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        Iterator iterator2 = $this$mapTo$iv$iv2.iterator();
        while (iterator2.hasNext()) {
            item$iv$iv = iterator2.next();
            Class clazz = (Class)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            Object t = it.newInstance();
            if (t == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode");
            }
            collection.add((DisablerMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        Iterable $this$forEach$iv = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                DisablerMode it = (DisablerMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (DisablerMode)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)it.getModeName()));
            }
        });
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DisablerMode it2 = (DisablerMode)element$iv;
            boolean bl = false;
            item$iv$iv = it2.getModeName();
            modulesMode2 = new BoolValue(it2, (String)item$iv$iv){
                final /* synthetic */ DisablerMode $it;
                {
                    this.$it = $it;
                    super($super_call_param$1, false);
                }

                protected void onChange(boolean oldValue, boolean newValue) {
                    if (newValue && !oldValue) {
                        this.$it.onEnable();
                    } else if (!newValue && oldValue) {
                        this.$it.onDisable();
                    }
                }
            };
            settings.add((Value<?>)modulesMode2);
            mode.add((BoolValue)modulesMode2);
            mode2.add(it2);
        }
        modeList = Unit.INSTANCE;
        $this$map$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".disablers"), DisablerMode.class);
        $i$f$map = false;
        $this$mapTo$iv$iv2 = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        Object bl = $this$mapTo$iv$iv2.iterator();
        while (bl.hasNext()) {
            item$iv$iv = bl.next();
            modulesMode2 = (Class)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl2 = false;
            Object t = it.newInstance();
            if (t == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode");
            }
            collection.add((DisablerMode)t);
        }
        $this$sortedBy$iv = (List)destination$iv$iv;
        $i$f$sortedBy = false;
        modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                DisablerMode it = (DisablerMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (DisablerMode)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)it.getModeName()));
            }
        });
        Object it3 = object = CollectionsKt.toMutableList((Collection)INSTANCE.getModeValue());
        boolean bl3 = false;
        Iterable $this$map$iv2 = INSTANCE.getModes();
        boolean $i$f$map2 = false;
        bl = $this$map$iv2;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv2 : $this$mapTo$iv$iv) {
            void mode2;
            DisablerMode disablerMode = (DisablerMode)item$iv$iv2;
            Collection collection2 = destination$iv$iv2;
            boolean bl4 = false;
            Iterable $this$forEach$iv2 = mode2.getValues();
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv2) {
                Value value = (Value)element$iv;
                boolean bl5 = false;
                it3.add(value.displayable(new Function0<Boolean>((DisablerMode)mode2){
                    final /* synthetic */ DisablerMode $mode;
                    {
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        Value<?> value = Disabler.INSTANCE.getValue(this.$mode.getModeName());
                        return value == null ? false : Intrinsics.areEqual(value.getValue(), true);
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv2;
        values = object;
    }
}

