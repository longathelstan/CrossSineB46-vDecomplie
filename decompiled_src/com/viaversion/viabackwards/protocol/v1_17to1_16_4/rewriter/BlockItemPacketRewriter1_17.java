/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_17to1_16_4.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.LongArrayTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.data.MapColorMappings1_16_4;
import com.viaversion.viabackwards.protocol.v1_17to1_16_4.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_17;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_1to1_16_2.packet.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.v1_16_4to1_17.packet.ServerboundPackets1_17;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public final class BlockItemPacketRewriter1_17
extends BackwardsItemRewriter<ClientboundPackets1_17, ServerboundPackets1_16_2, Protocol1_17To1_16_4> {
    static final int BEDROCK_BLOCK_STATE = 33;

    public BlockItemPacketRewriter1_17(Protocol1_17To1_16_4 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_17> blockRewriter = BlockRewriter.for1_14(this.protocol);
        new RecipeRewriter<ClientboundPackets1_17>(this.protocol).register(ClientboundPackets1_17.UPDATE_RECIPES);
        this.registerCooldown(ClientboundPackets1_17.COOLDOWN);
        this.registerSetContent(ClientboundPackets1_17.CONTAINER_SET_CONTENT);
        this.registerSetEquipment(ClientboundPackets1_17.SET_EQUIPMENT);
        this.registerMerchantOffers(ClientboundPackets1_17.MERCHANT_OFFERS);
        this.registerAdvancements(ClientboundPackets1_17.UPDATE_ADVANCEMENTS);
        blockRewriter.registerBlockBreakAck(ClientboundPackets1_17.BLOCK_BREAK_ACK);
        blockRewriter.registerBlockEvent(ClientboundPackets1_17.BLOCK_EVENT);
        blockRewriter.registerLevelEvent(ClientboundPackets1_17.LEVEL_EVENT, 1010, 2001);
        this.registerSetCreativeModeSlot(ServerboundPackets1_16_2.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_16_2.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    short slot = wrapper.passthrough(Types.SHORT);
                    byte button = wrapper.passthrough(Types.BYTE);
                    wrapper.read(Types.SHORT);
                    int mode2 = wrapper.passthrough(Types.VAR_INT);
                    Item clicked = BlockItemPacketRewriter1_17.this.handleItemToServer(wrapper.user(), wrapper.read(Types.ITEM1_13_2));
                    wrapper.write(Types.VAR_INT, 0);
                    PlayerLastCursorItem state = wrapper.user().get(PlayerLastCursorItem.class);
                    if (mode2 == 0 && button == 0 && clicked != null) {
                        state.setLastCursorItem(clicked);
                    } else if (mode2 == 0 && button == 1 && clicked != null) {
                        if (state.isSet()) {
                            state.setLastCursorItem(clicked);
                        } else {
                            state.setLastCursorItem(clicked, (clicked.amount() + 1) / 2);
                        }
                    } else if (mode2 != 5 || slot != -999 || button != 0 && button != 4) {
                        state.setLastCursorItem(null);
                    }
                    Item carried = state.getLastCursorItem();
                    if (carried == null) {
                        wrapper.write(Types.ITEM1_13_2, clicked);
                    } else {
                        wrapper.write(Types.ITEM1_13_2, carried);
                    }
                });
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.CONTAINER_SET_SLOT, wrapper -> {
            short windowId = wrapper.passthrough(Types.UNSIGNED_BYTE);
            short slot = wrapper.passthrough(Types.SHORT);
            Item carried = wrapper.read(Types.ITEM1_13_2);
            if (carried != null && windowId == -1 && slot == -1) {
                wrapper.user().get(PlayerLastCursorItem.class).setLastCursorItem(carried);
            }
            wrapper.write(Types.ITEM1_13_2, this.handleItemToClient(wrapper.user(), carried));
        });
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_16_2.CONTAINER_ACK, null, wrapper -> {
            wrapper.cancel();
            if (!ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
                return;
            }
            short inventoryId = wrapper.read(Types.UNSIGNED_BYTE);
            short confirmationId = wrapper.read(Types.SHORT);
            boolean accepted = wrapper.read(Types.BOOLEAN);
            if (inventoryId == 0 && accepted) {
                PacketWrapper pongPacket = wrapper.create(ServerboundPackets1_17.PONG);
                pongPacket.write(Types.INT, Integer.valueOf(confirmationId));
                pongPacket.sendToServer(Protocol1_17To1_16_4.class);
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    if (id == 16) {
                        wrapper.passthrough(Types.FLOAT);
                        wrapper.passthrough(Types.FLOAT);
                        wrapper.passthrough(Types.FLOAT);
                        wrapper.passthrough(Types.FLOAT);
                        wrapper.read(Types.FLOAT);
                        wrapper.read(Types.FLOAT);
                        wrapper.read(Types.FLOAT);
                    } else if (id == 37) {
                        wrapper.set(Types.INT, 0, -1);
                        wrapper.cancel();
                    }
                });
                this.handler(BlockItemPacketRewriter1_17.this.levelParticlesHandler());
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).mergePacket(ClientboundPackets1_17.SET_BORDER_SIZE, ClientboundPackets1_16_2.SET_BORDER, 0);
        ((Protocol1_17To1_16_4)this.protocol).mergePacket(ClientboundPackets1_17.SET_BORDER_LERP_SIZE, ClientboundPackets1_16_2.SET_BORDER, 1);
        ((Protocol1_17To1_16_4)this.protocol).mergePacket(ClientboundPackets1_17.SET_BORDER_CENTER, ClientboundPackets1_16_2.SET_BORDER, 2);
        ((Protocol1_17To1_16_4)this.protocol).mergePacket(ClientboundPackets1_17.INITIALIZE_BORDER, ClientboundPackets1_16_2.SET_BORDER, 3);
        ((Protocol1_17To1_16_4)this.protocol).mergePacket(ClientboundPackets1_17.SET_BORDER_WARNING_DELAY, ClientboundPackets1_16_2.SET_BORDER, 4);
        ((Protocol1_17To1_16_4)this.protocol).mergePacket(ClientboundPackets1_17.SET_BORDER_WARNING_DISTANCE, ClientboundPackets1_16_2.SET_BORDER, 5);
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.LIGHT_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    Object tracker = wrapper.user().getEntityTracker(Protocol1_17To1_16_4.class);
                    int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
                    long[] skyLightMask = wrapper.read(Types.LONG_ARRAY_PRIMITIVE);
                    long[] blockLightMask = wrapper.read(Types.LONG_ARRAY_PRIMITIVE);
                    int cutSkyLightMask = BlockItemPacketRewriter1_17.this.cutLightMask(skyLightMask, startFromSection);
                    int cutBlockLightMask = BlockItemPacketRewriter1_17.this.cutLightMask(blockLightMask, startFromSection);
                    wrapper.write(Types.VAR_INT, cutSkyLightMask);
                    wrapper.write(Types.VAR_INT, cutBlockLightMask);
                    long[] emptySkyLightMask = wrapper.read(Types.LONG_ARRAY_PRIMITIVE);
                    long[] emptyBlockLightMask = wrapper.read(Types.LONG_ARRAY_PRIMITIVE);
                    wrapper.write(Types.VAR_INT, BlockItemPacketRewriter1_17.this.cutLightMask(emptySkyLightMask, startFromSection));
                    wrapper.write(Types.VAR_INT, BlockItemPacketRewriter1_17.this.cutLightMask(emptyBlockLightMask, startFromSection));
                    this.writeLightArrays(wrapper, BitSet.valueOf(skyLightMask), cutSkyLightMask, startFromSection, tracker.currentWorldSectionHeight());
                    this.writeLightArrays(wrapper, BitSet.valueOf(blockLightMask), cutBlockLightMask, startFromSection, tracker.currentWorldSectionHeight());
                });
            }

            void writeLightArrays(PacketWrapper wrapper, BitSet bitMask, int cutBitMask, int startFromSection, int sectionHeight) {
                int i;
                int packetContentsLength = wrapper.read(Types.VAR_INT);
                int read = 0;
                ArrayList<byte[]> light = new ArrayList<byte[]>();
                for (i = 0; i < startFromSection; ++i) {
                    if (!bitMask.get(i)) continue;
                    ++read;
                    wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                }
                for (i = 0; i < 18; ++i) {
                    if (!this.isSet(cutBitMask, i)) continue;
                    ++read;
                    light.add(wrapper.read(Types.BYTE_ARRAY_PRIMITIVE));
                }
                for (i = startFromSection + 18; i < sectionHeight + 2; ++i) {
                    if (!bitMask.get(i)) continue;
                    ++read;
                    wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                }
                if (read != packetContentsLength) {
                    for (i = read; i < packetContentsLength; ++i) {
                        wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                    }
                }
                for (byte[] bytes : light) {
                    wrapper.write(Types.BYTE_ARRAY_PRIMITIVE, bytes);
                }
            }

            boolean isSet(int mask, int i) {
                return (mask & 1 << i) != 0;
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.SECTION_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.LONG);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    BlockChangeRecord[] records;
                    long chunkPos = wrapper.get(Types.LONG, 0);
                    int chunkY = (int)(chunkPos << 44 >> 44);
                    if (chunkY < 0 || chunkY > 15) {
                        wrapper.cancel();
                        return;
                    }
                    for (BlockChangeRecord record : records = wrapper.passthrough(Types.VAR_LONG_BLOCK_CHANGE_ARRAY)) {
                        if (ViaBackwards.getConfig().bedrockAtY0() && chunkY == 0 && record.getSectionY() == 0) {
                            record.setBlockId(33);
                            continue;
                        }
                        record.setBlockId(((Protocol1_17To1_16_4)BlockItemPacketRewriter1_17.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_14);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int y = wrapper.get(Types.BLOCK_POSITION1_14, 0).y();
                    if (y < 0 || y > 255) {
                        wrapper.cancel();
                        return;
                    }
                    if (ViaBackwards.getConfig().bedrockAtY0() && y == 0) {
                        wrapper.set(Types.VAR_INT, 0, 33);
                    } else {
                        wrapper.set(Types.VAR_INT, 0, ((Protocol1_17To1_16_4)BlockItemPacketRewriter1_17.this.protocol).getMappingData().getNewBlockStateId(wrapper.get(Types.VAR_INT, 0)));
                    }
                });
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.LEVEL_CHUNK, wrapper -> {
            ChunkSection lowestSection;
            Object tracker = wrapper.user().getEntityTracker(Protocol1_17To1_16_4.class);
            int currentWorldSectionHeight = tracker.currentWorldSectionHeight();
            Chunk chunk = wrapper.read(new ChunkType1_17(currentWorldSectionHeight));
            wrapper.write(ChunkType1_16_2.TYPE, chunk);
            int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
            chunk.setBiomeData(Arrays.copyOfRange(chunk.getBiomeData(), startFromSection * 64, startFromSection * 64 + 1024));
            chunk.setBitmask(this.cutMask(chunk.getChunkMask(), startFromSection, false));
            chunk.setChunkMask(null);
            ChunkSection[] sections = Arrays.copyOfRange(chunk.getSections(), startFromSection, startFromSection + 16);
            chunk.setSections(sections);
            CompoundTag heightMaps = chunk.getHeightMap();
            for (Tag heightMapTag : heightMaps.values()) {
                if (!(heightMapTag instanceof LongArrayTag)) continue;
                LongArrayTag heightMap = (LongArrayTag)heightMapTag;
                int[] heightMapData = new int[256];
                int bitsPerEntry = MathUtil.ceilLog2((currentWorldSectionHeight << 4) + 1);
                CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerEntry, heightMapData.length, heightMap.getValue(), (i, v) -> {
                    heightMapData[i] = MathUtil.clamp(v + tracker.currentMinY(), 0, 255);
                });
                heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, i -> heightMapData[i]));
            }
            blockRewriter.handleChunk(chunk);
            if (ViaBackwards.getConfig().bedrockAtY0() && (lowestSection = chunk.getSections()[0]) != null) {
                DataPalette blocks = lowestSection.palette(PaletteType.BLOCKS);
                for (int x = 0; x < 16; ++x) {
                    for (int z = 0; z < 16; ++z) {
                        blocks.setIdAt(x, 0, z, 33);
                    }
                }
            }
            chunk.getBlockEntities().removeIf(compound -> {
                NumberTag tag = compound.getNumberTag("y");
                if (tag == null) {
                    return false;
                }
                int y = tag.asInt();
                return y < 0 || y > 255 || ViaBackwards.getConfig().bedrockAtY0() && y == 0;
            });
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_ENTITY_DATA, wrapper -> {
            int y = wrapper.passthrough(Types.BLOCK_POSITION1_14).y();
            if (y < 0 || y > 255 || ViaBackwards.getConfig().bedrockAtY0() && y == 0) {
                wrapper.cancel();
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_DESTRUCTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int y = wrapper.passthrough(Types.BLOCK_POSITION1_14).y();
                    if (y < 0 || y > 255 || ViaBackwards.getConfig().bedrockAtY0() && y == 0) {
                        wrapper.cancel();
                    }
                });
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_17.MAP_ITEM_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BYTE);
                this.handler(wrapper -> wrapper.write(Types.BOOLEAN, true));
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    boolean hasMarkers = wrapper.read(Types.BOOLEAN);
                    if (!hasMarkers) {
                        wrapper.write(Types.VAR_INT, 0);
                    } else {
                        MapColorRewriter.getRewriteHandler(MapColorMappings1_16_4::getMappedColor).handle(wrapper);
                    }
                });
            }
        });
    }

    int cutLightMask(long[] mask, int startFromSection) {
        if (mask.length == 0) {
            return 0;
        }
        return this.cutMask(BitSet.valueOf(mask), startFromSection, true);
    }

    int cutMask(BitSet mask, int startFromSection, boolean lightMask) {
        int cutMask = 0;
        int to = startFromSection + (lightMask ? 18 : 16);
        int i = startFromSection;
        int j = 0;
        while (i < to) {
            if (mask.get(i)) {
                cutMask |= 1 << j;
            }
            ++i;
            ++j;
        }
        return cutMask;
    }
}

