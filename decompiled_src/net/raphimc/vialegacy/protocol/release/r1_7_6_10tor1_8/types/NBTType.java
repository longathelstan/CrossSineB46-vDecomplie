/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types;

import com.viaversion.nbt.io.NBTIO;
import com.viaversion.nbt.limiter.TagLimiter;
import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
        if (length < 0) {
            return null;
        }
        ByteBuf data = buffer.readSlice((int)length);
        GZIPInputStream in = new GZIPInputStream((InputStream)new ByteBufInputStream(data));
        try {
            compoundTag = NBTIO.readTag(new DataInputStream(in), TagLimiter.create(0x200000, 512), true, CompoundTag.class);
        }
        catch (Throwable throwable) {
            try {
                try {
                    ((InputStream)in).close();
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
        ((InputStream)in).close();
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
        ByteBuf data = buffer.alloc().buffer();
        try {
            try (GZIPOutputStream out = new GZIPOutputStream((OutputStream)new ByteBufOutputStream(data));){
                NBTIO.writeTag(new DataOutputStream(out), nbt, true);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            buffer.writeShort(data.readableBytes());
            buffer.writeBytes(data);
        }
        finally {
            data.release();
        }
    }
}

