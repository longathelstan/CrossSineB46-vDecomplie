/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface MappingData {
    public void load();

    public int getNewBlockStateId(int var1);

    public int getNewBlockId(int var1);

    public int getOldBlockId(int var1);

    public int getNewItemId(int var1);

    public int getOldItemId(int var1);

    public int getNewParticleId(int var1);

    public int getNewAttributeId(int var1);

    public int getNewSoundId(int var1);

    public int getOldSoundId(int var1);

    public @Nullable List<TagData> getTags(RegistryType var1);

    public @Nullable BiMappings getItemMappings();

    public @Nullable FullMappings getFullItemMappings();

    public @Nullable ParticleMappings getParticleMappings();

    public @Nullable Mappings getBlockMappings();

    public @Nullable Mappings getBlockEntityMappings();

    public @Nullable Mappings getBlockStateMappings();

    public @Nullable Mappings getSoundMappings();

    public @Nullable Mappings getStatisticsMappings();

    public @Nullable Mappings getMenuMappings();

    public @Nullable Mappings getEnchantmentMappings();

    public @Nullable Mappings getAttributeMappings();

    public @Nullable Mappings getPaintingMappings();

    public @Nullable FullMappings getEntityMappings();

    public @Nullable FullMappings getArgumentTypeMappings();

    public @Nullable FullMappings getRecipeSerializerMappings();

    public @Nullable FullMappings getDataComponentSerializerMappings();
}

