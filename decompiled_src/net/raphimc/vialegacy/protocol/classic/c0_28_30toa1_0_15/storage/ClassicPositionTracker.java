/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.model.ChunkCoord;

public class ClassicPositionTracker
implements StorableObject {
    public double posX;
    public double stance;
    public double posZ;
    public float yaw;
    public float pitch;
    public boolean spawned;

    public void writeToPacket(PacketWrapper wrapper) {
        int x = (int)(this.posX * 32.0);
        int y = (int)(this.stance * 32.0);
        int z = (int)(this.posZ * 32.0);
        int yaw = (int)(this.yaw * 256.0f / 360.0f) & 0xFF;
        int pitch = (int)(this.pitch * 256.0f / 360.0f) & 0xFF;
        wrapper.write(Types.BYTE, (byte)-1);
        wrapper.write(Types.SHORT, (short)x);
        wrapper.write(Types.SHORT, (short)y);
        wrapper.write(Types.SHORT, (short)z);
        wrapper.write(Types.BYTE, (byte)(yaw - 128));
        wrapper.write(Types.BYTE, (byte)pitch);
    }

    public BlockPosition getBlockPosition() {
        return new BlockPosition(ClassicPositionTracker.floor(this.posX), ClassicPositionTracker.floor(this.stance), ClassicPositionTracker.floor(this.posZ));
    }

    public ChunkCoord getChunkPosition() {
        BlockPosition pos = this.getBlockPosition();
        return new ChunkCoord(pos.x() >> 4, pos.z() >> 4);
    }

    private static int floor(double f) {
        int i = (int)f;
        return f < (double)i ? i - 1 : i;
    }
}

