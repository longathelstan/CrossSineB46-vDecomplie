/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.world.Target;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\bJ\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u000e\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0016\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\bR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006\u00a8\u0006\u0018"}, d2={"Lnet/ccbluex/liquidbounce/utils/EntityUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "healthSubstrings", "", "", "[Ljava/lang/String;", "canRayCast", "", "entity", "Lnet/minecraft/entity/Entity;", "getHealth", "", "Lnet/minecraft/entity/EntityLivingBase;", "fromScoreboard", "absorption", "getName", "networkPlayerInfoIn", "Lnet/minecraft/client/network/NetworkPlayerInfo;", "isAnimal", "isFriend", "isMob", "isSelected", "canAttackCheck", "CrossSine"})
public final class EntityUtils
extends MinecraftInstance {
    @NotNull
    public static final EntityUtils INSTANCE = new EntityUtils();
    @NotNull
    private static final String[] healthSubstrings;

    private EntityUtils() {
    }

    public final boolean isSelected(@NotNull Entity entity, boolean canAttackCheck) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (entity instanceof EntityLivingBase && (((Boolean)Target.INSTANCE.getDeadValue().get()).booleanValue() || entity.func_70089_S()) && entity != MinecraftInstance.mc.field_71439_g && (((Boolean)Target.INSTANCE.getInvisibleValue().get()).booleanValue() || !entity.func_82150_aj())) {
            if (((Boolean)Target.INSTANCE.getPlayerValue().get()).booleanValue() && entity instanceof EntityPlayer) {
                if (canAttackCheck) {
                    if (AntiBot.isBot((EntityLivingBase)entity)) {
                        return false;
                    }
                    if (this.isFriend(entity) && !NoFriends.INSTANCE.getState()) {
                        return false;
                    }
                    if (Intrinsics.areEqual(((EntityPlayer)entity).func_70005_c_(), MinecraftInstance.mc.field_71439_g.func_70005_c_())) {
                        return false;
                    }
                    if (((EntityPlayer)entity).func_175149_v()) {
                        return false;
                    }
                    if (((EntityPlayer)entity).func_70608_bn()) {
                        return false;
                    }
                    if (!CrossSine.INSTANCE.getCombatManager().isFocusEntity((EntityPlayer)entity)) {
                        return false;
                    }
                    return (Boolean)Target.INSTANCE.getFriendValue().get() == false || !Target.INSTANCE.isInYourTeam((EntityLivingBase)entity);
                }
                return true;
            }
            return (Boolean)Target.INSTANCE.getMobValue().get() != false && this.isMob(entity) || (Boolean)Target.INSTANCE.getAnimalValue().get() != false && this.isAnimal(entity);
        }
        return false;
    }

    public final boolean canRayCast(@NotNull Entity entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (entity instanceof EntityLivingBase) {
            return entity instanceof EntityPlayer ? !((Boolean)Target.INSTANCE.getFriendValue().get()).booleanValue() || !Target.INSTANCE.isInYourTeam((EntityLivingBase)entity) : (Boolean)Target.INSTANCE.getMobValue().get() != false && this.isMob(entity) || (Boolean)Target.INSTANCE.getAnimalValue().get() != false && this.isAnimal(entity);
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isFriend(@NotNull Entity entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (!(entity instanceof EntityPlayer)) return false;
        if (entity.func_70005_c_() == null) return false;
        FriendsConfig friendsConfig = CrossSine.INSTANCE.getFileManager().getFriendsConfig();
        String string = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(string, "entity.getName()");
        if (!friendsConfig.isFriend(ColorUtils.stripColor(string))) return false;
        return true;
    }

    private final boolean isAnimal(Entity entity) {
        return entity instanceof EntityAnimal || entity instanceof EntitySquid || entity instanceof EntityGolem || entity instanceof EntityVillager || entity instanceof EntityBat;
    }

    private final boolean isMob(Entity entity) {
        return entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityGhast || entity instanceof EntityDragon;
    }

    @NotNull
    public final String getName(@NotNull NetworkPlayerInfo networkPlayerInfoIn) {
        String string;
        Intrinsics.checkNotNullParameter(networkPlayerInfoIn, "networkPlayerInfoIn");
        if (networkPlayerInfoIn.func_178854_k() != null) {
            String string2 = networkPlayerInfoIn.func_178854_k().func_150254_d();
            Intrinsics.checkNotNullExpressionValue(string2, "networkPlayerInfoIn.displayName.formattedText");
            string = string2;
        } else {
            String string3 = ScorePlayerTeam.func_96667_a((Team)((Team)networkPlayerInfoIn.func_178850_i()), (String)networkPlayerInfoIn.func_178845_a().getName());
            Intrinsics.checkNotNullExpressionValue(string3, "formatPlayerName(\n      \u2026ameProfile.name\n        )");
            string = string3;
        }
        return string;
    }

    public final float getHealth(@NotNull EntityLivingBase entity, boolean fromScoreboard, boolean absorption) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (fromScoreboard && entity instanceof EntityPlayer) {
            int scoreboardHealth;
            EntityUtils $this$getHealth_u24lambda_u2d0 = this;
            boolean bl = false;
            Scoreboard scoreboard = ((EntityPlayer)entity).func_96123_co();
            Score objective = scoreboard.func_96529_a(((EntityPlayer)entity).func_70005_c_(), scoreboard.func_96539_a(2));
            ScoreObjective scoreObjective = objective.func_96645_d();
            if (ArraysKt.contains(healthSubstrings, scoreObjective == null ? null : scoreObjective.func_96678_d()) && (scoreboardHealth = objective.func_96652_c()) > 0) {
                return scoreboardHealth;
            }
        }
        float health = entity.func_110143_aJ();
        if (absorption) {
            health += entity.func_110139_bj();
        }
        return health > 0.0f ? health : 20.0f;
    }

    public static /* synthetic */ float getHealth$default(EntityUtils entityUtils, EntityLivingBase entityLivingBase, boolean bl, boolean bl2, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        if ((n & 4) != 0) {
            bl2 = true;
        }
        return entityUtils.getHealth(entityLivingBase, bl, bl2);
    }

    static {
        String[] stringArray = new String[]{"hp", "health", "\u2764", "lives"};
        healthSubstrings = stringArray;
    }
}

