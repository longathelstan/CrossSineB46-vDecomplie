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
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/matrix/Matrix62xNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "matrixCanSpoof", "", "matrixFallTicks", "", "matrixIsFall", "matrixLastMotionY", "", "onEnable", "", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class Matrix62xNofall
extends NoFallMode {
    private boolean matrixCanSpoof;
    private int matrixFallTicks;
    private boolean matrixIsFall;
    private double matrixLastMotionY;

    public Matrix62xNofall() {
        super("Matrix6.2.X");
    }

    @Override
    public void onEnable() {
        this.matrixCanSpoof = false;
        this.matrixFallTicks = 0;
        this.matrixIsFall = false;
        this.matrixLastMotionY = 0.0;
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.matrixIsFall) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                this.matrixIsFall = false;
            }
        }
        if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R - MinecraftInstance.mc.field_71439_g.field_70181_x > 3.0) {
            this.matrixIsFall = true;
            if (this.matrixFallTicks == 0) {
                this.matrixLastMotionY = MinecraftInstance.mc.field_71439_g.field_70181_x;
            }
            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
            MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
            MinecraftInstance.mc.field_71439_g.field_70143_R = 3.2f;
            int n = this.matrixFallTicks;
            boolean bl = 8 <= n ? n < 10 : false;
            if (bl) {
                this.matrixCanSpoof = true;
            }
            n = this.matrixFallTicks;
            this.matrixFallTicks = n + 1;
        }
        if (this.matrixFallTicks > 12 && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = this.matrixLastMotionY;
            MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
            this.matrixFallTicks = 0;
            this.matrixCanSpoof = false;
        }
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C03PacketPlayer && this.matrixCanSpoof) {
            ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
            this.matrixCanSpoof = false;
        }
    }
}

