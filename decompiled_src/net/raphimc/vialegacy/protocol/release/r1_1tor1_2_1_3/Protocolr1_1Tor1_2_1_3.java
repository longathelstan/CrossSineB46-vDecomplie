/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.IdAndData;
import java.util.Arrays;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.LegacyProtocolVersion;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.EndBiomeGenerator;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.NetherBiomeGenerator;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.PlainsBiomeGenerator;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.beta.WorldChunkManager_b1_7;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.biome.release.WorldChunkManager_r1_1;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model.LegacyNibbleArray;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model.NonFullChunk;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.packet.ClientboundPackets1_1;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.packet.ServerboundPackets1_1;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.storage.PendingBlocksTracker;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.storage.SeedStorage;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.task.BlockReceiveInvalidatorTask;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types.Types1_1;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.packet.ClientboundPackets1_2_1;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.packet.ServerboundPackets1_2_1;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.types.Types1_2_4;
import net.raphimc.vialegacy.protocol.release.r1_3_1_2tor1_4_2.types.Types1_3_1;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.storage.ChunkTracker;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_1Tor1_2_1_3
extends StatelessProtocol<ClientboundPackets1_1, ClientboundPackets1_2_1, ServerboundPackets1_1, ServerboundPackets1_2_1> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_1Tor1_2_1_3() {
        super(ClientboundPackets1_1.class, ClientboundPackets1_2_1.class, ServerboundPackets1_1.class, ServerboundPackets1_2_1.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientbound(ClientboundPackets1_1.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.handler(wrapper -> {
                    wrapper.user().get(SeedStorage.class).seed = wrapper.read(Types.LONG);
                });
                this.map(Types1_6_4.STRING);
                this.map(Types.INT);
                this.map(Types.BYTE, Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> Protocolr1_1Tor1_2_1_3.this.handleRespawn(wrapper.get(Types.INT, 2), wrapper.user()));
            }
        });
        this.registerClientbound(ClientboundPackets1_1.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE, Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.handler(wrapper -> {
                    wrapper.user().get(SeedStorage.class).seed = wrapper.read(Types.LONG);
                });
                this.map(Types1_6_4.STRING);
                this.handler(wrapper -> Protocolr1_1Tor1_2_1_3.this.handleRespawn(wrapper.get(Types.INT, 0), wrapper.user()));
            }
        });
        this.registerClientbound(ClientboundPackets1_1.ADD_MOB, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> wrapper.write(Types.BYTE, wrapper.get(Types.BYTE, 0)));
                this.map(Types1_3_1.ENTITY_DATA_LIST);
            }
        });
        this.registerClientbound(ClientboundPackets1_1.MOVE_ENTITY_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> Protocolr1_1Tor1_2_1_3.this.sendEntityHeadLook(wrapper.get(Types.INT, 0), wrapper.get(Types.BYTE, 0), wrapper));
            }
        });
        this.registerClientbound(ClientboundPackets1_1.MOVE_ENTITY_POS_ROT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> Protocolr1_1Tor1_2_1_3.this.sendEntityHeadLook(wrapper.get(Types.INT, 0), wrapper.get(Types.BYTE, 3), wrapper));
            }
        });
        this.registerClientbound(ClientboundPackets1_1.TELEPORT_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.handler(wrapper -> Protocolr1_1Tor1_2_1_3.this.sendEntityHeadLook(wrapper.get(Types.INT, 0), wrapper.get(Types.BYTE, 0), wrapper));
            }
        });
        this.registerClientbound(ClientboundPackets1_1.LEVEL_CHUNK, wrapper -> {
            int[] newBiomeData;
            ChunkTracker chunkTracker = wrapper.user().get(ChunkTracker.class);
            SeedStorage seedStorage = wrapper.user().get(SeedStorage.class);
            PendingBlocksTracker pendingBlocksTracker = wrapper.user().get(PendingBlocksTracker.class);
            Chunk chunk = wrapper.read(Types1_1.CHUNK);
            if (chunk instanceof NonFullChunk) {
                NonFullChunk nonFullChunk = (NonFullChunk)chunk;
                if (!chunkTracker.isChunkLoaded(chunk.getX(), chunk.getZ())) {
                    wrapper.cancel();
                    return;
                }
                wrapper.setPacketType(ClientboundPackets1_2_1.CHUNK_BLOCKS_UPDATE);
                wrapper.write(Types.INT, nonFullChunk.getX());
                wrapper.write(Types.INT, nonFullChunk.getZ());
                wrapper.write(Types1_7_6.BLOCK_CHANGE_RECORD_ARRAY, nonFullChunk.asBlockChangeRecords().toArray(new BlockChangeRecord[0]));
                pendingBlocksTracker.markReceived(new BlockPosition((nonFullChunk.getX() << 4) + nonFullChunk.getStartPos().x(), nonFullChunk.getStartPos().y(), (nonFullChunk.getZ() << 4) + nonFullChunk.getStartPos().z()), new BlockPosition((nonFullChunk.getX() << 4) + nonFullChunk.getEndPos().x() - 1, nonFullChunk.getEndPos().y() - 1, (nonFullChunk.getZ() << 4) + nonFullChunk.getEndPos().z() - 1));
                return;
            }
            pendingBlocksTracker.markReceived(new BlockPosition(chunk.getX() << 4, 0, chunk.getZ() << 4), new BlockPosition((chunk.getX() << 4) + 15, chunk.getSections().length * 16, (chunk.getZ() << 4) + 15));
            if (seedStorage.worldChunkManager != null) {
                byte[] byArray = seedStorage.worldChunkManager.getBiomeDataAt(chunk.getX(), chunk.getZ());
                newBiomeData = new int[byArray.length];
                for (int i = 0; i < byArray.length; ++i) {
                    newBiomeData[i] = byArray[i] & 0xFF;
                }
            } else {
                newBiomeData = new int[256];
                Arrays.fill(newBiomeData, 1);
            }
            chunk.setBiomeData(newBiomeData);
            for (ChunkSection section : chunk.getSections()) {
                if (section == null) continue;
                LegacyNibbleArray oldBlockLight = new LegacyNibbleArray(section.getLight().getBlockLight(), 4);
                NibbleArray newBlockLight = new NibbleArray(oldBlockLight.size());
                LegacyNibbleArray oldSkyLight = new LegacyNibbleArray(section.getLight().getSkyLight(), 4);
                NibbleArray newSkyLight = new NibbleArray(oldSkyLight.size());
                for (int x = 0; x < 16; ++x) {
                    for (int y = 0; y < 16; ++y) {
                        for (int z = 0; z < 16; ++z) {
                            newBlockLight.set(x, y, z, oldBlockLight.get(x, y, z));
                            newSkyLight.set(x, y, z, oldSkyLight.get(x, y, z));
                        }
                    }
                }
                section.getLight().setBlockLight(newBlockLight.getHandle());
                section.getLight().setSkyLight(newSkyLight.getHandle());
            }
            if (chunk.getSections().length < 16) {
                ChunkSection[] chunkSectionArray = new ChunkSection[16];
                System.arraycopy(chunk.getSections(), 0, chunkSectionArray, 0, chunk.getSections().length);
                chunk.setSections(chunkSectionArray);
            }
            wrapper.write(Types1_2_4.CHUNK, chunk);
        });
        this.registerClientbound(ClientboundPackets1_1.CHUNK_BLOCKS_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types1_1.BLOCK_CHANGE_RECORD_ARRAY, Types1_7_6.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(wrapper -> {
                    BlockChangeRecord[] blockChangeRecords;
                    PendingBlocksTracker pendingBlocksTracker = wrapper.user().get(PendingBlocksTracker.class);
                    int chunkX = wrapper.get(Types.INT, 0);
                    int chunkZ = wrapper.get(Types.INT, 1);
                    for (BlockChangeRecord record : blockChangeRecords = wrapper.get(Types1_7_6.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                        int targetX = record.getSectionX() + (chunkX << 4);
                        short targetY = record.getY(-1);
                        int targetZ = record.getSectionZ() + (chunkZ << 4);
                        pendingBlocksTracker.markReceived(new BlockPosition(targetX, targetY, targetZ));
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_1.BLOCK_UPDATE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> wrapper.user().get(PendingBlocksTracker.class).markReceived(wrapper.get(Types1_7_6.BLOCK_POSITION_UBYTE, 0)));
            }
        });
        this.registerClientbound(ClientboundPackets1_1.EXPLODE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    PendingBlocksTracker pendingBlocksTracker = wrapper.user().get(PendingBlocksTracker.class);
                    ChunkTracker chunkTracker = wrapper.user().get(ChunkTracker.class);
                    int x = wrapper.get(Types.DOUBLE, 0).intValue();
                    int y = wrapper.get(Types.DOUBLE, 1).intValue();
                    int z = wrapper.get(Types.DOUBLE, 2).intValue();
                    int recordCount = wrapper.get(Types.INT, 0);
                    for (int i = 0; i < recordCount; ++i) {
                        BlockPosition pos = new BlockPosition(x + wrapper.passthrough(Types.BYTE), y + wrapper.passthrough(Types.BYTE), z + wrapper.passthrough(Types.BYTE));
                        IdAndData block = chunkTracker.getBlockNotNull(pos);
                        if (block.getId() == 0) continue;
                        pendingBlocksTracker.addPending(pos, block);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_1.LEVEL_EVENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_7_6.BLOCK_POSITION_UBYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    int sfxId = wrapper.get(Types.INT, 0);
                    int sfxData = wrapper.get(Types.INT, 1);
                    if (sfxId == 2001) {
                        int blockID = sfxData & 0xFF;
                        int blockData = sfxData >> 8 & 0xFF;
                        wrapper.set(Types.INT, 1, blockID + (blockData << 12));
                    } else if (sfxId == 1009) {
                        wrapper.set(Types.INT, 0, 1008);
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_2_1.HANDSHAKE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, s -> s.split(";")[0]);
            }
        });
        this.registerServerbound(ServerboundPackets1_2_1.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.create(Types.LONG, 0L);
                this.map(Types1_6_4.STRING);
                this.map(Types.INT);
                this.map(Types.INT, Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
            }
        });
        this.registerServerbound(ServerboundPackets1_2_1.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT, Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.create(Types.LONG, 0L);
                this.map(Types1_6_4.STRING);
            }
        });
    }

    void handleRespawn(int dimensionId, UserConnection user) {
        if (((ClientWorld)user.getClientWorld(Protocolr1_1Tor1_2_1_3.class)).setEnvironment(dimensionId)) {
            user.get(PendingBlocksTracker.class).clear();
        }
        if (ViaLegacy.getConfig().isOldBiomes()) {
            SeedStorage seedStorage = user.get(SeedStorage.class);
            seedStorage.worldChunkManager = dimensionId == -1 ? new NetherBiomeGenerator() : (dimensionId == 1 ? new EndBiomeGenerator() : (dimensionId == 0 ? (user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.b1_8tob1_8_1) ? new WorldChunkManager_r1_1(user, seedStorage.seed) : (user.getProtocolInfo().serverProtocolVersion().newerThanOrEqualTo(LegacyProtocolVersion.a1_0_15) ? new WorldChunkManager_b1_7(seedStorage.seed) : new PlainsBiomeGenerator())) : null));
        }
    }

    void sendEntityHeadLook(int entityId, byte headYaw, PacketWrapper wrapper) {
        PacketWrapper entityHeadLook = PacketWrapper.create(ClientboundPackets1_2_1.ROTATE_HEAD, wrapper.user());
        entityHeadLook.write(Types.INT, entityId);
        entityHeadLook.write(Types.BYTE, headYaw);
        wrapper.send(Protocolr1_1Tor1_2_1_3.class);
        entityHeadLook.send(Protocolr1_1Tor1_2_1_3.class);
        wrapper.cancel();
    }

    @Override
    public void register(ViaProviders providers) {
        Via.getPlatform().runRepeatingSync(new BlockReceiveInvalidatorTask(), 1L);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_1Tor1_2_1_3.class, ClientboundPackets1_1::getPacket));
        userConnection.addClientWorld(Protocolr1_1Tor1_2_1_3.class, new ClientWorld());
        userConnection.put(new SeedStorage());
        userConnection.put(new PendingBlocksTracker(userConnection));
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

