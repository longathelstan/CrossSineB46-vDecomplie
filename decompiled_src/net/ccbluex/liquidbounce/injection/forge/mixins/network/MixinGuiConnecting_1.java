/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.net.InetAddress;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.protocol.api.ExtendedServerData;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets={"net.minecraft.client.multiplayer.GuiConnecting$1"})
public class MixinGuiConnecting_1 {
    @Redirect(method={"run"}, at=@At(value="INVOKE", target="Lnet/minecraft/network/NetworkManager;func_181124_a(Ljava/net/InetAddress;IZ)Lnet/minecraft/network/NetworkManager;"), remap=false)
    public NetworkManager trackVersion(InetAddress address, int i, boolean b) {
        ProtocolVersion version;
        if (MinecraftInstance.mc.func_147104_D() instanceof ExtendedServerData && (version = ((ExtendedServerData)MinecraftInstance.mc.func_147104_D()).viaForge$getVersion()) != null) {
            ProtocolBase.getManager().setTargetVersionSilent(version);
        }
        return NetworkManager.func_181124_a((InetAddress)address, (int)i, (boolean)b);
    }
}

