/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/matrix/MatrixCollideNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "needSpoof", "", "packet1Count", "", "packetModify", "onEnable", "", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class MatrixCollideNofall
extends NoFallMode {
    private int packet1Count;
    private boolean packetModify;
    private boolean needSpoof;

    public MatrixCollideNofall() {
        super("MatrixCollide");
    }

    @Override
    public void onEnable() {
        this.needSpoof = false;
        this.packetModify = false;
        this.packet1Count = 0;
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if ((double)((int)MinecraftInstance.mc.field_71439_g.field_70143_R) - MinecraftInstance.mc.field_71439_g.field_70181_x > 3.0) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70159_w *= 0.1;
            entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            entityPlayerSP.field_70179_y *= 0.1;
            this.needSpoof = true;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70143_R / (float)3 > (float)this.packet1Count) {
            this.packet1Count = (int)MinecraftInstance.mc.field_71439_g.field_70143_R / 3;
            this.packetModify = true;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            this.packet1Count = 0;
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C03PacketPlayer && this.needSpoof) {
            ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
            this.needSpoof = false;
        }
    }
}

