/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;

public class BlockChangeRecordArrayType
extends Type<BlockChangeRecord[]> {
    public BlockChangeRecordArrayType() {
        super(BlockChangeRecord[].class);
    }

    @Override
    public BlockChangeRecord[] read(ByteBuf buffer) {
        int length = buffer.readUnsignedShort();
        int dataLength = buffer.readInt();
        byte[] data = new byte[dataLength];
        buffer.readBytes(data);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(data));
        BlockChangeRecord[] blockChangeRecords = new BlockChangeRecord[length];
        try {
            for (int i = 0; i < length; ++i) {
                short position = dataInputStream.readShort();
                short blockId = dataInputStream.readShort();
                blockChangeRecords[i] = new BlockChangeRecord1_8(position >> 12 & 0xF, position & 0xFF, position >> 8 & 0xF, (int)blockId);
            }
        }
        catch (IOException e) {
            int n = dataLength;
            ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Block change record array length mismatch: Expected " + n + " bytes", e);
        }
        return blockChangeRecords;
    }

    @Override
    public void write(ByteBuf buffer, BlockChangeRecord[] records) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            for (BlockChangeRecord record : records) {
                dataOutputStream.writeShort((short)(record.getSectionX() << 12 | record.getSectionZ() << 8 | record.getY(-1)));
                dataOutputStream.writeShort((short)record.getBlockId());
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] data = byteArrayOutputStream.toByteArray();
        buffer.writeShort(records.length);
        buffer.writeInt(data.length);
        buffer.writeBytes(data);
    }
}

