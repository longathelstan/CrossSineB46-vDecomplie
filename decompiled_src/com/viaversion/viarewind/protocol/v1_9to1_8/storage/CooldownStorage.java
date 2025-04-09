/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.storage;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.v1_9to1_8.cooldown.CooldownVisualization;
import com.viaversion.viarewind.protocol.v1_9to1_8.storage.BlockPlaceDestroyTracker;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.util.Pair;
import java.util.List;
import java.util.logging.Level;

public class CooldownStorage
implements StorableObject {
    private CooldownVisualization.Factory visualizationFactory = CooldownVisualization.Factory.fromConfiguration();
    private CooldownVisualization current;
    private double attackSpeed = 4.0;
    private long lastHit = 0L;

    public void tick(UserConnection connection) {
        if (!this.hasCooldown()) {
            this.endCurrentVisualization();
            return;
        }
        BlockPlaceDestroyTracker tracker = connection.get(BlockPlaceDestroyTracker.class);
        if (tracker.isMining()) {
            this.lastHit = 0L;
            this.endCurrentVisualization();
            return;
        }
        if (this.current == null) {
            this.current = this.visualizationFactory.create(connection);
        }
        try {
            this.current.show(this.getCooldown());
        }
        catch (Exception exception) {
            ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Unable to show cooldown visualization", exception);
        }
    }

    private void endCurrentVisualization() {
        if (this.current != null) {
            try {
                this.current.hide();
            }
            catch (Exception exception) {
                ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Unable to hide cooldown visualization", exception);
            }
            this.current = null;
        }
    }

    public boolean hasCooldown() {
        long time = System.currentTimeMillis() - this.lastHit;
        double cooldown = this.restrain((double)time * this.attackSpeed / 1000.0, 1.5);
        return cooldown > 0.1 && cooldown < 1.1;
    }

    public double getCooldown() {
        long time = System.currentTimeMillis() - this.lastHit;
        return this.restrain((double)time * this.attackSpeed / 1000.0, 1.0);
    }

    private double restrain(double x, double b) {
        if (x < 0.0) {
            return 0.0;
        }
        return Math.min(x, b);
    }

    public void setAttackSpeed(double base, List<Pair<Byte, Double>> modifiers) {
        int j;
        this.attackSpeed = base;
        for (j = 0; j < modifiers.size(); ++j) {
            if (modifiers.get(j).key() != 0) continue;
            this.attackSpeed += modifiers.get(j).value().doubleValue();
            modifiers.remove(j--);
        }
        for (j = 0; j < modifiers.size(); ++j) {
            if (modifiers.get(j).key() != 1) continue;
            this.attackSpeed += base * modifiers.get(j).value();
            modifiers.remove(j--);
        }
        for (j = 0; j < modifiers.size(); ++j) {
            if (modifiers.get(j).key() != 2) continue;
            this.attackSpeed *= 1.0 + modifiers.get(j).value();
            modifiers.remove(j--);
        }
    }

    public void hit() {
        this.lastHit = System.currentTimeMillis();
    }

    public void setLastHit(long lastHit) {
        this.lastHit = lastHit;
    }

    public CooldownVisualization.Factory getVisualizationFactory() {
        return this.visualizationFactory;
    }

    public void setVisualizationFactory(CooldownVisualization.Factory visualizationFactory) {
        this.visualizationFactory = visualizationFactory;
    }
}

