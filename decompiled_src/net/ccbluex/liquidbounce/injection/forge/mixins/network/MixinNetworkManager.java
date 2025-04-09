/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import java.net.InetAddress;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.protocol.api.VFNetworkManager;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.util.LazyLoadBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={NetworkManager.class})
public abstract class MixinNetworkManager
implements VFNetworkManager {
    @Shadow
    private Channel field_150746_k;
    @Shadow
    private INetHandler field_150744_m;
    @Unique
    private ProtocolVersion viaForge$targetVersion;

    @Inject(method={"func_181124_a"}, at={@At(value="INVOKE", target="Lio/netty/bootstrap/Bootstrap;group(Lio/netty/channel/EventLoopGroup;)Lio/netty/bootstrap/AbstractBootstrap;")}, locals=LocalCapture.CAPTURE_FAILHARD, remap=false)
    private static void trackSelfTarget(InetAddress address, int serverPort, boolean useNativeTransport, CallbackInfoReturnable<NetworkManager> cir, NetworkManager networkmanager, Class oclass, LazyLoadBase lazyloadbase) {
        ((VFNetworkManager)networkmanager).viaForge$setTrackedVersion(ProtocolBase.getManager().getTargetVersion());
    }

    @Inject(method={"setCompressionTreshold"}, at={@At(value="RETURN")})
    public void reorderPipeline(int p_setCompressionTreshold_1_, CallbackInfo ci) {
        ProtocolBase.getManager().reorderCompression(this.field_150746_k);
    }

    @Override
    public ProtocolVersion viaForge$getTrackedVersion() {
        return this.viaForge$targetVersion;
    }

    @Override
    public void viaForge$setTrackedVersion(ProtocolVersion version) {
        this.viaForge$targetVersion = version;
    }

    @Overwrite
    protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet p_channelRead0_2_) throws Exception {
        PacketEvent event = new PacketEvent(p_channelRead0_2_, PacketEvent.Type.RECEIVE);
        CrossSine.eventManager.callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        if (this.field_150746_k.isOpen()) {
            try {
                p_channelRead0_2_.func_148833_a(this.field_150744_m);
            }
            catch (ThreadQuickExitException threadQuickExitException) {
                // empty catch block
            }
        }
    }

    @Inject(method={"sendPacket(Lnet/minecraft/network/Packet;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void send(Packet<?> packet, CallbackInfo callback) {
        if (PacketUtils.INSTANCE.getPacketType(packet) != PacketUtils.PacketType.CLIENTSIDE) {
            return;
        }
        if (!PacketUtils.INSTANCE.handleSendPacket(packet)) {
            PacketEvent event = new PacketEvent(packet, PacketEvent.Type.SEND);
            CrossSine.eventManager.callEvent(event);
            if (event.isCancelled()) {
                callback.cancel();
            } else if (BlinkUtils.INSTANCE.pushPacket(packet)) {
                callback.cancel();
            }
        }
    }
}

