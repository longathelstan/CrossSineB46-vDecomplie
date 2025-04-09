/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.matrix;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/matrix/Matrix663Nofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "firstNfall", "", "matrixSafe", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "matrixSend", "nearGround", "onDisable", "", "onEnable", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class Matrix663Nofall
extends NoFallMode {
    private boolean matrixSend;
    @NotNull
    private final BoolValue matrixSafe = new BoolValue("SafeNoFall", true);
    private boolean firstNfall = true;
    private boolean nearGround;

    public Matrix663Nofall() {
        super("Matrix6.6.3");
    }

    @Override
    public void onEnable() {
        this.matrixSend = false;
        this.firstNfall = true;
        this.nearGround = false;
    }

    @Override
    public void onDisable() {
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        block5: {
            block4: {
                int n;
                Intrinsics.checkNotNullParameter(event, "event");
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                FallingPlayer fallingPlayer = new FallingPlayer((EntityPlayer)entityPlayerSP);
                BlockPos collLoc = fallingPlayer.findCollision(60);
                if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R - MinecraftInstance.mc.field_71439_g.field_70181_x > 3.0) break block4;
                BlockPos blockPos = collLoc;
                int n2 = blockPos == null ? 0 : (n = blockPos.func_177956_o());
                if (!(Math.abs((double)n2 - MinecraftInstance.mc.field_71439_g.field_70163_u) < 3.0) || !((double)MinecraftInstance.mc.field_71439_g.field_70143_R - MinecraftInstance.mc.field_71439_g.field_70181_x > 2.0)) break block5;
            }
            MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
            this.matrixSend = true;
            if (((Boolean)this.matrixSafe.get()).booleanValue()) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = 0.3f;
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 0.5;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 0.5;
                return;
            }
            MinecraftInstance.mc.field_71428_T.field_74278_d = 0.5f;
            return;
        }
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getPacket() instanceof C03PacketPlayer && this.matrixSend) {
            int n;
            BlockPos collLoc;
            this.matrixSend = false;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            FallingPlayer fallingPlayer = new FallingPlayer((EntityPlayer)entityPlayerSP);
            BlockPos blockPos = collLoc = fallingPlayer.findCollision(60);
            int n2 = blockPos == null ? 0 : (n = blockPos.func_177956_o());
            if (Math.abs((double)n2 - MinecraftInstance.mc.field_71439_g.field_70163_u) > 2.0) {
                event.cancelEvent();
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer)event.getPacket()).field_149479_a, ((C03PacketPlayer)event.getPacket()).field_149477_b, ((C03PacketPlayer)event.getPacket()).field_149478_c, true));
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((C03PacketPlayer)event.getPacket()).field_149479_a, ((C03PacketPlayer)event.getPacket()).field_149477_b, ((C03PacketPlayer)event.getPacket()).field_149478_c, false));
            }
        }
    }
}

