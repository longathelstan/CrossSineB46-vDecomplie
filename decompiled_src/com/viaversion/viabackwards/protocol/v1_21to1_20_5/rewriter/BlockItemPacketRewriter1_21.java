/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_21to1_20_5.rewriter;

import com.viaversion.nbt.tag.ByteTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.BackwardsStructuredItemRewriter;
import com.viaversion.viabackwards.api.rewriters.StructuredEnchantmentRewriter;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.Protocol1_21To1_20_5;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage.EnchantmentsPaintingsStorage;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage.OpenScreenStorage;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.storage.PlayerRotationStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataContainer;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.item.data.Enchantments;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter.RecipeRewriter1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Enchantments1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.util.SerializerVersion;
import java.util.ArrayList;

public final class BlockItemPacketRewriter1_21
extends BackwardsStructuredItemRewriter<ClientboundPacket1_21, ServerboundPacket1_20_5, Protocol1_21To1_20_5> {
    final StructuredEnchantmentRewriter enchantmentRewriter = new StructuredEnchantmentRewriter(this);

    public BlockItemPacketRewriter1_21(Protocol1_21To1_20_5 protocol) {
        super(protocol, Types1_21.ITEM, Types1_21.ITEM_ARRAY, Types1_20_5.ITEM, Types1_20_5.ITEM_ARRAY, Types1_21.ITEM_COST, Types1_21.OPTIONAL_ITEM_COST, Types1_20_5.ITEM_COST, Types1_20_5.OPTIONAL_ITEM_COST, Types1_21.PARTICLE, Types1_20_5.PARTICLE);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_21> blockRewriter = BlockRewriter.for1_20_2(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_21.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_21.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_21.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelChunk1_19(ClientboundPackets1_21.LEVEL_CHUNK_WITH_LIGHT, ChunkType1_20_2::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_21.BLOCK_ENTITY_DATA);
        this.registerCooldown(ClientboundPackets1_21.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_21.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_21.CONTAINER_SET_SLOT);
        this.registerAdvancements1_20_3(ClientboundPackets1_21.UPDATE_ADVANCEMENTS);
        this.registerSetEquipment(ClientboundPackets1_21.SET_EQUIPMENT);
        this.registerContainerClick1_17_1(ServerboundPackets1_20_5.CONTAINER_CLICK);
        this.registerMerchantOffers1_20_5(ClientboundPackets1_21.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_20_5.SET_CREATIVE_MODE_SLOT);
        this.registerLevelParticles1_20_5(ClientboundPackets1_21.LEVEL_PARTICLES);
        this.registerExplosion(ClientboundPackets1_21.EXPLODE);
        ((Protocol1_21To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_21.OPEN_SCREEN, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int menuType = wrapper.passthrough(Types.VAR_INT);
            wrapper.user().get(OpenScreenStorage.class).setMenuType(menuType);
            ((ComponentRewriter)((Protocol1_21To1_20_5)this.protocol).getComponentRewriter()).passthroughAndProcess(wrapper);
        });
        ((Protocol1_21To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_21.CONTAINER_SET_DATA, wrapper -> {
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            short property = wrapper.passthrough(Types.SHORT);
            if (property >= 4 && property <= 6) {
                OpenScreenStorage openScreenStorage = wrapper.user().get(OpenScreenStorage.class);
                if (openScreenStorage.menuType() != 13) {
                    return;
                }
                short enchantmentId = wrapper.read(Types.SHORT);
                EnchantmentsPaintingsStorage storage = wrapper.user().get(EnchantmentsPaintingsStorage.class);
                String key = storage.enchantments().idToKey(enchantmentId);
                int mappedId = key != null ? Enchantments1_20_5.keyToId(key) : -1;
                wrapper.write(Types.SHORT, (short)mappedId);
            }
        });
        ((Protocol1_21To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_21.HORSE_SCREEN_OPEN, wrapper -> {
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            int columns = wrapper.read(Types.VAR_INT);
            wrapper.write(Types.VAR_INT, columns * 3 + 1);
        });
        ((Protocol1_21To1_20_5)this.protocol).registerClientbound(ClientboundPackets1_21.LEVEL_EVENT, wrapper -> {
            int event = wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.BLOCK_POSITION1_14);
            int data = wrapper.read(Types.INT);
            if (event == 1010) {
                int itemId = wrapper.user().get(EnchantmentsPaintingsStorage.class).jubeboxSongToItem(data);
                if (itemId == -1) {
                    wrapper.cancel();
                    return;
                }
                wrapper.write(Types.INT, itemId);
            } else if (event == 2001) {
                wrapper.write(Types.INT, ((Protocol1_21To1_20_5)this.protocol).getMappingData().getNewBlockStateId(data));
            } else {
                wrapper.write(Types.INT, data);
            }
        });
        ((Protocol1_21To1_20_5)this.protocol).registerServerbound(ServerboundPackets1_20_5.USE_ITEM, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            PlayerRotationStorage rotation = wrapper.user().get(PlayerRotationStorage.class);
            wrapper.write(Types.FLOAT, Float.valueOf(rotation.yaw()));
            wrapper.write(Types.FLOAT, Float.valueOf(rotation.pitch()));
        });
        new RecipeRewriter1_20_3<ClientboundPackets1_21>(this.protocol).register1_20_5(ClientboundPackets1_21.UPDATE_RECIPES);
    }

    @Override
    public Item handleItemToClient(UserConnection connection, Item item) {
        boolean trident;
        if (item.isEmpty()) {
            return item;
        }
        StructuredDataContainer data = item.dataContainer();
        data.setIdLookup(this.protocol, true);
        EnchantmentsPaintingsStorage storage = connection.get(EnchantmentsPaintingsStorage.class);
        IdRewriteFunction idRewriteFunction = id -> {
            String key = storage.enchantments().idToKey(id);
            return key != null ? Enchantments1_20_5.keyToId(key) : -1;
        };
        StructuredEnchantmentRewriter.DescriptionSupplier descriptionSupplier = (id, level) -> {
            Tag description = storage.enchantmentDescription(id);
            if (description == null) {
                return new StringTag("Unknown enchantment");
            }
            ATextComponent component = SerializerVersion.V1_20_5.toComponent(description);
            component.getStyle().setItalic(false);
            component.getStyle().setFormatting(TextFormatting.GRAY);
            component.getSiblings().add(new StringComponent(" "));
            component.getSiblings().add(new TranslationComponent("enchantment.level.%s".formatted(level), new Object[0]));
            return SerializerVersion.V1_20_5.toTag(component);
        };
        this.enchantmentRewriter.rewriteEnchantmentsToClient(data, StructuredDataKey.ENCHANTMENTS, idRewriteFunction, descriptionSupplier, false);
        this.enchantmentRewriter.rewriteEnchantmentsToClient(data, StructuredDataKey.STORED_ENCHANTMENTS, idRewriteFunction, descriptionSupplier, true);
        int identifier = item.identifier();
        super.handleItemToClient(connection, item);
        com.viaversion.viaversion.protocols.v1_20_5to1_21.rewriter.BlockItemPacketRewriter1_21.downgradeItemData(item);
        StructuredDataContainer dataContainer = item.dataContainer();
        if (dataContainer.has(StructuredDataKey.RARITY)) {
            return item;
        }
        boolean bl = trident = identifier == 1188;
        if (trident || identifier == 1200) {
            dataContainer.set(StructuredDataKey.RARITY, trident ? 3 : 1);
            this.saveTag(this.createCustomTag(item), new ByteTag(true), "rarity");
        }
        return item;
    }

    @Override
    public Item handleItemToServer(UserConnection connection, Item item) {
        if (item.isEmpty()) {
            return item;
        }
        StructuredDataContainer dataContainer = item.dataContainer();
        dataContainer.setIdLookup(this.protocol, false);
        EnchantmentsPaintingsStorage storage = connection.get(EnchantmentsPaintingsStorage.class);
        this.rewriteEnchantmentToServer(storage, item, StructuredDataKey.ENCHANTMENTS);
        this.rewriteEnchantmentToServer(storage, item, StructuredDataKey.STORED_ENCHANTMENTS);
        this.enchantmentRewriter.handleToServer(item);
        super.handleItemToServer(connection, item);
        com.viaversion.viaversion.protocols.v1_20_5to1_21.rewriter.BlockItemPacketRewriter1_21.updateItemData(item);
        com.viaversion.viaversion.protocols.v1_20_5to1_21.rewriter.BlockItemPacketRewriter1_21.resetRarityValues(item, this.nbtTagName("rarity"));
        return item;
    }

    void rewriteEnchantmentToServer(EnchantmentsPaintingsStorage storage, Item item, StructuredDataKey<Enchantments> key) {
        Enchantments enchantments = item.dataContainer().get(key);
        if (enchantments == null) {
            return;
        }
        ArrayList<PendingIdChange> updatedIds = new ArrayList<PendingIdChange>();
        for (Int2IntMap.Entry entry : enchantments.enchantments().int2IntEntrySet()) {
            int mappedId;
            int id = entry.getIntKey();
            String enchantmentKey = Enchantments1_20_5.idToKey(id);
            if (enchantmentKey == null || id == (mappedId = storage.enchantments().keyToId(enchantmentKey))) continue;
            int level = entry.getIntValue();
            updatedIds.add(new PendingIdChange(id, mappedId, level));
        }
        for (PendingIdChange change : updatedIds) {
            enchantments.remove(change.id);
        }
        for (PendingIdChange change : updatedIds) {
            enchantments.add(change.mappedId, change.level);
        }
    }

    private static final class PendingIdChange {
        final int id;
        final int mappedId;
        final int level;

        PendingIdChange(int id, int mappedId, int level) {
            this.id = id;
            this.mappedId = mappedId;
            this.level = level;
        }

        public int id() {
            return this.id;
        }

        public int mappedId() {
            return this.mappedId;
        }

        public int level() {
            return this.level;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof PendingIdChange)) {
                return false;
            }
            PendingIdChange pendingIdChange = (PendingIdChange)object;
            return this.id == pendingIdChange.id && this.mappedId == pendingIdChange.mappedId && this.level == pendingIdChange.level;
        }

        public int hashCode() {
            return ((0 * 31 + Integer.hashCode(this.id)) * 31 + Integer.hashCode(this.mappedId)) * 31 + Integer.hashCode(this.level);
        }

        public String toString() {
            return String.format("%s[id=%s, mappedId=%s, level=%s]", this.getClass().getSimpleName(), Integer.toString(this.id), Integer.toString(this.mappedId), Integer.toString(this.level));
        }
    }
}

