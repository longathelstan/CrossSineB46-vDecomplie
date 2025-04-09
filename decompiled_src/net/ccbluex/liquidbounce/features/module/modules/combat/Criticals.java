/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
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
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S0BPacketAnimation;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Criticals", category=ModuleCategory.COMBAT, autoDisable=EnumAutoDisableType.FLAG)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0007J\u0010\u00105\u001a\u0002022\u0006\u00103\u001a\u000206H\u0007J\b\u00107\u001a\u000202H\u0016J\b\u00108\u001a\u000202H\u0016J\u0010\u00109\u001a\u0002022\u0006\u00103\u001a\u00020:H\u0007J\u0010\u0010;\u001a\u0002022\u0006\u00103\u001a\u00020<H\u0007J\u0010\u0010=\u001a\u0002022\u0006\u00103\u001a\u00020>H\u0007J\u0010\u0010?\u001a\u0002022\u0006\u00103\u001a\u00020@H\u0007J\u0010\u0010A\u001a\u0002022\u0006\u00103\u001a\u00020BH\u0007J\u0010\u0010C\u001a\u0002022\u0006\u00103\u001a\u00020DH\u0007J,\u0010E\u001a\u0002022\b\b\u0002\u0010F\u001a\u00020G2\b\b\u0002\u0010H\u001a\u00020G2\b\b\u0002\u0010I\u001a\u00020G2\u0006\u0010J\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00140\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010$\u001a\u00020%8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010'R\u001a\u0010(\u001a\u00020\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001e\u0010-\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e0.X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100\u00a8\u0006K"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Criticals;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "CritTiming", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "antiDesync", "", "getAntiDesync", "()Z", "setAntiDesync", "(Z)V", "debugValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "flagTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "hurtTimeValue", "lookValue", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "modeValue", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "msTimer", "s08DelayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "s08FlagValue", "getS08FlagValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "syncTimer", "tag", "", "getTag", "()Ljava/lang/String;", "target", "getTarget", "()I", "setTarget", "(I)V", "values", "", "getValues", "()Ljava/util/List;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onBlockBB", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "sendCriticalPacket", "xOffset", "", "yOffset", "zOffset", "ground", "CrossSine"})
public final class Criticals
extends Module {
    @NotNull
    private final List<CriticalMode> modes;
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final IntegerValue delayValue;
    @NotNull
    private final BoolValue s08FlagValue;
    @NotNull
    private final Value<Integer> s08DelayValue;
    @NotNull
    private final IntegerValue hurtTimeValue;
    @NotNull
    private final ListValue CritTiming;
    @NotNull
    private final BoolValue lookValue;
    @NotNull
    private final BoolValue debugValue;
    @NotNull
    private final MSTimer msTimer;
    @NotNull
    private final MSTimer flagTimer;
    @NotNull
    private final MSTimer syncTimer;
    private int target;
    private boolean antiDesync;
    @NotNull
    private final List<Value<?>> values;

    /*
     * WARNING - void declaration
     */
    public Criticals() {
        void $this$mapTo$iv$iv;
        void $this$mapTo$iv$iv2;
        void $this$map$iv;
        Collection collection;
        void $this$mapTo$iv$iv3;
        void $this$map$iv2;
        Object object = (String[])ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".criticals"), CriticalMode.class);
        Criticals criticals = this;
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
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode");
            }
            collection.add((CriticalMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy22 = false;
        criticals.modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                CriticalMode it = (CriticalMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (CriticalMode)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)it.getModeName()));
            }
        });
        Iterable $i$f$sortedBy22 = this.modes;
        criticals = this;
        boolean $i$f$map2 = false;
        destination$iv$iv = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo22 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            void it;
            CriticalMode bl = (CriticalMode)item$iv$iv;
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
        criticals.modeValue = new ListValue(this, (String[])object){
            final /* synthetic */ Criticals this$0;
            {
                this.this$0 = $receiver;
                super("Mode", $super_call_param$1, "Packet");
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
        this.delayValue = new IntegerValue("Delay", 0, 0, 500);
        this.s08FlagValue = new BoolValue("FlagPause", true);
        this.s08DelayValue = new IntegerValue("FlagPause-Time", 100, 100, 5000).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Criticals this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getS08FlagValue().get();
            }
        });
        this.hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
        object = new String[]{"Always", "OnGround", "OffGround"};
        this.CritTiming = new ListValue("CritTiming", (String[])object, "Always");
        this.lookValue = new BoolValue("UseC06Packet", false);
        this.debugValue = new BoolValue("DebugMessage", false);
        this.msTimer = new MSTimer();
        this.flagTimer = new MSTimer();
        this.syncTimer = new MSTimer();
        $this$toTypedArray$iv = object = CollectionsKt.toMutableList((Collection)super.getValues());
        criticals = this;
        boolean bl = false;
        Iterable $this$map$iv3 = this.modes;
        boolean $i$f$map3 = false;
        Iterable $i$f$mapTo22 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo3 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void mode2;
            CriticalMode criticalMode = (CriticalMode)item$iv$iv;
            Collection collection2 = destination$iv$iv3;
            boolean bl3 = false;
            Iterable $this$forEach$iv = mode2.getValues();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                void it;
                Value value = (Value)element$iv;
                boolean bl4 = false;
                it.add(value.displayable(new Function0<Boolean>(this, (CriticalMode)mode2){
                    final /* synthetic */ Criticals this$0;
                    final /* synthetic */ CriticalMode $mode;
                    {
                        this.this$0 = $receiver;
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return this.this$0.getModeValue().equals(this.$mode.getModeName());
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv3;
        criticals.values = object;
    }

    private final CriticalMode getMode() {
        Object v0;
        block2: {
            for (Object t : (Iterable)this.modes) {
                CriticalMode it = (CriticalMode)t;
                boolean bl = false;
                if (!this.getModeValue().equals(it.getModeName())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        CriticalMode criticalMode = v0;
        if (criticalMode == null) {
            throw new NullPointerException();
        }
        return criticalMode;
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final BoolValue getS08FlagValue() {
        return this.s08FlagValue;
    }

    public final int getTarget() {
        return this.target;
    }

    public final void setTarget(int n) {
        this.target = n;
    }

    public final boolean getAntiDesync() {
        return this.antiDesync;
    }

    public final void setAntiDesync(boolean bl) {
        this.antiDesync = bl;
    }

    @Override
    public void onEnable() {
        this.target = 0;
        this.msTimer.reset();
        this.flagTimer.reset();
        this.syncTimer.reset();
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
        if (!this.getState()) {
            return;
        }
        String string = ((String)this.CritTiming.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "always": {
                break;
            }
            case "onground": {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                return;
            }
            case "offground": {
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                return;
            }
        }
        this.getMode().onUpdate(event);
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        String string = ((String)this.CritTiming.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "always": {
                break;
            }
            case "onground": {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                return;
            }
            case "offground": {
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                return;
            }
        }
        this.getMode().onMotion(event);
        if (event.getEventState() != EventState.PRE) {
            return;
        }
        this.getMode().onPreMotion(event);
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        String string = ((String)this.CritTiming.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "always": {
                break;
            }
            case "onground": {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                return;
            }
            case "offground": {
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                return;
            }
        }
        if (event.getTargetEntity() instanceof EntityLivingBase) {
            Entity entity = event.getTargetEntity();
            this.target = ((EntityLivingBase)entity).func_145782_y();
            if (!MinecraftInstance.mc.field_71439_g.field_70122_E || MinecraftInstance.mc.field_71439_g.func_70617_f_() || MinecraftInstance.mc.field_71439_g.field_70134_J || MinecraftInstance.mc.field_71439_g.func_70090_H() || MinecraftInstance.mc.field_71439_g.func_180799_ab() || MinecraftInstance.mc.field_71439_g.field_70154_o != null || ((EntityLivingBase)entity).field_70737_aN > ((Number)this.hurtTimeValue.get()).intValue() || !this.msTimer.hasTimePassed(((Number)this.delayValue.get()).intValue())) {
                return;
            }
            if (((Boolean)this.s08FlagValue.get()).booleanValue() && !this.flagTimer.hasTimePassed(((Number)this.s08DelayValue.get()).intValue())) {
                return;
            }
            this.antiDesync = true;
            this.getMode().onAttack(event);
            this.msTimer.reset();
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return;
        }
        Packet<?> packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook) {
            this.flagTimer.reset();
            this.antiDesync = false;
            if (((Boolean)this.debugValue.get()).booleanValue()) {
                this.alert("FLAG");
            }
        }
        if (packet instanceof C03PacketPlayer && (MovementUtils.INSTANCE.isMoving() || this.syncTimer.hasTimePassed(1000L) || this.msTimer.hasTimePassed(((Number)this.delayValue.get()).intValue() / 5 + 75))) {
            this.antiDesync = false;
        }
        if (((Boolean)this.s08FlagValue.get()).booleanValue() && !this.flagTimer.hasTimePassed(((Number)this.s08DelayValue.get()).intValue())) {
            return;
        }
        this.getMode().onPacket(event);
        if (packet instanceof S0BPacketAnimation && ((Boolean)this.debugValue.get()).booleanValue() && ((S0BPacketAnimation)packet).func_148977_d() == 4 && ((S0BPacketAnimation)packet).func_148978_c() == this.target) {
            this.alert("CRIT");
        }
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

    public final void sendCriticalPacket(double xOffset, double yOffset, double zOffset, boolean ground) {
        double x = MinecraftInstance.mc.field_71439_g.field_70165_t + xOffset;
        double y = MinecraftInstance.mc.field_71439_g.field_70163_u + yOffset;
        double z = MinecraftInstance.mc.field_71439_g.field_70161_v + zOffset;
        if (((Boolean)this.lookValue.get()).booleanValue()) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A, ground));
        } else {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
        }
    }

    public static /* synthetic */ void sendCriticalPacket$default(Criticals criticals, double d, double d2, double d3, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            d = 0.0;
        }
        if ((n & 2) != 0) {
            d2 = 0.0;
        }
        if ((n & 4) != 0) {
            d3 = 0.0;
        }
        criticals.sendCriticalPacket(d, d2, d3, bl);
    }

    @Override
    @NotNull
    public List<Value<?>> getValues() {
        return this.values;
    }
}

