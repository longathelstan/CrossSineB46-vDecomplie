/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.ColorManager;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="TargetStrafe", category=ModuleCategory.MOVEMENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010&\u001a\u00020\u000b2\b\u0010'\u001a\u0004\u0018\u00010 H\u0002J\b\u0010(\u001a\u00020\u000bH\u0002J\u000e\u0010)\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020+J\u0018\u0010,\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.H\u0002J\u000e\u00100\u001a\u00020\u000b2\u0006\u0010*\u001a\u000201J\u0010\u00102\u001a\u0002032\u0006\u0010*\u001a\u00020+H\u0007J\u0010\u00104\u001a\u0002032\u0006\u0010*\u001a\u000205H\u0007J\u0010\u00106\u001a\u0002032\u0006\u0010*\u001a\u000207H\u0007J\u0006\u00108\u001a\u00020\u000bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\r\"\u0004\b\u0013\u0010\u000fR\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00069"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/movement/TargetStrafe;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "callBackYaw", "", "getCallBackYaw", "()D", "setCallBackYaw", "(D)V", "direction", "doStrafe", "", "getDoStrafe", "()Z", "setDoStrafe", "(Z)V", "holdSpaceValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "isEnabled", "setEnabled", "lineWidthValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "ongroundValue", "onlyFlightValue", "onlySpeedValue", "radiusModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "radiusValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "renderModeValue", "targetEntity", "Lnet/minecraft/entity/EntityLivingBase;", "getTargetEntity", "()Lnet/minecraft/entity/EntityLivingBase;", "setTargetEntity", "(Lnet/minecraft/entity/EntityLivingBase;)V", "thirdPersonViewValue", "canStrafe", "target", "checkVoid", "doMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "isVoid", "xPos", "", "zPos", "modifyStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onMove", "", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "toggleStrafe", "CrossSine"})
public final class TargetStrafe
extends Module {
    @NotNull
    private final BoolValue thirdPersonViewValue = new BoolValue("ThirdPersonView", false);
    @NotNull
    private final ListValue renderModeValue;
    @NotNull
    private final Value<Float> lineWidthValue;
    @NotNull
    private final ListValue radiusModeValue;
    @NotNull
    private final FloatValue radiusValue;
    @NotNull
    private final BoolValue ongroundValue;
    @NotNull
    private final BoolValue holdSpaceValue;
    @NotNull
    private final BoolValue onlySpeedValue;
    @NotNull
    private final BoolValue onlyFlightValue;
    private double direction;
    @Nullable
    private EntityLivingBase targetEntity;
    private boolean isEnabled;
    private boolean doStrafe;
    private double callBackYaw;

    public TargetStrafe() {
        String[] stringArray = new String[]{"Circle", "Polygon", "None"};
        this.renderModeValue = new ListValue("RenderMode", stringArray, "Polygon");
        this.lineWidthValue = new FloatValue("LineWidth", 1.0f, 1.0f, 10.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ TargetStrafe this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return !TargetStrafe.access$getRenderModeValue$p(this.this$0).equals("None");
            }
        });
        stringArray = new String[]{"Normal", "Strict"};
        this.radiusModeValue = new ListValue("RadiusMode", stringArray, "Normal");
        this.radiusValue = new FloatValue("Radius", 0.5f, 0.1f, 5.0f);
        this.ongroundValue = new BoolValue("OnlyOnGround", false);
        this.holdSpaceValue = new BoolValue("HoldSpace", false);
        this.onlySpeedValue = new BoolValue("OnlySpeed", true);
        this.onlyFlightValue = new BoolValue("OnlyFlight", true);
        this.direction = -1.0;
    }

    @Nullable
    public final EntityLivingBase getTargetEntity() {
        return this.targetEntity;
    }

    public final void setTargetEntity(@Nullable EntityLivingBase entityLivingBase) {
        this.targetEntity = entityLivingBase;
    }

    public final boolean isEnabled() {
        return this.isEnabled;
    }

    public final void setEnabled(boolean bl) {
        this.isEnabled = bl;
    }

    public final boolean getDoStrafe() {
        return this.doStrafe;
    }

    public final void setDoStrafe(boolean bl) {
        this.doStrafe = bl;
    }

    public final double getCallBackYaw() {
        return this.callBackYaw;
    }

    public final void setCallBackYaw(double d) {
        this.callBackYaw = d;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!Intrinsics.areEqual(this.renderModeValue.get(), "None") && this.canStrafe(this.targetEntity)) {
            if (this.targetEntity == null || !this.doStrafe) {
                return;
            }
            int[] nArray = new int[]{0};
            int[] counter = nArray;
            if (StringsKt.equals((String)this.renderModeValue.get(), "Circle", true)) {
                GL11.glPushMatrix();
                GL11.glDisable((int)3553);
                GL11.glEnable((int)2848);
                GL11.glEnable((int)2881);
                GL11.glEnable((int)2832);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glHint((int)3154, (int)4354);
                GL11.glHint((int)3155, (int)4354);
                GL11.glHint((int)3153, (int)4354);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glLineWidth((float)((Number)this.lineWidthValue.get()).floatValue());
                GL11.glBegin((int)3);
                EntityLivingBase entityLivingBase = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase);
                double d = entityLivingBase.field_70142_S;
                EntityLivingBase entityLivingBase2 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase2);
                double d2 = entityLivingBase2.field_70165_t;
                EntityLivingBase entityLivingBase3 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase3);
                double x = d + (d2 - entityLivingBase3.field_70142_S) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78730_l;
                EntityLivingBase entityLivingBase4 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase4);
                double d3 = entityLivingBase4.field_70137_T;
                EntityLivingBase entityLivingBase5 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase5);
                double d4 = entityLivingBase5.field_70163_u;
                EntityLivingBase entityLivingBase6 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase6);
                double y = d3 + (d4 - entityLivingBase6.field_70137_T) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78731_m;
                EntityLivingBase entityLivingBase7 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase7);
                double d5 = entityLivingBase7.field_70136_U;
                EntityLivingBase entityLivingBase8 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase8);
                double d6 = entityLivingBase8.field_70161_v;
                EntityLivingBase entityLivingBase9 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase9);
                double z = d5 + (d6 - entityLivingBase9.field_70136_U) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78728_n;
                int n = 0;
                while (n < 360) {
                    int i = n++;
                    Color rainbow = new Color(Color.HSBtoRGB((float)(((double)MinecraftInstance.mc.field_71439_g.field_70173_aa / 70.0 + Math.sin((double)i / 50.0 * 1.75)) % (double)1.0f), 0.7f, 1.0f));
                    GL11.glColor3f((float)((float)rainbow.getRed() / 255.0f), (float)((float)rainbow.getGreen() / 255.0f), (float)((float)rainbow.getBlue() / 255.0f));
                    GL11.glVertex3d((double)(x + ((Number)this.radiusValue.get()).doubleValue() * Math.cos((double)i * (Math.PI * 2) / 45.0)), (double)y, (double)(z + ((Number)this.radiusValue.get()).doubleValue() * Math.sin((double)i * (Math.PI * 2) / 45.0)));
                }
                GL11.glEnd();
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)2929);
                GL11.glDisable((int)2848);
                GL11.glDisable((int)2881);
                GL11.glEnable((int)2832);
                GL11.glEnable((int)3553);
                GL11.glPopMatrix();
            } else {
                float rad = ((Number)this.radiusValue.get()).floatValue();
                GL11.glPushMatrix();
                GL11.glDisable((int)3553);
                RenderUtils.startDrawing();
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glLineWidth((float)((Number)this.lineWidthValue.get()).floatValue());
                GL11.glBegin((int)3);
                EntityLivingBase entityLivingBase = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase);
                double d = entityLivingBase.field_70142_S;
                EntityLivingBase entityLivingBase10 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase10);
                double d7 = entityLivingBase10.field_70165_t;
                EntityLivingBase entityLivingBase11 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase11);
                double x = d + (d7 - entityLivingBase11.field_70142_S) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78730_l;
                EntityLivingBase entityLivingBase12 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase12);
                double d8 = entityLivingBase12.field_70137_T;
                EntityLivingBase entityLivingBase13 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase13);
                double d9 = entityLivingBase13.field_70163_u;
                EntityLivingBase entityLivingBase14 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase14);
                double y = d8 + (d9 - entityLivingBase14.field_70137_T) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78731_m;
                EntityLivingBase entityLivingBase15 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase15);
                double d10 = entityLivingBase15.field_70136_U;
                EntityLivingBase entityLivingBase16 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase16);
                double d11 = entityLivingBase16.field_70161_v;
                EntityLivingBase entityLivingBase17 = this.targetEntity;
                Intrinsics.checkNotNull(entityLivingBase17);
                double z = d10 + (d11 - entityLivingBase17.field_70136_U) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78728_n;
                int n = 0;
                while (n < 11) {
                    int i = n++;
                    counter[0] = counter[0] + 1;
                    Color rainbow = new Color(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                    GL11.glColor3f((float)((float)rainbow.getRed() / 255.0f), (float)((float)rainbow.getGreen() / 255.0f), (float)((float)rainbow.getBlue() / 255.0f));
                    if ((double)rad < 0.8 && (double)rad > 0.0) {
                        GL11.glVertex3d((double)(x + (double)rad * Math.cos((double)i * (Math.PI * 2) / 3.0)), (double)y, (double)(z + (double)rad * Math.sin((double)i * (Math.PI * 2) / 3.0)));
                    }
                    if ((double)rad < 1.5 && (double)rad > 0.7) {
                        counter[0] = counter[0] + 1;
                        RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        GL11.glVertex3d((double)(x + (double)rad * Math.cos((double)i * (Math.PI * 2) / 4.0)), (double)y, (double)(z + (double)rad * Math.sin((double)i * (Math.PI * 2) / 4.0)));
                    }
                    if ((double)rad < 2.0 && (double)rad > 1.4) {
                        counter[0] = counter[0] + 1;
                        RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        GL11.glVertex3d((double)(x + (double)rad * Math.cos((double)i * (Math.PI * 2) / 5.0)), (double)y, (double)(z + (double)rad * Math.sin((double)i * (Math.PI * 2) / 5.0)));
                    }
                    if ((double)rad < 2.4 && (double)rad > 1.9) {
                        counter[0] = counter[0] + 1;
                        RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        GL11.glVertex3d((double)(x + (double)rad * Math.cos((double)i * (Math.PI * 2) / 6.0)), (double)y, (double)(z + (double)rad * Math.sin((double)i * (Math.PI * 2) / 6.0)));
                    }
                    if ((double)rad < 2.7 && (double)rad > 2.3) {
                        counter[0] = counter[0] + 1;
                        RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        GL11.glVertex3d((double)(x + (double)rad * Math.cos((double)i * (Math.PI * 2) / 7.0)), (double)y, (double)(z + (double)rad * Math.sin((double)i * (Math.PI * 2) / 7.0)));
                    }
                    if ((double)rad < 6.0 && (double)rad > 2.6) {
                        counter[0] = counter[0] + 1;
                        RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        GL11.glVertex3d((double)(x + (double)rad * Math.cos((double)i * (Math.PI * 2) / 8.0)), (double)y, (double)(z + (double)rad * Math.sin((double)i * (Math.PI * 2) / 8.0)));
                    }
                    if ((double)rad < 7.0 && (double)rad > 5.9) {
                        counter[0] = counter[0] + 1;
                        RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                        GL11.glVertex3d((double)(x + (double)rad * Math.cos((double)i * (Math.PI * 2) / 9.0)), (double)y, (double)(z + (double)rad * Math.sin((double)i * (Math.PI * 2) / 9.0)));
                    }
                    if (!((double)rad < 11.0) || !((double)rad > 6.9)) continue;
                    counter[0] = counter[0] + 1;
                    RenderUtils.glColor(ColorManager.astolfoRainbow(counter[0] * 100, 5, 107));
                    GL11.glVertex3d((double)(x + (double)rad * Math.cos((double)i * (Math.PI * 2) / 10.0)), (double)y, (double)(z + (double)rad * Math.sin((double)i * (Math.PI * 2) / 10.0)));
                }
                GL11.glEnd();
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)2929);
                RenderUtils.stopDrawing();
                GL11.glEnable((int)3553);
                GL11.glPopMatrix();
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.doStrafe && (!((Boolean)this.ongroundValue.get()).booleanValue() || MinecraftInstance.mc.field_71439_g.field_70122_E)) {
            if (!this.canStrafe(this.targetEntity)) {
                this.isEnabled = false;
                return;
            }
            boolean aroundVoid = false;
            int n = -1;
            while (n < 1) {
                int x = n++;
                int n2 = -1;
                while (n2 < 1) {
                    int z;
                    if (!this.isVoid(x, z = n2++)) continue;
                    aroundVoid = true;
                }
            }
            if (aroundVoid) {
                this.direction *= (double)-1;
            }
            int _1IlIll1 = 0;
            if (StringsKt.equals((String)this.radiusModeValue.get(), "Strict", true)) {
                _1IlIll1 = 1;
            }
            EntityLivingBase entityLivingBase = this.targetEntity;
            Intrinsics.checkNotNull(entityLivingBase);
            MovementUtils.INSTANCE.doTargetStrafe(entityLivingBase, (float)this.direction, ((Number)this.radiusValue.get()).floatValue(), event, _1IlIll1);
            this.callBackYaw = RotationUtils.getRotationsEntity(this.targetEntity).getYaw();
            this.isEnabled = true;
            if (!((Boolean)this.thirdPersonViewValue.get()).booleanValue()) {
                return;
            }
            MinecraftInstance.mc.field_71474_y.field_74320_O = this.canStrafe(this.targetEntity) ? 3 : 0;
        } else {
            this.isEnabled = false;
            if (!((Boolean)this.thirdPersonViewValue.get()).booleanValue()) {
                return;
            }
            MinecraftInstance.mc.field_71474_y.field_74320_O = 3;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean canStrafe(EntityLivingBase target) {
        if (target == null) return false;
        if (((Boolean)this.holdSpaceValue.get()).booleanValue()) {
            if (!MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) return false;
        }
        if (((Boolean)this.onlySpeedValue.get()).booleanValue()) {
            Speed speed = CrossSine.INSTANCE.getModuleManager().get(Speed.class);
            Intrinsics.checkNotNull(speed);
            if (!speed.getState()) return false;
        }
        if ((Boolean)this.onlyFlightValue.get() == false) return true;
        Flight flight = CrossSine.INSTANCE.getModuleManager().get(Flight.class);
        Intrinsics.checkNotNull(flight);
        if (!flight.getState()) return false;
        return true;
    }

    public final boolean modifyStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.isEnabled || event.isCancelled()) {
            return false;
        }
        MovementUtils.INSTANCE.strafe();
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean toggleStrafe() {
        if (this.targetEntity == null) return false;
        if (((Boolean)this.holdSpaceValue.get()).booleanValue()) {
            if (!MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) return false;
        }
        if (((Boolean)this.onlySpeedValue.get()).booleanValue()) {
            Speed speed = CrossSine.INSTANCE.getModuleManager().get(Speed.class);
            Intrinsics.checkNotNull(speed);
            if (!speed.getState()) return false;
        }
        if ((Boolean)this.onlyFlightValue.get() == false) return true;
        Flight flight = CrossSine.INSTANCE.getModuleManager().get(Flight.class);
        Intrinsics.checkNotNull(flight);
        if (!flight.getState()) return false;
        return true;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityLivingBase entityLivingBase = KillAura.INSTANCE.getState() ? KillAura.INSTANCE.getCurrentTarget() : (this.targetEntity = SilentAura.INSTANCE.getState() ? SilentAura.INSTANCE.getTarget() : CrossSine.INSTANCE.getCombatManager().getTarget());
        if (MinecraftInstance.mc.field_71439_g.field_70123_F) {
            this.direction = -this.direction;
            this.direction = this.direction >= 0.0 ? 1.0 : -1.0;
        }
    }

    public final boolean doMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.getState()) {
            return false;
        }
        if (this.doStrafe && (!((Boolean)this.ongroundValue.get()).booleanValue() || MinecraftInstance.mc.field_71439_g.field_70122_E)) {
            EntityLivingBase entityLivingBase = this.targetEntity;
            if (entityLivingBase == null) {
                return false;
            }
            EntityLivingBase entity = entityLivingBase;
            MovementUtils.doTargetStrafe$default(MovementUtils.INSTANCE, entity, (float)this.direction, ((Number)this.radiusValue.get()).floatValue(), event, 0, 16, null);
            this.callBackYaw = RotationUtils.getRotationsEntity(entity).getYaw();
            this.isEnabled = true;
        } else {
            this.isEnabled = false;
        }
        return true;
    }

    private final boolean checkVoid() {
        int n = -2;
        while (n < 3) {
            int x = n++;
            int n2 = -2;
            while (n2 < 3) {
                int z;
                if (!this.isVoid(x, z = n2++)) continue;
                return true;
            }
        }
        return false;
    }

    private final boolean isVoid(int xPos, int zPos) {
        if (MinecraftInstance.mc.field_71439_g.field_70163_u < 0.0) {
            return true;
        }
        for (int off = 0; off < (int)MinecraftInstance.mc.field_71439_g.field_70163_u + 2; off += 2) {
            AxisAlignedBB bb = MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d((double)xPos, -((double)off), (double)zPos);
            if (MinecraftInstance.mc.field_71441_e.func_72945_a((Entity)MinecraftInstance.mc.field_71439_g, bb).isEmpty()) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static final /* synthetic */ ListValue access$getRenderModeValue$p(TargetStrafe $this) {
        return $this.renderModeValue;
    }
}

