/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;

public final class DataPaletteImpl
implements DataPalette {
    static final int DEFAULT_INITIAL_SIZE = 16;
    final IntList palette;
    final Int2IntMap inversePalette;
    final int sizeBits;
    ChunkData values;

    public DataPaletteImpl(int valuesLength) {
        this(valuesLength, 16);
    }

    public DataPaletteImpl(int valuesLength, int initialSize) {
        this.values = new EmptyChunkData(valuesLength);
        this.sizeBits = Integer.numberOfTrailingZeros(valuesLength) / 3;
        this.palette = new IntArrayList(initialSize);
        this.inversePalette = new Int2IntOpenHashMap((int)((float)initialSize * 0.75f));
        this.inversePalette.defaultReturnValue(-1);
    }

    @Override
    public int index(int x, int y, int z) {
        return (y << this.sizeBits | z) << this.sizeBits | x;
    }

    @Override
    public int idAt(int sectionCoordinate) {
        int index2 = this.values.get(sectionCoordinate);
        return this.palette.getInt(index2);
    }

    @Override
    public void setIdAt(int sectionCoordinate, int id) {
        int index2 = this.inversePalette.get(id);
        if (index2 == -1) {
            index2 = this.palette.size();
            this.palette.add(id);
            this.inversePalette.put(id, index2);
        }
        this.values.set(sectionCoordinate, index2);
    }

    @Override
    public int paletteIndexAt(int packedCoordinate) {
        return this.values.get(packedCoordinate);
    }

    @Override
    public void setPaletteIndexAt(int sectionCoordinate, int index2) {
        this.values.set(sectionCoordinate, index2);
    }

    @Override
    public int size() {
        return this.palette.size();
    }

    @Override
    public int idByIndex(int index2) {
        return this.palette.getInt(index2);
    }

    @Override
    public void setIdByIndex(int index2, int id) {
        int oldId = this.palette.set(index2, id);
        if (oldId == id) {
            return;
        }
        this.inversePalette.put(id, index2);
        if (this.inversePalette.get(oldId) == index2) {
            this.inversePalette.remove(oldId);
            for (int i = 0; i < this.palette.size(); ++i) {
                if (this.palette.getInt(i) != oldId) continue;
                this.inversePalette.put(oldId, i);
                break;
            }
        }
    }

    @Override
    public void replaceId(int oldId, int newId) {
        int index2 = this.inversePalette.remove(oldId);
        if (index2 == -1) {
            return;
        }
        this.inversePalette.put(newId, index2);
        for (int i = 0; i < this.palette.size(); ++i) {
            if (this.palette.getInt(i) != oldId) continue;
            this.palette.set(i, newId);
        }
    }

    @Override
    public void addId(int id) {
        this.inversePalette.put(id, this.palette.size());
        this.palette.add(id);
    }

    @Override
    public void clear() {
        this.palette.clear();
        this.inversePalette.clear();
    }

    private class EmptyChunkData
    implements ChunkData {
        final int size;

        public EmptyChunkData(int size) {
            this.size = size;
        }

        @Override
        public int get(int idx) {
            return 0;
        }

        @Override
        public void set(int idx, int val) {
            if (val != 0) {
                DataPaletteImpl.this.values = new ByteChunkData(this.size);
                DataPaletteImpl.this.values.set(idx, val);
            }
        }
    }

    static interface ChunkData {
        public int get(int var1);

        public void set(int var1, int var2);
    }

    private static class ShortChunkData
    implements ChunkData {
        final short[] data;

        public ShortChunkData(byte[] data) {
            this.data = new short[data.length];
            for (int i = 0; i < data.length; ++i) {
                this.data[i] = (short)(data[i] & 0xFF);
            }
        }

        @Override
        public int get(int idx) {
            return this.data[idx];
        }

        @Override
        public void set(int idx, int val) {
            this.data[idx] = (short)val;
        }
    }

    private class ByteChunkData
    implements ChunkData {
        final byte[] data;

        public ByteChunkData(int size) {
            this.data = new byte[size];
        }

        @Override
        public int get(int idx) {
            return this.data[idx] & 0xFF;
        }

        @Override
        public void set(int idx, int val) {
            if (val > 255) {
                DataPaletteImpl.this.values = new ShortChunkData(this.data);
                DataPaletteImpl.this.values.set(idx, val);
                return;
            }
            this.data[idx] = (byte)val;
        }
    }
}

