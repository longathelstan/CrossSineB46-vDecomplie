/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_13;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.NamedSoundMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.ParticleIdMappings1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.BlockEntityProvider;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.PaintingProvider;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.storage.BlockStorage;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.util.IdAndData;
import com.viaversion.viaversion.util.Key;
import java.util.Iterator;
import java.util.Optional;

public class WorldPacketRewriter1_13 {
    static final IntSet VALID_BIOMES;

    public static void register(Protocol1_12_2To1_13 protocol) {
        protocol.registerClientbound(ClientboundPackets1_12_1.ADD_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.UUID);
                this.handler(wrapper -> {
                    String motive;
                    PaintingProvider provider = Via.getManager().getProviders().get(PaintingProvider.class);
                    Optional<Integer> id = provider.getIntByIdentifier(motive = wrapper.read(Types.STRING));
                    if (id.isPresent() ^ true && !Via.getConfig().isSuppressConversionWarnings()) {
                        String string = motive;
                        Protocol1_12_2To1_13.LOGGER.warning("Could not find painting motive: " + string + " falling back to default (0)");
                    }
                    wrapper.write(Types.VAR_INT, id.orElse(0));
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.NAMED_COMPOUND_TAG);
                this.handler(wrapper -> {
                    BlockStorage storage;
                    BlockStorage.ReplacementData replacementData;
                    BlockPosition position = wrapper.get(Types.BLOCK_POSITION1_8, 0);
                    short action = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    CompoundTag tag = wrapper.get(Types.NAMED_COMPOUND_TAG, 0);
                    BlockEntityProvider provider = Via.getManager().getProviders().get(BlockEntityProvider.class);
                    int newId = provider.transform(wrapper.user(), position, tag, true);
                    if (newId != -1 && (replacementData = (storage = wrapper.user().get(BlockStorage.class)).get(position)) != null) {
                        replacementData.setReplacement(newId);
                    }
                    if (action == 5) {
                        wrapper.cancel();
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    BlockPosition pos = wrapper.get(Types.BLOCK_POSITION1_8, 0);
                    short action = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    short param = wrapper.get(Types.UNSIGNED_BYTE, 1);
                    int blockId = wrapper.get(Types.VAR_INT, 0);
                    if (blockId == 25) {
                        blockId = 73;
                    } else if (blockId == 33) {
                        blockId = 99;
                    } else if (blockId == 29) {
                        blockId = 92;
                    } else if (blockId == 54) {
                        blockId = 142;
                    } else if (blockId == 146) {
                        blockId = 305;
                    } else if (blockId == 130) {
                        blockId = 249;
                    } else if (blockId == 138) {
                        blockId = 257;
                    } else if (blockId == 52) {
                        blockId = 140;
                    } else if (blockId == 209) {
                        blockId = 472;
                    } else if (blockId >= 219 && blockId <= 234) {
                        blockId = blockId - 219 + 483;
                    }
                    if (blockId == 73) {
                        PacketWrapper blockChange = wrapper.create(ClientboundPackets1_13.BLOCK_UPDATE);
                        blockChange.write(Types.BLOCK_POSITION1_8, pos);
                        blockChange.write(Types.VAR_INT, 249 + action * 24 * 2 + param * 2);
                        blockChange.send(Protocol1_12_2To1_13.class);
                    }
                    wrapper.set(Types.VAR_INT, 0, blockId);
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    BlockPosition position = wrapper.get(Types.BLOCK_POSITION1_8, 0);
                    int newId = WorldPacketRewriter1_13.toNewId(wrapper.get(Types.VAR_INT, 0));
                    UserConnection userConnection = wrapper.user();
                    if (Via.getConfig().isServersideBlockConnections()) {
                        newId = ConnectionData.connect(userConnection, position, newId);
                        ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), newId);
                    }
                    wrapper.set(Types.VAR_INT, 0, WorldPacketRewriter1_13.checkStorage(wrapper.user(), position, newId));
                    if (Via.getConfig().isServersideBlockConnections()) {
                        wrapper.send(Protocol1_12_2To1_13.class);
                        wrapper.cancel();
                        ConnectionData.update(userConnection, position);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.CHUNK_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BLOCK_CHANGE_ARRAY);
                this.handler(wrapper -> {
                    BlockPosition position;
                    BlockChangeRecord[] records;
                    int chunkX = wrapper.get(Types.INT, 0);
                    int chunkZ = wrapper.get(Types.INT, 1);
                    UserConnection userConnection = wrapper.user();
                    for (BlockChangeRecord record : records = wrapper.get(Types.BLOCK_CHANGE_ARRAY, 0)) {
                        int newBlock = WorldPacketRewriter1_13.toNewId(record.getBlockId());
                        position = new BlockPosition(record.getSectionX() + (chunkX << 4), record.getY(), record.getSectionZ() + (chunkZ << 4));
                        record.setBlockId(WorldPacketRewriter1_13.checkStorage(wrapper.user(), position, newBlock));
                        if (!Via.getConfig().isServersideBlockConnections()) continue;
                        ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), newBlock);
                    }
                    if (Via.getConfig().isServersideBlockConnections()) {
                        for (BlockChangeRecord record : records) {
                            int blockState = record.getBlockId();
                            position = new BlockPosition(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                            ConnectionHandler handler = ConnectionData.getConnectionHandler(blockState);
                            if (handler == null) continue;
                            blockState = handler.connect(userConnection, position, blockState);
                            record.setBlockId(blockState);
                            ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), blockState);
                        }
                        wrapper.send(Protocol1_12_2To1_13.class);
                        wrapper.cancel();
                        for (BlockChangeRecord record : records) {
                            BlockPosition position2 = new BlockPosition(record.getSectionX() + chunkX * 16, record.getY(), record.getSectionZ() + chunkZ * 16);
                            ConnectionData.update(userConnection, position2);
                        }
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.EXPLODE, new PacketHandlers(){

            @Override
            public void register() {
                if (!Via.getConfig().isServersideBlockConnections()) {
                    return;
                }
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int i;
                    UserConnection userConnection = wrapper.user();
                    int x = (int)Math.floor(wrapper.get(Types.FLOAT, 0).floatValue());
                    int y = (int)Math.floor(wrapper.get(Types.FLOAT, 1).floatValue());
                    int z = (int)Math.floor(wrapper.get(Types.FLOAT, 2).floatValue());
                    int recordCount = wrapper.get(Types.INT, 0);
                    BlockPosition[] records = new BlockPosition[recordCount];
                    for (i = 0; i < recordCount; ++i) {
                        BlockPosition position;
                        records[i] = position = new BlockPosition(x + wrapper.passthrough(Types.BYTE), (short)(y + wrapper.passthrough(Types.BYTE)), z + wrapper.passthrough(Types.BYTE));
                        ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), 0);
                    }
                    wrapper.send(Protocol1_12_2To1_13.class);
                    wrapper.cancel();
                    for (i = 0; i < recordCount; ++i) {
                        ConnectionData.update(userConnection, records[i]);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.FORGET_LEVEL_CHUNK, new PacketHandlers(){

            @Override
            public void register() {
                if (Via.getConfig().isServersideBlockConnections()) {
                    this.handler(wrapper -> {
                        int x = wrapper.passthrough(Types.INT);
                        int z = wrapper.passthrough(Types.INT);
                        ConnectionData.blockConnectionProvider.unloadChunk(wrapper.user(), x, z);
                    });
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.CUSTOM_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    String sound = Key.stripMinecraftNamespace(wrapper.get(Types.STRING, 0));
                    String newSoundId = NamedSoundMappings1_13.getNewId(sound);
                    wrapper.set(Types.STRING, 0, newSoundId);
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.LEVEL_CHUNK, wrapper -> {
            Object clientWorld = wrapper.user().getClientWorld(Protocol1_12_2To1_13.class);
            BlockStorage storage = wrapper.user().get(BlockStorage.class);
            ChunkType1_9_3 type = ChunkType1_9_3.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            ChunkType1_13 type1_13 = ChunkType1_13.forEnvironment(((ClientWorld)clientWorld).getEnvironment());
            Chunk chunk = wrapper.read(type);
            wrapper.write(type1_13, chunk);
            for (int s = 0; s < chunk.getSections().length; ++s) {
                int p;
                boolean willSave;
                DataPalette blocks;
                block21: {
                    block20: {
                        ChunkSection section = chunk.getSections()[s];
                        if (section == null) continue;
                        blocks = section.palette(PaletteType.BLOCKS);
                        for (int p2 = 0; p2 < blocks.size(); ++p2) {
                            int old = blocks.idByIndex(p2);
                            int newId = WorldPacketRewriter1_13.toNewId(old);
                            blocks.setIdByIndex(p2, newId);
                        }
                        if (!chunk.isFullChunk()) break block20;
                        willSave = false;
                        for (p = 0; p < blocks.size(); ++p) {
                            if (!storage.isWelcome(blocks.idByIndex(p))) continue;
                            willSave = true;
                            break;
                        }
                        if (!willSave) break block21;
                    }
                    for (int idx = 0; idx < 4096; ++idx) {
                        int id = blocks.idAt(idx);
                        BlockPosition position = new BlockPosition(ChunkSection.xFromIndex(idx) + (chunk.getX() << 4), ChunkSection.yFromIndex(idx) + (s << 4), ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4));
                        if (storage.isWelcome(id)) {
                            storage.store(position, id);
                            continue;
                        }
                        if (chunk.isFullChunk()) continue;
                        storage.remove(position);
                    }
                }
                if (!Via.getConfig().isServersideBlockConnections() || !ConnectionData.needStoreBlocks()) continue;
                if (!chunk.isFullChunk()) {
                    ConnectionData.blockConnectionProvider.unloadChunkSection(wrapper.user(), chunk.getX(), s, chunk.getZ());
                }
                willSave = false;
                for (p = 0; p < blocks.size(); ++p) {
                    if (!ConnectionData.isWelcome(blocks.idByIndex(p))) continue;
                    willSave = true;
                    break;
                }
                if (!willSave) continue;
                for (int idx = 0; idx < 4096; ++idx) {
                    int id = blocks.idAt(idx);
                    if (!ConnectionData.isWelcome(id)) continue;
                    int globalX = ChunkSection.xFromIndex(idx) + (chunk.getX() << 4);
                    int globalY = ChunkSection.yFromIndex(idx) + (s << 4);
                    int globalZ = ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4);
                    ConnectionData.blockConnectionProvider.storeBlock(wrapper.user(), globalX, globalY, globalZ, id);
                }
            }
            if (chunk.isBiomeData()) {
                int latestBiomeWarn = Integer.MIN_VALUE;
                for (int i = 0; i < 256; ++i) {
                    int biome = chunk.getBiomeData()[i];
                    if (VALID_BIOMES.contains(biome)) continue;
                    if (biome != 255 && latestBiomeWarn != biome) {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            int n = biome;
                            Protocol1_12_2To1_13.LOGGER.warning("Received invalid biome id: " + n);
                        }
                        latestBiomeWarn = biome;
                    }
                    chunk.getBiomeData()[i] = 1;
                }
            }
            BlockEntityProvider provider = Via.getManager().getProviders().get(BlockEntityProvider.class);
            Iterator<CompoundTag> iterator2 = chunk.getBlockEntities().iterator();
            while (iterator2.hasNext()) {
                String id;
                StringTag idTag;
                CompoundTag tag = iterator2.next();
                int newId = provider.transform(wrapper.user(), null, tag, false);
                if (newId != -1) {
                    int z;
                    short y;
                    int x = tag.getNumberTag("x").asInt();
                    BlockPosition position = new BlockPosition(x, y = tag.getNumberTag("y").asShort(), z = tag.getNumberTag("z").asInt());
                    BlockStorage.ReplacementData replacementData = storage.get(position);
                    if (replacementData != null) {
                        replacementData.setReplacement(newId);
                    }
                    chunk.getSections()[y >> 4].palette(PaletteType.BLOCKS).setIdAt(x & 0xF, y & 0xF, z & 0xF, newId);
                }
                if ((idTag = tag.getStringTag("id")) == null || !(id = Key.namespaced(idTag.getValue())).equals("minecraft:noteblock") && !id.equals("minecraft:flower_pot")) continue;
                iterator2.remove();
            }
            if (Via.getConfig().isServersideBlockConnections()) {
                ConnectionData.connectBlocks(wrapper.user(), chunk);
                wrapper.send(Protocol1_12_2To1_13.class);
                wrapper.cancel();
                ConnectionData.NeighbourUpdater updater = new ConnectionData.NeighbourUpdater(wrapper.user());
                for (int i = 0; i < chunk.getSections().length; ++i) {
                    ChunkSection section = chunk.getSections()[i];
                    if (section == null) continue;
                    updater.updateChunkSectionNeighbours(chunk.getX(), chunk.getZ(), i);
                }
            }
        });
        protocol.registerClientbound(ClientboundPackets1_12_1.LEVEL_PARTICLES, new PacketHandlers(){

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
                    int particleId = wrapper.get(Types.INT, 0);
                    int dataCount = 0;
                    if (particleId == 37 || particleId == 38 || particleId == 46) {
                        dataCount = 1;
                    } else if (particleId == 36) {
                        dataCount = 2;
                    }
                    Integer[] data = new Integer[dataCount];
                    for (int i = 0; i < data.length; ++i) {
                        data[i] = wrapper.read(Types.VAR_INT);
                    }
                    Particle particle = ParticleIdMappings1_13.rewriteParticle(particleId, data);
                    if (particle == null || particle.id() == -1) {
                        wrapper.cancel();
                        return;
                    }
                    if (particle.id() == 11) {
                        int count = wrapper.get(Types.INT, 1);
                        float speed = wrapper.get(Types.FLOAT, 6).floatValue();
                        if (count == 0) {
                            wrapper.set(Types.INT, 1, 1);
                            wrapper.set(Types.FLOAT, 6, Float.valueOf(0.0f));
                            for (int i = 0; i < 3; ++i) {
                                float colorValue = wrapper.get(Types.FLOAT, i + 3).floatValue() * speed;
                                if (colorValue == 0.0f && i == 0) {
                                    colorValue = 1.0f;
                                }
                                particle.getArgument(i).setValue(Float.valueOf(colorValue));
                                wrapper.set(Types.FLOAT, i + 3, Float.valueOf(0.0f));
                            }
                        }
                    }
                    wrapper.set(Types.INT, 0, particle.id());
                    for (Particle.ParticleData<?> particleData : particle.getArguments()) {
                        particleData.write(wrapper);
                    }
                });
            }
        });
        protocol.registerServerbound(ServerboundPackets1_13.USE_ITEM_ON, wrapper -> {
            BlockPosition pos = wrapper.passthrough(Types.BLOCK_POSITION1_8);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.VAR_INT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            if (Via.getConfig().isServersideBlockConnections() && ConnectionData.needStoreBlocks()) {
                ConnectionData.markModified(wrapper.user(), pos);
            }
        });
        protocol.registerServerbound(ServerboundPackets1_13.PLAYER_ACTION, wrapper -> {
            int status = wrapper.passthrough(Types.VAR_INT);
            BlockPosition pos = wrapper.passthrough(Types.BLOCK_POSITION1_8);
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            if (status == 0 && Via.getConfig().isServersideBlockConnections() && ConnectionData.needStoreBlocks()) {
                ConnectionData.markModified(wrapper.user(), pos);
            }
        });
    }

    public static int toNewId(int oldId) {
        int newId;
        if (oldId < 0) {
            oldId = 0;
        }
        if ((newId = Protocol1_12_2To1_13.MAPPINGS.getBlockMappings().getNewId(oldId)) != -1) {
            return newId;
        }
        newId = Protocol1_12_2To1_13.MAPPINGS.getBlockMappings().getNewId(IdAndData.removeData(oldId));
        if (newId != -1) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                int n = oldId;
                Protocol1_12_2To1_13.LOGGER.warning("Missing block " + n);
            }
            return newId;
        }
        if (!Via.getConfig().isSuppressConversionWarnings()) {
            int n = oldId;
            Protocol1_12_2To1_13.LOGGER.warning("Missing block completely " + n);
        }
        return 0;
    }

    static int checkStorage(UserConnection user, BlockPosition position, int newId) {
        BlockStorage storage = user.get(BlockStorage.class);
        if (storage.contains(position)) {
            BlockStorage.ReplacementData data = storage.get(position);
            if (data.getOriginal() == newId) {
                if (data.getReplacement() != -1) {
                    return data.getReplacement();
                }
            } else {
                storage.remove(position);
                if (storage.isWelcome(newId)) {
                    storage.store(position, newId);
                }
            }
        } else if (storage.isWelcome(newId)) {
            storage.store(position, newId);
        }
        return newId;
    }

    static {
        int i;
        VALID_BIOMES = new IntOpenHashSet(70);
        for (i = 0; i < 50; ++i) {
            VALID_BIOMES.add(i);
        }
        VALID_BIOMES.add(127);
        for (i = 129; i <= 134; ++i) {
            VALID_BIOMES.add(i);
        }
        VALID_BIOMES.add(140);
        VALID_BIOMES.add(149);
        VALID_BIOMES.add(151);
        for (i = 155; i <= 158; ++i) {
            VALID_BIOMES.add(i);
        }
        for (i = 160; i <= 167; ++i) {
            VALID_BIOMES.add(i);
        }
    }
}

