/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.RendererExtensionKt;
import net.ccbluex.liquidbounce.utils.render.BlendUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.WorldToScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

@ModuleInfo(name="ESP", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020 H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/ESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "colorGreenValue", "colorModeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "colorRedValue", "csgoDirectLineValue", "", "csgoShowHealthValue", "csgoShowHeldItemValue", "csgoShowNameValue", "csgoWidthValue", "", "decimalFormat", "Ljava/text/DecimalFormat;", "modeValue", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "outlineWidthValue", "getColor", "Ljava/awt/Color;", "entity", "Lnet/minecraft/entity/Entity;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"})
public final class ESP
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final Value<Float> outlineWidthValue;
    @NotNull
    private final Value<Boolean> csgoDirectLineValue;
    @NotNull
    private final Value<Boolean> csgoShowHealthValue;
    @NotNull
    private final Value<Boolean> csgoShowHeldItemValue;
    @NotNull
    private final Value<Boolean> csgoShowNameValue;
    @NotNull
    private final Value<Float> csgoWidthValue;
    @NotNull
    private final ListValue colorModeValue;
    @NotNull
    private final Value<Integer> colorRedValue;
    @NotNull
    private final Value<Integer> colorGreenValue;
    @NotNull
    private final Value<Integer> colorBlueValue;
    @NotNull
    private final DecimalFormat decimalFormat;

    public ESP() {
        String[] stringArray = new String[]{"Box", "OtherBox", "2D", "Real2D", "CSGO", "CSGO-Old", "Outline", "HealthLine"};
        this.modeValue = new ListValue("Mode", stringArray, "Outline");
        this.outlineWidthValue = new FloatValue("Outline-Width", 3.0f, 0.5f, 5.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("Outline");
            }
        });
        this.csgoDirectLineValue = new BoolValue("CSGO-DirectLine", false).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("CSGO");
            }
        });
        this.csgoShowHealthValue = new BoolValue("CSGO-ShowHealth", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("CSGO");
            }
        });
        this.csgoShowHeldItemValue = new BoolValue("CSGO-ShowHeldItem", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("CSGO");
            }
        });
        this.csgoShowNameValue = new BoolValue("CSGO-ShowName", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("CSGO");
            }
        });
        this.csgoWidthValue = new FloatValue("CSGOOld-Width", 2.0f, 0.5f, 5.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("CSGO-Old");
            }
        });
        stringArray = new String[]{"Name", "Armor", "Theme", "OFF"};
        this.colorModeValue = new ListValue("ColorMode", stringArray, "Name");
        this.colorRedValue = new IntegerValue("R", 255, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Intrinsics.areEqual(ESP.access$getColorModeValue$p(this.this$0).get(), "OFF");
            }
        });
        this.colorGreenValue = new IntegerValue("G", 255, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Intrinsics.areEqual(ESP.access$getColorModeValue$p(this.this$0).get(), "OFF");
            }
        });
        this.colorBlueValue = new IntegerValue("B", 255, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Intrinsics.areEqual(ESP.access$getColorModeValue$p(this.this$0).get(), "OFF");
            }
        });
        this.decimalFormat = new DecimalFormat("0.0");
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        boolean need2dTranslate;
        Intrinsics.checkNotNullParameter(event, "event");
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String mode2 = string;
        Matrix4f mvMatrix = WorldToScreen.getMatrix(2982);
        Matrix4f projectionMatrix = WorldToScreen.getMatrix(2983);
        boolean bl = need2dTranslate = Intrinsics.areEqual(mode2, "csgo") || Intrinsics.areEqual(mode2, "real2d") || Intrinsics.areEqual(mode2, "csgo-old");
        if (need2dTranslate) {
            GL11.glPushAttrib((int)8192);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glMatrixMode((int)5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho((double)0.0, (double)MinecraftInstance.mc.field_71443_c, (double)MinecraftInstance.mc.field_71440_d, (double)0.0, (double)-1.0, (double)1.0);
            GL11.glMatrixMode((int)5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glDisable((int)2929);
            GL11.glBlendFunc((int)770, (int)771);
            GlStateManager.func_179098_w();
            GlStateManager.func_179132_a((boolean)true);
            GL11.glLineWidth((float)1.0f);
        }
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            Intrinsics.checkNotNullExpressionValue(entity, "entity");
            if (!EntityUtils.INSTANCE.isSelected(entity, true)) continue;
            EntityLivingBase entityLiving = (EntityLivingBase)entity;
            Color color = this.getColor((Entity)entityLiving);
            block10 : switch (mode2) {
                case "box": 
                case "otherbox": {
                    RenderUtils.drawEntityBox(entity, color, !Intrinsics.areEqual(mode2, "otherbox"), true, ((Number)this.outlineWidthValue.get()).floatValue());
                    break;
                }
                case "outline": {
                    RenderUtils.drawEntityBox(entity, color, true, false, ((Number)this.outlineWidthValue.get()).floatValue());
                    break;
                }
                case "2d": {
                    RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
                    Timer timer = MinecraftInstance.mc.field_71428_T;
                    double posX = entityLiving.field_70142_S + (entityLiving.field_70165_t - entityLiving.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b;
                    double posY = entityLiving.field_70137_T + (entityLiving.field_70163_u - entityLiving.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c;
                    double posZ = entityLiving.field_70136_U + (entityLiving.field_70161_v - entityLiving.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d;
                    RenderUtils.draw2D(entityLiving, posX, posY, posZ, color.getRGB(), Color.BLACK.getRGB());
                    break;
                }
                case "csgo-old": 
                case "real2d": 
                case "csgo": {
                    RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
                    Timer timer = MinecraftInstance.mc.field_71428_T;
                    AxisAlignedBB bb = entityLiving.func_174813_aQ().func_72317_d(-entityLiving.field_70165_t, -entityLiving.field_70163_u, -entityLiving.field_70161_v).func_72317_d(entityLiving.field_70142_S + (entityLiving.field_70165_t - entityLiving.field_70142_S) * (double)timer.field_74281_c, entityLiving.field_70137_T + (entityLiving.field_70163_u - entityLiving.field_70137_T) * (double)timer.field_74281_c, entityLiving.field_70136_U + (entityLiving.field_70161_v - entityLiving.field_70136_U) * (double)timer.field_74281_c).func_72317_d(-renderManager.field_78725_b, -renderManager.field_78726_c, -renderManager.field_78723_d);
                    double[][] posY = new double[8][];
                    double[] dArray = new double[]{bb.field_72340_a, bb.field_72338_b, bb.field_72339_c};
                    posY[0] = dArray;
                    dArray = new double[]{bb.field_72340_a, bb.field_72337_e, bb.field_72339_c};
                    posY[1] = dArray;
                    dArray = new double[]{bb.field_72336_d, bb.field_72337_e, bb.field_72339_c};
                    posY[2] = dArray;
                    dArray = new double[]{bb.field_72336_d, bb.field_72338_b, bb.field_72339_c};
                    posY[3] = dArray;
                    dArray = new double[]{bb.field_72340_a, bb.field_72338_b, bb.field_72334_f};
                    posY[4] = dArray;
                    dArray = new double[]{bb.field_72340_a, bb.field_72337_e, bb.field_72334_f};
                    posY[5] = dArray;
                    dArray = new double[]{bb.field_72336_d, bb.field_72337_e, bb.field_72334_f};
                    posY[6] = dArray;
                    dArray = new double[]{bb.field_72336_d, bb.field_72338_b, bb.field_72334_f};
                    posY[7] = dArray;
                    double[][] boxVertices = posY;
                    float minX = MinecraftInstance.mc.field_71443_c;
                    float minY = MinecraftInstance.mc.field_71440_d;
                    float maxX = 0.0f;
                    float maxY = 0.0f;
                    int n = ((Object[])boxVertices).length;
                    for (int i = 0; i < n; ++i) {
                        double[] boxVertex = boxVertices[i];
                        Vector2f vector2f = WorldToScreen.worldToScreen(new Vector3f((float)boxVertex[0], (float)boxVertex[1], (float)boxVertex[2]), mvMatrix, projectionMatrix, MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71440_d);
                        if (vector2f == null) continue;
                        Vector2f screenPos = vector2f;
                        minX = RangesKt.coerceAtMost(screenPos.x, minX);
                        minY = RangesKt.coerceAtMost(screenPos.y, minY);
                        maxX = RangesKt.coerceAtLeast(screenPos.x, maxX);
                        maxY = RangesKt.coerceAtLeast(screenPos.y, maxY);
                    }
                    if (minX == (float)MinecraftInstance.mc.field_71443_c || minY == (float)MinecraftInstance.mc.field_71440_d || maxX == 0.0f || maxY == 0.0f) break;
                    int n2 = -1;
                    switch (mode2.hashCode()) {
                        case -1518963662: {
                            if (mode2.equals("csgo-old")) {
                                n2 = 1;
                            }
                            break;
                        }
                        case -934973296: {
                            if (mode2.equals("real2d")) {
                                n2 = 2;
                            }
                            break;
                        }
                        case 3063128: {
                            if (mode2.equals("csgo")) {
                                n2 = 3;
                            }
                            break;
                        }
                    }
                    switch (n2) {
                        case 3: {
                            RenderUtils.glColor(color);
                            if (!this.csgoDirectLineValue.get().booleanValue()) {
                                float distX = (maxX - minX) / 3.0f;
                                float distY = (maxY - minY) / 3.0f;
                                GL11.glBegin((int)3);
                                GL11.glVertex2f((float)minX, (float)(minY + distY));
                                GL11.glVertex2f((float)minX, (float)minY);
                                GL11.glVertex2f((float)(minX + distX), (float)minY);
                                GL11.glEnd();
                                GL11.glBegin((int)3);
                                GL11.glVertex2f((float)minX, (float)(maxY - distY));
                                GL11.glVertex2f((float)minX, (float)maxY);
                                GL11.glVertex2f((float)(minX + distX), (float)maxY);
                                GL11.glEnd();
                                GL11.glBegin((int)3);
                                GL11.glVertex2f((float)(maxX - distX), (float)minY);
                                GL11.glVertex2f((float)maxX, (float)minY);
                                GL11.glVertex2f((float)maxX, (float)(minY + distY));
                                GL11.glEnd();
                                GL11.glBegin((int)3);
                                GL11.glVertex2f((float)(maxX - distX), (float)maxY);
                                GL11.glVertex2f((float)maxX, (float)maxY);
                                GL11.glVertex2f((float)maxX, (float)(maxY - distY));
                                GL11.glEnd();
                            } else {
                                GL11.glBegin((int)2);
                                GL11.glVertex2f((float)minX, (float)minY);
                                GL11.glVertex2f((float)minX, (float)maxY);
                                GL11.glVertex2f((float)maxX, (float)maxY);
                                GL11.glVertex2f((float)maxX, (float)minY);
                                GL11.glEnd();
                            }
                            if (this.csgoShowHealthValue.get().booleanValue()) {
                                float barHeight = (maxY - minY) * (1.0f - entityLiving.func_110143_aJ() / entityLiving.func_110138_aP());
                                GL11.glColor4f((float)0.1f, (float)1.0f, (float)0.1f, (float)1.0f);
                                GL11.glBegin((int)7);
                                GL11.glVertex2f((float)(maxX + 2.0f), (float)(minY + barHeight));
                                GL11.glVertex2f((float)(maxX + 2.0f), (float)maxY);
                                GL11.glVertex2f((float)(maxX + 3.0f), (float)maxY);
                                GL11.glVertex2f((float)(maxX + 3.0f), (float)(minY + barHeight));
                                GL11.glEnd();
                                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                                GL11.glEnable((int)3553);
                                GL11.glEnable((int)2929);
                                MinecraftInstance.mc.field_71466_p.func_175065_a(Intrinsics.stringPlus(this.decimalFormat.format(Float.valueOf(entityLiving.func_110143_aJ())), "\u00a7c\u2764"), maxX + 4.0f, minY + barHeight, ColorUtils.healthColor$default(ColorUtils.INSTANCE, entityLiving.func_110143_aJ(), entityLiving.func_110138_aP(), 0, 4, null).getRGB(), false);
                                GL11.glDisable((int)3553);
                                GL11.glDisable((int)2929);
                                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            }
                            if (this.csgoShowHeldItemValue.get().booleanValue()) {
                                ItemStack itemStack = entityLiving.func_70694_bm();
                                if ((itemStack == null ? null : itemStack.func_82833_r()) != null) {
                                    GL11.glEnable((int)3553);
                                    GL11.glEnable((int)2929);
                                    Object barHeight = MinecraftInstance.mc.field_71466_p;
                                    Intrinsics.checkNotNullExpressionValue(barHeight, "mc.fontRendererObj");
                                    FontRenderer fontRenderer = barHeight;
                                    barHeight = entityLiving.func_70694_bm().func_82833_r();
                                    Intrinsics.checkNotNullExpressionValue(barHeight, "entityLiving.heldItem.displayName");
                                    RendererExtensionKt.drawCenteredString(fontRenderer, (String)barHeight, minX + (maxX - minX) / 2.0f, maxY + 2.0f, -1);
                                    GL11.glDisable((int)3553);
                                    GL11.glDisable((int)2929);
                                }
                            }
                            if (!this.csgoShowNameValue.get().booleanValue()) break block10;
                            GL11.glEnable((int)3553);
                            GL11.glEnable((int)2929);
                            Object barHeight = MinecraftInstance.mc.field_71466_p;
                            Intrinsics.checkNotNullExpressionValue(barHeight, "mc.fontRendererObj");
                            FontRenderer fontRenderer = barHeight;
                            barHeight = entityLiving.func_145748_c_().func_150254_d();
                            Intrinsics.checkNotNullExpressionValue(barHeight, "entityLiving.displayName.formattedText");
                            RendererExtensionKt.drawCenteredString(fontRenderer, (String)barHeight, minX + (maxX - minX) / 2.0f, minY - 12.0f, -1);
                            GL11.glDisable((int)3553);
                            GL11.glDisable((int)2929);
                            break;
                        }
                        case 2: {
                            RenderUtils.drawRect(minX - 1.0f, minY - 1.0f, minX, maxY, color);
                            RenderUtils.drawRect(maxX, minY - 1.0f, maxX + 1.0f, maxY + 1.0f, color);
                            RenderUtils.drawRect(minX - 1.0f, maxY, maxX, maxY + 1.0f, color);
                            RenderUtils.drawRect(minX - 1.0f, minY - 1.0f, maxX, minY, color);
                            break;
                        }
                        case 1: {
                            float width = ((Number)this.csgoWidthValue.get()).floatValue() * ((maxY - minY) / (float)50);
                            RenderUtils.drawRect(minX - width, minY - width, minX, maxY, color);
                            RenderUtils.drawRect(maxX, minY - width, maxX + width, maxY + width, color);
                            RenderUtils.drawRect(minX - width, maxY, maxX, maxY + width, color);
                            RenderUtils.drawRect(minX - width, minY - width, maxX, minY, color);
                            float hpSize = (maxY + width - minY) * (entityLiving.func_110143_aJ() / entityLiving.func_110138_aP());
                            RenderUtils.drawRect(minX - width * (float)3, minY - width, minX - width * (float)2, maxY + width, Color.GRAY);
                            RenderUtils.drawRect(minX - width * (float)3, maxY - hpSize, minX - width * (float)2, maxY + width, ColorUtils.healthColor$default(ColorUtils.INSTANCE, entityLiving.func_110143_aJ(), entityLiving.func_110138_aP(), 0, 4, null));
                        }
                    }
                    break;
                }
                case "healthline": {
                    float r = ((EntityLivingBase)entity).func_110143_aJ() / ((EntityLivingBase)entity).func_110138_aP();
                    GL11.glPushMatrix();
                    RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
                    Timer timer = MinecraftInstance.mc.field_71428_T;
                    GL11.glTranslated((double)(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b), (double)(entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c - 0.2), (double)(entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d));
                    GL11.glRotated((double)(-MinecraftInstance.mc.func_175598_ae().field_78735_i), (double)0.0, (double)1.0, (double)0.0);
                    int[] nArray = new int[]{2896, 2929};
                    RenderUtils.disableGlCap(nArray);
                    GL11.glScalef((float)0.03f, (float)0.03f, (float)0.03f);
                    Gui.func_73734_a((int)21, (int)-1, (int)26, (int)75, (int)Color.black.getRGB());
                    Gui.func_73734_a((int)22, (int)((int)((float)74 * r)), (int)25, (int)74, (int)Color.darkGray.getRGB());
                    Gui.func_73734_a((int)22, (int)0, (int)25, (int)((int)((float)74 * r)), (int)BlendUtils.getHealthColor(((EntityLivingBase)entity).func_110143_aJ(), ((EntityLivingBase)entity).func_110138_aP()).getRGB());
                    RenderUtils.enableGlCap(3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    RenderUtils.resetCaps();
                    GlStateManager.func_179117_G();
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glPopMatrix();
                }
            }
        }
        if (need2dTranslate) {
            GL11.glEnable((int)2929);
            GL11.glMatrixMode((int)5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode((int)5888);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String mode2 = string;
        float partialTicks = event.getPartialTicks();
        Map entityMap = new HashMap();
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            Intrinsics.checkNotNullExpressionValue(entity, "entity");
            if (!EntityUtils.INSTANCE.isSelected(entity, false)) continue;
            EntityLivingBase entityLiving = (EntityLivingBase)entity;
            Color color = this.getColor((Entity)entityLiving);
            if (!entityMap.containsKey(color)) {
                Map map = entityMap;
                ArrayList arrayList = new ArrayList();
                map.put(color, arrayList);
            }
            Object v = entityMap.get(color);
            Intrinsics.checkNotNull(v);
            ((ArrayList)v).add(entityLiving);
        }
    }

    @NotNull
    public final Color getColor(@NotNull Entity entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (entity instanceof EntityLivingBase) {
            if (((EntityLivingBase)entity).field_70737_aN > 0) {
                Color color = Color.RED;
                Intrinsics.checkNotNullExpressionValue(color, "RED");
                return color;
            }
            if (EntityUtils.INSTANCE.isFriend(entity)) {
                Color color = Color.BLUE;
                Intrinsics.checkNotNullExpressionValue(color, "BLUE");
                return color;
            }
            if (Intrinsics.areEqual(this.colorModeValue.get(), "Name")) {
                String string = ((EntityLivingBase)entity).func_145748_c_().func_150254_d();
                Intrinsics.checkNotNullExpressionValue(string, "entity.displayName.formattedText");
                char[] cArray = string.toCharArray();
                Intrinsics.checkNotNullExpressionValue(cArray, "this as java.lang.String).toCharArray()");
                char[] chars = cArray;
                int n = 0;
                int n2 = chars.length;
                while (n < n2) {
                    int index2;
                    int i;
                    if (chars[i = n++] != '\u00a7' || i + 1 >= chars.length || (index2 = GameFontRenderer.Companion.getColorIndex(chars[i + 1])) < 0 || index2 > 15) continue;
                    return new Color(ColorUtils.hexColors[index2]);
                }
            } else if (Intrinsics.areEqual(this.colorModeValue.get(), "Armor")) {
                if (entity instanceof EntityPlayer) {
                    ItemStack itemStack = ((EntityPlayer)entity).field_71071_by.field_70460_b[3];
                    if (itemStack == null) {
                        return new Color(Integer.MAX_VALUE);
                    }
                    ItemStack entityHead = itemStack;
                    if (entityHead.func_77973_b() instanceof ItemArmor) {
                        Item item = entityHead.func_77973_b();
                        if (item == null) {
                            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
                        }
                        ItemArmor entityItemArmor = (ItemArmor)item;
                        return new Color(entityItemArmor.func_82814_b(entityHead));
                    }
                }
            } else if (Intrinsics.areEqual(this.colorModeValue.get(), "Theme")) {
                return ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, null);
            }
        }
        return new Color(((Number)this.colorRedValue.get()).intValue(), ((Number)this.colorGreenValue.get()).intValue(), ((Number)this.colorBlueValue.get()).intValue());
    }

    public static final /* synthetic */ ListValue access$getColorModeValue$p(ESP $this) {
        return $this.colorModeValue;
    }
}

