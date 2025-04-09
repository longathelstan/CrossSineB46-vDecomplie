/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Speed", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020)H\u0016J\u0010\u0010+\u001a\u00020)2\u0006\u0010,\u001a\u00020-H\u0007J\u0010\u0010.\u001a\u00020)2\u0006\u0010,\u001a\u00020/H\u0007J\u0010\u00100\u001a\u00020)2\u0006\u0010,\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020)2\u0006\u0010,\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020)2\u0006\u0010,\u001a\u000205H\u0007J\u0010\u00106\u001a\u00020)2\u0006\u0010,\u001a\u000207H\u0007J\b\u00108\u001a\u00020\rH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\"\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001e\u0010%\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0&X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001b\u00a8\u00069"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Speed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "flagCheck", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getFlagCheck", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "flagMS", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getFlagMS", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "flagged", "", "getFlagged", "()Z", "setFlagged", "(Z)V", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "getModes", "()Ljava/util/List;", "noWater", "tag", "", "getTag", "()Ljava/lang/String;", "timerMS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "getTimerMS", "()Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "values", "", "getValues", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "stopWorking", "CrossSine"})
public final class Speed
extends Module {
    @NotNull
    public static final Speed INSTANCE;
    @NotNull
    private static final List<SpeedMode> modes;
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final BoolValue flagCheck;
    @NotNull
    private static final Value<Integer> flagMS;
    @NotNull
    private static final BoolValue noWater;
    private static boolean flagged;
    @NotNull
    private static final TimerMS timerMS;
    @NotNull
    private static final List<Value<?>> values;

    private Speed() {
    }

    @NotNull
    public final List<SpeedMode> getModes() {
        return modes;
    }

    private final SpeedMode getMode() {
        Object v0;
        block2: {
            for (Object t : (Iterable)modes) {
                SpeedMode it = (SpeedMode)t;
                boolean bl = false;
                if (!modeValue.equals(it.getModeName())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        SpeedMode speedMode = v0;
        if (speedMode == null) {
            throw new NullPointerException();
        }
        return speedMode;
    }

    @NotNull
    public final BoolValue getFlagCheck() {
        return flagCheck;
    }

    @NotNull
    public final Value<Integer> getFlagMS() {
        return flagMS;
    }

    public final boolean getFlagged() {
        return flagged;
    }

    public final void setFlagged(boolean bl) {
        flagged = bl;
    }

    @NotNull
    public final TimerMS getTimerMS() {
        return timerMS;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (timerMS.hasTimePassed(((Number)flagMS.get()).intValue())) {
            flagged = false;
        }
        if (MinecraftInstance.mc.field_71439_g.func_70093_af() || MinecraftInstance.mc.field_71439_g.func_70090_H() && ((Boolean)noWater.get()).booleanValue()) {
            return;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0f && modeValue.equals("Legit") || MovementUtils.INSTANCE.isMoving()) {
            MinecraftInstance.mc.field_71439_g.func_70051_ag();
        }
        if (this.stopWorking()) {
            return;
        }
        this.getMode().onUpdate();
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)Interface.INSTANCE.getDynamicIsland().get()).booleanValue()) {
            return;
        }
        DecimalFormat decimalFormat3 = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
        if (this.stopWorking()) {
            Fonts.font32.drawCenteredString(Intrinsics.stringPlus("Disable Speed : ", decimalFormat3.format((double)(Speed.timerMS.time + (long)2000 - System.currentTimeMillis()) / 1000.0)), (float)event.getScaledResolution().func_78326_a() / 2.0f, (float)event.getScaledResolution().func_78328_b() / 2.0f + 5.0f, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 3, null).getRGB(), true);
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.stopWorking()) {
            return;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70701_bs > 0.0f && modeValue.equals("Legit") || MovementUtils.INSTANCE.isMoving()) {
            MinecraftInstance.mc.field_71439_g.func_70051_ag();
        }
        this.getMode().onMotion(event);
        if (MinecraftInstance.mc.field_71439_g.func_70093_af() || event.getEventState() != EventState.PRE || MinecraftInstance.mc.field_71439_g.func_70090_H() && ((Boolean)noWater.get()).booleanValue()) {
            return;
        }
        this.getMode().onPreMotion();
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.func_70093_af() || MinecraftInstance.mc.field_71439_g.func_70090_H() && ((Boolean)noWater.get()).booleanValue()) {
            return;
        }
        if (this.stopWorking()) {
            return;
        }
        this.getMode().onMove(event);
        TargetStrafe targetStrafe = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
        Intrinsics.checkNotNull(targetStrafe);
        targetStrafe.doMove(event);
    }

    @EventTarget
    public final void onTick(@NotNull TickEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.func_70093_af() || MinecraftInstance.mc.field_71439_g.func_70090_H() && ((Boolean)noWater.get()).booleanValue()) {
            return;
        }
        if (this.stopWorking()) {
            return;
        }
        this.getMode().onTick();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            flagged = true;
            timerMS.reset();
        }
        if (this.stopWorking()) {
            return;
        }
        this.getMode().onPacket(event);
    }

    @Override
    public void onEnable() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        this.getMode().onEnable();
    }

    @Override
    public void onDisable() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        this.getMode().onDisable();
        flagged = false;
    }

    private final boolean stopWorking() {
        return (Boolean)flagCheck.get() != false && flagged && !timerMS.hasTimePassed(((Number)flagMS.get()).intValue());
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)modeValue.get();
    }

    @Override
    @NotNull
    public List<Value<?>> getValues() {
        return values;
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
        INSTANCE = new Speed();
        Iterable $this$map$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".speeds"), SpeedMode.class);
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
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode");
            }
            collection.add((SpeedMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                SpeedMode it = (SpeedMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (SpeedMode)b;
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
            SpeedMode bl = (SpeedMode)item$iv$iv;
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
                if (Speed.INSTANCE.getState()) {
                    Speed.INSTANCE.onDisable();
                }
            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                if (Speed.INSTANCE.getState()) {
                    Speed.INSTANCE.onEnable();
                }
            }
        };
        flagCheck = new BoolValue("Flag-Check", false);
        flagMS = new IntegerValue("StopForMS", 500, 0, 2000).displayable(flagMS.1.INSTANCE);
        noWater = new BoolValue("NoWater", true);
        timerMS = new TimerMS();
        Object it = object = CollectionsKt.toMutableList((Collection)super.getValues());
        boolean bl = false;
        Iterable $this$map$iv3 = INSTANCE.getModes();
        boolean $i$f$map3 = false;
        Iterable $i$f$mapTo22 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo3 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void mode2;
            SpeedMode speedMode = (SpeedMode)item$iv$iv;
            Collection collection2 = destination$iv$iv3;
            boolean bl3 = false;
            Iterable $this$forEach$iv = mode2.getValues();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Value value = (Value)element$iv;
                boolean bl4 = false;
                it.add(value.displayable(new Function0<Boolean>((SpeedMode)mode2){
                    final /* synthetic */ SpeedMode $mode;
                    {
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return Speed.access$getModeValue$p().equals(this.$mode.getModeName());
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv3;
        values = object;
    }
}

