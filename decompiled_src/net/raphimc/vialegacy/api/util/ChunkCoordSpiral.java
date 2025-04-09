/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import net.raphimc.vialegacy.api.model.ChunkCoord;

public class ChunkCoordSpiral
implements Iterable<ChunkCoord> {
    final ChunkCoord center;
    final ChunkCoord lowerBound;
    final ChunkCoord upperBound;
    final int step;
    boolean returnCenter = true;

    public ChunkCoordSpiral(ChunkCoord center, ChunkCoord lowerBound, ChunkCoord upperBound, int step) {
        this.center = center;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.step = step;
    }

    public ChunkCoordSpiral(ChunkCoord center, ChunkCoord radius, int step) {
        this(center, new ChunkCoord(center.chunkX - radius.chunkX, center.chunkZ - radius.chunkZ), new ChunkCoord(center.chunkX + radius.chunkX, center.chunkZ + radius.chunkZ), step);
    }

    public ChunkCoordSpiral(ChunkCoord center, ChunkCoord radius) {
        this(center, radius, 1);
    }

    @Override
    public Iterator<ChunkCoord> iterator() {
        return new Iterator<ChunkCoord>(){
            int x;
            int z;
            float n;
            int floorN;
            int i;
            int j;
            {
                this.x = ChunkCoordSpiral.this.center.chunkX;
                this.z = ChunkCoordSpiral.this.center.chunkZ;
                this.n = 1.0f;
                this.floorN = 1;
                this.i = 0;
                this.j = 0;
            }

            @Override
            public boolean hasNext() {
                return ChunkCoordSpiral.this.returnCenter || this.x >= ChunkCoordSpiral.this.lowerBound.chunkX && this.x <= ChunkCoordSpiral.this.upperBound.chunkX && this.z >= ChunkCoordSpiral.this.lowerBound.chunkZ && this.z <= ChunkCoordSpiral.this.upperBound.chunkZ;
            }

            @Override
            public ChunkCoord next() {
                if (ChunkCoordSpiral.this.returnCenter) {
                    ChunkCoordSpiral.this.returnCenter = false;
                    return new ChunkCoord(this.x, this.z);
                }
                this.floorN = (int)Math.floor(this.n);
                if (this.j < this.floorN) {
                    switch (this.i % 4) {
                        case 0: {
                            this.z += ChunkCoordSpiral.this.step;
                            break;
                        }
                        case 1: {
                            this.x += ChunkCoordSpiral.this.step;
                            break;
                        }
                        case 2: {
                            this.z -= ChunkCoordSpiral.this.step;
                            break;
                        }
                        case 3: {
                            this.x -= ChunkCoordSpiral.this.step;
                        }
                    }
                    ++this.j;
                    return new ChunkCoord(this.x, this.z);
                }
                this.j = 0;
                this.n = (float)((double)this.n + 0.5);
                ++this.i;
                return this.next();
            }
        };
    }

    @Override
    public Spliterator<ChunkCoord> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator(), 16);
    }
}

