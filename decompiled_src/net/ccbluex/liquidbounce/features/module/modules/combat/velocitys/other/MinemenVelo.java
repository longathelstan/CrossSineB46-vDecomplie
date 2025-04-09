/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\rH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/other/MinemenVelo;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/velocitys/VelocityMode;", "()V", "canCancel", "", "lastCancel", "ticks", "", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MinemenVelo
extends VelocityMode {
    private int ticks;
    private boolean lastCancel;
    private boolean canCancel;

    public MinemenVelo() {
        super("Minemen");
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int n = this.ticks;
        this.ticks = n + 1;
        if (this.ticks > 23) {
            this.canCancel = true;
        }
        boolean bl = 2 <= (n = this.ticks) ? n < 5 : false;
        if (bl && !this.lastCancel) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70159_w *= 0.99;
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70179_y *= 0.99;
        } else if (this.ticks == 5 && !this.lastCancel) {
            MovementUtils.INSTANCE.strafe();
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        block6: {
            block8: {
                block7: {
                    Intrinsics.checkNotNullParameter(event, "event");
                    Packet<?> packet = event.getPacket();
                    if (!(packet instanceof S12PacketEntityVelocity)) break block6;
                    if (MinecraftInstance.mc.field_71439_g == null) break block7;
                    WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
                    Entity entity = worldClient == null ? null : worldClient.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
                    if (entity == null) {
                        return;
                    }
                    if (Intrinsics.areEqual(entity, MinecraftInstance.mc.field_71439_g)) break block8;
                }
                return;
            }
            this.ticks = 0;
            if (this.canCancel) {
                event.cancelEvent();
                this.lastCancel = true;
                this.canCancel = false;
            } else {
                MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                this.lastCancel = false;
            }
        }
    }
}

