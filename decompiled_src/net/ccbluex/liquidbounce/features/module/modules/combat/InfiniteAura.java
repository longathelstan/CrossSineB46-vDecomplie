/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.InfiniteAura;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PathUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="InfiniteAura", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020\u001dH\u0002J\u001a\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\u00072\b\b\u0002\u0010'\u001a\u00020\u001bH\u0002J\b\u0010(\u001a\u00020#H\u0016J\b\u0010)\u001a\u00020#H\u0016J\u0010\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020,H\u0007J\u0010\u0010-\u001a\u00020#2\u0006\u0010+\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020#2\u0006\u0010+\u001a\u000200H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/InfiniteAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "cpsValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "distValue", "lastTarget", "Lnet/minecraft/entity/EntityLivingBase;", "getLastTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setLastTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "moveDistanceValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "noLagBackValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "noRegenValue", "packetBack", "packetValue", "pathRenderValue", "points", "", "Lnet/minecraft/util/Vec3;", "swingValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "targetsValue", "", "thread", "Ljava/lang/Thread;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "doTpAura", "", "getDelay", "hit", "entity", "force", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class InfiniteAura
extends Module {
    @NotNull
    public static final InfiniteAura INSTANCE = new InfiniteAura();
    @NotNull
    private static final ListValue packetValue;
    @NotNull
    private static final BoolValue packetBack;
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final Value<Integer> targetsValue;
    @NotNull
    private static final IntegerValue cpsValue;
    @NotNull
    private static final IntegerValue distValue;
    @NotNull
    private static final FloatValue moveDistanceValue;
    @NotNull
    private static final BoolValue noRegenValue;
    @NotNull
    private static final BoolValue noLagBackValue;
    @NotNull
    private static final Value<Boolean> swingValue;
    @NotNull
    private static final BoolValue pathRenderValue;
    @Nullable
    private static EntityLivingBase lastTarget;
    @NotNull
    private static final MSTimer timer;
    @NotNull
    private static List<Vec3> points;
    @Nullable
    private static Thread thread;

    private InfiniteAura() {
    }

    @Nullable
    public final EntityLivingBase getLastTarget() {
        return lastTarget;
    }

    public final void setLastTarget(@Nullable EntityLivingBase entityLivingBase) {
        lastTarget = entityLivingBase;
    }

    private final int getDelay() {
        return 1000 / ((Number)cpsValue.get()).intValue();
    }

    @Override
    public void onEnable() {
        timer.reset();
        points.clear();
    }

    @Override
    public void onDisable() {
        timer.reset();
        points.clear();
        Thread thread2 = thread;
        if (thread2 != null) {
            thread2.stop();
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!timer.hasTimePassed(this.getDelay())) {
            return;
        }
        Thread thread2 = thread;
        if (thread2 == null ? false : thread2.isAlive()) {
            return;
        }
        String string = ((String)modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String string2 = string;
        if (Intrinsics.areEqual(string2, "aura")) {
            thread = ThreadsKt.thread$default(false, false, null, "InfiniteAura", 0, onUpdate.1.INSTANCE, 23, null);
            points.clear();
            timer.reset();
        } else if (Intrinsics.areEqual(string2, "click")) {
            if (MinecraftInstance.mc.field_71474_y.field_74312_F.func_151470_d()) {
                thread = ThreadsKt.thread$default(false, false, null, "InfiniteAura", 0, onUpdate.2.INSTANCE, 23, null);
                timer.reset();
            }
            points.clear();
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void doTpAura() {
        void $this$filterTo$iv$iv;
        List list = MinecraftInstance.mc.field_71441_e.field_72996_f;
        Intrinsics.checkNotNullExpressionValue(list, "mc.theWorld.loadedEntityList");
        Iterable $this$filter$iv = list;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Entity it = (Entity)element$iv$iv;
            boolean bl = false;
            boolean bl2 = it instanceof EntityLivingBase && EntityUtils.INSTANCE.isSelected(it, true) && MinecraftInstance.mc.field_71439_g.func_70032_d(it) < (float)((Number)distValue.get()).intValue();
            if (!bl2) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List targets = CollectionsKt.toMutableList((List)destination$iv$iv);
        if (targets.isEmpty()) {
            return;
        }
        List $this$sortBy$iv = targets;
        boolean $i$f$sortBy = false;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                public final int compare(T a, T b) {
                    Entity it = (Entity)a;
                    boolean bl = false;
                    Comparable comparable = Float.valueOf(MinecraftInstance.mc.field_71439_g.func_70032_d(it));
                    it = (Entity)b;
                    Comparable comparable2 = comparable;
                    bl = false;
                    return ComparisonsKt.compareValues(comparable2, (Comparable)Float.valueOf(MinecraftInstance.mc.field_71439_g.func_70032_d(it)));
                }
            });
        }
        int count = 0;
        Iterator iterator2 = targets.iterator();
        while (iterator2.hasNext()) {
            Entity entity;
            Entity entity2 = entity = (Entity)iterator2.next();
            if (entity2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
            }
            if (InfiniteAura.hit$default(this, (EntityLivingBase)entity2, false, 2, null)) {
                int n = count;
                count = n + 1;
            }
            if (count <= ((Number)targetsValue.get()).intValue()) continue;
            break;
        }
    }

    private final boolean hit(EntityLivingBase entity, boolean force) {
        List<Vec3> path = PathUtils.findBlinkPath(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, ((Number)moveDistanceValue.get()).floatValue());
        if (path.isEmpty()) {
            return false;
        }
        Intrinsics.checkNotNullExpressionValue(path, "path");
        Vec3 it = CollectionsKt.last(path);
        boolean bl = false;
        double lastDistance = entity.func_70011_f(it.field_72450_a, it.field_72448_b, it.field_72449_c);
        if (!force && lastDistance > 10.0) {
            return false;
        }
        Iterable $this$forEach$iv = path;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Vec3 it2 = (Vec3)element$iv;
            boolean bl2 = false;
            if (packetValue.equals("PacketPosition")) {
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(it2.field_72450_a, it2.field_72448_b, it2.field_72449_c, true));
            } else {
                NetHandlerPlayClient netHandlerPlayClient = MinecraftInstance.mc.func_147114_u();
                double d = it2.field_72450_a;
                double d2 = it2.field_72448_b;
                double d3 = it2.field_72449_c;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP);
                float f = entityPlayerSP.field_70177_z;
                EntityPlayerSP entityPlayerSP2 = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP2);
                netHandlerPlayClient.func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(d, d2, d3, f, entityPlayerSP2.field_70125_A, true));
            }
            Intrinsics.checkNotNullExpressionValue(it2, "it");
            points.add(it2);
        }
        if (lastDistance > 3.0 && ((Boolean)packetBack.get()).booleanValue()) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, true));
        }
        if (swingValue.get().booleanValue()) {
            MinecraftInstance.mc.field_71439_g.func_71038_i();
        } else {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
        }
        MinecraftInstance.mc.field_71442_b.func_78764_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, (Entity)entity);
        int n = path.size() - 1;
        if (0 <= n) {
            do {
                int i = n--;
                Vec3 vec = path.get(i);
                if (packetValue.equals("PacketPosition")) {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c, true));
                    continue;
                }
                NetHandlerPlayClient netHandlerPlayClient = MinecraftInstance.mc.func_147114_u();
                double d = vec.field_72450_a;
                double d4 = vec.field_72448_b;
                double d5 = vec.field_72449_c;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP);
                float f = entityPlayerSP.field_70177_z;
                EntityPlayerSP entityPlayerSP3 = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNull(entityPlayerSP3);
                netHandlerPlayClient.func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(d, d4, d5, f, entityPlayerSP3.field_70125_A, true));
            } while (0 <= n);
        }
        if (((Boolean)packetBack.get()).booleanValue()) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
        }
        return true;
    }

    static /* synthetic */ boolean hit$default(InfiniteAura infiniteAura, EntityLivingBase entityLivingBase, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return infiniteAura.hit(entityLivingBase, bl);
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        boolean isMovePacket;
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            timer.reset();
        }
        boolean bl = isMovePacket = event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition || event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook;
        if (((Boolean)noRegenValue.get()).booleanValue() && event.getPacket() instanceof C03PacketPlayer && !isMovePacket) {
            event.cancelEvent();
        }
        if (((Boolean)noLagBackValue.get()).booleanValue() && event.getPacket() instanceof S08PacketPlayerPosLook) {
            PlayerCapabilities capabilities = new PlayerCapabilities();
            capabilities.field_75101_c = true;
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C13PacketPlayerAbilities(capabilities));
            double x = ((S08PacketPlayerPosLook)event.getPacket()).func_148932_c() - MinecraftInstance.mc.field_71439_g.field_70165_t;
            double y = ((S08PacketPlayerPosLook)event.getPacket()).func_148928_d() - MinecraftInstance.mc.field_71439_g.field_70163_u;
            double z = ((S08PacketPlayerPosLook)event.getPacket()).func_148933_e() - MinecraftInstance.mc.field_71439_g.field_70161_v;
            double diff = Math.sqrt(x * x + y * y + z * z);
            event.cancelEvent();
            PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)event.getPacket()).func_148932_c(), ((S08PacketPlayerPosLook)event.getPacket()).func_148928_d(), ((S08PacketPlayerPosLook)event.getPacket()).func_148933_e(), ((S08PacketPlayerPosLook)event.getPacket()).func_148931_f(), ((S08PacketPlayerPosLook)event.getPacket()).func_148930_g(), true)));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        List<Vec3> list = points;
        synchronized (list) {
            boolean bl = false;
            if (points.isEmpty() || !((Boolean)pathRenderValue.get()).booleanValue()) {
                return;
            }
            double renderPosX = MinecraftInstance.mc.func_175598_ae().field_78730_l;
            double renderPosY = MinecraftInstance.mc.func_175598_ae().field_78731_m;
            double renderPosZ = MinecraftInstance.mc.func_175598_ae().field_78728_n;
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glShadeModel((int)7425);
            GL11.glDisable((int)3553);
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2929);
            GL11.glDisable((int)2896);
            GL11.glDepthMask((boolean)false);
            RenderUtils.glColor(ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, null));
            for (Vec3 vec : points) {
                double x = vec.field_72450_a - renderPosX;
                double y = vec.field_72448_b - renderPosY;
                double z = vec.field_72449_c - renderPosZ;
                double width = 0.3;
                double height = MinecraftInstance.mc.field_71439_g.func_70047_e();
                MinecraftInstance.mc.field_71460_t.func_78479_a(MinecraftInstance.mc.field_71428_T.field_74281_c, 2);
                GL11.glLineWidth((float)2.0f);
                GL11.glBegin((int)3);
                GL11.glVertex3d((double)(x - width), (double)y, (double)(z - width));
                GL11.glVertex3d((double)(x - width), (double)y, (double)(z - width));
                GL11.glVertex3d((double)(x - width), (double)(y + height), (double)(z - width));
                GL11.glVertex3d((double)(x + width), (double)(y + height), (double)(z - width));
                GL11.glVertex3d((double)(x + width), (double)y, (double)(z - width));
                GL11.glVertex3d((double)(x - width), (double)y, (double)(z - width));
                GL11.glVertex3d((double)(x - width), (double)y, (double)(z + width));
                GL11.glEnd();
                GL11.glBegin((int)3);
                GL11.glVertex3d((double)(x + width), (double)y, (double)(z + width));
                GL11.glVertex3d((double)(x + width), (double)(y + height), (double)(z + width));
                GL11.glVertex3d((double)(x - width), (double)(y + height), (double)(z + width));
                GL11.glVertex3d((double)(x - width), (double)y, (double)(z + width));
                GL11.glVertex3d((double)(x + width), (double)y, (double)(z + width));
                GL11.glVertex3d((double)(x + width), (double)y, (double)(z - width));
                GL11.glEnd();
                GL11.glBegin((int)3);
                GL11.glVertex3d((double)(x + width), (double)(y + height), (double)(z + width));
                GL11.glVertex3d((double)(x + width), (double)(y + height), (double)(z - width));
                GL11.glEnd();
                GL11.glBegin((int)3);
                GL11.glVertex3d((double)(x - width), (double)(y + height), (double)(z + width));
                GL11.glVertex3d((double)(x - width), (double)(y + height), (double)(z - width));
                GL11.glEnd();
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            GL11.glDisable((int)2848);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Unit unit = Unit.INSTANCE;
        }
    }

    public static final /* synthetic */ void access$doTpAura(InfiniteAura $this) {
        $this.doTpAura();
    }

    public static final /* synthetic */ IntegerValue access$getDistValue$p() {
        return distValue;
    }

    public static final /* synthetic */ boolean access$hit(InfiniteAura $this, EntityLivingBase entity, boolean force) {
        return $this.hit(entity, force);
    }

    public static final /* synthetic */ ListValue access$getModeValue$p() {
        return modeValue;
    }

    static {
        String[] stringArray = new String[]{"PacketPosition", "PacketPosLook"};
        packetValue = new ListValue("PacketMode", stringArray, "PacketPosition");
        packetBack = new BoolValue("DoTeleportBackPacket", false);
        stringArray = new String[]{"Aura", "Click"};
        modeValue = new ListValue("Mode", stringArray, "Aura");
        targetsValue = new IntegerValue("Targets", 3, 1, 10).displayable(targetsValue.1.INSTANCE);
        cpsValue = new IntegerValue("CPS", 1, 1, 10);
        distValue = new IntegerValue("Distance", 30, 20, 100);
        moveDistanceValue = new FloatValue("MoveDistance", 5.0f, 2.0f, 15.0f);
        noRegenValue = new BoolValue("NoRegen", true);
        noLagBackValue = new BoolValue("NoLagback", true);
        swingValue = new BoolValue("Swing", true).displayable(swingValue.1.INSTANCE);
        pathRenderValue = new BoolValue("PathRender", true);
        timer = new MSTimer();
        points = new ArrayList();
    }
}

