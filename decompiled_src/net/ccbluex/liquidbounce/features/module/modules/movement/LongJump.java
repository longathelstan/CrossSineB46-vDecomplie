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
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="LongJump", category=ModuleCategory.MOVEMENT, autoDisable=EnumAutoDisableType.FLAG)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0007J\b\u0010/\u001a\u00020,H\u0016J\b\u00100\u001a\u00020,H\u0016J\u0010\u00101\u001a\u00020,2\u0006\u0010-\u001a\u000202H\u0007J\u0010\u00103\u001a\u00020,2\u0006\u0010-\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020,2\u0006\u0010-\u001a\u000206H\u0007J\u0010\u00107\u001a\u00020,2\u0006\u0010-\u001a\u000208H\u0007J\u0010\u00109\u001a\u00020,2\u0006\u0010-\u001a\u00020:H\u0007J\u0010\u0010;\u001a\u00020,2\u0006\u0010-\u001a\u00020<H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0006R\u001a\u0010\u001c\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\f\"\u0004\b\u001e\u0010\u000eR\u001a\u0010\u001f\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\f\"\u0004\b!\u0010\u000eR\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u001e\u0010&\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0'X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*\u00a8\u0006="}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/LongJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoDisableValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getAutoDisableValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "autoJumpValue", "getAutoJumpValue", "hasJumped", "", "getHasJumped", "()Z", "setHasJumped", "(Z)V", "jumped", "getJumped", "setJumped", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/longjumps/LongJumpMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "motionResetValue", "getMotionResetValue", "needReset", "getNeedReset", "setNeedReset", "no", "getNo", "setNo", "tag", "", "getTag", "()Ljava/lang/String;", "values", "", "Lnet/ccbluex/liquidbounce/features/value/Value;", "getValues", "()Ljava/util/List;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class LongJump
extends Module {
    @NotNull
    private final List<LongJumpMode> modes;
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final BoolValue motionResetValue;
    @NotNull
    private final BoolValue autoJumpValue;
    @NotNull
    private final BoolValue autoDisableValue;
    private boolean jumped;
    private boolean hasJumped;
    private boolean no;
    private boolean needReset;
    @NotNull
    private final List<Value<?>> values;

    /*
     * WARNING - void declaration
     */
    public LongJump() {
        void $this$mapTo$iv$iv;
        void $this$mapTo$iv$iv2;
        void $this$map$iv;
        Collection collection;
        void $this$mapTo$iv$iv3;
        void $this$map$iv2;
        Object object = (String[])ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".longjumps"), LongJumpMode.class);
        LongJump longJump = this;
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
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.longjumps.LongJumpMode");
            }
            collection.add((LongJumpMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy22 = false;
        longJump.modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                LongJumpMode it = (LongJumpMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (LongJumpMode)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)it.getModeName()));
            }
        });
        Iterable $i$f$sortedBy22 = this.modes;
        longJump = this;
        boolean $i$f$map2 = false;
        destination$iv$iv = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo22 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            void it;
            LongJumpMode bl = (LongJumpMode)item$iv$iv;
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
        longJump.modeValue = new ListValue(this, (String[])object){
            final /* synthetic */ LongJump this$0;
            {
                this.this$0 = $receiver;
                super("Mode", $super_call_param$1, "NCP");
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
        this.motionResetValue = new BoolValue("MotionReset", true);
        this.autoJumpValue = new BoolValue("AutoJump", true);
        this.autoDisableValue = new BoolValue("AutoDisable", true);
        this.needReset = true;
        $this$toTypedArray$iv = object = CollectionsKt.toMutableList((Collection)super.getValues());
        longJump = this;
        boolean bl = false;
        Iterable $this$map$iv3 = this.modes;
        boolean $i$f$map3 = false;
        Iterable $i$f$mapTo22 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo3 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void mode2;
            LongJumpMode longJumpMode = (LongJumpMode)item$iv$iv;
            Collection collection2 = destination$iv$iv3;
            boolean bl3 = false;
            Iterable $this$forEach$iv = mode2.getValues();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                void it;
                Value value = (Value)element$iv;
                boolean bl4 = false;
                it.add(value.displayable(new Function0<Boolean>(this, (LongJumpMode)mode2){
                    final /* synthetic */ LongJump this$0;
                    final /* synthetic */ LongJumpMode $mode;
                    {
                        this.this$0 = $receiver;
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return LongJump.access$getModeValue$p(this.this$0).equals(this.$mode.getModeName());
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv3;
        longJump.values = object;
    }

    private final LongJumpMode getMode() {
        Object v0;
        block2: {
            for (Object t : (Iterable)this.modes) {
                LongJumpMode it = (LongJumpMode)t;
                boolean bl = false;
                if (!this.modeValue.equals(it.getModeName())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        LongJumpMode longJumpMode = v0;
        if (longJumpMode == null) {
            throw new NullPointerException();
        }
        return longJumpMode;
    }

    @NotNull
    public final BoolValue getMotionResetValue() {
        return this.motionResetValue;
    }

    @NotNull
    public final BoolValue getAutoJumpValue() {
        return this.autoJumpValue;
    }

    @NotNull
    public final BoolValue getAutoDisableValue() {
        return this.autoDisableValue;
    }

    public final boolean getJumped() {
        return this.jumped;
    }

    public final void setJumped(boolean bl) {
        this.jumped = bl;
    }

    public final boolean getHasJumped() {
        return this.hasJumped;
    }

    public final void setHasJumped(boolean bl) {
        this.hasJumped = bl;
    }

    public final boolean getNo() {
        return this.no;
    }

    public final void setNo(boolean bl) {
        this.no = bl;
    }

    public final boolean getNeedReset() {
        return this.needReset;
    }

    public final void setNeedReset(boolean bl) {
        this.needReset = bl;
    }

    @Override
    public void onEnable() {
        this.jumped = false;
        this.hasJumped = false;
        this.no = false;
        this.needReset = true;
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
        if (((Boolean)this.motionResetValue.get()).booleanValue() && this.needReset) {
            MovementUtils.INSTANCE.resetMotion(true);
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        this.getMode().onUpdate(event);
        if (!this.no && ((Boolean)this.autoJumpValue.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.field_70122_E && MovementUtils.INSTANCE.isMoving()) {
            this.jumped = true;
            if (this.hasJumped && ((Boolean)this.autoDisableValue.get()).booleanValue()) {
                this.setState(false);
                return;
            }
            MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
            this.hasJumped = true;
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        this.getMode().onMotion(event);
        if (event.getEventState() != EventState.PRE) {
            return;
        }
        this.getMode().onPreMotion(event);
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        this.getMode().onPacket(event);
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        this.getMode().onMove(event);
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        this.getMode().onBlockBB(event);
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        this.getMode().onJump(event);
        this.jumped = true;
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        this.getMode().onStep(event);
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

    public static final /* synthetic */ ListValue access$getModeValue$p(LongJump $this) {
        return $this.modeValue;
    }
}

