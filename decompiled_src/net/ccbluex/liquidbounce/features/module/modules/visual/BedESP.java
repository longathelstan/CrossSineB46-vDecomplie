/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="BedESP", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0011H\u0007R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/BedESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "posList", "", "Lkotlin/Pair;", "Lnet/minecraft/util/BlockPos;", "searchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "findBlocks", "type", "Lnet/minecraft/block/BlockBed$EnumPartType;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class BedESP
extends Module {
    @NotNull
    private final MSTimer searchTimer = new MSTimer();
    @NotNull
    private final List<Pair<BlockPos, BlockPos>> posList = new ArrayList();

    private final BlockPos findBlocks(BlockBed.EnumPartType type) {
        int radius = 50;
        BlockPos playerPos = MinecraftInstance.mc.field_71439_g.func_180425_c();
        BlockPos closestPos = null;
        double closestDistance = Double.MAX_VALUE;
        int n = -radius;
        if (n <= radius) {
            int x;
            do {
                int y;
                x = n++;
                int n2 = -radius;
                if (n2 > radius) continue;
                do {
                    int z;
                    y = n2++;
                    int n3 = -radius;
                    if (n3 > radius) continue;
                    do {
                        double distance;
                        BlockPos blockPos;
                        IBlockState blockState;
                        Block block;
                        if (!((block = (blockState = MinecraftInstance.mc.field_71441_e.func_180495_p(blockPos = playerPos.func_177982_a(x, y, z = n3++))).func_177230_c()) instanceof BlockBed) || blockState.func_177229_b((IProperty)BlockBed.field_176472_a) != type || !((distance = MinecraftInstance.mc.field_71439_g.func_174831_c(blockPos)) < closestDistance)) continue;
                        closestPos = blockPos;
                        closestDistance = distance;
                    } while (z != radius);
                } while (y != radius);
            } while (x != radius);
        }
        return closestPos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.searchTimer.hasTimePassed(1000L)) {
            List<Pair<BlockPos, BlockPos>> list = this.posList;
            synchronized (list) {
                boolean bl = false;
                this.posList.clear();
                BlockPos headPos = this.findBlocks(BlockBed.EnumPartType.HEAD);
                BlockPos footPos = this.findBlocks(BlockBed.EnumPartType.FOOT);
                if (headPos != null && footPos != null) {
                    this.posList.add(new Pair<BlockPos, BlockPos>(headPos, footPos));
                }
                Unit unit = Unit.INSTANCE;
            }
            this.searchTimer.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        List<Pair<BlockPos, BlockPos>> list = this.posList;
        synchronized (list) {
            boolean bl = false;
            for (Pair<BlockPos, BlockPos> pair : this.posList) {
                BlockPos headPos = pair.component1();
                BlockPos footPos = pair.component2();
                RenderUtils.drawBlockBox(headPos, footPos, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 80, false, 4, null), false);
            }
            Unit unit = Unit.INSTANCE;
        }
        GlStateManager.func_179117_G();
    }
}

