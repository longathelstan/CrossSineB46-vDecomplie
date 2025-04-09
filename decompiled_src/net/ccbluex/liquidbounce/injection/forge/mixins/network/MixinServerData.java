/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.ccbluex.liquidbounce.protocol.api.ExtendedServerData;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={ServerData.class})
public class MixinServerData
implements ExtendedServerData {
    @Unique
    private ProtocolVersion viaForge$version;

    @Inject(method={"getNBTCompound"}, at={@At(value="INVOKE", target="Lnet/minecraft/nbt/NBTTagCompound;setString(Ljava/lang/String;Ljava/lang/String;)V", ordinal=0)}, locals=LocalCapture.CAPTURE_FAILHARD)
    public void saveVersion(CallbackInfoReturnable<NBTTagCompound> cir, NBTTagCompound nbttagcompound) {
        if (this.viaForge$version != null) {
            nbttagcompound.func_74768_a("viaForge$version", this.viaForge$version.getVersion());
        }
    }

    @Inject(method={"getServerDataFromNBTCompound"}, at={@At(value="TAIL")})
    private static void getVersion(NBTTagCompound nbtCompound, CallbackInfoReturnable<ServerData> cir) {
        if (nbtCompound.func_74764_b("viaForge$version")) {
            ProtocolVersion version = nbtCompound.func_74762_e("viaForge$version") != 0 ? ProtocolVersion.getProtocol(nbtCompound.func_74762_e("viaForge$version")) : ProtocolVersion.getClosest(nbtCompound.func_74779_i("viaForge$version"));
            ((ExtendedServerData)cir.getReturnValue()).viaForge$setVersion(version);
        }
    }

    @Inject(method={"copyFrom"}, at={@At(value="HEAD")})
    public void track(ServerData serverDataIn, CallbackInfo ci) {
        if (serverDataIn instanceof ExtendedServerData) {
            this.viaForge$version = ((ExtendedServerData)serverDataIn).viaForge$getVersion();
        }
    }

    @Override
    public ProtocolVersion viaForge$getVersion() {
        return this.viaForge$version;
    }

    @Override
    public void viaForge$setVersion(ProtocolVersion version) {
        this.viaForge$version = version;
    }
}

