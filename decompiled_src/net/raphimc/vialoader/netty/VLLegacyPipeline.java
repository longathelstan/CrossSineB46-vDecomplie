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
import net.raphimc.viabedrock.netty.BatchLengthCodec;
import net.raphimc.viabedrock.netty.PacketEncapsulationCodec;
import net.raphimc.vialegacy.netty.PreNettyLengthPrepender;
import net.raphimc.vialegacy.netty.PreNettyLengthRemover;
import net.raphimc.vialoader.netty.CompressionReorderEvent;
import net.raphimc.vialoader.netty.ViaDecoder;
import net.raphimc.vialoader.netty.ViaEncoder;
import net.raphimc.vialoader.netty.viabedrock.DisconnectHandler;
import net.raphimc.vialoader.netty.viabedrock.RakMessageEncapsulationCodec;

public abstract class VLLegacyPipeline
extends ChannelInboundHandlerAdapter {
    public static final String VIA_DECODER_NAME = "via-decoder";
    public static final String VIA_ENCODER_NAME = "via-encoder";
    public static final String VIALEGACY_PRE_NETTY_LENGTH_PREPENDER_NAME = "vialegacy-pre-netty-length-prepender";
    public static final String VIALEGACY_PRE_NETTY_LENGTH_REMOVER_NAME = "vialegacy-pre-netty-length-remover";
    public static final String VIABEDROCK_DISCONNECT_HANDLER_NAME = "viabedrock-disconnect-handler";
    public static final String VIABEDROCK_FRAME_ENCAPSULATION_HANDLER_NAME = "viabedrock-frame-encapsulation";
    public static final String VIABEDROCK_PACKET_ENCAPSULATION_HANDLER_NAME = "viabedrock-packet-encapsulation";
    protected final UserConnection user;
    protected final ProtocolVersion version;

    public VLLegacyPipeline(UserConnection user) {
        this(user, Via.getManager().getProviders().get(VersionProvider.class).getServerProtocol(user));
    }

    @Deprecated
    public VLLegacyPipeline(UserConnection user, ProtocolVersion version) {
        this.user = user;
        this.version = version;
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        ctx.pipeline().addBefore(this.packetDecoderName(), VIA_DECODER_NAME, this.createViaDecoder());
        ctx.pipeline().addBefore(this.packetEncoderName(), VIA_ENCODER_NAME, this.createViaEncoder());
        ProtocolVersion r1_6_4 = ProtocolVersion.getProtocol(VersionType.RELEASE_INITIAL, 78);
        if (r1_6_4.isKnown() && this.version.olderThanOrEqualTo(r1_6_4)) {
            ctx.pipeline().addBefore(this.lengthSplitterName(), VIALEGACY_PRE_NETTY_LENGTH_PREPENDER_NAME, this.createViaLegacyPreNettyLengthPrepender());
            ctx.pipeline().addBefore(this.lengthPrependerName(), VIALEGACY_PRE_NETTY_LENGTH_REMOVER_NAME, this.createViaLegacyPreNettyLengthRemover());
        } else if (this.version.getName().startsWith("Bedrock")) {
            ctx.pipeline().addBefore(this.lengthSplitterName(), VIABEDROCK_DISCONNECT_HANDLER_NAME, this.createViaBedrockDisconnectHandler());
            ctx.pipeline().addBefore(this.lengthSplitterName(), VIABEDROCK_FRAME_ENCAPSULATION_HANDLER_NAME, this.createViaBedrockFrameEncapsulationHandler());
            this.replaceLengthSplitter(ctx, this.createViaBedrockBatchLengthCodec());
            ctx.pipeline().remove(this.lengthPrependerName());
            ctx.pipeline().addBefore(VIA_DECODER_NAME, VIABEDROCK_PACKET_ENCAPSULATION_HANDLER_NAME, this.createViaBedrockPacketEncapsulationHandler());
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (CompressionReorderEvent.INSTANCE.equals(evt)) {
            int decoderIndex = ctx.pipeline().names().indexOf(this.decompressName());
            if (decoderIndex == -1) {
                return;
            }
            if (decoderIndex > ctx.pipeline().names().indexOf(VIA_DECODER_NAME)) {
                ChannelHandler decoder = ctx.pipeline().get(VIA_DECODER_NAME);
                ChannelHandler encoder = ctx.pipeline().get(VIA_ENCODER_NAME);
                ctx.pipeline().remove(decoder);
                ctx.pipeline().remove(encoder);
                ctx.pipeline().addAfter(this.decompressName(), VIA_DECODER_NAME, decoder);
                ctx.pipeline().addAfter(this.compressName(), VIA_ENCODER_NAME, encoder);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    protected ChannelHandler createViaDecoder() {
        return new ViaDecoder(this.user);
    }

    protected ChannelHandler createViaEncoder() {
        return new ViaEncoder(this.user);
    }

    protected ChannelHandler createViaLegacyPreNettyLengthPrepender() {
        return new PreNettyLengthPrepender(this.user);
    }

    protected ChannelHandler createViaLegacyPreNettyLengthRemover() {
        return new PreNettyLengthRemover(this.user);
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

    protected void replaceLengthSplitter(ChannelHandlerContext ctx, ChannelHandler handler) {
        ctx.pipeline().replace(this.lengthSplitterName(), this.lengthSplitterName(), handler);
    }

    protected abstract String decompressName();

    protected abstract String compressName();

    protected abstract String packetDecoderName();

    protected abstract String packetEncoderName();

    protected abstract String lengthSplitterName();

    protected abstract String lengthPrependerName();
}

