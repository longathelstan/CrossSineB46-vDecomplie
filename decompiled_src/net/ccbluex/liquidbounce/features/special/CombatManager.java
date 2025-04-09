/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.special;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EntityKilledEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0016\u001a\u00020\rH\u0016J\u000e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\nJ\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020 H\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR\u001e\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\"\u0010\u0013\u001a\u0004\u0018\u00010\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006!"}, d2={"Lnet/ccbluex/liquidbounce/features/special/CombatManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "attackedEntityList", "", "Lnet/minecraft/entity/EntityLivingBase;", "getAttackedEntityList", "()Ljava/util/List;", "focusedPlayerList", "Lnet/minecraft/entity/player/EntityPlayer;", "getFocusedPlayerList", "<set-?>", "", "inCombat", "getInCombat", "()Z", "lastAttackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "target", "getTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "handleEvents", "isFocusEntity", "entity", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class CombatManager
extends MinecraftInstance
implements Listenable {
    @NotNull
    private final MSTimer lastAttackTimer = new MSTimer();
    private boolean inCombat;
    @Nullable
    private EntityLivingBase target;
    @NotNull
    private final List<EntityLivingBase> attackedEntityList = new ArrayList();
    @NotNull
    private final List<EntityPlayer> focusedPlayerList = new ArrayList();

    public final boolean getInCombat() {
        return this.inCombat;
    }

    @Nullable
    public final EntityLivingBase getTarget() {
        return this.target;
    }

    @NotNull
    public final List<EntityLivingBase> getAttackedEntityList() {
        return this.attackedEntityList;
    }

    @NotNull
    public final List<EntityPlayer> getFocusedPlayerList() {
        return this.focusedPlayerList;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        MovementUtils.INSTANCE.updateBlocksPerSecond();
        Iterable $this$map$iv = this.attackedEntityList;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            EntityLivingBase entityLivingBase = (EntityLivingBase)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            EntityLivingBase it = (EntityLivingBase)element$iv;
            boolean bl = false;
            if (!it.field_70128_L) continue;
            CrossSine.INSTANCE.getEventManager().callEvent(new EntityKilledEvent(it));
            this.getAttackedEntityList().remove(it);
        }
        if (!this.lastAttackTimer.hasTimePassed(1000L)) {
            this.inCombat = true;
            return;
        }
        if (this.target == null) return;
        if (!(MinecraftInstance.mc.field_71439_g.func_70032_d((Entity)this.target) > 7.0f) && this.inCombat) {
            EntityLivingBase entityLivingBase = this.target;
            Intrinsics.checkNotNull(entityLivingBase);
            if (!entityLivingBase.field_70128_L) {
                this.inCombat = true;
                return;
            }
        }
        this.target = null;
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Entity target = event.getTargetEntity();
        if (target instanceof EntityLivingBase && EntityUtils.INSTANCE.isSelected(target, true)) {
            this.target = (EntityLivingBase)target;
            if (!this.attackedEntityList.contains(target)) {
                this.attackedEntityList.add((EntityLivingBase)target);
            }
        }
        this.lastAttackTimer.reset();
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.inCombat = false;
        this.target = null;
        this.attackedEntityList.clear();
        this.focusedPlayerList.clear();
    }

    public final boolean isFocusEntity(@NotNull EntityPlayer entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (this.focusedPlayerList.isEmpty()) {
            return true;
        }
        return this.focusedPlayerList.contains(entity);
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

