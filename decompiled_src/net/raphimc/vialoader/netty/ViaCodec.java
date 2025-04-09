/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.netty;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageCodec;
import java.util.List;

public class ViaCodec
extends ByteToMessageCodec<ByteBuf> {
    protected final UserConnection user;

    public ViaCodec(UserConnection user) {
        this.user = user;
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
        if (!this.user.checkOutgoingPacket()) {
            throw CancelEncoderException.generate(null);
        }
        out.writeBytes(in);
        if (this.user.shouldTransformPacket()) {
            this.user.transformOutgoing(out, CancelEncoderException::generate);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!this.user.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(in);
        try {
            if (this.user.shouldTransformPacket()) {
                this.user.transformIncoming(transformedBuf, CancelDecoderException::generate);
            }
            out.add(transformedBuf.retain());
        }
        finally {
            transformedBuf.release();
        }
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        try {
            super.write(ctx, msg, promise);
        }
        catch (Throwable e) {
            if (!PipelineUtil.containsCause(e, CancelCodecException.class)) {
                throw e;
            }
            promise.setSuccess();
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        block2: {
            try {
                super.channelRead(ctx, msg);
            }
            catch (Throwable e) {
                if (PipelineUtil.containsCause(e, CancelCodecException.class)) break block2;
                throw e;
            }
        }
    }

    public boolean isSharable() {
        return this.user != null;
    }
}

