/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import java.util.Map;
import java.util.StringJoiner;

public class PistonHandler
implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
    private final Object2IntMap<String> pistonIds = new Object2IntOpenHashMap<String>();

    public PistonHandler() {
        this.pistonIds.defaultReturnValue(-1);
        if (Via.getConfig().isServersideBlockConnections()) {
            Object2IntMap<String> keyToId = ConnectionData.getKeyToId();
            for (Map.Entry entry : keyToId.entrySet()) {
                if (!((String)entry.getKey()).contains("piston")) continue;
                this.addEntries((String)entry.getKey(), (Integer)entry.getValue());
            }
        } else {
            ListTag<StringTag> blockStates = MappingDataLoader.INSTANCE.loadNBT("blockstates-1.13.nbt").getListTag("blockstates", StringTag.class);
            for (int id = 0; id < blockStates.size(); ++id) {
                StringTag state = blockStates.get(id);
                String key = state.getValue();
                if (!key.contains("piston")) continue;
                this.addEntries(key, id);
            }
        }
    }

    private void addEntries(String data, int id) {
        id = Protocol1_13To1_12_2.MAPPINGS.getNewBlockStateId(id);
        this.pistonIds.put(data, id);
        String substring = data.substring(10);
        if (!substring.startsWith("piston") && !substring.startsWith("sticky_piston")) {
            return;
        }
        String[] split = data.substring(0, data.length() - 1).split("\\[");
        String[] properties = split[1].split(",");
        String string = properties[0];
        String string2 = properties[1];
        String string3 = split[0];
        data = string3 + "[" + string2 + "," + string + "]";
        this.pistonIds.put(data, id);
    }

    @Override
    public CompoundTag transform(int blockId, CompoundTag tag) {
        CompoundTag blockState = tag.getCompoundTag("blockState");
        if (blockState == null) {
            return tag;
        }
        String dataFromTag = this.getDataFromTag(blockState);
        if (dataFromTag == null) {
            return tag;
        }
        int id = this.pistonIds.getInt(dataFromTag);
        if (id == -1) {
            return tag;
        }
        tag.putInt("blockId", id >> 4);
        tag.putInt("blockData", id & 0xF);
        return tag;
    }

    private String getDataFromTag(CompoundTag tag) {
        StringTag name = tag.getStringTag("Name");
        if (name == null) {
            return null;
        }
        CompoundTag properties = tag.getCompoundTag("Properties");
        if (properties == null) {
            return name.getValue();
        }
        String string = name.getValue();
        StringJoiner joiner = new StringJoiner(",", string + "[", "]");
        for (Map.Entry<String, Tag> entry : properties) {
            if (!(entry.getValue() instanceof StringTag)) continue;
            String string2 = ((StringTag)entry.getValue()).getValue();
            String string3 = entry.getKey();
            joiner.add(string3 + "=" + string2);
        }
        return joiner.toString();
    }
}

