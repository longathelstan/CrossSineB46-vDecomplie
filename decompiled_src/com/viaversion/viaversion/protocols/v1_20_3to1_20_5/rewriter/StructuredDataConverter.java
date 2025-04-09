/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.GameProfile;
import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.AdventureModePredicate;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrimMaterial;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrimPattern;
import com.viaversion.viaversion.api.minecraft.item.data.AttributeModifiers1_20_5;
import com.viaversion.viaversion.api.minecraft.item.data.BannerPattern;
import com.viaversion.viaversion.api.minecraft.item.data.BannerPatternLayer;
import com.viaversion.viaversion.api.minecraft.item.data.Bee;
import com.viaversion.viaversion.api.minecraft.item.data.BlockPredicate;
import com.viaversion.viaversion.api.minecraft.item.data.Enchantments;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableComponent;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableString;
import com.viaversion.viaversion.api.minecraft.item.data.FireworkExplosion;
import com.viaversion.viaversion.api.minecraft.item.data.FoodEffect;
import com.viaversion.viaversion.api.minecraft.item.data.Instrument;
import com.viaversion.viaversion.api.minecraft.item.data.PotionEffect;
import com.viaversion.viaversion.api.minecraft.item.data.PotionEffectData;
import com.viaversion.viaversion.api.minecraft.item.data.StatePropertyMatcher;
import com.viaversion.viaversion.api.minecraft.item.data.SuspiciousStewEffect;
import com.viaversion.viaversion.api.minecraft.item.data.ToolRule;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.Protocol1_20_3To1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Attributes1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.BannerPatterns1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Enchantments1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.EquipmentSlots1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Instruments1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.MapDecorations1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.PotionEffects1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Potions1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter.BlockItemPacketRewriter1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.ArmorTrimStorage;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.BannerPatternStorage;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class StructuredDataConverter {
    static final int HIDE_ENCHANTMENTS = 1;
    static final int HIDE_ATTRIBUTES = 2;
    static final int HIDE_UNBREAKABLE = 4;
    static final int HIDE_CAN_DESTROY = 8;
    static final int HIDE_CAN_PLACE_ON = 16;
    static final int HIDE_ADDITIONAL = 32;
    static final int HIDE_DYE_COLOR = 64;
    static final int HIDE_ARMOR_TRIM = 128;
    static final String BACKUP_TAG_KEY = "VV|DataComponents";
    static final String ITEM_BACKUP_TAG_KEY = "VV|Id";
    final Map<StructuredDataKey<?>, DataConverter<?>> rewriters = new Reference2ObjectOpenHashMap();
    final boolean backupInconvertibleData;

    public StructuredDataConverter(boolean backupInconvertibleData) {
        this.backupInconvertibleData = backupInconvertibleData;
        this.register(StructuredDataKey.CUSTOM_DATA, (T data, CompoundTag tag) -> {});
        this.register(StructuredDataKey.DAMAGE, (T data, CompoundTag tag) -> tag.putInt("Damage", (int)data));
        this.register(StructuredDataKey.UNBREAKABLE, (T data, CompoundTag tag) -> {
            tag.putBoolean("Unbreakable", true);
            if (!data.showInTooltip()) {
                this.putHideFlag(tag, 4);
            }
        });
        this.register(StructuredDataKey.CUSTOM_NAME, (T data, CompoundTag tag) -> StructuredDataConverter.getDisplayTag(tag).putString("Name", ComponentUtil.tagToJsonString(data)));
        this.register(StructuredDataKey.ITEM_NAME, (T data, CompoundTag tag) -> {
            CompoundTag displayTag = StructuredDataConverter.getDisplayTag(tag);
            if (!displayTag.contains("Name")) {
                CompoundTag name = new CompoundTag();
                name.putBoolean("italic", false);
                name.putString("text", "");
                name.put("extra", new ListTag<Tag>(Collections.singletonList(data)));
                displayTag.putString("Name", ComponentUtil.tagToJsonString(name));
            }
        });
        this.register(StructuredDataKey.LORE, (T data, CompoundTag tag) -> {
            ListTag<StringTag> lore = new ListTag<StringTag>(StringTag.class);
            for (Tag loreEntry : data) {
                lore.add(new StringTag(ComponentUtil.tagToJsonString(loreEntry)));
            }
            StructuredDataConverter.getDisplayTag(tag).put("Lore", lore);
        });
        this.register(StructuredDataKey.ENCHANTMENTS, (T data, CompoundTag tag) -> this.convertEnchantments((Enchantments)data, tag, false));
        this.register(StructuredDataKey.STORED_ENCHANTMENTS, (T data, CompoundTag tag) -> this.convertEnchantments((Enchantments)data, tag, true));
        this.register(StructuredDataKey.ATTRIBUTE_MODIFIERS1_20_5, (T data, CompoundTag tag) -> {
            ListTag<CompoundTag> modifiers = new ListTag<CompoundTag>(CompoundTag.class);
            for (int i = 0; i < data.modifiers().length; ++i) {
                AttributeModifiers1_20_5.AttributeModifier modifier = data.modifiers()[i];
                String identifier = Attributes1_20_5.idToKey(modifier.attribute());
                if (identifier == null) continue;
                CompoundTag modifierTag = new CompoundTag();
                modifierTag.putString("AttributeName", identifier.equals("generic.jump_strength") ? "horse.jump_strength" : identifier);
                modifierTag.putString("Name", modifier.modifier().name());
                modifierTag.putDouble("Amount", modifier.modifier().amount());
                if (modifier.slotType() != 0) {
                    modifierTag.putString("Slot", EquipmentSlots1_20_5.idToKey(modifier.slotType()));
                }
                modifierTag.putInt("Operation", modifier.modifier().operation());
                modifiers.add(modifierTag);
            }
            tag.put("AttributeModifiers", modifiers);
            if (!data.showInTooltip()) {
                this.putHideFlag(tag, 2);
            }
        });
        this.register(StructuredDataKey.CUSTOM_MODEL_DATA, (T data, CompoundTag tag) -> tag.putInt("CustomModelData", (int)data));
        this.register(StructuredDataKey.HIDE_ADDITIONAL_TOOLTIP, (T data, CompoundTag tag) -> this.putHideFlag(tag, 32));
        this.register(StructuredDataKey.REPAIR_COST, (T data, CompoundTag tag) -> tag.putInt("RepairCost", (int)data));
        this.register(StructuredDataKey.DYED_COLOR, (T data, CompoundTag tag) -> {
            StructuredDataConverter.getDisplayTag(tag).putInt("color", data.rgb());
            if (!data.showInTooltip()) {
                this.putHideFlag(tag, 64);
            }
        });
        this.register(StructuredDataKey.MAP_COLOR, (T data, CompoundTag tag) -> StructuredDataConverter.getDisplayTag(tag).putInt("MapColor", (int)data));
        this.register(StructuredDataKey.MAP_ID, (T data, CompoundTag tag) -> tag.putInt("map", (int)data));
        this.register(StructuredDataKey.MAP_DECORATIONS, (T data, CompoundTag tag) -> {
            ListTag<CompoundTag> decorations = new ListTag<CompoundTag>(CompoundTag.class);
            for (Map.Entry<String, Tag> entry : data.entrySet()) {
                CompoundTag decorationTag = (CompoundTag)entry.getValue();
                int id = MapDecorations1_20_5.keyToId(decorationTag.getString("type"));
                if (id == -1) continue;
                CompoundTag convertedDecoration = new CompoundTag();
                convertedDecoration.putString("id", entry.getKey());
                convertedDecoration.putInt("type", id);
                convertedDecoration.putDouble("x", decorationTag.getDouble("x"));
                convertedDecoration.putDouble("z", decorationTag.getDouble("z"));
                convertedDecoration.putFloat("rot", decorationTag.getFloat("rotation"));
                decorations.add(convertedDecoration);
            }
            tag.put("Decorations", decorations);
        });
        this.register(StructuredDataKey.WRITABLE_BOOK_CONTENT, (T data, CompoundTag tag) -> {
            ListTag<StringTag> pages = new ListTag<StringTag>(StringTag.class);
            CompoundTag filteredPages = new CompoundTag();
            for (int i = 0; i < ((FilterableString[])data).length; ++i) {
                FilterableString page = data[i];
                pages.add(new StringTag((String)page.raw()));
                if (page.filtered() == null) continue;
                filteredPages.putString(Integer.toString(i), (String)page.filtered());
            }
            tag.put("pages", pages);
            tag.put("filtered_pages", filteredPages);
        });
        this.register(StructuredDataKey.WRITTEN_BOOK_CONTENT, (T data, CompoundTag tag) -> {
            ListTag<StringTag> pages = new ListTag<StringTag>(StringTag.class);
            CompoundTag filteredPages = new CompoundTag();
            for (int i = 0; i < data.pages().length; ++i) {
                FilterableComponent page = data.pages()[i];
                pages.add(new StringTag(ComponentUtil.tagToJsonString((Tag)page.raw())));
                if (page.filtered() == null) continue;
                filteredPages.putString(Integer.toString(i), ComponentUtil.tagToJsonString((Tag)page.filtered()));
            }
            tag.put("pages", pages);
            tag.put("filtered_pages", filteredPages);
            tag.putString("author", data.author());
            tag.putInt("generation", data.generation());
            tag.putBoolean("resolved", data.resolved());
            tag.putString("title", (String)data.title().raw());
            if (data.title().filtered() != null) {
                tag.putString("filtered_title", (String)data.title().filtered());
            }
        });
        this.register(StructuredDataKey.BASE_COLOR, (T data, CompoundTag tag) -> StructuredDataConverter.getBlockEntityTag(tag).putInt("Base", (int)data));
        this.register(StructuredDataKey.CHARGED_PROJECTILES1_20_5, (UserConnection connection, T data, CompoundTag tag) -> {
            this.convertItemList(connection, (Item[])data, tag, "ChargedProjectiles");
            if (((Item[])data).length != 0) {
                tag.putBoolean("Charged", true);
            }
        });
        this.register(StructuredDataKey.BUNDLE_CONTENTS1_20_5, (UserConnection connection, T data, CompoundTag tag) -> this.convertItemList(connection, (Item[])data, tag, "Items"));
        this.register(StructuredDataKey.LODESTONE_TRACKER, (T data, CompoundTag tag) -> {
            tag.putBoolean("LodestoneTracked", data.tracked());
            if (data.position() != null) {
                CompoundTag positionTag = new CompoundTag();
                positionTag.putInt("X", data.position().x());
                positionTag.putInt("Y", data.position().y());
                positionTag.putInt("Z", data.position().z());
                tag.put("LodestonePos", positionTag);
                tag.putString("LodestoneDimension", data.position().dimension());
            } else {
                tag.putString("LodestoneDimension", "viaversion:viaversion");
            }
        });
        this.register(StructuredDataKey.FIREWORKS, (T data, CompoundTag tag) -> {
            CompoundTag fireworksTag = new CompoundTag();
            fireworksTag.putByte("Flight", (byte)data.flightDuration());
            tag.put("Fireworks", fireworksTag);
            if (data.explosions().length > 0) {
                ListTag<CompoundTag> explosionsTag = new ListTag<CompoundTag>(CompoundTag.class);
                for (FireworkExplosion explosion : data.explosions()) {
                    explosionsTag.add(this.convertExplosion(explosion));
                }
                fireworksTag.put("Explosions", explosionsTag);
            }
        });
        this.register(StructuredDataKey.FIREWORK_EXPLOSION, (T data, CompoundTag tag) -> tag.put("Explosion", this.convertExplosion((FireworkExplosion)data)));
        this.register(StructuredDataKey.PROFILE, (T data, CompoundTag tag) -> {
            if (data.name() != null && data.id() == null && data.properties().length == 0) {
                tag.putString("SkullOwner", data.name());
                return;
            }
            CompoundTag profileTag = new CompoundTag();
            tag.put("SkullOwner", profileTag);
            if (data.name() != null) {
                profileTag.putString("Name", data.name());
            }
            if (data.id() != null) {
                profileTag.put("Id", new IntArrayTag(UUIDUtil.toIntArray(data.id())));
            }
            CompoundTag propertiesTag = new CompoundTag();
            for (GameProfile.Property property : data.properties()) {
                ListTag<CompoundTag> values2 = new ListTag<CompoundTag>(CompoundTag.class);
                CompoundTag propertyTag = new CompoundTag();
                propertyTag.putString("Value", property.value());
                if (property.signature() != null) {
                    propertyTag.putString("Signature", property.signature());
                }
                values2.add(propertyTag);
                propertiesTag.put(property.name(), values2);
            }
            profileTag.put("Properties", propertiesTag);
        });
        this.register(StructuredDataKey.INSTRUMENT, (T data, CompoundTag tag) -> {
            if (!data.hasId()) {
                if (backupInconvertibleData) {
                    CompoundTag backupTag = new CompoundTag();
                    Instrument instrument = (Instrument)data.value();
                    if (instrument.soundEvent().hasId()) {
                        backupTag.putInt("sound_event", instrument.soundEvent().id());
                    } else {
                        CompoundTag soundEventTag = new CompoundTag();
                        SoundEvent soundEvent = instrument.soundEvent().value();
                        soundEventTag.putString("identifier", soundEvent.identifier());
                        if (soundEvent.fixedRange() != null) {
                            soundEventTag.putFloat("fixed_range", soundEvent.fixedRange().floatValue());
                        }
                        backupTag.put("sound_event", soundEventTag);
                    }
                    backupTag.putInt("use_duration", instrument.useDuration());
                    backupTag.putFloat("range", instrument.range());
                    StructuredDataConverter.getBackupTag(tag).put("instrument", backupTag);
                }
                return;
            }
            String identifier = Instruments1_20_3.idToKey(data.id());
            if (identifier != null) {
                tag.putString("instrument", identifier);
            }
        });
        this.register(StructuredDataKey.BEES, (T data, CompoundTag tag) -> {
            ListTag<CompoundTag> bees = new ListTag<CompoundTag>(CompoundTag.class);
            for (Bee bee : data) {
                CompoundTag beeTag = new CompoundTag();
                beeTag.put("EntityData", bee.entityData());
                beeTag.putInt("TicksInHive", bee.ticksInHive());
                beeTag.putInt("MinOccupationTicks", bee.minTicksInHive());
                bees.add(beeTag);
            }
            this.getBlockEntityTag(tag, "beehive").put("Bees", bees);
        });
        this.register(StructuredDataKey.LOCK, (T data, CompoundTag tag) -> StructuredDataConverter.getBlockEntityTag(tag).put("Lock", (Tag)data));
        this.register(StructuredDataKey.NOTE_BLOCK_SOUND, (T data, CompoundTag tag) -> this.getBlockEntityTag(tag, "player_head").putString("note_block_sound", (String)data));
        this.register(StructuredDataKey.POT_DECORATIONS, (T data, CompoundTag tag) -> {
            IntArrayTag originalSherds = null;
            ListTag<StringTag> sherds = new ListTag<StringTag>(StringTag.class);
            for (int id : data.itemIds()) {
                String name = this.toMappedItemName(id);
                if (name.isEmpty()) {
                    if (!backupInconvertibleData || originalSherds != null) continue;
                    originalSherds = new IntArrayTag(data.itemIds());
                    continue;
                }
                sherds.add(new StringTag(name));
            }
            if (originalSherds != null) {
                StructuredDataConverter.getBackupTag(tag).put("pot_decorations", originalSherds);
            }
            this.getBlockEntityTag(tag, "decorated_pot").put("sherds", sherds);
        });
        this.register(StructuredDataKey.CREATIVE_SLOT_LOCK, (T data, CompoundTag tag) -> tag.put("CustomCreativeLock", new CompoundTag()));
        this.register(StructuredDataKey.DEBUG_STICK_STATE, (T data, CompoundTag tag) -> tag.put("DebugProperty", (Tag)data));
        this.register(StructuredDataKey.RECIPES, (T data, CompoundTag tag) -> tag.put("Recipes", (Tag)data));
        this.register(StructuredDataKey.ENTITY_DATA, (T data, CompoundTag tag) -> tag.put("EntityTag", (Tag)data));
        this.register(StructuredDataKey.BUCKET_ENTITY_DATA, (T data, CompoundTag tag) -> {
            for (String mobTagName : BlockItemPacketRewriter1_20_5.MOB_TAGS) {
                if (!data.contains(mobTagName)) continue;
                tag.put(mobTagName, data.get(mobTagName));
            }
        });
        this.register(StructuredDataKey.BLOCK_ENTITY_DATA, (T data, CompoundTag tag) -> {
            CompoundTag blockEntityTag = tag.getCompoundTag("BlockEntityTag");
            if (blockEntityTag != null) {
                blockEntityTag.putAll((CompoundTag)data);
            } else {
                tag.put("BlockEntityTag", (Tag)data);
            }
        });
        this.register(StructuredDataKey.CONTAINER_LOOT, (T data, CompoundTag tag) -> {
            Tag lootTableSeed;
            Tag lootTable = data.get("loot_table");
            if (lootTable != null) {
                StructuredDataConverter.getBlockEntityTag(tag).put("LootTable", lootTable);
            }
            if ((lootTableSeed = data.get("loot_table_seed")) != null) {
                StructuredDataConverter.getBlockEntityTag(tag).put("LootTableSeed", lootTableSeed);
            }
        });
        this.register(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                StructuredDataConverter.getBackupTag(tag).putBoolean("enchantment_glint_override", (boolean)data);
            }
            if (!data.booleanValue()) {
                return;
            }
            ListTag<CompoundTag> enchantmentsTag = tag.getListTag("Enchantments", CompoundTag.class);
            if (enchantmentsTag == null) {
                enchantmentsTag = new ListTag<CompoundTag>(CompoundTag.class);
                tag.put("Enchantments", enchantmentsTag);
            } else if (!enchantmentsTag.isEmpty()) {
                return;
            }
            CompoundTag invalidEnchantment = new CompoundTag();
            invalidEnchantment.putString("id", "");
            enchantmentsTag.add(invalidEnchantment);
        });
        this.register(StructuredDataKey.POTION_CONTENTS, (T data, CompoundTag tag) -> {
            String potion;
            if (data.potion() != null && (potion = Potions1_20_5.idToKey(data.potion())) != null) {
                tag.putString("Potion", potion);
            }
            if (data.customColor() != null) {
                tag.putInt("CustomPotionColor", data.customColor());
            }
            ListTag<CompoundTag> customPotionEffectsTag = new ListTag<CompoundTag>(CompoundTag.class);
            for (PotionEffect effect : data.customEffects()) {
                CompoundTag effectTag = new CompoundTag();
                String id = PotionEffects1_20_5.idToKey(effect.effect());
                if (id != null) {
                    effectTag.putString("id", id);
                }
                PotionEffectData details = effect.effectData();
                effectTag.putByte("amplifier", (byte)details.amplifier());
                effectTag.putInt("duration", details.duration());
                effectTag.putBoolean("ambient", details.ambient());
                effectTag.putBoolean("show_particles", details.showParticles());
                effectTag.putBoolean("show_icon", details.showIcon());
                customPotionEffectsTag.add(effectTag);
            }
            tag.put("custom_potion_effects", customPotionEffectsTag);
        });
        this.register(StructuredDataKey.SUSPICIOUS_STEW_EFFECTS, (T data, CompoundTag tag) -> {
            ListTag<CompoundTag> effectsTag = new ListTag<CompoundTag>(CompoundTag.class);
            for (SuspiciousStewEffect effect : data) {
                CompoundTag effectTag = new CompoundTag();
                String id = PotionEffects1_20_5.idToKey(effect.mobEffect());
                if (id != null) {
                    effectTag.putString("id", id);
                }
                effectTag.putInt("duration", effect.duration());
                effectsTag.add(effectTag);
            }
            tag.put("effects", effectsTag);
        });
        this.register(StructuredDataKey.BANNER_PATTERNS, (UserConnection connection, T data, CompoundTag tag) -> {
            BannerPatternStorage patternStorage = connection.get(BannerPatternStorage.class);
            if (backupInconvertibleData && Arrays.stream(data).anyMatch(layer -> {
                if (layer.pattern().isDirect()) {
                    return true;
                }
                int id = layer.pattern().id();
                String identifier = patternStorage != null ? patternStorage.pattern(id) : BannerPatterns1_20_5.idToKey(id);
                return identifier == null || identifier.equals("flow") || identifier.equals("guster");
            })) {
                ListTag<CompoundTag> originalPatterns = new ListTag<CompoundTag>(CompoundTag.class);
                for (BannerPatternLayer layer2 : data) {
                    CompoundTag layerTag = new CompoundTag();
                    if (layer2.pattern().isDirect()) {
                        CompoundTag patternTag = new CompoundTag();
                        BannerPattern pattern = layer2.pattern().value();
                        patternTag.putString("asset_id", pattern.assetId());
                        patternTag.putString("translation_key", pattern.translationKey());
                        layerTag.put("pattern", patternTag);
                    } else {
                        layerTag.putInt("pattern", layer2.pattern().id());
                    }
                    layerTag.putInt("dye_color", layer2.dyeColor());
                    originalPatterns.add(layerTag);
                }
                StructuredDataConverter.getBackupTag(tag).put("banner_patterns", originalPatterns);
            }
            ListTag<CompoundTag> patternsTag = new ListTag<CompoundTag>(CompoundTag.class);
            for (BannerPatternLayer layer2 : data) {
                String compactKey;
                String key;
                if (layer2.pattern().isDirect()) continue;
                int id = layer2.pattern().id();
                String string = key = patternStorage != null ? patternStorage.pattern(id) : BannerPatterns1_20_5.idToKey(id);
                if (key == null || (compactKey = BannerPatterns1_20_5.fullIdToCompact(key)) == null) continue;
                CompoundTag patternTag = new CompoundTag();
                patternTag.putString("Pattern", compactKey);
                patternTag.putInt("Color", layer2.dyeColor());
                patternsTag.add(patternTag);
            }
            this.getBlockEntityTag(tag, "banner").put("Patterns", patternsTag);
        });
        this.register(StructuredDataKey.CONTAINER1_20_5, (UserConnection connection, T data, CompoundTag tag) -> this.convertItemList(connection, (Item[])data, StructuredDataConverter.getBlockEntityTag(tag), "Items"));
        this.register(StructuredDataKey.CAN_PLACE_ON, (T data, CompoundTag tag) -> this.convertBlockPredicates(tag, (AdventureModePredicate)data, "CanPlaceOn", 16));
        this.register(StructuredDataKey.CAN_BREAK, (T data, CompoundTag tag) -> this.convertBlockPredicates(tag, (AdventureModePredicate)data, "CanDestroy", 8));
        this.register(StructuredDataKey.MAP_POST_PROCESSING, (T data, CompoundTag tag) -> {
            if (data == null) {
                return;
            }
            if (data == 0) {
                tag.putBoolean("map_to_lock", true);
            } else if (data == 1) {
                tag.putInt("map_scale_direction", 1);
            }
        });
        this.register(StructuredDataKey.TRIM, (UserConnection connection, T data, CompoundTag tag) -> {
            String oldKey;
            CompoundTag trimTag = new CompoundTag();
            ArmorTrimStorage trimStorage = connection.get(ArmorTrimStorage.class);
            if (data.material().isDirect()) {
                CompoundTag materialTag = new CompoundTag();
                ArmorTrimMaterial material = data.material().value();
                materialTag.putString("asset_name", material.assetName());
                String ingredientName = this.toMappedItemName(material.itemId());
                if (backupInconvertibleData && ingredientName.isEmpty()) {
                    StructuredDataConverter.getBackupTag(materialTag).putInt(ITEM_BACKUP_TAG_KEY, material.itemId());
                }
                materialTag.putString("ingredient", ingredientName);
                materialTag.put("item_model_index", new FloatTag(material.itemModelIndex()));
                CompoundTag overrideArmorMaterials = new CompoundTag();
                if (!material.overrideArmorMaterials().isEmpty()) {
                    for (Int2ObjectMap.Entry entry : material.overrideArmorMaterials().int2ObjectEntrySet()) {
                        overrideArmorMaterials.put(Integer.toString(entry.getIntKey()), new StringTag((String)entry.getValue()));
                    }
                    materialTag.put("override_armor_materials", overrideArmorMaterials);
                }
                materialTag.put("description", material.description());
                trimTag.put("material", materialTag);
            } else {
                oldKey = trimStorage.trimMaterials().idToKey(data.material().id());
                if (oldKey != null) {
                    trimTag.putString("material", oldKey);
                }
            }
            if (data.pattern().isDirect()) {
                CompoundTag patternTag = new CompoundTag();
                ArmorTrimPattern pattern = data.pattern().value();
                patternTag.putString("assetId", pattern.assetName());
                String itemName = this.toMappedItemName(pattern.itemId());
                if (backupInconvertibleData && itemName.isEmpty()) {
                    StructuredDataConverter.getBackupTag(patternTag).putInt(ITEM_BACKUP_TAG_KEY, pattern.itemId());
                }
                patternTag.putString("templateItem", itemName);
                patternTag.put("description", pattern.description());
                patternTag.putBoolean("decal", pattern.decal());
                trimTag.put("pattern", patternTag);
            } else {
                oldKey = trimStorage.trimPatterns().idToKey(data.pattern().id());
                if (oldKey != null) {
                    trimTag.putString("pattern", oldKey);
                }
            }
            tag.put("Trim", trimTag);
            if (!data.showInTooltip()) {
                this.putHideFlag(tag, 128);
            }
        });
        this.register(StructuredDataKey.BLOCK_STATE, (T data, CompoundTag tag) -> {
            CompoundTag blockStateTag = new CompoundTag();
            tag.put("BlockStateTag", blockStateTag);
            for (Map.Entry<String, String> entry : data.properties().entrySet()) {
                blockStateTag.putString(entry.getKey(), entry.getValue());
            }
        });
        this.register(StructuredDataKey.HIDE_TOOLTIP, (T data, CompoundTag tag) -> {
            this.putHideFlag(tag, 255);
            if (backupInconvertibleData) {
                StructuredDataConverter.getBackupTag(tag).putBoolean("hide_tooltip", true);
            }
        });
        this.register(StructuredDataKey.INTANGIBLE_PROJECTILE, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                StructuredDataConverter.getBackupTag(tag).put("intangible_projectile", (Tag)data);
            }
        });
        this.register(StructuredDataKey.MAX_STACK_SIZE, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                StructuredDataConverter.getBackupTag(tag).putInt("max_stack_size", (int)data);
            }
        });
        this.register(StructuredDataKey.MAX_DAMAGE, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                StructuredDataConverter.getBackupTag(tag).putInt("max_damage", (int)data);
            }
        });
        this.register(StructuredDataKey.RARITY, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                StructuredDataConverter.getBackupTag(tag).putInt("rarity", (int)data);
            }
        });
        this.register(StructuredDataKey.FOOD1_20_5, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                CompoundTag backupTag = new CompoundTag();
                backupTag.putInt("nutrition", data.nutrition());
                backupTag.putFloat("saturation_modifier", data.saturationModifier());
                backupTag.putBoolean("can_always_eat", data.canAlwaysEat());
                backupTag.putFloat("eat_seconds", data.eatSeconds());
                ListTag<CompoundTag> possibleEffectsTag = new ListTag<CompoundTag>(CompoundTag.class);
                for (FoodEffect effect : data.possibleEffects()) {
                    CompoundTag effectTag = new CompoundTag();
                    PotionEffect potionEffect = effect.effect();
                    CompoundTag potionEffectTag = new CompoundTag();
                    potionEffectTag.putInt("effect", potionEffect.effect());
                    potionEffectTag.put("effect_data", this.convertPotionEffectData(potionEffect.effectData()));
                    effectTag.putFloat("probability", effect.probability());
                    effectTag.put("effect", potionEffectTag);
                    possibleEffectsTag.add(effectTag);
                }
                backupTag.put("possible_effects", possibleEffectsTag);
                StructuredDataConverter.getBackupTag(tag).put("food", backupTag);
            }
        });
        this.register(StructuredDataKey.FIRE_RESISTANT, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                StructuredDataConverter.getBackupTag(tag).putBoolean("fire_resistant", true);
            }
        });
        this.register(StructuredDataKey.TOOL, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                CompoundTag backupTag = new CompoundTag();
                ListTag<CompoundTag> rulesTag = new ListTag<CompoundTag>(CompoundTag.class);
                for (ToolRule rule : data.rules()) {
                    CompoundTag ruleTag = new CompoundTag();
                    HolderSet set = rule.blocks();
                    if (set.hasTagKey()) {
                        ruleTag.putString("blocks", set.tagKey());
                    } else {
                        ruleTag.put("blocks", new IntArrayTag(set.ids()));
                    }
                    if (rule.speed() != null) {
                        ruleTag.putFloat("speed", rule.speed().floatValue());
                    }
                    if (rule.correctForDrops() != null) {
                        ruleTag.putBoolean("correct_for_drops", rule.correctForDrops());
                    }
                    rulesTag.add(ruleTag);
                }
                backupTag.put("rules", rulesTag);
                backupTag.putFloat("default_mining_speed", data.defaultMiningSpeed());
                backupTag.putInt("damage_per_block", data.damagePerBlock());
                StructuredDataConverter.getBackupTag(tag).put("tool", backupTag);
            }
        });
        this.register(StructuredDataKey.OMINOUS_BOTTLE_AMPLIFIER, (T data, CompoundTag tag) -> {
            if (backupInconvertibleData) {
                StructuredDataConverter.getBackupTag(tag).putInt("ominous_bottle_amplifier", (int)data);
            }
        });
    }

    int unmappedItemId(int id) {
        return Protocol1_20_3To1_20_5.MAPPINGS.getOldItemId(id);
    }

    String toMappedItemName(int id) {
        int mappedId = this.unmappedItemId(id);
        return mappedId != -1 ? Protocol1_20_3To1_20_5.MAPPINGS.getFullItemMappings().identifier(mappedId) : "";
    }

    static CompoundTag getBlockEntityTag(CompoundTag tag) {
        return StructuredDataConverter.getOrCreate(tag, "BlockEntityTag");
    }

    CompoundTag getBlockEntityTag(CompoundTag tag, String blockEntity) {
        CompoundTag blockEntityTag = StructuredDataConverter.getOrCreate(tag, "BlockEntityTag");
        if (!blockEntityTag.contains("id")) {
            blockEntityTag.putString("id", blockEntity);
        }
        return blockEntityTag;
    }

    static CompoundTag getDisplayTag(CompoundTag tag) {
        return StructuredDataConverter.getOrCreate(tag, "display");
    }

    static CompoundTag getBackupTag(CompoundTag tag) {
        return StructuredDataConverter.getOrCreate(tag, BACKUP_TAG_KEY);
    }

    static CompoundTag getOrCreate(CompoundTag tag, String key) {
        CompoundTag subTag = tag.getCompoundTag(key);
        if (subTag == null) {
            subTag = new CompoundTag();
            tag.put(key, subTag);
        }
        return subTag;
    }

    public static @Nullable CompoundTag removeBackupTag(CompoundTag tag) {
        CompoundTag backupTag = tag.getCompoundTag(BACKUP_TAG_KEY);
        if (backupTag != null) {
            tag.remove(BACKUP_TAG_KEY);
        }
        return backupTag;
    }

    static int removeItemBackupTag(CompoundTag tag, int unmappedId) {
        IntTag itemBackupTag = tag.getIntTag(ITEM_BACKUP_TAG_KEY);
        if (itemBackupTag != null) {
            tag.remove(ITEM_BACKUP_TAG_KEY);
            return itemBackupTag.asInt();
        }
        return unmappedId;
    }

    void convertBlockPredicates(CompoundTag tag, AdventureModePredicate data, String key, int hideFlag) {
        ListTag<StringTag> predicatedListTag = new ListTag<StringTag>(StringTag.class);
        for (BlockPredicate predicate : data.predicates()) {
            HolderSet holders = predicate.holderSet();
            if (holders == null) {
                if (!this.backupInconvertibleData) continue;
                continue;
            }
            if (holders.hasTagKey()) {
                String string = holders.tagKey();
                String tagKey = "#" + string;
                predicatedListTag.add(this.serializeBlockPredicate(predicate, tagKey));
                continue;
            }
            for (int id : holders.ids()) {
                String name = Protocol1_20_3To1_20_5.MAPPINGS.blockName(id);
                if (name == null) {
                    if (!this.backupInconvertibleData) continue;
                    continue;
                }
                predicatedListTag.add(this.serializeBlockPredicate(predicate, name));
            }
        }
        tag.put(key, predicatedListTag);
        if (!data.showInTooltip()) {
            this.putHideFlag(tag, hideFlag);
        }
    }

    StringTag serializeBlockPredicate(BlockPredicate predicate, String identifier) {
        StringBuilder builder = new StringBuilder(identifier);
        if (predicate.propertyMatchers() != null) {
            for (StatePropertyMatcher matcher : predicate.propertyMatchers()) {
                if (!matcher.matcher().isLeft()) continue;
                builder.append(matcher.name()).append('=');
                builder.append(matcher.matcher().left());
            }
        }
        if (predicate.tag() != null) {
            builder.append(predicate.tag());
        }
        return new StringTag(builder.toString());
    }

    CompoundTag convertExplosion(FireworkExplosion explosion) {
        CompoundTag explosionTag = new CompoundTag();
        explosionTag.putInt("Type", explosion.shape());
        explosionTag.put("Colors", new IntArrayTag((int[])explosion.colors().clone()));
        explosionTag.put("FadeColors", new IntArrayTag((int[])explosion.fadeColors().clone()));
        explosionTag.putBoolean("Trail", explosion.hasTrail());
        explosionTag.putBoolean("Flicker", explosion.hasTwinkle());
        return explosionTag;
    }

    CompoundTag convertPotionEffectData(PotionEffectData data) {
        CompoundTag effectDataTag = new CompoundTag();
        effectDataTag.putInt("amplifier", data.amplifier());
        effectDataTag.putInt("duration", data.duration());
        effectDataTag.putBoolean("ambient", data.ambient());
        effectDataTag.putBoolean("show_particles", data.showParticles());
        effectDataTag.putBoolean("show_icon", data.showIcon());
        if (data.hiddenEffect() != null) {
            effectDataTag.put("hidden_effect", this.convertPotionEffectData(data.hiddenEffect()));
        }
        return effectDataTag;
    }

    void convertItemList(UserConnection connection, Item[] items, CompoundTag tag, String key) {
        ListTag<CompoundTag> itemsTag = new ListTag<CompoundTag>(CompoundTag.class);
        for (int i = 0; i < items.length; ++i) {
            Item item = items[i];
            CompoundTag savedItem = this.itemToTag(connection, item);
            if (this.backupInconvertibleData) {
                savedItem.putByte("Slot", (byte)i);
            }
            itemsTag.add(savedItem);
        }
        tag.put(key, itemsTag);
    }

    CompoundTag itemToTag(UserConnection connection, Item item) {
        CompoundTag savedItem = new CompoundTag();
        if (item != null) {
            String name = this.toMappedItemName(item.identifier());
            savedItem.putString("id", name);
            if (this.backupInconvertibleData && name.isEmpty()) {
                savedItem.putInt(ITEM_BACKUP_TAG_KEY, item.identifier());
            }
            savedItem.putByte("Count", (byte)item.amount());
            CompoundTag itemTag = new CompoundTag();
            for (StructuredData<?> data : item.dataContainer().data().values()) {
                this.writeToTag(connection, data, itemTag);
            }
            savedItem.put("tag", itemTag);
        } else {
            savedItem.putString("id", "air");
        }
        return savedItem;
    }

    void convertEnchantments(Enchantments data, CompoundTag tag, boolean storedEnchantments) {
        ListTag<CompoundTag> enchantments = new ListTag<CompoundTag>(CompoundTag.class);
        for (Int2IntMap.Entry entry : data.enchantments().int2IntEntrySet()) {
            int enchantmentId = entry.getIntKey();
            String identifier = Enchantments1_20_5.idToKey(enchantmentId);
            if (identifier == null || identifier.equals("density") || identifier.equals("breach") || identifier.equals("wind_burst")) continue;
            if (identifier.equals("sweeping_edge")) {
                identifier = "sweeping";
            }
            CompoundTag enchantment = new CompoundTag();
            enchantment.putString("id", identifier);
            enchantment.putShort("lvl", (short)entry.getIntValue());
            enchantments.add(enchantment);
        }
        tag.put(storedEnchantments ? "StoredEnchantments" : "Enchantments", enchantments);
        if (!data.showInTooltip()) {
            this.putHideFlag(tag, storedEnchantments ? 32 : 1);
        }
    }

    void putHideFlag(CompoundTag tag, int value) {
        tag.putInt("HideFlags", tag.getInt("HideFlags") | value);
    }

    public <T> void writeToTag(UserConnection connection, StructuredData<T> data, CompoundTag tag) {
        if (data.isEmpty()) {
            return;
        }
        DataConverter<?> converter = this.rewriters.get(data.key());
        Preconditions.checkNotNull(converter, (String)"No converter for %s found", (Object[])new Object[]{data.key()});
        converter.convert(connection, data.value(), tag);
    }

    <T> void register(StructuredDataKey<T> key, DataConverter<T> converter) {
        this.rewriters.put(key, converter);
    }

    <T> void register(StructuredDataKey<T> key, SimpleDataConverter<T> converter) {
        DataConverter<Object> c = (connection, data, tag) -> converter.convert(data, tag);
        this.rewriters.put(key, c);
    }

    public boolean backupInconvertibleData() {
        return this.backupInconvertibleData;
    }

    @FunctionalInterface
    static interface SimpleDataConverter<T> {
        public void convert(T var1, CompoundTag var2);
    }

    @FunctionalInterface
    static interface DataConverter<T> {
        public void convert(UserConnection var1, T var2, CompoundTag var3);
    }
}

