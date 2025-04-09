/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import net.ccbluex.liquidbounce.features.module.modules.world.HackerDetector;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.PlayerData;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.combat.AutoBlockCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.combat.VelocityCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.move.NoSlowCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.move.ScaffoldCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.checks.rotation.RotationCheck;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class CheckManager {
    private final PlayerData data = new PlayerData();
    private static final Class<?>[] checksClz = new Class[]{AutoBlockCheck.class, NoSlowCheck.class, ScaffoldCheck.class, RotationCheck.class, VelocityCheck.class};
    private final LinkedList<Check> checks = new LinkedList();
    private double totalVL = 0.0;
    private short addedTicks = 0;

    public CheckManager(EntityOtherPlayerMP target) {
        for (Class<?> clz : checksClz) {
            try {
                this.checks.add((Check)clz.getConstructor(EntityOtherPlayerMP.class).newInstance(target));
            }
            catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void livingUpdate() {
        for (Check check : this.checks) {
            try {
                check.onLivingUpdate();
                if (!check.wasFailed()) continue;
                if (HackerDetector.shouldAlert()) {
                    ClientUtils.INSTANCE.displayChatMessage("\u00a7l\u00a77[\u00a7l\u00a79HackDetector\u00a7l\u00a77]\u00a7F: " + check.handlePlayer.func_145748_c_().func_150254_d() + "dectected for \u00a7C" + check.name);
                }
                this.totalVL += check.getPoint();
                if (HackerDetector.catchPlayer(check.handlePlayer.func_145748_c_().func_150254_d().toString(), check.reportName(), this.totalVL)) {
                    this.totalVL = -5.0;
                }
                this.addedTicks = (short)40;
                check.reset();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.addedTicks = (short)(this.addedTicks - 1);
        if (this.addedTicks <= 0) {
            this.totalVL -= this.totalVL > 0.0 ? 0.005 : 0.0;
        }
    }

    public void positionUpdate(double x, double y, double z) {
        for (Check check : this.checks) {
            try {
                check.positionUpdate(x, y, z);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

