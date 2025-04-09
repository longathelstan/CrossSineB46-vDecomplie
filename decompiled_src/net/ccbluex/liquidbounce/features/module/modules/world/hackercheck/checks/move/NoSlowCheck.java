/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.move;

import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class NoSlowCheck
extends Check {
    short sprintBuffer = 0;
    short motionBuffer = 0;

    public NoSlowCheck(EntityOtherPlayerMP playerMP) {
        super(playerMP);
        this.name = "NoSlow";
        this.checkViolationLevel = 20.0;
    }

    @Override
    public void onLivingUpdate() {
        if (((Boolean)HackerDetector.INSTANCE.noSlowValue.get()).booleanValue() && (this.handlePlayer.func_71039_bw() || this.handlePlayer.func_70632_aY())) {
            if (this.handlePlayer.func_70051_ag()) {
                this.sprintBuffer = (short)(this.sprintBuffer + 1);
                if (this.sprintBuffer > 5) {
                    this.flag("Sprinting when using item or blocking", 5.0);
                }
                return;
            }
            double dx = this.handlePlayer.field_70169_q - this.handlePlayer.field_70165_t;
            double dz = this.handlePlayer.field_70166_s - this.handlePlayer.field_70161_v;
            if (dx * dx + dz * dz > 0.0625 && (this.motionBuffer = (short)(this.motionBuffer + 1)) > 10) {
                this.flag("NoSprint but keep in sprint motion when blocking", 5.0);
                this.motionBuffer = (short)7;
                return;
            }
            this.motionBuffer = (short)(this.motionBuffer - (short)(this.motionBuffer > 0 ? 1 : 0));
            this.sprintBuffer = (short)(this.sprintBuffer - (short)(this.sprintBuffer > 0 ? 1 : 0));
            if (this.sprintBuffer < 2) {
                this.reward();
            }
        }
    }

    @Override
    public String description() {
        return "using item and moving suspiciously";
    }
}

