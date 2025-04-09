/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.GameProfile;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.AdventureModePredicate;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrim;
import com.viaversion.viaversion.api.minecraft.item.data.AttributeModifiers1_20_5;
import com.viaversion.viaversion.api.minecraft.item.data.AttributeModifiers1_21;
import com.viaversion.viaversion.api.minecraft.item.data.BannerPatternLayer;
import com.viaversion.viaversion.api.minecraft.item.data.Bee;
import com.viaversion.viaversion.api.minecraft.item.data.BlockStateProperties;
import com.viaversion.viaversion.api.minecraft.item.data.DyedColor;
import com.viaversion.viaversion.api.minecraft.item.data.Enchantments;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableString;
import com.viaversion.viaversion.api.minecraft.item.data.FireworkExplosion;
import com.viaversion.viaversion.api.minecraft.item.data.Fireworks;
import com.viaversion.viaversion.api.minecraft.item.data.FoodProperties;
import com.viaversion.viaversion.api.minecraft.item.data.Instrument;
import com.viaversion.viaversion.api.minecraft.item.data.JukeboxPlayable;
import com.viaversion.viaversion.api.minecraft.item.data.LodestoneTracker;
import com.viaversion.viaversion.api.minecraft.item.data.PotDecorations;
import com.viaversion.viaversion.api.minecraft.item.data.PotionContents;
import com.viaversion.viaversion.api.minecraft.item.data.SuspiciousStewEffect;
import com.viaversion.viaversion.api.minecraft.item.data.ToolProperties;
import com.viaversion.viaversion.api.minecraft.item.data.Unbreakable;
import com.viaversion.viaversion.api.minecraft.item.data.WrittenBook;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import com.viaversion.viaversion.util.Unit;
import java.util.Objects;

