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
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.TeleportEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Phase", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0015H\u0016J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020!H\u0007J\u0010\u0010\"\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020#H\u0007J\u0010\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020+H\u0007R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\u0010X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006,"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/Phase;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/player/phases/PhaseMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "tag", "", "getTag", "()Ljava/lang/String;", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPushOut", "Lnet/ccbluex/liquidbounce/event/PushOutEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onTeleport", "Lnet/ccbluex/liquidbounce/event/TeleportEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class Phase
extends Module {
    @NotNull
    private final List<PhaseMode> modes;
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final List<Value<?>> values;

    /*
     * WARNING - void declaration
     */
    public Phase() {
        void $this$mapTo$iv$iv;
        void $this$mapTo$iv$iv2;
        void $this$map$iv;
        Collection collection;
        void $this$mapTo$iv$iv3;
        void $this$map$iv2;
        Object object = (String[])ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".phases"), PhaseMode.class);
        Phase phase = this;
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
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.phases.PhaseMode");
            }
            collection.add((PhaseMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy22 = false;
        phase.modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                PhaseMode it = (PhaseMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (PhaseMode)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)it.getModeName()));
            }
        });
        Iterable $i$f$sortedBy22 = this.modes;
        phase = this;
        boolean $i$f$map2 = false;
        destination$iv$iv = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo22 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            void it;
            PhaseMode bl = (PhaseMode)item$iv$iv;
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
        phase.modeValue = new ListValue(this, (String[])object){
            final /* synthetic */ Phase this$0;
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
        $this$toTypedArray$iv = object = CollectionsKt.toMutableList((Collection)super.getValues());
        phase = this;
        boolean bl = false;
        Iterable $this$map$iv3 = this.modes;
        boolean $i$f$map3 = false;
        Iterable $i$f$mapTo22 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo3 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void mode2;
            PhaseMode phaseMode = (PhaseMode)item$iv$iv;
            Collection collection2 = destination$iv$iv3;
            boolean bl3 = false;
            Iterable $this$forEach$iv = mode2.getValues();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                void it;
                Value value = (Value)element$iv;
                boolean bl4 = false;
                it.add(value.displayable(new Function0<Boolean>(this, (PhaseMode)mode2){
                    final /* synthetic */ Phase this$0;
                    final /* synthetic */ PhaseMode $mode;
                    {
                        this.this$0 = $receiver;
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return Phase.access$getModeValue$p(this.this$0).equals(this.$mode.getModeName());
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv3;
        phase.values = object;
    }

    private final PhaseMode getMode() {
        Object v0;
        block2: {
            for (Object t : (Iterable)this.modes) {
                PhaseMode it = (PhaseMode)t;
                boolean bl = false;
                if (!this.modeValue.equals(it.getModeName())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        PhaseMode phaseMode = v0;
        if (phaseMode == null) {
            throw new NullPointerException();
        }
        return phaseMode;
    }

    @Override
    public void onEnable() {
        this.getMode().onEnable();
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        this.getMode().onDisable();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onUpdate(event);
    }

    @EventTarget
    public final void onTeleport(@NotNull TeleportEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onTeleport(event);
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onMotion(event);
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onPacket(event);
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onMove(event);
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onBlockBB(event);
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onJump(event);
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onStep(event);
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onWorld(event);
    }

    @EventTarget
    public final void onPushOut(@NotNull PushOutEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.cancelEvent();
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

    public static final /* synthetic */ ListValue access$getModeValue$p(Phase $this) {
        return $this.modeValue;
    }
}

