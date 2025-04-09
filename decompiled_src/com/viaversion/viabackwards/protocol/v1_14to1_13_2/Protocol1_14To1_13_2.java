/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14to1_13_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter.BlockItemPacketRewriter1_14;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter.CommandRewriter1_14;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter.EntityPacketRewriter1_14;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter.PlayerPacketRewriter1_14;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.rewriter.SoundPacketRewriter1_14;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.storage.ChunkLightStorage;
import com.viaversion.viabackwards.protocol.v1_14to1_13_2.storage.DifficultyStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_14;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.v1_13_2to1_14.packet.ServerboundPackets1_14;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;

public class Protocol1_14To1_13_2
extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_13, ServerboundPackets1_14, ServerboundPackets1_13> {
    public static final BackwardsMappingData MAPPINGS = new BackwardsMappingData("1.14", "1.13.2", Protocol1_13_2To1_14.class);
    private final EntityPacketRewriter1_14 entityRewriter = new EntityPacketRewriter1_14(this);
    private final BlockItemPacketRewriter1_14 itemRewriter = new BlockItemPacketRewriter1_14(this);
    private final TranslatableRewriter<ClientboundPackets1_14> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_14>(this, ComponentRewriter.ReadType.JSON);

    public Protocol1_14To1_13_2() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_13.class, ServerboundPackets1_14.class, ServerboundPackets1_13.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossEvent(ClientboundPackets1_14.BOSS_EVENT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_14.CHAT);
        this.translatableRewriter.registerPlayerCombat(ClientboundPackets1_14.PLAYER_COMBAT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_14.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_14.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_14.SET_TITLES);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_14(this).registerDeclareCommands(ClientboundPackets1_14.COMMANDS);
        new PlayerPacketRewriter1_14(this).register();
        new SoundPacketRewriter1_14(this).register();
        new StatisticsRewriter<ClientboundPackets1_14>(this).register(ClientboundPackets1_14.AWARD_STATS);
        this.cancelClientbound(ClientboundPackets1_14.SET_CHUNK_CACHE_CENTER);
        this.cancelClientbound(ClientboundPackets1_14.SET_CHUNK_CACHE_RADIUS);
        this.registerClientbound(ClientboundPackets1_14.UPDATE_TAGS, wrapper -> {
            int blockTagsSize = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < blockTagsSize; ++i) {
                wrapper.passthrough(Types.STRING);
                int[] blockIds = wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
                for (int j = 0; j < blockIds.length; ++j) {
                    int blockId;
                    int id = blockIds[j];
                    blockIds[j] = blockId = MAPPINGS.getNewBlockId(id);
                }
            }
            int itemTagsSize = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < itemTagsSize; ++i) {
                wrapper.passthrough(Types.STRING);
                int[] itemIds = wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
                for (int j = 0; j < itemIds.length; ++j) {
                    int oldId;
                    int itemId = itemIds[j];
                    itemIds[j] = oldId = MAPPINGS.getItemMappings().getNewId(itemId);
                }
            }
            int fluidTagsSize = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < fluidTagsSize; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
            }
            int entityTagsSize = wrapper.read(Types.VAR_INT);
            for (int i = 0; i < entityTagsSize; ++i) {
                wrapper.read(Types.STRING);
                wrapper.read(Types.VAR_INT_ARRAY_PRIMITIVE);
            }
        });
        this.registerClientbound(ClientboundPackets1_14.LIGHT_UPDATE, null, (PacketWrapper wrapper) -> {
            int x = wrapper.read(Types.VAR_INT);
            int z = wrapper.read(Types.VAR_INT);
            int skyLightMask = wrapper.read(Types.VAR_INT);
            int blockLightMask = wrapper.read(Types.VAR_INT);
            int emptySkyLightMask = wrapper.read(Types.VAR_INT);
            int emptyBlockLightMask = wrapper.read(Types.VAR_INT);
            byte[][] skyLight = new byte[16][];
            if (Protocol1_14To1_13_2.isSet(skyLightMask, 0)) {
                wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
            }
            for (int i = 0; i < 16; ++i) {
                if (Protocol1_14To1_13_2.isSet(skyLightMask, i + 1)) {
                    skyLight[i] = wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                    continue;
                }
                if (!Protocol1_14To1_13_2.isSet(emptySkyLightMask, i + 1)) continue;
                skyLight[i] = ChunkLightStorage.EMPTY_LIGHT;
            }
            if (Protocol1_14To1_13_2.isSet(skyLightMask, 17)) {
                wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
            }
            byte[][] blockLight = new byte[16][];
            if (Protocol1_14To1_13_2.isSet(blockLightMask, 0)) {
                wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
            }
            for (int i = 0; i < 16; ++i) {
                if (Protocol1_14To1_13_2.isSet(blockLightMask, i + 1)) {
                    blockLight[i] = wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
                    continue;
                }
                if (!Protocol1_14To1_13_2.isSet(emptyBlockLightMask, i + 1)) continue;
                blockLight[i] = ChunkLightStorage.EMPTY_LIGHT;
            }
            if (Protocol1_14To1_13_2.isSet(blockLightMask, 17)) {
                wrapper.read(Types.BYTE_ARRAY_PRIMITIVE);
            }
            wrapper.user().get(ChunkLightStorage.class).setStoredLight(skyLight, blockLight, x, z);
            wrapper.cancel();
        });
    }

    private static boolean isSet(int mask, int i) {
        return (mask & 1 << i) != 0;
    }

    @Override
    public void init(UserConnection user) {
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_14.PLAYER));
        user.addClientWorld(this.getClass(), new ClientWorld());
        if (!user.has(ChunkLightStorage.class)) {
            user.put(new ChunkLightStorage());
        }
        user.put(new DifficultyStorage());
    }

    @Override
    public BackwardsMappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPacketRewriter1_14 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_14 getItemRewriter() {
        return this.itemRewriter;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_14> getComponentRewriter() {
        return this.translatableRewriter;
    }
}

