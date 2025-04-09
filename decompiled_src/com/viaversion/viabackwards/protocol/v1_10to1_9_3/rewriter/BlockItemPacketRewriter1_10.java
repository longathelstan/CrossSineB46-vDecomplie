/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_10to1_9_3.rewriter;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.protocol.v1_10to1_9_3.Protocol1_10To1_9_3;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;

public class BlockItemPacketRewriter1_10
extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_10To1_9_3> {
    public BlockItemPacketRewriter1_10(Protocol1_10To1_9_3 protocol) {
        super(protocol, "1.10");
    }

    @Override
    protected void registerPackets() {
        this.registerBlockChange(ClientboundPackets1_9_3.BLOCK_UPDATE);
        this.registerMultiBlockChange(ClientboundPackets1_9_3.CHUNK_BLOCKS_UPDATE);
        this.registerSetSlot(ClientboundPackets1_9_3.CONTAINER_SET_SLOT);
        this.registerSetContent(ClientboundPackets1_9_3.CONTAINER_SET_CONTENT);
        this.registerSetEquippedItem(ClientboundPackets1_9_3.SET_EQUIPPED_ITEM);
        this.registerCustomPayloadTradeList(ClientboundPackets1_9_3.CUSTOM_PAYLOAD);
        this.registerContainerClick(ServerboundPackets1_9_3.CONTAINER_CLICK);
        this.registerSetCreativeModeSlot(ServerboundPackets1_9_3.SET_CREATIVE_MODE_SLOT);
        ((Protocol1_10To1_9_3)this.protocol).registerClientbound(ClientboundPackets1_9_3.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_10To1_9_3.class);
            ChunkType1_9_3 type = ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            Chunk chunk = wrapper.passthrough(type);
            this.handleChunk(chunk);
        });
        ((Protocol1_10To1_9_3)this.protocol).getEntityRewriter().filter().handler((event, data) -> {
            if (data.dataType().type().equals(Types.ITEM1_8)) {
                data.setValue(this.handleItemToClient(event.user(), (Item)data.getValue()));
            }
        });
        ((Protocol1_10To1_9_3)this.protocol).registerClientbound(ClientboundPackets1_9_3.LEVEL_PARTICLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    if (id == 46) {
                        wrapper.set(Types.INT, 0, 38);
                    }
                });
            }
        });
    }
}

