/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.LongArrayTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_15;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.protocols.v1_15_2to1_16.packet.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import java.util.Map;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.Protocol20w14infiniteTo1_16;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.data.BiomeData20w14infinite;
import net.raphimc.viaaprilfools.protocol.s20w14infinitetov1_16.packet.ClientboundPackets20w14infinite;

public class BlockItemPacketRewriter20w14infinite
extends ItemRewriter<ClientboundPackets20w14infinite, ServerboundPackets1_16, Protocol20w14infiniteTo1_16> {
    public BlockItemPacketRewriter20w14infinite(Protocol20w14infiniteTo1_16 protocol) {
        super(protocol, Types.ITEM1_13_2, Types.ITEM1_13_2_SHORT_ARRAY);
    }

    @Override
    protected void registerPackets() {
        this.registerCooldown(ClientboundPackets20w14infinite.COOLDOWN);
        this.registerSetContent(ClientboundPackets20w14infinite.CONTAINER_SET_CONTENT);
        this.registerSetSlot(ClientboundPackets20w14infinite.CONTAINER_SET_SLOT);
        this.registerMerchantOffers(ClientboundPackets20w14infinite.MERCHANT_OFFERS);
        this.registerAdvancements(ClientboundPackets20w14infinite.UPDATE_ADVANCEMENTS);
        this.registerLevelParticles(ClientboundPackets20w14infinite.LEVEL_PARTICLES, Types.DOUBLE);
        this.registerContainerClick(ServerboundPackets1_16.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_16.SET_CREATIVE_MODE_SLOT);
        BlockRewriter<ClientboundPackets20w14infinite> blockRewriter = BlockRewriter.for1_14(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets20w14infinite.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets20w14infinite.BLOCK_UPDATE);
        blockRewriter.registerChunkBlocksUpdate(ClientboundPackets20w14infinite.CHUNK_BLOCKS_UPDATE);
        blockRewriter.registerBlockBreakAck(ClientboundPackets20w14infinite.BLOCK_BREAK_ACK);
        blockRewriter.registerLevelEvent(ClientboundPackets20w14infinite.LEVEL_EVENT, 1010, 2001);
        ((Protocol20w14infiniteTo1_16)this.protocol).registerClientbound(ClientboundPackets20w14infinite.LIGHT_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> wrapper.write(Types.BOOLEAN, true));
            }
        });
        ((Protocol20w14infiniteTo1_16)this.protocol).registerClientbound(ClientboundPackets20w14infinite.LEVEL_CHUNK, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> {
                    Chunk chunk = wrapper.read(ChunkType1_15.TYPE);
                    wrapper.write(ChunkType1_16.TYPE, chunk);
                    chunk.setIgnoreOldLightData(chunk.isFullChunk());
                    for (int s = 0; s < chunk.getSections().length; ++s) {
                        ChunkSection section = chunk.getSections()[s];
                        if (section == null) continue;
                        DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                        for (int i2 = 0; i2 < blockPalette.size(); ++i2) {
                            int old = blockPalette.idByIndex(i2);
                            blockPalette.setIdByIndex(i2, ((Protocol20w14infiniteTo1_16)BlockItemPacketRewriter20w14infinite.this.protocol).getMappingData().getNewBlockStateId(old));
                        }
                    }
                    if (chunk.getBiomeData() != null) {
                        for (int i3 = 0; i3 < chunk.getBiomeData().length; ++i3) {
                            if (BiomeData20w14infinite.isValid(chunk.getBiomeData()[i3])) continue;
                            chunk.getBiomeData()[i3] = 1;
                        }
                    }
                    CompoundTag heightMaps = chunk.getHeightMap();
                    for (Map.Entry<String, Tag> heightMapTag : heightMaps) {
                        LongArrayTag heightMap = (LongArrayTag)heightMapTag.getValue();
                        int[] heightMapData = new int[256];
                        CompactArrayUtil.iterateCompactArray(9, heightMapData.length, heightMap.getValue(), (i, v) -> {
                            heightMapData[i] = v;
                        });
                        heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, i -> heightMapData[i]));
                    }
                });
            }
        });
        ((Protocol20w14infiniteTo1_16)this.protocol).registerClientbound(ClientboundPackets20w14infinite.SET_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int slot = wrapper.read(Types.VAR_INT);
                    wrapper.write(Types.BYTE, (byte)slot);
                    BlockItemPacketRewriter20w14infinite.this.handleItemToClient(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2));
                });
            }
        });
        ((Protocol20w14infiniteTo1_16)this.protocol).registerServerbound(ServerboundPackets1_16.EDIT_BOOK, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(wrapper -> BlockItemPacketRewriter20w14infinite.this.handleItemToServer(wrapper.user(), wrapper.passthrough(Types.ITEM1_13_2)));
            }
        });
    }
}

