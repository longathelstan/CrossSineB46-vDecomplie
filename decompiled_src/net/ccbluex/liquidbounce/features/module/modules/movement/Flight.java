/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
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
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Flight", category=ModuleCategory.MOVEMENT, autoDisable=EnumAutoDisableType.FLAG)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u00a4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020CH\u0007J\b\u0010D\u001a\u00020AH\u0016J\b\u0010E\u001a\u00020AH\u0016J\u0010\u0010F\u001a\u00020A2\u0006\u0010B\u001a\u00020GH\u0007J\u0010\u0010H\u001a\u00020A2\u0006\u0010B\u001a\u00020IH\u0007J\u0010\u0010J\u001a\u00020A2\u0006\u0010B\u001a\u00020KH\u0007J\u0010\u0010L\u001a\u00020A2\u0006\u0010B\u001a\u00020MH\u0007J\u0010\u0010N\u001a\u00020A2\u0006\u0010B\u001a\u00020OH\u0007J\u0010\u0010P\u001a\u00020A2\u0006\u0010B\u001a\u00020QH\u0007J\u0010\u0010R\u001a\u00020A2\u0006\u0010B\u001a\u00020SH\u0007J\u0010\u0010T\u001a\u00020A2\u0006\u0010B\u001a\u00020UH\u0007J\u0010\u0010V\u001a\u00020A2\u0006\u0010B\u001a\u00020WH\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016R\u001a\u0010\u001a\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000e\"\u0004\b\u001c\u0010\u0010R\u001a\u0010\u001d\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0014\"\u0004\b\u001f\u0010\u0016R\u000e\u0010 \u001a\u00020!X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020!X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010'\u001a\b\u0012\u0004\u0012\u00020#0(\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00040-X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010.\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0006\"\u0004\b0\u0010\bR\u0014\u00101\u001a\u0002028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b3\u00104R\u001a\u00105\u001a\u000206X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u001e\u0010;\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030-0<X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010*R\u000e\u0010>\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020\f0-X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006X"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Flight;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "antiDesync", "", "getAntiDesync", "()Z", "setAntiDesync", "(Z)V", "fakeDamageValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "launchPitch", "", "getLaunchPitch", "()F", "setLaunchPitch", "(F)V", "launchX", "", "getLaunchX", "()D", "setLaunchX", "(D)V", "launchY", "getLaunchY", "setLaunchY", "launchYaw", "getLaunchYaw", "setLaunchYaw", "launchZ", "getLaunchZ", "setLaunchZ", "markValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/flights/FlightMode;", "modeValue", "modes", "", "getModes", "()Ljava/util/List;", "motionResetValue", "motionResetYValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "needReset", "getNeedReset", "setNeedReset", "tag", "", "getTag", "()Ljava/lang/String;", "time", "", "getTime", "()I", "setTime", "(I)V", "values", "", "getValues", "viewBobbingValue", "viewBobbingYawValue", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3d", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class Flight
extends Module {
    @NotNull
    public static final Flight INSTANCE;
    @NotNull
    private static final List<FlightMode> modes;
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final BoolValue motionResetValue;
    @NotNull
    private static final Value<Boolean> motionResetYValue;
    @NotNull
    private static final ListValue markValue;
    @NotNull
    private static final BoolValue fakeDamageValue;
    @NotNull
    private static final BoolValue viewBobbingValue;
    @NotNull
    private static final Value<Float> viewBobbingYawValue;
    private static double launchX;
    private static double launchY;
    private static double launchZ;
    private static float launchYaw;
    private static float launchPitch;
    private static int time;
    private static boolean antiDesync;
    private static boolean needReset;
    @NotNull
    private static final List<Value<?>> values;

    private Flight() {
    }

    @NotNull
    public final List<FlightMode> getModes() {
        return modes;
    }

    private final FlightMode getMode() {
        Object v0;
        block2: {
            for (Object t : (Iterable)modes) {
                FlightMode it = (FlightMode)t;
                boolean bl = false;
                if (!modeValue.equals(it.getModeName())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        FlightMode flightMode = v0;
        if (flightMode == null) {
            throw new NullPointerException();
        }
        return flightMode;
    }

    public final double getLaunchX() {
        return launchX;
    }

    public final void setLaunchX(double d) {
        launchX = d;
    }

    public final double getLaunchY() {
        return launchY;
    }

    public final void setLaunchY(double d) {
        launchY = d;
    }

    public final double getLaunchZ() {
        return launchZ;
    }

    public final void setLaunchZ(double d) {
        launchZ = d;
    }

    public final float getLaunchYaw() {
        return launchYaw;
    }

    public final void setLaunchYaw(float f) {
        launchYaw = f;
    }

    public final float getLaunchPitch() {
        return launchPitch;
    }

    public final void setLaunchPitch(float f) {
        launchPitch = f;
    }

    public final int getTime() {
        return time;
    }

    public final void setTime(int n) {
        time = n;
    }

    public final boolean getAntiDesync() {
        return antiDesync;
    }

    public final void setAntiDesync(boolean bl) {
        antiDesync = bl;
    }

    public final boolean getNeedReset() {
        return needReset;
    }

    public final void setNeedReset(boolean bl) {
        needReset = bl;
    }

    @Override
    public void onEnable() {
        antiDesync = false;
        needReset = true;
        if (MinecraftInstance.mc.field_71439_g.field_70122_E && ((Boolean)fakeDamageValue.get()).booleanValue()) {
            PacketEvent event = new PacketEvent((Packet)new S19PacketEntityStatus((Entity)MinecraftInstance.mc.field_71439_g, 2), PacketEvent.Type.RECEIVE);
            CrossSine.INSTANCE.getEventManager().callEvent(event);
            if (!event.isCancelled()) {
                MinecraftInstance.mc.field_71439_g.func_70103_a((byte)2);
            }
        }
        launchX = MinecraftInstance.mc.field_71439_g.field_70165_t;
        launchY = MinecraftInstance.mc.field_71439_g.field_70163_u;
        launchZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
        launchYaw = MinecraftInstance.mc.field_71439_g.field_70177_z;
        launchPitch = MinecraftInstance.mc.field_71439_g.field_70125_A;
        this.getMode().onEnable();
    }

    @Override
    public void onDisable() {
        antiDesync = false;
        MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b = false;
        MinecraftInstance.mc.field_71439_g.field_71075_bZ.func_75092_a(0.05f);
        MinecraftInstance.mc.field_71439_g.field_70145_X = false;
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        MinecraftInstance.mc.field_71439_g.field_71102_ce = 0.02f;
        if (((Boolean)motionResetValue.get()).booleanValue() && needReset) {
            MovementUtils.INSTANCE.resetMotion(motionResetYValue.get());
        }
        this.getMode().onDisable();
        time = 0;
    }

    @EventTarget
    public final void onRender3d(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (markValue.equals("Off")) {
            return;
        }
        RenderUtils.drawPlatform(markValue.equals("Up") ? launchY + 2.0 : launchY, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72337_e < launchY + 2.0 ? new Color(0, 255, 0, 90) : new Color(255, 0, 0, 90), 1.0);
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onUpdate(event);
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)viewBobbingValue.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.field_71109_bG = ((Number)viewBobbingYawValue.get()).floatValue();
            MinecraftInstance.mc.field_71439_g.field_71107_bF = ((Number)viewBobbingYawValue.get()).floatValue();
        }
        this.getMode().onMotion(event);
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onPacket(event);
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onWorld(event);
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onMove(event);
    }

    @EventTarget
    public final void onTick(@NotNull TickEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.getMode().onTick(event);
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

    public static final /* synthetic */ BoolValue access$getMotionResetValue$p() {
        return motionResetValue;
    }

    public static final /* synthetic */ BoolValue access$getViewBobbingValue$p() {
        return viewBobbingValue;
    }

    /*
     * WARNING - void declaration
     */
    static {
        void $this$mapTo$iv$iv;
        void $this$mapTo$iv$iv2;
        Collection collection;
        void $this$mapTo$iv$iv3;
        INSTANCE = new Flight();
        Iterable $this$map$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".flights"), FlightMode.class);
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
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.flights.FlightMode");
            }
            collection.add((FlightMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                FlightMode it = (FlightMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (FlightMode)b;
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
            FlightMode bl = (FlightMode)item$iv$iv;
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
                if (Flight.INSTANCE.getState()) {
                    Flight.INSTANCE.onDisable();
                }
            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                if (Flight.INSTANCE.getState()) {
                    Flight.INSTANCE.onEnable();
                }
            }
        };
        motionResetValue = new BoolValue("MotionReset", false);
        motionResetYValue = new BoolValue("ResetY", false).displayable(motionResetYValue.1.INSTANCE);
        object = new String[]{"Up", "Down", "Off"};
        markValue = new ListValue("Mark", (String[])object, "Off");
        fakeDamageValue = new BoolValue("FakeDamage", false);
        viewBobbingValue = new BoolValue("ViewBobbing", false);
        viewBobbingYawValue = new FloatValue("ViewBobbingYaw", 0.1f, 0.0f, 0.5f).displayable(viewBobbingYawValue.1.INSTANCE);
        needReset = true;
        Object it = object = CollectionsKt.toMutableList((Collection)super.getValues());
        boolean bl = false;
        Iterable $this$map$iv3 = INSTANCE.getModes();
        boolean $i$f$map3 = false;
        Iterable $i$f$mapTo22 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo3 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void mode2;
            FlightMode flightMode = (FlightMode)item$iv$iv;
            Collection collection2 = destination$iv$iv3;
            boolean bl3 = false;
            Iterable $this$forEach$iv = mode2.getValues();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Value value = (Value)element$iv;
                boolean bl4 = false;
                it.add(value.displayable(new Function0<Boolean>((FlightMode)mode2){
                    final /* synthetic */ FlightMode $mode;
                    {
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return Flight.access$getModeValue$p().equals(this.$mode.getModeName());
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv3;
        values = object;
    }
}

