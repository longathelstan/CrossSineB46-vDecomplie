/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.MapData;

public class MapStorage
implements StorableObject {
    private final Int2ObjectMap<MapData> maps = new Int2ObjectOpenHashMap<MapData>();

    public MapData getMapData(int id) {
        return (MapData)this.maps.get(id);
    }

    public void putMapData(int id, MapData mapData) {
        this.maps.put(id, mapData);
    }
}

