/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.data.FullMappingsBase;
import com.viaversion.viaversion.api.data.IdentityMappings;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingDataBase
implements MappingData {
    protected final String unmappedVersion;
    protected final String mappedVersion;
    protected FullMappings argumentTypeMappings;
    protected FullMappings entityMappings;
    protected FullMappings recipeSerializerMappings;
    protected FullMappings itemDataSerializerMappings;
    protected ParticleMappings particleMappings;
    protected BiMappings itemMappings;
    protected BiMappings blockMappings;
    protected BiMappings attributeMappings;
    protected Mappings blockStateMappings;
    protected Mappings blockEntityMappings;
    protected Mappings soundMappings;
    protected Mappings statisticsMappings;
    protected Mappings enchantmentMappings;
    protected Mappings paintingMappings;
    protected Mappings menuMappings;
    protected Map<RegistryType, List<TagData>> tags;

    public MappingDataBase(String unmappedVersion, String mappedVersion) {
        this.unmappedVersion = unmappedVersion;
        this.mappedVersion = mappedVersion;
    }

    @Override
    public void load() {
        if (Via.getManager().isDebug()) {
            String string = this.mappedVersion;
            String string2 = this.unmappedVersion;
            this.getLogger().info("Loading " + string2 + " -> " + string + " mappings...");
        }
        String string = this.mappedVersion;
        String string3 = this.unmappedVersion;
        CompoundTag data = this.readMappingsFile("mappings-" + string3 + "to" + string + ".nbt");
        this.blockMappings = this.loadBiMappings(data, "blocks");
        this.blockStateMappings = this.loadMappings(data, "blockstates");
        this.blockEntityMappings = this.loadMappings(data, "blockentities");
        this.soundMappings = this.loadMappings(data, "sounds");
        this.statisticsMappings = this.loadMappings(data, "statistics");
        this.menuMappings = this.loadMappings(data, "menus");
        this.enchantmentMappings = this.loadMappings(data, "enchantments");
        this.paintingMappings = this.loadMappings(data, "paintings");
        this.attributeMappings = this.loadBiMappings(data, "attributes");
        String string4 = this.unmappedVersion;
        CompoundTag unmappedIdentifierData = this.readUnmappedIdentifiersFile("identifiers-" + string4 + ".nbt");
        String string5 = this.mappedVersion;
        CompoundTag mappedIdentifierData = this.readMappedIdentifiersFile("identifiers-" + string5 + ".nbt");
        if (unmappedIdentifierData != null && mappedIdentifierData != null) {
            this.itemMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "items");
            this.entityMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "entities");
            this.argumentTypeMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "argumenttypes");
            this.recipeSerializerMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "recipe_serializers");
            this.itemDataSerializerMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "data_component_type");
            List<String> unmappedParticles = this.identifiersFromGlobalIds(unmappedIdentifierData, "particles");
            List<String> mappedParticles = this.identifiersFromGlobalIds(mappedIdentifierData, "particles");
            if (unmappedParticles != null && mappedParticles != null) {
                Mappings particleMappings = this.loadMappings(data, "particles");
                if (particleMappings == null) {
                    particleMappings = new IdentityMappings(unmappedParticles.size(), mappedParticles.size());
                }
                this.particleMappings = new ParticleMappings(unmappedParticles, mappedParticles, particleMappings);
            }
        } else {
            this.itemMappings = this.loadBiMappings(data, "items");
        }
        CompoundTag tagsTag = data.getCompoundTag("tags");
        if (tagsTag != null) {
            this.tags = new EnumMap<RegistryType, List<TagData>>(RegistryType.class);
            this.loadTags(RegistryType.ITEM, tagsTag);
            this.loadTags(RegistryType.BLOCK, tagsTag);
            this.loadTags(RegistryType.ENTITY, tagsTag);
        }
        this.loadExtras(data);
    }

    protected @Nullable List<String> identifiersFromGlobalIds(CompoundTag mappingsTag, String key) {
        return MappingDataLoader.INSTANCE.identifiersFromGlobalIds(mappingsTag, key);
    }

    protected @Nullable CompoundTag readMappingsFile(String name) {
        return MappingDataLoader.INSTANCE.loadNBT(name);
    }

    protected @Nullable CompoundTag readUnmappedIdentifiersFile(String name) {
        return MappingDataLoader.INSTANCE.loadNBT(name, true);
    }

    protected @Nullable CompoundTag readMappedIdentifiersFile(String name) {
        return MappingDataLoader.INSTANCE.loadNBT(name, true);
    }

    protected @Nullable Mappings loadMappings(CompoundTag data, String key) {
        return MappingDataLoader.INSTANCE.loadMappings(data, key);
    }

    protected @Nullable FullMappings loadFullMappings(CompoundTag data, CompoundTag unmappedIdentifiersTag, CompoundTag mappedIdentifiersTag, String key) {
        if (!unmappedIdentifiersTag.contains(key) || !mappedIdentifiersTag.contains(key)) {
            return null;
        }
        List<String> unmappedIdentifiers = this.identifiersFromGlobalIds(unmappedIdentifiersTag, key);
        List<String> mappedIdentifiers = this.identifiersFromGlobalIds(mappedIdentifiersTag, key);
        Mappings mappings = this.loadBiMappings(data, key);
        if (mappings == null) {
            mappings = new IdentityMappings(unmappedIdentifiers.size(), mappedIdentifiers.size());
        }
        return new FullMappingsBase(unmappedIdentifiers, mappedIdentifiers, mappings);
    }

    protected @Nullable BiMappings loadBiMappings(CompoundTag data, String key) {
        Mappings mappings = this.loadMappings(data, key);
        return mappings != null ? BiMappings.of(mappings) : null;
    }

    private void loadTags(RegistryType type, CompoundTag data) {
        CompoundTag tag = data.getCompoundTag(type.resourceLocation());
        if (tag == null) {
            return;
        }
        ArrayList<TagData> tagsList = new ArrayList<TagData>(this.tags.size());
        for (Map.Entry<String, Tag> entry : tag.entrySet()) {
            IntArrayTag entries = (IntArrayTag)entry.getValue();
            tagsList.add(new TagData(entry.getKey(), entries.getValue()));
        }
        this.tags.put(type, tagsList);
    }

    @Override
    public int getNewBlockStateId(int id) {
        return this.checkValidity(id, this.blockStateMappings.getNewId(id), "blockstate");
    }

    @Override
    public int getNewBlockId(int id) {
        return this.checkValidity(id, this.blockMappings.getNewId(id), "block");
    }

    @Override
    public int getOldBlockId(int id) {
        return this.blockMappings.getNewIdOrDefault(id, 1);
    }

    @Override
    public int getNewItemId(int id) {
        return this.checkValidity(id, this.itemMappings.getNewId(id), "item");
    }

    @Override
    public int getOldItemId(int id) {
        return this.itemMappings.inverse().getNewIdOrDefault(id, 1);
    }

    @Override
    public int getNewParticleId(int id) {
        return this.checkValidity(id, this.particleMappings.getNewId(id), "particles");
    }

    @Override
    public int getNewAttributeId(int id) {
        return this.checkValidity(id, this.attributeMappings.getNewId(id), "attributes");
    }

    @Override
    public int getNewSoundId(int id) {
        return this.checkValidity(id, this.soundMappings.getNewId(id), "sound");
    }

    @Override
    public int getOldSoundId(int i) {
        return this.soundMappings.getNewIdOrDefault(i, 0);
    }

    @Override
    public @Nullable List<TagData> getTags(RegistryType type) {
        return this.tags != null ? this.tags.get((Object)type) : null;
    }

    @Override
    public @Nullable BiMappings getItemMappings() {
        return this.itemMappings;
    }

    @Override
    public @Nullable FullMappings getFullItemMappings() {
        if (this.itemMappings instanceof FullMappings) {
            return (FullMappings)this.itemMappings;
        }
        return null;
    }

    @Override
    public @Nullable ParticleMappings getParticleMappings() {
        return this.particleMappings;
    }

    @Override
    public @Nullable Mappings getBlockMappings() {
        return this.blockMappings;
    }

    @Override
    public @Nullable Mappings getBlockEntityMappings() {
        return this.blockEntityMappings;
    }

    @Override
    public @Nullable Mappings getBlockStateMappings() {
        return this.blockStateMappings;
    }

    @Override
    public @Nullable Mappings getSoundMappings() {
        return this.soundMappings;
    }

    @Override
    public @Nullable Mappings getStatisticsMappings() {
        return this.statisticsMappings;
    }

    @Override
    public @Nullable Mappings getMenuMappings() {
        return this.menuMappings;
    }

    @Override
    public @Nullable Mappings getEnchantmentMappings() {
        return this.enchantmentMappings;
    }

    @Override
    public @Nullable Mappings getAttributeMappings() {
        return this.attributeMappings;
    }

    @Override
    public @Nullable FullMappings getEntityMappings() {
        return this.entityMappings;
    }

    @Override
    public @Nullable FullMappings getArgumentTypeMappings() {
        return this.argumentTypeMappings;
    }

    @Override
    public @Nullable FullMappings getDataComponentSerializerMappings() {
        return this.itemDataSerializerMappings;
    }

    @Override
    public @Nullable Mappings getPaintingMappings() {
        return this.paintingMappings;
    }

    @Override
    public @Nullable FullMappings getRecipeSerializerMappings() {
        return this.recipeSerializerMappings;
    }

    protected Logger getLogger() {
        return Via.getPlatform().getLogger();
    }

    protected int checkValidity(int id, int mappedId, String type) {
        if (mappedId == -1) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                this.getLogger().warning(String.format("Missing %s %s for %s %s %d", this.mappedVersion, type, this.unmappedVersion, type, id));
            }
            return 0;
        }
        return mappedId;
    }

    protected void loadExtras(CompoundTag data) {
    }
}

