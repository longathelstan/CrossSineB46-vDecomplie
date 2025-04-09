/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.FloatTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.GameProfile;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.AdventureModePredicate;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrim;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrimMaterial;
import com.viaversion.viaversion.api.minecraft.item.data.ArmorTrimPattern;
import com.viaversion.viaversion.api.minecraft.item.data.AttributeModifiers1_20_5;
import com.viaversion.viaversion.api.minecraft.item.data.BannerPattern;
import com.viaversion.viaversion.api.minecraft.item.data.BannerPatternLayer;
import com.viaversion.viaversion.api.minecraft.item.data.Bee;
import com.viaversion.viaversion.api.minecraft.item.data.BlockPredicate;
import com.viaversion.viaversion.api.minecraft.item.data.BlockStateProperties;
import com.viaversion.viaversion.api.minecraft.item.data.DyedColor;
import com.viaversion.viaversion.api.minecraft.item.data.Enchantments;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableComponent;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableString;
import com.viaversion.viaversion.api.minecraft.item.data.FireworkExplosion;
import com.viaversion.viaversion.api.minecraft.item.data.Fireworks;
import com.viaversion.viaversion.api.minecraft.item.data.FoodEffect;
import com.viaversion.viaversion.api.minecraft.item.data.FoodProperties;
import com.viaversion.viaversion.api.minecraft.item.data.Instrument;
import com.viaversion.viaversion.api.minecraft.item.data.LodestoneTracker;
import com.viaversion.viaversion.api.minecraft.item.data.PotDecorations;
import com.viaversion.viaversion.api.minecraft.item.data.PotionContents;
import com.viaversion.viaversion.api.minecraft.item.data.PotionEffect;
import com.viaversion.viaversion.api.minecraft.item.data.PotionEffectData;
import com.viaversion.viaversion.api.minecraft.item.data.StatePropertyMatcher;
import com.viaversion.viaversion.api.minecraft.item.data.SuspiciousStewEffect;
import com.viaversion.viaversion.api.minecraft.item.data.ToolProperties;
import com.viaversion.viaversion.api.minecraft.item.data.ToolRule;
import com.viaversion.viaversion.api.minecraft.item.data.Unbreakable;
import com.viaversion.viaversion.api.minecraft.item.data.WrittenBook;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.type.types.item.StructuredDataType;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.Protocol1_20_3To1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.ArmorMaterials1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Attributes1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.BannerPatterns1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.DyeColors;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Enchantments1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.EquipmentSlots1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Instruments1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.PotionEffects1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Potions1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter.BlockItemPacketRewriter1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.ArmorTrimStorage;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Either;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.SerializerVersion;
import com.viaversion.viaversion.util.UUIDUtil;
import com.viaversion.viaversion.util.Unit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ComponentRewriter1_20_5<C extends ClientboundPacketType>
extends ComponentRewriter<C> {
    final Map<StructuredDataKey<?>, ConverterPair<?>> converters = new Reference2ObjectOpenHashMap();
    final StructuredDataType structuredDataType;

    public ComponentRewriter1_20_5(Protocol<C, ?, ?, ?> protocol, StructuredDataType structuredDataType) {
        super(protocol, ComponentRewriter.ReadType.NBT);
        this.structuredDataType = structuredDataType;
        this.register(StructuredDataKey.CUSTOM_DATA, this::convertCustomData);
        this.register(StructuredDataKey.MAX_STACK_SIZE, this::convertMaxStackSize);
        this.register(StructuredDataKey.MAX_DAMAGE, this::convertMaxDamage);
        this.register(StructuredDataKey.DAMAGE, this::convertDamage);
        this.register(StructuredDataKey.UNBREAKABLE, this::convertUnbreakable);
        this.register(StructuredDataKey.CUSTOM_NAME, this::convertCustomName);
        this.register(StructuredDataKey.ITEM_NAME, this::convertItemName);
        this.register(StructuredDataKey.LORE, this::convertLore);
        this.register(StructuredDataKey.RARITY, this::convertRarity);
        this.register(StructuredDataKey.ENCHANTMENTS, this::convertEnchantments);
        this.register(StructuredDataKey.CAN_PLACE_ON, this::convertCanPlaceOn);
        this.register(StructuredDataKey.CAN_BREAK, this::convertCanBreak);
        this.register(StructuredDataKey.ATTRIBUTE_MODIFIERS1_20_5, this::convertAttributeModifiers);
        this.register(StructuredDataKey.CUSTOM_MODEL_DATA, this::convertCustomModelData);
        this.register(StructuredDataKey.HIDE_ADDITIONAL_TOOLTIP, this::convertHideAdditionalTooltip);
        this.register(StructuredDataKey.HIDE_TOOLTIP, this::convertHideTooltip);
        this.register(StructuredDataKey.REPAIR_COST, this::convertRepairCost);
        this.register(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE, this::convertEnchantmentGlintOverride);
        this.registerEmpty(StructuredDataKey.CREATIVE_SLOT_LOCK);
        this.register(StructuredDataKey.INTANGIBLE_PROJECTILE, this::convertIntangibleProjectile);
        this.register(StructuredDataKey.FOOD1_20_5, this::convertFood);
        this.register(StructuredDataKey.FIRE_RESISTANT, this::convertFireResistant);
        this.register(StructuredDataKey.TOOL, this::convertTool);
        this.register(StructuredDataKey.STORED_ENCHANTMENTS, this::convertStoredEnchantments);
        this.register(StructuredDataKey.DYED_COLOR, this::convertDyedColor);
        this.register(StructuredDataKey.MAP_COLOR, this::convertMapColor);
        this.register(StructuredDataKey.MAP_ID, this::convertMapId);
        this.register(StructuredDataKey.MAP_DECORATIONS, this::convertMapDecorations);
        this.registerEmpty(StructuredDataKey.MAP_POST_PROCESSING);
        this.register(StructuredDataKey.CHARGED_PROJECTILES1_20_5, this::convertChargedProjectiles);
        this.register(StructuredDataKey.BUNDLE_CONTENTS1_20_5, this::convertBundleContents);
        this.register(StructuredDataKey.POTION_CONTENTS, this::convertPotionContents);
        this.register(StructuredDataKey.SUSPICIOUS_STEW_EFFECTS, this::convertSuspiciousStewEffects);
        this.register(StructuredDataKey.WRITABLE_BOOK_CONTENT, this::convertWritableBookContent);
        this.register(StructuredDataKey.WRITTEN_BOOK_CONTENT, this::convertWrittenBookContent);
        this.register(StructuredDataKey.TRIM, this::convertTrim);
        this.register(StructuredDataKey.DEBUG_STICK_STATE, this::convertDebugStickRate);
        this.register(StructuredDataKey.ENTITY_DATA, this::convertEntityData);
        this.register(StructuredDataKey.BUCKET_ENTITY_DATA, this::convertBucketEntityData);
        this.register(StructuredDataKey.BLOCK_ENTITY_DATA, this::convertBlockEntityData);
        this.register(StructuredDataKey.INSTRUMENT, this::convertInstrument);
        this.register(StructuredDataKey.OMINOUS_BOTTLE_AMPLIFIER, this::convertOminousBottleAmplifier);
        this.register(StructuredDataKey.RECIPES, this::convertRecipes);
        this.register(StructuredDataKey.LODESTONE_TRACKER, this::convertLodestoneTracker);
        this.register(StructuredDataKey.FIREWORK_EXPLOSION, this::convertFireworkExplosion);
        this.register(StructuredDataKey.FIREWORKS, this::convertFireworks);
        this.register(StructuredDataKey.PROFILE, this::convertProfile);
        this.register(StructuredDataKey.NOTE_BLOCK_SOUND, this::convertNoteBlockSound);
        this.register(StructuredDataKey.BANNER_PATTERNS, this::convertBannerPatterns);
        this.register(StructuredDataKey.BASE_COLOR, this::convertBaseColor);
        this.register(StructuredDataKey.POT_DECORATIONS, this::convertPotDecorations);
        this.register(StructuredDataKey.CONTAINER1_20_5, this::convertContainer);
        this.register(StructuredDataKey.BLOCK_STATE, this::convertBlockState);
        this.register(StructuredDataKey.BEES, this::convertBees);
        this.register(StructuredDataKey.LOCK, this::convertLock);
        this.register(StructuredDataKey.CONTAINER_LOOT, this::convertContainerLoot);
    }

    @Override
    protected void handleHoverEvent(UserConnection connection, CompoundTag hoverEventTag) {
        super.handleHoverEvent(connection, hoverEventTag);
        StringTag actionTag = hoverEventTag.getStringTag("action");
        if (actionTag == null) {
            return;
        }
        if (actionTag.getValue().equals("show_item")) {
            Item structuredItem;
            CompoundTag tagTag;
            CompoundTag contentsTag;
            Tag valueTag = hoverEventTag.remove("value");
            if (valueTag != null) {
                CompoundTag tag = ComponentUtil.deserializeShowItem(valueTag, SerializerVersion.V1_20_3);
                CompoundTag contentsTag2 = new CompoundTag();
                contentsTag2.put("id", tag.getStringTag("id"));
                contentsTag2.put("count", new IntTag(tag.getByte("Count")));
                if (tag.get("tag") instanceof CompoundTag) {
                    contentsTag2.put("tag", new StringTag(SerializerVersion.V1_20_3.toSNBT(tag.getCompoundTag("tag"))));
                }
                hoverEventTag.put("contents", contentsTag2);
            }
            if ((contentsTag = hoverEventTag.getCompoundTag("contents")) == null) {
                return;
            }
            StringTag idTag = contentsTag.getStringTag("id");
            if (idTag == null) {
                return;
            }
            int itemId = Protocol1_20_3To1_20_5.MAPPINGS.getFullItemMappings().id(idTag.getValue());
            if (itemId == -1) {
                itemId = 1;
            }
            StringTag tag = (StringTag)contentsTag.remove("tag");
            try {
                tagTag = tag != null ? (CompoundTag)SerializerVersion.V1_20_3.toTag(tag.getValue()) : null;
            }
            catch (Exception e) {
                if (!Via.getConfig().isSuppressConversionWarnings()) {
                    CompoundTag compoundTag = contentsTag;
                    this.protocol.getLogger().log(Level.WARNING, "Error reading NBT in show_item: " + compoundTag, e);
                }
                return;
            }
            DataItem dataItem = new DataItem();
            dataItem.setIdentifier(itemId);
            if (tagTag != null) {
                dataItem.setTag(tagTag);
            }
            if ((structuredItem = this.protocol.getItemRewriter().handleItemToClient(connection, dataItem)).amount() < 1) {
                structuredItem.setAmount(1);
            }
            if (structuredItem.identifier() != 0) {
                String identifier = this.mappedIdentifier(structuredItem.identifier());
                if (identifier != null) {
                    contentsTag.putString("id", identifier);
                }
            } else {
                contentsTag.putString("id", "minecraft:stone");
            }
            Map<StructuredDataKey<?>, StructuredData<?>> data = structuredItem.dataContainer().data();
            if (!data.isEmpty()) {
                CompoundTag components;
                try {
                    components = this.toTag(connection, data, false);
                }
                catch (Exception e) {
                    if (!Via.getConfig().isSuppressConversionWarnings()) {
                        this.protocol.getLogger().log(Level.WARNING, "Error writing components in show_item!", e);
                    }
                    return;
                }
                contentsTag.put("components", components);
            }
        } else if (actionTag.getValue().equals("show_entity")) {
            CompoundTag contentsTag;
            Tag valueTag = hoverEventTag.remove("value");
            if (valueTag != null) {
                CompoundTag tag = ComponentUtil.deserializeShowItem(valueTag, SerializerVersion.V1_20_3);
                CompoundTag contentsTag3 = new CompoundTag();
                contentsTag3.put("type", tag.getStringTag("type"));
                contentsTag3.put("id", tag.getStringTag("id"));
                contentsTag3.put("name", SerializerVersion.V1_20_3.toTag(SerializerVersion.V1_20_3.toComponent(tag.getString("name"))));
                hoverEventTag.put("contents", contentsTag3);
            }
            if ((contentsTag = hoverEventTag.getCompoundTag("contents")) == null) {
                return;
            }
            if (this.protocol.getMappingData().getEntityMappings().mappedId(contentsTag.getString("type")) == -1) {
                contentsTag.put("type", new StringTag("pig"));
            }
        }
    }

    public CompoundTag toTag(UserConnection connection, Map<StructuredDataKey<?>, StructuredData<?>> data, boolean empty) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<StructuredDataKey<?>, StructuredData<?>> entry : data.entrySet()) {
            StructuredDataKey<?> key = entry.getKey();
            String identifier = key.identifier();
            ConverterPair<?> converter = this.converters.get(key);
            if (converter == null) {
                String string = identifier;
                Via.getPlatform().getLogger().severe("No converter found for data component: " + string);
                continue;
            }
            StructuredData<?> value = entry.getValue();
            if (value.isEmpty()) {
                if (empty) {
                    String string = identifier;
                    tag.put("!" + string, new CompoundTag());
                    continue;
                }
                String string = identifier;
                throw new IllegalArgumentException("Empty structured data: " + string);
            }
            Tag valueTag = converter.dataConverter.convert(connection, value.value());
            if (valueTag == null) continue;
            tag.put(identifier, valueTag);
        }
        return tag;
    }

    public List<StructuredData<?>> toData(CompoundTag tag) {
        ArrayList list = new ArrayList();
        for (Map.Entry<String, Tag> entry : tag.entrySet()) {
            StructuredData<?> data = this.readFromTag(entry.getKey(), entry.getValue());
            list.add(data);
        }
        return list;
    }

    public StructuredData<?> readFromTag(String identifier, Tag tag) {
        int id = this.protocol.getMappingData().getDataComponentSerializerMappings().mappedId(identifier);
        Preconditions.checkArgument((id != -1 ? 1 : 0) != 0, (String)"Unknown data component: %s", (Object[])new Object[]{identifier});
        StructuredDataKey<?> key = this.structuredDataType.key(id);
        return this.readFromTag(key, id, tag);
    }

    <T> StructuredData<T> readFromTag(StructuredDataKey<T> key, int id, Tag tag) {
        TagConverter<T> converter = this.tagConverter(key);
        Preconditions.checkNotNull(converter, (String)"No converter found for: %s", (Object[])new Object[]{key});
        return StructuredData.of(key, converter.convert(tag), id);
    }

    String mappedIdentifier(int id) {
        return Protocol1_20_3To1_20_5.MAPPINGS.getFullItemMappings().mappedIdentifier(id);
    }

    protected CompoundTag convertCustomData(CompoundTag value) {
        return value;
    }

    protected IntTag convertMaxStackSize(Integer value) {
        return this.convertIntRange(value, 1, 99);
    }

    protected IntTag convertMaxDamage(Integer value) {
        return this.convertPositiveInt(value);
    }

    protected IntTag convertDamage(Integer value) {
        return this.convertNonNegativeInt(value);
    }

    protected CompoundTag convertUnbreakable(Unbreakable value) {
        CompoundTag tag = new CompoundTag();
        if (!value.showInTooltip()) {
            tag.putBoolean("show_in_tooltip", false);
        }
        return tag;
    }

    protected StringTag convertCustomName(Tag value) {
        return this.convertComponent(value);
    }

    protected StringTag convertItemName(Tag value) {
        return this.convertComponent(value);
    }

    protected ListTag<StringTag> convertLore(Tag[] value) {
        return this.convertComponents(value, 256);
    }

    protected StringTag convertRarity(Integer value) {
        return this.convertEnumEntry(value, "common", "uncommon", "rare", "epic");
    }

    protected CompoundTag convertEnchantments(Enchantments value) {
        CompoundTag tag = new CompoundTag();
        CompoundTag levels = new CompoundTag();
        for (Int2IntMap.Entry entry : value.enchantments().int2IntEntrySet()) {
            int level = this.checkIntRange(0, 255, entry.getIntValue());
            levels.putInt(Enchantments1_20_5.idToKey(entry.getIntKey()), level);
        }
        tag.put("levels", levels);
        if (!value.showInTooltip()) {
            tag.putBoolean("show_in_tooltip", false);
        }
        return tag;
    }

    protected CompoundTag convertCanPlaceOn(AdventureModePredicate value) {
        return this.convertBlockPredicate(value);
    }

    protected CompoundTag convertCanBreak(AdventureModePredicate value) {
        return this.convertBlockPredicate(value);
    }

    protected CompoundTag convertBlockPredicate(AdventureModePredicate value) {
        CompoundTag tag = new CompoundTag();
        ListTag<CompoundTag> predicates = new ListTag<CompoundTag>(CompoundTag.class);
        for (BlockPredicate predicate : value.predicates()) {
            CompoundTag predicateTag = new CompoundTag();
            if (predicate.holderSet() != null) {
                this.convertHolderSet(predicateTag, "blocks", predicate.holderSet());
            }
            if (predicate.propertyMatchers() != null) {
                predicateTag.put("state", this.createState(predicate));
            }
            if (predicate.tag() != null) {
                predicateTag.put("nbt", predicate.tag());
            }
            predicates.add(predicateTag);
        }
        tag.put("predicates", predicates);
        if (!value.showInTooltip()) {
            tag.putBoolean("show_in_tooltip", false);
        }
        return tag;
    }

    protected CompoundTag createState(BlockPredicate predicate) {
        CompoundTag state = new CompoundTag();
        for (StatePropertyMatcher matcher : predicate.propertyMatchers()) {
            Either<String, StatePropertyMatcher.RangedMatcher> match = matcher.matcher();
            if (match.isLeft()) {
                state.putString(matcher.name(), match.left());
                continue;
            }
            StatePropertyMatcher.RangedMatcher range = match.right();
            CompoundTag rangeTag = new CompoundTag();
            if (range.minValue() != null) {
                rangeTag.putString("min", range.minValue());
            }
            if (range.maxValue() != null) {
                rangeTag.putString("max", range.maxValue());
            }
            state.put(matcher.name(), rangeTag);
        }
        return state;
    }

    protected CompoundTag convertAttributeModifiers(AttributeModifiers1_20_5 value) {
        CompoundTag tag = new CompoundTag();
        ListTag<CompoundTag> modifiers = new ListTag<CompoundTag>(CompoundTag.class);
        for (AttributeModifiers1_20_5.AttributeModifier modifier : value.modifiers()) {
            CompoundTag modifierTag = new CompoundTag();
            String type = Attributes1_20_5.idToKey(modifier.attribute());
            if (type == null) {
                int n = modifier.attribute();
                throw new IllegalArgumentException("Unknown attribute type: " + n);
            }
            modifierTag.putString("type", type);
            this.convertModifierData(modifierTag, modifier.modifier());
            if (modifier.slotType() != 0) {
                String slotType = EquipmentSlots1_20_5.idToKey(modifier.slotType());
                Preconditions.checkNotNull((Object)slotType, (String)"Unknown slot type %s", (Object[])new Object[]{modifier.slotType()});
                modifierTag.putString("slot", slotType);
            }
            modifiers.add(modifierTag);
        }
        tag.put("modifiers", modifiers);
        if (!value.showInTooltip()) {
            tag.putBoolean("show_in_tooltip", false);
        }
        return tag;
    }

    protected IntTag convertCustomModelData(Integer value) {
        return new IntTag(value);
    }

    protected CompoundTag convertHideAdditionalTooltip(Unit value) {
        return this.convertUnit();
    }

    protected CompoundTag convertHideTooltip(Unit value) {
        return this.convertUnit();
    }

    protected IntTag convertRepairCost(Integer value) {
        return this.convertIntRange(value, 0, Integer.MAX_VALUE);
    }

    protected ByteTag convertEnchantmentGlintOverride(Boolean value) {
        return new ByteTag(value);
    }

    protected CompoundTag convertIntangibleProjectile(Tag value) {
        return this.convertUnit();
    }

    protected CompoundTag convertFood(FoodProperties value) {
        CompoundTag tag = new CompoundTag();
        tag.put("nutrition", this.convertNonNegativeInt(value.nutrition()));
        tag.putFloat("saturation", value.saturationModifier());
        if (value.canAlwaysEat()) {
            tag.putBoolean("can_always_eat", true);
        }
        if (value.eatSeconds() != 1.6f) {
            tag.put("eat_seconds", this.convertPositiveFloat(Float.valueOf(value.eatSeconds())));
        }
        if (value.possibleEffects().length > 0) {
            ListTag<CompoundTag> effects = new ListTag<CompoundTag>(CompoundTag.class);
            for (FoodEffect foodEffect : value.possibleEffects()) {
                CompoundTag effectTag = new CompoundTag();
                CompoundTag potionEffectTag = new CompoundTag();
                this.convertPotionEffect(potionEffectTag, foodEffect.effect());
                effectTag.put("effect", potionEffectTag);
                if (foodEffect.probability() == 1.0f) continue;
                effectTag.putFloat("probability", foodEffect.probability());
            }
            tag.put("effects", effects);
        }
        return tag;
    }

    protected CompoundTag convertFireResistant(Unit value) {
        return this.convertUnit();
    }

    protected CompoundTag convertTool(ToolProperties value) {
        CompoundTag tag = new CompoundTag();
        ListTag<CompoundTag> rules = new ListTag<CompoundTag>(CompoundTag.class);
        for (ToolRule rule : value.rules()) {
            CompoundTag ruleTag = new CompoundTag();
            this.convertHolderSet(ruleTag, "blocks", rule.blocks());
            if (rule.speed() != null) {
                ruleTag.putFloat("speed", rule.speed().floatValue());
            }
            if (rule.correctForDrops() != null) {
                ruleTag.putBoolean("correct_for_drops", rule.correctForDrops());
            }
            rules.add(ruleTag);
        }
        tag.put("rules", rules);
        if (value.defaultMiningSpeed() != 1.0f) {
            tag.putFloat("default_mining_speed", value.defaultMiningSpeed());
        }
        if (value.damagePerBlock() != 1) {
            tag.put("damage_per_block", this.convertNonNegativeInt(value.damagePerBlock()));
        }
        return tag;
    }

    protected CompoundTag convertStoredEnchantments(Enchantments value) {
        return this.convertEnchantments(value);
    }

    protected CompoundTag convertDyedColor(DyedColor value) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("rgb", value.rgb());
        if (!value.showInTooltip()) {
            tag.putBoolean("show_in_tooltip", false);
        }
        return tag;
    }

    protected IntTag convertMapColor(Integer value) {
        return new IntTag(value);
    }

    protected IntTag convertMapId(Integer value) {
        return new IntTag(value);
    }

    protected CompoundTag convertMapDecorations(CompoundTag value) {
        return value;
    }

    protected ListTag<CompoundTag> convertChargedProjectiles(UserConnection connection, Item[] value) {
        return this.convertItemArray(connection, value);
    }

    protected ListTag<CompoundTag> convertBundleContents(UserConnection connection, Item[] value) {
        return this.convertItemArray(connection, value);
    }

    protected CompoundTag convertPotionContents(PotionContents value) {
        String potion;
        CompoundTag tag = new CompoundTag();
        if (value.potion() != null && (potion = Potions1_20_5.idToKey(value.potion())) != null) {
            tag.putString("potion", potion);
        }
        if (value.customColor() != null) {
            tag.putInt("custom_color", value.customColor());
        }
        for (PotionEffect effect : value.customEffects()) {
            this.convertPotionEffect(tag, effect);
        }
        return tag;
    }

    protected ListTag<CompoundTag> convertSuspiciousStewEffects(SuspiciousStewEffect[] value) {
        ListTag<CompoundTag> tag = new ListTag<CompoundTag>(CompoundTag.class);
        for (SuspiciousStewEffect effect : value) {
            CompoundTag effectTag = new CompoundTag();
            String id = PotionEffects1_20_5.idToKey(effect.mobEffect());
            if (id != null) {
                effectTag.putString("id", id);
            }
            if (effect.duration() != 160) {
                effectTag.putInt("duration", effect.duration());
            }
            tag.add(effectTag);
        }
        return tag;
    }

    protected CompoundTag convertWritableBookContent(FilterableString[] value) {
        CompoundTag tag = new CompoundTag();
        if (value == null) {
            return tag;
        }
        if (value.length > 100) {
            int n = value.length;
            throw new IllegalArgumentException("Too many pages: " + n);
        }
        ListTag<CompoundTag> pagesTag = new ListTag<CompoundTag>(CompoundTag.class);
        for (FilterableString page : value) {
            CompoundTag pageTag = new CompoundTag();
            this.convertFilterableString(pageTag, page, 1024);
            pagesTag.add(pageTag);
        }
        tag.put("pages", pagesTag);
        return tag;
    }

    protected CompoundTag convertWrittenBookContent(WrittenBook value) {
        CompoundTag tag = new CompoundTag();
        this.convertFilterableString(tag, value.title(), 32);
        tag.putString("author", value.author());
        if (value.generation() != 0) {
            tag.put("generation", this.convertIntRange(value.generation(), 0, 3));
        }
        CompoundTag title2 = new CompoundTag();
        this.convertFilterableString(title2, value.title(), 32);
        tag.put("title", title2);
        ListTag<CompoundTag> pagesTag = new ListTag<CompoundTag>(CompoundTag.class);
        for (FilterableComponent page : value.pages()) {
            CompoundTag pageTag = new CompoundTag();
            this.convertFilterableComponent(pageTag, page);
            pagesTag.add(pageTag);
        }
        if (!pagesTag.isEmpty()) {
            tag.put("pages", pagesTag);
        }
        if (value.resolved()) {
            tag.putBoolean("resolved", true);
        }
        return tag;
    }

    protected CompoundTag convertTrim(UserConnection connection, ArmorTrim value) {
        CompoundTag tag = new CompoundTag();
        Holder<ArmorTrimMaterial> material = value.material();
        ArmorTrimStorage trimStorage = connection.get(ArmorTrimStorage.class);
        if (material.hasId()) {
            String trimMaterial = trimStorage.trimMaterials().idToKey(material.id());
            tag.putString("material", trimMaterial);
        } else {
            ArmorTrimMaterial armorTrimMaterial = material.value();
            CompoundTag materialTag = new CompoundTag();
            String ingredient = Protocol1_20_3To1_20_5.MAPPINGS.getFullItemMappings().identifier(armorTrimMaterial.itemId());
            if (ingredient == null) {
                int n = armorTrimMaterial.itemId();
                throw new IllegalArgumentException("Unknown item: " + n);
            }
            CompoundTag overrideArmorMaterialsTag = new CompoundTag();
            for (Int2ObjectMap.Entry entry : armorTrimMaterial.overrideArmorMaterials().int2ObjectEntrySet()) {
                String materialKey = ArmorMaterials1_20_5.idToKey(entry.getIntKey());
                if (materialKey == null) continue;
                overrideArmorMaterialsTag.putString(materialKey, (String)entry.getValue());
            }
            materialTag.putString("asset_name", armorTrimMaterial.assetName());
            materialTag.putString("ingredient", ingredient);
            materialTag.putFloat("item_model_index", armorTrimMaterial.itemModelIndex());
            materialTag.put("override_armor_materials", overrideArmorMaterialsTag);
            materialTag.put("description", armorTrimMaterial.description());
            tag.put("material", materialTag);
        }
        Holder<ArmorTrimPattern> pattern = value.pattern();
        if (pattern.hasId()) {
            tag.putString("pattern", trimStorage.trimPatterns().idToKey(pattern.id()));
        } else {
            ArmorTrimPattern armorTrimPattern = pattern.value();
            CompoundTag patternTag = new CompoundTag();
            String templateItem = Protocol1_20_3To1_20_5.MAPPINGS.getFullItemMappings().identifier(armorTrimPattern.itemId());
            if (templateItem == null) {
                int n = armorTrimPattern.itemId();
                throw new IllegalArgumentException("Unknown item: " + n);
            }
            patternTag.put("asset_id", this.convertIdentifier(armorTrimPattern.assetName()));
            patternTag.putString("template_item", templateItem);
            patternTag.put("description", armorTrimPattern.description());
            if (armorTrimPattern.decal()) {
                patternTag.putBoolean("decal", true);
            }
            tag.put("pattern", patternTag);
        }
        if (!value.showInTooltip()) {
            tag.putBoolean("show_in_tooltip", false);
        }
        return tag;
    }

    protected CompoundTag convertDebugStickRate(CompoundTag value) {
        return value;
    }

    protected CompoundTag convertEntityData(CompoundTag value) {
        return this.convertNbtWithId(value);
    }

    protected CompoundTag convertBucketEntityData(CompoundTag value) {
        return this.convertNbt(value);
    }

    protected CompoundTag convertBlockEntityData(CompoundTag value) {
        return this.convertNbtWithId(value);
    }

    protected Tag convertInstrument(Holder<Instrument> value) {
        if (value.hasId()) {
            return new StringTag(Instruments1_20_3.idToKey(value.id()));
        }
        Instrument instrument = value.value();
        CompoundTag tag = new CompoundTag();
        Holder<SoundEvent> sound = instrument.soundEvent();
        if (sound.hasId()) {
            tag.putString("sound_event", Protocol1_20_3To1_20_5.MAPPINGS.soundName(sound.id()));
        } else {
            SoundEvent soundEvent = sound.value();
            CompoundTag soundEventTag = new CompoundTag();
            soundEventTag.put("sound_id", this.convertIdentifier(soundEvent.identifier()));
            if (soundEvent.fixedRange() != null) {
                soundEventTag.putFloat("range", soundEvent.fixedRange().floatValue());
            }
        }
        tag.put("use_duration", this.convertPositiveInt(instrument.useDuration()));
        tag.put("range", this.convertPositiveFloat(Float.valueOf(instrument.range())));
        return tag;
    }

    protected IntTag convertOminousBottleAmplifier(Integer value) {
        return this.convertIntRange(value, 0, 4);
    }

    protected Tag convertRecipes(Tag value) {
        return value;
    }

    protected CompoundTag convertLodestoneTracker(LodestoneTracker value) {
        CompoundTag tag = new CompoundTag();
        if (value.position() != null) {
            this.convertGlobalPos(tag, value.position());
        }
        if (!value.tracked()) {
            tag.putBoolean("tracked", false);
        }
        return tag;
    }

    protected CompoundTag convertFireworkExplosion(FireworkExplosion value) {
        CompoundTag tag = new CompoundTag();
        tag.put("shape", this.convertEnumEntry(value.shape(), "small_ball", "large_ball", "star", "creeper", "burst"));
        if (value.colors().length > 0) {
            tag.put("colors", new IntArrayTag(value.colors()));
        }
        if (value.fadeColors().length > 0) {
            tag.put("fade_colors", new IntArrayTag(value.fadeColors()));
        }
        if (value.hasTrail()) {
            tag.putBoolean("trail", true);
        }
        if (value.hasTwinkle()) {
            tag.putBoolean("twinkle", true);
        }
        return tag;
    }

    protected CompoundTag convertFireworks(Fireworks value) {
        CompoundTag tag = new CompoundTag();
        if (value.flightDuration() != 0) {
            tag.put("flight_duration", this.convertUnsignedByte((byte)value.flightDuration()));
        }
        ListTag<CompoundTag> explosions = new ListTag<CompoundTag>(CompoundTag.class);
        if (value.explosions().length > 256) {
            int n = value.explosions().length;
            throw new IllegalArgumentException("Too many explosions: " + n);
        }
        for (FireworkExplosion explosion : value.explosions()) {
            explosions.add(this.convertFireworkExplosion(explosion));
        }
        tag.put("explosions", explosions);
        return tag;
    }

    protected CompoundTag convertProfile(GameProfile value) {
        CompoundTag tag = new CompoundTag();
        if (value.name() != null) {
            tag.putString("name", value.name());
        }
        if (value.id() != null) {
            tag.put("id", new IntArrayTag(UUIDUtil.toIntArray(value.id())));
        }
        if (value.properties().length > 0) {
            this.convertProperties(tag, value.properties());
        }
        return tag;
    }

    protected StringTag convertNoteBlockSound(String value) {
        return this.convertIdentifier(value);
    }

    protected ListTag<CompoundTag> convertBannerPatterns(BannerPatternLayer[] value) {
        ListTag<CompoundTag> tag = new ListTag<CompoundTag>(CompoundTag.class);
        for (BannerPatternLayer layer : value) {
            CompoundTag layerTag = new CompoundTag();
            this.convertBannerPattern(layerTag, layer.pattern());
            layerTag.put("color", this.convertDyeColor(layer.dyeColor()));
            tag.add(layerTag);
        }
        return tag;
    }

    protected StringTag convertBaseColor(Integer value) {
        return this.convertDyeColor(value);
    }

    protected ListTag<StringTag> convertPotDecorations(PotDecorations value) {
        ListTag<StringTag> tag = new ListTag<StringTag>(StringTag.class);
        for (int decoration : value.itemIds()) {
            String identifier = this.mappedIdentifier(decoration);
            if (identifier == null) {
                int n = decoration;
                throw new IllegalArgumentException("Unknown item: " + n);
            }
            tag.add(new StringTag(identifier));
        }
        return tag;
    }

    protected ListTag<CompoundTag> convertContainer(UserConnection connection, Item[] value) {
        ListTag<CompoundTag> tag = new ListTag<CompoundTag>(CompoundTag.class);
        ListTag<CompoundTag> items = this.convertItemArray(connection, value);
        for (int i = 0; i < items.size(); ++i) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putInt("slot", i);
            itemTag.put("item", items.get(i));
            tag.add(itemTag);
        }
        return tag;
    }

    protected CompoundTag convertBlockState(BlockStateProperties value) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<String, String> entry : value.properties().entrySet()) {
            tag.putString(entry.getKey(), entry.getValue());
        }
        return tag;
    }

    protected ListTag<CompoundTag> convertBees(Bee[] value) {
        ListTag<CompoundTag> tag = new ListTag<CompoundTag>(CompoundTag.class);
        for (Bee bee : value) {
            CompoundTag beeTag = new CompoundTag();
            if (!bee.entityData().isEmpty()) {
                beeTag.put("entity_data", this.convertNbt(bee.entityData()));
            }
            beeTag.putInt("ticks_in_hive", bee.ticksInHive());
            beeTag.putInt("min_ticks_in_hive", bee.minTicksInHive());
        }
        return tag;
    }

    protected StringTag convertLock(Tag value) {
        return (StringTag)value;
    }

    protected CompoundTag convertContainerLoot(CompoundTag value) {
        return value;
    }

    protected void convertModifierData(CompoundTag tag, AttributeModifiers1_20_5.ModifierData data) {
        tag.put("uuid", new IntArrayTag(UUIDUtil.toIntArray(data.uuid())));
        tag.putString("name", data.name());
        tag.putDouble("amount", data.amount());
        tag.putString("operation", BlockItemPacketRewriter1_20_5.ATTRIBUTE_OPERATIONS[data.operation()]);
    }

    protected void convertPotionEffect(CompoundTag tag, PotionEffect effect) {
        String id = PotionEffects1_20_5.idToKey(effect.effect());
        if (id == null) {
            int n = effect.effect();
            throw new IllegalArgumentException("Unknown potion effect: " + n);
        }
        tag.putString("id", id);
        this.convertPotionEffectData(tag, effect.effectData());
    }

    protected void convertPotionEffectData(CompoundTag tag, PotionEffectData data) {
        if (data.amplifier() != 0) {
            tag.putInt("amplifier", data.amplifier());
        }
        if (data.duration() != 0) {
            tag.putInt("duration", data.duration());
        }
        if (data.ambient()) {
            tag.putBoolean("ambient", true);
        }
        if (!data.showParticles()) {
            tag.putBoolean("show_particles", false);
        }
        tag.putBoolean("show_icon", data.showIcon());
        if (data.hiddenEffect() != null) {
            CompoundTag hiddenEffect = new CompoundTag();
            this.convertPotionEffectData(hiddenEffect, data.hiddenEffect());
            tag.put("hidden_effect", hiddenEffect);
        }
    }

    protected void convertHolderSet(CompoundTag tag, String name, HolderSet set) {
        if (set.hasTagKey()) {
            tag.putString(name, set.tagKey());
        } else {
            ListTag<StringTag> identifiers = new ListTag<StringTag>(StringTag.class);
            for (int id : set.ids()) {
                String identifier = Protocol1_20_3To1_20_5.MAPPINGS.blockName(id);
                if (identifier == null) continue;
                identifiers.add(new StringTag(identifier));
            }
            tag.put(name, identifiers);
        }
    }

    protected ListTag<CompoundTag> convertItemArray(UserConnection connection, Item[] value) {
        ListTag<CompoundTag> tag = new ListTag<CompoundTag>(CompoundTag.class);
        for (Item item : value) {
            CompoundTag itemTag = new CompoundTag();
            this.convertItem(connection, itemTag, item);
            tag.add(itemTag);
        }
        return tag;
    }

    protected void convertItem(UserConnection connection, CompoundTag tag, Item item) {
        String identifier = this.mappedIdentifier(item.identifier());
        if (identifier == null) {
            int n = item.identifier();
            throw new IllegalArgumentException("Unknown item: " + n);
        }
        tag.putString("id", identifier);
        try {
            tag.put("count", this.convertPositiveInt(item.amount()));
        }
        catch (IllegalArgumentException ignored) {
            tag.putInt("count", 1);
        }
        Map<StructuredDataKey<?>, StructuredData<?>> components = item.dataContainer().data();
        tag.put("components", this.toTag(connection, components, true));
    }

    protected void convertFilterableString(CompoundTag tag, FilterableString string, int max) {
        tag.put("raw", this.convertString((String)string.raw(), 0, max));
        if (string.filtered() != null) {
            tag.put("filtered", this.convertString((String)string.filtered(), 0, max));
        }
    }

    protected void convertFilterableComponent(CompoundTag tag, FilterableComponent component) {
        tag.put("raw", this.convertComponent((Tag)component.raw()));
        if (component.filtered() != null) {
            tag.put("filtered", this.convertComponent((Tag)component.filtered()));
        }
    }

    protected void convertGlobalPos(CompoundTag tag, GlobalBlockPosition position) {
        CompoundTag posTag = new CompoundTag();
        posTag.putString("dimension", position.dimension());
        posTag.put("pos", new IntArrayTag(new int[]{position.x(), position.y(), position.z()}));
        tag.put("target", posTag);
    }

    protected void convertProperties(CompoundTag tag, GameProfile.Property[] properties) {
        ListTag<CompoundTag> propertiesTag = new ListTag<CompoundTag>(CompoundTag.class);
        for (GameProfile.Property property : properties) {
            CompoundTag propertyTag = new CompoundTag();
            propertyTag.putString("name", property.name());
            propertyTag.putString("value", property.value());
            if (property.signature() != null) {
                propertyTag.putString("signature", property.signature());
            }
            propertiesTag.add(propertyTag);
        }
        tag.put("properties", propertiesTag);
    }

    protected void convertBannerPattern(CompoundTag tag, Holder<BannerPattern> pattern) {
        if (pattern.hasId()) {
            tag.putString("pattern", BannerPatterns1_20_5.idToKey(pattern.id()));
            return;
        }
        BannerPattern bannerPattern = pattern.value();
        CompoundTag patternTag = new CompoundTag();
        patternTag.put("asset_id", this.convertIdentifier(bannerPattern.assetId()));
        patternTag.putString("translation_key", bannerPattern.translationKey());
        tag.put("pattern", patternTag);
    }

    protected IntTag convertPositiveInt(Integer value) {
        return this.convertIntRange(value, 1, Integer.MAX_VALUE);
    }

    protected IntTag convertNonNegativeInt(Integer value) {
        return this.convertIntRange(value, 0, Integer.MAX_VALUE);
    }

    protected IntTag convertIntRange(Integer value, int min, int max) {
        return new IntTag(this.checkIntRange(min, max, value));
    }

    protected FloatTag convertPositiveFloat(Float value) {
        return this.convertFloatRange(value, 0.0f, Float.MAX_VALUE);
    }

    protected FloatTag convertFloatRange(Float value, float min, float max) {
        return new FloatTag(this.checkFloatRange(min, max, value.floatValue()));
    }

    protected StringTag convertString(String value, int min, int max) {
        return new StringTag(this.checkStringRange(min, max, value));
    }

    protected ByteTag convertUnsignedByte(byte value) {
        if (value > 255) {
            byte by = value;
            throw new IllegalArgumentException("Value out of range: " + by);
        }
        return new ByteTag(value);
    }

    protected StringTag convertComponent(Tag value) {
        return this.convertComponent(value, Integer.MAX_VALUE);
    }

    protected StringTag convertComponent(Tag value, int max) {
        String json = this.serializerVersion().toString(this.serializerVersion().toComponent(value));
        return new StringTag(this.checkStringRange(0, max, json));
    }

    protected ListTag<StringTag> convertComponents(Tag[] value, int maxLength) {
        this.checkIntRange(0, maxLength, value.length);
        ListTag<StringTag> listTag = new ListTag<StringTag>(StringTag.class);
        for (Tag tag : value) {
            String json = this.serializerVersion().toString(this.serializerVersion().toComponent(tag));
            listTag.add(new StringTag(json));
        }
        return listTag;
    }

    protected StringTag convertEnumEntry(Integer value, String ... values2) {
        Integer n = value;
        Preconditions.checkArgument((value >= 0 && value < values2.length ? 1 : 0) != 0, (Object)("Enum value out of range: " + n));
        return new StringTag(values2[value]);
    }

    protected CompoundTag convertUnit() {
        return new CompoundTag();
    }

    protected CompoundTag convertNbt(CompoundTag tag) {
        return tag;
    }

    protected CompoundTag convertNbtWithId(CompoundTag tag) {
        if (tag.getStringTag("id") == null) {
            CompoundTag compoundTag = tag;
            throw new IllegalArgumentException("Missing id tag in nbt: " + compoundTag);
        }
        return tag;
    }

    protected StringTag convertIdentifier(String value) {
        if (!Key.isValid(value)) {
            String string = value;
            throw new IllegalArgumentException("Invalid identifier: " + string);
        }
        return new StringTag(value);
    }

    protected StringTag convertDyeColor(Integer value) {
        return new StringTag(DyeColors.colorById(value));
    }

    int checkIntRange(int min, int max, int value) {
        int n = value;
        Preconditions.checkArgument((value >= min && value <= max ? 1 : 0) != 0, (Object)("Value out of range: " + n));
        return value;
    }

    float checkFloatRange(float min, float max, float value) {
        float f = value;
        Preconditions.checkArgument((value >= min && value <= max ? 1 : 0) != 0, (Object)("Value out of range: " + f));
        return value;
    }

    String checkStringRange(int min, int max, String value) {
        String string = value;
        int length = value.length();
        Preconditions.checkArgument((length >= min && length <= max ? 1 : 0) != 0, (Object)("Value out of range: " + string));
        return value;
    }

    protected <T> void registerEmpty(StructuredDataKey<T> key) {
        this.converters.put(key, new ConverterPair(null, null));
    }

    protected <T> void register(StructuredDataKey<T> key, DataConverter<T> dataConverter) {
        this.converters.put(key, new ConverterPair<T>(dataConverter, null));
    }

    protected <T> void register(StructuredDataKey<T> key, SimpleDataConverter<T> dataConverter) {
        DataConverter<Object> converter = ($, value) -> dataConverter.convert(value);
        this.converters.put(key, new ConverterPair<Object>(converter, null));
    }

    protected <T> void register(StructuredDataKey<T> key, DataConverter<T> dataConverter, TagConverter<T> tagConverter) {
        this.converters.put(key, new ConverterPair<T>(dataConverter, tagConverter));
    }

    protected <T> @Nullable DataConverter<T> dataConverter(StructuredDataKey<T> key) {
        ConverterPair<?> converters = this.converters.get(key);
        return converters != null ? converters.dataConverter : null;
    }

    protected <T> @Nullable TagConverter<T> tagConverter(StructuredDataKey<T> key) {
        ConverterPair<?> converters = this.converters.get(key);
        return converters != null ? converters.tagConverter : null;
    }

    public SerializerVersion serializerVersion() {
        return SerializerVersion.V1_20_5;
    }

    @FunctionalInterface
    protected static interface SimpleDataConverter<T> {
        public Tag convert(T var1);
    }

    @FunctionalInterface
    protected static interface DataConverter<T> {
        public Tag convert(UserConnection var1, T var2);
    }

    private static final class ConverterPair<T> {
        final DataConverter<T> dataConverter;
        final TagConverter<T> tagConverter;

        ConverterPair(DataConverter<T> dataConverter, TagConverter<T> tagConverter) {
            this.dataConverter = dataConverter;
            this.tagConverter = tagConverter;
        }

        public DataConverter<T> dataConverter() {
            return this.dataConverter;
        }

        public TagConverter<T> tagConverter() {
            return this.tagConverter;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ConverterPair)) {
                return false;
            }
            ConverterPair converterPair = (ConverterPair)object;
            return Objects.equals(this.dataConverter, converterPair.dataConverter) && Objects.equals(this.tagConverter, converterPair.tagConverter);
        }

        public int hashCode() {
            return (0 * 31 + Objects.hashCode(this.dataConverter)) * 31 + Objects.hashCode(this.tagConverter);
        }

        public String toString() {
            return String.format("%s[dataConverter=%s, tagConverter=%s]", this.getClass().getSimpleName(), Objects.toString(this.dataConverter), Objects.toString(this.tagConverter));
        }
    }

    @FunctionalInterface
    protected static interface TagConverter<T> {
        public T convert(Tag var1);
    }
}

