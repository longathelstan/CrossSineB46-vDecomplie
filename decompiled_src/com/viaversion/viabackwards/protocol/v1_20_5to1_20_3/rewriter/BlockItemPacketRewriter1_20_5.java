/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.BackwardsStructuredItemRewriter;
import com.viaversion.viabackwards.api.rewriters.StructuredEnchantmentRewriter;
import com.viaversion.viabackwards.protocol.v1_20_5to1_20_3.Protocol1_20_5To1_20_3;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Holder;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.SoundEvent;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.StructuredItem;
import com.viaversion.viaversion.api.minecraft.item.data.FireworkExplosion;
import com.viaversion.viaversion.api.minecraft.item.data.Fireworks;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPacket1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.packet.ServerboundPackets1_20_3;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter.RecipeRewriter1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.Protocol1_20_3To1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.BannerPatterns1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.rewriter.StructuredDataConverter;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.Key;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPacketRewriter1_20_5
extends BackwardsStructuredItemRewriter<ClientboundPacket1_20_5, ServerboundPacket1_20_3, Protocol1_20_5To1_20_3> {
    private static final StructuredDataConverter DATA_CONVERTER = new StructuredDataConverter(true);
    private final Protocol1_20_3To1_20_5 vvProtocol = Via.getManager().getProtocolManager().getProtocol(Protocol1_20_3To1_20_5.class);
    private final StructuredEnchantmentRewriter enchantmentRewriter = new StructuredEnchantmentRewriter(this);

    public BlockItemPacketRewriter1_20_5(Protocol1_20_5To1_20_3 protocol) {
        super(protocol, Types1_20_5.ITEM, Types1_20_5.ITEM_ARRAY, Types.ITEM1_20_2, Types.ITEM1_20_2_ARRAY);
        this.enchantmentRewriter.setRewriteIds(false);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_20_5> blockRewriter = BlockRewriter.for1_20_2(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_20_5.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_20_5.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_20_5.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_20_5.LEVEL_EVENT, 1010, 2001);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_20_5.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_20_2::new, (user, blockEntity) -> this.updateBlockEntityTag((UserConnection)user, blockEntity.tag()));
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            wrapper.passthrough(Types.VAR_INT);
            CompoundTag tag = wrapper.passthrough(Types.COMPOUND_TAG);
            this.updateBlockEntityTag(wrapper.user(), tag);
        });
        this.registerCooldown(ClientboundPackets1_20_5.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_20_5.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_20_5.CONTAINER_SET_SLOT);
        this.registerAdvancements1_20_3(ClientboundPackets1_20_5.UPDATE_ADVANCEMENTS);
        this.registerContainerClick1_17_1(ServerboundPackets1_20_3.CONTAINER_CLICK);
        this.registerContainerSetData(ClientboundPackets1_20_5.CONTAINER_SET_DATA);
        this.registerSetCreativeModeSlot(ServerboundPackets1_20_3.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_20_5To1_20_3)this.protocol).registerServerbound(ServerboundPackets1_20_3.CONTAINER_BUTTON_CLICK, wrapper -> {
            int containerId = wrapper.read(Types.BYTE) & 0xFF;
            int buttonId = wrapper.read(Types.BYTE) & 0xFF;
            wrapper.write(Types.VAR_INT, containerId);
            wrapper.write(Types.VAR_INT, buttonId);
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.LEVEL_PARTICLES, wrapper -> {
            wrapper.write(Types.VAR_INT, 0);
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            float data = wrapper.passthrough(Types.FLOAT).floatValue();
            wrapper.passthrough(Types.INT);
            Particle particle = wrapper.read(Types1_20_5.PARTICLE);
            this.rewriteParticle(wrapper.user(), particle);
            if (particle.id() == ((Protocol1_20_5To1_20_3)this.protocol).getMappingData().getParticleMappings().mappedId("entity_effect")) {
                int color = (Integer)particle.removeArgument(0).getValue();
                if (data == 0.0f) {
                    wrapper.set(Types.FLOAT, 3, Float.valueOf(color));
                }
            } else if (particle.id() == ((Protocol1_20_5To1_20_3)this.protocol).getMappingData().getParticleMappings().mappedId("dust_color_transition")) {
                particle.add(3, Types.FLOAT, (Float)particle.removeArgument(6).getValue());
            }
            wrapper.set(Types.VAR_INT, 0, particle.id());
            for (Particle.ParticleData<?> argument : particle.getArguments()) {
                argument.write(wrapper);
            }
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.EXPLODE, wrapper -> {
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
            Particle smallExplosionParticle = wrapper.passthroughAndMap(Types1_20_5.PARTICLE, Types1_20_3.PARTICLE);
            Particle largeExplosionParticle = wrapper.passthroughAndMap(Types1_20_5.PARTICLE, Types1_20_3.PARTICLE);
            this.rewriteParticle(wrapper.user(), smallExplosionParticle);
            this.rewriteParticle(wrapper.user(), largeExplosionParticle);
            Holder soundEventHolder = (Holder)((Object)wrapper.read(Types.SOUND_EVENT));
            if (soundEventHolder.isDirect()) {
                SoundEvent soundEvent = (SoundEvent)soundEventHolder.value();
                wrapper.write(Types.STRING, soundEvent.identifier());
                wrapper.write(Types.OPTIONAL_FLOAT, soundEvent.fixedRange());
            } else {
                int soundId = ((Protocol1_20_5To1_20_3)this.protocol).getMappingData().getSoundMappings().getNewId(soundEventHolder.id());
                String soundKey = Protocol1_20_3To1_20_5.MAPPINGS.soundName(soundId);
                wrapper.write(Types.STRING, soundKey != null ? soundKey : "minecraft:entity.generic.explode");
                wrapper.write(Types.OPTIONAL_FLOAT, null);
            }
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.MERCHANT_OFFERS, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                Item input = this.handleItemToClient(wrapper.user(), wrapper.read(Types1_20_5.ITEM_COST));
                this.cleanInput(input);
                wrapper.write(Types.ITEM1_20_2, input);
                Item result = this.handleItemToClient(wrapper.user(), wrapper.read(Types1_20_5.ITEM));
                wrapper.write(Types.ITEM1_20_2, result);
                Item secondInput = wrapper.read(Types1_20_5.OPTIONAL_ITEM_COST);
                if (secondInput != null) {
                    secondInput = this.handleItemToClient(wrapper.user(), secondInput);
                    this.cleanInput(secondInput);
                }
                wrapper.write(Types.ITEM1_20_2, secondInput);
                wrapper.passthrough(Types.BOOLEAN);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.FLOAT);
                wrapper.passthrough(Types.INT);
            }
        });
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.MAP_ITEM_DATA, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.BYTE);
            wrapper.passthrough(Types.BOOLEAN);
            if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                int icons = wrapper.passthrough(Types.VAR_INT);
                for (int i = 0; i < icons; ++i) {
                    int decorationType = wrapper.read(Types.VAR_INT);
                    wrapper.write(Types.VAR_INT, decorationType == 34 ? 32 : decorationType);
                    wrapper.passthrough(Types.BYTE);
                    wrapper.passthrough(Types.BYTE);
                    wrapper.passthrough(Types.BYTE);
                    wrapper.passthrough(Types.OPTIONAL_TAG);
                }
            }
        });
        RecipeRewriter1_20_3 recipeRewriter = new RecipeRewriter1_20_3(this.protocol);
        ((Protocol1_20_5To1_20_3)this.protocol).registerClientbound(ClientboundPackets1_20_5.UPDATE_RECIPES, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                String recipeIdentifier = wrapper.read(Types.STRING);
                int serializerTypeId = wrapper.read(Types.VAR_INT);
                String serializerType = ((Protocol1_20_5To1_20_3)this.protocol).getMappingData().getRecipeSerializerMappings().mappedIdentifier(serializerTypeId);
                wrapper.write(Types.STRING, serializerType);
                wrapper.write(Types.STRING, recipeIdentifier);
                recipeRewriter.handleRecipeType(wrapper, Key.stripMinecraftNamespace(serializerType));
            }
        });
    }

    private void updateBlockEntityTag(UserConnection connection, CompoundTag tag) {
        if (tag == null) {
            return;
        }
        Tag profileTag = tag.remove("profile");
        if (profileTag instanceof StringTag) {
            tag.put("SkullOwner", profileTag);
        } else if (profileTag instanceof CompoundTag) {
            this.updateProfileTag(tag, (CompoundTag)profileTag);
        }
        ListTag<CompoundTag> patternsTag = tag.getListTag("patterns", CompoundTag.class);
        if (patternsTag != null) {
            for (CompoundTag patternTag : patternsTag) {
                String pattern = patternTag.getString("pattern", "");
                String color = patternTag.getString("color");
                String compactIdentifier = BannerPatterns1_20_5.fullIdToCompact(Key.stripMinecraftNamespace(pattern));
                if (compactIdentifier == null || color == null) continue;
                patternTag.remove("pattern");
                patternTag.remove("color");
                patternTag.putString("Pattern", compactIdentifier);
                patternTag.putInt("Color", BlockItemPacketRewriter1_20_5.colorId(color));
            }
            tag.remove("patterns");
            tag.put("Patterns", patternsTag);
        }
    }

    private void updateProfileTag(CompoundTag tag, CompoundTag profileTag) {
        ListTag<CompoundTag> propertiesListTag;
        IntArrayTag idTag;
        CompoundTag skullOwnerTag = new CompoundTag();
        tag.put("SkullOwner", skullOwnerTag);
        String name = profileTag.getString("name");
        if (name != null) {
            skullOwnerTag.putString("Name", name);
        }
        if ((idTag = profileTag.getIntArrayTag("id")) != null) {
            skullOwnerTag.put("Id", idTag);
        }
        if ((propertiesListTag = profileTag.getListTag("properties", CompoundTag.class)) == null) {
            return;
        }
        CompoundTag propertiesTag = new CompoundTag();
        for (CompoundTag propertyTag : propertiesListTag) {
            String property = propertyTag.getString("name", "");
            String value = propertyTag.getString("value", "");
            String signature = propertyTag.getString("signature");
            ListTag<CompoundTag> list = new ListTag<CompoundTag>(CompoundTag.class);
            CompoundTag updatedPropertyTag = new CompoundTag();
            updatedPropertyTag.putString("Value", value);
            if (signature != null) {
                updatedPropertyTag.putString("Signature", signature);
            }
            list.add(updatedPropertyTag);
            propertiesTag.put(property, list);
        }
        skullOwnerTag.put("Properties", propertiesTag);
    }

    private void cleanInput(@Nullable Item item) {
        if (item == null || item.tag() == null) {
            return;
        }
        CompoundTag tag = item.tag();
        StructuredDataConverter.removeBackupTag(tag);
        CompoundTag display = tag.getCompoundTag("display");
        if (display != null) {
            this.removeEmptyList(display, "Lore");
            if (display.isEmpty()) {
                tag.remove("display");
            }
        }
        this.removeEmptyList(tag, "Enchantments");
        this.removeEmptyList(tag, "AttributeModifiers");
        if (tag.getInt("RepairCost", -1) == 0) {
            tag.remove("RepairCost");
        }
        if (tag.isEmpty()) {
            item.setTag(null);
        }
    }

    private void removeEmptyList(CompoundTag tag, String key) {
        ListTag<?> list = tag.getListTag(key);
        if (list != null && list.isEmpty()) {
            tag.remove(key);
        }
    }

    private static int colorId(String color) {
        int n;
        switch (color) {
            case "orange": {
                n = 1;
                break;
            }
            case "magenta": {
                n = 2;
                break;
            }
            case "light_blue": {
                n = 3;
                break;
            }
            case "yellow": {
                n = 4;
                break;
            }
            case "lime": {
                n = 5;
                break;
            }
            case "pink": {
                n = 6;
                break;
            }
            case "gray": {
                n = 7;
                break;
            }
            case "light_gray": {
                n = 8;
                break;
            }
            case "cyan": {
                n = 9;
                break;
            }
            case "purple": {
                n = 10;
                break;
            }
            case "blue": {
                n = 11;
                break;
            }
            case "brown": {
                n = 12;
                break;
            }
            case "green": {
                n = 13;
                break;
            }
            case "red": {
                n = 14;
                break;
            }
            case "black": {
                n = 15;
                break;
            }
            default: {
                n = 0;
            }
        }
        return n;
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, Item item) {
        if (item.isEmpty()) {
            return null;
        }
        StructuredDataContainer data = item.dataContainer();
        item.dataContainer().setIdLookup(this.protocol, true);
        this.enchantmentRewriter.handleToClient(item);
        super.handleItemToClient(connection, item);
        this.updateComponent(connection, item, StructuredDataKey.ITEM_NAME, "item_name");
        this.updateComponent(connection, item, StructuredDataKey.CUSTOM_NAME, "custom_name");
        Tag[] lore = data.get(StructuredDataKey.LORE);
        if (lore != null) {
            for (Tag tag : lore) {
                ((ComponentRewriter)((Protocol1_20_5To1_20_3)this.protocol).getComponentRewriter()).processTag(connection, tag);
            }
        }
        if (item.identifier() == 1105 && !data.has(StructuredDataKey.FIREWORKS)) {
            data.set(StructuredDataKey.FIREWORKS, new Fireworks(1, new FireworkExplosion[0]));
        }
        CompoundTag customData = data.get(StructuredDataKey.CUSTOM_DATA);
        Item oldItem = this.vvProtocol.getItemRewriter().toOldItem(connection, item, DATA_CONVERTER);
        if (customData != null) {
            oldItem.tag().put(this.nbtTagName(), customData.copy());
        }
        if (oldItem.tag() != null && oldItem.tag().isEmpty()) {
            oldItem.setTag(null);
        }
        return oldItem;
    }

    @Override
    protected void updateItemDataComponents(UserConnection connection, Item item, boolean clientbound) {
    }

    @Override
    public Item handleItemToServer(UserConnection connection, @Nullable Item item) {
        Tag tag;
        if (item == null) {
            return StructuredItem.empty();
        }
        Item structuredItem = this.vvProtocol.getItemRewriter().toStructuredItem(connection, item);
        if (item.tag() != null && (tag = item.tag().get(this.nbtTagName())) instanceof CompoundTag) {
            CompoundTag tag2 = (CompoundTag)tag;
            structuredItem.dataContainer().set(StructuredDataKey.CUSTOM_DATA, tag2);
        }
        structuredItem.dataContainer().setIdLookup(this.protocol, false);
        this.enchantmentRewriter.handleToServer(structuredItem);
        return super.handleItemToServer(connection, structuredItem);
    }
}

