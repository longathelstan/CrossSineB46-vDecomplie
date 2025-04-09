/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.other;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0011H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/other/BoatDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "canModify", "", "noGroundValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getNearBoat", "Lnet/minecraft/entity/Entity;", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class BoatDisabler
extends DisablerMode {
    private boolean canModify;
    @NotNull
    private final BoolValue noGroundValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "NoGround"), false);

    public BoatDisabler() {
        super("Boat");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70154_o != null) {
            MinecraftInstance.mc.field_71439_g.field_70125_A = 90.0f;
            MinecraftInstance.mc.field_71439_g.func_71038_i();
            MinecraftInstance.mc.field_71442_b.func_78764_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71439_g.field_70154_o);
            MinecraftInstance.mc.field_71439_g.func_71038_i();
            MinecraftInstance.mc.field_71442_b.func_78764_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, this.getNearBoat());
            this.canModify = true;
            this.getDisabler().debugMessage("Destroy Boat");
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (MinecraftInstance.mc.field_71439_g.field_70154_o != null) {
            this.canModify = true;
        }
        if (this.canModify && packet instanceof C03PacketPlayer) {
            ((C03PacketPlayer)packet).field_149474_g = (Boolean)this.noGroundValue.get() == false;
        }
    }

    @Override
    public void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.canModify = false;
    }

    @Override
    public void onEnable() {
        this.getDisabler().debugMessage("Place 2 Boats Next To Each other And Right Click To Use It!");
        this.canModify = false;
    }

    private final Entity getNearBoat() {
        List entities = MinecraftInstance.mc.field_71441_e.field_72996_f;
        for (Entity entity_ : entities) {
            if (!(entity_ instanceof EntityBoat) || Intrinsics.areEqual(entity_, MinecraftInstance.mc.field_71439_g.field_70154_o)) continue;
            return entity_;
        }
        return null;
    }
}

