/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.combat;

import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class AutoBlockCheck
extends Check {
    short blockingTime = 0;
    short buffer = 0;

    public AutoBlockCheck(EntityOtherPlayerMP playerMP) {
        super(playerMP);
        this.name = "AutoBlock";
        this.checkViolationLevel = 2.0;
    }

    @Override
    public void onLivingUpdate() {
        if (((Boolean)HackerDetector.INSTANCE.autoBlockValue.get()).booleanValue()) {
            this.blockingTime = this.handlePlayer.func_70632_aY() ? (short)(this.blockingTime + 1) : (short)0;
            if (this.blockingTime > 10 && this.handlePlayer.field_82175_bq) {
                this.buffer = (short)(this.buffer + 1);
                if (this.buffer > 5) {
                    this.flag("swing when blocking " + this.blockingTime, 1.0);
                }
            } else {
                this.buffer = 0;
            }
        }
    }

    @Override
    public String description() {
        return "Swing when blocking";
    }

    @Override
    public String reportName() {
        return "killAura";
    }
}

