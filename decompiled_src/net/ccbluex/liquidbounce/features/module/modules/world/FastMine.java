/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="FastMine", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/FastMine;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "boost", "", "damage", "", "facing", "Lnet/minecraft/util/EnumFacing;", "pos", "Lnet/minecraft/util/BlockPos;", "speedValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "onMotion", "", "e", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class FastMine
extends Module {
    @NotNull
    private final FloatValue speedValue = new FloatValue("Speed", 1.5f, 1.0f, 3.0f);
    @Nullable
    private EnumFacing facing;
    @Nullable
    private BlockPos pos;
    private boolean boost;
    private float damage;

    /*
     * WARNING - void declaration
     */
    @EventTarget
    public final void onMotion(@NotNull MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        if (e.isPre()) {
            MinecraftInstance.mc.field_71442_b.field_78781_i = 0;
            if (this.pos != null && this.boost) {
                float f;
                float f2;
                IBlockState iBlockState = MinecraftInstance.mc.field_71441_e.func_180495_p(this.pos);
                if (iBlockState == null) {
                    return;
                }
                IBlockState blockState = iBlockState;
                float f3 = this.damage;
                FastMine fastMine = this;
                try {
                    FastMine fastMine2 = fastMine;
                    f2 = f3;
                    f = blockState.func_177230_c().func_180647_a((EntityPlayer)MinecraftInstance.mc.field_71439_g, (World)MinecraftInstance.mc.field_71441_e, this.pos) * ((Number)this.speedValue.get()).floatValue();
                }
                catch (Exception exception) {
                    void ex;
                    FastMine fastMine3 = fastMine;
                    float f4 = f3;
                    ex.printStackTrace();
                    return;
                }
                fastMine2.damage = f2 + f;
                if (this.damage >= 1.0f) {
                    try {
                        MinecraftInstance.mc.field_71441_e.func_180501_a(this.pos, Blocks.field_150350_a.func_176223_P(), 11);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }
                    PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, this.facing)));
                    this.damage = 0.0f;
                    this.boost = false;
                }
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        if (e.getPacket() instanceof C07PacketPlayerDigging) {
            Packet<?> packet = e.getPacket();
            if (((C07PacketPlayerDigging)packet).func_180762_c() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                this.boost = true;
                this.pos = ((C07PacketPlayerDigging)packet).func_179715_a();
                this.facing = ((C07PacketPlayerDigging)packet).func_179714_b();
                this.damage = 0.0f;
            } else if (((C07PacketPlayerDigging)packet).func_180762_c() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK | ((C07PacketPlayerDigging)packet).func_180762_c() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                this.boost = false;
                this.pos = null;
                this.facing = null;
            }
        }
    }
}

