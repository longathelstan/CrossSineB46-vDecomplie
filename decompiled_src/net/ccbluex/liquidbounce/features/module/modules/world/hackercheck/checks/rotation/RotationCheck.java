/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.rotation;

import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class RotationCheck
extends Check {
    public RotationCheck(EntityOtherPlayerMP playerMP) {
        super(playerMP);
        this.name = "RotationInvalid";
        this.checkViolationLevel = 20.0;
    }

    @Override
    public void onLivingUpdate() {
        if (((Boolean)HackerDetector.INSTANCE.rotationValue.get()).booleanValue() && (this.handlePlayer.field_70125_A > 90.0f || this.handlePlayer.field_70125_A < -90.0f)) {
            this.flag("Invalid Rotation pitch", 5.0);
        }
    }
}

