/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.type.item;

import com.viaversion.nbt.io.NBTIO;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTType
extends Type<CompoundTag> {
    public NBTType() {
        super(CompoundTag.class);
    }

    @Override
    public CompoundTag read(ByteBuf buffer) {
        CompoundTag compoundTag;
        short length = buffer.readShort();
        if (length <= 0) {
            return null;
        }
        ByteBuf compressed = buffer.readSlice((int)length);
        GZIPInputStream gzipStream = new GZIPInputStream((InputStream)new ByteBufInputStream(compressed));
        try {
            compoundTag = NBTIO.reader(CompoundTag.class).named().read(gzipStream);
        }
        catch (Throwable throwable) {
            try {
                try {
                    gzipStream.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        gzipStream.close();
        return compoundTag;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf buffer, CompoundTag nbt) {
        if (nbt == null) {
            buffer.writeShort(-1);
            return;
        }
        ByteBuf compressedBuf = buffer.alloc().buffer();
        try {
            try (GZIPOutputStream gzipStream = new GZIPOutputStream((OutputStream)new ByteBufOutputStream(compressedBuf));){
                NBTIO.writer().named().write(gzipStream, (Tag)nbt);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            buffer.writeShort(compressedBuf.readableBytes());
            buffer.writeBytes(compressedBuf);
        }
        finally {
            compressedBuf.release();
        }
    }
}

