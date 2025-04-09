/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.BannerHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.BedHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.PistonHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.SkullHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.block_entity_handlers.SpawnerHandler;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;

public class BackwardsBlockEntityProvider
implements Provider {
    final Map<String, BackwardsBlockEntityHandler> handlers = new HashMap<String, BackwardsBlockEntityHandler>();

    public BackwardsBlockEntityProvider() {
        this.handlers.put("flower_pot", new FlowerPotHandler());
        this.handlers.put("bed", new BedHandler());
        this.handlers.put("banner", new BannerHandler());
        this.handlers.put("skull", new SkullHandler());
        this.handlers.put("mob_spawner", new SpawnerHandler());
        this.handlers.put("piston", new PistonHandler());
    }

    public boolean isHandled(String key) {
        return this.handlers.containsKey(Key.stripMinecraftNamespace(key));
    }

    public CompoundTag transform(UserConnection user, BlockPosition position, CompoundTag tag) {
        StringTag idTag = tag.getStringTag("id");
        if (idTag == null) {
            return tag;
        }
        String id = idTag.getValue();
        BackwardsBlockEntityHandler handler = this.handlers.get(Key.stripMinecraftNamespace(id));
        if (handler == null) {
            return tag;
        }
        BackwardsBlockStorage storage = user.get(BackwardsBlockStorage.class);
        Integer blockId = storage.get(position);
        if (blockId == null) {
            return tag;
        }
        return handler.transform(blockId, tag);
    }

    public CompoundTag transform(UserConnection user, BlockPosition position, String id) {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", id);
        tag.putInt("x", Math.toIntExact(position.x()));
        tag.putInt("y", Math.toIntExact(position.y()));
        tag.putInt("z", Math.toIntExact(position.z()));
        return this.transform(user, position, tag);
    }

    @FunctionalInterface
    public static interface BackwardsBlockEntityHandler {
        public CompoundTag transform(int var1, CompoundTag var2);
    }
}

