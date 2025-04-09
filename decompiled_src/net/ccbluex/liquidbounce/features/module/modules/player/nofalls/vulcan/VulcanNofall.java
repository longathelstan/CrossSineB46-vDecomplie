/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.vulcan;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/vulcan/VulcanNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "doSpoof", "", "nextSpoof", "vulCanNoFall", "vulCantNoFall", "onEnable", "", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class VulcanNofall
extends NoFallMode {
    private boolean vulCanNoFall;
    private boolean vulCantNoFall;
    private boolean nextSpoof;
    private boolean doSpoof;

    public VulcanNofall() {
        super("Vulcan");
    }

    @Override
    public void onEnable() {
        this.vulCanNoFall = false;
        this.vulCantNoFall = false;
        this.nextSpoof = false;
        this.doSpoof = false;
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.vulCanNoFall && (double)MinecraftInstance.mc.field_71439_g.field_70143_R > 3.25) {
            this.vulCanNoFall = true;
        }
        if (this.vulCanNoFall && MinecraftInstance.mc.field_71439_g.field_70122_E && this.vulCantNoFall) {
            this.vulCantNoFall = false;
        }
        if (this.vulCantNoFall) {
            return;
        }
        if (this.nextSpoof) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -0.1;
            MinecraftInstance.mc.field_71439_g.field_70143_R = -0.1f;
            MovementUtils.INSTANCE.strafe(0.3f);
            this.nextSpoof = false;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70143_R > 3.5625f) {
            MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
            this.doSpoof = true;
            this.nextSpoof = true;
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C03PacketPlayer && this.doSpoof) {
            ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
            this.doSpoof = false;
            ((C03PacketPlayer)event.getPacket()).field_149477_b = (double)Math.round(MinecraftInstance.mc.field_71439_g.field_70163_u * (double)2) / (double)2;
            MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, ((C03PacketPlayer)event.getPacket()).field_149477_b, MinecraftInstance.mc.field_71439_g.field_70161_v);
        }
    }
}

