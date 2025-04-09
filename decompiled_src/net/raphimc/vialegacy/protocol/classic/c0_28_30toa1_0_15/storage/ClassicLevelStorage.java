/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.util.ChunkUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.model.ChunkCoord;
import net.raphimc.vialegacy.api.util.ChunkCoordSpiral;
import net.raphimc.vialegacy.protocol.alpha.a1_0_15toa1_0_16_2.packet.ClientboundPacketsa1_0_15;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.Protocolc0_28_30Toa1_0_15;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.model.ClassicLevel;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.provider.ClassicWorldHeightProvider;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicBlockRemapper;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicPositionTracker;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model.LegacyNibbleArray;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types.Types1_1;

public class ClassicLevelStorage
extends StoredObject {
    private ByteArrayOutputStream netBuffer = new ByteArrayOutputStream(262144);
    private ClassicLevel classicLevel;
    private int chunkXCount;
    private int sectionYCount;
    private int chunkZCount;
    private int subChunkXLength;
    private int subChunkYLength;
    private int subChunkZLength;
    private int sectionBitmask;
    private final Set<ChunkCoord> loadedChunks = new HashSet<ChunkCoord>();
    private long eventLoopPing = 0L;

    public ClassicLevelStorage(UserConnection user) {
        super(user);
    }

    public void addDataPart(byte[] part, int partSize) {
        if (this.netBuffer == null) {
            throw new IllegalStateException("Level is already fully loaded");
        }
        this.netBuffer.write(part, 0, partSize);
    }

    public void finish(int sizeX, int sizeY, int sizeZ) {
        try {
            DataInputStream dis = new DataInputStream(new GZIPInputStream((InputStream)new ByteArrayInputStream(this.netBuffer.toByteArray()), 65536));
            byte[] blocks = new byte[dis.readInt()];
            dis.readFully(blocks);
            dis.close();
            this.netBuffer = null;
            this.classicLevel = new ClassicLevel(sizeX, sizeY, sizeZ, blocks);
        }
        catch (Throwable e) {
            throw new IllegalStateException("Failed to load level", e);
        }
        short maxChunkSectionCount = Via.getManager().getProviders().get(ClassicWorldHeightProvider.class).getMaxChunkSectionCount(this.user());
        this.chunkXCount = sizeX >> 4;
        if (sizeX % 16 != 0) {
            ++this.chunkXCount;
        }
        this.sectionYCount = sizeY >> 4;
        if (sizeY % 16 != 0) {
            ++this.sectionYCount;
        }
        if (this.sectionYCount > maxChunkSectionCount) {
            this.sectionYCount = maxChunkSectionCount;
        }
        this.chunkZCount = sizeZ >> 4;
        if (sizeZ % 16 != 0) {
            ++this.chunkZCount;
        }
        this.subChunkXLength = Math.min(16, sizeX);
        this.subChunkYLength = Math.min(16, sizeY);
        this.subChunkZLength = Math.min(16, sizeZ);
        this.sectionBitmask = 0;
        for (int i = 0; i < this.sectionYCount; ++i) {
            this.sectionBitmask = this.sectionBitmask << 1 | 1;
        }
        for (int chunkX = -1; chunkX <= this.chunkXCount; ++chunkX) {
            for (int chunkZ = -1; chunkZ <= this.chunkZCount; ++chunkZ) {
                if (chunkX >= 0 && chunkX < this.chunkXCount && chunkZ >= 0 && chunkZ < this.chunkZCount) continue;
                Chunk chunk = ChunkUtil.createEmptyChunk(chunkX, chunkZ, Math.max(8, this.sectionYCount), this.sectionBitmask);
                ChunkUtil.setDummySkylight(chunk, true);
                PacketWrapper chunkData = PacketWrapper.create(ClientboundPacketsa1_0_15.LEVEL_CHUNK, this.user());
                chunkData.write(Types1_1.CHUNK, chunk);
                chunkData.send(Protocolc0_28_30Toa1_0_15.class);
            }
        }
    }

    public void tick() {
        ClassicPositionTracker positionTracker = this.user().get(ClassicPositionTracker.class);
        if (!positionTracker.spawned) {
            return;
        }
        long start = System.currentTimeMillis();
        this.user().getChannel().eventLoop().submit(() -> {
            this.eventLoopPing = System.currentTimeMillis() - start;
        });
        int limit = 0;
        if (this.eventLoopPing < 50L) {
            limit = 12;
        } else if (this.eventLoopPing < 100L) {
            limit = 6;
        } else if (this.eventLoopPing < 250L) {
            limit = 3;
        } else if (this.eventLoopPing < 400L) {
            limit = 1;
        }
        if (limit != 0) {
            this.sendChunks(positionTracker.getChunkPosition(), ViaLegacy.getConfig().getClassicChunkRange(), limit);
        }
    }

    public void sendChunks(ChunkCoord center, int radius) {
        this.sendChunks(center, radius, Integer.MAX_VALUE);
    }

    public void sendChunks(ChunkCoord center, int radius, int limit) {
        ChunkCoordSpiral spiral = new ChunkCoordSpiral(center, new ChunkCoord(radius, radius));
        for (ChunkCoord coord : spiral) {
            if (!this.shouldSend(coord)) continue;
            if (limit-- <= 0) {
                return;
            }
            this.sendChunk(coord);
        }
    }

    public void sendChunk(ChunkCoord coord) {
        if (!this.shouldSend(coord)) {
            return;
        }
        ClassicBlockRemapper remapper = this.user().get(ClassicBlockRemapper.class);
        this.classicLevel.calculateLight(coord.chunkX * 16, coord.chunkZ * 16, this.subChunkXLength, this.subChunkZLength);
        ChunkSection[] modernSections = new ChunkSection[Math.max(8, this.sectionYCount)];
        for (int sectionY = 0; sectionY < this.sectionYCount; ++sectionY) {
            modernSections[sectionY] = new ChunkSectionImpl(true);
            ChunkSectionImpl section = modernSections[sectionY];
            section.palette(PaletteType.BLOCKS).addId(0);
            LegacyNibbleArray skyLight = new LegacyNibbleArray(4096, 4);
            for (int y = 0; y < this.subChunkYLength; ++y) {
                int totalY = y + sectionY * 16;
                for (int x = 0; x < this.subChunkXLength; ++x) {
                    int totalX = x + coord.chunkX * 16;
                    for (int z = 0; z < this.subChunkZLength; ++z) {
                        int totalZ = z + coord.chunkZ * 16;
                        section.palette(PaletteType.BLOCKS).setIdAt(x, y, z, remapper.mapper().get(this.classicLevel.getBlock(totalX, totalY, totalZ)).toRawData());
                        skyLight.set(x, y, z, this.classicLevel.isLit(totalX, totalY, totalZ) ? 15 : 9);
                    }
                }
            }
            section.getLight().setSkyLight(skyLight.getHandle());
        }
        this.loadedChunks.add(coord);
        BaseChunk viaChunk = new BaseChunk(coord.chunkX, coord.chunkZ, true, false, this.sectionBitmask, modernSections, new int[256], new ArrayList<CompoundTag>());
        PacketWrapper chunkData = PacketWrapper.create(ClientboundPacketsa1_0_15.LEVEL_CHUNK, this.user());
        chunkData.write(Types1_1.CHUNK, viaChunk);
        chunkData.send(Protocolc0_28_30Toa1_0_15.class);
    }

    private boolean shouldSend(ChunkCoord coord) {
        if (!this.hasReceivedLevel()) {
            return false;
        }
        boolean isInBounds = coord.chunkX >= 0 && coord.chunkX < this.chunkXCount && coord.chunkZ >= 0 && coord.chunkZ < this.chunkZCount;
        return isInBounds && !this.isChunkLoaded(coord);
    }

    public boolean isChunkLoaded(ChunkCoord coord) {
        return this.loadedChunks.contains(coord);
    }

    public boolean isChunkLoaded(BlockPosition position) {
        return this.isChunkLoaded(new ChunkCoord(position.x() >> 4, position.z() >> 4));
    }

    public boolean hasReceivedLevel() {
        return this.classicLevel != null;
    }

    public ClassicLevel getClassicLevel() {
        return this.classicLevel;
    }
}

