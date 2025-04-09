/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.Event;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lnet/ccbluex/liquidbounce/event/EntityMovementEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "moveEntity", "Lnet/minecraft/entity/Entity;", "(Lnet/minecraft/entity/Entity;)V", "getMoveEntity", "()Lnet/minecraft/entity/Entity;", "CrossSine"})
public final class EntityMovementEvent
extends Event {
    @NotNull
    private final Entity moveEntity;

    public EntityMovementEvent(@NotNull Entity moveEntity) {
        Intrinsics.checkNotNullParameter(moveEntity, "moveEntity");
        this.moveEntity = moveEntity;
    }

    @NotNull
    public final Entity getMoveEntity() {
        return this.moveEntity;
    }
}

