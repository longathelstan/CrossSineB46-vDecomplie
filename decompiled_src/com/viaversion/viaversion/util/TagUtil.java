/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.util.Key;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class TagUtil {
    public static ListTag<CompoundTag> getRegistryEntries(CompoundTag tag, String key) {
        return TagUtil.getRegistryEntries(tag, key, null);
    }

    public static ListTag<CompoundTag> getRegistryEntries(CompoundTag tag, String key, @Nullable ListTag<CompoundTag> defaultValue) {
        CompoundTag registry = TagUtil.getNamespacedCompoundTag(tag, key);
        if (registry == null) {
            return defaultValue;
        }
        return registry.getListTag("value", CompoundTag.class);
    }

    public static ListTag<CompoundTag> removeRegistryEntries(CompoundTag tag, String key) {
        return TagUtil.removeRegistryEntries(tag, key, null);
    }

    public static ListTag<CompoundTag> removeRegistryEntries(CompoundTag tag, String key, @Nullable ListTag<CompoundTag> defaultValue) {
        String currentKey = Key.namespaced(key);
        CompoundTag registry = tag.getCompoundTag(currentKey);
        if (registry == null && (registry = tag.getCompoundTag(currentKey = Key.stripMinecraftNamespace(key))) == null) {
            return defaultValue;
        }
        tag.remove(currentKey);
        return registry.getListTag("value", CompoundTag.class);
    }

    public static boolean removeNamespaced(CompoundTag tag, String key) {
        return tag.remove(Key.namespaced(key)) != null || tag.remove(Key.stripMinecraftNamespace(key)) != null;
    }

    public static @Nullable CompoundTag getNamespacedCompoundTag(CompoundTag tag, String key) {
        CompoundTag compoundTag = tag.getCompoundTag(Key.namespaced(key));
        return compoundTag != null ? compoundTag : tag.getCompoundTag(Key.stripMinecraftNamespace(key));
    }

    public static @Nullable ListTag<CompoundTag> getNamespacedCompoundTagList(CompoundTag tag, String key) {
        ListTag<CompoundTag> listTag = tag.getListTag(Key.namespaced(key), CompoundTag.class);
        return listTag != null ? listTag : tag.getListTag(Key.stripMinecraftNamespace(key), CompoundTag.class);
    }

    public static Tag handleDeep(Tag tag, TagUpdater consumer) {
        return TagUtil.handleDeep(null, tag, consumer);
    }

    static Tag handleDeep(@Nullable String key, Tag tag, TagUpdater consumer) {
        if (tag instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag)tag;
            for (Map.Entry<String, Tag> entry : compoundTag.entrySet()) {
                Tag updatedTag = TagUtil.handleDeep(entry.getKey(), entry.getValue(), consumer);
                entry.setValue(updatedTag);
            }
        } else if (tag instanceof ListTag) {
            ListTag listTag = (ListTag)tag;
            TagUtil.handleListTag(listTag, consumer);
        }
        return consumer.update(key, tag);
    }

    static <T extends Tag> void handleListTag(ListTag<T> listTag, TagUpdater consumer) {
        listTag.getValue().replaceAll(t -> TagUtil.handleDeep(null, t, consumer));
    }

    @FunctionalInterface
    public static interface TagUpdater {
        public Tag update(@Nullable String var1, Tag var2);
    }
}

