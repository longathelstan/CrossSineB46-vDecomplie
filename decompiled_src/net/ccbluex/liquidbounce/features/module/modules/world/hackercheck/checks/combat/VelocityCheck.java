/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/hackercheck/checks/combat/VelocityCheck;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/hackercheck/Check;", "playerMP", "Lnet/minecraft/client/entity/EntityOtherPlayerMP;", "(Lnet/minecraft/client/entity/EntityOtherPlayerMP;)V", "getPlayerMP", "()Lnet/minecraft/client/entity/EntityOtherPlayerMP;", "posX", "", "posY", "posZ", "onLivingUpdate", "", "CrossSine"})
public final class VelocityCheck
extends Check {
    @NotNull
    private final EntityOtherPlayerMP playerMP;
    private double posX;
    private double posY;
    private double posZ;

    public VelocityCheck(@NotNull EntityOtherPlayerMP playerMP) {
        Intrinsics.checkNotNullParameter(playerMP, "playerMP");
        super(playerMP);
        this.playerMP = playerMP;
        this.name = "Velocity";
        this.checkViolationLevel = 5.0;
    }

    @NotNull
    public final EntityOtherPlayerMP getPlayerMP() {
        return this.playerMP;
    }

    @Override
    public void onLivingUpdate() {
        int n;
        if (((Boolean)HackerDetector.INSTANCE.velocityValue.get()).booleanValue() && this.handlePlayer.field_70172_ad <= 0) {
            this.posX = this.handlePlayer.field_70165_t;
            this.posY = this.handlePlayer.field_70163_u;
            this.posZ = this.handlePlayer.field_70161_v;
        }
        boolean bl = 2 <= (n = this.handlePlayer.field_70172_ad) ? n < 7 : false;
        if (bl && this.handlePlayer.field_70165_t == this.posX && this.handlePlayer.field_70163_u == this.posY && this.handlePlayer.field_70161_v == this.posZ) {
            this.flag("No Movement since taking velocity", 5.0);
        }
    }
}

