/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={S32PacketConfirmTransaction.class})
public class MixinS32PacketConfirmTransaction {
    @Shadow
    private int field_148894_a;
    @Shadow
    private short field_148892_b;
    @Shadow
    private boolean field_148893_c;

    @Overwrite
    public void func_148837_a(PacketBuffer buf) {
        if (ProtocolFixer.newerThanOrEqualsTo1_17()) {
            this.field_148894_a = buf.readInt();
        } else {
            this.field_148894_a = buf.readUnsignedByte();
            this.field_148892_b = buf.readShort();
            this.field_148893_c = buf.readBoolean();
        }
    }
}

