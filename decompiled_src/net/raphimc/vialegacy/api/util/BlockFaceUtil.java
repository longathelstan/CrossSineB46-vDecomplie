/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.util;

import com.viaversion.viaversion.api.minecraft.BlockFace;

public class BlockFaceUtil {
    public static BlockFace getFace(int direction) {
        BlockFace blockFace;
        switch (direction) {
            case 0: {
                blockFace = BlockFace.BOTTOM;
                break;
            }
            default: {
                blockFace = BlockFace.TOP;
                break;
            }
            case 2: {
                blockFace = BlockFace.NORTH;
                break;
            }
            case 3: {
                blockFace = BlockFace.SOUTH;
                break;
            }
            case 4: {
                blockFace = BlockFace.WEST;
                break;
            }
            case 5: {
                blockFace = BlockFace.EAST;
            }
        }
        return blockFace;
    }
}

