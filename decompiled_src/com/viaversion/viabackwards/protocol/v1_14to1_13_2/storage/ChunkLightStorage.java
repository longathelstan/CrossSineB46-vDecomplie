/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_14to1_13_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChunkLightStorage
implements StorableObject {
    public static final byte[] FULL_LIGHT = new byte[2048];
    public static final byte[] EMPTY_LIGHT = new byte[2048];
    static Constructor<?> fastUtilLongObjectHashMap;
    final Map<Long, ChunkLight> storedLight = this.createLongObjectMap();

    public void setStoredLight(byte[][] skyLight, byte[][] blockLight, int x, int z) {
        this.storedLight.put(this.getChunkSectionIndex(x, z), new ChunkLight(skyLight, blockLight));
    }

    public ChunkLight getStoredLight(int x, int z) {
        return this.storedLight.get(this.getChunkSectionIndex(x, z));
    }

    public void clear() {
        this.storedLight.clear();
    }

    public void unloadChunk(int x, int z) {
        this.storedLight.remove(this.getChunkSectionIndex(x, z));
    }

    long getChunkSectionIndex(int x, int z) {
        return ((long)x & 0x3FFFFFFL) << 38 | (long)z & 0x3FFFFFFL;
    }

    Map<Long, ChunkLight> createLongObjectMap() {
        if (fastUtilLongObjectHashMap != null) {
            try {
                return (Map)fastUtilLongObjectHashMap.newInstance(new Object[0]);
            }
            catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<Long, ChunkLight>();
    }

    static {
        Arrays.fill(FULL_LIGHT, (byte)-1);
        Arrays.fill(EMPTY_LIGHT, (byte)0);
        try {
            fastUtilLongObjectHashMap = Class.forName("com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectOpenHashMap").getConstructor(new Class[0]);
        }
        catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException) {
            // empty catch block
        }
    }

    public static final class ChunkLight {
        final byte[][] skyLight;
        final byte[][] blockLight;

        public ChunkLight(byte[][] skyLight, byte[][] blockLight) {
            this.skyLight = skyLight;
            this.blockLight = blockLight;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ChunkLight that = (ChunkLight)o;
            if (!Arrays.deepEquals((Object[])this.skyLight, (Object[])that.skyLight)) {
                return false;
            }
            return Arrays.deepEquals((Object[])this.blockLight, (Object[])that.blockLight);
        }

        public int hashCode() {
            int result = Arrays.deepHashCode((Object[])this.skyLight);
            result = 31 * result + Arrays.deepHashCode((Object[])this.blockLight);
            return result;
        }

        public byte[][] skyLight() {
            return this.skyLight;
        }

        public byte[][] blockLight() {
            return this.blockLight;
        }

        public String toString() {
            return String.format("%s[skyLight=%s, blockLight=%s]", this.getClass().getSimpleName(), Objects.toString(this.skyLight), Objects.toString(this.blockLight));
        }
    }
}

