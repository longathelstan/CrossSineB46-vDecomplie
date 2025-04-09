/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.normal;

import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/normal/PhaseNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "phaseOffsetValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "onNoFall", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class PhaseNofall
extends NoFallMode {
    @NotNull
    private final IntegerValue phaseOffsetValue = new IntegerValue(Intrinsics.stringPlus(this.getValuePrefix(), "PhaseOffset"), 1, 0, 5);

    public PhaseNofall() {
        super("Phase");
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70143_R > (float)(3 + ((Number)this.phaseOffsetValue.get()).intValue())) {
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            Object object = new FallingPlayer((EntityPlayer)entityPlayerSP).findCollision(5);
            if (object == null) {
                return;
            }
            Object fallPos = object;
            if ((double)fallPos.func_177956_o() - MinecraftInstance.mc.field_71439_g.field_70181_x / 20.0 < MinecraftInstance.mc.field_71439_g.field_70163_u) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = 0.05f;
                object = new Timer();
                long l = 100L;
                TimerTask timerTask2 = new TimerTask((BlockPos)fallPos){
                    final /* synthetic */ BlockPos $fallPos$inlined;
                    {
                        this.$fallPos$inlined = blockPos;
                    }

                    public void run() {
                        TimerTask $this$onNoFall_u24lambda_u2d0 = this;
                        boolean bl = false;
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition((double)this.$fallPos$inlined.func_177958_n(), (double)this.$fallPos$inlined.func_177956_o(), (double)this.$fallPos$inlined.func_177952_p(), true));
                        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
                    }
                };
                ((Timer)object).schedule(timerTask2, l);
            }
        }
    }
}

