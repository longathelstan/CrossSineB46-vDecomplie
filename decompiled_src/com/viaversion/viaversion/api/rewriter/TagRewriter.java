/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.rewriter.MappingDataListener;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface TagRewriter
extends MappingDataListener {
    public void removeTags(String var1);

    public void removeTag(RegistryType var1, String var2);

    public void renameTag(RegistryType var1, String var2, String var3);

    public void addEmptyTag(RegistryType var1, String var2);

    public void addEmptyTags(RegistryType var1, String ... var2);

    public void addEntityTag(String var1, EntityType ... var2);

    public void addTag(RegistryType var1, String var2, int ... var3);

    public void addTagRaw(RegistryType var1, String var2, int ... var3);

    public @Nullable List<TagData> getNewTags(RegistryType var1);

    public List<TagData> getOrComputeNewTags(RegistryType var1);
}

