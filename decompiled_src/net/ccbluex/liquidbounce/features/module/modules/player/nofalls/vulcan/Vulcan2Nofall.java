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
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/vulcan/Vulcan2Nofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "doSpoof", "", "onEnable", "", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class Vulcan2Nofall
extends NoFallMode {
    private boolean doSpoof;

    public Vulcan2Nofall() {
        super("Vulcan2");
    }

    @Override
    public void onEnable() {
        this.doSpoof = false;
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R > 2.0) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.9f;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        }
        if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R > 2.8) {
            this.doSpoof = true;
            MinecraftInstance.mc.field_71439_g.field_70181_x = -0.1;
            MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
            MinecraftInstance.mc.field_71439_g.field_70181_x += MinecraftInstance.mc.field_71439_g.field_70181_x / 10.0;
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C03PacketPlayer && this.doSpoof) {
            ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
            this.doSpoof = false;
        }
    }
}

