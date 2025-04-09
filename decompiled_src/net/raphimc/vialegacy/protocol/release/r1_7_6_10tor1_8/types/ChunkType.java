/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.IdAndData;
import com.viaversion.viaversion.util.Pair;
import io.netty.buffer.ByteBuf;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.ExtendedBlockStorage;

public class ChunkType
extends Type<Chunk> {
    private final boolean hasSkyLight;

    public ChunkType(boolean hasSkyLight) {
        super(Chunk.class);
        this.hasSkyLight = hasSkyLight;
    }

    protected void readUnusedInt(ByteBuf byteBuf) {
    }

    protected void writeUnusedInt(ByteBuf byteBuf, Chunk chunk) {
    }

    @Override
    public Chunk read(ByteBuf byteBuf) {
        int chunkX = byteBuf.readInt();
        int chunkZ = byteBuf.readInt();
        boolean fullChunk = byteBuf.readBoolean();
        short primaryBitMask = byteBuf.readShort();
        short additionalBitMask = byteBuf.readShort();
        int compressedSize = byteBuf.readInt();
        this.readUnusedInt(byteBuf);
        byte[] data = new byte[compressedSize];
        byteBuf.readBytes(data);
        byte[] uncompressedData = new byte[ChunkType.getSize(primaryBitMask, additionalBitMask, fullChunk, this.hasSkyLight)];
        Inflater inflater = new Inflater();
        try {
            inflater.setInput(data, 0, compressedSize);
            inflater.inflate(uncompressedData);
        }
        catch (DataFormatException ex) {
            throw new RuntimeException("Bad compressed data format");
        }
        finally {
            inflater.end();
        }
        if (fullChunk && primaryBitMask == 0) {
            return new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList<CompoundTag>());
        }
        return ChunkType.deserialize(chunkX, chunkZ, fullChunk, this.hasSkyLight, primaryBitMask, additionalBitMask, uncompressedData);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf byteBuf, Chunk chunk) {
        int compressedSize;
        byte[] compressedData;
        Pair<byte[], Short> chunkData = ChunkType.serialize(chunk);
        byte[] data = chunkData.key();
        short additionalBitMask = chunkData.value();
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(data, 0, data.length);
            deflater.finish();
            compressedData = new byte[data.length];
            compressedSize = deflater.deflate(compressedData);
        }
        finally {
            deflater.end();
        }
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        byteBuf.writeShort(chunk.getBitmask());
        byteBuf.writeShort((int)additionalBitMask);
        byteBuf.writeInt(compressedSize);
        this.writeUnusedInt(byteBuf, chunk);
        byteBuf.writeBytes(compressedData, 0, compressedSize);
    }

    public static Chunk deserialize(int chunkX, int chunkZ, boolean fullChunk, boolean skyLight, int primaryBitMask, int additionalBitMask, byte[] chunkData) {
        NibbleArray nibbleArray;
        int i;
        ExtendedBlockStorage[] storageArrays = new ExtendedBlockStorage[16];
        int dataPosition = 0;
        for (i = 0; i < storageArrays.length; ++i) {
            if ((primaryBitMask & 1 << i) == 0) continue;
            if (storageArrays[i] == null) {
                storageArrays[i] = new ExtendedBlockStorage(skyLight);
            }
            byte[] lsbArray = storageArrays[i].getBlockLSBArray();
            System.arraycopy(chunkData, dataPosition, lsbArray, 0, lsbArray.length);
            dataPosition += lsbArray.length;
        }
        for (i = 0; i < storageArrays.length; ++i) {
            if ((primaryBitMask & 1 << i) == 0 || storageArrays[i] == null) continue;
            nibbleArray = storageArrays[i].getBlockMetadataArray();
            System.arraycopy(chunkData, dataPosition, nibbleArray.getHandle(), 0, nibbleArray.getHandle().length);
            dataPosition += nibbleArray.getHandle().length;
        }
        for (i = 0; i < storageArrays.length; ++i) {
            if ((primaryBitMask & 1 << i) == 0 || storageArrays[i] == null) continue;
            nibbleArray = storageArrays[i].getBlockLightArray();
            System.arraycopy(chunkData, dataPosition, nibbleArray.getHandle(), 0, nibbleArray.getHandle().length);
            dataPosition += nibbleArray.getHandle().length;
        }
        if (skyLight) {
            for (i = 0; i < storageArrays.length; ++i) {
                if ((primaryBitMask & 1 << i) == 0 || storageArrays[i] == null) continue;
                nibbleArray = storageArrays[i].getSkyLightArray();
                System.arraycopy(chunkData, dataPosition, nibbleArray.getHandle(), 0, nibbleArray.getHandle().length);
                dataPosition += nibbleArray.getHandle().length;
            }
        }
        for (i = 0; i < storageArrays.length; ++i) {
            if ((additionalBitMask & 1 << i) == 0) continue;
            if (storageArrays[i] != null) {
                nibbleArray = storageArrays[i].getOrCreateBlockMSBArray();
                System.arraycopy(chunkData, dataPosition, nibbleArray.getHandle(), 0, nibbleArray.getHandle().length);
                dataPosition += nibbleArray.getHandle().length;
                continue;
            }
            dataPosition += 2048;
        }
        int[] biomeData = null;
        if (fullChunk) {
            biomeData = new int[256];
            for (int i2 = 0; i2 < biomeData.length; ++i2) {
                biomeData[i2] = chunkData[dataPosition + i2] & 0xFF;
            }
            dataPosition += biomeData.length;
        }
        ChunkSection[] sections = new ChunkSection[16];
        for (int i3 = 0; i3 < storageArrays.length; ++i3) {
            ExtendedBlockStorage storage = storageArrays[i3];
            if (storage == null) continue;
            sections[i3] = new ChunkSectionImpl(true);
            ChunkSectionImpl section = sections[i3];
            section.palette(PaletteType.BLOCKS).addId(0);
            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    for (int y = 0; y < 16; ++y) {
                        section.palette(PaletteType.BLOCKS).setIdAt(x, y, z, IdAndData.toRawData(storage.getBlockId(x, y, z), storage.getBlockMetadata(x, y, z)));
                    }
                }
            }
            section.getLight().setBlockLight(storage.getBlockLightArray().getHandle());
            if (!skyLight) continue;
            section.getLight().setSkyLight(storage.getSkyLightArray().getHandle());
        }
        return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitMask, sections, biomeData, new ArrayList<CompoundTag>());
    }

    public static Pair<byte[], Short> serialize(Chunk chunk) {
        int i;
        ExtendedBlockStorage[] storageArrays = new ExtendedBlockStorage[16];
        for (int i2 = 0; i2 < storageArrays.length; ++i2) {
            ChunkSection section = chunk.getSections()[i2];
            if (section == null) continue;
            ExtendedBlockStorage storage = storageArrays[i2] = new ExtendedBlockStorage(section.getLight().hasSkyLight());
            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    for (int y = 0; y < 16; ++y) {
                        int flatBlock = section.palette(PaletteType.BLOCKS).idAt(x, y, z);
                        storage.setBlockId(x, y, z, flatBlock >> 4);
                        storage.setBlockMetadata(x, y, z, flatBlock & 0xF);
                    }
                }
            }
            storage.getBlockLightArray().setHandle(section.getLight().getBlockLight());
            if (!section.getLight().hasSkyLight()) continue;
            storage.getSkyLightArray().setHandle(section.getLight().getSkyLight());
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (i = 0; i < storageArrays.length; ++i) {
            if ((chunk.getBitmask() & 1 << i) == 0) continue;
            output.writeBytes(storageArrays[i].getBlockLSBArray());
        }
        for (i = 0; i < storageArrays.length; ++i) {
            if ((chunk.getBitmask() & 1 << i) == 0) continue;
            output.writeBytes(storageArrays[i].getBlockMetadataArray().getHandle());
        }
        for (i = 0; i < storageArrays.length; ++i) {
            if ((chunk.getBitmask() & 1 << i) == 0) continue;
            output.writeBytes(storageArrays[i].getBlockLightArray().getHandle());
        }
        for (i = 0; i < storageArrays.length; ++i) {
            if ((chunk.getBitmask() & 1 << i) == 0 || storageArrays[i].getSkyLightArray() == null) continue;
            output.writeBytes(storageArrays[i].getSkyLightArray().getHandle());
        }
        short additionalBitMask = 0;
        for (int i3 = 0; i3 < storageArrays.length; ++i3) {
            if ((chunk.getBitmask() & 1 << i3) == 0 || !storageArrays[i3].hasBlockMSBArray()) continue;
            additionalBitMask = (short)(additionalBitMask | 1 << i3);
            output.writeBytes(storageArrays[i3].getOrCreateBlockMSBArray().getHandle());
        }
        if (chunk.isFullChunk() && chunk.getBiomeData() != null) {
            for (int biome : chunk.getBiomeData()) {
                output.write(biome);
            }
        }
        return new Pair<byte[], Short>(output.toByteArray(), additionalBitMask);
    }

    public static int getSize(short primaryBitMask, short additionalBitMask, boolean fullChunk, boolean skyLight) {
        int primarySectionCount = Integer.bitCount(primaryBitMask & 0xFFFF);
        int additionalSectionCount = Integer.bitCount(additionalBitMask & 0xFFFF);
        int size = 8192 * primarySectionCount + 2048 * additionalSectionCount;
        if (skyLight) {
            size += 2048 * primarySectionCount;
        }
        if (fullChunk) {
            size += 256;
        }
        return size;
    }
}

