/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={C0FPacketConfirmTransaction.class})
public class MixinC0FPacketConfirmTransaction {
    @Shadow
    private int field_149536_a;
    @Shadow
    private short field_149534_b;
    @Shadow
    private boolean field_149535_c;

    @Overwrite
    public void func_148840_b(PacketBuffer buf) {
        if (ProtocolFixer.newerThanOrEqualsTo1_17()) {
            buf.writeInt(this.field_149536_a);
        } else {
            buf.writeByte(this.field_149536_a);
            buf.writeShort((int)this.field_149534_b);
            buf.writeByte(this.field_149535_c ? 1 : 0);
        }
    }
}

