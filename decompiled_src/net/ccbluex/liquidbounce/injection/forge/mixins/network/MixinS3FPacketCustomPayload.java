/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={S3FPacketCustomPayload.class})
public class MixinS3FPacketCustomPayload {
    @Shadow
    private PacketBuffer field_149171_b;

    @Inject(method={"processPacket(Lnet/minecraft/network/play/INetHandlerPlayClient;)V"}, at={@At(value="TAIL")})
    private void releaseData(INetHandlerPlayClient handler, CallbackInfo ci) {
        if (this.field_149171_b != null) {
            this.field_149171_b.release();
        }
    }
}

