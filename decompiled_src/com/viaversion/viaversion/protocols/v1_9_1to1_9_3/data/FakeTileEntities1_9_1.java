/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_9_1to1_9_3.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

public class FakeTileEntities1_9_1 {
    private static final Int2ObjectMap<CompoundTag> tileEntities = new Int2ObjectOpenHashMap<CompoundTag>();

    private static void register(String name, int ... ids) {
        for (int id : ids) {
            CompoundTag comp = new CompoundTag();
            comp.put("id", new StringTag(name));
            tileEntities.put(id, comp);
        }
    }

    public static boolean isTileEntity(int block) {
        return tileEntities.containsKey(block);
    }

    public static CompoundTag createTileEntity(int x, int y, int z, int block) {
        CompoundTag originalTag = (CompoundTag)tileEntities.get(block);
        if (originalTag != null) {
            CompoundTag tag = originalTag.copy();
            tag.put("x", new IntTag(x));
            tag.put("y", new IntTag(y));
            tag.put("z", new IntTag(z));
            return tag;
        }
        return null;
    }

    static {
        FakeTileEntities1_9_1.register("Furnace", 61, 62);
        FakeTileEntities1_9_1.register("Chest", 54, 146);
        FakeTileEntities1_9_1.register("EnderChest", 130);
        FakeTileEntities1_9_1.register("RecordPlayer", 84);
        FakeTileEntities1_9_1.register("Trap", 23);
        FakeTileEntities1_9_1.register("Dropper", 158);
        FakeTileEntities1_9_1.register("Sign", 63, 68);
        FakeTileEntities1_9_1.register("MobSpawner", 52);
        FakeTileEntities1_9_1.register("Music", 25);
        FakeTileEntities1_9_1.register("Piston", 33, 34, 29, 36);
        FakeTileEntities1_9_1.register("Cauldron", 117);
        FakeTileEntities1_9_1.register("EnchantTable", 116);
        FakeTileEntities1_9_1.register("Airportal", 119, 120);
        FakeTileEntities1_9_1.register("Beacon", 138);
        FakeTileEntities1_9_1.register("Skull", 144);
        FakeTileEntities1_9_1.register("DLDetector", 178, 151);
        FakeTileEntities1_9_1.register("Hopper", 154);
        FakeTileEntities1_9_1.register("Comparator", 149, 150);
        FakeTileEntities1_9_1.register("FlowerPot", 140);
        FakeTileEntities1_9_1.register("Banner", 176, 177);
        FakeTileEntities1_9_1.register("EndGateway", 209);
        FakeTileEntities1_9_1.register("Control", 137);
    }
}

