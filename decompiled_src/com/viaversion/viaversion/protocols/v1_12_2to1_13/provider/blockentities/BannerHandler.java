/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.blockentities;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.BlockEntityProvider;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.storage.BlockStorage;
import com.viaversion.viaversion.util.ComponentUtil;

public class BannerHandler
implements BlockEntityProvider.BlockEntityHandler {
    private static final int WALL_BANNER_START = 7110;
    private static final int WALL_BANNER_STOP = 7173;
    private static final int BANNER_START = 6854;
    private static final int BANNER_STOP = 7109;

    @Override
    public int transform(UserConnection user, CompoundTag tag) {
        StringTag name;
        int color;
        BlockPosition position;
        BlockStorage storage = user.get(BlockStorage.class);
        if (!storage.contains(position = new BlockPosition(tag.getNumberTag("x").asInt(), tag.getNumberTag("y").asShort(), tag.getNumberTag("z").asInt()))) {
            CompoundTag compoundTag = tag;
            Protocol1_12_2To1_13.LOGGER.warning("Received an banner color update packet, but there is no banner! O_o " + compoundTag);
            return -1;
        }
        int blockId = storage.get(position).getOriginal();
        NumberTag base = tag.getNumberTag("Base");
        int n = color = base != null ? base.asInt() : 0;
        if (blockId >= 6854 && blockId <= 7109) {
            blockId += (15 - color) * 16;
        } else if (blockId >= 7110 && blockId <= 7173) {
            blockId += (15 - color) * 4;
        } else {
            CompoundTag compoundTag = tag;
            Protocol1_12_2To1_13.LOGGER.warning("Why does this block have the banner block entity? :(" + compoundTag);
        }
        ListTag<CompoundTag> patterns = tag.getListTag("Patterns", CompoundTag.class);
        if (patterns != null) {
            for (CompoundTag pattern : patterns) {
                NumberTag colorTag = pattern.getNumberTag("Color");
                if (colorTag == null) continue;
                pattern.putInt("Color", 15 - colorTag.asInt());
            }
        }
        if ((name = tag.getStringTag("CustomName")) != null) {
            name.setValue(ComponentUtil.legacyToJsonString(name.getValue()));
        }
        return blockId;
    }
}

