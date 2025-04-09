/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client.impl;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.client.TargetHUD;
import net.ccbluex.liquidbounce.features.module.modules.combat.InfiniteAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.BedAura;
import net.ccbluex.liquidbounce.features.module.modules.world.Disabler;
import net.ccbluex.liquidbounce.ui.client.gui.clickgui.extensions.AnimHelperKt;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J(\u0010!\u001a\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0002J2\u0010&\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020\f2\u0006\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020\f2\u0006\u0010*\u001a\u00020\f2\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0011\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/impl/DynamicIsland;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "alpha", "", "animAlpha", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "animPosX", "animPosX2", "animPosY", "animPosY2", "disabler", "", "getDisabler", "()Ljava/lang/Float;", "setDisabler", "(Ljava/lang/Float;)V", "Ljava/lang/Float;", "mainTarget", "Lnet/minecraft/entity/EntityLivingBase;", "getMainTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setMainTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "posX", "posX2", "posY", "posY2", "prevBlock", "draw", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "drawTH", "stringWidth", "target", "string", "", "set", "startX", "startY", "endX", "endY", "CrossSine"})
public final class DynamicIsland
extends MinecraftInstance {
    @NotNull
    public static final DynamicIsland INSTANCE = new DynamicIsland();
    @Nullable
    private static Float disabler;
    @Nullable
    private static EntityLivingBase mainTarget;
    private static float posX;
    private static float posY;
    private static float posX2;
    private static float posY2;
    private static int alpha;
    @NotNull
    private static final Animation animPosX;
    @NotNull
    private static final Animation animPosY;
    @NotNull
    private static final Animation animPosX2;
    @NotNull
    private static final Animation animPosY2;
    @NotNull
    private static final Animation animAlpha;
    private static float prevBlock;

    private DynamicIsland() {
    }

    @Nullable
    public final Float getDisabler() {
        return disabler;
    }

    public final void setDisabler(@Nullable Float f) {
        disabler = f;
    }

    @Nullable
    public final EntityLivingBase getMainTarget() {
        return mainTarget;
    }

    public final void setMainTarget(@Nullable EntityLivingBase entityLivingBase) {
        mainTarget = entityLivingBase;
    }

    public final void draw(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        animPosX.run(posX);
        animPosY.run(posY);
        animPosX2.run(posX2);
        animPosY2.run(posY2);
        animAlpha.run(alpha);
        if (((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue()) {
            BlurUtils.blurAreaRounded((float)DynamicIsland.animPosX.value + 1.0f, (float)DynamicIsland.animPosY.value + 1.0f, (float)DynamicIsland.animPosX2.value - 1.0f, (float)DynamicIsland.animPosY2.value - 1.0f, 8.0f, 10.0f);
        }
        RenderUtils.drawBloomRoundedRect((float)DynamicIsland.animPosX.value, (float)DynamicIsland.animPosY.value, (float)DynamicIsland.animPosX2.value, (float)DynamicIsland.animPosY2.value, 12.0f, 8.0f, 3.0f, new Color(10, 10, 10, (int)DynamicIsland.animAlpha.value), RenderUtils.ShaderBloom.BOTH);
        GlStateManager.func_179117_G();
        GL11.glPushMatrix();
        Stencil.write(false);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.fastRoundedRect((float)DynamicIsland.animPosX.value, (float)DynamicIsland.animPosY.value, (float)DynamicIsland.animPosX2.value, (float)DynamicIsland.animPosY2.value, 0.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        Stencil.erase(true);
        String string = "";
        int width = event.getScaledResolution().func_78326_a();
        float halfWidth = (float)width / 2.0f;
        mainTarget = MinecraftInstance.mc.field_71462_r instanceof GuiChat ? (EntityLivingBase)MinecraftInstance.mc.field_71439_g : (InfiniteAura.INSTANCE.getState() && InfiniteAura.INSTANCE.getLastTarget() != null ? InfiniteAura.INSTANCE.getLastTarget() : (Interface.INSTANCE.getAttackTarget() != null ? Interface.INSTANCE.getAttackTarget() : null));
        float stringWidth = 0.0f;
        if (!TargetHUD.INSTANCE.getState() && mainTarget != null) {
            EntityLivingBase entityLivingBase = mainTarget;
            Intrinsics.checkNotNull(entityLivingBase);
            String string2 = entityLivingBase.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(string2, "mainTarget!!.name");
            string = string2;
            float posY = 0.0f;
            if ((KillAura.INSTANCE.getState() || SilentAura.INSTANCE.getState()) && !(MinecraftInstance.mc.field_71462_r instanceof GuiChat)) {
                for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
                    Object object;
                    Intrinsics.checkNotNullExpressionValue(entity, "entity");
                    if (!EntityUtils.INSTANCE.isSelected(entity, true)) continue;
                    if (KillAura.INSTANCE.getState()) {
                        object = MinecraftInstance.mc.field_71439_g;
                        Intrinsics.checkNotNullExpressionValue(object, "mc.thePlayer");
                        if (EntityExtensionKt.getDistanceToEntityBox((Entity)object, entity) <= (double)((Number)KillAura.INSTANCE.getDiscoverRangeValue().get()).floatValue()) {
                            object = entity.func_70005_c_();
                            Intrinsics.checkNotNullExpressionValue(object, "entity.name");
                            if ((float)Fonts.font40SemiBold.func_78256_a((String)object) > stringWidth) {
                                object = entity.func_70005_c_();
                                Intrinsics.checkNotNullExpressionValue(object, "entity.name");
                                stringWidth = Fonts.font40SemiBold.func_78256_a((String)object);
                            }
                            EntityLivingBase entityLivingBase2 = (EntityLivingBase)entity;
                            object = ((EntityLivingBase)entity).func_70005_c_();
                            Intrinsics.checkNotNullExpressionValue(object, "entity.name");
                            this.drawTH(posY, stringWidth, entityLivingBase2, (String)object);
                            posY += 30.0f;
                        }
                    }
                    if (!SilentAura.INSTANCE.getState()) continue;
                    object = MinecraftInstance.mc.field_71439_g;
                    Intrinsics.checkNotNullExpressionValue(object, "mc.thePlayer");
                    if (!(EntityExtensionKt.getDistanceToEntityBox((Entity)object, entity) <= (double)((Number)SilentAura.INSTANCE.getDiscoverValue().get()).floatValue())) continue;
                    object = entity.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(object, "entity.name");
                    if ((float)Fonts.font40SemiBold.func_78256_a((String)object) > stringWidth) {
                        object = entity.func_70005_c_();
                        Intrinsics.checkNotNullExpressionValue(object, "entity.name");
                        stringWidth = Fonts.font40SemiBold.func_78256_a((String)object);
                    }
                    EntityLivingBase entityLivingBase3 = (EntityLivingBase)entity;
                    object = ((EntityLivingBase)entity).func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(object, "entity.name");
                    this.drawTH(posY, stringWidth, entityLivingBase3, (String)object);
                    posY += 30.0f;
                }
            } else {
                float f = Fonts.font40SemiBold.func_78256_a(string);
                EntityLivingBase entityLivingBase4 = mainTarget;
                Intrinsics.checkNotNull(entityLivingBase4);
                this.drawTH(0.0f, f, entityLivingBase4, string);
            }
        } else if (Flight.INSTANCE.getState()) {
            string = "Flight";
            String string2 = "Speed : " + new DecimalFormat("#.##").format(MovementUtils.INSTANCE.getBps()) + " BPS";
            boolean bl = Fonts.font40SemiBold.func_78256_a(string) < Fonts.font35SemiBold.func_78256_a(string2);
            DynamicIsland.set$default(this, halfWidth - (float)(bl ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0f - (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0f : 13.0f) + (bl ? 2.0f : 0.0f), 11.0f, halfWidth + (float)(bl ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0f + (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0f : 13.0f) - (bl ? 2.0f : 0.0f), 14.0f + (float)Fonts.font40SemiBold.getHeight() + 12.0f + 4.0f + (float)Fonts.font35SemiBold.getHeight(), 0, 16, null);
            Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f, new Color(255, 255, 255, 255).getRGB());
            Fonts.font35SemiBold.drawCenteredString(string2, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f + (float)Fonts.font35SemiBold.getHeight() + 7.0f, new Color(255, 255, 255, 255).getRGB());
        } else if (Speed.INSTANCE.getState() && Speed.INSTANCE.getFlagged() && ((Boolean)Speed.INSTANCE.getFlagCheck().get()).booleanValue()) {
            string = Intrinsics.stringPlus("Disable Speed : ", new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH)).format((double)(Speed.INSTANCE.getTimerMS().time + ((Number)Speed.INSTANCE.getFlagMS().get()).longValue() - System.currentTimeMillis()) / 1000.0));
            DynamicIsland.set$default(this, halfWidth - (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0f - 9.0f, 11.0f, halfWidth + (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0f + 9.0f, 14.0f + (float)Fonts.font40SemiBold.getHeight() + 12.0f, 0, 16, null);
            Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f, new Color(255, 255, 255, 255).getRGB());
        } else if (BedAura.INSTANCE.getState() && !BedAura.INSTANCE.getDelay().hasTimePassed(1000L)) {
            String string3;
            DecimalFormat d = new DecimalFormat("0", new DecimalFormatSymbols(Locale.ENGLISH));
            string = "BedAura";
            StringBuilder stringBuilder = new StringBuilder().append("Blocks : ");
            if (BedAura.INSTANCE.getPos() == null) {
                string3 = "None";
            } else {
                Block block = BlockUtils.getBlock(BedAura.INSTANCE.getPos());
                Intrinsics.checkNotNull(block);
                string3 = block.func_149732_F();
            }
            String string2 = stringBuilder.append((Object)string3).append(" | Progress : ").append((Object)d.format(BedAura.INSTANCE.getAnimation().value * (double)100)).append('%').toString();
            DynamicIsland.set$default(this, halfWidth - (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0f - 60.0f, 11.0f, halfWidth + (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0f + 60.0f, 14.0f + (float)Fonts.font40SemiBold.getHeight() + (float)Fonts.font35SemiBold.getHeight() + 2.0f + 12.0f + 7.0f, 0, 16, null);
            Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f, new Color(255, 255, 255, 255).getRGB());
            Fonts.font35SemiBold.drawCenteredString(string2, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f + (float)Fonts.font40SemiBold.getHeight() + 2.0f, new Color(255, 255, 255, 255).getRGB());
            RenderUtils.drawRoundedRect((float)DynamicIsland.animPosX.value + 8.0f, (float)DynamicIsland.animPosY2.value - 12.0f, (float)DynamicIsland.animPosX2.value - 8.0f, (float)DynamicIsland.animPosY2.value - 5.0f, 3.5f, new Color(50, 50, 50, 255).getRGB());
            RenderUtils.drawGradientRoundedRect((float)DynamicIsland.animPosX.value + 8.0f, (float)DynamicIsland.animPosY2.value - 12.0f, (float)DynamicIsland.animPosX.value + (float)(DynamicIsland.animPosX2.value - DynamicIsland.animPosX.value - (double)8.0f) * (float)BedAura.INSTANCE.getAnimation().value, (float)DynamicIsland.animPosY2.value - 5.0f, 3, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 2, null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, null).getRGB());
        } else if (Scaffold.INSTANCE.getState() && MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
            string = "Scaffold";
            String string2 = Scaffold.INSTANCE.getBlockAmount() - Scaffold.INSTANCE.getPlaceTick() + " Blocks left - " + new DecimalFormat("#.##").format(MovementUtils.INSTANCE.getBps()) + " BPS";
            boolean bl = Fonts.font40SemiBold.func_78256_a(string) < Fonts.font35SemiBold.func_78256_a(string2);
            DynamicIsland.set$default(this, halfWidth - (float)(bl ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0f - (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0f : 13.0f) + (bl ? 2.0f : 0.0f), 11.0f, halfWidth + (float)(bl ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0f + (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0f : 13.0f) - (bl ? 2.0f : 0.0f), 14.0f + (float)Fonts.font40SemiBold.getHeight() + 12.0f + 4.0f + (float)Fonts.font35SemiBold.getHeight() + 7.0f, 0, 16, null);
            Fonts.font40SemiBold.drawCenteredString(string, halfWidth + 10.0f, (float)DynamicIsland.animPosY.value + 9.0f, new Color(255, 255, 255, 255).getRGB());
            Fonts.font35SemiBold.drawCenteredString(string2, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f + (float)Fonts.font35SemiBold.getHeight() + 7.0f, new Color(255, 255, 255, 255).getRGB());
            RenderUtils.renderItemIcon((int)halfWidth - 37, 15, MinecraftInstance.mc.field_71439_g.func_70694_bm());
            RenderUtils.drawRoundedRect((float)DynamicIsland.animPosX.value + 8.0f, (float)DynamicIsland.animPosY2.value - 12.0f, (float)DynamicIsland.animPosX2.value - 8.0f, (float)DynamicIsland.animPosY2.value - 5.0f, 3.5f, new Color(50, 50, 50, 255).getRGB());
            RenderUtils.drawGradientRoundedRect((float)DynamicIsland.animPosX.value + 8.0f, (float)DynamicIsland.animPosY2.value - 12.0f, (float)DynamicIsland.animPosX.value + (float)(DynamicIsland.animPosX2.value - DynamicIsland.animPosX.value - (double)8.0f) * prevBlock, (float)DynamicIsland.animPosY2.value - 5.0f, 3, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 2, null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, null).getRGB());
            prevBlock = AnimHelperKt.animSmooth(prevBlock, ((float)Scaffold.INSTANCE.getBlockAmount() - (float)Scaffold.INSTANCE.getPlaceTick()) / 64.0f, 0.001f);
        } else if (Disabler.INSTANCE.getState() && disabler != null) {
            string = "WatchDog Disabler";
            DynamicIsland.set$default(this, halfWidth - (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0f - 70.0f, 11.0f, halfWidth + (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0f + 70.0f, 14.0f + (float)Fonts.font40SemiBold.getHeight() + 12.0f + 20.0f, 0, 16, null);
            Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f, new Color(255, 255, 255, 255).getRGB());
            RenderUtils.drawRoundedRect((float)DynamicIsland.animPosX.value + 8.0f, (float)DynamicIsland.animPosY2.value - 12.0f, (float)DynamicIsland.animPosX2.value - 8.0f, (float)DynamicIsland.animPosY2.value - 5.0f, 3.5f, new Color(50, 50, 50, 255).getRGB());
            float f = (float)DynamicIsland.animPosX.value + 8.0f;
            float f2 = (float)DynamicIsland.animPosY2.value - 12.0f;
            float f3 = (float)DynamicIsland.animPosX.value;
            float f4 = (float)(DynamicIsland.animPosX2.value - DynamicIsland.animPosX.value - (double)8.0f);
            Float f5 = disabler;
            Intrinsics.checkNotNull(f5);
            RenderUtils.drawGradientRoundedRect(f, f2, f3 + f4 * f5.floatValue(), (float)DynamicIsland.animPosY2.value - 5.0f, 3, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 2, null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, null).getRGB());
        } else if (SlotUtils.INSTANCE.getSpoofing() && MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g() != null) {
            string = MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? MinecraftInstance.mc.field_71439_g.func_70694_bm().field_77994_a + " Blocks left" : "";
            String string2 = Intrinsics.stringPlus("Using : ", SlotUtils.INSTANCE.getModule());
            boolean bl = Fonts.font40SemiBold.func_78256_a(string) < Fonts.font35SemiBold.func_78256_a(string2);
            DynamicIsland.set$default(this, halfWidth - (float)(bl ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0f - (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0f : 13.0f) + (bl ? 2.0f : 0.0f), 11.0f, halfWidth + (float)(bl ? Fonts.font35SemiBold.func_78256_a(string2) : Fonts.font40SemiBold.func_78256_a(string)) / 2.0f + (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? 15.0f : 13.0f) - (bl ? 2.0f : 0.0f), 14.0f + (float)Fonts.font40SemiBold.getHeight() + 12.0f + 4.0f + (float)Fonts.font35SemiBold.getHeight(), 0, 16, null);
            Fonts.font40SemiBold.drawCenteredString(string, halfWidth + 10.0f, (float)DynamicIsland.animPosY.value + 9.0f, new Color(255, 255, 255, 255).getRGB());
            Fonts.font35SemiBold.drawCenteredString(string2, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f + (float)Fonts.font35SemiBold.getHeight() + 7.0f, new Color(255, 255, 255, 255).getRGB());
            RenderUtils.renderItemIcon(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock ? (int)halfWidth - 37 : (int)halfWidth - 8, 15, MinecraftInstance.mc.field_71439_g.func_70694_bm());
        } else {
            string = "CrossSine \u00a7f| " + MinecraftInstance.mc.field_71439_g.func_70005_c_() + " | " + ServerUtils.getRemoteIp() + " | " + Minecraft.func_175610_ah() + "fps";
            DynamicIsland.set$default(this, halfWidth - (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0f - 9.0f, 11.0f, halfWidth + (float)Fonts.font40SemiBold.func_78256_a(string) / 2.0f + 9.0f, 14.0f + (float)Fonts.font40SemiBold.getHeight() + 12.0f, 0, 16, null);
            Fonts.font40SemiBold.drawCenteredString(string, halfWidth, (float)DynamicIsland.animPosY.value + 9.0f, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 255, false, 4, null).getRGB());
        }
        GlStateManager.func_179117_G();
        Stencil.dispose();
        GL11.glPopMatrix();
    }

    private final void set(float startX, float startY, float endX, float endY, int alpha) {
        posX = startX;
        posY = startY;
        posX2 = endX;
        posY2 = endY;
        DynamicIsland.alpha = alpha;
    }

    static /* synthetic */ void set$default(DynamicIsland dynamicIsland, float f, float f2, float f3, float f4, int n, int n2, Object object) {
        if ((n2 & 0x10) != 0) {
            n = 180;
        }
        dynamicIsland.set(f, f2, f3, f4, n);
    }

    private final void drawTH(float posY, float stringWidth, EntityLivingBase target, String string) {
        int width = new ScaledResolution(MinecraftInstance.mc).func_78326_a();
        int height = new ScaledResolution(MinecraftInstance.mc).func_78328_b();
        float halfWidth = (float)width / 2.0f;
        float halfHeight = (float)height / 2.0f;
        this.set(halfWidth + 60.0f, halfHeight - 18.0f - posY / (float)2, halfWidth + 60.0f + 60.0f + stringWidth + 5.0f, halfHeight + 18.0f + posY / (float)2, 100);
        Fonts.font40SemiBold.drawCenteredString(string, (float)DynamicIsland.animPosX.value + ((float)DynamicIsland.animPosX2.value - (float)DynamicIsland.animPosX.value) / (float)2 + 15.0f, (float)DynamicIsland.animPosY.value + 7.0f + posY, new Color(255, 255, 255, 255).getRGB());
        Fonts.font40SemiBold.drawCenteredString(string, (float)DynamicIsland.animPosX.value + ((float)DynamicIsland.animPosX2.value - (float)DynamicIsland.animPosX.value) / (float)2 + 15.0f, (float)DynamicIsland.animPosY.value + 7.0f + posY, new Color(255, 255, 255, 255).getRGB());
        GL11.glPushMatrix();
        Stencil.write(false);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.fastRoundedRect((float)DynamicIsland.animPosX.value + 6.0f, (float)DynamicIsland.animPosY.value + 5.0f + posY, (float)DynamicIsland.animPosX.value + 6.0f + 26.0f, (float)DynamicIsland.animPosY.value + 5.0f + 26.0f + posY, 6.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        Stencil.erase(true);
        RenderUtils.drawHead(EntityExtensionKt.getSkin(target), (int)DynamicIsland.animPosX.value + 6, (int)DynamicIsland.animPosY.value + 5 + (int)posY, 26, 26, Color.WHITE.getRGB());
        GlStateManager.func_179117_G();
        Stencil.dispose();
        GL11.glPopMatrix();
        RenderUtils.drawRoundedRect((float)DynamicIsland.animPosX.value + 40.0f, (float)DynamicIsland.animPosY.value + 24.5f + posY, (float)DynamicIsland.animPosX2.value - 5.0f - ((float)DynamicIsland.animPosX.value + 40.0f) - 4.0f, 4.5f, 2.0f, new Color(0, 0, 0, 200).getRGB(), 1.0f, Color.WHITE.getRGB());
        RenderUtils.drawGradientRoundedRect((float)DynamicIsland.animPosX.value + 40.0f, (float)DynamicIsland.animPosY.value + 24.5f + posY, (float)DynamicIsland.animPosX.value + 30.0f + (((float)DynamicIsland.animPosX2.value - 5.0f - ((float)DynamicIsland.animPosX.value + 30.0f)) * (target.func_110143_aJ() / target.func_110138_aP()) - 4.0f), (float)DynamicIsland.animPosY.value + 29.0f + posY, 2, ClientTheme.getColor$default(ClientTheme.INSTANCE, 0, false, 3, null).getRGB(), ClientTheme.getColor$default(ClientTheme.INSTANCE, 90, false, 2, null).getRGB());
    }

    static {
        animPosX = new Animation(Easing.EASE_OUT_CIRC, 500L);
        animPosY = new Animation(Easing.EASE_OUT_CIRC, 500L);
        animPosX2 = new Animation(Easing.EASE_OUT_CIRC, 500L);
        animPosY2 = new Animation(Easing.EASE_OUT_CIRC, 500L);
        animAlpha = new Animation(Easing.EASE_OUT_CIRC, 1000L);
    }
}

