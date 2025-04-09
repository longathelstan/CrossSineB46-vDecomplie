/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_12to1_11_1.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.LongArrayTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viabackwards.protocol.v1_12to1_11_1.data.MapColorMappings1_11_1;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_11_1to1_12.packet.ServerboundPackets1_12;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.SerializerVersion;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockItemPacketRewriter1_12
extends LegacyBlockItemRewriter<ClientboundPackets1_12, ServerboundPackets1_9_3, Protocol1_12To1_11_1> {
    public BlockItemPacketRewriter1_12(Protocol1_12To1_11_1 protocol) {
        super(protocol, "1.12");
    }

    @Override
    protected void registerPackets() {
        this.registerBlockChange(ClientboundPackets1_12.BLOCK_UPDATE);
        this.registerMultiBlockChange(ClientboundPackets1_12.CHUNK_BLOCKS_UPDATE);
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    int count = wrapper.passthrough(Types.VAR_INT);
                    for (int i = 0; i < count * 3; ++i) {
                        wrapper.passthrough(Types.BYTE);
                    }
                });
                this.handler(wrapper -> {
                    short columns = wrapper.passthrough(Types.UNSIGNED_BYTE);
                    if (columns <= 0) {
                        return;
                    }
                    wrapper.passthrough(Types.UNSIGNED_BYTE);
                    wrapper.passthrough(Types.UNSIGNED_BYTE);
                    wrapper.passthrough(Types.UNSIGNED_BYTE);
                    byte[] data = wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                    for (int i = 0; i < data.length; ++i) {
                        short color = (short)(data[i] & 0xFF);
                        if (color <= 143) continue;
                        color = (short)MapColorMappings1_11_1.getNearestOldColor(color);
                        data[i] = (byte)color;
                    }
                    wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, data);
                });
            }
        });
        this.registerSetSlot(ClientboundPackets1_12.CONTAINER_SET_SLOT);
        this.registerSetContent(ClientboundPackets1_12.CONTAINER_SET_CONTENT);
        this.registerSetEquippedItem(ClientboundPackets1_12.SET_EQUIPPED_ITEM);
        this.registerCustomPayloadTradeList(ClientboundPackets1_12.CUSTOM_PAYLOAD);
        ((Protocol1_12To1_11_1)this.protocol).registerServerbound(ServerboundPackets1_9_3.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.VAR_INT);
                this.map(Types.ITEM1_8);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.VAR_INT, 0) == 1) {
                        wrapper.set(Types.ITEM1_8, 0, null);
                        PacketWrapper confirm = wrapper.create(ServerboundPackets1_12.CONTAINER_ACK);
                        confirm.write(Types.UNSIGNED_BYTE, wrapper.get(Types.UNSIGNED_BYTE, 0));
                        confirm.write(Types.SHORT, wrapper.get(Types.SHORT, 1));
                        confirm.write(Types.BOOLEAN, false);
                        wrapper.sendToServer(Protocol1_12To1_11_1.class);
                        wrapper.cancel();
                        confirm.sendToServer(Protocol1_12To1_11_1.class);
                        return;
                    }
                    Item item = wrapper.get(Types.ITEM1_8, 0);
                    BlockItemPacketRewriter1_12.this.handleItemToServer(wrapper.user(), item);
                });
            }
        });
        this.registerSetCreativeModeSlot(ServerboundPackets1_9_3.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_12To1_11_1.class);
            ChunkType1_9_3 type = ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            Chunk chunk = wrapper.passthrough(type);
            this.handleChunk(chunk);
            for (CompoundTag tag : chunk.getBlockEntities()) {
                String id = tag.getString("id");
                if (id == null || !Key.stripMinecraftNamespace(id).equals("sign")) continue;
                this.handleSignText(tag);
            }
        });
        ((Protocol1_12To1_11_1)this.protocol).registerClientbound(ClientboundPackets1_12.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    short type = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    if (type == 9) {
                        CompoundTag tag = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                        BlockItemPacketRewriter1_12.this.handleSignText(tag);
                    } else if (type == 11) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((Protocol1_12To1_11_1)this.protocol).getEntityRewriter().filter().handler((event, data) -> {
            if (data.dataType().type().equals(Types.ITEM1_8)) {
                data.setValue(this.handleItemToClient(event.user(), (Item)data.getValue()));
            }
        });
        ((Protocol1_12To1_11_1)this.protocol).registerServerbound(ServerboundPackets1_9_3.CLIENT_COMMAND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    if (wrapper.get(Types.VAR_INT, 0) == 2) {
                        wrapper.cancel();
                    }
                });
            }
        });
    }

    void handleSignText(CompoundTag tag) {
        for (int i = 0; i < 4; ++i) {
            int n = i + 1;
            StringTag lineTag = tag.getStringTag("Text" + n);
            if (lineTag == null) continue;
            lineTag.setValue(ComponentUtil.convertJsonOrEmpty(lineTag.getValue(), SerializerVersion.V1_12, SerializerVersion.V1_9).toString());
        }
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToClient(connection, item);
        if (item.tag() != null) {
            CompoundTag backupTag = new CompoundTag();
            if (this.handleNbtToClient(item.tag(), backupTag)) {
                item.tag().put("Via|LongArrayTags", backupTag);
            }
        }
        return item;
    }

    boolean handleNbtToClient(CompoundTag compoundTag, CompoundTag backupTag) {
        Iterator<Map.Entry<String, Tag>> iterator2 = compoundTag.iterator();
        boolean hasLongArrayTag = false;
        while (iterator2.hasNext()) {
            Map.Entry<String, Tag> entry = iterator2.next();
            Tag tag = entry.getValue();
            if (tag instanceof CompoundTag) {
                CompoundTag tag2 = (CompoundTag)tag;
                CompoundTag nestedBackupTag = new CompoundTag();
                backupTag.put(entry.getKey(), nestedBackupTag);
                hasLongArrayTag |= this.handleNbtToClient(tag2, nestedBackupTag);
                continue;
            }
            tag = entry.getValue();
            if (!(tag instanceof LongArrayTag)) continue;
            LongArrayTag tag3 = (LongArrayTag)tag;
            backupTag.put(entry.getKey(), this.fromLongArrayTag(tag3));
            iterator2.remove();
            hasLongArrayTag = true;
        }
        return hasLongArrayTag;
    }

    @Override
    public @Nullable Item handleItemToServer(UserConnection connection, Item item) {
        Tag tag;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(connection, item);
        if (item.tag() != null && (tag = item.tag().remove("Via|LongArrayTags")) instanceof CompoundTag) {
            CompoundTag tag2 = (CompoundTag)tag;
            this.handleNbtToServer(item.tag(), tag2);
        }
        return item;
    }

    void handleNbtToServer(CompoundTag compoundTag, CompoundTag backupTag) {
        for (Map.Entry<String, Tag> entry : backupTag) {
            if (entry.getValue() instanceof CompoundTag) {
                CompoundTag nestedTag = compoundTag.getCompoundTag(entry.getKey());
                this.handleNbtToServer(nestedTag, (CompoundTag)entry.getValue());
                continue;
            }
            compoundTag.put(entry.getKey(), this.fromIntArrayTag((IntArrayTag)entry.getValue()));
        }
    }

    IntArrayTag fromLongArrayTag(LongArrayTag tag) {
        int[] intArray = new int[tag.length() * 2];
        long[] longArray = tag.getValue();
        int i = 0;
        for (long l : longArray) {
            intArray[i++] = (int)(l >> 32);
            intArray[i++] = (int)l;
        }
        return new IntArrayTag(intArray);
    }

    LongArrayTag fromIntArrayTag(IntArrayTag tag) {
        long[] longArray = new long[tag.length() / 2];
        int[] intArray = tag.getValue();
        int i = 0;
        int j = 0;
        while (i < intArray.length) {
            longArray[j] = (long)intArray[i] << 32 | (long)intArray[i + 1] & 0xFFFFFFFFL;
            i += 2;
            ++j;
        }
        return new LongArrayTag(longArray);
    }
}

