/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.List;
import me.liuli.path.Cell;
import me.liuli.path.Pathfinder;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.MinecraftWorldProvider;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public final class PathUtils
extends MinecraftInstance {
    public static List<Vec3> findBlinkPath(double tpX, double tpY, double tpZ) {
        return PathUtils.findBlinkPath(tpX, tpY, tpZ, 5.0);
    }

    public static List<Vec3> findBlinkPath(double tpX, double tpY, double tpZ, double dist) {
        return PathUtils.findBlinkPath(PathUtils.mc.field_71439_g.field_70165_t, PathUtils.mc.field_71439_g.field_70163_u, PathUtils.mc.field_71439_g.field_70161_v, tpX, tpY, tpZ, dist);
    }

    public static List<Vec3> findBlinkPath(double curX, double curY, double curZ, double tpX, double tpY, double tpZ, double dashDistance) {
        MinecraftWorldProvider worldProvider = new MinecraftWorldProvider((World)PathUtils.mc.field_71441_e);
        Pathfinder pathfinder = new Pathfinder(new Cell((int)curX, (int)curY, (int)curZ), new Cell((int)tpX, (int)tpY, (int)tpZ), Pathfinder.COMMON_NEIGHBORS, worldProvider);
        return PathUtils.simplifyPath(pathfinder.findPath(3000), dashDistance, worldProvider);
    }

    public static ArrayList<Vec3> simplifyPath(ArrayList<Cell> path, double dashDistance, MinecraftWorldProvider worldProvider) {
        Vec3 lastLoc;
        ArrayList<Vec3> finalPath = new ArrayList<Vec3>();
        Cell cell = path.get(0);
        Vec3 lastDashLoc = lastLoc = new Vec3((double)cell.x + 0.5, (double)cell.y, (double)cell.z + 0.5);
        for (int i = 1; i < path.size() - 1; ++i) {
            cell = path.get(i);
            Vec3 vec3 = new Vec3((double)cell.x + 0.5, (double)cell.y, (double)cell.z + 0.5);
            boolean canContinue = true;
            if (vec3.func_72436_e(lastDashLoc) > dashDistance * dashDistance) {
                canContinue = false;
            } else {
                double smallX = Math.min(lastDashLoc.field_72450_a, vec3.field_72450_a);
                double smallY = Math.min(lastDashLoc.field_72448_b, vec3.field_72448_b);
                double smallZ = Math.min(lastDashLoc.field_72449_c, vec3.field_72449_c);
                double bigX = Math.max(lastDashLoc.field_72450_a, vec3.field_72450_a);
                double bigY = Math.max(lastDashLoc.field_72448_b, vec3.field_72448_b);
                double bigZ = Math.max(lastDashLoc.field_72449_c, vec3.field_72449_c);
                int x = (int)smallX;
                block1: while ((double)x <= bigX) {
                    int y = (int)smallY;
                    while ((double)y <= bigY) {
                        int z = (int)smallZ;
                        while ((double)z <= bigZ) {
                            if (worldProvider.isBlocked(x, y, z)) {
                                canContinue = false;
                                break block1;
                            }
                            ++z;
                        }
                        ++y;
                    }
                    ++x;
                }
            }
            if (!canContinue) {
                finalPath.add(lastLoc);
                lastDashLoc = lastLoc;
            }
            lastLoc = vec3;
        }
        return finalPath;
    }
}

