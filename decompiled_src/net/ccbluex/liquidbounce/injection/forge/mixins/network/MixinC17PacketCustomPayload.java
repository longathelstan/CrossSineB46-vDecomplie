/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={C17PacketCustomPayload.class})
public class MixinC17PacketCustomPayload {
    @Shadow
    private PacketBuffer field_149561_c;

    @Inject(method={"processPacket(Lnet/minecraft/network/play/INetHandlerPlayServer;)V"}, at={@At(value="TAIL")})
    private void releaseData(INetHandlerPlayServer handler, CallbackInfo ci) {
        if (this.field_149561_c != null) {
            this.field_149561_c.release();
        }
    }
}

