/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.data;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.util.IdAndData;
import net.raphimc.vialegacy.api.data.BlockList1_6;

public class ExtendedClassicBlocks {
    public static final int COBBLESTONE_SLAB = 50;
    public static final int ROPE = 51;
    public static final int SANDSTONE = 52;
    public static final int SNOW = 53;
    public static final int FIRE = 54;
    public static final int LIGHT_PINK_WOOL = 55;
    public static final int FOREST_GREEN_WOOL = 56;
    public static final int BROWN_WOOL = 57;
    public static final int DEEP_BLUE_WOOL = 58;
    public static final int TURQUOISE_WOOL = 59;
    public static final int ICE = 60;
    public static final int CERAMIC_TILE = 61;
    public static final int MAGMA = 62;
    public static final int PILLAR = 63;
    public static final int CRATE = 64;
    public static final int STONE_BRICK = 65;
    public static Int2ObjectMap<IdAndData> MAPPING = new Int2ObjectOpenHashMap<IdAndData>(15, 0.99f);
    public static Object2IntMap<IdAndData> REVERSE_MAPPING = new Object2IntOpenHashMap<IdAndData>(15, 0.99f);

    static {
        MAPPING.defaultReturnValue(new IdAndData(BlockList1_6.stone.blockId(), 0));
        REVERSE_MAPPING.defaultReturnValue(1);
        MAPPING.put(50, new IdAndData(BlockList1_6.stoneSingleSlab.blockId(), 3));
        MAPPING.put(51, new IdAndData(BlockList1_6.deadBush.blockId(), 0));
        MAPPING.put(52, new IdAndData(BlockList1_6.sandStone.blockId(), 0));
        MAPPING.put(53, new IdAndData(BlockList1_6.snow.blockId(), 0));
        MAPPING.put(54, new IdAndData(BlockList1_6.torchWood.blockId(), 5));
        MAPPING.put(55, new IdAndData(BlockList1_6.cloth.blockId(), 6));
        MAPPING.put(56, new IdAndData(BlockList1_6.cloth.blockId(), 13));
        MAPPING.put(57, new IdAndData(BlockList1_6.cloth.blockId(), 12));
        MAPPING.put(58, new IdAndData(BlockList1_6.cloth.blockId(), 11));
        MAPPING.put(59, new IdAndData(BlockList1_6.cloth.blockId(), 9));
        MAPPING.put(60, new IdAndData(BlockList1_6.ice.blockId(), 0));
        MAPPING.put(61, new IdAndData(BlockList1_6.blockNetherQuartz.blockId(), 1));
        MAPPING.put(62, new IdAndData(BlockList1_6.oreNetherQuartz.blockId(), 0));
        MAPPING.put(63, new IdAndData(BlockList1_6.blockNetherQuartz.blockId(), 2));
        MAPPING.put(64, new IdAndData(BlockList1_6.jukebox.blockId(), 0));
        MAPPING.put(65, new IdAndData(BlockList1_6.stoneBrick.blockId(), 0));
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.stoneSingleSlab.blockId(), 3), 50);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.deadBush.blockId(), 0), 51);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.sandStone.blockId(), 0), 52);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.snow.blockId(), 0), 53);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.torchWood.blockId(), 0), 54);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.cloth.blockId(), 12), 57);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.ice.blockId(), 0), 60);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.blockNetherQuartz.blockId(), 1), 61);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.oreNetherQuartz.blockId(), 0), 62);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.blockNetherQuartz.blockId(), 2), 63);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.jukebox.blockId(), 0), 64);
        REVERSE_MAPPING.put(new IdAndData(BlockList1_6.stoneBrick.blockId(), 0), 65);
    }
}

