/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value={C00Handshake.class})
public class MixinC00Handshake {
    @Shadow
    public int field_149599_c;
    @Shadow
    public String field_149598_b;
    @Shadow
    private int field_149600_a;
    @Shadow
    private EnumConnectionState field_149597_d;

    @ModifyConstant(method={"writePacketData"}, constant={@Constant(stringValue="\u0000FML\u0000")})
    private String injectAntiForge(String constant) {
        return !Minecraft.func_71410_x().func_71387_A() ? "" : "\u0000FML\u0000";
    }
}

