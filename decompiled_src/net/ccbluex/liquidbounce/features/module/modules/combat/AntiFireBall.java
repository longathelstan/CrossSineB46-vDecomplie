/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.ItemFireball;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="AntiFireBall", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J.\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020#2\u0006\u0010&\u001a\u00020#2\u0006\u0010'\u001a\u00020#J&\u0010(\u001a\u00020#2\u0006\u0010)\u001a\u00020#2\u0006\u0010*\u001a\u00020#2\u0006\u0010\"\u001a\u00020#2\u0006\u0010+\u001a\u00020#J\u0010\u0010,\u001a\u00020!2\u0006\u0010-\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020!2\u0006\u0010-\u001a\u000200H\u0007J\u0010\u00101\u001a\u00020!2\u0006\u0010-\u001a\u000202H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AntiFireBall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "checkTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "checked", "", "displayName", "", "getDisplayName", "()Ljava/lang/String;", "setDisplayName", "(Ljava/lang/String;)V", "distance", "", "getDistance", "()F", "setDistance", "(F)V", "fireBall", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "hitfireball", "leftDelay", "", "leftLastSwing", "maxTurnSpeed", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "minTurnSpeed", "radiusValue", "rotationValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "scaleValue", "drawArrow", "", "x", "", "y", "angle", "size", "degrees", "getRotations", "eX", "eZ", "z", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AntiFireBall
extends Module {
    @NotNull
    private final BoolValue fireBall = new BoolValue("indicators-FireBall", true);
    @NotNull
    private final FloatValue scaleValue = new FloatValue("Size", 0.7f, 0.65f, 1.25f);
    @NotNull
    private final FloatValue radiusValue = new FloatValue("Radius", 50.0f, 15.0f, 150.0f);
    @NotNull
    private final BoolValue hitfireball = new BoolValue("HitFireBall", false);
    @NotNull
    private final Value<Boolean> rotationValue = new BoolValue("Rotation", true).displayable(new Function0<Boolean>(this){
        final /* synthetic */ AntiFireBall this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AntiFireBall.access$getHitfireball$p(this.this$0).get();
        }
    });
    @NotNull
    private final FloatValue maxTurnSpeed = (FloatValue)new FloatValue(this){
        final /* synthetic */ AntiFireBall this$0;
        {
            this.this$0 = $receiver;
            super("MaxTurnSpeed", 120.0f, 0.0f, 180.0f);
        }

        protected void onChanged(float oldValue, float newValue) {
            float i = ((Number)AntiFireBall.access$getMinTurnSpeed$p(this.this$0).get()).floatValue();
            if (i > newValue) {
                this.set(Float.valueOf(i));
            }
        }
    }.displayable(new Function0<Boolean>(this){
        final /* synthetic */ AntiFireBall this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AntiFireBall.access$getHitfireball$p(this.this$0).get();
        }
    });
    @NotNull
    private final FloatValue minTurnSpeed = (FloatValue)new FloatValue(this){
        final /* synthetic */ AntiFireBall this$0;
        {
            this.this$0 = $receiver;
            super("MinTurnSpeed", 80.0f, 0.0f, 180.0f);
        }

        protected void onChanged(float oldValue, float newValue) {
            float i = ((Number)AntiFireBall.access$getMaxTurnSpeed$p(this.this$0).get()).floatValue();
            if (i < newValue) {
                this.set(Float.valueOf(i));
            }
        }
    }.displayable(new Function0<Boolean>(this){
        final /* synthetic */ AntiFireBall this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AntiFireBall.access$getHitfireball$p(this.this$0).get();
        }
    });
    private long leftDelay = 50L;
    private long leftLastSwing;
    private boolean checked;
    @NotNull
    private final TimerMS checkTimer = new TimerMS();
    private float distance;
    public String displayName;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)this.hitfireball.get()).booleanValue()) {
            for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
                if (!(entity instanceof EntityFireball) || !((double)MinecraftInstance.mc.field_71439_g.func_70032_d(entity) <= 3.1)) continue;
                if (this.checked) {
                    return;
                }
                if (this.rotationValue.get().booleanValue()) {
                    RotationUtils.setTargetRotation(RotationUtils.limitAngleChange(RotationUtils.serverRotation, RotationUtils.getRotationsNonLivingEntity(entity), RandomUtils.INSTANCE.nextFloat(((Number)this.minTurnSpeed.get()).floatValue(), ((Number)this.maxTurnSpeed.get()).floatValue())), 0);
                }
                if (!Intrinsics.areEqual(MinecraftInstance.mc.field_71476_x.field_72308_g, entity) || System.currentTimeMillis() - this.leftLastSwing < this.leftDelay) continue;
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK)));
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C0APacketAnimation()));
                if (MinecraftInstance.mc.field_71442_b.field_78779_k != WorldSettings.GameType.SPECTATOR) {
                    MinecraftInstance.mc.field_71439_g.func_71059_n(entity);
                }
                this.leftLastSwing = System.currentTimeMillis();
                this.leftDelay = TimeUtils.INSTANCE.randomClickDelay(20, 20);
            }
        }
        if (this.checkTimer.hasTimePassed(1000L)) {
            this.checked = false;
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement && ((C08PacketPlayerBlockPlacement)packet).func_149574_g().func_77973_b() instanceof ItemFireball) {
            this.checkTimer.reset();
            this.checked = true;
        }
    }

    public final float getDistance() {
        return this.distance;
    }

    public final void setDistance(float f) {
        this.distance = f;
    }

    @NotNull
    public final String getDisplayName() {
        String string = this.displayName;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException("displayName");
        return null;
    }

    public final void setDisplayName(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.displayName = string;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        ScaledResolution t = new ScaledResolution(MinecraftInstance.mc);
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            String name = entity.func_70005_c_();
            if (!Intrinsics.areEqual(name, "Fireball")) continue;
            this.distance = (float)Math.floor(MinecraftInstance.mc.field_71439_g.func_70032_d(entity));
            Intrinsics.checkNotNullExpressionValue(name, "name");
            this.setDisplayName(name);
            float scale = ((Number)this.scaleValue.get()).floatValue();
            double entX = entity.field_70165_t;
            double entZ = entity.field_70161_v;
            double px = MinecraftInstance.mc.field_71439_g.field_70165_t;
            double pz = MinecraftInstance.mc.field_71439_g.field_70161_v;
            float pYaw = MinecraftInstance.mc.field_71439_g.field_70177_z;
            float radius = ((Number)this.radiusValue.get()).floatValue();
            double yaw = Math.toRadians(this.getRotations(entX, entZ, px, pz) - (double)pYaw);
            double arrowX = (double)(t.func_78326_a() / 2) + (double)radius * Math.sin(yaw);
            double arrowY = (double)(t.func_78328_b() / 2) - (double)radius * Math.cos(yaw);
            double textX = (double)(t.func_78326_a() / 2) + (double)(radius - (float)13) * Math.sin(yaw);
            double textY = (double)(t.func_78328_b() / 2) - (double)(radius - (float)13) * Math.cos(yaw);
            double imgX = (double)(t.func_78326_a() / 2) + (double)(radius - (float)18) * Math.sin(yaw);
            double imgY = (double)(t.func_78328_b() / 2) - (double)(radius - (float)18) * Math.cos(yaw);
            double d = arrowY - (double)(t.func_78328_b() / 2);
            double d2 = arrowX - (double)(t.func_78326_a() / 2);
            double arrowAngle = Math.atan2(d, d2);
            this.drawArrow(arrowX, arrowY, arrowAngle, 3.0, 100.0);
            GlStateManager.func_179131_c((float)255.0f, (float)255.0f, (float)255.0f, (float)255.0f);
            if (Intrinsics.areEqual(this.getDisplayName(), "Fireball") && ((Boolean)this.fireBall.get()).booleanValue()) {
                GlStateManager.func_179152_a((float)scale, (float)scale, (float)scale);
                RenderUtils.drawImage(new ResourceLocation("textures/items/fireball.png"), (int)(imgX / (double)scale - (double)5), (int)(imgY / (double)scale - (double)5), 32, 32);
                GlStateManager.func_179152_a((float)(1.0f / scale), (float)(1.0f / scale), (float)(1.0f / scale));
            }
            GlStateManager.func_179152_a((float)scale, (float)scale, (float)scale);
            Fonts.minecraftFont.func_175063_a("" + this.distance + 'm', (float)(textX / (double)scale - (double)(Fonts.minecraftFont.func_78256_a("" + this.distance + 'm') / 2)), (float)(textY / (double)scale - (double)4), -1);
            GlStateManager.func_179152_a((float)(1.0f / scale), (float)(1.0f / scale), (float)(1.0f / scale));
        }
    }

    public final void drawArrow(double x, double y, double angle, double size, double degrees) {
        double arrowSize = size * (double)2;
        double arrowX = x - arrowSize * Math.cos(angle);
        double arrowY = y - arrowSize * Math.sin(angle);
        double arrowAngle1 = angle + Math.toRadians(degrees);
        double arrowAngle2 = angle - Math.toRadians(degrees);
        RenderUtils.drawLine(x, y, arrowX + arrowSize * Math.cos(arrowAngle1), arrowY + arrowSize * Math.sin(arrowAngle1), (float)size);
        RenderUtils.drawLine(x, y, arrowX + arrowSize * Math.cos(arrowAngle2), arrowY + arrowSize * Math.sin(arrowAngle2), (float)size);
    }

    public final double getRotations(double eX, double eZ, double x, double z) {
        double xDiff = eX - x;
        double zDiff = eZ - z;
        double yaw = -(Math.atan2(xDiff, zDiff) * 57.29577951308232);
        return yaw;
    }

    public static final /* synthetic */ BoolValue access$getHitfireball$p(AntiFireBall $this) {
        return $this.hitfireball;
    }

    public static final /* synthetic */ FloatValue access$getMinTurnSpeed$p(AntiFireBall $this) {
        return $this.minTurnSpeed;
    }

    public static final /* synthetic */ FloatValue access$getMaxTurnSpeed$p(AntiFireBall $this) {
        return $this.maxTurnSpeed;
    }
}

