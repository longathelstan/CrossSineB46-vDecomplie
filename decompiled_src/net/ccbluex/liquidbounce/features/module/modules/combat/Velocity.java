/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

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
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Velocity", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u00020/H\u0016J\u0010\u00101\u001a\u00020/2\u0006\u00102\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020/2\u0006\u00102\u001a\u000205H\u0007R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001e\u0010\u001e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u001fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020#X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u0011\u0010(\u001a\u00020)\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0007\u00a8\u00066"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Velocity;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chance", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getChance", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "horizontal", "getHorizontal", "m", "", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "modes", "", "noFireValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "oc", "og", "om", "tag", "getTag", "()Ljava/lang/String;", "values", "", "getValues", "()Ljava/util/List;", "velocityInput", "", "getVelocityInput", "()Z", "setVelocityInput", "(Z)V", "velocityTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getVelocityTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "vertical", "getVertical", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Velocity
extends Module {
    @NotNull
    public static final Velocity INSTANCE;
    @NotNull
    private static final List<VelocityMode> modes;
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final Value<Integer> horizontal;
    @NotNull
    private static final Value<Integer> vertical;
    @NotNull
    private static final Value<Integer> chance;
    @NotNull
    private static final Value<String> m;
    @NotNull
    private static final BoolValue og;
    @NotNull
    private static final BoolValue oc;
    @NotNull
    private static final BoolValue om;
    @NotNull
    private static final BoolValue noFireValue;
    @NotNull
    private static final MSTimer velocityTimer;
    private static boolean velocityInput;
    @NotNull
    private static final List<Value<?>> values;

    private Velocity() {
    }

    private final VelocityMode getMode() {
        Object v0;
        block2: {
            for (Object t : (Iterable)modes) {
                VelocityMode it = (VelocityMode)t;
                boolean bl = false;
                if (!INSTANCE.getModeValue().equals(it.getModeName())) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        VelocityMode velocityMode = v0;
        if (velocityMode == null) {
            throw new NullPointerException();
        }
        return velocityMode;
    }

    @NotNull
    public final ListValue getModeValue() {
        return modeValue;
    }

    @NotNull
    public final Value<Integer> getHorizontal() {
        return horizontal;
    }

    @NotNull
    public final Value<Integer> getVertical() {
        return vertical;
    }

    @NotNull
    public final Value<Integer> getChance() {
        return chance;
    }

    @NotNull
    public final MSTimer getVelocityTimer() {
        return velocityTimer;
    }

    public final boolean getVelocityInput() {
        return velocityInput;
    }

    public final void setVelocityInput(boolean bl) {
        velocityInput = bl;
    }

    @Override
    public void onEnable() {
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
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.func_70090_H() || MinecraftInstance.mc.field_71439_g.func_180799_ab() || MinecraftInstance.mc.field_71439_g.field_70134_J) {
            return;
        }
        if ((Boolean)og.get() != false && !MinecraftInstance.mc.field_71439_g.field_70122_E || (Boolean)oc.get() != false && !CrossSine.INSTANCE.getCombatManager().getInCombat() || ((Boolean)om.get()).booleanValue() && !MovementUtils.INSTANCE.isMoving()) {
            return;
        }
        if (((Boolean)noFireValue.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.func_70027_ad()) {
            return;
        }
        this.getMode().onUpdate(event);
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        block6: {
            block8: {
                block7: {
                    Intrinsics.checkNotNullParameter(event, "event");
                    if ((Boolean)og.get() != false && !MinecraftInstance.mc.field_71439_g.field_70122_E || (Boolean)oc.get() != false && !CrossSine.INSTANCE.getCombatManager().getInCombat() || ((Boolean)om.get()).booleanValue() && !MovementUtils.INSTANCE.isMoving()) {
                        return;
                    }
                    this.getMode().onPacket(event);
                    Packet<?> packet = event.getPacket();
                    if (!(packet instanceof S12PacketEntityVelocity)) break block6;
                    if (MinecraftInstance.mc.field_71439_g == null) break block7;
                    WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                    Entity entity = worldClient == null ? null : worldClient.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
                    if (entity == null) {
                        return;
                    }
                    if (Intrinsics.areEqual(entity, MinecraftInstance.mc.field_71439_g)) break block8;
                }
                return;
            }
            if (((Boolean)noFireValue.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.func_70027_ad()) {
                return;
            }
            velocityTimer.reset();
            this.getMode().onVelocityPacket(event);
        }
    }

    @Override
    @NotNull
    public String getTag() {
        String string;
        return Intrinsics.areEqual(modeValue.get(), "Standard") ? (Intrinsics.areEqual(string = m.get(), "Text") ? "Standard" : (Intrinsics.areEqual(string, "Percent") ? new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(vertical.get()) + "% " + new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(horizontal.get()) + '%' : "")) : (String)modeValue.get();
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
        void $this$mapTo$iv$iv2;
        Collection collection;
        void $this$mapTo$iv$iv3;
        INSTANCE = new Velocity();
        Iterable $this$map$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(INSTANCE.getClass().getPackage().getName(), ".velocitys"), VelocityMode.class);
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
                throw new NullPointerException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode");
            }
            collection.add((VelocityMode)t);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        modes = CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator(){

            public final int compare(T a, T b) {
                VelocityMode it = (VelocityMode)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getModeName());
                it = (VelocityMode)b;
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
            VelocityMode bl = (VelocityMode)item$iv$iv;
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
                if (Velocity.INSTANCE.getState()) {
                    Velocity.INSTANCE.onDisable();
                }
            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                if (Velocity.INSTANCE.getState()) {
                    Velocity.INSTANCE.onEnable();
                }
            }
        };
        horizontal = new IntegerValue("Horizontal", 0, 0, 100).displayable(horizontal.1.INSTANCE);
        vertical = new IntegerValue("Vertical", 0, 0, 100).displayable(vertical.1.INSTANCE);
        chance = new IntegerValue("Chance", 100, 0, 100).displayable(chance.1.INSTANCE);
        object = new String[]{"Text", "Percent"};
        m = new ListValue("StandardTag", (String[])object, "Text").displayable(m.1.INSTANCE);
        og = new BoolValue("OnlyGround", false);
        oc = new BoolValue("OnlyCombat", false);
        om = new BoolValue("OnlyMove", false);
        noFireValue = new BoolValue("noFire", false);
        velocityTimer = new MSTimer();
        Object it = object = CollectionsKt.toMutableList((Collection)super.getValues());
        boolean bl = false;
        Iterable $this$map$iv3 = modes;
        boolean $i$f$map3 = false;
        Iterable $i$f$mapTo22 = $this$map$iv3;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
        boolean $i$f$mapTo3 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void mode2;
            VelocityMode velocityMode = (VelocityMode)item$iv$iv;
            Collection collection2 = destination$iv$iv3;
            boolean bl3 = false;
            Iterable $this$forEach$iv = mode2.getValues();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Value value = (Value)element$iv;
                boolean bl4 = false;
                it.add(value.displayable(new Function0<Boolean>((VelocityMode)mode2){
                    final /* synthetic */ VelocityMode $mode;
                    {
                        this.$mode = $mode;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return Velocity.INSTANCE.getModeValue().equals(this.$mode.getModeName());
                    }
                }));
            }
            collection2.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv3;
        values = object;
    }
}

