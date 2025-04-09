/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiBot;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.NetworkExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="AntiBot", category=ModuleCategory.COMBAT, array=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010%\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010A\u001a\u00020BH\u0002J\u0010\u0010C\u001a\u00020\n2\u0006\u0010D\u001a\u00020EH\u0007J\u0010\u0010F\u001a\u00020B2\u0006\u0010G\u001a\u00020HH\u0007J\b\u0010I\u001a\u00020BH\u0016J\u0010\u0010J\u001a\u00020B2\u0006\u0010K\u001a\u00020LH\u0007J\u0010\u0010M\u001a\u00020B2\u0006\u0010K\u001a\u00020NH\u0007J\u0010\u0010O\u001a\u00020B2\u0006\u0010K\u001a\u00020PH\u0007J\u0018\u0010Q\u001a\u00020B2\u0006\u0010D\u001a\u00020R2\u0006\u0010S\u001a\u00020\nH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00100\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000203X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u00108\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00190\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006T"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AntiBot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "air", "", "", "airValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "alwaysInRadiusRemoveValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "alwaysInRadiusValue", "alwaysInRadiusWithTicksCheckValue", "alwaysRadiusValue", "", "armorValue", "colorValue", "czechHekGMCheckValue", "czechHekPingCheckValue", "czechHekValue", "debugValue", "derpValue", "duplicate", "Ljava/util/UUID;", "duplicateCompareModeValue", "", "duplicateInTabValue", "duplicateInWorldValue", "entityIDValue", "fastDamageTicksValue", "fastDamageValue", "ground", "groundValue", "hasRemovedEntities", "healthValue", "hiddenNameValue", "hitted", "invalidGround", "", "invalidGroundValue", "invisible", "lastDamage", "lastDamageVl", "livingTimeTicksValue", "livingTimeValue", "needHitValue", "noClip", "noClipValue", "notAlwaysInRadius", "pingValue", "regex", "Lkotlin/text/Regex;", "removeFromWorld", "removeIntervalValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "reusedEntityIdValue", "spawnInCombat", "spawnInCombatValue", "swing", "swingValue", "tabModeValue", "tabValue", "validNameValue", "wasAdded", "wasInvisibleValue", "clearAll", "", "isBot", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "onAttack", "e", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "processEntityMove", "Lnet/minecraft/entity/Entity;", "onGround", "CrossSine"})
public final class AntiBot
extends Module {
    @NotNull
    public static final AntiBot INSTANCE = new AntiBot();
    @NotNull
    private static final BoolValue tabValue = new BoolValue("Tab", true);
    @NotNull
    private static final Value<String> tabModeValue;
    @NotNull
    private static final BoolValue entityIDValue;
    @NotNull
    private static final BoolValue colorValue;
    @NotNull
    private static final BoolValue livingTimeValue;
    @NotNull
    private static final Value<Integer> livingTimeTicksValue;
    @NotNull
    private static final BoolValue groundValue;
    @NotNull
    private static final BoolValue airValue;
    @NotNull
    private static final BoolValue invalidGroundValue;
    @NotNull
    private static final BoolValue swingValue;
    @NotNull
    private static final BoolValue healthValue;
    @NotNull
    private static final BoolValue derpValue;
    @NotNull
    private static final BoolValue wasInvisibleValue;
    @NotNull
    private static final BoolValue validNameValue;
    @NotNull
    private static final BoolValue hiddenNameValue;
    @NotNull
    private static final BoolValue armorValue;
    @NotNull
    private static final BoolValue pingValue;
    @NotNull
    private static final BoolValue needHitValue;
    @NotNull
    private static final BoolValue noClipValue;
    @NotNull
    private static final BoolValue czechHekValue;
    @NotNull
    private static final Value<Boolean> czechHekPingCheckValue;
    @NotNull
    private static final Value<Boolean> czechHekGMCheckValue;
    @NotNull
    private static final BoolValue reusedEntityIdValue;
    @NotNull
    private static final BoolValue spawnInCombatValue;
    @NotNull
    private static final BoolValue duplicateInWorldValue;
    @NotNull
    private static final BoolValue duplicateInTabValue;
    @NotNull
    private static final Value<String> duplicateCompareModeValue;
    @NotNull
    private static final BoolValue fastDamageValue;
    @NotNull
    private static final Value<Integer> fastDamageTicksValue;
    @NotNull
    private static final BoolValue removeFromWorld;
    @NotNull
    private static final IntegerValue removeIntervalValue;
    @NotNull
    private static final BoolValue debugValue;
    @NotNull
    private static final BoolValue alwaysInRadiusValue;
    @NotNull
    private static final Value<Float> alwaysRadiusValue;
    @NotNull
    private static final Value<Boolean> alwaysInRadiusRemoveValue;
    @NotNull
    private static final Value<Boolean> alwaysInRadiusWithTicksCheckValue;
    @NotNull
    private static final List<Integer> ground;
    @NotNull
    private static final List<Integer> air;
    @NotNull
    private static final Map<Integer, Integer> invalidGround;
    @NotNull
    private static final List<Integer> swing;
    @NotNull
    private static final List<Integer> invisible;
    @NotNull
    private static final List<Integer> hitted;
    @NotNull
    private static final List<Integer> spawnInCombat;
    @NotNull
    private static final List<Integer> notAlwaysInRadius;
    @NotNull
    private static final Map<Integer, Integer> lastDamage;
    @NotNull
    private static final Map<Integer, Float> lastDamageVl;
    @NotNull
    private static final List<UUID> duplicate;
    @NotNull
    private static final List<Integer> noClip;
    @NotNull
    private static final List<Integer> hasRemovedEntities;
    @NotNull
    private static final Regex regex;
    private static boolean wasAdded;

    private AntiBot() {
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71441_e == null) {
            return;
        }
        if (((Boolean)removeFromWorld.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.field_70173_aa > 0 && MinecraftInstance.mc.field_71439_g.field_70173_aa % ((Number)removeIntervalValue.get()).intValue() == 0) {
            List ent = new ArrayList();
            for (EntityPlayer entity : MinecraftInstance.mc.field_71441_e.field_73010_i) {
                if (entity == MinecraftInstance.mc.field_71439_g) continue;
                Intrinsics.checkNotNullExpressionValue(entity, "entity");
                if (!AntiBot.isBot((EntityLivingBase)entity)) continue;
                ent.add(entity);
            }
            if (ent.isEmpty()) {
                return;
            }
            for (EntityPlayer e : ent) {
                MinecraftInstance.mc.field_71441_e.func_72900_e((Entity)e);
                if (!((Boolean)debugValue.get()).booleanValue()) continue;
                ClientUtils.INSTANCE.displayChatMessage("\u00a77[\u00a7a\u00a7lAnti Bot\u00a77] \u00a7fRemoved \u00a7r" + e.func_70005_c_() + " \u00a7fdue to it being a bot.");
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @JvmStatic
    public static final boolean isBot(@NotNull EntityLivingBase entity) {
        Entity it;
        int count$iv;
        boolean $i$f$count;
        Iterable $this$count$iv;
        CharSequence charSequence;
        block41: {
            block42: {
                Intrinsics.checkNotNullParameter(entity, "entity");
                if (!(entity instanceof EntityPlayer) || entity == MinecraftInstance.mc.field_71439_g) {
                    return false;
                }
                if (!INSTANCE.getState()) {
                    return false;
                }
                if (((Boolean)validNameValue.get()).booleanValue()) {
                    charSequence = ((EntityPlayer)entity).func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(charSequence, "entity.name");
                    charSequence = charSequence;
                    if (!regex.matches(charSequence)) {
                        return true;
                    }
                }
                if (!((Boolean)hiddenNameValue.get()).booleanValue()) break block41;
                charSequence = entity.func_70005_c_();
                Intrinsics.checkNotNullExpressionValue(charSequence, "entity.getName()");
                if (StringsKt.contains$default(charSequence, "\u00a7", false, 2, null)) break block42;
                if (!entity.func_145818_k_()) break block41;
                charSequence = entity.func_95999_t();
                Intrinsics.checkNotNullExpressionValue(charSequence, "entity.getCustomNameTag()");
                CharSequence charSequence2 = charSequence;
                charSequence = entity.func_70005_c_();
                Intrinsics.checkNotNullExpressionValue(charSequence, "entity.getName()");
                if (!StringsKt.contains$default(charSequence2, charSequence, false, 2, null)) break block41;
            }
            return true;
        }
        if (((Boolean)colorValue.get()).booleanValue()) {
            charSequence = ((EntityPlayer)entity).func_145748_c_().func_150254_d();
            Intrinsics.checkNotNullExpressionValue(charSequence, "entity.displayName.formattedText");
            if (!StringsKt.contains$default((CharSequence)StringsKt.replace$default(charSequence, "\u00a7r", "", false, 4, null), "\u00a7", false, 2, null)) {
                return true;
            }
        }
        if (((Boolean)livingTimeValue.get()).booleanValue() && entity.field_70173_aa < ((Number)livingTimeTicksValue.get()).intValue()) {
            return true;
        }
        if (((Boolean)groundValue.get()).booleanValue() && !ground.contains(((EntityPlayer)entity).func_145782_y())) {
            return true;
        }
        if (((Boolean)airValue.get()).booleanValue() && !air.contains(((EntityPlayer)entity).func_145782_y())) {
            return true;
        }
        if (((Boolean)swingValue.get()).booleanValue() && !swing.contains(((EntityPlayer)entity).func_145782_y())) {
            return true;
        }
        if (((Boolean)noClipValue.get()).booleanValue() && noClip.contains(((EntityPlayer)entity).func_145782_y())) {
            return true;
        }
        if (((Boolean)reusedEntityIdValue.get()).booleanValue() && hasRemovedEntities.contains(((EntityPlayer)entity).func_145782_y())) {
            return false;
        }
        if (((Boolean)healthValue.get()).booleanValue() && (((EntityPlayer)entity).func_110143_aJ() > 20.0f || ((EntityPlayer)entity).func_110143_aJ() <= 0.0f)) {
            return true;
        }
        if (((Boolean)spawnInCombatValue.get()).booleanValue() && spawnInCombat.contains(((EntityPlayer)entity).func_145782_y())) {
            return true;
        }
        if (((Boolean)entityIDValue.get()).booleanValue() && (((EntityPlayer)entity).func_145782_y() >= 1000000000 || ((EntityPlayer)entity).func_145782_y() <= -1)) {
            return true;
        }
        if (((Boolean)derpValue.get()).booleanValue() && (entity.field_70125_A > 90.0f || entity.field_70125_A < -90.0f)) {
            return true;
        }
        if (((Boolean)wasInvisibleValue.get()).booleanValue() && invisible.contains(((EntityPlayer)entity).func_145782_y())) {
            return true;
        }
        if (((Boolean)armorValue.get()).booleanValue() && ((EntityPlayer)entity).field_71071_by.field_70460_b[0] == null && ((EntityPlayer)entity).field_71071_by.field_70460_b[1] == null && ((EntityPlayer)entity).field_71071_by.field_70460_b[2] == null && ((EntityPlayer)entity).field_71071_by.field_70460_b[3] == null) {
            return true;
        }
        if (((Boolean)pingValue.get()).booleanValue()) {
            NetworkPlayerInfo networkPlayerInfo = MinecraftInstance.mc.func_147114_u().func_175102_a(((EntityPlayer)entity).func_110124_au());
            if (networkPlayerInfo == null ? false : networkPlayerInfo.func_178853_c() == 0) {
                return true;
            }
        }
        if (((Boolean)needHitValue.get()).booleanValue() && !hitted.contains(((EntityPlayer)entity).func_145782_y())) {
            return true;
        }
        if (((Boolean)invalidGroundValue.get()).booleanValue() && ((Number)invalidGround.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0)).intValue() >= 10) {
            return true;
        }
        if (((Boolean)tabValue.get()).booleanValue()) {
            boolean equals = tabModeValue.equals("Equals");
            String string = ((EntityPlayer)entity).func_145748_c_().func_150254_d();
            Intrinsics.checkNotNullExpressionValue(string, "entity.displayName.formattedText");
            String targetName = ColorUtils.stripColor(string);
            for (NetworkPlayerInfo networkPlayerInfo : MinecraftInstance.mc.func_147114_u().func_175106_d()) {
                Intrinsics.checkNotNullExpressionValue(networkPlayerInfo, "networkPlayerInfo");
                String networkName = ColorUtils.stripColor(NetworkExtensionKt.getFullName(networkPlayerInfo));
                if (!(equals ? Intrinsics.areEqual(targetName, networkName) : StringsKt.contains$default((CharSequence)targetName, networkName, false, 2, null))) continue;
                return false;
            }
            return true;
        }
        if (duplicateCompareModeValue.equals("WhenSpawn") && duplicate.contains(((EntityPlayer)entity).func_146103_bH().getId())) {
            return true;
        }
        if (((Boolean)duplicateInWorldValue.get()).booleanValue() && duplicateCompareModeValue.equals("OnTime")) {
            int n;
            List equals = MinecraftInstance.mc.field_71441_e.field_72996_f;
            Intrinsics.checkNotNullExpressionValue(equals, "mc.theWorld.loadedEntityList");
            $this$count$iv = equals;
            $i$f$count = false;
            if ($this$count$iv instanceof Collection && $this$count$iv.isEmpty()) {
                n = 0;
            } else {
                count$iv = 0;
                for (Object element$iv : $this$count$iv) {
                    it = (Entity)element$iv;
                    boolean bl = false;
                    if (!(it instanceof EntityPlayer && Intrinsics.areEqual(((EntityPlayer)it).func_70005_c_(), ((EntityPlayer)it).func_70005_c_())) || ++count$iv >= 0) continue;
                    CollectionsKt.throwCountOverflow();
                }
                n = count$iv;
            }
            if (n > 1) {
                return true;
            }
        }
        if (((Boolean)duplicateInTabValue.get()).booleanValue() && duplicateCompareModeValue.equals("OnTime")) {
            boolean bl;
            $this$count$iv = MinecraftInstance.mc.func_147114_u().func_175106_d();
            Intrinsics.checkNotNullExpressionValue($this$count$iv, "mc.netHandler.playerInfoMap");
            $this$count$iv = $this$count$iv;
            $i$f$count = false;
            if (((Collection)$this$count$iv).isEmpty()) {
                bl = false;
            } else {
                void var3_4;
                count$iv = 0;
                for (Object element$iv : $this$count$iv) {
                    it = (NetworkPlayerInfo)element$iv;
                    boolean bl2 = false;
                    if (!Intrinsics.areEqual(((EntityPlayer)entity).func_70005_c_(), it.func_178845_a().getName()) || ++count$iv >= 0) continue;
                    CollectionsKt.throwCountOverflow();
                }
                bl = var3_4;
            }
            if (bl > true) {
                return true;
            }
        }
        if (((Boolean)fastDamageValue.get()).booleanValue() && ((Number)lastDamageVl.getOrDefault(((EntityPlayer)entity).func_145782_y(), Float.valueOf(0.0f))).floatValue() > 0.0f) {
            return true;
        }
        if (((Boolean)alwaysInRadiusValue.get()).booleanValue() && !notAlwaysInRadius.contains(((EntityPlayer)entity).func_145782_y())) {
            if (alwaysInRadiusRemoveValue.get().booleanValue()) {
                MinecraftInstance.mc.field_71441_e.func_72900_e((Entity)entity);
            }
            return true;
        }
        charSequence = ((EntityPlayer)entity).func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(charSequence, "entity.name");
        return ((CharSequence)charSequence).length() == 0 || Intrinsics.areEqual(((EntityPlayer)entity).func_70005_c_(), MinecraftInstance.mc.field_71439_g.func_70005_c_());
    }

    @Override
    public void onDisable() {
        this.clearAll();
        super.onDisable();
    }

    private final void processEntityMove(Entity entity, boolean onGround) {
        if (entity instanceof EntityPlayer) {
            if (onGround && !ground.contains(((EntityPlayer)entity).func_145782_y())) {
                ground.add(((EntityPlayer)entity).func_145782_y());
            }
            if (!onGround && !air.contains(((EntityPlayer)entity).func_145782_y())) {
                air.add(((EntityPlayer)entity).func_145782_y());
            }
            if (onGround) {
                if (!(entity.field_70167_r == entity.field_70163_u)) {
                    Map<Integer, Integer> map = invalidGround;
                    Integer n = ((EntityPlayer)entity).func_145782_y();
                    Integer n2 = ((Number)invalidGround.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0)).intValue() + 1;
                    map.put(n, n2);
                }
            } else {
                int currentVL = ((Number)invalidGround.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0)).intValue() / 2;
                if (currentVL <= 0) {
                    invalidGround.remove(((EntityPlayer)entity).func_145782_y());
                } else {
                    Map<Integer, Integer> map = invalidGround;
                    Integer n = ((EntityPlayer)entity).func_145782_y();
                    Integer n3 = currentVL;
                    map.put(n, n3);
                }
            }
            if (((EntityPlayer)entity).func_82150_aj() && !invisible.contains(((EntityPlayer)entity).func_145782_y())) {
                invisible.add(((EntityPlayer)entity).func_145782_y());
            }
            if (!noClip.contains(((EntityPlayer)entity).func_145782_y())) {
                List cb = MinecraftInstance.mc.field_71441_e.func_72945_a(entity, ((EntityPlayer)entity).func_174813_aQ().func_72331_e(0.0625, 0.0625, 0.0625));
                Intrinsics.checkNotNullExpressionValue(cb, "cb");
                if (!((Collection)cb).isEmpty()) {
                    noClip.add(((EntityPlayer)entity).func_145782_y());
                }
            }
            if (!(((Boolean)livingTimeValue.get()).booleanValue() && entity.field_70173_aa <= ((Number)livingTimeTicksValue.get()).intValue() && alwaysInRadiusWithTicksCheckValue.get().booleanValue() || notAlwaysInRadius.contains(((EntityPlayer)entity).func_145782_y()) || !(MinecraftInstance.mc.field_71439_g.func_70032_d(entity) > ((Number)alwaysRadiusValue.get()).floatValue()))) {
                notAlwaysInRadius.add(((EntityPlayer)entity).func_145782_y());
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Object entity;
        Packet<?> packet;
        block32: {
            Object $this$forEach$iv;
            block35: {
                block34: {
                    block33: {
                        block31: {
                            Intrinsics.checkNotNullParameter(event, "event");
                            if (MinecraftInstance.mc.field_71439_g == null || MinecraftInstance.mc.field_71441_e == null) {
                                return;
                            }
                            if (((Boolean)czechHekValue.get()).booleanValue()) {
                                S38PacketPlayerListItem packetListItem;
                                S38PacketPlayerListItem.AddPlayerData data;
                                packet = event.getPacket();
                                if (packet instanceof S41PacketServerDifficulty) {
                                    wasAdded = false;
                                }
                                if (packet instanceof S38PacketPlayerListItem && (data = (S38PacketPlayerListItem.AddPlayerData)(packetListItem = (S38PacketPlayerListItem)event.getPacket()).func_179767_a().get(0)).func_179962_a() != null && data.func_179962_a().getName() != null) {
                                    if (!wasAdded) {
                                        wasAdded = Intrinsics.areEqual(data.func_179962_a().getName(), MinecraftInstance.mc.field_71439_g.func_70005_c_());
                                    } else if (!(MinecraftInstance.mc.field_71439_g.func_175149_v() || MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75101_c || czechHekPingCheckValue.get().booleanValue() && data.func_179963_b() == 0 || czechHekGMCheckValue.get().booleanValue() && data.func_179960_c() == WorldSettings.GameType.NOT_SET)) {
                                        event.cancelEvent();
                                        if (((Boolean)debugValue.get()).booleanValue()) {
                                            ClientUtils.INSTANCE.displayChatMessage("\u00a77[\u00a7a\u00a7lAnti Bot/\u00a76Matrix\u00a77] \u00a7fPrevented \u00a7r" + data.func_179962_a().getName() + " \u00a7ffrom spawning.");
                                        }
                                    }
                                }
                            }
                            if (!((packet = event.getPacket()) instanceof S18PacketEntityTeleport)) break block31;
                            Entity entity2 = MinecraftInstance.mc.field_71441_e.func_73045_a(((S18PacketEntityTeleport)packet).func_149451_c());
                            if (entity2 == null) {
                                return;
                            }
                            this.processEntityMove(entity2, ((S18PacketEntityTeleport)packet).func_179697_g());
                            break block32;
                        }
                        if (!(packet instanceof S14PacketEntity)) break block33;
                        Entity entity3 = ((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e);
                        if (entity3 == null) {
                            return;
                        }
                        this.processEntityMove(entity3, ((S14PacketEntity)packet).func_179742_g());
                        break block32;
                    }
                    if (!(packet instanceof S0BPacketAnimation)) break block34;
                    entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S0BPacketAnimation)packet).func_148978_c());
                    if (entity != null && entity instanceof EntityLivingBase && ((S0BPacketAnimation)packet).func_148977_d() == 0 && !swing.contains(((EntityLivingBase)entity).func_145782_y())) {
                        swing.add(((EntityLivingBase)entity).func_145782_y());
                    }
                    break block32;
                }
                if (!(packet instanceof S38PacketPlayerListItem)) break block35;
                if (!duplicateCompareModeValue.equals("WhenSpawn") || ((S38PacketPlayerListItem)packet).func_179768_b() != S38PacketPlayerListItem.Action.ADD_PLAYER) break block32;
                entity = ((S38PacketPlayerListItem)packet).func_179767_a();
                Intrinsics.checkNotNullExpressionValue(entity, "packet.entries");
                $this$forEach$iv = (Iterable)entity;
                boolean $i$f$forEach = false;
                Iterator iterator2 = $this$forEach$iv.iterator();
                while (iterator2.hasNext()) {
                    Object object;
                    S38PacketPlayerListItem.AddPlayerData entry;
                    block37: {
                        boolean bl;
                        block30: {
                            EntityPlayer it;
                            boolean $i$f$any;
                            Iterable $this$any$iv;
                            String name;
                            block36: {
                                boolean bl2;
                                block29: {
                                    Object element$iv = iterator2.next();
                                    entry = (S38PacketPlayerListItem.AddPlayerData)element$iv;
                                    boolean bl3 = false;
                                    name = entry.func_179962_a().getName();
                                    if (!((Boolean)duplicateInWorldValue.get()).booleanValue()) break block36;
                                    object = MinecraftInstance.mc.field_71441_e.field_73010_i;
                                    Intrinsics.checkNotNullExpressionValue(object, "mc.theWorld.playerEntities");
                                    $this$any$iv = (Iterable)object;
                                    $i$f$any = false;
                                    if ($this$any$iv instanceof Collection && $this$any$iv.isEmpty()) {
                                        bl2 = false;
                                    } else {
                                        for (Object element$iv2 : $this$any$iv) {
                                            it = (EntityPlayer)element$iv2;
                                            boolean bl4 = false;
                                            if (!Intrinsics.areEqual(it.func_70005_c_(), name)) continue;
                                            bl2 = true;
                                            break block29;
                                        }
                                        bl2 = false;
                                    }
                                }
                                if (bl2) break block37;
                            }
                            if (!((Boolean)duplicateInTabValue.get()).booleanValue()) continue;
                            $this$any$iv = MinecraftInstance.mc.func_147114_u().func_175106_d();
                            Intrinsics.checkNotNullExpressionValue($this$any$iv, "mc.netHandler.playerInfoMap");
                            $this$any$iv = $this$any$iv;
                            $i$f$any = false;
                            if (((Collection)$this$any$iv).isEmpty()) {
                                bl = false;
                            } else {
                                for (Object element$iv2 : $this$any$iv) {
                                    it = (NetworkPlayerInfo)element$iv2;
                                    boolean bl5 = false;
                                    if (!Intrinsics.areEqual(it.func_178845_a().getName(), name)) continue;
                                    bl = true;
                                    break block30;
                                }
                                bl = false;
                            }
                        }
                        if (!bl) continue;
                    }
                    object = entry.func_179962_a().getId();
                    Intrinsics.checkNotNullExpressionValue(object, "entry.profile.id");
                    duplicate.add((UUID)object);
                }
                break block32;
            }
            if (packet instanceof S0CPacketSpawnPlayer) {
                if (CrossSine.INSTANCE.getCombatManager().getInCombat() && !hasRemovedEntities.contains(((S0CPacketSpawnPlayer)packet).func_148943_d())) {
                    spawnInCombat.add(((S0CPacketSpawnPlayer)packet).func_148943_d());
                }
            } else if (packet instanceof S13PacketDestroyEntities) {
                Collection collection = hasRemovedEntities;
                $this$forEach$iv = ((S13PacketDestroyEntities)packet).func_149098_c();
                Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "packet.entityIDs");
                CollectionsKt.addAll(collection, ArraysKt.toTypedArray((int[])$this$forEach$iv));
            }
        }
        if (packet instanceof S19PacketEntityStatus && ((S19PacketEntityStatus)packet).func_149160_c() == 2 || packet instanceof S0BPacketAnimation && ((S0BPacketAnimation)packet).func_148977_d() == 1) {
            Entity entity4;
            if (packet instanceof S19PacketEntityStatus) {
                entity4 = ((S19PacketEntityStatus)packet).func_149161_a((World)MinecraftInstance.mc.field_71441_e);
            } else {
                entity4 = packet instanceof S0BPacketAnimation ? MinecraftInstance.mc.field_71441_e.func_73045_a(((S0BPacketAnimation)packet).func_148978_c()) : (Entity)null;
                if (entity4 == null) {
                    return;
                }
            }
            entity = entity4;
            if (entity instanceof EntityPlayer) {
                lastDamageVl.put(((EntityPlayer)entity).func_145782_y(), Float.valueOf(((Number)lastDamageVl.getOrDefault(((EntityPlayer)entity).func_145782_y(), Float.valueOf(0.0f))).floatValue() + (entity.field_70173_aa - ((Number)lastDamage.getOrDefault(((EntityPlayer)entity).func_145782_y(), 0)).intValue() <= ((Number)fastDamageTicksValue.get()).intValue() ? 1.0f : -0.5f)));
                lastDamage.put(((EntityPlayer)entity).func_145782_y(), entity.field_70173_aa);
            }
        }
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        Entity entity = e.getTargetEntity();
        if (entity instanceof EntityLivingBase && !hitted.contains(((EntityLivingBase)entity).func_145782_y())) {
            hitted.add(((EntityLivingBase)entity).func_145782_y());
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.clearAll();
    }

    private final void clearAll() {
        hitted.clear();
        swing.clear();
        ground.clear();
        invalidGround.clear();
        invisible.clear();
        lastDamage.clear();
        lastDamageVl.clear();
        notAlwaysInRadius.clear();
        duplicate.clear();
        spawnInCombat.clear();
        noClip.clear();
        hasRemovedEntities.clear();
    }

    public static final /* synthetic */ BoolValue access$getTabValue$p() {
        return tabValue;
    }

    public static final /* synthetic */ BoolValue access$getLivingTimeValue$p() {
        return livingTimeValue;
    }

    public static final /* synthetic */ BoolValue access$getCzechHekValue$p() {
        return czechHekValue;
    }

    public static final /* synthetic */ BoolValue access$getDuplicateInTabValue$p() {
        return duplicateInTabValue;
    }

    public static final /* synthetic */ BoolValue access$getDuplicateInWorldValue$p() {
        return duplicateInWorldValue;
    }

    public static final /* synthetic */ BoolValue access$getFastDamageValue$p() {
        return fastDamageValue;
    }

    public static final /* synthetic */ BoolValue access$getAlwaysInRadiusValue$p() {
        return alwaysInRadiusValue;
    }

    static {
        String[] stringArray = new String[]{"Equals", "Contains"};
        tabModeValue = new ListValue("TabMode", stringArray, "Contains").displayable(tabModeValue.1.INSTANCE);
        entityIDValue = new BoolValue("EntityID", true);
        colorValue = new BoolValue("Color", false);
        livingTimeValue = new BoolValue("LivingTime", false);
        livingTimeTicksValue = new IntegerValue("LivingTimeTicks", 40, 1, 200).displayable(livingTimeTicksValue.1.INSTANCE);
        groundValue = new BoolValue("Ground", true);
        airValue = new BoolValue("Air", false);
        invalidGroundValue = new BoolValue("InvalidGround", true);
        swingValue = new BoolValue("Swing", false);
        healthValue = new BoolValue("Health", false);
        derpValue = new BoolValue("Derp", true);
        wasInvisibleValue = new BoolValue("WasInvisible", false);
        validNameValue = new BoolValue("ValidName", true);
        hiddenNameValue = new BoolValue("HiddenName", false);
        armorValue = new BoolValue("Armor", false);
        pingValue = new BoolValue("Ping", false);
        needHitValue = new BoolValue("NeedHit", false);
        noClipValue = new BoolValue("NoClip", false);
        czechHekValue = new BoolValue("CzechMatrix", false);
        czechHekPingCheckValue = new BoolValue("PingCheck", true).displayable(czechHekPingCheckValue.1.INSTANCE);
        czechHekGMCheckValue = new BoolValue("GamemodeCheck", true).displayable(czechHekGMCheckValue.1.INSTANCE);
        reusedEntityIdValue = new BoolValue("ReusedEntityId", false);
        spawnInCombatValue = new BoolValue("SpawnInCombat", false);
        duplicateInWorldValue = new BoolValue("DuplicateInWorld", false);
        duplicateInTabValue = new BoolValue("DuplicateInTab", false);
        stringArray = new String[]{"OnTime", "WhenSpawn"};
        duplicateCompareModeValue = new ListValue("DuplicateCompareMode", stringArray, "OnTime").displayable(duplicateCompareModeValue.1.INSTANCE);
        fastDamageValue = new BoolValue("FastDamage", false);
        fastDamageTicksValue = new IntegerValue("FastDamageTicks", 5, 1, 20).displayable(fastDamageTicksValue.1.INSTANCE);
        removeFromWorld = new BoolValue("RemoveFromWorld", false);
        removeIntervalValue = new IntegerValue("Remove-Interval", 20, 1, 100);
        debugValue = new BoolValue("Debug", false);
        alwaysInRadiusValue = new BoolValue("AlwaysInRadius", false);
        alwaysRadiusValue = new FloatValue("AlwaysInRadiusBlocks", 20.0f, 5.0f, 30.0f).displayable(alwaysRadiusValue.1.INSTANCE);
        alwaysInRadiusRemoveValue = new BoolValue("AlwaysInRadiusRemove", false).displayable(alwaysInRadiusRemoveValue.1.INSTANCE);
        alwaysInRadiusWithTicksCheckValue = new BoolValue("AlwaysInRadiusWithTicksCheck", false).displayable(alwaysInRadiusWithTicksCheckValue.1.INSTANCE);
        ground = new ArrayList();
        air = new ArrayList();
        invalidGround = new LinkedHashMap();
        swing = new ArrayList();
        invisible = new ArrayList();
        hitted = new ArrayList();
        spawnInCombat = new ArrayList();
        notAlwaysInRadius = new ArrayList();
        lastDamage = new LinkedHashMap();
        lastDamageVl = new LinkedHashMap();
        duplicate = new ArrayList();
        noClip = new ArrayList();
        hasRemovedEntities = new ArrayList();
        regex = new Regex("\\w{3,16}");
        wasAdded = MinecraftInstance.mc.field_71439_g != null;
    }
}

