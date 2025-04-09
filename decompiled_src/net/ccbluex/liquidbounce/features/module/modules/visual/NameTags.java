/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.FontValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="NameTags", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0007J\u0018\u0010-\u001a\u00020*2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0014H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020&X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/NameTags;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "backgroundColorAlphaValue", "", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorRedValue", "borderColorAlphaValue", "borderColorBlueValue", "borderColorGreenValue", "borderColorRedValue", "borderValue", "clearNamesValue", "distanceValue", "enchantValue", "entityKeep", "", "fontShadowValue", "fontValue", "Lnet/ccbluex/liquidbounce/features/value/FontValue;", "friendValue", "healthBarValue", "healthValue", "inventoryBackground", "Lnet/minecraft/util/ResourceLocation;", "jelloAlphaValue", "jelloColorValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "onlyTarget", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "pingValue", "potionValue", "scaleValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "targetTicks", "translateY", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "renderNameTag", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "tag", "CrossSine"})
public final class NameTags
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final Value<Boolean> healthValue;
    @NotNull
    private final Value<Boolean> pingValue;
    @NotNull
    private final Value<Boolean> healthBarValue;
    @NotNull
    private final Value<Boolean> distanceValue;
    @NotNull
    private final Value<Boolean> armorValue;
    @NotNull
    private final Value<Boolean> enchantValue;
    @NotNull
    private final Value<Boolean> potionValue;
    @NotNull
    private final Value<Boolean> clearNamesValue;
    @NotNull
    private final FontValue fontValue;
    @NotNull
    private final Value<Boolean> borderValue;
    @NotNull
    private final Value<Boolean> fontShadowValue;
    @NotNull
    private final Value<Boolean> friendValue;
    @NotNull
    private final Value<Boolean> jelloColorValue;
    @NotNull
    private final Value<Integer> jelloAlphaValue;
    @NotNull
    private final FloatValue scaleValue;
    @NotNull
    private final BoolValue onlyTarget;
    @NotNull
    private final FloatValue translateY;
    @NotNull
    private final Value<Integer> backgroundColorRedValue;
    @NotNull
    private final Value<Integer> backgroundColorGreenValue;
    @NotNull
    private final Value<Integer> backgroundColorBlueValue;
    @NotNull
    private final Value<Integer> backgroundColorAlphaValue;
    @NotNull
    private final Value<Integer> borderColorRedValue;
    @NotNull
    private final Value<Integer> borderColorGreenValue;
    @NotNull
    private final Value<Integer> borderColorBlueValue;
    @NotNull
    private final Value<Integer> borderColorAlphaValue;
    private int targetTicks;
    @NotNull
    private String entityKeep;
    @NotNull
    private final ResourceLocation inventoryBackground;

    public NameTags() {
        Object object = new String[]{"CrossSine", "Simple", "Liquid", "Jello"};
        this.modeValue = new ListValue("Mode", (String[])object, "Liquid");
        this.healthValue = new BoolValue("Health", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.pingValue = new BoolValue("Ping", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.healthBarValue = new BoolValue("Bar", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.distanceValue = new BoolValue("Distance", false).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.armorValue = new BoolValue("Armor", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.enchantValue = new BoolValue("Enchant", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.potionValue = new BoolValue("Potions", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.clearNamesValue = new BoolValue("ClearNames", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        object = Fonts.font40;
        Intrinsics.checkNotNullExpressionValue(object, "font40");
        this.fontValue = new FontValue("Font", (FontRenderer)object);
        this.borderValue = new BoolValue("Border", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.fontShadowValue = new BoolValue("Shadow", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.friendValue = new BoolValue("Friend", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.jelloColorValue = new BoolValue("JelloHPColor", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Jello");
            }
        });
        this.jelloAlphaValue = new IntegerValue("JelloAlpha", 170, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Jello");
            }
        });
        this.scaleValue = new FloatValue("Scale", 1.0f, 1.0f, 4.0f);
        this.onlyTarget = new BoolValue("OnlyTarget", false);
        this.translateY = new FloatValue("TanslateY", 0.55f, -2.0f, 2.0f);
        this.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.borderColorRedValue = new IntegerValue("Border-R", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.borderColorGreenValue = new IntegerValue("Border-G", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.borderColorBlueValue = new IntegerValue("Border-B", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.borderColorAlphaValue = new IntegerValue("Border-Alpha", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ NameTags this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return NameTags.access$getModeValue$p(this.this$0).equals("Liquid");
            }
        });
        this.entityKeep = "yes zywl";
        this.inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            Intrinsics.checkNotNullExpressionValue(entity, "entity");
            if (!EntityUtils.INSTANCE.isSelected(entity, false)) continue;
            this.renderNameTag((EntityLivingBase)entity, Intrinsics.stringPlus(!this.modeValue.equals("Liquid") && AntiBot.isBot((EntityLivingBase)entity) ? "\u00a7e" : "", this.clearNamesValue.get() != false ? ((EntityLivingBase)entity).func_70005_c_() : entity.func_145748_c_().func_150260_c()));
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void renderNameTag(EntityLivingBase entity, String tag) {
        int n;
        if (((Boolean)this.onlyTarget.get()).booleanValue() && !Intrinsics.areEqual(entity, CrossSine.INSTANCE.getCombatManager().getTarget()) && !Intrinsics.areEqual(entity.func_70005_c_(), this.entityKeep)) {
            return;
        }
        if (((Boolean)this.onlyTarget.get()).booleanValue() && Intrinsics.areEqual(entity, CrossSine.INSTANCE.getCombatManager().getTarget())) {
            String string = entity.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(string, "entity.getName()");
            this.entityKeep = string;
            n = this.targetTicks;
            this.targetTicks = n + 1;
            if (this.targetTicks >= 5) {
                this.targetTicks = 4;
            }
        } else if (((Boolean)this.onlyTarget.get()).booleanValue() && CrossSine.INSTANCE.getCombatManager().getTarget() == null) {
            n = this.targetTicks;
            this.targetTicks = n + -1;
            if (this.targetTicks <= -1) {
                this.targetTicks = 0;
                this.entityKeep = "fdp is skidded";
            }
        }
        if (((Boolean)this.onlyTarget.get()).booleanValue() && this.targetTicks == 0) {
            return;
        }
        FontRenderer fontRenderer = (FontRenderer)this.fontValue.get();
        GL11.glPushMatrix();
        RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
        Timer timer = MinecraftInstance.mc.field_71428_T;
        GL11.glTranslated((double)(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b), (double)(entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c + (double)entity.func_70047_e() + (double)((Number)this.translateY.get()).floatValue()), (double)(entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d));
        GL11.glRotatef((float)(-MinecraftInstance.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)MinecraftInstance.mc.func_175598_ae().field_78732_j, (float)1.0f, (float)0.0f, (float)0.0f);
        float distance = MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)entity) / 4.0f;
        if (distance < 1.0f) {
            distance = 1.0f;
        }
        float scale = distance / 150.0f * ((Number)this.scaleValue.get()).floatValue();
        Object object = new int[]{2896, 2929};
        RenderUtils.disableGlCap(object);
        RenderUtils.enableGlCap(3042);
        GL11.glBlendFunc((int)770, (int)771);
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch ((Object)string) {
            case "crosssine": {
                String string2;
                string = entity.func_145748_c_().func_150254_d();
                Intrinsics.checkNotNullExpressionValue(string, "entity.displayName.formattedText");
                int width = RangesKt.coerceAtLeast(Fonts.font40SemiBold.func_78256_a(string), 30) / 2;
                String healthText = "" + ' ' + (int)entity.func_110143_aJ() + '\u2764';
                int healthWidth = Fonts.font40SemiBold.func_78256_a(healthText);
                GL11.glScalef((float)(-scale * (float)2), (float)(-scale * (float)2), (float)(scale * (float)2));
                RenderUtils.drawBloomRoundedRect((float)(-width) - 6.0f, (float)(-Fonts.font40SemiBold.field_78288_b) * 2.0f, (float)width + 6.0f, -2.0f, 4.5f, 1.3f, new Color(0, 0, 0, 100), RenderUtils.ShaderBloom.BLOOMONLY);
                RenderUtils.drawBloomRoundedRect((float)width + 7.0f, (float)(-Fonts.font40SemiBold.field_78288_b) * 2.0f, (float)width + 6.0f + (float)healthWidth + 10.0f, -2.0f, 4.5f, 1.3f, new Color(0, 0, 0, 100), RenderUtils.ShaderBloom.BLOOMONLY);
                String string3 = string2 = entity.func_145748_c_().func_150254_d();
                Intrinsics.checkNotNullExpressionValue(string2, "entity.displayName.formattedText");
                Fonts.font40SemiBold.func_78276_b(string3, (int)((float)(-Fonts.font40SemiBold.func_78256_a(string2)) * 0.5f), (int)((float)(-Fonts.font40SemiBold.field_78288_b) * 1.4f), Color.WHITE.getRGB());
                Fonts.font40SemiBold.func_78276_b(healthText, (int)((float)width + 6.0f + 5.0f), (int)((float)(-Fonts.font40SemiBold.field_78288_b) * 1.4f), Color.WHITE.getRGB());
                break;
            }
            case "simple": {
                float healthPercent = RangesKt.coerceAtMost(entity.func_110143_aJ() / entity.func_110138_aP(), 1.0f);
                int width = RangesKt.coerceAtLeast(fontRenderer.func_78256_a(tag), 30) / 2;
                float maxWidth = (float)(width * 2) + 12.0f;
                GL11.glScalef((float)(-scale * (float)2), (float)(-scale * (float)2), (float)(scale * (float)2));
                RenderUtils.drawRect((float)(-width) - 6.0f, (float)(-fontRenderer.field_78288_b) * 1.7f, (float)width + 6.0f, -2.0f, new Color(0, 0, 0, ((Number)this.jelloAlphaValue.get()).intValue()));
                RenderUtils.drawRect((float)(-width) - 6.0f, -2.0f, (float)(-width) - 6.0f + maxWidth * healthPercent, 0.0f, ColorUtils.INSTANCE.healthColor(entity.func_110143_aJ(), entity.func_110138_aP(), ((Number)this.jelloAlphaValue.get()).intValue()));
                RenderUtils.drawRect((float)(-width) - 6.0f + maxWidth * healthPercent, -2.0f, (float)width + 6.0f, 0.0f, new Color(0, 0, 0, ((Number)this.jelloAlphaValue.get()).intValue()));
                fontRenderer.func_78276_b(tag, (int)((float)(-fontRenderer.func_78256_a(tag)) * 0.5f), (int)((float)(-fontRenderer.field_78288_b) * 1.4f), Color.WHITE.getRGB());
                break;
            }
            case "liquid": {
                String distanceText;
                boolean bot = AntiBot.isBot(entity);
                String nameColor = bot ? "\u00a73" : (entity.func_82150_aj() ? "\u00a76" : (entity.func_70093_af() ? "\u00a74" : "\u00a77"));
                int ping = EntityExtensionKt.getPing(entity);
                String string4 = distanceText = this.distanceValue.get() != false ? "\u00a77 [\u00a7a" + MathKt.roundToInt(MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)entity)) + "\u00a77]" : "";
                String pingText = this.pingValue.get() != false && entity instanceof EntityPlayer ? " \u00a77[" + (ping > 200 ? "\u00a7c" : (ping > 100 ? "\u00a7e" : "\u00a7a")) + ping + "ms\u00a77]" : "";
                String healthText = this.healthValue.get() != false ? "\u00a77 \u00a7f" + (int)entity.func_110143_aJ() + "\u00a7c\u2764\u00a77" : "";
                String botText = bot ? " \u00a77[\u00a76\u00a7lBot\u00a77]" : "";
                String text = distanceText + pingText + nameColor + tag + healthText + botText;
                GL11.glScalef((float)(-scale), (float)(-scale), (float)scale);
                float width = (float)fontRenderer.func_78256_a(text) * 0.5f;
                float dist = width + 4.0f - (-width - 2.0f);
                GL11.glDisable((int)3553);
                GL11.glEnable((int)3042);
                Color bgColor = new Color(((Number)this.backgroundColorRedValue.get()).intValue(), ((Number)this.backgroundColorGreenValue.get()).intValue(), ((Number)this.backgroundColorBlueValue.get()).intValue(), ((Number)this.backgroundColorAlphaValue.get()).intValue());
                Color borderColor = new Color(((Number)this.borderColorRedValue.get()).intValue(), ((Number)this.borderColorGreenValue.get()).intValue(), ((Number)this.borderColorBlueValue.get()).intValue(), ((Number)this.borderColorAlphaValue.get()).intValue());
                if (this.borderValue.get().booleanValue()) {
                    RenderUtils.quickDrawBorderedRect(-width - 2.0f, -2.0f, width + 4.0f, (float)fontRenderer.field_78288_b + 2.0f + (this.healthBarValue.get() != false ? 2.0f : 0.0f), 2.0f, borderColor.getRGB(), bgColor.getRGB());
                } else {
                    RenderUtils.quickDrawRect(-width - 2.0f, -2.0f, width + 4.0f, (float)fontRenderer.field_78288_b + 2.0f + (this.healthBarValue.get() != false ? 2.0f : 0.0f), bgColor.getRGB());
                }
                if (this.healthBarValue.get().booleanValue()) {
                    RenderUtils.quickDrawRect(-width - 2.0f, (float)fontRenderer.field_78288_b + 3.0f, -width - 2.0f + dist, (float)fontRenderer.field_78288_b + 4.0f, new Color(10, 155, 10).getRGB());
                    RenderUtils.quickDrawRect(-width - 2.0f, (float)fontRenderer.field_78288_b + 3.0f, -width - 2.0f + dist * RangesKt.coerceIn(entity.func_110143_aJ() / entity.func_110138_aP(), 0.0f, 1.0f), (float)fontRenderer.field_78288_b + 4.0f, new Color(10, 255, 10).getRGB());
                }
                GL11.glEnable((int)3553);
                fontRenderer.func_175065_a(text, 1.0f + -width, Intrinsics.areEqual(fontRenderer, Fonts.minecraftFont) ? 1.0f : 1.5f, 0xFFFFFF, this.fontShadowValue.get().booleanValue());
                boolean foundPotion = false;
                if (this.potionValue.get().booleanValue() && entity instanceof EntityPlayer) {
                    void $this$filterTo$iv$iv;
                    void $this$filter$iv;
                    Potion it;
                    void $this$mapTo$iv$iv;
                    Collection collection = entity.func_70651_bq();
                    if (collection == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Collection<net.minecraft.potion.PotionEffect>");
                    }
                    Iterable $this$map$iv = collection;
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        PotionEffect potionEffect = (PotionEffect)item$iv$iv;
                        Collection collection2 = destination$iv$iv;
                        boolean bl = false;
                        collection2.add(Potion.field_76425_a[it.func_76456_a()]);
                    }
                    $this$map$iv = (List)destination$iv$iv;
                    boolean $i$f$filter = false;
                    $this$mapTo$iv$iv = $this$filter$iv;
                    destination$iv$iv = new ArrayList();
                    boolean $i$f$filterTo = false;
                    for (Object element$iv$iv : $this$filterTo$iv$iv) {
                        it = (Potion)element$iv$iv;
                        boolean bl = false;
                        if (!it.func_76400_d()) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    List potions = (List)destination$iv$iv;
                    if (!potions.isEmpty()) {
                        foundPotion = true;
                        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                        GlStateManager.func_179140_f();
                        GlStateManager.func_179098_w();
                        int minX = potions.size() * -20 / 2;
                        int index2 = 0;
                        GL11.glPushMatrix();
                        GlStateManager.func_179091_B();
                        for (Potion potion : potions) {
                            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            MinecraftInstance.mc.func_110434_K().func_110577_a(this.inventoryBackground);
                            int i1 = potion.func_76392_e();
                            RenderUtils.drawTexturedModalRect(minX + index2 * 20, -22, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18, 0.0f);
                            int n2 = index2;
                            index2 = n2 + 1;
                        }
                        GlStateManager.func_179101_C();
                        GL11.glPopMatrix();
                        GlStateManager.func_179141_d();
                        GlStateManager.func_179084_k();
                        GlStateManager.func_179098_w();
                    }
                }
                if (this.armorValue.get().booleanValue() && entity instanceof EntityPlayer) {
                    int n3 = 0;
                    while (n3 < 5) {
                        int index3;
                        if (entity.func_71124_b(index3 = n3++) == null) continue;
                        MinecraftInstance.mc.func_175599_af().field_77023_b = -147.0f;
                        MinecraftInstance.mc.func_175599_af().func_180450_b(entity.func_71124_b(index3), -50 + index3 * 20, this.potionValue.get() != false && foundPotion ? -42 : -22);
                    }
                    GlStateManager.func_179141_d();
                    GlStateManager.func_179084_k();
                    GlStateManager.func_179098_w();
                }
                if (!this.enchantValue.get().booleanValue() || !(entity instanceof EntityPlayer)) break;
                GL11.glPushMatrix();
                int n4 = 0;
                while (n4 < 5) {
                    int index4;
                    if (entity.func_71124_b(index4 = n4++) == null) continue;
                    MinecraftInstance.mc.func_175599_af().func_175030_a(MinecraftInstance.mc.field_71466_p, entity.func_71124_b(index4), -50 + index4 * 20, this.potionValue.get() != false && foundPotion ? -42 : -22);
                    RenderUtils.drawExhiEnchants(entity.func_71124_b(index4), -50.0f + (float)index4 * 20.0f, this.potionValue.get() != false && foundPotion ? -42.0f : -22.0f);
                }
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GL11.glEnable((int)2848);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glPopMatrix();
                break;
            }
            case "jello": {
                Color hpBarColor = new Color(255, 255, 255, ((Number)this.jelloAlphaValue.get()).intValue());
                String name = entity.func_145748_c_().func_150260_c();
                if (this.jelloColorValue.get().booleanValue()) {
                    Intrinsics.checkNotNullExpressionValue(name, "name");
                    if (StringsKt.startsWith$default(name, "\u00a7", false, 2, null)) {
                        String healthText = name.substring(1, 2);
                        Intrinsics.checkNotNullExpressionValue(healthText, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                        hpBarColor = ColorUtils.INSTANCE.colorCode(healthText, ((Number)this.jelloAlphaValue.get()).intValue());
                    }
                }
                Color bgColor = new Color(20, 20, 20, ((Number)this.jelloAlphaValue.get()).intValue());
                int width = fontRenderer.func_78256_a(tag) / 2;
                float maxWidth = (float)width + 4.0f - ((float)(-width) - 4.0f);
                float healthPercent = entity.func_110143_aJ() / entity.func_110138_aP();
                GL11.glScalef((float)(-scale * (float)2), (float)(-scale * (float)2), (float)(scale * (float)2));
                RenderUtils.drawRect((float)(-width) - 4.0f, (float)(-fontRenderer.field_78288_b) * 3.0f, (float)width + 4.0f, -3.0f, bgColor);
                if (healthPercent > 1.0f) {
                    healthPercent = 1.0f;
                }
                RenderUtils.drawRect((float)(-width) - 4.0f, -3.0f, (float)(-width) - 4.0f + maxWidth * healthPercent, 0.0f, hpBarColor);
                RenderUtils.drawRect((float)(-width) - 4.0f + maxWidth * healthPercent, -3.0f, (float)width + 4.0f, 0.0f, bgColor);
                fontRenderer.func_78276_b(tag, -width, -fontRenderer.field_78288_b * 2 - 4, Color.WHITE.getRGB());
                GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
                fontRenderer.func_78276_b(Intrinsics.stringPlus("Health: ", (int)entity.func_110143_aJ()), -width * 2, -fontRenderer.field_78288_b * 2, Color.WHITE.getRGB());
            }
        }
        RenderUtils.resetCaps();
        GlStateManager.func_179117_G();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(NameTags $this) {
        return $this.modeValue;
    }
}

