/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/impl/PlayerStats;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "airAnim", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "armorAnim", "foodAnim", "healthAnim", "healthYellowAnim", "draw", "", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "sr", "Lnet/minecraft/client/gui/ScaledResolution;", "CrossSine"})
public final class PlayerStats
extends MinecraftInstance {
    @NotNull
    public static final PlayerStats INSTANCE = new PlayerStats();
    @NotNull
    private static final Animation armorAnim = new Animation(Easing.LINEAR, 250L);
    @NotNull
    private static final Animation healthAnim = new Animation(Easing.LINEAR, 250L);
    @NotNull
    private static final Animation healthYellowAnim = new Animation(Easing.LINEAR, 250L);
    @NotNull
    private static final Animation foodAnim = new Animation(Easing.LINEAR, 250L);
    @NotNull
    private static final Animation airAnim = new Animation(Easing.LINEAR, 250L);

    private PlayerStats() {
    }

    public final void draw(@NotNull EntityPlayer player, @NotNull ScaledResolution sr) {
        Intrinsics.checkNotNullParameter(player, "player");
        Intrinsics.checkNotNullParameter(sr, "sr");
        int height = sr.func_78328_b();
        float halfWidth = (float)sr.func_78326_a() / 2.0f;
        int l6 = MinecraftInstance.mc.field_71439_g.func_70086_ai();
        int k7 = MathHelper.func_76143_f((double)((double)(l6 - 2) * 10.0 / 300.0));
        armorAnim.run((float)player.func_70658_aO() / 20.0f);
        healthAnim.run(player.func_110143_aJ() / 20.0f);
        healthYellowAnim.run(MinecraftInstance.mc.field_71439_g.func_110139_bj() / 4.0f);
        foodAnim.run((float)player.func_71024_bL().func_75116_a() / 20.0f);
        airAnim.run((float)k7 / 10.0f);
        if (player.func_70658_aO() > 0) {
            RenderUtils.drawBloomRoundedRect(halfWidth - 91.0f, (float)height - 52.0f - (PlayerStats.healthYellowAnim.value > 0.0 ? 12.0f : 0.0f), halfWidth - 6.0f, (float)height - 44.0f - (PlayerStats.healthYellowAnim.value > 0.0 ? 12.0f : 0.0f), 2.5f, 1.8f, new Color(50, 50, 50), RenderUtils.ShaderBloom.BOTH);
            RenderUtils.drawBloomRoundedRect(halfWidth - 91.0f, (float)height - 52.0f - (PlayerStats.healthYellowAnim.value > 0.0 ? 12.0f : 0.0f), halfWidth - 91.0f + 4.0f + 81.0f * (float)PlayerStats.armorAnim.value, (float)height - 44.0f - (PlayerStats.healthYellowAnim.value > 0.0 ? 12.0f : 0.0f), 2.5f, 1.0f, new Color(100, 100, 255), RenderUtils.ShaderBloom.BOTH);
        }
        if (PlayerStats.healthYellowAnim.value > 0.0) {
            RenderUtils.drawBloomRoundedRect(halfWidth - 91.0f, (float)height - 52.0f, halfWidth - 6.0f, (float)height - 44.0f, 2.5f, 1.8f, new Color(50, 50, 50), RenderUtils.ShaderBloom.BOTH);
            RenderUtils.drawBloomRoundedRect(halfWidth - 91.0f, (float)height - 52.0f, halfWidth - 91.0f + 4.0f + 81.0f * (float)PlayerStats.healthYellowAnim.value, (float)height - 44.0f, 2.5f, 1.0f, new Color(255, 255, 20), RenderUtils.ShaderBloom.BOTH);
        }
        RenderUtils.drawBloomRoundedRect(halfWidth - 91.0f, (float)height - 40.0f, halfWidth - 6.0f, (float)height - 32.0f, 2.5f, 1.8f, new Color(50, 50, 50), RenderUtils.ShaderBloom.BOTH);
        RenderUtils.drawBloomRoundedRect(halfWidth - 91.0f, (float)height - 40.0f, halfWidth - 91.0f + 4.0f + 81.0f * (float)PlayerStats.healthAnim.value, (float)height - 32.0f, 2.5f, 1.0f, new Color(255, 10, 10), RenderUtils.ShaderBloom.BOTH);
        RenderUtils.drawBloomRoundedRect(halfWidth + 6.0f, (float)height - 40.0f, halfWidth + 6.0f + 85.0f, (float)height - 32.0f, 2.5f, 1.8f, new Color(50, 50, 50), RenderUtils.ShaderBloom.BOTH);
        RenderUtils.drawBloomRoundedRect(halfWidth + 6.0f, (float)height - 40.0f, halfWidth + 6.0f + 4.0f + 81.0f * (float)PlayerStats.foodAnim.value, (float)height - 32.0f, 2.5f, 1.8f, new Color(28, 167, 222), RenderUtils.ShaderBloom.BOTH);
        if (player.func_70055_a(Material.field_151586_h)) {
            airAnim.run((float)k7 / 10.0f);
            RenderUtils.drawBloomRoundedRect(halfWidth + (float)6, (float)height - 52.0f, halfWidth + (float)6 + 85.0f, (float)height - 52.0f + 8.0f, 2.5f, 2.5f, new Color(43, 42, 43), RenderUtils.ShaderBloom.BOTH);
            RenderUtils.drawBloomRoundedRect(halfWidth + (float)6, (float)height - 52.0f, halfWidth + (float)6 + (float)85 * (float)PlayerStats.airAnim.value, (float)height - 52.0f + 8.0f, 2.5f, 2.5f, new Color(28, 167, 222), RenderUtils.ShaderBloom.BOTH);
        }
    }
}

