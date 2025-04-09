/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/aac/OldAACNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "oldaacState", "", "onEnable", "", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class OldAACNofall
extends NoFallMode {
    private int oldaacState;

    public OldAACNofall() {
        super("OldAAC");
    }

    @Override
    public void onEnable() {
        this.oldaacState = 0;
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70143_R > 2.0f) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer(true));
            this.oldaacState = 2;
        } else if (this.oldaacState == 2 && MinecraftInstance.mc.field_71439_g.field_70143_R < 2.0f) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
            this.oldaacState = 3;
            return;
        }
        switch (this.oldaacState) {
            case 3: {
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
                this.oldaacState = 4;
                break;
            }
            case 4: {
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
                this.oldaacState = 5;
                break;
            }
            case 5: {
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.1;
                this.oldaacState = 1;
            }
        }
    }
}

