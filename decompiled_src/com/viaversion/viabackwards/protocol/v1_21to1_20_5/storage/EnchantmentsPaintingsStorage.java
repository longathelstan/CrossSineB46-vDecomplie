/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.util.KeyMappings;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class EnchantmentsPaintingsStorage
implements StorableObject {
    private KeyMappings enchantments;
    private KeyMappings paintings;
    private int[] paintingMappings;
    private Tag[] enchantmentDescriptions;
    private int[] jubeboxSongsToItems;

    public KeyMappings enchantments() {
        return this.enchantments;
    }

    public void setEnchantments(KeyMappings enchantment, Tag[] enchantmentDescriptions) {
        this.enchantments = enchantment;
        this.enchantmentDescriptions = enchantmentDescriptions;
    }

    public KeyMappings paintings() {
        return this.paintings;
    }

    public void setPaintings(KeyMappings paintings, int[] paintingMappings) {
        this.paintings = paintings;
        this.paintingMappings = paintingMappings;
    }

    public void setJubeboxSongsToItems(int[] jubeboxSongsToItems) {
        this.jubeboxSongsToItems = jubeboxSongsToItems;
    }

    public int jubeboxSongToItem(int id) {
        return id >= 0 && id < this.jubeboxSongsToItems.length ? this.jubeboxSongsToItems[id] : -1;
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }

    public int mappedPainting(int id) {
        return id > 0 && id < this.paintingMappings.length ? this.paintingMappings[id] : 0;
    }

    public @Nullable Tag enchantmentDescription(int id) {
        return id > 0 && id < this.enchantmentDescriptions.length ? this.enchantmentDescriptions[id] : null;
    }
}

