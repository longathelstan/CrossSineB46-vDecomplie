/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.types;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.IdAndData;
import io.netty.buffer.ByteBuf;

public class BlockChangeRecordArrayType
extends Type<BlockChangeRecord[]> {
    public BlockChangeRecordArrayType() {
        super(BlockChangeRecord[].class);
    }

    @Override
    public BlockChangeRecord[] read(ByteBuf buffer) {
        int i;
        int length = buffer.readUnsignedShort();
        short[] positions = new short[length];
        short[] blocks = new short[length];
        byte[] metas = new byte[length];
        for (i = 0; i < length; ++i) {
            positions[i] = buffer.readShort();
        }
        for (i = 0; i < length; ++i) {
            blocks[i] = buffer.readUnsignedByte();
        }
        for (i = 0; i < length; ++i) {
            metas[i] = buffer.readByte();
        }
        BlockChangeRecord[] blockChangeRecords = new BlockChangeRecord[length];
        for (int i2 = 0; i2 < length; ++i2) {
            blockChangeRecords[i2] = new BlockChangeRecord1_8(positions[i2] >> 12 & 0xF, positions[i2] & 0xFF, positions[i2] >> 8 & 0xF, IdAndData.toRawData(blocks[i2], metas[i2]));
        }
        return blockChangeRecords;
    }

    @Override
    public void write(ByteBuf buffer, BlockChangeRecord[] records) {
        buffer.writeShort(records.length);
        for (BlockChangeRecord record : records) {
            buffer.writeShort(record.getSectionX() << 12 | record.getSectionZ() << 8 | record.getY(-1));
        }
        for (BlockChangeRecord record : records) {
            buffer.writeByte(record.getBlockId() >> 4);
        }
        for (BlockChangeRecord record : records) {
            buffer.writeByte(record.getBlockId() & 0xF);
        }
    }
}

