/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

@ModuleInfo(name="Breadcrumbs", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001(B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020'H\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Breadcrumbs;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "color", "Ljava/awt/Color;", "getColor", "()Ljava/awt/Color;", "colorAlphaValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "drawTargetsValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "drawThePlayerValue", "fadeTimeValue", "fadeValue", "lineWidthValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "onlyThirdPersonValue", "points", "", "", "Lnet/ccbluex/liquidbounce/features/module/modules/visual/Breadcrumbs$BreadcrumbPoint;", "precisionValue", "sphereList", "sphereScaleValue", "", "typeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "updatePoints", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "BreadcrumbPoint", "CrossSine"})
public final class Breadcrumbs
extends Module {
    @NotNull
    private final ListValue typeValue;
    @NotNull
    private final IntegerValue colorAlphaValue;
    @NotNull
    private final BoolValue fadeValue;
    @NotNull
    private final BoolValue drawThePlayerValue;
    @NotNull
    private final BoolValue drawTargetsValue;
    @NotNull
    private final IntegerValue fadeTimeValue;
    @NotNull
    private final IntegerValue precisionValue;
    @NotNull
    private final Value<Integer> lineWidthValue;
    @NotNull
    private final Value<Float> sphereScaleValue;
    @NotNull
    private final BoolValue onlyThirdPersonValue;
    @NotNull
    private final Map<Integer, List<BreadcrumbPoint>> points;
    private final int sphereList;

    public Breadcrumbs() {
        String[] stringArray = new String[]{"Line", "Rect", "Sphere", "Rise"};
        this.typeValue = new ListValue("Type", stringArray, "Line");
        this.colorAlphaValue = new IntegerValue("Alpha", 255, 0, 255);
        this.fadeValue = new BoolValue("Fade", true);
        this.drawThePlayerValue = new BoolValue("DrawThePlayer", true);
        this.drawTargetsValue = new BoolValue("DrawTargets", true);
        this.fadeTimeValue = new IntegerValue("FadeTime", 5, 1, 20);
        this.precisionValue = new IntegerValue("Precision", 4, 1, 20);
        this.lineWidthValue = new IntegerValue("LineWidth", 1, 1, 10).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Breadcrumbs this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Breadcrumbs.access$getTypeValue$p(this.this$0).equals("Line");
            }
        });
        this.sphereScaleValue = new FloatValue("SphereScale", 0.6f, 0.1f, 2.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Breadcrumbs this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Breadcrumbs.access$getTypeValue$p(this.this$0).equals("Sphere") || Breadcrumbs.access$getTypeValue$p(this.this$0).equals("Rise");
            }
        });
        this.onlyThirdPersonValue = new BoolValue("OnlyThirdPerson", true);
        this.points = new LinkedHashMap();
        this.sphereList = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.sphereList, (int)4864);
        Sphere shaft = new Sphere();
        shaft.setDrawStyle(100012);
        shaft.draw(0.3f, 25, 10);
        GL11.glEndList();
    }

    @NotNull
    public final Color getColor() {
        return ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, null);
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)this.onlyThirdPersonValue.get()).booleanValue() && MinecraftInstance.mc.field_71474_y.field_74320_O == 0) {
            return;
        }
        int fTime = ((Number)this.fadeTimeValue.get()).intValue() * 1000;
        long fadeSec = System.currentTimeMillis() - (long)fTime;
        float colorAlpha = ((Number)this.colorAlphaValue.get()).floatValue() / 255.0f;
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2929);
        MinecraftInstance.mc.field_71460_t.func_175072_h();
        double renderPosX = MinecraftInstance.mc.func_175598_ae().field_78730_l;
        double renderPosY = MinecraftInstance.mc.func_175598_ae().field_78731_m;
        double renderPosZ = MinecraftInstance.mc.func_175598_ae().field_78728_n;
        Map<Integer, List<BreadcrumbPoint>> $this$forEach$iv = this.points;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<Integer, List<BreadcrumbPoint>>> iterator2 = $this$forEach$iv.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<Integer, List<BreadcrumbPoint>> element$iv;
            Map.Entry<Integer, List<BreadcrumbPoint>> $dstr$_u24__u24$mutableList = element$iv = iterator2.next();
            boolean bl = false;
            List<BreadcrumbPoint> mutableList = $dstr$_u24__u24$mutableList.getValue();
            double lastPosX = 114514.0;
            double lastPosY = 114514.0;
            double lastPosZ = 114514.0;
            String string = ((String)this.typeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            Object object = string;
            if (Intrinsics.areEqual(object, "line")) {
                GL11.glLineWidth((float)((Number)this.lineWidthValue.get()).intValue());
                GL11.glEnable((int)2848);
                GL11.glBegin((int)3);
            } else if (Intrinsics.areEqual(object, "rect")) {
                GL11.glDisable((int)2884);
            }
            for (BreadcrumbPoint point : CollectionsKt.reversed((Iterable)mutableList)) {
                float f;
                if (((Boolean)this.fadeValue.get()).booleanValue()) {
                    float pct = (float)(point.getTime() - fadeSec) / (float)fTime;
                    if (pct < 0.0f || pct > 1.0f) {
                        mutableList.remove(point);
                        continue;
                    }
                    f = pct;
                } else {
                    f = 1.0f;
                }
                float alpha = f * colorAlpha;
                if (!this.typeValue.equals("Rise")) {
                    RenderUtils.glColor(point.getColor(), alpha);
                }
                String string2 = ((String)this.typeValue.get()).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                switch (string2) {
                    case "line": {
                        GL11.glVertex3d((double)(point.getX() - renderPosX), (double)(point.getY() - renderPosY), (double)(point.getZ() - renderPosZ));
                        break;
                    }
                    case "rect": {
                        if (!(lastPosX == 114514.0 && lastPosY == 114514.0 && lastPosZ == 114514.0)) {
                            GL11.glBegin((int)7);
                            GL11.glVertex3d((double)(point.getX() - renderPosX), (double)(point.getY() - renderPosY), (double)(point.getZ() - renderPosZ));
                            GL11.glVertex3d((double)lastPosX, (double)lastPosY, (double)lastPosZ);
                            GL11.glVertex3d((double)lastPosX, (double)(lastPosY + (double)MinecraftInstance.mc.field_71439_g.field_70131_O), (double)lastPosZ);
                            GL11.glVertex3d((double)(point.getX() - renderPosX), (double)(point.getY() - renderPosY + (double)MinecraftInstance.mc.field_71439_g.field_70131_O), (double)(point.getZ() - renderPosZ));
                            GL11.glEnd();
                        }
                        lastPosX = point.getX() - renderPosX;
                        lastPosY = point.getY() - renderPosY;
                        lastPosZ = point.getZ() - renderPosZ;
                        break;
                    }
                    case "sphere": {
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)(point.getX() - renderPosX), (double)(point.getY() - renderPosY), (double)(point.getZ() - renderPosZ));
                        GL11.glScalef((float)((Number)this.sphereScaleValue.get()).floatValue(), (float)((Number)this.sphereScaleValue.get()).floatValue(), (float)((Number)this.sphereScaleValue.get()).floatValue());
                        GL11.glCallList((int)this.sphereList);
                        GL11.glPopMatrix();
                        break;
                    }
                    case "rise": {
                        float circleScale = ((Number)this.sphereScaleValue.get()).floatValue();
                        RenderUtils.glColor(point.getColor(), 30);
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)(point.getX() - renderPosX), (double)(point.getY() - renderPosY), (double)(point.getZ() - renderPosZ));
                        GL11.glScalef((float)(circleScale * 1.3f), (float)(circleScale * 1.3f), (float)(circleScale * 1.3f));
                        GL11.glCallList((int)this.sphereList);
                        GL11.glPopMatrix();
                        RenderUtils.glColor(point.getColor(), 50);
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)(point.getX() - renderPosX), (double)(point.getY() - renderPosY), (double)(point.getZ() - renderPosZ));
                        GL11.glScalef((float)(circleScale * 0.8f), (float)(circleScale * 0.8f), (float)(circleScale * 0.8f));
                        GL11.glCallList((int)this.sphereList);
                        GL11.glPopMatrix();
                        RenderUtils.glColor(point.getColor(), alpha);
                        GL11.glPushMatrix();
                        GL11.glTranslated((double)(point.getX() - renderPosX), (double)(point.getY() - renderPosY), (double)(point.getZ() - renderPosZ));
                        GL11.glScalef((float)(circleScale * 0.4f), (float)(circleScale * 0.4f), (float)(circleScale * 0.4f));
                        GL11.glCallList((int)this.sphereList);
                        GL11.glPopMatrix();
                    }
                }
            }
            string = ((String)this.typeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            object = string;
            if (Intrinsics.areEqual(object, "line")) {
                GL11.glEnd();
                GL11.glDisable((int)2848);
                continue;
            }
            if (!Intrinsics.areEqual(object, "rect")) continue;
            GL11.glEnable((int)2884);
        }
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Map.Entry<Integer, List<BreadcrumbPoint>> element$iv;
        Intrinsics.checkNotNullParameter(event, "event");
        Object $this$forEach$iv = this.points;
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<Integer, List<BreadcrumbPoint>>> iterator2 = $this$forEach$iv.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<Integer, List<BreadcrumbPoint>> $dstr$id$_u24__u24 = element$iv = iterator2.next();
            boolean bl = false;
            int id = ((Number)$dstr$id$_u24__u24.getKey()).intValue();
            if (MinecraftInstance.mc.field_71441_e.func_73045_a(id) != null) continue;
            this.points.remove(id);
        }
        if (MinecraftInstance.mc.field_71439_g.field_70173_aa % ((Number)this.precisionValue.get()).intValue() != 0) {
            return;
        }
        if (((Boolean)this.drawTargetsValue.get()).booleanValue()) {
            $this$forEach$iv = MinecraftInstance.mc.field_71441_e.field_72996_f;
            Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "mc.theWorld.loadedEntityList");
            $this$forEach$iv = (Iterable)$this$forEach$iv;
            $i$f$forEach = false;
            iterator2 = $this$forEach$iv.iterator();
            while (iterator2.hasNext()) {
                element$iv = iterator2.next();
                Entity it = (Entity)element$iv;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                if (!EntityUtils.INSTANCE.isSelected(it, true)) continue;
                this.updatePoints((EntityLivingBase)it);
            }
        }
        if (((Boolean)this.drawThePlayerValue.get()).booleanValue()) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            this.updatePoints((EntityLivingBase)entityPlayerSP);
        }
    }

    private final void updatePoints(EntityLivingBase entity) {
        List list;
        List list2 = this.points.get(entity.func_145782_y());
        if (list2 == null) {
            List list3;
            List it = list3 = (List)new ArrayList();
            boolean bl = false;
            Map<Integer, List<BreadcrumbPoint>> map = this.points;
            Integer n = entity.func_145782_y();
            map.put(n, it);
            list = list3;
        } else {
            list = list2;
        }
        list.add((BreadcrumbPoint)new BreadcrumbPoint(entity.field_70165_t, entity.func_174813_aQ().field_72338_b, entity.field_70161_v, System.currentTimeMillis(), this.getColor().getRGB()));
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.points.clear();
    }

    @Override
    public void onDisable() {
        this.points.clear();
    }

    public static final /* synthetic */ ListValue access$getTypeValue$p(Breadcrumbs $this) {
        return $this.typeValue;
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Breadcrumbs$BreadcrumbPoint;", "", "x", "", "y", "z", "time", "", "color", "", "(DDDJI)V", "getColor", "()I", "getTime", "()J", "getX", "()D", "getY", "getZ", "CrossSine"})
    public static final class BreadcrumbPoint {
        private final double x;
        private final double y;
        private final double z;
        private final long time;
        private final int color;

        public BreadcrumbPoint(double x, double y, double z, long time, int color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.time = time;
            this.color = color;
        }

        public final double getX() {
            return this.x;
        }

        public final double getY() {
            return this.y;
        }

        public final double getZ() {
            return this.z;
        }

        public final long getTime() {
            return this.time;
        }

        public final int getColor() {
            return this.color;
        }
    }
}

