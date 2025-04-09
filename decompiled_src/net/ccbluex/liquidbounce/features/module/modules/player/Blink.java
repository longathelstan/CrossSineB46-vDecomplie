/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.visual.Breadcrumbs;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.FakePlayer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="Blink", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\b\u0010\u001e\u001a\u00020\u001cH\u0016J\u0010\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!H\u0007J\u0010\u0010\"\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020#H\u0007J\u0010\u0010$\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020%H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006&"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/Blink;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "fakePlayer", "Lnet/ccbluex/liquidbounce/utils/FakePlayer;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayClient;", "positions", "Ljava/util/LinkedList;", "", "pulseDelayValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "pulseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "pulseValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "renderPlayer", "serverPacket", "tag", "", "getTag", "()Ljava/lang/String;", "clearPackets", "", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class Blink
extends Module {
    @NotNull
    public static final Blink INSTANCE = new Blink();
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final BoolValue renderPlayer;
    @NotNull
    private static final BoolValue pulseValue;
    @NotNull
    private static final BoolValue serverPacket;
    @NotNull
    private static final Value<Integer> pulseDelayValue;
    @NotNull
    private static final MSTimer pulseTimer;
    @NotNull
    private static FakePlayer fakePlayer;
    @NotNull
    private static final LinkedList<double[]> positions;
    @NotNull
    private static final LinkedBlockingQueue<Packet<INetHandlerPlayClient>> packets;

    private Blink() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onEnable() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        if (modeValue.equals("All")) {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
        } else {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, false, true, false, false, false, false, false, false, false, 2039, null);
        }
        packets.clear();
        LinkedList<double[]> linkedList = positions;
        synchronized (linkedList) {
            boolean bl = false;
            double[] dArray = new double[]{MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b + (double)(MinecraftInstance.mc.field_71439_g.func_70047_e() / (float)2), MinecraftInstance.mc.field_71439_g.field_70161_v};
            positions.add(dArray);
            dArray = new double[]{MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b, MinecraftInstance.mc.field_71439_g.field_70161_v};
            boolean bl2 = positions.add(dArray);
        }
        pulseTimer.reset();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onDisable() {
        LinkedList<double[]> linkedList = positions;
        synchronized (linkedList) {
            boolean bl = false;
            positions.clear();
            Unit unit = Unit.INSTANCE;
        }
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
        this.clearPackets();
        fakePlayer.disable();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Object object = positions;
        synchronized (object) {
            boolean bl = false;
            double[] dArray = new double[]{MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.func_174813_aQ().field_72338_b, MinecraftInstance.mc.field_71439_g.field_70161_v};
            bl = positions.add(dArray);
        }
        object = MinecraftInstance.mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(object, "mc.thePlayer");
        fakePlayer.setup((EntityPlayer)object);
        if (((Boolean)pulseValue.get()).booleanValue() && pulseTimer.hasTimePassed(((Number)pulseDelayValue.get()).intValue())) {
            object = positions;
            synchronized (object) {
                boolean bl = false;
                positions.clear();
                Unit unit = Unit.INSTANCE;
            }
            BlinkUtils.releasePacket$default(BlinkUtils.INSTANCE, null, false, 0, 0, 15, null);
            this.clearPackets();
            fakePlayer.disable();
            pulseTimer.reset();
        }
    }

    private final void clearPackets() {
        while (!packets.isEmpty()) {
            Packet<INetHandlerPlayClient> packet = packets.take();
            if (packet == null) {
                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.network.Packet<net.minecraft.network.play.INetHandlerPlayClient?>");
            }
            PacketUtils.INSTANCE.handlePacket(packet);
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (((Boolean)serverPacket.get()).booleanValue()) {
            String string = packet.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(string, "packet.javaClass.simpleName");
            if (StringsKt.startsWith(string, "S", true)) {
                if (MinecraftInstance.mc.field_71439_g.field_70173_aa < 20) {
                    return;
                }
                event.cancelEvent();
                packets.add(packet);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!((Boolean)renderPlayer.get()).booleanValue()) {
            return;
        }
        Breadcrumbs breadcrumbs = CrossSine.INSTANCE.getModuleManager().get(Breadcrumbs.class);
        Intrinsics.checkNotNull(breadcrumbs);
        Breadcrumbs breadcrumbs2 = breadcrumbs;
        LinkedList<double[]> linkedList = positions;
        synchronized (linkedList) {
            boolean bl = false;
            GL11.glPushMatrix();
            GL11.glDisable((int)3553);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2929);
            MinecraftInstance.mc.field_71460_t.func_175072_h();
            GL11.glLineWidth((float)2.0f);
            GL11.glBegin((int)3);
            RenderUtils.glColor(breadcrumbs2.getColor());
            double renderPosX = MinecraftInstance.mc.func_175598_ae().field_78730_l;
            double renderPosY = MinecraftInstance.mc.func_175598_ae().field_78731_m;
            double renderPosZ = MinecraftInstance.mc.func_175598_ae().field_78728_n;
            for (double[] pos : positions) {
                GL11.glVertex3d((double)(pos[0] - renderPosX), (double)(pos[1] - renderPosY), (double)(pos[2] - renderPosZ));
            }
            GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            GL11.glEnd();
            GL11.glEnable((int)2929);
            GL11.glDisable((int)2848);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3553);
            GL11.glPopMatrix();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return Intrinsics.stringPlus("", BlinkUtils.bufferSize$default(BlinkUtils.INSTANCE, null, 1, null));
    }

    public static final /* synthetic */ BoolValue access$getPulseValue$p() {
        return pulseValue;
    }

    static {
        String[] stringArray = new String[]{"All", "Movement"};
        modeValue = new ListValue("Blink-Mode", stringArray, "All");
        renderPlayer = new BoolValue("Render", false);
        pulseValue = new BoolValue("Pulse", false);
        serverPacket = new BoolValue("Server-Packet", false);
        pulseDelayValue = new IntegerValue("PulseDelay", 1000, 100, 5000).displayable(pulseDelayValue.1.INSTANCE);
        pulseTimer = new MSTimer();
        fakePlayer = new FakePlayer();
        positions = new LinkedList();
        packets = new LinkedBlockingQueue();
    }
}

