/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_15_2to1_16.data;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.LongTag;
import com.viaversion.nbt.tag.StringTag;
import java.util.Arrays;

public class DimensionRegistries1_16 {
    private static final CompoundTag DIMENSIONS_TAG = new CompoundTag();
    private static final String[] WORLD_NAMES = new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};

    public static CompoundTag getDimensionsTag() {
        return DIMENSIONS_TAG.copy();
    }

    public static String[] getWorldNames() {
        return Arrays.copyOf(WORLD_NAMES, WORLD_NAMES.length);
    }

    private static CompoundTag createOverworldEntry() {
        CompoundTag tag = new CompoundTag();
        tag.put("name", new StringTag("minecraft:overworld"));
        tag.put("has_ceiling", new ByteTag(0));
        DimensionRegistries1_16.addSharedOverwaldEntries(tag);
        return tag;
    }

    private static CompoundTag createOverworldCavesEntry() {
        CompoundTag tag = new CompoundTag();
        tag.put("name", new StringTag("minecraft:overworld_caves"));
        tag.put("has_ceiling", new ByteTag(1));
        DimensionRegistries1_16.addSharedOverwaldEntries(tag);
        return tag;
    }

    private static void addSharedOverwaldEntries(CompoundTag tag) {
        tag.put("piglin_safe", new ByteTag(0));
        tag.put("natural", new ByteTag(1));
        tag.put("ambient_light", new FloatTag(0.0f));
        tag.put("infiniburn", new StringTag("minecraft:infiniburn_overworld"));
        tag.put("respawn_anchor_works", new ByteTag(0));
        tag.put("has_skylight", new ByteTag(1));
        tag.put("bed_works", new ByteTag(1));
        tag.put("has_raids", new ByteTag(1));
        tag.put("logical_height", new IntTag(256));
        tag.put("shrunk", new ByteTag(0));
        tag.put("ultrawarm", new ByteTag(0));
    }

    private static CompoundTag createNetherEntry() {
        CompoundTag tag = new CompoundTag();
        tag.put("piglin_safe", new ByteTag(1));
        tag.put("natural", new ByteTag(0));
        tag.put("ambient_light", new FloatTag(0.1f));
        tag.put("infiniburn", new StringTag("minecraft:infiniburn_nether"));
        tag.put("respawn_anchor_works", new ByteTag(1));
        tag.put("has_skylight", new ByteTag(0));
        tag.put("bed_works", new ByteTag(0));
        tag.put("fixed_time", new LongTag(18000L));
        tag.put("has_raids", new ByteTag(0));
        tag.put("name", new StringTag("minecraft:the_nether"));
        tag.put("logical_height", new IntTag(128));
        tag.put("shrunk", new ByteTag(1));
        tag.put("ultrawarm", new ByteTag(1));
        tag.put("has_ceiling", new ByteTag(1));
        return tag;
    }

    private static CompoundTag createEndEntry() {
        CompoundTag tag = new CompoundTag();
        tag.put("piglin_safe", new ByteTag(0));
        tag.put("natural", new ByteTag(0));
        tag.put("ambient_light", new FloatTag(0.0f));
        tag.put("infiniburn", new StringTag("minecraft:infiniburn_end"));
        tag.put("respawn_anchor_works", new ByteTag(0));
        tag.put("has_skylight", new ByteTag(0));
        tag.put("bed_works", new ByteTag(0));
        tag.put("fixed_time", new LongTag(6000L));
        tag.put("has_raids", new ByteTag(1));
        tag.put("name", new StringTag("minecraft:the_end"));
        tag.put("logical_height", new IntTag(256));
        tag.put("shrunk", new ByteTag(0));
        tag.put("ultrawarm", new ByteTag(0));
        tag.put("has_ceiling", new ByteTag(0));
        return tag;
    }

    static {
        ListTag<CompoundTag> list = new ListTag<CompoundTag>(CompoundTag.class);
        list.add(DimensionRegistries1_16.createOverworldEntry());
        list.add(DimensionRegistries1_16.createOverworldCavesEntry());
        list.add(DimensionRegistries1_16.createNetherEntry());
        list.add(DimensionRegistries1_16.createEndEntry());
        DIMENSIONS_TAG.put("dimension", list);
    }
}

