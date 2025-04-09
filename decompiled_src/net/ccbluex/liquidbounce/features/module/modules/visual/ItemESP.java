/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
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
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="ItemESP", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/ItemESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "colorGreenValue", "colorRedValue", "colorThemeClient", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "entityConvertedPointsMap", "", "Lnet/minecraft/entity/item/EntityItem;", "", "itemCount", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "nameTags", "onlyCount", "outlineWidth", "", "getColor", "Ljava/awt/Color;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"})
public final class ItemESP
extends Module {
    @NotNull
    private final Map<EntityItem, double[]> entityConvertedPointsMap = new HashMap();
    @NotNull
    private final BoolValue nameTags = new BoolValue("NameTag", false);
    @NotNull
    private final Value<Boolean> itemCount = new BoolValue("ItemCount", false).displayable(new Function0<Boolean>(this){
        final /* synthetic */ ItemESP this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)ItemESP.access$getNameTags$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Boolean> onlyCount = new BoolValue("OnlyCount", false).displayable(new Function0<Boolean>(this){
        final /* synthetic */ ItemESP this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)ItemESP.access$getNameTags$p(this.this$0).get();
        }
    });
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final Value<Float> outlineWidth;
    @NotNull
    private final Value<Integer> colorRedValue;
    @NotNull
    private final Value<Integer> colorGreenValue;
    @NotNull
    private final Value<Integer> colorBlueValue;
    @NotNull
    private final BoolValue colorThemeClient;

    public ItemESP() {
        String[] stringArray = new String[]{"Box", "OtherBox", "Outline", "LightBox"};
        this.modeValue = new ListValue("Mode", stringArray, "Box");
        this.outlineWidth = new FloatValue("Outline-Width", 3.0f, 0.5f, 5.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ItemESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return ItemESP.access$getModeValue$p(this.this$0).equals("Outline");
            }
        });
        this.colorRedValue = new IntegerValue("R", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ItemESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)ItemESP.access$getColorThemeClient$p(this.this$0).get() == false;
            }
        });
        this.colorGreenValue = new IntegerValue("G", 255, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ItemESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)ItemESP.access$getColorThemeClient$p(this.this$0).get() == false;
            }
        });
        this.colorBlueValue = new IntegerValue("B", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ ItemESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)ItemESP.access$getColorThemeClient$p(this.this$0).get() == false;
            }
        });
        this.colorThemeClient = new BoolValue("ClientTheme", true);
    }

    private final Color getColor() {
        return (Boolean)this.colorThemeClient.get() != false ? ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, null) : new Color(((Number)this.colorRedValue.get()).intValue(), ((Number)this.colorGreenValue.get()).intValue(), ((Number)this.colorBlueValue.get()).intValue());
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        Color color = this.getColor();
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityArrow)) continue;
            String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "box": {
                    RenderUtils.drawEntityBox(entity, color, true, true, ((Number)this.outlineWidth.get()).floatValue());
                    break;
                }
                case "otherbox": {
                    RenderUtils.drawEntityBox(entity, color, false, true, ((Number)this.outlineWidth.get()).floatValue());
                    break;
                }
                case "outline": {
                    RenderUtils.drawEntityBox(entity, color, true, false, ((Number)this.outlineWidth.get()).floatValue());
                }
            }
        }
        if (StringsKt.equals((String)this.modeValue.get(), "LightBox", true)) {
            for (Object o : MinecraftInstance.mc.field_71441_e.field_72996_f) {
                if (!(o instanceof EntityItem)) continue;
                Entity item = o;
                double x = item.field_70165_t - MinecraftInstance.mc.func_175598_ae().field_78725_b;
                double y = item.field_70163_u + 0.5 - MinecraftInstance.mc.func_175598_ae().field_78726_c;
                double z = item.field_70161_v - MinecraftInstance.mc.func_175598_ae().field_78723_d;
                GL11.glEnable((int)3042);
                GL11.glLineWidth((float)2.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.2, y - 0.3, z - 0.2, x + 0.2, y - 0.4, z + 0.2));
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.15f);
                RenderUtils.drawBoundingBox(new AxisAlignedBB(x - 0.2, y - 0.3, z - 0.2, x + 0.2, y - 0.4, z + 0.2));
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glDisable((int)3042);
            }
        }
        if (StringsKt.equals((String)this.modeValue.get(), "Exhibition", true)) {
            this.entityConvertedPointsMap.clear();
            float pTicks = MinecraftInstance.mc.field_71428_T.field_74281_c;
            for (Entity e2 : MinecraftInstance.mc.field_71441_e.func_72910_y()) {
                boolean bl;
                boolean bl2;
                void it;
                double d;
                if (!(e2 instanceof EntityItem)) continue;
                Entity ent = e2;
                double x = ent.field_70142_S + (ent.field_70165_t - ent.field_70142_S) * (double)pTicks;
                double cfr_ignored_0 = -MinecraftInstance.mc.func_175598_ae().field_78730_l + 0.36;
                double y = ent.field_70137_T + (ent.field_70163_u - ent.field_70137_T) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78731_m;
                double z = ent.field_70136_U + (ent.field_70161_v - ent.field_70136_U) * (double)pTicks;
                double cfr_ignored_1 = -MinecraftInstance.mc.func_175598_ae().field_78728_n + 0.36;
                double topY = 0.0;
                double d2 = d = (double)ent.field_70131_O + 0.15;
                double d3 = y;
                boolean bl3 = false;
                topY = it;
                Unit unit = Unit.INSTANCE;
                y = d3 + d;
                double[] convertedPoints = RenderUtils.convertTo2D(x, y, z);
                double[] convertedPoints2 = RenderUtils.convertTo2D(x - 0.36, y, z - 0.36);
                double xd = 0.0;
                boolean bl4 = bl2 = convertedPoints2 != null;
                if (_Assertions.ENABLED && !bl2) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                Intrinsics.checkNotNull(convertedPoints2);
                if (convertedPoints2[2] < 0.0 || convertedPoints2[2] >= 1.0) continue;
                x = ent.field_70142_S + (ent.field_70165_t - ent.field_70142_S) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78730_l - 0.36;
                z = ent.field_70136_U + (ent.field_70161_v - ent.field_70136_U) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78728_n - 0.36;
                double[] convertedPointsBottom = RenderUtils.convertTo2D(x, y, z);
                y = ent.field_70137_T + (ent.field_70163_u - ent.field_70137_T) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78731_m - 0.05;
                double[] convertedPointsx = RenderUtils.convertTo2D(x, y, z);
                x = ent.field_70142_S + (ent.field_70165_t - ent.field_70142_S) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78730_l - 0.36;
                z = ent.field_70136_U + (ent.field_70161_v - ent.field_70136_U) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78728_n + 0.36;
                double[] convertedPointsTop1 = RenderUtils.convertTo2D(x, topY, z);
                double[] convertedPointsx2 = RenderUtils.convertTo2D(x, y, z);
                x = ent.field_70142_S + (ent.field_70165_t - ent.field_70142_S) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78730_l + 0.36;
                z = ent.field_70136_U + (ent.field_70161_v - ent.field_70136_U) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78728_n + 0.36;
                double[] convertedPointsz = RenderUtils.convertTo2D(x, y, z);
                x = ent.field_70142_S + (ent.field_70165_t - ent.field_70142_S) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78730_l + 0.36;
                z = ent.field_70136_U + (ent.field_70161_v - ent.field_70136_U) * (double)pTicks - MinecraftInstance.mc.func_175598_ae().field_78728_n - 0.36;
                double[] convertedPointsTop2 = RenderUtils.convertTo2D(x, topY, z);
                double[] convertedPointsz2 = RenderUtils.convertTo2D(x, y, z);
                boolean bl5 = bl = convertedPoints != null;
                if (_Assertions.ENABLED && !bl) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                boolean bl6 = bl = convertedPointsx != null;
                if (_Assertions.ENABLED && !bl) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                boolean bl7 = bl = convertedPointsTop1 != null;
                if (_Assertions.ENABLED && !bl) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                boolean bl8 = bl = convertedPointsTop2 != null;
                if (_Assertions.ENABLED && !bl) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                boolean bl9 = bl = convertedPointsz2 != null;
                if (_Assertions.ENABLED && !bl) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                boolean bl10 = bl = convertedPointsz != null;
                if (_Assertions.ENABLED && !bl) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                boolean bl11 = bl = convertedPointsx2 != null;
                if (_Assertions.ENABLED && !bl) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                boolean bl12 = bl = convertedPointsBottom != null;
                if (_Assertions.ENABLED && !bl) {
                    String string = "Assertion failed";
                    throw new AssertionError((Object)string);
                }
                double[] dArray = new double[25];
                Intrinsics.checkNotNull(convertedPoints);
                dArray[0] = convertedPoints[0];
                dArray[1] = convertedPoints[1];
                dArray[2] = xd;
                dArray[3] = convertedPoints[2];
                Intrinsics.checkNotNull(convertedPointsBottom);
                dArray[4] = convertedPointsBottom[0];
                dArray[5] = convertedPointsBottom[1];
                dArray[6] = convertedPointsBottom[2];
                Intrinsics.checkNotNull(convertedPointsx);
                dArray[7] = convertedPointsx[0];
                dArray[8] = convertedPointsx[1];
                dArray[9] = convertedPointsx[2];
                Intrinsics.checkNotNull(convertedPointsx2);
                dArray[10] = convertedPointsx2[0];
                dArray[11] = convertedPointsx2[1];
                dArray[12] = convertedPointsx2[2];
                Intrinsics.checkNotNull(convertedPointsz);
                dArray[13] = convertedPointsz[0];
                dArray[14] = convertedPointsz[1];
                dArray[15] = convertedPointsz[2];
                Intrinsics.checkNotNull(convertedPointsz2);
                dArray[16] = convertedPointsz2[0];
                dArray[17] = convertedPointsz2[1];
                dArray[18] = convertedPointsz2[2];
                Intrinsics.checkNotNull(convertedPointsTop1);
                dArray[19] = convertedPointsTop1[0];
                dArray[20] = convertedPointsTop1[1];
                dArray[21] = convertedPointsTop1[2];
                Intrinsics.checkNotNull(convertedPointsTop2);
                dArray[22] = convertedPointsTop2[0];
                dArray[23] = convertedPointsTop2[1];
                dArray[24] = convertedPointsTop2[2];
                this.entityConvertedPointsMap.put((EntityItem)ent, dArray);
            }
        }
        if (((Boolean)this.nameTags.get()).booleanValue()) {
            for (Entity item : MinecraftInstance.mc.field_71441_e.func_72910_y()) {
                if (!(item instanceof EntityItem)) continue;
                String string = this.onlyCount.get() == false ? Intrinsics.stringPlus(((EntityItem)item).func_92059_d().func_82833_r(), this.itemCount.get() != false && ((EntityItem)item).func_92059_d().field_77994_a > 1 ? Intrinsics.stringPlus(" x", ((EntityItem)item).func_92059_d().field_77994_a) : String.valueOf(((EntityItem)item).func_92059_d().field_77994_a)) : "";
                GL11.glPushMatrix();
                GL11.glTranslated((double)(item.field_70142_S + (item.field_70165_t - item.field_70142_S) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78725_b), (double)(item.field_70137_T + (item.field_70163_u - item.field_70137_T) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78726_c - 0.2), (double)(item.field_70136_U + (item.field_70161_v - item.field_70136_U) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78723_d));
                GL11.glRotated((double)(-MinecraftInstance.mc.func_175598_ae().field_78735_i), (double)0.0, (double)1.0, (double)0.0);
                int[] nArray = new int[]{2896, 2929};
                RenderUtils.disableGlCap(nArray);
                GL11.glScalef((float)-0.03f, (float)-0.03f, (float)-0.03f);
                MinecraftInstance.mc.field_71466_p.func_175065_a(string, -6.0f, -30.0f, new Color(255, 255, 255).getRGB(), true);
                RenderUtils.enableGlCap(3042);
                GL11.glBlendFunc((int)770, (int)771);
                RenderUtils.resetCaps();
                GlStateManager.func_179117_G();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glPopMatrix();
            }
        }
    }

    public static final /* synthetic */ BoolValue access$getNameTags$p(ItemESP $this) {
        return $this.nameTags;
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(ItemESP $this) {
        return $this.modeValue;
    }

    public static final /* synthetic */ BoolValue access$getColorThemeClient$p(ItemESP $this) {
        return $this.colorThemeClient;
    }
}

