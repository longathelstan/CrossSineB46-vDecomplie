/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.netty.viabedrock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class DisconnectHandler
extends ChannelOutboundHandlerAdapter {
    private boolean calledDisconnect = false;

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        if (ctx.channel().isActive() && !this.calledDisconnect) {
            this.calledDisconnect = true;
            ctx.disconnect(promise);
        } else {
            super.close(ctx, promise);
        }
    }
}

