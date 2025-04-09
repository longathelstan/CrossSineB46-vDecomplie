/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.netty;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.protocol.version.VersionType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import net.raphimc.viabedrock.netty.BatchLengthCodec;
import net.raphimc.viabedrock.netty.PacketEncapsulationCodec;
import net.raphimc.vialegacy.netty.PreNettyLengthCodec;
import net.raphimc.vialoader.netty.CompressionReorderEvent;
import net.raphimc.vialoader.netty.ViaCodec;
import net.raphimc.vialoader.netty.viabedrock.DisconnectHandler;
import net.raphimc.vialoader.netty.viabedrock.RakMessageEncapsulationCodec;

public abstract class VLPipeline
extends ChannelInboundHandlerAdapter {
    public static final String VIA_CODEC_NAME = "via-codec";
    public static final String VIALEGACY_PRE_NETTY_LENGTH_CODEC_NAME = "vialegacy-pre-netty-length-codec";
    public static final String VIABEDROCK_DISCONNECT_HANDLER_NAME = "viabedrock-disconnect-handler";
    public static final String VIABEDROCK_FRAME_ENCAPSULATION_HANDLER_NAME = "viabedrock-frame-encapsulation";
    public static final String VIABEDROCK_PACKET_ENCAPSULATION_HANDLER_NAME = "viabedrock-packet-encapsulation";
    protected final UserConnection user;
    protected final ProtocolVersion version;

    public VLPipeline(UserConnection user) {
        this(user, Via.getManager().getProviders().get(VersionProvider.class).getServerProtocol(user));
    }

    @Deprecated
    public VLPipeline(UserConnection user, ProtocolVersion version) {
        this.user = user;
        this.version = version;
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        ctx.pipeline().addBefore(this.packetCodecName(), VIA_CODEC_NAME, this.createViaCodec());
        ProtocolVersion r1_6_4 = ProtocolVersion.getProtocol(VersionType.RELEASE_INITIAL, 78);
        if (r1_6_4.isKnown() && this.version.olderThanOrEqualTo(r1_6_4)) {
            ctx.pipeline().addBefore(this.lengthCodecName(), VIALEGACY_PRE_NETTY_LENGTH_CODEC_NAME, this.createViaLegacyPreNettyLengthCodec());
        } else if (this.version.getName().startsWith("Bedrock")) {
            ctx.pipeline().addBefore(this.lengthCodecName(), VIABEDROCK_DISCONNECT_HANDLER_NAME, this.createViaBedrockDisconnectHandler());
            ctx.pipeline().addBefore(this.lengthCodecName(), VIABEDROCK_FRAME_ENCAPSULATION_HANDLER_NAME, this.createViaBedrockFrameEncapsulationHandler());
            this.replaceLengthCodec(ctx, this.createViaBedrockBatchLengthCodec());
            ctx.pipeline().addBefore(VIA_CODEC_NAME, VIABEDROCK_PACKET_ENCAPSULATION_HANDLER_NAME, this.createViaBedrockPacketEncapsulationHandler());
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ChannelPipeline pipeline;
        if (CompressionReorderEvent.INSTANCE.equals(evt) && (pipeline = ctx.pipeline()).names().indexOf(this.compressionCodecName()) > pipeline.names().indexOf(VIA_CODEC_NAME)) {
            pipeline.addAfter(this.compressionCodecName(), VIA_CODEC_NAME, pipeline.remove(VIA_CODEC_NAME));
        }
        super.userEventTriggered(ctx, evt);
    }

    protected ChannelHandler createViaCodec() {
        return new ViaCodec(this.user);
    }

    protected ChannelHandler createViaLegacyPreNettyLengthCodec() {
        return new PreNettyLengthCodec(this.user);
    }

    protected ChannelHandler createViaBedrockDisconnectHandler() {
        return new DisconnectHandler();
    }

    protected ChannelHandler createViaBedrockFrameEncapsulationHandler() {
        return new RakMessageEncapsulationCodec();
    }

    protected ChannelHandler createViaBedrockBatchLengthCodec() {
        return new BatchLengthCodec();
    }

    protected ChannelHandler createViaBedrockPacketEncapsulationHandler() {
        return new PacketEncapsulationCodec();
    }

    protected void replaceLengthCodec(ChannelHandlerContext ctx, ChannelHandler handler) {
        ctx.pipeline().replace(this.lengthCodecName(), this.lengthCodecName(), handler);
    }

    protected abstract String compressionCodecName();

    protected abstract String packetCodecName();

    protected abstract String lengthCodecName();
}

