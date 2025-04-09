/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.data;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.util.ComponentUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappedItem {
    private final int id;
    private final String jsonName;
    private final Tag tagName;
    private final Integer customModelData;

    public MappedItem(int id, String name) {
        this(id, name, null);
    }

    public MappedItem(int id, String name, @Nullable Integer customModelData) {
        this.id = id;
        String string = name;
        this.jsonName = ComponentUtil.legacyToJsonString("\u00a7f" + string, true);
        this.tagName = ComponentUtil.jsonStringToTag(this.jsonName);
        this.customModelData = customModelData;
    }

    public int id() {
        return this.id;
    }

    public String jsonName() {
        return this.jsonName;
    }

    public Tag tagName() {
        return this.tagName;
    }

    public @Nullable Integer customModelData() {
        return this.customModelData;
    }
}