public final class StructuredDataKey<T> {
    private final String identifier;
    private final Type<T> type;
    public static final StructuredDataKey<CompoundTag> CUSTOM_DATA = new StructuredDataKey<CompoundTag>("custom_data", Types.COMPOUND_TAG);
    public static final StructuredDataKey<Integer> MAX_STACK_SIZE = new StructuredDataKey<Integer>("max_stack_size", Types.VAR_INT);
    public static final StructuredDataKey<Integer> MAX_DAMAGE = new StructuredDataKey<Integer>("max_damage", Types.VAR_INT);
    public static final StructuredDataKey<Integer> DAMAGE = new StructuredDataKey<Integer>("damage", Types.VAR_INT);
    public static final StructuredDataKey<Unbreakable> UNBREAKABLE = new StructuredDataKey<Unbreakable>("unbreakable", Unbreakable.TYPE);
    public static final StructuredDataKey<Tag> CUSTOM_NAME = new StructuredDataKey<Tag>("custom_name", Types.TAG);
    public static final StructuredDataKey<Tag> ITEM_NAME = new StructuredDataKey<Tag>("item_name", Types.TAG);
    public static final StructuredDataKey<Tag[]> LORE = new StructuredDataKey<Tag[]>("lore", Types.TAG_ARRAY);
    public static final StructuredDataKey<Integer> RARITY = new StructuredDataKey<Integer>("rarity", Types.VAR_INT);
    public static final StructuredDataKey<Enchantments> ENCHANTMENTS = new StructuredDataKey<Enchantments>("enchantments", Enchantments.TYPE);
    public static final StructuredDataKey<AdventureModePredicate> CAN_PLACE_ON = new StructuredDataKey<AdventureModePredicate>("can_place_on", AdventureModePredicate.TYPE);
    public static final StructuredDataKey<AdventureModePredicate> CAN_BREAK = new StructuredDataKey<AdventureModePredicate>("can_break", AdventureModePredicate.TYPE);
    public static final StructuredDataKey<AttributeModifiers1_20_5> ATTRIBUTE_MODIFIERS1_20_5 = new StructuredDataKey<AttributeModifiers1_20_5>("attribute_modifiers", AttributeModifiers1_20_5.TYPE);
    public static final StructuredDataKey<AttributeModifiers1_21> ATTRIBUTE_MODIFIERS1_21 = new StructuredDataKey<AttributeModifiers1_21>("attribute_modifiers", AttributeModifiers1_21.TYPE);
    public static final StructuredDataKey<Integer> CUSTOM_MODEL_DATA = new StructuredDataKey<Integer>("custom_model_data", Types.VAR_INT);
    public static final StructuredDataKey<Unit> HIDE_ADDITIONAL_TOOLTIP = new StructuredDataKey<Unit>("hide_additional_tooltip", Types.EMPTY);
    public static final StructuredDataKey<Unit> HIDE_TOOLTIP = new StructuredDataKey<Unit>("hide_tooltip", Types.EMPTY);
    public static final StructuredDataKey<Integer> REPAIR_COST = new StructuredDataKey<Integer>("repair_cost", Types.VAR_INT);
    public static final StructuredDataKey<Unit> CREATIVE_SLOT_LOCK = new StructuredDataKey<Unit>("creative_slot_lock", Types.EMPTY);
    public static final StructuredDataKey<Boolean> ENCHANTMENT_GLINT_OVERRIDE = new StructuredDataKey<Boolean>("enchantment_glint_override", Types.BOOLEAN);
    public static final StructuredDataKey<Tag> INTANGIBLE_PROJECTILE = new StructuredDataKey<Tag>("intangible_projectile", Types.TAG);
    public static final StructuredDataKey<FoodProperties> FOOD1_20_5 = new StructuredDataKey<FoodProperties>("food", FoodProperties.TYPE1_20_5);
    public static final StructuredDataKey<FoodProperties> FOOD1_21 = new StructuredDataKey<FoodProperties>("food", FoodProperties.TYPE1_21);
    public static final StructuredDataKey<Unit> FIRE_RESISTANT = new StructuredDataKey<Unit>("fire_resistant", Types.EMPTY);
    public static final StructuredDataKey<ToolProperties> TOOL = new StructuredDataKey<ToolProperties>("tool", ToolProperties.TYPE);
    public static final StructuredDataKey<Enchantments> STORED_ENCHANTMENTS = new StructuredDataKey<Enchantments>("stored_enchantments", Enchantments.TYPE);
    public static final StructuredDataKey<DyedColor> DYED_COLOR = new StructuredDataKey<DyedColor>("dyed_color", DyedColor.TYPE);
    public static final StructuredDataKey<Integer> MAP_COLOR = new StructuredDataKey<Integer>("map_color", Types.INT);
    public static final StructuredDataKey<Integer> MAP_ID = new StructuredDataKey<Integer>("map_id", Types.VAR_INT);
    public static final StructuredDataKey<CompoundTag> MAP_DECORATIONS = new StructuredDataKey<CompoundTag>("map_decorations", Types.COMPOUND_TAG);
    public static final StructuredDataKey<Integer> MAP_POST_PROCESSING = new StructuredDataKey<Integer>("map_post_processing", Types.VAR_INT);
    public static final StructuredDataKey<Item[]> CHARGED_PROJECTILES1_20_5 = new StructuredDataKey<Item[]>("charged_projectiles", Types1_20_5.ITEM_ARRAY);
    public static final StructuredDataKey<Item[]> CHARGED_PROJECTILES1_21 = new StructuredDataKey<Item[]>("charged_projectiles", Types1_21.ITEM_ARRAY);
    public static final StructuredDataKey<Item[]> BUNDLE_CONTENTS1_20_5 = new StructuredDataKey<Item[]>("bundle_contents", Types1_20_5.ITEM_ARRAY);
    public static final StructuredDataKey<Item[]> BUNDLE_CONTENTS1_21 = new StructuredDataKey<Item[]>("bundle_contents", Types1_21.ITEM_ARRAY);
    public static final StructuredDataKey<PotionContents> POTION_CONTENTS = new StructuredDataKey<PotionContents>("potion_contents", PotionContents.TYPE);
    public static final StructuredDataKey<SuspiciousStewEffect[]> SUSPICIOUS_STEW_EFFECTS = new StructuredDataKey<SuspiciousStewEffect[]>("suspicious_stew_effects", SuspiciousStewEffect.ARRAY_TYPE);
    public static final StructuredDataKey<FilterableString[]> WRITABLE_BOOK_CONTENT = new StructuredDataKey<FilterableString[]>("writable_book_content", FilterableString.ARRAY_TYPE);
    public static final StructuredDataKey<WrittenBook> WRITTEN_BOOK_CONTENT = new StructuredDataKey<WrittenBook>("written_book_content", WrittenBook.TYPE);
    public static final StructuredDataKey<ArmorTrim> TRIM = new StructuredDataKey<ArmorTrim>("trim", ArmorTrim.TYPE);
    public static final StructuredDataKey<CompoundTag> DEBUG_STICK_STATE = new StructuredDataKey<CompoundTag>("debug_stick_state", Types.COMPOUND_TAG);
    public static final StructuredDataKey<CompoundTag> ENTITY_DATA = new StructuredDataKey<CompoundTag>("entity_data", Types.COMPOUND_TAG);
    public static final StructuredDataKey<CompoundTag> BUCKET_ENTITY_DATA = new StructuredDataKey<CompoundTag>("bucket_entity_data", Types.COMPOUND_TAG);
    public static final StructuredDataKey<CompoundTag> BLOCK_ENTITY_DATA = new StructuredDataKey<CompoundTag>("block_entity_data", Types.COMPOUND_TAG);
    public static final StructuredDataKey<Holder<Instrument>> INSTRUMENT = new StructuredDataKey<Instrument>("instrument", Instrument.TYPE);
    public static final StructuredDataKey<Integer> OMINOUS_BOTTLE_AMPLIFIER = new StructuredDataKey<Integer>("ominous_bottle_amplifier", Types.VAR_INT);
    public static final StructuredDataKey<JukeboxPlayable> JUKEBOX_PLAYABLE = new StructuredDataKey<JukeboxPlayable>("jukebox_playable", JukeboxPlayable.TYPE);
    public static final StructuredDataKey<Tag> RECIPES = new StructuredDataKey<Tag>("recipes", Types.TAG);
    public static final StructuredDataKey<LodestoneTracker> LODESTONE_TRACKER = new StructuredDataKey<LodestoneTracker>("lodestone_tracker", LodestoneTracker.TYPE);
    public static final StructuredDataKey<FireworkExplosion> FIREWORK_EXPLOSION = new StructuredDataKey<FireworkExplosion>("firework_explosion", FireworkExplosion.TYPE);
    public static final StructuredDataKey<Fireworks> FIREWORKS = new StructuredDataKey<Fireworks>("fireworks", Fireworks.TYPE);
    public static final StructuredDataKey<GameProfile> PROFILE = new StructuredDataKey<GameProfile>("profile", Types.GAME_PROFILE);
    public static final StructuredDataKey<String> NOTE_BLOCK_SOUND = new StructuredDataKey<String>("note_block_sound", Types.STRING);
    public static final StructuredDataKey<BannerPatternLayer[]> BANNER_PATTERNS = new StructuredDataKey<BannerPatternLayer[]>("banner_patterns", BannerPatternLayer.ARRAY_TYPE);
    public static final StructuredDataKey<Integer> BASE_COLOR = new StructuredDataKey<Integer>("base_color", Types.VAR_INT);
    public static final StructuredDataKey<PotDecorations> POT_DECORATIONS = new StructuredDataKey<PotDecorations>("pot_decorations", PotDecorations.TYPE);
    public static final StructuredDataKey<Item[]> CONTAINER1_20_5 = new StructuredDataKey<Item[]>("container", Types1_20_5.ITEM_ARRAY);
    public static final StructuredDataKey<Item[]> CONTAINER1_21 = new StructuredDataKey<Item[]>("container", Types1_21.ITEM_ARRAY);
    public static final StructuredDataKey<BlockStateProperties> BLOCK_STATE = new StructuredDataKey<BlockStateProperties>("block_state", BlockStateProperties.TYPE);
    public static final StructuredDataKey<Bee[]> BEES = new StructuredDataKey<Bee[]>("bees", Bee.ARRAY_TYPE);
    public static final StructuredDataKey<Tag> LOCK = new StructuredDataKey<Tag>("lock", Types.TAG);
    public static final StructuredDataKey<CompoundTag> CONTAINER_LOOT = new StructuredDataKey<CompoundTag>("container_loot", Types.COMPOUND_TAG);

    public StructuredDataKey(String identifier, Type<T> type) {
        this.identifier = identifier;
        this.type = type;
    }

    public String toString() {
        Type<T> type = this.type;
        String string = this.identifier;
        return "StructuredDataKey{identifier='" + string + "', type=" + type + "}";
    }

    public String identifier() {
        return this.identifier;
    }

    public Type<T> type() {
        return this.type;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StructuredDataKey)) {
            return false;
        }
        StructuredDataKey structuredDataKey = (StructuredDataKey)object;
        return Objects.equals(this.identifier, structuredDataKey.identifier) && Objects.equals(this.type, structuredDataKey.type);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.identifier)) * 31 + Objects.hashCode(this.type);
    }
}

