/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.template;

import com.viaversion.viabackwards.api.rewriters.BackwardsStructuredItemRewriter;
import com.viaversion.viabackwards.protocol.template.Protocol1_98To1_99;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_21;
import com.viaversion.viaversion.protocols.v1_20_2to1_20_3.rewriter.RecipeRewriter1_20_3;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import com.viaversion.viaversion.rewriter.BlockRewriter;

final class BlockItemPacketRewriter1_99
extends BackwardsStructuredItemRewriter<ClientboundPacket1_21, ServerboundPacket1_20_5, Protocol1_98To1_99> {
    public BlockItemPacketRewriter1_99(Protocol1_98To1_99 protocol) {
        super(protocol, Types1_21.ITEM, Types1_21.ITEM_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_21> blockRewriter = BlockRewriter.for1_20_2(this.protocol);
        blockRewriter.registerBlockEvent(ClientboundPackets1_21.BLOCK_EVENT);
        blockRewriter.registerBlockUpdate(ClientboundPackets1_21.BLOCK_UPDATE);
        blockRewriter.registerSectionBlocksUpdate1_20(ClientboundPackets1_21.SECTION_BLOCKS_UPDATE);
        blockRewriter.registerLevelEvent1_21(ClientboundPackets1_21.LEVEL_EVENT, 2001);
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
        this.registerContainerSetData(ClientboundPackets1_21.CONTAINER_SET_DATA);
        this.registerLevelParticles1_20_5(ClientboundPackets1_21.LEVEL_PARTICLES);
        this.registerExplosion(ClientboundPackets1_21.EXPLODE);
        new RecipeRewriter1_20_3<ClientboundPackets1_21>(this.protocol).register1_20_5(ClientboundPackets1_21.UPDATE_RECIPES);
    }
}

