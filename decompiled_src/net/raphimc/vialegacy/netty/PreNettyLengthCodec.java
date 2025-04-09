/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.netty;

import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import java.util.List;
import net.raphimc.vialegacy.netty.PreNettyLengthPrepender;
import net.raphimc.vialegacy.netty.PreNettyLengthRemover;

public class PreNettyLengthCodec
extends ByteToMessageCodec<ByteBuf> {
    protected final PreNettyLengthRemover encoder;
    protected final PreNettyLengthPrepender decoder;

    public PreNettyLengthCodec(UserConnection user) {
        this.encoder = new PreNettyLengthRemover(user);
        this.decoder = new PreNettyLengthPrepender(user);
    }

    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) {
        this.encoder.encode(ctx, in, out);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        this.decoder.decode(ctx, in, out);
    }
}

