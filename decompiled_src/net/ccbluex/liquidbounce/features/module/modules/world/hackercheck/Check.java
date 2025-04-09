/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck;

import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.PlayerData;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class Check
extends MinecraftInstance {
    public static boolean debug = false;
    public static PlayerData data = new PlayerData();
    protected EntityOtherPlayerMP handlePlayer = null;
    protected String name = "NONE";
    protected boolean enabled = true;
    protected double violationLevel = 0.0;
    protected double checkViolationLevel = 20.0;
    protected double vlStep = 5.0;

    public Check(EntityOtherPlayerMP playerMP) {
        this.handlePlayer = playerMP;
    }

    public void positionUpdate(double x, double y, double z) {
    }

    public void onLivingUpdate() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void flag(String verbose, double vl) {
        this.violationLevel += vl;
        if (debug) {
            ClientUtils.INSTANCE.displayChatMessage(String.format("\u00a7l\u00a77[\u00a7l\u00a79HackDetector\u00a7l\u00a77]\u00a7r \u00a7c%s\u00a73 failed\u00a72 %s \u00a7r\u00a77(x\u00a74%s\u00a77) %s", this.handlePlayer.func_70005_c_(), this.name, (int)this.violationLevel, verbose));
        }
    }

    public void reward() {
        this.reward(0.1);
    }

    public void reward(double rewardVL) {
        this.violationLevel = Math.max(0.0, this.violationLevel - rewardVL);
    }

    public void shrinkVL(double t) {
        this.violationLevel *= t;
    }

    public boolean wasFailed() {
        return this.violationLevel > this.checkViolationLevel;
    }

    public String description() {
        return "cheating";
    }

    public void reset() {
        this.violationLevel = 0.0;
    }

    public double getPoint() {
        return 5.0;
    }

    public String reportName() {
        return this.name;
    }
}

