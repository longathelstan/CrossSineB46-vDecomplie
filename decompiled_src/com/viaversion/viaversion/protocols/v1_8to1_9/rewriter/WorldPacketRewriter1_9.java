/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.BulkChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.EffectIdMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.PotionIdMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.data.SoundEffectMappings1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.CommandBlockProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.provider.HandItemProvider;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.ClientWorld1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.storage.EntityTracker1_9;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.Optional;

public class WorldPacketRewriter1_9 {
    public static void register(Protocol1_8To1_9 protocol) {
        protocol.registerClientbound(ClientboundPackets1_8.UPDATE_SIGN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.handler(wrapper -> {
                    for (int i = 0; i < 4; ++i) {
                        String line = wrapper.read(Types.STRING);
                        Protocol1_8To1_9.STRING_TO_JSON.write(wrapper, line);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    id = EffectIdMappings1_9.getNewId(id);
                    wrapper.set(Types.INT, 0, id);
                });
                this.handler(wrapper -> {
                    int id = wrapper.get(Types.INT, 0);
                    if (id == 2002) {
                        int data = wrapper.get(Types.INT, 1);
                        int newData = PotionIdMappings1_9.getNewPotionID(data);
                        wrapper.set(Types.INT, 1, newData);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    String name = Key.stripMinecraftNamespace(wrapper.get(Types.STRING, 0));
                    SoundEffectMappings1_9 effect = SoundEffectMappings1_9.getByName(name);
                    int catid = 0;
                    String newname = name;
                    if (effect != null) {
                        catid = effect.getCategory().getId();
                        newname = effect.getNewName();
                    }
                    wrapper.set(Types.STRING, 0, newname);
                    wrapper.write(Types.VAR_INT, catid);
                    if (!Via.getConfig().cancelBlockSounds()) {
                        return;
                    }
                    if (effect != null && effect.isBreakSound()) {
                        EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                        int x = wrapper.passthrough(Types.INT);
                        int y = wrapper.passthrough(Types.INT);
                        int z = wrapper.passthrough(Types.INT);
                        if (tracker.interactedBlockRecently((int)Math.floor((double)x / 8.0), (int)Math.floor((double)y / 8.0), (int)Math.floor((double)z / 8.0))) {
                            wrapper.cancel();
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.LEVEL_CHUNK, wrapper -> {
            block4: {
                long chunkHash;
                Chunk chunk;
                ClientWorld1_9 clientWorld;
                block3: {
                    clientWorld = (ClientWorld1_9)wrapper.user().getClientWorld(Protocol1_8To1_9.class);
                    chunk = wrapper.read(ChunkType1_8.forEnvironment(clientWorld.getEnvironment()));
                    chunkHash = ClientWorld1_9.toLong(chunk.getX(), chunk.getZ());
                    if (!chunk.isFullChunk() || chunk.getBitmask() != 0) break block3;
                    wrapper.setPacketType(ClientboundPackets1_9.FORGET_LEVEL_CHUNK);
                    wrapper.write(Types.INT, chunk.getX());
                    wrapper.write(Types.INT, chunk.getZ());
                    CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                    provider.unloadChunk(wrapper.user(), chunk.getX(), chunk.getZ());
                    clientWorld.getLoadedChunks().remove(chunkHash);
                    if (!Via.getConfig().isChunkBorderFix()) break block4;
                    for (BlockFace face : BlockFace.HORIZONTAL) {
                        int chunkX = chunk.getX() + face.modX();
                        int chunkZ = chunk.getZ() + face.modZ();
                        if (clientWorld.getLoadedChunks().contains(ClientWorld1_9.toLong(chunkX, chunkZ))) continue;
                        PacketWrapper unloadChunk = wrapper.create(ClientboundPackets1_9.FORGET_LEVEL_CHUNK);
                        unloadChunk.write(Types.INT, chunkX);
                        unloadChunk.write(Types.INT, chunkZ);
                        unloadChunk.send(Protocol1_8To1_9.class);
                    }
                    break block4;
                }
                ChunkType1_9_1 chunkType = ChunkType1_9_1.forEnvironment(clientWorld.getEnvironment());
                wrapper.write(chunkType, chunk);
                clientWorld.getLoadedChunks().add(chunkHash);
                if (Via.getConfig().isChunkBorderFix()) {
                    for (BlockFace face : BlockFace.HORIZONTAL) {
                        int chunkX = chunk.getX() + face.modX();
                        int chunkZ = chunk.getZ() + face.modZ();
                        if (clientWorld.getLoadedChunks().contains(ClientWorld1_9.toLong(chunkX, chunkZ))) continue;
                        PacketWrapper emptyChunk = wrapper.create(ClientboundPackets1_9.LEVEL_CHUNK);
                        BaseChunk c = new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], new int[256], new ArrayList<CompoundTag>());
                        emptyChunk.write(chunkType, c);
                        emptyChunk.send(Protocol1_8To1_9.class);
                    }
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.MAP_BULK_CHUNK, null, wrapper -> {
            wrapper.cancel();
            ClientWorld1_9 clientWorld = (ClientWorld1_9)wrapper.user().getClientWorld(Protocol1_8To1_9.class);
            Chunk[] chunks = wrapper.read(BulkChunkType1_8.TYPE);
            ChunkType1_9_1 chunkType = ChunkType1_9_1.forEnvironment(clientWorld.getEnvironment());
            for (Chunk chunk : chunks) {
                PacketWrapper chunkData = wrapper.create(ClientboundPackets1_9.LEVEL_CHUNK);
                chunkData.write(chunkType, chunk);
                chunkData.send(Protocol1_8To1_9.class);
                clientWorld.getLoadedChunks().add(ClientWorld1_9.toLong(chunk.getX(), chunk.getZ()));
                if (!Via.getConfig().isChunkBorderFix()) continue;
                for (BlockFace face : BlockFace.HORIZONTAL) {
                    int chunkX = chunk.getX() + face.modX();
                    int chunkZ = chunk.getZ() + face.modZ();
                    if (clientWorld.getLoadedChunks().contains(ClientWorld1_9.toLong(chunkX, chunkZ))) continue;
                    PacketWrapper emptyChunk = wrapper.create(ClientboundPackets1_9.LEVEL_CHUNK);
                    BaseChunk c = new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], new int[256], new ArrayList<CompoundTag>());
                    emptyChunk.write(chunkType, c);
                    emptyChunk.send(Protocol1_8To1_9.class);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_8.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    CompoundTag tag;
                    short action = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    if (action == 1 && (tag = wrapper.get(Types.NAMED_COMPOUND_TAG, 0)) != null) {
                        StringTag entityId = tag.getStringTag("EntityId");
                        if (entityId != null) {
                            String entity = entityId.getValue();
                            CompoundTag spawn = new CompoundTag();
                            spawn.putString("id", entity);
                            tag.put("SpawnData", spawn);
                        } else {
                            CompoundTag spawn = new CompoundTag();
                            spawn.putString("id", "AreaEffectCloud");
                            tag.put("SpawnData", spawn);
                        }
                    }
                    if (action == 2) {
                        CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                        provider.addOrUpdateBlock(wrapper.user(), wrapper.get(Types.BLOCK_POSITION1_8, 0), wrapper.get(Types.NAMED_COMPOUND_TAG, 0));
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.SIGN_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.handler(wrapper -> {
                    for (int i = 0; i < 4; ++i) {
                        String line = wrapper.read(Types.STRING);
                        wrapper.write(Types.COMPONENT, ComponentUtil.plainToJson(line));
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.PLAYER_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BLOCK_POSITION1_8);
                this.handler(wrapper -> {
                    int status = wrapper.get(Types.VAR_INT, 0);
                    if (status == 6) {
                        wrapper.cancel();
                    }
                });
                this.handler(wrapper -> {
                    EntityTracker1_9 entityTracker;
                    int status = wrapper.get(Types.VAR_INT, 0);
                    if ((status == 5 || status == 4 || status == 3) && (entityTracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class)).isBlocking()) {
                        entityTracker.setBlocking(false);
                        if (!Via.getConfig().isShowShieldWhenSwordInHand()) {
                            entityTracker.setSecondHand(null);
                        }
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9.USE_ITEM, null, wrapper -> {
            int hand = wrapper.read(Types.VAR_INT);
            wrapper.clearInputBuffer();
            wrapper.setPacketType(ServerboundPackets1_8.USE_ITEM_ON);
            wrapper.write(Types.BLOCK_POSITION1_8, new BlockPosition(-1, -1, -1));
            wrapper.write(Types.UNSIGNED_BYTE, (short)255);
            Item item = Via.getManager().getProviders().get(HandItemProvider.class).getHandItem(wrapper.user());
            if (Via.getConfig().isShieldBlocking()) {
                boolean isSword;
                EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                boolean showShieldWhenSwordInHand = Via.getConfig().isShowShieldWhenSwordInHand();
                boolean bl = showShieldWhenSwordInHand ? tracker.hasSwordInHand() : (isSword = item != null && Protocol1_8To1_9.isSword(item.identifier()));
                if (isSword) {
                    boolean blockUsingMainHand;
                    if (hand == 0 && !tracker.isBlocking()) {
                        tracker.setBlocking(true);
                        if (!showShieldWhenSwordInHand && tracker.getItemInSecondHand() == null) {
                            DataItem shield = new DataItem(442, 1, 0, null);
                            tracker.setSecondHand(shield);
                        }
                    }
                    boolean bl2 = blockUsingMainHand = Via.getConfig().isNoDelayShieldBlocking() && !showShieldWhenSwordInHand;
                    if (blockUsingMainHand && hand == 1 || !blockUsingMainHand && hand == 0) {
                        wrapper.cancel();
                    }
                } else {
                    if (!showShieldWhenSwordInHand) {
                        tracker.setSecondHand(null);
                    }
                    tracker.setBlocking(false);
                }
            }
            wrapper.write(Types.ITEM1_8, item);
            wrapper.write(Types.UNSIGNED_BYTE, (short)0);
            wrapper.write(Types.UNSIGNED_BYTE, (short)0);
            wrapper.write(Types.UNSIGNED_BYTE, (short)0);
        });
        protocol.registerServerbound(ServerboundPackets1_9.USE_ITEM_ON, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.VAR_INT, Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    int hand = wrapper.read(Types.VAR_INT);
                    if (hand != 0) {
                        wrapper.cancel();
                    }
                });
                this.handler(wrapper -> {
                    Item item = Via.getManager().getProviders().get(HandItemProvider.class).getHandItem(wrapper.user());
                    wrapper.write(Types.ITEM1_8, item);
                });
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    CommandBlockProvider provider = Via.getManager().getProviders().get(CommandBlockProvider.class);
                    BlockPosition pos = wrapper.get(Types.BLOCK_POSITION1_8, 0);
                    Optional<CompoundTag> tag = provider.get(wrapper.user(), pos);
                    if (tag.isPresent()) {
                        PacketWrapper updateBlockEntity = PacketWrapper.create(ClientboundPackets1_9.BLOCK_ENTITY_DATA, null, wrapper.user());
                        updateBlockEntity.write(Types.BLOCK_POSITION1_8, pos);
                        updateBlockEntity.write(Types.UNSIGNED_BYTE, (short)2);
                        updateBlockEntity.write(Types.NAMED_COMPOUND_TAG, tag.get());
                        updateBlockEntity.scheduleSend(Protocol1_8To1_9.class);
                    }
                });
                if (!Via.getConfig().cancelBlockSounds()) {
                    return;
                }
                this.handler(wrapper -> {
                    short face = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    if (face == 255) {
                        return;
                    }
                    BlockPosition p = wrapper.get(Types.BLOCK_POSITION1_8, 0);
                    int x = p.x();
                    int y = p.y();
                    int z = p.z();
                    switch (face) {
                        case 0: {
                            --y;
                            break;
                        }
                        case 1: {
                            ++y;
                            break;
                        }
                        case 2: {
                            --z;
                            break;
                        }
                        case 3: {
                            ++z;
                            break;
                        }
                        case 4: {
                            --x;
                            break;
                        }
                        case 5: {
                            ++x;
                        }
                    }
                    EntityTracker1_9 tracker = (EntityTracker1_9)wrapper.user().getEntityTracker(Protocol1_8To1_9.class);
                    tracker.addBlockInteraction(new BlockPosition(x, y, z));
                });
            }
        });
    }
}

