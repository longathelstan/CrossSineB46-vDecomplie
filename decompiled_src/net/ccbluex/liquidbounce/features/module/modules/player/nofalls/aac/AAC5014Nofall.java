/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.aac;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/aac/AAC5014Nofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "aac5Check", "", "aac5Timer", "", "aac5doFlag", "onEnable", "", "onNoFall", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class AAC5014Nofall
extends NoFallMode {
    private boolean aac5Check;
    private boolean aac5doFlag;
    private int aac5Timer;

    public AAC5014Nofall() {
        super("AAC5.0.14");
    }

    @Override
    public void onEnable() {
        this.aac5Check = false;
        this.aac5Timer = 0;
        this.aac5doFlag = false;
    }

    @Override
    public void onNoFall(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.aac5Check = false;
        for (double offsetYs = 0.0; MinecraftInstance.mc.field_71439_g.field_70181_x - 1.5 < offsetYs; offsetYs -= 0.5) {
            Block block;
            BlockPos blockPos = new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + offsetYs, MinecraftInstance.mc.field_71439_g.field_70161_v);
            Block block2 = block = BlockUtils.getBlock(blockPos);
            Intrinsics.checkNotNull(block2);
            AxisAlignedBB axisAlignedBB = block2.func_180640_a((World)MinecraftInstance.mc.field_71441_e, blockPos, BlockUtils.getState(blockPos));
            if (axisAlignedBB == null) continue;
            offsetYs = -999.9;
            this.aac5Check = true;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            MinecraftInstance.mc.field_71439_g.field_70143_R = -2.0f;
            this.aac5Check = false;
        }
        if (this.aac5Timer > 0) {
            --this.aac5Timer;
        }
        if (this.aac5Check && (double)MinecraftInstance.mc.field_71439_g.field_70143_R > 2.5 && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
            this.aac5doFlag = true;
            this.aac5Timer = 18;
        } else if (this.aac5Timer < 2) {
            this.aac5doFlag = false;
        }
        if (this.aac5doFlag) {
            if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.5, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            } else {
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 0.42, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
            }
        }
    }
}

