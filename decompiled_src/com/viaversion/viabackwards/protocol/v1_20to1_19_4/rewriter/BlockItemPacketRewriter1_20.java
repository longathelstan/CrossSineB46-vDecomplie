/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_20to1_19_4.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.protocol.v1_20to1_19_4.Protocol1_20To1_19_4;
import com.viaversion.viabackwards.protocol.v1_20to1_19_4.storage.BackSignEditStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.packet.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.v1_19_3to1_19_4.rewriter.RecipeRewriter1_19_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPacketRewriter1_20
extends BackwardsItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_4, Protocol1_20To1_19_4> {
    static final Set<String> NEW_TRIM_PATTERNS = new HashSet<String>(Arrays.asList("host", "raiser", "shaper", "silence", "wayfinder"));

    public BlockItemPacketRewriter1_20(Protocol1_20To1_19_4 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_ARRAY);
    }

    @Override
    public void registerPackets() {
        final BlockRewriter<ClientboundPackets1_19_4> blockRewriter = BlockRewriter.for1_14(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_19_4.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_19_4.BLOCK_UPDATE);
        blockRewriter.registerLevelEvent(ClientboundPackets1_19_4.LEVEL_EVENT, 1010, 2001);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, this::handleBlockEntity);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.LEVEL_CHUNK_WITH_LIGHT, new PacketHandlers(){

            @Override
            protected void register() {
                this.handler(blockRewriter.chunkHandler1_19(ChunkType1_18::new, (user, blockEntity) -> BlockItemPacketRewriter1_20.this.handleBlockEntity((BlockEntity)blockEntity)));
                this.create(Types.BOOLEAN, true);
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.LIGHT_UPDATE, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.write(Types.BOOLEAN, true);
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.SECTION_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.LONG);
                this.create(Types.BOOLEAN, false);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Types.VAR_LONG_BLOCK_CHANGE_ARRAY)) {
                        record.setBlockId(((Protocol1_20To1_19_4)BlockItemPacketRewriter1_20.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SCREEN, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            this.handleMenuType(wrapper);
            ((ComponentRewriter)((Protocol1_20To1_19_4)this.protocol).getComponentRewriter()).passthroughAndProcess(wrapper);
        });
        this.registerCooldown(ClientboundPackets1_19_4.COOLDOWN);
        this.registerSetContent1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_CONTENT);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_4.CONTAINER_SET_SLOT);
        this.registerSetEquipment(ClientboundPackets1_19_4.SET_EQUIPMENT);
        this.registerContainerClick1_17_1(ServerboundPackets1_19_4.CONTAINER_CLICK);
        this.registerMerchantOffers1_19(ClientboundPackets1_19_4.MERCHANT_OFFERS);
        this.registerSetCreativeModeSlot(ServerboundPackets1_19_4.SET_CREATIVE_MODE_SLOT);
        this.registerContainerSetData(ClientboundPackets1_19_4.CONTAINER_SET_DATA);
        this.registerLevelParticles1_19(ClientboundPackets1_19_4.LEVEL_PARTICLES);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Types.COMPONENT);
                    wrapper.passthrough(Types.COMPONENT);
                    this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                    wrapper.passthrough(Types.VAR_INT);
                    int flags = wrapper.passthrough(Types.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                wrapper.passthrough(Types.STRING_ARRAY);
                int arrayLength = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < arrayLength; ++array) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }
                wrapper.read(Types.BOOLEAN);
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, wrapper -> {
            BlockPosition position = wrapper.passthrough(Types.BLOCK_POSITION1_14);
            boolean frontSide = wrapper.read(Types.BOOLEAN);
            if (frontSide) {
                wrapper.user().remove(BackSignEditStorage.class);
            } else {
                wrapper.user().put(new BackSignEditStorage(position));
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerServerbound(ServerboundPackets1_19_4.SIGN_UPDATE, wrapper -> {
            BlockPosition position = wrapper.passthrough(Types.BLOCK_POSITION1_14);
            BackSignEditStorage backSignEditStorage = wrapper.user().remove(BackSignEditStorage.class);
            boolean frontSide = backSignEditStorage == null || !backSignEditStorage.position().equals(position);
            wrapper.write(Types.BOOLEAN, frontSide);
        });
        new RecipeRewriter1_19_4<ClientboundPackets1_19_4>(this.protocol).register(ClientboundPackets1_19_4.UPDATE_RECIPES);
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
        String pattern;
        StringTag patternTag;
        CompoundTag trimTag;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(connection, item);
        CompoundTag tag = item.tag();
        if (tag != null && (trimTag = tag.getCompoundTag("Trim")) != null && (patternTag = trimTag.getStringTag("pattern")) != null && NEW_TRIM_PATTERNS.contains(pattern = Key.stripMinecraftNamespace(patternTag.getValue()))) {
            tag.remove("Trim");
            tag.put(this.nbtTagName("Trim"), trimTag);
        }
        return item;
    }

    @Override
    public @Nullable Item handleItemToServer(UserConnection connection, @Nullable Item item) {
        Tag trimTag;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(connection, item);
        CompoundTag tag = item.tag();
        if (tag != null && (trimTag = tag.remove(this.nbtTagName("Trim"))) != null) {
            tag.put("Trim", trimTag);
        }
        return item;
    }

    void handleBlockEntity(BlockEntity blockEntity) {
        if (blockEntity.typeId() != 7 && blockEntity.typeId() != 8) {
            return;
        }
        CompoundTag tag = blockEntity.tag();
        Tag frontText = tag.remove("front_text");
        tag.remove("back_text");
        if (frontText instanceof CompoundTag) {
            Tag glowing;
            CompoundTag frontTextTag = (CompoundTag)frontText;
            this.writeMessages(frontTextTag, tag, false);
            this.writeMessages(frontTextTag, tag, true);
            Tag color = frontTextTag.remove("color");
            if (color != null) {
                tag.put("Color", color);
            }
            if ((glowing = frontTextTag.remove("has_glowing_text")) != null) {
                tag.put("GlowingText", glowing);
            }
        }
    }

    void writeMessages(CompoundTag frontText, CompoundTag tag, boolean filtered) {
        ListTag<StringTag> messages = frontText.getListTag(filtered ? "filtered_messages" : "messages", StringTag.class);
        if (messages == null) {
            return;
        }
        int i = 0;
        for (StringTag message : messages) {
            int n = ++i;
            String string = filtered ? "FilteredText" : "Text";
            tag.put(string + n, message);
        }
    }
}

