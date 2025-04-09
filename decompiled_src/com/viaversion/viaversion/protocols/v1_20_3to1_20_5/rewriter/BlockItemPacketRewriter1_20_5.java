/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.IntTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.GameProfile;
import com.viaversion.viaversion.api.minecraft.GlobalBlockPosition;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.HolderSet;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.data.StructuredData;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
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
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter.RecipeRewriter1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.Protocol1_20_3To1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Attributes1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.BannerPatterns1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.DyeColors;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Enchantments1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.EquipmentSlots1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Instruments1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.MapDecorations1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.MaxStackSize1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.PotionEffects1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Potions1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter.EntityPacketRewriter1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter.StructuredDataConverter;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.ArmorTrimStorage;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.storage.BannerPatternStorage;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Either;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.MathUtil;
import com.viaversion.viaversion.util.SerializerVersion;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPacketRewriter1_20_5
extends ItemRewriter<ClientboundPacket1_20_3, ServerboundPacket1_20_5, Protocol1_20_3To1_20_5> {
    public static final String[] MOB_TAGS = new String[]{"NoAI", "Silent", "NoGravity", "Glowing", "Invulnerable", "Health", "Age", "Variant", "HuntingCooldown", "BucketVariantTag"};
    public static final String[] ATTRIBUTE_OPERATIONS = new String[]{"add_value", "add_multiplied_base", "add_multiplied_total"};
    static final StructuredDataConverter DATA_CONVERTER = new StructuredDataConverter(false);
    static final GameProfile.Property[] EMPTY_PROPERTIES = new GameProfile.Property[0];
    static final StatePropertyMatcher[] EMPTY_PROPERTY_MATCHERS = new StatePropertyMatcher[0];

    public BlockItemPacketRewriter1_20_5(Protocol1_20_3To1_20_5 protocol) {
        super(protocol, Types.ITEM1_20_2, Types.ITEM1_20_2_ARRAY, Types1_20_5.ITEM, Types1_20_5.ITEM_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_20_3> blockRewriter = BlockRewriter.for1_20_2(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_20_3.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_20_3.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_20_3.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_20_3.LEVEL_EVENT, 1010, 2001);
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.LEVEL_CHUNK_WITH_LIGHT, wrapper -> {
            Chunk chunk = blockRewriter.handleChunk1_19(wrapper, ChunkType1_20_2::new);
            for (int i = 0; i < chunk.blockEntities().size(); ++i) {
                BlockEntity blockEntity = chunk.blockEntities().get(i);
                if (this.isUnknownBlockEntity(blockEntity.typeId())) {
                    chunk.blockEntities().remove(i--);
                    continue;
                }
                this.updateBlockEntityTag(wrapper.user(), null, blockEntity.tag());
            }
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            int typeId = wrapper.passthrough(Types.VAR_INT);
            if (this.isUnknownBlockEntity(typeId)) {
                wrapper.cancel();
                return;
            }
            CompoundTag tag = wrapper.read(Types.COMPOUND_TAG);
            if (tag != null) {
                this.updateBlockEntityTag(wrapper.user(), null, tag);
            } else {
                tag = new CompoundTag();
            }
            wrapper.write(Types.COMPOUND_TAG, tag);
        });
        this.registerCooldown(ClientboundPackets1_20_3.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_20_3.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_20_3.CONTAINER_SET_SLOT);
        this.registerContainerClick1_17_1(ServerboundPackets1_20_5.CONTAINER_CLICK);
        this.registerContainerSetData(ClientboundPackets1_20_3.CONTAINER_SET_DATA);
        this.registerSetCreativeModeSlot(ServerboundPackets1_20_5.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_20_3To1_20_5)this.protocol).registerServerbound(ServerboundPackets1_20_5.CONTAINER_BUTTON_CLICK, wrapper -> {
            byte containerId = wrapper.read(Types.VAR_INT).byteValue();
            byte buttonId = wrapper.read(Types.VAR_INT).byteValue();
            wrapper.write(Types.BYTE, containerId);
            wrapper.write(Types.BYTE, buttonId);
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.UPDATE_ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Types.TAG);
                    wrapper.passthrough(Types.TAG);
                    Item item = this.handleNonEmptyItemToClient(wrapper.user(), wrapper.read(this.itemType()));
                    wrapper.write(this.mappedItemType(), item);
                    wrapper.passthrough(Types.VAR_INT);
                    int flags = wrapper.passthrough(Types.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                int requirements = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }
                wrapper.passthrough(Types.BOOLEAN);
            }
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.LEVEL_PARTICLES, wrapper -> {
            int particleId = wrapper.read(Types.VAR_INT);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            float offX = wrapper.passthrough(Types.FLOAT).floatValue();
            float offY = wrapper.passthrough(Types.FLOAT).floatValue();
            float offZ = wrapper.passthrough(Types.FLOAT).floatValue();
            float data = wrapper.passthrough(Types.FLOAT).floatValue();
            int count = wrapper.passthrough(Types.INT);
            ParticleMappings mappings = ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getParticleMappings();
            int mappedId = mappings.getNewId(particleId);
            Particle particle = new Particle(mappedId);
            if (mappedId == mappings.mappedId("entity_effect")) {
                int color;
                if (data == 0.0f) {
                    color = 0;
                } else if (count != 0) {
                    color = ThreadLocalRandom.current().nextInt();
                } else {
                    int red2 = Math.round(offX * 255.0f);
                    int green2 = Math.round(offY * 255.0f);
                    int blue2 = Math.round(offZ * 255.0f);
                    color = red2 << 16 | green2 << 8 | blue2;
                }
                particle.add(Types.INT, EntityPacketRewriter1_20_5.withAlpha(color));
            } else if (particleId == mappings.id("dust_color_transition")) {
                for (int i = 0; i < 7; ++i) {
                    particle.add(Types.FLOAT, wrapper.read(Types.FLOAT));
                }
                particle.add(Types.FLOAT, (Float)particle.removeArgument(3).getValue());
            } else if (mappings.isBlockParticle(particleId)) {
                int blockStateId = wrapper.read(Types.VAR_INT);
                particle.add(Types.VAR_INT, ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getNewBlockStateId(blockStateId));
            } else if (mappings.isItemParticle(particleId)) {
                Item item = this.handleNonEmptyItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_20_2));
                particle.add(Types1_20_5.ITEM, item);
            } else if (particleId == mappings.id("dust")) {
                for (int i = 0; i < 4; ++i) {
                    particle.add(Types.FLOAT, wrapper.read(Types.FLOAT));
                }
            } else if (particleId == mappings.id("vibration")) {
                int sourceTypeId = wrapper.read(Types.VAR_INT);
                particle.add(Types.VAR_INT, sourceTypeId);
                if (sourceTypeId == 0) {
                    particle.add(Types.BLOCK_POSITION1_14, wrapper.read(Types.BLOCK_POSITION1_14));
                } else if (sourceTypeId == 1) {
                    particle.add(Types.VAR_INT, wrapper.read(Types.VAR_INT));
                    particle.add(Types.FLOAT, wrapper.read(Types.FLOAT));
                } else {
                    int n = sourceTypeId;
                    ((Protocol1_20_3To1_20_5)this.protocol).getLogger().warning("Unknown vibration path position source type: " + n);
                }
                particle.add(Types.VAR_INT, wrapper.read(Types.VAR_INT));
            } else if (particleId == mappings.id("sculk_charge")) {
                particle.add(Types.FLOAT, wrapper.read(Types.FLOAT));
            } else if (particleId == mappings.id("shriek")) {
                particle.add(Types.VAR_INT, wrapper.read(Types.VAR_INT));
            }
            wrapper.write(Types1_20_5.PARTICLE, particle);
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.EXPLODE, wrapper -> {
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.FLOAT);
            int blocks = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < blocks; ++i) {
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.BYTE);
            }
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.VAR_INT);
            Particle smallExplosionParticle = wrapper.passthroughAndMap(Types1_20_3.PARTICLE, Types1_20_5.PARTICLE);
            Particle largeExplosionParticle = wrapper.passthroughAndMap(Types1_20_3.PARTICLE, Types1_20_5.PARTICLE);
            this.rewriteParticle(wrapper.user(), smallExplosionParticle);
            this.rewriteParticle(wrapper.user(), largeExplosionParticle);
            String sound = wrapper.read(Types.STRING);
            Float range = wrapper.read(Types.OPTIONAL_FLOAT);
            wrapper.write(Types.SOUND_EVENT, Holder.of(new SoundEvent(sound, range)));
        });
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.MERCHANT_OFFERS, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                Item input = this.handleNonEmptyItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_20_2));
                wrapper.write(Types1_20_5.ITEM_COST, input);
                Item output = this.handleNonEmptyItemToClient(wrapper.user(), wrapper.read(Types.ITEM1_20_2));
                wrapper.write(Types1_20_5.ITEM, output);
                Item secondInput = wrapper.read(Types.ITEM1_20_2);
                if (secondInput != null && (secondInput = this.handleItemToClient(wrapper.user(), secondInput)).isEmpty()) {
                    secondInput = null;
                }
                wrapper.write(Types1_20_5.OPTIONAL_ITEM_COST, secondInput);
                wrapper.passthrough(Types.BOOLEAN);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.FLOAT);
                wrapper.passthrough(Types.INT);
            }
        });
        RecipeRewriter1_20_3<ClientboundPacket1_20_3> recipeRewriter = new RecipeRewriter1_20_3<ClientboundPacket1_20_3>(this.protocol){

            @Override
            protected Item rewrite(UserConnection connection, @Nullable Item item) {
                return BlockItemPacketRewriter1_20_5.this.handleNonEmptyItemToClient(connection, item);
            }
        };
        ((Protocol1_20_3To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_20_3.UPDATE_RECIPES, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                String type = wrapper.read(Types.STRING);
                wrapper.passthrough(Types.STRING);
                wrapper.write(Types.VAR_INT, ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getRecipeSerializerMappings().mappedId(type));
                recipeRewriter.handleRecipeType(wrapper, type);
            }
        });
    }

    public Item handleNonEmptyItemToClient(UserConnection connection, @Nullable Item item) {
        if ((item = this.handleItemToClient(connection, item)).isEmpty()) {
            return new StructuredItem(1, 1);
        }
        return item;
    }

    @Override
    public Item handleItemToClient(UserConnection connection, @Nullable Item item) {
        if (item == null) {
            return StructuredItem.empty();
        }
        CompoundTag tag = item.tag();
        Item structuredItem = this.toStructuredItem(connection, item);
        if (tag != null) {
            tag.putBoolean(this.nbtTagName(), true);
            structuredItem.dataContainer().set(StructuredDataKey.CUSTOM_DATA, tag);
        }
        this.appendItemDataFixComponents(connection, structuredItem);
        if (Via.getConfig().handleInvalidItemCount() && structuredItem.amount() > MaxStackSize1_20_3.getMaxStackSize(structuredItem.identifier())) {
            structuredItem.dataContainer().set(StructuredDataKey.MAX_STACK_SIZE, structuredItem.amount());
        }
        return super.handleItemToClient(connection, structuredItem);
    }

    @Override
    public @Nullable Item handleItemToServer(UserConnection connection, Item item) {
        if (item.isEmpty()) {
            return null;
        }
        super.handleItemToServer(connection, item);
        return this.toOldItem(connection, item, DATA_CONVERTER);
    }

    public Item toOldItem(UserConnection connection, Item item, StructuredDataConverter dataConverter) {
        StructuredDataContainer data = item.dataContainer();
        data.setIdLookup(this.protocol, true);
        StructuredData<CompoundTag> customData = data.getNonEmptyData(StructuredDataKey.CUSTOM_DATA);
        CompoundTag tag = customData != null ? customData.value() : new CompoundTag();
        DataItem dataItem = new DataItem(item.identifier(), (byte)item.amount(), tag);
        if (!dataConverter.backupInconvertibleData() && customData != null && tag.remove(this.nbtTagName()) != null) {
            return dataItem;
        }
        for (StructuredData<?> structuredData : data.data().values()) {
            dataConverter.writeToTag(connection, structuredData, tag);
        }
        if (tag.isEmpty()) {
            dataItem.setTag(null);
        }
        return dataItem;
    }

    public Item toStructuredItem(UserConnection connection, Item old) {
        IntTag mapScaleDirectionTag;
        ListTag<StringTag> canDestroyTag;
        ListTag<StringTag> canPlaceOnTag;
        ListTag<CompoundTag> decorationsTag;
        boolean showAttributes;
        int id;
        String instrument;
        ListTag<CompoundTag> effectsTag;
        NumberTag trackedTag;
        ListTag<StringTag> recipesTag;
        CompoundTag explosionTag;
        CompoundTag trimTag;
        NumberTag unbreakable;
        CompoundTag debugProperty;
        CompoundTag blockEntityTag;
        CompoundTag entityTag;
        CompoundTag blockState;
        NumberTag customModelData;
        NumberTag repairCost;
        CompoundTag tag = old.tag();
        StructuredItem item = new StructuredItem(old.identifier(), (byte)old.amount(), new StructuredDataContainer());
        StructuredDataContainer data = item.dataContainer();
        data.setIdLookup(this.protocol, true);
        if (tag == null) {
            return item;
        }
        int hideFlagsValue = tag.getInt("HideFlags");
        if ((hideFlagsValue & 0x20) != 0) {
            data.set(StructuredDataKey.HIDE_ADDITIONAL_TOOLTIP);
        }
        this.updateDisplay(connection, data, tag.getCompoundTag("display"), hideFlagsValue);
        NumberTag damage = tag.getNumberTag("Damage");
        if (damage != null && damage.asInt() != 0) {
            data.set(StructuredDataKey.DAMAGE, damage.asInt());
        }
        if ((repairCost = tag.getNumberTag("RepairCost")) != null && repairCost.asInt() != 0) {
            data.set(StructuredDataKey.REPAIR_COST, repairCost.asInt());
        }
        if ((customModelData = tag.getNumberTag("CustomModelData")) != null) {
            data.set(StructuredDataKey.CUSTOM_MODEL_DATA, customModelData.asInt());
        }
        if ((blockState = tag.getCompoundTag("BlockStateTag")) != null) {
            this.updateBlockState(data, blockState);
        }
        if ((entityTag = tag.getCompoundTag("EntityTag")) != null) {
            if ((entityTag = entityTag.copy()).contains("variant")) {
                entityTag.putString("id", "minecraft:painting");
            } else if (entityTag.contains("ShowArms")) {
                entityTag.putString("id", "minecraft:armor_stand");
            }
            data.set(StructuredDataKey.ENTITY_DATA, entityTag);
        }
        if ((blockEntityTag = tag.getCompoundTag("BlockEntityTag")) != null) {
            CompoundTag clonedTag = blockEntityTag.copy();
            this.updateBlockEntityTag(connection, data, clonedTag);
            if (blockEntityTag.contains("id")) {
                item.dataContainer().set(StructuredDataKey.BLOCK_ENTITY_DATA, clonedTag);
            }
        }
        if ((debugProperty = tag.getCompoundTag("DebugProperty")) != null) {
            data.set(StructuredDataKey.DEBUG_STICK_STATE, debugProperty.copy());
        }
        if ((unbreakable = tag.getNumberTag("Unbreakable")) != null && unbreakable.asBoolean()) {
            data.set(StructuredDataKey.UNBREAKABLE, new Unbreakable((hideFlagsValue & 4) == 0));
        }
        if ((trimTag = tag.getCompoundTag("Trim")) != null) {
            this.updateArmorTrim(connection, data, trimTag, (hideFlagsValue & 0x80) == 0);
        }
        if ((explosionTag = tag.getCompoundTag("Explosion")) != null) {
            data.set(StructuredDataKey.FIREWORK_EXPLOSION, this.readExplosion(explosionTag));
        }
        if ((recipesTag = tag.getListTag("Recipes", StringTag.class)) != null) {
            data.set(StructuredDataKey.RECIPES, recipesTag);
        }
        if ((trackedTag = tag.getNumberTag("LodestoneTracked")) != null) {
            CompoundTag lodestonePosTag = tag.getCompoundTag("LodestonePos");
            String lodestoneDimension = tag.getString("LodestoneDimension");
            this.updateLodestoneTracker(trackedTag.asBoolean(), lodestonePosTag, lodestoneDimension, data);
        }
        if ((effectsTag = tag.getListTag("effects", CompoundTag.class)) != null) {
            this.updateEffects(effectsTag, data);
        }
        if ((instrument = tag.getString("instrument")) != null && (id = Instruments1_20_3.keyToId(instrument)) != -1) {
            data.set(StructuredDataKey.INSTRUMENT, Holder.of(id));
        }
        ListTag<CompoundTag> attributeModifiersTag = tag.getListTag("AttributeModifiers", CompoundTag.class);
        boolean bl = showAttributes = (hideFlagsValue & 2) == 0;
        if (attributeModifiersTag != null) {
            this.updateAttributes(data, attributeModifiersTag, showAttributes);
        } else if (!showAttributes) {
            data.set(StructuredDataKey.ATTRIBUTE_MODIFIERS1_20_5, new AttributeModifiers1_20_5(new AttributeModifiers1_20_5.AttributeModifier[0], false));
        }
        CompoundTag fireworksTag = tag.getCompoundTag("Fireworks");
        if (fireworksTag != null) {
            ListTag<CompoundTag> explosionsTag = fireworksTag.getListTag("Explosions", CompoundTag.class);
            this.updateFireworks(data, fireworksTag, explosionsTag);
        }
        if (old.identifier() == 1085) {
            this.updateWritableBookPages(data, tag);
        } else if (old.identifier() == 1086) {
            this.updateWrittenBookPages(connection, data, tag);
        }
        this.updatePotionTags(data, tag);
        this.updateMobTags(data, tag);
        this.updateItemList(connection, data, tag, "ChargedProjectiles", StructuredDataKey.CHARGED_PROJECTILES1_20_5, false);
        if (old.identifier() == 927) {
            this.updateItemList(connection, data, tag, "Items", StructuredDataKey.BUNDLE_CONTENTS1_20_5, false);
        }
        this.updateEnchantments(data, tag, "Enchantments", StructuredDataKey.ENCHANTMENTS, (hideFlagsValue & 1) == 0);
        this.updateEnchantments(data, tag, "StoredEnchantments", StructuredDataKey.STORED_ENCHANTMENTS, (hideFlagsValue & 0x20) == 0);
        NumberTag mapId = tag.getNumberTag("map");
        if (mapId != null) {
            data.set(StructuredDataKey.MAP_ID, mapId.asInt());
        }
        if ((decorationsTag = tag.getListTag("Decorations", CompoundTag.class)) != null) {
            this.updateMapDecorations(data, decorationsTag);
        }
        this.updateProfile(data, tag.get("SkullOwner"));
        CompoundTag customCreativeLock = tag.getCompoundTag("CustomCreativeLock");
        if (customCreativeLock != null) {
            data.set(StructuredDataKey.CREATIVE_SLOT_LOCK);
        }
        if ((canPlaceOnTag = tag.getListTag("CanPlaceOn", StringTag.class)) != null) {
            data.set(StructuredDataKey.CAN_PLACE_ON, this.updateBlockPredicates(canPlaceOnTag, (hideFlagsValue & 0x10) == 0));
        }
        if ((canDestroyTag = tag.getListTag("CanDestroy", StringTag.class)) != null) {
            data.set(StructuredDataKey.CAN_BREAK, this.updateBlockPredicates(canDestroyTag, (hideFlagsValue & 8) == 0));
        }
        if ((mapScaleDirectionTag = tag.getIntTag("map_scale_direction")) != null) {
            data.set(StructuredDataKey.MAP_POST_PROCESSING, 1);
        } else {
            NumberTag mapToLockTag = tag.getNumberTag("map_to_lock");
            if (mapToLockTag != null) {
                data.set(StructuredDataKey.MAP_POST_PROCESSING, 0);
            }
        }
        CompoundTag backupTag = StructuredDataConverter.removeBackupTag(tag);
        if (backupTag != null) {
            this.restoreFromBackupTag(backupTag, data);
        }
        return item;
    }

    void appendItemDataFixComponents(UserConnection connection, Item item) {
        ProtocolVersion serverVersion = connection.getProtocolInfo().serverProtocolVersion();
        if (serverVersion.olderThanOrEqualTo(ProtocolVersion.v1_17_1) && item.identifier() == 1182) {
            item.dataContainer().set(StructuredDataKey.MAX_DAMAGE, 326);
        }
        if (serverVersion.olderThanOrEqualTo(ProtocolVersion.v1_8) && (item.identifier() == 814 || item.identifier() == 819 || item.identifier() == 824 || item.identifier() == 829 || item.identifier() == 834)) {
            item.dataContainer().set(StructuredDataKey.FOOD1_20_5, new FoodProperties(0, 0.0f, true, 3600.0f, null, new FoodEffect[0]));
        }
    }

    int unmappedItemId(String name) {
        return ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getFullItemMappings().id(name);
    }

    int toMappedItemId(String name) {
        int unmappedId = this.unmappedItemId(name);
        return unmappedId != -1 ? ((Protocol1_20_3To1_20_5)this.protocol).getMappingData().getNewItemId(unmappedId) : -1;
    }

    void restoreFromBackupTag(CompoundTag backupTag, StructuredDataContainer data) {
        ListTag<CompoundTag> bannerPatterns;
        IntTag ominousBottleAmplifier;
        CompoundTag tool;
        CompoundTag food;
        IntTag rarity;
        IntTag maxDamage;
        IntTag maxStackSize;
        Tag intangibleProjectile;
        ByteTag enchantmentGlintOverride;
        IntArrayTag potDecorationsTag;
        CompoundTag instrument = backupTag.getCompoundTag("instrument");
        if (instrument != null) {
            this.restoreInstrumentFromBackup(instrument, data);
        }
        if ((potDecorationsTag = backupTag.getIntArrayTag("pot_decorations")) != null && potDecorationsTag.getValue().length == 4) {
            data.set(StructuredDataKey.POT_DECORATIONS, new PotDecorations(potDecorationsTag.getValue()));
        }
        if ((enchantmentGlintOverride = backupTag.getByteTag("enchantment_glint_override")) != null) {
            data.set(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE, enchantmentGlintOverride.asBoolean());
        }
        if (backupTag.contains("hide_tooltip")) {
            data.set(StructuredDataKey.HIDE_TOOLTIP);
        }
        if ((intangibleProjectile = backupTag.get("intangible_projectile")) != null) {
            data.set(StructuredDataKey.INTANGIBLE_PROJECTILE, intangibleProjectile);
        }
        if ((maxStackSize = backupTag.getIntTag("max_stack_size")) != null) {
            data.set(StructuredDataKey.MAX_STACK_SIZE, MathUtil.clamp(maxStackSize.asInt(), 1, 99));
        }
        if ((maxDamage = backupTag.getIntTag("max_damage")) != null) {
            data.set(StructuredDataKey.MAX_DAMAGE, Math.max(maxDamage.asInt(), 1));
        }
        if ((rarity = backupTag.getIntTag("rarity")) != null) {
            data.set(StructuredDataKey.RARITY, rarity.asInt());
        }
        if ((food = backupTag.getCompoundTag("food")) != null) {
            this.restoreFoodFromBackup(food, data);
        }
        if (backupTag.contains("fire_resistant")) {
            data.set(StructuredDataKey.FIRE_RESISTANT);
        }
        if ((tool = backupTag.getCompoundTag("tool")) != null) {
            this.restoreToolFromBackup(tool, data);
        }
        if ((ominousBottleAmplifier = backupTag.getIntTag("ominous_bottle_amplifier")) != null) {
            data.set(StructuredDataKey.OMINOUS_BOTTLE_AMPLIFIER, MathUtil.clamp(ominousBottleAmplifier.asInt(), 0, 4));
        }
        if ((bannerPatterns = backupTag.getListTag("banner_patterns", CompoundTag.class)) != null) {
            this.restoreBannerPatternsFromBackup(bannerPatterns, data);
        }
    }

    void restoreInstrumentFromBackup(CompoundTag instrument, StructuredDataContainer data) {
        Holder<Object> soundEvent;
        int useDuration = instrument.getInt("use_duration");
        float range = instrument.getFloat("range");
        CompoundTag soundEventTag = instrument.getCompoundTag("sound_event");
        if (soundEventTag != null) {
            StringTag identifier = soundEventTag.getStringTag("identifier");
            if (identifier == null) {
                return;
            }
            soundEvent = Holder.of(new SoundEvent(identifier.getValue(), soundEventTag.contains("fixed_range") ? Float.valueOf(soundEventTag.getFloat("fixed_range")) : null));
        } else {
            soundEvent = Holder.of(instrument.getInt("sound_event"));
        }
        data.set(StructuredDataKey.INSTRUMENT, Holder.of(new Instrument(soundEvent, useDuration, range)));
    }

    void restoreFoodFromBackup(CompoundTag food, StructuredDataContainer data) {
        int nutrition = food.getInt("nutrition");
        float saturation = food.getFloat("saturation");
        boolean canAlwaysEat = food.getBoolean("can_always_eat");
        float eatSeconds = food.getFloat("eat_seconds");
        ListTag<CompoundTag> possibleEffectsTag = food.getListTag("possible_effects", CompoundTag.class);
        if (possibleEffectsTag == null) {
            return;
        }
        ArrayList<FoodEffect> possibleEffects = new ArrayList<FoodEffect>();
        for (CompoundTag effect : possibleEffectsTag) {
            CompoundTag potionEffectTag = effect.getCompoundTag("effect");
            if (potionEffectTag == null) continue;
            possibleEffects.add(new FoodEffect(new PotionEffect(potionEffectTag.getInt("effect"), this.readPotionEffectData(potionEffectTag)), effect.getFloat("probability")));
        }
        data.set(StructuredDataKey.FOOD1_20_5, new FoodProperties(nutrition, saturation, canAlwaysEat, eatSeconds, null, possibleEffects.toArray(new FoodEffect[0])));
    }

    void restoreToolFromBackup(CompoundTag tool, StructuredDataContainer data) {
        ListTag<CompoundTag> rulesTag = tool.getListTag("rules", CompoundTag.class);
        if (rulesTag == null) {
            return;
        }
        ArrayList<ToolRule> rules = new ArrayList<ToolRule>();
        for (CompoundTag tag : rulesTag) {
            HolderSet blocks = null;
            Tag tag2 = tag.get("blocks");
            if (tag2 instanceof StringTag) {
                StringTag blocksTag = (StringTag)tag2;
                blocks = HolderSet.of(blocksTag.getValue());
            } else {
                IntArrayTag blockIds = tag.getIntArrayTag("blocks");
                if (blockIds != null) {
                    blocks = HolderSet.of(blockIds.getValue());
                }
            }
            if (blocks == null) continue;
            rules.add(new ToolRule(blocks, tag.contains("speed") ? Float.valueOf(tag.getFloat("speed")) : null, tag.contains("correct_for_drops") ? Boolean.valueOf(tag.getBoolean("correct_for_drops")) : null));
        }
        data.set(StructuredDataKey.TOOL, new ToolProperties(rules.toArray(new ToolRule[0]), tool.getFloat("default_mining_speed"), tool.getInt("damage_per_block")));
    }

    void restoreBannerPatternsFromBackup(ListTag<CompoundTag> bannerPatterns, StructuredDataContainer data) {
        ArrayList<BannerPatternLayer> patternLayer = new ArrayList<BannerPatternLayer>();
        for (CompoundTag tag : bannerPatterns) {
            Holder<Object> pattern;
            CompoundTag patternTag = tag.getCompoundTag("pattern");
            if (patternTag != null) {
                String assetId = patternTag.getString("asset_id");
                String translationKey = patternTag.getString("translation_key");
                pattern = Holder.of(new BannerPattern(assetId, translationKey));
            } else {
                pattern = Holder.of(tag.getInt("pattern"));
            }
            int dyeColor = tag.getInt("dye_color");
            patternLayer.add(new BannerPatternLayer(pattern, dyeColor));
        }
        data.set(StructuredDataKey.BANNER_PATTERNS, patternLayer.toArray(new BannerPatternLayer[0]));
    }

    AdventureModePredicate updateBlockPredicates(ListTag<StringTag> tag, boolean showInTooltip) {
        BlockPredicate[] predicates = (BlockPredicate[])tag.stream().map(StringTag::getValue).map(this::deserializeBlockPredicate).filter(Objects::nonNull).toArray(BlockPredicate[]::new);
        return new AdventureModePredicate(predicates, showInTooltip);
    }

    @Nullable BlockPredicate deserializeBlockPredicate(String rawPredicate) {
        CompoundTag tag;
        ArrayList<StatePropertyMatcher> propertyMatchers;
        HolderSet holders;
        block10: {
            String identifier;
            int propertiesStartIndex = rawPredicate.indexOf(91);
            int tagStartIndex = rawPredicate.indexOf(123);
            int idLength = rawPredicate.length();
            if (propertiesStartIndex != -1) {
                idLength = propertiesStartIndex;
            }
            if (tagStartIndex != -1) {
                idLength = Math.min(propertiesStartIndex, tagStartIndex);
            }
            if (!(identifier = rawPredicate.substring(0, idLength)).startsWith("#")) {
                int id = Protocol1_20_3To1_20_5.MAPPINGS.blockId(identifier);
                if (id == -1) {
                    return null;
                }
                holders = HolderSet.of(new int[]{id});
            } else {
                holders = HolderSet.of(identifier.substring(1));
            }
            int propertiesEndIndex = rawPredicate.indexOf(93);
            propertyMatchers = new ArrayList<StatePropertyMatcher>();
            if (propertiesStartIndex != -1 && propertiesEndIndex != -1) {
                for (String property : rawPredicate.substring(propertiesStartIndex + 1, propertiesEndIndex).split(",")) {
                    int propertySplitIndex = property.indexOf(61);
                    if (propertySplitIndex == -1) continue;
                    String propertyId = property.substring(0, propertySplitIndex).trim();
                    String propertyValue = property.substring(propertySplitIndex + 1).trim();
                    propertyMatchers.add(new StatePropertyMatcher(propertyId, Either.left(propertyValue)));
                }
            }
            int tagEndIndex = rawPredicate.indexOf(125);
            tag = null;
            if (tagStartIndex != -1 && tagEndIndex != -1) {
                try {
                    tag = (CompoundTag)SerializerVersion.V1_20_3.toTag(rawPredicate.substring(tagStartIndex, tagEndIndex + 1));
                }
                catch (Exception e) {
                    if (!Via.getManager().isDebug()) break block10;
                    String string = rawPredicate.substring(tagStartIndex, tagEndIndex + 1);
                    Protocol1_20_3To1_20_5.LOGGER.log(Level.SEVERE, "Failed to parse block predicate tag: " + string, e);
                }
            }
        }
        return new BlockPredicate(holders, propertyMatchers.isEmpty() ? null : propertyMatchers.toArray(EMPTY_PROPERTY_MATCHERS), tag);
    }

    void updateAttributes(StructuredDataContainer data, ListTag<CompoundTag> attributeModifiersTag, boolean showInTooltip) {
        ArrayList<AttributeModifiers1_20_5.AttributeModifier> modifiers = new ArrayList<AttributeModifiers1_20_5.AttributeModifier>();
        for (int i = 0; i < attributeModifiersTag.size(); ++i) {
            int attributeId;
            int operationId;
            int slotTypeId;
            CompoundTag modifierTag = attributeModifiersTag.get(i);
            String attributeName = modifierTag.getString("AttributeName");
            String name = modifierTag.getString("Name");
            NumberTag amountTag = modifierTag.getNumberTag("Amount");
            IntArrayTag uuidTag = modifierTag.getIntArrayTag("UUID");
            String slotType = modifierTag.getString("Slot", "any");
            if (name == null || attributeName == null || amountTag == null || uuidTag == null || (slotTypeId = EquipmentSlots1_20_5.keyToId(slotType)) == -1 || (operationId = modifierTag.getInt("Operation")) < 0 || operationId > 2 || (attributeId = Attributes1_20_5.keyToId(attributeName)) == -1) continue;
            modifiers.add(new AttributeModifiers1_20_5.AttributeModifier(attributeId, new AttributeModifiers1_20_5.ModifierData(UUIDUtil.fromIntArray(uuidTag.getValue()), name, amountTag.asDouble(), operationId), slotTypeId));
        }
        data.set(StructuredDataKey.ATTRIBUTE_MODIFIERS1_20_5, new AttributeModifiers1_20_5(modifiers.toArray(new AttributeModifiers1_20_5.AttributeModifier[0]), showInTooltip));
    }

    PotionEffectData readPotionEffectData(CompoundTag tag) {
        byte amplifier = tag.getByte("amplifier");
        int duration = tag.getInt("duration");
        boolean ambient = tag.getBoolean("ambient");
        boolean showParticles = tag.getBoolean("show_particles");
        boolean showIcon = tag.getBoolean("show_icon");
        PotionEffectData hiddenEffect = null;
        CompoundTag hiddenEffectTag = tag.getCompoundTag("hidden_effect");
        if (hiddenEffectTag != null) {
            hiddenEffect = this.readPotionEffectData(hiddenEffectTag);
        }
        return new PotionEffectData(amplifier, duration, ambient, showParticles, showIcon, hiddenEffect);
    }

    void updatePotionTags(StructuredDataContainer data, CompoundTag tag) {
        int id;
        String potion = tag.getString("Potion");
        Integer potionId = null;
        if (potion != null && (id = Potions1_20_5.keyToId(potion)) != -1) {
            potionId = id;
        }
        NumberTag customPotionColorTag = tag.getNumberTag("CustomPotionColor");
        ListTag<CompoundTag> customPotionEffectsTag = tag.getListTag("custom_potion_effects", CompoundTag.class);
        PotionEffect[] potionEffects = null;
        if (customPotionEffectsTag != null) {
            potionEffects = (PotionEffect[])customPotionEffectsTag.stream().map(effectTag -> {
                String identifier = effectTag.getString("id");
                if (identifier == null) {
                    return null;
                }
                int id = PotionEffects1_20_5.keyToId(identifier);
                if (id == -1) {
                    return null;
                }
                return new PotionEffect(id, this.readPotionEffectData((CompoundTag)effectTag));
            }).filter(Objects::nonNull).toArray(PotionEffect[]::new);
        }
        if (potionId != null || customPotionColorTag != null || potionEffects != null) {
            data.set(StructuredDataKey.POTION_CONTENTS, new PotionContents(potionId, customPotionColorTag != null ? Integer.valueOf(customPotionColorTag.asInt()) : null, potionEffects != null ? potionEffects : new PotionEffect[]{}));
        }
    }

    void updateArmorTrim(UserConnection connection, StructuredDataContainer data, CompoundTag trimTag, boolean showInTooltip) {
        Holder<Object> patternHolder;
        Holder<Object> materialHolder;
        Tag materialTag = trimTag.get("material");
        ArmorTrimStorage trimStorage = connection.get(ArmorTrimStorage.class);
        if (materialTag instanceof StringTag) {
            StringTag materialStringTag = (StringTag)materialTag;
            int id = trimStorage.trimMaterials().keyToId(materialStringTag.getValue());
            if (id == -1) {
                return;
            }
            materialHolder = Holder.of(id);
        } else if (materialTag instanceof CompoundTag) {
            CompoundTag materialCompoundTag = (CompoundTag)materialTag;
            StringTag assetNameTag = materialCompoundTag.getStringTag("asset_name");
            StringTag ingredientTag = materialCompoundTag.getStringTag("ingredient");
            if (assetNameTag == null || ingredientTag == null) {
                return;
            }
            int ingredientId = StructuredDataConverter.removeItemBackupTag(materialCompoundTag, this.toMappedItemId(ingredientTag.getValue()));
            if (ingredientId == -1) {
                return;
            }
            NumberTag itemModelIndexTag = materialCompoundTag.getNumberTag("item_model_index");
            CompoundTag overrideArmorMaterialsTag = materialCompoundTag.getCompoundTag("override_armor_materials");
            Tag descriptionTag = materialCompoundTag.get("description");
            Int2ObjectOpenHashMap<String> overrideArmorMaterials = new Int2ObjectOpenHashMap<String>();
            if (overrideArmorMaterialsTag != null) {
                for (Map.Entry<String, Tag> entry : overrideArmorMaterialsTag.entrySet()) {
                    Tag tag = entry.getValue();
                    if (!(tag instanceof StringTag)) continue;
                    StringTag valueTag = (StringTag)tag;
                    try {
                        int id = Integer.parseInt(entry.getKey());
                        overrideArmorMaterials.put(id, valueTag.getValue());
                    }
                    catch (NumberFormatException numberFormatException) {}
                }
            }
            materialHolder = Holder.of(new ArmorTrimMaterial(assetNameTag.getValue(), ingredientId, itemModelIndexTag != null ? itemModelIndexTag.asFloat() : 0.0f, overrideArmorMaterials, descriptionTag));
        } else {
            return;
        }
        Tag patternTag = trimTag.get("pattern");
        if (patternTag instanceof StringTag) {
            StringTag patternStringTag = (StringTag)patternTag;
            int id = trimStorage.trimPatterns().keyToId(patternStringTag.getValue());
            if (id == -1) {
                return;
            }
            patternHolder = Holder.of(id);
        } else if (patternTag instanceof CompoundTag) {
            CompoundTag patternCompoundTag = (CompoundTag)patternTag;
            String assetId = patternCompoundTag.getString("assetId");
            String templateItem = patternCompoundTag.getString("templateItem");
            if (assetId == null || templateItem == null) {
                return;
            }
            int templateItemId = StructuredDataConverter.removeItemBackupTag(patternCompoundTag, this.toMappedItemId(templateItem));
            if (templateItemId == -1) {
                return;
            }
            Tag descriptionTag = patternCompoundTag.get("description");
            boolean decal = patternCompoundTag.getBoolean("decal");
            patternHolder = Holder.of(new ArmorTrimPattern(assetId, templateItemId, descriptionTag, decal));
        } else {
            return;
        }
        data.set(StructuredDataKey.TRIM, new ArmorTrim(materialHolder, patternHolder, showInTooltip));
    }

    void updateMobTags(StructuredDataContainer data, CompoundTag tag) {
        CompoundTag bucketEntityData = new CompoundTag();
        for (String mobTagKey : MOB_TAGS) {
            Tag mobTag = tag.get(mobTagKey);
            if (mobTag == null) continue;
            bucketEntityData.put(mobTagKey, mobTag);
        }
        if (!bucketEntityData.isEmpty()) {
            data.set(StructuredDataKey.BUCKET_ENTITY_DATA, bucketEntityData);
        }
    }

    void updateBlockState(StructuredDataContainer data, CompoundTag blockState) {
        HashMap<String, String> properties = new HashMap<String, String>();
        for (Map.Entry<String, Tag> entry : blockState.entrySet()) {
            Tag value = entry.getValue();
            if (value instanceof StringTag) {
                StringTag valueStringTag = (StringTag)value;
                properties.put(entry.getKey(), valueStringTag.getValue());
                continue;
            }
            if (!(value instanceof IntTag)) continue;
            IntTag valueIntTag = (IntTag)value;
            properties.put(entry.getKey(), Integer.toString(valueIntTag.asInt()));
        }
        data.set(StructuredDataKey.BLOCK_STATE, new BlockStateProperties(properties));
    }

    void updateFireworks(StructuredDataContainer data, CompoundTag fireworksTag, ListTag<CompoundTag> explosionsTag) {
        byte flightDuration = fireworksTag.getByte("Flight");
        Fireworks fireworks = new Fireworks(flightDuration, explosionsTag != null ? (FireworkExplosion[])explosionsTag.stream().limit(256L).map(this::readExplosion).toArray(FireworkExplosion[]::new) : new FireworkExplosion[]{});
        data.set(StructuredDataKey.FIREWORKS, fireworks);
    }

    void updateEffects(ListTag<CompoundTag> effects, StructuredDataContainer data) {
        SuspiciousStewEffect[] suspiciousStewEffects = new SuspiciousStewEffect[effects.size()];
        for (int i = 0; i < effects.size(); ++i) {
            SuspiciousStewEffect stewEffect;
            CompoundTag effect = effects.get(i);
            String effectIdString = effect.getString("id", "luck");
            int duration = effect.getInt("duration");
            int effectId = PotionEffects1_20_5.keyToId(effectIdString);
            if (effectId == -1) continue;
            suspiciousStewEffects[i] = stewEffect = new SuspiciousStewEffect(effectId, duration);
        }
        data.set(StructuredDataKey.SUSPICIOUS_STEW_EFFECTS, suspiciousStewEffects);
    }

    void updateLodestoneTracker(boolean tracked, CompoundTag lodestonePosTag, String lodestoneDimension, StructuredDataContainer data) {
        GlobalBlockPosition position = null;
        if (lodestonePosTag != null && lodestoneDimension != null) {
            int x = lodestonePosTag.getInt("X");
            int y = lodestonePosTag.getInt("Y");
            int z = lodestonePosTag.getInt("Z");
            position = new GlobalBlockPosition(lodestoneDimension, x, y, z);
        }
        data.set(StructuredDataKey.LODESTONE_TRACKER, new LodestoneTracker(position, tracked));
    }

    FireworkExplosion readExplosion(CompoundTag tag) {
        int shape = tag.getInt("Type");
        IntArrayTag colors = tag.getIntArrayTag("Colors");
        IntArrayTag fadeColors = tag.getIntArrayTag("FadeColors");
        boolean trail = tag.getBoolean("Trail");
        boolean flicker = tag.getBoolean("Flicker");
        return new FireworkExplosion(shape, colors != null ? colors.getValue() : new int[]{}, fadeColors != null ? fadeColors.getValue() : new int[]{}, trail, flicker);
    }

    void updateWritableBookPages(StructuredDataContainer data, CompoundTag tag) {
        ListTag<StringTag> pagesTag = tag.getListTag("pages", StringTag.class);
        CompoundTag filteredPagesTag = tag.getCompoundTag("filtered_pages");
        if (pagesTag == null) {
            return;
        }
        ArrayList<FilterableString> pages = new ArrayList<FilterableString>();
        for (int i = 0; i < pagesTag.size(); ++i) {
            StringTag filteredPage;
            StringTag page = pagesTag.get(i);
            String filtered = null;
            if (filteredPagesTag != null && (filteredPage = filteredPagesTag.getStringTag(String.valueOf(i))) != null) {
                filtered = this.limit(filteredPage.getValue(), 1024);
            }
            pages.add(new FilterableString(this.limit(page.getValue(), 1024), filtered));
            if (pages.size() == 100) break;
        }
        data.set(StructuredDataKey.WRITABLE_BOOK_CONTENT, pages.toArray(new FilterableString[0]));
    }

    void updateWrittenBookPages(UserConnection connection, StructuredDataContainer data, CompoundTag tag) {
        boolean valid;
        String title2 = tag.getString("title");
        String author = tag.getString("author");
        ListTag<StringTag> pagesTag = tag.getListTag("pages", StringTag.class);
        boolean bl = valid = author != null && title2 != null && title2.length() <= 32 && pagesTag != null;
        if (valid) {
            for (StringTag page : pagesTag) {
                if (page.getValue().length() <= Short.MAX_VALUE) continue;
                valid = false;
                break;
            }
        }
        ArrayList<FilterableComponent> pages = new ArrayList<FilterableComponent>();
        if (valid) {
            CompoundTag filteredPagesTag = tag.getCompoundTag("filtered_pages");
            for (int i = 0; i < pagesTag.size(); ++i) {
                Tag parsedPage;
                StringTag filteredPage;
                StringTag page = pagesTag.get(i);
                Tag filtered = null;
                if (filteredPagesTag != null && (filteredPage = filteredPagesTag.getStringTag(String.valueOf(i))) != null) {
                    try {
                        filtered = this.jsonToTag(connection, filteredPage);
                    }
                    catch (Exception e) {
                        continue;
                    }
                }
                try {
                    parsedPage = this.jsonToTag(connection, page);
                }
                catch (Exception e) {
                    continue;
                }
                pages.add(new FilterableComponent(parsedPage, filtered));
            }
        } else {
            CompoundTag invalidPage = new CompoundTag();
            invalidPage.putString("text", "* Invalid book tag *");
            invalidPage.putString("color", "#AA0000");
            pages.add(new FilterableComponent(invalidPage, null));
        }
        String filteredTitle = tag.getString("filtered_title");
        int generation = tag.getInt("generation");
        boolean resolved = tag.getBoolean("resolved");
        WrittenBook writtenBook = new WrittenBook(new FilterableString(this.limit(title2 == null ? "" : title2, 32), this.limit(filteredTitle, 32)), author == null ? "" : author, MathUtil.clamp(generation, 0, 3), pages.toArray(new FilterableComponent[0]), resolved);
        data.set(StructuredDataKey.WRITTEN_BOOK_CONTENT, writtenBook);
    }

    Tag jsonToTag(UserConnection connection, StringTag stringTag) {
        Tag tag = ComponentUtil.jsonStringToTag(stringTag.getValue(), SerializerVersion.V1_20_3, SerializerVersion.V1_20_3);
        ((ComponentRewriter)((Protocol1_20_3To1_20_5)this.protocol).getComponentRewriter()).processTag(connection, tag);
        return tag;
    }

    void updateItemList(UserConnection connection, StructuredDataContainer data, CompoundTag tag, String key, StructuredDataKey<Item[]> dataKey, boolean allowEmpty) {
        ListTag<CompoundTag> itemsTag = tag.getListTag(key, CompoundTag.class);
        if (itemsTag != null) {
            Item[] items = (Item[])itemsTag.stream().limit(256L).map(item -> this.itemFromTag(connection, (CompoundTag)item)).filter(item -> allowEmpty || !item.isEmpty()).toArray(Item[]::new);
            data.set(dataKey, items);
        }
    }

    Item itemFromTag(UserConnection connection, CompoundTag item) {
        String id = item.getString("id");
        if (id == null) {
            return StructuredItem.empty();
        }
        int itemId = StructuredDataConverter.removeItemBackupTag(item, this.unmappedItemId(id));
        if (itemId == -1) {
            return StructuredItem.empty();
        }
        byte count = item.getByte("Count", (byte)1);
        CompoundTag tag = item.getCompoundTag("tag");
        return this.handleItemToClient(connection, new DataItem(itemId, count, tag));
    }

    void updateEnchantments(StructuredDataContainer data, CompoundTag tag, String key, StructuredDataKey<Enchantments> newKey, boolean show) {
        ListTag<CompoundTag> enchantmentsTag = tag.getListTag(key, CompoundTag.class);
        if (enchantmentsTag == null) {
            return;
        }
        Enchantments enchantments = new Enchantments(new Int2IntOpenHashMap(), show);
        for (CompoundTag enchantment : enchantmentsTag) {
            int intId;
            String id = enchantment.getString("id");
            NumberTag lvl = enchantment.getNumberTag("lvl");
            if (id == null || lvl == null) continue;
            if (Key.stripMinecraftNamespace(id).equals("sweeping")) {
                id = Key.namespaced("sweeping_edge");
            }
            if ((intId = Enchantments1_20_5.keyToId(id)) == -1) continue;
            enchantments.enchantments().put(intId, MathUtil.clamp(lvl.asInt(), 0, 255));
        }
        data.set(newKey, enchantments);
        if (!enchantmentsTag.isEmpty() && enchantments.size() == 0) {
            data.set(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE, true);
        }
    }

    void updateProfile(StructuredDataContainer data, Tag skullOwnerTag) {
        if (skullOwnerTag instanceof StringTag) {
            StringTag nameTag = (StringTag)skullOwnerTag;
            String name = nameTag.getValue();
            if (this.isValidName(name)) {
                data.set(StructuredDataKey.PROFILE, new GameProfile(name, null, EMPTY_PROPERTIES));
            }
        } else if (skullOwnerTag instanceof CompoundTag) {
            CompoundTag skullOwner = (CompoundTag)skullOwnerTag;
            String name = skullOwner.getString("Name", "");
            if (!this.isValidName(name)) {
                name = null;
            }
            IntArrayTag idTag = skullOwner.getIntArrayTag("Id");
            UUID uuid = null;
            if (idTag != null) {
                uuid = UUIDUtil.fromIntArray(idTag.getValue());
            }
            ArrayList<GameProfile.Property> properties = new ArrayList<GameProfile.Property>(1);
            CompoundTag propertiesTag = skullOwner.getCompoundTag("Properties");
            if (propertiesTag != null) {
                this.updateProperties(propertiesTag, properties);
            }
            data.set(StructuredDataKey.PROFILE, new GameProfile(name, uuid, properties.toArray(EMPTY_PROPERTIES)));
        }
    }

    @Nullable String limit(@Nullable String s, int length) {
        if (s == null) {
            return null;
        }
        return s.length() > length ? s.substring(0, length) : s;
    }

    void updateBees(StructuredDataContainer data, ListTag<CompoundTag> beesTag) {
        Bee[] bees = (Bee[])beesTag.stream().map(bee -> {
            CompoundTag entityData = bee.getCompoundTag("EntityData");
            if (entityData == null) {
                return null;
            }
            int ticksInHive = bee.getInt("TicksInHive");
            int minOccupationTicks = bee.getInt("MinOccupationTicks");
            return new Bee(entityData, ticksInHive, minOccupationTicks);
        }).filter(Objects::nonNull).toArray(Bee[]::new);
        data.set(StructuredDataKey.BEES, bees);
    }

    void updateProperties(CompoundTag propertiesTag, List<GameProfile.Property> properties) {
        for (Map.Entry<String, Tag> entry : propertiesTag.entrySet()) {
            Tag tag = entry.getValue();
            if (!(tag instanceof ListTag)) continue;
            ListTag listTag = (ListTag)tag;
            for (Tag propertyTag : listTag) {
                if (!(propertyTag instanceof CompoundTag)) continue;
                CompoundTag compoundTag = (CompoundTag)propertyTag;
                String value = compoundTag.getString("Value", "");
                String signature = compoundTag.getString("Signature");
                properties.add(new GameProfile.Property(this.limit(entry.getKey(), 64), value, this.limit(signature, 1024)));
                if (properties.size() != 16) continue;
                return;
            }
        }
    }

    void updateMapDecorations(StructuredDataContainer data, ListTag<CompoundTag> decorationsTag) {
        CompoundTag updatedDecorationsTag = new CompoundTag();
        for (CompoundTag decorationTag : decorationsTag) {
            String id = decorationTag.getString("id", "");
            int type = decorationTag.getInt("type");
            double x = decorationTag.getDouble("x");
            double z = decorationTag.getDouble("z");
            float rotation = decorationTag.getFloat("rot");
            CompoundTag updatedDecorationTag = new CompoundTag();
            updatedDecorationTag.putString("type", MapDecorations1_20_5.idToKey(type));
            updatedDecorationTag.putDouble("x", x);
            updatedDecorationTag.putDouble("z", z);
            updatedDecorationTag.putFloat("rotation", rotation);
            updatedDecorationsTag.put(id, updatedDecorationTag);
        }
        data.set(StructuredDataKey.MAP_DECORATIONS, updatedDecorationsTag);
    }

    void updateDisplay(UserConnection connection, StructuredDataContainer data, CompoundTag displayTag, int hideFlags) {
        NumberTag colorTag;
        ListTag<StringTag> loreTag;
        StringTag nameTag;
        if (displayTag == null) {
            return;
        }
        NumberTag mapColorTag = displayTag.getNumberTag("MapColor");
        if (mapColorTag != null) {
            data.set(StructuredDataKey.MAP_COLOR, mapColorTag.asInt());
        }
        if ((nameTag = displayTag.getStringTag("Name")) != null) {
            try {
                Tag convertedName = this.jsonToTag(connection, nameTag);
                data.set(StructuredDataKey.CUSTOM_NAME, convertedName);
            }
            catch (Exception convertedName) {
                // empty catch block
            }
        }
        if ((loreTag = displayTag.getListTag("Lore", StringTag.class)) != null) {
            try {
                data.set(StructuredDataKey.LORE, (Tag[])loreTag.stream().limit(256L).map(t -> this.jsonToTag(connection, (StringTag)t)).toArray(Tag[]::new));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if ((colorTag = displayTag.getNumberTag("color")) != null) {
            data.set(StructuredDataKey.DYED_COLOR, new DyedColor(colorTag.asInt(), (hideFlags & 0x40) == 0));
        }
    }

    void addBlockEntityId(CompoundTag tag, String id) {
        if (!tag.contains("id")) {
            tag.putString("id", id);
        }
    }

    boolean isUnknownBlockEntity(int id) {
        return id < 0 || id > 42;
    }

    void updateBlockEntityTag(UserConnection connection, @Nullable StructuredDataContainer data, CompoundTag tag) {
        Tag skullOwnerTag;
        if (tag == null) {
            return;
        }
        if (data != null) {
            Tag baseColorTag;
            StringTag lootTableTag;
            StringTag noteBlockSoundTag;
            ListTag<StringTag> sherdsTag;
            ListTag<CompoundTag> beesTag;
            StringTag lockTag = tag.getStringTag("Lock");
            if (lockTag != null) {
                data.set(StructuredDataKey.LOCK, lockTag);
            }
            if ((beesTag = tag.getListTag("Bees", CompoundTag.class)) != null) {
                this.updateBees(data, beesTag);
                this.addBlockEntityId(tag, "beehive");
            }
            if ((sherdsTag = tag.getListTag("sherds", StringTag.class)) != null && sherdsTag.size() == 4) {
                String backSherd = sherdsTag.get(0).getValue();
                String leftSherd = sherdsTag.get(1).getValue();
                String rightSherd = sherdsTag.get(2).getValue();
                String frontSherd = sherdsTag.get(3).getValue();
                data.set(StructuredDataKey.POT_DECORATIONS, new PotDecorations(this.toMappedItemId(backSherd), this.toMappedItemId(leftSherd), this.toMappedItemId(rightSherd), this.toMappedItemId(frontSherd)));
                this.addBlockEntityId(tag, "decorated_pot");
            }
            if ((noteBlockSoundTag = tag.getStringTag("note_block_sound")) != null) {
                data.set(StructuredDataKey.NOTE_BLOCK_SOUND, noteBlockSoundTag.getValue());
                this.addBlockEntityId(tag, "player_head");
            }
            if ((lootTableTag = tag.getStringTag("LootTable")) != null) {
                long lootTableSeed = tag.getLong("LootTableSeed");
                CompoundTag containerLoot = new CompoundTag();
                containerLoot.putString("loot_table", lootTableTag.getValue());
                containerLoot.putLong("loot_table_seed", lootTableSeed);
                data.set(StructuredDataKey.CONTAINER_LOOT, containerLoot);
            }
            if ((baseColorTag = tag.remove("Base")) instanceof NumberTag) {
                NumberTag baseColorIntTag = (NumberTag)baseColorTag;
                data.set(StructuredDataKey.BASE_COLOR, baseColorIntTag.asInt());
            }
            if (tag.contains("Items")) {
                this.updateItemList(connection, data, tag, "Items", StructuredDataKey.CONTAINER1_20_5, true);
                this.addBlockEntityId(tag, "shulker_box");
            }
        }
        if ((skullOwnerTag = tag.remove("SkullOwner")) instanceof StringTag) {
            StringTag nameTag = (StringTag)skullOwnerTag;
            CompoundTag profileTag = new CompoundTag();
            profileTag.putString("name", nameTag.getValue());
            tag.put("profile", profileTag);
        } else if (skullOwnerTag instanceof CompoundTag) {
            CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
            this.updateSkullOwnerTag(tag, skullOwnerCompoundTag);
        }
        ListTag<CompoundTag> patternsTag = tag.getListTag("Patterns", CompoundTag.class);
        if (patternsTag != null) {
            BannerPatternStorage patternStorage = connection.get(BannerPatternStorage.class);
            BannerPatternLayer[] layers = (BannerPatternLayer[])patternsTag.stream().map(patternTag -> {
                String pattern = patternTag.getString("Pattern", "");
                int color = patternTag.getInt("Color", -1);
                String fullPatternIdentifier = BannerPatterns1_20_5.compactToFullId(pattern);
                if (fullPatternIdentifier == null || color == -1) {
                    return null;
                }
                patternTag.remove("Pattern");
                patternTag.remove("Color");
                patternTag.putString("pattern", fullPatternIdentifier);
                patternTag.putString("color", DyeColors.colorById(color));
                int id = patternStorage != null ? patternStorage.bannerPatterns().keyToId(fullPatternIdentifier) : BannerPatterns1_20_5.keyToId(fullPatternIdentifier);
                return id != -1 ? new BannerPatternLayer(Holder.of(id), color) : null;
            }).filter(Objects::nonNull).toArray(BannerPatternLayer[]::new);
            tag.remove("Patterns");
            tag.put("patterns", patternsTag);
            this.addBlockEntityId(tag, "banner");
            if (data != null) {
                data.set(StructuredDataKey.BANNER_PATTERNS, layers);
            }
        }
        this.removeEmptyItem(tag, "item");
        this.removeEmptyItem(tag, "RecordItem");
        this.removeEmptyItem(tag, "Book");
    }

    void removeEmptyItem(CompoundTag tag, String key) {
        int id;
        CompoundTag itemTag = tag.getCompoundTag(key);
        if (itemTag != null && (id = itemTag.getInt("id")) == 0) {
            tag.remove(key);
        }
    }

    void updateSkullOwnerTag(CompoundTag tag, CompoundTag skullOwnerTag) {
        Tag tag2;
        IntArrayTag idTag;
        CompoundTag profileTag = new CompoundTag();
        tag.put("profile", profileTag);
        String name = skullOwnerTag.getString("Name");
        if (name != null && this.isValidName(name)) {
            profileTag.putString("name", name);
        }
        if ((idTag = skullOwnerTag.getIntArrayTag("Id")) != null) {
            profileTag.put("id", idTag);
        }
        if (!((tag2 = skullOwnerTag.remove("Properties")) instanceof CompoundTag)) {
            return;
        }
        CompoundTag propertiesTag = (CompoundTag)tag2;
        ListTag<CompoundTag> propertiesListTag = new ListTag<CompoundTag>(CompoundTag.class);
        for (Map.Entry<String, Tag> entry : propertiesTag.entrySet()) {
            Tag tag3 = entry.getValue();
            if (!(tag3 instanceof ListTag)) continue;
            ListTag entryValue = (ListTag)tag3;
            for (Tag propertyTag : entryValue) {
                if (!(propertyTag instanceof CompoundTag)) continue;
                CompoundTag propertyCompoundTag = (CompoundTag)propertyTag;
                CompoundTag updatedPropertyTag = new CompoundTag();
                String value = propertyCompoundTag.getString("Value", "");
                String signature = propertyCompoundTag.getString("Signature");
                updatedPropertyTag.putString("name", entry.getKey());
                updatedPropertyTag.putString("value", value);
                if (signature != null) {
                    updatedPropertyTag.putString("signature", signature);
                }
                propertiesListTag.add(updatedPropertyTag);
            }
        }
        profileTag.put("properties", propertiesListTag);
    }

    boolean isValidName(String name) {
        if (name.length() > 16) {
            return false;
        }
        int len = name.length();
        for (int i = 0; i < len; ++i) {
            char c = name.charAt(i);
            if (c >= '!' && c <= '~') continue;
            return false;
        }
        return true;
    }
}

