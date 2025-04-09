/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.client.ScoreboardModule;
import net.ccbluex.liquidbounce.features.module.modules.visual.Crosshair;
import net.ccbluex.liquidbounce.features.module.modules.visual.NoRender;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.injection.forge.mixins.gui.MixinGui;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.FontUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.render.BlurUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiStreamIndicator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GuiIngame.class})
public abstract class MixinGuiInGame
extends MixinGui {
    @Shadow
    protected int field_73845_h;
    @Shadow
    protected int field_92017_k;
    @Shadow
    protected ItemStack field_92016_l;
    @Shadow
    protected int field_175195_w;
    @Shadow
    protected String field_175201_x = "";
    @Shadow
    protected String field_175200_y = "";
    @Shadow
    protected GuiStreamIndicator field_152127_m;
    @Shadow
    @Final
    protected static ResourceLocation field_110330_c;
    @Shadow
    @Final
    protected Minecraft field_73839_d;
    @Shadow
    protected final Random field_73842_c = new Random();
    @Shadow
    protected int field_73837_f;
    @Shadow
    protected int field_175194_C = 0;
    @Shadow
    protected int field_175189_D = 0;
    @Shadow
    protected long field_175190_E = 0L;
    @Shadow
    protected long field_175191_F = 0L;
    private final Pattern LINK_PATTERN = Pattern.compile("(http(s)?://.)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[A-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&//=]*)");

    @Shadow
    protected abstract void func_175184_a(int var1, int var2, int var3, float var4, EntityPlayer var5);

    @Inject(method={"showCrosshair"}, at={@At(value="HEAD")}, cancellable=true)
    private void injectCrosshair(CallbackInfoReturnable<Boolean> cir) {
        if (Interface.INSTANCE.getState() && this.field_73839_d.field_71474_y.field_74320_O != 0) {
            cir.setReturnValue(false);
        }
        if (Crosshair.INSTANCE.getState()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method={"renderScoreboard"}, at={@At(value="HEAD")}, cancellable=true)
    private void injectScoreboard(ScoreObjective scoreObjective, ScaledResolution scaledResolution, CallbackInfo callbackInfo) {
        float posY = ScoreboardModule.INSTANCE.getPosY();
        float posX = ScoreboardModule.INSTANCE.getPosX();
        if (ScoreboardModule.INSTANCE.getState()) {
            if (((Boolean)ScoreboardModule.INSTANCE.getRoundMode().get()).booleanValue()) {
                Scoreboard scoreboard = scoreObjective.func_96682_a();
                Color color = new Color(0, 0, 0, ScoreboardModule.INSTANCE.getAlpha());
                Collection collection = scoreboard.func_96534_i(scoreObjective);
                ArrayList list = Lists.newArrayList((Iterable)Iterables.filter((Iterable)collection, (Predicate)new Predicate<Score>(){

                    public boolean apply(Score p_apply_1_) {
                        return p_apply_1_.func_96653_e() != null && !p_apply_1_.func_96653_e().startsWith("#");
                    }
                }));
                collection = list.size() > 15 ? Lists.newArrayList((Iterable)Iterables.skip((Iterable)list, (int)(collection.size() - 15))) : list;
                int i = this.field_73839_d.field_71466_p.func_78256_a(scoreObjective.func_96678_d());
                for (Score score : collection) {
                    ScorePlayerTeam scoreplayerteam = scoreboard.func_96509_i(score.func_96653_e());
                    String s = ScorePlayerTeam.func_96667_a((Team)scoreplayerteam, (String)score.func_96653_e()) + ": " + ((Boolean)ScoreboardModule.INSTANCE.getShowNumber().get() != false ? EnumChatFormatting.RED + "" + score.func_96652_c() : "");
                    Matcher linkMatcher = this.LINK_PATTERN.matcher(s);
                    if (linkMatcher.find() && ((Boolean)ScoreboardModule.INSTANCE.getChangedDomain().get()).booleanValue()) {
                        s = "crosssine.github.io";
                    }
                    i = Math.max(i, this.field_73839_d.field_71466_p.func_78256_a(s));
                }
                int i1 = collection.size() * this.field_73839_d.field_71466_p.field_78288_b;
                int j1 = scaledResolution.func_78328_b() / 2 + i1 / 3;
                int k1 = 3;
                int l1 = (int)((float)(scaledResolution.func_78326_a() - i - k1 - 4) + posX);
                int j = 0;
                int width = i + k1 + 4;
                BlurUtils.blurAreaRounded(l1 - 4 - 2, (int)((float)(j1 - i1) + posY - (float)this.field_73839_d.field_71466_p.field_78288_b) - 2 - 2, l1 - 2 + width + 4 + 2, (int)((float)j1 + posY) + 4 + 2, 7.0f, 10.0f);
                RenderUtils.drawBloomRoundedRect(l1 - 4 - 5, (int)((float)(j1 - i1) + posY - (float)this.field_73839_d.field_71466_p.field_78288_b) - 2 - 5, l1 - 2 + width + 4 + 5, (int)((float)j1 + posY) + 4 + 5, 7.0f, 2.5f, color, RenderUtils.ShaderBloom.BLOOMONLY);
                ScoreboardModule.INSTANCE.setUx_size(l1 - 4 - 2);
                ScoreboardModule.INSTANCE.setUy_size((float)(j1 - i1) + posY - (float)this.field_73839_d.field_71466_p.field_78288_b - 2.0f - 2.0f);
                ScoreboardModule.INSTANCE.setUx2_size(l1 - 2 + width + 4 + 2);
                ScoreboardModule.INSTANCE.setUy2_size((float)j1 + posY + 4.0f + 2.0f);
                for (Score score1 : collection) {
                    ScorePlayerTeam scoreplayerteam1 = scoreboard.func_96509_i(score1.func_96653_e());
                    String s1 = ScorePlayerTeam.func_96667_a((Team)scoreplayerteam1, (String)score1.func_96653_e());
                    String s2 = EnumChatFormatting.RED + "" + score1.func_96652_c();
                    int k = j1 - ++j * this.field_73839_d.field_71466_p.field_78288_b;
                    int l = (int)((float)(scaledResolution.func_78326_a() - k1 + 2 - 4) + posX);
                    Matcher linkMatcher = this.LINK_PATTERN.matcher(s1);
                    if (linkMatcher.find() && ((Boolean)ScoreboardModule.INSTANCE.getChangedDomain().get()).booleanValue()) {
                        s1 = "crosssine.github.io";
                        FontUtils.INSTANCE.drawGradientString(this.field_73839_d.field_71466_p, s1, l1, (int)((float)k + posY), ClientTheme.INSTANCE.getColor(0, false).getRGB(), ClientTheme.INSTANCE.getColor(180, false).getRGB(), 1.0f, (boolean)((Boolean)ScoreboardModule.INSTANCE.getTextShadow().get()));
                    } else {
                        this.field_73839_d.field_71466_p.func_175065_a(s1, (float)l1, (float)k + posY, 0x20FFFFFF, ((Boolean)ScoreboardModule.INSTANCE.getTextShadow().get()).booleanValue());
                    }
                    if (((Boolean)ScoreboardModule.INSTANCE.getShowNumber().get()).booleanValue()) {
                        this.field_73839_d.field_71466_p.func_175065_a(s2, (float)(l - this.field_73839_d.field_71466_p.func_78256_a(s2)), (float)k + posY, 0x20FFFFFF, ((Boolean)ScoreboardModule.INSTANCE.getTextShadow().get()).booleanValue());
                    }
                    if (j != collection.size()) continue;
                    String s3 = scoreObjective.func_96678_d();
                    this.field_73839_d.field_71466_p.func_175065_a(s3, (float)l1 + (float)i / 2.0f - (float)this.field_73839_d.field_71466_p.func_78256_a(s3) / 2.0f, (float)(k - this.field_73839_d.field_71466_p.field_78288_b) + posY, 0x20FFFFFF, ((Boolean)ScoreboardModule.INSTANCE.getTextShadow().get()).booleanValue());
                }
            } else {
                Scoreboard lvt_3_1_ = scoreObjective.func_96682_a();
                Collection lvt_4_1_ = lvt_3_1_.func_96534_i(scoreObjective);
                ArrayList lvt_5_1_ = Lists.newArrayList((Iterable)Iterables.filter((Iterable)lvt_4_1_, (Predicate)new Predicate<Score>(){

                    public boolean apply(Score p_apply_1_) {
                        return p_apply_1_.func_96653_e() != null && !p_apply_1_.func_96653_e().startsWith("#");
                    }
                }));
                lvt_4_1_ = lvt_5_1_.size() > 15 ? Lists.newArrayList((Iterable)Iterables.skip((Iterable)lvt_5_1_, (int)(lvt_4_1_.size() - 15))) : lvt_5_1_;
                int lvt_6_1_ = this.field_73839_d.field_71466_p.func_78256_a(scoreObjective.func_96678_d());
                for (Score lvt_8_1_ : lvt_4_1_) {
                    ScorePlayerTeam lvt_9_1_ = lvt_3_1_.func_96509_i(lvt_8_1_.func_96653_e());
                    String lvt_10_1_ = ScorePlayerTeam.func_96667_a((Team)lvt_9_1_, (String)lvt_8_1_.func_96653_e()) + ": " + EnumChatFormatting.RED + lvt_8_1_.func_96652_c();
                    Matcher linkMatcher = this.LINK_PATTERN.matcher(lvt_10_1_);
                    if (linkMatcher.find()) {
                        lvt_10_1_ = "crosssine.github.io";
                    }
                    lvt_6_1_ = Math.max(lvt_6_1_, this.field_73839_d.field_71466_p.func_78256_a(lvt_10_1_));
                }
                int lvt_7_2_ = lvt_4_1_.size() * this.field_73839_d.field_71466_p.field_78288_b;
                int lvt_8_2_ = scaledResolution.func_78328_b() / 2 + lvt_7_2_ / 3;
                int lvt_9_2_ = 3;
                int lvt_10_2_ = (int)((float)(scaledResolution.func_78326_a() - lvt_6_1_ - lvt_9_2_) + posX);
                int lvt_11_1_ = 0;
                int lvt_12_1 = lvt_6_1_ + lvt_9_2_;
                BlurUtils.blurAreaRounded(lvt_10_2_, (float)(lvt_8_2_ - lvt_7_2_) + posY - (float)this.field_73839_d.field_71466_p.field_78288_b, lvt_10_2_ - 2 + lvt_12_1, (float)lvt_8_2_ + posY, 7.0f, 10.0f);
                ScoreboardModule.INSTANCE.setUx_size(lvt_10_2_);
                ScoreboardModule.INSTANCE.setUy_size((float)(lvt_8_2_ - lvt_7_2_) + posY - (float)this.field_73839_d.field_71466_p.field_78288_b);
                ScoreboardModule.INSTANCE.setUx2_size(lvt_10_2_ - 2 + lvt_12_1);
                ScoreboardModule.INSTANCE.setUy2_size((float)lvt_8_2_ + posY);
                for (Score lvt_13_1_ : lvt_4_1_) {
                    ScorePlayerTeam lvt_14_1_ = lvt_3_1_.func_96509_i(lvt_13_1_.func_96653_e());
                    String lvt_15_1_ = ScorePlayerTeam.func_96667_a((Team)lvt_14_1_, (String)lvt_13_1_.func_96653_e());
                    String lvt_16_1_ = EnumChatFormatting.RED + "" + lvt_13_1_.func_96652_c();
                    int lvt_18_1_ = lvt_8_2_ - ++lvt_11_1_ * this.field_73839_d.field_71466_p.field_78288_b;
                    int lvt_19_1_ = (int)((float)(scaledResolution.func_78326_a() - lvt_9_2_ + 2) + posX);
                    Gui.func_73734_a((int)(lvt_10_2_ - 2), (int)((int)((float)lvt_18_1_ + posY)), (int)lvt_19_1_, (int)((int)((float)(lvt_18_1_ + this.field_73839_d.field_71466_p.field_78288_b) + posY)), (int)0x50000000);
                    Matcher linkMatcher = this.LINK_PATTERN.matcher(lvt_15_1_);
                    if (linkMatcher.find() && ((Boolean)ScoreboardModule.INSTANCE.getChangedDomain().get()).booleanValue()) {
                        lvt_15_1_ = "crosssine.github.io";
                        FontUtils.INSTANCE.drawGradientString(this.field_73839_d.field_71466_p, lvt_15_1_, lvt_10_2_, (int)((float)lvt_18_1_ + posY), ClientTheme.INSTANCE.getColor(0, false).getRGB(), ClientTheme.INSTANCE.getColor(180, false).getRGB(), 1.0f, (boolean)((Boolean)ScoreboardModule.INSTANCE.getTextShadow().get()));
                    } else {
                        this.field_73839_d.field_71466_p.func_175065_a(lvt_15_1_, (float)lvt_10_2_, (float)lvt_18_1_ + posY, 0x20FFFFFF, ((Boolean)ScoreboardModule.INSTANCE.getTextShadow().get()).booleanValue());
                    }
                    if (((Boolean)ScoreboardModule.INSTANCE.getShowNumber().get()).booleanValue()) {
                        this.field_73839_d.field_71466_p.func_175065_a(lvt_16_1_, (float)(lvt_19_1_ - this.field_73839_d.field_71466_p.func_78256_a(lvt_16_1_)), (float)lvt_18_1_ + posY, 0x20FFFFFF, ((Boolean)ScoreboardModule.INSTANCE.getTextShadow().get()).booleanValue());
                    }
                    if (lvt_11_1_ != lvt_4_1_.size()) continue;
                    String lvt_20_1_ = scoreObjective.func_96678_d();
                    Gui.func_73734_a((int)(lvt_10_2_ - 2), (int)((int)((float)(lvt_18_1_ - this.field_73839_d.field_71466_p.field_78288_b - 1) + posY)), (int)lvt_19_1_, (int)((int)((float)(lvt_18_1_ - 1) + posY)), (int)0x60000000);
                    Gui.func_73734_a((int)(lvt_10_2_ - 2), (int)((int)((float)(lvt_18_1_ - 1) + posY)), (int)lvt_19_1_, (int)((int)((float)lvt_18_1_ + posY)), (int)0x50000000);
                    this.field_73839_d.field_71466_p.func_78276_b(lvt_20_1_, lvt_10_2_ + lvt_6_1_ / 2 - this.field_73839_d.field_71466_p.func_78256_a(lvt_20_1_) / 2, (int)((float)(lvt_18_1_ - this.field_73839_d.field_71466_p.field_78288_b) + posY), 0x20FFFFFF);
                }
            }
        }
        if (!CrossSine.INSTANCE.getDestruced()) {
            callbackInfo.cancel();
        }
    }

    @Overwrite
    protected void func_180479_a(ScaledResolution sr, float partialTicks) {
        if (this.field_73839_d.func_175606_aa() instanceof EntityPlayer) {
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            EntityPlayer entityPlayer = (EntityPlayer)this.field_73839_d.func_175606_aa();
            int i = sr.func_78326_a() / 2;
            if (((Boolean)Interface.INSTANCE.getRoundedHotbar().get()).booleanValue()) {
                GlStateManager.func_179094_E();
                if (((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue()) {
                    BlurUtils.blurAreaRounded((float)(i - 91) - 2.5f, (float)(sr.func_78328_b() - 22) - 2.5f, (float)(i + 91) + 2.5f, (float)sr.func_78328_b() + 2.5f, 4.5f, 10.0f);
                }
                RenderUtils.drawBloomRoundedRect(i - 91, sr.func_78328_b() - 22, i + 91, sr.func_78328_b(), 7.0f, 4.5f, new Color(0, 0, 0, 80), RenderUtils.ShaderBloom.BOTH);
                RenderUtils.originalRoundedRect((float)((double)(i - 91) + ((double)(91 - i) + ((double)((float)sr.func_78326_a() / 2.0f - 91.0f) + Interface.INSTANCE.getAnimationSlot().value))) + 1.0f, sr.func_78328_b() - 1 - 1, (float)((double)(i - 91) + ((double)(91 - i) + ((double)((float)sr.func_78326_a() / 2.0f - 91.0f) + Interface.INSTANCE.getAnimationSlot().value)) + 22.0) - 1.0f, sr.func_78328_b() - 22 + 1, 7.0f, new Color(0, 0, 0, 100).getRGB());
                GlStateManager.func_179121_F();
            } else {
                this.field_73839_d.func_110434_K().func_110577_a(field_110330_c);
                float f = this.field_73735_i;
                this.field_73735_i = -90.0f;
                this.func_73729_b(i - 91, sr.func_78328_b() - 22, 0, 0, 182, 22);
                this.func_73729_b(i - 91 - 1 + SlotUtils.INSTANCE.getSlot() * 20, sr.func_78328_b() - 22 - 1, 0, 22, 24, 22);
                this.field_73735_i = f;
            }
            GlStateManager.func_179091_B();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            RenderHelper.func_74520_c();
            for (int j = 0; j < 9; ++j) {
                int k = sr.func_78326_a() / 2 - 90 + j * 20 + 2;
                int l = sr.func_78328_b() - 16 - 3;
                this.func_175184_a(j, k, l, partialTicks, entityPlayer);
            }
            RenderHelper.func_74518_a();
            GlStateManager.func_179101_C();
            GlStateManager.func_179084_k();
        }
        CrossSine.eventManager.callEvent(new Render2DEvent(partialTicks, StaticStorage.scaledResolution));
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    @Inject(method={"renderBossHealth"}, at={@At(value="HEAD")}, cancellable=true)
    private void injectBossHealth(CallbackInfo callbackInfo) {
        NoRender NoRender2 = CrossSine.moduleManager.getModule(NoRender.class);
        if (NoRender2.getState() && ((Boolean)NoRender2.getBossHealth().get()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Overwrite
    public FontRenderer func_175179_f() {
        return CrossSine.INSTANCE.getDestruced() || !Interface.INSTANCE.getState() ? this.field_73839_d.field_71466_p : Fonts.font40;
    }

    @Overwrite
    public void func_73831_a() {
        if (this.field_73845_h > 0) {
            --this.field_73845_h;
        }
        if (this.field_175195_w > 0) {
            --this.field_175195_w;
            if (this.field_175195_w <= 0) {
                this.field_175201_x = "";
                this.field_175200_y = "";
            }
        }
        ++this.field_73837_f;
        this.field_152127_m.func_152439_a();
        if (this.field_73839_d.field_71439_g != null) {
            ItemStack lvt_1_1_ = SlotUtils.INSTANCE.getStack();
            if (lvt_1_1_ == null) {
                this.field_92017_k = 0;
            } else if (this.field_92016_l == null || lvt_1_1_.func_77973_b() != this.field_92016_l.func_77973_b() || !ItemStack.func_77970_a((ItemStack)lvt_1_1_, (ItemStack)this.field_92016_l) || !lvt_1_1_.func_77984_f() && lvt_1_1_.func_77960_j() != this.field_92016_l.func_77960_j()) {
                this.field_92017_k = 40;
            } else if (this.field_92017_k > 0) {
                --this.field_92017_k;
            }
            this.field_92016_l = lvt_1_1_;
        }
    }
}

