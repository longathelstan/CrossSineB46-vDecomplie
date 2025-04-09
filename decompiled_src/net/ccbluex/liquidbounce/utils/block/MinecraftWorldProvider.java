/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.block;

import me.liuli.path.Cell;
import me.liuli.path.IWorldProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockWall;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class MinecraftWorldProvider
implements IWorldProvider {
    private final World world;

    public MinecraftWorldProvider(World world) {
        this.world = world;
    }

    @Override
    public boolean isBlocked(Cell cell) {
        return this.isBlocked(cell.x, cell.y, cell.z);
    }

    public boolean isBlocked(int x, int y, int z) {
        return this.isSolid(x, y, z) || this.isSolid(x, y + 1, z) || this.unableToStand(x, y - 1, z);
    }

    private boolean isSolid(int x, int y, int z) {
        Block block = this.world.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
        if (block == null) {
            return true;
        }
        return block.func_149688_o().func_76220_a();
    }

    private boolean unableToStand(int x, int y, int z) {
        Block block = this.world.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
        return block instanceof BlockFence || block instanceof BlockWall;
    }
}

